package dronePackage;

public class TowerClass implements Tower {
    Iterator i_bases;
    Iterator i_drones;
    Iterator i_orders;
    Iterator i_swarms;
    Object i_extraError;
    int i_baseNumber;

    public TowerClass() {
        i_bases = new IteratorClass();
        i_drones = new IteratorClass();
        i_orders = new IteratorClass();
        i_swarms = new IteratorClass();
    }

    @Override
    public int createBase(Location a_coords, String a_name) {
        int l_Result;
        if (i_bases.IdExists(a_name)) {
            l_Result = 1;
        } else if (baseCoordsExists(a_coords)) {
            l_Result = 2;
        } else {
            BaseClass l_Base = new BaseClass(a_coords, a_name);
            i_bases.addElement(l_Base);
            l_Result = 0;
        }
        return l_Result;
    }

    private boolean baseCoordsExists(Location a_coords) {
        Base l_Base;
        i_bases.reset();

        while (i_bases.hasNext()) {
            l_Base = (Base) i_bases.next();
            if (l_Base.getCoords().sameCoords(a_coords))
                return true;
        }
        return false;
    }

    @Override
    public int makeDrone(String a_droneName, String a_baseName, String a_droneType, int a_droneCapacity,
            int a_droneRange, Tower a_tower) {
        int l_Result;
        if (a_droneCapacity < 1) {
            l_Result = 1;
        } else if (a_droneRange < 10) {
            l_Result = 2;
        } else if (!i_bases.IdExists(a_baseName)) {
            l_Result = 3;
        } else if (droneIdExists(a_droneName)) {
            l_Result = 4;
        } else if (!(a_droneType.equals("hermit") || a_droneType.equals("sociable"))) {
            l_Result = 5;
        } else {
            ((Base) i_bases.getElement(a_baseName)).createDrone(a_droneName, a_droneType, a_droneCapacity, a_droneRange,
                    a_tower);
            l_Result = 0;
        }
        return l_Result;
    }

    private boolean droneIdExists(String a_droneName) {
        boolean l_result = false;
        l_result = i_drones.IdExists(a_droneName) || i_swarms.IdExists(a_droneName);
        return l_result;
    }

    @Override
    public String listBases() {
        i_bases.reset();
        String l_toReturn = "";
        while (i_bases.hasNext()) {
            l_toReturn += listBase((Base) i_bases.next());
        }
        return l_toReturn;
    }

    /*
     * Montijo [3870,-897] Hangar: [cristiano 20 40, pepe 30 100] Service bay:
     * [fesja 20 40, battaglia 30 100, aboubakar 10 25] Sintra [3880,-938] Hangar:
     * [drone01 30 40] The service bay is empty! Monte Real [3951,-852] The hangar
     * is empty! The service bay is empty
     */
    private String listBase(Base a_toList) {
        String l_toReturn = a_toList.getBaseName() + " " + a_toList.getCoords().prettyCoords() + "\n";
        l_toReturn += a_toList.hangarPrint() + "\n";
        l_toReturn += a_toList.servicePrint() + "\n";
        return l_toReturn;
    }

    public String[] baseNames() {
        i_bases.reset();
        String[] l_toReturn = new String[i_bases.length()];
        for (int i = 0; i < i_bases.length(); i++) {
            l_toReturn[i] = ((Base) i_bases.next()).getBaseName();
        }
        return l_toReturn;
    }

    public int moveToServiceBay(String a_baseName, int a_droneRange) {
        int l_Result;
        i_drones.reset();
        if (i_bases.IdExists(a_baseName)) {
            if (i_drones.hasNext()) {
                l_Result = 0;
            }
            l_Result = 1;
        } else {
            l_Result = 2;
        }
        return l_Result;
    }

    @Override
    public void addDrone(DroneClass a_toAdd) {
        i_drones.addElement(a_toAdd);
    }

    /** */
    private int Base2BaseDistance(String a_fromBaseId, String a_toBaseId) {

        Base l_fromBase = (Base) i_bases.getElement(a_fromBaseId);
        Base l_toBase = (Base) i_bases.getElement(a_toBaseId);
        if (l_fromBase == null) // from base does not exist
            return -1;
        else if (l_toBase == null) // to base does not exist
            return -2;
        else
            return l_fromBase.getCoords().distanceTo(l_toBase.getCoords());

    }

    /**
     * Return true if it a drone with a_droneId identifier exists in the base with
     * a_baseId identifier
     * 
     * @param a_droneId Drone that we're searching
     * @param a_baseId  Base where we're seeking the Drone
     */
    private boolean DroneInBase(String a_droneId, String a_BaseId) {
        return ((Base) i_bases.getElement(a_BaseId)).droneIdExists(a_droneId);
    }

