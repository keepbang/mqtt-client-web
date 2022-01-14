export const mqttApi = {
    connect: async (mqttConfig) => await postApi('/v1/connect', mqttConfig),
    publish: async (message) => await postApi('/v1/publish', message),
    disconnect: async () => await getApi('/v1/disconnect'),
    status: async () => await getApi('/v1/status'),
    get: async () => await getApi('/v1/config')
}

function postApi(url, data) {
    return fetch(url, {
        method: 'post',
        mode: 'cors',
        cache: "no-cache",
        credentials: 'same-origin',
        headers: {
            'Content-Type': 'application/json'
        },
        redirect: 'follow',
        referrer: 'no-referrer',
        body: JSON.stringify(data),
    }).then(response => response.json());
}

function getApi(url, data) {
    return fetch(url, {
        method: 'get',
        mode: 'cors',
        cache: "no-cache",
        credentials: 'same-origin',
        headers: {
            'Content-Type': 'application/json'
        },
        redirect: 'follow',
        referrer: 'no-referrer',
        body: JSON.stringify(data),
    }).then(response => response.json());
}