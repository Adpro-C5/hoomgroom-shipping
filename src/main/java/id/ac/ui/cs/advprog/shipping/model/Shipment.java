package id.ac.ui.cs.advprog.shipping.model;

import enums.ShippingStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Builder
@Getter
public class Shipment {
    String id;
    String orderId;
    String status;

    public Shipment() {
        this.id = UUID.randomUUID().toString();
        this.orderId = null;
        this.status = ShippingStatus.MENUNGGU_VERIFIKASI.toString();
    }

    public Shipment(String id, String orderId) {
        this.id = id;
        this.orderId = orderId;
        this.status = ShippingStatus.MENUNGGU_VERIFIKASI.toString();
    }

    public Shipment(String id, String orderId, String status) {
        this.id = id;
        this.orderId = orderId;
        setStatus(status);
    }

    public void setStatus(String status) {
        if(ShippingStatus.contains(status)){
            this.status = status;
        } else {
            throw new IllegalArgumentException("Invalid status");
        }
    }
}


