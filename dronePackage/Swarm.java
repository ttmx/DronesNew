package dronePackage;

class Swarm extends DroneClass implements ManagedObject {
    Iterator i_drones;

    public Swarm(Iterator a_drones, String a_identifier) {
        i_drones = a_drones;
        i_droneId = a_identifier;
        a_drones.reset();
        i_capacity = 0;
        i_droneType = "swarm";
        DroneClass l_thisdrone;
        i_range = 2147483647;
        while(a_drones.hasNext()){
            l_thisdrone =(DroneClass) a_drones.next();
            i_capacity+= l_thisdrone.i_capacity;
            i_range = (i_range>l_thisdrone.getRange()) ?l_thisdrone.getRange() : i_range ;
        }
    }

    @Override
    public String getObjectID() {
        return i_droneId;
    }

    public Iterator exportIte(){
        return i_drones;
    }
}