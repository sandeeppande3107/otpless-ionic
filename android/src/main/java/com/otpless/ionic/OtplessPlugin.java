package com.otpless.ionic;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.getcapacitor.Bridge;
import com.getcapacitor.BridgeActivity;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginHandle;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.otpless.main.OtplessManager;
import com.otpless.main.OtplessView;
import com.otpless.utils.Utility;

@CapacitorPlugin(name = "OtplessPlugin")
public class OtplessPlugin extends Plugin {

    private static final String TAG = OtplessPlugin.class.getSimpleName();
    private OtplessView otplessView;

    /**
     * @param activity ionic BridgeActivity dependency
     * @return boolean if back press is handled by view or not
     */
    public static boolean onBackPressed(@NonNull final BridgeActivity activity) {
        final Bridge bridge = activity.getBridge();
        if (bridge == null) return false;
        if (bridge.getPlugin("OtplessPlugin") != null) {
            final PluginHandle handle = bridge.getPlugin("OtplessPlugin");
            return ((OtplessPlugin) handle.getInstance()).onBackPressed();
        }
        return false;
    }

    @Override
    public void load() {
        checkOrInitOtpless(getActivity());
        Log.d(TAG, "otpless view loaded.");
    }

    private boolean checkOrInitOtpless(final Activity activity) {
        if (activity == null) return false;
        if (otplessView == null) {
            otplessView = OtplessManager.getInstance().getOtplessView(activity);
        }
        return true;
    }

    @Override
    protected void handleOnNewIntent(Intent intent) {
        final String dat;
        if (intent != null && intent.getData() != null) {
            dat = intent.getData().toString();
        } else {
            dat = "no uri data";
        }
        Log.d(TAG, "got call in handleOnNewIntent data: " + dat);
        if (otplessView != null && otplessView.verifyIntent(intent)) return;
        super.handleOnNewIntent(intent);
    }

    /**
     * Starts the otpless login with floater. The result is sent to ionic js side through,
     * OtplessResultEvent event.
     *
     * @param call having info of additional jsonParams.
     */
    @PluginMethod
    public void start(PluginCall call) {
        if (!checkOrInitOtpless(getActivity())) return;
        final JSObject obj = call.getObject("jsonParams");
        onMainThread(() ->
                otplessView.startOtpless(obj, otplessUserDetail -> notifyListeners(
                        "OtplessResultEvent", Converter.fromResponseToJson(otplessUserDetail))
                )
        );
        call.resolve();
    }

    /**
     * Starts the otpless login with floater.
     *
     * @param call having info of additional jsonParams and promise callback which is used to send back the result.
     */
    @PluginMethod
    public void startWithCallback(PluginCall call) {
        if (!checkOrInitOtpless(getActivity())) return;
        otplessView.showOtplessFab(false);
        final JSObject obj = call.getObject("jsonParams");
        onMainThread(() ->
                otplessView.startOtpless(obj,
                        otplessUserDetail -> call.resolve(Converter.fromResponseToJson(otplessUserDetail))
                )
        );
    }

    /**
     * Should be called after whole sign-in process is completed. It must be called when
     * using sign-in button to remove it after login.
     *
     * @param call just an ionic object as per signature. Do not need any additional info here.
     */
    @PluginMethod
    public void onSignInCompleted(PluginCall call) {
        if (otplessView == null) return;
        onMainThread(() -> otplessView.onSignInCompleted());
        call.resolve();
    }

    /**
     * Set the configuration whether to show the sign-in button or not in floater case
     *
     * @param call ionic call object have promise callback and configuration info
     */
    @PluginMethod
    public void showFabButton(PluginCall call) {
        if (checkOrInitOtpless(getActivity())) {
            Boolean isShowFab = call.getBoolean("isShowFab", true);
            if (isShowFab == null) {
                isShowFab = true;
            }
            otplessView.showOtplessFab(isShowFab);
        }
        call.resolve();
    }

    /**
     * checks if whatsapp is installed or not and send the result in promise object
     * as hasWhatsapp
     *
     * @param call holder for promised object and other info
     */
    @PluginMethod
    public void isWhatsappInstalled(PluginCall call) {
        Boolean isInstalled = false;
        if (getActivity() != null) {
            isInstalled = Utility.isWhatsAppInstalled(getActivity());
        }
        final JSObject jsObject = new JSObject();
        jsObject.put("hasWhatsapp", isInstalled);
        call.resolve(jsObject);
    }

    /**
     * Shows otpless login page
     *
     * @param call having additional jsonParams info and promise object
     */
    @PluginMethod
    public void showOtplessLoginPage(PluginCall call) {
        if (!checkOrInitOtpless(getActivity())) return;
        final JSObject obj = call.getObject("jsonParams");
        onMainThread(() ->
                otplessView.showOtplessLoginPage(obj,
                        otplessUserDetail -> call.resolve(Converter.fromResponseToJson(otplessUserDetail))
                )
        );
    }

    boolean onBackPressed() {
        if (otplessView == null) return false;
        return otplessView.onBackPressed();
    }

    private void onMainThread(final Runnable callback) {
        if (getActivity() == null) return;
        getActivity().runOnUiThread(callback);
    }
}
