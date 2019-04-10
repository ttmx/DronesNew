package dronePackage;

public class FlightControl {
    Tower i_tower;

    // my implementation of iterators doesnt allow for multiple items with the same
    // ID,
    // to guarentee that doesn't happen I placed two different iterators here.
    Iterator i_flightIte;
    Iterator i_deliveries;
    Iterator i_bases;
    Iterator i_drones;
    Iterator i_done;
    int i_tick;

    public FlightControl(Tower a_tower) {
        i_tower = a_tower;
        i_bases = a_tower.exportIte("bases");
        i_drones = a_tower.exportIte("drones");
        i_tick = 0;
        i_deliveries = new IteratorClass();
        i_flightIte = new IteratorClass();
    }

    /**
     * 
     * @param a_droneId
     * @param a_destination
     * @param a_duration
     * @return 0 if successful
     */
    public void newFlight(String a_departure, String a_droneId, String a_destination, int a_duration) {
        i_flightIte.addElement(new FlightPattern(a_droneId, a_destination, i_tick + a_duration));
        ((DroneClass)((Base)i_bases.getElement(a_departure)).exportHangar().getElement(a_droneId)).removeFuel(a_duration*10);
        ((Base)i_bases.getElement(a_departure)).exportHangar().removeElement(a_droneId);
        
    }
    public void newFlight(String a_departure, String a_droneId, String a_destination, int a_duration,OrderClass a_order) {
        i_flightIte.addElement(new FlightPattern(a_droneId, a_destination, i_tick + a_duration,a_order));
        ((DroneClass)((Base)i_bases.getElement(a_departure)).exportHangar().getElement(a_droneId)).removeFuel(a_duration*10);
        ((Base)i_bases.getElement(a_departure)).exportHangar().removeElement(a_droneId);
    }

    public void tictac(int a_delta) {
        i_tick += a_delta;
        updateFlights(i_flightIte);
        updateFlights(i_deliveries);
        updateDeliveries();
    }

    private void updateFlights(Iterator a_toUpdate) {

        a_toUpdate.reset();
        FlightPattern l_flight;
        while (a_toUpdate.hasNext()) {
            l_flight = (FlightPattern) a_toUpdate.next();
            if (l_flight.hasEnded(i_tick)) {
                Hangar l_hangar = ((Base) i_bases.getElement(l_flight.getDestination())).exportHangar();
                l_hangar.addElement(i_drones.getElement(l_flight.droneId()));
                a_toUpdate.removeElement(l_flight.droneId());
                ((DroneClass) i_drones.getElement(l_flight.droneId())).land();
                
                if(l_flight.getOrder()!=null){
                    i_done.addElement(l_flight.getOrder());
                }
            }
        }
    }

    private void updateDeliveries() {
        
    }

}