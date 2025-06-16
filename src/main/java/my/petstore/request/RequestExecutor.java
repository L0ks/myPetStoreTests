package my.petstore.request;

import my.petstore.data.StoreClientData;
import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RequestExecutor {
    private static final Logger logger = LogManager.getLogger(RequestExecutor.class);
    private final RestTemplate restTemplate;
    StoreClientData storeClientData = new StoreClientData();

    public RequestExecutor() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(3000);
        factory.setReadTimeout(5000);
        restTemplate = new RestTemplate(factory);
    }

    private URI createUri(String endpoint, Map<String, List<?>> queryparams) {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUri(URI.create(storeClientData.getBaseUrl()))
                .pathSegment(endpoint);

        queryparams.forEach(builder::queryParam);
        return builder.build().toUri();
    }

    private <T, B> ResponseEntity<T> executeRequest(
            HttpMethod method,
            String endpoint,
            B body,
            Map<String, List<?>> queryParams,
            ParameterizedTypeReference<T> responseType
    ) {
        URI uri = createUri(endpoint, queryParams);
        logger.info("Executing " + method + " request to: " + uri);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<B> entity = (body != null) ? new HttpEntity<>(body, headers) : emptyEntity();

        try {
            return restTemplate.exchange(uri, method, entity, responseType);
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            logger.warn("HTTP error response: " + ex.getStatusCode() + " - " + ex.getResponseBodyAsString());

            // Create response with raw body and status code
            HttpHeaders errorHeaders = new HttpHeaders();
            errorHeaders.setContentType(MediaType.APPLICATION_JSON);

            return new ResponseEntity<>(null, errorHeaders, ex.getStatusCode());
        }
    }

    public <T> ResponseEntity<T> executeRequest(String endpoint, ParameterizedTypeReference<T> responseType) {
        return executeRequest(HttpMethod.GET, endpoint, null, Collections.emptyMap(), responseType);
    }

    public <T, B> ResponseEntity<T> executeRequest(String endpoint, ParameterizedTypeReference<T> responseType, B body) {
        return executeRequest(HttpMethod.POST, endpoint, body, Collections.emptyMap(), responseType);
    }

    public <T> ResponseEntity<T> executeRequest(HttpMethod method, String endpointTemplate, Object pathParam, ParameterizedTypeReference<T> responseType) {
        String endpoint = endpointTemplate.replace("{param}", pathParam.toString());
        return executeRequest(method, endpoint, null, Collections.emptyMap(), responseType);
    }

    @SuppressWarnings("unchecked")
    public static <T> HttpEntity<T> emptyEntity() {
        return (HttpEntity<T>) HttpEntity.EMPTY;
    }

}
