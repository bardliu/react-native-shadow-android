/**
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 *
 * @format
 * @flow strict-local
 */

import type { ViewProps } from 'react-native';

// @ts-ignore
import ReactNativeFeatureFlags from 'react-native/Libraries/ReactNative/ReactNativeFeatureFlags';
import { StyleSheet } from 'react-native';
// @ts-ignore
import TextAncestor from 'react-native/Libraries/Text/TextAncestor';
import ShadowViewNativeComponent from './ShadowViewNativeComponent';
import * as React from 'react';

export type Props = ViewProps;

/**
 * The most fundamental component for building a UI, View is a container that
 * supports layout with flexbox, style, some touch handling, and accessibility
 * controls.
 *
 * @see https://reactnative.dev/docs/view
 */
const ShadowView = React.forwardRef(
  (
    {
      accessibilityElementsHidden,
      accessibilityLabel,
      accessibilityLabelledBy,
      accessibilityLiveRegion,
      accessibilityState,
      accessibilityValue,
      'aria-busy': ariaBusy,
      'aria-checked': ariaChecked,
      'aria-disabled': ariaDisabled,
      'aria-expanded': ariaExpanded,
      'aria-hidden': ariaHidden,
      'aria-label': ariaLabel,
      'aria-labelledby': ariaLabelledBy,
      'aria-live': ariaLive,
      'aria-selected': ariaSelected,
      'aria-valuemax': ariaValueMax,
      'aria-valuemin': ariaValueMin,
      'aria-valuenow': ariaValueNow,
      'aria-valuetext': ariaValueText,
      focusable,
      id,
      importantForAccessibility,
      nativeID,
      pointerEvents,
      // @ts-ignore: web only
      tabIndex,
      ...otherProps
    }: ViewProps,
    forwardedRef: React.LegacyRef<any>
  ) => {
    const hasTextAncestor = React.useContext(TextAncestor);
    const _accessibilityLabelledBy =
      ariaLabelledBy?.split(/\s*,\s*/g) ?? accessibilityLabelledBy;

    let _accessibilityState;
    if (
      accessibilityState != null ||
      ariaBusy != null ||
      ariaChecked != null ||
      ariaDisabled != null ||
      ariaExpanded != null ||
      ariaSelected != null
    ) {
      _accessibilityState = {
        busy: ariaBusy ?? accessibilityState?.busy,
        checked: ariaChecked ?? accessibilityState?.checked,
        disabled: ariaDisabled ?? accessibilityState?.disabled,
        expanded: ariaExpanded ?? accessibilityState?.expanded,
        selected: ariaSelected ?? accessibilityState?.selected,
      };
    }
    let _accessibilityValue;
    if (
      accessibilityValue != null ||
      ariaValueMax != null ||
      ariaValueMin != null ||
      ariaValueNow != null ||
      ariaValueText != null
    ) {
      _accessibilityValue = {
        max: ariaValueMax ?? accessibilityValue?.max,
        min: ariaValueMin ?? accessibilityValue?.min,
        now: ariaValueNow ?? accessibilityValue?.now,
        text: ariaValueText ?? accessibilityValue?.text,
      };
    }

    // $FlowFixMe[underconstrained-implicit-instantiation]
    let style = StyleSheet.flatten(otherProps.style);

    // $FlowFixMe[sketchy-null-mixed]
    const newPointerEvents = style?.pointerEvents || pointerEvents;
    const collapsableOverride =
      ReactNativeFeatureFlags.shouldForceUnflattenForElevation()
        ? {
            collapsable:
              style != null && style.elevation != null && style.elevation !== 0
                ? false
                : otherProps.collapsable,
          }
        : {};

    const actualView = (
      <ShadowViewNativeComponent
        {...otherProps}
        {...collapsableOverride}
        accessibilityLiveRegion={
          ariaLive === 'off' ? 'none' : ariaLive ?? accessibilityLiveRegion
        }
        accessibilityLabel={ariaLabel ?? accessibilityLabel}
        focusable={tabIndex !== undefined ? !tabIndex : focusable}
        accessibilityState={_accessibilityState}
        accessibilityElementsHidden={ariaHidden ?? accessibilityElementsHidden}
        accessibilityLabelledBy={_accessibilityLabelledBy}
        accessibilityValue={_accessibilityValue}
        importantForAccessibility={
          ariaHidden === true
            ? 'no-hide-descendants'
            : importantForAccessibility
        }
        nativeID={id ?? nativeID}
        style={style}
        // $FlowFixMe[incompatible-type]
        pointerEvents={newPointerEvents}
        ref={forwardedRef}
      />
    );

    if (hasTextAncestor) {
      return (
        <TextAncestor.Provider value={false}>
          {actualView}
        </TextAncestor.Provider>
      );
    }

    return actualView;
  }
);

ShadowView.displayName = 'ShadowView';

export default ShadowView;
