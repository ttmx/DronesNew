package dronePackage;
public class FlightPattern implements ManagedObject{
    private String i_droneId;
    private String i_destination;
    private int i_endTick;
    private int i_startTick;
    private OrderClass i_order;
    private String i_id;
    private String i_origin;
    public FlightPattern(String a_droneId,String a_destination,int a_endTick,int a_startTick,String a_origin){
        i_droneId = a_droneId;
        i_destination = a_destination;
        i_endTick = a_endTick;
        i_id = i_droneId;
        i_order = null;
        i_origin = a_origin;
        i_startTick = a_startTick;
    }

    // doing an "extends" for a single variable seems unnecessary
    public FlightPattern(String a_droneId,String a_destination,int a_endTick,int a_startTick,String a_origin,OrderClass a_order){
        i_droneId = a_droneId;
        i_id = a_order.getObjectID();
        i_destination = a_destination;
        i_endTick = a_endTick;
        i_order = a_order;
        i_origin = a_origin;
        i_startTick = a_startTick;
    }

    @Override
    public String getObjectID() {
        return i_id;
    }
    public boolean hasEnded(int a_tick){
        return a_tick >= i_endTick;
    }
    public int getEndTick(){
        return i_endTick;
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
    public String getOrigin(){
        return i_origin;
    }
    public int coveredDistance(int a_tick){
        return (a_tick-i_startTick)*10;
    }
    public int totalDistance(){
        return (i_endTick-i_startTick)*10;
    }
    public String type(){
        if(i_order==null){
            return "relocation";
        }else{
            return "delivery";
        }
    }
}