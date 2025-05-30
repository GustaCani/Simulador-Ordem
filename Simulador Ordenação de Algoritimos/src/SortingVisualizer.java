import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.ArrayList;

public class SortingVisualizer extends JPanel {

    private int[] array;
    private ArrayList<int[]> frames = new ArrayList<>();
    private ArrayList<Integer> swapCounts = new ArrayList<>();
    private int currentFrame = 0;
    private boolean animationEnded = false;

    private JComboBox<String> comboAlgoritmos;
    private JButton btnStart;
    private SortingAlgorithm currentAlgorithm;

    public SortingVisualizer() {
        setLayout(new BorderLayout());

        JPanel controlPanel = new JPanel();

        comboAlgoritmos = new JComboBox<>(new String[] {
            "Insertion Sort", "Selection Sort", "Merge Sort", "Quick Sort", "Heap Sort"
        });

        btnStart = new JButton("Iniciar");

        controlPanel.add(new JLabel("Escolha o algoritmo:"));
        controlPanel.add(comboAlgoritmos);
        controlPanel.add(btnStart);

        add(controlPanel, BorderLayout.NORTH);

        array = solicitarVetorPersonalizado();

        btnStart.addActionListener(e -> {
            frames.clear();
            swapCounts.clear();
            currentFrame = 0;
            animationEnded = false;

            String escolha = (String) comboAlgoritmos.getSelectedItem();

            if ("Insertion Sort".equals(escolha)) {
                currentAlgorithm = new InsertionSortAlgorithm(array);
            } else if ("Selection Sort".equals(escolha)) {
                currentAlgorithm = new SelectionSortAlgorithm(array);
            } else if ("Merge Sort".equals(escolha)) {
                currentAlgorithm = new MergeSortAlgorithm(array);
            } else if ("Quick Sort".equals(escolha)) {
                currentAlgorithm = new QuickSortAlgorithm(array);
            } else if ("Heap Sort".equals(escolha)) {
                currentAlgorithm = new HeapSortAlgorithm(array);
            } else {
                JOptionPane.showMessageDialog(this, "Algoritmo não implementado.");
                return;
            }

            currentAlgorithm.sortAndCapture();
            frames = currentAlgorithm.getFrames();
            swapCounts = currentAlgorithm.getSwapCounts();

            startAnimation();
        });
    }

    public void startAnimation() {
        new Thread(() -> {
            try {
                for (int i = 0; i < frames.size(); i++) {
                    currentFrame = i;
                    repaint();
                    saveFrame(this, i);
                    Thread.sleep(50);
                }
                animationEnded = true;
                repaint();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void saveFrame(JPanel panel, int index) throws Exception {
        File dir = new File("frames");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        BufferedImage image = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        panel.paint(g2);
        g2.dispose();
        File outputFile = new File(dir, String.format("frame%03d.png", index));
        ImageIO.write(image, "png", outputFile);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (frames.isEmpty()) return;

        int[] arr = frames.get(currentFrame);

        int width = getWidth();
        int height = getHeight();

        int textHeight = 30;
        int leftMargin = 50;

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));

        if (!animationEnded) {
            String texto = "Trocas: " + (currentFrame < swapCounts.size() ? swapCounts.get(currentFrame) : 0);
            g.drawString(texto, 10, textHeight - 10);
        } else {
            int totalTrocas = swapCounts.isEmpty() ? 0 : swapCounts.get(swapCounts.size() - 1);
            String mensagem = "Ordenação finalizada! Total de trocas: " + totalTrocas;
            g.drawString(mensagem, 10, textHeight - 10);
        }

        int barAreaHeight = height - textHeight;
        int barAreaWidth = width - leftMargin;

        int barWidth = Math.max(1, barAreaWidth / arr.length);
        int maxValue = getMaxValue(arr);

        int roundedMax = ((maxValue + 4) / 5) * 5;

        g.setColor(Color.GRAY);
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        for (int value = 0; value <= roundedMax; value += 5) {
            int y = height - (int) (((double) value / roundedMax) * barAreaHeight);
            g.drawLine(leftMargin, y, width, y);
            g.drawString(String.valueOf(value), 5, y - 2);
        }

        for (int i = 0; i < arr.length; i++) {
            int barHeight = (int)(((double) arr[i] / roundedMax) * barAreaHeight);
            g.setColor(Color.BLUE);
            g.fillRect(leftMargin + i * barWidth, height - barHeight, barWidth - 1, barHeight);
        }
    }

    private int getMaxValue(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int val : arr) {
            if (val > max)
                max = val;
        }
        return max;
    }

    public static int[] solicitarVetorPersonalizado() {
        while (true) {
            String entrada = JOptionPane.showInputDialog(
                null,
                "Digite os números separados por vírgula (ex: 5,1,9,3,2):",
                "Entrada do Vetor",
                JOptionPane.QUESTION_MESSAGE
            );

            if (entrada == null) {
                System.exit(0);
            }

            try {
                String[] partes = entrada.trim().split(",");
                int[] vetor = new int[partes.length];
                for (int i = 0; i < partes.length; i++) {
                    vetor[i] = Integer.parseInt(partes[i].trim());
                }
                return vetor;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(
                    null,
                    "Entrada inválida. Digite apenas números separados por vírgula.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Visualizador de Ordenação");
        SortingVisualizer visualizer = new SortingVisualizer();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(visualizer);
        frame.setVisible(true);
    }
}
