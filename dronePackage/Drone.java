package dronePackage;

public interface Drone {
void flyToBase();
int getCapacity();
int getFuel();
int getRange();

void addDelivery(String a_orderID);

String prettyPrint();

}
