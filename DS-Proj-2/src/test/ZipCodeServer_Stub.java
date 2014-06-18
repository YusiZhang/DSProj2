package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
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
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			
			//send message to yourRMI
			RMIMessage sendMessage = new RMIMessage();
			sendMessage.setType("invoke");
			sendMessage.setArgs(city);
			sendMessage.setMethodName("find");
			out.writeObject(sendMessage);
			
			//get message from yourRMI
			RMIMessage receiveMessage = (RMIMessage)in.readObject();
			if(receiveMessage.getType().equals("return")){
				System.out.println("Get return from server " + (String)receiveMessage.getReturnValue());
				return (String) receiveMessage.getReturnValue();
			}else if(receiveMessage.getType().equals("exception")){
				throw receiveMessage.getE();
			}
			
			//clean up
			out.flush();
			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
