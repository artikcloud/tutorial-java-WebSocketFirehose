# Tutorial Java Firehose WebSocket

This is a starter code to connect to ARTIK Cloud [Firehose WebSocket (/live)](https://developer.artik.cloud/documentation/data-management/rest-and-websockets.html#firehose-websocket) endpoint using ARTIK Cloud Java SDK.  By running this sample you will learn to:

- Connect to the ARTIK Cloud Firehose WebSocket url.
- Monitor realtime messages sent to ARTIK Cloud by the specified source device.

Consult [An IoT remote control](https://developer.artik.cloud/documentation/tutorials/an-iot-remote-control.html#develop-an-android-app) for how to develop an Android monitor app.

## Requirements

- Java 7 or higher 
- ARTIK Cloud Java SDK (version >= 2.1.2)
- [Apache Maven](https://maven.apache.org/download.cgi)

## Setup

### Setup at ARTIK Cloud

1. Go to the Devices Dashboard (https://my.artik.cloud) and [add a new device](https://developer.artik.cloud/documentation/tools/web-tools.html#connecting-a-device):

```
Device Type Name: Example Simple Smart Light
Unique Name: cloud.artik.example.simple_smartlight
```
2. Get the device ID for your newly created device in the [Device Info](https://developer.artik.cloud/documentation/tools/web-tools.html#managing-a-device-token) screen.
3. Retrieve a [user token](https://developer.artik.cloud/documentation/tools/api-console.html#find-your-user-token) with our api-console.

Save the device ID and user token for use later.

### Setup Project

#### 1. Setup project:

1. We will use Eclipse as our IDE to load our sample code.  Import the project as a **maven project**:

```
1. Select the File menu then select Import.
2. Select Maven => Existing Maven Projects
3. Browse and select the Root Directory to this sample code. 
```

2. Or, if you do not have maven you can simply create a new Java Project:

```
1. Create a new Java Project with Eclipse.
2. Copy the Config.java and Monitor.java file into the 'src' folder of your new Java Project.
```

3. Fill in your user token and device token obtained earlier into the 'Config.java' file

```java
public class Config {
	public static final String DEVICE_ID = "YOUR_DEVICE_ID";
	public static final String USER_TOKEN = "YOUR_USER_TOKEN";	
}
```

#### 2. Install dependencies

This dependencies setup is only required if you did not import the project with maven.

1. Download the needed jar files:

```
1. Using your browser, search for 'artik cloud' on https://search.maven.org
2. In the Download section, download the jar-with-dependencies.jar jar file.
3. In Eclipse, import the jar file into your project by:

```
2. Import the downloaded jar into your new Java project:

```
1. Select Your Project in the Eclipse Package Explorer
2. Select File => Properties => Java Build Path
3. Click on Libraries => Add External JARs
4. Select the .jar file you downloaded earlier.
```

#### 3. Run Project

1. Select your project from the Eclipse IDE.
2. Select `run` from the menu. 

```bash
> Status: CONNECTING ...
< Connected!
```

## Demo:

1. Send messages to your Example Simple Smart Light using the [Online Device Simulator](https://developer.artik.cloud/documentation/tools/web-tools.html#using-the-online-device-simulator).   This sample has the following settings:

```
* Simulate data on the boolean "state" field.  Keep the default interval to 5000 ms (5 secs).   
* Alternative between true/false value by setting the Data Pattern to Alternating Boolean.
```

2. Go back to your running sample application.   The terminal screen should output a message every 5 seconds:

```bash
< Received message: class MessageOut {
    data: {state=true}
    cid: null
    ddid: null
    sdid: a12345...
    ts: 1504311135000
    type: message
    mid: 920fd5dbd81f4e6b97500252138f1cfb
    uid: b67890a ...
    sdtid: dtd1d3e0934d9348b783166938c0380128
    cts: 1504311135834
    mv: 1
}
< message.getData(): {state=true}

< Received message: class MessageOut {
    data: {state=false}
    cid: null
    ddid: null
    sdid: a12345...
    ts: 1504311140000
    type: message
    mid: 8913a306bb3b4a5392288fad9a607961
    uid: b67890a ...
    sdtid: dtd1d3e0934d9348b783166938c0380128
    cts: 1504311140606
    mv: 1
}
< message.getData(): {state=false}
```

## More about ARTIK Cloud

If you are not familiar with ARTIK Cloud, we have extensive documentation at <https://developer.artik.cloud/documentation>

The full ARTIK Cloud API specification can be found at <https://developer.artik.cloud/documentation/api-reference/>

Peek into advanced sample applications at [https://developer.artik.cloud/documentation/tutorials/code-samples](https://developer.artik.cloud/documentation/tutorials/code-samples/)

To create and manage your services and devices on ARTIK Cloud, visit the Developer Dashboard at [https://developer.artik.cloud](https://developer.artik.cloud/)

## License and Copyright

Licensed under the Apache License. See [LICENSE](./LICENSE).

Copyright (c) 2017 Samsung Electronics Co., Ltd.
