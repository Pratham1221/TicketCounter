# TicketCounter
This is a tickets buying and selling platorm where organisers can sell their tickets
and people can buy tickets for their favourite shows. People who want to  
sell their tickets can use this platform to sell tickets in "pdf" 
form where the ticket will have the desired credentials of the person buying their tickets.

I am really excited for this project as it is my very first java project and I personally feel it  
can be used by people and have some real life help but in worst case if it turned out  
to be redundant I will definitely use it if I ever organised an event.

Language: Java

## User Stories

- As a user, I want to be able to add my event on the software.
- As a user, I want to be able to receive a ticket of the show with my credentials.
- As a user, I want to be able to see the number of tickets available for a particular show.
- As a user, I want to be able to see the current shows and buy tickets.
- As a user, I want to be able to load my previous data.
- As a user, I want to be able to save my data.

## Instructions

- You can generate the first required event related to adding an event by logging in  
  as an organiser and click on add event.
- You can generate the second required event related to buying a ticket by logging in  
  as a user and select a show and then buy a ticket, pdf of the ticket can also be viewed  
  by clicking on "my shows".
- You can locate my visual component at the starting of the program, the program  
  shows a logo of the app for first 2 seconds.
- You can save the state of my application by going to the login screen and close the application,  
  a dialog box with instructions to save the data will appear.
- You can reload the state of my application at the very start of the program, a dialog  
  box will appear with instructions to load prevoius saved data.

## Event Log

Fri Dec 02 01:40:04 PST 2022  
pratham created an event named: Nabeel graduation  
Fri Dec 02 01:40:21 PST 2022  
pratham purchased a ticket for HouseParty  
Fri Dec 02 01:40:23 PST 2022  
pratham purchased a ticket for Nabeel graduation

## The Program Design

- My design has three main classes Event, Organiser and User.
- Each event has an organiser and each organiser has a list of events
- Each user has a list of events that they have bought tickets for.
- JsonReader and JsonWriter are dependent on all the three main classes to read and 
  write data.
- The EventLog class has a list of events that are stored and printed at the end on the  
  console. Both Organiser and User are in some way dependent on both eventlog and events 
  to keep track of logs.

## Some future improvements...

- Make association between event and user class such that each event has a list of  
  users that are attending the event, this is missing in the current implementation.
- Make a parent class named person(or something similar) to the organiser and user class so that  
  I can group the common features like name, username, showsList etc. in the parent  
  class and fix redundancy.
