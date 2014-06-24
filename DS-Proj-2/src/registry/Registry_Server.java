/*
 * author : Yue Zhuo , Yusi Zhang
 * 
 */
package registry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Hashtable;

public class Registry_Server {
	String host;
	int port;
	ServerSocket listener;
	Socket socket;
	BufferedReader in;
	ObjectOutputStream os;
	Hashtable<String, RemoteObjectRef> binds = new Hashtable<String, RemoteObjectRef>();

	public Registry_Server(String host, int port) {
		this.host = host;
		this.port = port;
	}

	// handle the lookup() request from the client
	public void lookup() throws ClassNotFoundException, UnknownHostException,
			IOException {

		try {

			listener = new ServerSocket(this.port);
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (true) {
			try {
				socket = listener.accept();
				in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				os = new ObjectOutputStream(socket.getOutputStream());
				String serviceName = in.readLine();
				if (serviceName.equals("lookup")) {
					System.out.println("ServiceName is " + serviceName);
					String interfaceName = in.readLine();
					System.out.println("interfacename " + interfaceName);
					RemoteObjectRef res = binds.get(interfaceName);
					if (res != null) {
						os.writeObject(res);
						os.flush();
						os.close();
						
						socket.close();
					}
				} else if (serviceName.equals("locate")) {
					System.out.println("ServiceName is " + serviceName);
					os.writeObject("I am a simple registry.");
					os.flush();
					os.close();
					
					socket.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void rebind(String serviceName, RemoteObjectRef ror) {
		binds.put(serviceName, ror);

	}

	public static void main(String[] args) {
		Registry_Server server = new Registry_Server("127.0.0.7", 15640);
		RemoteObjectRef ror = new RemoteObjectRef("127.0.0.1", 15641, 1,"ZipCodeServer");
		server.rebind("ZipCodeServer", ror);

		try {
			server.lookup();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
