package bardliu.reactnativeshadowandroid;

import android.annotation.TargetApi;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.PointerEvents;
import com.facebook.react.uimanager.Spacing;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerHelper;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.annotations.ReactPropGroup;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.react.views.view.ReactDrawableHelper;
import com.facebook.react.views.view.ViewGroupClickEvent;
import com.facebook.yoga.YogaConstants;
import java.util.Locale;
import java.util.Map;

public class ShadowAndroidViewManager extends ReactClippingViewManager<ReactViewGroup> {
  public static final String REACT_CLASS = "ShadowAndroidView";

  private static final int[] SPACING_TYPES = {
    Spacing.ALL,
    Spacing.LEFT,
    Spacing.RIGHT,
    Spacing.TOP,
    Spacing.BOTTOM,
    Spacing.START,
    Spacing.END,
  };
  private static final int CMD_HOTSPOT_UPDATE = 1;
  private static final int CMD_SET_PRESSED = 2;
  private static final String HOTSPOT_UPDATE_KEY = "hotspotUpdate";

  @Override
  public String getName() {
    return REACT_CLASS;
  }

  @ReactProp(name = "accessible")
  public void setAccessible(ReactViewGroup view, boolean accessible) {
    view.setFocusable(accessible);
  }

  @ReactProp(name = "hasTVPreferredFocus")
  public void setTVPreferredFocus(ReactViewGroup view, boolean hasTVPreferredFocus) {
    if (hasTVPreferredFocus) {
      view.setFocusable(true);
      view.setFocusableInTouchMode(true);
      view.requestFocus();
    }
  }

  @ReactProp(name = "nextFocusDown", defaultInt = View.NO_ID)
  public void nextFocusDown(ReactViewGroup view, int viewId) {
    view.setNextFocusDownId(viewId);
  }

  @ReactProp(name = "nextFocusForward", defaultInt = View.NO_ID)
  public void nextFocusForward(ReactViewGroup view, int viewId) {
    view.setNextFocusForwardId(viewId);
  }

  @ReactProp(name = "nextFocusLeft", defaultInt = View.NO_ID)
  public void nextFocusLeft(ReactViewGroup view, int viewId) {
    view.setNextFocusLeftId(viewId);
  }

  @ReactProp(name = "nextFocusRight", defaultInt = View.NO_ID)
  public void nextFocusRight(ReactViewGroup view, int viewId) {
    view.setNextFocusRightId(viewId);
  }

  @ReactProp(name = "nextFocusUp", defaultInt = View.NO_ID)
  public void nextFocusUp(ReactViewGroup view, int viewId) {
    view.setNextFocusUpId(viewId);
  }

  @ReactPropGroup(
    names = {
      ViewProps.BORDER_RADIUS,
      ViewProps.BORDER_TOP_LEFT_RADIUS,
      ViewProps.BORDER_TOP_RIGHT_RADIUS,
      ViewProps.BORDER_BOTTOM_RIGHT_RADIUS,
      ViewProps.BORDER_BOTTOM_LEFT_RADIUS,
      ViewProps.BORDER_TOP_START_RADIUS,
      ViewProps.BORDER_TOP_END_RADIUS,
      ViewProps.BORDER_BOTTOM_START_RADIUS,
      ViewProps.BORDER_BOTTOM_END_RADIUS,
    },
    defaultFloat = YogaConstants.UNDEFINED)
  public void setBorderRadius(ReactViewGroup view, int index, float borderRadius) {
    if (!YogaConstants.isUndefined(borderRadius) && borderRadius < 0) {
      borderRadius = YogaConstants.UNDEFINED;
    }

    if (!YogaConstants.isUndefined(borderRadius)) {
      borderRadius = PixelUtil.toPixelFromDIP(borderRadius);
    }

    if (index == 0) {
      view.setBorderRadius(borderRadius);
    } else {
      view.setBorderRadius(borderRadius, index - 1);
    }
  }

  @ReactProp(name = "borderStyle")
  public void setBorderStyle(ReactViewGroup view, @Nullable String borderStyle) {
    view.setBorderStyle(borderStyle);
  }

