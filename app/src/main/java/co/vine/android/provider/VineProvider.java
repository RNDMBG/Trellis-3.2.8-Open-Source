            package co.vine.android.provider;

            import android.content.ContentProvider;
            import android.content.ContentValues;
            import android.content.UriMatcher;
            import android.database.Cursor;
            import android.database.sqlite.SQLiteDatabase;
            import android.database.sqlite.SQLiteException;
            import android.database.sqlite.SQLiteQueryBuilder;
            import android.net.Uri;
            import android.text.TextUtils;
            import android.util.Log;
            import co.vine.android.util.BuildUtil;
            import co.vine.android.util.CrashUtil;
            
            
    public class VineProvider extends ContentProvider {
                private static final int ACTIVITY = 33;
                private static final int ACTIVITY_WITH_FOLLOW_REQUESTS = 34;
                private static final int ACTIVITY_WITH_FOLLOW_REQUESTS_ALL = 35;
                private static final int CHANNELS = 8;
                private static final int COMMENTS = 4;
                private static final int CONVERSATION = 118;
                private static final int CONVERSATION_MESSAGE_USER = 113;
                private static final int CONVERSATION_RECIPIENTS = 114;
                private static final int CONVERSATION_RECIPIENTS_USERS_VIEW = 120;
                private static final int CONVERSATION_SINGLE = 119;
                private static final int EDITIONS = 401;
                private static final int FILTER_VINE_FRIENDS = 117;
                private static final int HIDE_PROFILE_REPOSTS = 116;
                private static final int INBOX = 115;
                private static final int LIKES = 5;
                private static final boolean LOGGABLE;
                private static final int MESSAGES = 111;
                private static final int MESSAGES_SINGLE = 112;
                private static final int NOTIFICATIONS = 109;
                private static final int POSTS = 6;
                private static final int POST_GROUPS_VIEW_ALL_TIMELINE = 23;
                private static final int POST_GROUPS_VIEW_ALL_TIMELINE_ON_THE_RISE = 26;
                private static final int POST_GROUPS_VIEW_ALL_TIMELINE_POPULAR_NOW = 27;
                private static final int POST_GROUPS_VIEW_ALL_TIMELINE_SINGLE = 29;
                private static final int POST_GROUPS_VIEW_ALL_TIMELINE_TAG = 28;
                private static final int POST_GROUPS_VIEW_ALL_TIMELINE_USER = 24;
                private static final int POST_GROUPS_VIEW_ALL_TIMELINE_USER_LIKES = 25;
                private static final int POST_GROUPS_VIEW_ARBITRARY_TIMELINE = 32;
                private static final int POST_GROUPS_VIEW_TIMELINE = 20;
                private static final int POST_GROUPS_VIEW_TIMELINE_CHANNEL_POPULAR = 30;
                private static final int POST_GROUPS_VIEW_TIMELINE_CHANNEL_RECENT = 31;
                private static final int POST_GROUPS_VIEW_TIMELINE_USER = 21;
                private static final int POST_GROUPS_VIEW_TIMELINE_USER_LIKES = 22;
                private static final int PUT_RECENT_TAG = 301;
                private static final int RECENT_TAG = 110;
                private static final int SETTINGS = 3;
                private static final String TAG = "VineProvider";
                private static final int TAG_SEARCH_RESULTS = 7;
                private static final int UPDATE_RECENT_TAG = 501;
                private static final int USER = 1;
                private static final int USER_GROUPS_VIEW_ALL_FOLLOW = 102;
                private static final int USER_GROUPS_VIEW_FIND_FRIENDS_ADDRESS = 104;
                private static final int USER_GROUPS_VIEW_FIND_FRIENDS_TWITTER = 105;
                private static final int USER_GROUPS_VIEW_FOLLOWERS = 101;
                private static final int USER_GROUPS_VIEW_FOLLOWING = 100;
                private static final int USER_GROUPS_VIEW_FRIENDS = 103;
                private static final int USER_GROUPS_VIEW_LIKERS = 106;
                private static final int USER_GROUPS_VIEW_REVINERS = 108;
                private static final int USER_GROUPS_VIEW_USER_SEARCH_RESULTS = 107;
                private static final int USER_ID = 2;
                private static final UriMatcher sUriMatcher;

                static {
            LOGGABLE = BuildUtil.isLogsOn() || Log.isLoggable(TAG, 3);
            sUriMatcher = new UriMatcher(-1);
           sUriMatcher.addURI(Vine.AUTHORITY, "users", 1);
           sUriMatcher.addURI(Vine.AUTHORITY, "users/id/#", 2);
           sUriMatcher.addURI(Vine.AUTHORITY, "settings", 3);
           sUriMatcher.addURI(Vine.AUTHORITY, "comments", 4);
           sUriMatcher.addURI(Vine.AUTHORITY, "likes", 5);
           sUriMatcher.addURI(Vine.AUTHORITY, "posts", 6);
           sUriMatcher.addURI(Vine.AUTHORITY, "user_groups_view/following/#", 100);
           sUriMatcher.addURI(Vine.AUTHORITY, "user_groups_view/followers/#", 101);
           sUriMatcher.addURI(Vine.AUTHORITY, "user_groups_view/all_follow/#", 102);
           sUriMatcher.addURI(Vine.AUTHORITY, "user_groups_view/friends/#", 103);
           sUriMatcher.addURI(Vine.AUTHORITY, "user_groups_view/find_friends_twitter/#", 105);
           sUriMatcher.addURI(Vine.AUTHORITY, "user_groups_view/find_friends_address/#", 104);
           sUriMatcher.addURI(Vine.AUTHORITY, "user_groups_view/likers/#", 106);
           sUriMatcher.addURI(Vine.AUTHORITY, "user_groups_view/reviners/#", 108);
           sUriMatcher.addURI(Vine.AUTHORITY, "user_groups_view/user_search_results", 107);
           sUriMatcher.addURI(Vine.AUTHORITY, "post_groups_view/timeline/#", 20);
           sUriMatcher.addURI(Vine.AUTHORITY, "post_groups_view/user_profile/#", 21);
           sUriMatcher.addURI(Vine.AUTHORITY, "post_groups_view/user_likes/#", 22);
           sUriMatcher.addURI(Vine.AUTHORITY, Vine.TagSearchResults.TABLE_NAME, 7);
           sUriMatcher.addURI(Vine.AUTHORITY, Vine.TagsRecentlyUsed.TABLE_NAME, 110);
           sUriMatcher.addURI(Vine.AUTHORITY, "users/hide_profile_reposts/#", 116);
           sUriMatcher.addURI(Vine.AUTHORITY, "channels", 8);
           sUriMatcher.addURI(Vine.AUTHORITY, "post_comments_likes_view/all_timeline/#", 23);
           sUriMatcher.addURI(Vine.AUTHORITY, "post_comments_likes_view/all_timeline_user/#", 24);
           sUriMatcher.addURI(Vine.AUTHORITY, "post_comments_likes_view/all_timeline_user_likes/#", 25);
           sUriMatcher.addURI(Vine.AUTHORITY, "post_comments_likes_view/all_timeline_on_the_rise/#", 26);
           sUriMatcher.addURI(Vine.AUTHORITY, "post_comments_likes_view/all_timeline_popular/#", 27);
           sUriMatcher.addURI(Vine.AUTHORITY, "post_comments_likes_view/all_timeline_tag/#", 28);
           sUriMatcher.addURI(Vine.AUTHORITY, "post_comments_likes_view/post/#", 29);
           sUriMatcher.addURI(Vine.AUTHORITY, "post_comments_likes_view/timeline_channel_popular/#", 30);
           sUriMatcher.addURI(Vine.AUTHORITY, "post_comments_likes_view/timeline_channel_recent/#", 31);
           sUriMatcher.addURI(Vine.AUTHORITY, "post_comments_likes_view/timelines/#", 32);
           sUriMatcher.addURI(Vine.AUTHORITY, "conversations", 118);
           sUriMatcher.addURI(Vine.AUTHORITY, "conversations/conversation", 119);
           sUriMatcher.addURI(Vine.AUTHORITY, "messages", 111);
           sUriMatcher.addURI(Vine.AUTHORITY, "messages/message", 112);
           sUriMatcher.addURI(Vine.AUTHORITY, "message_users_view/conversation", 113);
           sUriMatcher.addURI(Vine.AUTHORITY, "conversation_recipients/conversation", 114);
           sUriMatcher.addURI(Vine.AUTHORITY, "conversation_recipients_users_view/conversation", 120);
           sUriMatcher.addURI(Vine.AUTHORITY, Vine.InboxView.TABLE_NAME, 115);
           sUriMatcher.addURI(Vine.AUTHORITY, "user_groups_view/friends/filter/#", 117);
           sUriMatcher.addURI(Vine.AUTHORITY, "tag_recently_used/put_tag", 301);
           sUriMatcher.addURI(Vine.AUTHORITY, "tag_recently_used/update_tag", UPDATE_RECENT_TAG);
           sUriMatcher.addURI(Vine.AUTHORITY, "editions", 401);
                }

                @Override 
       public boolean onCreate() {
           return true;
                }

                
                
                
                @Override 
       public Cursor query(Uri uri, String[] projection, String selection, String[] selArgs, String sortOrder) {
                    String orderBy;
           String limit = uri.getQueryParameter(Vine.QUERY_PARAMETER_LIMIT);
           String groupBy = null;
                    try {
               SQLiteDatabase db = VineDatabaseHelper.getDatabaseHelper(getContext()).getReadableDatabase();
               SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
               if (LOGGABLE) {
                   Log.d(TAG, "QUERY: uri " + uri + " -> " + sUriMatcher.match(uri));
                        }
               int match = sUriMatcher.match(uri);
                        switch (match) {
                            case 1:
                       qb.setTables("users");
                       orderBy = Vine.Users.SORT_ORDER_DEFAULT;
                       if (!TextUtils.isEmpty(sortOrder)) {
                           orderBy = sortOrder;
                                }
                                try {
                           Cursor c = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
                           c.setNotificationUri(getContext().getContentResolver(), uri);
                           return c;
                                } catch (Exception e) {
                           CrashUtil.log("Cannot execute {} - {}: {} {} {} {} {}.", uri, Integer.valueOf(match), db.toString(), qb.getTables(), selection, groupBy, orderBy);
                           throw new RuntimeException(e);
                                }
                            case 2:
                       qb.setTables("users");
                       qb.appendWhere("user_id=" + uri.getLastPathSegment());
                       orderBy = Vine.Users.SORT_ORDER_DEFAULT;
                       if (!TextUtils.isEmpty(sortOrder)) {
                           orderBy = sortOrder;
                                }
                       Cursor c2 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
                       c2.setNotificationUri(getContext().getContentResolver(), uri);
                       return c2;
                            case 3:
                       qb.setTables("settings");
                       orderBy = null;
                       if (!TextUtils.isEmpty(sortOrder)) {
                           orderBy = sortOrder;
                                }
                       Cursor c3 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
                       c3.setNotificationUri(getContext().getContentResolver(), uri);
                       return c3;
                            case 4:
                       qb.setTables("comments");
                       orderBy = Vine.Comments.SORT_ORDER_DEFAULT;
                       if (!TextUtils.isEmpty(sortOrder)) {
                           orderBy = sortOrder;
                                }
                       Cursor c4 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
                       c4.setNotificationUri(getContext().getContentResolver(), uri);
                       return c4;
                            case 5:
                       qb.setTables("likes");
                       orderBy = null;
                       if (!TextUtils.isEmpty(sortOrder)) {
                           orderBy = sortOrder;
                                }
                       Cursor c5 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
                       c5.setNotificationUri(getContext().getContentResolver(), uri);
                       return c5;
                            case 6:
                       qb.setTables("posts");
                       orderBy = null;
                       if (!TextUtils.isEmpty(sortOrder)) {
                           orderBy = sortOrder;
                                }
                       Cursor c6 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
                       c6.setNotificationUri(getContext().getContentResolver(), uri);
                       return c6;
                            case 7:
                       qb.setTables(Vine.TagSearchResults.TABLE_NAME);
                       orderBy = "_id ASC";
                       if (!TextUtils.isEmpty(sortOrder)) {
                           orderBy = sortOrder;
                                }
                       Cursor c7 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
                       c7.setNotificationUri(getContext().getContentResolver(), uri);
                       return c7;
                            case 8:
                       qb.setTables("channels");
                       orderBy = null;
                       if (!TextUtils.isEmpty(sortOrder)) {
                           orderBy = sortOrder;
                                }
                       Cursor c8 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
                       c8.setNotificationUri(getContext().getContentResolver(), uri);
                       return c8;
                            case 20:
                       qb.setTables(Vine.PostGroupsView.TABLE_NAME);
                       qb.appendWhere("tag=" + uri.getLastPathSegment() + " AND " + Vine.PostGroupColumns.TYPE + "=1");
                       orderBy = "sort_id DESC";
                       if (!TextUtils.isEmpty(sortOrder)) {
                           orderBy = sortOrder;
                                }
                       Cursor c9 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
                       c9.setNotificationUri(getContext().getContentResolver(), uri);
                       return c9;
                            case 21:
                       qb.setTables(Vine.PostGroupsView.TABLE_NAME);
                       qb.appendWhere("tag=" + uri.getLastPathSegment() + " AND " + Vine.PostGroupColumns.TYPE + "=2");
                       orderBy = "sort_id DESC";
                       if (!TextUtils.isEmpty(sortOrder)) {
                           orderBy = sortOrder;
                                }
                       Cursor c10 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
                       c10.setNotificationUri(getContext().getContentResolver(), uri);
                       return c10;
                            case 22:
                       qb.setTables(Vine.PostGroupsView.TABLE_NAME);
                       qb.appendWhere("tag=" + uri.getLastPathSegment() + " AND " + Vine.PostGroupColumns.TYPE + "=3");
                       orderBy = "sort_id DESC";
                       if (!TextUtils.isEmpty(sortOrder)) {
                           orderBy = sortOrder;
                                }
                       Cursor c11 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
                       c11.setNotificationUri(getContext().getContentResolver(), uri);
                       return c11;
                            case 100:
                       qb.setTables("user_groups_view");
                       qb.appendWhere("tag=" + uri.getLastPathSegment() + " AND type=1");
                       groupBy = "user_id";
                       orderBy = Vine.UserGroupsView.SORT_ORDER_DEFINED_DESCENDING;
                       if (!TextUtils.isEmpty(sortOrder)) {
                           orderBy = sortOrder;
                                }
                       Cursor c12 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
                       c12.setNotificationUri(getContext().getContentResolver(), uri);
                       return c12;
                            case 101:
                       qb.setTables("user_groups_view");
                       qb.appendWhere("tag=" + uri.getLastPathSegment() + " AND type=2");
                       groupBy = "user_id";
                       orderBy = Vine.UserGroupsView.SORT_ORDER_DEFINED_DESCENDING;
                       if (!TextUtils.isEmpty(sortOrder)) {
                           orderBy = sortOrder;
                                }
                       Cursor c13 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
                       c13.setNotificationUri(getContext().getContentResolver(), uri);
                       return c13;
                            case 102:
                       qb.setTables("user_groups_view");
                       qb.appendWhere("tag=" + uri.getLastPathSegment() + " AND (type=2 OR type=1)");
                       groupBy = "user_id";
                       orderBy = "_id ASC";
                       if (!TextUtils.isEmpty(sortOrder)) {
                           orderBy = sortOrder;
                                }
                       Cursor c14 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
                       c14.setNotificationUri(getContext().getContentResolver(), uri);
                       return c14;
                            case 103:
                       qb.setTables("user_groups_view");
                       qb.appendWhere("tag=" + uri.getLastPathSegment() + " AND type=10");
                       groupBy = "user_id";
                       orderBy = "_id ASC";
                       if (!TextUtils.isEmpty(sortOrder)) {
                           orderBy = sortOrder;
                                }
                       Cursor c15 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
                       c15.setNotificationUri(getContext().getContentResolver(), uri);
                       return c15;
                            case 104:
                       qb.setTables("user_groups_view");
                       qb.appendWhere("tag=" + uri.getLastPathSegment() + " AND type=7");
                       groupBy = "user_id";
                       orderBy = "_id ASC";
                       if (!TextUtils.isEmpty(sortOrder)) {
                           orderBy = sortOrder;
                                }
                       Cursor c16 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
                       c16.setNotificationUri(getContext().getContentResolver(), uri);
                       return c16;
                            case 105:
                       qb.setTables("user_groups_view");
                       qb.appendWhere("tag=" + uri.getLastPathSegment() + " AND type=6");
                       groupBy = "user_id";
                       orderBy = "_id ASC";
                       if (!TextUtils.isEmpty(sortOrder)) {
                           orderBy = sortOrder;
                                }
                       Cursor c17 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
                       c17.setNotificationUri(getContext().getContentResolver(), uri);
                       return c17;
                            case 106:
                       qb.setTables("user_groups_view");
                       qb.appendWhere("tag=" + uri.getLastPathSegment() + " AND type=5");
                       groupBy = "user_id";
                       orderBy = Vine.UserGroupsView.SORT_ORDER_DEFINED_DESCENDING;
                       if (!TextUtils.isEmpty(sortOrder)) {
                           orderBy = sortOrder;
                                }
                       Cursor c18 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
                       c18.setNotificationUri(getContext().getContentResolver(), uri);
                       return c18;
                            case 107:
                       qb.setTables("user_groups_view");
                       qb.appendWhere("type=8");
                       groupBy = "user_id";
                       orderBy = "_id ASC";
                       if (!TextUtils.isEmpty(sortOrder)) {
                           orderBy = sortOrder;
                                }
                       Cursor c19 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
                       c19.setNotificationUri(getContext().getContentResolver(), uri);
                       return c19;
                            case 108:
                       qb.setTables("user_groups_view");
                       qb.appendWhere("tag=" + uri.getLastPathSegment() + " AND type=9");
                       groupBy = "user_id";
                       orderBy = Vine.UserGroupsView.SORT_ORDER_DEFINED_DESCENDING;
                       if (!TextUtils.isEmpty(sortOrder)) {
                           orderBy = sortOrder;
                                }
                       Cursor c110 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
                       c110.setNotificationUri(getContext().getContentResolver(), uri);
                       return c110;
                            case 109:
                       qb.setTables("notifications");
                       orderBy = null;
                       if (!TextUtils.isEmpty(sortOrder)) {
                           orderBy = sortOrder;
                                }
                       Cursor c111 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
                       c111.setNotificationUri(getContext().getContentResolver(), uri);
                       return c111;
                            case 110:
                       qb.setTables(Vine.TagsRecentlyUsed.TABLE_NAME);
                       return qb.query(db, projection, selection, selArgs, null, null, sortOrder);
                            case 111:
                       qb.setTables("messages");
                       orderBy = null;
                       if (!TextUtils.isEmpty(sortOrder)) {
                           orderBy = sortOrder;
                                }
                       Cursor c112 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
                       c112.setNotificationUri(getContext().getContentResolver(), uri);
                       return c112;
                            case 112:
                       String messageId = uri.getQueryParameter("message_id");
                       qb.setTables("messages");
                       qb.appendWhere("message_id=" + messageId);
                       orderBy = null;
                       if (!TextUtils.isEmpty(sortOrder)) {
                           orderBy = sortOrder;
                                }
                       Cursor c113 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
                       c113.setNotificationUri(getContext().getContentResolver(), uri);
                       return c113;
                            case 113:
                       String conversationObjectId = uri.getQueryParameter("conversation_row_id");
                       qb.setTables(Vine.ConversationMessageUsersView.TABLE_NAME);
                       qb.appendWhere("conversation_row_id=" + conversationObjectId);
                       selection = "deleted=0";
                       orderBy = null;
                       if (!TextUtils.isEmpty(sortOrder)) {
                           orderBy = sortOrder;
                                }
                       Cursor c114 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
                       c114.setNotificationUri(getContext().getContentResolver(), uri);
                       return c114;
                            case 114:
                       String conversationObjectId2 = uri.getQueryParameter("conversation_row_id");
                       qb.setTables(Vine.ConversationRecipients.TABLE_NAME);
                       qb.appendWhere("conversation_row_id=" + conversationObjectId2);
                       groupBy = "user_row_id";
                       orderBy = null;
                       if (!TextUtils.isEmpty(sortOrder)) {
                           orderBy = sortOrder;
                                }
                       Cursor c115 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
                       c115.setNotificationUri(getContext().getContentResolver(), uri);
                       return c115;
                            case 115:
                       qb.setTables(Vine.InboxView.TABLE_NAME);
                       qb.appendWhere("is_hidden=" + uri.getQueryParameter(Vine.InboxView.PARAM_HIDDEN) + " AND network_type=" + uri.getQueryParameter("network_type"));
                       orderBy = null;
                       if (!TextUtils.isEmpty(sortOrder)) {
                           orderBy = sortOrder;
                                }
                       Cursor c116 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
                       c116.setNotificationUri(getContext().getContentResolver(), uri);
                       return c116;
                            case 116:
                       String userId = uri.getLastPathSegment();
                       selection = VineDatabaseSQL.SELECTION_USER_ID;
                       selArgs = new String[]{String.valueOf(userId)};
                       qb.setTables("users");
                       orderBy = null;
                       if (!TextUtils.isEmpty(sortOrder)) {
                           orderBy = sortOrder;
                                }
                       Cursor c117 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
                       c117.setNotificationUri(getContext().getContentResolver(), uri);
                       return c117;
                            case 117:
                       qb.setTables("user_groups_view");
                       String query = Uri.decode(uri.getQueryParameter(Vine.UserGroupColumns.QUERY_FRIEND_FILTER));
                       String type = uri.getQueryParameter(Vine.UserGroupColumns.QUERY_GROUP_TYPE);
                       if (TextUtils.isEmpty(query)) {
                           orderBy = null;
                                } else {
                           String wildcardQuery = "%" + query + "%";
                           selection = "tag=" + uri.getLastPathSegment() + " AND type=" + type + " AND (username LIKE ? OR description LIKE ?)";
                           selArgs = new String[]{wildcardQuery, wildcardQuery};
                           orderBy = null;
                                }
                       if (!TextUtils.isEmpty(sortOrder)) {
                           orderBy = sortOrder;
                                }
                       Cursor c118 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
                       c118.setNotificationUri(getContext().getContentResolver(), uri);
                       return c118;
                            case 118:
                       qb.setTables("conversations");
                       orderBy = null;
                       if (!TextUtils.isEmpty(sortOrder)) {
                           orderBy = sortOrder;
                                }
                       Cursor c119 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
                       c119.setNotificationUri(getContext().getContentResolver(), uri);
                       return c119;
                            case 119:
                       String conversationId = uri.getQueryParameter("conversation_id");
                       qb.setTables("conversations");
                       qb.appendWhere("conversation_id=" + conversationId);
                       orderBy = null;
                       if (!TextUtils.isEmpty(sortOrder)) {
                           orderBy = sortOrder;
                                }
                       Cursor c1110 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
                       c1110.setNotificationUri(getContext().getContentResolver(), uri);
                       return c1110;
                            case 120:
                       String conversationObjectId3 = uri.getQueryParameter("conversation_row_id");
                       qb.setTables(Vine.ConversationRecipientsUsersView.TABLE_NAME);
                       qb.appendWhere("conversation_row_id=" + conversationObjectId3);
                       groupBy = "user_row_id";
                       orderBy = null;
                       if (!TextUtils.isEmpty(sortOrder)) {
                           orderBy = sortOrder;
                                }
                       Cursor c1111 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
                       c1111.setNotificationUri(getContext().getContentResolver(), uri);
                       return c1111;
                            case 401:
                       qb.setTables("editions");
                       orderBy = null;
                       if (!TextUtils.isEmpty(sortOrder)) {
                           orderBy = sortOrder;
                                }
                       Cursor c1112 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
                       c1112.setNotificationUri(getContext().getContentResolver(), uri);
                       return c1112;
                            default:
                       orderBy = null;
                       if (!TextUtils.isEmpty(sortOrder)) {
                           orderBy = sortOrder;
                                }
                       Cursor c1113 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
                       c1113.setNotificationUri(getContext().getContentResolver(), uri);
                       return c1113;
                        }
                    } catch (SQLiteException e2) {
               CrashUtil.logException(e2, "Failed to get a readable database on query.", new Object[0]);
               return null;
                    }
                }

                @Override 
       public String getType(Uri uri) {
                    switch (sUriMatcher.match(uri)) {
                        case 1:
                   return Vine.Users.CONTENT_TYPE;
                        case 2:
                   return Vine.Users.CONTENT_ITEM_TYPE;
                        case 3:
                   return Vine.Settings.CONTENT_ITEM_TYPE;
                        case 100:
                        case 101:
                   return Vine.UserGroups.CONTENT_TYPE;
                        default:
                   throw new IllegalArgumentException("Unknown URL " + uri);
                    }
                }

                @Override 
       public Uri insert(Uri uri, ContentValues values) {
           SQLiteDatabase db = VineDatabaseHelper.getDatabaseHelper(getContext()).getWritableDatabase();
           if (LOGGABLE) {
               Log.d(TAG, "INSERT: uri " + uri + " -> " + sUriMatcher.match(uri));
                    }
           long rowId = -1;
           String id = "";
           int match = sUriMatcher.match(uri);
                    switch (match) {
                        case 301:
                   id = values.getAsString(Vine.TagsColumns.TAG_ID);
                   rowId = db.insert(Vine.TagsRecentlyUsed.TABLE_NAME, null, values);
                   if (LOGGABLE) {
                       Log.d(TAG, "Tag inserted to recently-used with rowId=" + rowId);
                            }
                            break;
                    }
                    if (rowId >= 0) {
               return uri.buildUpon().appendPath(id).build();
                    }
                    return null;
                }

                @Override 
       public int delete(Uri uri, String s, String[] strings) {
           return 0;
                }

                @Override 
       public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
           SQLiteDatabase db = VineDatabaseHelper.getDatabaseHelper(getContext()).getWritableDatabase();
           if (LOGGABLE) {
               Log.d(TAG, "UPDATE: uri " + uri + " -> " + sUriMatcher.match(uri));
                    }
           int match = sUriMatcher.match(uri);
                    switch (match) {
                        case UPDATE_RECENT_TAG :
                   db.update(Vine.TagsRecentlyUsed.TABLE_NAME, values, selection, selectionArgs);
                        default:
                   return 0;
                    }
                }
            }
