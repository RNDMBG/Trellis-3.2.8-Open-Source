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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 36 */    public class VineProvider extends ContentProvider {
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
/* 38 */            LOGGABLE = BuildUtil.isLogsOn() || Log.isLoggable(TAG, 3);
/* 97 */            sUriMatcher = new UriMatcher(-1);
/* 100 */           sUriMatcher.addURI(Vine.AUTHORITY, "users", 1);
/* 101 */           sUriMatcher.addURI(Vine.AUTHORITY, "users/id/#", 2);
/* 102 */           sUriMatcher.addURI(Vine.AUTHORITY, "settings", 3);
/* 104 */           sUriMatcher.addURI(Vine.AUTHORITY, "comments", 4);
/* 105 */           sUriMatcher.addURI(Vine.AUTHORITY, "likes", 5);
/* 107 */           sUriMatcher.addURI(Vine.AUTHORITY, "posts", 6);
/* 109 */           sUriMatcher.addURI(Vine.AUTHORITY, "user_groups_view/following/#", 100);
/* 111 */           sUriMatcher.addURI(Vine.AUTHORITY, "user_groups_view/followers/#", 101);
/* 113 */           sUriMatcher.addURI(Vine.AUTHORITY, "user_groups_view/all_follow/#", 102);
/* 115 */           sUriMatcher.addURI(Vine.AUTHORITY, "user_groups_view/friends/#", 103);
/* 117 */           sUriMatcher.addURI(Vine.AUTHORITY, "user_groups_view/find_friends_twitter/#", 105);
/* 119 */           sUriMatcher.addURI(Vine.AUTHORITY, "user_groups_view/find_friends_address/#", 104);
/* 121 */           sUriMatcher.addURI(Vine.AUTHORITY, "user_groups_view/likers/#", 106);
/* 123 */           sUriMatcher.addURI(Vine.AUTHORITY, "user_groups_view/reviners/#", 108);
/* 125 */           sUriMatcher.addURI(Vine.AUTHORITY, "user_groups_view/user_search_results", 107);
/* 128 */           sUriMatcher.addURI(Vine.AUTHORITY, "post_groups_view/timeline/#", 20);
/* 130 */           sUriMatcher.addURI(Vine.AUTHORITY, "post_groups_view/user_profile/#", 21);
/* 132 */           sUriMatcher.addURI(Vine.AUTHORITY, "post_groups_view/user_likes/#", 22);
/* 135 */           sUriMatcher.addURI(Vine.AUTHORITY, Vine.TagSearchResults.TABLE_NAME, 7);
/* 136 */           sUriMatcher.addURI(Vine.AUTHORITY, Vine.TagsRecentlyUsed.TABLE_NAME, 110);
/* 137 */           sUriMatcher.addURI(Vine.AUTHORITY, "users/hide_profile_reposts/#", 116);
/* 140 */           sUriMatcher.addURI(Vine.AUTHORITY, "channels", 8);
/* 142 */           sUriMatcher.addURI(Vine.AUTHORITY, "post_comments_likes_view/all_timeline/#", 23);
/* 144 */           sUriMatcher.addURI(Vine.AUTHORITY, "post_comments_likes_view/all_timeline_user/#", 24);
/* 146 */           sUriMatcher.addURI(Vine.AUTHORITY, "post_comments_likes_view/all_timeline_user_likes/#", 25);
/* 148 */           sUriMatcher.addURI(Vine.AUTHORITY, "post_comments_likes_view/all_timeline_on_the_rise/#", 26);
/* 150 */           sUriMatcher.addURI(Vine.AUTHORITY, "post_comments_likes_view/all_timeline_popular/#", 27);
/* 152 */           sUriMatcher.addURI(Vine.AUTHORITY, "post_comments_likes_view/all_timeline_tag/#", 28);
/* 154 */           sUriMatcher.addURI(Vine.AUTHORITY, "post_comments_likes_view/post/#", 29);
/* 156 */           sUriMatcher.addURI(Vine.AUTHORITY, "post_comments_likes_view/timeline_channel_popular/#", 30);
/* 158 */           sUriMatcher.addURI(Vine.AUTHORITY, "post_comments_likes_view/timeline_channel_recent/#", 31);
/* 160 */           sUriMatcher.addURI(Vine.AUTHORITY, "post_comments_likes_view/timelines/#", 32);
/* 165 */           sUriMatcher.addURI(Vine.AUTHORITY, "conversations", 118);
/* 166 */           sUriMatcher.addURI(Vine.AUTHORITY, "conversations/conversation", 119);
/* 168 */           sUriMatcher.addURI(Vine.AUTHORITY, "messages", 111);
/* 169 */           sUriMatcher.addURI(Vine.AUTHORITY, "messages/message", 112);
/* 170 */           sUriMatcher.addURI(Vine.AUTHORITY, "message_users_view/conversation", 113);
/* 171 */           sUriMatcher.addURI(Vine.AUTHORITY, "conversation_recipients/conversation", 114);
/* 172 */           sUriMatcher.addURI(Vine.AUTHORITY, "conversation_recipients_users_view/conversation", 120);
/* 173 */           sUriMatcher.addURI(Vine.AUTHORITY, Vine.InboxView.TABLE_NAME, 115);
/* 174 */           sUriMatcher.addURI(Vine.AUTHORITY, "user_groups_view/friends/filter/#", 117);
/* 178 */           sUriMatcher.addURI(Vine.AUTHORITY, "tag_recently_used/put_tag", 301);
/* 179 */           sUriMatcher.addURI(Vine.AUTHORITY, "tag_recently_used/update_tag", UPDATE_RECENT_TAG);
/* 180 */           sUriMatcher.addURI(Vine.AUTHORITY, "editions", 401);
                }

                @Override // android.content.ContentProvider
