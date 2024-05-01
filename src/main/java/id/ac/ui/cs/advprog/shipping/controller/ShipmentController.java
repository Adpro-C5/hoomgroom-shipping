package id.ac.ui.cs.advprog.shipping.controller;

import id.ac.ui.cs.advprog.shipping.factory.ShipmentFactory;
import id.ac.ui.cs.advprog.shipping.model.Shipment;
import id.ac.ui.cs.advprog.shipping.service.ShipmentService;
import id.ac.ui.cs.advprog.shipping.service.ShipmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shipment")
public class ShipmentController {
    private ShipmentFactory shipmentFactory = new ShipmentFactory();
    @Autowired
    private ShipmentService shipmentService;

    @PostMapping("/create/{orderId}")
    public ResponseEntity<Shipment> createShipment(@PathVariable("orderId") String orderId) {
        Shipment shipment = shipmentFactory.create(orderId);
        return new ResponseEntity<>(shipmentService.saveShipment(shipment), HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Shipment> getShipment(@PathVariable("id") String id) {
        return new ResponseEntity<>(shipmentService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/get-by-order-id/{orderId}")
    public ResponseEntity<Shipment> getShipmentByOrderId(@PathVariable("orderId") String orderId) {
        return new ResponseEntity<>(shipmentService.findByOrderId(orderId), HttpStatus.OK);
    }

    @GetMapping("/get-all")
    public ResponseEntity<Iterable<Shipment>> getAllShipments() {
        return new ResponseEntity<>(shipmentService.getAllShipments(), HttpStatus.OK);
    }

    @PostMapping("/update-status/{id}/{status}")
    public ResponseEntity<Shipment> updateShipmentStatus(@PathVariable("id") String id, @PathVariable("status") String status) {
        Shipment shipment = shipmentService.findById(id);
        shipment.setStatus(status);
        return new ResponseEntity<>(shipmentService.saveShipment(shipment), HttpStatus.OK);
    }

    @PostMapping("/update-status-order/{orderId}/{status}")
    public ResponseEntity<Shipment> updateShipmentStatusByOrderId(@PathVariable("orderId") String orderId, @PathVariable("status") String status) {
        Shipment shipment = shipmentService.findByOrderId(orderId);
        shipment.setStatus(status);
        return new ResponseEntity<>(shipmentService.saveShipment(shipment), HttpStatus.OK);
    }

    @GetMapping("/")
    public String trackingPage() {
        return "trackShippingPage";
    }

    @GetMapping("/result/{id}")
    public String trackingResultPage(@PathVariable("id") String id){
        return "resultPage";
    }

}
