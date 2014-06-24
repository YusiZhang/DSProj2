/*
 * author : Yusi
 * based on sample code from 15640
 */
package registry;

import java.util.*;
import java.net.*;
import java.io.*;

public class SimpleRegistry {
	// registry holds its port and host, and connects to it each time.
	public String Host;
	public int Port;

	// ultra simple constructor.
	public SimpleRegistry(String IPAdr, int PortNum) {
		Host = IPAdr;
		Port = PortNum;
	}

	// returns the ROR (if found) or null (if else)
	public RemoteObjectRef lookup(String serviceName) throws IOException, InterruptedException {
		// open socket.
		// it assumes registry is already located by locate registry.
		// you should usually do try-catch here (and later).
		Socket soc = new Socket(Host, Port);
		System.out.println(soc.toString());

		System.out.println("socket made.");

		// get TCP streams and wrap them.
		ObjectInputStream in = new ObjectInputStream(soc.getInputStream());
		// BufferedReader in = new BufferedReader(new
		// InputStreamReader(soc.getInputStream()));
		PrintWriter out = new PrintWriter(soc.getOutputStream(), true);

		System.out.println("stream made.");

		// it is locate request, with a service name.
		out.println("lookup");
		Thread.sleep(500);
		out.println(serviceName);
		System.out.println("Send interface name " + serviceName);
		System.out.println("command and service name sent.");

		// branch according to the answer.
		String res = in.readLine();
		RemoteObjectRef ror = null;
		try {
			ror = (RemoteObjectRef) in.readObject();
			if (ror != null) {
				System.out.println("found " + ror.Remote_Interface_Name);
			} else {
				System.out.println("it is not found!");
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		soc.close();

		// return ROR.
		return ror;
	}

}
