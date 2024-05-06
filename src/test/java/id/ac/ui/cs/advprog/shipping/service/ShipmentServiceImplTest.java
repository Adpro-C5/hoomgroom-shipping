package id.ac.ui.cs.advprog.shipping.service;

import enums.ShippingStatus;
import id.ac.ui.cs.advprog.shipping.factory.ShipmentFactory;
import id.ac.ui.cs.advprog.shipping.factory.ShipmentRepositoryFactory;
import id.ac.ui.cs.advprog.shipping.model.Shipment;
import id.ac.ui.cs.advprog.shipping.repository.ShipmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShipmentServiceImplTest {
    @InjectMocks
    ShipmentServiceImpl shipmentService;
    @Mock
    ShipmentRepository shipmentRepository;
    ShipmentFactory shipmentFactory;
    List<Shipment> shipments;

    @BeforeEach
    public void setUp() {
        shipmentFactory = new ShipmentFactory();
        shipments = new ArrayList<>();
        Shipment shipment1 = shipmentFactory.create("1", "1", "DIKIRIM");
        Shipment shipment2 = shipmentFactory.create("2", "2", "DIPROSES");
        shipments.add(shipment1); shipments.add(shipment2);
    }

    @Test
    void testSaveShipment() {
        Shipment shipment = shipments.get(0);
        doReturn(shipment).when(shipmentRepository).saveShipment(shipment);
        Shipment result = shipmentService.saveShipment(shipment);
        verify(shipmentRepository,times(1)).saveShipment(shipment);
        assertEquals(shipment, result);
    }

    @Test
    void testSaveShipmentWithExistingId() {
        Shipment shipment = shipments.get(0);
        doReturn(shipment).when(shipmentRepository).findById(shipment.getId());
        Shipment result = shipmentService.saveShipment(shipment);
        verify(shipmentRepository, times(1)).findById(shipment.getId());
        verify(shipmentRepository, times(0)).saveShipment(shipment);
        assertNull(result);
    }

    @Test
    void testSetShipmentStatus() {
        Shipment shipment = shipments.get(0);
        doReturn(shipment).when(shipmentRepository).findById(shipment.getId());
        doReturn(shipment).when(shipmentRepository).saveShipment(shipment);
        Shipment result = shipmentService.setShipmentStatus(shipment.getId(), ShippingStatus.DIKIRIM.toString());
        verify(shipmentRepository,times(1)).findById(shipment.getId());
        verify(shipmentRepository,times(1)).saveShipment(shipment);
        assertEquals(shipment, result);
    }

    @Test
    void testSetShipmentStatusWithNonExistingShipment() {
        Shipment shipment = shipments.get(0);
        doReturn(null).when(shipmentRepository).findById(shipment.getId());
        NoSuchElementException noSuchElementException = assertThrows(NoSuchElementException.class, () -> {
            shipmentService.setShipmentStatus(shipment.getId(), ShippingStatus.DIKIRIM.toString());
        });
        verify(shipmentRepository,times(1)).findById(shipment.getId());
        verify(shipmentRepository,times(0)).saveShipment(shipment);
    }


    @Test
    void testDeleteShipment() {
        Shipment shipment = shipments.get(0);
        doReturn(shipment).when(shipmentRepository).deleteShipment(shipment.getId());
        Shipment result = shipmentService.deleteShipment(shipment.getId());
        verify(shipmentRepository,times(1)).deleteShipment(shipment.getId());
        assertEquals(shipment, result);
    }

    @Test
    void testDeleteNonExistingShipment() {
        Shipment shipment = shipments.get(0);
        doReturn(null).when(shipmentRepository).deleteShipment(shipment.getId());
        Shipment result = shipmentService.deleteShipment(shipment.getId());
        verify(shipmentRepository,times(1)).deleteShipment(shipment.getId());
        assertNull(result);
    }

    @Test
    void testFindByIdIfFound() {
        Shipment shipment = shipments.get(0);
        doReturn(shipment).when(shipmentRepository).findById(shipment.getId());
        Shipment result = shipmentService.findById(shipment.getId());
        verify(shipmentRepository, times(1)).findById(shipment.getId());
        assertEquals(shipment, result);
    }

    @Test
    void testFindByIdIfNotFound() {
        Shipment shipment = shipments.get(0);
        doReturn(null).when(shipmentRepository).findById(shipment.getId());
        Shipment result = shipmentService.findById(shipment.getId());
        verify(shipmentRepository, times(1)).findById(shipment.getId());
        assertNull(result);
    }

    @Test
    void testFindAll() {
        doReturn(shipments).when(shipmentRepository).findAll();
        List<Shipment> result = shipmentService.getAllShipments();
        verify(shipmentRepository, times(1)).findAll();
        assertEquals(shipments, result);
    }

    @Test
    void testFindByOrderIdIfFound() {
        Shipment shipment = shipments.get(0);
        doReturn(shipment).when(shipmentRepository).findByOrderId(shipment.getOrderId());
        Shipment result = shipmentService.findByOrderId(shipment.getOrderId());
        verify(shipmentRepository, times(1)).findByOrderId(shipment.getOrderId());
        assertEquals(shipment, result);
    }
}
