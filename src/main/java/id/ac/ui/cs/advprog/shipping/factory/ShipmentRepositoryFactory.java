package id.ac.ui.cs.advprog.shipping.factory;

import id.ac.ui.cs.advprog.shipping.repository.ShipmentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ShipmentRepositoryFactory implements Factory<ShipmentRepository>{
    @Override
    public ShipmentRepository create() {
        ShipmentRepository shipmentRepository = null;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("shipment");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManagerFactory.close();
        shipmentRepository = new ShipmentRepository(entityManager);
        entityManager.close();
        return shipmentRepository;
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
