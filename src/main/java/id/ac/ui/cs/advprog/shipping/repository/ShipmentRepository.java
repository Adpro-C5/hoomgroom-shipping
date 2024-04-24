package id.ac.ui.cs.advprog.shipping.repository;

import id.ac.ui.cs.advprog.shipping.model.Shipment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ShipmentRepository {
    private List<Shipment> shipmentData = new ArrayList<>();
    public Shipment findById(String id) {
        for (Shipment shipment : shipmentData) {
            if (shipment.getId().equals(id)) {
                return shipment;
            }
        }
        return null;
    }

    public Shipment findByOrderId(String orderId) {
        for (Shipment shipment : shipmentData) {
            if (shipment.getOrderId().equals(orderId)) {
                return shipment;
            }
        }
        return null;
    }

    public Shipment saveShipment(Shipment shipment) {
        int index = shipmentData.indexOf(shipment);
        if (index == -1) {
            shipmentData.add(shipment);
            return shipment;
        } else {
            shipmentData.set(index, shipment);
            return null;
        }
    }

    public Shipment deleteShipment(String id) {
        Shipment shipment = findById(id);
        if (shipment != null) {
            shipmentData.remove(shipment);
            return shipment;
        }
        return null;
    }

    public List<Shipment> findAll() {
        return shipmentData;
    }
}
