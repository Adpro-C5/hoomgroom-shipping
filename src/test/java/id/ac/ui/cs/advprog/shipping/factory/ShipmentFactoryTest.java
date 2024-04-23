package id.ac.ui.cs.advprog.shipping.factory;

import enums.ShippingStatus;
import id.ac.ui.cs.advprog.shipping.factory.ShipmentFactory;
import id.ac.ui.cs.advprog.shipping.model.Shipment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShipmentFactoryTest {
    @Test
    void testCreate() {
        ShipmentFactory shipmentFactory = new ShipmentFactory();
        Shipment shipment = shipmentFactory.create();
        assertEquals(null, shipment.getOrderId());
        assertEquals(ShippingStatus.MENUNGGU_VERIFIKASI.getValue(), shipment.getStatus());
    }

    @Test
    void testCreateWithIdAndOrderId() {
        ShipmentFactory shipmentFactory = new ShipmentFactory();
        Shipment shipment = shipmentFactory.create("1", "1");
        assertEquals("1", shipment.getId());
        assertEquals("1", shipment.getOrderId());
        assertEquals(ShippingStatus.MENUNGGU_VERIFIKASI.getValue(), shipment.getStatus());
    }

    @Test
    void testCreateWithIdOrderIdAndStatus() {
        ShipmentFactory shipmentFactory = new ShipmentFactory();
        Shipment shipment = shipmentFactory.create("1", "1", ShippingStatus.DIKIRIM.getValue());
        assertEquals("1", shipment.getId());
        assertEquals("1", shipment.getOrderId());
        assertEquals(ShippingStatus.DIKIRIM.getValue(), shipment.getStatus());
    }
}
