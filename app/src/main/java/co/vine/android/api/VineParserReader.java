            package co.vine.android.api;

            import android.os.Bundle;
            import android.util.Log;
            import co.vine.android.network.HttpResult;
            import co.vine.android.network.NetworkOperationReader;
            import com.edisonwang.android.slog.SLog;
            import com.fasterxml.jackson.core.JsonParser;
            import java.io.ByteArrayInputStream;
            import java.io.ByteArrayOutputStream;
            import java.io.IOException;
            import java.io.InputStream;
            
            
    public class VineParserReader implements NetworkOperationReader {
                public static final String EXTRA_LAST_FRIEND_INDEX = "friend_index";
                public static final boolean LOGGABLE = Log.isLoggable("VineParserReader", 3);
                public static final String TAG = "VineParserReader";
                public static final int TYPE_ACTIVITY = 9;
                public static final int TYPE_ACTIVITY_COUNTS = 26;
                public static final int TYPE_CHANNELS = 18;
                public static final int TYPE_CLIENT_FLAGS = 29;
                public static final int TYPE_CLIENT_PROFILE = 13;
                public static final int TYPE_COMMENT = 7;
                public static final int TYPE_COMMENTS = 6;
                public static final int TYPE_COMPLAINTS_MENU = 35;
                public static final int TYPE_CONVERSATION = 22;
                public static final int TYPE_EDITIONS = 20;
                public static final int TYPE_GENERAL = 1;
                public static final int TYPE_GROUPED_ACTIVITY = 28;
                public static final int TYPE_INBOX = 25;
                public static final int TYPE_LIKE = 10;
                public static final int TYPE_LIKES = 11;
                public static final int TYPE_LOGIN = 4;
                public static final int TYPE_LOOPS_SUBMISSION = 27;
                public static final int TYPE_MESSAGE = 23;
                public static final int TYPE_NOTIFICATION_SETTINGS = 30;
                public static final int TYPE_PENDING_NOTIF_COUNT = 21;
                public static final int TYPE_POSTS = 8;
                public static final int TYPE_POST_RESPONSE = 12;
                public static final int TYPE_PRIVATE_MESSAGE_RESPONSE = 24;
                public static final int TYPE_REVINE = 19;
                public static final int TYPE_SERVER_STATUS = 15;
                public static final int TYPE_SIGN_UP = 5;
                public static final int TYPE_TAGS = 14;
                public static final int TYPE_UPLOAD = 16;
                public static final int TYPE_USER = 2;
                public static final int TYPE_USERS = 3;
                private final Bundle mBundle = new Bundle();
                private Object mParsedObject;
                private final int mType;

                @Override 
        public void readInput(int statusCode, long contentLength, InputStream is) throws IOException {
            
            
            
            if (statusCode == 200) {
                        switch (this.mType) {
                            case 4:
                        JsonParser jsonParser = VineParsers.createParser(is);
                        this.mParsedObject = VineParsers.parseLogin(jsonParser);
                                break;
                            default:
                       JsonParser jsonParser2 = VineParsers.createParser(is);
                       this.mParsedObject = VineParsers.parseVineResponse(jsonParser2, this.mType, this.mBundle);
                                break;
                        }
               return;
                    }
           JsonParser jsonParser3 = VineParsers.createParser(is);
           this.mParsedObject = VineParsers.parseError(jsonParser3);
           if (LOGGABLE) {
               Log.d("VineParserReader", "ERROR: Response with " + statusCode);
                    }
                }

       public <T> T getParsedObject() {
           return (T) this.mParsedObject;
                }

       public static VineParserReader createParserReader(int type) {
           return new VineParserReader(type);
                }

       public static VineParserReader createLoginReader() {
           return new VineParserReader(4);
                }

       private VineParserReader(int type) {
           this.mType = type;
                }

                @Override 
       public void onHandleError(HttpResult result) {
                }

       public Bundle getExtraBundle() {
           return this.mBundle;
                }
            }
