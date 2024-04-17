package enums;

import lombok.Getter;

@Getter
public enum ShippingStatus {
    MENUNGGU_VERIFIKASI("MENUNGGU_VERIFIKASI"),
    DIPROSES("DIPROSES"),
    DIKIRIM("DIKIRIM"),
    TIBA("TIBA"),
    SELESAI("SELESAI");

    private final String value;

    private ShippingStatus(String value){
        this.value = value;
    }

    public static boolean contains(String param){
        for(ShippingStatus shippingStatus:ShippingStatus.values()){
            if(shippingStatus.name().equals(param)){
                return true;
            }
        }
        return false;
    }
}