import javax.swing.*;
import java.awt.*;

/**
 * The class <b>GameView</b> provides the current view of the entire Game. It extends
 * <b>JFrame</b> and lays out an instance of  <b>BoardView</b> (the actual game) and 
 * two instances of JButton. The action listener for the buttons is the controller.
 *
 * @author Zarif Shahriar
 */

public class GameView extends JFrame {

    private BoardView board;
    protected JButton quit;
    protected JButton reset;
    protected JPanel panel;

    /**
     * Constructor used for initializing the Frame
     *
     * @param model
     *            the model of the game (already initialized)
     * @param gameController
     *            the controller
     */

    public GameView(GameModel model, GameController gameController) {

        setTitle("Circle The Dot Game");
        setSize(500,500);
        setLayout(new BorderLayout());

        board = new BoardView(model,gameController);
        quit = new JButton("Quit");
        quit.addActionListener(gameController);
        reset= new JButton("Reset");
        reset.addActionListener(gameController);

        panel = new JPanel();
        panel.add(quit);
        panel.add(reset);
        panel.setVisible(true);

        add(panel, BorderLayout.SOUTH);
        add(board.panel);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);


    }

    /**
     * Getter method for the attribute board.
     *
     * @return a reference to the BoardView instance
     */

    public BoardView getBoardView(){
        return board;
    }

}