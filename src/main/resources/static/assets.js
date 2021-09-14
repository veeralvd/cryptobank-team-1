//document.getElementById("allAssets"). {
const urlAssets = "http://localhost:8080/assets"
const optionsAssets = {
    method: 'GET',
    headers: {
        'Content-Type': 'application/json',
        'Authorization': localStorage.getItem('Authorization')
    }
}
fetchTable(urlAssets, optionsAssets);


document.getElementById("showPortfolio").addEventListener('click',
    function (event) {
        event.preventDefault()
        const urlPortfolio = "http://localhost:8080/portfolio"
        const options = {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': localStorage.getItem('Authorization')
            }
        }
        fetchTable(urlPortfolio, options);
        document.title = "My Portfolio";
        document.getElementById("allAssets").style.display="none"
        document.getElementById("showPortfolio").style.display
        /*document.getElementById("showPortfolio").style.display="none"
        document.getElementById("showAssets").style.display="block"
        document.getElementById("assetsTable").style.display="none"
        document.getElementById("portfolioTable").style.display="block"*/
    }, false);


document.getElementById("showAssets").addEventListener('click',
    function (event) {
        event.preventDefault()
        fetchTable(urlAssets, optionsAssets);
        document.getElementById("showAssets").style.display="none"
        document.getElementById("showPortfolio").style.display="block"
        document.getElementById("portfolioTable").style.display="none"
        document.getElementById("assetsTable").style.display="block"
    }, false);


function fetchTable(url, options) {
    fetch(url, options)
        .then(response => {
            if (response.ok) {
                response.json()
                    .then(json => {
                        let jason = json;
                        let table = document.querySelector("table");
                        let data = Object.keys(jason[0]);
                        generateTableHead(table, data);
                        generateTable(table, jason);

                    })
                    .catch((error) => {
                        console.error('Error' + error);
                    })
            } else {
                console.log('niet gelukt')
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




/*let abbreviation = "?abbreviation" + abbreviation
let url = "http://localhost:8080/assets/doge"*/

/*let url = new URL(window.location.href)
console.log(url.origin)
const options = {
    method: "POST",
    headers: {
        "Content-type": "application/json"
    }
}*/

//relatieve url:
/*fetch(`${url.origin}/{abbreviation}`)
    .then(response => response.json())
    .then(data => console.log(data));*/

