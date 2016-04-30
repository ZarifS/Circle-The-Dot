import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * The class <b>BoardView</b> provides the current view of the board. It extends
 * <b>JPanel</b> and lays out a two dimensional array of <b>DotButton</b> instances.
 *
 * @author Zarif Shahriar
 */
public class BoardView extends JPanel {

    private DotButton [][] buttons;
    private GameModel gameModel;
    protected JPanel panel;
    private int size;



    /**
     * Constructor used for initializing the board. The action listener for
     * the board's DotButton is the game controller
     *
     * @param gameModel
     *            the model of the game (already initialized)
     * @param gameController
     *            the controller
     */

    public BoardView(GameModel gameModel, GameController gameController) {
        this.gameModel=gameModel;
        size = gameModel.getSize();
        panel = new JPanel();
        panel.setLayout(new GridLayout(size,size,0,0));
        buttons=new DotButton[size][size];
        panel.setVisible(true);
        for(int i =0;i<size;i++){
            JPanel row = new JPanel();
            row.setLayout(new GridLayout(1,1,0,0));
            row.setVisible(true);
            if(i%2==0) {
                row.setBorder(new EmptyBorder(0,40,0,60));
            }
            else{
                row.setBorder(new EmptyBorder(0,60,0,40));
            }
            panel.add(row);
            for(int j=0;j<size;j++){
                buttons[i][j]= new DotButton(i,j,0);
                buttons[i][j].addActionListener(gameController);
                row.add(buttons[i][j]);
            }
        }

    }

    /**
     * update the status of the board's DotButton instances based on the current game model
     */

    public void update(){
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                buttons[i][j].setType(gameModel.getCurrentStatus(i,j));
            }
        }
    }

}