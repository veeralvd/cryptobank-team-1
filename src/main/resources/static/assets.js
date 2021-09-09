
/*
let abbreviation = "?abbreviation" + abbreviation
let url = "http://localhost:8080/assets/doge"
*/
let url = new URL(window.location.href)
console.log(url.origin)
const options = {
    method: "POST",
    headers: {
        "Content-type": "application/json"
    }
}

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
