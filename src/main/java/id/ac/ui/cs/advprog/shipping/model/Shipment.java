package id.ac.ui.cs.advprog.shipping.model;

import enums.ShippingStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;


@Entity
@Builder
@Getter
@Table(name = "shipment")
public class Shipment {
    @Id
    @Column(name = "id",nullable = false)
    String id;
    @Column(name = "orderId",nullable = false)
    String orderId;
    @Column(name = "status",nullable = false)
    String status;

    public Shipment() {
        this.id = UUID.randomUUID().toString();
        this.orderId = null;
        this.status = ShippingStatus.MENUNGGU_VERIFIKASI.toString();
    }

    public Shipment(String orderId) {
        this.id = UUID.randomUUID().toString();
        this.orderId = orderId;
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


