# rmi-distributed-system
RMI Distributed System

Project Install Instructions :

1 ) Download the Api s folder from the github link.
Open the Api s folder using an IDE (Eclipse or Intellij).
Give this command in the terminal : mvn install -Dmaven.test.skip=true

Before running Api s : Run the sql in the desktop.
Run the apis :
	If you are using the eclipse IDE, run as java application.
	If you are using the Intellij IDE, you can run as application.


2 ) Then Clone or download the angular web application from the github link.
Open the project folder in the command prompt.
Give this command in the cmd : npm install
After installing the npm , give this command : ng serve -o
(In this web app, there are no initial records as it calls the Api after 
10 seconds and this is done periodically in every 10 seconds )

3 ) Download the RMI from the github link.
Configure java 13 in eclipse.
Run the RMI server.

Admin Login Details
email : admin
password : admin321

(If there are any errors : add the external libraries to the project)
(Initially no sensors are present. So add the sensors to view the sensors)

