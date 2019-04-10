package dronePackage;

public class TowerClass implements Tower {
    Iterator i_bases;
    Iterator i_drones;
    Iterator i_orders;
    Iterator i_swarms;
    Object i_extraError;
    int i_baseNumber;
    FlightControl i_fc;

    public TowerClass() {
        i_bases = new IteratorClass();
        i_drones = new IteratorClass();
        i_orders = new IteratorClass();
        i_swarms = new IteratorClass();
        i_fc = new FlightControl(this);
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
        if (!i_bases.IdExists(a_baseName)) {
            l_Result = 1;
        }else {
            Base l_base =(Base)i_bases.getElement(a_baseName);
            l_base.exportHangar().reset();
            Iterator l_exportIte = new IteratorClass();
            while(l_base.exportHangar().hasNext()){
                DroneClass l_drone = (DroneClass)l_base.exportHangar().next();
                if(l_drone.getFuel()<a_droneRange){
                    l_exportIte.addElement(l_drone);
                    l_base.moveToServiceBay(l_drone.getObjectID());
                    i_fc.createService(a_baseName,l_drone.getObjectID());
                }
            }
            i_extraError = l_exportIte;
            l_Result = 0;
        }
        if(((Iterator)i_extraError).length()==0){
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
        } else if (!DroneInBase(a_droneId, a_originBaseId)) {
            l_Result = 3; // a_droneId does not exist
        } else if (!(Base2BaseDistance(a_originBaseId, a_destinationBaseId) < ((Drone) i_drones.getElement(a_droneId))
                .getRange())) {
            l_Result = 4; // a_droneId does not have capacity to fly from a_OriginBase to
                          // a_DestinationBase
        } else {
            ((Drone) i_drones.getElement(a_droneId)).goSwitchBaseroo();
            i_fc.newFlight(a_originBaseId, a_droneId, a_destinationBaseId,(int)Math.ceil(Base2BaseDistance(a_originBaseId, a_destinationBaseId)/10f));
            l_Result = 0;
        }
        return l_Result;
    }

