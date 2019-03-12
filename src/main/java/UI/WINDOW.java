package UI;
import model.Coord;
import model.Mapable;
import service.FlyFigure;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class WINDOW extends JFrame implements Runnable  , Mapable {

    private Box[][] boxes;
    private FlyFigure flyFigure;

    public WINDOW() {
        boxes = new Box[Confing.WIDTH][Confing.HEIGHT];
        initForm();
        initBoxes();
        addKeyListener(new KeyAdapter());
        TimerAdapter time = new TimerAdapter();
        Timer timer = new Timer(160,time);
        timer.start();


    }

    public void addFigure() {
        flyFigure =new FlyFigure(this);
        if(flyFigure.canPlaceFigure())
            showFigure();
        else {
            setVisible(false);
            dispose();

        }

    }

    private void initForm() {
        setSize(Confing.WIDTH * Confing.SIZE + 15,
                Confing.HEIGHT * Confing.SIZE + 40);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Tetris The Game");
        setLayout(null);
        setVisible(true);


    }

    private void initBoxes() {
        for (int x = 0; x < Confing.WIDTH; x++)
            for (int y = 0; y < Confing.HEIGHT; y++) {
                boxes[x][y] = new Box(x, y);
                add(boxes[x][y]);

            }


    }

    public void run() {
        repaint();

    }

    private void showFigure() {
        showFigure(1);
    }

    private void hideFigure() {
        showFigure(0);
    }


    private void showFigure(int color) {
        for (Coord dot: flyFigure.getFigure().dots)
            setBoxesColor(flyFigure.getCoord().x + dot.x, flyFigure.getCoord().y + dot.y, color);


    }

    private void setBoxesColor(int x, int y, int color) {
        if (x < 0 || x >= Confing.WIDTH) return;
        if (y < 0 || y >= Confing.HEIGHT) return;
        boxes[x][y].setColor(color);
    }

    public  int getBoxColor(int x,int y){
        if (x < 0 || x >= Confing.WIDTH) return-1;
        if (y < 0 || y >= Confing.HEIGHT) return-1;
        return boxes[x][y].getColor();
    }
    private  void moveFly(int sx,int sy){
        hideFigure();
        flyFigure.moveFigure(sx,sy);
        showFigure();
    }
    private  void turnFly(int direction){
        hideFigure();
        flyFigure.turnFigure(direction);
        showFigure();
    }

    class KeyAdapter implements KeyListener {
        public void keyTyped(KeyEvent e) {

        }

        public void keyPressed(KeyEvent e) {

            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    moveFly(-1, 0);
                    break;
                case KeyEvent.VK_RIGHT:
                    moveFly(+1, 0);
                    break;
                case KeyEvent.VK_U:
                    moveFly(0, -1);
                    break;
                case KeyEvent.VK_DOWN:
                    turnFly(1);
                    break;
                case KeyEvent.VK_UP:
                    turnFly(2);
                    break;
            }

        }

        public void keyReleased(KeyEvent e) {

        }
    }
    private void stripLines() {
        for (int y =Confing.HEIGHT -1;y>0;y--)
            while (isFulLine(y))
                dropLine(y);




    }
    private void dropLine(int y){
        for (int my =y-1;my>=0;my--)
            for (int x = 0; x <Confing.WIDTH ; x++)
        setBoxesColor(x,my+1,getBoxColor(x,my));
        for (int x = 0; x <Confing.WIDTH ; x++)
            setBoxesColor(x,0,0);
    }
     private boolean isFulLine(int y){

         for (int x = 0; x <Confing.WIDTH ; x++)
             if (getBoxColor(x,y)!=2)
                 return false;
             return true;

     }

    class  TimerAdapter implements ActionListener{
        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */
        public void actionPerformed(ActionEvent e) {
            moveFly(0,1);
            if (flyFigure.isLanded()){
                showFigure(2);
                stripLines();
                addFigure();
            }
        }
    }


}
