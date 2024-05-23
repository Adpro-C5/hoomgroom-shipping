package id.ac.ui.cs.advprog.shipping.controller;

import enums.ShippingStatus;
import id.ac.ui.cs.advprog.shipping.factory.ShipmentFactory;
import id.ac.ui.cs.advprog.shipping.model.Shipment;
import id.ac.ui.cs.advprog.shipping.service.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/shipment")
public class ShipmentController {
    private final ShipmentFactory shipmentFactory = new ShipmentFactory();
    @Autowired
    private ShipmentService shipmentService;
    private static final String NOTFOUNDMESSAGE = "Shipment not found";
    private static final String INVALIDSTATUSMESSAGE = "Invalid status";
    private static final String SUCCESSUPDATEMESSAGE = "Shipment status updated successfully";

    @PostMapping("/create/{orderId}")
    public ResponseEntity<Object> createShipment(@PathVariable("orderId") String orderId) throws ExecutionException, InterruptedException {
        if (orderId == null || orderId.isEmpty()) {
            return new ResponseEntity<>("Order ID cannot be empty", HttpStatus.BAD_REQUEST);
        }
        try {
            Shipment shipment = shipmentFactory.create(orderId);
            Shipment savedShipment = shipmentService.saveShipment(shipment).get();
            return new ResponseEntity<>(savedShipment, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create shipment", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getShipment(@PathVariable("id") String id) throws ExecutionException, InterruptedException {
        Shipment shipment = shipmentService.findById(id).get();
        try {
            if (shipment != null) {
                return new ResponseEntity<>(shipment, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(NOTFOUNDMESSAGE, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(NOTFOUNDMESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-by-order-id/{orderId}")
    public ResponseEntity<Object> getShipmentByOrderId(@PathVariable("orderId") String orderId) throws ExecutionException, InterruptedException{
        try {
            Shipment shipment = shipmentService.findByOrderId(orderId).get();
            return new ResponseEntity<>(shipment, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(NOTFOUNDMESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<Object> getAllShipments() throws ExecutionException, InterruptedException {
        try{
            Iterable<Shipment> shipments = shipmentService.getAllShipments().get();
            if (ObjectUtils.isEmpty(shipments)) {
                return new ResponseEntity<>("No Shipments yet", HttpStatus.OK);
            } else {
                return new ResponseEntity<>(shipments, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to get shipments", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/update-status/{id}/{status}")
    public ResponseEntity<Object> updateShipmentStatus(@PathVariable("id") String id, @PathVariable("status") String status) throws ExecutionException, InterruptedException{
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
            return new ResponseEntity<>(NOTFOUNDMESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/update-status-order/{orderId}/{status}")
    public ResponseEntity<Object> updateShipmentStatusByOrderId(@PathVariable("orderId") String orderId, @PathVariable("status") String status) {
        if (!ShippingStatus.contains(status)) {
            return new ResponseEntity<>(INVALIDSTATUSMESSAGE, HttpStatus.BAD_REQUEST);
        }
        try {
            Shipment shipment = shipmentService.findByOrderId(orderId).get();
            shipmentService.setShipmentStatus(shipment.getId(), status);
            return new ResponseEntity<>(SUCCESSUPDATEMESSAGE, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(NOTFOUNDMESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
