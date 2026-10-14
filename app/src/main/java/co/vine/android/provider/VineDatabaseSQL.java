            package co.vine.android.provider;

            import android.database.Cursor;
            import co.vine.android.api.VineRecipient;
            import java.util.HashMap;
            
            
   public class VineDatabaseSQL {

                
                public static final String f105AS = " AS ";
                public static final String CHANNELS_TABLE_SQL = "CREATE TABLE  channels (_id INTEGER PRIMARY KEY,channel_id INT UNIQUE NOT NULL,channel TEXT NOT NULL,timestamp INT,background_color TEXT,font_color TEXT,last_used_timestamp INT,is_last INT,icon_full_url TEXT,retina_icon_full_url TEXT,following INT,retina_explore_icon_full_url TEXT);";
                public static final String COMMENTS_TABLE_SQL = "CREATE TABLE  comments (_id INTEGER PRIMARY KEY,comment_id INT UNIQUE NOT NULL,post_id INT NOT NULL,avatar_url TEXT,comment TEXT,user_id INT,username TEXT,timestamp INT,location TEXT,verified INT,entities BLOB,is_last INT,last_refresh INT);";
                public static final String CONVERSATIONS_TABLE_SQL = "CREATE TABLE  conversations (_id INTEGER PRIMARY KEY,conversation_id INT NOT NULL,network_type INT NOT NULL, unread_message_count INT, is_hidden INT DEFAULT 0, last_message INT, last_message_timestamp INT, is_last INT);";
                public static final String CONVERSATION_MESSAGE_USERS_VIEW_SQL = "CREATE VIEW message_users_view AS SELECT m._id AS _id,m.conversation_row_id AS conversation_row_id,m.message_id AS message_id,m.user_row_id AS user_row_id,m.message AS message,m.timestamp AS timestamp,m.video_url AS video_url,m.thumbnail_url AS thumbnail_url,m.is_last AS is_last,m.vanished AS vanished,m.max_loops AS max_loops,m.vanished_date AS vanished_date,m.ephemeral AS ephemeral,m.local_start_time AS local_start_time,m.deleted AS deleted,m.post_id AS post_id,m.upload_path AS upload_path,m.error_code AS error_code,m.error_reason AS error_reason,c.conversation_id AS conversation_id,c.network_type AS network_type,c.is_hidden AS is_hidden,c.unread_message_count AS unread_message_count,user.username AS sender_user_name,user.avatar_url AS sender_avatar,user.profile_background AS sender_profile_background,post.user_id AS author_user_id,post.username AS author_user_name,post.avatar_url AS author_avatar,post.entities AS post_entities,post.description AS post_description,post.share_url AS post_share_url FROM messages AS  m LEFT JOIN users AS user ON m.user_row_id=user._id LEFT JOIN conversations AS c ON m.conversation_row_id=c._id LEFT JOIN (SELECT p.post_id, p.user_id, p.username, p.avatar_url, p.entities, p.description, p.share_url FROM messages AS m LEFT JOIN post_comments_likes_view AS p ON m.post_id=p.post_id GROUP BY p.post_id) AS post ON m.post_id=post.post_id;";
                public static final String CONVERSATION_RECIPIENTS_TABLE_SQL = "CREATE TABLE conversation_recipients (_id INTEGER PRIMARY KEY,conversation_row_id INT NOT NULL,user_row_id INT NOT NULL, UNIQUE (conversation_row_id,user_row_id) ON CONFLICT IGNORE);";
                public static final String CONVERSATION_RECIPIENTS_USERS_VIEW = "CREATE VIEW  conversation_recipients_users_view AS SELECT conversation_recipients._id AS _id,conversation_recipients.conversation_row_id AS conversation_row_id,conversation_recipients.user_row_id AS user_row_id,user.username AS username,user.user_id AS user_id,user.phone_number AS phone_number,user.email_address AS email_address,user.profile_background AS profile_background FROM conversation_recipients LEFT JOIN users AS user ON conversation_recipients.user_row_id=user._id;";
                public static final String CREATE_TABLE = "CREATE TABLE ";
                public static final String CREATE_VIEW = "CREATE VIEW ";
                public static final String EDITIONS_TABLE_SQL = "CREATE TABLE  editions (_id INTEGER PRIMARY KEY,edition_code TEXT,edition_name TEXT);";
                public static final String INBOX_VIEW_SQL = "CREATE VIEW inbox_view AS SELECT cr._id AS _id,cr.conversation_row_id AS conversation_row_id,( SELECT COUNT(*) FROM conversation_recipients where conversation_row_id = cr.conversation_row_id group by conversation_row_id) AS recipients_count,m.timestamp AS timestamp,m.error_reason AS error_reason,c.network_type AS network_type,c.unread_message_count AS unread_message_count,c.is_hidden AS is_hidden,c.last_message AS last_message,c.is_last AS is_last,u.username AS username,u._id AS user_row_id,u.user_id AS user_id,u.external AS user_is_external,u.following_flag AS following_flag,u.avatar_url AS avatar_url,u.profile_background AS profile_background FROM conversation_recipients AS cr JOIN users AS u ON cr.user_row_id= u._id JOIN messages AS m ON c.last_message=m._id JOIN conversations AS c ON c._id =cr.conversation_row_id GROUP BY cr.conversation_row_id ORDER BY timestamp DESC;";
                public static final String LIKES_TABLE_SQL = "CREATE TABLE  likes (_id INTEGER PRIMARY KEY,like_id INT UNIQUE NOT NULL,post_id INT NOT NULL,avatar_url TEXT,user_id INT,username TEXT,timestamp INT,location TEXT,verified INT,last_refresh INT);";
                public static final String MESSAGES_TABLE_SQL = "CREATE TABLE  messages (_id INTEGER PRIMARY KEY,conversation_row_id INT NOT NULL,message_id INT NOT NULL,user_row_id INT NOT NULL,timestamp INT,message TEXT,video_url TEXT,thumbnail_url TEXT, is_last INT,vanished INT,max_loops INT,vanished_date INT,ephemeral INT,local_start_time INT,deleted INT,post_id INT,upload_path TEXT,error_code INT,error_reason TEXT);";
                public static final String NOTIFICATIONS_TABLE_SQL = "CREATE TABLE  notifications (_id INTEGER PRIMARY KEY,notification_id INT UNIQUE NOT NULL,notification_type INT,message TEXT,cleared INT, conversation_row_id INT, avatar_url TEXT);";
                public static final String PAGE_CURSOR_TABLE_SQL = "CREATE TABLE  page_cursors (_id INTEGER PRIMARY KEY,type INT,tag TEXT,kind INT,next_page INT,previous_page INT,anchor TEXT,reversed INT);";
                public static final String POSTS_TABLE_SQL = "CREATE TABLE  posts (_id INTEGER PRIMARY KEY,post_id INT NOT NULL,my_repost_id INT,tags BLOB,thumbnail_url TEXT,share_url TEXT,video_low_uRL TEXT,video_url TEXT,description TEXT,foursquare_venue_id TEXT,metadata_flags INT,post_flags INT,avatar_url TEXT,user_id INT,username TEXT,timestamp INT,location TEXT,venue_data BLOB,entities BLOB,likes_count INT,reviners_count INT,comments_count INT,user_background_color INT,last_refresh INT,loops INT,velocity INT,on_fire INT);";
                public static final String POST_COMMENTS_LIKES_VIEW_SQL = "CREATE VIEW  post_comments_likes_view AS  SELECT post_groups_view._id AS _id,post_groups_view.post_id AS post_id,post_groups_view.my_repost_id AS my_repost_id,post_groups_view.tags AS tags,post_groups_view.thumbnail_url AS thumbnail_url,post_groups_view.share_url AS share_url,post_groups_view.video_low_uRL AS video_low_uRL,post_groups_view.video_url AS video_url,post_groups_view.description AS description,post_groups_view.foursquare_venue_id AS foursquare_venue_id,post_groups_view.metadata_flags AS metadata_flags,post_groups_view.post_flags AS post_flags,post_groups_view.post_type AS post_type,post_groups_view.tag AS tag,post_groups_view.sort_id AS sort_id,post_groups_view.is_last AS is_last,post_groups_view.avatar_url AS avatar_url,post_groups_view.user_id AS user_id,post_groups_view.timestamp AS timestamp,post_groups_view.location AS location,post_groups_view.username AS username,post_groups_view.venue_data AS venue_data,post_groups_view.entities AS entities,post_groups_view.repost_data AS repost_data,post_groups_view.reposter_id AS reposter_id,post_groups_view.likes_count AS likes_count,post_groups_view.reviners_count AS reviners_count,post_groups_view.comments_count AS comments_count,post_groups_view.user_background_color AS user_background_color,post_groups_view.loops AS loops,post_groups_view.velocity AS velocity,post_groups_view.on_fire AS on_fire,post_groups_view.last_refresh AS last_refresh,likes.like_id AS like_id,likes.avatar_url AS like_user_avatar_url,likes.user_id AS like_user_user_id,likes.timestamp AS like_user_timestamp,likes.location AS like_user_location,likes.username AS like_user_username,likes.verified AS like_user_verified,null  AS comment_id,null  AS comment,null  AS comment_user_avatar_url,null  AS comment_user_user_id,null  AS comment_user_timestamp,null  AS comment_user_location,null  AS comment_user_username,null  AS comment_user_verified,null  AS comment_is_last,null  AS comment_entities FROM post_groups_view LEFT JOIN likes ON post_groups_view.post_id = likes.post_id UNION SELECT post_groups_view._id AS _id,post_groups_view.post_id AS post_id,post_groups_view.my_repost_id AS my_repost_id,post_groups_view.tags AS tags,post_groups_view.thumbnail_url AS thumbnail_url,post_groups_view.share_url AS share_url,post_groups_view.video_low_uRL AS video_low_uRL,post_groups_view.video_url AS video_url,post_groups_view.description AS description,post_groups_view.foursquare_venue_id AS foursquare_venue_id,post_groups_view.metadata_flags AS metadata_flags,post_groups_view.post_flags AS post_flags,post_groups_view.post_type AS post_type,post_groups_view.tag AS tag,post_groups_view.sort_id AS sort_id,post_groups_view.is_last AS is_last,post_groups_view.avatar_url AS avatar_url,post_groups_view.user_id AS user_id,post_groups_view.timestamp AS timestamp,post_groups_view.location AS location,post_groups_view.username AS username,post_groups_view.venue_data AS venue_data,post_groups_view.entities AS entities,post_groups_view.repost_data AS repost_data,post_groups_view.reposter_id AS reposter_id,post_groups_view.likes_count AS likes_count,post_groups_view.reviners_count AS reviners_count,post_groups_view.comments_count AS comments_count,post_groups_view.user_background_color AS user_background_color,post_groups_view.loops AS loops,post_groups_view.velocity AS velocity,post_groups_view.on_fire AS on_fire,post_groups_view.last_refresh AS last_refresh,null  AS like_id,null  AS like_user_avatar_url,null  AS like_user_user_id,null  AS like_user_timestamp,null  AS like_user_location,null  AS like_user_username,null  AS like_user_verified,comments.comment_id AS comment_id,comments.comment AS comment,comments.avatar_url AS comment_user_avatar_url,comments.user_id AS comment_user_user_id,comments.timestamp AS comment_user_timestamp,comments.location AS comment_user_location,comments.username AS comment_user_username,comments.verified AS comment_user_verified,comments.is_last AS comment_is_last,comments.entities AS comment_entities FROM post_groups_view LEFT JOIN comments ON post_groups_view.post_id = comments.post_id ";
                public static final String POST_GROUPS_TABLE_SQL = "CREATE TABLE  post_groups (_id INTEGER PRIMARY KEY,post_type INT,tag TEXT,post_id INT,is_last INT,repost_data BLOB,reposter_id INT,sort_id INT,g_flags INT);";
                public static final String POST_GROUPS_VIEW_SQL = "CREATE VIEW  post_groups_view AS SELECT post_groups._id AS _id,post_groups.post_type AS post_type,post_groups.tag AS tag,post_groups.post_id AS post_id,post_groups.is_last AS is_last,post_groups.g_flags AS g_flags,post_groups.sort_id AS sort_id,post_groups.repost_data AS repost_data,post_groups.reposter_id AS reposter_id,posts.username AS username,posts.avatar_url AS avatar_url,posts.thumbnail_url AS thumbnail_url,posts.metadata_flags AS metadata_flags,posts.user_id AS user_id,posts.my_repost_id AS my_repost_id,posts.timestamp AS timestamp,posts.location AS location,posts.tags AS tags,posts.share_url AS share_url,posts.video_low_uRL AS video_low_uRL,posts.video_url AS video_url,posts.video_low_uRL AS video_low_uRL,posts.description AS description,posts.foursquare_venue_id AS foursquare_venue_id,posts.post_flags AS post_flags,posts.venue_data AS venue_data,posts.entities AS entities,posts.likes_count AS likes_count,posts.reviners_count AS reviners_count,posts.comments_count AS comments_count,posts.user_background_color AS user_background_color,posts.loops AS loops,posts.velocity AS velocity,posts.on_fire AS on_fire,posts.last_refresh AS last_refresh FROM post_groups LEFT JOIN posts AS posts ON post_groups.post_id=posts.post_id;";
                public static final String SELECTION_COMMENTS_BY_POST_ID = "post_id=?";
                public static final String SELECTION_PAGE_CURSOR_KIND_TYPE_TAG = "kind=? AND type=? AND tag=?";
                public static final String SELECTION_POST_GROUP_TYPE = "post_type=?";
                public static final String SELECTION_POST_GROUP_TYPE_AND_TAG = "post_type=? AND tag=?";
                public static final String SELECTION_POST_ID = "post_id=?";
                public static final String SELECTION_POST_ID_AND_TYPE_AND_TAG = "post_id=? AND post_type=? AND tag=?";
                public static final String SELECTION_ROW_ID = "_id=?";
                public static final String SELECTION_USER_GROUP_TYPE = "type=?";
                public static final String SELECTION_USER_GROUP_TYPE_AND_TAG = "type=? AND tag=?";
                public static final String SELECTION_USER_ID = "user_id=?";
                public static final String SETTINGS_TABLE_SQL = "CREATE TABLE  settings (_id INTEGER PRIMARY KEY,name TEXT UNIQUE ON CONFLICT REPLACE,value TEXT);";
                public static final String TAGS_RECENTLY_USED_TABLE_SQL = "CREATE TABLE  tag_recently_used (_id INTEGER PRIMARY KEY,tag_id INT UNIQUE NOT NULL,tag_name TEXT NOT NULL,last_used_timestamp TEXT);";
                public static final String TAGS_TABLE_SQL = "CREATE TABLE  tag_search_results (_id INTEGER PRIMARY KEY,tag_id INT UNIQUE NOT NULL,tag_name TEXT NOT NULL,last_used_timestamp TEXT,is_last INT);";
                public static final String USERS_SECTION_TABLE_SQL = "CREATE TABLE  user_sections (_id INTEGER PRIMARY KEY,user_id INT, section_type INT, section_index INT, section_title TEXT, last_section_refresh INT);";
                public static final String USERS_TABLE_SQL = "CREATE TABLE  users (_id INTEGER PRIMARY KEY,user_id INT,avatar_url TEXT,blocked INT,blocking INT,hide_profile_reposts INT,description TEXT,location TEXT,explicit INT,external INT,follower_count INT,following_count INT,following_flag INT,like_count INT,post_count INT,username TEXT,email_address TEXT,phone_number TEXT,verified INT,follow_status INT,last_refresh INT,accepts_oon_conversations INT,profile_background INT, loop_count INT);";
                public static final String USER_GROUPS_TABLE_SQL = "CREATE TABLE  user_groups (_id INTEGER PRIMARY KEY,type INT,tag TEXT,user_id INT,is_last INT,g_flags INT,order_id INT);";
                public static final String USER_GROUPS_VIEW_SQL = "CREATE VIEW  user_groups_view AS SELECT user_groups._id AS _id,user_groups.type AS type,user_groups.tag AS tag,user_groups.user_id AS user_id,user_groups.is_last AS is_last,user_groups.g_flags AS g_flags,user_groups.order_id AS order_id,user.username AS username,user.user_id AS user_id,user.avatar_url AS avatar_url,user.blocked AS blocked,user.blocking AS blocking,user.description AS description,user.location AS location,user.explicit AS explicit,user.follower_count AS follower_count,user.following_count AS following_count,user.following_flag AS following_flag,user.like_count AS like_count,user.post_count AS post_count,user.follow_status AS follow_status,user.verified AS verified,user.accepts_oon_conversations AS accepts_oon_conversations,user.profile_background AS profile_background, user.loop_count AS loop_count, section.last_section_refresh AS last_section_refresh,section.section_index AS section_index,section.section_title AS section_title,section.section_type AS section_type FROM user_groups LEFT JOIN users AS user ON user_groups.user_id=user.user_id LEFT JOIN user_sections as section ON section.user_id=user.user_id ;";

                public static final class ChannelsQuery {
                    public static final int INDEX_BACKGROUND_COLOR = 4;
                    public static final int INDEX_CHANNEL = 2;
                    public static final int INDEX_CHANNEL_ID = 1;
                    public static final int INDEX_EXPLORE_RETINA_ICON_FULL_URL = 11;
                    public static final int INDEX_FOLLOWING = 10;
                    public static final int INDEX_FONT_COLOR = 5;
                    public static final int INDEX_ICON_FULL_URL = 8;
                    public static final int INDEX_ID = 0;
                    public static final int INDEX_IS_LAST = 7;
                    public static final int INDEX_LAST_USED_TIMESTAMP = 6;
                    public static final int INDEX_RETINA_ICON_FULL_URL = 9;
                    public static final int INDEX_TIMESTAMP = 3;
                    public static final String[] PROJECTION = {"_id", "channel_id", "channel", "timestamp", Vine.ChannelsColumns.BACKGROUND_COLOR, Vine.ChannelsColumns.FONT_COLOR, "last_used_timestamp", "is_last", Vine.ChannelsColumns.ICON_FULL_URL, Vine.ChannelsColumns.RETINA_ICON_FULL_URL, "following", Vine.ChannelsColumns.INDEX_EXPLORE_RETINA_ICON_FULL_URL};
                }

                public static final class CommentsQuery {
                    public static final int INDEX_AVATAR_URL = 3;
                    public static final int INDEX_COMMENT = 9;
                    public static final int INDEX_COMMENT_ID = 1;
                    public static final int INDEX_ENTITIES = 10;
                    public static final int INDEX_ID = 0;
                    public static final int INDEX_IS_LAST = 11;
                    public static final int INDEX_LOCATION = 7;
                    public static final int INDEX_POST_ID = 2;
                    public static final int INDEX_TIMESTAMP = 6;
                    public static final int INDEX_USERNAME = 5;
                    public static final int INDEX_USER_ID = 4;
                    public static final int INDEX_VERIFIED = 8;
                    public static final String[] PROJECTION = {"_id", "comment_id", "post_id", "avatar_url", "user_id", "username", "timestamp", "location", "verified", "comment", "entities", "is_last"};
                }

                public static final class ConversationMessageUsersQuery {
                    public static final int INDEX_AUTHOR_AVATAR = 28;
                    public static final int INDEX_AUTHOR_USER_ID = 26;
                    public static final int INDEX_AUTHOR_USER_NAME = 27;
                    public static final int INDEX_CONVERSATION_ID = 19;
                    public static final int INDEX_CONVERSATION_ROW_ID = 1;
                    public static final int INDEX_DELETED = 14;
                    public static final int INDEX_EPHEMERAL = 12;
                    public static final int INDEX_ERROR_CODE = 17;
                    public static final int INDEX_ERROR_MESSAGE = 18;
                    public static final int INDEX_HIDDEN = 22;
                    public static final int INDEX_ID = 0;
                    public static final int INDEX_IS_LAST = 8;
                    public static final int INDEX_LOCAL_START_TIME = 13;
                    public static final int INDEX_MAX_LOOPS = 10;
                    public static final int INDEX_MESSAGE = 5;
                    public static final int INDEX_MESSAGE_ID = 2;
                    public static final int INDEX_NETWORK_TYPE = 20;
                    public static final int INDEX_POST_DESCRIPTION = 30;
                    public static final int INDEX_POST_ENTITIES = 29;
                    public static final int INDEX_POST_ID = 15;
                    public static final int INDEX_POST_SHARE_URL = 31;
                    public static final int INDEX_SENDER_AVATAR_URL = 24;
                    public static final int INDEX_SENDER_PROFILE_BACKGROUND = 25;
                    public static final int INDEX_SENDER_USER_NAME = 23;
                    public static final int INDEX_THUMBNAIL_URL = 7;
                    public static final int INDEX_TIMESTAMP = 4;
                    public static final int INDEX_UNREAD_COUNT = 21;
                    public static final int INDEX_UPLOAD_PATH = 16;
                    public static final int INDEX_USER_ID = 3;
                    public static final int INDEX_VANISHED = 9;
                    public static final int INDEX_VANISHED_DATE = 11;
                    public static final int INDEX_VIDEO_URL = 6;
                    public static final String[] PROJECTION = {"_id", "conversation_row_id", "message_id", "user_row_id", "timestamp", "message", "video_url", "thumbnail_url", "is_last", Vine.MessagesColumns.VANISHED, "max_loops", Vine.MessagesColumns.VANISHED_DATE, Vine.MessagesColumns.EPHEMERAL, Vine.MessagesColumns.LOCAL_START_TIME, Vine.MessagesColumns.DELETED, "post_id", "upload_path", "error_code", Vine.MessagesColumns.ERROR_REASON, "conversation_id", "network_type", Vine.ConversationsColumns.UNREAD_MESSAGE_COUNT, Vine.ConversationsColumns.IS_HIDDEN, Vine.ConversationMessageUsersView.SENDER_USER_NAME, Vine.ConversationMessageUsersView.SENDER_AVATAR, Vine.ConversationMessageUsersView.SENDER_PROFILE_BACKGROUND, Vine.ConversationMessageUsersView.AUTHOR_USER_ID, Vine.ConversationMessageUsersView.AUTHOR_USER_NAME, Vine.ConversationMessageUsersView.AUTHOR_AVATAR, Vine.ConversationMessageUsersView.POST_ENTITIES, Vine.ConversationMessageUsersView.POST_DESCRIPTION, Vine.ConversationMessageUsersView.POST_SHARE_URL};
                }

                public static final class ConversationRecipientsQuery {
                    public static final int INDEX_CONVERSATION_ROW_ID = 1;
                    public static final int INDEX_ID = 0;
                    public static final int INDEX_USER_ROW_ID = 2;
                    public static final String[] PROJECTION = {"_id", "conversation_row_id", "user_row_id"};
                }

                public static final class ConversationRecipientsUsersViewQuery {
                    public static final int INDEX_CONVERSATION_ROW_ID = 1;
                    public static final int INDEX_EMAIL = 5;
                    public static final int INDEX_ID = 0;
                    public static final int INDEX_PHONE_NUMBER = 4;
                    public static final int INDEX_USERNAME = 3;
                    public static final int INDEX_USERS_PROFILE_BACKGROUND = 7;
                    public static final int INDEX_USER_ID = 6;
                    public static final int INDEX_USER_ROW_ID = 2;
                    public static final String[] PROJECTION = {"_id", "conversation_row_id", "user_row_id", "username", "phone_number", Vine.UserColumns.EMAIL_ADDRESS, "user_id", "profile_background"};
                }

                public static final class EditionsQuery {
                    public static final int INDEX_EDITION_CODE = 1;
                    public static final int INDEX_EDITION_NAME = 2;
                    public static final int INDEX_ID = 0;
                    public static final String[] PROJECTION = {"_id", Vine.Editions.EDITION_CODE, Vine.Editions.EDITION_NAME};
                }

                public static final class InboxQuery {
                    public static final int INBOX_FOLLOWING_FLAG = 11;
                    public static final int INDEX_AVATAR_URL = 12;
                    public static final int INDEX_CONVERSATION_ROW_ID = 1;
                    public static final int INDEX_ERROR = 15;
                    public static final int INDEX_ID = 0;
                    public static final int INDEX_IS_HIDDEN = 5;
                    public static final int INDEX_IS_LAST = 7;
                    public static final int INDEX_LAST_MESSAGE_ROW_ID = 6;
                    public static final int INDEX_NETWORK_TYPE = 3;
                    public static final int INDEX_PROFILE_BACKGROUND = 13;
                    public static final int INDEX_RECIPIENTS_COUNT = 14;
                    public static final int INDEX_TIMESTAMP = 2;
                    public static final int INDEX_UNREAD_COUNT = 4;
                    public static final int INDEX_USERNAME = 8;
                    public static final int INDEX_USER_ID = 10;
                    public static final int INDEX_USER_IS_EXTERNAL = 16;
                    public static final int INDEX_USER_ROW_ID = 9;
                    public static final String[] PROJECTION = {"_id", "conversation_row_id", "timestamp", "network_type", Vine.ConversationsColumns.UNREAD_MESSAGE_COUNT, Vine.ConversationsColumns.IS_HIDDEN, Vine.ConversationsColumns.LAST_MESSAGE_ROW_ID, "is_last", "username", "user_row_id", "user_id", "following_flag", "avatar_url", "profile_background", Vine.InboxView.RECIPIENTS_COUNT, Vine.MessagesColumns.ERROR_REASON, Vine.InboxView.USER_IS_EXTERNAL};
                }

                public static final class LikesQuery {
                    public static final int INDEX_AVATAR_URL = 3;
                    public static final int INDEX_ID = 0;
                    public static final int INDEX_LIKE_ID = 1;
                    public static final int INDEX_LOCATION = 7;
                    public static final int INDEX_POST_ID = 2;
                    public static final int INDEX_TIMESTAMP = 6;
                    public static final int INDEX_USERNAME = 5;
                    public static final int INDEX_USER_ID = 4;
                    public static final String[] PROJECTION = {"_id", Vine.LikesColumns.LIKE_ID, "post_id", "avatar_url", "user_id", "username", "timestamp", "location", "verified"};
                }

                public static final class MessagesQuery {
                    public static final int INDEX_CONVERSATION_ROW_ID = 1;
                    public static final int INDEX_DELETED = 14;
                    public static final int INDEX_EPHEMERAL = 12;
                    public static final int INDEX_ERROR_CODE = 17;
                    public static final int INDEX_ERROR_REASON = 18;
                    public static final int INDEX_ID = 0;
                    public static final int INDEX_IS_LAST = 8;
                    public static final int INDEX_LOCAL_START_TIME = 13;
                    public static final int INDEX_MAX_LOOPS = 10;
                    public static final int INDEX_MESSAGE = 5;
                    public static final int INDEX_MESSAGE_ID = 2;
                    public static final int INDEX_POST_ID = 15;
                    public static final int INDEX_THUMBNAIL_URL = 7;
                    public static final int INDEX_TIMESTAMP = 4;
                    public static final int INDEX_UPLOAD_PATH = 16;
                    public static final int INDEX_USER_ID = 3;
                    public static final int INDEX_VANISHED = 9;
                    public static final int INDEX_VANISHED_DATE = 11;
                    public static final int INDEX_VIDEO_URL = 6;
                    public static final String[] PROJECTION = {"_id", "conversation_row_id", "message_id", "user_row_id", "timestamp", "message", "video_url", "thumbnail_url", "is_last", Vine.MessagesColumns.VANISHED, "max_loops", Vine.MessagesColumns.VANISHED_DATE, Vine.MessagesColumns.EPHEMERAL, Vine.MessagesColumns.LOCAL_START_TIME, Vine.MessagesColumns.DELETED, "post_id", "upload_path", "error_code", Vine.MessagesColumns.ERROR_REASON};
                }

                public static final class NotificationsQuery {
                    public static final int INDEX_AVATAR_URL = 6;
                    public static final int INDEX_CLEARED = 4;
                    public static final int INDEX_CONVERSATION_ROW_ID = 5;
                    public static final int INDEX_ID = 0;
                    public static final int INDEX_MESSAGE = 3;
                    public static final int INDEX_NOTIFICATION_ID = 1;
                    public static final int INDEX_TYPE = 2;
                    public static final String[] PROJECTION = {"_id", "notification_id", Vine.NotificationsColumns.NOTIFICATION_TYPE, "message", Vine.NotificationsColumns.CLEARED, "conversation_row_id", "avatar_url"};
                }

                public static final class PageCursorsQuery {
                    public static final int INDEX_ANCHOR = 6;
                    public static final int INDEX_ID = 0;
                    public static final int INDEX_KIND = 3;
                    public static final int INDEX_NEXT_PAGE = 4;
                    public static final int INDEX_PREVIOUS_PAGE = 5;
                    public static final int INDEX_REVERSED = 7;
                    public static final int INDEX_TAG = 2;
                    public static final int INDEX_TYPE = 1;
                    public static final String[] PROJECTION = {"_id", "type", "tag", Vine.PageCursors.KIND, "next_page", "previous_page", "anchor", Vine.PageCursors.REVERSED};
                }

                public static final class PostGroupsQuery {
                    public static final int INDEX_POST_ID = 0;
                    public static final int INDEX_SORT_ID = 3;
                    public static final int INDEX_TAG = 2;
                    public static final int INDEX_TYPE = 1;
                    public static final String[] PROJECTION = {"post_id", Vine.PostGroupColumns.TYPE, "tag", Vine.PostGroupColumns.SORT_ID};
                }

                public static final class PostGroupsViewQuery {
                    public static final int INDEX_AVATAR_URL = 15;
                    public static final int INDEX_COMMENTS_COUNT = 26;
                    public static final int INDEX_DESCRIPTION = 8;
                    public static final int INDEX_ENTITIES = 21;
                    public static final int INDEX_FOURSQUARE_VENUE_ID = 9;
                    public static final int INDEX_ID = 0;
                    public static final int INDEX_IS_LAST = 14;
                    public static final int INDEX_LAST_REFRESH = 31;
                    public static final int INDEX_LIKES_COUNT = 24;
                    public static final int INDEX_LOCATION = 18;
                    public static final int INDEX_LOOPS = 28;
                    public static final int INDEX_METADATA_FLAGS = 10;
                    public static final int INDEX_MY_REPOST_ID = 2;
                    public static final int INDEX_ON_FIRE = 30;
                    public static final int INDEX_POST_FLAGS = 11;
                    public static final int INDEX_POST_ID = 1;
                    public static final int INDEX_REPOSTER_ID = 23;
                    public static final int INDEX_REPOST_DATA = 22;
                    public static final int INDEX_REVINERS_COUNT = 25;
                    public static final int INDEX_SHARE_URL = 5;
                    public static final int INDEX_SORT_ID = 32;
                    public static final int INDEX_TAG = 13;
                    public static final int INDEX_TAGS = 3;
                    public static final int INDEX_THUMBNAIL_URL = 4;
                    public static final int INDEX_TIMESTAMP = 17;
                    public static final int INDEX_TYPE = 12;
                    public static final int INDEX_USERNAME = 19;
                    public static final int INDEX_USER_BACKGROUND_COLOR = 27;
                    public static final int INDEX_USER_ID = 16;
                    public static final int INDEX_VELOCITY = 29;
                    public static final int INDEX_VENUE_DATA = 20;
                    public static final int INDEX_VIDEO_LOW_URL = 6;
                    public static final int INDEX_VIDEO_URL = 7;
                    public static final String[] PROJECTION = {"_id", "post_id", "my_repost_id", "tags", "thumbnail_url", "share_url", Vine.PostColumns.VIDEO_LOW_URL, "video_url", "description", Vine.PostColumns.FOURSQUARE_VENUE_ID, Vine.PostColumns.METADATA_FLAGS, Vine.PostColumns.POST_FLAGS, Vine.PostGroupColumns.TYPE, "tag", "is_last", "avatar_url", "user_id", "timestamp", "location", "username", Vine.PostColumns.VENUE_DATA, "entities", Vine.PostGroupColumns.REPOST_DATA, Vine.PostGroupColumns.REPOSTER_ID, Vine.PostColumns.LIKES_COUNT, Vine.PostColumns.REVINERS_COUNT, Vine.PostColumns.COMMENTS_COUNT, Vine.PostColumns.USER_BACKGROUND_COLOR, "loops", Vine.PostColumns.VELOCITY, Vine.PostColumns.ON_FIRE, "last_refresh", Vine.PostGroupColumns.SORT_ID};
                }

                public static final class PostsQuery {
                    public static final int INDEX_AVATAR_URL = 11;
                    public static final int INDEX_COMMENTS_COUNT = 18;
                    public static final int INDEX_DESCRIPTION = 7;
                    public static final int INDEX_FOURSQUARE_VENUE_ID = 8;
                    public static final int INDEX_ID = 0;
                    public static final int INDEX_LAST_REFRESH = 21;
                    public static final int INDEX_LIKES_COUNT = 16;
                    public static final int INDEX_LOCATION = 15;
                    public static final int INDEX_LOOPS = 22;
                    public static final int INDEX_METADATA_FLAGS = 9;
                    public static final int INDEX_ON_FIRE = 24;
                    public static final int INDEX_POST_FLAGS = 10;
                    public static final int INDEX_POST_ID = 1;
                    public static final int INDEX_REVINERS_COUNT = 17;
                    public static final int INDEX_SHARE_URL = 4;
                    public static final int INDEX_TAGS = 2;
                    public static final int INDEX_THUMBNAIL_URL = 3;
                    public static final int INDEX_TIMESTAMP = 14;
                    public static final int INDEX_USERNAME = 13;
                    public static final int INDEX_USER_BACKGROUND_COLOR = 19;
                    public static final int INDEX_USER_ID = 12;
                    public static final int INDEX_VELOCITY = 23;
                    public static final int INDEX_VENUE_DATA = 20;
                    public static final int INDEX_VIDEO_LOW_URL = 5;
                    public static final int INDEX_VIDEO_URL = 6;
                    public static final String[] PROJECTION = {"_id", "post_id", "tags", "thumbnail_url", "share_url", Vine.PostColumns.VIDEO_LOW_URL, "video_url", "description", Vine.PostColumns.FOURSQUARE_VENUE_ID, Vine.PostColumns.METADATA_FLAGS, Vine.PostColumns.POST_FLAGS, "avatar_url", "user_id", "username", "timestamp", "location", Vine.PostColumns.LIKES_COUNT, Vine.PostColumns.REVINERS_COUNT, Vine.PostColumns.COMMENTS_COUNT, Vine.PostColumns.USER_BACKGROUND_COLOR, Vine.PostColumns.VENUE_DATA, "last_refresh", "loops", Vine.PostColumns.VELOCITY, Vine.PostColumns.ON_FIRE};
                }

                public static final class TableQuery {
                    public static final int INDEX_ID = 0;
                    public static final String[] PROJECTION = {"_id"};
                }

                public static final class TagsQuery {
                    public static final int INDEX_ID = 0;
                    public static final int INDEX_IS_LAST = 4;
                    public static final int INDEX_LAST_USED_TIMESTAMP = 3;
                    public static final int INDEX_TAG_ID = 1;
                    public static final int INDEX_TAG_NAME = 2;
                    public static final String[] PROJECTION = {"_id", Vine.TagsColumns.TAG_ID, "tag_name", "last_used_timestamp", "is_last"};
                }

                public static final class TagsRecentlyUsedQuery {
                    public static final int INDEX_ID = 0;
                    public static final int INDEX_LAST_USED_TIMESTAMP = 3;
                    public static final int INDEX_TAG_ID = 1;
                    public static final int INDEX_TAG_NAME = 2;
                    public static final String[] PROJECTION = {"_id", Vine.TagsColumns.TAG_ID, "tag_name", "last_used_timestamp"};
                }

                public static final class UserGroupsQuery {
                    public static final int INDEX_USER_ID = 0;
                    public static final String[] PROJECTION = {"user_id"};
                }

       public static final class UsersQuery {
                    public static final int INDEX_ACCEPT_OOM_CONVERSATIONS = 18;
                    public static final int INDEX_AVATAR_URL = 2;
                    public static final int INDEX_BLOCKED = 3;
                    public static final int INDEX_BLOCKING = 4;
                    public static final int INDEX_DESCRIPTION = 5;
                    public static final int INDEX_EXPLICIT = 7;
                    public static final int INDEX_FOLLOWER_COUNT = 8;
                    public static final int INDEX_FOLLOWING_COUNT = 9;
                    public static final int INDEX_FOLLOW_STATUS = 15;
                    public static final int INDEX_FRIENDSHIP = 10;
                    public static final int INDEX_ID = 0;
                    public static final int INDEX_IS_LAST = 17;
                    public static final int INDEX_LAST_SECTION_REFRESH = 23;
                    public static final int INDEX_LIKE_COUNT = 11;
                    public static final int INDEX_LOCATION = 6;
                    public static final int INDEX_LOOP_COUNT = 25;
                    public static final int INDEX_ORDER = 16;
                    public static final int INDEX_POST_COUNT = 12;
                    public static final int INDEX_PROFILE_BACKGROUND = 19;
                    public static final int INDEX_SECTION_INDEX = 20;
                    public static final int INDEX_SECTION_TITLE = 22;
                    public static final int INDEX_SECTION_TYPE = 21;
                    public static final int INDEX_TYPE = 24;
                    public static final int INDEX_USERNAME = 13;
                    public static final int INDEX_USER_ID = 1;
                    public static final int INDEX_VERIFIED = 14;
                    public static final String[] PROJECTION = {"_id", "user_id", "avatar_url", "blocked", Vine.UserColumns.BLOCKING, "description", "location", "explicit", Vine.UserColumns.FOLLOWER_COUNT, Vine.UserColumns.FOLLOWING_COUNT, "following_flag", Vine.UserColumns.LIKE_COUNT, Vine.UserColumns.POST_COUNT, "username", "verified", Vine.UserColumns.FOLLOW_STATUS, Vine.UserGroupColumns.ORDER_ID, "is_last", Vine.UserColumns.ACCEPTS_OON_CONVERSATIONS, "profile_background", Vine.UserSections.SECTION_INDEX, Vine.UserSections.SECTION_TYPE, Vine.UserSections.SECTION_TITLE, Vine.UserSections.LAST_SECTION_REFRESH, "type", Vine.UserColumns.LOOP_COUNT};

           public static VineRecipient getVineRecipient(Cursor cursor) {
               VineRecipient vr = VineRecipient.fromUser(cursor.getString(13), cursor.getLong(1), cursor.getInt(19), -1L);
               vr.avatarUrl = cursor.getString(2);
               vr.lastFriendRefresh = cursor.getLong(23);
               vr.sectionTitle = cursor.getString(22);
               vr.friendIndex = cursor.getLong(20);
               vr.sectionIndex = (int) (vr.friendIndex >> 32);
               return vr;
                    }
                }

       public static final class PostCommentsLikesQuery {
                    public static final int INDEX_AVATAR_URL = 15;
                    public static final int INDEX_COMMENT = 35;
                    public static final int INDEX_COMMENTS_COUNT = 26;
                    public static final int INDEX_COMMENT_ENTITIES = 43;
                    public static final int INDEX_COMMENT_ID = 34;
                    public static final int INDEX_COMMENT_IS_LAST = 42;
                    public static final int INDEX_COMMENT_USER_AVATAR_URL = 36;
                    public static final int INDEX_COMMENT_USER_LOCATION = 39;
                    public static final int INDEX_COMMENT_USER_TIMESTAMP = 38;
                    public static final int INDEX_COMMENT_USER_USERNAME = 40;
                    public static final int INDEX_COMMENT_USER_USER_ID = 37;
                    public static final int INDEX_COMMENT_USER_VERIFIED = 41;
                    public static final int INDEX_DESCRIPTION = 8;
                    public static final int INDEX_ENTITIES = 21;
                    public static final int INDEX_FOURSQUARE_VENUE_ID = 9;
                    public static final int INDEX_ID = 0;
                    public static final int INDEX_IS_LAST = 14;
                    public static final int INDEX_LIKES_COUNT = 24;
                    public static final int INDEX_LIKE_ID = 27;
                    public static final int INDEX_LIKE_USER_AVATAR_URL = 28;
                    public static final int INDEX_LIKE_USER_LOCATION = 31;
                    public static final int INDEX_LIKE_USER_TIMESTAMP = 30;
                    public static final int INDEX_LIKE_USER_USERNAME = 32;
                    public static final int INDEX_LIKE_USER_USER_ID = 29;
                    public static final int INDEX_LIKE_USER_VERIFIED = 33;
                    public static final int INDEX_LOCATION = 18;
                    public static final int INDEX_LOOPS = 45;
                    public static final int INDEX_METADATA_FLAGS = 10;
                    public static final int INDEX_MY_REPOST_ID = 2;
                    public static final int INDEX_ON_FIRE = 47;
                    public static final int INDEX_POST_FLAGS = 11;
                    public static final int INDEX_POST_ID = 1;
                    public static final int INDEX_POST_LAST_REFRESH = 48;
                    public static final int INDEX_REPOSTER_ID = 23;
                    public static final int INDEX_REPOST_DATA = 22;
                    public static final int INDEX_REVINERS_COUNT = 25;
                    public static final int INDEX_SHARE_URL = 5;
                    public static final int INDEX_SORT_ID = 49;
                    public static final int INDEX_TAG = 13;
                    public static final int INDEX_TAGS = 3;
                    public static final int INDEX_THUMBNAIL_URL = 4;
                    public static final int INDEX_TIMESTAMP = 17;
                    public static final int INDEX_TYPE = 12;
                    public static final int INDEX_USERNAME = 19;
                    public static final int INDEX_USER_BACKGROUND_COLOR = 44;
                    public static final int INDEX_USER_ID = 16;
                    public static final int INDEX_VELOCITY = 46;
                    public static final int INDEX_VENUE_DATA = 20;
                    public static final int INDEX_VIDEO_LOW_URL = 6;
                    public static final int INDEX_VIDEO_URL = 7;
                    public static final String[] PROJECTION = {"_id", "post_id", "my_repost_id", "tags", "thumbnail_url", "share_url", Vine.PostColumns.VIDEO_LOW_URL, "video_url", "description", Vine.PostColumns.FOURSQUARE_VENUE_ID, Vine.PostColumns.METADATA_FLAGS, Vine.PostColumns.POST_FLAGS, Vine.PostGroupColumns.TYPE, "tag", "is_last", "avatar_url", "user_id", "timestamp", "location", "username", Vine.PostColumns.VENUE_DATA, "entities", Vine.PostGroupColumns.REPOST_DATA, Vine.PostGroupColumns.REPOSTER_ID, Vine.PostColumns.LIKES_COUNT, Vine.PostColumns.REVINERS_COUNT, Vine.PostColumns.COMMENTS_COUNT, Vine.LikesColumns.LIKE_ID, Vine.PostCommentsLikesView.LIKE_USER_AVATAR_URL, Vine.PostCommentsLikesView.LIKE_USER_USER_ID, Vine.PostCommentsLikesView.LIKE_USER_TIMESTAMP, Vine.PostCommentsLikesView.LIKE_USER_LOCATION, Vine.PostCommentsLikesView.LIKE_USER_USERNAME, Vine.PostCommentsLikesView.LIKE_USER_VERIFIED, "comment_id", "comment", Vine.PostCommentsLikesView.COMMENT_USER_AVATAR_URL, Vine.PostCommentsLikesView.COMMENT_USER_USER_ID, Vine.PostCommentsLikesView.COMMENT_USER_TIMESTAMP, Vine.PostCommentsLikesView.COMMENT_USER_LOCATION, Vine.PostCommentsLikesView.COMMENT_USER_USERNAME, Vine.PostCommentsLikesView.COMMENT_USER_VERIFIED, Vine.PostCommentsLikesView.COMMENT_IS_LAST, Vine.PostCommentsLikesView.COMMENT_ENTITIES, Vine.PostColumns.USER_BACKGROUND_COLOR, "loops", Vine.PostColumns.VELOCITY, Vine.PostColumns.ON_FIRE, "last_refresh", Vine.PostGroupColumns.SORT_ID};
                    public static final HashMap<String, Integer> LOOKUP = new HashMap<>(43);

                    static {
               LOOKUP.put("_id", 0);
               LOOKUP.put("post_id", 1);
               LOOKUP.put("my_repost_id", 2);
               LOOKUP.put("tags", 3);
               LOOKUP.put("thumbnail_url", 4);
               LOOKUP.put("share_url", 5);
               LOOKUP.put(Vine.PostColumns.VIDEO_LOW_URL, 6);
               LOOKUP.put("video_url", 7);
               LOOKUP.put("description", 8);
               LOOKUP.put(Vine.PostColumns.FOURSQUARE_VENUE_ID, 9);
               LOOKUP.put(Vine.PostColumns.METADATA_FLAGS, 10);
               LOOKUP.put(Vine.PostColumns.POST_FLAGS, 11);
               LOOKUP.put(Vine.PostGroupColumns.TYPE, 12);
               LOOKUP.put("tag", 13);
               LOOKUP.put(Vine.PostGroupColumns.SORT_ID, 49);
               LOOKUP.put("is_last", 14);
               LOOKUP.put("avatar_url", 15);
               LOOKUP.put("user_id", 16);
               LOOKUP.put("timestamp", 17);
               LOOKUP.put("location", 18);
               LOOKUP.put("username", 19);
               LOOKUP.put(Vine.PostColumns.VENUE_DATA, 20);
               LOOKUP.put("entities", 21);
               LOOKUP.put(Vine.PostGroupColumns.REPOST_DATA, 22);
               LOOKUP.put(Vine.PostGroupColumns.REPOSTER_ID, 23);
               LOOKUP.put(Vine.PostColumns.LIKES_COUNT, 24);
               LOOKUP.put(Vine.PostColumns.REVINERS_COUNT, 25);
               LOOKUP.put(Vine.PostColumns.COMMENTS_COUNT, 26);
               LOOKUP.put(Vine.LikesColumns.LIKE_ID, 27);
               LOOKUP.put(Vine.PostCommentsLikesView.LIKE_USER_AVATAR_URL, 28);
               LOOKUP.put(Vine.PostCommentsLikesView.LIKE_USER_USER_ID, 29);
               LOOKUP.put(Vine.PostCommentsLikesView.LIKE_USER_TIMESTAMP, 30);
               LOOKUP.put(Vine.PostCommentsLikesView.LIKE_USER_LOCATION, 31);
               LOOKUP.put(Vine.PostCommentsLikesView.LIKE_USER_USERNAME, 32);
               LOOKUP.put(Vine.PostCommentsLikesView.LIKE_USER_VERIFIED, 33);
               LOOKUP.put("comment_id", 34);
               LOOKUP.put("comment", 35);
               LOOKUP.put(Vine.PostCommentsLikesView.COMMENT_USER_AVATAR_URL, 36);
               LOOKUP.put(Vine.PostCommentsLikesView.COMMENT_USER_USER_ID, 37);
               LOOKUP.put(Vine.PostCommentsLikesView.COMMENT_USER_TIMESTAMP, 38);
               LOOKUP.put(Vine.PostCommentsLikesView.COMMENT_USER_LOCATION, 39);
               LOOKUP.put(Vine.PostCommentsLikesView.COMMENT_USER_USERNAME, 40);
               LOOKUP.put(Vine.PostCommentsLikesView.COMMENT_USER_VERIFIED, 41);
               LOOKUP.put(Vine.PostCommentsLikesView.COMMENT_IS_LAST, 42);
               LOOKUP.put(Vine.PostCommentsLikesView.COMMENT_ENTITIES, 43);
               LOOKUP.put(Vine.PostColumns.USER_BACKGROUND_COLOR, 44);
               LOOKUP.put("loops", 45);
               LOOKUP.put(Vine.PostColumns.VELOCITY, 46);
               LOOKUP.put(Vine.PostColumns.ON_FIRE, 47);
               LOOKUP.put("last_refresh", 48);
                    }
                }
            }
