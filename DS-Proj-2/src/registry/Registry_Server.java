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
	public void lookup() throws ClassNotFoundException, UnknownHostException, IOException {
		socket = new Socket(host, port);
		try {
		 in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		 os = new ObjectOutputStream(socket.getOutputStream());
		
			listener = new ServerSocket(this.port);
		}catch (IOException e) {
			e.printStackTrace();
		}
//		Thread t =  new Thread(){
//			@Override
//			public void run(){
//				super.run();
				while(true) {
					try{
						socket = listener.accept();
						String serviceName = in.readLine();
						
						RemoteObjectRef res = binds.get(serviceName);
						if (res != null) {
							os.writeObject(res);
							
							os.close();
							socket.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
//			}
//		}
//		t.start();
	}

	public void rebind(String serviceName, RemoteObjectRef ror) {
		binds.put(serviceName, ror);

	}
}
