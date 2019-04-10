package dronePackage;

public class FlightControl {
    Tower i_tower;

    Iterator i_flightIte;
    Iterator i_fuelUp;
    Iterator i_bases;
    Iterator i_drones;
    Iterator i_done;
    int i_tick;

    public FlightControl(Tower a_tower) {
        i_tower = a_tower;
        i_bases = a_tower.exportIte("bases");
        i_drones = a_tower.exportIte("drones");
        i_tick = 0;
        i_flightIte = new IteratorClass();
        i_fuelUp = new IteratorClass();
        i_done = new IteratorClass();
    }
    /**
    * Return the base with an identifier that is equal to a_baseId
     * @param a_baseId the base we're seeking in i_bases
     */
    private Base getBase(String a_baseId) { 
        return ((Base) i_bases.getElement(a_baseId));
    }
    /**
     * 
     * @param a_droneId
     * @param a_destination
     * @param a_duration
     * @return 0 if successful
     */
    public void newFlight(String a_departure, String a_droneId, String a_destination, int a_duration) {
        i_flightIte.addElement(new FlightPattern(a_droneId, a_destination, i_tick + a_duration, i_tick, a_departure));
        ((DroneClass) getBase(a_departure).exportHangar().getElement(a_droneId))
                .removeFuel(a_duration * 10);
        getBase(a_departure).exportHangar().removeElement(a_droneId);

    }

    public void newFlight(String a_departure, String a_droneId, String a_destination, int a_duration,
            OrderClass a_order) {
        i_flightIte.addElement(new FlightPattern(a_droneId, a_destination, i_tick + a_duration, i_tick, a_departure, a_order));
        ((DroneClass) getBase(a_departure).exportHangar().getElement(a_droneId))
                .removeFuel(a_duration * 10);
        getBase(a_departure).exportHangar().removeElement(a_droneId);
    }

    public void tictac(int a_delta) {
        i_tick += a_delta;
        updateFlights(i_flightIte);
        updateFuel();
    }

    private void updateFlights(Iterator a_toUpdate) {

        a_toUpdate.reset();
        FlightPattern l_flight;
        while (a_toUpdate.hasNext()) {
            l_flight = (FlightPattern) a_toUpdate.next();
            if (l_flight.hasEnded(i_tick)) {
                Hangar l_hangar = getBase(l_flight.getDestination()).exportHangar();
                l_hangar.addElement(i_drones.getElement(l_flight.droneId()));
                a_toUpdate.removeElement(l_flight.droneId());
                ((DroneClass) i_drones.getElement(l_flight.droneId())).land();

                if (l_flight.getOrder() != null) {
                    l_flight.getOrder().setCompleteTick(l_flight.getEndTick());
                    i_done.addElement(l_flight.getOrder());
                }
            }
        }
    }
    public void updateFuel(){
        i_fuelUp.reset();
        while(i_fuelUp.hasNext()){
            FuelAction l_fuel = (FuelAction)i_fuelUp.next();
            if(l_fuel.isDone(i_tick)){
                ((DroneClass)i_drones.getElement(l_fuel.getObjectID())).refuel();
                getBase(l_fuel.baseName()).moveToHangar(l_fuel.getObjectID());
            }
        }
    }

    public Iterator exportFlight() {
        return i_flightIte;
    }
    public Iterator exportDone(){
        return i_done;
    }

    public int getTick() {
        return i_tick;
    }

	public void createService(String a_baseName, String a_droneId) {
        i_fuelUp.addElement(new FuelAction(a_droneId,a_baseName,i_tick+3));
    }
}