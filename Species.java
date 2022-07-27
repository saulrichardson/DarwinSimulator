
import structure5.*;
import java.io.*;
import java.util.Scanner;
/**
 * The individual creatures in the world are all representatives of
 * some species class and share certain common characteristics, such
 * as the species name and the program they execute.  Rather than copy
 * this information into each creature, this data can be recorded once
 * as part of the description for a species and then each creature can
 * simply include the appropriate species pointer as part of its
 * internal data structure.
 * <p>
 * 
 * To encapsulate all of the operations operating on a species within
 * this abstraction, this provides a constructor that will read a file
 * containing the name of the creature and its program, as described
 * in the earlier part of this assignment.  To make the folder
 * structure more manageable, the species files for each creature are
 * stored in a subfolder named Creatures.  This, creating the Species
 * for the file Hop.txt will causes the program to read in
 * "Creatures/Hop.txt".
 * 
 * <p>
 *
 * Note: The instruction addresses start at one, not zero.
 */
public class Species {
    protected Vector<Instruction> program;
    protected String name;
    protected String color;
    protected String comments;
    /**
     * Create a species for the given file.
     * @pre fileName exists in the Creature subdirectory.
     */
    public Species(String fileName)  {
        Scanner input = 
            new Scanner(new FileStream("Creatures" + 
                                       java.io.File.separator + 
                                       fileName));
        Assert.condition(input.hasNext(),"Has a creature name.");
        name = input.next();
        Assert.condition(input.hasNext(),"Has a color.");
        color = input.next();
        Scanner programInput = Compiler.rewrite(input,Compiler.DarwinKeywords,".");
        comments = "";
        program = new Vector<Instruction>();
        while (true) {
            String operation;
            Assert.condition(programInput.hasNext(),
                             "A statement found in creature "+name);
            operation = programInput.next();
            int address = program.size()+1;
            if (operation.equals(".")) break;
            if (operation.equals("hop")) {
                program.add(new Instruction(Instruction.HOP,address));
            } else if (operation.equals("left")) {
                program.add(new Instruction(Instruction.LEFT,address));
            } else if (operation.equals("right")) {
                program.add(new Instruction(Instruction.RIGHT,address));
            } else if (operation.equals("go")) {
                address = programInput.nextInt();
                program.add(new Instruction(Instruction.GO,address));
            } else if (operation.equals("infect")) {
                if (programInput.hasNextInt()) address = programInput.nextInt();
                else address = 1;
                program.add(new Instruction(Instruction.INFECT,address));
            } else if (operation.equals("ifempty")) {
                address = programInput.nextInt();
                program.add(new Instruction(Instruction.IFEMPTY,address));
            } else if (operation.equals("ifwall")) {
                address = programInput.nextInt();
                program.add(new Instruction(Instruction.IFWALL,address));
            } else if (operation.equals("ifsame")) {
                address = programInput.nextInt();
                program.add(new Instruction(Instruction.IFSAME,address));
            } else if (operation.equals("ifenemy")) {
                address = programInput.nextInt();
                program.add(new Instruction(Instruction.IFENEMY,address));
            } else if (operation.equals("ifrandom")) {
                address = programInput.nextInt();
                program.add(new Instruction(Instruction.IFRANDOM,address));
            } else Assert.fail("Unrecognized opcode: "+operation);
        }
        if (input.hasNextLine()) input.nextLine();
        while (input.hasNextLine()) comments += input.nextLine()+"\n";
    }
    /**
     * Return the name of the species.
     */
    public String getName() {
        return name;
    }
    /**
     * Return the color of the species.
     */
    public String getColor() {
        return color;
    }
    /**
     * Return the number of instructions in the program.
     */
    public int programSize() {
        return program.size();
    }
    /**
     * Return an instruction from the program.
     * @pre  1 <= i <= programSize().
     * @post returns instruction i of the program.
     */
    public Instruction programStep(int i) {
        Assert.pre(1 <= i && i <= programSize(), "Instruction within program.");
        return program.get(i-1);
    }
    /**
     * Return a String representation of the program.
     */
    public String programToString() {
        String s = "";
        for (int i = 1; i <= programSize(); i++) {
            s = s + (i) + ": " + programStep(i) + "\n";

        }
        return s;
    }
    public String toString()
    {
        String result = "Name: "+getName()+"\n";
        result += "Color: "+getColor()+"\n";
        result += "Comments:\n"+comments;
        result += "Program:\n"+programToString();
        return result;
    }
    
    public static void main(String s[]) {
        Species sp = new Species(s.length > 0 ? s[0] : "Hop.txt");
        System.out.println(sp);
    }
}
