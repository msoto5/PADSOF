package servicio;
/*
import java.io.*;

public class ServicioInput {
    private FileInputStream file;
    private ObjectInputStream input;

    public void abrir()
            throws IOException {
        file = new FileInputStream("./src/backUp/servicios.ser");
        input = new ObjectInputStream(file);
    }  
    
    public void cerrar()
            throws IOException {
        if (input != null)
            input.close();
    }

    public Servicio leer()
            throws IOException, ClassNotFoundException {
        Servicio servicio = null;
        if (input != null) {
            try {
                servicio = (Servicio) input.readObject();
            } catch (EOFException eof) {
                // Fin del fichero
            }
        }
        return servicio;
    }
}
*/