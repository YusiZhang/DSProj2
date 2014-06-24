/*
 * author : Yusi
 * based on sample code from 15640
 */
/* This does not offer the code of the whole communication module 
 (CM) for RMI: but it gives some hints about how you can make 
 it. I call it simply "yourRMI". 

 For example, it  shows how you can get the host name etc.,
 (well you can hardwire it if you like, I should say),
 or how you can make a class out of classname as a string.

 This just shows one design option. Other options are
 possible. We assume there is a unique skeleton for each
 remote object class (not object) which is called by CM 
 by static methods for unmarshalling etc. We can do without
 it, in which case CM does marshalling/unmarhshalling.
 Which is simpler, I cannot say, since both have their
 own simpleness and complexity.
 */
package communication;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.*;
import java.util.ArrayList;
import java.util.Hashtable;

import registry.RemoteObjectRef;
import test.ZipCodeServerImpl;

public class yourRMI {
	static String host;
	static int port;

	// It will use a hash table, which contains ROR together with
	// reference to the remote object.
	// As you can see, the exception handling is not done at all.
	public static void main(String args[]) throws Exception {

		// it should have its own port. assume you hardwire it.
		host = (InetAddress.getLocalHost()).getHostName();
		port = 15641;
		/*
		 *ror table  for testing 
		 *Two hash tables:
		 *0.|*************key*******|**********value*******|
		 *1.|**serviceName(interfaceName)**|**Arraylist<ror>**|
		 *2.|**ObjectKey(objectName)**|**actual remote object**|
		 */
		//table1
		Hashtable<String,ArrayList<RemoteObjectRef>> tableROR = new Hashtable<String,ArrayList<RemoteObjectRef>>();
		//testing ror
		RemoteObjectRef ror1 = new RemoteObjectRef(host, port, 1,"ZipCodeServer");
		RemoteObjectRef ror2 = new RemoteObjectRef(host, port, 2,"ZipCodeServer");
		RemoteObjectRef ror3 = new RemoteObjectRef(host, port, 3,"ZipCodeServer");
		ArrayList<RemoteObjectRef> list = new ArrayList<RemoteObjectRef>();
		list.add(ror1);
		list.add(ror2);
		list.add(ror3);
		//interfaceName-rorList pair
		tableROR.put("ZipCodeServer", list);
		//table2
		Hashtable <Integer,Object> tableRO = new Hashtable<Integer, Object>();
		ZipCodeServerImpl impl1 = new ZipCodeServerImpl();
		ZipCodeServerImpl impl2 = new ZipCodeServerImpl();
		ZipCodeServerImpl impl3 = new ZipCodeServerImpl();
		tableRO.put(1, impl1);
		tableRO.put(2, impl2);
		tableRO.put(3, impl3);
		// create a socket.
		ServerSocket serverSoc = new ServerSocket(port);
		
		// Now we go into a loop.
		// Look at rmiregistry.java for a simple server programming.
		// The code is far from optimal but in any way you can get basics.
		// Actually you should use multiple threads, or this easily
		// deadlocks. But for your implementation I do not ask it.
		// For design, consider well.
		
		/*
		 * unmarshall message directly, no skeleton
		 */
		while (true) {
			// (1) receives an invocation request.
			Socket socket = serverSoc.accept();
			System.out.println("socket from " + socket.toString() + "is received");
			// (2) creates a socket and input/output streams.
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			// (3) gets the invocation, in martiallled form.
			RMIMessage receiveMessage = (RMIMessage)in.readObject();
			System.out.println("Object is read " + receiveMessage.toString());
			
			// (4.1) get args, method name, out of message
			Object arguments = receiveMessage.getArgs();
			String methodName = receiveMessage.getMethodName();
			System.out.println("args are" + arguments.toString());
			System.out.println("method name is " + methodName);
			// (4.2) gets the real object reference from tbl.
			System.out.println(receiveMessage.getRor().getRemote_Interface_Name()+"Impl");
			Object ro = tableRO.get(receiveMessage.getRor().getObj_Key());
			
			
			
			
			
			try {
				// (5) Either:
				// -- using the interface name, asks the skeleton,
				// together with the object reference, to unmartial
				// and invoke the real object.
				// -- or do unmarshalling directly and involkes that
				// object directly.
				Method method;
				Object returnValue;
				if(receiveMessage.getArgs().equals("")){
					 method = ro.getClass().getMethod(receiveMessage.getMethodName());
					// (6) receives the return value, which (if not marshalled
					// you should marshal it here) and send it out to the
					// the source of the invoker.
					 returnValue = method.invoke(ro);
				}else {
					 method = ro.getClass().getMethod(receiveMessage.getMethodName(), receiveMessage.getArgs().getClass());
					// (6) receives the return value, which (if not marshalled
					// you should marshal it here) and send it out to the
					// the source of the invoker.
					 returnValue = method.invoke(ro, receiveMessage.getArgs());
				}
				
				
				// (6.1) send reply message to stub
				RMIMessage replyMessage = new RMIMessage();
				replyMessage.setType("return");
				replyMessage.setReturnValue(returnValue);
				out.writeObject(replyMessage);
			} catch (Exception e) {
				System.out.println("exception catched in yourRMI");
				RMIMessage replyMessage = new RMIMessage();
				replyMessage.setType("exception");
				replyMessage.setE(e);;
				out.writeObject(replyMessage);
			}
			
			
			// (7) closes the socket.
			socket.close();
		}
	}
}
