/*
the responses from the java servlet will be named domContainer and will contain the following parts:
(everything is a string unless otherwise noted)

state: String

elements: 
{
id(4 digit int)
parent(4 digit int)
type(determines which method to call in javascript to create this element)
value
}


inputs:
{
	id(4 digit int)
	name
	default(text to appear before user has typed anything)
}


followed by the triggers array these triggers will trigger the serverRequest function.
triggers:
{
	id(4 digit int)
	method
}

*/

/*
java containers that receive input from js


public class JsInput {
	public int id;
	public String name;
	public String value;
}

public class RequestContainer {
	public String state;
	public ArrayList<JsInput> inputs;

}

This program will create a JSON string formatted for the RequestContainer

*/




/*

	async function servletMessage(){
		
	let jsonMessage = "{method: 'POST', headers: {'Content-Type': 'application/json'}, body:\n\"jsState";
	jsonMessage += jsState;
	jsonMessage += '"';
	
	let myPairs = domContainer.inputNames.split('&');
	
	for(let i = 0; i < myPairs.length; ++i)
	{
		let mySplitter = myPairs[i].split(':');
		jsonMessage += '"';
		jsonMessage += mySplitter[0];
		jsonMessage += '": "';
		jsonMessage += document.getElementById(mySplitter[1]).value;
	}
	jsonMessage += "}";
	let response = await fetch('http://localhost:8080/project1/main/', jsonMessage);

	}
	
	*/
	
	/*
	let response = await fetch('http://localhost:8080/project1/main/', {
	  method: 'POST',
	  headers: {
	    'Content-Type': 'application/json'
	  },
	  body: JSON.stringify(requestContainer)
	});
	*/
	
async function servletMessage(){
	let requestContainer = {
		state: 'logged_out',
		username: document.getElementById('username').value,
		password: document.getElementById('password').value
	}
	
	
let response = await fetch('http://localhost:8080/project1/main/', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json'
  },
  body: JSON.stringify(requestContainer)
});



  if(response === 302){
  
   // let domContainer = await response.json();
console.log(domContainer.state);
  }else{
    console.log("Failed to connect. Status: ");
	console.log(response.status);
  }
}


let submitButton = document.getElementById('submit_button');




submitButton.onclick = () => {
servletMessage();
 }

  
 
