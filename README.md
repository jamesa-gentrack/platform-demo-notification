## Platform Push Notification Demo

### Introduction
This repository demonstrates integration between [Gentrack Platform](https://help.gentrack.com/platform) and [Taplytics](https://taplytics.com/).

The use case is:

1. A bill is generated in a Gentrack core system, Velocity or Junifer.
2. A event is triggered and published to the Gentrack Platform.
3. The Gentrack Platform publishes the event to a registered *webhook*.
4. The webhook sends a statement to an individual customer via a push notification to an Android app.

This repository implements a sample webhook and an Android application:

* webhook - a NodeJs/Express application that receives bill ready events from the Platform and then pushes notifications to registered devices via Taplytics APIs.
* Android Application -  An Android application that receives bill ready notifications and presents end customers a bill statement on the screen.

#### Build Android Application
1. Download and install the latest Android Studio 3.
2. Sign up an account and create an app on Taplytics.
3. At the `SDK integration` step on the welcome page for the Taplytics App, select `Android/TV/FIre TV`. Find `TAPLYTICS ISO/ANDROID SDK KEY` and `TAPLYTICS ISO/ANDROID URL SCHEME`.
4. Replace the values in Android app:
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
5. Build and run the application.
6. When testing the application in an Emulator, it requies an image with `API level = 24 and Target = Android 7.0(Google APIs)`. In addition, you need to log in to a google account in the emulator in order to receive notifications.
7. Open the App on Taplytics. It should now display to a dashboard.
8. Set up [Google Push Certificates](https://taplytics.com/docs/guides/push-notifications/google-push-certificates) for the APP.
9. Restart the Android App.

### Deploy Webhook to Heroku
* Deployment
    1. Clone the repository and cd into the directory
    2. Download heroku cli and deploy the application. See [Deploying Node.js Apps on Heroku](https://devcenter.heroku.com/articles/deploying-nodejs#deploy-your-application-to-heroku)
    ```
        heroku login
        heroku create
        git push heroku master
    ```
    3. Test the URL in a browser, e.g, `http://my-app.herokuapp.com`.
    The webhook URL will be `http://myapp.herokuapp.com/webhook`
* Environment variables:
    * `heroku config:add PUBLIC_KEY="$(cat ~/pubkey.pem)"`
        * pubkey.pem` is a text file containing the public key for the application which can be obtained from the platform developer portal.
    * `heroku config:set TAPLYTICS_API_TOKEN="TAPLYTICS REST API PRIVATE KEY"`


#### Send Notifications from Developer Portal
1. Create a new application in Developer.
2. Set the end point to the Heroku webhook, e.g, `http://myapp.herokuapp.com/webhook`.
3. Send a test event.

### Disclaimer
It is for demonstration only.
