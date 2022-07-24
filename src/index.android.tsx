import React from 'react';
import type { ViewProps } from 'react-native';
// @ts-ignore
import TextAncestor from 'react-native/Libraries/Text/TextAncestor';
import ViewNativeComponent from './ViewNativeComponent';

/**
 * The most fundamental component for building a UI, View is a container that
 * supports layout with flexbox, style, some touch handling, and accessibility
 * controls.
 *
 * @see https://reactnative.dev/docs/view.html
 */
const View = React.forwardRef((props: ViewProps, forwardedRef) => {
  return (
    <TextAncestor.Provider value={false}>
      <ViewNativeComponent {...props} ref={forwardedRef} />
    </TextAncestor.Provider>
  );
});

View.displayName = 'View';

export default View;
