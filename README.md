# Dogstagram - Instagram for Dogs! 😉

Here are the features of the app:

1. It fetches a list of dog breeds and sub-breeds from the [Dog CEO API](https://dog.ceo/dog-api/)
2. Selecting any breed or sub=breed shows their images
3. Clicking any image on the grid gets a single image view
4. Image can be swiped left-right on the single image view
5. Slide the image up (or press the back button) to go to the grid of images
6. Internet connectivity checks are gracefully handled

# App architecture

1. The app is written fully using Kotlin
2. It follows MVVM architecture where fragments observe the viewmodels. Viewmodels get data from repositories and the repository talks to the data sources. In the current implementation, repositories talk to only remote data sources.
3. The overall app is divided into multiple modules as per their responsibilities:
    - The `:app` module is responsible for rendering UI on the screen. It depends on a `:viewmodels` module.
    - The `:viewmodel` module is responsible for presentation logic and it contains all the viewmodels. It depends on `:data` module.
    - The `:data` module is responsible for providing data to the viewmodel from different data sources. It depends on the `:domain` module
    - The `:domain` module contains all the models related to the domain.
4. The app uses a few Jetpack libraries like:
    - LiveData
    - Viewmodel
    - navigation
    - fragment-ktx
    - recyclerview
    - constraintlayout
5. The app follows a reactive programming pattern and it uses Kotlin Coroutines and Flow
6. Dagger2 has been used for Dependency Injection
7. A few other third-party libraries includes:
   - Retrofit and OkHttp for network communication
   - GSON for serialization
   - Picasso for image loading
   - stfalcon-imageviewer for implementing imageviewer
   - multilevelview for multi-level recyclerview

# Running

To run the app open the project on AndroidStudio 4.1.3 and run the `app` module. If there are compilation errors, it may be because of the compile-time code generation.

Try `gradlew clean` in that case and run the app again.

# Unit test cases

`:viewmodel` module and `:data` module contains unit test cases and they can be executed using the following command:
```
$ ./gradlew clean testDebugUnitTest
```

_Test summary can be seen on the console output._

# Possible improvements

1. Data persistence using Room database for the listing of breeds and sub-breeds.
2. Implementing layouts using Jetpack compose. Still learning it and could not do it due to time constraints
3. Dagger can be replaced with Hilt
4. UI test cases using Espresso
5. Code coverage using JaCoCo
