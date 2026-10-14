            package android.support.v4.view.accessibility;

            import android.os.Build;
            import android.os.Parcelable;
            import android.view.View;
            import java.util.Collections;
            import java.util.List;
            
            
    public class AccessibilityRecordCompat {
                private static final AccessibilityRecordImpl IMPL;
                private final Object mRecord;

                interface AccessibilityRecordImpl {
                    int getAddedCount(Object obj);

                    CharSequence getBeforeText(Object obj);

                    CharSequence getClassName(Object obj);

                    CharSequence getContentDescription(Object obj);

                    int getCurrentItemIndex(Object obj);

                    int getFromIndex(Object obj);

                    int getItemCount(Object obj);

                    int getMaxScrollX(Object obj);

                    int getMaxScrollY(Object obj);

                    Parcelable getParcelableData(Object obj);

                    int getRemovedCount(Object obj);

                    int getScrollX(Object obj);

                    int getScrollY(Object obj);

                    AccessibilityNodeInfoCompat getSource(Object obj);

                    List<CharSequence> getText(Object obj);

                    int getToIndex(Object obj);

                    int getWindowId(Object obj);

                    boolean isChecked(Object obj);

                    boolean isEnabled(Object obj);

                    boolean isFullScreen(Object obj);

                    boolean isPassword(Object obj);

                    boolean isScrollable(Object obj);

                    Object obtain();

                    Object obtain(Object obj);

                    void recycle(Object obj);

                    void setAddedCount(Object obj, int i);

                    void setBeforeText(Object obj, CharSequence charSequence);

                    void setChecked(Object obj, boolean z);

                    void setClassName(Object obj, CharSequence charSequence);

                    void setContentDescription(Object obj, CharSequence charSequence);

                    void setCurrentItemIndex(Object obj, int i);

                    void setEnabled(Object obj, boolean z);

                    void setFromIndex(Object obj, int i);

                    void setFullScreen(Object obj, boolean z);

                    void setItemCount(Object obj, int i);

                    void setMaxScrollX(Object obj, int i);

                    void setMaxScrollY(Object obj, int i);

                    void setParcelableData(Object obj, Parcelable parcelable);

                    void setPassword(Object obj, boolean z);

                    void setRemovedCount(Object obj, int i);

                    void setScrollX(Object obj, int i);

                    void setScrollY(Object obj, int i);

                    void setScrollable(Object obj, boolean z);

                    void setSource(Object obj, View view);

                    void setSource(Object obj, View view, int i);

                    void setToIndex(Object obj, int i);
                }

        static class AccessibilityRecordStubImpl implements AccessibilityRecordImpl {
            AccessibilityRecordStubImpl() {
                    }

                    @Override 
            public Object obtain() {
                return null;
                    }

                    @Override 
            public Object obtain(Object record) {
                return null;
                    }

                    @Override 
            public int getAddedCount(Object record) {
                return 0;
                    }

                    @Override 
            public CharSequence getBeforeText(Object record) {
                return null;
                    }

                    @Override 
            public CharSequence getClassName(Object record) {
               return null;
                    }

                    @Override 
           public CharSequence getContentDescription(Object record) {
               return null;
                    }

                    @Override 
           public int getCurrentItemIndex(Object record) {
               return 0;
                    }

                    @Override 
           public int getFromIndex(Object record) {
               return 0;
                    }

                    @Override 
           public int getItemCount(Object record) {
               return 0;
                    }

                    @Override 
           public int getMaxScrollX(Object record) {
               return 0;
                    }

                    @Override 
           public int getMaxScrollY(Object record) {
               return 0;
                    }

                    @Override 
           public Parcelable getParcelableData(Object record) {
               return null;
                    }

                    @Override 
           public int getRemovedCount(Object record) {
               return 0;
                    }

                    @Override 
           public int getScrollX(Object record) {
               return 0;
                    }

                    @Override 
           public int getScrollY(Object record) {
               return 0;
                    }

                    @Override 
           public AccessibilityNodeInfoCompat getSource(Object record) {
               return null;
                    }

                    @Override 
           public List<CharSequence> getText(Object record) {
               return Collections.emptyList();
                    }

                    @Override 
           public int getToIndex(Object record) {
               return 0;
                    }

                    @Override 
           public int getWindowId(Object record) {
               return 0;
                    }

                    @Override 
           public boolean isChecked(Object record) {
               return false;
                    }

                    @Override 
           public boolean isEnabled(Object record) {
               return false;
                    }

                    @Override 
           public boolean isFullScreen(Object record) {
               return false;
                    }

                    @Override 
           public boolean isPassword(Object record) {
               return false;
                    }

                    @Override 
           public boolean isScrollable(Object record) {
               return false;
                    }

                    @Override 
           public void recycle(Object record) {
                    }

                    @Override 
           public void setAddedCount(Object record, int addedCount) {
                    }

                    @Override 
           public void setBeforeText(Object record, CharSequence beforeText) {
                    }

                    @Override 
           public void setChecked(Object record, boolean isChecked) {
                    }

                    @Override 
           public void setClassName(Object record, CharSequence className) {
                    }

                    @Override 
           public void setContentDescription(Object record, CharSequence contentDescription) {
                    }

                    @Override 
           public void setCurrentItemIndex(Object record, int currentItemIndex) {
                    }

                    @Override 
           public void setEnabled(Object record, boolean isEnabled) {
                    }

                    @Override 
           public void setFromIndex(Object record, int fromIndex) {
                    }

                    @Override 
           public void setFullScreen(Object record, boolean isFullScreen) {
                    }

                    @Override 
           public void setItemCount(Object record, int itemCount) {
                    }

                    @Override 
           public void setMaxScrollX(Object record, int maxScrollX) {
                    }

                    @Override 
           public void setMaxScrollY(Object record, int maxScrollY) {
                    }

                    @Override 
           public void setParcelableData(Object record, Parcelable parcelableData) {
                    }

                    @Override 
           public void setPassword(Object record, boolean isPassword) {
                    }

                    @Override 
           public void setRemovedCount(Object record, int removedCount) {
                    }

                    @Override 
           public void setScrollX(Object record, int scrollX) {
                    }

                    @Override 
           public void setScrollY(Object record, int scrollY) {
                    }

                    @Override 
           public void setScrollable(Object record, boolean scrollable) {
                    }

                    @Override 
           public void setSource(Object record, View source) {
                    }

                    @Override 
           public void setSource(Object record, View root, int virtualDescendantId) {
                    }

                    @Override 
           public void setToIndex(Object record, int toIndex) {
                    }
                }

       static class AccessibilityRecordIcsImpl extends AccessibilityRecordStubImpl {
           AccessibilityRecordIcsImpl() {
                    }

                    @Override 
           public Object obtain() {
               return AccessibilityRecordCompatIcs.obtain();
                    }

                    @Override 
           public Object obtain(Object record) {
               return AccessibilityRecordCompatIcs.obtain(record);
                    }

                    @Override 
           public int getAddedCount(Object record) {
               return AccessibilityRecordCompatIcs.getAddedCount(record);
                    }

                    @Override 
           public CharSequence getBeforeText(Object record) {
               return AccessibilityRecordCompatIcs.getBeforeText(record);
                    }

                    @Override 
           public CharSequence getClassName(Object record) {
               return AccessibilityRecordCompatIcs.getClassName(record);
                    }

                    @Override 
           public CharSequence getContentDescription(Object record) {
               return AccessibilityRecordCompatIcs.getContentDescription(record);
                    }

                    @Override 
           public int getCurrentItemIndex(Object record) {
               return AccessibilityRecordCompatIcs.getCurrentItemIndex(record);
                    }

                    @Override 
           public int getFromIndex(Object record) {
               return AccessibilityRecordCompatIcs.getFromIndex(record);
                    }

                    @Override 
           public int getItemCount(Object record) {
               return AccessibilityRecordCompatIcs.getItemCount(record);
                    }

                    @Override 
           public Parcelable getParcelableData(Object record) {
               return AccessibilityRecordCompatIcs.getParcelableData(record);
                    }

                    @Override 
           public int getRemovedCount(Object record) {
               return AccessibilityRecordCompatIcs.getRemovedCount(record);
                    }

                    @Override 
           public int getScrollX(Object record) {
               return AccessibilityRecordCompatIcs.getScrollX(record);
                    }

                    @Override 
           public int getScrollY(Object record) {
               return AccessibilityRecordCompatIcs.getScrollY(record);
                    }

                    @Override 
           public AccessibilityNodeInfoCompat getSource(Object record) {
               return AccessibilityNodeInfoCompat.wrapNonNullInstance(AccessibilityRecordCompatIcs.getSource(record));
                    }

                    @Override 
           public List<CharSequence> getText(Object record) {
               return AccessibilityRecordCompatIcs.getText(record);
                    }

                    @Override 
           public int getToIndex(Object record) {
               return AccessibilityRecordCompatIcs.getToIndex(record);
                    }

                    @Override 
           public int getWindowId(Object record) {
               return AccessibilityRecordCompatIcs.getWindowId(record);
                    }

                    @Override 
           public boolean isChecked(Object record) {
               return AccessibilityRecordCompatIcs.isChecked(record);
                    }

                    @Override 
           public boolean isEnabled(Object record) {
               return AccessibilityRecordCompatIcs.isEnabled(record);
                    }

                    @Override 
           public boolean isFullScreen(Object record) {
               return AccessibilityRecordCompatIcs.isFullScreen(record);
                    }

                    @Override 
           public boolean isPassword(Object record) {
               return AccessibilityRecordCompatIcs.isPassword(record);
                    }

                    @Override 
           public boolean isScrollable(Object record) {
               return AccessibilityRecordCompatIcs.isScrollable(record);
                    }

                    @Override 
           public void recycle(Object record) {
               AccessibilityRecordCompatIcs.recycle(record);
                    }

                    @Override 
           public void setAddedCount(Object record, int addedCount) {
               AccessibilityRecordCompatIcs.setAddedCount(record, addedCount);
                    }

                    @Override 
           public void setBeforeText(Object record, CharSequence beforeText) {
               AccessibilityRecordCompatIcs.setBeforeText(record, beforeText);
                    }

                    @Override 
           public void setChecked(Object record, boolean isChecked) {
               AccessibilityRecordCompatIcs.setChecked(record, isChecked);
                    }

                    @Override 
           public void setClassName(Object record, CharSequence className) {
               AccessibilityRecordCompatIcs.setClassName(record, className);
                    }

                    @Override 
           public void setContentDescription(Object record, CharSequence contentDescription) {
               AccessibilityRecordCompatIcs.setContentDescription(record, contentDescription);
                    }

                    @Override 
           public void setCurrentItemIndex(Object record, int currentItemIndex) {
               AccessibilityRecordCompatIcs.setCurrentItemIndex(record, currentItemIndex);
                    }

                    @Override 
           public void setEnabled(Object record, boolean isEnabled) {
               AccessibilityRecordCompatIcs.setEnabled(record, isEnabled);
                    }

                    @Override 
           public void setFromIndex(Object record, int fromIndex) {
               AccessibilityRecordCompatIcs.setFromIndex(record, fromIndex);
                    }

                    @Override 
           public void setFullScreen(Object record, boolean isFullScreen) {
               AccessibilityRecordCompatIcs.setFullScreen(record, isFullScreen);
                    }

                    @Override 
           public void setItemCount(Object record, int itemCount) {
               AccessibilityRecordCompatIcs.setItemCount(record, itemCount);
                    }

                    @Override 
           public void setParcelableData(Object record, Parcelable parcelableData) {
               AccessibilityRecordCompatIcs.setParcelableData(record, parcelableData);
                    }

                    @Override 
           public void setPassword(Object record, boolean isPassword) {
               AccessibilityRecordCompatIcs.setPassword(record, isPassword);
                    }

                    @Override 
           public void setRemovedCount(Object record, int removedCount) {
               AccessibilityRecordCompatIcs.setRemovedCount(record, removedCount);
                    }

                    @Override 
           public void setScrollX(Object record, int scrollX) {
               AccessibilityRecordCompatIcs.setScrollX(record, scrollX);
                    }

                    @Override 
           public void setScrollY(Object record, int scrollY) {
               AccessibilityRecordCompatIcs.setScrollY(record, scrollY);
                    }

                    @Override 
           public void setScrollable(Object record, boolean scrollable) {
               AccessibilityRecordCompatIcs.setScrollable(record, scrollable);
                    }

                    @Override 
           public void setSource(Object record, View source) {
               AccessibilityRecordCompatIcs.setSource(record, source);
                    }

                    @Override 
           public void setToIndex(Object record, int toIndex) {
               AccessibilityRecordCompatIcs.setToIndex(record, toIndex);
                    }
                }

       static class AccessibilityRecordIcsMr1Impl extends AccessibilityRecordIcsImpl {
           AccessibilityRecordIcsMr1Impl() {
                    }

                    @Override 
           public int getMaxScrollX(Object record) {
               return AccessibilityRecordCompatIcsMr1.getMaxScrollX(record);
                    }

                    @Override 
           public int getMaxScrollY(Object record) {
               return AccessibilityRecordCompatIcsMr1.getMaxScrollY(record);
                    }

                    @Override 
           public void setMaxScrollX(Object record, int maxScrollX) {
               AccessibilityRecordCompatIcsMr1.setMaxScrollX(record, maxScrollX);
                    }

                    @Override 
           public void setMaxScrollY(Object record, int maxScrollY) {
               AccessibilityRecordCompatIcsMr1.setMaxScrollY(record, maxScrollY);
                    }
                }

       static class AccessibilityRecordJellyBeanImpl extends AccessibilityRecordIcsMr1Impl {
           AccessibilityRecordJellyBeanImpl() {
                    }

                    @Override 
           public void setSource(Object record, View root, int virtualDescendantId) {
               AccessibilityRecordCompatJellyBean.setSource(record, root, virtualDescendantId);
                    }
                }

                static {
           if (Build.VERSION.SDK_INT >= 16) {
               IMPL = new AccessibilityRecordJellyBeanImpl();
               return;
                    }
           if (Build.VERSION.SDK_INT >= 15) {
               IMPL = new AccessibilityRecordIcsMr1Impl();
                    } else if (Build.VERSION.SDK_INT >= 14) {
               IMPL = new AccessibilityRecordIcsImpl();
                    } else {
               IMPL = new AccessibilityRecordStubImpl();
                    }
                }

       public AccessibilityRecordCompat(Object record) {
           this.mRecord = record;
                }

       public Object getImpl() {
           return this.mRecord;
                }

       public static AccessibilityRecordCompat obtain(AccessibilityRecordCompat record) {
           return new AccessibilityRecordCompat(IMPL.obtain(record.mRecord));
                }

       public static AccessibilityRecordCompat obtain() {
           return new AccessibilityRecordCompat(IMPL.obtain());
                }

       public void setSource(View source) {
           IMPL.setSource(this.mRecord, source);
                }

       public void setSource(View root, int virtualDescendantId) {
           IMPL.setSource(this.mRecord, root, virtualDescendantId);
                }

       public AccessibilityNodeInfoCompat getSource() {
           return IMPL.getSource(this.mRecord);
                }

       public int getWindowId() {
           return IMPL.getWindowId(this.mRecord);
                }

       public boolean isChecked() {
           return IMPL.isChecked(this.mRecord);
                }

       public void setChecked(boolean isChecked) {
           IMPL.setChecked(this.mRecord, isChecked);
                }

       public boolean isEnabled() {
           return IMPL.isEnabled(this.mRecord);
                }

       public void setEnabled(boolean isEnabled) {
           IMPL.setEnabled(this.mRecord, isEnabled);
                }

       public boolean isPassword() {
           return IMPL.isPassword(this.mRecord);
                }

       public void setPassword(boolean isPassword) {
           IMPL.setPassword(this.mRecord, isPassword);
                }

       public boolean isFullScreen() {
           return IMPL.isFullScreen(this.mRecord);
                }

       public void setFullScreen(boolean isFullScreen) {
           IMPL.setFullScreen(this.mRecord, isFullScreen);
                }

       public boolean isScrollable() {
           return IMPL.isScrollable(this.mRecord);
                }

       public void setScrollable(boolean scrollable) {
           IMPL.setScrollable(this.mRecord, scrollable);
                }

       public int getItemCount() {
           return IMPL.getItemCount(this.mRecord);
                }

       public void setItemCount(int itemCount) {
           IMPL.setItemCount(this.mRecord, itemCount);
                }

       public int getCurrentItemIndex() {
           return IMPL.getCurrentItemIndex(this.mRecord);
                }

       public void setCurrentItemIndex(int currentItemIndex) {
           IMPL.setCurrentItemIndex(this.mRecord, currentItemIndex);
                }

       public int getFromIndex() {
           return IMPL.getFromIndex(this.mRecord);
                }

       public void setFromIndex(int fromIndex) {
           IMPL.setFromIndex(this.mRecord, fromIndex);
                }

       public int getToIndex() {
           return IMPL.getToIndex(this.mRecord);
                }

       public void setToIndex(int toIndex) {
           IMPL.setToIndex(this.mRecord, toIndex);
                }

       public int getScrollX() {
           return IMPL.getScrollX(this.mRecord);
                }

       public void setScrollX(int scrollX) {
           IMPL.setScrollX(this.mRecord, scrollX);
                }

       public int getScrollY() {
           return IMPL.getScrollY(this.mRecord);
                }

       public void setScrollY(int scrollY) {
           IMPL.setScrollY(this.mRecord, scrollY);
                }

       public int getMaxScrollX() {
           return IMPL.getMaxScrollX(this.mRecord);
                }

       public void setMaxScrollX(int maxScrollX) {
           IMPL.setMaxScrollX(this.mRecord, maxScrollX);
                }

       public int getMaxScrollY() {
           return IMPL.getMaxScrollY(this.mRecord);
                }

       public void setMaxScrollY(int maxScrollY) {
           IMPL.setMaxScrollY(this.mRecord, maxScrollY);
                }

       public int getAddedCount() {
           return IMPL.getAddedCount(this.mRecord);
                }

       public void setAddedCount(int addedCount) {
           IMPL.setAddedCount(this.mRecord, addedCount);
                }

       public int getRemovedCount() {
           return IMPL.getRemovedCount(this.mRecord);
                }

       public void setRemovedCount(int removedCount) {
           IMPL.setRemovedCount(this.mRecord, removedCount);
                }

       public CharSequence getClassName() {
           return IMPL.getClassName(this.mRecord);
                }

       public void setClassName(CharSequence className) {
           IMPL.setClassName(this.mRecord, className);
                }

       public List<CharSequence> getText() {
           return IMPL.getText(this.mRecord);
                }

       public CharSequence getBeforeText() {
           return IMPL.getBeforeText(this.mRecord);
                }

       public void setBeforeText(CharSequence beforeText) {
           IMPL.setBeforeText(this.mRecord, beforeText);
                }

       public CharSequence getContentDescription() {
           return IMPL.getContentDescription(this.mRecord);
                }

       public void setContentDescription(CharSequence contentDescription) {
           IMPL.setContentDescription(this.mRecord, contentDescription);
                }

       public Parcelable getParcelableData() {
           return IMPL.getParcelableData(this.mRecord);
                }

      public void setParcelableData(Parcelable parcelableData) {
          IMPL.setParcelableData(this.mRecord, parcelableData);
                }

      public void recycle() {
          IMPL.recycle(this.mRecord);
                }

      public int hashCode() {
          if (this.mRecord == null) {
                        return 0;
                    }
                    return this.mRecord.hashCode();
                }

      public boolean equals(Object obj) {
          if (this == obj) {
                        return true;
                    }
          if (obj != null && getClass() == obj.getClass()) {
              AccessibilityRecordCompat other = (AccessibilityRecordCompat) obj;
              if (this.mRecord == null) {
                            return other.mRecord == null;
                        }
                        return this.mRecord.equals(other.mRecord);
                    }
                    return false;
                }
            }
