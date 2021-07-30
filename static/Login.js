
/*
       
*/
let state = 'logged_out';

async function loginRequest()
{
	let requestContainer = {
		state: 'logged_out', 
		username: document.getElementById('username').value, 
		password: document.getElementById('password').value
		};
	
	
	let response = await fetch('http://localhost:8080/project1/Login.html', {
	method: 'POST',
	headers: {
		'Content-Type': 'application/json'
	},
	body: JSON.stringify(requestContainer)
	});
	
  if(response.ok)
	{
     responseContainer = await response.json();
	console.log(responseContainer.state);

	if(state == 'login_failed')
	{
	unloadLoginFailed();
	}
	
	if(state == 'logged_out')
	{
	unloadLogin();	
	}
	state = responseContainer.state;
	checkState();
	}else{
    console.log("Failed to connect. Status: ");
	console.log(response.status);
  }
};

async function submitReimbursementRequest()
{
	

	let requestContainer = {
		state: 'add_reimbursement',
		amount: document.getElementById('amount').value,
		description: document.getElementById('description').value,
		receipt: document.getElementById('receipt').value,
		authorId: responseContainer.id,
		typeId: 1
		
		};
	
	
	let response = await fetch('http://localhost:8080/project1/Login.html', {
	method: 'POST',
	headers: {
		'Content-Type': 'application/json'
	},
	body: JSON.stringify(requestContainer)
	});
	
  if(response.ok)
	{
     responseContainer = await response.json();
	console.log(responseContainer.state);
	state = responseContainer.state;
	checkState();
	}else{
    console.log("Failed to connect. Status: ");
	console.log(response.status);
  }	
	
	
};



var loginPage = document.createElement("div");
loginPage.innerHTML = `<div class="row">
 <h3 class="col-sm-4">Employee Reimbursement System</h3>
</div>
<form class="form-control">
Employee Login
<br>
<label for="user_name">User Name:</label>
<br>
<input type="text" name="user_name" id="username" placeholder="username">
<br>
<label for="password">Password:</label>
<br>
<input type="text" name="password" id="password" placeholder="password">
<br>
<button type="button" id="submit_button">Submit</button>   
    </form>`;

var loginFailedPage =  document.createElement("div");
loginFailedPage.innerHTML = `<div class="row">
 <h3 class="col-sm-4">Employee Reimbursement System</h3>
</div>
<form class="form-control">
Employee Login
<br>
<label for="user_name">User Name:</label>
<br>
<input type="text" name="user_name" id="username" placeholder="username">
<br>
<label for="password">Password:</label>
<br>
<input type="text" name="password" id="password" placeholder="password">
<br>
<button type="button" id="submit_button">Submit</button>
Username or Password are incorrect, please try again.
    </form>`;

var managerMenuPage = document.createElement("div");
var employeeMenuPage = document.createElement("div");
var addReimbursement = document.createElement("div");





      



function loadLogin()
{
document.body.appendChild(loginPage);   	
}
function unloadLogin()
{
document.body.removeChild(loginPage);
}

function loadLoginFailed()
{
document.body.appendChild(loginFailedPage);   	
}
function unloadLoginFailed()
{
document.body.removeChild(loginFailedPage);
}


function loadEmployeeMenu()
{
	
let employeeMenu = `
	 <button id="newReimbursement" class="btn btn-success">Request Reimbursement</button>

  <table class="table" id="reimbursements">
    <thead>
      <tr>
        <th class="col-sm-1">Reimbursement ID</th>
        <th class="col-sm-1">Amount</th>
        <th class="col-sm-1">Date Submitted</th>
        <th class="col-sm-1">Date Resolved</th>
        <th class="col-sm-1">Description</th>
        <th class="col-sm-1">Receipt</th>
        <th class="col-sm-1">Author</th>
        <th class="col-sm-1">Resolver</th>
        <th class="col-sm-1">Type</th>
        <th class="col-sm-1">Status</th>
      </tr>
    </thead>

    <tbody>
`;
	
	for(let i = 0; i < responseContainer.reimbursements.length; ++i)
	{
		employeeMenu += `<tr><td class="col-sm-1">`;
		employeeMenu += responseContainer.reimbursements[i].id;
		employeeMenu += `	</td>`;
		employeeMenu += `<td class="col-sm-1">`;
		employeeMenu += responseContainer.reimbursements[i].amount;
		employeeMenu += `	</td>`;
		employeeMenu += `<td class="col-sm-1">`;
		employeeMenu += responseContainer.reimbursements[i].submitted;
		employeeMenu += `	</td>`;
		employeeMenu += `<td class="col-sm-1">`;
		employeeMenu += responseContainer.reimbursements[i].resolved;
		employeeMenu += `	</td>`;
		employeeMenu += `<td class="col-sm-1">`;
		employeeMenu += responseContainer.reimbursements[i].description;
		employeeMenu += `	</td>`;
		employeeMenu += `<td class="col-sm-1">`;
		employeeMenu += responseContainer.reimbursements[i].receipt;
		employeeMenu += `	</td>`;
		employeeMenu += `<td class="col-sm-1">`;
		employeeMenu += responseContainer.reimbursements[i].author;
		employeeMenu += `	</td>`;
		employeeMenu += `<td class="col-sm-1">`;
		employeeMenu += responseContainer.reimbursements[i].resolver;
		employeeMenu += `	</td>`;
		employeeMenu += `<td class="col-sm-1">`;
		employeeMenu += responseContainer.reimbursements[i].status;
		employeeMenu += `	</td>`;
		employeeMenu += `<td class="col-sm-1">`;
		employeeMenu += responseContainer.reimbursements[i].type;
		employeeMenu += `	</td></tr><br>`;
	}

employeeMenu += `
    </tbody>
</table>
`;
employeeMenuPage.innerHTML =	employeeMenu;
document.body.appendChild(employeeMenuPage);   	
}
function unloadEmployeeMenu()
{
document.body.removeChild(employeeMenuPage);
}

