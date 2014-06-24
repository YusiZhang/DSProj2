/*
 * author : Yusi
 * based on sample code from 15640
 */
package communication;

import registry.RemoteObjectRef;

public class MyStub {
	RemoteObjectRef ror;

	public RemoteObjectRef getRor() {
		return ror;
	}

	public void setRor(RemoteObjectRef ror) {
		this.ror = ror;
	}
	
}
