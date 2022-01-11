# canary-weather

Project of Android application that show basic weather information for the following 7 days based on your local position.

## Setup Instruction
In order to run the application you have to follow these steps:
* Clone the entire project from branch master usign GIT.
* Open the project usign the Android Studio IDE.
* Build the project and run on a phisical device or simulator.
* Enjoy the app

## The App
### Description
The application is composed of a main page that show information about temperature and weather condition of the current day. With a swipe is it possible to move the the next days and by clicking to the detail button a detail page appear with some specific information about that day such as: sunrise, moonrise, wind speed, ecc.

## Architecture and Design
The project is a single Activity application with a Model-View-ViewModel (MVVM) architecture combined with a Room Database and a repository system to access the data. I choose this architecture because moves some of the logic outside the activity/fragment and thanks to the ViewModel we avoid the problem of managing some logic related to lifecycle.

The app uses [Retrofit](https://square.github.io/retrofit/) to retrieve the data about the weather from an free API service [OpenWeatherApi](https://openweathermap.org/api). The information is retrieved in the backround from the API and stored in the Room Database using corourines. The app shows the data inside the database and react when they are updated thanks to the use of Kotlin Flow and LiveData. If there is no connection or the API fails during the request the app continues showning the old information stored inside the database and the error is managed by the system architecture and could be showed in the UI thanks to the ResultWrapper class.

The forecast for the following 7 days is showed using a ViewPager2 with a FragmentStateAdapter that create a fragment for each day with the specific epoch in the constructor of the fragment. I create a special view model class called ViewModelStore scoped on the single Activity lifecycle, this special view model permits to share data between fragments in a easy way.

## Assignment consideration
The project follows some of the [clean architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) principles, it has a very basic UI that could be improved but I preferred to concentrate my effort on the architecture that I think it is more relevant for the assignment.
