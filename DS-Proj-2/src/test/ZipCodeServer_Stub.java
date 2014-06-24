/*
 * author : Yusi
 * based on sample code from 15640
 */
package test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

		System.out.println("Stub init is called ");

		try {
			socket = new Socket(this.getRor().getIP_adr(), this.getRor().getPort());
			System.out.println("Stub : " + "init target port is"+ this.getRor().getPort());

			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

			// send message to yourRMI
			RMIMessage sendMessage = new RMIMessage();
			sendMessage.setType("invoke");
			sendMessage.setArgs(newlist);
			sendMessage.setMethodName("initialise");
			sendMessage.setRor(this.getRor());
			out.writeObject(sendMessage);
			System.out.println("Stub init send message " + sendMessage.toString());

			// get message from yourRMI
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			RMIMessage receiveMessage = (RMIMessage) in.readObject();
			if (receiveMessage.getType().equals("return")) {
				System.out.println("Get return from server "+ (String) receiveMessage.getReturnValue());
			} else if (receiveMessage.getType().equals("exception")) {
				throw receiveMessage.getE();
			}

			// clean up
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

	}

	@Override
	public String find(String city) {

		System.out.println("Stub find is called ");

		try {
			socket = new Socket(this.getRor().getIP_adr(), this.getRor()
					.getPort());
			System.out.println("Stub : " + "target port is"
					+ this.getRor().getPort());

			ObjectOutputStream out = new ObjectOutputStream(
					socket.getOutputStream());

			// send message to yourRMI
			RMIMessage sendMessage = new RMIMessage();
			sendMessage.setType("invoke");
			sendMessage.setArgs(city);
			sendMessage.setMethodName("find");
			sendMessage.setRor(this.getRor());
			out.writeObject(sendMessage);
			System.out.println("Stub send message " + sendMessage.toString());

			// get message from yourRMI
			ObjectInputStream in = new ObjectInputStream(
					socket.getInputStream());
			RMIMessage receiveMessage = (RMIMessage) in.readObject();
			if (receiveMessage.getType().equals("return")) {
				System.out.println("Get return from server "
						+ (String) receiveMessage.getReturnValue());
				return (String) receiveMessage.getReturnValue();
			} else if (receiveMessage.getType().equals("exception")) {
				throw receiveMessage.getE();
			}

			// clean up
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
		System.out.println("Stub findAll is called ");

		try {
			socket = new Socket(this.getRor().getIP_adr(), this.getRor()
					.getPort());
			System.out.println("Stub : " + "target port is"
					+ this.getRor().getPort());

			ObjectOutputStream out = new ObjectOutputStream(
					socket.getOutputStream());

			// send message to yourRMI
			RMIMessage sendMessage = new RMIMessage();
			sendMessage.setType("invoke");
			
			//intentionally set wrong args to test exception handle funciton.
			sendMessage.setArgs("testArg");
			//
			
			sendMessage.setMethodName("findAll");
			sendMessage.setRor(this.getRor());
			out.writeObject(sendMessage);
			System.out.println("Stub send message " + sendMessage.toString());

			// get message from yourRMI
			ObjectInputStream in = new ObjectInputStream(
					socket.getInputStream());
			RMIMessage receiveMessage = (RMIMessage) in.readObject();
			if (receiveMessage.getType().equals("return")) {
				System.out.println("Get return from server "
						+ (ZipCodeList) receiveMessage.getReturnValue());
				return (ZipCodeList) receiveMessage.getReturnValue();
			} else if (receiveMessage.getType().equals("exception")) {
				System.out.println("exception catched in stub!!");
				throw receiveMessage.getE();
			}

			// clean up
			out.flush();
			socket.close();
		} catch (UnknownHostException e) {
			System.out.println(e.toString());
		} catch (IOException e) {
			System.out.println(e.toString());
		} catch (ClassNotFoundException e) {
			System.out.println(e.toString());
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return null;
	}

	@Override
	public void printAll() {
		ZipCodeList temp = l;
		while (temp != null) {
			System.out.println("city: " + temp.city + ", " + "code: "
					+ temp.ZipCode + "\n");
			temp = temp.next;
		}
	}

}