  @ReactProp(name = "hitSlop")
  public void setHitSlop(final ReactViewGroup view, @Nullable ReadableMap hitSlop) {
    if (hitSlop == null) {
      view.setHitSlopRect(null);
    } else {
      view.setHitSlopRect(
        new Rect(
          hitSlop.hasKey("left")
            ? (int) PixelUtil.toPixelFromDIP(hitSlop.getDouble("left"))
            : 0,
          hitSlop.hasKey("top") ? (int) PixelUtil.toPixelFromDIP(hitSlop.getDouble("top")) : 0,
          hitSlop.hasKey("right")
            ? (int) PixelUtil.toPixelFromDIP(hitSlop.getDouble("right"))
            : 0,
          hitSlop.hasKey("bottom")
            ? (int) PixelUtil.toPixelFromDIP(hitSlop.getDouble("bottom"))
            : 0));
    }
  }

  @ReactProp(name = ViewProps.POINTER_EVENTS)
  public void setPointerEvents(ReactViewGroup view, @Nullable String pointerEventsStr) {
    if (pointerEventsStr == null) {
      view.setPointerEvents(PointerEvents.AUTO);
    } else {
      PointerEvents pointerEvents =
        PointerEvents.valueOf(pointerEventsStr.toUpperCase(Locale.US).replace("-", "_"));
      view.setPointerEvents(pointerEvents);
    }
  }

  @ReactProp(name = "nativeBackgroundAndroid")
  public void setNativeBackground(ReactViewGroup view, @Nullable ReadableMap bg) {
    view.setTranslucentBackgroundDrawable(
      bg == null
        ? null
        : ReactDrawableHelper.createDrawableFromJSDescription(view.getContext(), bg));
  }

  @TargetApi(Build.VERSION_CODES.M)
  @ReactProp(name = "nativeForegroundAndroid")
  public void setNativeForeground(ReactViewGroup view, @Nullable ReadableMap fg) {
    view.setForeground(
      fg == null
        ? null
        : ReactDrawableHelper.createDrawableFromJSDescription(view.getContext(), fg));
  }

  @ReactProp(name = ViewProps.NEEDS_OFFSCREEN_ALPHA_COMPOSITING)
  public void setNeedsOffscreenAlphaCompositing(
    ReactViewGroup view, boolean needsOffscreenAlphaCompositing) {
    view.setNeedsOffscreenAlphaCompositing(needsOffscreenAlphaCompositing);
  }

  @ReactPropGroup(
    names = {
      ViewProps.BORDER_WIDTH,
      ViewProps.BORDER_LEFT_WIDTH,
      ViewProps.BORDER_RIGHT_WIDTH,
      ViewProps.BORDER_TOP_WIDTH,
      ViewProps.BORDER_BOTTOM_WIDTH,
      ViewProps.BORDER_START_WIDTH,
      ViewProps.BORDER_END_WIDTH,
    },
    defaultFloat = YogaConstants.UNDEFINED)
  public void setBorderWidth(ReactViewGroup view, int index, float width) {
    if (!YogaConstants.isUndefined(width) && width < 0) {
      width = YogaConstants.UNDEFINED;
    }

    if (!YogaConstants.isUndefined(width)) {
      width = PixelUtil.toPixelFromDIP(width);
    }

    view.setBorderWidth(SPACING_TYPES[index], width);
  }

  @ReactPropGroup(
    names = {
      ViewProps.BORDER_COLOR,
      ViewProps.BORDER_LEFT_COLOR,
      ViewProps.BORDER_RIGHT_COLOR,
      ViewProps.BORDER_TOP_COLOR,
      ViewProps.BORDER_BOTTOM_COLOR,
      ViewProps.BORDER_START_COLOR,
      ViewProps.BORDER_END_COLOR
    },
    customType = "Color")
  public void setBorderColor(ReactViewGroup view, int index, Integer color) {
    float rgbComponent =
      color == null ? YogaConstants.UNDEFINED : (float) ((int) color & 0x00FFFFFF);
    float alphaComponent = color == null ? YogaConstants.UNDEFINED : (float) ((int) color >>> 24);
    view.setBorderColor(SPACING_TYPES[index], rgbComponent, alphaComponent);
  }

  @ReactProp(name = ViewProps.COLLAPSABLE)
  public void setCollapsable(ReactViewGroup view, boolean collapsable) {
    // no-op: it's here only so that "collapsable" property is exported to JS. The value is actually
    // handled in NativeViewHierarchyOptimizer
  }

