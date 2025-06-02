package my.petstore;

import my.petstore.enums.StoreEndpoints;
import my.petstore.util.OrderDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class StoreClient {
    RequestExecutor requestExecutor = new RequestExecutor();

    public ResponseEntity<Map<String, Integer>> getPetInventories() {
        return requestExecutor.executeRequest(StoreEndpoints.INTENTORY.getEndpoint(), new ParameterizedTypeReference<Map<String, Integer>>() {});
    }

    public ResponseEntity<OrderDto> placePetOrder(OrderDto dto) {
        return requestExecutor.executeRequest(StoreEndpoints.PLACEORDER.getEndpoint(), new ParameterizedTypeReference<OrderDto>() {}, dto);
    }

    public ResponseEntity<OrderDto> getOrder(Integer orderID) {
        return requestExecutor.executeRequest(HttpMethod.GET, StoreEndpoints.CHECKORDER.getEndpoint(), orderID, new ParameterizedTypeReference<OrderDto>() {});
    }

    public ResponseEntity<Void> deleteOrder(Integer orderID) {
        return requestExecutor.executeRequest(HttpMethod.DELETE, StoreEndpoints.DELETEORDER.getEndpoint(), orderID, new ParameterizedTypeReference<Void>() {});
    }


}
