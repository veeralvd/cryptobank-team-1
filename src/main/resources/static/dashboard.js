//import {fetchWithRetry, updateHeaderOptions} from "./general.js";

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
console.log("VOOR FETCH RETRY")

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
        let refreshTry = await fetch(urlRefresh, options);
        if (refreshTry.status === 200) {
            localStorage.setItem('Authorization', refreshTry.headers.get('Authorization'));
            localStorage.setItem('refresh_token', refreshTry.headers.get('refresh_token'));
            updateHeaderOptions();
            return x(...args);
        } else {
            alert("Session expired. Login to regain access.")
            window.location.assign('http://localhost:8080/index.html')
        }
    }
    return firstTry;
}

function updateHeaderOptions() {
    options.headers.Authorization = localStorage.getItem('Authorization');
}

 function fetchTable(url, options, tableId) {
    fetchWithRetry(fetch, url, options)
        .then(response => {
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
                        document.querySelector('#portfolio').insertAdjacentText("afterbegin", 'Je portfolio is nog leeg!')
                        //console.error('Error' + error);
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

const urlPortfolioValue = "http://localhost:8080/getportfoliovalue";

function getPortfolioValue() {
    fetchTotalValue(urlPortfolioValue, options, '#currentBalance');
    document.title = "Total Portfolio Value";
}
document.addEventListener("DOMContentLoaded", function (){
    getPortfolioValue();
})

function fetchTotalValue(url, options, id){
    fetchWithRetry(fetch, url, options)
        .then(response => {
            if(response.ok){
                response.json()
                    .then(json => {
                        let value = json;
                        document.querySelector('#currentBalance').insertAdjacentText('beforeend', value);
                    })
                    .catch((error) => {
                        console.error('Error' + error);
                    })
            }
        })
}


