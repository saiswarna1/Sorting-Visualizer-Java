import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class SortingVisualizer extends JPanel {
    private static final int ARRAY_SIZE = 50;
    private static final int BAR_WIDTH = 10;
    private static final int GAP_WIDTH = 15; // Gap between bars
    private static final int DELAY = 20; // Reduced delay for faster visualization
    
    private int[] array;
    
    public SortingVisualizer() {
        array = new int[ARRAY_SIZE];
        Random random = new Random();
        
        for (int i = 0; i < ARRAY_SIZE; i++) {
            array[i] = random.nextInt(400) + 50;
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < array.length; i++) {
            int x = i * (BAR_WIDTH + GAP_WIDTH);
            g.fillRect(x, getHeight() - array[i], BAR_WIDTH, array[i]);
            g.setColor(Color.BLACK);
            g.drawString(String.valueOf(array[i]), x + (BAR_WIDTH / 4), getHeight() - array[i] - 5);
            g.setColor(Color.DARK_GRAY);
        }
    }
    
    public void bubbleSort() {
            for (int i = 0; i < array.length - 1; i++) {
                for (int j = 0; j < array.length - 1 - i; j++) {
                    if (array[j] > array[j + 1]) {
                        int temp = array[j];
                        array[j] = array[j + 1];
                        array[j + 1] = temp;
                        repaint();
                        try {
                            Thread.sleep(DELAY);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
    }

    public void insertionSort() {
            for (int i = 1; i < array.length; i++) {
                int key = array[i];
                int j = i - 1;
                while (j >= 0 && array[j] > key) {
                    array[j + 1] = array[j];
                    j = j - 1;
                }
                array[j + 1] = key;
                repaint();
                try {
                    Thread.sleep(DELAY);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
    }

    public void mergeSort() {
            mergeSortHelper(array, 0, array.length - 1);
    }

    private void mergeSortHelper(int[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSortHelper(arr, left, mid);
            mergeSortHelper(arr, mid + 1, right);
            merge(arr, left, mid, right);
            repaint();
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
        int[] L = new int[n1];
        int[] R = new int[n2];

        System.arraycopy(arr, left, L, 0, n1);
        System.arraycopy(arr, mid + 1, R, 0, n2);

        int i = 0, j = 0;
        int k = left;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("Sorting Visualizer");
        SortingVisualizer panel = new SortingVisualizer();
        frame.add(panel);
      //  frame.setSize(800, 600);
        frame.setSize(ARRAY_SIZE * BAR_WIDTH*GAP_WIDTH, 500);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the frame to full size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        String[] options = {"Bubble Sort", "Insertion Sort", "Merge Sort"};
        int choice = JOptionPane.showOptionDialog(frame, "Choose sorting algorithm", "Sorting Visualizer",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        switch (choice) {
            case 0:
                panel.bubbleSort();
                break;
            case 1:
                panel.insertionSort();
                break;
            case 2:
                panel.mergeSort();
                break;
            default:
                break;
        }
    }
}

