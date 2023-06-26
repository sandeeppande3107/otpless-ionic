export interface OtplessPluginPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
