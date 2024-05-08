package id.ac.ui.cs.advprog.shipping.service;

import id.ac.ui.cs.advprog.shipping.model.Shipment;
import id.ac.ui.cs.advprog.shipping.repository.ShipmentRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;

@Service
public class ShipmentServiceImpl implements ShipmentService {
    private static final Logger logger = LoggerFactory.getLogger(ShipmentServiceImpl.class);

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Override
    @Async("asyncTaskExecutor")
    public CompletableFuture<Shipment> findById(String id) {
        logger.info("Async task findById started");
        Shipment shipment = shipmentRepository.findById(id);
        logger.info("Async task findById completed");
        return CompletableFuture.completedFuture(shipment);
    }

    @Override
    @Async("asyncTaskExecutor")
    public CompletableFuture<Shipment> findByOrderId(String orderId) {
        logger.info("Async task findByOrderId started");
        Shipment shipment = shipmentRepository.findByOrderId(orderId);
        logger.info("Async task findByOrderId completed");
        return CompletableFuture.completedFuture(shipment);
    }

    @Override
    @Async("asyncTaskExecutor")
    public CompletableFuture<Shipment> saveShipment(Shipment shipment) {
        logger.info("Async task saveShipment started");
        if (shipmentRepository.findById(shipment.getId()) == null) {
            Shipment savedShipment = shipmentRepository.saveShipment(shipment);
            logger.info("Async task saveShipment completed");
            return CompletableFuture.completedFuture(savedShipment);
        } else {
            logger.info("Async task saveShipment completed (shipment already exists)");
            return CompletableFuture.completedFuture(null);
        }
    }

    @Override
    @Async("asyncTaskExecutor")
    public CompletableFuture<Shipment> deleteShipment(String id) {
        logger.info("deleteShipment started for shipment with id {}", id);
        Shipment deletedShipment = shipmentRepository.deleteShipment(id);
        logger.info("deleteShipment completed");
        return CompletableFuture.completedFuture(deletedShipment);
    }

    @Override
    @Async("asyncTaskExecutor")
    public CompletableFuture<List<Shipment>> getAllShipments() {
        logger.info("Async task getAllShipments started");
        List<Shipment> shipments = shipmentRepository.findAll();
        logger.info("Async task getAllShipments completed");
        return CompletableFuture.completedFuture(shipments);
    }

    @Override
    @Async("asyncTaskExecutor")
    public CompletableFuture<Shipment> setShipmentStatus(String id, String status) {
        logger.info("setShipmentStatus started for shipment with id {}", id);
        Shipment shipmentToChange = shipmentRepository.findById(id);
        if (shipmentToChange != null) {
            shipmentToChange.setStatus(status);
            Shipment updatedShipment = shipmentRepository.saveShipment(shipmentToChange);
            logger.info("setShipmentStatus completed");
            return CompletableFuture.completedFuture(updatedShipment);
        } else {
            logger.error("Shipment with id {} not found", id);
            throw new NoSuchElementException("Shipment with id " + id + " not found");
        }
    }
}
