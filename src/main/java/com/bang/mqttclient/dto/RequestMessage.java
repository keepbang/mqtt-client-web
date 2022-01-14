package com.bang.mqttclient.dto;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.StandardCharsets;

public class RequestMessage {
    private String topic;
    private String payload;

    protected RequestMessage() {
    }

    public RequestMessage(String topic, String payload) {
        this.topic = topic;
        this.payload = payload;
    }

    public MqttMessage generateMessage() {
        MqttMessage mqttMessage = new MqttMessage(payload.getBytes(StandardCharsets.UTF_8));
        mqttMessage.setQos(0);

        return mqttMessage;
    }

    public String getTopic() {
        return topic;
    }

    public String getPayload() {
        return payload;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