    /**
     * @param a_originBaseId
     * @param a_droneId
     * @param a_destinationBaseId
     * @return 1 if origin base does not exist,2 if destination base does not exist,
     *         3 if drone does not exist in origin base, 4 if drone does not have
     *         fuel necessary
     */
    public int flyToBase(String a_originBaseId, String a_droneId, String a_destinationBaseId) {
        int l_Result;
        if (!i_bases.IdExists(a_originBaseId)) {
            l_Result = 1; // a_OriginBase does not exist
        } else if (!i_bases.IdExists(a_destinationBaseId)) {
            l_Result = 2; // a_DestinationBase does not exist
        } else if (!DroneInBase(a_originBaseId, a_droneId)) {
            l_Result = 3; // a_droneId does not exist
        } else if (!(Base2BaseDistance(a_originBaseId, a_destinationBaseId) < ((Drone) i_drones.getElement(a_droneId))
                .getRange())) {
            l_Result = 4; // a_droneId does not have capacity to fly from a_OriginBase to
                          // a_DestinationBase
        } else {
            ((Base) i_bases.getElement(a_destinationBaseId)).addDrone(((DroneClass) i_drones.getElement(a_droneId)));
            ((Base) i_bases.getElement(a_originBaseId)).flyToBase(a_droneId);
            l_Result = 0;
        }
        return l_Result;
    }

    public int makeSwarm(String a_baseId, String a_swarmId, String[] a_drones) {
        int l_toReturn;
        boolean l_hasCopy = false;
        boolean hasHermit = false;
        Base l_base = (Base) i_bases.getElement(a_baseId);
        for (int i = 0; i < a_drones.length; i++) {
            for (int a = 0; a < a_drones.length; a++) {
                if (a_drones[i].equals(a_drones[a]) && a != i) {
                    l_hasCopy = true;
                    i_extraError = a_drones[a];
                    break;
                }
            }
        }
        if (!i_bases.IdExists(a_baseId)) {
            l_toReturn = 1;
        } else if (a_drones.length < 2) {
            l_toReturn = 2;
        } else if (l_hasCopy) {
            l_toReturn = 3;
        } else {
            for (int i = 0; i < a_drones.length; i++) {
                Drone l_drone;
                if (l_base.droneIdExists(a_drones[i])) {
                    l_drone = (Drone) i_drones.getElement(a_drones[i]);
                    if (l_drone.droneType().equals("hermit")) {
                        i_extraError = a_drones[i];
                        hasHermit = true;
                    }
                }
            }
            if (hasHermit) {
                l_toReturn = 4;
            } else {
                boolean l_unavailable = false;
                for (int i = 0; i < a_drones.length; i++) {

                    // Special order of operations lets second condition only be run if a drone does
                    // exist
                    if ((!l_base.droneIdExists(a_drones[i]))|| ((Drone) l_base.exportHangar().getElement(a_drones[i])).isFlying()) {
                        i_extraError = a_drones[i];
                        l_unavailable = true;
                        break;
                    }
                }
                if (l_unavailable) {
                    l_toReturn = 5;
                } else if (i_swarms.IdExists(a_swarmId)) {
                    l_toReturn = 6;
                } else {
                    l_toReturn = 0;
                    actualDroneAdd(a_baseId, a_swarmId, a_drones);
                }
            }

        }
        return l_toReturn;
    }

    private void actualDroneAdd(String a_baseId, String a_swarmId, String[] a_drones) {
        Base l_base = (Base) i_bases.getElement(a_baseId);
        Hangar l_hangar = l_base.exportHangar();
        Iterator l_toStickIn = new IteratorClass();

        for (int i = 0; i < a_drones.length; i++) {
            l_hangar.moveTo(a_drones[i], l_toStickIn);
        }
        Swarm l_swarm = new Swarm(l_toStickIn);
        i_swarms.addElement(l_swarm);
    }

    /*public int addOrder(String a_baseName, String a_orderId, int dimension, Location a_coords) {
        int l_Result;
        if(!i_bases.IdExists(a_baseName)) {
            l_Result = 1; // a_baseName does not exist
        } else if() {
            l_Result = 2; // a_orderId already exists   
        } else if(!(a_dimension > 0)) {
            l_Result = 3; // a_dimension is not a positive integer
        } else {
            ((Base) i_bases.getElement(a_baseName)).addOrder( a_orderId, dimension, a_coords);
            l_Result = 0;
        }
    }
*/
    public String listOrders(String a_baseName) {
        String l_toPrint = "";
        if (i_bases.IdExists(a_baseName)) {
            l_toPrint = ((Base) i_bases.getElement(a_baseName)).listOrders();
        } else {
            l_toPrint = "Base " + a_baseName + " does not exist!";
        }
        return l_toPrint;
    }

    public String listAllOrders() {
    String l_toPrint = "";
    if() {
    while(i_bases.hasNext()) {
    l_toPrint += "Orders in " + ((Base) i_bases.next()).getBaseName() + ":" + "\n";
    if() {
    l_toPrint += ((Base) i_bases.next()).listOrders();    
    } else {
    l_toPrint += "There are no pending orders in " + ((Base) i_bases.next()).getBaseName() + "." + "\n";   
    }    
    }
    } else {
    l_toPrint = "There are no pending orders!";    
    }
    return l_toPrint;    
    }

    public int deliverOrders(String a_destinationBaseId, String a_droneId, String a_orderId) {
        int l_Return;
        if (!i_bases.IdExists(a_destinationBaseId)) {
            l_Return = 1;
        } else if (!((Base) i_bases.getElement(a_destinationBaseId)).droneIdExists(a_droneId)) {
            l_Return = 2;
        } else if (s)
            return l_Return;
    }

    public Object extraError(){
        return i_extraError;
    }
}