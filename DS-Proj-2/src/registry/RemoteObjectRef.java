package registry;

import communication.MyStub;

public class RemoteObjectRef {
	String IP_adr;
	int Port;
	int Obj_Key;
	String Remote_Interface_Name;

	public RemoteObjectRef(String ip, int port, int obj_key, String riname) {
		IP_adr = ip;
		Port = port;
		Obj_Key = obj_key;
		Remote_Interface_Name = riname;
	}

	// this method is important, since it is a stub creator.
	//
	public MyStub localise() throws ClassNotFoundException, Exception {
		// Implement this as you like: essentially you should
		// create a new stub object and returns it.
		// Assume the stub class has the name e.g.
		//
		// Remote_Interface_Name + "_stub".
		//
		// Then you can create a new stub as follows:
		//
		 Class c = Class.forName(Remote_Interface_Name + "_Stub"); //i.e. ZipCodeServer_Stub
		 MyStub stub = (MyStub)c.newInstance();
		 stub.setHost(IP_adr);
		 stub.setPort(Port);
		//
		// For this to work, your stub should have a constructor without
		// arguments.
		// You know what it does when it is called: it gives communication
		// module
		// all what it got (use CM's static methods), including its method name,
		// arguments etc., in a marshalled form, and CM (yourRMI) sends it out
		// to
		// another place.
		// Here let it return null.
		return stub;
	}
	
	public String getIP_adr() {
		return IP_adr;
	}

	public int getPort() {
		return Port;
	}

	public int getObj_Key() {
		return Obj_Key;
	}

	public String getRemote_Interface_Name() {
		return Remote_Interface_Name;
	}

	public void setIP_adr(String iP_adr) {
		IP_adr = iP_adr;
	}

	public void setPort(int port) {
		Port = port;
	}

	public void setObj_Key(int obj_Key) {
		Obj_Key = obj_Key;
	}

	public void setRemote_Interface_Name(String remote_Interface_Name) {
		Remote_Interface_Name = remote_Interface_Name;
	}
}
