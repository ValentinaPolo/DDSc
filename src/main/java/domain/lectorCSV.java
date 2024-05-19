package domain;

import colaboraciones.DistribuirVianda;
import colaboraciones.DonacionDeDinero;
import colaboraciones.DonacionVianda;
import colaboraciones.EntrgarTarjeta;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class lectorCSV {
    public lectorCSV(String separador) {
        this.separador = separador;
    }

    private String separador = ",";

    public void lector(String pathArchivoCSV){
        String linea;
        ArrayList<Colaborador> colaboradores = new ArrayList<>();
        HashMap<String, Colaborador> colaboradorMap = new HashMap<>();
        try( BufferedReader bufferLectura = new BufferedReader(new FileReader(pathArchivoCSV))){
            linea = bufferLectura.readLine();
          while (linea != null){
              String data[] = linea.split(separador);
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
                  eniviarCorreo(colaborador);

              }

          }
        }
        catch (IOException e){
            e.printStackTrace();
        };

        }

    private void eniviarCorreo(Colaborador colaborador) {
      try{
          Email email = EnviadorMail.conectaEmail();
          email.setSubject("Agradecimiento por colaboracion");
          email.setMsg("Estimado"+colaborador.getDatosPersonales().getNombre()+" "+colaborador.getDatosPersonales().getApellido()+
                  "Gracias por tu colaboracion,\n\n Te creamos una cuenta. Tus credenciales de acceso son:" +
                  "Usuario: "+colaborador.getUsuario().getNombreUsuario()+ "\n"
          +"Contrasenia = "+colaborador.getUsuario().getContrasenia()+"\n\n"+
                  "Por favor ingrese al sistema y verifique los datos");
          email.addTo(colaborador.getDatosPersonales().getMail());
          email.send();

      }
      catch (EmailException e) {
          e.printStackTrace();
      }


}
}


