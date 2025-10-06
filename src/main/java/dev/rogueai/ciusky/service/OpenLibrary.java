package dev.rogueai.ciusky.service;

import dev.rogueai.ciusky.service.model.openlibrary.Root;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Duration;

@Service
public class OpenLibrary {

    private final RestTemplate restTemplate;

    private final Bucket bucket;

    public OpenLibrary() {
        this.restTemplate = new RestTemplate();

        Bandwidth bandwidth = Bandwidth.builder().capacity(1).refillGreedy(1, Duration.ofSeconds(60)).build();
        this.bucket = Bucket.builder().addLimit(bandwidth).build();
    }

    public Throttled<Root> search(String title, int limit) {
        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
        if (probe.isConsumed()) {
            String titleSearchStr = title.replace(" ", "+");
            UriComponents uriComponents = UriComponentsBuilder.newInstance().scheme("https") //
                    .host("openlibrary.org").path("search.json") //
                    .query("title={title}&limit={limit}").buildAndExpand(titleSearchStr, limit);
            String url = uriComponents.encode().toString();
            Root response = restTemplate.getForObject(url, Root.class);
            return new Throttled<>(response, 0);
        }
        long waitForRefill = probe.getNanosToWaitForRefill() / 1_000_000_000;
        return new Throttled<>(null, (int) ((waitForRefill / 60.0) * 100));

    }

}
