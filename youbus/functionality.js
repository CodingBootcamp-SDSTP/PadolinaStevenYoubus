window.onload = function() {
	var locationid = "";
	document.getElementById("trips").onclick = function() {
		getRequest("/youbus/trips");
	};
	document.getElementById("buses").onclick = function() {
		getRequest("/youbus/buses");
	};
	document.getElementById("routes").onclick = function() {
		getRequest("/youbus/routes");
	};
	document.getElementById("locations").onclick = function() {
		getRequest("/youbus/locations");
	};
	document.getElementById("drivers").onclick = function() {
		getRequest("/youbus/drivers");
	};
	document.getElementById("conductors").onclick = function() {
		getRequest("/youbus/conductors");
	};

	document.getElementById("addroute").onclick = function() {
		let container = createElement("inputswrapper", "div", "inputscontainer", null);
		createElement("inputscontainer", "h2", null, null).innerHTML = "New Route";
		let routename = createElement("inputscontainer", "input", "routename", null);
		routename.placeholder = "Route Name";
		let origin = createElement("inputscontainer", "select", "origin", null);
		createElement("origin", "option", null, null).innerHTML = "Select Origin";
		testRequest("/youbus/locations", function(objects) {
			if(objects) {
				let tempid = "";
				origin.onfocus = function() {
					origin.innerHTML = "";
					for(let object of objects) {
						let a = createElement("origin", "option", null, null);
						a.innerHTML = object.name + ", " + object.city;
						a.value = object.ID;
					}
				}
				destination.onfocus = function() {
					destination.innerHTML = "";
					for(let object of objects) {
						let a = createElement("destination", "option", null, null);
						a.innerHTML = object.name + ", " + object.city;
						a.value = object.ID;
					}
				}
			}
		});
		let destination = createElement("inputscontainer", "select", "destination", null);
		createElement("destination", "option", null, null).innerHTML = "Select Destination";
		let distance = createElement("inputscontainer", "input", "distance", null);
		distance.type = "number";
		distance.placeholder = "Distance (km)";
		let duration = createElement("inputscontainer", "input", "duration", null);
		duration.type = "number";
		duration.placeholder = "Travel Duration (minutes)";
		let rate = createElement("inputscontainer", "input", "duration", null);
		rate.type = "number";
		rate.placeholder = "Trip Price";
		let confirm = createElement("inputscontainer", "button", null, "buttons");
		confirm.innerHTML = "Add Route";
		let cancel = createElement("inputscontainer", "button", null, "buttons");
		cancel.innerHTML = "Cancel";
		let iw = document.getElementById("inputswrapper");
		let ipw = document.getElementById("inputpanelwrapper");
		confirm.onclick = function() {
			confirm.disabled = true;
			let body = "routename=" + routename.value + "&origin=" + origin.value + "&destination=" + destination.value + "&distance=" + distance.value + "&duration="+ duration.value + "&rate=" + rate.value + "&type=route";
			postRequest("/youbus/add", body, function(response) {
				alert(response);
				iw.removeChild(container);
				ipw.className = "hidden";
			});
		}
		cancel.onclick = function() {
			iw.removeChild(container);
			ipw.className = "hidden";
		}
		ipw.className = "shown";
	};

	document.getElementById("addbus").onclick = function() {
		let container = createElement("inputswrapper", "div", "inputscontainer", null);
		createElement("inputscontainer", "h2", null, null).innerHTML = "New Bus";
		let busname = createElement("inputscontainer", "input", "busname", null);
		busname.placeholder = "Bus Name";
		let plateno = createElement("inputscontainer", "input", "plateno", null);
		plateno.placeholder = "Plate No";
		let driver = createElement("inputscontainer", "select", "driver", null);
		createElement("driver", "option", null, null).innerHTML = "Select driver";
		testRequest("/youbus/crews", function(objects) {
			if(objects) {
				driver.onfocus = function() {
					driver.innerHTML = "";
					for(let object of objects) {
						if(object.type == "driver") {
							let a = createElement("driver", "option", null, null);
							a.innerHTML = object.firstname + " " + object.lastname;
							a.value = object.ID;
						}
					}
				}
				conductor.onfocus = function() {
					conductor.innerHTML = "";
					for(let object of objects) {
						if(object.type == "conductor") {
							let a = createElement("conductor", "option", null, null);
							a.innerHTML = object.firstname + " " + object.lastname;
							a.value = object.ID;
						}
					}
				}
			}
		});
		let conductor = createElement("inputscontainer", "select", "conductor", null);
		createElement("conductor", "option", null, null).innerHTML = "Select conductor";
		let capacity = createElement("inputscontainer", "input", "capacity", null);
		capacity.type = "number";
		capacity.placeholder = "Seat capacity";
		let confirm = createElement("inputscontainer", "button", null, "buttons");
		confirm.innerHTML = "Add Bus";
		let cancel = createElement("inputscontainer", "button", null, "buttons");
		cancel.innerHTML = "Cancel";
		let iw = document.getElementById("inputswrapper");
		let ipw = document.getElementById("inputpanelwrapper");
		confirm.onclick = function() {
			confirm.disabled = true;
			let body = "busname=" + busname.value + "&plateno=" + plateno.value + "&driver=" + driver.value + "&conductor=" + conductor.value + "&capacity=" + capacity.value + "&type=bus";
			postRequest("/youbus/add", body, function(response) {
				alert(response);
				iw.removeChild(container);
				ipw.className = "hidden";
			});
		}
		cancel.onclick = function() {
			iw.removeChild(container);
			ipw.className = "hidden";
		}
		ipw.className = "shown";
	};

	document.getElementById("addtrip").onclick = function() {
		let container = createElement("inputswrapper", "div", "inputscontainer", null);
		createElement("inputscontainer", "h2", null, null).innerHTML = "New Trip";
		let routeid = createElement("inputscontainer", "select", "routeid", null);
		createElement("routeid", "option", null, null).innerHTML = "Select Route";
		let busid = createElement("inputscontainer", "select", "busid", null);
		createElement("busid", "option", null, null).innerHTML = "Select bus";
		testRequest("/youbus/routes", function(objects) {
			if(objects) {
				routeid.onfocus = function() {
					routeid.innerHTML = "";
					for(let object of objects) {
						let a = createElement("routeid", "option", null, null);
						a.innerHTML = object.name;
						a.value = object.ID;
					}
				}
			}
		});
		testRequest("/youbus/buses", function(objects) {
			if(objects) {
				busid.onfocus = function() {
					busid.innerHTML = "";
					for(let object of objects) {
						let a = createElement("busid", "option", null, null);
						a.innerHTML = object.name;
						a.value = object.ID;
					}
				}
			}
		});
		createElement("inputscontainer", "p", null, null).innerHTML = "Trip date:";
		let tripdate = createElement("inputscontainer", "input", null, null);
		tripdate.type = "date";
		let today = new Date();
		let dd = today.getDate();
		let mm = today.getMonth() + 1;
		let yyyy = today.getFullYear();
		tripdate.value = yyyy + "-" + ((mm > 9) ? "" : "0") + mm + "-" + ((dd > 9) ? "" : "0") + dd;
		createElement("inputscontainer", "p", null, null).innerHTML = "Departure time:";
		let departure = createElement("inputscontainer", "input", null, null);
		departure.type = "time";
		let hh = today.getHours();
		let m = today.getMinutes();
		departure.value = ((hh > 9) ? "" : "0") + hh + ":" + ((m > 9) ? "" : "0") + m;
		let confirm = createElement("inputscontainer", "button", null, "buttons");
		confirm.innerHTML = "Add Bus";
		let cancel = createElement("inputscontainer", "button", null, "buttons");
		cancel.innerHTML = "Cancel";
		let iw = document.getElementById("inputswrapper");
		let ipw = document.getElementById("inputpanelwrapper");
		confirm.onclick = function() {
			confirm.disabled = true;
			let body = "routeid=" + routeid.value + "&busid=" + busid.value + "&date=" + tripdate.value + "&departure=" + departure.value + "&type=trip";
			postRequest("/youbus/add", body, function(response) {
				alert(response);
				iw.removeChild(container);
				ipw.className = "hidden";
			});
		}
		cancel.onclick = function() {
			iw.removeChild(container);
			ipw.className = "hidden";
		}
		ipw.className = "shown";
	};

	document.getElementById("addcrew").onclick = function() {
		let iw = document.getElementById("inputswrapper");
		let container = createElement("inputswrapper", "div", "inputscontainer", null);
		createElement("inputscontainer", "h2", null, null).innerHTML = "New Crew";
		let lname = createElement("inputscontainer", "input", null, null);
		lname.placeholder = "Last Name"
		let fname = createElement("inputscontainer", "input", null, null);
		fname.placeholder = "First Name"
		let sex = createElement("inputscontainer", "select", "sex", null);
		createElement("sex", "option", null, null).innerHTML = "Male";
		createElement("sex", "option", null, null).innerHTML = "Female";
		createElement("inputscontainer", "p", null, null).innerHTML = "Birthdate:";
		let birthdate = createElement("inputscontainer", "input", null, null);
		birthdate.type = "date";
		birthdate.value = "1980-01-01";
		createElement("inputscontainer", "p", null, null).innerHTML = "Crew Type:";
		let ctype = createElement("inputscontainer", "select", "ctype", null);
		createElement("ctype", "option", null, null).innerHTML = "Driver";
		createElement("ctype", "option", null, null).innerHTML = "Conductor";
		let license = createElement("inputscontainer", "input", null, null);
		license.placeholder = "Driver's License";
		let prevstate = "Driver"
		ctype.onchange = function() {
			if(ctype.value == "Driver") {
				license.disabled = false;
			}
			else if(ctype.value == "Conductor") {
				license.disabled = true;
			}
			prevstate = ctype.value;
		}
		let confirm = createElement("inputscontainer", "button", null, "buttons");
		confirm.innerHTML = "Add Crew";
		let cancel = createElement("inputscontainer", "button", null, "buttons");
		cancel.innerHTML = "Cancel";
		let ipw = document.getElementById("inputpanelwrapper");
		confirm.onclick = function() {
			confirm.disabled = true;
			let body = "lastname=" + lname.value + "&firstname=" + fname.value + "&sex=" + sex.value + "&birthdate=" + birthdate.value + ((license.disabled) ? "" : "&license=" + license.value) + "&type=" + ctype.value;
			postRequest("/youbus/add", body, function(response) {
				alert(response);
				iw.removeChild(container);
				ipw.className = "hidden";
			});
		}
		cancel.onclick = function() {
			iw.removeChild(container);
			ipw.className = "hidden";
		}
		ipw.className = "shown";
	};

	document.getElementById("addlocation").onclick = function() {
		let iw = document.getElementById("inputswrapper");
		let container = createElement("inputswrapper", "div", "inputscontainer", null);
		createElement("inputscontainer", "h2", null, null).innerHTML = "New Crew";
		let name = createElement("inputscontainer", "input", null, null);
		name.placeholder = "Location Name"
		let city = createElement("inputscontainer", "input", null, null);
		city.placeholder = "City"
		let confirm = createElement("inputscontainer", "button", null, "buttons");
		confirm.innerHTML = "Add Crew";
		let cancel = createElement("inputscontainer", "button", null, "buttons");
		cancel.innerHTML = "Cancel";
		let ipw = document.getElementById("inputpanelwrapper");
		confirm.onclick = function() {
			confirm.disabled = true;
			let body = "name=" + name.value + "&city=" + city.value + "&type=location";
			postRequest("/youbus/add", body, function(response) {
				alert(response);
				iw.removeChild(container);
				ipw.className = "hidden";
			});
		}
		cancel.onclick = function() {
			iw.removeChild(container);
			ipw.className = "hidden";
		}
		ipw.className = "shown";
	};
};

