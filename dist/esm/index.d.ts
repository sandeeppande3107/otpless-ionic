import type { OtplessPlugin } from './definitions';
declare const OtplessInstance: OtplessPlugin;
interface OtplessResultCallback {
    (result: any): void;
}
declare class OtplessManager {
    showOtplessLoginPage(jsonParams: any): Promise<any>;
    isWhatsappInstalled(): Promise<string>;
    setLoaderVisibility(visibility: boolean): Promise<void>;
    setWebViewInspectable(isInspectable: boolean): Promise<void>;
    enableOneTap(isOnetap: boolean): Promise<void>;
    initHeadless(appId: String): Promise<void>;
    setHeadlessCallback(resultCallback: OtplessResultCallback): Promise<void>;
    startHeadless(request: any): Promise<void>;
    clearListener(): void;
}
export * from './definitions';
export { OtplessManager, OtplessInstance };
