package id.ac.ui.cs.advprog.shipping.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
public class Shipment {
    String id;
    String orderId;
    String status;

    public Shipment(String id, String orderId) {

    }

    public Shipment(String id, String orderId, String status) {

    }


}


