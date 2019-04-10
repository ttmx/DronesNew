package dronePackage;
public class FlightPattern{
    private Tower i_tower;
    private String i_droneId;
    private String i_destination;
    private int i_endTick;
    public FlightPattern(Tower a_tower,String a_droneId,String a_destination,int a_endTick){
        i_tower = a_tower;
        i_droneId = a_droneId;
        i_destination = a_destination;
    }
}