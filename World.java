

import structure5.*;
/**
 * This module includes the functions necessary to keep track of the
 * creatures in a two-dimensional world. In order for the design to be
 * general, the interface adopts the following design: <p>
 * 1. The contents have generic type E.  <p>
 * 2. The dimensions of the world array are specified by the client. <p>
 * There are many ways to implement this structure.  HINT: 
 * look at the structure.Matrix class.  You should need to add no more than
 * about ten lines of code to this file.
 */
public class World<E> { 
    private Matrix<E> mir;
    /**
     * This function creates a new world consisting of width columns 
     * and height rows, each of which is numbered beginning at 0. 
     * A newly created world contains no objects. 
     */
    public World(int w, int h) {
        mir = new Matrix<E>(w,h);
    }
    /**
     * Returns the height of the world.
     */
    public int height() {
        return mir.height();
    }
    /**
     * Returns the width of the world.
     */
    public int width() {
        return mir.width();
    }
    /**
     * Returns whether pos is in the world or not.
     * @pre  pos is a non-null position.
     * @post returns true if pos is an (x,y) location in 
     *       the bounds of the board.
     */
    public boolean inRange(Position pos) {
        return 0 <= pos.getY() && pos.getY() < height() &&
            0 <= pos.getX() && pos.getX() < width();
    }
    /**
     * Set a position on the board to contain c.
     * @pre  pos is a non-null position on the board.
     */
    public void set(Position pos, E c) {
        mir.set(pos.getY(),pos.getX(),c);
    }
    /**
     * Return the contents of a position on the board.
     * @pre  pos is a non-null position on the board.
     */
    public E get(Position pos) {
        return mir.get(pos.getY(),pos.getX());
    }
    public String toString()
    {
        String s = "<World:\n";
        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                s += get(new Position(x,y))+" ";
            }
            s += "\n";
        }
        s += ">";
        return s;
    }
    public static void main(String s[]) {
        World<String> w = new World<String>(4,3);
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 4; col++) {
                Position p = new Position(row, col);
                w.set(p,p.toString());
            }
        }
        System.out.println(w);
    }
        
}
