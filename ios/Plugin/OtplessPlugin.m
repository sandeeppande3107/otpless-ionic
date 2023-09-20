#import <Foundation/Foundation.h>
#import <Capacitor/Capacitor.h>

// Define the plugin using the CAP_PLUGIN Macro, and
// each method the plugin supports using the CAP_PLUGIN_METHOD macro.
CAP_PLUGIN(OtplessPlugin, "OtplessPlugin",
           CAP_PLUGIN_METHOD(start, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(startWithCallback, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(onSignInCompleted, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(showFabButton, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(showOtplessLoginPage, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(isWhatsappInstalled, CAPPluginReturnPromise);
)
