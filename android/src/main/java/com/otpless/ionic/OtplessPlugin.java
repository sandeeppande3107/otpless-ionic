package com.otpless.ionic;

import android.app.Activity;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.otpless.views.OtplessManager;

@CapacitorPlugin(name = "OtplessPlugin")
public class OtplessPlugin extends Plugin {

    private static final String TAG = OtplessPlugin.class.getSimpleName();

    @Override
    public void load() {
        AppCompatActivity activity = this.getActivity();
        if (activity != null) {
            activity.getLifecycle().addObserver((LifecycleEventObserver) (lifecycleOwner, event) -> {
                if (event == Lifecycle.Event.ON_CREATE) {
                    OtplessManager.getInstance().init(activity);
                    Log.d(TAG, "otpless plugin initialized.");
                }
            });
        }
    }

    @PluginMethod
    public void start(PluginCall call) {
        final JSObject obj = call.getObject("jsonParams");
        OtplessManager.getInstance().start(otplessUserDetail -> {
            notifyListeners("OtplessResultEvent", Converter.fromResponseToJson(otplessUserDetail));
        }, obj);
        call.resolve();
    }

    @PluginMethod
    public void startWithCallback(PluginCall call) {
        OtplessManager.getInstance().showFabButton(false);
        final JSObject obj = call.getObject("jsonParams");
        OtplessManager.getInstance().start(otplessUserDetail -> {
            call.resolve(Converter.fromResponseToJson(otplessUserDetail));
        }, obj);
    }

    @PluginMethod
    public void onSignInCompleted(PluginCall call) {
        final Activity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(() -> {
                OtplessManager.getInstance().onSignInCompleted();
            });
        }
        call.resolve();
    }

    @PluginMethod
    public void showFabButton(PluginCall call) {
        Boolean isShowFab = call.getBoolean("isShowFab", true);
        if (isShowFab == null) {
            isShowFab = true;
        }
        OtplessManager.getInstance().showFabButton(isShowFab);
        call.resolve();
    }
}
