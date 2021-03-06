package dronePackage;
public interface Base{
    /**
     * 
     * @return coordenates as Location object
     */
    Location getCoords();
    /**
     * 
     * @return base name as String
     */
    String getBaseName();

    void createDrone(String a_droneID, String a_kind, int a_capacity, int a_range, Tower a_tower);
    
    boolean droneIdExists(String a_droneName);
    String servicePrint();
    String hangarPrint();
    
    void moveToHangar(String a_droneId);

    void moveToServiceBay(String a_droneId);

    void addOrder(OrderClass a_order);

    String listOrders();

    void addDrone(DroneClass drone);

    Hangar exportHangar();

    int getOrderLength();
}