/* 184 */       public boolean onCreate() {
/* 185 */           return true;
                }

                /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
                /* JADX WARN: Code duplicated, block: B:12:0x005f  */
                /* JADX WARN: Code duplicated, block: B:9:0x0057  */
                @Override // android.content.ContentProvider
/* 191 */       public Cursor query(Uri uri, String[] projection, String selection, String[] selArgs, String sortOrder) {
                    String orderBy;
/* 192 */           String limit = uri.getQueryParameter(Vine.QUERY_PARAMETER_LIMIT);
/* 194 */           String groupBy = null;
                    try {
/* 198 */               SQLiteDatabase db = VineDatabaseHelper.getDatabaseHelper(getContext()).getReadableDatabase();
/* 205 */               SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
/* 206 */               if (LOGGABLE) {
/* 207 */                   Log.d(TAG, "QUERY: uri " + uri + " -> " + sUriMatcher.match(uri));
                        }
/* 210 */               int match = sUriMatcher.match(uri);
                        switch (match) {
                            case 1:
/* 214 */                       qb.setTables("users");
/* 215 */                       orderBy = Vine.Users.SORT_ORDER_DEFAULT;
/* 449 */                       if (!TextUtils.isEmpty(sortOrder)) {
/* 450 */                           orderBy = sortOrder;
                                }
                                try {
/* 453 */                           Cursor c = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
/* 456 */                           c.setNotificationUri(getContext().getContentResolver(), uri);
/* 457 */                           return c;
                                } catch (Exception e) {
/* 459 */                           CrashUtil.log("Cannot execute {} - {}: {} {} {} {} {}.", uri, Integer.valueOf(match), db.toString(), qb.getTables(), selection, groupBy, orderBy);
/* 461 */                           throw new RuntimeException(e);
                                }
                            case 2:
/* 219 */                       qb.setTables("users");
/* 220 */                       qb.appendWhere("user_id=" + uri.getLastPathSegment());
/* 221 */                       orderBy = Vine.Users.SORT_ORDER_DEFAULT;
/* 449 */                       if (!TextUtils.isEmpty(sortOrder)) {
/* 450 */                           orderBy = sortOrder;
                                }
/* 453 */                       Cursor c2 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
/* 456 */                       c2.setNotificationUri(getContext().getContentResolver(), uri);
/* 457 */                       return c2;
                            case 3:
/* 225 */                       qb.setTables("settings");
/* 193 */                       orderBy = null;
/* 449 */                       if (!TextUtils.isEmpty(sortOrder)) {
/* 450 */                           orderBy = sortOrder;
                                }
/* 453 */                       Cursor c3 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
/* 456 */                       c3.setNotificationUri(getContext().getContentResolver(), uri);
/* 457 */                       return c3;
                            case 4:
/* 241 */                       qb.setTables("comments");
/* 242 */                       orderBy = Vine.Comments.SORT_ORDER_DEFAULT;
/* 449 */                       if (!TextUtils.isEmpty(sortOrder)) {
/* 450 */                           orderBy = sortOrder;
                                }
/* 453 */                       Cursor c4 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
/* 456 */                       c4.setNotificationUri(getContext().getContentResolver(), uri);
/* 457 */                       return c4;
                            case 5:
/* 237 */                       qb.setTables("likes");
/* 193 */                       orderBy = null;
/* 449 */                       if (!TextUtils.isEmpty(sortOrder)) {
/* 450 */                           orderBy = sortOrder;
                                }
/* 453 */                       Cursor c5 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
/* 456 */                       c5.setNotificationUri(getContext().getContentResolver(), uri);
/* 457 */                       return c5;
                            case 6:
/* 229 */                       qb.setTables("posts");
/* 193 */                       orderBy = null;
/* 449 */                       if (!TextUtils.isEmpty(sortOrder)) {
/* 450 */                           orderBy = sortOrder;
                                }
/* 453 */                       Cursor c6 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
/* 456 */                       c6.setNotificationUri(getContext().getContentResolver(), uri);
/* 457 */                       return c6;
                            case 7:
/* 362 */                       qb.setTables(Vine.TagSearchResults.TABLE_NAME);
/* 363 */                       orderBy = "_id ASC";
/* 449 */                       if (!TextUtils.isEmpty(sortOrder)) {
/* 450 */                           orderBy = sortOrder;
                                }
/* 453 */                       Cursor c7 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
/* 456 */                       c7.setNotificationUri(getContext().getContentResolver(), uri);
/* 457 */                       return c7;
                            case 8:
/* 367 */                       qb.setTables("channels");
/* 193 */                       orderBy = null;
/* 449 */                       if (!TextUtils.isEmpty(sortOrder)) {
/* 450 */                           orderBy = sortOrder;
                                }
/* 453 */                       Cursor c8 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
/* 456 */                       c8.setNotificationUri(getContext().getContentResolver(), uri);
/* 457 */                       return c8;
                            case 20:
/* 335 */                       qb.setTables(Vine.PostGroupsView.TABLE_NAME);
/* 336 */                       qb.appendWhere("tag=" + uri.getLastPathSegment() + " AND " + Vine.PostGroupColumns.TYPE + "=1");
/* 340 */                       orderBy = "sort_id DESC";
/* 449 */                       if (!TextUtils.isEmpty(sortOrder)) {
/* 450 */                           orderBy = sortOrder;
                                }
/* 453 */                       Cursor c9 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
/* 456 */                       c9.setNotificationUri(getContext().getContentResolver(), uri);
/* 457 */                       return c9;
                            case 21:
/* 344 */                       qb.setTables(Vine.PostGroupsView.TABLE_NAME);
/* 345 */                       qb.appendWhere("tag=" + uri.getLastPathSegment() + " AND " + Vine.PostGroupColumns.TYPE + "=2");
/* 349 */                       orderBy = "sort_id DESC";
/* 449 */                       if (!TextUtils.isEmpty(sortOrder)) {
/* 450 */                           orderBy = sortOrder;
                                }
/* 453 */                       Cursor c10 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
/* 456 */                       c10.setNotificationUri(getContext().getContentResolver(), uri);
/* 457 */                       return c10;
                            case 22:
/* 353 */                       qb.setTables(Vine.PostGroupsView.TABLE_NAME);
/* 354 */                       qb.appendWhere("tag=" + uri.getLastPathSegment() + " AND " + Vine.PostGroupColumns.TYPE + "=3");
/* 358 */                       orderBy = "sort_id DESC";
/* 449 */                       if (!TextUtils.isEmpty(sortOrder)) {
/* 450 */                           orderBy = sortOrder;
                                }
/* 453 */                       Cursor c11 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
/* 456 */                       c11.setNotificationUri(getContext().getContentResolver(), uri);
/* 457 */                       return c11;
                            case 100:
/* 246 */                       qb.setTables("user_groups_view");
/* 247 */                       qb.appendWhere("tag=" + uri.getLastPathSegment() + " AND type=1");
/* 251 */                       groupBy = "user_id";
/* 252 */                       orderBy = Vine.UserGroupsView.SORT_ORDER_DEFINED_DESCENDING;
/* 449 */                       if (!TextUtils.isEmpty(sortOrder)) {
/* 450 */                           orderBy = sortOrder;
                                }
/* 453 */                       Cursor c12 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
/* 456 */                       c12.setNotificationUri(getContext().getContentResolver(), uri);
/* 457 */                       return c12;
                            case 101:
/* 256 */                       qb.setTables("user_groups_view");
/* 257 */                       qb.appendWhere("tag=" + uri.getLastPathSegment() + " AND type=2");
/* 261 */                       groupBy = "user_id";
/* 262 */                       orderBy = Vine.UserGroupsView.SORT_ORDER_DEFINED_DESCENDING;
/* 449 */                       if (!TextUtils.isEmpty(sortOrder)) {
/* 450 */                           orderBy = sortOrder;
                                }
/* 453 */                       Cursor c13 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
/* 456 */                       c13.setNotificationUri(getContext().getContentResolver(), uri);
/* 457 */                       return c13;
                            case 102:
/* 266 */                       qb.setTables("user_groups_view");
/* 267 */                       qb.appendWhere("tag=" + uri.getLastPathSegment() + " AND (type=2 OR type=1)");
/* 273 */                       groupBy = "user_id";
/* 274 */                       orderBy = "_id ASC";
/* 449 */                       if (!TextUtils.isEmpty(sortOrder)) {
/* 450 */                           orderBy = sortOrder;
                                }
/* 453 */                       Cursor c14 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
/* 456 */                       c14.setNotificationUri(getContext().getContentResolver(), uri);
/* 457 */                       return c14;
                            case 103:
/* 278 */                       qb.setTables("user_groups_view");
/* 279 */                       qb.appendWhere("tag=" + uri.getLastPathSegment() + " AND type=10");
/* 283 */                       groupBy = "user_id";
/* 284 */                       orderBy = "_id ASC";
/* 449 */                       if (!TextUtils.isEmpty(sortOrder)) {
/* 450 */                           orderBy = sortOrder;
                                }
/* 453 */                       Cursor c15 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
/* 456 */                       c15.setNotificationUri(getContext().getContentResolver(), uri);
/* 457 */                       return c15;
                            case 104:
/* 298 */                       qb.setTables("user_groups_view");
/* 299 */                       qb.appendWhere("tag=" + uri.getLastPathSegment() + " AND type=7");
/* 303 */                       groupBy = "user_id";
/* 304 */                       orderBy = "_id ASC";
/* 449 */                       if (!TextUtils.isEmpty(sortOrder)) {
/* 450 */                           orderBy = sortOrder;
                                }
/* 453 */                       Cursor c16 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
/* 456 */                       c16.setNotificationUri(getContext().getContentResolver(), uri);
/* 457 */                       return c16;
                            case 105:
/* 288 */                       qb.setTables("user_groups_view");
/* 289 */                       qb.appendWhere("tag=" + uri.getLastPathSegment() + " AND type=6");
/* 293 */                       groupBy = "user_id";
/* 294 */                       orderBy = "_id ASC";
/* 449 */                       if (!TextUtils.isEmpty(sortOrder)) {
/* 450 */                           orderBy = sortOrder;
                                }
/* 453 */                       Cursor c17 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
/* 456 */                       c17.setNotificationUri(getContext().getContentResolver(), uri);
/* 457 */                       return c17;
                            case 106:
/* 308 */                       qb.setTables("user_groups_view");
/* 309 */                       qb.appendWhere("tag=" + uri.getLastPathSegment() + " AND type=5");
/* 313 */                       groupBy = "user_id";
/* 314 */                       orderBy = Vine.UserGroupsView.SORT_ORDER_DEFINED_DESCENDING;
/* 449 */                       if (!TextUtils.isEmpty(sortOrder)) {
/* 450 */                           orderBy = sortOrder;
                                }
/* 453 */                       Cursor c18 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
/* 456 */                       c18.setNotificationUri(getContext().getContentResolver(), uri);
/* 457 */                       return c18;
                            case 107:
/* 328 */                       qb.setTables("user_groups_view");
/* 329 */                       qb.appendWhere("type=8");
/* 330 */                       groupBy = "user_id";
/* 331 */                       orderBy = "_id ASC";
/* 449 */                       if (!TextUtils.isEmpty(sortOrder)) {
/* 450 */                           orderBy = sortOrder;
                                }
/* 453 */                       Cursor c19 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
/* 456 */                       c19.setNotificationUri(getContext().getContentResolver(), uri);
/* 457 */                       return c19;
                            case 108:
/* 318 */                       qb.setTables("user_groups_view");
/* 319 */                       qb.appendWhere("tag=" + uri.getLastPathSegment() + " AND type=9");
/* 323 */                       groupBy = "user_id";
/* 324 */                       orderBy = Vine.UserGroupsView.SORT_ORDER_DEFINED_DESCENDING;
/* 449 */                       if (!TextUtils.isEmpty(sortOrder)) {
/* 450 */                           orderBy = sortOrder;
                                }
/* 453 */                       Cursor c110 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
/* 456 */                       c110.setNotificationUri(getContext().getContentResolver(), uri);
/* 457 */                       return c110;
                            case 109:
/* 371 */                       qb.setTables("notifications");
/* 193 */                       orderBy = null;
/* 449 */                       if (!TextUtils.isEmpty(sortOrder)) {
/* 450 */                           orderBy = sortOrder;
                                }
/* 453 */                       Cursor c111 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
/* 456 */                       c111.setNotificationUri(getContext().getContentResolver(), uri);
/* 457 */                       return c111;
                            case 110:
/* 375 */                       qb.setTables(Vine.TagsRecentlyUsed.TABLE_NAME);
/* 194 */                       return qb.query(db, projection, selection, selArgs, null, null, sortOrder);
                            case 111:
/* 379 */                       qb.setTables("messages");
/* 193 */                       orderBy = null;
/* 449 */                       if (!TextUtils.isEmpty(sortOrder)) {
/* 450 */                           orderBy = sortOrder;
                                }
/* 453 */                       Cursor c112 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
/* 456 */                       c112.setNotificationUri(getContext().getContentResolver(), uri);
/* 457 */                       return c112;
                            case 112:
/* 383 */                       String messageId = uri.getQueryParameter("message_id");
/* 384 */                       qb.setTables("messages");
/* 385 */                       qb.appendWhere("message_id=" + messageId);
/* 193 */                       orderBy = null;
/* 449 */                       if (!TextUtils.isEmpty(sortOrder)) {
/* 450 */                           orderBy = sortOrder;
                                }
/* 453 */                       Cursor c113 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
/* 456 */                       c113.setNotificationUri(getContext().getContentResolver(), uri);
/* 457 */                       return c113;
                            case 113:
/* 399 */                       String conversationObjectId = uri.getQueryParameter("conversation_row_id");
/* 400 */                       qb.setTables(Vine.ConversationMessageUsersView.TABLE_NAME);
/* 401 */                       qb.appendWhere("conversation_row_id=" + conversationObjectId);
/* 402 */                       selection = "deleted=0";
/* 193 */                       orderBy = null;
/* 449 */                       if (!TextUtils.isEmpty(sortOrder)) {
/* 450 */                           orderBy = sortOrder;
                                }
/* 453 */                       Cursor c114 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
/* 456 */                       c114.setNotificationUri(getContext().getContentResolver(), uri);
/* 457 */                       return c114;
                            case 114:
/* 406 */                       String conversationObjectId2 = uri.getQueryParameter("conversation_row_id");
/* 407 */                       qb.setTables(Vine.ConversationRecipients.TABLE_NAME);
/* 408 */                       qb.appendWhere("conversation_row_id=" + conversationObjectId2);
/* 409 */                       groupBy = "user_row_id";
/* 193 */                       orderBy = null;
/* 449 */                       if (!TextUtils.isEmpty(sortOrder)) {
/* 450 */                           orderBy = sortOrder;
                                }
/* 453 */                       Cursor c115 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
/* 456 */                       c115.setNotificationUri(getContext().getContentResolver(), uri);
/* 457 */                       return c115;
                            case 115:
/* 420 */                       qb.setTables(Vine.InboxView.TABLE_NAME);
/* 421 */                       qb.appendWhere("is_hidden=" + uri.getQueryParameter(Vine.InboxView.PARAM_HIDDEN) + " AND network_type=" + uri.getQueryParameter("network_type"));
/* 193 */                       orderBy = null;
/* 449 */                       if (!TextUtils.isEmpty(sortOrder)) {
/* 450 */                           orderBy = sortOrder;
                                }
/* 453 */                       Cursor c116 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
/* 456 */                       c116.setNotificationUri(getContext().getContentResolver(), uri);
/* 457 */                       return c116;
                            case 116:
/* 426 */                       String userId = uri.getLastPathSegment();
/* 427 */                       selection = VineDatabaseSQL.SELECTION_USER_ID;
/* 428 */                       selArgs = new String[]{String.valueOf(userId)};
/* 429 */                       qb.setTables("users");
/* 193 */                       orderBy = null;
/* 449 */                       if (!TextUtils.isEmpty(sortOrder)) {
/* 450 */                           orderBy = sortOrder;
                                }
/* 453 */                       Cursor c117 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
/* 456 */                       c117.setNotificationUri(getContext().getContentResolver(), uri);
/* 457 */                       return c117;
                            case 117:
/* 433 */                       qb.setTables("user_groups_view");
/* 434 */                       String query = Uri.decode(uri.getQueryParameter(Vine.UserGroupColumns.QUERY_FRIEND_FILTER));
/* 435 */                       String type = uri.getQueryParameter(Vine.UserGroupColumns.QUERY_GROUP_TYPE);
/* 436 */                       if (TextUtils.isEmpty(query)) {
/* 193 */                           orderBy = null;
                                } else {
/* 437 */                           String wildcardQuery = "%" + query + "%";
/* 438 */                           selection = "tag=" + uri.getLastPathSegment() + " AND type=" + type + " AND (username LIKE ? OR description LIKE ?)";
/* 442 */                           selArgs = new String[]{wildcardQuery, wildcardQuery};
/* 193 */                           orderBy = null;
                                }
/* 449 */                       if (!TextUtils.isEmpty(sortOrder)) {
/* 450 */                           orderBy = sortOrder;
                                }
/* 453 */                       Cursor c118 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
/* 456 */                       c118.setNotificationUri(getContext().getContentResolver(), uri);
/* 457 */                       return c118;
                            case 118:
/* 389 */                       qb.setTables("conversations");
/* 193 */                       orderBy = null;
/* 449 */                       if (!TextUtils.isEmpty(sortOrder)) {
/* 450 */                           orderBy = sortOrder;
                                }
/* 453 */                       Cursor c119 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
/* 456 */                       c119.setNotificationUri(getContext().getContentResolver(), uri);
/* 457 */                       return c119;
                            case 119:
/* 393 */                       String conversationId = uri.getQueryParameter("conversation_id");
/* 394 */                       qb.setTables("conversations");
/* 395 */                       qb.appendWhere("conversation_id=" + conversationId);
/* 193 */                       orderBy = null;
/* 449 */                       if (!TextUtils.isEmpty(sortOrder)) {
/* 450 */                           orderBy = sortOrder;
                                }
/* 453 */                       Cursor c1110 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
/* 456 */                       c1110.setNotificationUri(getContext().getContentResolver(), uri);
/* 457 */                       return c1110;
                            case 120:
/* 413 */                       String conversationObjectId3 = uri.getQueryParameter("conversation_row_id");
/* 414 */                       qb.setTables(Vine.ConversationRecipientsUsersView.TABLE_NAME);
/* 415 */                       qb.appendWhere("conversation_row_id=" + conversationObjectId3);
/* 416 */                       groupBy = "user_row_id";
/* 193 */                       orderBy = null;
/* 449 */                       if (!TextUtils.isEmpty(sortOrder)) {
/* 450 */                           orderBy = sortOrder;
                                }
/* 453 */                       Cursor c1111 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
/* 456 */                       c1111.setNotificationUri(getContext().getContentResolver(), uri);
/* 457 */                       return c1111;
                            case 401:
/* 233 */                       qb.setTables("editions");
/* 193 */                       orderBy = null;
/* 449 */                       if (!TextUtils.isEmpty(sortOrder)) {
/* 450 */                           orderBy = sortOrder;
                                }
/* 453 */                       Cursor c1112 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
/* 456 */                       c1112.setNotificationUri(getContext().getContentResolver(), uri);
/* 457 */                       return c1112;
                            default:
/* 193 */                       orderBy = null;
/* 449 */                       if (!TextUtils.isEmpty(sortOrder)) {
/* 450 */                           orderBy = sortOrder;
                                }
/* 453 */                       Cursor c1113 = qb.query(db, projection, selection, selArgs, groupBy, null, orderBy, limit);
/* 456 */                       c1113.setNotificationUri(getContext().getContentResolver(), uri);
/* 457 */                       return c1113;
                        }
                    } catch (SQLiteException e2) {
/* 201 */               CrashUtil.logException(e2, "Failed to get a readable database on query.", new Object[0]);
/* 202 */               return null;
                    }
                }

                @Override // android.content.ContentProvider
