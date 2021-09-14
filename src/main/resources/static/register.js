document.querySelector('#register').addEventListener('click',
    function (event){
        console.log("hallo");
        event.preventDefault();

        let formData = new FormData(document.querySelector('#registrationForm'));

        console.log(formData.toString());
        formData.forEach(i => console.log(i))
        console.log(formData.entries());
        let plainFormData = Object.fromEntries(formData.entries());
        let personalFormDataJsonString = JSON.stringify(plainFormData);
        console.log(personalFormDataJsonString)


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
            },
            body: personalFormDataJsonString
        }

        // Token komt terug als username & password erkend wordt
        fetch(url,options)
            .then(response => {
                if (response.ok){
                    response.text();
                    alert('User registered');
                    window.location.assign("http://localhost:8080/index.html")
                }else if (!response.ok) {
                    console.log('username and/or password are incorrect')
                }})
            .catch((error) => {
                console.error('Error ' + error);
            })

    })