import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class InsertionSort extends JPanel {
    private final int WIDTH = 1000, HEIGHT = WIDTH * 9 /16;
    private final int SIZE = 200;
    private final float[] bar_height = new float[SIZE];
    private SwingWorker<Void, Void> sorter;
    private int current_index, compareTo;
    public static JFrame frame = new JFrame("Insertion Visualizer");

    private InsertionSort(){
        setBackground(Color.black);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        initBarHeight();
        initSorter();
        initShuffler();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(Color.white);
        Rectangle2D.Float bar;
        float BAR_WIDTH = WIDTH / SIZE;
        for(int i = 0; i<SIZE; i++){
            bar = new Rectangle2D.Float(i* BAR_WIDTH, 0, BAR_WIDTH, bar_height[i]);
             g2d.fill(bar);
        }
             g2d.setColor(Color.red);
             bar = new Rectangle2D.Float(current_index* BAR_WIDTH, 0, BAR_WIDTH, bar_height[current_index]);
             g2d.fill(bar);

             g2d.setColor(Color.green);
             bar = new Rectangle2D.Float(compareTo* BAR_WIDTH, 0, BAR_WIDTH, bar_height[compareTo]);
             g2d.fill(bar);

    }

    public void initSorter(){
        sorter = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws InterruptedException {
                for(current_index = 1; current_index < SIZE; current_index++){
                    compareTo = current_index;
                    while(compareTo > 0 && bar_height[compareTo] < bar_height[compareTo - 1]){
                        swap(compareTo, compareTo - 1);
                        compareTo--;
                        Thread.sleep(1);
                        repaint();
                    }
                }
                current_index = 0;
                compareTo = 0;

                return null;
            }
        };
    }

    private void initShuffler(){
        SwingWorker<Void, Void> shuffler = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws InterruptedException {
                int middle = SIZE / 2;
                for (int i = 0, j = middle; i < middle; i++, j++) {
                    int random_index = new Random().nextInt(SIZE);
                    swap(i, random_index);

                    random_index = new Random().nextInt(SIZE);
                    swap(j, random_index);

                    Thread.sleep(10);
                    repaint();
                }
                return null;
            }

            @Override
            public void done() {
                super.done();
                sorter.execute();
            }
        };
        shuffler.execute();
    }

    private void swap(int indexA, int indexB){
        float temp = bar_height[indexA];
        bar_height[indexA] = bar_height[indexB];
        bar_height[indexB] = temp;
    }

    private void initBarHeight(){
        float interval = (float) HEIGHT / SIZE;
        for(int i =0; i<SIZE; i++){
            bar_height[i] = i * interval;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
           frame.setResizable(false);
           frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           frame.setContentPane(new InsertionSort());
           frame.validate();
           frame.pack();
           frame.setLocationRelativeTo(null);
           frame.setVisible(true);
        });
    }
}
