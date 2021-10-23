package chatgo;

import java.net.*;
import java.io.*;


public class MyServerSocket
{
	ServerSocket socket;

	public MyServerSocket(int port) {
		// Construye un 'server socket' para la obtención de connexiones.
		// Este socket no está conectado y por lo tanto no se pueden
		// aplicar los métodos 'read' y 'write'.
	    try {
	    	socket = new ServerSocket(port);
	    } catch (IOException e) {
	    }
	}

	public MySocket accept() {
		// Espera y obtiene un socket connectado con un nuevo cliente
	    try {
	    	Socket cs = socket.accept();
	    	return new MySocket(cs);
	    } catch (IOException e) {
	    	return null;
	    }
	}
}
