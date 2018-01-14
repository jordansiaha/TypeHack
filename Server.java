
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server {

	public static void main(String[] args) {
		new Server();
	}

	// all necessary variables
	private Vector<ObjectOutputStream> clientOutputStreams;

	// --------------------------------------------------
	// Server - Constructor
	//
	// used to accept clients and then create a new
	// thread to write new paint objects to all clients.
	// --------------------------------------------------
	public Server() {
		clientOutputStreams = new Vector<ObjectOutputStream>();

		try {
			@SuppressWarnings("resource")
			ServerSocket serverSock = new ServerSocket(9999);
			System.out.println(serverSock.getInetAddress().getHostAddress());

			while (true) {
				System.out.println("Waiting for connection...");
				Socket clientSocket = serverSock.accept();

				System.out.println("connected");
				System.out.println("client Name: " + clientSocket.getInetAddress().getHostName());
				System.out.println("client IP: " + clientSocket.getInetAddress().getHostAddress());

				ObjectOutputStream writer = new ObjectOutputStream(clientSocket.getOutputStream());
				clientOutputStreams.add(writer);

				ObjectInputStream inputFromClient = new ObjectInputStream(clientSocket.getInputStream());

				String output = (String) inputFromClient.readObject();

				FileWriter fw = null;
				try {
					/* Yang: make sure to change file name when not appending */
					fw = new FileWriter(new File("ServerRecord.txt"), true);
					fw.write(output);
					fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				System.out.println("Record Written");
			}

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
