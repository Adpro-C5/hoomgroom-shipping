package id.ac.ui.cs.advprog.shipping.factory;

import id.ac.ui.cs.advprog.shipping.repository.ShipmentRepository;

public class ShipmentRepositoryFactory implements Factory<ShipmentRepository>{
    @Override
    public ShipmentRepository create() {
        return new ShipmentRepository();
    }
    @Override
    public ShipmentRepository create(String orderId) {
        throw new UnsupportedOperationException();
    }
    @Override
    public ShipmentRepository create(String id, String orderId) {
        throw new UnsupportedOperationException();
    }
    @Override
    public ShipmentRepository create(String id, String orderId, String status) {
        throw new UnsupportedOperationException();
    }
}
