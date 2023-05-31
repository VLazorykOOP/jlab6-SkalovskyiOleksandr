import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.table.DefaultTableModel;


public class MatrixSumGUI {
    private JFrame frame;
    private JPanel panel;
    private JLabel label;
    private JTextField textField;
    private JButton button;
    private JTable table;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MatrixSumGUI gui = new MatrixSumGUI();
                gui.createAndShowGUI();
            }
        });
    }

    public void createAndShowGUI() {
        frame = new JFrame("Matrix Sum");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel(new BorderLayout());

        label = new JLabel("Enter the file path:");
        panel.add(label, BorderLayout.NORTH);

        textField = new JTextField();
        panel.add(textField, BorderLayout.CENTER);

        button = new JButton("Calculate");
        button.addActionListener(new ButtonListener());
        panel.add(button, BorderLayout.SOUTH);

        frame.getContentPane().add(panel);
        frame.setSize(400, 150);
        frame.setVisible(true);
    }

    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String filePath = textField.getText();

            try {
                int[][] matrix = readMatrixFromFile(filePath);
                calculateAndDisplay(matrix);
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(frame, "File not found.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Invalid data format in the file.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (CustomException e) {
                JOptionPane.showMessageDialog(frame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private int[][] readMatrixFromFile(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);

        int n = scanner.nextInt();
        int[][] matrix = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }

        scanner.close();

        return matrix;
    }

    private void calculateAndDisplay(int[][] matrix) throws CustomException {
        int n = matrix.length;

        int[] rowSums = new int[n];
        int[] colSums = new int[n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                rowSums[i] += matrix[i][j];
                colSums[j] += matrix[i][j];
            }
        }

        DefaultTableModel tableModel = new DefaultTableModel(n, 3);
        tableModel.setColumnIdentifiers(new String[] { "Row", "Column", "Sum" });

        int rowIndex = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (rowSums[i] == colSums[j]) {
                    tableModel.setValueAt(i, rowIndex, 0);
                    tableModel.setValueAt(j, rowIndex, 1);
                    tableModel.setValueAt(rowSums[i], rowIndex, 2);
                    rowIndex++;
                }
            }
        }

        if (rowIndex == 0) {
            throw new CustomException("No rows and columns with equal sums.");
        }

        table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.EAST);
        panel.revalidate();
    }

    private class CustomException extends ArithmeticException {
        public CustomException(String message) {
            super(message);
        }
    }
}
