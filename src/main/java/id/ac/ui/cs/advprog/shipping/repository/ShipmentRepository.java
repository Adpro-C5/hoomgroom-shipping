package id.ac.ui.cs.advprog.shipping.repository;

import id.ac.ui.cs.advprog.shipping.model.Shipment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ShipmentRepository {
    private List<Shipment> shipmentData = new ArrayList<>();
    public Shipment findById(String id) {
        return null;
    }

    public Shipment findByOrderId(String orderId) {
        return null;
    }

    public Shipment saveShipment(Shipment shipment) {
        return null;
    }

    public Shipment deleteShipment(String id) {
        return null;
    }
}
