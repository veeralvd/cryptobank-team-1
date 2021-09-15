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

        //TODO nu leeg scherm bij een 403 geen authorization, moet opnieuw inloggen
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

document.getElementById("buy").addEventListener("click", function (event) {
    event.preventDefault()

    let asset = String(document.querySelector("#asset").value)
    let amount = Number(document.querySelector("#amount").value)
    let data = "?assetAbbr=" + asset + "&assetAmount=" + amount
    const urlBuy = "http://localhost:8080/buyasset" + data;
    const optionsPost = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': localStorage.getItem('Authorization')
        }
    }
    fetch(urlBuy, optionsPost)
        .then(response => {
            if (response.ok) {
                response.text(); //TODO uitlegd krijgen wat dit doet
                alert("Assets gekocht")
                //TODO blijven we op hetzelfde scherm? Gaan we elders heen? Wat ziet de gebruiker?
            } else if (!response.ok) {
                alert("Failed to buy assets")
                console.log("Failed to buy assets")
            }
        })
        .catch((error) => {
            console.log("Error " + error);
        })
})


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
                console.log('Failed to fetch data for table')
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

