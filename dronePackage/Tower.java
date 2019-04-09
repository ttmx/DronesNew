package dronePackage;

public interface Tower {
    /**
     * 
     * @param a_coords
     * @param a_name
     * @return 0 if success, 1 if same name exist, 2 if same coords exists
     */
    int createBase(Location a_coords, String a_name);
    /**
     * 
     * @param a_droneName
     * @param a_baseName
     * @param a_droneType
     * @param a_droneCapacity
     * @param a_droneRange
     * @return 0 if success, 1 if drone capacity <1, 2 if drone range <10, 3 if base doesn't exist, 4 if same name exist, 5 if drone type is invalid
     */
    int makeDrone(String a_droneName, String a_baseName, String a_droneType, int a_droneCapacity, int a_droneRange, Tower a_tower);
    String listBases();
    String[] baseNames();
    /**
     * Add drone to i_drones
     * @param a_drone
     */
    void addDrone(DroneClass a_drone);
    
    /**
     * 
     * @param a_baseName
     * @param a_droneRange
     * @return 0 if sucess, 1 if there are no drones with fewer range, 2 if base doesn't exist
     */
    int moveToServiceBay(String a_baseName, int a_droneRange);

    int flyToBase(String a_OriginBase, String a_droneId, String a_DestinationBase);

    //int addOrder(String a_baseName, String a_orderId, int a_dimension, Location a_coords);

    String listOrders(String a_baseName);

    String listAllOrders();

    int deliverOrders(String a_destinationBaseId, String a_droneId, String a_orderId); 

    int makeSwarm(String a_baseId, String a_swarmId, String[] a_drones);
    
    Object extraError();

}