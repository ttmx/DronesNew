package dronePackage;
public class FlightPattern implements ManagedObject{
    private String i_droneId;
    private String i_destination;
    private int i_endTick;
    private OrderClass i_order;
    private String i_id;
    public FlightPattern(String a_droneId,String a_destination,int a_endTick){
        i_droneId = a_droneId;
        i_destination = a_destination;
        i_endTick = a_endTick;
        i_id = i_droneId;
        i_order = null;
    }

    // doing an "extends" for a single variable seems unnecessary
    public FlightPattern(String a_droneId,String a_destination,int a_endTick,OrderClass a_order){
        i_droneId = a_droneId;
        i_destination = a_destination;
        i_endTick = a_endTick;
        i_order = a_order;

    }

    @Override
    public String getObjectID() {
        return i_id;
    }
    public boolean hasEnded(int a_tick){
        return a_tick >= i_endTick;
    }
    public String getDestination(){
        return i_destination;
    }
    public OrderClass getOrder(){
        return i_order;
    }
    public String droneId(){
        return i_droneId;
    }
}