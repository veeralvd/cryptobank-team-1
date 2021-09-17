let options = {
    method: 'GET',
    headers: {
        'Content-Type': 'application/json',
        'Authorization': localStorage.getItem('Authorization')
    }
}


 function getPortfolio() {
    const urlPortfolio = "http://localhost:8080/portfolio"

     fetchTable(urlPortfolio, options, "#portfolioTable");
    document.title = "My Portfolio";
}

document.addEventListener("DOMContentLoaded", function (){
    getPortfolio();
})


document.querySelector("#myportfolio").addEventListener('click',
     function (event) {
        event.preventDefault()
        const urlPortfolio = "http://localhost:8080/portfolio"

        getPortfolio();

     }, false);

async function fetchWithRetry(x, ...args) {

        let firstTry = await x(...args);
        console.log(firstTry);
        console.log("In de fetch with retry, na de fetch!!!" )
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
                    console.log("fetch aangeroepen");
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


 function fetchTable(url, options, tableId) {
    fetchWithRetry(fetch, url, options)
        .then(response => {
            console.log("In de fetchTable")
            if (response.ok) {
                response.json()
                    .then(json => {
                        let jason = json;
                        let table = document.querySelector(tableId);
                        table.innerHTML = "";
                        let data = Object.keys(jason[0]);
                        generateTableHead(table, data);
                        generateTable(table, jason);
                    })
                    .catch((error) => {
                        console.error('Error' + error);
                    })
            } else if(!response.ok) {
                return response.status

            }else {
               document.querySelector('#portfolio').insertAdjacentText("afterbegin", 'Je portfolio is nog leeg!')
            }
        })
}

function generateTableHead(table, data) {
    let thead = table.createTHead();
    let row = thead.insertRow();
    for (let key of data) {
        let th = document.createElement("th");
        let text = document.createTextNode(key);
        th.appendChild(text);
        row.appendChild(th);
    }
}

function generateTable(table, data) {
    for (let element of data) {
        let row = table.insertRow();
        if (element === null) {
            element = "-"
        }
        for (let key in element) {
            let cell = row.insertCell();
            let text = document.createTextNode(element[key]);
            cell.appendChild(text);
        }
    }
}

function updateHeaderOptions() {
    options.headers.Authorization = localStorage.getItem('Authorization');
}

