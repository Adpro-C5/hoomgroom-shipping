package id.ac.ui.cs.advprog.shipping.factory;

import enums.ShippingStatus;
import id.ac.ui.cs.advprog.shipping.model.Shipment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ShipmentFactoryTest {
    @Test
    void testCreate() {
        ShipmentFactory shipmentFactory = new ShipmentFactory();
        Shipment shipment = shipmentFactory.create();
        assertNull(shipment.getOrderId());
        assertEquals(ShippingStatus.MENUNGGU_VERIFIKASI.getValue(), shipment.getStatus());
        assertNull(shipment.getNoResi());
        assertNull(shipment.getTransportationType());
    }

    @Test
    void testCreateWithOrderId() {
        ShipmentFactory shipmentFactory = new ShipmentFactory();
        Shipment shipment = shipmentFactory.create("1");
        assertEquals("1", shipment.getOrderId());
        assertEquals(ShippingStatus.MENUNGGU_VERIFIKASI.getValue(), shipment.getStatus());
        assertNull(shipment.getNoResi());
        assertNull(shipment.getTransportationType());
    }

    @Test
    void testCreateWithIdAndOrderId() {
        ShipmentFactory shipmentFactory = new ShipmentFactory();
        Shipment shipment = shipmentFactory.create("1", "1");
        assertEquals("1", shipment.getId());
        assertEquals("1", shipment.getOrderId());
        assertEquals(ShippingStatus.MENUNGGU_VERIFIKASI.getValue(), shipment.getStatus());
        assertNull(shipment.getNoResi());
        assertNull(shipment.getTransportationType());
    }

    @Test
    void testCreateWithIdOrderIdAndStatus() {
        ShipmentFactory shipmentFactory = new ShipmentFactory();
        Shipment shipment = shipmentFactory.create("1", "1", ShippingStatus.DIKIRIM.getValue());
        assertEquals("1", shipment.getId());
        assertEquals("1", shipment.getOrderId());
        assertEquals(ShippingStatus.DIKIRIM.getValue(), shipment.getStatus());
        assertNull(shipment.getNoResi());
        assertNull(shipment.getTransportationType());
    }
}
