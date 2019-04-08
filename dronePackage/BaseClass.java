package dronePackage;

public class BaseClass implements Base, ManagedObject {
    Location i_coords;
    String i_baseName;
    Hangar i_hangar;
    ServiceBay i_serviceBay;
    Iterator i_orders;

    public BaseClass(Location a_coords, String a_baseName) {
        this.i_coords = a_coords;
        this.i_baseName = a_baseName;
        i_hangar = new Hangar();
        i_serviceBay = new ServiceBay();
    }

    @Override
    public Location getCoords() {
        return i_coords;
    }

    @Override
    public String getBaseName() {
        return i_baseName;
    }

    public String getObjectID() {
        return i_baseName;
    }

    @Override
    public void createDrone(String a_droneId, String a_droneType, int a_capacity, int a_range, Tower a_tower) {
        i_hangar.spawnDrone(a_droneId, a_droneType, a_capacity, a_range,a_tower);

    }

    public void moveToServiceBay(String a_droneId) {
    i_serviceBay.addElement(i_hangar.getElement(a_droneId));  
    }

    public void moveToHangar(String a_droneId) {
    i_hangar.addElement(i_serviceBay.getElement(a_droneId));    
    }

    @Override
    public boolean droneIdExists(String a_droneName) {
        return (i_serviceBay.IdExists(a_droneName)) || (i_hangar.IdExists(a_droneName));
    }

    @Override
    public String servicePrint() {
        if (i_serviceBay.length() > 0) {
            return "Service bay: "+i_serviceBay.prettyList();
        }else{
            return "The service bay is empty!";
        }
    }

    @Override
    public String hangarPrint() {
        if (i_hangar.length() > 0) {
            return "Hangar: "+i_hangar.prettyList();
        }else{
            return "The hangar is empty!";
        }
    }

    public void addOrder(String order_Id, int a_dimension, Location a_coords)  {
        OrderClass l_Order = new OrderClass(order_Id, a_dimension, a_coords);
        i_orders.addElement(l_Order);
   }

    @Override
    public void flyToBase(String a_droneId) {

    }

}