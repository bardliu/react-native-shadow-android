import type { ViewProps } from 'react-native';
// @ts-ignore
import * as NativeComponentRegistry from 'react-native/Libraries/NativeComponent/NativeComponentRegistry';
// @ts-ignore
import { type HostComponent } from 'react-native/Libraries/Renderer/shims/ReactNativeTypes';
// @ts-ignore
import codegenNativeCommands from 'react-native/Libraries/Utilities/codegenNativeCommands';
import ReactNativeViewViewConfigAndroid from './ReactNativeViewViewConfigAndroid';

const ViewNativeComponent: HostComponent<ViewProps> =
  NativeComponentRegistry.get<ViewProps>(
    'ShadowAndroidView',
    () => ReactNativeViewViewConfigAndroid
  );

interface NativeCommands {
  hotspotUpdate: (
    viewRef: React.ElementRef<HostComponent<any>>,
    x: number,
    y: number
  ) => void;
  setPressed: (
    viewRef: React.ElementRef<HostComponent<any>>,
    pressed: boolean
  ) => void;
}

export const Commands: NativeCommands = codegenNativeCommands<NativeCommands>({
  supportedCommands: ['hotspotUpdate', 'setPressed'],
});

export default ViewNativeComponent;

export type ViewNativeComponentType = HostComponent<ViewProps>;
