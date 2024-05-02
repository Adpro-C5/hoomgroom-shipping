package id.ac.ui.cs.advprog.shipping.service;

import id.ac.ui.cs.advprog.shipping.model.Shipment;
import id.ac.ui.cs.advprog.shipping.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ShipmentServiceImpl implements ShipmentService {
    @Autowired
    private ShipmentRepository shipmentRepository;

    @Override
    public Shipment findById(String id) {
        return shipmentRepository.findById(id);
    }

    @Override
    public Shipment findByOrderId(String orderId) {
        return shipmentRepository.findByOrderId(orderId);
    }

    @Override
    public Shipment saveShipment(Shipment shipment) {
        if (shipmentRepository.findById(shipment.getId()) == null) {
            return shipmentRepository.saveShipment(shipment);
        } else {
            return null;
        }
    }

    @Override
    public Shipment deleteShipment(String id) {
        return shipmentRepository.deleteShipment(id);
    }

    @Override
    public List<Shipment> getAllShipments() {
        return shipmentRepository.findAll();
    }

    @Override
    public Shipment setShipmentStatus(String id, String status) {
        Shipment shipmentToChange = shipmentRepository.findById(id);
        if (shipmentToChange != null) {
            shipmentToChange.setStatus(status);
            return shipmentRepository.saveShipment(shipmentToChange);
        } else {
            throw new NoSuchElementException();
        }
    }
}
