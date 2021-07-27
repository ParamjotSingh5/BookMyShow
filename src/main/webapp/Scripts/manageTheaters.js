let allTheaters = null;
Init();

function Init(){
    fetchAllTheaters();
    addRows(allTheaters);
}

function addRows(theatersArray){
    var manageThearterBody = document.getElementById("manageThearterBody");
    
    for(var each in theatersArray){
        manageThearterBody.appendChild(createEachTheaterRow(theatersArray[each]));
    }
}

function createEachTheaterRow(arg){

    var theatersEachRow = document.createElement("TR");
    theatersEachRow.setAttribute("scope","col");
    theatersEachRow.setAttribute("Id", `theaterId_${arg.Id}`);

    var NameTD = document.createElement("TD");    
    theatersEachRow.appendChild(NameTD);
    var nameInput = document.createElement("INPUT");
    nameInput.setAttribute("type","text");    
    nameInput.setAttribute("disabled", "disabled");
    nameInput.classList.add("form-control");    
    nameInput.classList.add("theaterName");
    nameInput.value =  arg.Name; 
    NameTD.appendChild(nameInput);

    var slotsTD = document.createElement("TD");        
    theatersEachRow.appendChild(slotsTD);
    var slotsInput = document.createElement("INPUT");
    slotsInput.setAttribute("type","text");        
    slotsInput.setAttribute("disabled","disabled");
    slotsInput.classList.add("form-control");
    slotsInput.classList.add("theaterSlots");
    slotsInput.value =  arg.Slots; 
    slotsTD.appendChild(slotsInput);

    var locationTD = document.createElement("TD");    
    theatersEachRow.appendChild(locationTD);
    var locationInput = document.createElement("INPUT");
    locationInput.setAttribute("type","text");    
    locationInput.classList.add("form-control");
    locationInput.setAttribute("disabled", "disabled");
    locationInput.classList.add("theaterlocation");
    locationInput.value =  arg.Location; 
    locationTD.appendChild(locationInput);

    //Save Edit Buttons
    var saveEditTD = document.createElement("TD");
    theatersEachRow.appendChild(saveEditTD);
    var Editbtn = document.createElement("BUTTON");
    Editbtn.classList.add("btn");
    Editbtn.classList.add("btn-primary");
    Editbtn.setAttribute("id",`editbtn_${arg.Id}`);
    Editbtn.setAttribute("onclick", `EditTheaterRow(${arg.Id})`);
    Editbtn.innerHTML= `<i class="fas fa-edit"></i> Edit`;
    saveEditTD.appendChild(Editbtn);

    var Savebtn = document.createElement("BUTTON");
    Savebtn.classList.add("btn");
    Savebtn.classList.add("btn-primary");    
    Savebtn.setAttribute("id",`savebtn_${arg.Id}`);
    Savebtn.setAttribute("onclick", `SaveTheaterRow(${arg.Id})`);
    Savebtn.innerHTML= `<i class="far fa-save"></i> Save`;
    Savebtn.style.display="none";
    saveEditTD.appendChild(Savebtn);

    // Delete Buttons
    var DeleteTD = document.createElement("TD");
    theatersEachRow.appendChild(DeleteTD);
    var Deletebtn = document.createElement("BUTTON");
    Deletebtn.classList.add("btn");
    Deletebtn.classList.add("btn-primary");    
    Deletebtn.setAttribute("onclick", `DeleteTheaterRow(${arg.Id})`);
    Deletebtn.innerHTML= `<i class="fas fa-trash-alt"></i>`;
    DeleteTD.appendChild(Deletebtn);

    return theatersEachRow;
}

function fetchAllTheaters(){
    $.ajax({
        type: 'GET',
        url: '/getAllTheaters',        
        async:false,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function(data) {
            console.log(data); 
            allTheaters = data;           
        }
     });
}

function EditTheaterRow(theaterId){
    var theaterRow = document.getElementById(`theaterId_${theaterId}`);
    var allInputs = theaterRow.getElementsByTagName("INPUT");

    for(var i =0; i < allInputs.length; i++){
        allInputs[i].removeAttribute("disabled");
    }

    //hide edit button
    theaterRow.querySelector(`#editbtn_${theaterId}`).style.display="none";    

    theaterRow.querySelector(`#savebtn_${theaterId}`).style.display="block";    
}

function SaveTheaterRow(theaterId){
    var theaterRow = document.getElementById(`theaterId_${theaterId}`);

    var allInputs = theaterRow.getElementsByTagName("INPUT");

    for(var i =0; i < allInputs.length; i++){
        allInputs[i].setAttribute("disabled","disabled");
    }

    //hide edit button
    theaterRow.querySelector(`#editbtn_${theaterId}`).style.display="block";    

    theaterRow.querySelector(`#savebtn_${theaterId}`).style.display="none"; 

    updateTheater(theaterId);
}

function updateTheater(theaterId){
    var theaterRow = document.getElementById(`theaterId_${theaterId}`);

    var allInputs = theaterRow.getElementsByTagName("INPUT");

    var theaterName = allInputs[0].value;
    var theaterSlots = allInputs[1].value;
    var theaterlocation = allInputs[2].value;

    $.ajax({
        type: 'GET',
        url: '/updateTheater',        
        data: {'theaterId': theaterId, 'theaterName': theaterName, 'theaterSlots': theaterSlots, 'theaterlocation': theaterlocation },
        async:false,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function(data) {
            console.log(data); 
            if(data){
                $('.opreationSuccessToast').toast('show');
            }
        }
     });
}

function addTheater(){
    var theaterName = document.querySelector('#theaterNameInput').value;
    var theaterslots = document.querySelector('#theaterslotsInput').value;
    var theaterLocation = document.querySelector('#theaterLocationInput').value;

    if(theaterName === "" || theaterslots === "" || theaterLocation === ""){
        return;
    }

    if(!isNumeric(theaterslots)){
        return;
    }

    $.ajax({
        type: 'GET',
        url: '/addTheater',        
        data: {'theaterName': theaterName, 'theaterslots': theaterslots, 'theaterLocation': theaterLocation },
        async:false,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function(data) {
            console.log(data); 
            if(data){
                document.getElementById("addtheaterModalClosebtn").click();                
                window.location.reload();
            }
        }
     });
}

function isNumeric(str) {
    if (typeof str != "string") return false // we only process strings!  
    return !isNaN(str) && // use type coercion to parse the _entirety_ of the string (`parseFloat` alone does not do this)...
           !isNaN(parseFloat(str)) // ...and ensure strings of whitespace fail
  }

function DeleteTheaterRow(theaterId){
    $.ajax({
        type: 'GET',
        url: '/deleteTheater',        
        data: {'theaterId': theaterId },
        async:false,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function(data) {
            console.log(data); 
            if(data){
                document.getElementById("addtheaterModalClosebtn").click();                
                window.location.reload();
            }
            else{
                $('.deletiontoast').toast('show');
            }
        }
     });
}