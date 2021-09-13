
document.getElementById("showAssets").addEventListener('click',
    function (event) {
    event.preventDefault()
    const urlAssets = "http://localhost:8080/assets"
    const options = {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': localStorage.getItem('Authorization')
        }
    }
    fetchTable(urlAssets, options);
    document.getElementById("showAssets").style.display="none"
    })


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
        console.log(localStorage.getItem('Authorization'))
        //fetchTable(urlPortfolio, options);
        fetch(urlPortfolio, options)
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
    })


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


// let table = document.querySelector("assetTable");
// let data = Object.keys(assetsReturned[0]);
// generateTableHead(table, data);

/*
let abbreviation = "?abbreviation" + abbreviation
let url = "http://localhost:8080/assets/doge"
*/
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


/*const assets = [
    {"abbreviation":"ADA","name":"Cardano","description":"ADA","rate":null},
    {"abbreviation":"BCH","name":"Bitcoin Cash","description":"BCH","rate":null},
    {"abbreviation":"BNB","name":"Binance Coin","description":"BNB","rate":null},
    {"abbreviation":"BTC","name":"Bitcoin","description":"BTC","rate":null},
    {"abbreviation":"BUSD","name":"Binance USD","description":"BUSD","rate":null},
    {"abbreviation":"CAKE","name":"PancakeSwap","description":"pannekoek, goed idee voor lunch","rate":null},
    {"abbreviation":"DOGE","name":"Dogecoin","description":"DOGE","rate":null},
    {"abbreviation":"DOT","name":"Polkadot","description":"polka klinkt wel lekker pools en gezellig","rate":null},
    {"abbreviation":"ETH","name":"Ethereum","description":"ETH","rate":null},
    {"abbreviation":"HEX","name":"HEX","description":"HEX","rate":null},
    {"abbreviation":"LINK","name":"Chainlink","description":"LINK","rate":null},
    {"abbreviation":"LTC","name":"Litecoin","description":"LTC","rate":null},
    {"abbreviation":"LUNA","name":"Terra","description":"LUNA","rate":null},
    {"abbreviation":"MATIC","name":"Polygon","description":"MATIC","rate":null},
    {"abbreviation":"SOL","name":"Solana","description":"SOL is een prima zomerbiertje","rate":null},
    {"abbreviation":"UNI","name":"Uniswap","description":"UNI","rate":null},
    {"abbreviation":"USDC","name":"USDC","description":"USDC","rate":null},
    {"abbreviation":"USDT","name":"Tether USD","description":"USDT","rate":null},
    {"abbreviation":"WBTC","name":"Wrapped BTC","description":"een wrap is altijd lekker","rate":null},
    {"abbreviation":"XRP","name":"XRP","description":"XRP","rate":null}
]*/
