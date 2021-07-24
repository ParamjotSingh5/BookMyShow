let screeningMoviesJSON;

function getScreeningMovies(){
    $.ajax({
        type: 'GET',
        url: '/getScreeningMovies',
        async:false,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function(data) {
            console.log(data);
            screeningMoviesJSON = data;
        }
    });
}


Init();

function Init(){
    getScreeningMovies();
    AddTheaters();
}

function AddTheaters(){

    var mainContentDiv = document.getElementById("mainContent");  

    //iterate over theaters
    for(var prop in screeningMoviesJSON){
        var currentTheater = screeningMoviesJSON[prop];

        var theaterContainer = document.createElement("DIV");
        theaterContainer.classList.add("container");
        theaterContainer.classList.add("m-4");
        theaterContainer.classList.add("theaterContainer");
        theaterContainer.setAttribute("id", "theater"+currentTheater.Id);

        var theaterHead = document.createElement("DIV");
        theaterHead.classList.add("row");
        theaterHead.classList.add("p-2");
        theaterHead.classList.add("align-items-center");
        theaterHead.classList.add("theaterHead");
        theaterContainer.appendChild(theaterHead);

        var theaterNameContainer = document.createElement("DIV");
        theaterNameContainer.classList.add("col");
        theaterNameContainer.classList.add("theaterNameContainer");
        theaterHead.appendChild(theaterNameContainer);

        var theaterName = document.createElement("H2");
        theaterName.innerHTML = currentTheater.Name;
        theaterNameContainer.appendChild(theaterName);

        var TotalSlots = document.createElement("DIV");
        TotalSlots.classList.add("col");
        TotalSlots.classList.add("TotalSlots");
        theaterHead.appendChild(TotalSlots);

        var SlotsIcon = document.createElement("I");
        SlotsIcon.classList.add("fas");
        SlotsIcon.classList.add("fa-chair");
        TotalSlots.appendChild(SlotsIcon);
        
        TotalSlots.appendChild(document.createTextNode(" "+currentTheater.Slots));        

        var theaterLocation = document.createElement("DIV");
        theaterLocation.classList.add("col");
        theaterLocation.classList.add("theaterLocation");
        theaterHead.appendChild(theaterLocation);

        var locationIcon = document.createElement("I");
        locationIcon.classList.add("fas");
        locationIcon.classList.add("fa-map-marker-alt");
        theaterLocation.appendChild(locationIcon);
        
        theaterLocation.appendChild(document.createTextNode(" " + currentTheater.Location));

        var theaterBody = document.createElement("DIV");
        theaterBody.classList.add("row");
        theaterBody.classList.add("p-2");
        theaterBody.classList.add("m-1");
        theaterBody.classList.add("theaterBody");
        theaterContainer.appendChild(theaterBody);

        console.log("movies:" + currentTheater.movies);
        //itearte over movies add inot theaterBody

        for(var movieprop in currentTheater.movies){
           var currentMovie = currentTheater.movies[movieprop];
           
           theaterBody.appendChild(createMovieTemplate(currentMovie));
        }

        mainContentDiv.appendChild(theaterContainer);
    }
}

function createMovieTemplate(movie){

    var movieContainer = document.createElement("DIV");
    movieContainer.classList.add("col-2");
    movieContainer.classList.add("p-3");
    movieContainer.classList.add("m-2");
    movieContainer.classList.add("moviesConatiner");
    
    var movieNameContainer = document.createElement("DIV");
    movieNameContainer.classList.add("row");
    movieNameContainer.classList.add("movieName");
    movieContainer.appendChild(movieNameContainer);

    var movieName = document.createElement("H2");    
    movieName.innerHTML = movie.Name;
    movieNameContainer.appendChild(movieName);

    var movieStartDateContainer = document.createElement("DIV");
    movieStartDateContainer.classList.add("row");
    movieStartDateContainer.classList.add("movieStartDateContainer");
    movieContainer.appendChild(movieStartDateContainer);

    var movieStartDate = document.createElement("P");    
    movieStartDate.innerHTML = "Shows Starts On " + movie.ScreeningStartDate;
    movieStartDateContainer.appendChild(movieStartDate);

    var movieEndDateContainer = document.createElement("DIV");
    movieEndDateContainer.classList.add("row");
    movieEndDateContainer.classList.add("movieEndDateContainer");
    movieContainer.appendChild(movieEndDateContainer);

    var movieEndDate = document.createElement("P");    
    movieEndDate.innerHTML = "Up to "+ movie.ScreeningEndDate;
    movieEndDateContainer.appendChild(movieEndDate);

    var movieShowTimeContainer = document.createElement("DIV");
    movieShowTimeContainer.classList.add("row");
    movieShowTimeContainer.classList.add("movieShowTimeContainer");
    movieContainer.appendChild(movieShowTimeContainer);

    var movieShowTime = document.createElement("P");    
    movieShowTime.innerHTML = "Show Timing: " + movie.Showtime.Shift;
    movieShowTimeContainer.appendChild(movieShowTime);

   return movieContainer;
}




