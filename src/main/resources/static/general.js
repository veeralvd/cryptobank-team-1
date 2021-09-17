

async function fetchWithRetry(x, ...args) {
    let firstTry = await x(...args);
    if (firstTry.status === 403) {
        console.log("toRefresh in ieder geval gestart")
        const urlRefresh = "http://localhost:8080/refresh"
        const options = {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': localStorage.getItem('refresh_token')
            }
        }
        await fetch(urlRefresh, options)
            .then(response => {
                if (response.ok) {
                    localStorage.setItem('Authorization', response.headers.get('Authorization'));
                    localStorage.setItem('refresh_token', response.headers.get('refresh_token'));
                    updateHeaderOptions();
                    return  x(...args);
                } else {
                    alert("Session expired. Login to regain access.")
                    window.location.assign('http://localhost:8080/index.html')
                }
            })
            .catch((error) => {
                console.log('Error: ' + error)
            })
    }
    return firstTry;
}

function updateHeaderOptions() {
    options.headers.Authorization = localStorage.getItem('Authorization');
}

export {fetchWithRetry}