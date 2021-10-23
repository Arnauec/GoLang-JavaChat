package chatgo;

import java.net.*;
import java.io.*;

public class MySocket {

    public Socket socket;
    BufferedReader in;
    PrintWriter out;

    public MySocket(String host, int port) {// Construye y conecta un socket con el servidor indicado
        try {
            socket = new Socket(host, port);
            init_streams();
        } catch (IOException e) {
        }
    }

    MySocket(Socket s) {
        socket = s;
        init_streams();
    }

    private void init_streams() {
        try {
            in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()
            ));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
        }
    }

    public String read_str() { // Lee el siguiente 'string'.
    // Si se ha cerrado el otro extremo, obtiene 'null'.
        try {
            return in.readLine();
        } catch (IOException e) {
            //return null;

            return null;
        }
    }

    public int read_int() { // Lee el siguiente 'string' interpretándolo como un valor decimal.
        return Integer.parseInt(read_str());
    }

    public boolean read_boolean() { // Lee el siguiente 'string' interpretándolo como un valor booleano.
        return Boolean.parseBoolean(read_str());
    }

    public char read_char() { // Lee el siguiente char.
        return read_str().charAt(0);
    }

    public void write_str(String s) { // Escribe 's' en el socket.
        out.println(s);
    }

    public void write_int(int i) { // Escribe la representación textual (decimal) de 'i'.
        write_str(Integer.toString(i));
    }

    public void write_boolean(boolean b) { // Escribe la representación textual (decimal) de 'b'.
        write_str(Boolean.toString(b));
    }

    public void write_char(char c) { // Escribe la representación textual de 'c'.
        write_str(new Character(c).toString());
    }

    public void close() {// Cierra el socket.
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
        }
    }
}

