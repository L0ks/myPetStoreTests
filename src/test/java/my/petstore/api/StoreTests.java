package my.petstore.api;

import lombok.SneakyThrows;
import my.petstore.api.base.StoreTestBase;
import my.petstore.dto.OrderDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.stream.Stream;

public class StoreTests extends StoreTestBase {

    static Stream<Arguments> orderDtoValidArgumentsStream() {
        return Stream.of(
                Arguments.of(new OrderDto(randomSmallInt(), randomPositiveInt(), randomPositiveInt(), date, "placed", randomBoolean())));
    }

    @SneakyThrows
    @Tag("store")
    @Tag("happy")
    @Tag("regression")
    @Test
    public void inventoryTest() {
        ResponseEntity<Map<String, Integer>> response = client.getPetInventories();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode(), "failed to retrieve inventory");
    }

    @SneakyThrows
    @Tag("store")
    @Tag("happy")
    @Tag("regression")
    @ParameterizedTest
    @MethodSource("orderDtoValidArgumentsStream")
    public void placeOrderTest(OrderDto dto) {
        ResponseEntity<OrderDto> response = client.placePetOrder(dto);
        OrderDto actualResponse = response.getBody();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode(), "Failed to place pet order");
        Assertions.assertEquals(dto, actualResponse, "Expected response for placed order does not match actual response");
    }

    @SneakyThrows
    @Tag("store")
    @Tag("happy")
    @Tag("regression")
    @ParameterizedTest
    @MethodSource("orderDtoValidArgumentsStream")
    public void checkDtoTest(OrderDto dto) {
        int orderID = dto.getId();
        client.placePetOrder(dto);

        ResponseEntity<OrderDto> response = client.getOrder(orderID);
        OrderDto actualResponse = response.getBody();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode() , "Failed to retrieve pet order");
        Assertions.assertEquals(dto, actualResponse, "Expected response for placed order does not match actual response");
    }

    @SneakyThrows
    @Tag("store")
    @Tag("happy")
    @Tag("regression")
    @ParameterizedTest
    @MethodSource("orderDtoValidArgumentsStream")
    public void deleteOrderTest(OrderDto dto) {
        int orderID = dto.getId();
        client.placePetOrder(dto);

        ResponseEntity<Void> responseDel = client.deleteOrder(orderID);
        Assertions.assertEquals(HttpStatus.OK, responseDel.getStatusCode(), "Failed to retrieve pet order");
        ResponseEntity<OrderDto> responseCheck = client.getOrder(orderID);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseCheck.getStatusCode(), "Retrieved pet order, that was deleted");
    }
}
