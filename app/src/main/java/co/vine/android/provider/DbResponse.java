            package co.vine.android.provider;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 14 */    public class DbResponse {
                public static final int RESPONSE_CODE_ERROR = 3;
                public static final int RESPONSE_CODE_NON_CONTINGUOUS_CLEAN_MADE = 4;
                public static final int RESPONSE_CODE_NO_CHANGE = 2;
                public static final int RESPONSE_CODE_SUCCESS = 1;
                public final int code;
                public final int numInserted;
                public final int numUpdated;

/* 15 */        public DbResponse(int numInserted, int numUpdated, int code) {
/* 17 */            this.numInserted = numInserted;
/* 18 */            this.numUpdated = numUpdated;
/* 19 */            this.code = code;
                }

/* 23 */        public String toString() {
/* 24 */            return "<DbResponse> inserted: " + this.numInserted + " updated: " + this.numUpdated + " responseCode: " + this.code;
                }
            }
