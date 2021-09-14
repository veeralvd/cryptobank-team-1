const options = {
    method: 'GET',
    headers: {
        'Content-Type': 'application/json',
        'Authorization': localStorage.getItem('Authorization')
    }
}
const urlAssets = "http://localhost:8080/assets"

showAllAssets()

document.getElementById("myPortfolio").addEventListener('click',
    function (event) {
        event.preventDefault()
        const urlPortfolio = "http://localhost:8080/portfolio"

        fetchTable(urlPortfolio, options, "#portfolioTable");
        document.title = "My Portfolio";
        document.getElementById("allAssets").style.display="none"
        document.getElementById("showPortfolio").style.display="block"
    }, false);


document.getElementById("showAssets").addEventListener('click',
    function (event) {
        event.preventDefault()
        fetchTable(urlAssets, options, "#assetTable");
        document.getElementById("showPortfolio").style.display="none"
        document.getElementById("allAssets").style.display="block"
    }, false);


function showAllAssets() {
    document.getElementById("allAssets")
    {
        fetchTable(urlAssets, options, "#assetTable");
    }
}

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

