package com.gevi.pinger;

import gpjl.network.Client;

public class AppClient extends Client {

    private final IClient clientInterface;

    public AppClient(String hostname, IClient clientInterface) {
        super(hostname);
        this.clientInterface = clientInterface;
    }

    @Override
    protected void readString(String string) {
        clientInterface.readString(string);
    }

    @Override
    protected void readObject(Object obj) {
        clientInterface.readObject(obj);
    }

    @Override
    protected void onDisconnect() {
        clientInterface.onDisconnect();
    }

}