function loadManagerMenu()
{
	
let managerMenu = `
	 <button id="new_Reimbursement_Button" class="btn btn-success">Approve Reimbursement</button>

  <table class="table" id="reimbursements">
    <thead>
      <tr>
        <th class="col-sm-1">Reimbursement ID</th>
        <th class="col-sm-1">Amount</th>
        <th class="col-sm-1">Date Submitted</th>
        <th class="col-sm-1">Date Resolved</th>
        <th class="col-sm-1">Description</th>
        <th class="col-sm-1">Receipt</th>
        <th class="col-sm-1">Author</th>
        <th class="col-sm-1">Resolver</th>
        <th class="col-sm-1">Type</th>
        <th class="col-sm-1">Status</th>
      </tr>
    </thead>

    <tbody>
`;
	
	for(let i = 0; i < responseContainer.reimbursements.length; ++i)
	{
		managerMenu += `<tr><td class="col-sm-1">`;
		managerMenu += responseContainer.reimbursements[i].id;
		managerMenu += `	</td>`;
		managerMenu += `<td class="col-sm-1">`;
		managerMenu += responseContainer.reimbursements[i].amount;
		managerMenu += `	</td>`;
		managerMenu += `<td class="col-sm-1">`;
		managerMenu += responseContainer.reimbursements[i].submitted;
		managerMenu += `	</td>`;
		managerMenu += `<td class="col-sm-1">`;
		managerMenu += responseContainer.reimbursements[i].resolved;
		managerMenu += `	</td>`;
		managerMenu += `<td class="col-sm-1">`;
		managerMenu += responseContainer.reimbursements[i].description;
		managerMenu += `	</td>`;
		managerMenu += `<td class="col-sm-1">`;
		managerMenu += responseContainer.reimbursements[i].receipt;
		managerMenu += `	</td>`;
		managerMenu += `<td class="col-sm-1">`;
		managerMenu += responseContainer.reimbursements[i].author;
		managerMenu += `	</td>`;
		managerMenu += `<td class="col-sm-1">`;
		managerMenu += responseContainer.reimbursements[i].resolver;
		managerMenu += `	</td>`;
		managerMenu += `<td class="col-sm-1">`;
		managerMenu += responseContainer.reimbursements[i].status;
		managerMenu += `	</td>`;
		managerMenu += `<td class="col-sm-1">`;
		managerMenu += responseContainer.reimbursements[i].type;
		managerMenu += `	</td></tr><br>`;
	}

managerMenu += `
    </tbody>
</table>
`;
managerMenuPage.innerHTML =	managerMenu;
document.body.appendChild(managerMenuPage);   	
}
function unloadManagerMenu()
{
document.body.removeChild(managerMenuPage);
}


function loadAddReimbursement()
{

	
addReimbursement = document.createElement("div");
addReimbursement.innerHTML = `<div class="row">
 <h3 class="col-sm-4">Employee Reimbursement System</h3>
</div>
<form class="form-control">
Add Reimbursement
<br>
<label for="amount">Amount:</label>
<br>
<input type="text" name="amound" id="amount" placeholder="amount">
<br>
<label for="description">Description:</label>
<br>
<input type="text" name="description" id="description" placeholder="description">
<br>
<label for="receipt">Receipt:</label>
<br>
<input type="text" name="receipt" id="receipt" placeholder="receipt">
<br>
<button type="button" id="submit_button">Submit</button>   
    </form>`;
	
	document.body.appendChild(addReimbursement);
}

function unloadAddReimbursement()
{
document.body.removeChild(addReimbursement);	
}



checkState();




function checkState()
{
if(state == 'login_failed')
{
loadLoginFailed();
let submitButton = document.getElementById('submit_button');
submitButton.onclick = () => {
loginRequest();
 };	
}

if(state == 'logged_out')
{
loadLogin();
let submitButton = document.getElementById('submit_button');
submitButton.onclick = () => {
loginRequest();
 };	
}

if(state == 'employee_menu')
{
loadEmployeeMenu();
let reimbursementButton = document.getElementByID('new_Reimbursement_button');
reimbursementButton.onclick = () => {
submitReimbursementRequest();
};
}

if(state == 'manager_menu')
{
loadManagerMenu();
}

}
