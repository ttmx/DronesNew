package dronePackage;

public class OrderClass implements Order, ManagedObject {
    private String i_orderID;
    private int i_dimension;
    private Location i_coords;
    private boolean i_delivered;
    private String i_baseName;
    private int i_compTime;
    public OrderClass(String a_orderID, int a_dimension, Location a_coords, String a_baseName) {
        i_orderID = a_orderID;
        i_dimension = a_dimension;
        i_coords = a_coords;
        i_delivered = false;
        i_baseName = a_baseName;
    }

    public String getObjectID() {
        return i_orderID;
    }

    /**
     * @return the i_orderID
     */
    public String getI_orderID() {
        return i_orderID;
    }

    /**
     * @param i_orderID the i_orderID to set
     */
    public void setI_orderID(String i_orderID) {
        this.i_orderID = i_orderID;
    }

    /**
     * @return the i_dimension
     */
    public int getI_dimension() {
        return i_dimension;
    }

    /**
     * @param i_dimension the i_dimension to set
     */
    public void setI_dimension(int i_dimension) {
        this.i_dimension = i_dimension;
    }

    /**
     * @return the i_coords
     */
    public Location getI_coords() {
        return i_coords;
    }

        /**
     * @return the i_baseName
     */
    public String getI_baseName() {
    return i_baseName;    
    }

    /**
     * @param i_coords the i_coords to set
     */
    public void setI_coords(Location i_coords) {
        this.i_coords = i_coords;
    }

    public String prettyPrint() {
        String l_toReturn = getObjectID() + "; " + getI_dimension() + "; " + getI_coords().prettyCoords();
        return l_toReturn;
    }

    public void done(){
        i_delivered = true;
    }

    public boolean isDelivered() {
        return i_delivered;
    }
    public void setCompleteTick(int a_tick){
        i_compTime = a_tick;
    }
    public int getCompTime(){
        return i_compTime;
    }
}