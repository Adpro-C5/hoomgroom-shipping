package id.ac.ui.cs.advprog.shipping.factory;

import id.ac.ui.cs.advprog.shipping.model.Shipment;

public class ShipmentFactory implements Factory<Shipment>{
    @Override
    public Shipment create() {
        return new Shipment();
    }
    @Override
    public Shipment create(String orderId) {
        return new Shipment(orderId);
    }
    @Override
    public Shipment create(String id, String orderId) {
        return new Shipment(id, orderId);
    }
    @Override
    public Shipment create(String id, String orderId, String status) {
        return new Shipment(id, orderId, status);
    }
}
