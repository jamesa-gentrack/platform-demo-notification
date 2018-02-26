## Platform Push Notification Demo

### Introduction
This repository demonstrates integration between [Gentrack Platform](https://help.gentrack.com/platform) and [Taplytics](https://taplytics.com/).

The use case is:

1. A bill is generated for a customer in a Gentrack core system, Velocity or Junifer.
2. The core system triggers an event and publishes it to the Gentrack Platform.
3. The Gentrack Platform then publishes the event to a registered *webhook*.
4. The webhook sends a push notification to the customer, through an *Android app* running on the customer's phone.
5. The customer receives and taps the notification to view the bill statement on his or her phone.

This repository implements a sample *webhook*  and an Android application:

* *webhook* - a NodeJs/Express application that receives bill events from the Platform and then pushes notifications to registered devices using Taplytics APIs.
* *Android App* -  A mobile app that receives push notifications and presents statement on screen.

#### Build Android Application
1. Download and install the latest Android Studio 3.x.
2. Clone the source code from this repository and open the project in Android Studio.
3. Sign up a Taplytics account and create an application.  Select `Android/TV/FIre TV`, at the `SDK integration` step on the welcome page,
4. Find values for `TAPLYTICS ISO/ANDROID SDK KEY` and `TAPLYTICS ISO/ANDROID URL SCHEME`, and replace them in the following files in the Android app:
    * TAPLYTICS ISO/ANDROID SDK KEY
    ```
    File: src\main\java\io\gentrack\platformnotificationdemo\App.java
    ...
    Taplytics.startTaplytics(this, "#########");
    ...
    ```
    * TAPLYTICS ISO/ANDROID URL SCHEME
    ```
    File: src\main\AndroidManifest.xml
    ...
    <data android:scheme="##########" />
    ```
5. Build and run the application. To test the application in an Emulator, you must create an image with `API level = 24 and Target = Android 7.0(Google APIs)`. In addition, you must add and log in to a Google account in the Emulator in order to receive push notifications.
6. When the application is running, it will connect to Taplytics to complete integration setup.
8. Set up [Google Push Certificates](https://taplytics.com/docs/guides/push-notifications/google-push-certificates) from the dashboard on Typlystics.
9. Restart the Android application and it is ready to receive push notifications.

### Deploy Webhook to Heroku
* Deployment

    [![Deploy to Heroku](https://www.herokucdn.com/deploy/button.png)](https://heroku.com/deploy?template=https://github.com/jamesa-gentrack/platform-demo-notification)

    1. Click the `Deploy to Heroku` button to start the process.
    2. On the next page, enter your application's public key and Taplytics API key.
    3. Test the URL in a browser, e.g, `http://my-app.herokuapp.com`.
    The webhook URL will be `http://myapp.herokuapp.com/webhook`

#### Send Notifications from Developer Portal
1. Create a new application in Developer.
2. Set the end point to the Heroku webhook, e.g, `http://myapp.herokuapp.com/webhook`.
3. Send a test event.

### Disclaimer
It is for demonstration only.
