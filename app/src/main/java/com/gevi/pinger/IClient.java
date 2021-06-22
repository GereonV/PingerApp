package com.gevi.pinger;

public interface IClient {

    void readString(String string);
    void readObject(Object obj);
    void onDisconnect();

}
