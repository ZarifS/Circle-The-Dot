import java.util.Random;

/**
 * The class <b>GameModel</b> holds the model, the state of the systems.
 * It stores the followiung information:
 * - the current location of the blue dot
 * - the state of all the dots on the board (available, selected or
 *  occupied by the blue dot
 * - the size of the board
 * - the number of steps since the last reset
 *
 * The model provides all of this informations to the other classes trough
 *  appropriate Getters.
 * The controller can also update the model through Setters.
 * Finally, the model is also in charge of initializing the game
 *
 * @author Zarif Shahriar 
 */
public class GameModel {


    /**
     * predefined values to capture the state of a point
     */
    public static final int AVAILABLE 	= 0;
    public static final int SELECTED 	= 1;
    public static final int DOT 		= 2;

// ADD YOUR INSTANCE VARIABLES HERE

    private int sizeOfGame;
    private int steps;
    private int status[][];
    private Point blueDot;

    /**
     * Constructor to initialize the model to a given size of board.
     *
     * @param size
     *            the size of the board
     */
    public GameModel(int size) {
        sizeOfGame=size;
        status = new int[size][size];
        }



    /**
     * Resets the model to (re)start a game. The previous game (if there is one)
     * is cleared up . The blue dot is positioned as per instructions, and each
     * dot of the board is either AVAILABLE, or SELECTED (with
     * a probability 1/INITIAL_PROBA). The number of steps is reset.
     */
    public void reset(){
        initializeGame();
        initializeRandomDots();
        initializeBlueDot();
        steps=0;
    }

    /**
     * generates a random number between 0 and n.
     * @param n
     *            the integer n.*
     * @return the random number generated.
     */
    private int random(int n){
        Random ran = new Random();
        return (ran.nextInt(n));
    }

	/**
     * Initializes Game as per instructions.
     */
    private void initializeGame(){
        for (int i=0;i<sizeOfGame;i++) {
            for (int j = 0; j < sizeOfGame; j++) {
                status[i][j] = AVAILABLE;
            }
        }
    }

    /**
     * Initializes BlueDot as per instructions.
     */
    private void initializeBlueDot(){
        int x,y,a,b;
        do {
            if (sizeOfGame % 2 == 0) {
                //x coordinate randomly chosen
                a = random(2);
                if (a == 0) {
                    x = sizeOfGame / 2;
                } else {
                    x = (sizeOfGame / 2) - 1;
                }

                //y coordinate randomly chosen
                b = random(2);
                if (b == 0) {
                    y = sizeOfGame / 2;
                } else {
                    y = (sizeOfGame / 2) - 1;
                }
            } else {
                //x coordinate randomly chosen
                a = random(3);
                if (a == 0) {
                    x = sizeOfGame / 2;
                } else if (a == 1) {
                    x = (sizeOfGame / 2) + 1;
                } else {
                    x = (sizeOfGame / 2) - 1;
                }

                //y coord randomly chosen
                b = random(3);
                if (b == 0) {
                    y = sizeOfGame / 2;
                } else if (b == 1) {
                    y = (sizeOfGame / 2) + 1;
                } else {
                    y = (sizeOfGame / 2) - 1;
                }
            }
        } while(status[x][y]==1);

        setCurrentDot(x,y);
    }

    /**
     * Initializes random dots as per instructions.
     */
    private void initializeRandomDots(){
        int x=random(10);
        for(int i=0;i<status.length;i++){
            for (int j=0;j<status.length;j++){
                int y=random(10);
                if(y==x){
                    select(i,j);
                }
            }
        }
    }


    /**
     * Getter <b>class</b> method for the size of the game
     *
     * @return the value of the attribute sizeOfGame
     */
    public  int getSize(){
        return sizeOfGame;
    }

    /**
     * returns the current status (AVAILABLE, SELECTED or DOT) of a given dot in the game
     *
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */
    public int getCurrentStatus(int i, int j){
        return status[i][j];

    }


    /**
     * Sets the status of the dot at coordinate (i,j) to SELECTED, and
     * increases the number of steps by one
     *
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     */
    public void select(int i, int j){
        status[i][j]=SELECTED;
        steps++;
    }

    /**
     * Puts the blue dot at coordinate (i,j). Clears the previous location
     * of the blue dot. If the i coordinate is "-1", it means that the blue
     * dot exits the board (the player lost)
     *
     * @param i
     *            the new x coordinate of the blue dot
     * @param j
     *            the new y coordinate of the blue dot
     */
    public void setCurrentDot(int i, int j){
        for(int a=0; a<status.length;a++){
            for(int b=0;b<status.length;b++){
                if(getCurrentStatus(a,b)==DOT){
                    status[a][b]=0;}
            }
        }
        status[i][j]=DOT;
        blueDot=new Point(i,j);
    }

    /**
     * Getter method for the current blue dot
     *
     * @return the location of the curent blue dot
     */
    public Point getCurrentDot(){
        return blueDot;
    }

    /**
     * Getter method for the current number of steps
     *
     * @return the current number of steps
     */
    public int getNumberOfSteps(){
        return steps;
    }
}
