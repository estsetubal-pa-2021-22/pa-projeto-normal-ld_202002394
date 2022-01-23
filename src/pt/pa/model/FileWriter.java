package pt.pa.model;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class FileWriter {

    List<String> file;

    public FileWriter() {
        this.file = new ArrayList<>();
    }

    // Returns a list of rows, based on a received squared Integer matrix
    public void matrixToList(int[][] matrix) {
        for (int[] ints : matrix) {
            StringBuilder row = new StringBuilder();
            for (int j = 0; j < matrix.length; j++)
                row.append(" ").append(ints[j]);
            file.add(row.toString().trim());
        }
    }

    // Saves a file in a given folder
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

