import java.awt.*;
import java.util.Random;

public class AlgorithmGen {

    int[][] grid;
    int gridSize;

    int solX;
    int solY;
    int sol;

    public AlgorithmGen(int n){
        this.grid = new int[n][n];
        this.gridSize = n;
        genGrid();
    }

    public static void drawGrid(Graphics2D g, int[][] grid, int n, int width, int height, int x, int y){

        int dx = (width)/n;
        int dy = (height)/n;


        for (int i = x; i < (dx * n) + 1 + x ; i+=dx) {
            g.drawLine(i, y, i, (dy * n) + y);
        }
        for (int i = y; i <(dy * n) + 1 + y; i+= dy) {
            g.drawLine(x, i, (dx * n) + x, i);
        }
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {

                if(grid[i][j] == 1){

                    drawCell(g,i,j,x,y,n,n,width,height);

                }

            }

        }
    }

    public static void drawCell(Graphics2D g, int x, int y, int offSetX, int offSetY, int gridWidth, int gridHeight, int width, int height){

        int dx = offSetX + (((width)/gridWidth) * x);
        int dy = offSetY + (((height)/gridHeight) * y);


        Rectangle rect = new Rectangle(dx, dy, (width/gridWidth), (height/gridHeight));
        g.fill(rect);
    };

    public static void drawSol(Graphics2D g, int x, int y, int length, int n , int offsetX , int offsetY , int width , int height){

        int dx = ((width)/n);
        int dy = ((height)/n);
        g.setPaint(new Color(0, 255, 0));
        g.fillRect(offsetX + (x*dx) - ((length-1)*dx), offsetY + (y*dy) - ((length-1)*dy),(length*dx),(length*dy));
        g.setPaint(new Color(0, 0, 0));


    };

    public void genGrid(){
        Random rand = new Random();
        for (int i = 0; i < gridSize ; i++) {
            for (int j = 0; j < gridSize ; j++) {
                int val = rand.nextInt(100);
                if(val < 5) grid[i][j] = 1;
                else grid[i][j] = 0;
            }
        }
    }

    public void Solver(){

        int[][] leastsq = new int[gridSize][gridSize];
        solX = 0;
        solY = 0;
        sol = 0;


        for (int j = 0; j < gridSize ; j++) {

            for (int i = 0; i < gridSize ; i++) {

                if(grid[i][j] == 1) leastsq[i][j] = 0;
                else if(i == 0 || j == 0) leastsq[i][j] = 1;
                else leastsq[i][j] = Math.min(leastsq[i-1][j-1],Math.min(leastsq[i-1][j],leastsq[i][j-1])) + 1;

                if(leastsq[i][j] > sol){
                    solX = i;
                    solY = j;
                    sol = leastsq[i][j];
                }




            }

        }



    }











}
