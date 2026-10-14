            package co.vine.android.provider;
            
            
            public class VineUploadsDatabaseSQL {
                public static final String ADD_COLUMN = " ADD COLUMN ";
                public static final String ALTER_TABLE = "ALTER TABLE ";

                
                public static final String f106AS = " AS ";
                public static final String CREATE_TABLE = "CREATE TABLE ";
                public static final String CREATE_VIEW = "CREATE VIEW ";
                public static final String UPLOADS_ADD_CAPTCHA_URL_COLUMN = "ALTER TABLE uploads ADD COLUMN captcha_url TEXT;";
                public static final String UPLOADS_ADD_CONVERSATION_ID_COLUMN = "ALTER TABLE uploads ADD COLUMN conversation_row_id INT;";
                public static final String UPLOADS_ADD_MAX_LOOPS_COLUMN = "ALTER TABLE uploads ADD COLUMN max_loops INT;";
                public static final String UPLOADS_ADD_MESSAGE_ROW_ID_COLUMN = "ALTER TABLE uploads ADD COLUMN message_row INT;";
                public static final String UPLOADS_ADD_OWNER_ID_COLUMN = "ALTER TABLE uploads ADD COLUMN owner_id INT;";
                public static final String UPLOADS_ADD_PRIVATE_COLUMN = "ALTER TABLE uploads ADD COLUMN is_private INT;";
                public static final String UPLOADS_ADD_REFERENCE_COLUMN = "ALTER TABLE uploads ADD COLUMN reference TEXT;";
                public static final String UPLOADS_TABLE_SQL = "CREATE TABLE  IF NOT EXISTS uploads (_id INTEGER PRIMARY KEY,path TEXT,hash TEXT,status INT,post_info TEXT,video_url TEXT,thumbnail_path TEXT,thumbnail_url TEXT,upload_time TEXT,is_private INT,conversation_row_id INT,reference TEXT,owner_id INT,captcha_url TEXT, max_loops INT, message_row INT);";

                public static final class UploadsQuery {
                    public static final int INDEX_CAPTCHA_URL = 13;
                    public static final int INDEX_CONVERSATION_ROW_ID = 10;
                    public static final int INDEX_HASH = 2;
                    public static final int INDEX_ID = 0;
                    public static final int INDEX_IS_PRIVATE = 9;
                    public static final int INDEX_MAX_LOOPS = 14;
                    public static final int INDEX_MESSAGE_ROW_ID = 15;
                    public static final int INDEX_OWNER_ID = 12;
                    public static final int INDEX_PATH = 1;
                    public static final int INDEX_POST_INFO = 4;
                    public static final int INDEX_REFERENCE = 11;
                    public static final int INDEX_STATUS = 3;
                    public static final int INDEX_THUMBNAIL_PATH = 6;
                    public static final int INDEX_THUMBNAIL_URL = 7;
                    public static final int INDEX_UPLOAD_TIME = 8;
                    public static final int INDEX_VIDEO_URL = 5;
                    public static final String[] PROJECTION = {"_id", "path", "hash", "status", "post_info", "video_url", "thumbnail_path", "thumbnail_url", "upload_time", "is_private", "conversation_row_id", "reference", "owner_id", "captcha_url", "max_loops", VineUploads.UploadsColumns.MESSAGE_ROW_ID};
                }
            }
