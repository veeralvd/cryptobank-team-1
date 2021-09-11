const URL = window.location.href;

getToken();

function getToken(){
    const stringUrl = URL.split(`=`)
    const token = stringUrl[1];
    console.log(token);

    console.log(token.substring(9));
    let tokenString = "Bearer " + token.substring(9);
    localStorage.setItem('Authorization', tokenString);
}

document.querySelector('#resetPassword').addEventListener(`click`,
    function (event){
        event.preventDefault() // anders wordt de gebruiker naar andere pagina geleidt
        // // selecteer inputs en zet waarden omde normale submit functie uitgevoerd


        let password = String(document.querySelector('#username').value)
        let passwordConf = String(document.querySelector('#password').value)

        let passwordsAreEqual = Boolean(password === passwordConf);

        // Stuurt username en password naar de server
        let data = '?password=' + password
        const url = `http://localhost:8080/login` + data;
        const options = {
            method: `POST`,
            headers: {
                'Content-Type': 'application/json'
            }
        }
        // Token komt terug als username & password erkent wordt
        fetch(url,options)
            .then(response => {
                if (response.ok){
                    response.text();
                    let accessToken = response.headers.get('Authorization');
                    let refreshToken = response.headers.get('refresh_token');
                    console.log(accessToken + "hallo access");
                    console.log(refreshToken + "hallo refresh");
                    localStorage.setItem('Authorization', accessToken);
                    localStorage.setItem('refresh_token', refreshToken);
                    window.location.assign("http://localhost:8080/assets.html")
                }else if (!response.ok) {
                    console.log('username and/or password are incorrect')
                }})
            .catch((error) => {
                console.error('Error ' + error);
            })

    })