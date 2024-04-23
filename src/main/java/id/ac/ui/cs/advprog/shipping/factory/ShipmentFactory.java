package id.ac.ui.cs.advprog.shipping.factory;

import id.ac.ui.cs.advprog.shipping.model.Shipment;

public class ShipmentFactory implements Factory<Shipment>{
    @Override
    public Shipment create() {
        return null;
    }
    @Override
    public Shipment create(String id, String orderId) {
        return null;
    }
    @Override
    public Shipment create(String id, String orderId, String status) {
        return null;
    }
}
