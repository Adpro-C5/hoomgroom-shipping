package id.ac.ui.cs.advprog.shipping.repository;

import enums.ShippingStatus;
import id.ac.ui.cs.advprog.shipping.factory.ShipmentFactory;
import id.ac.ui.cs.advprog.shipping.factory.ShipmentRepositoryFactory;
import id.ac.ui.cs.advprog.shipping.model.Shipment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ShipmentRepositoryTest {
    ShipmentRepository shipmentRepository;
    ShipmentRepositoryFactory shipmentRepositoryFactory;
    List<Shipment> shipments;
    ShipmentFactory shipmentFactory;

    @BeforeEach
    public void setUp() {
        shipmentRepositoryFactory = new ShipmentRepositoryFactory();
        shipmentRepository = shipmentRepositoryFactory.create();
        shipmentFactory = new ShipmentFactory();
        Shipment shipment1 = shipmentFactory.create("1", "1", ShippingStatus.DIKIRIM.getValue());
        Shipment shipment2 = shipmentFactory.create("2", "2", ShippingStatus.DIPROSES.getValue());
        shipments = new ArrayList<>();
        shipments.add(shipment1); shipments.add(shipment2);
    }

    @Test
    void testSaveShipment() {
        Shipment shipment = shipments.get(0);
        shipmentRepository.saveShipment(shipment);
        Shipment compare = shipmentRepository.findById(shipment.getId());
        assertEquals(shipment, compare);
        assertEquals(shipment.getId(), compare.getId());
        assertEquals(shipment.getOrderId(), compare.getOrderId());
        assertEquals(shipment.getStatus(), compare.getStatus());
    }
    @Test
    void testSaveShipmentWithExistingId() {
        Shipment shipment = shipments.get(0);
        shipmentRepository.saveShipment(shipment);
        Shipment compare = shipmentRepository.findById(shipment.getId());
        assertEquals(shipment, compare);
        assertEquals(shipment.getId(), compare.getId());
        assertEquals(shipment.getOrderId(), compare.getOrderId());
        assertEquals(shipment.getStatus(), compare.getStatus());

        assertNull(shipmentRepository.saveShipment(shipment));
    }

    @Test
    void testDeleteShipment() {
        Shipment shipment = shipments.get(0);
        shipmentRepository.saveShipment(shipment);
        Shipment compare = shipmentRepository.findById(shipment.getId());
        assertEquals(shipment, compare);
        assertEquals(shipment.getId(), compare.getId());
        assertEquals(shipment.getOrderId(), compare.getOrderId());
        assertEquals(shipment.getStatus(), compare.getStatus());

        assertEquals(shipment,shipmentRepository.deleteShipment(shipment.getId()));
        assertNull(shipmentRepository.findById(shipment.getId()));
    }

    @Test
    void testDeleteShipmentWithNonExistingId() {
        Shipment shipment = shipments.get(0);
        shipmentRepository.saveShipment(shipment);
        Shipment compare = shipmentRepository.findById(shipment.getId());
        assertEquals(shipment, compare);
        assertEquals(shipment.getId(), compare.getId());
        assertEquals(shipment.getOrderId(), compare.getOrderId());
        assertEquals(shipment.getStatus(), compare.getStatus());

        assertNull(shipmentRepository.deleteShipment("3"));
    }

    @Test
    void testFindById() {
        Shipment shipment = shipments.get(0);
        shipmentRepository.saveShipment(shipment);
        Shipment compare = shipmentRepository.findById(shipment.getId());
        assertEquals(shipment, compare);
        assertEquals(shipment.getId(), compare.getId());
        assertEquals(shipment.getOrderId(), compare.getOrderId());
        assertEquals(shipment.getStatus(), compare.getStatus());
    }

    @Test
    void testFindByIdWithNonExistingId() {
        Shipment shipment = shipments.get(0);
        shipmentRepository.saveShipment(shipment);
        Shipment compare = shipmentRepository.findById(shipment.getId());
        assertEquals(shipment, compare);
        assertEquals(shipment.getId(), compare.getId());
        assertEquals(shipment.getOrderId(), compare.getOrderId());
        assertEquals(shipment.getStatus(), compare.getStatus());

        assertNull(shipmentRepository.findById("3"));
    }

    @Test
    void testFindByOrderId() {
        Shipment shipment = shipments.get(0);
        shipmentRepository.saveShipment(shipment);
        Shipment compare = shipmentRepository.findByOrderId(shipment.getOrderId());
        assertEquals(shipment, compare);
        assertEquals(shipment.getId(), compare.getId());
        assertEquals(shipment.getOrderId(), compare.getOrderId());
        assertEquals(shipment.getStatus(), compare.getStatus());
    }

    @Test
    void testFindByOrderIdWithNonExistingOrderId() {
        Shipment shipment = shipments.get(0);
        shipmentRepository.saveShipment(shipment);
        Shipment compare = shipmentRepository.findByOrderId(shipment.getOrderId());
        assertEquals(shipment, compare);
        assertEquals(shipment.getId(), compare.getId());
        assertEquals(shipment.getOrderId(), compare.getOrderId());
        assertEquals(shipment.getStatus(), compare.getStatus());

        assertNull(shipmentRepository.findByOrderId("3"));
    }

    @Test
    void testFindAll(){
        Shipment shipment1 = shipments.get(0);
        Shipment shipment2 = shipments.get(1);
        shipmentRepository.saveShipment(shipment1);
        shipmentRepository.saveShipment(shipment2);
        List<Shipment> compare = shipmentRepository.findAll();
        assertEquals(shipment1, compare.get(0));
        assertEquals(shipment2, compare.get(1));
        assertEquals(compare.size(), 2);
    }
}
