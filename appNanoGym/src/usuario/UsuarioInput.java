package usuario;
/*
import java.io.*;

public class UsuarioInput {
    private FileInputStream file;
    private ObjectInputStream input;

    public void abrir()
            throws IOException {
        file = new FileInputStream("./src/backUp/usuarios.ser");
        input = new ObjectInputStream(file);
    }

    public void cerrar()
            throws IOException {
        if (input != null)
            input.close();
    }

    public Usuario leer()
            throws IOException, ClassNotFoundException {
        Usuario usuario = null;
        if (input != null) {
            try {
                usuario = (Usuario) input.readObject();
            } catch (EOFException eof) {
                System.err.println("Problema leyendo usuario");
            }
        }
        return usuario;
    }
}
*/
