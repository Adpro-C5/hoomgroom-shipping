package id.ac.ui.cs.advprog.shipping.service;

import id.ac.ui.cs.advprog.shipping.model.Shipment;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ShipmentService {
    public CompletableFuture<Shipment> findById(String id);
    public CompletableFuture<Shipment> findByOrderId(String orderId);
    public CompletableFuture<Shipment> saveShipment(Shipment shipment);
    public CompletableFuture<Shipment> deleteShipment(String id);
    public CompletableFuture<List<Shipment>> getAllShipments();
    public CompletableFuture<Shipment> setShipmentStatus(String id, String status);
}
