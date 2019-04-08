package dronePackage;
class Swarm extends DroneClass implements ManagedObject{
Drone[] i_drones;
public Swarm(DroneClass[] a_drones){
    i_drones = a_drones;
    }


    @Override
    public String getObjectID() {
        return super.getObjectID();
    }
}