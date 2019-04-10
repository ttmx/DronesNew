package dronePackage;

public interface Drone {
    void flyToBase();

    int getCapacity();

    int getFuel();

    int getRange();

    String droneType();

    void goDeliveroo();

    void doneDeliveroo();

    String prettyPrint();

    boolean isFlying();

    int returnFlightType();

    void goSwitchBaseroo();

    String getObjectID();
}
