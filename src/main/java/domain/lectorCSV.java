package domain;


import colaboraciones.DistribuirVianda;
import colaboraciones.DonacionDeDinero;
import colaboraciones.DonacionVianda;
import colaboraciones.EntregarTarjeta;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.Getter;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static domain.EnviadorMail.enviarConGMail;

public class lectorCSV {
    public lectorCSV(String separador) {
        this.separador = separador;
    }

    private String separador = ",";
    @Getter
    private Map<String, Object> colaboraciones = new HashMap<>();
    public void lector(String pathArchivo) throws FileNotFoundException, IllegalArgumentException {
        String[] linea = null;
        HashMap<String, Colaborador> colaboradorMap = new HashMap<>();
        CSVReader csvReader = new CSVReader(new FileReader(pathArchivo));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try{
          while ((linea = csvReader.readNext()) != null){
              String tipoDoc = linea[0];
              String documento = linea[1];
              String nombre = linea[2];
              String apellido = linea[3];
              String mail = linea[4];
              Date fechaColaboracion = sdf.parse(linea[5]);
              String formaColaboracion = linea[6];
              Integer cantidad = Integer.parseInt(linea[7]);
              Colaborador colaborador;

              String key = tipoDoc + documento;
              if(colaboradorMap.get(key) == null){
                  DatosPersonales datosPersonales = new DatosPersonales(tipoDoc, documento, nombre, apellido, mail);
                  Usuario usuario = new Usuario(mail, nombre);
                  colaborador = new Colaborador(datosPersonales, usuario);
                  colaboradorMap.put(key, colaborador);
                  //eniviarCorreo(colaborador);
              }

              colaborador = colaboradorMap.get(key);
              String colaboracionKey = key + linea[5];

              switch (formaColaboracion){
                  case "DINERO":
                      DonacionDeDinero donacionDeDinero = new DonacionDeDinero(fechaColaboracion, cantidad, colaborador);
                      colaboraciones.put(colaboracionKey, donacionDeDinero);
                      break;
                  case "DONACION_VIANDAS":
                      DonacionVianda donacionVianda = new DonacionVianda(fechaColaboracion, cantidad, colaborador);
                      colaboraciones.put(colaboracionKey, donacionVianda);
                      break;
                  case "REDISTRIBUCION_VIANDAS":
                      DistribuirVianda distribuirVianda = new DistribuirVianda(fechaColaboracion, cantidad, colaborador);
                      colaboraciones.put(colaboracionKey,distribuirVianda);
                      break;
                  case "ENTREGA_TARJETAS":
                      EntregarTarjeta entregarTarjeta= new EntregarTarjeta(fechaColaboracion, cantidad, colaborador);
                      colaboraciones.put(colaboracionKey, entregarTarjeta);
                      break;
                  default:
                      throw new IllegalArgumentException("Colaboracion no valida");
              }}

            csvReader.close();

        }
        catch (IOException e){
            e.printStackTrace();
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
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


