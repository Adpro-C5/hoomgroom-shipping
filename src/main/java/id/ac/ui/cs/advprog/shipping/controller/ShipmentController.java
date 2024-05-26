package id.ac.ui.cs.advprog.shipping.controller;

import enums.ShippingStatus;
import enums.TransportationType;
import id.ac.ui.cs.advprog.shipping.factory.ShipmentFactory;
import id.ac.ui.cs.advprog.shipping.helper.AuthHelper;
import id.ac.ui.cs.advprog.shipping.model.Shipment;
import id.ac.ui.cs.advprog.shipping.service.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import java.util.concurrent.ExecutionException;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/shipment")
public class ShipmentController {
    private final ShipmentFactory shipmentFactory = new ShipmentFactory();
    private final AuthHelper authHelper;
    private final ShipmentService shipmentService;
    @Autowired
    public ShipmentController(ShipmentService shipmentService, AuthHelper authHelper) {
        this.shipmentService = shipmentService;
        this.authHelper = authHelper;
    }
    private static final String NOTFOUNDMESSAGE = "Shipment not found";
    private static final String INVALIDSTATUSMESSAGE = "Invalid status";
    private static final String INVALIDTRANSPORTATIONTYPEMESSAGE = "Invalid transportation type";
    private static final String SUCCESSUPDATEMESSAGE = "Shipment updated successfully";
    private static final String UNAUTHORIZEDMESSAGE = "Unauthorized, You're not an admin";
    private static final String ADMINROLE = "ADMIN";

    @PostMapping("/create/{orderId}")
    public ResponseEntity<Object> createShipment(@PathVariable("orderId") String orderId) {
        if (orderId == null || orderId.isEmpty()) {
            return new ResponseEntity<>("Order ID cannot be empty", HttpStatus.BAD_REQUEST);
        }
        try {
            Shipment shipment = shipmentFactory.create(orderId);
            Shipment savedShipment = shipmentService.saveShipment(shipment).get();
            return new ResponseEntity<>(savedShipment, HttpStatus.CREATED);
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            return new ResponseEntity<>("Failed to create shipment", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getShipment(@PathVariable("id") String id) throws ExecutionException, InterruptedException {
        try {
            Shipment shipment = shipmentService.findById(id).get();
            if (shipment != null) {
                return new ResponseEntity<>(shipment, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(NOTFOUNDMESSAGE, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            return new ResponseEntity<>(NOTFOUNDMESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-by-order-id/{orderId}")
    public ResponseEntity<Object> getShipmentByOrderId(@PathVariable("orderId") String orderId){
        try {
            Shipment shipment = shipmentService.findByOrderId(orderId).get();
            return new ResponseEntity<>(shipment, HttpStatus.OK);
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            return new ResponseEntity<>(NOTFOUNDMESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<Object> getAllShipments() {
        try{
            Iterable<Shipment> shipments = shipmentService.getAllShipments().get();
            if (ObjectUtils.isEmpty(shipments)) {
                return new ResponseEntity<>("No Shipments yet", HttpStatus.OK);
            } else {
                return new ResponseEntity<>(shipments, HttpStatus.OK);
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            return new ResponseEntity<>("Failed to get shipments", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/update-status/{id}/{status}")
    public ResponseEntity<Object> updateShipmentStatus(@PathVariable("id") String id,
                                                       @PathVariable("status") String status,
                                                       @RequestHeader("Authorization") String jwtToken){

        String role = authHelper.getUserRole(jwtToken);
        if(role == null || !role.equals(ADMINROLE)){
            return new ResponseEntity<>(UNAUTHORIZEDMESSAGE, HttpStatus.UNAUTHORIZED);
        }

        try{
            Shipment shipment = shipmentService.findById(id).get();
            if (!ShippingStatus.contains(status)) {
                return new ResponseEntity<>(INVALIDSTATUSMESSAGE, HttpStatus.BAD_REQUEST);
            }
            if (shipment != null) {
                shipmentService.setShipmentStatus(id, status);
                return new ResponseEntity<>(SUCCESSUPDATEMESSAGE, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(NOTFOUNDMESSAGE, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            return new ResponseEntity<>(NOTFOUNDMESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/update-status-order/{orderId}/{status}")
    public ResponseEntity<Object> updateShipmentStatusByOrderId(@PathVariable("orderId") String orderId,
                                                                @PathVariable("status") String status,
                                                                @RequestHeader("Authorization") String jwtToken){
        String role = authHelper.getUserRole(jwtToken);
        if(role == null || !role.equals(ADMINROLE)){
            return new ResponseEntity<>(UNAUTHORIZEDMESSAGE, HttpStatus.UNAUTHORIZED);
        }

        if (!ShippingStatus.contains(status)) {
            return new ResponseEntity<>(INVALIDSTATUSMESSAGE, HttpStatus.BAD_REQUEST);
        }
        try {
            Shipment shipment = shipmentService.findByOrderId(orderId).get();
            shipmentService.setShipmentStatus(shipment.getId(), status);
            return new ResponseEntity<>(SUCCESSUPDATEMESSAGE, HttpStatus.OK);
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            return new ResponseEntity<>(NOTFOUNDMESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/set-transportation-type-order/{orderId}/{transportationType}")
    public ResponseEntity<Object> setShipmentTransportationTypeByOrderId(@PathVariable("orderId") String orderId,
                                                                         @PathVariable("transportationType") String transportationType,
                                                                         @RequestHeader("Authorization") String jwtToken){

        String role = authHelper.getUserRole(jwtToken);
        if(role == null || !role.equals(ADMINROLE)){
            return new ResponseEntity<>(UNAUTHORIZEDMESSAGE, HttpStatus.UNAUTHORIZED);
        }

        if(!TransportationType.contains(transportationType)){
            return new ResponseEntity<>(INVALIDTRANSPORTATIONTYPEMESSAGE, HttpStatus.BAD_REQUEST);
        }
        try {
            Shipment shipment = shipmentService.findByOrderId(orderId).get();
            shipmentService.setShipmentTransportationType(shipment.getId(), transportationType);
            return new ResponseEntity<>(SUCCESSUPDATEMESSAGE, HttpStatus.OK);
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            return new ResponseEntity<>(NOTFOUNDMESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/set-transportation-type/{id}/{transportationType}")
    public ResponseEntity<Object> setShipmentTransportationType(
            @PathVariable("id") String id, @PathVariable("transportationType") String transportationType,
            @RequestHeader("Authorization") String jwtToken){

        String role = authHelper.getUserRole(jwtToken);
        if(role == null || !role.equals(ADMINROLE)){
            return new ResponseEntity<>(UNAUTHORIZEDMESSAGE, HttpStatus.UNAUTHORIZED);
        }

        try{
            Shipment shipment = shipmentService.findById(id).get();
            if (!TransportationType.contains(transportationType)) {
                return new ResponseEntity<>(INVALIDTRANSPORTATIONTYPEMESSAGE, HttpStatus.BAD_REQUEST);
            }
            if (shipment != null) {
                shipmentService.setShipmentTransportationType(id, transportationType);
                return new ResponseEntity<>(SUCCESSUPDATEMESSAGE, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(NOTFOUNDMESSAGE, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            return new ResponseEntity<>(NOTFOUNDMESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
