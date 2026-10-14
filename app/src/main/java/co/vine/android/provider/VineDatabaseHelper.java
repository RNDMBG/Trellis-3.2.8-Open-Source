            package co.vine.android.provider;

            import android.content.ContentResolver;
            import android.content.ContentValues;
            import android.content.Context;
            import android.database.Cursor;
            import android.database.sqlite.SQLiteDatabase;
            import android.database.sqlite.SQLiteDiskIOException;
            import android.database.sqlite.SQLiteException;
            import android.database.sqlite.SQLiteFullException;
            import android.database.sqlite.SQLiteOpenHelper;
            import android.net.Uri;
            import android.support.annotation.NonNull;
            import android.text.TextUtils;
            import android.util.Log;
            import android.util.Pair;
            import co.vine.android.Friendships;
            import co.vine.android.VineLoggingException;
            import co.vine.android.api.VineChannel;
            import co.vine.android.api.VineComment;
            import co.vine.android.api.VineComparatorFactory;
            import co.vine.android.api.VineConversation;
            import co.vine.android.api.VineLike;
            import co.vine.android.api.VinePagedData;
            import co.vine.android.api.VinePost;
            import co.vine.android.api.VinePrivateMessage;
            import co.vine.android.api.VineRecipient;
            import co.vine.android.api.VineRepost;
            import co.vine.android.api.VineSingleNotification;
            import co.vine.android.api.VineTag;
            import co.vine.android.api.VineUser;
            import co.vine.android.client.AppController;
            import co.vine.android.client.VineAPI;
            import co.vine.android.util.BuildUtil;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.ImageUtils;
            import co.vine.android.util.LongSparseArray;
            import co.vine.android.util.Util;
            import com.edisonwang.android.slog.SLog;
            import com.google.android.exoplayer.ExoPlayer;
            import java.io.File;
            import java.io.IOException;
            import java.util.ArrayList;
            import java.util.Collection;
            import java.util.HashMap;
            import java.util.HashSet;
            import java.util.Iterator;
            import java.util.LinkedHashMap;
            import java.util.List;
            
            
    public class VineDatabaseHelper extends SQLiteOpenHelper {
                private static final String CURRENT_ACCOUNT_KEY = "current_account";
                private static final String CURRENT_ACCOUNT_NAME = "current_name";
                private static final String CURRENT_USER_ID_KEY = "current_user_id";
                public static final int DATABASE_VERSION = 26;
                private static final String DB_NAME = "vine";
                private static final boolean LOGGABLE;
                private static final int MAX_ACTIVITY_TABLE_SIZE = 500;
                private static final int MAX_COMMENTS_TABLE_SIZE = 5000;
                private static final int MAX_LIKES_TABLE_SIZE = 5000;
                private static final int MAX_POST_TABLE_SIZE = 1000;
                private static final int MAX_USERS_TABLE_SIZE = 10000;
                public static final int SCHEMA_VERSION = 1;
                private static final String SEPARATOR = "-";
                public static final String SETTINGS_NAME_SELECTION = "name=?";
                private static final String TAG = "VineDH";
                private static final HashMap<String, VineDatabaseHelper> sHelperMap;
                private final Context mAppContext;
                private ContentResolver mContentResolver;

                static {
            LOGGABLE = BuildUtil.isLogsOn() && Log.isLoggable(TAG, 3);
           sHelperMap = new HashMap<>();
                }

       public static synchronized VineDatabaseHelper getDatabaseHelper(Context context) {
                    VineDatabaseHelper helper;
                    try {
                        String databaseName = getDatabaseName(1);
               helper = sHelperMap.get(databaseName);
               if (helper == null) {
                   helper = new VineDatabaseHelper(context.getApplicationContext(), databaseName);
                   sHelperMap.put(databaseName, helper);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return helper;
                }

       public static File getDatabasePath(Context context) {
           return context.getDatabasePath(getDatabaseName(1));
                }

       private VineDatabaseHelper(Context context, String name) {
           super(context, name, (SQLiteDatabase.CursorFactory) null, 26);
           this.mContentResolver = context.getContentResolver();
           this.mAppContext = context.getApplicationContext();
                }

                @Override 
       public void onCreate(SQLiteDatabase db) {
           createTables(db);
                }

                @Override 
       public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
           if (LOGGABLE) {
               Log.d(TAG, "Upgrading from " + oldVersion + " to " + newVersion);
                    }
           dropTables(db);
           createTables(db);
                }

       protected void createTables(SQLiteDatabase db) {
           db.execSQL(VineDatabaseSQL.USERS_TABLE_SQL);
           db.execSQL(VineDatabaseSQL.SETTINGS_TABLE_SQL);
           db.execSQL(VineDatabaseSQL.USERS_SECTION_TABLE_SQL);
           db.execSQL(VineDatabaseSQL.USER_GROUPS_TABLE_SQL);
           db.execSQL(VineDatabaseSQL.COMMENTS_TABLE_SQL);
           db.execSQL(VineDatabaseSQL.USER_GROUPS_VIEW_SQL);
           db.execSQL(VineDatabaseSQL.LIKES_TABLE_SQL);
           db.execSQL(VineDatabaseSQL.POSTS_TABLE_SQL);
           db.execSQL(VineDatabaseSQL.POST_GROUPS_TABLE_SQL);
           db.execSQL(VineDatabaseSQL.POST_GROUPS_VIEW_SQL);
           db.execSQL(VineDatabaseSQL.POST_COMMENTS_LIKES_VIEW_SQL);
           db.execSQL(VineDatabaseSQL.PAGE_CURSOR_TABLE_SQL);
           db.execSQL(VineDatabaseSQL.TAGS_TABLE_SQL);
           db.execSQL(VineDatabaseSQL.CHANNELS_TABLE_SQL);
           db.execSQL(VineDatabaseSQL.NOTIFICATIONS_TABLE_SQL);
           db.execSQL(VineDatabaseSQL.TAGS_RECENTLY_USED_TABLE_SQL);
           db.execSQL(VineDatabaseSQL.EDITIONS_TABLE_SQL);
           db.execSQL(VineDatabaseSQL.MESSAGES_TABLE_SQL);
           db.execSQL(VineDatabaseSQL.CONVERSATIONS_TABLE_SQL);
           db.execSQL(VineDatabaseSQL.CONVERSATION_MESSAGE_USERS_VIEW_SQL);
           db.execSQL(VineDatabaseSQL.CONVERSATION_RECIPIENTS_TABLE_SQL);
           db.execSQL(VineDatabaseSQL.INBOX_VIEW_SQL);
           db.execSQL(VineDatabaseSQL.CONVERSATION_RECIPIENTS_USERS_VIEW);
                }

       private void dropTables(SQLiteDatabase db) {
           db.execSQL("DROP VIEW IF EXISTS  user_groups_view;");
           db.execSQL("DROP VIEW IF EXISTS  post_groups_view;");
           db.execSQL("DROP VIEW IF EXISTS  post_comments_likes_view;");
           db.execSQL("DROP TABLE IF EXISTS users;");
           db.execSQL("DROP TABLE IF EXISTS settings;");
           db.execSQL("DROP TABLE IF EXISTS user_groups;");
           db.execSQL("DROP TABLE IF EXISTS comments;");
           db.execSQL("DROP TABLE IF EXISTS likes;");
           db.execSQL("DROP TABLE IF EXISTS posts;");
           db.execSQL("DROP TABLE IF EXISTS post_groups;");
           db.execSQL("DROP TABLE IF EXISTS page_cursors;");
           db.execSQL("DROP TABLE IF EXISTS tag_search_results;");
           db.execSQL("DROP TABLE IF EXISTS tag_search_results;");
           db.execSQL("DROP TABLE IF EXISTS channels;");
           db.execSQL("DROP TABLE IF EXISTS notifications;");
           db.execSQL("DROP TABLE IF EXISTS tag_recently_used;");
           db.execSQL("DROP TABLE IF EXISTS editions;");
           db.execSQL("DROP TABLE IF EXISTS messages;");
           db.execSQL("DROP TABLE IF EXISTS conversation_recipients;");
           db.execSQL("DROP TABLE IF EXISTS conversations;");
           db.execSQL("DROP TABLE IF EXISTS user_sections;");
           db.execSQL("DROP VIEW IF EXISTS message_users_view;");
           db.execSQL("DROP VIEW IF EXISTS inbox_view;");
           db.execSQL("DROP VIEW IF EXISTS conversation_recipients_users_view;");
                }

       public synchronized Cursor getOldestSortId(int groupType) {
                    SQLiteDatabase db;
                    try {
                        db = getReadableDatabase();
                    } catch (Throwable th) {
                        throw th;
                    }
           return db.query(Vine.PostGroups.TABLE_NAME, new String[]{Vine.PostGroupColumns.SORT_ID}, VineDatabaseSQL.SELECTION_POST_GROUP_TYPE, new String[]{String.valueOf(groupType)}, null, null, "CAST (sort_id AS INTEGER) DESC", "1");
                }

       public synchronized DbResponse mergePosts(Collection<VinePost> items, int groupType, String tag, int pageType, int nextPage, int previousPage, long anchor, boolean userInitiated) throws IOException {
                    LongSparseArray<Long> orderMap;
                    String selectionString;
                    String[] selectionArgs;
                    String timelineSelection;
                    String[] timelineSelectionArgs;
                    int response;
                    DbResponse dbResponse;
                    Cursor tableCursor;
                    int sortId;
                    try {
                        SQLiteDatabase db = getWritableDatabase();
               LongSparseArray<VinePost> newPosts = new LongSparseArray<>(items.size());
               LongSparseArray<VinePost> updatedPosts = new LongSparseArray<>(items.size());
                        for (VinePost item : items) {
                   newPosts.put(item.postId, item);
                        }
               if (Util.isPopularTimeline(groupType)) {
                   orderMap = new LongSparseArray<>(items.size());
                   if (pageType == 1 || pageType == 2) {
                       sortId = 1;
                       removePostsFromGroup(groupType);
                            } else {
                       Cursor c = getOldestSortId(groupType);
                       if (c != null && c.moveToFirst()) {
                           int sortId2 = c.getInt(0);
                           sortId = sortId2 + 1;
                                } else {
                           sortId = 1;
                                }
                            }
                   Iterator<VinePost> it = items.iterator();
                            while (it.hasNext()) {
                       orderMap.put(it.next().postId, Long.valueOf(sortId));
                       sortId++;
                            }
                        } else {
                   orderMap = new LongSparseArray<>(items.size());
                            for (VinePost item2 : items) {
                       orderMap.put(item2.postId, Long.valueOf(VineComparatorFactory.get(groupType).getOrderId(item2)));
                            }
                        }
               boolean contiguous = true;
               if (!TextUtils.isEmpty(tag) && !Util.isPopularTimeline(groupType)) {
                   selectionString = VineDatabaseSQL.SELECTION_POST_GROUP_TYPE_AND_TAG;
                   selectionArgs = new String[]{String.valueOf(groupType), tag};
                        } else {
                   selectionString = VineDatabaseSQL.SELECTION_POST_GROUP_TYPE;
                   selectionArgs = new String[]{String.valueOf(groupType)};
                        }
               Cursor c2 = db.query(Vine.PostGroupsView.TABLE_NAME, VineDatabaseSQL.PostGroupsQuery.PROJECTION, selectionString, selectionArgs, null, null, "sort_id DESC");
               if (c2 != null) {
                            try {
                       if (c2.moveToFirst() && anchor > 0) {
                           contiguous = anchor < c2.getLong(3);
                                }
                       c2.close();
                            } catch (Throwable th) {
                                c2.close();
                                throw th;
                            }
                        }
               Cursor c3 = db.query("posts", new String[]{"post_id"}, null, null, null, null, null);
               if (c3 != null) {
                            try {
                       if (c3.moveToFirst()) {
                           do {
                               long postId = c3.getLong(0);
                               VinePost post = newPosts.removeKey(postId);
                               if (post != null) {
                                   updatedPosts.put(postId, post);
                                        }
                           } while (c3.moveToNext());
                                }
                       c3.close();
                            } catch (Throwable th2) {
                                c3.close();
                                throw th2;
                            }
                        }
               if (LOGGABLE && newPosts.isEmpty() && updatedPosts.isEmpty()) {
                   Log.d(TAG, "No new posts to merge or update");
                   dbResponse = new DbResponse(0, 0, 2);
                        } else {
                   LongSparseArray<Long> tagMap = new LongSparseArray<>(items.size());
                   ArrayList<VineUser> users = new ArrayList<>(items.size());
                   int size = newPosts.size();
                            for (int i = 0; i < size; i++) {
                       VinePost vc = newPosts.valueAt(i);
                       if (vc.user != null) {
                           users.add(vc.user);
                           tagMap.put(vc.userId, Long.valueOf(vc.postId));
                                }
                            }
                   mergeUsers(users, 4, null, 0, 0, tagMap, null);
                   long lastRefresh = System.currentTimeMillis();
                   int numInserted = 0;
                   int size2 = newPosts.size();
                   if (size2 > 0) {
                       db.beginTransaction();
                                try {
                           ContentValues values = new ContentValues();
                                    for (int i2 = 0; i2 < size2; i2++) {
                               VinePost post2 = newPosts.valueAt(i2);
                               values.clear();
                               post2.likesCount = post2.likes != null ? post2.likes.count : 0;
                               post2.commentsCount = post2.comments != null ? post2.comments.count : 0;
                               fillPostValues(values, post2, lastRefresh);
                               numInserted += db.insert("posts", null, values) > 0 ? 1 : 0;
                               if (post2.likes != null && post2.likes.items != null && !post2.likes.items.isEmpty()) {
                                   mergeLikes(post2.likes.items, post2.postId, -1, -1);
                                        }
                                    }
                           db.setTransactionSuccessful();
                           safeFinishTransaction(db);
                                } catch (Throwable th3) {
                                    safeFinishTransaction(db);
                                    throw th3;
                                }
                            }
                   int numUpdated = 0;
                   int size3 = updatedPosts.size();
                   if (size3 > 0) {
                       db.beginTransaction();
                                try {
                           ContentValues values2 = new ContentValues();
                                    for (int i3 = 0; i3 < size3; i3++) {
                               VinePost post3 = updatedPosts.valueAt(i3);
                               values2.clear();
                               post3.likesCount = post3.likes != null ? post3.likes.count : 0;
                               post3.commentsCount = post3.comments != null ? post3.comments.count : 0;
                               fillPostValues(values2, post3, lastRefresh);
                               numUpdated += db.update("posts", values2, "post_id=?", new String[]{String.valueOf(post3.postId)}) > 0 ? 1 : 0;
                               if (post3.likes != null && post3.likes.items != null && !post3.likes.items.isEmpty()) {
                                   mergeLikes(post3.likes.items, post3.postId, -1, -1);
                                        }
                                    }
                           db.setTransactionSuccessful();
                           safeFinishTransaction(db);
                                } catch (Throwable th4) {
                                    safeFinishTransaction(db);
                                    throw th4;
                                }
                            }
                   if (LOGGABLE) {
                       Log.d(TAG, "Inserted " + numInserted + " posts, and updated " + numUpdated + " with lastRefresh time " + lastRefresh);
                            }
                            if (anchor > 0 || nextPage > 0 || previousPage > 0) {
                       savePageCursor(2, groupType, tag, nextPage, previousPage, anchor, false);
                            }
                   if (contiguous) {
                       response = 1;
                            } else {
                       if (!TextUtils.isEmpty(tag)) {
                           timelineSelection = "post_type=? AND tag=? AND post_id<?";
                           timelineSelectionArgs = new String[]{String.valueOf(groupType), tag, String.valueOf(anchor)};
                                } else {
                           timelineSelection = "post_type=? AND post_id<?";
                           timelineSelectionArgs = new String[]{String.valueOf(groupType), String.valueOf(anchor)};
                                }
                       int deleted = db.delete("posts", "post_id<?", new String[]{String.valueOf(groupType)});
                       int deletedGroups = db.delete(Vine.PostGroups.TABLE_NAME, timelineSelection, timelineSelectionArgs);
                       if (LOGGABLE) {
                           Log.d(TAG, "Non contiguous timeline detected, deleted " + deleted + " posts and " + deletedGroups + " with id less than anchor: " + anchor + " of type " + groupType + " and tag " + tag);
                                }
                       response = 4;
                            }
                   int deleted2 = 0;
                   if (numInserted > 0 && contiguous && (tableCursor = db.query("posts", VineDatabaseSQL.TableQuery.PROJECTION, null, null, null, null, null)) != null) {
                                try {
                           if (tableCursor.moveToFirst()) {
                               int size4 = tableCursor.getCount();
                               if (size4 > 1000) {
                                            try {
                                       Cursor oldestNthRow = db.rawQuery("SELECT last_refresh FROM posts ORDER BY last_refresh DESC LIMIT 1 OFFSET 500", null);
                                       db.beginTransaction();
                                       if (oldestNthRow != null) {
                                                    try {
                                               if (oldestNthRow.moveToFirst()) {
                                                   long oldestNthId = oldestNthRow.getLong(0);
                                                   db.execSQL("DELETE FROM post_groups WHERE post_id IN (SELECT post_id FROM posts WHERE last_refresh < ?);", new String[]{String.valueOf(oldestNthId)});
                                                   deleted2 = db.delete("posts", "last_refresh<?", new String[]{String.valueOf(oldestNthId)});
                                                   if (LOGGABLE) {
                                                       Log.d(VineAPI.RESOURCE_SHARED, "Due to reaching maximum table size (" + size4 + " with a max of 1000), deleted " + deleted2 + " posts groups all less than last refresh id " + oldestNthId + ".");
                                                            }
                                                        }
                                               oldestNthRow.close();
                                                    } catch (Throwable th5) {
                                                        oldestNthRow.close();
                                                        throw th5;
                                                    }
                                                }
                                       db.setTransactionSuccessful();
                                       safeFinishTransaction(db);
                                            } catch (Throwable th6) {
                                                safeFinishTransaction(db);
                                                throw th6;
                                            }
                                        }
                                    }
                           tableCursor.close();
                                } catch (Throwable th7) {
                                    tableCursor.close();
                                    throw th7;
                                }
                            }
                   if ((numInserted > 0 || numUpdated > 0 || deleted2 > 0) && groupType != 0) {
                       if (groupType != -1) {
                           mergePostGroups(items, groupType, tag, orderMap);
                                }
                       if (userInitiated) {
                           notifyByType(groupType);
                                }
                            }
                   dbResponse = new DbResponse(numInserted, numUpdated, response);
                        }
                    } catch (Throwable th8) {
                        throw th8;
                    }
                    return dbResponse;
                }

       public synchronized int cleanUp(long timeAnchor) {
                    int deleted;
                    try {
                        SQLiteDatabase db = getWritableDatabase();
                        try {
                   db.beginTransaction();
                   db.execSQL("DELETE FROM post_groups WHERE post_id IN (SELECT post_id FROM posts WHERE last_refresh < ?);", new String[]{String.valueOf(timeAnchor)});
                   deleted = db.delete("posts", "last_refresh<?", new String[]{String.valueOf(timeAnchor)});
                   db.setTransactionSuccessful();
                   if (LOGGABLE) {
                       Log.d(TAG, "Cleanup performed, deleted " + deleted + " posts with last_refresh less than anchor: " + timeAnchor);
                            }
                   safeFinishTransaction(db);
                   if (deleted > 0) {
                       notifyPostCommentsViewUris();
                            }
                        } catch (Throwable th) {
                            safeFinishTransaction(db);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                    return deleted;
                }

       public synchronized void cleanUserSearchResults() {
                    try {
                        SQLiteDatabase db = getWritableDatabase();
                        try {
                   db.beginTransaction();
                   int deleted = db.delete("user_groups", VineDatabaseSQL.SELECTION_USER_GROUP_TYPE, new String[]{String.valueOf(8)});
                   if (LOGGABLE) {
                       Log.d(TAG, "Deleted " + deleted + " user search result rows.");
                            }
                   db.setTransactionSuccessful();
                   safeFinishTransaction(db);
                   if (deleted > 0) {
                       this.mContentResolver.notifyChange(Vine.UserGroupsView.CONTENT_URI_USER_SEARCH_RESULTS, null);
                            }
                        } catch (Throwable th) {
                            safeFinishTransaction(db);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }

       public synchronized void cleanTagSearchResults() {
                    try {
                        SQLiteDatabase db = getWritableDatabase();
                        try {
                   db.beginTransaction();
                   int deleted = db.delete(Vine.TagSearchResults.TABLE_NAME, null, null);
                   if (LOGGABLE) {
                       Log.d(TAG, "Deleted " + deleted + " tag search result rows.");
                            }
                   db.setTransactionSuccessful();
                   safeFinishTransaction(db);
                   if (deleted > 0) {
                       this.mContentResolver.notifyChange(Vine.TagSearchResults.CONTENT_URI, null);
                            }
                        } catch (Throwable th) {
                            safeFinishTransaction(db);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }

       public synchronized int mergeLikes(Collection<VineLike> items, long postId, int next, int previous) {
                    int numInserted;
                    Cursor tableCursor;
                    try {
                        SQLiteDatabase db = getWritableDatabase();
               LongSparseArray<VineLike> newLikes = new LongSparseArray<>(items.size());
               LongSparseArray<VineLike> updatedLikes = new LongSparseArray<>(items.size());
                        for (VineLike like : items) {
                   like.postId = postId;
                   newLikes.put(like.likeId, like);
                        }
               Cursor c = db.query("likes", VineDatabaseSQL.LikesQuery.PROJECTION, null, null, null, null, null);
               if (c != null) {
                   while (c.moveToNext()) {
                                try {
                           long likeId = c.getLong(1);
                           VineLike like2 = newLikes.removeKey(likeId);
                           if (like2 != null) {
                               updatedLikes.put(likeId, like2);
                                    }
                                } catch (Throwable th) {
                                    c.close();
                                    throw th;
                                }
                            }
                            c.close();
                        }
               int totalSize = newLikes.size() + updatedLikes.size();
               LongSparseArray<Long> orderMap = new LongSparseArray<>(totalSize);
               ArrayList<VineUser> users = new ArrayList<>(totalSize);
               int newLikeSize = newLikes.size();
                        for (int i = 0; i < newLikeSize; i++) {
                   VineLike vc = newLikes.valueAt(i);
                   if (vc.user != null) {
                       users.add(vc.user);
                       orderMap.put(vc.user.userId, Long.valueOf(vc.likeId));
                            }
                        }
               int updatedLikeSize = updatedLikes.size();
                        for (int i2 = 0; i2 < updatedLikeSize; i2++) {
                   VineLike vc2 = updatedLikes.valueAt(i2);
                   if (vc2.user != null) {
                       users.add(vc2.user);
                       orderMap.put(vc2.user.userId, Long.valueOf(vc2.likeId));
                            }
                        }
               if (!users.isEmpty()) {
                   mergeUsers(users, 5, String.valueOf(postId), next, previous, orderMap);
                        }
               numInserted = 0;
               long lastRefresh = System.currentTimeMillis();
               if (newLikeSize > 0) {
                   db.beginTransaction();
                            try {
                       ContentValues values = new ContentValues();
                                for (int i3 = 0; i3 < newLikeSize; i3++) {
                           VineLike like3 = newLikes.valueAt(i3);
                           values.clear();
                           fillLikeValues(values, like3, lastRefresh);
                           numInserted += db.insert("likes", null, values) > 0 ? 1 : 0;
                                }
                       db.setTransactionSuccessful();
                       safeFinishTransaction(db);
                            } catch (Throwable th2) {
                                safeFinishTransaction(db);
                                throw th2;
                            }
                        }
               int numUpdated = 0;
               if (updatedLikeSize > 0) {
                   db.beginTransaction();
                            try {
                       ContentValues values2 = new ContentValues();
                                for (int i4 = 0; i4 < updatedLikeSize; i4++) {
                           VineLike like4 = updatedLikes.valueAt(i4);
                           values2.clear();
                           fillLikeValues(values2, like4, lastRefresh);
                           numUpdated += db.update("likes", values2, "like_id=?", new String[]{String.valueOf(like4.likeId)}) > 0 ? 1 : 0;
                                }
                       db.setTransactionSuccessful();
                       safeFinishTransaction(db);
                            } catch (Throwable th3) {
                                safeFinishTransaction(db);
                                throw th3;
                            }
                        }
               int deleted = 0;
               if (numInserted > 0 && (tableCursor = db.query("likes", VineDatabaseSQL.TableQuery.PROJECTION, null, null, null, null, null)) != null) {
                            try {
                       if (tableCursor.moveToFirst()) {
                           int size = tableCursor.getCount();
                           if (size > 5000) {
                                        try {
                                   Cursor oldestNthRow = db.rawQuery("SELECT last_refresh FROM likes ORDER BY last_refresh DESC LIMIT 1 OFFSET 2500", null);
                                   db.beginTransaction();
                                   if (oldestNthRow != null) {
                                                try {
                                           if (oldestNthRow.moveToFirst()) {
                                               long oldestNthId = oldestNthRow.getLong(0);
                                               deleted = db.delete("likes", "last_refresh<?", new String[]{String.valueOf(oldestNthId)});
                                               if (LOGGABLE) {
                                                   Log.d(VineAPI.RESOURCE_SHARED, "Due to reaching maximum table size (" + size + " with a max of " + ExoPlayer.Factory.DEFAULT_MIN_REBUFFER_MS + "), deleted " + deleted + " likes, all less than refresh time " + oldestNthId);
                                                        }
                                                    }
                                           oldestNthRow.close();
                                                } catch (Throwable th4) {
                                                    oldestNthRow.close();
                                                    throw th4;
                                                }
                                            }
                                   db.setTransactionSuccessful();
                                   safeFinishTransaction(db);
                                        } catch (Throwable th5) {
                                            safeFinishTransaction(db);
                                            throw th5;
                                        }
                                    }
                                }
                       tableCursor.close();
                            } catch (Throwable th6) {
                                tableCursor.close();
                                throw th6;
                            }
                        }
               if (numInserted > 0 || numUpdated > 0 || deleted > 0) {
                   this.mContentResolver.notifyChange(Vine.Likes.CONTENT_URI, null);
                        }
               if (LOGGABLE) {
                   Log.d(TAG, "Inserted " + numInserted + " likes and updated " + numUpdated + ".");
                        }
                    } catch (Throwable th7) {
                        throw th7;
                    }
                    return numInserted;
                }

       public synchronized int deleteComment(String commentId) {
                    int deleted;
                    try {
                        SQLiteDatabase db = getWritableDatabase();
               deleted = db.delete("comments", "comment_id=?", new String[]{commentId});
               if (deleted > 0) {
                   this.mContentResolver.notifyChange(Vine.Comments.CONTENT_URI, null);
                   notifyPostCommentsViewUris();
                        }
               if (LOGGABLE) {
                   if (deleted > 0) {
                       Log.d(TAG, "Deleting comment: " + commentId);
                            } else {
                       Log.e(TAG, "Couldn't delete comment: " + commentId);
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return deleted;
                }

       public synchronized int getCommentsCount(long postId) {
                    int count = 0;
                    synchronized (this) {
                        try {
                            SQLiteDatabase db = getWritableDatabase();
                   Cursor c = db.query("comments", VineDatabaseSQL.CommentsQuery.PROJECTION, "post_id=?", new String[]{String.valueOf(postId)}, null, null, null);
                   if (c != null) {
                                try {
                           count = c.getCount();
                           c.close();
                                } catch (Throwable th) {
                                    c.close();
                                    throw th;
                                }
                            }
                        } catch (Throwable th2) {
                            throw th2;
                        }
                    }
                    return count;
                }

       public synchronized int addFollow(long userId, long sessionOwnerId, long followId, boolean notify) {
                    int result;
                    try {
                        SQLiteDatabase db = getWritableDatabase();
               String[] whereArgs = {String.valueOf(userId)};
               Cursor c = db.query("users", new String[]{"following_flag"}, VineDatabaseSQL.SELECTION_USER_ID, whereArgs, null, null, null);
               if (c != null) {
                            try {
                       if (c.moveToFirst()) {
                           int currentFriendship = c.getInt(0);
                           int newFriendship = Friendships.setFriendship(currentFriendship, 1);
                           ContentValues cv = new ContentValues();
                           cv.put("following_flag", Integer.valueOf(newFriendship));
                           result = db.update("users", cv, VineDatabaseSQL.SELECTION_USER_ID, whereArgs);
                           VineUser skeletonUser = new VineUser();
                           skeletonUser.userId = userId;
                           ArrayList<VineUser> users = new ArrayList<>(1);
                           users.add(skeletonUser);
                           LongSparseArray<Long> orderMap = null;
                                    if (followId > 0) {
                               orderMap = new LongSparseArray<>(1);
                               orderMap.put(userId, Long.valueOf(followId));
                                    }
                           mergeUserGroups(users, 1, String.valueOf(sessionOwnerId), null, orderMap);
                           if (notify) {
                               this.mContentResolver.notifyChange(Vine.UserGroupsView.CONTENT_URI_FOLLOWING, null);
                                    }
                           if (LOGGABLE) {
                               Log.d(TAG, "Followed user " + userId);
                                    }
                           if (c != null) {
                               c.close();
                                    }
                                } else {
                           if (c != null) {
                               c.close();
                                    }
                           result = -1;
                                }
                            } catch (Throwable th) {
                                if (c != null) {
                           c.close();
                                }
                                throw th;
                            }
                        } else {
                   result = -1;
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                    return result;
                }

       public synchronized int removeFollow(long userId, boolean deleteRow, boolean notify) {
                    int result;
                    try {
                        SQLiteDatabase db = getWritableDatabase();
               String[] whereArgs = {String.valueOf(userId)};
               Cursor c = db.query("users", new String[]{"following_flag"}, VineDatabaseSQL.SELECTION_USER_ID, whereArgs, null, null, null);
               if (c != null) {
                            try {
                       if (c.moveToFirst()) {
                           int currentFriendship = c.getInt(0);
                           int newFriendship = Friendships.unsetFriendship(currentFriendship, 1);
                           ContentValues cv = new ContentValues();
                           cv.put("following_flag", Integer.valueOf(newFriendship));
                           result = db.update("users", cv, VineDatabaseSQL.SELECTION_USER_ID, whereArgs);
                           if (deleteRow) {
                               removeUserWithType(userId, 1, notify, Vine.UserGroupsView.CONTENT_URI_FOLLOWING);
                                    }
                           if (LOGGABLE) {
                               Log.d(TAG, "Unfollowed user " + userId);
                                    }
                           c.close();
                                } else {
                           c.close();
                           result = -1;
                                }
                            } catch (Throwable th) {
                                c.close();
                                throw th;
                            }
                        } else {
                   result = -1;
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                    return result;
                }

       public synchronized int addLike(VineLike like, boolean notify) {
                    int result;
                    try {
                        SQLiteDatabase db = getWritableDatabase();
               String[] whereArgs = {String.valueOf(like.postId)};
               result = 0;
                        try {
                   db.beginTransaction();
                   Cursor c = db.query("posts", new String[]{Vine.PostColumns.METADATA_FLAGS}, "post_id=?", whereArgs, null, null, null);
                   if (c != null) {
                                try {
                           if (c.moveToFirst()) {
                               ContentValues cv = new ContentValues();
                               int flag = c.getInt(0);
                               cv.put(Vine.PostColumns.METADATA_FLAGS, Integer.valueOf(flag | 8));
                               result = db.update("posts", cv, "post_id=?", whereArgs) > 0 ? 1 : 0;
                               if (LOGGABLE) {
                                   Log.d(TAG, "Updating like value in post: " + like.postId);
                                        }
                                    }
                           if (c != null) {
                               c.close();
                                    }
                                } catch (Throwable th) {
                                    if (c != null) {
                               c.close();
                                    }
                                    throw th;
                                }
                            }
                   ArrayList<VineLike> likes = new ArrayList<>(1);
                   likes.add(like);
                   int mergeResult = mergeLikes(likes, like.postId, 0, 0);
                   db.setTransactionSuccessful();
                   safeFinishTransaction(db);
                   if (notify && (result > 0 || mergeResult > 0)) {
                       notifyPostCommentsViewUris();
                            }
                        } catch (Throwable th2) {
                            safeFinishTransaction(db);
                            throw th2;
                        }
                    } catch (Throwable th3) {
                        throw th3;
                    }
                    return result;
                }

       public void clearFileCache() {
           Util.removeCache(this.mAppContext);
           ImageUtils.removeFiles(this.mAppContext);
                }

       public synchronized int removeLike(long postId, long myUserId, boolean notify) {
                    int result;
                    try {
                        SQLiteDatabase db = getWritableDatabase();
               result = 0;
                        try {
                   db.beginTransaction();
                   String[] whereArgs = {String.valueOf(postId)};
                   Cursor c = db.query("posts", new String[]{Vine.PostColumns.METADATA_FLAGS}, "post_id=?", whereArgs, null, null, null);
                   if (c != null) {
                                try {
                           if (c.moveToFirst()) {
                               ContentValues cv = new ContentValues();
                               int flag = c.getInt(0);
                               cv.put(Vine.PostColumns.METADATA_FLAGS, Integer.valueOf(flag & (-9)));
                               if (LOGGABLE) {
                                   Log.d(TAG, "Removing like in post: " + postId);
                                        }
                               result = db.update("posts", cv, "post_id=?", whereArgs) > 0 ? 1 : 0;
                                    }
                           c.close();
                                } catch (Throwable th) {
                                    c.close();
                                    throw th;
                                }
                            }
                   int deletes = db.delete("likes", "post_id=? AND user_id=?", new String[]{String.valueOf(postId), String.valueOf(myUserId)});
                   int deletes2 = deletes + removePostsFromGroupByPostId(3, postId);
                   db.setTransactionSuccessful();
                   if (LOGGABLE) {
                       Log.d(TAG, "Deleted self like for post: " + postId);
                            }
                   safeFinishTransaction(db);
                   if (notify && (result > 0 || deletes2 > 0)) {
                       notifyPostCommentsViewUris();
                            }
                        } catch (Throwable th2) {
                            safeFinishTransaction(db);
                            throw th2;
                        }
                    } catch (Throwable th3) {
                        throw th3;
                    }
                    return result;
                }

       public synchronized int revine(VineRepost repost, long userId, boolean notify) {
                    int result;
                    try {
                        SQLiteDatabase db = getWritableDatabase();
               result = 0;
                        try {
                   db.beginTransaction();
                   String[] whereArgs = {String.valueOf(repost.postId)};
                   Cursor c = db.query("posts", new String[]{Vine.PostColumns.METADATA_FLAGS}, "post_id=?", whereArgs, null, null, null);
                   if (c != null) {
                                try {
                           if (c.getCount() > 0 && c.moveToFirst()) {
                               ContentValues cv = new ContentValues();
                               int flag = c.getInt(0);
                               cv.put(Vine.PostColumns.METADATA_FLAGS, Integer.valueOf(flag | 16));
                               cv.put("my_repost_id", Long.valueOf(repost.repostId));
                               c.close();
                               if (LOGGABLE) {
                                   Log.d(TAG, "Revined post " + repost.postId);
                                        }
                              result = db.update("posts", cv, "post_id=?", whereArgs) > 0 ? 1 : 0;
                                    }
                          c.close();
                                } catch (Throwable th) {
                                    c.close();
                                    throw th;
                                }
                            }
                  Cursor c2 = db.query(Vine.PostGroups.TABLE_NAME, new String[]{Vine.PostGroupColumns.REPOST_DATA}, VineDatabaseSQL.SELECTION_POST_ID_AND_TYPE_AND_TAG, new String[]{String.valueOf(repost.postId), String.valueOf(2), String.valueOf(userId)}, null, null, null);
                  if (c2 != null) {
                                try {
                          if (c2.getCount() > 0 && c2.moveToFirst()) {
                              VineRepost currentData = (VineRepost) Util.fromByteArray(c2.getBlob(0));
                              if (currentData != null) {
                                  currentData.repostId = repost.repostId;
                                        } else {
                                  currentData = new VineRepost();
                                  currentData.repostId = repost.repostId;
                                  currentData.postId = repost.postId;
                                        }
                              ContentValues cv2 = new ContentValues();
                              cv2.put(Vine.PostGroupColumns.REPOST_DATA, Util.toByteArray(currentData));
                              cv2.put(Vine.PostGroupColumns.SORT_ID, Long.valueOf(repost.repostId));
                              db.update(Vine.PostGroups.TABLE_NAME, cv2, VineDatabaseSQL.SELECTION_POST_ID_AND_TYPE_AND_TAG, new String[]{String.valueOf(repost.postId), String.valueOf(2), String.valueOf(userId)});
                              if (LOGGABLE) {
                                  Log.d(TAG, "Updated repost data for post " + currentData.postId + " with repost id " + currentData.repostId + " for user timeline.");
                                        }
                                    } else {
                              ContentValues cv3 = new ContentValues();
                              cv3.put(Vine.PostGroupColumns.TYPE, (Integer) 2);
                              cv3.put("post_id", Long.valueOf(repost.postId));
                              cv3.put(Vine.PostGroupColumns.SORT_ID, Long.valueOf(repost.repostId));
                              cv3.put("tag", Long.valueOf(userId));
                              cv3.put(Vine.PostGroupColumns.REPOST_DATA, Util.toByteArray(repost));
                              db.insert(Vine.PostGroups.TABLE_NAME, "post_id", cv3);
                              if (LOGGABLE) {
                                  Log.d(TAG, "Inserted repost data for post " + repost.postId + " with repost id " + repost.repostId + " for user timeline.");
                                        }
                                    }
                          c2.close();
                                } catch (Throwable th2) {
                                    c2.close();
                                    throw th2;
                                }
                            }
                  db.setTransactionSuccessful();
                  safeFinishTransaction(db);
                  if (notify && result > 0) {
                      notifyPostCommentsViewUris();
                            }
                        } catch (Throwable th3) {
                            safeFinishTransaction(db);
                            throw th3;
                        }
                    } catch (Throwable th4) {
                        throw th4;
                    }
                    return result;
                }

                @NonNull
      public synchronized ArrayList<VineSingleNotification> mergePushNotification(VineSingleNotification notification) {
                    ArrayList<VineSingleNotification> ret;
                    try {
                        SQLiteDatabase db = getWritableDatabase();
              ret = new ArrayList<>();
              int type = notification.notificationTypeId;
                        String selection = "notification_id=?" + (notification.isMessaging() ? " AND notification_type=?" : "");
              String[] whereArgs = notification.isMessaging() ? new String[]{String.valueOf(notification.notificationId), String.valueOf(type)} : new String[]{String.valueOf(notification.notificationId)};
              Cursor c = db.query("notifications", VineDatabaseSQL.NotificationsQuery.PROJECTION, selection, whereArgs, null, null, Vine.Notifications.SORT_ORDER_DEFAULT);
                        try {
                  db.beginTransaction();
                  if (c != null) {
                                try {
                          if (!c.moveToFirst()) {
                              ContentValues values = new ContentValues();
                              fillNotificationValues(values, notification, Util.toPrettyComment(notification));
                              int inserted = 0 + (db.insert("notifications", null, values) > 0 ? 1 : 0);
                              if (inserted > 0) {
                                  ret.addAll(getNotifications(type));
                                        }
                                    }
                          c.close();
                                } catch (Throwable th) {
                                    c.close();
                                    throw th;
                                }
                            }
                  db.setTransactionSuccessful();
                  safeFinishTransaction(db);
                        } catch (Throwable th2) {
                            safeFinishTransaction(db);
                            throw th2;
                        }
                    } catch (Throwable th3) {
                        throw th3;
                    }
                    return ret;
                }

      public synchronized ArrayList<VineSingleNotification> getNotifications(int notificationType) {
                    ArrayList<VineSingleNotification> ret;
                    synchronized (this) {
                        boolean isPrivateMessage = notificationType == 18;
                        try {
                            String selection = Vine.NotificationsColumns.NOTIFICATION_TYPE + (isPrivateMessage ? "=?" : "<>?");
                  String[] whereArgs = {String.valueOf(18)};
                  SQLiteDatabase db = getReadableDatabase();
                  ret = new ArrayList<>();
                  Cursor c = db.query("notifications", VineDatabaseSQL.NotificationsQuery.PROJECTION, selection, whereArgs, null, null, Vine.Notifications.SORT_ORDER_DEFAULT, "10");
                  if (c != null) {
                      while (c.moveToNext()) {
                          ret.add(new VineSingleNotification(c));
                                }
                      c.close();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return ret;
                }

      public synchronized int deletePushNotifications(int notificationDisplayId) {
                    int deleted;
                    synchronized (this) {
                        boolean isPrivateMessage = notificationDisplayId == 2;
                        try {
                            String selection = Vine.NotificationsColumns.NOTIFICATION_TYPE + (isPrivateMessage ? "=?" : "<>?");
                  String[] whereArgs = {String.valueOf(18)};
                  SQLiteDatabase db = getWritableDatabase();
                  deleted = 0 + db.delete("notifications", selection, whereArgs);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return deleted;
                }

      public void deletePushNotificationsForConversation(long conversationRowId) {
          SQLiteDatabase db = getWritableDatabase();
          db.delete("notifications", "conversation_row_id=?", new String[]{String.valueOf(conversationRowId)});
                }

      public synchronized Uri mergeSuggestedTag(VineTag tag) {
                    Uri result;
                    result = null;
                    try {
              String[] selectionArgs = {String.valueOf(tag.tagId)};
              Cursor c = this.mContentResolver.query(Vine.TagsRecentlyUsed.CONTENT_URI, VineDatabaseSQL.TagsRecentlyUsedQuery.PROJECTION, "tag_id=?", selectionArgs, "_id DESC");
              if (c != null) {
                  ContentValues values = new ContentValues();
                  if (c.getCount() < 1) {
                      fillTagValues(values, tag);
                      result = this.mContentResolver.insert(Vine.TagsRecentlyUsed.CONTENT_URI_PUT_TAG, values);
                            } else if (c.moveToFirst()) {
                      String[] selectionArgs2 = {String.valueOf(tag.tagId)};
                      values.put("last_used_timestamp", Long.valueOf(System.currentTimeMillis()));
                      this.mContentResolver.update(Vine.TagsRecentlyUsed.CONTENT_URI_UPDATE_RECENT_TAG, values, "tag_id=?", selectionArgs2);
                            }
                  c.close();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return result;
                }

      public synchronized int unRevine(long postId, long userId, boolean following, boolean notify) {
                    int result;
                    Cursor c;
                    try {
                        SQLiteDatabase db = getWritableDatabase();
              result = 0;
                        try {
                  db.beginTransaction();
                  String[] whereArgs = {String.valueOf(postId)};
                  Cursor c2 = db.query("posts", new String[]{Vine.PostColumns.METADATA_FLAGS}, "post_id=?", whereArgs, null, null, null);
                  if (c2 != null) {
                                try {
                          if (c2.getCount() > 0 && c2.moveToFirst()) {
                              ContentValues cv = new ContentValues();
                              int flag = c2.getInt(0);
                              cv.put(Vine.PostColumns.METADATA_FLAGS, Integer.valueOf(flag & (-17)));
                              cv.put("my_repost_id", (Integer) 0);
                              if (LOGGABLE) {
                                  Log.d(TAG, "Unrevined post " + postId);
                                        }
                              result = db.update("posts", cv, "post_id=?", whereArgs) > 0 ? 1 : 0;
                                    }
                          c2.close();
                                } catch (Throwable th) {
                                    c2.close();
                                    throw th;
                                }
                            }
                  int deletedGroups = db.delete(Vine.PostGroups.TABLE_NAME, VineDatabaseSQL.SELECTION_POST_ID_AND_TYPE_AND_TAG, new String[]{String.valueOf(postId), String.valueOf(2), String.valueOf(userId)});
                  if (LOGGABLE) {
                      Log.d(TAG, "Deleted " + deletedGroups + " with tag " + userId + " and type 2 and post id " + postId + " due to unrevining.");
                            }
                  if (!following && (c = db.query("posts", new String[]{"user_id"}, "post_id=?", new String[]{String.valueOf(postId)}, null, null, null)) != null) {
                                try {
                          if (c.moveToFirst()) {
                              long postUserId = c.getLong(0);
                              long postUserRowId = getUserRowIdForUserRemoteId(postUserId);
                              following = amFollowingUser(postUserRowId);
                                    }
                          c.close();
                                } catch (Throwable th2) {
                                    c.close();
                                    throw th2;
                                }
                            }
                  if (!following) {
                      int deletedGroups2 = deletedGroups + db.delete(Vine.PostGroups.TABLE_NAME, "post_id=? AND post_type=?", new String[]{String.valueOf(postId), String.valueOf(1)});
                      if (LOGGABLE) {
                          Log.d(TAG, "Deleted " + deletedGroups2 + " for type 1 and post id " + postId + " due to unrevining.");
                                }
                            } else {
                      ContentValues cv2 = new ContentValues();
                      cv2.putNull(Vine.PostGroupColumns.REPOST_DATA);
                      int r = db.update(Vine.PostGroups.TABLE_NAME, cv2, "post_id=? AND post_type=?", new String[]{String.valueOf(postId), String.valueOf(1)});
                      Log.d(TAG, "Removed   " + r + " repost data for type 1 and post id " + postId + " due to unrevining.");
                            }
                  db.setTransactionSuccessful();
                  safeFinishTransaction(db);
                  if (notify) {
                      notifyPostCommentsViewUris();
                            }
                        } catch (Throwable th3) {
                            safeFinishTransaction(db);
                            throw th3;
                        }
                    } catch (Throwable th4) {
                        throw th4;
                    }
                    return result;
                }

      public synchronized int deletePost(long postId) {
                    int deleted;
                    try {
                        SQLiteDatabase db = getWritableDatabase();
                        try {
                  db.beginTransaction();
                  deleted = db.delete("posts", "post_id=?", new String[]{String.valueOf(postId)});
                  int deletedGroups = 0;
                  if (deleted > 0) {
                      deletedGroups = db.delete(Vine.PostGroups.TABLE_NAME, "post_id=?", new String[]{String.valueOf(postId)});
                            }
                  if (deleted > 0 || deletedGroups > 0) {
                      notifyPostCommentsViewUris();
                            }
                  if (LOGGABLE) {
                      if (deletedGroups > 0) {
                          Log.d(TAG, "Removing the post: " + postId);
                                } else {
                          Log.e(TAG, "Couldn't delete post: " + postId);
                                }
                            }
                  db.setTransactionSuccessful();
                  safeFinishTransaction(db);
                        } catch (Throwable th) {
                            safeFinishTransaction(db);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                    return deleted;
                }

      public synchronized void expireTimeline(int type) {
                    try {
                        removePostsFromGroup(type);
              notifyPostCommentsViewUris();
                    } catch (Throwable th) {
                        throw th;
                    }
                }

      private synchronized int removePostsFromGroupByPostId(int type, long postId) {
                    SQLiteDatabase db;
                    String[] selArgs;
                    try {
                        db = getWritableDatabase();
              selArgs = new String[]{String.valueOf(type), String.valueOf(postId)};
                    } catch (Throwable th) {
                        throw th;
                    }
          return db.delete(Vine.PostGroups.TABLE_NAME, "post_type=? AND post_id=?", selArgs);
                }

      private synchronized int removePostsFromGroup(int type) {
                    int numGroups;
                    try {
                        SQLiteDatabase db = getWritableDatabase();
                        try {
                  db.beginTransaction();
                  numGroups = db.delete(Vine.PostGroups.TABLE_NAME, VineDatabaseSQL.SELECTION_POST_GROUP_TYPE, new String[]{String.valueOf(type)});
                  db.setTransactionSuccessful();
                  if (LOGGABLE) {
                      Log.d(TAG, "Clearing group " + type + " deleting " + numGroups + " groups ");
                            }
                  safeFinishTransaction(db);
                        } catch (Throwable th) {
                            safeFinishTransaction(db);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                    return numGroups;
                }

      public synchronized int removePostsFromGroupByUser(long userId, int type, String tag) {
                    int numGroups;
                    try {
                        SQLiteDatabase db = getWritableDatabase();
              Cursor c = db.query(Vine.PostGroupsView.TABLE_NAME, VineDatabaseSQL.PostGroupsQuery.PROJECTION, "user_id=? AND post_type=? AND tag=?", new String[]{String.valueOf(userId), String.valueOf(type), tag}, null, null, null);
              ArrayList<String> postIdsToRemove = new ArrayList<>();
              if (c != null) {
                            try {
                      if (c.moveToFirst()) {
                          do {
                              postIdsToRemove.add(String.valueOf(c.getLong(0)));
                          } while (c.moveToNext());
                                }
                      c.close();
                            } catch (Throwable th) {
                                c.close();
                                throw th;
                            }
                        }
              StringBuilder placeHolderString = new StringBuilder();
              int size = postIdsToRemove.size();
                        for (int i = 0; i < size; i++) {
                  placeHolderString.append("?");
                  if (i < size - 1 && size != 1) {
                      placeHolderString.append(",");
                            }
                        }
                        try {
                  db.beginTransaction();
                  String[] idArray = new String[postIdsToRemove.size()];
                  postIdsToRemove.add(String.valueOf(type));
                  String whereString = " IN (" + placeHolderString.toString() + ") AND " + Vine.PostGroupColumns.TYPE + "=?";
                  numGroups = db.delete(Vine.PostGroups.TABLE_NAME, "post_id" + whereString, (String[]) postIdsToRemove.toArray(idArray));
                  db.setTransactionSuccessful();
                  if (LOGGABLE) {
                      Log.d(TAG, "Due to unfollowing " + userId + " deleting " + numGroups + " groups  , of type " + type + " and tag " + tag);
                            }
                  safeFinishTransaction(db);
                  if (numGroups > 0) {
                      notifyPostCommentsViewUris();
                            }
                        } catch (Throwable th2) {
                            safeFinishTransaction(db);
                            throw th2;
                        }
                    } catch (Throwable th3) {
                        throw th3;
                    }
                    return numGroups;
                }

      public synchronized int mergeUsers(Collection<VineUser> users, int type, String groupTag, int next, int previous, LongSparseArray<Long> orderMap, long sectionRefreshTime, int sectionType) {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return mergeUsers(users, type, groupTag, next, previous, null, orderMap, sectionRefreshTime, sectionType);
                }

      public synchronized int mergeUsers(Collection<VineUser> users, int type, String groupTag, int next, int previous, LongSparseArray<Long> orderMap) {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return mergeUsers(users, type, groupTag, next, previous, null, orderMap);
                }

      public synchronized int mergeUsers(Collection<VineUser> users, int type, String groupTag, int next, int previous, LongSparseArray<Long> tagMap, LongSparseArray<Long> orderMap) {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return mergeUsers(users, type, groupTag, next, previous, tagMap, orderMap, -1L, -1);
                }

      private void mergeUserSection(SQLiteDatabase db, ContentValues values, VineUser user, long refresh, int sectionType) {
          if (sectionType >= 0) {
              values.clear();
              values.put(Vine.UserSections.SECTION_TYPE, Integer.valueOf(sectionType));
                        if (refresh > 0) {
                  values.put(Vine.UserSections.LAST_SECTION_REFRESH, Long.valueOf(refresh));
                        }
                        if (user.friendIndex > 0) {
                  values.put(Vine.UserSections.SECTION_INDEX, Long.valueOf(user.friendIndex));
                        }
              values.put("user_id", Long.valueOf(user.userId));
              values.put(Vine.UserSections.SECTION_TITLE, user.sectionTitle);
              String[] whereArgs = {String.valueOf(user.userId), String.valueOf(sectionType)};
              Cursor query = db.query(Vine.UserSections.TABLE_NAME, new String[]{"_id"}, "user_id=? AND section_type=?", whereArgs, null, null, null);
              if (query != null) {
                            try {
                      if (query.moveToFirst()) {
                          db.update(Vine.UserSections.TABLE_NAME, values, "user_id=? AND section_type=?", whereArgs);
                                } else {
                          db.insert(Vine.UserSections.TABLE_NAME, null, values);
                                }
                      query.close();
                            } catch (Throwable th) {
                                query.close();
                                throw th;
                            }
                        }
                    }
                }

      public synchronized int mergeUsers(Collection<VineUser> users, int type, String groupTag, int next, int previous, LongSparseArray<Long> tagMap, LongSparseArray<Long> orderMap, long sectionRefreshTime, int sectionType) {
                    int numInserted;
                    Cursor tableCursor;
                    try {
                        SQLiteDatabase db = getWritableDatabase();
              LongSparseArray<VineUser> newUsers = new LongSparseArray<>(users.size());
              LongSparseArray<VineUser> updatedUsers = new LongSparseArray<>(users.size());
                        for (VineUser user : users) {
                  newUsers.put(user.userId, user);
                        }
              Cursor c = db.query("users", new String[]{"user_id"}, null, null, null, null, null);
              if (c != null) {
                  while (c.moveToNext()) {
                                try {
                          long userId = c.getLong(0);
                          VineUser user2 = newUsers.removeKey(userId);
                          if (user2 != null) {
                              updatedUsers.put(userId, user2);
                                    }
                                } catch (Throwable th) {
                                    c.close();
                                    throw th;
                                }
                            }
                            c.close();
                        }
              numInserted = 0;
              int numUpdated = 0;
              long lastRefreshTime = System.currentTimeMillis();
              int size = newUsers.size();
              if (size > 0) {
                  db.beginTransaction();
                            try {
                      ContentValues values = new ContentValues();
                                for (int i = 0; i < size; i++) {
                          VineUser user3 = newUsers.valueAt(i);
                          values.clear();
                          fillUserContentValues(values, user3, lastRefreshTime);
                          numInserted += db.insert("users", "user_id", values) > 0 ? 1 : 0;
                          mergeUserSection(db, values, user3, sectionRefreshTime, sectionType);
                                }
                      db.setTransactionSuccessful();
                      safeFinishTransaction(db);
                            } catch (Throwable th2) {
                                safeFinishTransaction(db);
                                throw th2;
                            }
                        }
              int size2 = updatedUsers.size();
              if (size2 > 0) {
                  db.beginTransaction();
                            try {
                      ContentValues values2 = new ContentValues();
                                for (int i2 = 0; i2 < size2; i2++) {
                          VineUser user4 = updatedUsers.valueAt(i2);
                          values2.clear();
                          fillUserContentValues(values2, user4, lastRefreshTime);
                          numUpdated += db.update("users", values2, VineDatabaseSQL.SELECTION_USER_ID, new String[]{String.valueOf(user4.userId)}) > 0 ? 1 : 0;
                          mergeUserSection(db, values2, user4, sectionRefreshTime, sectionType);
                                }
                      db.setTransactionSuccessful();
                      safeFinishTransaction(db);
                            } catch (Throwable th3) {
                                safeFinishTransaction(db);
                                throw th3;
                            }
                        }
              int deleted = 0;
              if (numInserted > 0 && (tableCursor = db.query("users", VineDatabaseSQL.TableQuery.PROJECTION, null, null, null, null, null)) != null) {
                            try {
                      if (tableCursor.moveToFirst()) {
                          int size3 = tableCursor.getCount();
                          if (size3 > 10000) {
                                        try {
                                  Cursor oldestRefreshId = db.rawQuery("SELECT last_refresh FROM users ORDER BY last_refresh DESC LIMIT 1 OFFSET 5000", null);
                                  db.beginTransaction();
                                  if (oldestRefreshId != null) {
                                                try {
                                          if (oldestRefreshId.moveToFirst()) {
                                              long oldestNthId = oldestRefreshId.getLong(0);
                                              db.execSQL("DELETE FROM user_groups WHERE user_id IN (SELECT user_id FROM users WHERE last_refresh < ?);", new String[]{String.valueOf(oldestNthId)});
                                              deleted = db.delete("users", "last_refresh<?", new String[]{String.valueOf(oldestNthId)});
                                              if (LOGGABLE) {
                                                  Log.d(VineAPI.RESOURCE_SHARED, "Due to reaching maximum table size (" + size3 + " with a max of 10000), deleted " + deleted + " users, all less than last refresh of " + oldestNthId);
                                                        }
                                                    }
                                          oldestRefreshId.close();
                                                } catch (Throwable th4) {
                                                    oldestRefreshId.close();
                                                    throw th4;
                                                }
                                            }
                                  db.setTransactionSuccessful();
                                  safeFinishTransaction(db);
                                        } catch (Throwable th5) {
                                            safeFinishTransaction(db);
                                            throw th5;
                                        }
                                    }
                                }
                      tableCursor.close();
                            } catch (Throwable th6) {
                                tableCursor.close();
                                throw th6;
                            }
                        }
              if (numInserted > 0 || numUpdated > 0 || deleted > 0) {
                  if (type != -1) {
                      mergeUserGroups(users, type, groupTag, tagMap, orderMap);
                      if (next > 0 || previous > 0) {
                          savePageCursor(1, type, groupTag, next, previous, 0L, true);
                                }
                            }
                  this.mContentResolver.notifyChange(Vine.UserGroupsView.CONTENT_URI, null);
                  this.mContentResolver.notifyChange(Vine.UserGroupsView.CONTENT_URI_FIND_FRIENDS_TWITTER, null);
                  this.mContentResolver.notifyChange(Vine.UserGroupsView.CONTENT_URI_FIND_FRIENDS_ADDRESS, null);
                  this.mContentResolver.notifyChange(Vine.UserGroupsView.CONTENT_URI_LIKERS, null);
                  this.mContentResolver.notifyChange(Vine.UserGroupsView.CONTENT_URI_FOLLOWERS, null);
                  this.mContentResolver.notifyChange(Vine.UserGroupsView.CONTENT_URI_FOLLOWING, null);
                  this.mContentResolver.notifyChange(Vine.UserGroupsView.CONTENT_URI_FRIENDS, null);
                  this.mContentResolver.notifyChange(Vine.UserGroupsView.CONTENT_URI_USER_SEARCH_RESULTS, null);
                        }
              if (LOGGABLE) {
                  Log.d(TAG, "Inserted " + numInserted + " new users and updating " + numUpdated + " of type " + type + ".");
                        }
                    } catch (Throwable th7) {
                        throw th7;
                    }
                    return numInserted;
                }

      public synchronized int mergeSearchedTags(Collection<VineTag> tags, int next, int previous) {
                    int numInserted;
                    try {
                        SQLiteDatabase db = getWritableDatabase();
              HashMap<Long, VineTag> newTags = new HashMap<>(tags.size());
              HashMap<Long, VineTag> updatedTags = new HashMap<>(tags.size());
                        for (VineTag tag : tags) {
                  newTags.put(Long.valueOf(tag.tagId), tag);
                        }
              Cursor c = db.query(Vine.TagSearchResults.TABLE_NAME, new String[]{Vine.TagsColumns.TAG_ID}, null, null, null, null, null);
              if (c != null) {
                  while (c.moveToNext()) {
                                try {
                          long tagId = c.getLong(c.getColumnIndex(Vine.TagsColumns.TAG_ID));
                          VineTag tag2 = newTags.remove(Long.valueOf(tagId));
                          if (tag2 != null) {
                              updatedTags.put(Long.valueOf(tagId), tag2);
                                    }
                                } catch (Throwable th) {
                                    c.close();
                                    throw th;
                                }
                            }
                            c.close();
                        }
              numInserted = 0;
              int numUpdated = 0;
              if (!newTags.isEmpty()) {
                  db.beginTransaction();
                            try {
                      ContentValues values = new ContentValues();
                                for (VineTag tag3 : newTags.values()) {
                          values.clear();
                          fillTagValues(values, tag3);
                          numInserted += db.insert(Vine.TagSearchResults.TABLE_NAME, Vine.TagsColumns.TAG_ID, values) > 0 ? 1 : 0;
                                }
                      db.setTransactionSuccessful();
                      safeFinishTransaction(db);
                            } catch (Throwable th2) {
                                safeFinishTransaction(db);
                                throw th2;
                            }
                        }
              if (!updatedTags.isEmpty()) {
                  db.beginTransaction();
                            try {
                      ContentValues values2 = new ContentValues();
                                for (VineTag tag4 : updatedTags.values()) {
                          values2.clear();
                          fillTagValues(values2, tag4);
                          numUpdated += db.update(Vine.TagSearchResults.TABLE_NAME, values2, "tag_id=?", new String[]{String.valueOf(tag4.tagId)}) > 0 ? 1 : 0;
                                }
                      db.setTransactionSuccessful();
                      safeFinishTransaction(db);
                            } catch (Throwable th3) {
                                safeFinishTransaction(db);
                                throw th3;
                            }
                        }
              if (numInserted > 0 || numUpdated > 0) {
                  if (next > 0 || previous > 0) {
                      savePageCursor(4, 0, null, next, previous, 0L, true);
                            }
                  this.mContentResolver.notifyChange(Vine.TagSearchResults.CONTENT_URI, null);
                        }
              if (LOGGABLE) {
                  Log.d(TAG, "Inserted " + numInserted + " and updated " + numUpdated + " tags.");
                        }
                    } catch (Throwable th4) {
                        throw th4;
                    }
                    return numInserted;
                }

      public synchronized int mergeChannels(Collection<VineChannel> channels, int next, int previous) {
                    int inserted;
                    try {
                        SQLiteDatabase db = getWritableDatabase();
              inserted = 0;
              if (!channels.isEmpty()) {
                  db.beginTransaction();
                            try {
                      db.delete("channels", null, null);
                      ContentValues values = new ContentValues();
                                for (VineChannel channel : channels) {
                          values.clear();
                          fillChannelValues(values, channel);
                          inserted += db.insert("channels", null, values) > 0 ? 1 : 0;
                                }
                      db.setTransactionSuccessful();
                      safeFinishTransaction(db);
                            } catch (Throwable th) {
                                safeFinishTransaction(db);
                                throw th;
                            }
                        }
              if (next > 0 || previous > 0) {
                  savePageCursor(2, 0, null, next, previous, 0L, true);
                        }
              if (inserted > 0) {
                  if (LOGGABLE) {
                      Log.d(TAG, "Inserted " + inserted + " new channels.");
                            }
                  this.mContentResolver.notifyChange(Vine.Channels.CONTENT_URI, null);
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                    return inserted;
                }

      private synchronized void mergeUserGroups(Collection<VineUser> users, int type, String groupTag, LongSparseArray<Long> tagMap, LongSparseArray<Long> orderMap) {
                    Cursor cursor;
                    if (users != null && type != -1) {
                        try {
                  if (LOGGABLE) {
                      Log.d(TAG, "mergeUserGroups: " + users.size() + ", tagged by: " + groupTag + ", of type: " + type);
                            }
                  LinkedHashMap<Long, VineUser> newUserGroupsMap = new LinkedHashMap<>();
                            for (VineUser user : users) {
                      newUserGroupsMap.put(Long.valueOf(user.userId), user);
                            }
                  SQLiteDatabase db = getWritableDatabase();
                  if (groupTag != null) {
                      cursor = db.query("user_groups", VineDatabaseSQL.UserGroupsQuery.PROJECTION, VineDatabaseSQL.SELECTION_USER_GROUP_TYPE_AND_TAG, new String[]{String.valueOf(type), String.valueOf(groupTag)}, null, null, null);
                            } else {
                      cursor = db.query("user_groups", VineDatabaseSQL.UserGroupsQuery.PROJECTION, VineDatabaseSQL.SELECTION_USER_GROUP_TYPE, new String[]{String.valueOf(type)}, null, null, null);
                            }
                  if (cursor != null) {
                      while (cursor.moveToNext()) {
                                    try {
                              newUserGroupsMap.remove(Long.valueOf(cursor.getLong(0)));
                                    } catch (Throwable th) {
                                        cursor.close();
                                        throw th;
                                    }
                                }
                      cursor.close();
                      if (LOGGABLE) {
                          Log.d(TAG, "Inserting new user groups: " + newUserGroupsMap.size());
                                }
                      int newUserGroupsCount = newUserGroupsMap.size();
                      if (newUserGroupsCount > 0) {
                          db.beginTransaction();
                                    try {
                              ContentValues values = new ContentValues();
                              values.put("type", Integer.valueOf(type));
                              if (groupTag != null) {
                                  values.put("tag", groupTag);
                                        }
                              boolean useOrdering = orderMap != null;
                                        for (VineUser user2 : newUserGroupsMap.values()) {
                                  values.put("g_flags", (Integer) 0);
                                  values.put("user_id", Long.valueOf(user2.userId));
                                  if (useOrdering && orderMap.get(user2.userId) != null) {
                                      values.put(Vine.UserGroupColumns.ORDER_ID, orderMap.get(user2.userId));
                                            } else if (tagMap != null && tagMap.get(user2.userId) != null) {
                                      values.put("tag", tagMap.get(user2.userId));
                                            }
                                  db.insert("user_groups", "user_id", values);
                                        }
                              db.setTransactionSuccessful();
                              safeFinishTransaction(db);
                              this.mContentResolver.notifyChange(Vine.UserGroupsView.CONTENT_URI, null);
                                    } catch (Throwable th2) {
                                        safeFinishTransaction(db);
                                        throw th2;
                                    }
                                }
                            }
                        } catch (Throwable th3) {
                            throw th3;
                        }
                    }
                }

      private synchronized void mergePostGroups(Collection<VinePost> posts, int type, String groupTag, LongSparseArray<Long> orderMap) {
                    if (posts != null && type != -1) {
                        try {
                  if (LOGGABLE) {
                      Log.d(TAG, "mergePostGroups: " + posts.size() + ", tagged by: " + groupTag + ", of type: " + type);
                            }
                  LinkedHashMap<Long, VinePost> newPostGroups = new LinkedHashMap<>();
                            for (VinePost post : posts) {
                      newPostGroups.put(Long.valueOf(post.postId), post);
                            }
                  SQLiteDatabase db = getWritableDatabase();
                  Cursor cursor = db.query(Vine.PostGroups.TABLE_NAME, VineDatabaseSQL.PostGroupsQuery.PROJECTION, VineDatabaseSQL.SELECTION_POST_GROUP_TYPE, new String[]{String.valueOf(type)}, null, null, null);
                  if (cursor != null && cursor.moveToFirst()) {
                      do {
                                    try {
                              newPostGroups.remove(Long.valueOf(cursor.getLong(0)));
                                    } catch (Throwable th) {
                                        cursor.close();
                                        throw th;
                                    }
                      } while (cursor.moveToNext());
                      cursor.close();
                            }
                  int numInserted = 0;
                  int newPostGroupsCount = newPostGroups.size();
                  if (newPostGroupsCount > 0) {
                      db.beginTransaction();
                                try {
                          ContentValues values = new ContentValues();
                          values.put(Vine.PostGroupColumns.TYPE, Integer.valueOf(type));
                          values.put("tag", groupTag);
                                    for (VinePost post2 : newPostGroups.values()) {
                              values.put("g_flags", (Integer) 0);
                              values.put("post_id", Long.valueOf(post2.postId));
                              values.put(Vine.PostGroupColumns.REPOST_DATA, Util.toByteArray(post2.repost));
                              if (post2.repost != null) {
                                  values.put(Vine.PostGroupColumns.REPOSTER_ID, Long.valueOf(post2.repost.userId));
                                        } else {
                                  values.remove(Vine.PostGroupColumns.REPOSTER_ID);
                                        }
                              if (orderMap != null) {
                                  values.put(Vine.PostGroupColumns.SORT_ID, orderMap.get(post2.postId));
                                        }
                              numInserted += db.insert(Vine.PostGroups.TABLE_NAME, "post_id", values) > 0 ? 1 : 0;
                                    }
                          db.setTransactionSuccessful();
                          safeFinishTransaction(db);
                          this.mContentResolver.notifyChange(Vine.PostGroupsView.CONTENT_URI, null);
                                } catch (Throwable th2) {
                                    safeFinishTransaction(db);
                                    throw th2;
                                }
                            }
                  if (LOGGABLE) {
                      Log.d(TAG, "Inserted new post groups: " + numInserted + " of type " + type);
                            }
                        } catch (Throwable th3) {
                            throw th3;
                        }
                    }
                }

      public synchronized boolean removeUserWithType(long userId, int type, boolean notify, Uri uri) {
                    boolean z = true;
                    synchronized (this) {
                        try {
                            SQLiteDatabase db = getWritableDatabase();
                  String[] selArgs = {String.valueOf(type), String.valueOf(userId)};
                  int deleted = db.delete("user_groups", "type=? AND user_id=?", selArgs);
                  if (deleted > 0) {
                      if (notify) {
                          this.mContentResolver.notifyChange(uri, null);
                                }
                      if (LOGGABLE) {
                          Log.d(TAG, "Deleted user with userId=" + userId + " of type=" + type);
                                }
                            } else {
                                z = false;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return z;
                }

      public synchronized void mergeConversationRecipients(long conversationRowId, Collection<Long> userRowIds) {
                    int inserted = 0;
                    try {
              if (LOGGABLE) {
                  Log.d(TAG, "mergeConversations, count=" + userRowIds.size());
                        }
              if (!userRowIds.isEmpty()) {
                  SQLiteDatabase db = getWritableDatabase();
                            try {
                      db.beginTransaction();
                      ContentValues values = new ContentValues();
                      values.put("conversation_row_id", Long.valueOf(conversationRowId));
                                for (Long entry : userRowIds) {
                          values.put("user_row_id", entry);
                          inserted += db.insert(Vine.ConversationRecipients.TABLE_NAME, null, values) > 0 ? 1 : 0;
                                }
                      db.setTransactionSuccessful();
                      safeFinishTransaction(db);
                            } catch (Throwable th) {
                                safeFinishTransaction(db);
                                throw th;
                            }
                        }
              if (inserted > 0) {
                  this.mContentResolver.notifyChange(Vine.InboxView.CONTENT_URI, null);
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }

      public synchronized void mergeInbox(long sessionOwnerId, VinePagedData inbox, int next, int previous, long anchor, int networkType, int userGroup) throws IOException {
                    try {
                        if (inbox.participants != null && !inbox.participants.isEmpty()) {
                  mergeUsers(inbox.participants, userGroup, null, 0, 0, null);
                        }
                        for (VineConversation conversation : (ArrayList<VineConversation>) inbox.items) {
                  mergeConversation(sessionOwnerId, conversation, networkType, 0, 0, 0L);
                        }
              if (next > 0 || previous > 0 || anchor > 0) {
                  savePageCursor(6, networkType, null, next, previous, anchor, false);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

      public synchronized long mergeConversation(long sessionOwnerId, VineConversation conversation, int networkType, int nextPage, int prevPage, long anchor) throws IOException {
                    long conversationRowId;
                    try {
                        conversationRowId = mergeMessages(sessionOwnerId, conversation, networkType, nextPage, prevPage, anchor);
              if (conversation.users != null) {
                  ArrayList<Long> userRowIds = new ArrayList<>();
                  Iterator<Long> it = conversation.users.iterator();
                            while (it.hasNext()) {
                                long user = it.next().longValue();
                      long userRowId = getUserRowIdForUserRemoteId(user);
                                if (userRowId > -1) {
                          userRowIds.add(Long.valueOf(userRowId));
                                }
                            }
                  mergeConversationRecipients(conversationRowId, userRowIds);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return conversationRowId;
                }

      public int getConversationNetworkType(long conversationRowId) {
          SQLiteDatabase db = getReadableDatabase();
          String[] selectionArgs = {String.valueOf(conversationRowId)};
          String[] projection = {"network_type"};
          Cursor c = db.query("conversations", projection, "_id =? ", selectionArgs, null, null, null, "1");
          if (c == null) {
                        return 1;
                    }
          if (c.moveToFirst()) {
              int result = c.getInt(0);
              c.close();
              return result;
                    }
          c.close();
                    return 1;
                }

      public synchronized long mergeMessages(long sessionOwnerId, VineConversation conversation, int networkType, int nextPage, int prevPage, long anchor) throws IOException {
                    long conversationRowId;
                    long userRowId;
                    try {
                        LinkedHashMap<Long, VinePrivateMessage> newMessages = new LinkedHashMap<>();
              LinkedHashMap<Long, VinePrivateMessage> updatedMessages = new LinkedHashMap<>();
              HashMap<Long, Long> userIdToUserRowMap = new HashMap<>();
              long conversationId = conversation.conversationId;
              conversationRowId = mergeConversationWithRemoteId(conversationId, Integer.valueOf(networkType), null, conversation.unreadMessageCount, 0L);
              long oldestMessageId = Long.MAX_VALUE;
              if (conversation.messages != null) {
                            for (VinePrivateMessage message : conversation.messages) {
                                if (message.messageId < oldestMessageId) {
                          oldestMessageId = message.messageId;
                                }
                      if (!userIdToUserRowMap.containsKey(Long.valueOf(message.userId))) {
                          userRowId = getUserRowIdForUserRemoteId(message.userId);
                          userIdToUserRowMap.put(Long.valueOf(message.userId), Long.valueOf(userRowId));
                                } else {
                          userRowId = userIdToUserRowMap.get(Long.valueOf(message.userId)).longValue();
                                }
                      message.userId = userRowId;
                      newMessages.put(Long.valueOf(message.messageId), message);
                            }
                        }
              if (LOGGABLE) {
                  Log.d(TAG, "mergeMessages, count=" + newMessages.size());
                        }
              SQLiteDatabase db = getWritableDatabase();
              boolean contiguous = true;
              String[] selectionArgs = {String.valueOf(conversationRowId)};
              Cursor c = db.query("messages", VineDatabaseSQL.MessagesQuery.PROJECTION, "conversation_row_id =? ", selectionArgs, null, null, Vine.Messages.SORT_ORDER_REVERSE, "1");
              if (c != null) {
                  if (c.moveToFirst()) {
                      contiguous = oldestMessageId <= c.getLong(2);
                            }
                  c.close();
                        }
              Cursor cursor = this.mContentResolver.query(Vine.Messages.CONTENT_URI, VineDatabaseSQL.MessagesQuery.PROJECTION, null, null, null);
              if (cursor != null) {
                  while (cursor.moveToNext()) {
                      VinePrivateMessage message2 = newMessages.remove(Long.valueOf(cursor.getLong(2)));
                      if (message2 != null) {
                          updatedMessages.put(Long.valueOf(message2.messageId), message2);
                                }
                            }
                  cursor.close();
                        }
              int inserted = 0;
              int updated = 0;
              ArrayList<VinePost> posts = new ArrayList<>();
              if (!newMessages.isEmpty()) {
                  if (LOGGABLE) {
                      Log.d(TAG, "Inserting new messages, count=" + newMessages.size());
                            }
                  db.beginTransaction();
                            try {
                      ContentValues values = new ContentValues();
                                for (VinePrivateMessage message3 : newMessages.values()) {
                          values.clear();
                          fillMessageValues(values, conversationRowId, message3);
                          values.put(Vine.MessagesColumns.DELETED, (Integer) 0);
                          long insertedRow = db.insert("messages", null, values);
                          inserted += insertedRow > 0 ? 1 : 0;
                                    if (message3.messageId == conversation.lastMessage) {
                              updateConversationWithLastMessage(conversationRowId, insertedRow, message3.created);
                                    }
                          if (message3.post != null) {
                              posts.add(message3.post);
                                    }
                                }
                      mergePosts(posts, 12, String.valueOf(sessionOwnerId), 0, 0, 0, 0L, true);
                      db.setTransactionSuccessful();
                      safeFinishTransaction(db);
                      setConversationHidden(conversationRowId, false);
                            } catch (Throwable th) {
                                safeFinishTransaction(db);
                                throw th;
                            }
                        }
              if (!updatedMessages.isEmpty()) {
                  if (LOGGABLE) {
                      Log.d(TAG, "Updating existing messages, count=" + updatedMessages.size());
                            }
                  db.beginTransaction();
                            try {
                      ContentValues values2 = new ContentValues();
                                for (VinePrivateMessage message4 : updatedMessages.values()) {
                          values2.clear();
                          fillMessageValues(values2, conversationRowId, message4);
                          updated += db.update("messages", values2, "message_id=?", new String[]{String.valueOf(message4.messageId)}) > 0 ? 1 : 0;
                          if (message4.post != null) {
                              posts.add(message4.post);
                                    }
                                }
                      mergePosts(posts, 12, String.valueOf(sessionOwnerId), 0, 0, 0, 0L, true);
                      db.setTransactionSuccessful();
                      safeFinishTransaction(db);
                            } catch (Throwable th2) {
                                safeFinishTransaction(db);
                                throw th2;
                            }
                        }
              int deleted = 0;
              if (!contiguous) {
                  SLog.m447d("Deleting messages for conversation row id {}, non contiguous", Long.valueOf(conversationRowId));
                  deleted = db.delete("messages", "conversation_row_id =? AND message_id <? AND message_id > ?", new String[]{String.valueOf(conversationRowId), String.valueOf(oldestMessageId), "0"});
                        }
              if (nextPage > 0 || prevPage > 0 || anchor > 0) {
                  savePageCursor(7, 0, String.valueOf(conversationRowId), nextPage, prevPage, anchor, false);
                        }
              if (inserted > 0 || updated > 0 || deleted > 0) {
                  this.mContentResolver.notifyChange(Vine.Messages.CONTENT_URI, null);
                  this.mContentResolver.notifyChange(Vine.ConversationMessageUsersView.CONTENT_URI, null);
                  this.mContentResolver.notifyChange(Vine.InboxView.CONTENT_URI, null);
                        }
                    } catch (Throwable th3) {
                        throw th3;
                    }
                    return conversationRowId;
                }

      public synchronized void updateConversationWithLastMessage(long conversationRowId, long messageRowId, long created) {
                    try {
                        SQLiteDatabase db = getWritableDatabase();
                        try {
                  db.beginTransaction();
                  ContentValues values = new ContentValues();
                  values.put(Vine.ConversationsColumns.LAST_MESSAGE_ROW_ID, Long.valueOf(messageRowId));
                  values.put(Vine.ConversationsColumns.LAST_MESSAGE_TIMESTAMP, Long.valueOf(created));
                  int updated = db.update("conversations", values, "_id=? ", new String[]{String.valueOf(conversationRowId)});
                  db.setTransactionSuccessful();
                  safeFinishTransaction(db);
                  if (updated > 0) {
                      this.mContentResolver.notifyChange(Vine.InboxView.CONTENT_URI, null);
                      this.mContentResolver.notifyChange(Vine.Conversations.CONTENT_URI, null);
                            }
                        } catch (Throwable th) {
                            safeFinishTransaction(db);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }

      public synchronized long mergeConversationWithLocalId(long conversationRowId, long conversationId, int networkType) {
                    try {
                        SQLiteDatabase db = getWritableDatabase();
              ContentValues values = new ContentValues();
              fillConversationValues(values, Long.valueOf(conversationId), Integer.valueOf(networkType), null, 0L, 0L);
                        try {
                  db.beginTransaction();
                  int updated = db.update("conversations", values, VineDatabaseSQL.SELECTION_ROW_ID, new String[]{String.valueOf(conversationRowId)});
                  if (updated <= 0) {
                      conversationRowId = db.insert("conversations", null, values);
                            }
                  db.setTransactionSuccessful();
                  safeFinishTransaction(db);
                        } catch (Throwable th) {
                            safeFinishTransaction(db);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                    return conversationRowId;
                }

      public synchronized long mergeConversationWithRemoteId(long conversationId, Integer networkType, Boolean hidden, long unreadMessageCount, long lastMessage) {
                    long id;
                    try {
                        SQLiteDatabase db = getWritableDatabase();
              String[] selArgs = {String.valueOf(conversationId)};
                        try {
                  db.beginTransaction();
                  Cursor cursor = db.query("conversations", new String[]{"_id"}, "conversation_id=?", selArgs, null, null, "1");
                  id = -1;
                  if (cursor != null) {
                                try {
                          if (cursor.moveToFirst()) {
                              ContentValues values = new ContentValues();
                              fillConversationValues(values, Long.valueOf(conversationId), networkType, hidden, unreadMessageCount, lastMessage);
                              db.update("conversations", values, "conversation_id=?", new String[]{String.valueOf(conversationId)});
                              id = cursor.getLong(0);
                                    }
                          cursor.close();
                                } catch (Throwable th) {
                                    cursor.close();
                                    throw th;
                                }
                            }
                            if (id == -1) {
                      ContentValues values2 = new ContentValues();
                      fillConversationValues(values2, Long.valueOf(conversationId), networkType, hidden, unreadMessageCount, lastMessage);
                      id = db.insert("conversations", null, values2);
                                if (id == -1) {
                          throw new RuntimeException("Failed to insert conversation.");
                                }
                            }
                  db.setTransactionSuccessful();
                  safeFinishTransaction(db);
                        } catch (Throwable th2) {
                            safeFinishTransaction(db);
                            throw th2;
                        }
                    } catch (Throwable th3) {
                        throw th3;
                    }
                    return id;
                }

      public synchronized long mergeMessageWithMessageRow(long messageRowId, long conversationRowId, VinePrivateMessage message) {
                    long inserted;
                    try {
                        SQLiteDatabase db = getWritableDatabase();
              inserted = -1;
              ContentValues values = new ContentValues();
              fillMessageValues(values, conversationRowId, message);
                        try {
                  db.beginTransaction();
                  long updated = db.update("messages", values, VineDatabaseSQL.SELECTION_ROW_ID, new String[]{String.valueOf(messageRowId)});
                  db.setTransactionSuccessful();
                  safeFinishTransaction(db);
                            if (updated <= 0) {
                      values.put(Vine.MessagesColumns.DELETED, "0");
                      inserted = db.insert("messages", null, values);
                            }
                            if (inserted > 0 || updated > 0) {
                      this.mContentResolver.notifyChange(Vine.ConversationMessageUsersView.CONTENT_URI, null);
                      this.mContentResolver.notifyChange(Vine.InboxView.CONTENT_URI, null);
                            }
                            if (inserted <= 0) {
                                inserted = messageRowId;
                            }
                        } catch (Throwable th) {
                            safeFinishTransaction(db);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                    return inserted;
                }

      public synchronized long mergeMessage(long conversationRowId, VinePrivateMessage message) {
                    long inserted;
                    try {
                        SQLiteDatabase db = getWritableDatabase();
              inserted = -1;
              long updated = -1;
              ContentValues values = new ContentValues();
              fillMessageValues(values, conversationRowId, message);
                        if (-1 == -1 && message.messageId > 0) {
                  String[] selArgs = {String.valueOf(message.messageId)};
                  Cursor cursor = this.mContentResolver.query(Vine.Messages.CONTENT_URI, VineDatabaseSQL.MessagesQuery.PROJECTION, "message_id=?", selArgs, null);
                  if (cursor != null) {
                      if (cursor.moveToFirst()) {
                          long local = cursor.getLong(13);
                                    if (local > message.localStartTime) {
                              values.put(Vine.MessagesColumns.LOCAL_START_TIME, Long.valueOf(local));
                                    }
                                    try {
                              db.beginTransaction();
                              updated = db.update("messages", values, "message_id=?", new String[]{String.valueOf(message.messageId)});
                              db.setTransactionSuccessful();
                              safeFinishTransaction(db);
                                    } catch (Throwable th) {
                                        safeFinishTransaction(db);
                                        throw th;
                                    }
                                }
                      cursor.close();
                            }
                        }
                        if (-1 == -1 && updated <= 0) {
                  values.put(Vine.MessagesColumns.DELETED, "0");
                  inserted = db.insert("messages", null, values);
                        }
                        if (inserted > 0 || updated > 0) {
                  this.mContentResolver.notifyChange(Vine.ConversationMessageUsersView.CONTENT_URI, null);
                  this.mContentResolver.notifyChange(Vine.InboxView.CONTENT_URI, null);
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                    return inserted;
                }

      public synchronized void removeUsersByGroup(int type) {
                    try {
                        SQLiteDatabase db = getWritableDatabase();
              int deleted = db.delete("user_groups", VineDatabaseSQL.SELECTION_USER_GROUP_TYPE, new String[]{String.valueOf(type)});
              if (LOGGABLE) {
                  Log.d(TAG, "Deleted " + deleted + " users of type " + type);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

      public synchronized void removePostsByReposterId(long reposterId) {
                    try {
                        SQLiteDatabase db = getWritableDatabase();
              String[] selArgs = {String.valueOf(reposterId), String.valueOf(1)};
              int deleted = db.delete(Vine.PostGroups.TABLE_NAME, "reposter_id=? AND post_type =?", selArgs);
              if (LOGGABLE) {
                  Log.d(TAG, "Deleted " + deleted + " posts with reposterId=" + reposterId);
                        }
              if (deleted > 0) {
                  this.mContentResolver.notifyChange(Vine.PostGroupsView.CONTENT_URI, null);
                  this.mContentResolver.notifyChange(Vine.PostCommentsLikesView.CONTENT_URI, null);
                  this.mContentResolver.notifyChange(Vine.PostCommentsLikesView.CONTENT_URI_ALL_TIMELINE, null);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

      public synchronized void deleteConversation(long conversationId, long conversationRowId) {
                    try {
                        SQLiteDatabase db = getWritableDatabase();
              int deleted = 0;
                        try {
                  db.beginTransaction();
                            if (conversationId > 0) {
                      deleted = db.delete("conversations", "conversation_id=?", new String[]{String.valueOf(conversationId)});
                            }
                            if (conversationRowId > 0) {
                      deleted = deleted + db.delete("conversations", VineDatabaseSQL.SELECTION_ROW_ID, new String[]{String.valueOf(conversationRowId)}) + db.delete("messages", "conversation_row_id=?", new String[]{String.valueOf(conversationRowId)}) + db.delete(Vine.ConversationRecipients.TABLE_NAME, "conversation_row_id=?", new String[]{String.valueOf(conversationRowId)});
                            }
                  db.setTransactionSuccessful();
                  safeFinishTransaction(db);
                  if (LOGGABLE) {
                      Log.d(TAG, "Deleted conversation " + conversationId);
                            }
                  if (deleted > 0) {
                      notifyVMUris();
                            }
                        } catch (Throwable th) {
                            safeFinishTransaction(db);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }

      private void notifyVMUris() {
          this.mContentResolver.notifyChange(Vine.InboxView.CONTENT_URI, null);
          this.mContentResolver.notifyChange(Vine.ConversationMessageUsersView.CONTENT_URI, null);
          this.mContentResolver.notifyChange(Vine.Messages.CONTENT_URI, null);
          this.mContentResolver.notifyChange(Vine.Conversations.CONTENT_URI, null);
          this.mContentResolver.notifyChange(Vine.ConversationRecipients.CONTENT_URI, null);
                }

      public synchronized void markLastUser(int type, String tag) {
                    try {
                        markLastUser(type, tag, "order_id ASC");
                    } catch (Throwable th) {
                        throw th;
                    }
                }

      public synchronized void markLastUser(int type, String tag, String isLastOrdering) {
                    try {
                        long rowId = getLastUserRowId(type, tag, isLastOrdering);
                        if (rowId != 0) {
                  if (LOGGABLE) {
                      Log.d(TAG, "Tagging the oldest user of type: " + type + " row id: " + rowId);
                            }
                  SQLiteDatabase db = getWritableDatabase();
                  ContentValues values = new ContentValues();
                  values.put("is_last", (Integer) 0);
                            try {
                      db.beginTransaction();
                      db.update("user_groups", values, "is_last=1", null);
                      values.put("is_last", (Integer) 1);
                      db.update("user_groups", values, VineDatabaseSQL.SELECTION_ROW_ID, new String[]{String.valueOf(rowId)});
                      db.setTransactionSuccessful();
                      safeFinishTransaction(db);
                      this.mContentResolver.notifyChange(Vine.UserGroupsView.CONTENT_URI, null);
                            } catch (Throwable th) {
                                safeFinishTransaction(db);
                                throw th;
                            }
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }

      public synchronized void markLastPost(int type, String tag) {
                    try {
                        long rowId = getLastPostRowId(type, tag);
                        if (rowId != 0) {
                  SQLiteDatabase db = getWritableDatabase();
                  if (LOGGABLE) {
                      Log.d(TAG, "Tagging the oldest post of type: " + type + " and tag: " + tag + " row id: " + rowId);
                            }
                  ContentValues values = new ContentValues();
                  values.put("is_last", (Integer) 0);
                            try {
                      db.beginTransaction();
                      db.update(Vine.PostGroups.TABLE_NAME, values, "is_last=1", null);
                      values.put("is_last", (Integer) 1);
                      db.update(Vine.PostGroups.TABLE_NAME, values, VineDatabaseSQL.SELECTION_ROW_ID, new String[]{String.valueOf(rowId)});
                      db.setTransactionSuccessful();
                      safeFinishTransaction(db);
                      notifyPostCommentsViewUris();
                            } catch (Throwable th) {
                                safeFinishTransaction(db);
                                throw th;
                            }
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }

      public synchronized void markLastComment(long postId) {
                    try {
                        long rowId = getLastCommentRowId(postId);
                        if (rowId != 0) {
                  SQLiteDatabase db = getWritableDatabase();
                  ContentValues values = new ContentValues();
                  values.put("is_last", (Integer) 0);
                            try {
                      db.beginTransaction();
                      db.update("comments", values, "is_last=1 AND post_id=?", new String[]{String.valueOf(postId)});
                      values.put("is_last", (Integer) 1);
                      db.update("comments", values, VineDatabaseSQL.SELECTION_ROW_ID, new String[]{String.valueOf(rowId)});
                      db.setTransactionSuccessful();
                      safeFinishTransaction(db);
                            } catch (Throwable th) {
                                safeFinishTransaction(db);
                                throw th;
                            }
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }

      public synchronized void markLastMessage(long conversationRowId) {
                    try {
                        long rowId = getLastMessageRowId(conversationRowId);
                        if (rowId != 0) {
                  SQLiteDatabase db = getWritableDatabase();
                  ContentValues values = new ContentValues();
                  values.put("is_last", (Integer) 0);
                            try {
                      db.beginTransaction();
                      db.update("messages", values, "is_last=1 AND conversation_row_id=?", new String[]{String.valueOf(conversationRowId)});
                      values.put("is_last", (Integer) 1);
                      db.update("messages", values, VineDatabaseSQL.SELECTION_ROW_ID, new String[]{String.valueOf(rowId)});
                      db.setTransactionSuccessful();
                      safeFinishTransaction(db);
                            } catch (Throwable th) {
                                safeFinishTransaction(db);
                                throw th;
                            }
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }

      public synchronized void markLastTag() {
                    try {
                        long rowId = getLastTagRowId();
                        if (rowId != 0) {
                  if (LOGGABLE) {
                      Log.d(TAG, "Tagging the oldest tag with row id: " + rowId);
                            }
                  SQLiteDatabase db = getWritableDatabase();
                  ContentValues values = new ContentValues();
                  values.put("is_last", (Integer) 0);
                            try {
                      db.beginTransaction();
                      db.update(Vine.TagSearchResults.TABLE_NAME, values, "is_last=1", null);
                      values.put("is_last", (Integer) 1);
                      db.update(Vine.TagSearchResults.TABLE_NAME, values, VineDatabaseSQL.SELECTION_ROW_ID, new String[]{String.valueOf(rowId)});
                      db.setTransactionSuccessful();
                      this.mContentResolver.notifyChange(Vine.TagSearchResults.CONTENT_URI, null);
                      safeFinishTransaction(db);
                            } catch (Throwable th) {
                                safeFinishTransaction(db);
                                throw th;
                            }
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }

      public synchronized void markLastChannel() {
                    try {
                        long rowId = getLastChannelRowId();
                        if (rowId != 0) {
                  if (LOGGABLE) {
                      Log.d(TAG, "Tagging the oldest channel with row id: " + rowId);
                            }
                  SQLiteDatabase db = getWritableDatabase();
                  ContentValues values = new ContentValues();
                  values.put("is_last", (Integer) 0);
                            try {
                      db.beginTransaction();
                      db.update("channels", values, "is_last=1", null);
                      values.put("is_last", (Integer) 1);
                      db.update("channels", values, VineDatabaseSQL.SELECTION_ROW_ID, new String[]{String.valueOf(rowId)});
                      safeFinishTransaction(db);
                      this.mContentResolver.notifyChange(Vine.Channels.CONTENT_URI, null);
                            } catch (Throwable th) {
                                safeFinishTransaction(db);
                                throw th;
                            }
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }

      public synchronized void markChannelLastUsedTimestamp(long channelId) {
                    try {
                        SQLiteDatabase db = getWritableDatabase();
              ContentValues values = new ContentValues();
              values.put("last_used_timestamp", Long.valueOf(System.currentTimeMillis()));
              db.update("channels", values, "channel_id=?", new String[]{String.valueOf(channelId)});
                    } catch (Throwable th) {
                        throw th;
                    }
                }

      public synchronized void markLastConversation(int networkType) {
                    try {
                        long rowId = getLastConversationId(networkType);
                        if (rowId != 0) {
                  SQLiteDatabase db = getWritableDatabase();
                  ContentValues values = new ContentValues();
                  values.put("is_last", (Integer) 0);
                            try {
                      db.beginTransaction();
                      db.update("conversations", values, "is_last=1 AND network_type=?", new String[]{String.valueOf(networkType)});
                      values.put("is_last", (Integer) 1);
                      db.update("conversations", values, VineDatabaseSQL.SELECTION_ROW_ID, new String[]{String.valueOf(rowId)});
                      db.setTransactionSuccessful();
                      safeFinishTransaction(db);
                            } catch (Throwable th) {
                                safeFinishTransaction(db);
                                throw th;
                            }
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }

                
                
      public synchronized int saveUserValue(String propName, ContentValues values) {
                    int i = 0;
                    synchronized (this) {
                        try {
                            SQLiteDatabase db = getWritableDatabase();
                  if (db.update("settings", values, SETTINGS_NAME_SELECTION, new String[]{propName}) == 0) {
                      values.put("name", propName);
                                if (db.insert("settings", "name", values) != 0) {
                          if (this.mContentResolver == null) {
                              this.mContentResolver = this.mAppContext.getContentResolver();
                                    }
                          this.mContentResolver.notifyChange(Vine.Settings.CONTENT_URI, null);
                                    i = 1;
                                }
                            } else {
                      if (this.mContentResolver == null) {
                          this.mContentResolver = this.mAppContext.getContentResolver();
                                }
                      this.mContentResolver.notifyChange(Vine.Settings.CONTENT_URI, null);
                                i = 1;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return i;
                }

      public synchronized int savePageCursor(int kind, int type, String tag, int nextPage, int prevPage, long anchor, boolean reversed) {
                    int num;
                    try {
                        SQLiteDatabase db = getWritableDatabase();
              if (tag == null) {
                  tag = "";
                        }
              String[] selectionArgs = {String.valueOf(kind), String.valueOf(type), tag};
              Cursor c = db.query(Vine.PageCursors.TABLE_NAME, VineDatabaseSQL.PageCursorsQuery.PROJECTION, VineDatabaseSQL.SELECTION_PAGE_CURSOR_KIND_TYPE_TAG, selectionArgs, null, null, null);
              ContentValues values = new ContentValues();
              values.put(Vine.PageCursors.KIND, Integer.valueOf(kind));
              values.put("type", Integer.valueOf(type));
              values.put("tag", tag);
              values.put("next_page", Integer.valueOf(nextPage));
              values.put("previous_page", Integer.valueOf(prevPage));
              values.put("anchor", Long.valueOf(anchor));
              values.put(Vine.PageCursors.REVERSED, Boolean.valueOf(reversed));
              if (c == null) {
                  num = 0 + (db.insert(Vine.PageCursors.TABLE_NAME, "_id", values) > 0 ? 1 : 0);
                        } else {
                            try {
                      if (c.getCount() == 1) {
                          num = 0 + (db.update(Vine.PageCursors.TABLE_NAME, values, VineDatabaseSQL.SELECTION_PAGE_CURSOR_KIND_TYPE_TAG, selectionArgs) > 0 ? 1 : 0);
                                } else {
                          num = 0 + (db.insert(Vine.PageCursors.TABLE_NAME, "_id", values) > 0 ? 1 : 0);
                                }
                      c.close();
                            } catch (Throwable th) {
                                c.close();
                                throw th;
                            }
                        }
              if (LOGGABLE && num > 0) {
                  Log.d(TAG, "Saved pagecursor of kind: " + kind + " type: " + type + " tag: " + tag + " next:" + nextPage + " prev: " + prevPage + " anchor: " + anchor + " reverse: " + reversed);
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                    return num;
                }

      public synchronized int getNextPageCursor(int kind, int type, String tag, boolean reversed) {
                    String column;
                    int result;
                    try {
                        SQLiteDatabase db = getReadableDatabase();
              if (reversed) {
                  column = "previous_page";
                        } else {
                  column = "next_page";
                        }
              if (tag == null) {
                  tag = "";
                        }
              Cursor c = db.query(Vine.PageCursors.TABLE_NAME, new String[]{column}, VineDatabaseSQL.SELECTION_PAGE_CURSOR_KIND_TYPE_TAG, new String[]{String.valueOf(kind), String.valueOf(type), String.valueOf(tag)}, null, null, null);
              result = -1;
              if (c != null) {
                            try {
                      if (c.moveToFirst()) {
                          result = c.getInt(0);
                                }
                      c.close();
                            } catch (Throwable th) {
                                c.close();
                                throw th;
                            }
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                    return result;
                }

      public synchronized int getPreviousPageCursor(int kind, int type, String tag, boolean reversed) {
                    String column;
                    int result;
                    try {
                        SQLiteDatabase db = getReadableDatabase();
              if (reversed) {
                  column = "next_page";
                        } else {
                  column = "previous_page";
                        }
              if (tag == null) {
                  tag = "";
                        }
              Cursor c = db.query(Vine.PageCursors.TABLE_NAME, new String[]{column}, VineDatabaseSQL.SELECTION_PAGE_CURSOR_KIND_TYPE_TAG, new String[]{String.valueOf(kind), String.valueOf(type), String.valueOf(tag)}, null, null, null);
              result = -1;
              if (c != null) {
                            try {
                      if (c.moveToFirst()) {
                          int index = c.getColumnIndex(column);
                          result = c.getInt(index);
                                }
                      c.close();
                            } catch (Throwable th) {
                                c.close();
                                throw th;
                            }
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                    return result;
                }

      public synchronized long getPageAnchor(int kind, int type, String tag) {
                    long result;
                    try {
                        SQLiteDatabase db = getReadableDatabase();
              Cursor c = db.query(Vine.PageCursors.TABLE_NAME, new String[]{"anchor"}, VineDatabaseSQL.SELECTION_PAGE_CURSOR_KIND_TYPE_TAG, new String[]{String.valueOf(kind), String.valueOf(type), String.valueOf(tag)}, null, null, null);
              result = -1;
              if (c != null) {
                            try {
                      if (c.getCount() > 0 && c.moveToFirst()) {
                          result = c.getLong(0);
                                }
                      c.close();
                            } catch (Throwable th) {
                                c.close();
                                throw th;
                            }
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                    return result;
                }

      public synchronized void clearAllData() {
                    try {
                        SQLiteDatabase db = getWritableDatabase();
              db.delete("settings", null, null);
              db.delete("user_groups", null, null);
              db.delete("posts", null, null);
              db.delete("likes", null, null);
              db.delete(Vine.PostGroups.TABLE_NAME, null, null);
              db.delete("comments", null, null);
              db.delete(Vine.PageCursors.TABLE_NAME, null, null);
              db.delete(Vine.TagSearchResults.TABLE_NAME, null, null);
              db.delete("channels", null, null);
              db.delete("notifications", null, null);
              db.delete(Vine.TagsRecentlyUsed.TABLE_NAME, null, null);
              db.delete("messages", null, null);
              db.delete(Vine.ConversationRecipients.TABLE_NAME, null, null);
              db.delete("conversations", null, null);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

      public synchronized void clearCachedData() {
                    try {
                        SQLiteDatabase db = getWritableDatabase();
                        try {
                  db.beginTransaction();
                  db.delete("users", null, null);
                  db.delete("user_groups", null, null);
                  db.delete("posts", null, null);
                  db.delete("likes", null, null);
                  db.delete(Vine.PostGroups.TABLE_NAME, null, null);
                  db.delete("comments", null, null);
                  db.delete(Vine.PageCursors.TABLE_NAME, null, null);
                  db.delete(Vine.TagSearchResults.TABLE_NAME, null, null);
                  db.delete("channels", null, null);
                  db.delete("notifications", null, null);
                  db.delete(Vine.TagsRecentlyUsed.TABLE_NAME, null, null);
                  db.delete("messages", null, null);
                  db.delete(Vine.ConversationRecipients.TABLE_NAME, null, null);
                  db.delete("conversations", null, null);
                  db.setTransactionSuccessful();
                  safeFinishTransaction(db);
                        } catch (Throwable th) {
                            safeFinishTransaction(db);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }

      public synchronized void clearCachedData(boolean notify) {
                    try {
                        clearCachedData();
              if (notify) {
                  notifyPostCommentsViewUris();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

      private long getLastUserRowId(int type, String tag, String isLastOrdering) {
                    String selection;
                    String[] selectionArgs;
          SQLiteDatabase db = getReadableDatabase();
          if (tag != null) {
              selection = VineDatabaseSQL.SELECTION_USER_GROUP_TYPE_AND_TAG;
              selectionArgs = new String[]{String.valueOf(type), tag};
                    } else {
              selection = VineDatabaseSQL.SELECTION_USER_GROUP_TYPE;
              selectionArgs = new String[]{String.valueOf(type)};
                    }
          Cursor cursor = db.query("user_groups_view", VineDatabaseSQL.UsersQuery.PROJECTION, selection, selectionArgs, null, null, isLastOrdering, "1");
          if (cursor != null) {
                        try {
                  if (cursor.moveToFirst()) {
                      long j = cursor.getLong(0);
                      cursor.close();
                      return j;
                            }
                  cursor.close();
                        } catch (Throwable th) {
                            cursor.close();
                            throw th;
                        }
                    }
          return 0L;
                }

      private long getLastPostRowId(int type, String tag) {
          if (tag != null) {
              SQLiteDatabase db = getReadableDatabase();
              Cursor cursor = db.query(Vine.PostCommentsLikesView.TABLE_NAME, VineDatabaseSQL.PostCommentsLikesQuery.PROJECTION, VineDatabaseSQL.SELECTION_POST_GROUP_TYPE_AND_TAG, new String[]{String.valueOf(type), tag}, null, null, Vine.PostCommentsLikesView.SORT_ORDER_DEFINED_LAST, "1");
              if (cursor != null) {
                            try {
                      if (cursor.moveToFirst()) {
                          long j = cursor.getLong(0);
                          cursor.close();
                          return j;
                                }
                      cursor.close();
                            } catch (Throwable th) {
                                cursor.close();
                                throw th;
                            }
                        }
                    } else {
              CrashUtil.logException(new VineLoggingException("Tag was null when trying to mark last row."));
                    }
          return 0L;
                }

      private long getLastCommentRowId(long postId) {
          SQLiteDatabase db = getReadableDatabase();
          Cursor cursor = db.query("comments", VineDatabaseSQL.CommentsQuery.PROJECTION, "post_id =? ", new String[]{String.valueOf(postId)}, null, null, Vine.Comments.SORT_ORDER_DEFAULT, "1");
          if (cursor != null) {
                        try {
                  if (cursor.moveToFirst()) {
                      long j = cursor.getLong(0);
                      cursor.close();
                      return j;
                            }
                  cursor.close();
                        } catch (Throwable th) {
                            cursor.close();
                            throw th;
                        }
                    }
          return 0L;
                }

      private long getLastMessageRowId(long conversationRowId) {
          SQLiteDatabase db = getReadableDatabase();
          Cursor cursor = db.query("messages", VineDatabaseSQL.MessagesQuery.PROJECTION, "conversation_row_id =? AND deleted != 1 ", new String[]{String.valueOf(conversationRowId)}, null, null, Vine.Messages.SORT_ORDER_DEFAULT, "1");
          if (cursor != null) {
                        try {
                  if (cursor.moveToFirst()) {
                      long j = cursor.getLong(0);
                      cursor.close();
                      return j;
                            }
                  cursor.close();
                        } catch (Throwable th) {
                            cursor.close();
                            throw th;
                        }
                    }
          return 0L;
                }

      private long getLastTagRowId() {
          SQLiteDatabase db = getReadableDatabase();
          Cursor cursor = db.query(Vine.TagSearchResults.TABLE_NAME, VineDatabaseSQL.TagsQuery.PROJECTION, null, null, null, null, "_id DESC", "1");
          if (cursor != null) {
                        try {
                  if (cursor.moveToFirst()) {
                      long j = cursor.getLong(0);
                      cursor.close();
                      return j;
                            }
                  cursor.close();
                        } catch (Throwable th) {
                            cursor.close();
                            throw th;
                        }
                    }
          return 0L;
                }

      private long getLastChannelRowId() {
          SQLiteDatabase db = getReadableDatabase();
          Cursor cursor = db.query("channels", VineDatabaseSQL.ChannelsQuery.PROJECTION, null, null, null, null, Vine.Channels.SORT_ORDER_IS_LAST, "1");
          if (cursor != null) {
                        try {
                  if (cursor.moveToFirst()) {
                      long j = cursor.getLong(0);
                      cursor.close();
                      return j;
                            }
                  cursor.close();
                        } catch (Throwable th) {
                            cursor.close();
                            throw th;
                        }
                    }
          return 0L;
                }

      private long getLastConversationId(int networkType) {
          SQLiteDatabase db = getReadableDatabase();
          Cursor cursor = db.query("conversations", new String[]{"_id"}, "last_message>0 AND network_type =? ", new String[]{String.valueOf(networkType)}, null, null, Vine.Conversations.SORT_ORDER_DEFAULT_IS_LAST, "1");
          if (cursor != null) {
                        try {
                  if (cursor.moveToFirst()) {
                      long j = cursor.getLong(0);
                      cursor.close();
                      return j;
                            }
                  cursor.close();
                        } catch (Throwable th) {
                            cursor.close();
                            throw th;
                        }
                    }
          return 0L;
                }

      public boolean shouldHideProfileRevines(long userId) {
          SQLiteDatabase db = getReadableDatabase();
          boolean ret = false;
          String[] selArgs = {String.valueOf(userId)};
          Cursor cursor = db.query("users", new String[]{"hide_profile_reposts"}, VineDatabaseSQL.SELECTION_USER_ID, selArgs, null, null, null);
          if (cursor != null) {
                        try {
                  if (cursor.moveToFirst()) {
                      ret = cursor.getInt(0) > 0;
                            }
                  cursor.close();
                        } catch (Throwable th) {
                            cursor.close();
                            throw th;
                        }
                    }
          return ret;
                }

      public boolean setShouldHideProfileRevines(long userId, boolean hide) {
          SQLiteDatabase db = getWritableDatabase();
          String[] selArgs = {String.valueOf(userId)};
          ContentValues values = new ContentValues();
          values.put("hide_profile_reposts", Integer.valueOf(hide ? 1 : 0));
          db.beginTransaction();
                    try {
              int updated = db.update("users", values, VineDatabaseSQL.SELECTION_USER_ID, selArgs);
              db.setTransactionSuccessful();
              safeFinishTransaction(db);
                        return updated > 0;
                    } catch (Throwable th) {
                        safeFinishTransaction(db);
                        throw th;
                    }
                }

      private void fillUserContentValues(ContentValues values, VineUser user, long lastRefresh) {
          values.put("user_id", Long.valueOf(user.userId));
          values.put("avatar_url", user.avatarUrl);
          values.put("username", user.username);
          values.put("blocked", Integer.valueOf(user.blocked));
          values.put(Vine.UserColumns.BLOCKING, Integer.valueOf(user.blocking));
          values.put("description", user.description);
          values.put("location", user.location);
          values.put("explicit", Integer.valueOf(user.explicit));
          values.put(Vine.UserColumns.EXTERNAL, Integer.valueOf(user.external ? 1 : 0));
          values.put(Vine.UserColumns.FOLLOWER_COUNT, Integer.valueOf(user.followerCount));
          values.put(Vine.UserColumns.FOLLOWING_COUNT, Integer.valueOf(user.followingCount));
          if (!TextUtils.isEmpty(user.email)) {
              values.put(Vine.UserColumns.EMAIL_ADDRESS, user.email);
                    }
          if (!TextUtils.isEmpty(user.phoneNumber)) {
              values.put("phone_number", user.phoneNumber);
                    }
          if (user.following != -1) {
              values.put("following_flag", Integer.valueOf(user.following));
                    }
          values.put(Vine.UserColumns.LIKE_COUNT, Integer.valueOf(user.likeCount));
          values.put(Vine.UserColumns.POST_COUNT, Integer.valueOf(user.postCount));
          values.put(Vine.UserColumns.LOOP_COUNT, Long.valueOf(user.loopCount));
          values.put("verified", Integer.valueOf(user.verified));
          values.put(Vine.UserColumns.FOLLOW_STATUS, Integer.valueOf(user.followStatus));
          values.put("last_refresh", Long.valueOf(lastRefresh));
          if (user.profileBackground > -1) {
              values.put("profile_background", Integer.valueOf(user.profileBackground));
                    }
          values.put(Vine.UserColumns.ACCEPTS_OON_CONVERSATIONS, Integer.valueOf(user.acceptsOutOfNetworkConversations ? 1 : 0));
                }

      private void fillPostValues(ContentValues values, VinePost post, long lastRefresh) throws IOException {
          values.put("avatar_url", post.avatarUrl);
          values.put("description", post.description);
          values.put(Vine.PostColumns.FOURSQUARE_VENUE_ID, post.foursquareVenueId);
          values.put(Vine.PostColumns.METADATA_FLAGS, Integer.valueOf(post.metadataFlags));
          values.put("location", post.location);
          values.put(Vine.PostColumns.POST_FLAGS, Integer.valueOf(post.postFlags));
          values.put("post_id", Long.valueOf(post.postId));
          values.put("my_repost_id", Long.valueOf(post.myRepostId));
          values.put("share_url", post.shareUrl);
          values.put("thumbnail_url", post.thumbnailUrl);
          values.put("video_url", post.videoUrl);
          values.put(Vine.PostColumns.VIDEO_LOW_URL, post.videoLowUrl);
          values.put("timestamp", Long.valueOf(post.created));
          values.put("username", post.username);
          values.put("user_id", Long.valueOf(post.userId));
          values.put(Vine.PostColumns.LIKES_COUNT, Integer.valueOf(post.likesCount));
          values.put(Vine.PostColumns.REVINERS_COUNT, Integer.valueOf(post.revinersCount));
          values.put(Vine.PostColumns.COMMENTS_COUNT, Integer.valueOf(post.commentsCount));
          values.put(Vine.PostColumns.USER_BACKGROUND_COLOR, Integer.valueOf(post.userBackgroundColor));
          values.put("tags", VinePost.getBytesFromTags(post));
          values.put("loops", Long.valueOf(post.loops));
          values.put(Vine.PostColumns.VELOCITY, Double.valueOf(post.velocity));
          values.put(Vine.PostColumns.ON_FIRE, Boolean.valueOf(post.onFire));
          if (post.venueData != null) {
              values.put(Vine.PostColumns.VENUE_DATA, Util.toByteArray(post.venueData));
                    }
          if (post.entities != null) {
              values.put("entities", Util.toByteArray(post.entities));
                    }
          values.put("last_refresh", Long.valueOf(lastRefresh));
                }

      private void fillLikeValues(ContentValues values, VineLike like, long lastRefresh) {
          values.put(Vine.LikesColumns.LIKE_ID, Long.valueOf(like.likeId));
          values.put("user_id", Long.valueOf(like.userId));
          values.put("post_id", Long.valueOf(like.postId));
          values.put("avatar_url", like.avatarUrl);
          values.put("timestamp", Long.valueOf(like.created));
          values.put("location", like.location);
          values.put("username", like.username);
          values.put("verified", Integer.valueOf(like.verified));
          values.put("last_refresh", Long.valueOf(lastRefresh));
                }

      private void fillNotificationValues(ContentValues values, VineSingleNotification notification, String message) {
          values.put("avatar_url", notification.avatarUrl);
          values.put("notification_id", Long.valueOf(notification.notificationId));
          values.put(Vine.NotificationsColumns.NOTIFICATION_TYPE, Integer.valueOf(notification.notificationTypeId));
          values.put("conversation_row_id", Long.valueOf(notification.conversationRowId));
          values.put("message", message);
                }

      private void fillConversationValues(ContentValues values, Long conversationId, Integer networkType, Boolean hidden, long unreadMessageCount, long lastMessage) {
          if (conversationId != null) {
              values.put("conversation_id", conversationId);
                    }
          if (networkType != null) {
              values.put("network_type", networkType);
                    }
          if (hidden != null) {
              values.put(Vine.ConversationsColumns.IS_HIDDEN, Integer.valueOf(hidden.booleanValue() ? 1 : 0));
                    }
                    if (unreadMessageCount > -1) {
              values.put(Vine.ConversationsColumns.UNREAD_MESSAGE_COUNT, Long.valueOf(unreadMessageCount));
                    }
                    if (lastMessage > 0) {
              values.put(Vine.ConversationsColumns.LAST_MESSAGE_ROW_ID, Long.valueOf(lastMessage));
                    }
                }

      private void fillMessageValues(ContentValues values, long conversationRowId, VinePrivateMessage message) {
          values.put("conversation_row_id", Long.valueOf(conversationRowId));
          values.put("message_id", Long.valueOf(message.messageId));
          values.put("user_row_id", Long.valueOf(message.userId));
          values.put("message", message.message);
          values.put("timestamp", Long.valueOf(message.created));
          if (!TextUtils.isEmpty(message.videoUrl)) {
              values.put("video_url", message.videoUrl);
                    }
          if (!TextUtils.isEmpty(message.thumbnailUrl)) {
              values.put("thumbnail_url", message.thumbnailUrl);
                    }
          values.put("is_last", Boolean.valueOf(message.isLast));
          values.put(Vine.MessagesColumns.VANISHED, Boolean.valueOf(message.vanished));
          values.put("max_loops", Integer.valueOf(message.maxLoops));
          values.put(Vine.MessagesColumns.VANISHED_DATE, Long.valueOf(message.vanishedDate));
          values.put(Vine.MessagesColumns.EPHEMERAL, Boolean.valueOf(message.ephemeral));
          values.put(Vine.MessagesColumns.LOCAL_START_TIME, Long.valueOf(message.localStartTime));
          values.put("post_id", Long.valueOf(message.postId));
          values.put("error_code", Integer.valueOf(message.errorCode));
          values.put(Vine.MessagesColumns.ERROR_REASON, message.errorReason);
          if (!TextUtils.isEmpty(message.uploadPath)) {
              values.put("upload_path", message.uploadPath);
                    }
                }

      private void fillTagValues(ContentValues values, VineTag tag) {
          values.put(Vine.TagsColumns.TAG_ID, Long.valueOf(tag.tagId));
          values.put("tag_name", tag.tagName);
          values.put("last_used_timestamp", Long.valueOf(System.currentTimeMillis()));
                }

      private void fillCommentValues(ContentValues values, VineComment comment, long lastRefresh) {
          values.put("comment_id", Long.valueOf(comment.commentId));
          values.put("user_id", Long.valueOf(comment.userId));
          values.put("post_id", Long.valueOf(comment.postId));
          values.put("avatar_url", comment.avatarUrl);
          values.put("comment", comment.comment);
          values.put("timestamp", Long.valueOf(comment.timestamp));
          values.put("location", comment.location);
          values.put("username", comment.username);
          values.put("verified", Boolean.valueOf(comment.verified));
          if (comment.entities != null) {
              values.put("entities", Util.toByteArray(comment.entities));
                    }
          values.put("last_refresh", Long.valueOf(lastRefresh));
          values.put("is_last", Boolean.valueOf(comment.isLast));
                }

      private void fillChannelValues(ContentValues values, VineChannel channel) {
          values.put("channel_id", Long.valueOf(channel.channelId));
          values.put("channel", channel.channel);
          values.put(Vine.ChannelsColumns.BACKGROUND_COLOR, channel.backgroundColor);
          values.put(Vine.ChannelsColumns.FONT_COLOR, channel.fontColor);
          values.put("timestamp", Long.valueOf(channel.created));
          values.put(Vine.ChannelsColumns.ICON_FULL_URL, channel.iconFullUrl);
          values.put(Vine.ChannelsColumns.RETINA_ICON_FULL_URL, channel.retinaIconFullUrl);
          values.put("is_last", Boolean.valueOf(channel.isLast));
          values.put("following", Boolean.valueOf(channel.following));
                }

      public static String getDatabaseName(int schemaVersion) {
          return "vine-" + schemaVersion;
                }

      private void notifyPostCommentsViewUris() {
          this.mContentResolver.notifyChange(Vine.PostCommentsLikesView.CONTENT_URI_ALL_TIMELINE, null);
          this.mContentResolver.notifyChange(Vine.PostCommentsLikesView.CONTENT_URI_ALL_TIMELINE_USER, null);
          this.mContentResolver.notifyChange(Vine.PostCommentsLikesView.CONTENT_URI_ALL_TIMELINE_USER_LIKES, null);
          this.mContentResolver.notifyChange(Vine.PostCommentsLikesView.CONTENT_URI_ALL_TIMELINE_SINGLE, null);
                }

      private void notifyByType(int groupType) {
                    switch (groupType) {
                        case 4:
                  this.mContentResolver.notifyChange(Vine.PostCommentsLikesView.CONTENT_URI_ALL_TIMELINE_ON_THE_RISE, null);
                            break;
                        case 5:
                  this.mContentResolver.notifyChange(Vine.PostCommentsLikesView.CONTENT_URI_ALL_TIMELINE_POPULAR_NOW, null);
                            break;
                        case 6:
                  this.mContentResolver.notifyChange(Vine.PostCommentsLikesView.CONTENT_URI_ALL_TIMELINE_TAG, null);
                            break;
                        case 7:
                        default:
                  notifyPostCommentsViewUris();
                            break;
                        case 8:
                  this.mContentResolver.notifyChange(Vine.PostCommentsLikesView.CONTENT_URI_ALL_TIMELINE_CHANNEL_POPULAR, null);
                            break;
                        case 9:
                  this.mContentResolver.notifyChange(Vine.PostCommentsLikesView.CONTENT_URI_ALL_TIMELINE_CHANNEL_RECENT, null);
                            break;
                    }
                }

      public void safeFinishTransaction(SQLiteDatabase db) {
          if (db.inTransaction()) {
                        try {
                  db.endTransaction();
                        } catch (SQLiteDiskIOException e) {
                  throw e;
                        } catch (SQLiteFullException e2) {
                  long currentSize = -1;
                            try {
                      currentSize = Util.getCacheSize(this.mAppContext);
                            } catch (VineLoggingException e3) {
                      CrashUtil.logException(e2);
                            }
                  AppController.clearFileCache(this.mAppContext);
                            try {
                      CrashUtil.logException(new VineLoggingException(), "Db full, cleaned cache from {} to {}.", Long.valueOf(currentSize), Long.valueOf(Util.getCacheSize(this.mAppContext)));
                            } catch (VineLoggingException e4) {
                      CrashUtil.logException(new VineLoggingException(), "Db full, cleaned cache from {} to unknown.", Long.valueOf(currentSize));
                            }
                  if (db.inTransaction()) {
                      db.endTransaction();
                            }
                        } catch (SQLiteException e5) {
                  if (!e5.getMessage().contains("(code 1)")) {
                      throw e5;
                            }
                        } catch (IllegalStateException e6) {
                  if (!e6.getMessage().contains("already-closed")) {
                      throw e6;
                            }
                        }
                    }
                }

      public synchronized void mergeEditions(ArrayList<Pair<String, String>> items) {
                    try {
                        SQLiteDatabase db = getWritableDatabase();
              db.delete("editions", null, null);
              int numInserted = 0;
              if (!items.isEmpty()) {
                  db.beginTransaction();
                            try {
                      ContentValues values = new ContentValues();
                                for (Pair<String, String> edition : items) {
                          values.put(Vine.Editions.EDITION_CODE, (String) edition.first);
                          values.put(Vine.Editions.EDITION_NAME, (String) edition.second);
                          numInserted = (int) (((long) numInserted) + db.insert("editions", null, values));
                                }
                      db.setTransactionSuccessful();
                      safeFinishTransaction(db);
                            } catch (Throwable th) {
                                safeFinishTransaction(db);
                                throw th;
                            }
                        }
              if (numInserted > 0) {
                  this.mContentResolver.notifyChange(Vine.Editions.CONTENT_URI, null);
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }

      public synchronized void updateMessageLoopStartTime(long localStartTime, long messageId) {
                    synchronized (this) {
                        try {
                            SQLiteDatabase db = getWritableDatabase();
                  db.beginTransaction();
                            try {
                      ContentValues values = new ContentValues();
                      values.put(Vine.MessagesColumns.LOCAL_START_TIME, Long.valueOf(localStartTime));
                      int r = db.update("messages", values, "message_id=?", new String[]{String.valueOf(messageId)});
                      if (SLog.sLogsOn) {
                          SLog.m449d("Updated {} local start time to {} ? {}.", Long.valueOf(messageId), Long.valueOf(localStartTime), Boolean.valueOf(r == 1));
                                }
                      db.setTransactionSuccessful();
                      safeFinishTransaction(db);
                            } catch (Throwable th) {
                                safeFinishTransaction(db);
                                throw th;
                            }
                        } catch (Throwable th2) {
                            throw th2;
                        }
                    }
                }

      public synchronized long getConversationRemoteId(long conversationRowId) {
                    long id;
                    try {
                        SQLiteDatabase db = getReadableDatabase();
              Cursor c = db.query("conversations", new String[]{"conversation_id"}, "_id =?", new String[]{String.valueOf(conversationRowId)}, null, null, null, "1");
              if (c != null) {
                  id = -1;
                  if (c.moveToFirst()) {
                      id = c.getLong(0);
                            }
                  c.close();
                        } else {
                            id = -1;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return id;
                }

      public synchronized long getConversationRowId(long conversationRemoteId) {
                    long id;
                    try {
                        SQLiteDatabase db = getReadableDatabase();
              Cursor c = db.query("conversations", new String[]{"_id"}, "conversation_id =?", new String[]{String.valueOf(conversationRemoteId)}, null, null, null, "1");
              if (c != null) {
                  id = -1;
                  if (c.moveToFirst()) {
                      id = c.getLong(0);
                            }
                  c.close();
                        } else {
                            id = -1;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return id;
                }

      public synchronized long getLastMessageIdInConversation(long conversationRowId) {
                    long ret;
                    try {
                        SQLiteDatabase db = getWritableDatabase();
              Cursor c = db.query("messages", new String[]{"message_id"}, "conversation_row_id =? ", new String[]{String.valueOf(conversationRowId)}, null, null, Vine.Messages.SORT_ORDER_REVERSE, "1");
              ret = -1;
              if (c != null) {
                  if (c.moveToFirst()) {
                      ret = c.getLong(0);
                            }
                  c.close();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return ret;
                }

      public synchronized void clearUnreadCount(long conversationRowId) {
                    try {
                        SQLiteDatabase db = getWritableDatabase();
                        try {
                  db.beginTransaction();
                  ContentValues values = new ContentValues(1);
                  values.put(Vine.ConversationsColumns.UNREAD_MESSAGE_COUNT, (Integer) 0);
                  String[] whereArgs = {String.valueOf(conversationRowId)};
                  int updated = db.update("conversations", values, VineDatabaseSQL.SELECTION_ROW_ID, whereArgs);
                  db.setTransactionSuccessful();
                  safeFinishTransaction(db);
                  if (updated > 0) {
                      this.mContentResolver.notifyChange(Vine.ConversationMessageUsersView.CONTENT_URI, null);
                      this.mContentResolver.notifyChange(Vine.InboxView.CONTENT_URI, null);
                            }
                        } catch (Throwable th) {
                            safeFinishTransaction(db);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }

      public synchronized void deleteMessage(long messageId) {
                    try {
                        SQLiteDatabase db = getWritableDatabase();
              ContentValues values = new ContentValues();
              values.put(Vine.MessagesColumns.DELETED, (Integer) 1);
              String messageIdString = String.valueOf(messageId);
              int updated = db.update("messages", values, "message_id=?", new String[]{messageIdString});
              Cursor c = db.query("messages", new String[]{"conversation_row_id", "is_last"}, "message_id=?", new String[]{messageIdString}, null, null, null);
              if (c != null) {
                            try {
                      if (c.moveToFirst()) {
                          long conversationRowId = c.getLong(0);
                          boolean isLast = c.getInt(1) == 1;
                          if (getNumberVisibleMessagesInConversation(conversationRowId, db) == 0) {
                              setConversationHidden(conversationRowId, true);
                                    } else if (isLast) {
                              markLastMessage(conversationRowId);
                                    }
                                }
                      c.close();
                            } catch (Throwable th) {
                                c.close();
                                throw th;
                            }
                        }
              if (updated > 0) {
                  notifyVMUris();
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }

      public void permaDeleteMessageFromRowId(long messageRowId) {
          SQLiteDatabase db = getWritableDatabase();
          ContentValues values = new ContentValues();
          values.put(Vine.MessagesColumns.DELETED, (Integer) 1);
          String messageIdString = String.valueOf(messageRowId);
          int deleted = db.delete("messages", VineDatabaseSQL.SELECTION_ROW_ID, new String[]{messageIdString});
          if (deleted > 0) {
              notifyVMUris();
                    }
                }

      public int getNumberVisibleMessagesInConversation(long conversationRowId, SQLiteDatabase db) {
                    int res = 0;
          Cursor c = db.query("messages", new String[]{"_id"}, "conversation_row_id =? AND deleted != 1", new String[]{String.valueOf(conversationRowId)}, null, null, null);
          if (c != null) {
                        try {
                  res = c.getCount();
                  c.close();
                        } catch (Throwable th) {
                            c.close();
                            throw th;
                        }
                    }
          return res;
                }

      public void setConversationHidden(long conversationRowId, boolean hidden) {
          SQLiteDatabase db = getWritableDatabase();
          ContentValues cv = new ContentValues();
          cv.put(Vine.ConversationsColumns.IS_HIDDEN, Integer.valueOf(hidden ? 1 : 0));
          int updated = db.update("conversations", cv, VineDatabaseSQL.SELECTION_ROW_ID, new String[]{String.valueOf(conversationRowId)});
          Log.d(TAG, "Marked conversation " + conversationRowId + " hidden value " + hidden + " updated rows: " + updated);
                }

      public long createPreMergeConversationIdAndRecipientsIfNecessary(List<VineRecipient> recipients, int networkType) {
          HashSet<Long> userRowIds = new HashSet<>();
                    for (VineRecipient recipient : recipients) {
              if (!recipient.isFromUser()) {
                  long recipientId = getUserRowIdForRecipient(recipient);
                            if (recipientId <= 0) {
                      String email = null;
                      String phone = null;
                      if (recipient.isFromEmail()) {
                          email = recipient.value;
                                } else {
                          phone = recipient.value;
                                }
                      recipientId = createNewUserFromNonUserIdRecipientInfo(recipient.display, phone, email);
                            }
                  recipient.recipientId = recipientId;
                  userRowIds.add(Long.valueOf(recipientId));
                        } else {
                  userRowIds.add(Long.valueOf(recipient.recipientId));
                        }
                    }
          return createConversationRowId(userRowIds, networkType);
                }

      public long createNewUserFromNonUserIdRecipientInfo(String display, String phone, String email) {
          VineUser user = new VineUser(display, null, null, null, -1L, -1L, 0, 0, 0, true, 0, 0, 0, 0, 0, 0, email, phone, 0, 0, 0, 0, null, false, 0, 0, "", false, false, false, false, 0L, false);
          ContentValues values = new ContentValues();
          fillUserContentValues(values, user, System.currentTimeMillis());
          SQLiteDatabase db = getWritableDatabase();
          return db.insertOrThrow("users", "last_refresh", values);
                }

      public long mergeUserAndGetResultingRowId(VineUser user) {
          ContentValues values = new ContentValues();
          fillUserContentValues(values, user, System.currentTimeMillis());
          SQLiteDatabase db = getWritableDatabase();
          Cursor c = db.query("users", new String[]{"_id"}, VineDatabaseSQL.SELECTION_USER_ID, new String[]{String.valueOf(user.userId)}, null, null, null);
          if (c != null) {
                        try {
                  if (c.moveToFirst()) {
                      long updateId = c.getLong(0);
                      db.update("users", values, VineDatabaseSQL.SELECTION_ROW_ID, new String[]{String.valueOf(updateId)});
                      c.close();
                      return updateId;
                            }
                  c.close();
                        } catch (Throwable th) {
                            c.close();
                            throw th;
                        }
                    }
          return db.insertOrThrow("users", null, values);
                }

      public synchronized long createConversationRowId(HashSet<Long> userRowIds, int networkType) {
                    long conversationRowId;
                    try {
                        SQLiteDatabase db = getWritableDatabase();
                        try {
                  db.beginTransaction();
                  ContentValues values = new ContentValues();
                  fillConversationValues(values, -1L, Integer.valueOf(networkType), false, 0L, 0L);
                  conversationRowId = db.insert("conversations", null, values);
                  db.setTransactionSuccessful();
                  safeFinishTransaction(db);
                            if (conversationRowId > 0) {
                      mergeConversationRecipients(conversationRowId, userRowIds);
                      this.mContentResolver.notifyChange(Vine.InboxView.CONTENT_URI, null);
                            }
                        } catch (Throwable th) {
                            safeFinishTransaction(db);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                    return conversationRowId;
                }

      public VinePrivateMessage getMessageFromMessageRow(long mergedMessageId) {
          VinePrivateMessage vpm = null;
          SQLiteDatabase db = getReadableDatabase();
          Cursor c = null;
                    try {
              Cursor c2 = db.query(Vine.ConversationMessageUsersView.TABLE_NAME, VineDatabaseSQL.ConversationMessageUsersQuery.PROJECTION, VineDatabaseSQL.SELECTION_ROW_ID, new String[]{String.valueOf(mergedMessageId)}, null, null, null);
              if (c2 != null) {
                            try {
                      if (c2.moveToFirst()) {
                          vpm = new VinePrivateMessage(c2);
                                }
                      c2.close();
                            } catch (Throwable th) {
                                c2.close();
                                throw th;
                            }
                        }
              if (c2 != null) {
                  c2.close();
                        }
              return vpm;
                    } catch (Throwable th2) {
              if (0 != 0) {
                  c.close();
                        }
                        throw th2;
                    }
                }

      public void mergeRecipientsWithUsersAndRemoveUnusedRecipients(long conversationRowId, ArrayList<VineRecipient> recipients, ArrayList<VineUser> users) {
          HashSet<Long> conversationRecipients = new HashSet<>();
          HashMap<Long, VineUser> userMap = new HashMap<>();
                    for (VineUser user : users) {
              userMap.put(Long.valueOf(user.userId), user);
                    }
          SQLiteDatabase db = getWritableDatabase();
          Cursor c = db.query(Vine.ConversationRecipients.TABLE_NAME, new String[]{"user_row_id"}, "conversation_row_id =? ", new String[]{String.valueOf(conversationRowId)}, null, null, null);
          if (c != null) {
                        try {
                  c.moveToPosition(-1);
                  while (c.moveToNext()) {
                      conversationRecipients.add(Long.valueOf(c.getLong(0)));
                            }
                            c.close();
                        } catch (Throwable th) {
                            c.close();
                            throw th;
                        }
                    }
                    try {
              db.beginTransaction();
                        for (VineRecipient recipient : recipients) {
                  if (recipient != null && recipient.userId > 0 && recipient.recipientId > 0) {
                      ContentValues cv = new ContentValues();
                      VineUser user2 = userMap.get(Long.valueOf(recipient.userId));
                      if (user2 != null) {
                          fillUserContentValues(cv, user2, System.currentTimeMillis());
                          db.update("users", cv, "_id=? ", new String[]{String.valueOf(recipient.recipientId)});
                          conversationRecipients.remove(Long.valueOf(recipient.recipientId));
                                }
                            }
                        }
              db.setTransactionSuccessful();
              safeFinishTransaction(db);
              if (conversationRecipients.size() > 0) {
                            try {
                      db.beginTransaction();
                      int i = 0;
                      int size = conversationRecipients.size();
                      String[] recipientsStringArray = new String[size];
                      StringBuilder builder = new StringBuilder();
                      builder.append(" IN (");
                                for (Long l : conversationRecipients) {
                          builder.append("?");
                          recipientsStringArray[i] = l.toString();
                          if (i != size - 1) {
                              builder.append(", ");
                                    }
                          i++;
                                }
                      builder.append(")");
                      String inClause = builder.toString();
                      int deleted = db.delete("users", "_id" + inClause, recipientsStringArray);
                      int iDelete = deleted + db.delete(Vine.ConversationRecipients.TABLE_NAME, "user_row_id" + inClause, recipientsStringArray);
                      db.setTransactionSuccessful();
                      safeFinishTransaction(db);
                            } catch (Throwable th2) {
                                safeFinishTransaction(db);
                                throw th2;
                            }
                        }
                    } catch (Throwable th3) {
                        safeFinishTransaction(db);
                        throw th3;
                    }
                }

      public long getUserRowIdForUserRemoteId(long userId) {
          SQLiteDatabase db = getReadableDatabase();
          Cursor c = db.query("users", new String[]{"_id"}, "user_id =? ", new String[]{String.valueOf(userId)}, null, null, null);
          long rowId = -1;
          if (c != null) {
                        try {
                  if (c.moveToFirst()) {
                      rowId = c.getLong(0);
                            }
                  c.close();
                        } catch (Throwable th) {
                            c.close();
                            throw th;
                        }
                    }
          return rowId;
                }

      public long getUserRemoteIdForUserRowId(long userRowId) {
          SQLiteDatabase db = getReadableDatabase();
          Cursor c = db.query("users", new String[]{"user_id"}, "_id =? ", new String[]{String.valueOf(userRowId)}, null, null, null);
          long rowId = -1;
          if (c != null) {
                        try {
                  if (c.moveToFirst()) {
                      rowId = c.getLong(0);
                            }
                  c.close();
                        } catch (Throwable th) {
                            c.close();
                            throw th;
                        }
                    }
          return rowId;
                }

      public long getUserRowIdForRecipient(VineRecipient recipient) {
                    Cursor c;
                    if (recipient.recipientId > 0) {
              return recipient.recipientId;
                    }
          if (recipient.isFromUser() && recipient.userId > 0) {
              return getUserRowIdForUserRemoteId(recipient.userId);
                    }
          SQLiteDatabase db = getReadableDatabase();
          String column = null;
          if (recipient.isFromEmail()) {
              column = Vine.UserColumns.EMAIL_ADDRESS;
                    } else if (recipient.isFromPhone()) {
              column = "phone_number";
                    }
          if (column != null && (c = db.query("users", new String[]{"_id"}, column + "=? AND user_id= -1", new String[]{recipient.value}, null, null, null)) != null) {
                        try {
                  if (c.moveToFirst()) {
                      long j = c.getLong(0);
                      c.close();
                      return j;
                            }
                  c.close();
                        } catch (Throwable th) {
                            c.close();
                            throw th;
                        }
                    }
          return -1L;
                }

      public long determineOrCreateBestConversationRowIdForRecipients(List<VineRecipient> recipients, int networkType) {
          long conversationRowId = -1;
          if (recipients.size() == 1) {
              VineRecipient recipient = recipients.get(0);
              conversationRowId = determineBestConversationRowIdForRecipient(recipient);
                    }
                    if (conversationRowId <= 0) {
              long conversationRowId2 = createPreMergeConversationIdAndRecipientsIfNecessary(recipients, networkType);
              return conversationRowId2;
                    }
          return conversationRowId;
                }

      public long determineBestConversationRowIdForRecipient(VineRecipient recipient) {
          long userRowId = getUserRowIdForRecipient(recipient);
          return determineBestConversationRowIdForUserRowId(userRowId);
                }

      public long determineBestConversationRowIdForUserRemoteId(long userRemoteId) {
          long userRowId = getUserRowIdForUserRemoteId(userRemoteId);
          return determineBestConversationRowIdForUserRowId(userRowId);
                }

      public long determineBestConversationRowIdForUserRowId(long userRowId) {
          SQLiteDatabase db = getReadableDatabase();
          Cursor c = db.query(Vine.ConversationRecipients.TABLE_NAME, new String[]{"conversation_row_id"}, "user_row_id =?", new String[]{String.valueOf(userRowId)}, null, null, null);
          long resultConversationObjectId = -1;
          if (c != null) {
              while (c.moveToNext()) {
                            try {
                      long conversationObjectId = c.getLong(0);
                      Cursor c2 = db.query(Vine.ConversationRecipients.TABLE_NAME, new String[]{"_id"}, "conversation_row_id =? ", new String[]{String.valueOf(conversationObjectId)}, null, null, null);
                      if (c2 != null) {
                                    try {
                              if (c2.getCount() == 1) {
                                  resultConversationObjectId = conversationObjectId;
                                  c2.close();
                                  if (c2 == null) {
                                                break;
                                            }
                                  c2.close();
                                            break;
                                        }
                              if (c2 != null) {
                                  c2.close();
                                        }
                                    } catch (Throwable th) {
                                        if (c2 != null) {
                                  c2.close();
                                        }
                                        throw th;
                                    }
                                }
                            } catch (Throwable th2) {
                                if (c != null) {
                          c.close();
                                }
                                throw th2;
                            }
                        }
              if (c != null) {
                  c.close();
                        }
                    }
          return resultConversationObjectId;
                }

      public ArrayList<VineRecipient> getConversationRecipientsFromConversationRowId(long conversationRowId) throws Throwable {
                    SQLiteDatabase db = getReadableDatabase();
                    Cursor c = db.query(Vine.ConversationRecipients.TABLE_NAME, new String[]{"user_row_id"}, "conversation_row_id =? ", new String[]{String.valueOf(conversationRowId)}, null, null, null);
                    if (c == null) {
                        return null;
                    }
                    ArrayList<VineRecipient> result = new ArrayList<>();
                    try {
                        while (c.moveToNext()) {
                            long userRowId = c.getLong(0);
                            Cursor c2 = db.query("users", new String[]{"user_id"}, VineDatabaseSQL.SELECTION_ROW_ID, new String[]{String.valueOf(userRowId)}, null, null, null);
                            if (c2 != null) {
                                try {
                                    if (c2.moveToFirst()) {
                                        result.add(VineRecipient.fromUser(null, c2.getLong(0), 0, userRowId));
                                    }
                                } finally {
                                    c2.close();
                                }
                            }
                        }
                        return result;
                    } finally {
                        c.close();
                    }
                }

      public void setMessageError(String uploadPath, int errorCode, String errorReason) {
          if (!TextUtils.isEmpty(uploadPath)) {
              SQLiteDatabase db = getWritableDatabase();
              ContentValues cv = new ContentValues();
              cv.put("error_code", Integer.valueOf(errorCode));
              cv.put(Vine.MessagesColumns.ERROR_REASON, errorReason);
              int updated = db.update("messages", cv, "upload_path=?", new String[]{uploadPath});
              if (updated > 0) {
                  notifyVMUris();
                        }
                    }
                }

      public void setMessageError(long messageRowId, int errorCode, String errorReason) {
                    if (messageRowId > 0) {
              SQLiteDatabase db = getWritableDatabase();
              ContentValues cv = new ContentValues();
              cv.put("error_code", Integer.valueOf(errorCode));
              cv.put(Vine.MessagesColumns.ERROR_REASON, errorReason);
              int updated = db.update("messages", cv, VineDatabaseSQL.SELECTION_ROW_ID, new String[]{String.valueOf(messageRowId)});
              if (updated > 0) {
                  notifyVMUris();
                        }
                    }
                }

      public long getNewMessageId() {
          SQLiteDatabase db = getReadableDatabase();
          Cursor c = db.query("messages", new String[]{"message_id"}, null, null, null, null, Vine.Messages.SORT_ORDER_REVERSE, "1");
          long ret = 1;
          if (c != null) {
              if (c.moveToFirst()) {
                  ret = c.getLong(0) + 1;
                        }
              c.close();
                    }
          return ret;
                }

      public boolean amFollowingUser(long userRowId) {
          SQLiteDatabase db = getReadableDatabase();
          Cursor c = db.query("users", new String[]{"following_flag"}, "_id =? ", new String[]{String.valueOf(userRowId)}, null, null, null);
          boolean following = false;
          if (c != null) {
                        try {
                  if (c.moveToFirst()) {
                      following = c.getInt(0) == 1;
                            }
                  if (c != null) {
                      c.close();
                            }
                        } catch (Throwable th) {
                            if (c != null) {
                      c.close();
                            }
                            throw th;
                        }
                    }
          return following;
                }

      public void removeUserSectionsWithType(int sectionType) {
          SQLiteDatabase db = getWritableDatabase();
          db.delete(Vine.UserSections.TABLE_NAME, "section_type=?", new String[]{String.valueOf(sectionType)});
                }

      public String getDisplayForUser(long recipientId) {
          SQLiteDatabase db = getReadableDatabase();
          Cursor c = db.query("users", new String[]{"username"}, "_id =? ", new String[]{String.valueOf(recipientId)}, null, null, null);
          String name = null;
          if (c != null) {
                        try {
                  if (c.moveToFirst()) {
                      name = c.getString(0);
                            }
                  c.close();
                        } catch (Throwable th) {
                            c.close();
                            throw th;
                        }
                    }
          return name;
                }

      public void updateUserFollowingFlag(long userIdToFollow, boolean following) {
          SQLiteDatabase db = getWritableDatabase();
          ContentValues values = new ContentValues();
          values.put("following_flag", Integer.valueOf(following ? 1 : 0));
          db.update("users", values, VineDatabaseSQL.SELECTION_USER_ID, new String[]{String.valueOf(userIdToFollow)});
                }

      public Cursor getMessagesForConversationRowId(long conversationRowId) {
          SQLiteDatabase db = getReadableDatabase();
          return db.query(Vine.ConversationMessageUsersView.TABLE_NAME, VineDatabaseSQL.ConversationMessageUsersQuery.PROJECTION, "conversation_row_id=? AND error_code <> 0", new String[]{String.valueOf(conversationRowId)}, null, null, Vine.Messages.SORT_ORDER_DEFAULT);
                }

      public VinePost getPost(long postId) {
          SQLiteDatabase db = getReadableDatabase();
          Cursor c = db.query(Vine.PostCommentsLikesView.TABLE_NAME, VineDatabaseSQL.PostCommentsLikesQuery.PROJECTION, "post_id =? ", new String[]{String.valueOf(postId)}, null, null, null);
          if (c != null) {
                        try {
                  if (c.moveToFirst()) {
                      VinePost vp = VinePost.from(c);
                      c.close();
                      return vp;
                            }
                  c.close();
                        } catch (Throwable th) {
                            c.close();
                            throw th;
                        }
                    }
                    return null;
                }

      public void removeAccount() {
          setValue(CURRENT_ACCOUNT_KEY, (String) null);
          setValue(CURRENT_USER_ID_KEY, 0L);
          setValue(CURRENT_ACCOUNT_NAME, (String) null);
                }

      private void setValue(String propName, String value) {
          ContentValues values = new ContentValues();
          values.put(Vine.Settings.PROP_VALUE, value);
          saveUserValue(propName, values);
                }

      private void setValue(String propName, long value) {
          ContentValues values = new ContentValues();
          values.put(Vine.Settings.PROP_VALUE, Long.valueOf(value));
          saveUserValue(propName, values);
                }

      public static class VineViewedMessage {
                    public final long conversationId;
                    public final long localStartTime;
                    public final long messageId;

          public VineViewedMessage(long conversationId, long messageId, long localStartTime) {
              this.conversationId = conversationId;
              this.messageId = messageId;
              this.localStartTime = localStartTime;
                    }
                }

      public ArrayList<VineViewedMessage> getMessagesThatWereViewed() {
          SQLiteDatabase db = getWritableDatabase();
          Cursor c = db.query(Vine.ConversationMessageUsersView.TABLE_NAME, new String[]{"conversation_id", "message_id", Vine.MessagesColumns.LOCAL_START_TIME}, "local_start_time!=0", null, null, null, Vine.Messages.SORT_ORDER_REVERSE, "1");
          ArrayList<VineViewedMessage> messages = new ArrayList<>();
          if (c != null) {
              while (c.moveToNext()) {
                            try {
                                try {
                          messages.add(new VineViewedMessage(c.getLong(0), c.getLong(1), c.getLong(2)));
                                } catch (Exception e) {
                          SLog.m455e("Error while getting viewed messages.");
                          c.close();
                                }
                            } catch (Throwable th) {
                                c.close();
                                throw th;
                            }
                        }
              c.close();
                    }
          return messages;
                }
            }
