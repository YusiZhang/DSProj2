package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import communication.MyStub;
import communication.RMIMessage;

public class ZipCodeServer_Stub extends MyStub implements ZipCodeServer {
	ZipCodeList l;
	Socket socket;

	@Override
	public void initialise(ZipCodeList newlist) {
		l = newlist;
	}

	@Override
	public String find(String city) {

		
		try {
			socket = new Socket(this.getHost(), this.getPort());
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			
			RMIMessage message = new RMIMessage();
			message.setType("invoke");
			message.setArgs(city);
			
			
			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return null;
	}

	@Override
	public ZipCodeList findAll() {
		return null;
	}

	@Override
	public void printAll() {

	}

}
