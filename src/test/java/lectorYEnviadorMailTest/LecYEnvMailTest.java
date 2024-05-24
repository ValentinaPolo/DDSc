package lectorYEnviadorMailTest;

import com.opencsv.CSVWriter;
import domain.lectorCSV;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static lectorYEnviadorMailTest.CSVTests.createCSVFile;
import static org.junit.jupiter.api.Assertions.fail;

public class LecYEnvMailTest {
    @Test
    public void testLectorConArchivoCSV() throws FileNotFoundException {

        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"DNI", "12345678", "Juan", "Perez", "congelandoelhambre@gmail.com", "01/01/2023", "DINERO", "100"});
        data.add(new String[]{"DNI", "87654321", "Maria", "Gomez", "congelandoelhambre@gmail.com", "02/01/2023", "DONACION_VIANDAS", "50"});
        data.add(new String[]{"DNI", "87654321", "Maria", "Gomez", "congelandoelhambre@gmail.com", "05/01/2023", "DINERO", "250"});
        String archCSV = System.getProperty("user.home") + "\\Documents\\Prueba-Lector.csv";
        try (CSVWriter writer = new CSVWriter(new FileWriter(archCSV))) {
                writer.writeAll(data);
            }
         catch (IOException e) {
            e.printStackTrace();
            fail("Error al crear o leer el archivo CSV.");
        }
        lectorCSV lectura = new lectorCSV(archCSV);
        lectura.lector(archCSV);
    }

}
