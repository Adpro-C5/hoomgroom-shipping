package id.ac.ui.cs.advprog.shipping.repository;

import enums.ShippingStatus;
import id.ac.ui.cs.advprog.shipping.factory.ShipmentFactory;
import id.ac.ui.cs.advprog.shipping.model.Shipment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShipmentRepositoryTest {
    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private ShipmentRepository shipmentRepository;
    private ArrayList<Shipment> shipments;

    @BeforeEach
    public void setUp() {
        ShipmentFactory shipmentFactory = new ShipmentFactory();
        Shipment shipment1 = shipmentFactory.create("1", "1", ShippingStatus.DIKIRIM.getValue());
        Shipment shipment2 = shipmentFactory.create("2", "2", ShippingStatus.DIPROSES.getValue());
        shipments = new ArrayList<>();
        shipments.add(shipment1); shipments.add(shipment2);
    }

    @Test
    void testSaveShipment() {
        Shipment shipment = shipments.getFirst();
        Shipment compare = shipmentRepository.saveShipment(shipment);
        assertEquals(shipment, compare);
        verify(entityManager, times(1)).persist(shipment);
    }

    @Test
    void testSaveShipmentNotFound() {
        Shipment shipment = shipments.getFirst();
        when(shipmentRepository.findById(shipment.getId())).thenReturn(shipment);

        Shipment compare = shipmentRepository.saveShipment(shipment);
        assertEquals(shipment, compare);
        verify(entityManager, never()).persist(shipment);
        verify(entityManager, times(1)).merge(shipment);
    }

    @Test
    void testDeleteShipment() {
        Shipment shipment = shipments.getFirst();
        when(entityManager.find(Shipment.class, shipment.getId())).thenReturn(shipment);
        Shipment compare = shipmentRepository.deleteShipment(shipment.getId());
        assertEquals(shipment, compare);
        verify(entityManager, times(1)).remove(shipment);
    }

    @Test
    void testDeleteShipmentNotFound() {
        String shipmentId = "non-existent-id";
        when(entityManager.find(Shipment.class, shipmentId)).thenReturn(null);

        Shipment compare = shipmentRepository.deleteShipment(shipmentId);
        assertNull(compare);
        verify(entityManager, never()).remove(any(Shipment.class));
    }


    @Test
    void testFindById() {
        Shipment shipment = shipments.getFirst();
        when(entityManager.find(Shipment.class, shipment.getId())).thenReturn(shipment);
        assertEquals(shipment, shipmentRepository.findById(shipment.getId()));
    }


    @Test
    void testFindByOrderId() {
        Shipment shipment = shipments.getFirst();
        TypedQuery<Shipment> query = mock(TypedQuery.class);
        when(entityManager.createQuery("SELECT s FROM Shipment s WHERE s.orderId = :orderId", Shipment.class)).thenReturn(query);
        when(query.setParameter("orderId", shipment.getOrderId())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(shipment);
        assertEquals(shipment, shipmentRepository.findByOrderId(shipment.getOrderId()));
    }

    @Test
    void testFindAll() {
        TypedQuery<Shipment> query = mock(TypedQuery.class);
        when(entityManager.createQuery("SELECT s FROM Shipment s", Shipment.class)).thenReturn(query);
        when(query.getResultList()).thenReturn(shipments);
        assertEquals(shipments, shipmentRepository.findAll());
    }
}
