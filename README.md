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

#### Build Android Mobile Application
1. Download and install the latest Android Studio 3.x.
2. Open Android Studio and create a new project using the checking out from version control -> Git.
3. Sign up a Firebase account https://firebase.google.com/ if you don't already have one.
4. From the Firebase console add a new project.
5. Sign up a Taplytics account https://taplytics.com/ if you don't already have one.
6. From the Taplytics console add a new project application.  Select `Android/TV/FIre TV`, at the `SDK integration` step on the welcome page,
7. Find values for `TAPLYTICS ISO/ANDROID SDK KEY` and `TAPLYTICS ISO/ANDROID URL SCHEME`, and replace them in the following files in the Andriod Studio project:
    * TAPLYTICS ISO/ANDROID SDK KEY
    ```
    File: app\src\main\java\io\gentrack\platformnotificationdemo\App.java
    ...
    Taplytics.startTaplytics(this, "#########");
    ...
    ```
    * TAPLYTICS ISO/ANDROID URL SCHEME
    ```
    File: app\src\main\AndroidManifest.xml
    ...
    <data android:scheme="##########" />
    ```
8. Build and run the application. To test the application in an Emulator, you must create an image with `API level = 24 and Target = Android 7.0(Google APIs)`. In addition, you must add and log in to a Google account in the Emulator in order to receive push notifications.
9. When the application is running, it will connect to Taplytics to complete integration setup.
10. Set up [Google Push Certificates](https://taplytics.com/docs/guides/push-notifications/google-push-certificates) from the dashboard on Taplytics using the keys from the Firebase project.
11. Restart the Android application and it is ready to receive push notifications.

### Deploy Webhook Application
[![Deploy to Heroku](https://www.herokucdn.com/deploy/button.png)](https://heroku.com/deploy?template=https://github.com/jamesa-gentrack/platform-demo-notification)

1. Create a new application in the Platform Developer Portal (https://portal.gentrack.io/).
2. Click the `Deploy to Heroku` button to host the webhook.
3. On the next page, enter the Platform application's public key (App Settings -> Basic Information).
4. And Taplytics API key (Project Settings -> Taplytics REST API Private Key).
5. Test the URL in a browser, e.g, `https://(app-name).herokuapp.com`.
6. Subscribe webhook URL `https://(app-name).herokuapp.com/webhook` to the `bill-ready` event in the developer portal (Event Subscriptions -> Edit).
7. Send a test `bill-ready` event (Event Subscriptions -> Send Test Event).  A push notification will be received by the mobile application. 

### Disclaimer
This is a proof of concept for demonstrations only.
