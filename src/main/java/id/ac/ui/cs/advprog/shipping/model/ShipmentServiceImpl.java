package id.ac.ui.cs.advprog.shipping.model;

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
    public Shipment createShipment(String id, Order order, String status) {
        return null;
    }
    @Override
    public Shipment createShipment(String id, Order order) {
        return null;
    }
    @Override
    public Shipment editShipment(String id, Order order, String status) {
        return null;
    }
    @Override
    public void deleteShipment(String id) {
    }
}
