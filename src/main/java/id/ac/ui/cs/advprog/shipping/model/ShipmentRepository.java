package id.ac.ui.cs.advprog.shipping.model;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ShipmentRepository {
    private List<Shipment> shipmentData;
    public Shipment findById(String id) {
        return null;
    }

    public Shipment createShipment(String id, Order order, String status) {
        return null;
    }

    public Shipment createShipment(String id, Order order) {
        return null;
    }

    public Shipment editShipment(String id, Order order, String status) {
        return null;
    }

    public void deleteShipment(String id) {
    }
}
