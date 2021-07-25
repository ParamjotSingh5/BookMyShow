let movieDetails = null;
let availableSlotsData= null;
Init();

function Init(){
    getMoviesDetails();
    AddElements();
}

function getMoviesDetails(){

    var url_string = window.location.href;
    var url = new URL(url_string);
    var movieId = url.searchParams.get("Id");

    $.ajax({
        type: 'GET',
        url: '/getMovieDetails',
        data: { 'movieId': movieId },
        async:false,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function(data) {
            console.log(data);
            movieDetails = data;
        }
     });
}

function AddElements(){   

    createMovieTemplate(movieDetails.movie, movieDetails.theater);
    addOrderHistory(movieDetails.orderHistory);
}

function addOrderHistory(orderHistoryArray){
    var orderHistoryContainer = document.getElementById("orderHistroyTBody");
    
    for(var each in orderHistoryArray){
        orderHistoryContainer.appendChild(createEachOrderHistory(orderHistoryArray[each]));
    }

}

function createEachOrderHistory(arg){

    var orderHistoryEachRow = document.createElement("TR");
    orderHistoryEachRow.setAttribute("scope","col");

    var movieName = document.createElement("TD");    
    movieName.innerHTML = arg.MovieName;
    orderHistoryEachRow.appendChild(movieName);

    var slots = document.createElement("TD");    
    slots.innerHTML = arg.SlotsBooked ;
    orderHistoryEachRow.appendChild(slots);

    var reservationDate = document.createElement("TD");    
    reservationDate.innerHTML = arg.ReservationDate;
    orderHistoryEachRow.appendChild(reservationDate);

    var theatreName = document.createElement("TD");    
    theatreName.innerHTML = arg.TheaterName;
    orderHistoryEachRow.appendChild(theatreName);

    return orderHistoryEachRow;
}

function createMovieTemplate(movie, theater){
    
    var movieImgDiv = document.getElementById("movieImg");
    
    var movieImg = document.createElement("IMG");
    movieImg.setAttribute("src", "/images/generic_Movie.jpg");    
    movieImg.setAttribute("width", "200");
    movieImg.setAttribute("alt", "suppp");
    movieImgDiv.appendChild(movieImg)

    var movieName = document.getElementById("movieName");
    movieName.innerHTML = `Movie Name: ${movie.Name}`;

    var movieGenre = document.getElementById("movieGenre");
    movieGenre.innerHTML = `Genre: ${movie.Genre}`;

    var movieScreeningStartEndDate = document.getElementById("movieScreeningStartEndDate");
    movieScreeningStartEndDate.innerHTML = `Movie Screening starts from ${movie.ScreeningStartDate} up to ${movie.ScreeningEndDate}`

    var theaterName = document.getElementById("theaterName");
    theaterName.innerHTML = `Theater Name: ${theater.Name}`;

    var theaterLocation = document.getElementById("theaterLocation");
    theaterLocation.innerHTML = `Theater Location: ${theater.Location}`;

    //create Reservation Date selector
    let screeningStartDate = new Date(movie.ScreeningStartDate);
    let screeningEndDate = new Date(movie.ScreeningEndDate);

    var DateSpans = dateRange(screeningStartDate, screeningEndDate);

    createReservationDateSelector(DateSpans);   

    var firstELem = document.getElementById("reservationDateSelector").firstChild;
    reservationSelectChanged(firstELem);
}

function createReservationDateSelector(dateSpanArray){
    var selector = document.getElementById("reservationDateSelector");

    for(let singleDay in dateSpanArray){
        var z = document.createElement("option");
        z.setAttribute("value", dateSpanArray[singleDay].toDateString());
        var t = document.createTextNode(dateSpanArray[singleDay].toDateString());
        z.appendChild(t);

        selector.appendChild(z);
    }
}

function dateRange(startDate, endDate, steps = 1) {
    const dateArray = [];

    var todayDate = new Date();

    if(startDate < todayDate){
        startDate = todayDate;
    }

    const diffTime = Math.abs(endDate - startDate);
    const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24)); 

    let currentDate = new Date(startDate);

    for(var i=0; i < diffDays+1; i++){
        dateArray.push(new Date(currentDate));
        // Use UTC date to prevent problems with time zones and DST
        currentDate.setUTCDate(currentDate.getUTCDate() + steps);
    }   
  
    return dateArray;
}

function reservationSelectChanged(arg){
    var selectedDate = (arg.value || arg.options[arg.selectedIndex].value);  
    getAvailableSlotsOnSelectedDate(selectedDate);
    
    var availableSlotsCount = availableSlotsData.AvialbaleSlots - availableSlotsData.SlotsBooked;

    if(availableSlotsCount < 9){
        createSlotCountSelector(availableSlotsCount);
    }
    else{
        createSlotCountSelector(9);    
    }  

    var AvailableSlotsDIv = document.getElementById("AvailableSlotsDIv");
    AvailableSlotsDIv.innerHTML = `Total Slots: ${availableSlotsData.AvialbaleSlots}  Available Slots: ${availableSlotsCount}`;
}

function createSlotCountSelector(slotsCountToShow){
    var selector = document.getElementById("slotsCountSelector");

    selector.innerHTML = '';

    for(var i=0; i<= slotsCountToShow; i++){
        var z = document.createElement("option");
        z.setAttribute("value", i);
        var t = document.createTextNode(i);
        z.appendChild(t);

        selector.appendChild(z);
    }
}

function getAvailableSlotsOnSelectedDate(selectedDate){
    
    $.ajax({
        type: 'GET',
        url: '/getAvailableSlotsByDate',
        data: { 'selectedDate': selectedDate, 'movieId': movieDetails.movie.Id },
        async:false,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function(data) {
            console.log( data);
            availableSlotsData = data;
        }
     });
}

function slotsCountSelectChanged(arg){
    var slotsCount = (arg.value || arg.options[arg.selectedIndex].value); 

    if(slotsCount > 0){
        var btn = document.getElementById("bookNowbtn");
        btn.classList.remove("disabled");
    }
    else{
        var btn = document.getElementById("bookNowbtn");
        btn.classList.add("disabled");
    }
}

function bookThisMovies(){

    var dateSelectedEle = document.getElementById("reservationDateSelector");
    var dateSelected = dateSelectedEle.options[dateSelectedEle.selectedIndex].text;

    var slotSelectEle = document.getElementById("slotsCountSelector");
    var slotsCount = slotSelectEle.options[slotSelectEle.selectedIndex].text;

    $.ajax({
        type: 'GET',
        url: '/bookMovie',
        data: { 'movieId': movieDetails.movie.Id, 'dateSelected': dateSelected, 'slotsCount': slotsCount },
        async:false,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function(data) {
            console.log(data);            
            if(data > 0){
                document.getElementById("movieBooked").removeAttribute("style");
            }
        }
     });

}