package com.simonlzn.util;

public abstract class Subscriber{
    public String channel;

    public Subscriber(String channel) {
        this.channel = channel;
    }

    public abstract void Callback(Object message);
}
