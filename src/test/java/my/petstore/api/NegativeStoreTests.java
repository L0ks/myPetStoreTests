package my.petstore.api;

import lombok.SneakyThrows;
import my.petstore.api.base.StoreTestBase;
import my.petstore.endpoints.StoreEndpoints;
import my.petstore.request.RequestExecutor;
import my.petstore.dto.FaultyOrderDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.stream.Stream;

public class NegativeStoreTests extends StoreTestBase {
    RequestExecutor requestExecutor = new RequestExecutor();

    static Stream<Arguments> invalidDTOArgumentStream() {
        return Stream.of(
                Arguments.of(new FaultyOrderDto(0, randomPositiveInt(), randomPositiveInt(), date, "placed", randomBoolean())),
                Arguments.of(new FaultyOrderDto(-11, randomPositiveInt(), randomPositiveInt(), date, "placed", randomBoolean())),
                Arguments.of(new FaultyOrderDto(11, randomPositiveInt(), randomPositiveInt(), date, "placed", randomBoolean())),
                Arguments.of(new FaultyOrderDto("My Order", randomPositiveInt(), randomPositiveInt(), date, "placed", randomBoolean())),
                Arguments.of(new FaultyOrderDto(randomSmallInt(), -200, randomPositiveInt(), date, "placed", randomBoolean())),
                Arguments.of(new FaultyOrderDto(randomSmallInt(), "Goldfish", randomPositiveInt(), date, "placed", randomBoolean())),
                Arguments.of(new FaultyOrderDto(randomSmallInt(), randomPositiveInt(), -30, date, "placed", randomBoolean())),
                Arguments.of(new FaultyOrderDto(randomSmallInt(), randomPositiveInt(), "lots of 'em", date, "placed", randomBoolean())),
                Arguments.of(new FaultyOrderDto(randomSmallInt(), randomPositiveInt(), randomPositiveInt(), "Tomorrow", "placed", randomBoolean())),
                Arguments.of(new FaultyOrderDto(randomSmallInt(), randomPositiveInt(), randomPositiveInt(), 1987, "placed", randomBoolean())),
                Arguments.of(new FaultyOrderDto(randomSmallInt(), randomPositiveInt(), randomPositiveInt(), date, 2+2, randomBoolean())),
                Arguments.of(new FaultyOrderDto(randomSmallInt(), randomPositiveInt(), randomPositiveInt(), date, false, randomBoolean())),
                Arguments.of(new FaultyOrderDto(randomSmallInt(), randomPositiveInt(), randomPositiveInt(), date, "placed", "maybe?")),
                Arguments.of(new FaultyOrderDto())
        );
    }

    static Stream<Arguments> invalidIDArgumentStream() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of(false),
                Arguments.of(-1),
                Arguments.of(99),
                Arguments.of(0),
                Arguments.of(1.23456789)
        );
    }

    @SneakyThrows
    @Tag("store")
    @Tag("negative")
    @Tag("regression")
    @ParameterizedTest
    @MethodSource("invalidDTOArgumentStream")
    public void invalidOrderTest(FaultyOrderDto invalidDto) {
        ResponseEntity<FaultyOrderDto> response = requestExecutor.executeRequest(StoreEndpoints.PLACEORDER.getEndpoint(), new ParameterizedTypeReference<FaultyOrderDto>() {}, invalidDto);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Failed to place pet order");
    }

    @SneakyThrows
    @Tag("store")
    @Tag("negative")
    @Tag("regression")
    @ParameterizedTest
    @MethodSource("invalidIDArgumentStream")
    public void invalidOrderIDCheckTest(Object invalidID) {
        ResponseEntity<FaultyOrderDto> response = requestExecutor.executeRequest(HttpMethod.GET, StoreEndpoints.CHECKORDER.getEndpoint(), invalidID, new ParameterizedTypeReference<FaultyOrderDto>() {});
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Failed to place pet order");
    }

    @SneakyThrows
    @Tag("store")
    @Tag("negative")
    @Tag("regression")
    @ParameterizedTest
    @MethodSource("invalidIDArgumentStream")
    public void invalidOrderIDDeleteTest(Object invalidID) {
        ResponseEntity<Void> response = requestExecutor.executeRequest(HttpMethod.DELETE, StoreEndpoints.DELETEORDER.getEndpoint(), invalidID, new ParameterizedTypeReference<Void>() {});
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Failed to place pet order");
    }
}
