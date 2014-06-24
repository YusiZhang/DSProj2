/*
 * author : Yusi
 * 
 */
package communication;

import java.io.Serializable;

import registry.RemoteObjectRef;

public class RMIMessage implements Serializable{
	String type;
	String methodName;
	Object returnValue; //could be remote obj
	Object args;
	Exception e;
	RemoteObjectRef ror;
	
	public RMIMessage() {
		type = "";
		methodName = "";
		returnValue = "";
		args = "";
		ror = null;
		e = null;
	}
	
	public String toString(){
		if (type.equals("invoke"))
			return "invoke class " +ror.getRemote_Interface_Name() +" methodName: " + methodName + "/t" + "args: " + args.toString();
		else if (type.equals("return"))
			return "return " + returnValue.toString();
		else if (type.equals("exception"))	
			return "exception " + e.toString();
		else
			return "default";
		
	}
	
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Object getReturnValue() {
		return returnValue;
	}
	public void setReturnValue(Object returnValue) {
		this.returnValue = returnValue;
	}
	public Object getArgs() {
		return args;
	}
	public void setArgs(Object args) {
		this.args = args;
	}
	public Exception getE() {
		return e;
	}
	public void setE(Exception e) {
		this.e = e;
	}

	public RemoteObjectRef getRor() {
		return ror;
	}

	public void setRor(RemoteObjectRef ror) {
		this.ror = ror;
	}
	
	
	
	
	
	
}
