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

const usernameInput = document.querySelector('#username');
const passwordInput = document.querySelector('#password');
const firstNameInput = document.querySelector('#firstName');
const lastNameInput = document.querySelector('#lastName');
const socialSecurityNumberInput = document.querySelector('#socialSecurityNumber');
const streetInput = document.querySelector('#street');
const emailInput = document.querySelector('#email');
const small1 = document.querySelector('#line1');

const isRequired = value => value === '' ? false : true;
const isBetween = (length, min, max) => length < min || length > max ? false : true;
const isEmailValid = (email) => {
    const charsNotAllowed = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return charsNotAllowed.test(email);
}
const isPasswordOK = (password) => {
    const toCheck = new RegExp("^(?=.*[a-z])(?=.{8,})");
    return toCheck.test(password);
}

const showError = (input, message) => {
    input.classList.remove('success');
    input.classList.add('error');
    const error = input.querySelector('small');
    error.textContent = message;
}

const showSuccess = (input) => {
    input.classList.remove('error');
    input.classList.add('success');
    const error = input.querySelector('small');
    error.textContent = '';
}

const checkUsername = () => {
    let valid = false;
    const min = 4, max = 25;

    if(!isRequired(usernameInput)) {
        showError(small1, 'Username can not be blank')
    } else if (!isBetween(usernameInput.length, min, max)) {
        showError(small1, `Length must be between ${min} and ${max}`)
    } else {
        showSuccess(small1);
        valid = true;
    }
    return valid;
}
