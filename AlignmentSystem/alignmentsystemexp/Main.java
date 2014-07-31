/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alignmentsystemexp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 *
 * @author ckr
 */
public class Main {
    public static Map<CreatureType, Creature> typeToCre = new HashMap<CreatureType, Creature>();
    public static String workingDir = ""; // "/home/ckr/Desktop/";
    private static AppearanceStrategy appearStrategy;
    private static StatisticsStrategy statisticStrategy;
    private static RoomTypeStrategy roomTypeStrategy;

    public static void generateCreatures(double alignRad, double allieRad) throws IOException{
        PrintStream out = new PrintStream(new FileOutputStream(workingDir + "creatures.dat"));
        out.println("Faction   AE  OC  PV   HuA   CorA   ConA   UnA   ElA   MeA");
        // Faction base points
        Alignment[] alignStart = {
            new Alignment(0.0, 0.0, 0.05),
            new Alignment(0.5, 0.50, 0.65),
            new Alignment(0.1, -0.65, 0.65),
            new Alignment(-0.65, 0.50, 0.60),
            new Alignment(0.60, -0.20, -0.55),
            new Alignment(-0.50, -0.05, -0.65)};

        // Output files
        PrintStream[] alignFiles = {
            new PrintStream(new FileOutputStream(workingDir + "fa1.dat")),
            new PrintStream(new FileOutputStream(workingDir + "fa2.dat")),
            new PrintStream(new FileOutputStream(workingDir + "fa3.dat")),
            new PrintStream(new FileOutputStream(workingDir + "fa4.dat")),
            new PrintStream(new FileOutputStream(workingDir + "fa5.dat")),
            new PrintStream(new FileOutputStream(workingDir + "fa6.dat"))};

        // Faction base points
        Allies[] alliesStart = {
            new Allies(0.8, 0.0, 0.0, 0.0, 0.0, 0.0),
            new Allies(0.0, 0.8, 0.0, 0.0, 0.0, 0.0),
            new Allies(0.0, 0.0, 0.8, 0.0, 0.0, 0.0),
            new Allies(0.0, 0.0, 0.0, 0.8, 0.0, 0.0),
            new Allies(0.0, 0.0, 0.0, 0.0, 0.8, 0.0),
            new Allies(0.0, 0.0, 0.0, 0.0, 0.0, 0.8)};

        // Output files
        PrintStream[] alliesFiles = {
            new PrintStream(new FileOutputStream(workingDir + "ff1.dat")),
            new PrintStream(new FileOutputStream(workingDir + "ff2.dat")),
            new PrintStream(new FileOutputStream(workingDir + "ff3.dat")),
            new PrintStream(new FileOutputStream(workingDir + "ff4.dat")),
            new PrintStream(new FileOutputStream(workingDir + "ff5.dat")),
            new PrintStream(new FileOutputStream(workingDir + "ff6.dat"))};

        for(int i = 0; i < 6; i++){
            out.println(CreatureType.values()[i*20] + " " + alignStart[i] + " " + alliesStart[i]);
            alignFiles[i].println(alignStart[i]);
            alliesFiles[i].println(alliesStart[i]);
            for (int j = 0; j < 19; j++) {
                Alignment alignPoint = Alignment.forcePoint(Alignment.randPointInBall(alignRad, alignStart[i]));
                Allies alliesPoint = Allies.forcePoint(Allies.randPointInBall(allieRad, alliesStart[i]));
                out.println(CreatureType.values()[i*20+j+1] + " " + alignPoint + " " + alliesPoint);
                alignFiles[i].println(alignPoint);
                alliesFiles[i].println(alliesPoint);
            }
        }
    }

    public static Keeper loadFile(String filename, double evolveRate, double roomCreScale, int maxCreatures) throws FileNotFoundException, IOException{
        BufferedReader br = new BufferedReader(new FileReader(filename));
        List<Creature> creaturePool = new ArrayList<Creature>();
        br.readLine();
        String line = br.readLine();
        List<Room> basicRequirements = new LinkedList<Room>();
        basicRequirements.add(new Room(RoomType.Room1, new Dimension(3, 3)));
        basicRequirements.add(new Room(RoomType.Room2, new Dimension(3, 3)));
        basicRequirements.add(new Room(RoomType.Room3, new Dimension(3, 3)));
        while(line != null){
            StringTokenizer st = new StringTokenizer(line);
            String type = st.nextToken(),
                   ae = st.nextToken(),
                   oc = st.nextToken(),
                   pv = st.nextToken(),
                   hu = st.nextToken(),
                   cor = st.nextToken(),
                   con = st.nextToken(),
                   un = st.nextToken(),
                   el = st.nextToken(),
                   me = st.nextToken();
            Creature c = new Creature(appearStrategy, CreatureType.valueOf(type),
                    Double.parseDouble(hu),
                    Double.parseDouble(cor),
                    Double.parseDouble(con),
                    Double.parseDouble(un),
                    Double.parseDouble(el),
                    Double.parseDouble(me),
                    Double.parseDouble(ae),
                    Double.parseDouble(oc),
                    Double.parseDouble(pv),
                    Faction.valueOf(type.substring(0, 3)),
                    basicRequirements);
            creaturePool.add(c);
            typeToCre.put(CreatureType.valueOf(type), c);
            line = br.readLine();
        }
        return new Keeper(statisticStrategy, roomTypeStrategy, creaturePool, new ArrayList<Creature>(),
                new ArrayList<Room>(), new Allies(), new Alignment(), evolveRate, roomCreScale , maxCreatures, 1);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO be able to generate som start data
        // steepness (sigma), radius (rho) and mix (mu)
        appearStrategy = new SvenskmandAppearance(30.0, 3.0, 0.2);
        statisticStrategy = new SvenskmandStatistics();
        roomTypeStrategy = new SvenskmandRoomTypes();
        if(args.length > 0 && args[0].equals("-generate")){
            //generateCreatures(0.9, 0.4);
            try{
                generateCreatures(Double.parseDouble(args[1]), Double.parseDouble(args[2]));
            }catch(Exception e){
                System.out.println("Usage is: -generate alignradius alliesradius");
            }
        }else{
            testInterActive();
        }
    }

