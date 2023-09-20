import { registerPlugin } from '@capacitor/core';

import type { OtplessPlugin } from './definitions';

const OtplessInstance = registerPlugin<OtplessPlugin>('OtplessPlugin',);

class OtplessManager {

  async start(jsonParams: any | null = null) {
    await OtplessInstance.start({ "jsonParams": jsonParams });
  }

  async startWithCallback(jsonParams: any | null = null) {
    return  await OtplessInstance.startWithCallback({ "jsonParams": jsonParams });
  }
  async onSignInCompleted() {
    await OtplessInstance.onSignInCompleted();
  }

  async showFabButton(isShowFab: boolean) {
    await OtplessInstance.showFabButton({ isShowFab: isShowFab });
  }

  // to open the otpless login page
  async showOtplessLoginPage(jsonParams: any | null = null) {
    return await OtplessInstance.showOtplessLoginPage({ "jsonParams": jsonParams });
  }

  // to check if whatsapp is installed or not
  async isWhatsappInstalled() {
    const {hasWhatsapp} = await OtplessInstance.isWhatsappInstalled();
    return hasWhatsapp;
  }
}

export * from './definitions';
export { OtplessManager, OtplessInstance };
