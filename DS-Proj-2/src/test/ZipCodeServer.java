package test;

import communication.MyRemote;

//import java.rmi.Remote;

public interface ZipCodeServer extends MyRemote// extends YourRemote or whatever
{
    public void initialise(ZipCodeList newlist);
    public String find(String city);
    public ZipCodeList findAll();
    public void printAll();
}

