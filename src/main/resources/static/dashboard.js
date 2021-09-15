const options = {
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

window.onload = getPortfolio();

// document.getElementById("myPortfolio").addEventListener('click',
//     function (event) {
//         event.preventDefault()
//         const urlPortfolio = "http://localhost:8080/portfolio"
//
//         //TODO nu leeg scherm bij een 403 geen authorization, moet opnieuw inloggen
//         fetchTable(urlPortfolio, options, "#portfolioTable");
//         document.title = "My Portfolio";
//
//     }, false);



function fetchTable(url, options, tableId) {
    fetch(url, options)
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
                        console.error('Error' + error);
                    })
            } else {
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