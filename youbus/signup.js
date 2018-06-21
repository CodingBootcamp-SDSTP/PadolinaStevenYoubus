window.onload = function() {
	document.getElementById("cancel").onclick = function() {
		window.location.href = "http://localhost:8080/youbus/";
	}
	document.getElementById("register").onclick = function() {
		let pass = document.getElementById("password");
		let pass1 = document.getElementById("password1");
		if(pass.value != pass1.value) {
			alert("Password does not match");
			pass.value = null;
			pass1.value = null;
		}
		else {
			let body = 
				"firstname=" + document.getElementById("firstname").value +
				"&lastname=" + document.getElementById("lastname").value +
				"&sex=" + document.getElementById("sex").value +
				"&birthdate=" + document.getElementById("birthdate").value +
				"&email=" + document.getElementById("email").value +
				"&username=" + document.getElementById("username").value +
				"&password=" + document.getElementById("password").value + "&accounttype=user&type=user";
			postRequest("/youbus/register", body, function(response) {
				alert(response);
				window.location.href = "http://localhost:8080/youbus/";
			});
		}
	}
}

function postRequest(url, body, callback) {
	let rq = new XMLHttpRequest();
	rq.onreadystatechange = function() {
		if(rq.readyState == 4) {
			callback(rq.responseText);
		}
	};
	rq.open("POST", url, true);
	rq.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	rq.send(body);
}
