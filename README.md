## Platform Push Notification Demo

### Introduction
This repository demonstrates integration between [Gentrack Platform](https://help.gentrack.com/platform) and [Taplytics](https://taplytics.com/).

The use case is:

1. A bill is generated in a Gentrack core system, Velocity or Junifer.
2. A event is triggered and published to the Gentrack Platform.
3. The Gentrack Platform publishes the event to a registered customer webhook.
4. The webhook sends a statement to an individual customer via a push notification to an Android app.

### Source File Structure

1. webhook - a sample NodeJs/Express application that receives bill ready events from the Platform and then sends push notifications to registered devices via Taplytics APIs.
2. mobile/App/Android -  A sample Android application that registers and receives bill ready notifications and displays a bill statement.

### Sign up a Taplytics account
1. Add a new APP.  Then follow the instructions to set up the Taplytics SDK integration in the Android project.
2. Obtain the SDK/API keys for the application from `Open Settings/Project Settings`.

 * For Android App: `TAPLYTICS ISO/ANDROID SDK KEY` and `TAPLYTICS ISO/ANDROID URL SCHEME`
 * For webhook: `TAPLYTICS REST API PRIVATE KEY`

### Deploy webhook to Heroku
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


#### Build Android Application
1. Download and isntall Android Studio 3.0.
2. Open the project `mobileApp/android`. Replace TAPLYTICS ISO/ANDROID SDK KEY` and `TAPLYTICS ISO/ANDROID URL SCHEME` with keys obtained in the previous step in the source code.

    * TAPLYTICS ISO/ANDROID SDK KEY
    ```
    File: mobileApp\android\app\src\main\java\io\gentrack\platformnotificationdemo\App.java
    ...
    Taplytics.startTaplytics(this, "#########");
    ...
    ```    
    * TAPLYTICS ISO/ANDROID URL SCHEME
    ```
    File: mobileApp\android\app\src\main\AndroidManifest.xml
    ...
    <data android:scheme="##########" />
    ```
3. Build and run the application.
4. When testing the application in an Emulator, it requies an image with `API level = 24 and Target = Android 7.0(Google APIs)`. In addition, you need to log in to a google account in the emulator in order to receive notifications.

#### Send notifications from Developer Portal
1. Create a new application in Developer.
2. Set the end point to the Heroku webhook, e.g, `http://myapp.herokuapp.com/webhook`.
3. Send a test event.

### Disclaimer
It is for demonstration only.
