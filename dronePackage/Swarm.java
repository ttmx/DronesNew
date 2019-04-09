package dronePackage;
class Swarm extends DroneClass implements ManagedObject{
Iterator i_drones;
public Swarm(Iterator a_drones){
    i_drones = a_drones;
    }


    @Override
    public String getObjectID() {
        return super.getObjectID();
    }
}