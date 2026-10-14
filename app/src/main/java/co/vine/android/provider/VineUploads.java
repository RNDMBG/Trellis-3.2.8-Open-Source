            package co.vine.android.provider;

            import android.net.Uri;
            import android.provider.BaseColumns;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public class VineUploads {

                public static final class Uploads implements BaseColumns, UploadsColumns {
                    public static final String PATH_DELETE_UPLOAD = "uploads/delete_upload";
                    public static final String PATH_PUT_HASH = "uploads/put_hash";
                    public static final String PATH_PUT_MESSAGE_ROW_ID = "uploads/put_message_row_id";
                    public static final String PATH_PUT_NEW_UPLOAD = "uploads/put_new_upload";
                    public static final String PATH_PUT_POST_INFO = "uploads/put_post_info";
                    public static final String PATH_PUT_STATUS = "uploads/put_status";
                    public static final String PATH_PUT_UPLOAD_TIME = "uploads/put_upload_time";
                    public static final String PATH_PUT_URIS = "uploads/put_uris";
                    public static final String PATH_PUT_VALUES = "uploads/put_values";
                    public static final String PATH_REFERENCE = "uploads/reference";
                    public static final String PATH_UPLOAD = "uploads/upload";
                    public static final String QUERY_CAPTCHA_URL = "captcha_url";
                    public static final String QUERY_HASH = "hash";
                    public static final String QUERY_MESSAGE_ROW_ID = "message_row_id";
                    public static final String QUERY_PATH = "path";
                    public static final String QUERY_POST_INFO = "post_info";
                    public static final String QUERY_REFERENCE = "reference";
                    public static final String QUERY_STATUS = "status";
                    public static final String QUERY_THUMBNAIL_URL = "thumbnail_url";
                    public static final String QUERY_UPLOAD_TIME = "upload_time";
                    public static final String QUERY_VIDEO_URL = "video_url";
                    public static final String SORT_ORDER_DEFAULT = "_id ASC";
                    public static final String TABLE_NAME = "uploads";
                    public static final Uri CONTENT_URI = Uri.parse(VineUploadProvider.CONTENT_AUTHORITY + TABLE_NAME);
                }

                public interface UploadsColumns {
                    public static final String CAPTCHA_URL = "captcha_url";
                    public static final String CONVERSATION_ROW_ID = "conversation_row_id";
                    public static final String HASH = "hash";
                    public static final String IS_PRIVATE = "is_private";
                    public static final String MAX_LOOPS = "max_loops";
                    public static final String MESSAGE_ROW_ID = "message_row";
                    public static final String OWNER_ID = "owner_id";
                    public static final String PATH = "path";
                    public static final String POST_INFO = "post_info";
                    public static final String REFERENCE = "reference";
                    public static final String STATUS = "status";
                    public static final String THUMBNAIL_PATH = "thumbnail_path";
                    public static final String THUMBNAIL_URL = "thumbnail_url";
                    public static final String UPLOAD_TIME = "upload_time";
                    public static final String VIDEO_URL = "video_url";
                }
            }