    public static void testInterActive() throws FileNotFoundException, IOException{
        PrintStream log = new PrintStream(new FileOutputStream(workingDir + "log.txt"));
        PrintStream walk = new PrintStream(new FileOutputStream(workingDir + "walk.dat"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Input maximal number of creatures the keeper can have (e.g. 20)");
        log.println("Input maximal number of creatures the keeper can have (e.g. 20)");
        String input = br.readLine();
        log.println(input);
        int maxCreatures = Integer.parseInt(input);

        Keeper keeper = loadFile(workingDir + "creatures.dat", 1.0, 1.0, maxCreatures);

        System.out.println("Input starting alignment (e.g. 0.0 0.0 0.0)");
        log.println("Input starting alignment (e.g. 0.0 0.0 0.0)");
        input = br.readLine();
        log.println(input);
        StringTokenizer st = new StringTokenizer(input);
        keeper.setAlignment(new Alignment(
                Double.parseDouble(st.nextToken()),
                Double.parseDouble(st.nextToken()),
                Double.parseDouble(st.nextToken())));
        walk.println(keeper.getAlignment());

        System.out.println("Input evolve rate: 0.0 (no evolution) to 1.0 (total change) (e.g. 0.05)");
        log.println("Input evolve rate: 0.0 (no evolution) to 1.0 (total change) (e.g. 0.05)");
        input = br.readLine();
        log.println(input);
        keeper.setEvolveRate(Double.parseDouble(input));

        System.out.println("Input creature vs. room scale influence on the keeper alignment:\n"
                + "0.0 (only rooms) to 1.0 (only creatures) (e.g. 0.2)");
        log.println("Input creature vs. room scale influence on the keeper alignment:\n"
                + "0.0 (only rooms) to 1.0 (only creatures) (e.g. 0.2)");
        input = br.readLine();
        log.println(input);
        keeper.setRoomCreScale(Double.parseDouble(input));
        
        int i = 1;
        while (true) {
            System.out.println("*******************************************************************************");
            System.out.println("                                 Round " + i++);
            System.out.println("*******************************************************************************");
            System.out.println("Creatures before: " + keeper.printCreatures());
            log.println("*******************************************************************************");
            log.println("                                 Round " + i++);
            log.println("*******************************************************************************");
            log.println("Creatures before: " + keeper.printCreatures());
            if(keeper.getCreatures().size() < maxCreatures){
                keeper.waitForCreature();
                keeper.updateStatistics();
            }
            walk.println(keeper.getAlignment());
            System.out.println("Current allignment: " + keeper.getAlignment());
            System.out.println("Current factional mix: " + keeper.getAllies());
            System.out.println("Creatures after: " + keeper.printCreatures());
            System.out.println("Current rooms: " + keeper.printRooms());
            System.out.println("Select creature to evict: e.g.: 1 2 for the first two etc.\n"
                    + "(hit enter to select none)");
            log.println("Current allignment: " + keeper.getAlignment());
            log.println("Current factional mix: " + keeper.getAllies());
            log.println("Creatures after: " + keeper.printCreatures());
            log.println("Current rooms: " + keeper.printRooms());
            log.println("Select creature to evict: e.g.: 1 2 for the first two etc.\n"
                    + "(hit enter to select none)");
            input = br.readLine();
            log.println(input);
            st = new StringTokenizer(input);
            List<Integer> removed = new LinkedList<Integer>();
            int totalRemoved = 0;
            while(st.hasMoreTokens()){
                int remove = Integer.parseInt(st.nextToken());
                removed.add(remove);
                keeper.getCreatures().remove(remove-totalRemoved-1);
                totalRemoved++;
            }

            System.out.println("Select rooms to build: e.g.: Room1 3 5 Room2 5 5 ...  (hit enter to select none)");
            log.println("Select rooms to build: e.g.: Room1 3 5 Room2 5 5 ...  (hit enter to select none)");
            input = br.readLine();
            log.println(input);
            st = new StringTokenizer(input);
            while(st.hasMoreTokens()){
                RoomType type = RoomType.valueOf(st.nextToken());
                int height = Integer.parseInt(st.nextToken());
                int width = Integer.parseInt(st.nextToken());
                // The rooms is available
                if(keeper.getAvailableRoomTypes().contains(type)){
                    keeper.getRooms().add(new Room(type, new Dimension(height, width)));
                }else{ // The room is not available
                    System.out.println("The room: " + type + " is not available to the keeper!");
                    log.println("The room: " + type + " is not available to the keeper!");
                }
            }
            keeper.updateStatistics();
            walk.println(keeper.getAlignment());
        }
    }
}
