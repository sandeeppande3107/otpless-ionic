import Foundation
import Capacitor

import OtplessSDK

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(OtplessPlugin)
public class OtplessPlugin: CAPPlugin, onResponseDelegate {
    
    private var pluginCallWrap: CapPluginCallWrapper? = nil
    
    @objc func start(_ call: CAPPluginCall) {
        let jsObject = call.getObject("jsonParams")
        DispatchQueue.main.async {
            let viewController = UIApplication.shared.delegate?.window??.rootViewController
            Otpless.sharedInstance.delegate = self
            if let param = jsObject {
                Otpless.sharedInstance.startwithParams(vc: viewController!, params: param)
            } else {
                Otpless.sharedInstance.start(vc: viewController!)
            }
        }
        call.resolve()
    }
    
    @objc func startWithCallback(_ call: CAPPluginCall) {
        let jsObject = call.getObject("jsonParams")
        Otpless.sharedInstance.shouldHideButton(hide: true)
        DispatchQueue.main.async {
            let viewController = UIApplication.shared.delegate?.window??.rootViewController
            self.pluginCallWrap = CapPluginCallWrapper(pluginCall: call)
            Otpless.sharedInstance.delegate = self.pluginCallWrap!
            if let param = jsObject {
                Otpless.sharedInstance.startwithParams(vc: viewController!, params: param)
            } else {
                Otpless.sharedInstance.start(vc: viewController!)
            }
        }
    }
    
    @objc func onSignInCompleted(_ call: CAPPluginCall) {
        DispatchQueue.main.async {
            Otpless.sharedInstance.onSignedInComplete()
        }
    }
    
    @objc func showFabButton(_ call: CAPPluginCall) {
        let isToShow = call.getBool("isShowFab") ?? true
        Otpless.sharedInstance.shouldHideButton(hide: !isToShow)
    }
    
    public func onResponse(response: OtplessResponse?) {
        var result: JSObject = JSObject()
        result["errorMessage"] = response?.errorString
        if let data = response?.responseData {
            result["data"] = anyDictToJsObjDict(input: data)
        }
        notifyListeners("OtplessResultEvent", data: result)
    }
}

class CapPluginCallWrapper: onResponseDelegate {
    
    private let pluginCall: CAPPluginCall
    private var isCallUsed = false
    
    init(pluginCall: CAPPluginCall) {
        self.pluginCall = pluginCall
    }
    
    func onResponse(response: OtplessResponse?) {
        if isCallUsed {
            return
        }
        var result: JSObject = JSObject()
        result["errorMessage"] = response?.errorString
        if let data = response?.responseData {
            result["data"] = anyDictToJsObjDict(input: data)
        }
        pluginCall.resolve(result)
        isCallUsed = true
    }
    
}


private func anyDictToJsObjDict(input: [String: Any]) -> [String: JSValue] {
    var result: JSObject = JSObject()
    for (key, value) in input {
        if (value as? String) != nil {
            result[key] = value as! String
        } else if (value as? Bool) != nil {
            result[key] = value as! Bool
        } else if (value as? Int) != nil {
            result[key] = value as! Int
        } else if (value as? Float) != nil {
            result[key] = value as! Float
        } else if (value as? Double) != nil {
            result[key] = value as! Double
        } else if (value as? [Any]) != nil {
            result[key] = anyArrayToJsObjArray(input: value as! [Any])
        } else if (value as? [String: Any]) != nil {
            result[key] = anyDictToJsObjDict(input: value as! [String: Any])
        }
    }
    return result
}

private func anyArrayToJsObjArray(input: [Any]) -> [JSValue] {
    var result: [JSValue] = []
    for value in input {
        if (value as? String) != nil {
            result.append(value as! String)
        } else if (value as? Bool) != nil {
            result.append(value as! Bool)
        } else if (value as? Int) != nil {
            result.append(value as! Int)
        } else if (value as? Float) != nil {
            result.append(value as! Float)
        } else if (value as? Double) != nil {
            result.append(value as! Double)
        } else if (value as? [Any]) != nil {
            result.append(anyArrayToJsObjArray(input: value as! [Any]))
        } else if (value as? [String: Any]) != nil {
            result.append(anyDictToJsObjDict(input: value as! [String: Any]))
        }
    }
    return result
}
