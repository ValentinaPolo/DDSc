package lectorYEnviadorMailTest;

import domain.lectorCSV;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static lectorYEnviadorMailTest.CSVTests.createCSVFile;
import static org.junit.jupiter.api.Assertions.fail;

public class LecYEnvMailTest {
    @Test
    public void testLectorConArchivoCSV() {

        String[] header = {"TipoDoc", "Documento", "Nombre", "Apellido", "Mail", "FechaColaboracion", "FormaColaboracion", "Cantidad"};
        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"DNI", "12345678", "Juan", "Perez", "juan.perez@example.com", "01/01/2023", "DINERO", "100"});
        data.add(new String[]{"DNI", "87654321", "Maria", "Gomez", "maria.gomez@example.com", "02/01/2023", "DONACION_VIANDAS", "50"});

        try {

            File csvFile;
            csvFile = createCSVFile(header, data);
            lectorCSV lector = new lectorCSV(",");
            lector.lector(csvFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            fail("Error al crear o leer el archivo CSV.");
        }
    }
}
