package dronePackage;

public class ServiceBay extends IteratorClass {
    public String prettyList() {
        reset();
        String i_string;
        if (hasNext()) {
            i_string = "[";
            while (hasNext()) {
                i_string.concat(((Drone) next()).prettyPrint());
                if (hasNext()) {
                    i_string = i_string + ", ";
                } else {
                    i_string = i_string + "]";
                }
            }
        }else {
            i_string = "The service bay is empty!";
        }
        return i_string;
    }
}