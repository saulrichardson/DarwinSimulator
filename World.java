

import structure5.*;
/** Simple 2-D grid of objects. */
public class World<E> { 
    private Matrix<E> mir;
    /** Make an empty w×h grid. */
    public World(int w, int h) {
        mir = new Matrix<E>(w,h);
    }
    /** Grid height. */
    public int height() {
        return mir.height();
    }
    /** Grid width. */
    public int width() {
        return mir.width();
    }
    /** True if pos is on the grid. */
    public boolean inRange(Position pos) {
        return 0 <= pos.getY() && pos.getY() < height() &&
            0 <= pos.getX() && pos.getX() < width();
    }
    /** Put c at pos. */
    public void set(Position pos, E c) {
        mir.set(pos.getY(),pos.getX(),c);
    }
    /** Get what’s at pos. */
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
