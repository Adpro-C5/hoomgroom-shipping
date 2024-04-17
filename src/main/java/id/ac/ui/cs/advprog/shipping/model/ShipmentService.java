package id.ac.ui.cs.advprog.shipping.model;

public interface ShipmentService {
    public Shipment findById(String id);
    public Shipment createShipment(String id, Order order, String status);
    public Shipment createShipment(String id, Order order);
    public Shipment editShipment(String id, Order order, String status);
    public void deleteShipment(String id);
}