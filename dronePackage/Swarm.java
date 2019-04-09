package dronePackage;
class Swarm extends DroneClass implements ManagedObject{
Iterator i_drones;
String i_droneId;
public Swarm(Iterator a_drones, String a_identifier){
    i_drones = a_drones;
    i_droneId = a_identifier;
    }


    @Override
    public String getObjectID() {
        return super.getObjectID();
    }
}