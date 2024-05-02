package id.ac.ui.cs.advprog.shipping.service;

import id.ac.ui.cs.advprog.shipping.model.Shipment;

import java.util.List;

public interface ShipmentService {
    public Shipment findById(String id);
    public Shipment findByOrderId(String orderId);
    public Shipment saveShipment(Shipment shipment);
    public Shipment deleteShipment(String id);
    public List<Shipment> getAllShipments();
    public Shipment setShipmentStatus(String id, String status);
}
