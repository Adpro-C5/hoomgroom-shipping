package id.ac.ui.cs.advprog.shipping.factory;

import enums.ShippingStatus;
import id.ac.ui.cs.advprog.shipping.repository.ShipmentRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShipmentRepositoryFactoryTest {
    @Test
    void testCreate() {
        ShipmentRepositoryFactory shipmentRepositoryFactory = new ShipmentRepositoryFactory();
        ShipmentRepository shipmentRepository = shipmentRepositoryFactory.create();
        assertNotNull(shipmentRepository);
        assertInstanceOf(ShipmentRepository.class, shipmentRepository);
    }

    @Test
    void testCreateWrongArgument(){
        ShipmentRepositoryFactory shipmentRepositoryFactory = new ShipmentRepositoryFactory();
        assertThrows(UnsupportedOperationException.class, () -> {
            shipmentRepositoryFactory.create("1");
        });
        assertThrows(UnsupportedOperationException.class, () -> {
            shipmentRepositoryFactory.create("1","88");
        });
        String status = ShippingStatus.DIKIRIM.getValue();
        assertThrows(UnsupportedOperationException.class, () -> {
            shipmentRepositoryFactory.create("1","88", status);
        });
    }
}
