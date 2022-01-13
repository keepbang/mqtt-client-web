package com.bang.mqttclient.repository;

import com.bang.mqttclient.dto.MqttConfig;
import org.springframework.stereotype.Repository;

@Repository
public class MqttConfigRepository {
    private MqttConfig mqttConfig;

    public void updateMqttConfig(MqttConfig mqttConfig) {
        this.mqttConfig = mqttConfig;
    }

    public void clear() {
        mqttConfig.clear();
    }

    public MqttConfig getMqttConfig() {
        return mqttConfig;
    }
}
