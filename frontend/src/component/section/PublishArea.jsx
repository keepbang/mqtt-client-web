import React, {useState} from 'react';
import styled from 'styled-components';
import {Alert, AlertTitle, Button, Snackbar, TextField} from "@mui/material";
import {mqttApi} from "../../api/MqttApi";

const Wrapper = styled.div`
    width: 300px;
    height: 100%;
    padding: 30px 30px 0px;
    display: flex;
    border-right: 1px solid rgba(185,185,185,0.5);
    flex-direction: column;
    
    & > div:not(:last-child) {
        margin-bottom: 20px;
    }
`;

const initMessage = {
    topic: '',
    payload: ''
}

const PublishArea = ({connected}) => {
    const [message, setMessage] = useState(initMessage);
    const [open, setOpen] = useState(false);

    const changeMessage = (e) => {
        const {name, value} = e.target;

        setMessage({
            ...message,
            [name]: value
        });
    }

    const publishMessage = () => {
        mqttApi.publish(message)
            .then(data => {
                console.log(data);
                setOpen(true);
            })
            .catch(error => console.log(error));
    }

    const handleClose = () => {
        setOpen(false);
    }

    return (
        <Wrapper>
            <TextField id="topic-id" name="topic" label="topic" placeholder="/PUBLISH/MESSAGE" variant="outlined" value={message.topic} onChange={changeMessage} size="small"/>
            <TextField
                id="payload"
                name="payload"
                label="payload"
                multiline
                rows={10}
                value={message.payload}
                onChange={changeMessage}
            />
            <Button variant="outlined" onClick={publishMessage}>Publish message</Button>
            <Snackbar
                open={open}
                autoHideDuration={3000}
                onClose={handleClose}
            >
                <Alert severity="success">
                    <AlertTitle>Success</AlertTitle>
                    Publish message
                </Alert>
            </Snackbar>
        </Wrapper>
    );
}

export default PublishArea;