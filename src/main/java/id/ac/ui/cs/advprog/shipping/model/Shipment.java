package id.ac.ui.cs.advprog.shipping.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
public class Shipment {
    String id;
    Order order;
    String status;

    public Shipment(String id, Order order) {

    }

    public Shipment(String id, Order order, String status) {

    }


}


