/*
 * author : Yusi
 * based on sample code from 15640
 */
package communication;

import registry.RemoteObjectRef;

public class MyStub {
	//adding ror in mystub makes stub class easily to find out target port and host.
	RemoteObjectRef ror;

	public RemoteObjectRef getRor() {
		return ror;
	}

	public void setRor(RemoteObjectRef ror) {
		this.ror = ror;
	}
	
}
