package my.petstore.endpoints;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum StoreEndpoints {
    INVENTORY("store/inventory"),
    PLACEORDER("store/order"),
    CHECKORDER("store/order/{param}"),
    DELETEORDER("store/order/{param}");

    @Getter
    private final String endpoint;
}
