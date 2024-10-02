var process = process || {env: {NODE_ENV: "development"}};
var csrfValue = document.querySelector("meta[name='_csrf']").attributes['content'].value;
console.log(csrfValue)



document.getElementById("catalog-form").addEventListener("submit", function(e){
    
})

document.getElementById("orderSubmit").addEventListener("click", function(e){
    //e.preventDefault();
    console.log(csrfValue)
    var csrfEl = document.getElementById("submissionInput");
    csrfEl.value = csrfValue;

    var inputs = document.getElementsByTagName("input");
    console.log("Catalog Inputs")
    console.log(inputs["exampleInputPassword1"].value)
    var obj = new Object();
    obj.orderNow = true
    var catform = document.getElementById("catalog-form");
    //catform.submit();


    //const formDataJsonString = JSON.stringify(obj);
    /*
    fetch('/service-catalog/catalog/7/add', {
        method: 'POST',
		headers: {
			"Content-Type": "application/json",
			"Accept": "application/json"
		},
        body: formDataJsonString
      })
      .then(response => response.json())
      .then(result => {
        console.log('Success:', result);
      })
      .catch(error => {
        console.error('Error:', error);
      });
      */
});

/* Password Peek */
/*
document.getElementById("eye").addEventListener("click", function(e){
    var x = document.getElementById("exampleInputPassword1");
    if (x.type === "password") {
    x.type = "text";
    } else {
    x.type = "password";
    }
})
*/

/* Function to generate combination of password */
function generatePass() {
    let pass = '';
    let str = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ' +
        'abcdefghijklmnopqrstuvwxyz0123456789@#$';

    for (let i = 1; i <= 8; i++) {
        let char = Math.floor(Math.random()
            * str.length + 1);

        pass += str.charAt(char)
    }

    return pass;
}

console.log(document.querySelectorAll("select"));
document.getElementById("generateRandomPassword").addEventListener("click", function(e){
     passwordField = document.getElementById("exampleInputPassword1")
     passwordField.value = generatePass()
     passwordField.focus()
})
