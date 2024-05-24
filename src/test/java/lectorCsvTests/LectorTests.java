package lectorCsvTests;

import colaboraciones.DistribuirVianda;
import colaboraciones.DonacionDeDinero;
import colaboraciones.DonacionVianda;
import colaboraciones.EntregarTarjeta;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import domain.*;

import jakarta.mail.internet.InternetAddress;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class LectorTests {
    @Test
    public void testLeerArchivoCSV() throws IOException, ParseException {
        List<String[]> colaboraciones = new ArrayList<>();
        colaboraciones.add(new String[]{"DNI", "87654321", "Jose", "Hernandez", "jose@gmail", "12/12/2012", "DONACION_VIANDAS", "50"});
        colaboraciones.add(new String[]{"DNI", "12345678", "Juan", "Perez", "juanperez@gmail", "01/01/2023", "DINERO", "100"});


        String archCSV = System.getProperty("user.home") + "\\Documents\\ISO-Codes.csv";
        //CSVWriter writer = new CSVWriter(new FileWriter(archCSV));

        //writer.writeAll(colaboraciones);
        try (CSVWriter writer = new CSVWriter(new FileWriter(archCSV))) {
            writer.writeAll(colaboraciones);
        }

        lectorCSV lectura = new lectorCSV(archCSV);
        lectura.lector(archCSV);
        Map<String, Object> colaboracionesLeidas = lectura.getColaboraciones();

        // Verificar los objetos creados
        //assertThat(colaboracionesLeidas).hasSize(2);

        // Crear los objetos esperados
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Colaborador colaborador1 = new Colaborador(new DatosPersonales("DNI", "87654321", "Jose", "Hernandez", "jose@gmail"), new Usuario("jose@gmail", "Jose"));
        Colaborador colaborador2 = new Colaborador(new DatosPersonales("DNI", "12345678", "Juan", "Perez", "juanperez@gmail"), new Usuario("juanperez@gmail", "Juan"));

        // Verificación del primer objeto
        String key1 = "DNI8765432112/12/2012";
        assertThat(colaboracionesLeidas.containsKey(key1)).isTrue();
        assertThat(colaboracionesLeidas.get(key1)).isInstanceOf(DonacionVianda.class);
        DonacionVianda dv = (DonacionVianda) colaboracionesLeidas.get(key1);
        assertThat(dv.getColaborador()).usingRecursiveComparison().isEqualTo(colaborador1);
        assertThat(dv.getFechaColaboracion()).isEqualTo(sdf.parse("12/12/2012"));
        assertThat(dv.getCantidad()).isEqualTo(50);

        // Verificación del segundo objeto
        String key2 = "DNI1234567801/01/2023";
        assertThat(colaboracionesLeidas.containsKey(key2)).isTrue();
        assertThat(colaboracionesLeidas.get(key2)).isInstanceOf(DonacionDeDinero.class);
        DonacionDeDinero dd = (DonacionDeDinero) colaboracionesLeidas.get(key2);
        assertThat(dd.getColaborador()).usingRecursiveComparison().isEqualTo(colaborador2);
        assertThat(dd.getFechaColaboracion()).isEqualTo(sdf.parse("01/01/2023"));
        assertThat(dd.getCantidad()).isEqualTo(100);


    }

}
