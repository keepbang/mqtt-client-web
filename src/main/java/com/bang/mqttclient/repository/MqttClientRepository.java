package com.bang.mqttclient.repository;

import com.bang.mqttclient.dto.RequestMessage;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class MqttClientRepository {
    private MqttClient mqttClient;

    public void update(MqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }

    public boolean isConnected() {
        return isClient() && mqttClient.isConnected();
    }

    public boolean disconnect() {
        if(isConnected()) {
            try {
                mqttClient.disconnect();
                return true;
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean isClient() {
        return !Objects.isNull(mqttClient);
    }

    public boolean sendMessage(RequestMessage request) {
        try {
            mqttClient.publish(request.getTopic(), request.generateMessage());
            return true;
        } catch (MqttException e) {
            e.printStackTrace();
            return false;
        }
    }
}
