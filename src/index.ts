import { registerPlugin } from '@capacitor/core';

import type { OtplessPlugin } from './definitions';

const OtplessInstance = registerPlugin<OtplessPlugin>('OtplessPlugin',);

class OtplessManager {

  async start(jsonParams: any | null = null) {
    await OtplessInstance.start({ "jsonParams": jsonParams });
  }

  async startWithCallback(jsonParams: any | null = null): Promise<any> {
    return OtplessInstance.startWithCallback({ "jsonParams": jsonParams });
  }
  async onSignInCompleted() {
    await OtplessInstance.onSignInCompleted();
  }

  async showFabButton(isShowFab: boolean): Promise<void> {
    await OtplessInstance.showFabButton({ isShowFab: isShowFab });
  }
}

export * from './definitions';
export { OtplessManager, OtplessInstance };
