### Platform Push Notification Demo

This repository contains two applications demonstrateing the integration between [Gentrack Platform](https://help.gentrack.com/platform) and [Taplytics](https://taplytics.com/), for sending
bill ready push notifications to an Android application.

1. webhook - a NodeJs/Express webhook application that receives bill ready notifications from the Platform and then send push notifications to registered devices via Taplytics APIs.
2. mobile/App/Android -  An android application that registers and receives bill ready notifications and displays a bill statement.

### Sign up a Taplytics account
1. Add a new APP.  Then follow the instructions to set up the Taplytics SDK integration in the Android project.
2. Obtain the SDK key/API token for the project from `Open Settings/Project Settings'
 * For Android App: `TAPLYTICS ISO/ANDROID SDK KEY` and `TAPLYTICS ISO/ANDROID URL SCHEME`
 * For webhook: `TAPLYTICS REST API PRIVATE KEY`

### Deploy webhook to Heroku
1. Environment variables:
    * `heroku config:add PUBLIC_KEY="$(cat ~/pubkey.pem)"``
        * pubkey.pem` is the public key for the application which can be obtained from the platform developer portal.
    * `heroku config:set TAPLYTICS_API_TOKEN="TAPLYTICS REST API PRIVATE KEY"`


#### Build Android Application
1. Download and isntall Android Studio 3.0.
2. Open the project `mobileApp/android`. Replace TAPLYTICS ISO/ANDROID SDK KEY` and `TAPLYTICS ISO/ANDROID URL SCHEME` with keys obtained in the previous step in the source code. Build and run the application.
3. If run the application in an Emulator, create an image with `API level = 24 and Target = Android 7.0(Google APIs)`. In addition, you need to log in to a google account in the emulator.


#### Send notifications from Developer Portal
1. Create a new application in Developer.
2. Set the end point to the Herohu URL.
3. Send a test event.

### Disclaimer
It is for demonstration only.
