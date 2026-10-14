            package android.support.v4.view.accessibility;

            import android.graphics.Rect;
            import android.view.View;
            import android.view.accessibility.AccessibilityNodeInfo;
            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 26 */    class AccessibilityNodeInfoCompatIcs {
/* 27 */        AccessibilityNodeInfoCompatIcs() {
                }

/* 30 */        public static Object obtain() {
/* 31 */            return AccessibilityNodeInfo.obtain();
                }

/* 34 */        public static Object obtain(View source) {
/* 35 */            return AccessibilityNodeInfo.obtain(source);
                }

/* 38 */        public static Object obtain(Object info) {
/* 39 */            return AccessibilityNodeInfo.obtain((AccessibilityNodeInfo) info);
                }

/* 42 */        public static void addAction(Object info, int action) {
/* 43 */            ((AccessibilityNodeInfo) info).addAction(action);
                }

/* 46 */        public static void addChild(Object info, View child) {
/* 47 */            ((AccessibilityNodeInfo) info).addChild(child);
                }

/* 51 */        public static List<Object> findAccessibilityNodeInfosByText(Object info, String text) {
/* 52 */            return ((AccessibilityNodeInfo) info).findAccessibilityNodeInfosByText(text);
                }

/* 56 */        public static int getActions(Object info) {
/* 57 */            return ((AccessibilityNodeInfo) info).getActions();
                }

/* 60 */        public static void getBoundsInParent(Object info, Rect outBounds) {
/* 61 */            ((AccessibilityNodeInfo) info).getBoundsInParent(outBounds);
                }

/* 64 */        public static void getBoundsInScreen(Object info, Rect outBounds) {
/* 65 */            ((AccessibilityNodeInfo) info).getBoundsInScreen(outBounds);
                }

/* 68 */        public static Object getChild(Object info, int index) {
/* 69 */            return ((AccessibilityNodeInfo) info).getChild(index);
                }

/* 72 */        public static int getChildCount(Object info) {
/* 73 */            return ((AccessibilityNodeInfo) info).getChildCount();
                }

/* 76 */        public static CharSequence getClassName(Object info) {
/* 77 */            return ((AccessibilityNodeInfo) info).getClassName();
                }

/* 80 */        public static CharSequence getContentDescription(Object info) {
/* 81 */            return ((AccessibilityNodeInfo) info).getContentDescription();
                }

/* 84 */        public static CharSequence getPackageName(Object info) {
/* 85 */            return ((AccessibilityNodeInfo) info).getPackageName();
                }

/* 88 */        public static Object getParent(Object info) {
/* 89 */            return ((AccessibilityNodeInfo) info).getParent();
                }

/* 92 */        public static CharSequence getText(Object info) {
/* 93 */            return ((AccessibilityNodeInfo) info).getText();
                }

/* 96 */        public static int getWindowId(Object info) {
/* 97 */            return ((AccessibilityNodeInfo) info).getWindowId();
                }

/* 100 */       public static boolean isCheckable(Object info) {
/* 101 */           return ((AccessibilityNodeInfo) info).isCheckable();
                }

/* 104 */       public static boolean isChecked(Object info) {
/* 105 */           return ((AccessibilityNodeInfo) info).isChecked();
                }

/* 108 */       public static boolean isClickable(Object info) {
/* 109 */           return ((AccessibilityNodeInfo) info).isClickable();
                }

/* 112 */       public static boolean isEnabled(Object info) {
/* 113 */           return ((AccessibilityNodeInfo) info).isEnabled();
                }

/* 116 */       public static boolean isFocusable(Object info) {
/* 117 */           return ((AccessibilityNodeInfo) info).isFocusable();
                }

/* 120 */       public static boolean isFocused(Object info) {
/* 121 */           return ((AccessibilityNodeInfo) info).isFocused();
                }

/* 124 */       public static boolean isLongClickable(Object info) {
/* 125 */           return ((AccessibilityNodeInfo) info).isLongClickable();
                }

/* 128 */       public static boolean isPassword(Object info) {
/* 129 */           return ((AccessibilityNodeInfo) info).isPassword();
                }

/* 132 */       public static boolean isScrollable(Object info) {
/* 133 */           return ((AccessibilityNodeInfo) info).isScrollable();
                }

/* 136 */       public static boolean isSelected(Object info) {
/* 137 */           return ((AccessibilityNodeInfo) info).isSelected();
                }

/* 140 */       public static boolean performAction(Object info, int action) {
/* 141 */           return ((AccessibilityNodeInfo) info).performAction(action);
                }

/* 144 */       public static void setBoundsInParent(Object info, Rect bounds) {
/* 145 */           ((AccessibilityNodeInfo) info).setBoundsInParent(bounds);
                }

/* 148 */       public static void setBoundsInScreen(Object info, Rect bounds) {
/* 149 */           ((AccessibilityNodeInfo) info).setBoundsInScreen(bounds);
                }

/* 152 */       public static void setCheckable(Object info, boolean checkable) {
/* 153 */           ((AccessibilityNodeInfo) info).setCheckable(checkable);
                }

/* 156 */       public static void setChecked(Object info, boolean checked) {
/* 157 */           ((AccessibilityNodeInfo) info).setChecked(checked);
                }

/* 160 */       public static void setClassName(Object info, CharSequence className) {
/* 161 */           ((AccessibilityNodeInfo) info).setClassName(className);
                }

/* 164 */       public static void setClickable(Object info, boolean clickable) {
/* 165 */           ((AccessibilityNodeInfo) info).setClickable(clickable);
                }

/* 168 */       public static void setContentDescription(Object info, CharSequence contentDescription) {
/* 169 */           ((AccessibilityNodeInfo) info).setContentDescription(contentDescription);
                }

/* 172 */       public static void setEnabled(Object info, boolean enabled) {
/* 173 */           ((AccessibilityNodeInfo) info).setEnabled(enabled);
                }

/* 176 */       public static void setFocusable(Object info, boolean focusable) {
/* 177 */           ((AccessibilityNodeInfo) info).setFocusable(focusable);
                }

/* 180 */       public static void setFocused(Object info, boolean focused) {
/* 181 */           ((AccessibilityNodeInfo) info).setFocused(focused);
                }

/* 184 */       public static void setLongClickable(Object info, boolean longClickable) {
/* 185 */           ((AccessibilityNodeInfo) info).setLongClickable(longClickable);
                }

/* 188 */       public static void setPackageName(Object info, CharSequence packageName) {
/* 189 */           ((AccessibilityNodeInfo) info).setPackageName(packageName);
                }

/* 192 */       public static void setParent(Object info, View parent) {
/* 193 */           ((AccessibilityNodeInfo) info).setParent(parent);
                }

/* 196 */       public static void setPassword(Object info, boolean password) {
/* 197 */           ((AccessibilityNodeInfo) info).setPassword(password);
                }

/* 200 */       public static void setScrollable(Object info, boolean scrollable) {
/* 201 */           ((AccessibilityNodeInfo) info).setScrollable(scrollable);
                }

/* 204 */       public static void setSelected(Object info, boolean selected) {
/* 205 */           ((AccessibilityNodeInfo) info).setSelected(selected);
                }

/* 208 */       public static void setSource(Object info, View source) {
/* 209 */           ((AccessibilityNodeInfo) info).setSource(source);
                }

/* 212 */       public static void setText(Object info, CharSequence text) {
/* 213 */           ((AccessibilityNodeInfo) info).setText(text);
                }

/* 216 */       public static void recycle(Object info) {
/* 217 */           ((AccessibilityNodeInfo) info).recycle();
                }
            }
