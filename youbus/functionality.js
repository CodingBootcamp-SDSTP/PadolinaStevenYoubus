var session = JSON.parse(localStorage.getItem("session"));
var user;

window.onload = function() {
	if(session) {
		getUser(session.userID);
	}
	else {
		setUserLabel();
	}
	document.getElementById("logout").onclick = function() {
		if(session) {
			get("/youbus/logout/accountid/" + session.ID, function(object) {
				if(object) {
					if(object.logout = "ok") {
						session = null;
						localStorage.removeItem("session");
						window.location.href = "http://localhost:8080/youbus/";
					}
				}
			});
		}
	}
	document.getElementById("trips").onclick = function() {
		removeContents();
		getRequest("/youbus/trips", "trips");
	};
	document.getElementById("buses").onclick = function() {
		removeContents();
		getRequest("/youbus/buses", "buses");
	};
	document.getElementById("routes").onclick = function() {
		removeContents();
		getRequest("/youbus/routes", "routes");
	};
	document.getElementById("locations").onclick = function() {
		removeContents();
		getRequest("/youbus/locations", "locations");
	};
	document.getElementById("drivers").onclick = function() {
		removeContents();
		getRequest("/youbus/drivers", "drivers");
	};
	document.getElementById("conductors").onclick = function() {
		removeContents();
		getRequest("/youbus/conductors", "conductors");
	};

	document.getElementById("addroute").onclick = function() {
		checkSession(function(ses) {
			if(ses) {
				let container = createElement("inputswrapper", "div", "inputscontainer", null);
				createElement("inputscontainer", "h2", null, null).appendChild(document.createTextNode("New Route"));
				let routename = createElement("inputscontainer", "input", "routename", null);
				routename.placeholder = "Route Name";
				let origin = createElement("inputscontainer", "select", "origin", null);
				createElement("origin", "option", null, null).appendChild(document.createTextNode("Select Origin"));
				get("/youbus/locations", function(objects) {
					if(objects) {
						let tempid = "";
						origin.onfocus = function() {
							origin.innerHTML;
							for(let object of objects) {
								let a = createElement("origin", "option", null, null);
								a.appendChild(document.createTextNode(object.name + ", " + object.city));
								a.value = object.ID;
							}
						}
						destination.onfocus = function() {
							destination.innerHTML = "";
							for(let object of objects) {
								let a = createElement("destination", "option", null, null);
								a.appendChild(document.createTextNode(object.name + ", " + object.city));
								a.value = object.ID;
							}
						}
					}
				});
				let destination = createElement("inputscontainer", "select", "destination", null);
				createElement("destination", "option", null, null).appendChild(document.createTextNode("Select Destination"));
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
				confirm.appendChild(document.createTextNode("Add Route"));
				let cancel = createElement("inputscontainer", "button", null, "buttons");
				cancel.appendChild(document.createTextNode("Cancel"));
				let iw = document.getElementById("inputswrapper");
				let ipw = document.getElementById("inputpanelwrapper");
				confirm.onclick = function() {
					confirm.disabled = true;
					let body = "routename=" + routename.value + "&origin=" + origin.value + "&destination=" + destination.value + "&distance=" + distance.value + "&duration="+ duration.value + "&rate=" + rate.value + "&type=route";
					postRequest("/youbus/add", body, function(response) {
						alert(response);
						iw.removeChild(container);
						ipw.className = "hidden";
						document.getElementById("routes").click();
					});
				}
				cancel.onclick = function() {
					iw.removeChild(container);
					ipw.className = "hidden";
				}
				ipw.className = "shown";
			}
		});
	}

	document.getElementById("addbus").onclick = function() {
		checkSession(function(ses) {
			if(ses) {
				let container = createElement("inputswrapper", "div", "inputscontainer", null);
				createElement("inputscontainer", "h2", null, null).appendChild(document.createTextNode("New Bus"));
				let busname = createElement("inputscontainer", "input", "busname", null);
				busname.placeholder = "Bus Name";
				let plateno = createElement("inputscontainer", "input", "plateno", null);
				plateno.placeholder = "Plate No";
				let driver = createElement("inputscontainer", "select", "driver", null);
				createElement("driver", "option", null, null).appendChild(document.createTextNode("Select driver"));
				get("/youbus/crews", function(objects) {
					if(objects) {
						driver.onfocus = function() {
							driver.innerHTML = "";
							for(let object of objects) {
								if(object.type == "driver") {
									let a = createElement("driver", "option", null, null);
									a.appendChild(document.createTextNode(object.firstname + " " + object.lastname));
									a.value = object.ID;
								}
							}
						}
						conductor.onfocus = function() {
							conductor.innerHTML = "";
							for(let object of objects) {
								if(object.type == "conductor") {
									let a = createElement("conductor", "option", null, null);
									a.appendChild(document.createTextNode(object.firstname + " " + object.lastname));
									a.value = object.ID;
								}
							}
						}
					}
				});
				let conductor = createElement("inputscontainer", "select", "conductor", null);
				createElement("conductor", "option", null, null).appendChild(document.createTextNode("Select conductor"));
				let bustype = createElement("inputscontainer", "select", "bustype", null);
				createElement("bustype", "option", null, null).appendChild(document.createTextNode("Select Type"));
				get("/youbus/bustypes", function(objects) {
					if(objects) {
						bustype.onfocus = function() {
							bustype.innerHTML = "";
							for(let object of objects) {
								let a = createElement("bustype", "option", null, null);
								a.appendChild(document.createTextNode(object.name));
								a.value = object.id;
							}
						}
					}
				});
				let confirm = createElement("inputscontainer", "button", null, "buttons");
				confirm.appendChild(document.createTextNode("Add Bus"));
				let cancel = createElement("inputscontainer", "button", null, "buttons");
				cancel.appendChild(document.createTextNode("Cancel"));
				let iw = document.getElementById("inputswrapper");
				let ipw = document.getElementById("inputpanelwrapper");
				confirm.onclick = function() {
					confirm.disabled = true;
					let body = "busname=" + busname.value + "&plateno=" + plateno.value + "&driver=" + driver.value + "&conductor=" + conductor.value + "&bustype=" + bustype.value + "&type=bus";
					postRequest("/youbus/add", body, function(response) {
						alert(response);
						iw.removeChild(container);
						ipw.className = "hidden";
						document.getElementById("buses").click();
					});
				}
				cancel.onclick = function() {
					iw.removeChild(container);
					ipw.className = "hidden";
				}
				ipw.className = "shown";
			}
		});
	}

	document.getElementById("addtrip").onclick = function() {
		checkSession(function(ses) {
			if(ses) {
				let container = createElement("inputswrapper", "div", "inputscontainer", null);
				createElement("inputscontainer", "h2", null, null).appendChild(document.createTextNode("New Trip"));
				let tripname = createElement("inputscontainer", "input", null, null);
				tripname.placeholder = "Trip name";
				let routeid = createElement("inputscontainer", "select", "routeid", null);
				createElement("routeid", "option", null, null).appendChild(document.createTextNode("Select Route"));
				let busid = createElement("inputscontainer", "select", "busid", null);
				createElement("busid", "option", null, null).appendChild(document.createTextNode("Select bus"));
				get("/youbus/routes", function(objects) {
					if(objects.length > 0) {
						routeid.onfocus = function() {
							routeid.innerHTML = "";
							for(let object of objects) {
								let a = createElement("routeid", "option", null, null);
								a.appendChild(document.createTextNode(object.name));
								a.value = object.ID;
							}
						}
					}
					else {
						alert("There is no registered route yet.");
					}
				});
				get("/youbus/buses", function(objects) {
					if(objects.length) {
						busid.onfocus = function() {
							busid.innerHTML = "";
							for(let object of objects) {
								let a = createElement("busid", "option", null, null);
								a.appendChild(document.createTextNode(object.NAME));
								a.value = object.ID;
							}
						}
					}
					else {
						alert("There is no registered bus yet.");
						cancel.click();
					}
				});
				createElement("inputscontainer", "p", null, null).appendChild(document.createTextNode("Trip date:"));
				let tripdate = createElement("inputscontainer", "input", null, null);
				tripdate.type = "date";
				let today = new Date();
				let dd = today.getDate();
				let mm = today.getMonth() + 1;
				let yyyy = today.getFullYear();
				tripdate.value = yyyy + "-" + ((mm > 9) ? "" : "0") + mm + "-" + ((dd > 9) ? "" : "0") + dd;
				createElement("inputscontainer", "p", null, null).appendChild(document.createTextNode("Departure time:"));
				let departure = createElement("inputscontainer", "input", null, null);
				departure.type = "time";
				let hh = today.getHours();
				let m = today.getMinutes();
				departure.value = ((hh > 9) ? "" : "0") + hh + ":" + ((m > 9) ? "" : "0") + m;
				let confirm = createElement("inputscontainer", "button", null, "buttons");
				confirm.appendChild(document.createTextNode("Add Trip"));
				let cancel = createElement("inputscontainer", "button", null, "buttons");
				cancel.appendChild(document.createTextNode("Cancel"));
				let iw = document.getElementById("inputswrapper");
				let ipw = document.getElementById("inputpanelwrapper");
				confirm.onclick = function() {
					confirm.disabled = true;
					let body = "routeid=" + routeid.value + "&busid=" + busid.value + "&date=" + tripdate.value + "&departure=" + departure.value + "&tripname=" + tripname.value + "&type=trip";
					postRequest("/youbus/add", body, function(response) {
						alert(response);
						iw.removeChild(container);
						ipw.className = "hidden";
						document.getElementById("trips").click();
					});
				}
				cancel.onclick = function() {
					iw.removeChild(container);
					ipw.className = "hidden";
				}
				ipw.className = "shown";
			}
		});
	}

	document.getElementById("addcrew").onclick = function() {
		checkSession(function(ses) {
			if(ses) {
				let iw = document.getElementById("inputswrapper");
				let container = createElement("inputswrapper", "div", "inputscontainer", null);
				createElement("inputscontainer", "h2", null, null).appendChild(document.createTextNode("New Crew"));
				let lname = createElement("inputscontainer", "input", null, null);
				lname.placeholder = "Last Name"
				let fname = createElement("inputscontainer", "input", null, null);
				fname.placeholder = "First Name"
				let sex = createElement("inputscontainer", "select", "sex", null);
				createElement("sex", "option", null, null).appendChild(document.createTextNode("Male"));
				createElement("sex", "option", null, null).appendChild(document.createTextNode("Female"));
				createElement("inputscontainer", "p", null, null).appendChild(document.createTextNode("Birthdate:"));
				let birthdate = createElement("inputscontainer", "input", null, null);
				birthdate.type = "date";
				birthdate.value = "1980-01-01";
				createElement("inputscontainer", "p", null, null).appendChild(document.createTextNode("Crew Type:"));
				let ctype = createElement("inputscontainer", "select", "ctype", null);
				createElement("ctype", "option", null, null).appendChild(document.createTextNode("Driver"));
				createElement("ctype", "option", null, null).appendChild(document.createTextNode("Conductor"));
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
				confirm.appendChild(document.createTextNode("Add Crew"));
				let cancel = createElement("inputscontainer", "button", null, "buttons");
				cancel.appendChild(document.createTextNode("Cancel"));
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
			}
		});
	}

	document.getElementById("addlocation").onclick = function() {
		checkSession(function(ses) {
			if(ses) {
				let iw = document.getElementById("inputswrapper");
				let container = createElement("inputswrapper", "div", "inputscontainer", null);
				createElement("inputscontainer", "h2", null, null).appendChild(document.createTextNode("New Crew"));
				let name = createElement("inputscontainer", "input", null, null);
				name.placeholder = "Location Name"
				let city = createElement("inputscontainer", "input", null, null);
				city.placeholder = "City"
				let confirm = createElement("inputscontainer", "button", null, "buttons");
				confirm.appendChild(document.createTextNode("Add Location"));
				let cancel = createElement("inputscontainer", "button", null, "buttons");
				cancel.appendChild(document.createTextNode("Cancel"));
				let ipw = document.getElementById("inputpanelwrapper");
				confirm.onclick = function() {
					confirm.disabled = true;
					let body = "name=" + name.value + "&city=" + city.value + "&type=location";
					postRequest("/youbus/add", body, function(response) {
						alert(response);
						iw.removeChild(container);
						ipw.className = "hidden";
						document.getElementById("locations").click();
					});
				}
				cancel.onclick = function() {
					iw.removeChild(container);
					ipw.className = "hidden";
				}
				ipw.className = "shown";
			}
		});
	}
};

