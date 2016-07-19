package com.simonlzn.controller;

import com.simonlzn.message.MessageQueue;
import com.simonlzn.util.PubSub;
import com.simonlzn.util.Subscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    MessageQueue messageQueue;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public DeferredResult sendMessage(@RequestParam String message) {
        System.out.println("Sent : " + message);
        messageQueue.send(message, "");

        final DeferredResult result = new DeferredResult((long) 180000);

        Subscriber subscriber =
                new Subscriber("key") {
                    @Override
                    public void Callback(Object message) {
                        try {
                            result.setResult(message);
                        } catch (Exception e) {
                            result.setErrorResult(e);
                        } finally {
                            PubSub.Unsubscribe(this.channel, this);
                        }
                    }
                };

        PubSub.Subscribe("key", subscriber);

        return result;
    }
}
