//@author Branko V


    document.querySelector('#submit').addEventListener('click',
        function (event){
        event.preventDefault()
            let username = document.querySelector('#username').value
            let password = document.querySelector('#password').value
            console.log(username, password)
        })