function get(url, callback) {
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

function getRequest(url, s) {
	let rq = new XMLHttpRequest();
	rq.onreadystatechange = function() {
		if(rq.readyState == 4) {
			let trips = JSON.parse(rq.responseText);
			if(trips.length < 1) {
				alert("There are no " + s + " yet");
				return;
			}
			createTable(trips, s);
		}
	};
	rq.open('GET', url, true);
	rq.send();
}

function removeContents() {
	let con = document.getElementById("contents");
	while(con.firstChild) {
		con.removeChild(con.firstChild);
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

function createTable(trips, s) {
	let t = document.getElementById("table");
	let con = document.getElementById("contents");
	t = document.createElement('table');
	t.id = "table";
	let header = document.createElement('tr');
	fillHeader(header, trips[0]);
	t.appendChild(header);
	for(trip of trips) {
		let tr = document.createElement('tr');
		for(key in trip) {
			if(key.toLowerCase() != "bus_id") {
				let td = document.createElement('td');
				if(s != "trips") {
						td.appendChild(document.createTextNode(trip[key]));
				}
				else {
					if(key.toLowerCase() != "name") {
							td.appendChild(document.createTextNode(trip[key]));
					}
					else {
						let link = document.createElement("a");
						link.id = trip.ID;
						link.setAttribute("href", "#");
						link.appendChild(document.createTextNode(trip[key]));
						td.appendChild(link);
						manageTripTable(trip, link);
					}
				}
				tr.appendChild(td);
			}
		}
		t.appendChild(tr);
	}
	con.appendChild(t);
}

function manageTripTable(trip, link) {
	let cont = document.getElementById("contents");
	let o = trip;
	link.onclick = function() {
		checkSession(function(ses) {
			if(ses) {
				let object = o;
				removeContents();
				let bus = document.createElement("table");
				bus.id = "busdisplay";
				let column = [document.createElement("tr"), document.createElement("tr"), document.createElement("tr"), document.createElement("tr"), document.createElement("tr")];
				let header = document.createElement("h2");
				header.appendChild(document.createTextNode(object.NAME));
				cont.appendChild(header);
				let bname = document.createElement("h4");
				let cap = document.createElement("p");
				let as = document.createElement("p");
				bname.appendChild(document.createTextNode("Bus Name: " + object.BUS));
				cap.appendChild(document.createTextNode("Capacity: " + object.CAPACITY));
				as.appendChild(document.createTextNode("Available Seats: " + object.AVAILABLE_SEATS));
				cont.appendChild(bname);
				cont.appendChild(cap);
				get("/youbus/seats/busid/" + object.BUS_ID, function(objects) {
					if(objects) {
						let l = -1;
						let x = -1;
						switch(objects.length) {
							case 36:
								l = 9;
								x = 4;
								break;
							case 55:
								l = 11;
								x = 5;
								break;
							case 70:
								l = 14;
								x = 5;
								break;
						}
						for(let i = 0; i < x; i++) {
							let space = document.createElement("td");
							space.className = "space";
							column[i].appendChild(space);
							space = document.createElement("td");
							space.className = "space";
							column[i].appendChild(space);
						}
						let j = 0;
						let k = 0;
						for(let i = 0; i < objects.length; i++) {
							if(k >= l) {
								j++;
								k = 0;
							}
							let seatNo = objects[i].seatNo;
							let seat = document.createElement("td");
							if(seatNo) {
								if(objects[i].occupied == "true") {
									seat.className = "occupiedseats";
									seat.appendChild(document.createTextNode(seatNo));
								}
								else {
									let atag = document.createElement("a");
									atag.setAttribute("href","#");
									atag.id = objects[i].ID;
									atag.appendChild(document.createTextNode(seatNo));
									seat.appendChild(atag);
									manageSeatBooking(atag, objects[i], object, link);
								}
							}
							else {
								seat.className = "space";
							}
							column[j].appendChild(seat);
							k++;
						}
						for(let i = 0; i < x; i++) {
							bus.appendChild(column[i]);
						}
						cont.appendChild(bus);
						cont.appendChild(as)
					}
				});
			}
		});
	}
}

function manageSeatBooking(seatbutton, seat, trip, link) {
	seatbutton.onclick = function() {
		checkSession(function(ses) {
			if(ses) {
				get("/youbus/seats/seatid/" + seat.ID, function(objects) {
					if(objects) {
						if(objects[0].occupied == "false") {
							let iw = document.getElementById("inputswrapper");
							let container = createElement("inputswrapper", "div", "inputscontainer", null);
							createElement("inputscontainer", "h2", null, null).appendChild(document.createTextNode("Seat Booking"));
							createElement("inputscontainer", "p", null, null).appendChild(document.createTextNode("TRIP NAME: "));
							let tripName = createElement("inputscontainer", "input", null, null);
							tripName.value = trip.NAME.toUpperCase();
							tripName.disabled = true;
							createElement("inputscontainer", "p", null, null).appendChild(document.createTextNode("BUS NAME: "));
							let bus = createElement("inputscontainer", "input", null, null);
							bus.value = trip.BUS.toUpperCase();
							bus.disabled = true;
							createElement("inputscontainer", "p", null, null).appendChild(document.createTextNode("Passenger: "));
							let passenger = createElement("inputscontainer", "input", null, null);
							passenger.value = user.FIRSTNAME + " " + user.LASTNAME;
							passenger.disabled = true;
							createElement("inputscontainer", "p", null, null).appendChild(document.createTextNode("Seat No: "));
							let seatNo = createElement("inputscontainer", "input", null, null);
							seatNo.value = seat.seatNo;
							seatNo.disabled = true;
							createElement("inputscontainer", "p", null, null).appendChild(document.createTextNode("Origin: "));
							let origin = createElement("inputscontainer", "input", null, null);
							origin.value = trip.ORIGIN;
							origin.disabled = true;
							createElement("inputscontainer", "p", null, null).appendChild(document.createTextNode("Destination: "));
							let destination = createElement("inputscontainer", "input", null, null);
							destination.value = trip.DESTINATION;
							destination.disabled = true;
							createElement("inputscontainer", "p", null, null).appendChild(document.createTextNode("Fare: "));
							let fare = createElement("inputscontainer", "input", null, null);
							fare.value = trip.RATE;
							fare.disabled = true;
							let confirm = createElement("inputscontainer", "button", null, "buttons");
							confirm.appendChild(document.createTextNode("Confirm Booking"));
							let cancel = createElement("inputscontainer", "button", null, "buttons");
							cancel.appendChild(document.createTextNode("Cancel"));
							let ipw = document.getElementById("inputpanelwrapper");
							confirm.onclick = function() {
								get("/youbus/seats/seatid/" + seat.ID, function(objects) {
									if(objects) {
										if(objects[0].occupied == "false") {
											confirm.disabled = true;
											let body = "tripid=" + trip.ID + "&busid=" + trip.BUS_ID + "&seatid=" + seat.ID + "&userid=" + session.userID + "&type=booking";
											postRequest("/youbus/booktrip", body, function(response) {
												alert(response);
												iw.removeChild(container);
												ipw.className = "hidden";
												link.click();
											});
										}
										else {
											alert("Booking failed. Someone just booked the seat no. " + seat.seatNo +" just now.");
										}
									}
								});
							}
							cancel.onclick = function() {
								iw.removeChild(container);
								ipw.className = "hidden";
							}
							ipw.className = "shown";
						}
					}
				});
			}
		});
	}
}

function fillHeader(header, obj) {
	for(key in obj) {
		if(key.toLowerCase() != "bus_id") {
			let th = document.createElement('th');
			th.appendChild(document.createTextNode(key.replace(/_/g, " ").toUpperCase()));
			header.appendChild(th);
		}
	}
}

function checkSession(callback) {
	if(!session) {
		loginPopup();
	} else {
		get("/youbus/session/userid/" + session.ID, function(account) {
			if(account.sessionID != session.sessionID) {
				loginPopup();
				callback(false);
			}
			else {
				callback(true);
			}
		});
	}
}

function loginPopup() {
	var popup = document.createElement('div');
	popup.id = 'fullscreenpopup';
	var container = document.createElement('div');
	container.className = 'fullscreenpopup_container';
	popup.appendChild(container);
	var content = document.createElement('div');
	content.className = 'fullscreenpopup_content';
	container.appendChild(content);
	var p1 = document.createElement('p');
	p1.appendChild(document.createTextNode("Please login to proceed"));
	content.appendChild(p1);
	var p11 = document.createElement('p');
	var username = document.createElement('input');
	username.setAttribute('type', 'text');
	username.setAttribute('placeholder', 'Username');
	username.setAttribute('id', 'username');
	p11.appendChild(username);
	content.appendChild(p11);
	var p12 = document.createElement('p');
	var password = document.createElement('input');
	password.setAttribute('type', 'password');
	password.setAttribute('placeholder', 'Password');
	password.setAttribute('id', 'password');
	p12.appendChild(password);
	content.appendChild(p12);
	var p2 = document.createElement('p');
	var p4 = document.createElement('p');
	content.appendChild(p2);
	var button = document.createElement('button');
	button.className = 'loginbuttons';
	var cancel = document.createElement('button');
	cancel.className = 'loginbuttons';
	button.onclick = function() {
		get("/youbus/login/username/" + username.value, function(object) {
			if(object) {
				if(object.password == password.value) {
					session = object;
					localStorage.setItem("session", JSON.stringify(session));
					getUser(session.userID)
					document.body.removeChild(popup);
				}
				else {
					alert("Incorrect password");
					password.value = null;
				}
			}
			else {
				alert("Username does not exist");
			}
		});
	}
	cancel.onclick = function() {
		document.body.removeChild(document.getElementById('fullscreenpopup'));
	}
	p2.appendChild(button);
	p4.appendChild(cancel);
	var p3 = document.createElement('p');
	content.appendChild(p4);
	content.appendChild(p3);
	var button1 = document.createElement('a');
	button1.href = "/youbus/signup.html";
	p3.appendChild(button1);
	var span = document.createElement('span');
	span.appendChild(document.createTextNode('Login'));
	var cancelspan = document.createElement('span');
	cancelspan.appendChild(document.createTextNode('cancel'));
	button.appendChild(span);
	cancel.appendChild(cancelspan);
	button1.appendChild(document.createElement('span').appendChild(document.createTextNode('Signup')));
	document.body.appendChild(popup);
}

function getUser(id) {
	get("/youbus/users/userid/" + id, function(object) {
		if(object) {
			user = object;
			setUserLabel();
		}
	});
}

function setUserLabel() {
	document.getElementById("user").innerHTML = "User: " + ((user) ? user.FIRSTNAME + " " + user.LASTNAME : "Guest")
}