    /**
     * @param a_baseId  String with base id to place swarm at
     * @param a_swarmId To be created swarm id
     * @param a_drones  array of names of drones to place in swarm
     */
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
                    if ((!l_base.droneIdExists(a_drones[i]))
                            || ((Drone) l_base.exportHangar().getElement(a_drones[i])).isFlying()) {
                        i_extraError = a_drones[i];
                        l_unavailable = true;
                        break;
                    }
                }
                if (l_unavailable) {
                    l_toReturn = 5;
                } else if (i_drones.IdExists(a_swarmId)) {
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
            if (!(((DroneClass) l_hangar.getElement(a_drones[i])).droneType().equals("swarm"))) {
                l_hangar.moveTo(a_drones[i], l_toStickIn);
            } else {
                Swarm l_swarmyBoy = (Swarm) l_hangar.getElement(a_drones[i]);
                l_swarmyBoy.exportIte().reset();
                while (l_swarmyBoy.exportIte().hasNext()) {
                    l_toStickIn.addElement(l_swarmyBoy.exportIte().next());
                }
                l_hangar.removeElement(l_swarmyBoy.getObjectID());
                i_drones.removeElement(l_swarmyBoy.getObjectID());
                i_drones.removeElement(l_swarmyBoy.getObjectID());

            }
        }
        l_hangar.reset();
        Swarm l_swarm = new Swarm(l_toStickIn, a_swarmId);
        i_drones.addElement(l_swarm);
        l_hangar.addElement(l_swarm);
        i_swarms.addElement(l_swarm);
    }

    public int disband(String a_baseId, String a_droneId) {
        int l_toReturn;
        if (!i_bases.IdExists(a_baseId)) {
            l_toReturn = 1;
        } else {
            
            if (!(((Base) i_bases.getElement(a_baseId)).droneIdExists(a_droneId)
                && ((Drone) ((Base) i_bases.getElement(a_baseId)).exportHangar().getElement(a_droneId)).droneType()
                        .equals("swarm"))) { // Checks if swarm exists in base
            l_toReturn = 2;
        } else {
            Swarm l_drone = ((Swarm) ((Base) i_bases.getElement(a_baseId)).exportHangar().getElement(a_droneId));
            Iterator l_payload = l_drone.exportIte();
            Base l_base = (Base)i_bases.getElement(a_baseId);
            Hangar l_hangar = l_base.exportHangar();
            l_payload.reset();
            while(l_payload.hasNext()){
                l_hangar.addElement(l_payload.next());
            }
            l_hangar.removeElement(a_droneId);
            l_toReturn = 0;
        }}
        return l_toReturn;
    }

    public int addOrder(String a_baseName, String a_orderId, int a_dimension, Location a_coords) {
        int l_Result;
        if (!i_bases.IdExists(a_baseName)) {
            l_Result = 1; // a_baseName does not exist
        } else if (i_orders.IdExists(a_orderId)) {
            l_Result = 2; // a_orderId already exists
        } else if (!(a_dimension > 0)) {
            l_Result = 3; // a_dimension is not a positive integer
        } else {
            OrderClass l_add = new OrderClass(a_orderId, a_dimension, a_coords, a_baseName);
            ((Base) i_bases.getElement(a_baseName)).addOrder(l_add);
            i_orders.addElement(l_add);
            l_Result = 0;
        }
        return l_Result;
    }

    public int deliverOrders(String a_originBaseId, String a_droneId, String a_orderId) {
        int l_Return;
        if (!i_bases.IdExists(a_originBaseId)) {
            l_Return = 1;
        } else if (!((Base) i_bases.getElement(a_originBaseId)).droneIdExists(a_droneId)) {
            l_Return = 2;
        } else if (!i_orders.IdExists(a_orderId)) {
            l_Return = 3;
        } else if (((Base) i_bases.getElement(a_originBaseId)).getCoords().distanceTo(
                ((OrderClass) i_orders.getElement(a_orderId)).getI_coords())*2 >= ((Drone) i_drones.getElement(a_droneId))
                        .getFuel()) {
            l_Return = 4;
        } else if (((OrderClass) i_orders.getElement(a_orderId))
                .getI_dimension() > ((Drone) i_drones.getElement(a_droneId)).getCapacity()) {
            l_Return = 5;
        } else {
            Drone l_drone = (Drone)i_drones.getElement(a_droneId);
            l_drone.goDeliveroo();
            int l_distance = ((Base) i_bases.getElement(a_originBaseId)).getCoords().distanceTo(((OrderClass) i_orders.getElement(a_orderId)).getI_coords());
            i_fc.newFlight(a_originBaseId, a_droneId, a_originBaseId, l_distance*2,(OrderClass)i_orders.getElement(a_orderId));
            l_Return = 0;
        }
        return l_Return;
    }

    public Iterator inTransit() {
        /*
         * String l_toPrint = ""; while(i_drones.hasNext()) { if(((Drone)
         * i_drones.next()).isFlying()) { if(((Drone) i_drones.next()).droneType() == 0)
         * { l_toPrint += ((Drone) i_drones.next()).getObjectID() + " " + ((Drone)
         * i_drones.next()) + " " + ((Drone) i_drones.next()) + " " + ((Drone)
         * i_drones.next()) + " " + ((Drone) i_drones.next()) + } else { l_toPrint += }
         * 
         * } } return l_toPrint;
         */
        return i_fc.exportFlight();
    }
    public int getTick(){
        return i_fc.getTick();
    }
    // hacky fix for outputting multiple objects
    public Object extraError() {
        return i_extraError;
    }

    public Iterator exportIte(String a_toExportName) {
        Iterator a_toExport = null;
        switch (a_toExportName) {
        case "bases":
            a_toExport = i_bases;
            break;
        case "drones":
            a_toExport = i_drones;
            break;
        case "swarms":
            a_toExport = i_swarms;
            break;
        case "orders":
            a_toExport = i_orders;
            break;
        case "doneOrders":
            a_toExport = i_fc.exportDone();
            break;
        }
        return a_toExport;
    }
    //The word tictac has given me PTSD, flashbacks from vietnam somehow appear in my head whenever I hear it. I was never in vietnam.
    public void tictac(int a_delta){
        i_fc.tictac(a_delta);
    }
}