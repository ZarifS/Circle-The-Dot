
/**
 * The class <b>Point</b> is a simple helper class that stores a 2 dimensions element on a grid
 *
 * @author Zarif Shahriar
 */

public class Point {

    private int x;
    private int y;

    /**
     * Constructor
     *
     * @param x
     *            the x coordinate
     * @param y
     *            the y coordinate
     */
    public Point(int x, int y){
        this.x=x;
        this.y=y;
    }

    /**
     * Getter method for the attribute x.
     *
     * @return the value of the attribute x
     */
    public int getX(){
        return this.x;
    }

    /**
     * Getter method for the attribute y.
     *
     * @return the value of the attribute y
     */
    public int getY(){
        return this.y;
    }

    /**
     * Setter for x and y.
     * @param x
     *            the x coordinate
     * @param y
     *            the y coordinate
     */
    public void reset(int x, int y){
        this.x=x;
        this.y=y;
    }

}