import { Plugin } from "@capacitor/core";
export interface OtplessPlugin extends Plugin {
    showOtplessLoginPage(option: {
        jsonParams: any;
    }): Promise<any>;
    isWhatsappInstalled(): Promise<{
        hasWhatsapp: string;
    }>;
    setLoaderVisibility(option: {
        visibility: boolean;
    }): Promise<void>;
    setWebViewInspectable(option: {
        isInspectable: boolean;
    }): Promise<void>;
    enableOneTap(option: {
        isOnetap: boolean;
    }): Promise<void>;
    initHeadless(option: {
        appId: String;
    }): Promise<void>;
    setHeadlessCallback(): Promise<void>;
    startHeadless(option: {
        request: any;
    }): Promise<void>;
}
