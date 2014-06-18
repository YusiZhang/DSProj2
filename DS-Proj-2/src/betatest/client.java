package betatest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class client {

	public static void main(String[] args) throws IOException, IOException {
		Socket soc = new Socket("127.0.0.1", 15640);
		
		System.out.println("socket made.");

		// get TCP streams and wrap them.
		BufferedReader in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
		PrintWriter out = new PrintWriter(soc.getOutputStream(), true);

		System.out.println("stream made.");
		
		
	}
}
