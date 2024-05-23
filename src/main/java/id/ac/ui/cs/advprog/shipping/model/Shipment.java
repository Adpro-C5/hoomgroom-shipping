package id.ac.ui.cs.advprog.shipping.model;

import enums.ShippingStatus;
import enums.TransportationType;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;
import com.aventrix.jnanoid.jnanoid.NanoIdUtils;

@Entity
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
    @Column(name = "transportationType",nullable = true)
    String transportationType;
    @Column(name = "noResi",nullable = true)
    String noResi;

    public Shipment() {
        this.id = UUID.randomUUID().toString();
        this.orderId = null;
        this.status = ShippingStatus.MENUNGGU_VERIFIKASI.toString();
        this.transportationType = null;
        this.noResi = null;
    }

    public Shipment(String orderId) {
        this.id = UUID.randomUUID().toString();
        this.orderId = orderId;
        this.status = ShippingStatus.MENUNGGU_VERIFIKASI.toString();
        this.transportationType = null;
        this.noResi = null;
    }

    public Shipment(String id, String orderId) {
        this.id = id;
        this.orderId = orderId;
        this.status = ShippingStatus.MENUNGGU_VERIFIKASI.toString();
        this.transportationType = null;
        this.noResi = null;
    }

    public Shipment(String id, String orderId, String status) {
        this.id = id;
        this.orderId = orderId;
        setStatus(status);
        this.transportationType = null;
        this.noResi = null;
    }

    public void setStatus(String status) {
        if(ShippingStatus.contains(status)){
            this.status = status;
            if(status.equals(ShippingStatus.DIKIRIM.toString())){
                String nanoId = NanoIdUtils.randomNanoId();
                this.noResi = "HG-" + nanoId;
            }
        } else {
            throw new IllegalArgumentException("Invalid status");
        }
    }

    public void setTransportationType(String transportationType) {
        if(TransportationType.contains(transportationType)){
            this.transportationType = transportationType;
        } else {
            throw new IllegalArgumentException("Invalid transportation type");
        }
    }
}


