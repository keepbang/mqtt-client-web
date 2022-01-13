package com.bang.mqttclient.handler;

import com.bang.mqttclient.dto.MqttConfig;
import com.bang.mqttclient.dto.RequestMessage;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Profile("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MqttHandlerTest {

    @Autowired
    private MqttHandler mqttHandler;

    private static MqttConfig mqttConfig;

    @BeforeAll
    public static void setUp() {
        String broker = "tcp://192.168.3.170:8883";
        String clientId = "test_client_id";
        Integer keepAliveInterval = 60;
        Integer connectionTimeout = 30;
        mqttConfig = new MqttConfig(broker, clientId, keepAliveInterval, connectionTimeout);
    }

    @Test
    @DisplayName("브로커 연결 테스트")
    @Order(1)
    void connected() {
        // given
        // when
        boolean isConnect = mqttHandler.connect(mqttConfig);
        // then
        assertThat(isConnect).isTrue();
    }

    @Test
    @DisplayName("메세지 발행 테스트")
    @Order(2)
    void publish() {
        // given
        RequestMessage requestMessage = new RequestMessage("test", "test message");
        // when
        boolean isPublish = mqttHandler.publish(requestMessage);
        // then
        assertThat(isPublish).isTrue();
    }

    @Test
    @DisplayName("mqtt client 상태 테스트")
    @Order(3)
    void statusUp() {
        // given
        // when
        boolean actual = mqttHandler.status();
        // then
        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("mqtt client 연결 정보 확인")
    @Order(4)
    void checkConfig() {
        // given
        // when
        MqttConfig actual = mqttHandler.getConfig();
        // then
        assertThat(actual).isEqualTo(mqttConfig);
    }

    @Test
    @DisplayName("브로커 disconnect 테스트")
    @Order(5)
    void disconnect() {
        // given
        // when
        boolean isDisconnected = mqttHandler.disconnect();
        // then
        assertThat(isDisconnected).isTrue();
    }

    @Test
    @DisplayName("mqtt client 연결 끊김")
    @Order(6)
    void statusDown() {
        // given
        // when
        boolean actual = mqttHandler.status();
        // then
        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("mqtt client 상태 초기화")
    @Order(7)
    void checkConfigClear() {
        // given
        // when
        MqttConfig actual = mqttHandler.getConfig();
        mqttConfig.clear();
        // then
        assertThat(actual).isEqualTo(mqttConfig);
    }

}