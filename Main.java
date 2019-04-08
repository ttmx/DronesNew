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
    private static final String ORDER = "order";
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
                swarmFunction(a_scan);
                break;
            case SWARMCOMPONENTS:
                swarmcomponentsFunction(a_scan);
                break;
            case DISBAND:
                disbandFunction(a_scan);
                break;
            case LISTDRONES:
                listdronesFunction(a_scan);
                break;
            case FLYTOBASE:
                flytobaseFunction(a_scan);
                break;
            case ADDORDER:
                addorderFunction(a_scan);
                break;
            case ORDER:
                orderFunction(a_scan);
                break;
            case ALLORDERS:
                allordersFunction(a_scan);
                break;
            case DELIVER:
                deliverFunction(a_scan);
                break;
            case DELIVERED:
                deliveredFunction(a_scan);
                break;
            case INTRANSIT:
                intransitFunction(a_scan);
                break;
            case TICTAC:
                tictacFunction(a_scan);
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
        int errorCode = a_tower.makeDrone(i_droneName, i_baseName, a_scan.next(), a_scan.nextInt(), a_scan.nextInt(),a_tower);
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
    switch(l_Return) {
    case 0:
        System.out.println();        
        break;
    case 1:
        System.out.println("No drones were sent to the service station!");
        break;
    case 2:
        System.out.println("Base " + a_baseName + " does not exist!");
        break;   
    }
    }

    public static void swarmFunction(Scanner a_scan) {

    }

    public static void swarmcomponentsFunction(Scanner a_scan) {

    }

    public static void disbandFunction(Scanner a_scan) {

    }

    public static void listdronesFunction(Scanner a_scan) {

    }

    public static void flytobaseFunction(Scanner a_scan) {

    }

    public static void addorderFunction(Scanner a_scan) {

    }

    public static void orderFunction(Scanner a_scan) {

    }

    public static void allordersFunction(Scanner a_scan) {

    }

    public static void deliverFunction(Scanner a_scan) {

    }

    public static void deliveredFunction(Scanner a_scan) {

    }

    public static void intransitFunction(Scanner a_scan) {

    }

    public static void tictacFunction(Scanner a_scan) {

    }

    public static void exitFunction() {
        System.out.println(BYE);
    }

    public static void helpFunction() {
        System.out.println(HELPSTRING);
    }

}