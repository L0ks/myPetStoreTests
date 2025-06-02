package my.petstore.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum StoreEndpoints {
    INTENTORY("store/inventory"),
    PLACEORDER("store/order"),
    CHECKORDER("store/order/{param}"),
    DELETEORDER("store/order/{param}");

    @Getter
    private final String endpoint;
}
