package pt.pa.model;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Class responsible for creating a file under a given name with
 * the information on the graph.
 *
 * @author LD_202002394
 * @version Final
 *
 */
public class FileWriter {

    List<String> file;

    /**
     * Constructor of the class FileWriter. Creates an empty arrayList.
     *
     */
    public FileWriter() {
        this.file = new ArrayList<>();
    }

    /**
     * Returns a list of rows, based on a received squared Integer matrix.
     *
     * @param matrix int[][]
     *
     * @return Returns a list of rows, based on a received squared Integer matrix.
     */
    public void matrixToList(int[][] matrix) {
        for (int[] ints : matrix) {
            StringBuilder row = new StringBuilder();
            for (int j = 0; j < matrix.length; j++)
                row.append(" ").append(ints[j]);
            file.add(row.toString().trim());
        }
    }

    /**
     * Saves a file in a given folder.
     *
     * @param folderName    String
     *
     * @return Returns a string of the filename desired.
     */
    public String saveFile(String folderName) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("ddMMyyyy_HHmmss");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String fileName = "dataset/" + folderName + "/" + folderName + "_" + sdf1.format(timestamp) + ".txt";
        try {
            PrintWriter out = new PrintWriter(fileName);
            for (String line : this.file)
                out.println(line);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fileName;
    }

}

