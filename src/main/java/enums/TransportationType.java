package enums;

import lombok.Getter;

@Getter
public enum TransportationType {
    MOTOR("MOTOR"),
    TRUK("TRUK"),
    PESAWAT("PESAWAT");

    private final String value;

    private TransportationType(String value){
        this.value = value;
    }

    public static boolean contains(String param){
        for(TransportationType transportationType:TransportationType.values()){
            if(transportationType.name().equals(param)){
                return true;
            }
        }
        return false;
    }
}
