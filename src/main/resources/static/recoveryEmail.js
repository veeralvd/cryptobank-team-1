
document.querySelector('#sendLink').addEventListener('click', (e) => {
    e.preventDefault();
    let email = String(document.querySelector('#recoveryemail').value);
    const url = 'http://localhost:8080/api/forgot/password?email='+email;
    const options = {
        method: `POST`,
        headers: {
            'Content-Type': 'application/json'
        }
    }

    fetch(url, options)
        .then(response => {
            if (response.ok) {
                console.log(`Recovery email sent to ${email}`);
                document.querySelector('#recoveryform').style.visibility="hidden";
                document.querySelector('#emailsuccesful').style.visibility="visible";
                document.querySelector('#emailholder').innerHTML = `${email}`;
            }
            if(!response.ok) {
                alert('Email not known')
            }
        })
        .catch((error) => {
            console.log(error);
        })

})