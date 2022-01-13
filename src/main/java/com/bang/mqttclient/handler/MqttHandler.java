package com.bang.mqttclient.handler;

import com.bang.mqttclient.dto.MqttConfig;
import com.bang.mqttclient.dto.RequestMessage;
import com.bang.mqttclient.repository.MqttClientRepository;
import com.bang.mqttclient.repository.MqttConfigRepository;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.stereotype.Component;

@Component
public class MqttHandler {

    private final MqttClientRepository mqttClient;
    private final MqttConfigRepository mqttConfigRepository;

    public MqttHandler(
            final MqttClientRepository mqttClient
            , final MqttConfigRepository mqttConfigRepository
    ) {
        this.mqttClient = mqttClient;
        this.mqttConfigRepository = mqttConfigRepository;
    }

    public boolean connect(MqttConfig mqttConfig) {
        if(mqttClient.isConnected()) {
            disconnect();
        }

        MqttClient connectedClient = connect(mqttConfig.generateMqttClient()
                , mqttConfig.generateMqttOptions());
        mqttClient.update(connectedClient);

        boolean isConnected = mqttClient.isClient();
        if(isConnected) {
            mqttConfigRepository.updateMqttConfig(mqttConfig);
        }

        return isConnected;
    }

    private MqttClient connect(MqttClient mqttClient, MqttConnectOptions options) {
        try {
            mqttClient.connect(options);
            return mqttClient;
        } catch (MqttException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean disconnect() {
        mqttConfigRepository.clear();
        return mqttClient.disconnect();
    }

    public boolean publish(RequestMessage request) {
        return mqttClient.sendMessage(request);
    }

    public boolean status() {
        return mqttClient.isConnected();
    }

    public MqttConfig getConfig() {
        return mqttConfigRepository.getMqttConfig();
    }
}
