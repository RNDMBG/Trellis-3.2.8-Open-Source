            package co.vine.android.provider;
            
            
    public class DbResponse {
                public static final int RESPONSE_CODE_ERROR = 3;
                public static final int RESPONSE_CODE_NON_CONTINGUOUS_CLEAN_MADE = 4;
                public static final int RESPONSE_CODE_NO_CHANGE = 2;
                public static final int RESPONSE_CODE_SUCCESS = 1;
                public final int code;
                public final int numInserted;
                public final int numUpdated;

        public DbResponse(int numInserted, int numUpdated, int code) {
            this.numInserted = numInserted;
            this.numUpdated = numUpdated;
            this.code = code;
                }

        public String toString() {
            return "<DbResponse> inserted: " + this.numInserted + " updated: " + this.numUpdated + " responseCode: " + this.code;
                }
            }
