package com.simonlzn.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class PubSub {
    private static HashMap<String, Set<Subscriber>> subscribers = new HashMap<String, Set<Subscriber>>();

    public static void Publish(String channel, Object message) {
        Set<Subscriber> subscribersInChannel = subscribers.get(channel);

        if (subscribersInChannel == null)
            return;

        Set<Subscriber> s = (Set<Subscriber>)((HashSet)subscribersInChannel).clone();
        for (Subscriber i : s) {
            i.Callback(message);
        }
    }

    public static void Subscribe(String channel, Subscriber subscriber) {
        Set<Subscriber> subscribersInChannel = subscribers.get(channel);
        if (subscribersInChannel == null) {
            Set<Subscriber> newSet = new HashSet();
            newSet.add(subscriber);
            subscribers.put(channel, newSet);
        } else {
            if (subscribersInChannel.contains(subscriber))
                return;
            subscribersInChannel.add(subscriber);
        }
    }

    public static void Unsubscribe(String channel, Subscriber subscriber) {
        Set<Subscriber> subscribersInChannel = subscribers.get(channel);
        if (subscribersInChannel == null) {
            return;
        } else {
            if (subscribersInChannel.contains(subscriber))
                subscribersInChannel.remove(subscriber);
        }
    }
}
