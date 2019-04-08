package dronePackage;

public class OrderClass implements Order, ManagedObject {
    private String i_baseID, i_orderID;
    private int i_dimension;
    private Location i_coords;

    public OrderClass(String a_orderID, int a_dimension, Location a_coords) {
        i_orderID = a_orderID;
        i_dimension = a_dimension;
        i_coords = a_coords;
    }

    public String getObjectID() {
        return i_orderID;
    }

    /**
     * @return the i_baseID
     */
    public String getI_baseID() {
        return i_baseID;
    }

    /**
     * @param i_baseID the i_baseID to set
     */
    public void setI_baseID(String i_baseID) {
        this.i_baseID = i_baseID;
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
     * @param i_coords the i_coords to set
     */
    public void setI_coords(Location i_coords) {
        this.i_coords = i_coords;
    }

    public String prettyPrint() {
    String l_toReturn = getObjectID() + "; " + getI_dimension() + "; " + getI_coords().prettyCoords();
    return l_toReturn;
    }
}