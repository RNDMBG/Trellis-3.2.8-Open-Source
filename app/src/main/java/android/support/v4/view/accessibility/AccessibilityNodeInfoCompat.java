            package android.support.v4.view.accessibility;

            import android.graphics.Rect;
            import android.os.Build;
            import android.os.Bundle;
            import android.view.View;
            import java.util.ArrayList;
            import java.util.Collections;
            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 101 */   public class AccessibilityNodeInfoCompat {
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

/* 102 */       static class AccessibilityNodeInfoStubImpl implements AccessibilityNodeInfoImpl {
/* 103 */           AccessibilityNodeInfoStubImpl() {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 106 */           public Object obtain() {
/* 107 */               return null;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 111 */           public Object obtain(View source) {
/* 112 */               return null;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 116 */           public Object obtain(View root, int virtualDescendantId) {
/* 117 */               return null;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 121 */           public Object obtain(Object info) {
/* 122 */               return null;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 127 */           public void addAction(Object info, int action) {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 132 */           public void addChild(Object info, View child) {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 137 */           public void addChild(Object info, View child, int virtualDescendantId) {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 141 */           public List<Object> findAccessibilityNodeInfosByText(Object info, String text) {
/* 142 */               return Collections.emptyList();
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 146 */           public int getActions(Object info) {
/* 147 */               return 0;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 152 */           public void getBoundsInParent(Object info, Rect outBounds) {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 157 */           public void getBoundsInScreen(Object info, Rect outBounds) {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 161 */           public Object getChild(Object info, int index) {
/* 162 */               return null;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 166 */           public int getChildCount(Object info) {
/* 167 */               return 0;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 171 */           public CharSequence getClassName(Object info) {
/* 172 */               return null;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 176 */           public CharSequence getContentDescription(Object info) {
/* 177 */               return null;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 181 */           public CharSequence getPackageName(Object info) {
/* 182 */               return null;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 186 */           public Object getParent(Object info) {
/* 187 */               return null;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 191 */           public CharSequence getText(Object info) {
/* 192 */               return null;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 196 */           public int getWindowId(Object info) {
/* 197 */               return 0;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 201 */           public boolean isCheckable(Object info) {
/* 202 */               return false;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 206 */           public boolean isChecked(Object info) {
/* 207 */               return false;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 211 */           public boolean isClickable(Object info) {
/* 212 */               return false;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 216 */           public boolean isEnabled(Object info) {
/* 217 */               return false;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 221 */           public boolean isFocusable(Object info) {
/* 222 */               return false;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 226 */           public boolean isFocused(Object info) {
/* 227 */               return false;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 231 */           public boolean isVisibleToUser(Object info) {
/* 232 */               return false;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 236 */           public boolean isAccessibilityFocused(Object info) {
/* 237 */               return false;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 241 */           public boolean isLongClickable(Object info) {
/* 242 */               return false;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 246 */           public boolean isPassword(Object info) {
/* 247 */               return false;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 251 */           public boolean isScrollable(Object info) {
/* 252 */               return false;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 256 */           public boolean isSelected(Object info) {
/* 257 */               return false;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 261 */           public boolean performAction(Object info, int action) {
/* 262 */               return false;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 266 */           public boolean performAction(Object info, int action, Bundle arguments) {
/* 267 */               return false;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 272 */           public void setMovementGranularities(Object info, int granularities) {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 276 */           public int getMovementGranularities(Object info) {
/* 277 */               return 0;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 282 */           public void setBoundsInParent(Object info, Rect bounds) {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 287 */           public void setBoundsInScreen(Object info, Rect bounds) {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 292 */           public void setCheckable(Object info, boolean checkable) {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 297 */           public void setChecked(Object info, boolean checked) {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 302 */           public void setClassName(Object info, CharSequence className) {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 307 */           public void setClickable(Object info, boolean clickable) {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 312 */           public void setContentDescription(Object info, CharSequence contentDescription) {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 317 */           public void setEnabled(Object info, boolean enabled) {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 322 */           public void setFocusable(Object info, boolean focusable) {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 327 */           public void setFocused(Object info, boolean focused) {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 332 */           public void setVisibleToUser(Object info, boolean visibleToUser) {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 337 */           public void setAccessibilityFocused(Object info, boolean focused) {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 342 */           public void setLongClickable(Object info, boolean longClickable) {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 347 */           public void setPackageName(Object info, CharSequence packageName) {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 352 */           public void setParent(Object info, View parent) {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 357 */           public void setPassword(Object info, boolean password) {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 362 */           public void setScrollable(Object info, boolean scrollable) {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 367 */           public void setSelected(Object info, boolean selected) {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 372 */           public void setSource(Object info, View source) {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 377 */           public void setSource(Object info, View root, int virtualDescendantId) {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 381 */           public Object findFocus(Object info, int focus) {
/* 382 */               return null;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 386 */           public Object focusSearch(Object info, int direction) {
/* 387 */               return null;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 392 */           public void setText(Object info, CharSequence text) {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 397 */           public void recycle(Object info) {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 402 */           public void setParent(Object info, View root, int virtualDescendantId) {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 406 */           public String getViewIdResourceName(Object info) {
/* 407 */               return null;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 412 */           public void setViewIdResourceName(Object info, String viewId) {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 416 */           public int getLiveRegion(Object info) {
/* 417 */               return 0;
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 422 */           public void setLiveRegion(Object info, int mode) {
                    }
                }

/* 424 */       static class AccessibilityNodeInfoIcsImpl extends AccessibilityNodeInfoStubImpl {
/* 425 */           AccessibilityNodeInfoIcsImpl() {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 428 */           public Object obtain() {
/* 429 */               return AccessibilityNodeInfoCompatIcs.obtain();
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 433 */           public Object obtain(View source) {
/* 434 */               return AccessibilityNodeInfoCompatIcs.obtain(source);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 438 */           public Object obtain(Object info) {
/* 439 */               return AccessibilityNodeInfoCompatIcs.obtain(info);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 443 */           public void addAction(Object info, int action) {
/* 444 */               AccessibilityNodeInfoCompatIcs.addAction(info, action);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 448 */           public void addChild(Object info, View child) {
/* 449 */               AccessibilityNodeInfoCompatIcs.addChild(info, child);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 453 */           public List<Object> findAccessibilityNodeInfosByText(Object info, String text) {
/* 454 */               return AccessibilityNodeInfoCompatIcs.findAccessibilityNodeInfosByText(info, text);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 458 */           public int getActions(Object info) {
/* 459 */               return AccessibilityNodeInfoCompatIcs.getActions(info);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 463 */           public void getBoundsInParent(Object info, Rect outBounds) {
/* 464 */               AccessibilityNodeInfoCompatIcs.getBoundsInParent(info, outBounds);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 468 */           public void getBoundsInScreen(Object info, Rect outBounds) {
/* 469 */               AccessibilityNodeInfoCompatIcs.getBoundsInScreen(info, outBounds);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 473 */           public Object getChild(Object info, int index) {
/* 474 */               return AccessibilityNodeInfoCompatIcs.getChild(info, index);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 478 */           public int getChildCount(Object info) {
/* 479 */               return AccessibilityNodeInfoCompatIcs.getChildCount(info);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 483 */           public CharSequence getClassName(Object info) {
/* 484 */               return AccessibilityNodeInfoCompatIcs.getClassName(info);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 488 */           public CharSequence getContentDescription(Object info) {
/* 489 */               return AccessibilityNodeInfoCompatIcs.getContentDescription(info);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 493 */           public CharSequence getPackageName(Object info) {
/* 494 */               return AccessibilityNodeInfoCompatIcs.getPackageName(info);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 498 */           public Object getParent(Object info) {
/* 499 */               return AccessibilityNodeInfoCompatIcs.getParent(info);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 503 */           public CharSequence getText(Object info) {
/* 504 */               return AccessibilityNodeInfoCompatIcs.getText(info);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 508 */           public int getWindowId(Object info) {
/* 509 */               return AccessibilityNodeInfoCompatIcs.getWindowId(info);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 513 */           public boolean isCheckable(Object info) {
/* 514 */               return AccessibilityNodeInfoCompatIcs.isCheckable(info);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 518 */           public boolean isChecked(Object info) {
/* 519 */               return AccessibilityNodeInfoCompatIcs.isChecked(info);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 523 */           public boolean isClickable(Object info) {
/* 524 */               return AccessibilityNodeInfoCompatIcs.isClickable(info);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 528 */           public boolean isEnabled(Object info) {
/* 529 */               return AccessibilityNodeInfoCompatIcs.isEnabled(info);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 533 */           public boolean isFocusable(Object info) {
/* 534 */               return AccessibilityNodeInfoCompatIcs.isFocusable(info);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 538 */           public boolean isFocused(Object info) {
/* 539 */               return AccessibilityNodeInfoCompatIcs.isFocused(info);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 543 */           public boolean isLongClickable(Object info) {
/* 544 */               return AccessibilityNodeInfoCompatIcs.isLongClickable(info);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 548 */           public boolean isPassword(Object info) {
/* 549 */               return AccessibilityNodeInfoCompatIcs.isPassword(info);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 553 */           public boolean isScrollable(Object info) {
/* 554 */               return AccessibilityNodeInfoCompatIcs.isScrollable(info);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 558 */           public boolean isSelected(Object info) {
/* 559 */               return AccessibilityNodeInfoCompatIcs.isSelected(info);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 563 */           public boolean performAction(Object info, int action) {
/* 564 */               return AccessibilityNodeInfoCompatIcs.performAction(info, action);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 568 */           public void setBoundsInParent(Object info, Rect bounds) {
/* 569 */               AccessibilityNodeInfoCompatIcs.setBoundsInParent(info, bounds);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 573 */           public void setBoundsInScreen(Object info, Rect bounds) {
/* 574 */               AccessibilityNodeInfoCompatIcs.setBoundsInScreen(info, bounds);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 578 */           public void setCheckable(Object info, boolean checkable) {
/* 579 */               AccessibilityNodeInfoCompatIcs.setCheckable(info, checkable);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 583 */           public void setChecked(Object info, boolean checked) {
/* 584 */               AccessibilityNodeInfoCompatIcs.setChecked(info, checked);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 588 */           public void setClassName(Object info, CharSequence className) {
/* 589 */               AccessibilityNodeInfoCompatIcs.setClassName(info, className);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 593 */           public void setClickable(Object info, boolean clickable) {
/* 594 */               AccessibilityNodeInfoCompatIcs.setClickable(info, clickable);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 598 */           public void setContentDescription(Object info, CharSequence contentDescription) {
/* 599 */               AccessibilityNodeInfoCompatIcs.setContentDescription(info, contentDescription);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 603 */           public void setEnabled(Object info, boolean enabled) {
/* 604 */               AccessibilityNodeInfoCompatIcs.setEnabled(info, enabled);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 608 */           public void setFocusable(Object info, boolean focusable) {
/* 609 */               AccessibilityNodeInfoCompatIcs.setFocusable(info, focusable);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 613 */           public void setFocused(Object info, boolean focused) {
/* 614 */               AccessibilityNodeInfoCompatIcs.setFocused(info, focused);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 618 */           public void setLongClickable(Object info, boolean longClickable) {
/* 619 */               AccessibilityNodeInfoCompatIcs.setLongClickable(info, longClickable);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 623 */           public void setPackageName(Object info, CharSequence packageName) {
/* 624 */               AccessibilityNodeInfoCompatIcs.setPackageName(info, packageName);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 628 */           public void setParent(Object info, View parent) {
/* 629 */               AccessibilityNodeInfoCompatIcs.setParent(info, parent);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 633 */           public void setPassword(Object info, boolean password) {
/* 634 */               AccessibilityNodeInfoCompatIcs.setPassword(info, password);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 638 */           public void setScrollable(Object info, boolean scrollable) {
/* 639 */               AccessibilityNodeInfoCompatIcs.setScrollable(info, scrollable);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 643 */           public void setSelected(Object info, boolean selected) {
/* 644 */               AccessibilityNodeInfoCompatIcs.setSelected(info, selected);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 648 */           public void setSource(Object info, View source) {
/* 649 */               AccessibilityNodeInfoCompatIcs.setSource(info, source);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 653 */           public void setText(Object info, CharSequence text) {
/* 654 */               AccessibilityNodeInfoCompatIcs.setText(info, text);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 658 */           public void recycle(Object info) {
/* 659 */               AccessibilityNodeInfoCompatIcs.recycle(info);
                    }
                }

/* 661 */       static class AccessibilityNodeInfoJellybeanImpl extends AccessibilityNodeInfoIcsImpl {
/* 662 */           AccessibilityNodeInfoJellybeanImpl() {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 665 */           public Object obtain(View root, int virtualDescendantId) {
/* 666 */               return AccessibilityNodeInfoCompatJellyBean.obtain(root, virtualDescendantId);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 670 */           public Object findFocus(Object info, int focus) {
/* 671 */               return AccessibilityNodeInfoCompatJellyBean.findFocus(info, focus);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 675 */           public Object focusSearch(Object info, int direction) {
/* 676 */               return AccessibilityNodeInfoCompatJellyBean.focusSearch(info, direction);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 680 */           public void addChild(Object info, View child, int virtualDescendantId) {
/* 681 */               AccessibilityNodeInfoCompatJellyBean.addChild(info, child, virtualDescendantId);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 685 */           public void setSource(Object info, View root, int virtualDescendantId) {
/* 686 */               AccessibilityNodeInfoCompatJellyBean.setSource(info, root, virtualDescendantId);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 690 */           public boolean isVisibleToUser(Object info) {
/* 691 */               return AccessibilityNodeInfoCompatJellyBean.isVisibleToUser(info);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 695 */           public void setVisibleToUser(Object info, boolean visibleToUser) {
/* 696 */               AccessibilityNodeInfoCompatJellyBean.setVisibleToUser(info, visibleToUser);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 700 */           public boolean isAccessibilityFocused(Object info) {
/* 701 */               return AccessibilityNodeInfoCompatJellyBean.isAccessibilityFocused(info);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 705 */           public void setAccessibilityFocused(Object info, boolean focused) {
/* 706 */               AccessibilityNodeInfoCompatJellyBean.setAccesibilityFocused(info, focused);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 710 */           public boolean performAction(Object info, int action, Bundle arguments) {
/* 711 */               return AccessibilityNodeInfoCompatJellyBean.performAction(info, action, arguments);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 715 */           public void setMovementGranularities(Object info, int granularities) {
/* 716 */               AccessibilityNodeInfoCompatJellyBean.setMovementGranularities(info, granularities);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 720 */           public int getMovementGranularities(Object info) {
/* 721 */               return AccessibilityNodeInfoCompatJellyBean.getMovementGranularities(info);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 725 */           public void setParent(Object info, View root, int virtualDescendantId) {
/* 726 */               AccessibilityNodeInfoCompatJellyBean.setParent(info, root, virtualDescendantId);
                    }
                }

/* 728 */       static class AccessibilityNodeInfoJellybeanMr2Impl extends AccessibilityNodeInfoJellybeanImpl {
/* 729 */           AccessibilityNodeInfoJellybeanMr2Impl() {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 733 */           public String getViewIdResourceName(Object info) {
/* 734 */               return AccessibilityNodeInfoCompatJellybeanMr2.getViewIdResourceName(info);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 738 */           public void setViewIdResourceName(Object info, String viewId) {
/* 739 */               AccessibilityNodeInfoCompatJellybeanMr2.setViewIdResourceName(info, viewId);
                    }
                }

/* 741 */       static class AccessibilityNodeInfoKitKatImpl extends AccessibilityNodeInfoJellybeanMr2Impl {
/* 742 */           AccessibilityNodeInfoKitKatImpl() {
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 745 */           public int getLiveRegion(Object info) {
/* 746 */               return AccessibilityNodeInfoCompatKitKat.getLiveRegion(info);
                    }

                    @Override // android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoStubImpl, android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityNodeInfoImpl
/* 750 */           public void setLiveRegion(Object info, int mode) {
/* 751 */               AccessibilityNodeInfoCompatKitKat.setLiveRegion(info, mode);
                    }
                }

                static {
/* 756 */           if (Build.VERSION.SDK_INT >= 19) {
/* 757 */               IMPL = new AccessibilityNodeInfoKitKatImpl();
/* 767 */               return;
                    }
/* 758 */           if (Build.VERSION.SDK_INT >= 18) {
/* 759 */               IMPL = new AccessibilityNodeInfoJellybeanMr2Impl();
/* 767 */               return;
                    }
/* 760 */           if (Build.VERSION.SDK_INT >= 16) {
/* 761 */               IMPL = new AccessibilityNodeInfoJellybeanImpl();
                    } else if (Build.VERSION.SDK_INT >= 14) {
/* 763 */               IMPL = new AccessibilityNodeInfoIcsImpl();
                    } else {
/* 765 */               IMPL = new AccessibilityNodeInfoStubImpl();
                    }
                }

/* 1067 */      static AccessibilityNodeInfoCompat wrapNonNullInstance(Object object) {
/* 1068 */          if (object != null) {
/* 1069 */              return new AccessibilityNodeInfoCompat(object);
                    }
                    return null;
                }

/* 1079 */      public AccessibilityNodeInfoCompat(Object info) {
/* 1081 */          this.mInfo = info;
                }

/* 1087 */      public Object getInfo() {
/* 1088 */          return this.mInfo;
                }

/* 1098 */      public static AccessibilityNodeInfoCompat obtain(View source) {
/* 1099 */          return wrapNonNullInstance(IMPL.obtain(source));
                }

/* 1112 */      public static AccessibilityNodeInfoCompat obtain(View root, int virtualDescendantId) {
/* 1113 */          return wrapNonNullInstance(IMPL.obtain(root, virtualDescendantId));
                }

/* 1122 */      public static AccessibilityNodeInfoCompat obtain() {
/* 1123 */          return wrapNonNullInstance(IMPL.obtain());
                }

/* 1133 */      public static AccessibilityNodeInfoCompat obtain(AccessibilityNodeInfoCompat info) {
/* 1134 */          return wrapNonNullInstance(IMPL.obtain(info.mInfo));
                }

/* 1142 */      public void setSource(View source) {
/* 1143 */          IMPL.setSource(this.mInfo, source);
                }

/* 1165 */      public void setSource(View root, int virtualDescendantId) {
/* 1166 */          IMPL.setSource(this.mInfo, root, virtualDescendantId);
                }

/* 1180 */      public AccessibilityNodeInfoCompat findFocus(int focus) {
/* 1181 */          return wrapNonNullInstance(IMPL.findFocus(this.mInfo, focus));
                }

/* 1198 */      public AccessibilityNodeInfoCompat focusSearch(int direction) {
/* 1199 */          return wrapNonNullInstance(IMPL.focusSearch(this.mInfo, direction));
                }

/* 1207 */      public int getWindowId() {
/* 1208 */          return IMPL.getWindowId(this.mInfo);
                }

/* 1216 */      public int getChildCount() {
/* 1217 */          return IMPL.getChildCount(this.mInfo);
                }

/* 1233 */      public AccessibilityNodeInfoCompat getChild(int index) {
/* 1234 */          return wrapNonNullInstance(IMPL.getChild(this.mInfo, index));
                }

/* 1248 */      public void addChild(View child) {
/* 1249 */          IMPL.addChild(this.mInfo, child);
                }

/* 1266 */      public void addChild(View root, int virtualDescendantId) {
/* 1267 */          IMPL.addChild(this.mInfo, root, virtualDescendantId);
                }

/* 1279 */      public int getActions() {
/* 1280 */          return IMPL.getActions(this.mInfo);
                }

/* 1294 */      public void addAction(int action) {
/* 1295 */          IMPL.addAction(this.mInfo, action);
                }

/* 1310 */      public boolean performAction(int action) {
/* 1311 */          return IMPL.performAction(this.mInfo, action);
                }

/* 1327 */      public boolean performAction(int action, Bundle arguments) {
/* 1328 */          return IMPL.performAction(this.mInfo, action, arguments);
                }

/* 1343 */      public void setMovementGranularities(int granularities) {
/* 1344 */          IMPL.setMovementGranularities(this.mInfo, granularities);
                }

/* 1352 */      public int getMovementGranularities() {
/* 1353 */          return IMPL.getMovementGranularities(this.mInfo);
                }

/* 1369 */      public List<AccessibilityNodeInfoCompat> findAccessibilityNodeInfosByText(String text) {
/* 1370 */          List<AccessibilityNodeInfoCompat> result = new ArrayList<>();
/* 1371 */          List<Object> infos = IMPL.findAccessibilityNodeInfosByText(this.mInfo, text);
/* 1372 */          int infoCount = infos.size();
                    for (int i = 0; i < infoCount; i++) {
/* 1374 */              Object info = infos.get(i);
/* 1375 */              result.add(new AccessibilityNodeInfoCompat(info));
                    }
/* 1377 */          return result;
                }

/* 1390 */      public AccessibilityNodeInfoCompat getParent() {
/* 1391 */          return wrapNonNullInstance(IMPL.getParent(this.mInfo));
                }

/* 1405 */      public void setParent(View parent) {
/* 1406 */          IMPL.setParent(this.mInfo, parent);
                }

/* 1428 */      public void setParent(View root, int virtualDescendantId) {
/* 1429 */          IMPL.setParent(this.mInfo, root, virtualDescendantId);
                }

/* 1437 */      public void getBoundsInParent(Rect outBounds) {
/* 1438 */          IMPL.getBoundsInParent(this.mInfo, outBounds);
                }

/* 1452 */      public void setBoundsInParent(Rect bounds) {
/* 1453 */          IMPL.setBoundsInParent(this.mInfo, bounds);
                }

/* 1461 */      public void getBoundsInScreen(Rect outBounds) {
/* 1462 */          IMPL.getBoundsInScreen(this.mInfo, outBounds);
                }

/* 1476 */      public void setBoundsInScreen(Rect bounds) {
/* 1477 */          IMPL.setBoundsInScreen(this.mInfo, bounds);
                }

/* 1485 */      public boolean isCheckable() {
/* 1486 */          return IMPL.isCheckable(this.mInfo);
                }

/* 1500 */      public void setCheckable(boolean checkable) {
/* 1501 */          IMPL.setCheckable(this.mInfo, checkable);
                }

/* 1509 */      public boolean isChecked() {
/* 1510 */          return IMPL.isChecked(this.mInfo);
                }

/* 1524 */      public void setChecked(boolean checked) {
/* 1525 */          IMPL.setChecked(this.mInfo, checked);
                }

/* 1533 */      public boolean isFocusable() {
/* 1534 */          return IMPL.isFocusable(this.mInfo);
                }

/* 1548 */      public void setFocusable(boolean focusable) {
/* 1549 */          IMPL.setFocusable(this.mInfo, focusable);
                }

/* 1557 */      public boolean isFocused() {
/* 1558 */          return IMPL.isFocused(this.mInfo);
                }

/* 1572 */      public void setFocused(boolean focused) {
/* 1573 */          IMPL.setFocused(this.mInfo, focused);
                }

/* 1581 */      public boolean isVisibleToUser() {
/* 1582 */          return IMPL.isVisibleToUser(this.mInfo);
                }

/* 1597 */      public void setVisibleToUser(boolean visibleToUser) {
/* 1598 */          IMPL.setVisibleToUser(this.mInfo, visibleToUser);
                }

/* 1606 */      public boolean isAccessibilityFocused() {
/* 1607 */          return IMPL.isAccessibilityFocused(this.mInfo);
                }

/* 1622 */      public void setAccessibilityFocused(boolean focused) {
/* 1623 */          IMPL.setAccessibilityFocused(this.mInfo, focused);
                }

/* 1631 */      public boolean isSelected() {
/* 1632 */          return IMPL.isSelected(this.mInfo);
                }

/* 1646 */      public void setSelected(boolean selected) {
/* 1647 */          IMPL.setSelected(this.mInfo, selected);
                }

/* 1655 */      public boolean isClickable() {
/* 1656 */          return IMPL.isClickable(this.mInfo);
                }

/* 1670 */      public void setClickable(boolean clickable) {
/* 1671 */          IMPL.setClickable(this.mInfo, clickable);
                }

/* 1679 */      public boolean isLongClickable() {
/* 1680 */          return IMPL.isLongClickable(this.mInfo);
                }

/* 1694 */      public void setLongClickable(boolean longClickable) {
/* 1695 */          IMPL.setLongClickable(this.mInfo, longClickable);
                }

/* 1703 */      public boolean isEnabled() {
/* 1704 */          return IMPL.isEnabled(this.mInfo);
                }

/* 1718 */      public void setEnabled(boolean enabled) {
/* 1719 */          IMPL.setEnabled(this.mInfo, enabled);
                }

/* 1727 */      public boolean isPassword() {
/* 1728 */          return IMPL.isPassword(this.mInfo);
                }

/* 1742 */      public void setPassword(boolean password) {
/* 1743 */          IMPL.setPassword(this.mInfo, password);
                }

/* 1751 */      public boolean isScrollable() {
/* 1752 */          return IMPL.isScrollable(this.mInfo);
                }

/* 1766 */      public void setScrollable(boolean scrollable) {
/* 1767 */          IMPL.setScrollable(this.mInfo, scrollable);
                }

/* 1775 */      public CharSequence getPackageName() {
/* 1776 */          return IMPL.getPackageName(this.mInfo);
                }

/* 1790 */      public void setPackageName(CharSequence packageName) {
/* 1791 */          IMPL.setPackageName(this.mInfo, packageName);
                }

/* 1799 */      public CharSequence getClassName() {
/* 1800 */          return IMPL.getClassName(this.mInfo);
                }

/* 1814 */      public void setClassName(CharSequence className) {
/* 1815 */          IMPL.setClassName(this.mInfo, className);
                }

/* 1823 */      public CharSequence getText() {
/* 1824 */          return IMPL.getText(this.mInfo);
                }

/* 1838 */      public void setText(CharSequence text) {
/* 1839 */          IMPL.setText(this.mInfo, text);
                }

/* 1847 */      public CharSequence getContentDescription() {
/* 1848 */          return IMPL.getContentDescription(this.mInfo);
                }

/* 1862 */      public void setContentDescription(CharSequence contentDescription) {
/* 1863 */          IMPL.setContentDescription(this.mInfo, contentDescription);
                }

/* 1873 */      public void recycle() {
/* 1874 */          IMPL.recycle(this.mInfo);
                }

/* 1888 */      public void setViewIdResourceName(String viewId) {
/* 1889 */          IMPL.setViewIdResourceName(this.mInfo, viewId);
                }

/* 1904 */      public String getViewIdResourceName() {
/* 1905 */          return IMPL.getViewIdResourceName(this.mInfo);
                }

/* 1926 */      public int getLiveRegion() {
/* 1927 */          return IMPL.getLiveRegion(this.mInfo);
                }

/* 1942 */      public void setLiveRegion(int mode) {
/* 1943 */          IMPL.setLiveRegion(this.mInfo, mode);
                }

/* 1947 */      public int hashCode() {
/* 1948 */          if (this.mInfo == null) {
                        return 0;
                    }
                    return this.mInfo.hashCode();
                }

/* 1952 */      public boolean equals(Object obj) {
/* 1953 */          if (this == obj) {
                        return true;
                    }
/* 1956 */          if (obj != null && getClass() == obj.getClass()) {
/* 1962 */              AccessibilityNodeInfoCompat other = (AccessibilityNodeInfoCompat) obj;
/* 1963 */              if (this.mInfo == null) {
                            return other.mInfo == null;
                        }
                        return this.mInfo.equals(other.mInfo);
                    }
                    return false;
                }

/* 1974 */      public String toString() {
/* 1975 */          StringBuilder builder = new StringBuilder();
/* 1976 */          builder.append(super.toString());
/* 1978 */          Rect bounds = new Rect();
/* 1980 */          getBoundsInParent(bounds);
/* 1981 */          builder.append("; boundsInParent: " + bounds);
/* 1983 */          getBoundsInScreen(bounds);
/* 1984 */          builder.append("; boundsInScreen: " + bounds);
/* 1986 */          builder.append("; packageName: ").append(getPackageName());
/* 1987 */          builder.append("; className: ").append(getClassName());
/* 1988 */          builder.append("; text: ").append(getText());
/* 1989 */          builder.append("; contentDescription: ").append(getContentDescription());
/* 1990 */          builder.append("; viewId: ").append(getViewIdResourceName());
/* 1992 */          builder.append("; checkable: ").append(isCheckable());
/* 1993 */          builder.append("; checked: ").append(isChecked());
/* 1994 */          builder.append("; focusable: ").append(isFocusable());
/* 1995 */          builder.append("; focused: ").append(isFocused());
/* 1996 */          builder.append("; selected: ").append(isSelected());
/* 1997 */          builder.append("; clickable: ").append(isClickable());
/* 1998 */          builder.append("; longClickable: ").append(isLongClickable());
/* 1999 */          builder.append("; enabled: ").append(isEnabled());
/* 2000 */          builder.append("; password: ").append(isPassword());
/* 2001 */          builder.append("; scrollable: " + isScrollable());
/* 2003 */          builder.append("; [");
/* 2004 */          int actionBits = getActions();
                    while (actionBits != 0) {
/* 2005 */              int action = 1 << Integer.numberOfTrailingZeros(actionBits);
/* 2006 */              actionBits &= action ^ (-1);
/* 2007 */              builder.append(getActionSymbolicName(action));
/* 2008 */              if (actionBits != 0) {
/* 2009 */                  builder.append(", ");
                        }
                    }
/* 2012 */          builder.append("]");
/* 2014 */          return builder.toString();
                }

/* 2017 */      private static String getActionSymbolicName(int action) {
                    switch (action) {
                        case 1:
/* 2020 */                  return "ACTION_FOCUS";
                        case 2:
/* 2022 */                  return "ACTION_CLEAR_FOCUS";
                        case 4:
/* 2024 */                  return "ACTION_SELECT";
                        case 8:
/* 2026 */                  return "ACTION_CLEAR_SELECTION";
                        case 16:
/* 2028 */                  return "ACTION_CLICK";
                        case 32:
/* 2030 */                  return "ACTION_LONG_CLICK";
                        case 64:
/* 2032 */                  return "ACTION_ACCESSIBILITY_FOCUS";
                        case 128:
/* 2034 */                  return "ACTION_CLEAR_ACCESSIBILITY_FOCUS";
                        case 256:
/* 2036 */                  return "ACTION_NEXT_AT_MOVEMENT_GRANULARITY";
                        case 512:
/* 2038 */                  return "ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY";
                        case 1024:
/* 2040 */                  return "ACTION_NEXT_HTML_ELEMENT";
                        case 2048:
/* 2042 */                  return "ACTION_PREVIOUS_HTML_ELEMENT";
                        case 4096:
/* 2044 */                  return "ACTION_SCROLL_FORWARD";
                        case 8192:
/* 2046 */                  return "ACTION_SCROLL_BACKWARD";
                        case 16384:
/* 2050 */                  return "ACTION_COPY";
                        case 32768:
/* 2052 */                  return "ACTION_PASTE";
                        case 65536:
/* 2048 */                  return "ACTION_CUT";
                        case 131072:
/* 2054 */                  return "ACTION_SET_SELECTION";
                        default:
/* 2056 */                  return "ACTION_UNKNOWN";
                    }
                }
            }
