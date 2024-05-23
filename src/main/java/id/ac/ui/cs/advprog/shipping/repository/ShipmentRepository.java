package id.ac.ui.cs.advprog.shipping.repository;

import id.ac.ui.cs.advprog.shipping.model.Shipment;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public class ShipmentRepository {
    @Autowired
    EntityManager entityManager;
    @Transactional
    public Shipment findById(String id) {
        return entityManager.find(Shipment.class, id);
    }
    @Transactional
    public Shipment findByOrderId(String orderId) {
        return entityManager.createQuery("SELECT s FROM Shipment s WHERE s.orderId = :orderId", Shipment.class)
                .setParameter("orderId", orderId)
                .getSingleResult();
    }
    @Transactional
    public Shipment saveShipment(Shipment shipment) {
        if (findById(shipment.getId()) == null) {
            entityManager.persist(shipment);
            return shipment;
        } else {
            return entityManager.createQuery("UPDATE Shipment s SET status = :status WHERE s.id = :id", Shipment.class)
                    .setParameter("status", shipment.getStatus())
                    .setParameter("id", shipment.getId())
                    .getSingleResult();
        }
    }
    @Transactional
    public Shipment deleteShipment(String id) {
        if (entityManager.find(Shipment.class, id) != null) {
            Shipment shipment = entityManager.find(Shipment.class, id);
            entityManager.remove(shipment);
            return shipment;
        } else {
            return null;
        }
    }
    @Transactional
    public List<Shipment> findAll() {
        return entityManager.createQuery("SELECT s FROM Shipment s", Shipment.class)
                .getResultList();
    }
}
