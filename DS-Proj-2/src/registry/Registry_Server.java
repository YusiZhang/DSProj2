import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Registry_Server {
	String host;
	int port;
	ServerSocket listener;
	Socket socket;

	Hashtable<String, RemoteObjectRef> binds = new Hashtable<String, RemoteObjectRef>();

	public Registry_Server(String host, int port) {
		this.host = host;
		this.port = port;
	}

	// handle the lookup() request from the client
	public void lookup() {
		Socket soc = new Socket(Host, Port);
		
		BufferedReader in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
		ObjectOutputStream os = new ObjectOutputStream(soc.getOutputStream());
		try {
			listener = new ServerSocket(this.port);
		}catch (IOException e) {
			e.printStackTrace();
		}
		Thread t =  new Thread(){
			public void run(){
				super.run();
				while(true) {
					try{
						socket = listener.accept();
						in = new ObjectInputStream(socket.getInputStream());
						String serviceName = in.readLine();
						
						RemoteObjectRef res = binds.get(serviceName);
						if (res != null) {
							os.writeObject(res);
							
							os.close();
							socket.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		}	
		t.start();
	}

	public void rebind(String serviceName, RemoteObjectRef ror) {
		binds.put(serviceName, ror);

	}
}
