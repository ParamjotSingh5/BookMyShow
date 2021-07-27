# BookMyShow
Do you like movies, experience them in awarded theaters near you. Quickly book your tickets here.

# Setup

###### Prerequisite
* JAVA 8
* MySQL 
* MySQL Workbench to Import Database (Note: skip this, if you would like to import Database from the console)
* Any JAVA IDE (Intellij Idea, Eclipse)

#### Connecting with local Database 
This project is configured to connect with MySQL using `mysql-connector-java` dependency. Change the credentials that will be used to make connection with MySQL in `config.properties` file, located at `src/main/java/configs/config.properties`. 

Set these credentails :

`db.url=jdbc:mysql://localhost:3306`

`db.DatabaseName= Database Name`

`db.user= User Name`

`db.password= Your MySQL Password`

When you run this project, if port 8080 is already occupied by any other process on your system, the project will run on 8081 port, however, you can change it inside the `config.properties` file, just change the value of 

`server.port= Port number to run this project on`


Here are some screens available to access within the web app 

Admin View for managing theaters
![alt text](https://github.com/ParamjotSingh5/BookMyShow/blob/main/BookMySHowAdmin%20-%20manage%20theater.png)

Admin View Adding a new theater 
![alt text](https://github.com/ParamjotSingh5/BookMyShow/blob/main/BOOKMYSHOWAdmin%20-%20manage%20theater_ADDTheater.png)

Currently Screened Movies View.
![alt text](https://github.com/ParamjotSingh5/BookMyShow/blob/main/BookMySHow_Landing.png)

Users can book a movie here and can view Order Summary at the same time 
![alt text](https://github.com/ParamjotSingh5/BookMyShow/blob/main/BookMySHow_book%20a%20movie.png)

