/*
 * File: Question4.java
 * Author: David Hui
 * Description: Allows a user to view and add offenses to a license plate database
 */
import java.io.*;
import java.util.*;

public class Question4 {
    private static Map<String, LicensePlate> plateMap; // maps license plates to their respective objects
    private static boolean changedData = false; // keep track of whether we actually need to save the file
    public static void main(String[] args) throws IOException {

        Scanner inFile = new Scanner(new BufferedReader(new FileReader("cars.txt")));

        plateMap = new HashMap<>();

        // build our internal memory of all the license plates and their offenses
        int numCars = Integer.parseInt(inFile.nextLine());
        for(int i = 0; i<numCars;i++){
            LicensePlate curr = new LicensePlate(inFile.nextLine());
            int numOffenses = Integer.parseInt(inFile.nextLine());
            for(int j = 0;j<numOffenses;j++){
                String tDT = inFile.nextLine();
                String tInitials = inFile.nextLine();
                curr.addOffense(new Offense(tDT, tInitials));
            }
            plateMap.put(curr.id, curr);
        }

        mainLoop();

        saveData();
    }

    /**
     * The main loop of the program that handles command processing
     */
    public static void mainLoop(){
        Scanner in = new Scanner(System.in);
        Mode currentMode = Mode.MENU; // set the default mode to the menu

        // keep going until we are asked to exit
        while(currentMode != Mode.SAVE){
            switch (currentMode){
                case MENU: {
                    System.out.print("Please enter an option: \n- SHOW all offences for a plate\n- ADD an offense to a plate\n- SAVE and Exit\n> ");
                    currentMode = Mode.valueOf(in.nextLine().toUpperCase());
                    break;
                }
                case SHOW: {
                    System.out.println("Please enter the license plate:");
                    String plate = in.nextLine().toUpperCase();

                    // try to retrieve a plate object, otherwise return a blank plate
                    System.out.println(plateMap.getOrDefault(plate, new LicensePlate(plate)));
                    currentMode = Mode.MENU;

                    break;
                }
                case ADD: {
                    // request plate info
                    System.out.println("Please enter the license plate: ");
                    String plate = in.nextLine().toUpperCase();
                    System.out.println("Please enter the date and time of the offense: ");
                    String dt = in.nextLine();
                    System.out.println("Please enter your initials: ");
                    String initials = in.nextLine();

                    // try to retrieve an existing plate, otherwise make a new one
                    LicensePlate curr = plateMap.getOrDefault(plate, new LicensePlate(plate));
                    curr.addOffense(new Offense(dt, initials));
                    plateMap.put(plate, curr);

                    // mark file as need to be saved
                    changedData = true;

                    currentMode = Mode.MENU;

                    break;
                }

            }
        }
    }

    public static void saveData() throws IOException{
        // no need to write to the file if we haven't changed anything
        if(!changedData){
            System.out.println("No modifications were made to the database.");
            return;
        }

        System.out.println("Saving the database.");

        // initialize a PrintStream and write the data
        PrintStream dataWrite = new PrintStream(new File("cars.txt"));
        dataWrite.println(plateMap.size());
        for(String plateS : plateMap.keySet()){
            LicensePlate curr = plateMap.get(plateS);
            dataWrite.println(plateS);
            dataWrite.println(curr.numOffenses());
            for (Offense offense : curr.dumpOffenses()){
                dataWrite.println(offense.dt);
                dataWrite.println(offense.initials);
            }
        }
        dataWrite.close();
    }
}

// class to represent an offense
class Offense {
    public final String dt; // date and time of the offense
    public final String initials; // initials of the teacher

    public Offense(String dt, String initials){
        this.dt = dt;
        this.initials = initials;
    }

    @Override
    public String toString() {
        return String.format("%s spotted by %s", dt, initials);
    }
}

// class to represent a license plate and its Offenses
class LicensePlate {
    public final String id; // licence plate id
    private final Set<Offense> offenseSet; // set of offenses
    public LicensePlate(String id) {
        this.id = id;
        offenseSet = new HashSet<>();
    }

    /**
     * Adds an Offense to the LicensePlate
     * @param toAdd the Offense to add
     */
    public void addOffense(Offense toAdd){
        offenseSet.add(toAdd);
    }

    /**
     * Returns a Set of Offenses registered to the plate
     * @return a Set of Offenses registered to the plate
     */
    public Set<Offense> dumpOffenses(){
        // return a deep-copied version to prevent internal list from being modified unintentionally
        return new HashSet<>(offenseSet);
    }

    /**
     * Returns the number of Offenses registered to the plate
     * @return the number of Offenses registered to the plate
     */
    public int numOffenses(){
        return offenseSet.size();
    }

    @Override
    public String toString() {
        // format the license plate offenses in a list
        StringBuilder temp = new StringBuilder(String.format("Plate %s has the following offenses: \n", this.id));
        for(Offense offense : offenseSet){
            temp.append(offense).append("\n");
        }
        return temp.toString();
    }
}

// enum for our program modes
enum Mode {SHOW, ADD, MENU, SAVE}