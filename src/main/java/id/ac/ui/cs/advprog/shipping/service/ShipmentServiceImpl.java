package id.ac.ui.cs.advprog.shipping.service;

import id.ac.ui.cs.advprog.shipping.model.Shipment;
import id.ac.ui.cs.advprog.shipping.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShipmentServiceImpl implements ShipmentService {
    @Autowired
    private ShipmentRepository shipmentRepository;

    @Override
    public Shipment findById(String id) {
        return null;
    }
    @Override
    public Shipment createShipment(String id, String orderId, String status) {
        return null;
    }
    @Override
    public Shipment createShipment(String id, String orderId) {
        return null;
    }
    @Override
    public Shipment editShipment(String id, String orderId, String status) {
        return null;
    }
    @Override
    public void deleteShipment(String id) {
    }
}
