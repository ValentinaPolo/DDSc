package lectorCsvTests;

import colaboraciones.DistribuirVianda;
import colaboraciones.DonacionDeDinero;
import colaboraciones.DonacionVianda;
import colaboraciones.EntrgarTarjeta;

import domain.*;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LectorTests {
    @Test
    public void testLeerArchivoCSV() {
        String csvData = "DNI,12345678,Juan,Perez,juan.perez@example.com,01/01/2023,DINERO,100\n" +
                "DNI,87654321,Maria,Gomez,maria.gomez@example.com,02/01/2023,DONACION_VIANDAS,50";
        BufferedReader br = new BufferedReader(new StringReader(csvData));

        LectorTests lector = new LectorTests();
        ArrayList<Colaborador> colaboradores = lector.lector(br);

        assertEquals(2, colaboradores.size());
        assertTrue(colaboradores.get(0).getColaboraciones().get(0) instanceof DonacionDeDinero);
        assertTrue(colaboradores.get(1).getColaboraciones().get(1) instanceof DonacionVianda);
    }
    public ArrayList<Colaborador> lector(BufferedReader bufferLectura){
        String linea;
        ArrayList<Colaborador> colaboradores = new ArrayList<>();
        HashMap<String, Colaborador> colaboradorMap = new HashMap<>();
        try{
            linea = bufferLectura.readLine();
            while (linea != null){
                String data[] = linea.split(",");
                String tipoDoc = data[0];
                String documento = data[1];
                String nombre = data[2];
                String apellido = data[3];
                String mail = data[4];
                String fechaColaboracion = data[5];
                String formaColaboracion = data[6];
                Integer cantidad = Integer.parseInt(data[7]);
                Colaborador colaborador;
                Colaboracion colaboracion;
                switch (formaColaboracion){
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
                        colaboracion = new EntrgarTarjeta(fechaColaboracion, cantidad);
                    default:
                        throw new IllegalArgumentException("Colaboracion no valida");
                }
                String key = tipoDoc + documento;
                if(colaboradorMap.containsKey(key)){
                    colaborador = colaboradorMap.get(key);
                    colaborador.agregarColaboracion(colaboracion);
                }
                else {
                    DatosPersonales datosPersonales = new DatosPersonales(tipoDoc, documento, nombre, apellido, mail);
                    Usuario usuario = new Usuario(mail, nombre);
                    colaborador = new Colaborador(datosPersonales, usuario);
                    colaboradorMap.put(key, colaborador);
                    colaboradores.add(colaborador);


                }
                linea = bufferLectura.readLine();
            }

        }
        catch (IOException e){
            e.printStackTrace();
        };
        return colaboradores;
    }

}

