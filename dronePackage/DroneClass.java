package dronePackage;

public class DroneClass implements Drone, ManagedObject {
    String i_droneId;
    int i_capacity;
    int i_range;
    int i_fuel;
    String i_droneType;
    boolean i_isFlying;

    @Override
    public void flyToBase() {

    }

    public DroneClass(String a_droneId, String a_droneType, int a_capacity, int a_range) {
        this.i_droneId = a_droneId;
        this.i_capacity = a_capacity;
        this.i_range = a_range;
        this.i_fuel = a_range;
        this.i_droneType = a_droneType;
    }

    public DroneClass() {

    }
    
    public boolean isFlying(){
        return i_isFlying;
    }

    @Override
    public void addDelivery(String a_orderID) {

    }

    @Override
    public int getFuel() {
        return i_fuel;
    }

    @Override
    public int getRange() {
        return i_range;
    }

    @Override
    public String getObjectID() {
        return i_droneId;
    }

    @Override
    public int getCapacity() {
        return i_capacity;
    }

    public String prettyPrint() {
        return i_droneId + " " + i_capacity + " " + i_fuel;
    }

    public String droneType() {
        return i_droneType;
    }
}