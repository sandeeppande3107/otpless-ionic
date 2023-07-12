# otpless-ionic

Otpless ionic plugin support for android and ios apps

## Install

```bash
npm install otpless-ionic
npx cap sync
```
<br><br>

## API
<br>

### IONIC
* Import the OtplessManager and OtplessInstance

```
import { OtplessManager, OtplessInstance } from 'otpless-ionic';
```

<br>

* In your login component create the instance of OtplessManager, remove the existing listeners and add new listener callback.
Removing and adding listeners required as whole function component is called on data set change

```
let manager = new OtplessManager()
OtplessInstance.removeAllListeners();

OtplessInstance.addListener('OtplessResultEvent', (data: any) => {
    let message: string = '';
    if (data.data === null || data.data === undefined) {
      message = data.errorMessage;
    } else {
      let token = data.data.token;
      // to for token verification as per otpless backend verification doc
    }
});
```

* Call start method on button click or while loading component to launch OTPless sdk

```
manager.start();
```

* If some extra information need to pass for customization, create param attribute and pass to start method

```
let params = {
      'method': 'get',
      'params': {
        'optional': 'option'
      }
    };
manager.start(params);
```

* After authentication is completed callback is sent to listener, and sigin button is added at bottom left. User can use this button to launch again OTPless for signin.
After token verification is completed call onSignInComplete method to removed button and remove listeners

```
await manager.onSignInCompleted();
OtplessInstance.removeAllListeners();

```

* If signin button is not required call below line before calling start method

```
await manager.showFabButton(false);
```
<br>

* If button on bottom left is not required, then we can get the response through promise, it calls `showFabButton(false)` internally.

```
let params = {
  'method': 'get',
  'params': {
    'option': 'option'
  }
};

// passing parameter is optional
let data = await manager.startWithCallback(params);
let message: string = '';
if (data.data === null || data.data === undefined) {
  message = data.errorMessage;
} else {
  let token = data.data.token;
  // to for token verification as per otpless backend verification doc
}
```
<br>

### IOS
* Go to your application root folder and install pod. Run the below command
```
pod install
```

*  Add below lines in info.plist file
```
<key>CFBundleURLTypes</key>
 <array>
  <dict>
   <key>CFBundleURLSchemes</key> 
   <array>
    <string>$(PRODUCT_BUNDLE_IDENTIFIER).otpless</string> 
   </array>
   <key>CFBundleTypeRole</key>
   <string>Editor</string>
   <key>CFBundleURLName</key>
   <string>otpless</string> 
 </dict>
</array> 
<key>LSApplicationQueriesSchemes</key> 
<array>
 <string>whatsapp</string>
 <string>otpless</string> 
 <string>gootpless</string>
 <string>com.otpless.ios.app.otpless</string> 
 <string>googlegmail</string>
</array>
```

* Add application method in your AppDelegate

```
import OtplessSDK
```
```
@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {

    func application(_ app: UIApplication, open url: URL, options: [UIApplication.OpenURLOptionsKey: Any] = [:]) -> Bool {
        if (Otpless.sharedInstance.isOtplessDeeplink(url: url)) {
            Otpless.sharedInstance.processOtplessDeeplink(url: url)
            return true
        }
        return ApplicationDelegateProxy.shared.application(app, open: url, options: options)
    }


}
```
<br>

### Android
* In MainActivity register the plugin, before calling `super.onCreate`.
```
import com.otpless.ionic.OtplessPlugin;
```
```
protected void onCreate(Bundle savedInstanceState) {
  registerPlugin(OtplessPlugin.class);
  super.onCreate(savedInstanceState);
}
```



