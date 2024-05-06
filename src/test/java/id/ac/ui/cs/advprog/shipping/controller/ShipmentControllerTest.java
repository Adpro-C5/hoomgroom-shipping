package id.ac.ui.cs.advprog.shipping.controller;

import enums.ShippingStatus;
import id.ac.ui.cs.advprog.shipping.controller.ShipmentController;
import id.ac.ui.cs.advprog.shipping.model.Shipment;
import id.ac.ui.cs.advprog.shipping.service.ShipmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ShipmentControllerTest {

    @Mock
    private ShipmentService shipmentService;

    @InjectMocks
    private ShipmentController shipmentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateShipment() {
        String orderId = "123";
        Shipment shipment = new Shipment();
        when(shipmentService.saveShipment(any(Shipment.class))).thenReturn(shipment);

        ResponseEntity<Object> responseEntity = shipmentController.createShipment(orderId);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(shipment, responseEntity.getBody());
    }

    @Test
    void testGetShipment() {
        String id = "1";
        Shipment shipment = new Shipment();
        when(shipmentService.findById(id)).thenReturn(shipment);

        ResponseEntity<Object> responseEntity = shipmentController.getShipment(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(shipment, responseEntity.getBody());
    }

    @Test
    void testGetShipmentByOrderId() {
        String orderId = "123";
        Shipment shipment = new Shipment();
        when(shipmentService.findByOrderId(orderId)).thenReturn(shipment);

        ResponseEntity<Object> responseEntity = shipmentController.getShipmentByOrderId(orderId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(shipment, responseEntity.getBody());
    }

    @Test
    void testGetAllShipments() {
        List<Shipment> shipments = Collections.singletonList(new Shipment());
        when(shipmentService.getAllShipments()).thenReturn(shipments);

        ResponseEntity<Object> responseEntity = shipmentController.getAllShipments();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(shipments, responseEntity.getBody());
    }

    @Test
    void testUpdateShipmentStatus() {
        String id = "1";
        String status = ShippingStatus.DIKIRIM.toString();
        Shipment shipment = new Shipment();
        shipment.setStatus(status);
        when(shipmentService.findById(id)).thenReturn(shipment);

        ResponseEntity<Object> responseEntity = shipmentController.updateShipmentStatus(id, status);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Shipment status updated successfully", responseEntity.getBody());
    }

    @Test
    void testUpdateShipmentStatusByOrderId() {
        String orderId = "123";
        String status = ShippingStatus.DIKIRIM.toString();
        Shipment shipment = new Shipment();
        shipment.setStatus(status);
        when(shipmentService.findByOrderId(orderId)).thenReturn(shipment);

        ResponseEntity<Object> responseEntity = shipmentController.updateShipmentStatusByOrderId(orderId, status);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Shipment status updated successfully", responseEntity.getBody());
    }

    @Test
    void testCreateShipmentNulldOrderId() {
        String orderId = null;
        ResponseEntity<Object> responseEntity = shipmentController.createShipment(orderId);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void testCreateShipmentInvalidOrderId() {
        String orderId = "";
        ResponseEntity<Object> responseEntity = shipmentController.createShipment(orderId);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void testGetShipmentNotFound() {
        String id = "999";
        when(shipmentService.findById(id)).thenReturn(null);

        ResponseEntity<Object> responseEntity = shipmentController.getShipment(id);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Shipment not found", responseEntity.getBody());
    }

    @Test
    void testGetShipmentByOrderIdNotFound() {
        String orderId = "invalidOrderId";
        when(shipmentService.findByOrderId(orderId)).thenThrow(new RuntimeException("Internal Server Error"));

        ResponseEntity<Object> responseEntity = shipmentController.getShipmentByOrderId(orderId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Shipment not found", responseEntity.getBody());
    }

    @Test
    void testGetAllShipmentsNoShipments() {
        List<Shipment> shipments = Collections.emptyList();
        when(shipmentService.getAllShipments()).thenReturn(shipments);

        ResponseEntity<Object> responseEntity = shipmentController.getAllShipments();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("No Shipments yet", responseEntity.getBody());
    }

    @Test
    void testUpdateShipmentStatusInvalidStatus() {
        String id = "1";
        String invalidStatus = "InvalidStatus";
        when(shipmentService.findById(id)).thenReturn(new Shipment());

        ResponseEntity<Object> responseEntity = shipmentController.updateShipmentStatus(id, invalidStatus);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Invalid status", responseEntity.getBody());
    }

    @Test
    void testUpdateShipmentStatusNotFound() {
        String id = "999";
        String status = ShippingStatus.DIKIRIM.toString();
        when(shipmentService.findById(id)).thenReturn(null);

        ResponseEntity<Object> responseEntity = shipmentController.updateShipmentStatus(id, status);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Shipment not found", responseEntity.getBody());
    }

    @Test
    void testUpdateShipmentStatusByOrderIdNotFound() {
        String orderId = "999";
        String status = ShippingStatus.DIKIRIM.toString();
        when(shipmentService.findByOrderId(orderId)).thenReturn(null);

        ResponseEntity<Object> responseEntity = shipmentController.updateShipmentStatusByOrderId(orderId, status);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Shipment not found", responseEntity.getBody());
    }

    @Test
    void testUpdateOrderShipmentStatusInvalidStatus() {
        String id = "1";
        String invalidStatus = "InvalidStatus";
        when(shipmentService.findByOrderId(id)).thenReturn(new Shipment());

        ResponseEntity<Object> responseEntity = shipmentController.updateShipmentStatusByOrderId(id, invalidStatus);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Invalid status", responseEntity.getBody());
    }
}
