import java.util.Scanner;
import dronePackage.*;

public class Main {

    // Command constants
    private static final String BASE = "base";
    private static final String LISTBASES = "listbases";
    private static final String DRONE = "drone";
    private static final String SERVICE = "service";
    private static final String SWARM = "swarm";
    private static final String SWARMCOMPONENTS = "swarmcomponents";
    private static final String DISBAND = "disband";
    private static final String LISTDRONES = "listdrones";
    private static final String FLYTOBASE = "flytobase";
    private static final String ADDORDER = "addorder";
    private static final String ORDER = "orders";
    private static final String ALLORDERS = "allorders";
    private static final String DELIVER = "deliver";
    private static final String DELIVERED = "delivered";
    private static final String INTRANSIT = "intransit";
    private static final String TICTAC = "tictac";
    private static final String EXIT = "exit";
    private static final String HELP = "help";

    // Help string
    private static final String HELPSTRING = "base - registers a new distribution base\nlistBases - lists existing distribution bases\ndrone - registers a new drone in a starting base\nservice - service all grounded drones requiring service in a base\nswarm - creates a new swarm from free drones in a base\nswarmComponents - lists the drones within a swarm\ndisband - disbands the whole swarm\nlistDrones - lists all existing drones (and swarms)\nflyToBase - fly a drone (or swarm) to a base\naddOrder - add a new order to a base\norders - lists all pending orders from a base\nallOrders - lists all pending orders from all bases\ndeliver - deliver a package to a customer\ndelivered - lists all delivered orders\ninTransit - lists all drones (and swarms) currently flying\nticTac - advances the simulation clock n timestamps\nhelp - shows the available commands\nexit - terminates the execution of the program";

    // Bye
    private static final String BYE = "Bye!";

    public static void main(String[] args) {
        Scanner l_scan = new Scanner(System.in);
        Tower l_tower = new TowerClass();
        switcher(l_scan, l_tower);
    }

    static String simplifyCommand(Scanner a_scan, String a_preCommand) {
        System.out.print(a_preCommand);
        return a_scan.nextLine().trim().toLowerCase();

    }

    static void switcher(Scanner a_scan, Tower a_tower) {
        String l_command = "";
        while (!l_command.equals(EXIT)) {

            l_command = simplifyCommand(a_scan, "> ");
            switch (l_command) {
            case BASE:
                baseFunction(a_scan, a_tower);
                break;
            case LISTBASES:
                listbasesFunction(a_scan, a_tower);
                break;
            case DRONE:
                droneFunction(a_scan, a_tower);
                break;
            case SERVICE:
                serviceFunction(a_scan, a_tower);
                break;
            case SWARM:
                swarmFunction(a_scan, a_tower);
                break;
            case SWARMCOMPONENTS:
                swarmcomponentsFunction(a_scan, a_tower);
                break;
            case DISBAND:
                disbandFunction(a_scan, a_tower);
                break;
            case LISTDRONES:
                listdronesFunction(a_scan, a_tower);
                break;
            case FLYTOBASE:
                flytobaseFunction(a_scan, a_tower);
                break;
            case ADDORDER:
                addorderFunction(a_scan, a_tower);
                break;
            case ORDER:
                orderFunction(a_scan, a_tower);
                break;
            case ALLORDERS:
                allordersFunction(a_scan, a_tower);
                break;
            case DELIVER:
                deliverFunction(a_scan, a_tower);
                break;
            case DELIVERED:
                deliveredFunction(a_scan, a_tower);
                break;
            case INTRANSIT:
                intransitFunction(a_scan, a_tower);
                break;
            case TICTAC:
                tictacFunction(a_scan, a_tower);
                break;
            case EXIT:
                exitFunction();
                break;
            case HELP:
                helpFunction();
                break;
            default:
                System.out.println("Unknown command. Type help to see available commands.");
            }
        }
    }

    public static void baseFunction(Scanner a_scan, Tower a_tower) {
        Location l_coords = new Location(a_scan.nextInt(), a_scan.nextInt());
        String l_baseName = a_scan.nextLine().trim();
        int l_Return = a_tower.createBase(l_coords, l_baseName);
        switch (l_Return) {
        case 0:
            System.out.println("Base " + l_baseName + " created at " + l_coords.prettyCoords() + ".");
            break;
        case 1:
            System.out.println("Base already exists!");
            break;
        case 2:
            System.out.println("There is already another base at " + l_coords.prettyCoords() + "!");
        }

    }

    public static void listbasesFunction(Scanner a_scan, Tower a_tower) {
        if ((a_tower.baseNames().length) == 0) {
            System.out.println("There are no bases!");
        }
        System.out.print(a_tower.listBases());
    }

