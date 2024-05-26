package id.ac.ui.cs.advprog.shipping.controller;

import enums.ShippingStatus;
import enums.TransportationType;
import id.ac.ui.cs.advprog.shipping.helper.AuthHelper;
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
    private static final String ADMINROLE = "ADMIN";
    private static final String UNAUTHORIZEDMESSAGE = "Unauthorized, You're not an admin";
    private static final String INVALIDSTATUSMESSAGE = "Invalid status";
    private static final String NOTFOUNDMESSAGE = "Shipment not found";
    private static final String SUCCESSUPDATEMESSAGE = "Shipment updated successfully";
    private static final String INVALIDTRANSPORTATIONTYPEMESSAGE = "Invalid transportation type";

    @Mock
    private ShipmentService shipmentService;
    @Mock
    private AuthHelper authHelper;

    @InjectMocks
    private ShipmentController shipmentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateShipment() {
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
    void testGetShipmentByOrderId() {
        String orderId = "123";
        Shipment shipment = new Shipment();
        CompletableFuture<Shipment> futureShipment = CompletableFuture.completedFuture(shipment);
        when(shipmentService.findByOrderId(orderId)).thenReturn(futureShipment);

        ResponseEntity<Object> responseEntity = shipmentController.getShipmentByOrderId(orderId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(shipment, responseEntity.getBody());
    }

    @Test
    void testGetAllShipments() {
        List<Shipment> shipments = Collections.singletonList(new Shipment());
        CompletableFuture<List<Shipment>> futureShipments = CompletableFuture.completedFuture(shipments);
        when(shipmentService.getAllShipments()).thenReturn(futureShipments);

        ResponseEntity<Object> responseEntity = shipmentController.getAllShipments();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(shipments, responseEntity.getBody());
    }

    @Test
    void testCreateShipmentNullOrderId() {
        ResponseEntity<Object> responseEntity = shipmentController.createShipment(null);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void testCreateShipmentInvalidOrderId() {
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
        assertEquals(NOTFOUNDMESSAGE, responseEntity.getBody());
    }

    @Test
    void testGetShipmentByOrderIdNotFound() {
        String orderId = "invalidOrderId";
        CompletableFuture<Shipment> futureShipment = CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("Internal Server Error");
        });
        when(shipmentService.findByOrderId(orderId)).thenReturn(futureShipment);

        ResponseEntity<Object> responseEntity = shipmentController.getShipmentByOrderId(orderId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals(NOTFOUNDMESSAGE, responseEntity.getBody());
    }

    @Test
    void testGetAllShipmentsNoShipments() {
        List<Shipment> shipments = Collections.emptyList();
        CompletableFuture<List<Shipment>> futureShipments = CompletableFuture.completedFuture(shipments);
        when(shipmentService.getAllShipments()).thenReturn(futureShipments);

        ResponseEntity<Object> responseEntity = shipmentController.getAllShipments();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("No Shipments yet", responseEntity.getBody());
    }

    @Test
    void testUpdateShipmentStatus() {
        String id = "1";
        String status = ShippingStatus.DIKIRIM.toString();
        String jwtToken = "validJwtToken";
        Shipment shipment = new Shipment();
        shipment.setStatus(status);
        CompletableFuture<Shipment> futureShipment = CompletableFuture.completedFuture(shipment);

        when(authHelper.getUserRole(jwtToken)).thenReturn(ADMINROLE);
        when(shipmentService.findById(id)).thenReturn(futureShipment);

        ResponseEntity<Object> responseEntity = shipmentController.updateShipmentStatus(id, status, jwtToken);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(SUCCESSUPDATEMESSAGE, responseEntity.getBody());
    }

    @Test
    void testUpdateShipmentStatusByOrderId() {
        String orderId = "123";
        String status = ShippingStatus.DIKIRIM.toString();
        String jwtToken = "validJwtToken";
        Shipment shipment = new Shipment();
        shipment.setStatus(status);
        CompletableFuture<Shipment> futureShipment = CompletableFuture.completedFuture(shipment);

        when(authHelper.getUserRole(jwtToken)).thenReturn(ADMINROLE);
        when(shipmentService.findByOrderId(orderId)).thenReturn(futureShipment);

        ResponseEntity<Object> responseEntity = shipmentController.updateShipmentStatusByOrderId(orderId, status, jwtToken);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(SUCCESSUPDATEMESSAGE, responseEntity.getBody());
    }

    @Test
    void testUpdateShipmentStatusInvalidStatus()  {
        String id = "1";
        String invalidStatus = "InvalidStatus";
        String jwtToken = "validJwtToken";
        CompletableFuture<Shipment> futureShipment = CompletableFuture.completedFuture(new Shipment());

        when(authHelper.getUserRole(jwtToken)).thenReturn(ADMINROLE);
        when(shipmentService.findById(id)).thenReturn(futureShipment);

        ResponseEntity<Object> responseEntity = shipmentController.updateShipmentStatus(id, invalidStatus, jwtToken);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(INVALIDSTATUSMESSAGE, responseEntity.getBody());
    }

    @Test
    void testUpdateShipmentStatusNotFound() {
        String id = "999";
        String status = ShippingStatus.DIKIRIM.toString();
        String jwtToken = "validJwtToken";
        CompletableFuture<Shipment> futureShipment = CompletableFuture.completedFuture(null);

        when(authHelper.getUserRole(jwtToken)).thenReturn(ADMINROLE);
        when(shipmentService.findById(id)).thenReturn(futureShipment);

        ResponseEntity<Object> responseEntity = shipmentController.updateShipmentStatus(id, status, jwtToken);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(NOTFOUNDMESSAGE, responseEntity.getBody());
    }

    @Test
    void testUpdateShipmentStatusByOrderIdNotFound() {
        String orderId = "999";
        String status = ShippingStatus.DIKIRIM.toString();
        String jwtToken = "validJwtToken";
        CompletableFuture<Shipment> futureShipment = CompletableFuture.completedFuture(null);

        when(authHelper.getUserRole(jwtToken)).thenReturn(ADMINROLE);
        when(shipmentService.findByOrderId(orderId)).thenReturn(futureShipment);

        ResponseEntity<Object> responseEntity = shipmentController.updateShipmentStatusByOrderId(orderId, status, jwtToken);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals(NOTFOUNDMESSAGE, responseEntity.getBody());
    }

    @Test
    void testUpdateOrderShipmentStatusInvalidStatus() {
        String orderId = "1";
        String invalidStatus = "InvalidStatus";
        String jwtToken = "validJwtToken";
        CompletableFuture<Shipment> futureShipment = CompletableFuture.completedFuture(new Shipment());

        when(authHelper.getUserRole(jwtToken)).thenReturn(ADMINROLE);
        when(shipmentService.findByOrderId(orderId)).thenReturn(futureShipment);

        ResponseEntity<Object> responseEntity = shipmentController.updateShipmentStatusByOrderId(orderId, invalidStatus, jwtToken);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(INVALIDSTATUSMESSAGE, responseEntity.getBody());
    }

    @Test
    void testSetTransportationType() {
        String id = "1";
        String transportationType = TransportationType.TRUK.getValue();
        String jwtToken = "validJwtToken";
        Shipment shipment = new Shipment();
        shipment.setTransportationType(transportationType);
        CompletableFuture<Shipment> futureShipment = CompletableFuture.completedFuture(shipment);

        when(authHelper.getUserRole(jwtToken)).thenReturn(ADMINROLE);
        when(shipmentService.findById(id)).thenReturn(futureShipment);

        ResponseEntity<Object> responseEntity = shipmentController.setShipmentTransportationType(id, transportationType, jwtToken);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(SUCCESSUPDATEMESSAGE, responseEntity.getBody());
    }

    @Test
    void testSetTransportationTypeByOrderId() {
        String orderId = "123";
        String transportationType = TransportationType.TRUK.getValue();
        String jwtToken = "validJwtToken";
        Shipment shipment = new Shipment();
        shipment.setTransportationType(transportationType);
        CompletableFuture<Shipment> futureShipment = CompletableFuture.completedFuture(shipment);

        when(authHelper.getUserRole(jwtToken)).thenReturn(ADMINROLE);
        when(shipmentService.findByOrderId(orderId)).thenReturn(futureShipment);

        ResponseEntity<Object> responseEntity = shipmentController.setShipmentTransportationTypeByOrderId(orderId, transportationType, jwtToken);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(SUCCESSUPDATEMESSAGE, responseEntity.getBody());
    }

    @Test
    void testSetTransportationTypeByOrderIdNotFound() {
        String orderId = "999";
        String transportationType = TransportationType.TRUK.getValue();
        String jwtToken = "validJwtToken";
        CompletableFuture<Shipment> futureShipment = CompletableFuture.completedFuture(null);

        when(authHelper.getUserRole(jwtToken)).thenReturn(ADMINROLE);
        when(shipmentService.findByOrderId(orderId)).thenReturn(futureShipment);

        ResponseEntity<Object> responseEntity = shipmentController.setShipmentTransportationTypeByOrderId(orderId, transportationType, jwtToken);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals(NOTFOUNDMESSAGE, responseEntity.getBody());
    }

    @Test
    void testSetTransportationTypeInvalidTransportationType() {
        String id = "1";
        String invalidTransportationType = "InvalidTransportationType";
        String jwtToken = "validJwtToken";
        CompletableFuture<Shipment> futureShipment = CompletableFuture.completedFuture(new Shipment());

        when(authHelper.getUserRole(jwtToken)).thenReturn(ADMINROLE);
        when(shipmentService.findById(id)).thenReturn(futureShipment);

        ResponseEntity<Object> responseEntity = shipmentController.setShipmentTransportationType(id, invalidTransportationType, jwtToken);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(INVALIDTRANSPORTATIONTYPEMESSAGE, responseEntity.getBody());
    }

    @Test
    void testSetTransportationTypeByOrderIdInvalidTransportationType() {
        String orderId = "1";
        String invalidTransportationType = "InvalidTransportationType";
        String jwtToken = "validJwtToken";
        CompletableFuture<Shipment> futureShipment = CompletableFuture.completedFuture(new Shipment());

        when(authHelper.getUserRole(jwtToken)).thenReturn(ADMINROLE);
        when(shipmentService.findByOrderId(orderId)).thenReturn(futureShipment);

        ResponseEntity<Object> responseEntity = shipmentController.setShipmentTransportationTypeByOrderId(orderId, invalidTransportationType, jwtToken);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(INVALIDTRANSPORTATIONTYPEMESSAGE, responseEntity.getBody());
    }

    @Test
    void testUpdateShipmentStatusUnauthorized()  {
        String id = "1";
        String status = ShippingStatus.DIKIRIM.toString();

        when(authHelper.getUserRole("invalidJwtToken")).thenReturn(null);

        ResponseEntity<Object> responseEntity = shipmentController.updateShipmentStatus(id, status, "invalidJwtToken");

        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertEquals(UNAUTHORIZEDMESSAGE, responseEntity.getBody());
    }

    @Test
    void testUpdateShipmentStatusByOrderIdUnauthorized() {
        String orderId = "123";
        String status = ShippingStatus.DIKIRIM.toString();

        when(authHelper.getUserRole("invalidJwtToken")).thenReturn(null);

        ResponseEntity<Object> responseEntity = shipmentController.updateShipmentStatusByOrderId(orderId, status, "invalidJwtToken");

        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertEquals(UNAUTHORIZEDMESSAGE, responseEntity.getBody());
    }

    @Test
    void testSetTransportationTypeUnauthorized() {
        String id = "1";
        String transportationType = TransportationType.TRUK.getValue();

        when(authHelper.getUserRole("invalidJwtToken")).thenReturn(null);

        ResponseEntity<Object> responseEntity = shipmentController.setShipmentTransportationType(id, transportationType, "invalidJwtToken");

        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertEquals(UNAUTHORIZEDMESSAGE, responseEntity.getBody());
    }

    @Test
    void testSetTransportationTypeByOrderIdUnauthorized() {
        String orderId = "123";
        String transportationType = TransportationType.TRUK.getValue();

        when(authHelper.getUserRole("invalidJwtToken")).thenReturn(null);

        ResponseEntity<Object> responseEntity = shipmentController.setShipmentTransportationTypeByOrderId(orderId, transportationType, "invalidJwtToken");

        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertEquals(UNAUTHORIZEDMESSAGE, responseEntity.getBody());
    }

    @Test
    void testCreateShipmentCatchBlock() {
        doThrow(new RuntimeException()).when(shipmentService).saveShipment(any(Shipment.class));

        ResponseEntity<Object> response = shipmentController.createShipment("order1");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Failed to create shipment", response.getBody());
    }

    @Test
    void testGetShipmentCatchBlock() throws ExecutionException, InterruptedException {
        when(shipmentService.findById("id1")).thenThrow(new RuntimeException());

        ResponseEntity<Object> response = shipmentController.getShipment("id1");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Shipment not found", response.getBody());
    }

    @Test
    void testGetShipmentByOrderIdCatchBlock() {
        when(shipmentService.findByOrderId("order1")).thenThrow(new RuntimeException());

        ResponseEntity<Object> response = shipmentController.getShipmentByOrderId("order1");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Shipment not found", response.getBody());
    }

    @Test
    void testGetAllShipmentsCatchBlock() {
        when(shipmentService.getAllShipments()).thenThrow(new RuntimeException());

        ResponseEntity<Object> response = shipmentController.getAllShipments();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Failed to get shipments", response.getBody());
    }

    @Test
    void testUpdateShipmentStatusCatchBlock()  {
        when(authHelper.getUserRole(anyString())).thenReturn("ADMIN");
        when(shipmentService.findById("1")).thenThrow(new RuntimeException());

        ResponseEntity<Object> response = shipmentController.updateShipmentStatus("1", ShippingStatus.DIKIRIM.getValue(), "jwtToken");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Shipment not found", response.getBody());
    }

    @Test
    void testUpdateShipmentStatusByOrderIdCatchBlock() {
        when(authHelper.getUserRole(anyString())).thenReturn("ADMIN");
        when(shipmentService.findByOrderId("order1")).thenThrow(new RuntimeException());

        ResponseEntity<Object> response = shipmentController.updateShipmentStatusByOrderId("order1", ShippingStatus.DIKIRIM.getValue(), "jwtToken");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Shipment not found", response.getBody());
    }

    @Test
    void testSetShipmentTransportationTypeByOrderIdCatchBlock() {
        when(authHelper.getUserRole(anyString())).thenReturn("ADMIN");
        when(shipmentService.findByOrderId("order1")).thenThrow(new RuntimeException());

        ResponseEntity<Object> response = shipmentController.setShipmentTransportationTypeByOrderId("order1", TransportationType.TRUK.getValue(), "jwtToken");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Shipment not found", response.getBody());
    }

    @Test
    void testSetShipmentTransportationTypeCatchBlock()  {
        when(authHelper.getUserRole(anyString())).thenReturn("ADMIN");
        when(shipmentService.findById("1")).thenThrow(new RuntimeException());

        ResponseEntity<Object> response = shipmentController.setShipmentTransportationType("1", TransportationType.TRUK.getValue(), "jwtToken");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Shipment not found", response.getBody());
    }
}
