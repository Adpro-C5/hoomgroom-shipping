package id.ac.ui.cs.advprog.shipping.factory;

import id.ac.ui.cs.advprog.shipping.repository.ShipmentRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ShipmentRepositoryTest {
    @Test
    void testCreate() {
        ShipmentRepositoryFactory shipmentRepositoryFactory = new ShipmentRepositoryFactory();
        ShipmentRepository shipmentRepository = shipmentRepositoryFactory.create();
        assertNotNull(shipmentRepository);
        assertTrue(shipmentRepository instanceof ShipmentRepository);
    }
}
