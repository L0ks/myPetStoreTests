package my.petstore.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FaultyOrderDto {
    @JsonProperty("id")
    private Object id;
    @JsonProperty("petId")
    private Object petId;
    @JsonProperty("quantity")
    private Object quantity;
    @JsonProperty("shipDate")
    private Object shipDate;
    @JsonProperty("status")
    private Object status;
    @JsonProperty("complete")
    private Object complete;
}
