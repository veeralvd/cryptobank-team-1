




async function postFormDataAsJson({url, formData}) {

    const plainFormData = Object.fromEntries(formData.entries());
    const formDataJsonString = JSON.stringify(plainFormData);

    const fetchOptions = {
        method: 'PUT',
        headers: {
            "Content-Type": "application/json",
        },
        body: formDataJsonString,
        mode: 'no-cors',
    };

    const response = await fetch(url, fetchOptions);

    if (!response.ok) {
        const errorMessage = await response.text();
        throw new Error(errorMessage);
    }

    return response.json();
}
async function handleFormSubmit(event) {
    event.preventDefault();

    const form = event.currentTarget;
    const url = loginform.action;

    try {
        const formData = new FormData(form);
        
        const responseData = await postFormDataAsJson({url, formData});
        
        console.log({responseData})
    } catch (error) {
        console.error(error);
    }
}

const loginform = document.getElementById('loginform')

loginform.addEventListener('submit', handleFormSubmit);



