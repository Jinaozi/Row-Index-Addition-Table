import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RowIndexAdditionTable {

    public static void main(String[] args) {
        String filePath = "machine_problems_dataset.csv";
        List<String[]> tableData = new ArrayList<>();
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String header = br.readLine();
            String[] headerColumns = ("RowIndex," + header + ",Sum").split(",");
            tableData.add(headerColumns); 

            int rowIndex = 0;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(","); 

                int number1 = Integer.parseInt(values[0].trim());
                double number2 = Double.parseDouble(values[1].trim());

                double sum = number1 + number2;

                String[] newRow = new String[values.length + 2];
                newRow[0] = String.valueOf(rowIndex); 
                System.arraycopy(values, 0, newRow, 1, values.length); 
                newRow[newRow.length - 1] = String.format("%.2f", sum); 

                tableData.add(newRow); 
                rowIndex++;
            }

            printTable(tableData);

        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing numbers: " + e.getMessage());
        }
    }

    public static void printTable(List<String[]> tableData) {
        int[] columnWidths = calculateColumnWidths(tableData);

        for (String[] row : tableData) {
            for (int i = 0; i < row.length; i++) {
                System.out.printf("%-" + columnWidths[i] + "s ", row[i]);
            }
            System.out.println();
        }
    }

    public static int[] calculateColumnWidths(List<String[]> tableData) {
        int numColumns = tableData.get(0).length;
        int[] columnWidths = new int[numColumns];

        for (String[] row : tableData) {
            for (int i = 0; i < row.length; i++) {
                columnWidths[i] = Math.max(columnWidths[i], row[i].length());
            }
        }

        return columnWidths;
    }
}
