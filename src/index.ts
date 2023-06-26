import { registerPlugin } from '@capacitor/core';

import type { OtplessPluginPlugin } from './definitions';

const OtplessPlugin = registerPlugin<OtplessPluginPlugin>('OtplessPlugin', {
  web: () => import('./web').then(m => new m.OtplessPluginWeb()),
});

export * from './definitions';
export { OtplessPlugin };
