package com.bang.mqttclient.application;

import com.bang.mqttclient.dto.MqttConfig;
import com.bang.mqttclient.dto.RequestMessage;
import com.bang.mqttclient.handler.MqttHandler;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MqttController {

    private final MqttHandler mqttHandler;

    public MqttController(MqttHandler mqttHandler) {
        this.mqttHandler = mqttHandler;
    }

    @PostMapping(value = "/v1/connect", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> mqttConnect(
            @RequestBody MqttConfig mqttConfig
    ) {
        return ResponseEntity.ok(mqttHandler.connect(mqttConfig));
    }

    @PostMapping("/v1/publish")
    public ResponseEntity<Boolean> publish(
            @RequestBody RequestMessage requestMessage
    ) {
        return ResponseEntity.ok(mqttHandler.publish(requestMessage));
    }

    @GetMapping("/v1/disconnect")
    public ResponseEntity<Boolean> disconnect() {
        return ResponseEntity.ok(mqttHandler.disconnect());
    }

    @GetMapping("/v1/status")
    public ResponseEntity<Boolean> status() {
        return ResponseEntity.ok(mqttHandler.status());
    }

    @GetMapping("/v1/config")
    public ResponseEntity<MqttConfig> getConfig() {
        return ResponseEntity.ok(mqttHandler.getConfig());
    }
}
