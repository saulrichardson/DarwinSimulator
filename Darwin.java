
import structure5.*;
import java.util.Random;
import java.io.*;
/** Runs the whole simulation. */
class Darwin {
    private static Random chaos = new Random();
    private static Vector<Species>  kinds;
    private static Vector<Creature> critters;
    private static World<Creature>  theWorld;
    private static Vector<Species>  types;
    private static int              critterCount = 0;
    private final static int  WIDTH = 15;
    private final static int  HEIGHT = WIDTH;
    /** Entry point. Pass creature files as args. */
    public static void main(String s[]) {
        theWorld = new World<Creature>(WIDTH,HEIGHT);
        WorldMap.createWorldMap(theWorld.width(),theWorld.height());
        kinds = new Vector<Species>();
        types = new Vector<Species>();
        critters = new Vector<Creature>();
        critterCount = 0;
        for (String creatureFile : s) {
            Species kind = new Species(creatureFile);
            kinds.add(kind);
            for (int i = 0; i < 10; i++) {
                Position location = null;
                do { 
                    location = new Position(chaos.nextInt(WIDTH),chaos.nextInt(HEIGHT))
;
                } while (theWorld.get(location) != null);
                int direction = chaos.nextInt(4);
                critters.add(new Creature(kind,theWorld,location,direction));
                critterCount++;
            }
        }
        for (int j = 0; j < critterCount; j++) {
            int k = chaos.nextInt(critterCount);
            Creature crit = critters.get(j);
            critters.set(j,critters.get(k));
            critters.set(k,crit);
        }
        int minTypes = kinds.size();
        do {
            types.clear();
            for (Creature c : critters) {
                c.takeOneTurn();
                Species sp = c.species();
                if (!types.contains(sp)) types.add(sp);
            }
            if (types.size() < minTypes) {
                minTypes = types.size();
                System.out.println("Now featuring "+minTypes+" species:");
                for (Species sp : types) {
                    System.out.println(" * "+sp.getName());
                }
                if (minTypes == 1) { System.out.println("(It’s gonna be boring.)");}
            }
            WorldMap.pause(50);
        } while (true);
    }
    public static void simulate() {
    }
}
