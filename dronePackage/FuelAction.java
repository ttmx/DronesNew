package dronePackage;

public class FuelAction implements ManagedObject{
    String i_droneName;
    String i_baseName;
    int i_endTick;

    public FuelAction(String a_drone, String a_base, int a_endTick) {
        i_droneName = a_drone;
        i_baseName = a_base;
        i_endTick = a_endTick;
    }
    public boolean isDone(int a_tick){
        return a_tick >= i_endTick;
    }
    public String droneName(){
        return i_droneName;
    }
    public String baseName(){
        return i_baseName;
    }
	@Override
	public String getObjectID() {
		return i_droneName;
	}
}