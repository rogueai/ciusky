package dev.rogueai.ciusky.service.ext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class OpenLibraryApi {

    private static final Log logger = LogFactory.getLog(OpenLibraryApi.class);

    private final RestTemplate restTemplate;

    public OpenLibraryApi() {
        this.restTemplate = new RestTemplate();
    }

    public Root getRoot(String url) {
        logger.debug("Calling api " + url);
        return restTemplate.getForObject(url, Root.class);
    }

    public byte[] getImage(String url) {
        logger.debug("Calling api " + url);
        return restTemplate.getForObject(url, byte[].class);
    }

    public String querySearchByTitle(String title, int limit) {
        String titleSearchStr = title.trim().replace(" ", "+");
        UriComponents uriComponents = UriComponentsBuilder //
                .newInstance() //
                .scheme("https") //
                .host("openlibrary.org") //
                .path("search.json") //
                .query("title={title}&limit={limit}") //
                .buildAndExpand(titleSearchStr, limit);
        return uriComponents.encode().toString();
    }

    public String queryImage(String olid) {
        UriComponents uriComponents = UriComponentsBuilder //
                .newInstance() //
                .scheme("https") //
                .host("covers.openlibrary.org") //
                .path("b/olid/{olid}-M.jpg") //
                .buildAndExpand(olid);
        return uriComponents.encode().toString();
    }
}
