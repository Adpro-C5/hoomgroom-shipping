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
    public ResponseEntity<Object> createShipment(@PathVariable("orderId") String orderId) {
        if(orderId == null || orderId.isEmpty()){
            return new ResponseEntity<>("Order ID cannot be empty", HttpStatus.BAD_REQUEST);
        }
        Shipment shipment = shipmentFactory.create(orderId);
        Shipment savedShipment = shipmentService.saveShipment(shipment);
        return new ResponseEntity<>(savedShipment, HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getShipment(@PathVariable("id") String id) {
        Shipment shipment = shipmentService.findById(id);
        if (shipment != null) {
            return new ResponseEntity<>(shipment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(NOTFOUNDMESSAGE, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get-by-order-id/{orderId}")
    public ResponseEntity<Object> getShipmentByOrderId(@PathVariable("orderId") String orderId) {
        try {
            Shipment shipment = shipmentService.findByOrderId(orderId);
            return new ResponseEntity<>(shipment, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(NOTFOUNDMESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<Object> getAllShipments() {
        Iterable<Shipment> shipments = shipmentService.getAllShipments();
        if (ObjectUtils.isEmpty(shipments)) {
            return new ResponseEntity<>("No Shipments yet", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(shipments, HttpStatus.OK);
        }
    }


    @PostMapping("/update-status/{id}/{status}")
    public ResponseEntity<Object> updateShipmentStatus(@PathVariable("id") String id, @PathVariable("status") String status) {
        Shipment shipment = shipmentService.findById(id);
        if (!ShippingStatus.contains(status)) {
            return new ResponseEntity<>(INVALIDSTATUSMESSAGE, HttpStatus.BAD_REQUEST);
        }
        if (shipment != null) {
            shipment.setStatus(status);
            shipmentService.saveShipment(shipment);
            return new ResponseEntity<>(SUCCESSUPDATEMESSAGE, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(NOTFOUNDMESSAGE, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/update-status-order/{orderId}/{status}")
    public ResponseEntity<Object> updateShipmentStatusByOrderId(@PathVariable("orderId") String orderId, @PathVariable("status") String status) {
        if (!ShippingStatus.contains(status)) {
            return new ResponseEntity<>(INVALIDSTATUSMESSAGE, HttpStatus.BAD_REQUEST);
        }
        try {
            Shipment shipment = shipmentService.findByOrderId(orderId);
            shipment.setStatus(status);
            shipmentService.saveShipment(shipment);
            return new ResponseEntity<>(SUCCESSUPDATEMESSAGE, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(NOTFOUNDMESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
