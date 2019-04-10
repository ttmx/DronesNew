package dronePackage;

public class ServiceBay extends IteratorClass {
    public String prettyList() {
        reset();
        String i_string;
        i_string = "[";
        while (hasNext()) {
            i_string += ((Drone) next()).prettyPrint();
            if (hasNext()) {
                i_string = i_string + ", ";
            } else {
                i_string = i_string + "]";
            }
        }
        return i_string;
    }
}