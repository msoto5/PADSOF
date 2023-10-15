package sala;
/*
import java.io.*;

public class SalaInput {
    private FileInputStream file;
    private ObjectInputStream input;

    public void abrir()
            throws IOException {
        file = new FileInputStream("./src/backUp/salas.ser");
        input = new ObjectInputStream(file);
    }

    public void cerrar()
            throws IOException {
        if (input != null)
            input.close();
    }

    public Sala leer()
            throws IOException, ClassNotFoundException {
        Sala sala = null;
        if (input != null) {
            try {
                sala = (Sala) input.readObject();
            } catch (EOFException eof) {
                // Fin del fichero
            }
        }
        return sala;
    }
}
*/
