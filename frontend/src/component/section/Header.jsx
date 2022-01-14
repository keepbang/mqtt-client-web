import React, {useEffect, useState} from 'react';
import styled from 'styled-components';
import {Alert, Button, Snackbar, TextField} from "@mui/material";
import { v4 as uuidv4 } from 'uuid';
import {mqttApi} from "../../api/MqttApi";

const Wrapper = styled.div`
    width: 300px;
    height: 100%;
    padding: 0px 30px;
    display: flex;
    border-right: 1px solid rgba(185,185,185,0.5);
    flex-direction: column;
    
    & > div:first-child {
        margin-top: 30px;
    }
    
    & > div:not(:last-child) {
        margin-bottom: 20px;
    }
    
    & > button {
        height: 30px;
        margin-bottom: 20px;
    }
`;

const initMqttConfig = {
    broker: "tcp://localhost:1883",
    clientId: "",
    keepAliveInterval: 60,
    connectionTimeout: 30
}

const Header = ({connected, onConnected}) => {
    const [mqttConfig, setMqttConfig] = useState(initMqttConfig);
    const [open, setOpen] = useState(false);

    useEffect(() => {
        mqttApi.status()
            .then(data => {
                onConnected(data);
                if(data) {
                    mqttApi.get()
                        .then(data => {
                            setMqttConfig(data);
                        })
                        .catch(error => console.log(error));
                }
            })
            .catch(error => console.log(error));
    }, []);

    const onChangeHandler = (e) => {
        const {name, value} = e.target;

        const changer = {
            ...mqttConfig,
            [name]: value
        };

        setMqttConfig(changer);
    }

    const generateClientId = () => {
        let clientId = uuidv4().replaceAll('-','');
        setMqttConfig({
            ...mqttConfig,
            clientId: clientId
        })
    }

    const connectMqtt = () => {
        if(!connected) {
            mqttApi.connect(mqttConfig)
                .then(data => {
                    onConnected(data);
                    setOpen(true);
                })
                .catch(error => console.log(error));
        }
    }

    const disconnectMqtt = () => {
        if(connected) {
            mqttApi.disconnect()
                .then(data => {
                    onConnected(!data);
                    setOpen(true);
                })
                .catch(error => console.log(error));
        }
    }

    const handleClose = () => {
        setOpen(false);
    };

    return (
        <Wrapper>
            <TextField id="broker-url" name="broker" label="broker url" placeholder="tcp://localhost:1883" variant="outlined" value={mqttConfig.broker} onChange={onChangeHandler}/>
            <TextField id="client-id" name="clientId" label="client id" variant="outlined" value={mqttConfig.clientId} onChange={onChangeHandler}/>
            <Button variant="contained" onClick={generateClientId}>Generate Id</Button>
            <TextField id="keep-alive-interval" name="keepAliveInterval" type={"number"} label="keepalive interval" variant="outlined" value={mqttConfig.keepAliveInterval} onChange={onChangeHandler}/>
            <TextField id="connection-timeout" name="connectionTimeout" type={"number"} label="connection timeout" variant="outlined" value={mqttConfig.connectionTimeout} onChange={onChangeHandler}/>
            <Button variant="contained" onClick={connectMqtt} color="success" disabled={connected}>Connect</Button>
            <Button variant="contained" onClick={disconnectMqtt} color="warning" disabled={!connected}>Disconnect</Button>
            <Snackbar
                open={open}
                autoHideDuration={3000}
                onClose={handleClose}
            >
                <Alert onClose={handleClose} severity={connected ? "success" : "error"}>
                    {connected ? 'Connect mqtt broker' : 'Not connected'}
                </Alert>
            </Snackbar>
        </Wrapper>
    );
}

export default Header;