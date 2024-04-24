package id.ac.ui.cs.advprog.shipping.service;

import id.ac.ui.cs.advprog.shipping.model.Shipment;
import id.ac.ui.cs.advprog.shipping.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipmentServiceImpl implements ShipmentService {
    @Autowired
    private ShipmentRepository shipmentRepository;

    @Override
    public Shipment findById(String id) {
        return null;
    }

    @Override
    public Shipment findByOrderId(String orderId) {
        return null;
    }

    @Override
    public Shipment saveShipment(Shipment shipment) {
        return null;
    }

    @Override
    public Shipment deleteShipment(String id) {
        return null;
    }

    @Override
    public List<Shipment> getAllShipments() {
        return null;
    }

    @Override
    public Shipment setShipmentStatus(String id, String status) {
        return null;
    }
}
