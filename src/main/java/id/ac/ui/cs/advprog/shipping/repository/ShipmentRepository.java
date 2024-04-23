package id.ac.ui.cs.advprog.shipping.repository;

import id.ac.ui.cs.advprog.shipping.model.Shipment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ShipmentRepository {
    private List<Shipment> shipmentData;
    public Shipment findById(String id) {
        return null;
    }

    public Shipment createShipment(String id, String orderId, String status) {
        return null;
    }

    public Shipment createShipment(String id, String orderId) {
        return null;
    }

    public Shipment editShipment(String id, String orderId, String status) {
        return null;
    }

    public void deleteShipment(String id) {
    }
}
