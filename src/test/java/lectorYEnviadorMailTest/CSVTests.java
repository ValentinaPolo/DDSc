package lectorYEnviadorMailTest;

import com.opencsv.CSVWriter;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVTests {
    public static File createCSVFile(String[] header, List<String[]> data) throws IOException {
        File tempFile = File.createTempFile("test-data", ".csv");
        tempFile.deleteOnExit(); // Asegura que el archivo se elimine al finalizar la prueba

        try (CSVWriter writer = new CSVWriter(new FileWriter(tempFile))) {
            writer.writeNext(header);
            writer.writeAll(data);
        }

        return tempFile;
    }
}
