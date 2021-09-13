const URL = window.location.href;

getToken();

function getToken(){
    const stringUrl = URL.split(`=`)
    const token = stringUrl[1];
    console.log(token);

    localStorage.setItem('Authorization', token);
    console.log()
}

document.querySelector('#resetPassword').addEventListener(`click`,
    function (event){
        event.preventDefault() // anders wordt de gebruiker naar andere pagina geleidt
        // // selecteer inputs en zet waarden omde normale submit functie uitgevoerd


        let password = String(document.querySelector('#password').value)
        let passwordConf = String(document.querySelector('#passwordConf').value)

        let passwordsAreEqual = Boolean(password === passwordConf);
        // Moet de check op gelijke wachtwoorden hier?
        if (!passwordsAreEqual) {
            console.log('Passwords not the same')
        }

        // Stuurt username en password naar de server
        let data = '?password=' + password
        const url = `http://localhost:8080/submitnewpassword` + data;
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
                    // Door naar volgende pagina/scherm
                    // window.location.assign("http://localhost:8080/assets.html")
                }else if (!response.ok) {
                    console.log('Failed to save new password')
                }})
            .catch((error) => {
                console.error('Error ' + error);
            })

    })