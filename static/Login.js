
/*
       
*/
let state = 'logged_out';
let filter = 'VIEW ALL';
let responseContainer =
{
	message: " "
}
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
	unloadLogin();	
	state = responseContainer.state;
	console.log(responseContainer.message)
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
		userId: responseContainer.id,
		typeId: document.getElementById('reimbursement_type').value
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

async function resolveReimbursementRequest()
{
	
	let requestContainer = {
		state: 'resolve_reimbursement',
		reimbursementId: document.getElementById('resolve_reimbursement_id').value,
		userId: responseContainer.id,
		resolveStatus: document.getElementById('resolve_reimbursement_status').value
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
var managerMenuPage = document.createElement("div");
var employeeMenuPage = document.createElement("div");
var addReimbursementPage = document.createElement("div");
var resolveReimbursementPage = document.createElement("div");




      



function loadLogin()
{
loginPage.innerHTML = `<div class="row">
 <h3 class="col-sm-4">Employee Reimbursement System</h3><br>
<h4> ${responseContainer.message} </h4>
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
	
let employeeMenu = `<div class="row">
 <h3 class="col-sm-4">Employee Reimbursement System</h3><br>
<h4> ${responseContainer.message} </h4>
</div>
<button id="new_reimbursement" class="btn btn-success">Request Reimbursement</button>
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
employeeMenuPage.innerHTML = employeeMenu;
document.body.appendChild(employeeMenuPage);

let reimbursementButton = document.getElementById('new_reimbursement');
reimbursementButton.onclick = () => {
state = "add_reimbursement";
document.body.removeChild(employeeMenuPage);
checkState();
};


}
function unloadEmployeeMenu()
{
document.body.removeChild(employeeMenuPage);
}

function loadManagerMenu()
{
	
	
	
let managerMenu = `<div class="row">
 <h3 class="col-sm-4">Employee Reimbursement System</h3><br>
<h4> ${responseContainer.message} </h4>
</div>
	<button id="resolve_reimbursement_button" class="btn btn-success">Resolve Reimbursement</button>
	<br>
	<select name="filter_type" id="filter_type">
	<option value="PENDING">PENDING</option>
	<option value="APPROVED">APPROVED</option>
	<option value="DENIED">DENIED</option>
	<option value="VIEW ALL">VIEW ALL</option>
	</select>
	 <button id="filter_reimbursement_button" class="btn btn-success">Filter</button>
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
		
		if((filter === 'VIEW ALL')||(responseContainer.reimbursements[i].status === filter))
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
	}

managerMenu += `
    </tbody>
</table>
`;



managerMenuPage.innerHTML =	managerMenu;
document.body.appendChild(managerMenuPage);   	


let filterButton = document.getElementById('filter_reimbursement_button');
filterButton.onclick = () => {
filter = document.getElementById('filter_type').value;
state = "manager_menu";
document.body.removeChild(managerMenuPage);
checkState();
};



let resolveButton = document.getElementById('resolve_reimbursement_button');
resolveButton.onclick = () => {
state = "resolve_reimbursement";
document.body.removeChild(managerMenuPage);
checkState();
};


}
function unloadManagerMenu()
{
document.body.removeChild(managerMenuPage);
}


function loadAddReimbursement()
{

	

 let addReimbursement = `<div class="row">
 <h3 class="col-sm-4">Employee Reimbursement System</h3><br>
<h4> ${responseContainer.message} </h4>
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
<label for="type">Choose a type:</label>
<br>
<select name="reimbursement_type" id="reimbursement_type">
  <option value="1">LODGING</option>
  <option value="2">TRAVEL</option>
  <option value="3">FOOD</option>
  <option value="4">OTHER</option>
</select>
<br>
<button type="button" id="submit_reimbursement">Submit</button>   
    </form>`;
	addReimbursementPage.innerHTML = addReimbursement;
	document.body.appendChild(addReimbursementPage);
	
	
}

function unloadAddReimbursement()
{
document.body.removeChild(addReimbursementPage);	
}

function loadResolveReimbursement()
{
	
		
let resolveReimbursement = `<div class="row">
 <h3 class="col-sm-4">Employee Reimbursement System</h3><br>
<h4> ${responseContainer.message} </h4>
</div>
<button id="submit_resolve_reimbursement_button" class="btn btn-success">Resolve Reimbursement</button>

	<select name="resolve_reimbursement" id="resolve_reimbursement_id">`;
	for(let i = 0; i < responseContainer.reimbursements.length; ++i)
	{

		if(responseContainer.reimbursements[i].status === 'PENDING')
		{
		resolveReimbursement += `<option value="`;
		resolveReimbursement += responseContainer.reimbursements[i].id;
		resolveReimbursement += `">`;
		resolveReimbursement += `Id: `;
		resolveReimbursement += responseContainer.reimbursements[i].id;
		resolveReimbursement += `Amount: `;
		resolveReimbursement += responseContainer.reimbursements[i].amount;
		resolveReimbursement += `Submitted: `;
		resolveReimbursement += responseContainer.reimbursements[i].submitted;
		resolveReimbursement += `Description: `;
		resolveReimbursement += responseContainer.reimbursements[i].description;
		resolveReimbursement += `Author: `;
		resolveReimbursement += responseContainer.reimbursements[i].author;
		resolveReimbursement += `Type: `;
		resolveReimbursement += responseContainer.reimbursements[i].type;
		resolveReimbursement += `</option>`;
		}
	}

resolveReimbursement += `</select>`;
resolveReimbursement += `<select name="resolve_reimbursement_status" id="resolve_reimbursement_status">
  <option value="APPROVED">APPROVED</option>
  <option value="DENIED">DENIED</option>
</select>`;
	resolveReimbursementPage.innerHTML = resolveReimbursement;
	document.body.appendChild(resolveReimbursementPage);
	
	let resolveSubmitButton = document.getElementById('submit_resolve_reimbursement_button');
	resolveSubmitButton.onclick = () => {
	resolveReimbursementRequest();
	unloadResolveReimbursement();
};
}

function unloadResolveReimbursement()
{
	document.body.removeChild(resolveReimbursementPage);	
}





checkState();




function checkState()
{
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

}

if(state == 'manager_menu')
{
loadManagerMenu();
}

if(state == 'add_reimbursement')
{
loadAddReimbursement();

let submitReimbursementButton = document.getElementById('submit_reimbursement');
submitReimbursementButton.onclick = () => {
submitReimbursementRequest();
unloadAddReimbursement();
state = 'employee_menu';
};
}

if(state == 'resolve_reimbursement')
{
	loadResolveReimbursement();
}


}
