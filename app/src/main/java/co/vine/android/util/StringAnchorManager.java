            package co.vine.android.util;

            import android.content.Context;
            import android.content.SharedPreferences;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 17 */    public class StringAnchorManager {
                public static final int NONE_ID = 0;
                private static final String sPrefixAnchor = "anchor_";
                private static final String sPrefixIndex = "index_";
                private static final String sPrefixLastId = "lastId_";
                private static final String sPrefixRefreshTime = "refresh_time_";
                private final SharedPreferences mPrefs;
                private final String mType;

/* 18 */        public StringAnchorManager(Context context, String type) {
/* 20 */            this.mPrefs = CommonUtil.getSharedPrefs(context, "StringAnchorManager");
/* 21 */            this.mType = type;
                }

/* 24 */        public String getStringAnchor() {
/* 25 */            return this.mPrefs.getString(sPrefixAnchor + this.mType, null);
                }

/* 28 */        public long getRefreshTime() {
/* 29 */            return this.mPrefs.getLong(sPrefixRefreshTime + this.mType, -1L);
                }

/* 32 */        public long getIndex() {
/* 33 */            return this.mPrefs.getLong(sPrefixIndex + this.mType, -1L);
                }

/* 36 */        public long getLastId() {
/* 37 */            return this.mPrefs.getLong(sPrefixLastId + this.mType, -1L);
                }

/* 40 */        public boolean haveMore() {
                    return getLastId() != 0;
                }

/* 47 */        public static class Editor {
                    private final SharedPreferences.Editor mEditor;
                    private final String mType;

/* 48 */            public Editor(SharedPreferences.Editor edit, String type) {
/* 50 */                this.mEditor = edit;
/* 51 */                this.mType = type;
                    }

/* 54 */            public void setRefreshTime(long refreshTime) {
/* 55 */                this.mEditor.putLong(StringAnchorManager.sPrefixRefreshTime + this.mType, refreshTime);
                    }

/* 58 */            public void setAnchor(String anchor) {
/* 59 */                this.mEditor.putString(StringAnchorManager.sPrefixAnchor + this.mType, anchor);
                    }

/* 62 */            public void setIndex(long index) {
/* 63 */                this.mEditor.putLong(StringAnchorManager.sPrefixIndex + this.mType, index);
                    }

/* 66 */            public void setLastId(long lastId) {
/* 67 */                this.mEditor.putLong(StringAnchorManager.sPrefixLastId + this.mType, lastId);
                    }

/* 70 */            public void commit() {
/* 71 */                this.mEditor.commit();
                    }
                }

/* 75 */        public Editor edit() {
/* 76 */            return new Editor(this.mPrefs.edit(), this.mType);
                }
            }
