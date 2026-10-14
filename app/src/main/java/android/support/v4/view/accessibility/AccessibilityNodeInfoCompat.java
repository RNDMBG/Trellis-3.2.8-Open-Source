            package android.support.v4.view.accessibility;

            import android.graphics.Rect;
            import android.os.Build;
            import android.os.Bundle;
            import android.view.View;
            import java.util.ArrayList;
            import java.util.Collections;
            import java.util.List;
            
            
   public class AccessibilityNodeInfoCompat {
                public static final int ACTION_ACCESSIBILITY_FOCUS = 64;
                public static final String ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN = "ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN";
                public static final String ACTION_ARGUMENT_HTML_ELEMENT_STRING = "ACTION_ARGUMENT_HTML_ELEMENT_STRING";
                public static final String ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT = "ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT";
                public static final String ACTION_ARGUMENT_SELECTION_END_INT = "ACTION_ARGUMENT_SELECTION_END_INT";
                public static final String ACTION_ARGUMENT_SELECTION_START_INT = "ACTION_ARGUMENT_SELECTION_START_INT";
                public static final int ACTION_CLEAR_ACCESSIBILITY_FOCUS = 128;
                public static final int ACTION_CLEAR_FOCUS = 2;
                public static final int ACTION_CLEAR_SELECTION = 8;
                public static final int ACTION_CLICK = 16;
                public static final int ACTION_COPY = 16384;
                public static final int ACTION_CUT = 65536;
                public static final int ACTION_FOCUS = 1;
                public static final int ACTION_LONG_CLICK = 32;
                public static final int ACTION_NEXT_AT_MOVEMENT_GRANULARITY = 256;
                public static final int ACTION_NEXT_HTML_ELEMENT = 1024;
                public static final int ACTION_PASTE = 32768;
                public static final int ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY = 512;
                public static final int ACTION_PREVIOUS_HTML_ELEMENT = 2048;
                public static final int ACTION_SCROLL_BACKWARD = 8192;
                public static final int ACTION_SCROLL_FORWARD = 4096;
                public static final int ACTION_SELECT = 4;
                public static final int ACTION_SET_SELECTION = 131072;
                public static final int FOCUS_ACCESSIBILITY = 2;
                public static final int FOCUS_INPUT = 1;
                private static final AccessibilityNodeInfoImpl IMPL;
                public static final int MOVEMENT_GRANULARITY_CHARACTER = 1;
                public static final int MOVEMENT_GRANULARITY_LINE = 4;
                public static final int MOVEMENT_GRANULARITY_PAGE = 16;
                public static final int MOVEMENT_GRANULARITY_PARAGRAPH = 8;
                public static final int MOVEMENT_GRANULARITY_WORD = 2;
                private final Object mInfo;

                interface AccessibilityNodeInfoImpl {
                    void addAction(Object obj, int i);

                    void addChild(Object obj, View view);

                    void addChild(Object obj, View view, int i);

                    List<Object> findAccessibilityNodeInfosByText(Object obj, String str);

                    Object findFocus(Object obj, int i);

                    Object focusSearch(Object obj, int i);

                    int getActions(Object obj);

                    void getBoundsInParent(Object obj, Rect rect);

                    void getBoundsInScreen(Object obj, Rect rect);

                    Object getChild(Object obj, int i);

                    int getChildCount(Object obj);

                    CharSequence getClassName(Object obj);

                    CharSequence getContentDescription(Object obj);

                    int getLiveRegion(Object obj);

                    int getMovementGranularities(Object obj);

                    CharSequence getPackageName(Object obj);

                    Object getParent(Object obj);

                    CharSequence getText(Object obj);

                    String getViewIdResourceName(Object obj);

                    int getWindowId(Object obj);

                    boolean isAccessibilityFocused(Object obj);

                    boolean isCheckable(Object obj);

                    boolean isChecked(Object obj);

                    boolean isClickable(Object obj);

                    boolean isEnabled(Object obj);

                    boolean isFocusable(Object obj);

                    boolean isFocused(Object obj);

                    boolean isLongClickable(Object obj);

                    boolean isPassword(Object obj);

                    boolean isScrollable(Object obj);

                    boolean isSelected(Object obj);

                    boolean isVisibleToUser(Object obj);

                    Object obtain();

                    Object obtain(View view);

                    Object obtain(View view, int i);

                    Object obtain(Object obj);

                    boolean performAction(Object obj, int i);

                    boolean performAction(Object obj, int i, Bundle bundle);

                    void recycle(Object obj);

                    void setAccessibilityFocused(Object obj, boolean z);

                    void setBoundsInParent(Object obj, Rect rect);

                    void setBoundsInScreen(Object obj, Rect rect);

                    void setCheckable(Object obj, boolean z);

                    void setChecked(Object obj, boolean z);

                    void setClassName(Object obj, CharSequence charSequence);

                    void setClickable(Object obj, boolean z);

                    void setContentDescription(Object obj, CharSequence charSequence);

                    void setEnabled(Object obj, boolean z);

                    void setFocusable(Object obj, boolean z);

                    void setFocused(Object obj, boolean z);

                    void setLiveRegion(Object obj, int i);

                    void setLongClickable(Object obj, boolean z);

                    void setMovementGranularities(Object obj, int i);

                    void setPackageName(Object obj, CharSequence charSequence);

                    void setParent(Object obj, View view);

                    void setParent(Object obj, View view, int i);

                    void setPassword(Object obj, boolean z);

                    void setScrollable(Object obj, boolean z);

                    void setSelected(Object obj, boolean z);

                    void setSource(Object obj, View view);

                    void setSource(Object obj, View view, int i);

                    void setText(Object obj, CharSequence charSequence);

                    void setViewIdResourceName(Object obj, String str);

                    void setVisibleToUser(Object obj, boolean z);
                }

       static class AccessibilityNodeInfoStubImpl implements AccessibilityNodeInfoImpl {
           AccessibilityNodeInfoStubImpl() {
                    }

                    @Override 
           public Object obtain() {
               return null;
                    }

                    @Override 
           public Object obtain(View source) {
               return null;
                    }

                    @Override 
           public Object obtain(View root, int virtualDescendantId) {
               return null;
                    }

                    @Override 
           public Object obtain(Object info) {
               return null;
                    }

                    @Override 
           public void addAction(Object info, int action) {
                    }

                    @Override 
           public void addChild(Object info, View child) {
                    }

                    @Override 
           public void addChild(Object info, View child, int virtualDescendantId) {
                    }

                    @Override 
           public List<Object> findAccessibilityNodeInfosByText(Object info, String text) {
               return Collections.emptyList();
                    }

                    @Override 
           public int getActions(Object info) {
               return 0;
                    }

                    @Override 
           public void getBoundsInParent(Object info, Rect outBounds) {
                    }

                    @Override 
           public void getBoundsInScreen(Object info, Rect outBounds) {
                    }

                    @Override 
           public Object getChild(Object info, int index) {
               return null;
                    }

                    @Override 
           public int getChildCount(Object info) {
               return 0;
                    }

                    @Override 
           public CharSequence getClassName(Object info) {
               return null;
                    }

                    @Override 
           public CharSequence getContentDescription(Object info) {
               return null;
                    }

                    @Override 
           public CharSequence getPackageName(Object info) {
               return null;
                    }

                    @Override 
           public Object getParent(Object info) {
               return null;
                    }

                    @Override 
           public CharSequence getText(Object info) {
               return null;
                    }

                    @Override 
           public int getWindowId(Object info) {
               return 0;
                    }

                    @Override 
           public boolean isCheckable(Object info) {
               return false;
                    }

                    @Override 
           public boolean isChecked(Object info) {
               return false;
                    }

                    @Override 
           public boolean isClickable(Object info) {
               return false;
                    }

                    @Override 
           public boolean isEnabled(Object info) {
               return false;
                    }

                    @Override 
           public boolean isFocusable(Object info) {
               return false;
                    }

                    @Override 
           public boolean isFocused(Object info) {
               return false;
                    }

                    @Override 
           public boolean isVisibleToUser(Object info) {
               return false;
                    }

                    @Override 
           public boolean isAccessibilityFocused(Object info) {
               return false;
                    }

                    @Override 
           public boolean isLongClickable(Object info) {
               return false;
                    }

                    @Override 
           public boolean isPassword(Object info) {
               return false;
                    }

                    @Override 
           public boolean isScrollable(Object info) {
               return false;
                    }

                    @Override 
           public boolean isSelected(Object info) {
               return false;
                    }

                    @Override 
           public boolean performAction(Object info, int action) {
               return false;
                    }

                    @Override 
           public boolean performAction(Object info, int action, Bundle arguments) {
               return false;
                    }

                    @Override 
           public void setMovementGranularities(Object info, int granularities) {
                    }

                    @Override 
           public int getMovementGranularities(Object info) {
               return 0;
                    }

                    @Override 
           public void setBoundsInParent(Object info, Rect bounds) {
                    }

                    @Override 
           public void setBoundsInScreen(Object info, Rect bounds) {
                    }

                    @Override 
           public void setCheckable(Object info, boolean checkable) {
                    }

                    @Override 
           public void setChecked(Object info, boolean checked) {
                    }

                    @Override 
           public void setClassName(Object info, CharSequence className) {
                    }

                    @Override 
           public void setClickable(Object info, boolean clickable) {
                    }

                    @Override 
           public void setContentDescription(Object info, CharSequence contentDescription) {
                    }

                    @Override 
           public void setEnabled(Object info, boolean enabled) {
                    }

                    @Override 
           public void setFocusable(Object info, boolean focusable) {
                    }

                    @Override 
           public void setFocused(Object info, boolean focused) {
                    }

                    @Override 
           public void setVisibleToUser(Object info, boolean visibleToUser) {
                    }

                    @Override 
           public void setAccessibilityFocused(Object info, boolean focused) {
                    }

                    @Override 
           public void setLongClickable(Object info, boolean longClickable) {
                    }

                    @Override 
           public void setPackageName(Object info, CharSequence packageName) {
                    }

                    @Override 
           public void setParent(Object info, View parent) {
                    }

                    @Override 
           public void setPassword(Object info, boolean password) {
                    }

                    @Override 
           public void setScrollable(Object info, boolean scrollable) {
                    }

                    @Override 
           public void setSelected(Object info, boolean selected) {
                    }

                    @Override 
           public void setSource(Object info, View source) {
                    }

                    @Override 
           public void setSource(Object info, View root, int virtualDescendantId) {
                    }

                    @Override 
           public Object findFocus(Object info, int focus) {
               return null;
                    }

                    @Override 
           public Object focusSearch(Object info, int direction) {
               return null;
                    }

                    @Override 
           public void setText(Object info, CharSequence text) {
                    }

                    @Override 
           public void recycle(Object info) {
                    }

                    @Override 
           public void setParent(Object info, View root, int virtualDescendantId) {
                    }

                    @Override 
           public String getViewIdResourceName(Object info) {
               return null;
                    }

                    @Override 
           public void setViewIdResourceName(Object info, String viewId) {
                    }

                    @Override 
           public int getLiveRegion(Object info) {
               return 0;
                    }

                    @Override 
           public void setLiveRegion(Object info, int mode) {
                    }
                }

       static class AccessibilityNodeInfoIcsImpl extends AccessibilityNodeInfoStubImpl {
           AccessibilityNodeInfoIcsImpl() {
                    }

                    @Override 
           public Object obtain() {
               return AccessibilityNodeInfoCompatIcs.obtain();
                    }

                    @Override 
           public Object obtain(View source) {
               return AccessibilityNodeInfoCompatIcs.obtain(source);
                    }

                    @Override 
           public Object obtain(Object info) {
               return AccessibilityNodeInfoCompatIcs.obtain(info);
                    }

                    @Override 
           public void addAction(Object info, int action) {
               AccessibilityNodeInfoCompatIcs.addAction(info, action);
                    }

                    @Override 
           public void addChild(Object info, View child) {
               AccessibilityNodeInfoCompatIcs.addChild(info, child);
                    }

                    @Override 
           public List<Object> findAccessibilityNodeInfosByText(Object info, String text) {
               return AccessibilityNodeInfoCompatIcs.findAccessibilityNodeInfosByText(info, text);
                    }

                    @Override 
           public int getActions(Object info) {
               return AccessibilityNodeInfoCompatIcs.getActions(info);
                    }

                    @Override 
           public void getBoundsInParent(Object info, Rect outBounds) {
               AccessibilityNodeInfoCompatIcs.getBoundsInParent(info, outBounds);
                    }

                    @Override 
           public void getBoundsInScreen(Object info, Rect outBounds) {
               AccessibilityNodeInfoCompatIcs.getBoundsInScreen(info, outBounds);
                    }

                    @Override 
           public Object getChild(Object info, int index) {
               return AccessibilityNodeInfoCompatIcs.getChild(info, index);
                    }

                    @Override 
           public int getChildCount(Object info) {
               return AccessibilityNodeInfoCompatIcs.getChildCount(info);
                    }

                    @Override 
           public CharSequence getClassName(Object info) {
               return AccessibilityNodeInfoCompatIcs.getClassName(info);
                    }

                    @Override 
           public CharSequence getContentDescription(Object info) {
               return AccessibilityNodeInfoCompatIcs.getContentDescription(info);
                    }

                    @Override 
           public CharSequence getPackageName(Object info) {
               return AccessibilityNodeInfoCompatIcs.getPackageName(info);
                    }

                    @Override 
           public Object getParent(Object info) {
               return AccessibilityNodeInfoCompatIcs.getParent(info);
                    }

                    @Override 
           public CharSequence getText(Object info) {
               return AccessibilityNodeInfoCompatIcs.getText(info);
                    }

                    @Override 
           public int getWindowId(Object info) {
               return AccessibilityNodeInfoCompatIcs.getWindowId(info);
                    }

                    @Override 
           public boolean isCheckable(Object info) {
               return AccessibilityNodeInfoCompatIcs.isCheckable(info);
                    }

                    @Override 
           public boolean isChecked(Object info) {
               return AccessibilityNodeInfoCompatIcs.isChecked(info);
                    }

                    @Override 
           public boolean isClickable(Object info) {
               return AccessibilityNodeInfoCompatIcs.isClickable(info);
                    }

                    @Override 
           public boolean isEnabled(Object info) {
               return AccessibilityNodeInfoCompatIcs.isEnabled(info);
                    }

                    @Override 
           public boolean isFocusable(Object info) {
               return AccessibilityNodeInfoCompatIcs.isFocusable(info);
                    }

                    @Override 
           public boolean isFocused(Object info) {
               return AccessibilityNodeInfoCompatIcs.isFocused(info);
                    }

                    @Override 
           public boolean isLongClickable(Object info) {
               return AccessibilityNodeInfoCompatIcs.isLongClickable(info);
                    }

                    @Override 
           public boolean isPassword(Object info) {
               return AccessibilityNodeInfoCompatIcs.isPassword(info);
                    }

                    @Override 
           public boolean isScrollable(Object info) {
               return AccessibilityNodeInfoCompatIcs.isScrollable(info);
                    }

                    @Override 
           public boolean isSelected(Object info) {
               return AccessibilityNodeInfoCompatIcs.isSelected(info);
                    }

                    @Override 
           public boolean performAction(Object info, int action) {
               return AccessibilityNodeInfoCompatIcs.performAction(info, action);
                    }

                    @Override 
           public void setBoundsInParent(Object info, Rect bounds) {
               AccessibilityNodeInfoCompatIcs.setBoundsInParent(info, bounds);
                    }

                    @Override 
           public void setBoundsInScreen(Object info, Rect bounds) {
               AccessibilityNodeInfoCompatIcs.setBoundsInScreen(info, bounds);
                    }

                    @Override 
           public void setCheckable(Object info, boolean checkable) {
               AccessibilityNodeInfoCompatIcs.setCheckable(info, checkable);
                    }

                    @Override 
           public void setChecked(Object info, boolean checked) {
               AccessibilityNodeInfoCompatIcs.setChecked(info, checked);
                    }

                    @Override 
           public void setClassName(Object info, CharSequence className) {
               AccessibilityNodeInfoCompatIcs.setClassName(info, className);
                    }

                    @Override 
           public void setClickable(Object info, boolean clickable) {
               AccessibilityNodeInfoCompatIcs.setClickable(info, clickable);
                    }

                    @Override 
           public void setContentDescription(Object info, CharSequence contentDescription) {
               AccessibilityNodeInfoCompatIcs.setContentDescription(info, contentDescription);
                    }

                    @Override 
           public void setEnabled(Object info, boolean enabled) {
               AccessibilityNodeInfoCompatIcs.setEnabled(info, enabled);
                    }

                    @Override 
           public void setFocusable(Object info, boolean focusable) {
               AccessibilityNodeInfoCompatIcs.setFocusable(info, focusable);
                    }

                    @Override 
           public void setFocused(Object info, boolean focused) {
               AccessibilityNodeInfoCompatIcs.setFocused(info, focused);
                    }

                    @Override 
           public void setLongClickable(Object info, boolean longClickable) {
               AccessibilityNodeInfoCompatIcs.setLongClickable(info, longClickable);
                    }

                    @Override 
           public void setPackageName(Object info, CharSequence packageName) {
               AccessibilityNodeInfoCompatIcs.setPackageName(info, packageName);
                    }

                    @Override 
           public void setParent(Object info, View parent) {
               AccessibilityNodeInfoCompatIcs.setParent(info, parent);
                    }

                    @Override 
           public void setPassword(Object info, boolean password) {
               AccessibilityNodeInfoCompatIcs.setPassword(info, password);
                    }

                    @Override 
           public void setScrollable(Object info, boolean scrollable) {
               AccessibilityNodeInfoCompatIcs.setScrollable(info, scrollable);
                    }

                    @Override 
           public void setSelected(Object info, boolean selected) {
               AccessibilityNodeInfoCompatIcs.setSelected(info, selected);
                    }

                    @Override 
           public void setSource(Object info, View source) {
               AccessibilityNodeInfoCompatIcs.setSource(info, source);
                    }

                    @Override 
           public void setText(Object info, CharSequence text) {
               AccessibilityNodeInfoCompatIcs.setText(info, text);
                    }

                    @Override 
           public void recycle(Object info) {
               AccessibilityNodeInfoCompatIcs.recycle(info);
                    }
                }

       static class AccessibilityNodeInfoJellybeanImpl extends AccessibilityNodeInfoIcsImpl {
           AccessibilityNodeInfoJellybeanImpl() {
                    }

                    @Override 
           public Object obtain(View root, int virtualDescendantId) {
               return AccessibilityNodeInfoCompatJellyBean.obtain(root, virtualDescendantId);
                    }

                    @Override 
           public Object findFocus(Object info, int focus) {
               return AccessibilityNodeInfoCompatJellyBean.findFocus(info, focus);
                    }

                    @Override 
           public Object focusSearch(Object info, int direction) {
               return AccessibilityNodeInfoCompatJellyBean.focusSearch(info, direction);
                    }

                    @Override 
           public void addChild(Object info, View child, int virtualDescendantId) {
               AccessibilityNodeInfoCompatJellyBean.addChild(info, child, virtualDescendantId);
                    }

                    @Override 
           public void setSource(Object info, View root, int virtualDescendantId) {
               AccessibilityNodeInfoCompatJellyBean.setSource(info, root, virtualDescendantId);
                    }

                    @Override 
           public boolean isVisibleToUser(Object info) {
               return AccessibilityNodeInfoCompatJellyBean.isVisibleToUser(info);
                    }

                    @Override 
           public void setVisibleToUser(Object info, boolean visibleToUser) {
               AccessibilityNodeInfoCompatJellyBean.setVisibleToUser(info, visibleToUser);
                    }

                    @Override 
           public boolean isAccessibilityFocused(Object info) {
               return AccessibilityNodeInfoCompatJellyBean.isAccessibilityFocused(info);
                    }

                    @Override 
           public void setAccessibilityFocused(Object info, boolean focused) {
               AccessibilityNodeInfoCompatJellyBean.setAccesibilityFocused(info, focused);
                    }

                    @Override 
           public boolean performAction(Object info, int action, Bundle arguments) {
               return AccessibilityNodeInfoCompatJellyBean.performAction(info, action, arguments);
                    }

                    @Override 
           public void setMovementGranularities(Object info, int granularities) {
               AccessibilityNodeInfoCompatJellyBean.setMovementGranularities(info, granularities);
                    }

                    @Override 
           public int getMovementGranularities(Object info) {
               return AccessibilityNodeInfoCompatJellyBean.getMovementGranularities(info);
                    }

                    @Override 
           public void setParent(Object info, View root, int virtualDescendantId) {
               AccessibilityNodeInfoCompatJellyBean.setParent(info, root, virtualDescendantId);
                    }
                }

       static class AccessibilityNodeInfoJellybeanMr2Impl extends AccessibilityNodeInfoJellybeanImpl {
           AccessibilityNodeInfoJellybeanMr2Impl() {
                    }

                    @Override 
           public String getViewIdResourceName(Object info) {
               return AccessibilityNodeInfoCompatJellybeanMr2.getViewIdResourceName(info);
                    }

                    @Override 
           public void setViewIdResourceName(Object info, String viewId) {
               AccessibilityNodeInfoCompatJellybeanMr2.setViewIdResourceName(info, viewId);
                    }
                }

       static class AccessibilityNodeInfoKitKatImpl extends AccessibilityNodeInfoJellybeanMr2Impl {
           AccessibilityNodeInfoKitKatImpl() {
                    }

                    @Override 
           public int getLiveRegion(Object info) {
               return AccessibilityNodeInfoCompatKitKat.getLiveRegion(info);
                    }

                    @Override 
           public void setLiveRegion(Object info, int mode) {
               AccessibilityNodeInfoCompatKitKat.setLiveRegion(info, mode);
                    }
                }

                static {
           if (Build.VERSION.SDK_INT >= 19) {
               IMPL = new AccessibilityNodeInfoKitKatImpl();
               return;
                    }
           if (Build.VERSION.SDK_INT >= 18) {
               IMPL = new AccessibilityNodeInfoJellybeanMr2Impl();
               return;
                    }
           if (Build.VERSION.SDK_INT >= 16) {
               IMPL = new AccessibilityNodeInfoJellybeanImpl();
                    } else if (Build.VERSION.SDK_INT >= 14) {
               IMPL = new AccessibilityNodeInfoIcsImpl();
                    } else {
               IMPL = new AccessibilityNodeInfoStubImpl();
                    }
                }

      static AccessibilityNodeInfoCompat wrapNonNullInstance(Object object) {
          if (object != null) {
              return new AccessibilityNodeInfoCompat(object);
                    }
                    return null;
                }

      public AccessibilityNodeInfoCompat(Object info) {
          this.mInfo = info;
                }

      public Object getInfo() {
          return this.mInfo;
                }

      public static AccessibilityNodeInfoCompat obtain(View source) {
          return wrapNonNullInstance(IMPL.obtain(source));
                }

      public static AccessibilityNodeInfoCompat obtain(View root, int virtualDescendantId) {
          return wrapNonNullInstance(IMPL.obtain(root, virtualDescendantId));
                }

      public static AccessibilityNodeInfoCompat obtain() {
          return wrapNonNullInstance(IMPL.obtain());
                }

      public static AccessibilityNodeInfoCompat obtain(AccessibilityNodeInfoCompat info) {
          return wrapNonNullInstance(IMPL.obtain(info.mInfo));
                }

      public void setSource(View source) {
          IMPL.setSource(this.mInfo, source);
                }

      public void setSource(View root, int virtualDescendantId) {
          IMPL.setSource(this.mInfo, root, virtualDescendantId);
                }

      public AccessibilityNodeInfoCompat findFocus(int focus) {
          return wrapNonNullInstance(IMPL.findFocus(this.mInfo, focus));
                }

      public AccessibilityNodeInfoCompat focusSearch(int direction) {
          return wrapNonNullInstance(IMPL.focusSearch(this.mInfo, direction));
                }

      public int getWindowId() {
          return IMPL.getWindowId(this.mInfo);
                }

      public int getChildCount() {
          return IMPL.getChildCount(this.mInfo);
                }

      public AccessibilityNodeInfoCompat getChild(int index) {
          return wrapNonNullInstance(IMPL.getChild(this.mInfo, index));
                }

      public void addChild(View child) {
          IMPL.addChild(this.mInfo, child);
                }

      public void addChild(View root, int virtualDescendantId) {
          IMPL.addChild(this.mInfo, root, virtualDescendantId);
                }

      public int getActions() {
          return IMPL.getActions(this.mInfo);
                }

      public void addAction(int action) {
          IMPL.addAction(this.mInfo, action);
                }

      public boolean performAction(int action) {
          return IMPL.performAction(this.mInfo, action);
                }

      public boolean performAction(int action, Bundle arguments) {
          return IMPL.performAction(this.mInfo, action, arguments);
                }

      public void setMovementGranularities(int granularities) {
          IMPL.setMovementGranularities(this.mInfo, granularities);
                }

      public int getMovementGranularities() {
          return IMPL.getMovementGranularities(this.mInfo);
                }

      public List<AccessibilityNodeInfoCompat> findAccessibilityNodeInfosByText(String text) {
          List<AccessibilityNodeInfoCompat> result = new ArrayList<>();
          List<Object> infos = IMPL.findAccessibilityNodeInfosByText(this.mInfo, text);
          int infoCount = infos.size();
                    for (int i = 0; i < infoCount; i++) {
              Object info = infos.get(i);
              result.add(new AccessibilityNodeInfoCompat(info));
                    }
          return result;
                }

      public AccessibilityNodeInfoCompat getParent() {
          return wrapNonNullInstance(IMPL.getParent(this.mInfo));
                }

      public void setParent(View parent) {
          IMPL.setParent(this.mInfo, parent);
                }

      public void setParent(View root, int virtualDescendantId) {
          IMPL.setParent(this.mInfo, root, virtualDescendantId);
                }

      public void getBoundsInParent(Rect outBounds) {
          IMPL.getBoundsInParent(this.mInfo, outBounds);
                }

      public void setBoundsInParent(Rect bounds) {
          IMPL.setBoundsInParent(this.mInfo, bounds);
                }

      public void getBoundsInScreen(Rect outBounds) {
          IMPL.getBoundsInScreen(this.mInfo, outBounds);
                }

      public void setBoundsInScreen(Rect bounds) {
          IMPL.setBoundsInScreen(this.mInfo, bounds);
                }

      public boolean isCheckable() {
          return IMPL.isCheckable(this.mInfo);
                }

      public void setCheckable(boolean checkable) {
          IMPL.setCheckable(this.mInfo, checkable);
                }

      public boolean isChecked() {
          return IMPL.isChecked(this.mInfo);
                }

      public void setChecked(boolean checked) {
          IMPL.setChecked(this.mInfo, checked);
                }

      public boolean isFocusable() {
          return IMPL.isFocusable(this.mInfo);
                }

      public void setFocusable(boolean focusable) {
          IMPL.setFocusable(this.mInfo, focusable);
                }

      public boolean isFocused() {
          return IMPL.isFocused(this.mInfo);
                }

      public void setFocused(boolean focused) {
          IMPL.setFocused(this.mInfo, focused);
                }

      public boolean isVisibleToUser() {
          return IMPL.isVisibleToUser(this.mInfo);
                }

      public void setVisibleToUser(boolean visibleToUser) {
          IMPL.setVisibleToUser(this.mInfo, visibleToUser);
                }

      public boolean isAccessibilityFocused() {
          return IMPL.isAccessibilityFocused(this.mInfo);
                }

      public void setAccessibilityFocused(boolean focused) {
          IMPL.setAccessibilityFocused(this.mInfo, focused);
                }

      public boolean isSelected() {
          return IMPL.isSelected(this.mInfo);
                }

      public void setSelected(boolean selected) {
          IMPL.setSelected(this.mInfo, selected);
                }

      public boolean isClickable() {
          return IMPL.isClickable(this.mInfo);
                }

      public void setClickable(boolean clickable) {
          IMPL.setClickable(this.mInfo, clickable);
                }

      public boolean isLongClickable() {
          return IMPL.isLongClickable(this.mInfo);
                }

      public void setLongClickable(boolean longClickable) {
          IMPL.setLongClickable(this.mInfo, longClickable);
                }

      public boolean isEnabled() {
          return IMPL.isEnabled(this.mInfo);
                }

      public void setEnabled(boolean enabled) {
          IMPL.setEnabled(this.mInfo, enabled);
                }

      public boolean isPassword() {
          return IMPL.isPassword(this.mInfo);
                }

      public void setPassword(boolean password) {
          IMPL.setPassword(this.mInfo, password);
                }

      public boolean isScrollable() {
          return IMPL.isScrollable(this.mInfo);
                }

      public void setScrollable(boolean scrollable) {
          IMPL.setScrollable(this.mInfo, scrollable);
                }

      public CharSequence getPackageName() {
          return IMPL.getPackageName(this.mInfo);
                }

      public void setPackageName(CharSequence packageName) {
          IMPL.setPackageName(this.mInfo, packageName);
                }

      public CharSequence getClassName() {
          return IMPL.getClassName(this.mInfo);
                }

      public void setClassName(CharSequence className) {
          IMPL.setClassName(this.mInfo, className);
                }

      public CharSequence getText() {
          return IMPL.getText(this.mInfo);
                }

      public void setText(CharSequence text) {
          IMPL.setText(this.mInfo, text);
                }

      public CharSequence getContentDescription() {
          return IMPL.getContentDescription(this.mInfo);
                }

      public void setContentDescription(CharSequence contentDescription) {
          IMPL.setContentDescription(this.mInfo, contentDescription);
                }

      public void recycle() {
          IMPL.recycle(this.mInfo);
                }

      public void setViewIdResourceName(String viewId) {
          IMPL.setViewIdResourceName(this.mInfo, viewId);
                }

      public String getViewIdResourceName() {
          return IMPL.getViewIdResourceName(this.mInfo);
                }

      public int getLiveRegion() {
          return IMPL.getLiveRegion(this.mInfo);
                }

      public void setLiveRegion(int mode) {
          IMPL.setLiveRegion(this.mInfo, mode);
                }

      public int hashCode() {
          if (this.mInfo == null) {
                        return 0;
                    }
                    return this.mInfo.hashCode();
                }

      public boolean equals(Object obj) {
          if (this == obj) {
                        return true;
                    }
          if (obj != null && getClass() == obj.getClass()) {
              AccessibilityNodeInfoCompat other = (AccessibilityNodeInfoCompat) obj;
              if (this.mInfo == null) {
                            return other.mInfo == null;
                        }
                        return this.mInfo.equals(other.mInfo);
                    }
                    return false;
                }

      public String toString() {
          StringBuilder builder = new StringBuilder();
          builder.append(super.toString());
          Rect bounds = new Rect();
          getBoundsInParent(bounds);
          builder.append("; boundsInParent: " + bounds);
          getBoundsInScreen(bounds);
          builder.append("; boundsInScreen: " + bounds);
          builder.append("; packageName: ").append(getPackageName());
          builder.append("; className: ").append(getClassName());
          builder.append("; text: ").append(getText());
          builder.append("; contentDescription: ").append(getContentDescription());
          builder.append("; viewId: ").append(getViewIdResourceName());
          builder.append("; checkable: ").append(isCheckable());
          builder.append("; checked: ").append(isChecked());
          builder.append("; focusable: ").append(isFocusable());
          builder.append("; focused: ").append(isFocused());
          builder.append("; selected: ").append(isSelected());
          builder.append("; clickable: ").append(isClickable());
          builder.append("; longClickable: ").append(isLongClickable());
          builder.append("; enabled: ").append(isEnabled());
          builder.append("; password: ").append(isPassword());
          builder.append("; scrollable: " + isScrollable());
          builder.append("; [");
          int actionBits = getActions();
                    while (actionBits != 0) {
              int action = 1 << Integer.numberOfTrailingZeros(actionBits);
              actionBits &= action ^ (-1);
              builder.append(getActionSymbolicName(action));
              if (actionBits != 0) {
                  builder.append(", ");
                        }
                    }
          builder.append("]");
          return builder.toString();
                }

      private static String getActionSymbolicName(int action) {
                    switch (action) {
                        case 1:
                  return "ACTION_FOCUS";
                        case 2:
                  return "ACTION_CLEAR_FOCUS";
                        case 4:
                  return "ACTION_SELECT";
                        case 8:
                  return "ACTION_CLEAR_SELECTION";
                        case 16:
                  return "ACTION_CLICK";
                        case 32:
                  return "ACTION_LONG_CLICK";
                        case 64:
                  return "ACTION_ACCESSIBILITY_FOCUS";
                        case 128:
                  return "ACTION_CLEAR_ACCESSIBILITY_FOCUS";
                        case 256:
                  return "ACTION_NEXT_AT_MOVEMENT_GRANULARITY";
                        case 512:
                  return "ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY";
                        case 1024:
                  return "ACTION_NEXT_HTML_ELEMENT";
                        case 2048:
                  return "ACTION_PREVIOUS_HTML_ELEMENT";
                        case 4096:
                  return "ACTION_SCROLL_FORWARD";
                        case 8192:
                  return "ACTION_SCROLL_BACKWARD";
                        case 16384:
                  return "ACTION_COPY";
                        case 32768:
                  return "ACTION_PASTE";
                        case 65536:
                  return "ACTION_CUT";
                        case 131072:
                  return "ACTION_SET_SELECTION";
                        default:
                  return "ACTION_UNKNOWN";
                    }
                }
            }
