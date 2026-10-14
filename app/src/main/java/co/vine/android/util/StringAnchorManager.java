            package co.vine.android.util;

            import android.content.Context;
            import android.content.SharedPreferences;
            
            
    public class StringAnchorManager {
                public static final int NONE_ID = 0;
                private static final String sPrefixAnchor = "anchor_";
                private static final String sPrefixIndex = "index_";
                private static final String sPrefixLastId = "lastId_";
                private static final String sPrefixRefreshTime = "refresh_time_";
                private final SharedPreferences mPrefs;
                private final String mType;

        public StringAnchorManager(Context context, String type) {
            this.mPrefs = CommonUtil.getSharedPrefs(context, "StringAnchorManager");
            this.mType = type;
                }

        public String getStringAnchor() {
            return this.mPrefs.getString(sPrefixAnchor + this.mType, null);
                }

        public long getRefreshTime() {
            return this.mPrefs.getLong(sPrefixRefreshTime + this.mType, -1L);
                }

        public long getIndex() {
            return this.mPrefs.getLong(sPrefixIndex + this.mType, -1L);
                }

        public long getLastId() {
            return this.mPrefs.getLong(sPrefixLastId + this.mType, -1L);
                }

        public boolean haveMore() {
                    return getLastId() != 0;
                }

        public static class Editor {
                    private final SharedPreferences.Editor mEditor;
                    private final String mType;

            public Editor(SharedPreferences.Editor edit, String type) {
                this.mEditor = edit;
                this.mType = type;
                    }

            public void setRefreshTime(long refreshTime) {
                this.mEditor.putLong(StringAnchorManager.sPrefixRefreshTime + this.mType, refreshTime);
                    }

            public void setAnchor(String anchor) {
                this.mEditor.putString(StringAnchorManager.sPrefixAnchor + this.mType, anchor);
                    }

            public void setIndex(long index) {
                this.mEditor.putLong(StringAnchorManager.sPrefixIndex + this.mType, index);
                    }

            public void setLastId(long lastId) {
                this.mEditor.putLong(StringAnchorManager.sPrefixLastId + this.mType, lastId);
                    }

            public void commit() {
                this.mEditor.commit();
                    }
                }

        public Editor edit() {
            return new Editor(this.mPrefs.edit(), this.mType);
                }
            }
