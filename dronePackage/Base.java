package dronePackage;
public interface Base{
    /**
     * 
     * @return coordenates as Location object
     */
    Location getCoords();n
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

    void addOrder(String orderId, int a_dimension, Location a_coords);

    void flyToBase(String a_droneId);
}