    public static void droneFunction(Scanner a_scan, Tower a_tower) {
        String i_droneName = a_scan.nextLine();
        String i_baseName = a_scan.nextLine();
        int errorCode = a_tower.makeDrone(i_droneName, i_baseName, a_scan.next(), a_scan.nextInt(), a_scan.nextInt(),
                a_tower);
        a_scan.nextLine();
        switch (errorCode) {
        case 0:
            System.out.println("Drone " + i_droneName + " created.");
            break;
        case 1:
            System.out.println("Capacity has to be a positive integer!");
            break;
        case 2:
            System.out.println("Invalid range for a new drone!");
            break;
        case 3:
            System.out.println("Base " + i_baseName + " does not exist!");
            break;
        case 4:
            System.out.println("Drone " + i_droneName + " already exists!");
            break;
        case 5:
            System.out.println("Invalid drone type!");
            break;
        }
    }

    public static void serviceFunction(Scanner a_scan, Tower a_tower) {
        String a_baseName = a_scan.nextLine();
        int a_range = a_scan.nextInt();
        a_scan.nextLine();
        int l_Return = a_tower.moveToServiceBay(a_baseName, a_range);
        switch (l_Return) {
        case 0:
            Iterator l_ite = (Iterator)a_tower.extraError();
            l_ite.reset();
            while(l_ite.hasNext()){
                System.out.println(l_ite.next().getObjectID()+" moved to service bay.");
            }
            break;
        case 2:
            System.out.println("No drones were sent to the service station!");
            break;
        case 1:
            System.out.println("Base " + a_baseName + " does not exist!");
            break;
        }
    }

    public static void swarmFunction(Scanner a_scan, Tower a_tower) {
        /*
         * swarm Lajes swarm05 2 sociable02 sociable03
         */
        String l_base = a_scan.nextLine();
        String l_swarmId = a_scan.nextLine();
        int l_droneNum = a_scan.nextInt();
        a_scan.nextLine();
        String[] l_strArr = new String[l_droneNum];
        for (int i = 0; i < l_droneNum; i++) {
            l_strArr[i] = a_scan.nextLine();
        }
        int errorCode = a_tower.makeSwarm(l_base, l_swarmId, l_strArr);
        switch (errorCode) {
        case 0:

            System.out.println(l_swarmId + " created.");
            break;
        case 1:
            System.out.println("Base " + l_base + " does not exist!");
            break;
        case 2:
            System.out.println("Swarm must have at least two drones!");
            break;
        case 3:
            System.out.println("Cannot add drone " + ((String) a_tower.extraError()) + " twice!");
            break;
        case 4:
            System.out.println("Cannot add hermit drone " + a_tower.extraError() + "!");
            break;
        case 5:
            System.out.println("Drone " + a_tower.extraError() + " is not available in this base!");
            break;
        case 6:
            System.out.println("Swarm " + l_swarmId + " already exists!");
            break;
        }
    }

    public static void swarmcomponentsFunction(Scanner a_scan, Tower a_tower) {
        String l_droneName = a_scan.nextLine();
        Iterator l_drones = a_tower.exportIte("drones");
        DroneClass l_possibleSwarm = (DroneClass) l_drones.getElement(l_droneName);
        if (l_possibleSwarm == null) {
            System.out.println(l_droneName + " is not a swarm!");
        } else {
            if (l_possibleSwarm.droneType().equals("swarm")) {
                Iterator l_sdIte = ((Swarm) l_possibleSwarm).exportIte();
                l_sdIte.reset();
                while (l_sdIte.hasNext()) {
                    System.out.println(l_sdIte.next().getObjectID());
                }
                System.out.println(l_possibleSwarm.getCapacity() + " " + l_possibleSwarm.getFuel());
            }
        }
    }

    public static void disbandFunction(Scanner a_scan, Tower a_tower) {
        String l_baseId = a_scan.nextLine();
        String l_droneId = a_scan.nextLine();
        int l_errorCode = a_tower.disband(l_baseId, l_droneId);
        switch (l_errorCode) {
        case 0:
            System.out.println(l_droneId + " disbanded.");
            break;
        case 1:
            System.out.println("Base " + l_baseId + " does not exist!");
            break;
        case 2:
            System.out.println(l_droneId + " is not at " + l_baseId + "!");
            break;
        }

    }

    public static void listdronesFunction(Scanner a_scan, Tower a_tower) {
        Iterator l_droneIte = a_tower.exportIte("drones");
        l_droneIte.reset();
        if (l_droneIte.hasNext()) {
            while (l_droneIte.hasNext()) {
                System.out.println(l_droneIte.next().getObjectID());
            }
        } else {
            System.out.println("There are no drones!");
        }
    }

    public static void flytobaseFunction(Scanner a_scan, Tower a_tower) {
        String a_originBaseId = a_scan.nextLine();
        String a_droneId = a_scan.nextLine();
        String a_destinationId = a_scan.nextLine();
        int l_Result = a_tower.flyToBase(a_originBaseId, a_droneId, a_destinationId);
        switch (l_Result) {
        case 0:
            System.out.println(a_droneId + " flying from " + a_originBaseId + " to " + a_destinationId + ".");
            break;
        case 1:
            System.out.println("Source base " + a_originBaseId + " does not exist!");
            break;
        case 2:
            System.out.println("Target base " + a_destinationId + " does not exist!");
            break;
        case 3:
            System.out.println(a_droneId + " is not at " + a_originBaseId + "!");
            break;
        case 4:
            System.out.println("Drone " + a_droneId + " cannot reach " + a_destinationId + "!");
            break;
        }
    }

