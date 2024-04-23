package id.ac.ui.cs.advprog.shipping.factory;

import id.ac.ui.cs.advprog.shipping.repository.ShipmentRepository;

public class ShipmentRepositoryFactory implements Factory<ShipmentRepository>{
    @Override
    public ShipmentRepository create() {
        return null;
    }
    @Override
    public ShipmentRepository create(String id, String orderId) {
        return null;
    }
    @Override
    public ShipmentRepository create(String id, String orderId, String status) {
        return null;
    }
}
