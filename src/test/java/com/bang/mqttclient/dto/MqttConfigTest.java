package com.bang.mqttclient.dto;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Profile;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Profile("test")
public class MqttConfigTest {

    private MqttConfig mqttConfig;

    @BeforeEach
    public void setUp() {
        String broker = "tcp://192.168.3.170:8883";
        String clientId = UUID.randomUUID().toString();
        Integer keepAliveInterval = 60;
        Integer connectionTimeout = 30;
        mqttConfig = new MqttConfig(broker, clientId, keepAliveInterval, connectionTimeout);
    }

    @Test
    @DisplayName("mqtt client 생성")
    void generateMqttClient() throws MqttException {
        // given
        // when
        MqttClient mqttClient = mqttConfig.generateMqttClient();
        // then
        assertThat(mqttClient).isNotNull();
    }

    @Test
    @DisplayName("mqtt client options 생성")
    void generateMqttOptions() {
        // given
        // when
        MqttConnectOptions options = mqttConfig.generateMqttOptions();
        // then
        assertAll(
                () -> assertThat(options.getKeepAliveInterval()).isEqualTo(60),
                () -> assertThat(options.getConnectionTimeout()).isEqualTo(30)
        );
    }

    @Test
    @DisplayName("설정 초기화")
    void clear() {
        // given
        // when
        mqttConfig.clear();
        // then
        assertThat(mqttConfig).isEqualTo(new MqttConfig("","",0,0));
    }
}