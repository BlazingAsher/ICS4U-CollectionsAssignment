import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Question4 {
    private static Map<String, LicensePlate> plateMap;
    public static void main(String[] args) throws IOException {

        Scanner inFile = new Scanner(new BufferedReader(new FileReader("cars.txt")));

        plateMap = new HashMap<>();

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

    public static void mainLoop(){
        Scanner in = new Scanner(System.in);
        Mode currentMode = Mode.NEXT;

        Pattern plateVerify = Pattern.compile("[A-Za-z]{4}\\s[0-9]{3}");

        while(currentMode != Mode.SAVE){
            switch (currentMode){
                case NEXT: {
                    System.out.print("Please enter an option: \n- SHOW all offences for a plate\n- ADD an offense to a plate\n- SAVE and Exit\n");
                    currentMode = Mode.valueOf(in.nextLine().toUpperCase());
                    break;
                }

                case SHOW: {
                    System.out.println("Please enter the license plate:");
                    String plate = in.nextLine();
                    if(plateVerify.matcher(plate).find()){
                        System.out.println(plateMap.getOrDefault(plate, new LicensePlate(plate)));
                        currentMode = Mode.NEXT;
                    }
                    else{
                        System.out.println("Please enter the plate in the following format: AAAA 000");
                    }

                    break;
                }

                case ADD: {
                    System.out.println("Please enter the license plate: ");
                    String plate = in.nextLine();
                    System.out.println("Please enter the date and time of the offense: ");
                    String dt = in.nextLine();
                    System.out.println("Please enter your initials: ");
                    String initials = in.nextLine();

                    LicensePlate curr = plateMap.getOrDefault(plate, new LicensePlate(plate));
                    curr.addOffense(new Offense(dt, initials));
                    plateMap.put(plate, curr);

                    currentMode = Mode.NEXT;
                    break;
                }

            }
        }
    }

    public static void saveData() throws IOException{
        FileWriter dataWrite = new FileWriter(new File("cars.txt"));
        dataWrite.write(""+plateMap.size()+"\n");
        for(String plateS : plateMap.keySet()){
            LicensePlate curr = plateMap.get(plateS);
            dataWrite.write(plateS + "\n");
            dataWrite.write(""+curr.numOffenses() + "\n");
            System.out.println(curr.numOffenses());
            for (Offense offense : curr.dumpOffenses()){
                dataWrite.write(offense.dt + "\n");
                dataWrite.write(offense.initials + "\n");
            }
        }
        dataWrite.close();
    }
}

class Offense {
    public final String dt;
    public final String initials;

    public Offense(String dt, String initials){
        this.dt = dt;
        this.initials = initials;
    }

    @Override
    public String toString() {
        return String.format("%s spotted by %s", dt, initials);
    }
}

class LicensePlate {
    public final String id;
    private final Set<Offense> offenseSet;
    public LicensePlate(String id) {
        this.id = id;
        offenseSet = new HashSet<>();
    }

    public void addOffense(Offense toAdd){
        offenseSet.add(toAdd);
    }

    public Set<Offense> dumpOffenses(){
        // return a deep-copied version to prevent internal list from being modified unintentionally
        return new HashSet<>(offenseSet);
    }

    public int numOffenses(){
        return offenseSet.size();
    }

    @Override
    public String toString() {
        StringBuilder temp = new StringBuilder(String.format("Plate %s has the following offenses: \n", this.id));
        for(Offense offense : offenseSet){
            temp.append(offense).append("\n");
        }
        return temp.toString();
    }
}

enum Mode {SHOW, ADD, NEXT, SAVE}