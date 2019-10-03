console.log("Hi there, I'm client side JS")

const clientForm = document.getElementById('submit');
const form =document.getElementById("regForm");
const firstname = document.getElementById("firstname");
const lastname = document.getElementById("lastname");
const driverslicense = document.getElementById("driverslicense");
const date = document.getElementById("expiration");
const phone = document.getElementById("phone");
const errDl = document.getElementById("errDl");


clientForm.addEventListener('click',function (e) {
    
    // $messageButton.setAttribute('disabled','disabled')
    if(!checkLicense()){
        errDl.innerHTML = "Invalid license!";
        e.preventDefault();
    }
    else {

    try{
        fetch("/create-client",
        {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                // 'Authorization': 'Bearer ' + window.localStorage.getItem('token')
            },
            method: "POST",
            body: JSON.stringify({ firstname: firstname.value, lastname: lastname.value, phone: phone.value, driverslicense: driverslicense.value, expiration: expiration.value })
        }).then(function(response){
            console.log(response.status);
        })
   window.location.href = 'http://localhost:8080/client-register');
    }
    catch (error) {
    console.error('Error:', error);
    }

    }
});

function checkLicense () {
          var re1='([a-z])';	// Any Single Word Character (Not Whitespace) 1
          var re2='(-)';	// Any Single Character 1
          var re3='(\\d)';	// Any Single Digit 1
          var re4='(\\d)';	// Any Single Digit 2
          var re5='(\\d)';	// Any Single Digit 3
          var re6='(\\d)';	// Any Single Digit 4
          var re7='(-)';	// Any Single Character 2
          var re8='(\\d)';	// Any Single Digit 5
          var re9='(\\d)';	// Any Single Digit 6
          var re10='(\\d)';	// Any Single Digit 7
          var re11='(\\d)';	// Any Single Digit 8
          var re12='(\\d)';	// Any Single Digit 9
          var re13='(\\d)';	// Any Single Digit 10
          var re14='(-)';	// Any Single Character 3
          var re15='(\\d)';	// Any Single Digit 11
          var re16='(\\d)';	// Any Single Digit 12
          var p = new RegExp(re1+re2+re3+re4+re5+re6+re7+re8+re9+re10+re11+re12+re13+re14+re15+re16,["i"]);
  str = driverslicense.value
  if (!str.match(p)) {
    return false;
  } else {
  return true;
  }
 }