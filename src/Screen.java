import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Screen extends JPanel implements Runnable, KeyListener {


    public static final int GRID_SIZE = 300;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;

    private Thread thread;
    private boolean isRunning = false;

    private static final int FPS = 30;
    public static final int targetTime = 1000/FPS;

    private AlgorithmGen algGen;
    private boolean show;
    private boolean reset;



    public Screen(){
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        algGen = new AlgorithmGen(GRID_SIZE);
        algGen.Solver();
        show = false;
        reset = false;

        start();

    }

    private void start(){
        isRunning = true;
        thread = new Thread(this);
        thread.start();
        addKeyListener(this);

        //TODO; what does this do?
        setFocusable(true);

    }

    @Override
    public void run() {
        long start,elapsed,wait;

        while (isRunning){

            start = System.currentTimeMillis();

            tick();
            repaint();

            elapsed = System.currentTimeMillis() - start;
            wait = (targetTime - elapsed) ;

            if(wait < 0) wait = targetTime;

            try{ Thread.sleep(wait); }
            catch(Exception e){ e.printStackTrace(); }

        }

    }

    public void tick(){
        if(reset){
            algGen = new AlgorithmGen(GRID_SIZE);
            algGen.Solver();
            show = false;
            reset = false;
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        //g.drawRect(10,10,10,10);
        int z = algGen.gridSize;
        float twf = (z*WIDTH)/(z+1);
        float thf = (z*HEIGHT)/(z+1);
        int tw = (int) twf;
        int th = (int) thf;


        int trueWidth = (tw/algGen.gridSize) * algGen.gridSize;
        int trueHeight = (th/algGen.gridSize) * algGen.gridSize;

        if(show){
            AlgorithmGen.drawSol((Graphics2D) g,algGen.solX,algGen.solY,algGen.sol,algGen.gridSize,
                    (WIDTH-trueWidth)/2,(HEIGHT-trueHeight)/2, trueWidth, trueHeight);
        }
        AlgorithmGen.drawGrid((Graphics2D) g,algGen.grid,algGen.gridSize,trueWidth,trueHeight,
                (WIDTH-trueWidth)/2,(HEIGHT-trueHeight)/2);
    }



    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            show = !show;
        }
        else reset = !reset;
    }

    @Override
    public void keyTyped(KeyEvent e) {  }
}