/* 466 */       public String getType(Uri uri) {
                    switch (sUriMatcher.match(uri)) {
                        case 1:
/* 469 */                   return Vine.Users.CONTENT_TYPE;
                        case 2:
/* 472 */                   return Vine.Users.CONTENT_ITEM_TYPE;
                        case 3:
/* 475 */                   return Vine.Settings.CONTENT_ITEM_TYPE;
                        case 100:
                        case 101:
/* 479 */                   return Vine.UserGroups.CONTENT_TYPE;
                        default:
/* 482 */                   throw new IllegalArgumentException("Unknown URL " + uri);
                    }
                }

                @Override // android.content.ContentProvider
/* 488 */       public Uri insert(Uri uri, ContentValues values) {
/* 489 */           SQLiteDatabase db = VineDatabaseHelper.getDatabaseHelper(getContext()).getWritableDatabase();
/* 492 */           if (LOGGABLE) {
/* 493 */               Log.d(TAG, "INSERT: uri " + uri + " -> " + sUriMatcher.match(uri));
                    }
/* 496 */           long rowId = -1;
/* 498 */           String id = "";
/* 499 */           int match = sUriMatcher.match(uri);
                    switch (match) {
                        case 301:
/* 502 */                   id = values.getAsString(Vine.TagsColumns.TAG_ID);
/* 503 */                   rowId = db.insert(Vine.TagsRecentlyUsed.TABLE_NAME, null, values);
/* 504 */                   if (LOGGABLE) {
/* 505 */                       Log.d(TAG, "Tag inserted to recently-used with rowId=" + rowId);
                            }
                            break;
                    }
                    if (rowId >= 0) {
/* 515 */               return uri.buildUpon().appendPath(id).build();
                    }
                    return null;
                }

                @Override // android.content.ContentProvider
/* 521 */       public int delete(Uri uri, String s, String[] strings) {
/* 522 */           return 0;
                }

                @Override // android.content.ContentProvider
/* 526 */       public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
/* 527 */           SQLiteDatabase db = VineDatabaseHelper.getDatabaseHelper(getContext()).getWritableDatabase();
/* 530 */           if (LOGGABLE) {
/* 531 */               Log.d(TAG, "UPDATE: uri " + uri + " -> " + sUriMatcher.match(uri));
                    }
/* 535 */           int match = sUriMatcher.match(uri);
                    switch (match) {
                        case UPDATE_RECENT_TAG /* 501 */:
/* 538 */                   db.update(Vine.TagsRecentlyUsed.TABLE_NAME, values, selection, selectionArgs);
                        default:
/* 534 */                   return 0;
                    }
                }
            }
