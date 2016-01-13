### JXCore Android
JXCore android sample with no cordova.

**jxcore-android:** Library with jxcore native libs and java sources to initialize Jxcore. To use it you need to create an instance of the class jxcore.

- Initialize
```java
  jxcore jx = new jxcore();
  jx.pluginInitialize();
```

**android-jxcore-sample:** Simple android app to test Jxcore fuctionality. 
- It has http server (implemented in Javascript) that can be started and stoped, with two buttons. 
- Shows the status of the sever in a TextView.
- More details of how Jxcore engine starts can bee seen in android logs.
- Defines some methods to comunication Java->Javascript and Javascript->Java.

#### Functions
- Register Java function that can be called from Javascript
```java
    jxcore.RegisterMethod("serverStarted", new jxcore.JXcoreCallback() {
            @SuppressLint("NewApi")
            @Override
            public void Receiver(ArrayList<Object> params, String callbackId) {
                Log.i("jxcore", "Callback, ServerStarted! ");
            }
        });
```
- Call a registered Java function from Javascript. Using the JXCoreModule just use the function "callJavaFunction".
```javascript
     this.callJavaFunction("serverStarted");
```
- Register a Javascript function that can be called from Java. Adding registered functions to the method "registerJSFunction" into the jxcore_module.js
```javascript
    var startServerFunction = new JSFunction("JXCoreModule_startServer", function() {
            wsLocalServer.startServer();
            console.log("JXCoreModule_startServer method");
        } );
        
        return [startServerFunction];
```
- Call a registered Javascript function from Java.
```java
    jxcore.CallJSMethod("JXCoreModule_startServer", "{}");
```

#### Test
To test directly the projects follow the next instructions.

1. Install jxcore-android in local repository.

```sh
  $ cd jxcore-android
  $ gradle install
```
  
2. Install android-jxcore-sample in device (check the log to see that JXCore engine starts and that the http server works)

#### Installation in your own android project
- Install jxcore-android, "gradle install"
- Add the mavenLocal() to the repositories
- Add the dependecie in the gradle script of the android project
```javascript
     compile 'com.carlgira:jxcore-android:0.3.1.0'
```
- Add to the manifest the INTERNET permission.
```xml
  <uses-permission android:name="android.permission.INTERNET"></uses-permission>
```
- Copy the file jxcore_module.js to the assets/www/jxcore folder (create the folder). Modify that file according your needs. That module must have to functions, the "init" and the "registerJSFunction".
- Put in assets/www/jxcore folder all your jxcore (node js ) project.
- In your android activity create object  jxcore using an AsyncTask.

See everything in the example.

**References**
[capsample]: https://github.com/capriza/jxcore-android-sample
 You can also see [https://github.com/capriza/jxcore-android-sample][capsample]


