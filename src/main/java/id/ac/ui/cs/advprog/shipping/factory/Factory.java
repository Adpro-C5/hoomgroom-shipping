package id.ac.ui.cs.advprog.shipping.factory;

public interface Factory <T>{
    T create();
    T create(String id, String orderId);
    T create(String id, String orderId, String status);
}
