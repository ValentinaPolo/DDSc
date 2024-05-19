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

public class lectorCSV {
    public lectorCSV(String separador) {
        this.separador = separador;
    }

    private String separador = ",";

    public void lector(String pathArchivoCSV){
        String linea;
        ArrayList<Colaboracion> colaboraciones = null;
        try( BufferedReader bufferLectura = new BufferedReader(new FileReader(pathArchivoCSV))){
            linea = bufferLectura.readLine();
          while (linea != null){
              String data[] = linea.split(separador);
              Colaboracion colaboracion;
              switch (data[6]){
                  case "DINERO":
                      colaboracion = new DonacionDeDinero(data);
                      break;
                  case "DONACION_VIANDAS":
                      colaboracion = new DonacionVianda(data);
                      break;
                  case "REDISTRIBUCION_VIANDAS":
                      colaboracion = new DistribuirVianda(data);
                      break;
                  case "ENTREGA_TARJETAS":
                      colaboracion = new EntrgarTarjeta(data);
                  default:
                      throw new IllegalArgumentException("Colaboracion no valida");
              }
          colaboraciones.add(colaboracion);

          }
        }
        catch (IOException e){
            e.printStackTrace();
        };
        for(Colaboracion colaboracion: colaboraciones){
            if(colaboracion.getUsuario() == null){
                eniviarCorreo(colaboracion);
            }
        }

    }

    private void eniviarCorreo(Colaboracion colaboracion) {
      try{
          Email email = EnviadorMail.conectaEmail();
          email.setSubject("Agradecimiento por colaboracion");
          email.setMsg("Gracias por tu colaboracion, te creamos una cuenta");
          email.addTo(colaboracion.getMail());
          email.send();

      }
      catch (EmailException e) {
          e.printStackTrace();
      }


}}
