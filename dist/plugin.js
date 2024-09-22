var capacitorOtplessPlugin = (function (exports, core) {
    'use strict';

    const OtplessInstance = core.registerPlugin('OtplessPlugin');
    class OtplessManager {
        // to open the otpless login page
        async showOtplessLoginPage(jsonParams) {
            return await OtplessInstance.showOtplessLoginPage({ "jsonParams": jsonParams });
        }
        // to check if whatsapp is installed or not
        async isWhatsappInstalled() {
            const { hasWhatsapp } = await OtplessInstance.isWhatsappInstalled();
            return hasWhatsapp;
        }
        // set visibility of native loader
        async setLoaderVisibility(visibility) {
            await OtplessInstance.setLoaderVisibility({ visibility: visibility });
        }
        // to enable and disble the webview inspection
        async setWebViewInspectable(isInspectable) {
            await OtplessInstance.setWebViewInspectable({ isInspectable: isInspectable });
        }
        // to enable and disable onetap option
        async enableOneTap(isOnetap) {
            await OtplessInstance.enableOneTap({ isOnetap: isOnetap });
        }
        // to initialize headless
        async initHeadless(appId) {
            await OtplessInstance.initHeadless({ appId: appId });
        }
        // to set headless callback
        async setHeadlessCallback(resultCallback) {
            OtplessInstance.removeAllListeners();
            OtplessInstance.addListener('OtplessResultEvent', resultCallback);
            await OtplessInstance.setHeadlessCallback();
        }
        // to start headless sdk
        async startHeadless(request) {
            await OtplessInstance.startHeadless({ request: request });
        }
        clearListener() {
            OtplessInstance.removeAllListeners();
        }
    }

    exports.OtplessInstance = OtplessInstance;
    exports.OtplessManager = OtplessManager;

    Object.defineProperty(exports, '__esModule', { value: true });

    return exports;

})({}, capacitorExports);
//# sourceMappingURL=plugin.js.map
