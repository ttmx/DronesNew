package dronePackage;

public class Hangar extends IteratorClass {
    void spawnDrone(String a_droneId, String a_droneType, int a_capacity, int a_range,Tower a_tower) {
        DroneClass l_drone = new DroneClass(a_droneId, a_droneType, a_capacity, a_range);
        addElement(l_drone);
        a_tower.addDrone(l_drone);

    }

    public boolean droneExists(String a_droneName) {
        return IdExists(a_droneName);
    }

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