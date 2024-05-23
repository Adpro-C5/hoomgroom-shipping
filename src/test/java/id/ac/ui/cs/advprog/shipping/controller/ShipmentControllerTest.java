package id.ac.ui.cs.advprog.shipping.controller;

import enums.ShippingStatus;
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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ShipmentControllerTest {

    @Mock
    private ShipmentService shipmentService;

    @InjectMocks
    private ShipmentController shipmentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateShipment() throws ExecutionException, InterruptedException {
        String orderId = "123";
        Shipment shipment = new Shipment();
        CompletableFuture<Shipment> futureShipment = CompletableFuture.completedFuture(shipment);
        when(shipmentService.saveShipment(any(Shipment.class))).thenReturn(futureShipment);

        ResponseEntity<Object> responseEntity = shipmentController.createShipment(orderId);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(shipment, responseEntity.getBody());
    }

    @Test
    void testGetShipment() throws ExecutionException, InterruptedException {
        String id = "1";
        Shipment shipment = new Shipment();
        CompletableFuture<Shipment> futureShipment = CompletableFuture.completedFuture(shipment);
        when(shipmentService.findById(id)).thenReturn(futureShipment);

        ResponseEntity<Object> responseEntity = shipmentController.getShipment(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(shipment, responseEntity.getBody());
    }

    @Test
    void testGetShipmentByOrderId() throws ExecutionException, InterruptedException {
        String orderId = "123";
        Shipment shipment = new Shipment();
        CompletableFuture<Shipment> futureShipment = CompletableFuture.completedFuture(shipment);
        when(shipmentService.findByOrderId(orderId)).thenReturn(futureShipment);

        ResponseEntity<Object> responseEntity = shipmentController.getShipmentByOrderId(orderId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(shipment, responseEntity.getBody());
    }

    @Test
    void testGetAllShipments() throws ExecutionException, InterruptedException {
        List<Shipment> shipments = Collections.singletonList(new Shipment());
        CompletableFuture<List<Shipment>> futureShipments = CompletableFuture.completedFuture(shipments);
        when(shipmentService.getAllShipments()).thenReturn(futureShipments);

        ResponseEntity<Object> responseEntity = shipmentController.getAllShipments();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(shipments, responseEntity.getBody());
    }

    @Test
    void testUpdateShipmentStatus() throws ExecutionException, InterruptedException {
        String id = "1";
        String status = ShippingStatus.DIKIRIM.toString();
        Shipment shipment = new Shipment();
        shipment.setStatus(status);
        CompletableFuture<Shipment> futureShipment = CompletableFuture.completedFuture(shipment);
        when(shipmentService.findById(id)).thenReturn(futureShipment);

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
        CompletableFuture<Shipment> futureShipment = CompletableFuture.completedFuture(shipment);
        when(shipmentService.findByOrderId(orderId)).thenReturn(futureShipment);

        ResponseEntity<Object> responseEntity = shipmentController.updateShipmentStatusByOrderId(orderId, status);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Shipment status updated successfully", responseEntity.getBody());
    }

    @Test
    void testCreateShipmentNullOrderId() throws ExecutionException, InterruptedException {
        ResponseEntity<Object> responseEntity = shipmentController.createShipment(null);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void testCreateShipmentInvalidOrderId() throws ExecutionException, InterruptedException {
        String orderId = "";
        ResponseEntity<Object> responseEntity = shipmentController.createShipment(orderId);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void testGetShipmentNotFound() throws ExecutionException, InterruptedException {
        String id = "999";
        CompletableFuture<Shipment> futureShipment = CompletableFuture.completedFuture(null);
        when(shipmentService.findById(id)).thenReturn(futureShipment);

        ResponseEntity<Object> responseEntity = shipmentController.getShipment(id);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Shipment not found", responseEntity.getBody());
    }

    @Test
    void testGetShipmentByOrderIdNotFound() throws ExecutionException, InterruptedException {
        String orderId = "invalidOrderId";
        CompletableFuture<Shipment> futureShipment = CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("Internal Server Error");
        });
        when(shipmentService.findByOrderId(orderId)).thenReturn(futureShipment);

        ResponseEntity<Object> responseEntity = shipmentController.getShipmentByOrderId(orderId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Shipment not found", responseEntity.getBody());
    }

    @Test
    void testGetAllShipmentsNoShipments() throws ExecutionException, InterruptedException {
        List<Shipment> shipments = Collections.emptyList();
        CompletableFuture<List<Shipment>> futureShipments = CompletableFuture.completedFuture(shipments);
        when(shipmentService.getAllShipments()).thenReturn(futureShipments);

        ResponseEntity<Object> responseEntity = shipmentController.getAllShipments();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("No Shipments yet", responseEntity.getBody());
    }

    @Test
    void testUpdateShipmentStatusInvalidStatus() throws ExecutionException, InterruptedException {
        String id = "1";
        String invalidStatus = "InvalidStatus";
        CompletableFuture<Shipment> futureShipment = CompletableFuture.completedFuture(new Shipment());
        when(shipmentService.findById(id)).thenReturn(futureShipment);

        ResponseEntity<Object> responseEntity = shipmentController.updateShipmentStatus(id, invalidStatus);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Invalid status", responseEntity.getBody());
    }

    @Test
    void testUpdateShipmentStatusNotFound() throws ExecutionException, InterruptedException {
        String id = "999";
        String status = ShippingStatus.DIKIRIM.toString();
        CompletableFuture<Shipment> futureShipment = CompletableFuture.completedFuture(null);
        when(shipmentService.findById(id)).thenReturn(futureShipment);

        ResponseEntity<Object> responseEntity = shipmentController.updateShipmentStatus(id, status);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Shipment not found", responseEntity.getBody());
    }

    @Test
    void testUpdateShipmentStatusByOrderIdNotFound() {
        String orderId = "999";
        String status = ShippingStatus.DIKIRIM.toString();
        CompletableFuture<Shipment> futureShipment = CompletableFuture.completedFuture(null);
        when(shipmentService.findByOrderId(orderId)).thenReturn(futureShipment);

        ResponseEntity<Object> responseEntity = shipmentController.updateShipmentStatusByOrderId(orderId, status);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Shipment not found", responseEntity.getBody());
    }

    @Test
    void testUpdateOrderShipmentStatusInvalidStatus() {
        String id = "1";
        String invalidStatus = "InvalidStatus";
        CompletableFuture<Shipment> futureShipment = CompletableFuture.completedFuture(new Shipment());
        when(shipmentService.findByOrderId(id)).thenReturn(futureShipment);

        ResponseEntity<Object> responseEntity = shipmentController.updateShipmentStatusByOrderId(id, invalidStatus);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Invalid status", responseEntity.getBody());
    }
}
