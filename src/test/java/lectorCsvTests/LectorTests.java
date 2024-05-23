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

import static org.junit.jupiter.api.Assertions.*;

public class LectorTests {
    @Test
    public void testLeerArchivoCSV() throws IOException, ParseException {
        List<String[]> colaboraciones = new ArrayList<>();
        colaboraciones.add(new String[]{"DNI", "87654321", "Jose","Hernandez", "jose@gmail", "12/12/2012", "DONACION_VIANDAS", "50"});
        colaboraciones.add(new String[]{"DNI", "12345678", "Juan","Perez","juanperez@gmail", "01/01/2023", "DINERO", "100"});


        String archCSV =  System.getProperty("user.home") + "\\Documents\\ISO-Codes.csv";
        //CSVWriter writer = new CSVWriter(new FileWriter(archCSV));

        //writer.writeAll(colaboraciones);
        try (CSVWriter writer = new CSVWriter(new FileWriter(archCSV))) {
            writer.writeAll(colaboraciones);
        }


        ArrayList<Colaborador> colaboradores = lector(archCSV);
        assertEquals(2, colaboradores.size());
        assertInstanceOf(DonacionDeDinero.class, colaboradores.get(1).getColaboraciones().get(0));
        assertInstanceOf(DonacionVianda.class, colaboradores.get(0).getColaboraciones().get(0));
        assertEquals("DNI", colaboradores.get(0).getDatosPersonales().getTipoDoc());
        assertEquals("87654321", colaboradores.get(0).getDatosPersonales().getDocumento());
        assertEquals("Jose", colaboradores.get(0).getDatosPersonales().getNombre());
        assertEquals("Hernandez", colaboradores.get(0).getDatosPersonales().getApellido());
        assertEquals("jose@gmail", colaboradores.get(0).getDatosPersonales().getMail());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        //assertEquals(Integer.parseInt("50"), colaboradores.get(0).getColaboraciones().get(0).getCantidad());

    }
    public ArrayList<Colaborador> lector(String pathArchivo) throws FileNotFoundException, IllegalArgumentException {
        String[] linea;
        ArrayList<Colaborador> colaboradores = new ArrayList<>();
        HashMap<String, Colaborador> colaboradorMap = new HashMap<>();

        try (CSVReader csvReader = new CSVReader(new FileReader(pathArchivo))) {
            while ((linea = csvReader.readNext()) != null) {
                if (linea.length == 0) {
                    continue;
                }

                String tipoDoc = linea[0];
                String documento = linea[1];
                String nombre = linea[2];
                String apellido = linea[3];
                String mail = linea[4];
                String fechaColaboracion = linea[5];
                String formaColaboracion = linea[6];
                Integer cantidad = Integer.parseInt(linea[7]);
                Colaborador colaborador;
                Colaboracion colaboracion;

                switch (formaColaboracion) {
                    case "DINERO":
                        colaboracion = new DonacionDeDinero(fechaColaboracion, cantidad);
                        break;
                    case "DONACION_VIANDAS":
                        colaboracion = new DonacionVianda(fechaColaboracion, cantidad);
                        break;
                    case "REDISTRIBUCION_VIANDAS":
                        colaboracion = new DistribuirVianda(fechaColaboracion, cantidad);
                        break;
                    case "ENTREGA_TARJETAS":
                        colaboracion = new EntregarTarjeta(fechaColaboracion, cantidad);
                        break;
                    default:
                        throw new IllegalArgumentException("Colaboracion no valida");
                }

                String key = tipoDoc + documento;
                if (colaboradorMap.containsKey(key)) {
                    colaborador = colaboradorMap.get(key);
                    colaborador.agregarColaboracion(colaboracion);
                } else {
                    DatosPersonales datosPersonales = new DatosPersonales(tipoDoc, documento, nombre, apellido, mail);
                    Usuario usuario = new Usuario(mail, nombre);
                    colaborador = new Colaborador(datosPersonales, usuario);
                    colaborador.agregarColaboracion(colaboracion);
                    colaboradorMap.put(key, colaborador);
                    colaboradores.add(colaborador);
                }
            }
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }
        return colaboradores;
    }
}
