            package android.support.v4.view.accessibility;

            import android.os.Parcelable;
            import android.view.View;
            import android.view.accessibility.AccessibilityRecord;
            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 26 */    class AccessibilityRecordCompatIcs {
/* 27 */        AccessibilityRecordCompatIcs() {
                }

/* 30 */        public static Object obtain() {
/* 31 */            return AccessibilityRecord.obtain();
                }

/* 34 */        public static Object obtain(Object record) {
/* 35 */            return AccessibilityRecord.obtain((AccessibilityRecord) record);
                }

/* 38 */        public static int getAddedCount(Object record) {
/* 39 */            return ((AccessibilityRecord) record).getAddedCount();
                }

/* 42 */        public static CharSequence getBeforeText(Object record) {
/* 43 */            return ((AccessibilityRecord) record).getBeforeText();
                }

/* 46 */        public static CharSequence getClassName(Object record) {
/* 47 */            return ((AccessibilityRecord) record).getClassName();
                }

/* 50 */        public static CharSequence getContentDescription(Object record) {
/* 51 */            return ((AccessibilityRecord) record).getContentDescription();
                }

/* 54 */        public static int getCurrentItemIndex(Object record) {
/* 55 */            return ((AccessibilityRecord) record).getCurrentItemIndex();
                }

/* 58 */        public static int getFromIndex(Object record) {
/* 59 */            return ((AccessibilityRecord) record).getFromIndex();
                }

/* 62 */        public static int getItemCount(Object record) {
/* 63 */            return ((AccessibilityRecord) record).getItemCount();
                }

/* 66 */        public static Parcelable getParcelableData(Object record) {
/* 67 */            return ((AccessibilityRecord) record).getParcelableData();
                }

/* 70 */        public static int getRemovedCount(Object record) {
/* 71 */            return ((AccessibilityRecord) record).getRemovedCount();
                }

/* 74 */        public static int getScrollX(Object record) {
/* 75 */            return ((AccessibilityRecord) record).getScrollX();
                }

/* 78 */        public static int getScrollY(Object record) {
/* 79 */            return ((AccessibilityRecord) record).getScrollY();
                }

/* 82 */        public static Object getSource(Object record) {
/* 83 */            return ((AccessibilityRecord) record).getSource();
                }

/* 86 */        public static List<CharSequence> getText(Object record) {
/* 87 */            return ((AccessibilityRecord) record).getText();
                }

/* 90 */        public static int getToIndex(Object record) {
/* 91 */            return ((AccessibilityRecord) record).getToIndex();
                }

/* 94 */        public static int getWindowId(Object record) {
/* 95 */            return ((AccessibilityRecord) record).getWindowId();
                }

/* 98 */        public static boolean isChecked(Object record) {
/* 99 */            return ((AccessibilityRecord) record).isChecked();
                }

/* 102 */       public static boolean isEnabled(Object record) {
/* 103 */           return ((AccessibilityRecord) record).isEnabled();
                }

/* 106 */       public static boolean isFullScreen(Object record) {
/* 107 */           return ((AccessibilityRecord) record).isFullScreen();
                }

/* 110 */       public static boolean isPassword(Object record) {
/* 111 */           return ((AccessibilityRecord) record).isPassword();
                }

/* 114 */       public static boolean isScrollable(Object record) {
/* 115 */           return ((AccessibilityRecord) record).isScrollable();
                }

/* 118 */       public static void recycle(Object record) {
/* 119 */           ((AccessibilityRecord) record).recycle();
                }

/* 122 */       public static void setAddedCount(Object record, int addedCount) {
/* 123 */           ((AccessibilityRecord) record).setAddedCount(addedCount);
                }

/* 126 */       public static void setBeforeText(Object record, CharSequence beforeText) {
/* 127 */           ((AccessibilityRecord) record).setBeforeText(beforeText);
                }

/* 130 */       public static void setChecked(Object record, boolean isChecked) {
/* 131 */           ((AccessibilityRecord) record).setChecked(isChecked);
                }

/* 134 */       public static void setClassName(Object record, CharSequence className) {
/* 135 */           ((AccessibilityRecord) record).setClassName(className);
                }

/* 138 */       public static void setContentDescription(Object record, CharSequence contentDescription) {
/* 139 */           ((AccessibilityRecord) record).setContentDescription(contentDescription);
                }

/* 142 */       public static void setCurrentItemIndex(Object record, int currentItemIndex) {
/* 143 */           ((AccessibilityRecord) record).setCurrentItemIndex(currentItemIndex);
                }

/* 146 */       public static void setEnabled(Object record, boolean isEnabled) {
/* 147 */           ((AccessibilityRecord) record).setEnabled(isEnabled);
                }

/* 150 */       public static void setFromIndex(Object record, int fromIndex) {
/* 151 */           ((AccessibilityRecord) record).setFromIndex(fromIndex);
                }

/* 154 */       public static void setFullScreen(Object record, boolean isFullScreen) {
/* 155 */           ((AccessibilityRecord) record).setFullScreen(isFullScreen);
                }

/* 158 */       public static void setItemCount(Object record, int itemCount) {
/* 159 */           ((AccessibilityRecord) record).setItemCount(itemCount);
                }

/* 162 */       public static void setParcelableData(Object record, Parcelable parcelableData) {
/* 163 */           ((AccessibilityRecord) record).setParcelableData(parcelableData);
                }

/* 166 */       public static void setPassword(Object record, boolean isPassword) {
/* 167 */           ((AccessibilityRecord) record).setPassword(isPassword);
                }

/* 170 */       public static void setRemovedCount(Object record, int removedCount) {
/* 171 */           ((AccessibilityRecord) record).setRemovedCount(removedCount);
                }

/* 174 */       public static void setScrollX(Object record, int scrollX) {
/* 175 */           ((AccessibilityRecord) record).setScrollX(scrollX);
                }

/* 178 */       public static void setScrollY(Object record, int scrollY) {
/* 179 */           ((AccessibilityRecord) record).setScrollY(scrollY);
                }

/* 182 */       public static void setScrollable(Object record, boolean scrollable) {
/* 183 */           ((AccessibilityRecord) record).setScrollable(scrollable);
                }

/* 186 */       public static void setSource(Object record, View source) {
/* 187 */           ((AccessibilityRecord) record).setSource(source);
                }

/* 190 */       public static void setToIndex(Object record, int toIndex) {
/* 191 */           ((AccessibilityRecord) record).setToIndex(toIndex);
                }
            }
