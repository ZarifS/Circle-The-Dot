import java.util.LinkedList;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The class <b>GameController</b> is the controller of the game. It implements 
 * the interface ActionListener to be called back when the player makes a move. It computes
 * the next step of the game, and then updates model and view.
 *
 * @author Zarif Shahriar
 */


public class GameController implements ActionListener {

    private GameModel model;
    private GameView view;
    private Point[][] targets;
    private Point[][] selected;

    /**
     * Constructor used for initializing the controller. It creates the game's view 
     * and the game's model instances
     *
     * @param size
     *            the size of the board on which the game will be played
     */
    public GameController(int size) {
        model = new GameModel(size);
        view = new GameView(model,this);
    }


    /**
     * Starts the game
     */
    public void start(){
        model.reset();
        view.getBoardView().update();
    }


    /**
     * resets the game
     */
    public void reset(){
        model.reset();
        view.getBoardView().update();
    }

    /**
     * Callback used when the user clicks a button or one of the dots. 
     * Implements the logic of the game
     *
     * @param e
     *            the ActionEvent
     */

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == view.quit){
            System.exit(0);
        }
        if(e.getSource() == view.reset){
            reset();
        }
        if(e.getSource() instanceof DotButton){
           DotButton button = (DotButton)e.getSource();
           model.select(button.getRow(),button.getColumn());
           Point start = model.getCurrentDot();
            getSelected();
            getTargets();
            LinkedList<Point> newPath =breadthFirstSearch(start);
            if(newPath==null){
                win();
            }
            else {
                Point point = newPath.get(1);
                model.setCurrentDot(point.getX(), point.getY());
                view.getBoardView().update();
                if (inTarget(point)) {
                    lost();
                }
            }
        }
    }

    /**
     * If the player lost it displays the corresponding JOption Pane.
     */
    private void lost(){
        Object[] options = { "Quit", "Play Again" };
        int selection = JOptionPane.showOptionDialog(null,
                "You lost! Would you like to play again?",
                "Quit?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null,options,options[0]);


        if (selection == JOptionPane.YES_NO_OPTION)
        {
            System.exit(0);
        }

        else{
            this.reset();
        }
    }

    /**
     * If the player wins it displays the corresponding JOption Pane.
     */
    private void win(){
        Object[] options = { "Quit", "Play Again" };
        int selection = JOptionPane.showOptionDialog(null,
                "Congratulations! You won in "+model.getNumberOfSteps()+" steps!\nWould you like to play again?",
                "Quit?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);


        if (selection == JOptionPane.YES_NO_OPTION)
        {
            System.exit(0);
        }

        else{
            this.reset();
        }
    }


    /**
     * Gets all target dots and stores some in an array.
     */
    private void getTargets(){
        targets=new Point[model.getSize()][model.getSize()];
        int i=0;
        int j=0;
        //adds top border to targets
        do{
            targets[i][j] = new Point(i,j);
            j++;
        }while(j<model.getSize());

        //adds bottom border to targets
        i=(model.getSize()-1);
        j=0;
        do{
            targets[i][j] = new Point(i,j);
            j++;
        }while(j<model.getSize());

        //adds left border to targets
        j=0;
        i=0;
        do{
            targets[i][j] = new Point(i,j);
            i++;
        }while(i<model.getSize());

        //adds right border to targets
        j=model.getSize()-1;
        i=0;
        do{
            targets[i][j] = new Point(i,j);
            i++;
        }while(i<model.getSize());
    }

    /**
     * Gets all selected dots and stores them in an array.
     */
    private void getSelected(){
        selected=new Point[model.getSize()][model.getSize()];
        for(int i=0;i<model.getSize();i++){
            for(int j=0;j<model.getSize();j++){
                if(model.getCurrentStatus(i,j)==1){
                    selected[i][j]=new Point(i,j);
                }
            }
        }
    }

   
   /**
     * Checks if the neighbouring dot is blocked.
     * @param a
     *            the Point a which represents the current blueDot.
     * @return the boolean value true or false.
     */
    private boolean inBlocked(Point a){
        int x=a.getX();
        int y= a.getY();
        for(int i=0;i<model.getSize();i++){
            for (int j = 0; j < model.getSize(); j++) {
                if (selected[i][j] != null) {
                    if ((selected[i][j].getX() == x) && (selected[i][j].getY() == y)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Checks if the neighbouring dot is one of the target dots.
     * @param a
     *            a Point that is being checked.
     * @return the boolean value true or false.
     */
    private boolean inTarget(Point a){
        int x =a.getX();
        int y= a.getY();
        for(int i=0;i<model.getSize();i++){
            for (int j = 0; j < model.getSize(); j++) {
                if (targets[i][j] != null) {
                    if ((targets[i][j].getX() == x) && (targets[i][j].getY() == y)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * stores the coordinates of the point in the 2d array selected.
     * @param a
     *            a Point that is being checked.
     */
    private void addInSelected(Point a)
    {
        int i = a.getX();
        int j= a.getY();
        selected[i][j]=a;

    }


    /**
     * Method used to determine the shortest path to the border of the grid. Using the breadthFirstSearch algorithm.
     * @param start
     *            represents the blueDot.
     * @return null or a linkedlist of q.
     */
    private LinkedList<Point> breadthFirstSearch(Point start){
        int x,y;
        LinkedList<LinkedList<Point>> queue=new LinkedList<LinkedList<Point>>();
        LinkedList<Point> path= new LinkedList<Point>();
        LinkedList<Point> q;
        path.addLast(start);
        queue.addLast(path);

        Point c;
        Point[] p=new Point[6];
        while(!queue.isEmpty()){
            q=queue.removeFirst();
            c=q.getLast();
            x = c.getX();
            y = c.getY();
            if (c.getX() % 2 == 0) {
                p[0] = new Point(x, y + 1);
                p[1] = new Point(x, y - 1);
                p[2] = new Point(x - 1, y);
                p[3] = new Point(x - 1, y - 1);
                p[4] = new Point(x + 1, y);
                p[5] = new Point(x + 1, y - 1);
            } else {
                p[0] = new Point(x, y + 1);
                p[1] = new Point(x, y - 1);
                p[2] = new Point(x - 1, y);
                p[3] = new Point(x - 1, y + 1);
                p[4] = new Point(x + 1, y);
                p[5] = new Point(x + 1, y + 1);
            }
            for (int i = 0; i < 6; i++) {
                if (!inBlocked(p[i])) {
                    if (inTarget(p[i])) {
                        q.addLast(p[i]);
                        return q;
                    } else {
                        LinkedList<Point> temp = new LinkedList<>();
                        for (int a=0;a<q.size();a++){
                            temp.add(q.get(a));
                        }
                        temp.add(p[i]);
                        queue.addLast(temp);
                        addInSelected(p[i]);
                    }

                }

            }
        }
        return null;
    }

}
