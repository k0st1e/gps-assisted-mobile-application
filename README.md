## GPS Assisted Mobile Application

The aim of this mobile application is to take a user's location, some text that is entered via text fields and create a route from visited places. See below for current features and future work.

### Current Features

* Log-in & Registration is present.
* GPS geolocation works and pins can be added.
* Pages for self routes, other users routes, and trophies page is there to visit from the nav-bar.

### To-Do

* Security needs to be implemented. Sessions for the web and tokens for mobiles.
* Some small leaps to achieve route persistence so the users can see their routes and others.
* Gamify the experience to make it more fun. For example, give a "reward" if a user completes 5 routes.
* Add photograph functionality to complement a route(?)

### Misc

* Third-party libraries are needed and can be found in the gradle files. They are commented appropriately.
* The maps are implemented are with the help of Mapbox and the token is omitted.
* The back-end that the application communicates is omitted as the application is still in it's early stages.
* See the [springboot back-end](https://github.com/k0st1e/springboot-app-for-mobile-application) repository.
