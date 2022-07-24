const ReactNativeViewViewConfigAndroid = {
  uiViewClassName: 'MPSDKViewNative',
  bubblingEventTypes: {
    topSelect: {
      phasedRegistrationNames: {
        bubbled: 'onSelect',
        captured: 'onSelectCapture',
      },
    },
    topAssetDidLoad: {
      phasedRegistrationNames: {
        bubbled: 'onAssetDidLoad',
        captured: 'onAssetDidLoadCapture',
      },
    },
  },
  directEventTypes: {
    topClick: {
      registrationName: 'onClick',
    },
    topContentSizeChange: {
      registrationName: 'onContentSizeChange',
    },
    topLoadingError: {
      registrationName: 'onLoadingError',
    },
    topLoadingFinish: {
      registrationName: 'onLoadingFinish',
    },
    topLoadingStart: {
      registrationName: 'onLoadingStart',
    },
    topMessage: {
      registrationName: 'onMessage',
    },
    topMomentumScrollBegin: {
      registrationName: 'onMomentumScrollBegin',
    },
    topMomentumScrollEnd: {
      registrationName: 'onMomentumScrollEnd',
    },
    topScroll: {
      registrationName: 'onScroll',
    },
    topScrollBeginDrag: {
      registrationName: 'onScrollBeginDrag',
    },
    topScrollEndDrag: {
      registrationName: 'onScrollEndDrag',
    },
    topSelectionChange: {
      registrationName: 'onSelectionChange',
    },
    onAssetDidLoad: {
      registrationName: 'onAssetDidLoad',
    },
  },
  validAttributes: {
    hasTVPreferredFocus: true,
    focusable: true,
    nativeBackgroundAndroid: true,
    nativeForegroundAndroid: true,
    nextFocusDown: true,
    nextFocusForward: true,
    nextFocusLeft: true,
    nextFocusRight: true,
    nextFocusUp: true,
  },
};

export default ReactNativeViewViewConfigAndroid;
