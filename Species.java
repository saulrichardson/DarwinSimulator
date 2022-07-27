
import structure5.*;
import java.io.*;
import java.util.Scanner;
/** Holds shared info for a species (name, color, program). */
public class Species {
    protected Vector<Instruction> program;
    protected String name;
    protected String color;
    protected String comments;
    /** Build a species from a file. */
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
    /** Species name. */
    public String getName() {
        return name;
    }
    /** Species color. */
    public String getColor() {
        return color;
    }
    /** Program length. */
    public int programSize() {
        return program.size();
    }
    /** Get instruction i (1-based). */
    public Instruction programStep(int i) {
        Assert.pre(1 <= i && i <= programSize(), "Instruction within program.");
        return program.get(i-1);
    }
    /** Program as text. */
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
