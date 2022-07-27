 
import structure5.*;
import java.util.*;
/**
 * This class represents one creature on the board.
 * Each creature must remember its species, position, direction,
 * and the world in which it is living.
 * <p>
 * In addition, the Creature must remember the next instruction
 * out of its program to execute.
 * <p>
 * The creature is also repsonsible for making itself appear in
 * the WorldMap.  In fact, you should only update the WorldMap from
 * inside the Creature class.  
 */
public class Creature {
    private World<Creature> theWorld;
    private Species myKind;
    private Position location;
    private int direction;
    private int pc;
    private static Random chaos = new Random();
    /**
     * Create a creature of the given species, with the indicated
     * position and direction.  Note that we also pass in the 
     * world-- remember this world, so that you can check what
     * is in front of the creature and to update the board
     * when the creature moves.
     */
    public Creature(Species species,
                    World<Creature> world,
                    Position pos,
                    int dir) {
        myKind = species;
        theWorld = world;
        location = pos;
        direction = dir;
        world.set(location,this);
        pc = 1;
        //      draw();
    }
    /**
     * Return the species of the creature.
     */
    public Species species() {
        return myKind;
    }
    /**
     * Return the current direction of the creature.
     */
    public int direction() {
        return direction;
    }
    /**
     * Return the position of the creature.
     */
    public Position position() {
        return location;
    }
    /**
     * Execute steps from the Creature’s program until 
     * a hop, left, right, or infect instruction is executed.
     */
    public void takeOneTurn() {
        Instruction i = null;
        int opcode;
        int address;
        Position ahead = null;
        boolean aheadValid = false;
        boolean done = false;
        int rounds = 0;
        while (!done) {
            rounds++;
            Assert.condition(rounds < 100,"Failed to leave loop in species"+species().g
etName());
            i = myKind.programStep(pc++);
            opcode = i.getOpcode();
            address = i.getAddress();
            switch (direction) {
                case Position.NORTH:
                    ahead = new Position(location.getX(),location.getY()-1); break;
                case Position.SOUTH:
                    ahead = new Position(location.getX(),location.getY()+1); break;
                case Position.EAST:
                    ahead = new Position(location.getX()+1,location.getY()); break;
                case Position.WEST:
                    ahead = new Position(location.getX()-1,location.getY()); break;
            default:
                Assert.fail("Bad direction.");
            }
            aheadValid = theWorld.inRange(ahead);
            switch (opcode) {
            case Instruction.GO:
                pc = address;
                break;
            case Instruction.LEFT:
                direction = leftFrom(direction);
                draw();
                done = true;
                break;
            case Instruction.RIGHT:
                direction = rightFrom(direction);
                draw();
                done = true;
                break;
            case Instruction.HOP:
                if (aheadValid && theWorld.get(ahead) == null) {
                    undraw();
                    theWorld.set(ahead,theWorld.get(location));
                    theWorld.set(location,null);
                    location = ahead;
                    draw();
                }
                done = true;
                break;
            case Instruction.IFWALL:
                if (!aheadValid) pc = address;
                break;
            case Instruction.IFEMPTY:
                if (aheadValid&& theWorld.get(ahead) == null) pc = address;
                break;
            case Instruction.IFSAME:
                if (aheadValid) {
                    Creature that = theWorld.get(ahead);
                    if (that != null && that.species() == this.species()) {
                        pc = address;
                    }
                }
                break;
            case Instruction.IFENEMY:
                if (aheadValid) {
                    Creature that = theWorld.get(ahead);
                    if (that != null && that.species() != this.species()) {
                        pc = address;
                    }
                }
                break;
            case Instruction.INFECT:
                if (aheadValid) {
                    Creature that = theWorld.get(ahead);
                    if (that != null && that.species() != this.species()) {
                        that.becomeSpecies(this.species(),address);
                    }
                }
                done = true;
                break;
            case Instruction.IFRANDOM:
                if (chaos.nextInt()%2 == 0) {
                    pc = address;
                }
                break;
            default: Assert.fail("Unimplemented instruction: "+i); break;
            }
        }
    }
    private void becomeSpecies(Species newKind, int newpc)
    {
        myKind = newKind;
        pc = newpc;
        draw();
    }
    private void undraw()
    {
        WorldMap.displaySquare(location,’ ’,direction,myKind.getColor());
    }
    private void draw()
    {
        WorldMap.displaySquare(location,myKind.getName().charAt(0),direction,myKind.get
Color());
    }
    /**
     * Return the compass direction the is 90 degrees left of
     * the one passed in.
     */
    public static int leftFrom(int direction) {
        Assert.pre(0 <= direction && direction < 4, "Bad direction");
        return (4 + direction - 1) % 4;
    }
    /**
     * Return the compass direction the is 90 degrees right of
     * the one passed in.
     */
    public static int rightFrom(int direction) {
        Assert.pre(0 <= direction && direction < 4, "Bad direction");
        return (direction + 1) % 4;
    }
    public String toString()
    {
        String pointing = "?";
        switch (direction) {
            case Position.NORTH: pointing = "north"; break;
            case Position.SOUTH: pointing = "south"; break;
            case Position.EAST: pointing = "east"; break;
            case Position.WEST: pointing = "west"; break;
        }
        return "<Creature: "+myKind.getName()+" @"+location+" pointing "+pointing+">";
    }
    /**
     */
    public static void main(String st[]) {
        Species s = new Species(st.length>0?st[0]:"Hop.txt");
        World<Creature> w = new World<Creature>(10,10);
        WorldMap.createWorldMap(w.width(),w.height());
        Creature c = new Creature(s,w,new Position(1,2),2);
        for (int i = 0; i < 10; i++) {
            c.takeOneTurn();
            WorldMap.pause(500);
        }
    }
}
