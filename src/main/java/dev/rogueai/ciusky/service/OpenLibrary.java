package dev.rogueai.ciusky.service;

import dev.rogueai.ciusky.service.ext.OpenLibraryApi;
import dev.rogueai.ciusky.service.ext.Root;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@CacheConfig(cacheNames = { "openLibraryData", "openLibraryImages" })
public class OpenLibrary {

    private static final Log logger = LogFactory.getLog(OpenLibrary.class);

    private final static int BANDWIDTH_CAPACITY = 2;

    private final static int BANDWIDTH_REFILL = 1;

    private final static int BANDWIDTH_REFILL_SECONDS = 60;

    private final Bucket bucket;

    @Autowired
    OpenLibraryApi openLibraryApi;

    @Autowired
    CacheManager cacheManager;

    public OpenLibrary() {
        Bandwidth bandwidth = Bandwidth.builder().capacity(BANDWIDTH_CAPACITY) //
                .refillGreedy(BANDWIDTH_REFILL, Duration.ofSeconds(BANDWIDTH_REFILL_SECONDS)).build();
        this.bucket = Bucket.builder().addLimit(bandwidth).build();
    }

    public Throttled<Root> searchByTitle(String title, int limit) {
        String key = openLibraryApi.querySearchByTitle(title, limit);

        logger.info("looking at " + key);

        Cache cache = cacheManager.getCache("openLibraryData");

        if (cache == null) {
            throw new RuntimeException("Cache `openLibraryData` not found!");
        }

        Root value = cache.get(key, Root.class);
        if (value != null) {
            logger.info("returned cached value for key " + key);
            return new Throttled<>(value, 0);
        }

        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
        if (probe.isConsumed()) {
            value = openLibraryApi.getRoot(key);
            cache.put(key, value);
            logger.info("returned fresh value for key " + key);
            return new Throttled<>(value, 0);
        }

        float waitForRefill = (float) probe.getNanosToWaitForRefill() / 1_000_000_000;
        logger.info("waiting for refill " + waitForRefill + " seconds");
        return new Throttled<>(null, (int) ((waitForRefill / BANDWIDTH_REFILL_SECONDS) * 100));
    }

    public byte[] getImage(String olid) {
        String key = openLibraryApi.queryImage(olid);
        Cache cache = cacheManager.getCache("openLibraryImages");

        if (cache == null) {
            throw new RuntimeException("Cache `openLibraryImages` not found!");
        }

        byte[] value = cache.get(key, byte[].class);
        if (value != null) {
            return value;
        }

        value = openLibraryApi.getImage(key);
        cache.put(key, value);
        return value;
    }

}
