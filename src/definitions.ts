import { Plugin } from "@capacitor/core"

export interface OtplessPlugin extends Plugin {
  start(option: { jsonParams: any }): Promise<any>;
  startWithCallback(option: { jsonParams: any }): Promise<any>;
  onSignInCompleted(): Promise<void>;
  showFabButton(option: { isShowFab: boolean }): Promise<void>;
  // to open the optless login page
  showOtplessLoginPage(option: { jsonParams: any }): Promise<any>;
  // check if whatsapp is installed or not
  isWhatsappInstalled(): Promise<{ hasWhatsapp: string }>;
}