package backend.duka_kuu.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import backend.duka_kuu.domain.OrderLineItem;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class OrderDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String orderNumber;

//    @NotNull
//    private Long user;

    @NotNull
    private List<OrderLineItem> orderLineItems;

}