    public static void addorderFunction(Scanner a_scan, Tower a_tower) {
        String a_baseName = a_scan.nextLine();
        String a_orderId = a_scan.nextLine();
        int a_dimension = a_scan.nextInt();
        Location l_coords = new Location(a_scan.nextInt(), a_scan.nextInt());
        a_scan.nextLine();
        int l_Return = a_tower.addOrder(a_baseName, a_orderId, a_dimension, l_coords);
        switch (l_Return) {
        case 0:
            System.out.println("Order queued for delivery.");
            break;
        case 1:
            System.out.println("Source base " + a_baseName + " does not exist!");
            break;
        case 2:
            System.out.println("Order " + a_orderId + " already registered!");
            break;
        case 3:
            System.out.println("Order dimension must be a positive integer!");
            break;
        }
    }

    public static void orderFunction(Scanner a_scan, Tower a_tower) {
        String a_baseName = a_scan.nextLine();
        String l_toPrint = "";
        Iterator i_bases = a_tower.exportIte("bases");
        if (i_bases.IdExists(a_baseName) && ((Base) i_bases.getElement(a_baseName)).getOrderLength() > 0) {
            l_toPrint = ((Base) i_bases.getElement(a_baseName)).listOrders();
        } else if (i_bases.IdExists(a_baseName) && ((Base) i_bases.getElement(a_baseName)).getOrderLength() == 0) {
            l_toPrint = "There are no pending orders!";
        } else {
            l_toPrint = "Base " + a_baseName + " does not exist!";
        }
        System.out.print(l_toPrint);
    }

    public static void allordersFunction(Scanner a_scan, Tower a_tower) {
        Iterator l_bases = a_tower.exportIte("bases");
        Iterator l_orders = a_tower.exportIte("orders");
        String l_toPrint = "";
        if (!(l_orders.length() == 0)) {
            l_bases.reset();
            while (l_bases.hasNext()) {
                Base l_base = (Base) l_bases.next();
                if (l_base.getOrderLength() > 0)
                    l_toPrint += "Orders in " + l_base.getBaseName() + ":" + "\n";
                if (l_base.getOrderLength() > 0) {
                    l_toPrint += l_base.listOrders();
                } else {
                    l_toPrint += "There are no pending orders in " + l_base.getBaseName() + "." + "\n";
                }
            }
        } else {
            l_toPrint = "There are no pending orders!\n";
        }
        System.out.print(l_toPrint);
    }

    // Function takes in 3 arguments, origin base, drone id and order id, and sets a
    // delivery action on that "package"
    public static void deliverFunction(Scanner a_scan, Tower a_tower) {
        String a_originBaseId = a_scan.nextLine();
        String a_droneId = a_scan.nextLine();
        String a_orderId = a_scan.nextLine();
        int l_Result = a_tower.deliverOrders(a_originBaseId, a_droneId, a_orderId);
        switch (l_Result) {
        case 0:
            System.out.println(a_droneId + " will deliver " + a_orderId + ".");
            break;
        case 1:
            System.out.println("Base " + a_originBaseId + " does not exist!");
            break;
        case 2:
            System.out.println(a_droneId + " is not at " + a_originBaseId + "!");
            break;
        case 3:
            System.out.println(a_orderId + " is not pending!");
            break;
        case 4:
            System.out.println(a_orderId + " is too far for " + a_droneId + "!");
            break;
        case 5:
            System.out.println(a_orderId + " is too heavy for " + a_droneId + "!");
            break;
        }
    }

    public static void deliveredFunction(Scanner a_scan, Tower a_tower) {
    Iterator l_orderIte = a_tower.exportIte("doneOrders");
    l_orderIte.reset();
    if (l_orderIte.hasNext()) {
        while (l_orderIte.hasNext()) {
            OrderClass l_order = ((OrderClass) l_orderIte.next());
            System.out.println(l_order.getCompTime() + " " +  l_order.getI_orderID() + " " + l_order.getI_baseName() +  ".");
        } 
        } else {
        System.out.println("No orders delivered so far!");
    }

    }

    public static void intransitFunction(Scanner a_scan, Tower a_tower) {
        Iterator l_it = a_tower.inTransit();
        int l_tick = a_tower.getTick();
        l_it.reset();
        if (l_it.hasNext()) {
            while (l_it.hasNext()) {
                FlightPattern l_fp = (FlightPattern) l_it.next();
                System.out.println(l_fp.droneId() + " " + l_fp.getOrigin() + " " + l_fp.getDestination() + " "
                        + l_fp.coveredDistance(l_tick) + " " + l_fp.totalDistance() + " " + l_fp.type() + "!");
            }
        } else {
            System.out.println("No drones are flying!");
        }
    }

    // the fastest yikes in the west
    public static void tictacFunction(Scanner a_scan, Tower a_tower) {
        int l_delta = a_scan.nextInt();
        a_scan.nextLine();
        a_tower.tictac(l_delta);
    }

    public static void exitFunction() {
        System.out.println(BYE);
    }

    public static void helpFunction() {
        System.out.println(HELPSTRING);
    }

}