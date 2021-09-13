const URL = window.location.href;

getToken();

function getToken(){
    const stringUrl = URL.split(`=`)
    const token = stringUrl[1];
    console.log(token);
    if (token === undefined) {
        document.getElementById('submitNewPasswordForm').style.display="none"
    } else {
        localStorage.setItem('Authorization', token);
        document.getElementById('sumbitEmailForm').style.display="none"
    }
}

document.querySelector('#resetPassword').addEventListener(`click`,
    function (event){
        event.preventDefault() // anders wordt de gebruiker naar andere pagina geleidt
        // // selecteer inputs en zet waarden omde normale submit functie uitgevoerd


        let password = String(document.querySelector('#password').value)
        let passwordConf = String(document.querySelector('#passwordConf').value)

        let passwordsAreEqual = Boolean(password === passwordConf);

        if (!passwordsAreEqual) {

            //TODO We moeten nog functionaliteit voor wanneer de wachtwoorden NIET kloppen volgens onze richtlijnen OF
            // als de wachtwoorden niet gelijk zijn aan elkaar
            console.log('Passwords not the same')
        }

        else {

            // Stuurt username en password naar de server
            let data = '?password=' + password
            const url = `http://localhost:8080/api/forgot/submit` + data;
            const options = {
                method: `POST`,
                headers: {
                    'Content-Type': 'application/json'
                }
            }
            // Token komt terug als username & password erkent wordt
            fetch(url, options)
                .then(response => {
                    if (response.ok) {
                        response.text();

                        // Door naar volgende pagina/scherm
                        window.location.assign("http://localhost:8080/index.html")
                    } else if (!response.ok) {
                        console.log('Failed to save new password')
                    }
                })
                .catch((error) => {
                    console.error('Error ' + error);
                })
        }
    })

document.getElementById('submitEmail').addEventListener('click',
    function (event) {
    event.preventDefault();

    let email = String(document.querySelector('#emailToSubmit').value);
    let data = '?email=' + email;
    const url = 'http://localhost:8080/api/forgot/password' + data;
        const options = {
            method: `POST`,
            headers: {
                'Content-Type': 'application/json'
            }
        }
        fetch(url, options)
            .then(response => {
                if (response.ok) {
                    alert("Request for changing password send.")
                    window.location.assign("http://localhost:8080/index.html")
                } else if (response.status.valueOf()=== 418){
                    document.getElementById('inlogheader').innerHTML = "Email not found";
                }
            })
            .catch((error) => {
                console.error('Error ' + error);
            })
    })