function testRequest(url, callback) {
	let rq = new XMLHttpRequest();
	rq.onreadystatechange = function() {
		if(rq.readyState == 4) {
			let objects = JSON.parse(rq.responseText);
			callback(objects);
		}
	};
	rq.open('GET', url, true);
	rq.send();
}

function removeAllChild(parent) {
	while(parent.firstChild) {
		parent.removeChild(parent.firstChild);
	}
}

function createElement(parentid, type, id, classname) {
	let parent = document.getElementById(parentid);
	let element = document.createElement(type);
	if(id) {
		element.id = id;
	}
	if(classname) {
		element.className = classname;
	}
	parent.appendChild(element);
	return(element);
}

function addAttribute(element, attribute, value) {
	element.setAttribute(attribute, value);
}

function getRequest(url) {
	let rq = new XMLHttpRequest();
	rq.onreadystatechange = function() {
		if(rq.readyState == 4) {
			let trips = JSON.parse(rq.responseText);
			let a = document.getElementById("content");
			createTable(trips, a);
		}
	};
	rq.open('GET', url, true);
	rq.send();
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

function createTable(trips, a) {
	let t = document.getElementById("table");
	let con = document.getElementById("contents");
	if(t) {
		con.removeChild(t);
	}
	t = document.createElement('table');
	t.id = "table";
	let header = document.createElement('tr');
	fillHeader(header, trips[0]);
	t.appendChild(header);
	for(trip of trips) {
		let tr = document.createElement('tr');
		for(key in trip) {
			let td = document.createElement('td');
			td.innerHTML = trip[key];
			tr.appendChild(td);
		}
		t.appendChild(tr);
	}
	con.appendChild(t);
}

function fillHeader(header, obj) {
	for(key in obj) {
		let th = document.createElement('th');
		th.innerHTML = key.replace(/_/g, " ").toUpperCase();
		header.appendChild(th);
	}
}
