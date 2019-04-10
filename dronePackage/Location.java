package dronePackage;
import java.lang.Math;

// Class for locations with helpful function
public class Location {
    int i_lat;
    int i_long;

    public Location(int a_lat, int a_long) {
        i_lat = a_lat;
        i_long = a_long;
    }

    public int getLong() {
        return i_long;
    }

    public int getLat() {
        return i_lat;
    }
/**
 * 
 * @return coords in format [lat,long]
 */
    public String prettyCoords() {
        return "[" + i_lat + "," + i_long + "]";
    }
    public boolean sameCoords(Location a_otherCoords){
        return (i_long == a_otherCoords.getLong()) && (i_lat == a_otherCoords.getLat());
    }
    public int distanceTo(Location a_otherCoords){
        return (int)Math.ceil(Math.sqrt(Math.pow(a_otherCoords.i_lat-i_lat,2)+Math.pow(a_otherCoords.i_long-i_long,2)));
    }
}