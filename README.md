# Workout application

**This repository is created for the backend of our workout app. The project is for Mobile Programming course of Häme University of Applied Sciences. Here you can check [the frontend](https://github.com/jenhakk/workout-app) part.** 

Team: [Jenna Hakkarainen](https://github.com/jenhakk), [Amanda Karjalainen](https://github.com/amakarj), [Anna-Maria Palm](https://github.com/A-d-f) 

## Description

App is designed to keep track of workouts and personal measurements. It focuses primarily on training done with weights or own body weight,
which consists of series, repeats and weights or duration. The app is easy and simple to use and doesn’t take time from the actual workout.
The user can browse their previous measurements and workout sessions and see their progress. The app is targeted for goal-oriented trainers regardless of gender or age,
but for now the app doesn’t serve all sports, for example running, swimming, etc. 

The backend is built with **Java, MySQL and REST services** and it is mainly used for handling database queries. 
Data is saved in local database and it contains following tables:

* **Exercise:** includes exercise ids, names, picture names and boolean value checked (for checking if radio button has been clicked)
* **Measurements:** includes all measurements, measurement id, person id and date
* **Person:** includes user's personal information e.g. person id, name, location, height, slogan and picture name
* **Workout:** includes information of who has done the workout (id), workout's id and the date
* **WorkoutExercise:** includes details of done exercise in specific workout e.g. repeats, weights, duration with information about exercise, person id and date

