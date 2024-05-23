package id.ac.ui.cs.advprog.shipping.model;

import enums.ShippingStatus;
import enums.TransportationType;
import id.ac.ui.cs.advprog.shipping.factory.ShipmentFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShipmentTest {
    ShipmentFactory shipmentFactory = new ShipmentFactory();

    @Test
    void testCreateInvalidStatus(){
        assertThrows(IllegalArgumentException.class, () -> {
           shipmentFactory.create("1","1","HELLO");
        });
    }

    @Test
    void testCreateWithDefaultValues() {
        Shipment shipment = shipmentFactory.create();
        assertNotNull(shipment.getId());
        assertNull(shipment.getOrderId());
        assertTrue(ShippingStatus.contains(shipment.getStatus()));
    }

    @Test
    void testCreateWithOrderId() {
        Shipment shipment = shipmentFactory.create("1");
        assertNotNull(shipment.getId());
        assertEquals("1", shipment.getOrderId());
        assertTrue(ShippingStatus.contains(shipment.getStatus()));
    }

    @Test
    void testCreateWithIdOrderIdAndStatus() {
        Shipment shipment = shipmentFactory.create("1", "1", ShippingStatus.DIKIRIM.getValue());
        assertEquals("1", shipment.getId());
        assertEquals("1", shipment.getOrderId());
        assertEquals(ShippingStatus.DIKIRIM.getValue(), shipment.getStatus());
    }

    @Test
    void testCreateWithDefaultStatus() {
        Shipment shipment = shipmentFactory.create("1", "1");
        assertEquals("1", shipment.getId());
        assertEquals("1", shipment.getOrderId());
        assertEquals(ShippingStatus.MENUNGGU_VERIFIKASI.getValue(), shipment.getStatus());
    }

    @Test
    void setValidStatus(){
        Shipment shipment = shipmentFactory.create("1", "1");
        shipment.setStatus(ShippingStatus.DIPROSES.getValue());
        assertEquals(ShippingStatus.DIPROSES.getValue(),shipment.getStatus());
    }

    @Test
    void setValidStatusToDikirim(){
        Shipment shipment = shipmentFactory.create("1", "1");
        shipment.setStatus(ShippingStatus.DIKIRIM.getValue());
        assertEquals(ShippingStatus.DIKIRIM.getValue(),shipment.getStatus());
        assertNotNull(shipment.getNoResi());
    }

    @Test
    void setInvalidStatus(){
        Shipment shipment = shipmentFactory.create("1", "1");
        assertThrows(IllegalArgumentException.class, () -> {
            shipment.setStatus("HELLO");
        });
    }

    @Test
    void setValidTransportationType(){
        Shipment shipment = shipmentFactory.create("1", "1");
        shipment.setTransportationType(TransportationType.TRUK.getValue());
        assertEquals(TransportationType.TRUK.getValue(),shipment.getTransportationType());
    }

    @Test
    void setInvalidTransportationType(){
        Shipment shipment = shipmentFactory.create("1", "1");
        assertThrows(IllegalArgumentException.class, () -> {
            shipment.setTransportationType("HELLO");
        });
    }
}