  @ReactProp(name = "focusable")
  public void setFocusable(final ReactViewGroup view, boolean focusable) {
    if (focusable) {
      view.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            final EventDispatcher mEventDispatcher =
              UIManagerHelper.getEventDispatcherForReactTag(
                (ReactContext) view.getContext(), view.getId());
            if (mEventDispatcher == null) {
              return;
            }
            mEventDispatcher.dispatchEvent(
              new ViewGroupClickEvent(
                UIManagerHelper.getSurfaceId(view.getContext()), view.getId()));
          }
        });

      // Clickable elements are focusable. On API 26, this is taken care by setClickable.
      // Explicitly calling setFocusable here for backward compatibility.
      view.setFocusable(true /*isFocusable*/);
    } else {
      view.setOnClickListener(null);
      view.setClickable(false);
      // Don't set view.setFocusable(false) because we might still want it to be focusable for
      // accessibility reasons
    }
  }

  @ReactProp(name = ViewProps.OVERFLOW)
  public void setOverflow(ReactViewGroup view, String overflow) {
    view.setOverflow(overflow);
  }

  @ReactProp(name = "backfaceVisibility")
  public void setBackfaceVisibility(ReactViewGroup view, String backfaceVisibility) {
    view.setBackfaceVisibility(backfaceVisibility);
  }

  @ReactProp(name = "shadowRadius")
  public void setShadowRadius(@NonNull ReactViewGroup view, float shadowRadius) {
    view.setShadowRadius(shadowRadius);
    this.setElevation(view, 0f);
  }
  @ReactProp(name = "shadowOffset")
  public void setShadowOffset(@NonNull ReactViewGroup view, ReadableMap shadowOffset) {
    view.setShadowOffset(shadowOffset);
    this.setElevation(view, 0f);
  }
  @ReactProp(name = "shadowOpacity")
  public void setShadowOpacity(@NonNull ReactViewGroup view, float shadowOpacity) {
    view.setShadowOpacity(shadowOpacity);
    this.setElevation(view, 0f);
  }

  @Override
  public void setShadowColor(@NonNull ReactViewGroup view, int shadowColor) {
    super.setShadowColor(view, shadowColor);
    float rgbComponent = (float) ((int) shadowColor & 0x00FFFFFF);
    float alphaComponent = (float) ((int) shadowColor >>> 24);
    view.setShadowColor(rgbComponent, alphaComponent);
  }

  @Override
  public void setOpacity(@NonNull ReactViewGroup view, float opacity) {
    view.setOpacityIfPossible(opacity);
  }

  @Override
  public void setTransform(@NonNull ReactViewGroup view, @Nullable ReadableArray matrix) {
    super.setTransform(view, matrix);
    view.setBackfaceVisibilityDependantOpacity();
  }

  @Override
  public ReactViewGroup createViewInstance(ThemedReactContext context) {
    return new ReactViewGroup(context);
  }

  @Override
  public Map<String, Integer> getCommandsMap() {
    return MapBuilder.of(HOTSPOT_UPDATE_KEY, CMD_HOTSPOT_UPDATE, "setPressed", CMD_SET_PRESSED);
  }

  @Override
  public void receiveCommand(ReactViewGroup root, int commandId, @Nullable ReadableArray args) {
    switch (commandId) {
      case CMD_HOTSPOT_UPDATE:
      {
        handleHotspotUpdate(root, args);
        break;
      }
      case CMD_SET_PRESSED:
      {
        handleSetPressed(root, args);
        break;
      }
    }
  }

  @Override
  public void receiveCommand(ReactViewGroup root, String commandId, @Nullable ReadableArray args) {
    switch (commandId) {
      case HOTSPOT_UPDATE_KEY:
      {
        handleHotspotUpdate(root, args);
        break;
      }
      case "setPressed":
      {
        handleSetPressed(root, args);
        break;
      }
    }
  }

  private void handleSetPressed(ReactViewGroup root, @Nullable ReadableArray args) {
    if (args == null || args.size() != 1) {
      throw new JSApplicationIllegalArgumentException(
        "Illegal number of arguments for 'setPressed' command");
    }
    root.setPressed(args.getBoolean(0));
  }

  private void handleHotspotUpdate(ReactViewGroup root, @Nullable ReadableArray args) {
    if (args == null || args.size() != 2) {
      throw new JSApplicationIllegalArgumentException(
        "Illegal number of arguments for 'updateHotspot' command");
    }

    float x = PixelUtil.toPixelFromDIP(args.getDouble(0));
    float y = PixelUtil.toPixelFromDIP(args.getDouble(1));
    root.drawableHotspotChanged(x, y);
  }
}
