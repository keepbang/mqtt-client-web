package com.bang.mqttclient.dto;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.Objects;

public class MqttConfig {
    private String broker;
    private String clientId;
    private Integer keepAliveInterval;
    private Integer connectionTimeout;

    protected MqttConfig() {
    }

    public MqttConfig(String broker, String clientId, Integer keepAliveInterval, Integer connectionTimeout) {
        this.broker = broker;
        this.clientId = clientId;
        this.keepAliveInterval = keepAliveInterval;
        this.connectionTimeout = connectionTimeout;
    }

    public MqttClient generateMqttClient() {
        try {
            return new MqttClient(broker, clientId);
        } catch(MqttException e) {
            e.printStackTrace();
            return null;
        }
    }

    public MqttConnectOptions generateMqttOptions() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setKeepAliveInterval(keepAliveInterval);
        options.setConnectionTimeout(connectionTimeout);
        options.setCleanSession(true);
        return options;
    }

    public void clear() {
        this.broker = "";
        this.clientId = "";
        this.keepAliveInterval = 0;
        this.connectionTimeout = 0;
    }

    public String getBroker() {
        return broker;
    }

    public String getClientId() {
        return clientId;
    }

    public Integer getKeepAliveInterval() {
        return keepAliveInterval;
    }

    public Integer getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setBroker(String broker) {
        this.broker = broker;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setKeepAliveInterval(Integer keepAliveInterval) {
        this.keepAliveInterval = keepAliveInterval;
    }

    public void setConnectionTimeout(Integer connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MqttConfig that = (MqttConfig) o;
        return Objects.equals(broker, that.broker)
                && Objects.equals(clientId, that.clientId)
                && Objects.equals(keepAliveInterval, that.keepAliveInterval)
                && Objects.equals(connectionTimeout, that.connectionTimeout);
    }

    @Override
    public int hashCode() {
        return Objects.hash(broker, clientId, keepAliveInterval, connectionTimeout);
    }
}
