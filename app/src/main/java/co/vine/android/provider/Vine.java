            package co.vine.android.provider;

            import android.content.Context;
            import android.net.Uri;
            import android.provider.BaseColumns;
            import co.vine.android.util.CrossConstants;
            import co.vine.android.util.StringAnchorManager;
            import java.util.HashMap;
            
            
    public class Vine {
                public static final int LIMIT_DEFAULT = 20;
                public static final String QUERY_PARAMETER_DB_OWNER_ID = "db_owner_id";
                public static final String QUERY_PARAMETER_LIMIT = "limit";
                public static String AUTHORITY = CrossConstants.getAuthority(".provider.VineProvider");
                public static final String CONTENT_AUTHORITY = "content://" + AUTHORITY + "/";
                private static final HashMap<String, StringAnchorManager> sUserSectionAnchorManager = new HashMap<>();

                public interface BasePostColumns {
                    public static final String AVATAR_URL = "avatar_url";
                    public static final String ENTITIES = "entities";
                    public static final String FOLLOWING = "following";
                    public static final String IS_LAST = "is_last";
                    public static final String LOCATION = "location";
                    public static final String MY_REPOST_ID = "my_repost_id";
                    public static final String POST_ID = "post_id";
                    public static final String TIMESTAMP = "timestamp";
                    public static final String USERNAME = "username";
                    public static final String USER_ID = "user_id";
                }

                public static final class Channels implements BaseColumns, BasePostColumns, ChannelsColumns {
                    public static final Uri CONTENT_URI = Uri.parse(Vine.CONTENT_AUTHORITY + "channels");
                    public static final String SORT_ORDER_DEFAULT = "channel_id ASC";
                    public static final String SORT_ORDER_IS_LAST = "channel_id DESC";
                    public static final String SORT_ORDER_LAST_USED = "last_used_timestamp DESC, channel_id ASC";
                    public static final String TABLE_NAME = "channels";
                }

                public interface ChannelsColumns {
                    public static final String BACKGROUND_COLOR = "background_color";
                    public static final String CHANNEL = "channel";
                    public static final String CHANNEL_ID = "channel_id";
                    public static final String FONT_COLOR = "font_color";
                    public static final String ICON_FULL_URL = "icon_full_url";
                    public static final String INDEX_EXPLORE_RETINA_ICON_FULL_URL = "retina_explore_icon_full_url";
                    public static final String LAST_USED_TIMESTAMP = "last_used_timestamp";
                    public static final String RETINA_ICON_FULL_URL = "retina_icon_full_url";
                }

                public static final class Comments implements BaseColumns, CommentsColumns, BasePostColumns {
                    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.vine.android.comment";
                    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.vine.android.comments";
                    public static final Uri CONTENT_URI = Uri.parse(Vine.CONTENT_AUTHORITY + "comments");
                    public static final int LIMIT_DEFAULT = 800;
                    public static final String SORT_ORDER_DEFAULT = "comment_id ASC";
                    public static final String SORT_ORDER_LAST_REFRESH = "last_refresh DESC";
                    public static final String SORT_ORDER_REVERSE = "comment_id DESC";
                    public static final String TABLE_NAME = "comments";
                    public static final String VERIFIED = "verified";
                }

                public interface CommentsColumns {
                    public static final String COMMENT = "comment";
                    public static final String COMMENT_ID = "comment_id";
                    public static final String LAST_REFRESH = "last_refresh";
                }

                public static final class ConversationMessageUsersView implements BaseColumns, ConversationsColumns, MessagesColumns {
                    public static final String AUTHOR_AVATAR = "author_avatar";
                    public static final String AUTHOR_USER_ID = "author_user_id";
                    public static final String AUTHOR_USER_NAME = "author_user_name";
                    public static final String POST_DESCRIPTION = "post_description";
                    public static final String POST_ENTITIES = "post_entities";
                    public static final String POST_SHARE_URL = "post_share_url";
                    public static final String SENDER_AVATAR = "sender_avatar";
                    public static final String SENDER_PROFILE_BACKGROUND = "sender_profile_background";
                    public static final String SENDER_USER_NAME = "sender_user_name";
                    public static final String SORT_ORDER_DEFAULT = "timestamp ASC";
                    public static final String TABLE_NAME = "message_users_view";
                    public static final Uri CONTENT_URI = Uri.parse(Vine.CONTENT_AUTHORITY + TABLE_NAME);
                    public static final Uri CONTENT_URI_CONVERSATION = Uri.parse(Vine.CONTENT_AUTHORITY + TABLE_NAME + "/conversation");
                }

                public static final class ConversationRecipients implements BaseColumns {
                    public static final String CONVERSATION_ROW_ID = "conversation_row_id";
                    public static final String SORT_ORDER_DEFAULT = "conversation_row_id DESC";
                    public static final String USER_ROW_ID = "user_row_id";
                    public static final String TABLE_NAME = "conversation_recipients";
                    public static final Uri CONTENT_URI = Uri.parse(Vine.CONTENT_AUTHORITY + TABLE_NAME);
                    public static final Uri CONTENT_URI_CONVERSATION = Uri.parse(Vine.CONTENT_AUTHORITY + TABLE_NAME + "/conversation");
                }

                public static final class ConversationRecipientsUsersView implements BaseColumns, UserColumns {
                    public static final String CONVERSATION_ROW_ID = "conversation_row_id";
                    public static final String SORT_ORDER_DEFAULT = "conversation_row_id DESC";
                    public static final String USER_ROW_ID = "user_row_id";
                    public static final String TABLE_NAME = "conversation_recipients_users_view";
                    public static final Uri CONTENT_URI = Uri.parse(Vine.CONTENT_AUTHORITY + TABLE_NAME);
                    public static final Uri CONTENT_URI_CONVERSATION = Uri.parse(Vine.CONTENT_AUTHORITY + TABLE_NAME + "/conversation");
                }

                public static final class Conversations implements BaseColumns, ConversationsColumns {
                    public static final Uri CONTENT_URI = Uri.parse(Vine.CONTENT_AUTHORITY + "conversations");
                    public static final int NETWORK_TYPE_IN = 1;
                    public static final int NETWORK_TYPE_OUT = 2;
                    public static final String QUERY_CONVERSATION_ID = "conversation_id";
                    public static final String SORT_ORDER_DEFAULT = "last_message_timestamp DESC";
                    public static final String SORT_ORDER_DEFAULT_IS_LAST = "last_message_timestamp ASC, _id DESC";
                    public static final String TABLE_NAME = "conversations";
                }

                public interface ConversationsColumns {
                    public static final String CONVERSATION_ID = "conversation_id";
                    public static final String IS_HIDDEN = "is_hidden";
                    public static final String IS_LAST = "is_last";
                    public static final String LAST_MESSAGE_ROW_ID = "last_message";
                    public static final String LAST_MESSAGE_TIMESTAMP = "last_message_timestamp";
                    public static final String NETWORK_TYPE = "network_type";
                    public static final String UNREAD_MESSAGE_COUNT = "unread_message_count";
                }

                public static final class Editions implements BaseColumns {
                    public static final Uri CONTENT_URI = Uri.parse(Vine.CONTENT_AUTHORITY + "editions");
                    public static final String EDITION_CODE = "edition_code";
                    public static final String EDITION_NAME = "edition_name";
                    public static final String SORT_ORDER_DEFAULT = "edition_name DESC";
                    public static final String TABLE_NAME = "editions";
                }

                public static final class InboxView implements BaseColumns, ConversationsColumns, MessagesColumns {
                    public static final String AVATAR_URL = "avatar_url";
                    public static final String FOLLOWING_FLAG = "following_flag";
                    public static final String PARAM_HIDDEN = "hidden";
                    public static final String PARAM_NETWORK_TYPE = "network_type";
                    public static final String PROFILE_BACKGROUND = "profile_background";
                    public static final String RECIPIENTS_COUNT = "recipients_count";
                    public static final String USERNAME = "username";
                    public static final String USER_ID = "user_id";
                    public static final String USER_IS_EXTERNAL = "user_is_external";
                    public static final String TABLE_NAME = "inbox_view";
                    public static final Uri CONTENT_URI = Uri.parse(Vine.CONTENT_AUTHORITY + TABLE_NAME);
                }

                public static final class Likes implements BaseColumns, LikesColumns, BasePostColumns {
                    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.vine.android.like";
                    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.vine.android.likes";
                    public static final Uri CONTENT_URI = Uri.parse(Vine.CONTENT_AUTHORITY + "likes");
                    public static final int LIMIT_DEFAULT = 1000;
                    public static final String SORT_ORDER_LAST_REFRESH = "last_refresh DESC";
                    public static final String TABLE_NAME = "likes";
                    public static final String VERIFIED = "verified";
                }

                public interface LikesColumns {
                    public static final String LAST_REFRESH = "last_refresh";
                    public static final String LIKE_ID = "like_id";
                }

                public static final class Messages implements BaseColumns, MessagesColumns {
                    public static final Uri CONTENT_URI = Uri.parse(Vine.CONTENT_AUTHORITY + "messages");
                    public static final Uri CONTENT_URI_SINGLE_MESSAGE = Uri.parse(Vine.CONTENT_AUTHORITY + "messages/message");
                    public static final String IS_LAST = "is_last";
                    public static final String QUERY_CONVERSATION_ROW_ID = "conversation_row_id";
                    public static final String QUERY_MESSAGE_ID = "message_id";
                    public static final String SORT_ORDER_DEFAULT = "message_id ASC";
                    public static final String SORT_ORDER_REVERSE = "message_id DESC";
                    public static final String TABLE_NAME = "messages";
                }

                public interface MessagesColumns {
                    public static final String CONVERSATION_ROW_ID = "conversation_row_id";
                    public static final String DELETED = "deleted";
                    public static final String EPHEMERAL = "ephemeral";
                    public static final String ERROR_CODE = "error_code";
                    public static final String ERROR_REASON = "error_reason";
                    public static final String IS_LAST = "is_last";
                    public static final String LOCAL_START_TIME = "local_start_time";
                    public static final String MAX_LOOPS = "max_loops";
                    public static final String MESSAGE = "message";
                    public static final String MESSAGE_ID = "message_id";
                    public static final String POST_ID = "post_id";
                    public static final String THUMBNAIL_URL = "thumbnail_url";
                    public static final String TIMESTAMP = "timestamp";
                    public static final String UPLOAD_PATH = "upload_path";
                    public static final String USER_ROW_ID = "user_row_id";
                    public static final String VANISHED = "vanished";
                    public static final String VANISHED_DATE = "vanished_date";
                    public static final String VIDEO_URL = "video_url";
                }

                public static final class Notifications implements BaseColumns, NotificationsColumns {
                    public static final Uri CONTENT_URI = Uri.parse(Vine.CONTENT_AUTHORITY + "notifications");
                    public static final String SORT_ORDER_DEFAULT = "notification_id DESC";
                    public static final String TABLE_NAME = "notifications";
                }

                public interface NotificationsColumns {
                    public static final String AVATAR_URL = "avatar_url";
                    public static final String CLEARED = "cleared";
                    public static final String CONVERSATION_ROW_ID = "conversation_row_id";
                    public static final String MESSAGE = "message";
                    public static final String NOTIFICATION_ID = "notification_id";
                    public static final String NOTIFICATION_TYPE = "notification_type";
                }

                public static final class PageCursors implements BaseColumns {
                    public static final String ANCHOR = "anchor";
                    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.vine.android.page_cursor";
                    public static final int CURSOR_UNDEFINED = -1;
                    public static final String KIND = "kind";
                    public static final int KIND_ACTIVITY = 3;
                    public static final int KIND_COMMENTS = 5;
                    public static final int KIND_CONVERSATIONS = 6;
                    public static final int KIND_FRIENDS = 8;
                    public static final int KIND_MESSAGES = 7;
                    public static final int KIND_POSTS_TIMELINE = 2;
                    public static final int KIND_TAGS = 4;
                    public static final int KIND_USERS = 1;
                    public static final String NEXT_PAGE = "next_page";
                    public static final int PAGE_NEW = 1;
                    public static final int PAGE_TYPE_NEWER = 2;
                    public static final int PAGE_TYPE_OLDER = 3;
                    public static final String PREVIOUS_PAGE = "previous_page";
                    public static final String REVERSED = "reversed";
                    public static final String TAG = "tag";
                    public static final String TYPE = "type";
                    public static final String TABLE_NAME = "page_cursors";
                    public static final Uri CONTENT_URI = Uri.parse(Vine.CONTENT_AUTHORITY + TABLE_NAME);
                }

                public interface PostColumns {
                    public static final String COMMENTS_COUNT = "comments_count";
                    public static final String DESCRIPTION = "description";
                    public static final String FOURSQUARE_VENUE_ID = "foursquare_venue_id";
                    public static final String LAST_REFRESH = "last_refresh";
                    public static final String LIKES_COUNT = "likes_count";
                    public static final String LOOPS = "loops";
                    public static final String METADATA_FLAGS = "metadata_flags";
                    public static final String ON_FIRE = "on_fire";
                    public static final String POST_FLAGS = "post_flags";
                    public static final String REVINERS_COUNT = "reviners_count";
                    public static final String SHARE_URL = "share_url";
                    public static final String TAGS = "tags";
                    public static final String THUMBNAIL_URL = "thumbnail_url";
                    public static final String USER_BACKGROUND_COLOR = "user_background_color";
                    public static final String VELOCITY = "velocity";
                    public static final String VENUE_DATA = "venue_data";
                    public static final String VIDEO_LOW_URL = "video_low_uRL";
                    public static final String VIDEO_URL = "video_url";
                }

                public static final class PostCommentsLikesView implements BaseColumns, BasePostColumns, PostColumns, PostGroupColumns, LikesColumns, CommentsColumns, ChannelsColumns {
                    public static final String COMMENT_ENTITIES = "comment_entities";
                    public static final String COMMENT_IS_LAST = "comment_is_last";
                    public static final String COMMENT_USER_AVATAR_URL = "comment_user_avatar_url";
                    public static final String COMMENT_USER_LOCATION = "comment_user_location";
                    public static final String COMMENT_USER_TIMESTAMP = "comment_user_timestamp";
                    public static final String COMMENT_USER_USERNAME = "comment_user_username";
                    public static final String COMMENT_USER_USER_ID = "comment_user_user_id";
                    public static final String COMMENT_USER_VERIFIED = "comment_user_verified";
                    public static final String IS_LAST = "is_last";
                    public static final String LIKE_USER_AVATAR_URL = "like_user_avatar_url";
                    public static final String LIKE_USER_LOCATION = "like_user_location";
                    public static final String LIKE_USER_TIMESTAMP = "like_user_timestamp";
                    public static final String LIKE_USER_USERNAME = "like_user_username";
                    public static final String LIKE_USER_USER_ID = "like_user_user_id";
                    public static final String LIKE_USER_VERIFIED = "like_user_verified";
                    public static final String POST_ID = "post_id";
                    public static final String REQUIRED_ORDER = "sort_id DESC, comment_id ASC, like_id";
                    public static final String REQUIRED_ORDER_POPULAR = "sort_id ASC, post_id DESC, comment_id ASC, like_id";
                    public static final String SORT_ORDER_DEFINED_LAST = "sort_id ASC";
                    public static final String TABLE_NAME = "post_comments_likes_view";
                    public static final Uri CONTENT_URI = Uri.parse(Vine.CONTENT_AUTHORITY + TABLE_NAME);
                    public static final Uri CONTENT_URI_ALL_TIMELINE = Uri.parse(Vine.CONTENT_AUTHORITY + TABLE_NAME + "/all_timeline");
                    public static final Uri CONTENT_URI_ALL_TIMELINE_USER = Uri.parse(Vine.CONTENT_AUTHORITY + TABLE_NAME + "/all_timeline_user");
                    public static final Uri CONTENT_URI_ALL_TIMELINE_USER_LIKES = Uri.parse(Vine.CONTENT_AUTHORITY + TABLE_NAME + "/all_timeline_user_likes");
                    public static final Uri CONTENT_URI_ALL_TIMELINE_ON_THE_RISE = Uri.parse(Vine.CONTENT_AUTHORITY + TABLE_NAME + "/all_timeline_on_the_rise");
                    public static final Uri CONTENT_URI_ALL_TIMELINE_POPULAR_NOW = Uri.parse(Vine.CONTENT_AUTHORITY + TABLE_NAME + "/all_timeline_popular");
                    public static final Uri CONTENT_URI_ALL_TIMELINE_TAG = Uri.parse(Vine.CONTENT_AUTHORITY + TABLE_NAME + "/all_timeline_tag");
                    public static final Uri CONTENT_URI_ALL_TIMELINE_SINGLE = Uri.parse(Vine.CONTENT_AUTHORITY + TABLE_NAME + "/post");
                    public static final Uri CONTENT_URI_ALL_TIMELINE_CHANNEL_POPULAR = Uri.parse(Vine.CONTENT_AUTHORITY + TABLE_NAME + "/timeline_channel_popular");
                    public static final Uri CONTENT_URI_ALL_TIMELINE_CHANNEL_RECENT = Uri.parse(Vine.CONTENT_AUTHORITY + TABLE_NAME + "/timeline_channel_recent");
                    public static final Uri CONTENT_URI_ARBITRARY_TIMELINE = Uri.parse(Vine.CONTENT_AUTHORITY + TABLE_NAME + "/timelines");
                }

                public interface PostGroupColumns {
                    public static final String GROUP_FLAGS = "g_flags";
                    public static final String IS_LAST = "is_last";
                    public static final String POST_ID = "post_id";
                    public static final String QUERY_CHANNEL_ID = "channel_id";
                    public static final String QUERY_TAG_NAME = "tag_name";
                    public static final String REPOSTER_ID = "reposter_id";
                    public static final String REPOST_DATA = "repost_data";
                    public static final String SORT_ID = "sort_id";
                    public static final String TAG = "tag";
                    public static final String TYPE = "post_type";
                }

                public static final class PostGroups implements BaseColumns, PostGroupColumns {
                    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.vine.android.posts.groups";
                    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.vine.android.posts.groups";
                    public static final String SORT_ORDER_BY_SORT = "sort_id DESC";
                    public static final int TYPE_ARBITRARY_TIMELINE = 11;
                    public static final int TYPE_CHANNELS_POPULAR = 8;
                    public static final int TYPE_CHANNELS_RECENT = 9;
                    public static final int TYPE_ON_THE_RISE = 4;
                    public static final int TYPE_POPULAR_NOW = 5;
                    public static final int TYPE_POSTS = 7;
                    public static final int TYPE_TAG = 6;
                    public static final int TYPE_TIMELINE = 1;
                    public static final int TYPE_UNDEFINED = -1;
                    public static final int TYPE_USER_LIKES = 3;
                    public static final int TYPE_USER_TIMELINE = 2;
                    public static final int TYPE_USER_TIMELINE_NO_REPOSTS = 10;
                    public static final int TYPE_VM = 12;
                    public static final String TABLE_NAME = "post_groups";
                    public static final Uri CONTENT_URI = Uri.parse(Vine.CONTENT_AUTHORITY + TABLE_NAME);
                }

                public static final class PostGroupsView implements BaseColumns, PostColumns, BasePostColumns, PostGroupColumns {
                    public static final String IS_LAST = "is_last";
                    public static final String POST_ID = "post_id";
                    public static final String SORT_ORDER_DEFAULT = "sort_id DESC";
                    public static final String TABLE_NAME = "post_groups_view";
                    public static final Uri CONTENT_URI = Uri.parse(Vine.CONTENT_AUTHORITY + TABLE_NAME);
                    public static final Uri CONTENT_URI_TIMELINE = Uri.parse(Vine.CONTENT_AUTHORITY + TABLE_NAME + "/timeline");
                    public static final Uri CONTENT_URI_USER_PROFILE = Uri.parse(Vine.CONTENT_AUTHORITY + TABLE_NAME + "/user_profile");
                    public static final Uri CONTENT_URI_FIND_USER_LIKES = Uri.parse(Vine.CONTENT_AUTHORITY + TABLE_NAME + "/user_likes");
                }

                public static final class Posts implements BaseColumns, PostColumns, BasePostColumns {
                    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.vine.android.post";
                    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.vine.android.posts";
                    public static final Uri CONTENT_URI = Uri.parse(Vine.CONTENT_AUTHORITY + "posts");
                    public static final int LIMIT_DEFAULT = 400;
                    public static final String SORT_LAST_REFRESH = "last_refresh DESC";
                    public static final String SORT_ORDER_DEFAULT = "timestamp DESC";
                    public static final String TABLE_NAME = "posts";
                }

                public static final class Settings implements BaseColumns {
                    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.vine.android.settings";
                    public static final Uri CONTENT_URI = Uri.parse(Vine.CONTENT_AUTHORITY + "settings");
                    public static final String PROP_NAME = "name";
                    public static final String PROP_VALUE = "value";
                    public static final String TABLE_NAME = "settings";
                }

                public static final class TagSearchResults implements BaseColumns, TagsColumns {
                    public static final String SORT_ORDER_DEFAULT = "_id ASC";
                    public static final String SORT_ORDER_DEFAULT_IS_LAST = "_id DESC";
                    public static final String TABLE_NAME = "tag_search_results";
                    public static final Uri CONTENT_URI = Uri.parse(Vine.CONTENT_AUTHORITY + TABLE_NAME);
                }

                public interface TagsColumns {
                    public static final String IS_LAST = "is_last";
                    public static final String LAST_USED_TIMESTAMP = "last_used_timestamp";
                    public static final String TAG_ID = "tag_id";
                    public static final String TAG_NAME = "tag_name";
                }

                public static final class TagsRecentlyUsed implements BaseColumns, TagsColumns {
                    public static final String SORT_ORDER_DEFAULT = "_id DESC";
                    public static final String SORT_ORDER_NEWEST_FIRST = "last_used_timestamp DESC";
                    public static final String TABLE_NAME = "tag_recently_used";
                    public static final Uri CONTENT_URI = Uri.parse(Vine.CONTENT_AUTHORITY + TABLE_NAME);
                    public static final Uri CONTENT_URI_PUT_TAG = Uri.parse(Vine.CONTENT_AUTHORITY + TABLE_NAME + "/put_tag");
                    public static final Uri CONTENT_URI_UPDATE_RECENT_TAG = Uri.parse(Vine.CONTENT_AUTHORITY + TABLE_NAME + "/update_tag");
                }

                public interface UploadsColumns {
                    public static final String CONVERSATION_ID = "conversation_id";
                    public static final String HASH = "hash";
                    public static final String IS_PRIVATE = "is_private";
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

                public interface UserColumns {
                    public static final String ACCEPTS_OON_CONVERSATIONS = "accepts_oon_conversations";
                    public static final String AVATAR_URL = "avatar_url";
                    public static final String BLOCKED = "blocked";
                    public static final String BLOCKING = "blocking";
                    public static final String DESCRIPTION = "description";
                    public static final String EMAIL_ADDRESS = "email_address";
                    public static final String EMAIL_VERIFIED = "email_verified";
                    public static final String EXPLICIT = "explicit";
                    public static final String EXTERNAL = "external";
                    public static final String FOLLOWER_COUNT = "follower_count";
                    public static final String FOLLOWING_COUNT = "following_count";
                    public static final String FOLLOWING_FLAG = "following_flag";
                    public static final String FOLLOW_STATUS = "follow_status";
                    public static final String HIDE_PROFILE_REPOSTS = "hide_profile_reposts";
                    public static final String LAST_REFRESH = "last_refresh";
                    public static final String LIKE_COUNT = "like_count";
                    public static final String LOCATION = "location";
                    public static final String LOOP_COUNT = "loop_count";
                    public static final String PHONE_NUMBER = "phone_number";
                    public static final String PHONE_VERIFIED = "phone_verified";
                    public static final String POST_COUNT = "post_count";
                    public static final String PROFILE_BACKGROUND = "profile_background";
                    public static final String USERNAME = "username";
                    public static final String USER_ID = "user_id";
                    public static final String VERIFIED = "verified";
                }

                public interface UserGroupColumns {
                    public static final String GROUP_FLAGS = "g_flags";
                    public static final String IS_LAST = "is_last";
                    public static final String ORDER_ID = "order_id";
                    public static final String PAGE = "page";
                    public static final String QUERY_FRIEND_FILTER = "filter";
                    public static final String QUERY_GROUP_TYPE = "group_type";
                    public static final String TAG = "tag";
                    public static final String TYPE = "type";
                    public static final String USER_ID = "user_id";
                }

                public static final class UserGroups implements BaseColumns, UserGroupColumns {
                    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.vine.android.users.groups";
                    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.vine.android.users.groups";
                    public static final String SORT_ORDER_DEFAULT = "type DESC";
                    public static final int TAG_UNSPECIFIED = -1;
                    public static final int TYPE_USER_COMMENTS = 3;
                    public static final int TYPE_USER_FIND_FRIENDS_ADDRESS = 7;
                    public static final int TYPE_USER_FIND_FRIENDS_TWITTER = 6;
                    public static final int TYPE_USER_FOLLOWER = 2;
                    public static final int TYPE_USER_FOLLOWING = 1;
                    public static final int TYPE_USER_FRIENDS = 10;
                    public static final int TYPE_USER_LIKERS = 5;
                    public static final int TYPE_USER_NOTIFICATION = 12;
                    public static final int TYPE_USER_OUT_OF_NETWORK = 11;
                    public static final int TYPE_USER_POSTS = 4;
                    public static final int TYPE_USER_REVINERS = 9;
                    public static final int TYPE_USER_SEARCH_RESULTS = 8;
                    public static final int TYPE_USER_UNDEFINED = -1;
                    static final String TABLE_NAME = "user_groups";
                    public static final Uri CONTENT_URI = Uri.parse(Vine.CONTENT_AUTHORITY + TABLE_NAME);
                }

                public static final class UserGroupsView implements BaseColumns, UserColumns, UserGroupColumns, UserSections {
                    public static final String SORT_ORDER_DEFAULT = "_id ASC";
                    public static final String SORT_ORDER_DEFAULT_IS_LAST = "_id DESC";
                    public static final String SORT_ORDER_DEFINED_ASCENDING = "order_id ASC";
                    public static final String SORT_ORDER_DEFINED_DESCENDING = "order_id DESC";
                    public static final String SORT_ORDER_DEFINED_DESCENDING_IS_LAST = "order_id ASC";
                    public static final String SORT_ORDER_DEFINED_LAST = "order_id ASC";
                    public static final String SORT_ORDER_FRIEND_REFRESH_FIRST = "last_section_refresh DESC";
                    public static final String SORT_ORDER_FRIEND_REFRESH_FIRST_THEN_FRIEND_INDEX = "last_section_refresh DESC, section_index ASC";
                    public static final String USER_ID = "user_id";
                    static final String TABLE_NAME = "user_groups_view";
                    public static final Uri CONTENT_URI = Uri.parse(Vine.CONTENT_AUTHORITY + TABLE_NAME);
                    public static final Uri CONTENT_URI_FOLLOWERS = Uri.parse(Vine.CONTENT_AUTHORITY + TABLE_NAME + "/followers");
                    public static final Uri CONTENT_URI_FOLLOWING = Uri.parse(Vine.CONTENT_AUTHORITY + TABLE_NAME + "/following");
                    public static final Uri CONTENT_URI_ALL_FOLLOW = Uri.parse(Vine.CONTENT_AUTHORITY + TABLE_NAME + "/all_follow");
                    public static final Uri CONTENT_URI_FRIENDS = Uri.parse(Vine.CONTENT_AUTHORITY + TABLE_NAME + "/friends");
                    public static final Uri CONTENT_URI_FRIENDS_FILTER = Uri.withAppendedPath(CONTENT_URI_FRIENDS, UserGroupColumns.QUERY_FRIEND_FILTER);
                    public static final Uri CONTENT_URI_FIND_FRIENDS_TWITTER = Uri.parse(Vine.CONTENT_AUTHORITY + TABLE_NAME + "/find_friends_twitter");
                    public static final Uri CONTENT_URI_FIND_FRIENDS_ADDRESS = Uri.parse(Vine.CONTENT_AUTHORITY + TABLE_NAME + "/find_friends_address");
                    public static final Uri CONTENT_URI_LIKERS = Uri.parse(Vine.CONTENT_AUTHORITY + TABLE_NAME + "/likers");
                    public static final Uri CONTENT_URI_REVINERS = Uri.parse(Vine.CONTENT_AUTHORITY + TABLE_NAME + "/reviners");
                    public static final Uri CONTENT_URI_USER_SEARCH_RESULTS = Uri.parse(Vine.CONTENT_AUTHORITY + TABLE_NAME + "/user_search_results");
                }

                public interface UserSections extends BaseColumns {
                    public static final String LAST_SECTION_REFRESH = "last_section_refresh";
                    public static final String SECTION_INDEX = "section_index";
                    public static final String SECTION_TITLE = "section_title";
                    public static final String SECTION_TYPE = "section_type";
                    public static final String TABLE_NAME = "user_sections";
                    public static final int TYPE_FRIENDS = 1;
                    public static final int TYPE_RECIPIENT = 0;
                    public static final String USER_ID = "user_id";
                }

                public static final class Users implements BaseColumns, UserColumns {
                    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.vine.android.users";
                    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.vine.android.users";
                    public static final Uri CONTENT_URI = Uri.parse(Vine.CONTENT_AUTHORITY + "users");
                    public static final Uri CONTENT_URI_HIDE_PROFILE_REPOSTS = Uri.parse(Vine.CONTENT_AUTHORITY + "users/hide_profile_reposts");
                    public static final int LIMIT_DEFAULT = 400;
                    public static final String QUERY_HIDE_PROFILE_REPOSTS = "hide_profile_reposts";
                    public static final String SORT_ORDER_DEFAULT = "username ASC";
                    public static final String SORT_ORDER_LAST_REFRESH = "last_refresh DESC";
                    public static final String TABLE_NAME = "users";
                }

        public static StringAnchorManager getUserSectionsAnchorManager(Context context, int sectionType) {
            String key = "user_sections_" + sectionType;
            StringAnchorManager manager = sUserSectionAnchorManager.get(key);
            if (manager == null) {
                StringAnchorManager manager2 = new StringAnchorManager(context.getApplicationContext(), key);
                sUserSectionAnchorManager.put(key, manager2);
                return manager2;
                    }
            return manager;
                }

      private Vine() {
                }
            }
