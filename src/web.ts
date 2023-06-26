import { WebPlugin } from '@capacitor/core';

import type { OtplessPluginPlugin } from './definitions';

export class OtplessPluginWeb extends WebPlugin implements OtplessPluginPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
