package domain;

import colaboraciones.DistribuirVianda;
import colaboraciones.DonacionDeDinero;
import colaboraciones.DonacionVianda;
import colaboraciones.EntregarTarjeta;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static domain.EnviadorMail.enviarConGMail;

public class lectorCSV {
    public lectorCSV(String separador) {
        this.separador = separador;
    }

    private String separador = ",";

    public void lector(String pathArchivo) throws FileNotFoundException, IllegalArgumentException {
        String[] linea = null;
        ArrayList<Colaborador> colaboradores = new ArrayList<>();
        HashMap<String, Colaborador> colaboradorMap = new HashMap<>();
        CSVReader csvReader = new CSVReader(new FileReader(pathArchivo));
        try{
          while ((linea = csvReader.readNext()) != null){
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
                      colaboracion = new EntregarTarjeta(fechaColaboracion, cantidad);
                      break;
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
                  eniviarCorreo(colaborador);

              }
          }
            csvReader.close();

        }
        catch (IOException e){
            e.printStackTrace();
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
        ;
    }

    private void eniviarCorreo(Colaborador colaborador) {
        String destinatario = colaborador.getDatosPersonales().getMail();
        String asunto = "Agradecimiento por colaboracion";
        String mensaje = "\"Estimado\"+colaborador.getDatosPersonales().getNombre()+\" \"+colaborador.getDatosPersonales().getApellido()+\n" +
                "                  \"Gracias por tu colaboracion,\\n\\n Te creamos una cuenta. Tus credenciales de acceso son:\" +\n" +
                "                  \"Usuario: \"+colaborador.getUsuario().getNombreUsuario()+ \"\\n\"\n" +
                "          +\"Contrasenia = \"+colaborador.getUsuario().getContrasenia()+\"\\n\\n\"+\n" +
                "                  \"Por favor ingrese al sistema y verifique los datos\"";
        enviarConGMail(destinatario, asunto, mensaje);


    }
}


