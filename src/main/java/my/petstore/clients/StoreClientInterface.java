package my.petstore.clients;

import my.petstore.dto.OrderDto;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface StoreClientInterface
{
    ResponseEntity<Map<String, Integer>> getPetInventories();
    ResponseEntity<OrderDto> placePetOrder(OrderDto dto);
    ResponseEntity<OrderDto> getOrder(Integer orderID);
    ResponseEntity<Void> deleteOrder(Integer orderID);
}
