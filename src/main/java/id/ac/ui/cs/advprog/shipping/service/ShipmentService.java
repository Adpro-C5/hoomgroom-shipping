package id.ac.ui.cs.advprog.shipping.service;

import id.ac.ui.cs.advprog.shipping.model.Shipment;

public interface ShipmentService {
    public Shipment findById(String id);
    public Shipment createShipment(String id, String orderId, String status);
    public Shipment createShipment(String id, String orderId);
    public Shipment editShipment(String id, String orderId, String status);
    public void deleteShipment(String id);
}
