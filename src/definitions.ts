import { Plugin } from "@capacitor/core"

export interface OtplessPlugin extends Plugin {
  start(option: { jsonParams: any }): Promise<any>;
  startWithCallback(option: { jsonParams: any }): Promise<any>;
  onSignInCompleted(): Promise<void>;
  showFabButton(option: { isShowFab: boolean }): Promise<void>;
}