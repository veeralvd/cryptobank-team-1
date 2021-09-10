document.querySelector('#register').addEventListener('click',
    function (event){
        console.log("hallo");
        event.preventDefault();

        let registerForm = document.getElementById('#registrationForm')
        let formData = new FormData(registerForm)

        let plainFormData = Object.fromEntries(formData.entries());
        let formDataJsonString = JSON.stringify(plainFormData);


        console.log(formDataJsonString)
        // let username = String(document.querySelector('#username').value)
        // let password = String(document.querySelector('#password').value)
        // let firstName = String(document.querySelector('#firstName').value)
        // let lastName = String(document.querySelector('#lastName').value)
        // let dateOfBirth = String(document.querySelector('#dateOfBirth').value)
        // let socialSecurityNumber = String(document.querySelector('#socialSecurityNumber').value)
        // let street = String(document.querySelector('#street').value)
        // let zipcode = String(document.querySelector('#zipcode').value)
        // let housenumber = String(document.querySelector('#housenumber').value)
        // let addition = String(document.querySelector('#addition').value)
        // let city = String(document.querySelector('#city').value)
        // let email = String(document.querySelector('#email').value)


        // Stuurt username en password naar de server
        const url = `http://localhost:8080/register`
        const options = {
            method: `POST`,
            headers: {
                'Content-Type': 'application/json'
            }
        }
        // Token komt terug als username & password erkent wordt
        // fetch(url,options)
        //     .then(response => {
        //         if (response.ok){
        //             response.text();
        //
        //             window.location.assign("http://localhost:8080/login.html")
        //         }else if (!response.ok) {
        //             console.log('username and/or password are incorrect')
        //         }})
        //     .catch((error) => {
        //         console.error('Error ' + error);
        //     })

    })