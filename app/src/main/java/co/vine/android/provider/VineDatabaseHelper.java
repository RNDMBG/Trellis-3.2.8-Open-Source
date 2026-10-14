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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 91 */    public class VineDatabaseHelper extends SQLiteOpenHelper {
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
/* 93 */            LOGGABLE = BuildUtil.isLogsOn() && Log.isLoggable(TAG, 3);
/* 110 */           sHelperMap = new HashMap<>();
                }

/* 113 */       public static synchronized VineDatabaseHelper getDatabaseHelper(Context context) {
                    VineDatabaseHelper helper;
                    try {
                        String databaseName = getDatabaseName(1);
/* 115 */               helper = sHelperMap.get(databaseName);
/* 116 */               if (helper == null) {
/* 117 */                   helper = new VineDatabaseHelper(context.getApplicationContext(), databaseName);
/* 118 */                   sHelperMap.put(databaseName, helper);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return helper;
                }

/* 123 */       public static File getDatabasePath(Context context) {
/* 124 */           return context.getDatabasePath(getDatabaseName(1));
                }

/* 127 */       private VineDatabaseHelper(Context context, String name) {
/* 128 */           super(context, name, (SQLiteDatabase.CursorFactory) null, 26);
/* 129 */           this.mContentResolver = context.getContentResolver();
/* 130 */           this.mAppContext = context.getApplicationContext();
                }

                @Override // android.database.sqlite.SQLiteOpenHelper
/* 133 */       public void onCreate(SQLiteDatabase db) {
/* 134 */           createTables(db);
                }

                @Override // android.database.sqlite.SQLiteOpenHelper
/* 138 */       public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
/* 139 */           if (LOGGABLE) {
/* 140 */               Log.d(TAG, "Upgrading from " + oldVersion + " to " + newVersion);
                    }
/* 142 */           dropTables(db);
/* 143 */           createTables(db);
                }

/* 146 */       protected void createTables(SQLiteDatabase db) {
/* 147 */           db.execSQL(VineDatabaseSQL.USERS_TABLE_SQL);
/* 148 */           db.execSQL(VineDatabaseSQL.SETTINGS_TABLE_SQL);
/* 149 */           db.execSQL(VineDatabaseSQL.USERS_SECTION_TABLE_SQL);
/* 150 */           db.execSQL(VineDatabaseSQL.USER_GROUPS_TABLE_SQL);
/* 151 */           db.execSQL(VineDatabaseSQL.COMMENTS_TABLE_SQL);
/* 152 */           db.execSQL(VineDatabaseSQL.USER_GROUPS_VIEW_SQL);
/* 153 */           db.execSQL(VineDatabaseSQL.LIKES_TABLE_SQL);
/* 154 */           db.execSQL(VineDatabaseSQL.POSTS_TABLE_SQL);
/* 155 */           db.execSQL(VineDatabaseSQL.POST_GROUPS_TABLE_SQL);
/* 156 */           db.execSQL(VineDatabaseSQL.POST_GROUPS_VIEW_SQL);
/* 157 */           db.execSQL(VineDatabaseSQL.POST_COMMENTS_LIKES_VIEW_SQL);
/* 158 */           db.execSQL(VineDatabaseSQL.PAGE_CURSOR_TABLE_SQL);
/* 159 */           db.execSQL(VineDatabaseSQL.TAGS_TABLE_SQL);
/* 160 */           db.execSQL(VineDatabaseSQL.CHANNELS_TABLE_SQL);
/* 161 */           db.execSQL(VineDatabaseSQL.NOTIFICATIONS_TABLE_SQL);
/* 162 */           db.execSQL(VineDatabaseSQL.TAGS_RECENTLY_USED_TABLE_SQL);
/* 163 */           db.execSQL(VineDatabaseSQL.EDITIONS_TABLE_SQL);
/* 164 */           db.execSQL(VineDatabaseSQL.MESSAGES_TABLE_SQL);
/* 165 */           db.execSQL(VineDatabaseSQL.CONVERSATIONS_TABLE_SQL);
/* 166 */           db.execSQL(VineDatabaseSQL.CONVERSATION_MESSAGE_USERS_VIEW_SQL);
/* 167 */           db.execSQL(VineDatabaseSQL.CONVERSATION_RECIPIENTS_TABLE_SQL);
/* 168 */           db.execSQL(VineDatabaseSQL.INBOX_VIEW_SQL);
/* 169 */           db.execSQL(VineDatabaseSQL.CONVERSATION_RECIPIENTS_USERS_VIEW);
                }

/* 172 */       private void dropTables(SQLiteDatabase db) {
/* 173 */           db.execSQL("DROP VIEW IF EXISTS  user_groups_view;");
/* 174 */           db.execSQL("DROP VIEW IF EXISTS  post_groups_view;");
/* 175 */           db.execSQL("DROP VIEW IF EXISTS  post_comments_likes_view;");
/* 176 */           db.execSQL("DROP TABLE IF EXISTS users;");
/* 177 */           db.execSQL("DROP TABLE IF EXISTS settings;");
/* 178 */           db.execSQL("DROP TABLE IF EXISTS user_groups;");
/* 179 */           db.execSQL("DROP TABLE IF EXISTS comments;");
/* 180 */           db.execSQL("DROP TABLE IF EXISTS likes;");
/* 181 */           db.execSQL("DROP TABLE IF EXISTS posts;");
/* 182 */           db.execSQL("DROP TABLE IF EXISTS post_groups;");
/* 183 */           db.execSQL("DROP TABLE IF EXISTS page_cursors;");
/* 184 */           db.execSQL("DROP TABLE IF EXISTS tag_search_results;");
/* 185 */           db.execSQL("DROP TABLE IF EXISTS tag_search_results;");
/* 186 */           db.execSQL("DROP TABLE IF EXISTS channels;");
/* 187 */           db.execSQL("DROP TABLE IF EXISTS notifications;");
/* 188 */           db.execSQL("DROP TABLE IF EXISTS tag_recently_used;");
/* 189 */           db.execSQL("DROP TABLE IF EXISTS editions;");
/* 190 */           db.execSQL("DROP TABLE IF EXISTS messages;");
/* 191 */           db.execSQL("DROP TABLE IF EXISTS conversation_recipients;");
/* 192 */           db.execSQL("DROP TABLE IF EXISTS conversations;");
/* 193 */           db.execSQL("DROP TABLE IF EXISTS user_sections;");
/* 194 */           db.execSQL("DROP VIEW IF EXISTS message_users_view;");
/* 195 */           db.execSQL("DROP VIEW IF EXISTS inbox_view;");
/* 196 */           db.execSQL("DROP VIEW IF EXISTS conversation_recipients_users_view;");
                }

/* 200 */       public synchronized Cursor getOldestSortId(int groupType) {
                    SQLiteDatabase db;
                    try {
                        db = getReadableDatabase();
                    } catch (Throwable th) {
                        throw th;
                    }
/* 202 */           return db.query(Vine.PostGroups.TABLE_NAME, new String[]{Vine.PostGroupColumns.SORT_ID}, VineDatabaseSQL.SELECTION_POST_GROUP_TYPE, new String[]{String.valueOf(groupType)}, null, null, "CAST (sort_id AS INTEGER) DESC", "1");
                }

/* 221 */       public synchronized DbResponse mergePosts(Collection<VinePost> items, int groupType, String tag, int pageType, int nextPage, int previousPage, long anchor, boolean userInitiated) throws IOException {
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
/* 223 */               LongSparseArray<VinePost> newPosts = new LongSparseArray<>(items.size());
/* 224 */               LongSparseArray<VinePost> updatedPosts = new LongSparseArray<>(items.size());
                        for (VinePost item : items) {
/* 227 */                   newPosts.put(item.postId, item);
                        }
/* 229 */               if (Util.isPopularTimeline(groupType)) {
/* 230 */                   orderMap = new LongSparseArray<>(items.size());
/* 232 */                   if (pageType == 1 || pageType == 2) {
/* 233 */                       sortId = 1;
/* 234 */                       removePostsFromGroup(groupType);
                            } else {
/* 236 */                       Cursor c = getOldestSortId(groupType);
/* 237 */                       if (c != null && c.moveToFirst()) {
/* 238 */                           int sortId2 = c.getInt(0);
/* 239 */                           sortId = sortId2 + 1;
                                } else {
/* 241 */                           sortId = 1;
                                }
                            }
/* 244 */                   Iterator<VinePost> it = items.iterator();
                            while (it.hasNext()) {
/* 245 */                       orderMap.put(it.next().postId, Long.valueOf(sortId));
/* 246 */                       sortId++;
                            }
                        } else {
/* 249 */                   orderMap = new LongSparseArray<>(items.size());
                            for (VinePost item2 : items) {
/* 251 */                       orderMap.put(item2.postId, Long.valueOf(VineComparatorFactory.get(groupType).getOrderId(item2)));
                            }
                        }
/* 256 */               boolean contiguous = true;
/* 260 */               if (!TextUtils.isEmpty(tag) && !Util.isPopularTimeline(groupType)) {
/* 261 */                   selectionString = VineDatabaseSQL.SELECTION_POST_GROUP_TYPE_AND_TAG;
/* 262 */                   selectionArgs = new String[]{String.valueOf(groupType), tag};
                        } else {
/* 264 */                   selectionString = VineDatabaseSQL.SELECTION_POST_GROUP_TYPE;
/* 265 */                   selectionArgs = new String[]{String.valueOf(groupType)};
                        }
/* 267 */               Cursor c2 = db.query(Vine.PostGroupsView.TABLE_NAME, VineDatabaseSQL.PostGroupsQuery.PROJECTION, selectionString, selectionArgs, null, null, "sort_id DESC");
/* 270 */               if (c2 != null) {
                            try {
/* 272 */                       if (c2.moveToFirst() && anchor > 0) {
/* 275 */                           contiguous = anchor < c2.getLong(3);
                                }
/* 279 */                       c2.close();
                            } catch (Throwable th) {
                                c2.close();
                                throw th;
                            }
                        }
/* 284 */               Cursor c3 = db.query("posts", new String[]{"post_id"}, null, null, null, null, null);
/* 286 */               if (c3 != null) {
                            try {
/* 288 */                       if (c3.moveToFirst()) {
/* 295 */                           do {
/* 290 */                               long postId = c3.getLong(0);
/* 291 */                               VinePost post = newPosts.removeKey(postId);
/* 292 */                               if (post != null) {
/* 293 */                                   updatedPosts.put(postId, post);
                                        }
/* 295 */                           } while (c3.moveToNext());
                                }
/* 298 */                       c3.close();
                            } catch (Throwable th2) {
                                c3.close();
                                throw th2;
                            }
                        }
/* 302 */               if (LOGGABLE && newPosts.isEmpty() && updatedPosts.isEmpty()) {
/* 303 */                   Log.d(TAG, "No new posts to merge or update");
/* 304 */                   dbResponse = new DbResponse(0, 0, 2);
                        } else {
/* 307 */                   LongSparseArray<Long> tagMap = new LongSparseArray<>(items.size());
/* 308 */                   ArrayList<VineUser> users = new ArrayList<>(items.size());
/* 310 */                   int size = newPosts.size();
                            for (int i = 0; i < size; i++) {
/* 312 */                       VinePost vc = newPosts.valueAt(i);
/* 313 */                       if (vc.user != null) {
/* 314 */                           users.add(vc.user);
/* 315 */                           tagMap.put(vc.userId, Long.valueOf(vc.postId));
                                }
                            }
/* 319 */                   mergeUsers(users, 4, null, 0, 0, tagMap, null);
/* 321 */                   long lastRefresh = System.currentTimeMillis();
/* 323 */                   int numInserted = 0;
/* 325 */                   int size2 = newPosts.size();
/* 327 */                   if (size2 > 0) {
/* 328 */                       db.beginTransaction();
                                try {
/* 330 */                           ContentValues values = new ContentValues();
                                    for (int i2 = 0; i2 < size2; i2++) {
/* 332 */                               VinePost post2 = newPosts.valueAt(i2);
/* 334 */                               values.clear();
/* 337 */                               post2.likesCount = post2.likes != null ? post2.likes.count : 0;
/* 338 */                               post2.commentsCount = post2.comments != null ? post2.comments.count : 0;
/* 340 */                               fillPostValues(values, post2, lastRefresh);
/* 341 */                               numInserted += db.insert("posts", null, values) > 0 ? 1 : 0;
/* 342 */                               if (post2.likes != null && post2.likes.items != null && !post2.likes.items.isEmpty()) {
/* 343 */                                   mergeLikes(post2.likes.items, post2.postId, -1, -1);
                                        }
                                    }
/* 346 */                           db.setTransactionSuccessful();
/* 348 */                           safeFinishTransaction(db);
                                } catch (Throwable th3) {
                                    safeFinishTransaction(db);
                                    throw th3;
                                }
                            }
/* 352 */                   int numUpdated = 0;
/* 354 */                   int size3 = updatedPosts.size();
/* 355 */                   if (size3 > 0) {
/* 356 */                       db.beginTransaction();
                                try {
/* 358 */                           ContentValues values2 = new ContentValues();
                                    for (int i3 = 0; i3 < size3; i3++) {
/* 360 */                               VinePost post3 = updatedPosts.valueAt(i3);
/* 362 */                               values2.clear();
/* 364 */                               post3.likesCount = post3.likes != null ? post3.likes.count : 0;
/* 365 */                               post3.commentsCount = post3.comments != null ? post3.comments.count : 0;
/* 367 */                               fillPostValues(values2, post3, lastRefresh);
/* 368 */                               numUpdated += db.update("posts", values2, "post_id=?", new String[]{String.valueOf(post3.postId)}) > 0 ? 1 : 0;
/* 370 */                               if (post3.likes != null && post3.likes.items != null && !post3.likes.items.isEmpty()) {
/* 371 */                                   mergeLikes(post3.likes.items, post3.postId, -1, -1);
                                        }
                                    }
/* 374 */                           db.setTransactionSuccessful();
/* 376 */                           safeFinishTransaction(db);
                                } catch (Throwable th4) {
                                    safeFinishTransaction(db);
                                    throw th4;
                                }
                            }
/* 380 */                   if (LOGGABLE) {
/* 381 */                       Log.d(TAG, "Inserted " + numInserted + " posts, and updated " + numUpdated + " with lastRefresh time " + lastRefresh);
                            }
                            if (anchor > 0 || nextPage > 0 || previousPage > 0) {
/* 386 */                       savePageCursor(2, groupType, tag, nextPage, previousPage, anchor, false);
                            }
/* 392 */                   if (contiguous) {
/* 393 */                       response = 1;
                            } else {
/* 398 */                       if (!TextUtils.isEmpty(tag)) {
/* 399 */                           timelineSelection = "post_type=? AND tag=? AND post_id<?";
/* 401 */                           timelineSelectionArgs = new String[]{String.valueOf(groupType), tag, String.valueOf(anchor)};
                                } else {
/* 404 */                           timelineSelection = "post_type=? AND post_id<?";
/* 405 */                           timelineSelectionArgs = new String[]{String.valueOf(groupType), String.valueOf(anchor)};
                                }
/* 409 */                       int deleted = db.delete("posts", "post_id<?", new String[]{String.valueOf(groupType)});
/* 411 */                       int deletedGroups = db.delete(Vine.PostGroups.TABLE_NAME, timelineSelection, timelineSelectionArgs);
/* 413 */                       if (LOGGABLE) {
/* 414 */                           Log.d(TAG, "Non contiguous timeline detected, deleted " + deleted + " posts and " + deletedGroups + " with id less than anchor: " + anchor + " of type " + groupType + " and tag " + tag);
                                }
/* 418 */                       response = 4;
                            }
/* 424 */                   int deleted2 = 0;
/* 425 */                   if (numInserted > 0 && contiguous && (tableCursor = db.query("posts", VineDatabaseSQL.TableQuery.PROJECTION, null, null, null, null, null)) != null) {
                                try {
/* 430 */                           if (tableCursor.moveToFirst()) {
/* 431 */                               int size4 = tableCursor.getCount();
/* 432 */                               if (size4 > 1000) {
                                            try {
/* 434 */                                       Cursor oldestNthRow = db.rawQuery("SELECT last_refresh FROM posts ORDER BY last_refresh DESC LIMIT 1 OFFSET 500", null);
/* 440 */                                       db.beginTransaction();
/* 441 */                                       if (oldestNthRow != null) {
                                                    try {
/* 443 */                                               if (oldestNthRow.moveToFirst()) {
/* 444 */                                                   long oldestNthId = oldestNthRow.getLong(0);
/* 446 */                                                   db.execSQL("DELETE FROM post_groups WHERE post_id IN (SELECT post_id FROM posts WHERE last_refresh < ?);", new String[]{String.valueOf(oldestNthId)});
/* 452 */                                                   deleted2 = db.delete("posts", "last_refresh<?", new String[]{String.valueOf(oldestNthId)});
/* 455 */                                                   if (LOGGABLE) {
/* 456 */                                                       Log.d(VineAPI.RESOURCE_SHARED, "Due to reaching maximum table size (" + size4 + " with a max of 1000), deleted " + deleted2 + " posts groups all less than last refresh id " + oldestNthId + ".");
                                                            }
                                                        }
/* 463 */                                               oldestNthRow.close();
                                                    } catch (Throwable th5) {
                                                        oldestNthRow.close();
                                                        throw th5;
                                                    }
                                                }
/* 466 */                                       db.setTransactionSuccessful();
/* 468 */                                       safeFinishTransaction(db);
                                            } catch (Throwable th6) {
                                                safeFinishTransaction(db);
                                                throw th6;
                                            }
                                        }
                                    }
/* 473 */                           tableCursor.close();
                                } catch (Throwable th7) {
                                    tableCursor.close();
                                    throw th7;
                                }
                            }
/* 479 */                   if ((numInserted > 0 || numUpdated > 0 || deleted2 > 0) && groupType != 0) {
/* 481 */                       if (groupType != -1) {
/* 482 */                           mergePostGroups(items, groupType, tag, orderMap);
                                }
/* 484 */                       if (userInitiated) {
/* 485 */                           notifyByType(groupType);
                                }
                            }
/* 488 */                   dbResponse = new DbResponse(numInserted, numUpdated, response);
                        }
                    } catch (Throwable th8) {
                        throw th8;
                    }
                    return dbResponse;
                }

/* 491 */       public synchronized int cleanUp(long timeAnchor) {
                    int deleted;
                    try {
                        SQLiteDatabase db = getWritableDatabase();
                        try {
/* 495 */                   db.beginTransaction();
/* 496 */                   db.execSQL("DELETE FROM post_groups WHERE post_id IN (SELECT post_id FROM posts WHERE last_refresh < ?);", new String[]{String.valueOf(timeAnchor)});
/* 500 */                   deleted = db.delete("posts", "last_refresh<?", new String[]{String.valueOf(timeAnchor)});
/* 503 */                   db.setTransactionSuccessful();
/* 504 */                   if (LOGGABLE) {
/* 505 */                       Log.d(TAG, "Cleanup performed, deleted " + deleted + " posts with last_refresh less than anchor: " + timeAnchor);
                            }
/* 509 */                   safeFinishTransaction(db);
/* 512 */                   if (deleted > 0) {
/* 513 */                       notifyPostCommentsViewUris();
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

/* 518 */       public synchronized void cleanUserSearchResults() {
                    try {
                        SQLiteDatabase db = getWritableDatabase();
                        try {
/* 522 */                   db.beginTransaction();
/* 524 */                   int deleted = db.delete("user_groups", VineDatabaseSQL.SELECTION_USER_GROUP_TYPE, new String[]{String.valueOf(8)});
/* 527 */                   if (LOGGABLE) {
/* 528 */                       Log.d(TAG, "Deleted " + deleted + " user search result rows.");
                            }
/* 530 */                   db.setTransactionSuccessful();
/* 532 */                   safeFinishTransaction(db);
/* 535 */                   if (deleted > 0) {
/* 536 */                       this.mContentResolver.notifyChange(Vine.UserGroupsView.CONTENT_URI_USER_SEARCH_RESULTS, null);
                            }
                        } catch (Throwable th) {
                            safeFinishTransaction(db);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }

/* 540 */       public synchronized void cleanTagSearchResults() {
                    try {
                        SQLiteDatabase db = getWritableDatabase();
                        try {
/* 544 */                   db.beginTransaction();
/* 546 */                   int deleted = db.delete(Vine.TagSearchResults.TABLE_NAME, null, null);
/* 548 */                   if (LOGGABLE) {
/* 549 */                       Log.d(TAG, "Deleted " + deleted + " tag search result rows.");
                            }
/* 551 */                   db.setTransactionSuccessful();
/* 553 */                   safeFinishTransaction(db);
/* 556 */                   if (deleted > 0) {
/* 557 */                       this.mContentResolver.notifyChange(Vine.TagSearchResults.CONTENT_URI, null);
                            }
                        } catch (Throwable th) {
                            safeFinishTransaction(db);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }

/* 562 */       public synchronized int mergeLikes(Collection<VineLike> items, long postId, int next, int previous) {
                    int numInserted;
                    Cursor tableCursor;
                    try {
                        SQLiteDatabase db = getWritableDatabase();
/* 565 */               LongSparseArray<VineLike> newLikes = new LongSparseArray<>(items.size());
/* 567 */               LongSparseArray<VineLike> updatedLikes = new LongSparseArray<>(items.size());
                        for (VineLike like : items) {
/* 570 */                   like.postId = postId;
/* 571 */                   newLikes.put(like.likeId, like);
                        }
/* 574 */               Cursor c = db.query("likes", VineDatabaseSQL.LikesQuery.PROJECTION, null, null, null, null, null);
/* 577 */               if (c != null) {
/* 579 */                   while (c.moveToNext()) {
                                try {
/* 580 */                           long likeId = c.getLong(1);
/* 581 */                           VineLike like2 = newLikes.removeKey(likeId);
/* 582 */                           if (like2 != null) {
/* 583 */                               updatedLikes.put(likeId, like2);
                                    }
                                } catch (Throwable th) {
                                    c.close();
                                    throw th;
                                }
                            }
                            c.close();
                        }
/* 591 */               int totalSize = newLikes.size() + updatedLikes.size();
/* 592 */               LongSparseArray<Long> orderMap = new LongSparseArray<>(totalSize);
/* 593 */               ArrayList<VineUser> users = new ArrayList<>(totalSize);
/* 595 */               int newLikeSize = newLikes.size();
                        for (int i = 0; i < newLikeSize; i++) {
/* 598 */                   VineLike vc = newLikes.valueAt(i);
/* 599 */                   if (vc.user != null) {
/* 600 */                       users.add(vc.user);
/* 601 */                       orderMap.put(vc.user.userId, Long.valueOf(vc.likeId));
                            }
                        }
/* 604 */               int updatedLikeSize = updatedLikes.size();
                        for (int i2 = 0; i2 < updatedLikeSize; i2++) {
/* 607 */                   VineLike vc2 = updatedLikes.valueAt(i2);
/* 608 */                   if (vc2.user != null) {
/* 609 */                       users.add(vc2.user);
/* 610 */                       orderMap.put(vc2.user.userId, Long.valueOf(vc2.likeId));
                            }
                        }
/* 615 */               if (!users.isEmpty()) {
/* 616 */                   mergeUsers(users, 5, String.valueOf(postId), next, previous, orderMap);
                        }
/* 620 */               numInserted = 0;
/* 621 */               long lastRefresh = System.currentTimeMillis();
/* 622 */               if (newLikeSize > 0) {
/* 623 */                   db.beginTransaction();
                            try {
/* 625 */                       ContentValues values = new ContentValues();
                                for (int i3 = 0; i3 < newLikeSize; i3++) {
/* 627 */                           VineLike like3 = newLikes.valueAt(i3);
/* 628 */                           values.clear();
/* 629 */                           fillLikeValues(values, like3, lastRefresh);
/* 630 */                           numInserted += db.insert("likes", null, values) > 0 ? 1 : 0;
                                }
/* 632 */                       db.setTransactionSuccessful();
/* 634 */                       safeFinishTransaction(db);
                            } catch (Throwable th2) {
                                safeFinishTransaction(db);
                                throw th2;
                            }
                        }
/* 638 */               int numUpdated = 0;
/* 639 */               if (updatedLikeSize > 0) {
/* 640 */                   db.beginTransaction();
                            try {
/* 642 */                       ContentValues values2 = new ContentValues();
                                for (int i4 = 0; i4 < updatedLikeSize; i4++) {
/* 644 */                           VineLike like4 = updatedLikes.valueAt(i4);
/* 645 */                           values2.clear();
/* 646 */                           fillLikeValues(values2, like4, lastRefresh);
/* 647 */                           numUpdated += db.update("likes", values2, "like_id=?", new String[]{String.valueOf(like4.likeId)}) > 0 ? 1 : 0;
                                }
/* 650 */                       db.setTransactionSuccessful();
/* 652 */                       safeFinishTransaction(db);
                            } catch (Throwable th3) {
                                safeFinishTransaction(db);
                                throw th3;
                            }
                        }
/* 658 */               int deleted = 0;
/* 659 */               if (numInserted > 0 && (tableCursor = db.query("likes", VineDatabaseSQL.TableQuery.PROJECTION, null, null, null, null, null)) != null) {
                            try {
/* 664 */                       if (tableCursor.moveToFirst()) {
/* 665 */                           int size = tableCursor.getCount();
/* 666 */                           if (size > 5000) {
                                        try {
/* 668 */                                   Cursor oldestNthRow = db.rawQuery("SELECT last_refresh FROM likes ORDER BY last_refresh DESC LIMIT 1 OFFSET 2500", null);
/* 674 */                                   db.beginTransaction();
/* 675 */                                   if (oldestNthRow != null) {
                                                try {
/* 677 */                                           if (oldestNthRow.moveToFirst()) {
/* 678 */                                               long oldestNthId = oldestNthRow.getLong(0);
/* 679 */                                               deleted = db.delete("likes", "last_refresh<?", new String[]{String.valueOf(oldestNthId)});
/* 681 */                                               if (LOGGABLE) {
/* 682 */                                                   Log.d(VineAPI.RESOURCE_SHARED, "Due to reaching maximum table size (" + size + " with a max of " + ExoPlayer.Factory.DEFAULT_MIN_REBUFFER_MS + "), deleted " + deleted + " likes, all less than refresh time " + oldestNthId);
                                                        }
                                                    }
/* 689 */                                           oldestNthRow.close();
                                                } catch (Throwable th4) {
                                                    oldestNthRow.close();
                                                    throw th4;
                                                }
                                            }
/* 692 */                                   db.setTransactionSuccessful();
/* 694 */                                   safeFinishTransaction(db);
                                        } catch (Throwable th5) {
                                            safeFinishTransaction(db);
                                            throw th5;
                                        }
                                    }
                                }
/* 699 */                       tableCursor.close();
                            } catch (Throwable th6) {
                                tableCursor.close();
                                throw th6;
                            }
                        }
/* 704 */               if (numInserted > 0 || numUpdated > 0 || deleted > 0) {
/* 705 */                   this.mContentResolver.notifyChange(Vine.Likes.CONTENT_URI, null);
                        }
/* 707 */               if (LOGGABLE) {
/* 708 */                   Log.d(TAG, "Inserted " + numInserted + " likes and updated " + numUpdated + ".");
                        }
                    } catch (Throwable th7) {
                        throw th7;
                    }
                    return numInserted;
                }

/* 713 */       public synchronized int deleteComment(String commentId) {
                    int deleted;
                    try {
                        SQLiteDatabase db = getWritableDatabase();
/* 715 */               deleted = db.delete("comments", "comment_id=?", new String[]{commentId});
/* 719 */               if (deleted > 0) {
/* 720 */                   this.mContentResolver.notifyChange(Vine.Comments.CONTENT_URI, null);
/* 721 */                   notifyPostCommentsViewUris();
                        }
/* 724 */               if (LOGGABLE) {
/* 725 */                   if (deleted > 0) {
/* 726 */                       Log.d(TAG, "Deleting comment: " + commentId);
                            } else {
/* 728 */                       Log.e(TAG, "Couldn't delete comment: " + commentId);
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return deleted;
                }

/* 734 */       public synchronized int getCommentsCount(long postId) {
                    int count = 0;
                    synchronized (this) {
                        try {
                            SQLiteDatabase db = getWritableDatabase();
/* 737 */                   Cursor c = db.query("comments", VineDatabaseSQL.CommentsQuery.PROJECTION, "post_id=?", new String[]{String.valueOf(postId)}, null, null, null);
/* 739 */                   if (c != null) {
                                try {
/* 741 */                           count = c.getCount();
/* 744 */                           c.close();
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

/* 760 */       public synchronized int addFollow(long userId, long sessionOwnerId, long followId, boolean notify) {
                    int result;
                    try {
                        SQLiteDatabase db = getWritableDatabase();
/* 763 */               String[] whereArgs = {String.valueOf(userId)};
/* 765 */               Cursor c = db.query("users", new String[]{"following_flag"}, VineDatabaseSQL.SELECTION_USER_ID, whereArgs, null, null, null);
/* 768 */               if (c != null) {
                            try {
/* 770 */                       if (c.moveToFirst()) {
/* 771 */                           int currentFriendship = c.getInt(0);
/* 772 */                           int newFriendship = Friendships.setFriendship(currentFriendship, 1);
/* 775 */                           ContentValues cv = new ContentValues();
/* 776 */                           cv.put("following_flag", Integer.valueOf(newFriendship));
/* 778 */                           result = db.update("users", cv, VineDatabaseSQL.SELECTION_USER_ID, whereArgs);
/* 781 */                           VineUser skeletonUser = new VineUser();
/* 782 */                           skeletonUser.userId = userId;
/* 783 */                           ArrayList<VineUser> users = new ArrayList<>(1);
/* 784 */                           users.add(skeletonUser);
/* 785 */                           LongSparseArray<Long> orderMap = null;
                                    if (followId > 0) {
/* 787 */                               orderMap = new LongSparseArray<>(1);
/* 788 */                               orderMap.put(userId, Long.valueOf(followId));
                                    }
/* 790 */                           mergeUserGroups(users, 1, String.valueOf(sessionOwnerId), null, orderMap);
/* 793 */                           if (notify) {
/* 794 */                               this.mContentResolver.notifyChange(Vine.UserGroupsView.CONTENT_URI_FOLLOWING, null);
                                    }
/* 796 */                           if (LOGGABLE) {
/* 797 */                               Log.d(TAG, "Followed user " + userId);
                                    }
/* 802 */                           if (c != null) {
/* 803 */                               c.close();
                                    }
                                } else {
/* 802 */                           if (c != null) {
/* 803 */                               c.close();
                                    }
/* 807 */                           result = -1;
                                }
                            } catch (Throwable th) {
                                if (c != null) {
/* 803 */                           c.close();
                                }
                                throw th;
                            }
                        } else {
/* 807 */                   result = -1;
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                    return result;
                }

/* 816 */       public synchronized int removeFollow(long userId, boolean deleteRow, boolean notify) {
                    int result;
                    try {
                        SQLiteDatabase db = getWritableDatabase();
/* 819 */               String[] whereArgs = {String.valueOf(userId)};
/* 821 */               Cursor c = db.query("users", new String[]{"following_flag"}, VineDatabaseSQL.SELECTION_USER_ID, whereArgs, null, null, null);
/* 824 */               if (c != null) {
                            try {
/* 826 */                       if (c.moveToFirst()) {
/* 827 */                           int currentFriendship = c.getInt(0);
/* 828 */                           int newFriendship = Friendships.unsetFriendship(currentFriendship, 1);
/* 830 */                           ContentValues cv = new ContentValues();
/* 831 */                           cv.put("following_flag", Integer.valueOf(newFriendship));
/* 833 */                           result = db.update("users", cv, VineDatabaseSQL.SELECTION_USER_ID, whereArgs);
/* 837 */                           if (deleteRow) {
/* 838 */                               removeUserWithType(userId, 1, notify, Vine.UserGroupsView.CONTENT_URI_FOLLOWING);
                                    }
/* 842 */                           if (LOGGABLE) {
/* 843 */                               Log.d(TAG, "Unfollowed user " + userId);
                                    }
/* 848 */                           c.close();
                                } else {
/* 848 */                           c.close();
/* 851 */                           result = -1;
                                }
                            } catch (Throwable th) {
                                c.close();
                                throw th;
                            }
                        } else {
/* 851 */                   result = -1;
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                    return result;
                }

/* 860 */       public synchronized int addLike(VineLike like, boolean notify) {
                    int result;
                    try {
                        SQLiteDatabase db = getWritableDatabase();
/* 863 */               String[] whereArgs = {String.valueOf(like.postId)};
/* 866 */               result = 0;
                        try {
/* 869 */                   db.beginTransaction();
/* 872 */                   Cursor c = db.query("posts", new String[]{Vine.PostColumns.METADATA_FLAGS}, "post_id=?", whereArgs, null, null, null);
/* 875 */                   if (c != null) {
                                try {
/* 877 */                           if (c.moveToFirst()) {
/* 878 */                               ContentValues cv = new ContentValues();
/* 879 */                               int flag = c.getInt(0);
/* 881 */                               cv.put(Vine.PostColumns.METADATA_FLAGS, Integer.valueOf(flag | 8));
/* 882 */                               result = db.update("posts", cv, "post_id=?", whereArgs) > 0 ? 1 : 0;
/* 884 */                               if (LOGGABLE) {
/* 885 */                                   Log.d(TAG, "Updating like value in post: " + like.postId);
                                        }
                                    }
/* 889 */                           if (c != null) {
/* 890 */                               c.close();
                                    }
                                } catch (Throwable th) {
                                    if (c != null) {
/* 890 */                               c.close();
                                    }
                                    throw th;
                                }
                            }
/* 896 */                   ArrayList<VineLike> likes = new ArrayList<>(1);
/* 897 */                   likes.add(like);
/* 898 */                   int mergeResult = mergeLikes(likes, like.postId, 0, 0);
/* 899 */                   db.setTransactionSuccessful();
/* 901 */                   safeFinishTransaction(db);
/* 903 */                   if (notify && (result > 0 || mergeResult > 0)) {
/* 904 */                       notifyPostCommentsViewUris();
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

/* 913 */       public void clearFileCache() {
/* 914 */           Util.removeCache(this.mAppContext);
/* 915 */           ImageUtils.removeFiles(this.mAppContext);
                }

/* 925 */       public synchronized int removeLike(long postId, long myUserId, boolean notify) {
                    int result;
                    try {
                        SQLiteDatabase db = getWritableDatabase();
/* 927 */               result = 0;
                        try {
/* 930 */                   db.beginTransaction();
/* 931 */                   String[] whereArgs = {String.valueOf(postId)};
/* 932 */                   Cursor c = db.query("posts", new String[]{Vine.PostColumns.METADATA_FLAGS}, "post_id=?", whereArgs, null, null, null);
/* 935 */                   if (c != null) {
                                try {
/* 937 */                           if (c.moveToFirst()) {
/* 938 */                               ContentValues cv = new ContentValues();
/* 939 */                               int flag = c.getInt(0);
/* 941 */                               cv.put(Vine.PostColumns.METADATA_FLAGS, Integer.valueOf(flag & (-9)));
/* 943 */                               if (LOGGABLE) {
/* 944 */                                   Log.d(TAG, "Removing like in post: " + postId);
                                        }
/* 946 */                               result = db.update("posts", cv, "post_id=?", whereArgs) > 0 ? 1 : 0;
                                    }
/* 950 */                           c.close();
                                } catch (Throwable th) {
                                    c.close();
                                    throw th;
                                }
                            }
/* 955 */                   int deletes = db.delete("likes", "post_id=? AND user_id=?", new String[]{String.valueOf(postId), String.valueOf(myUserId)});
/* 958 */                   int deletes2 = deletes + removePostsFromGroupByPostId(3, postId);
/* 959 */                   db.setTransactionSuccessful();
/* 960 */                   if (LOGGABLE) {
/* 961 */                       Log.d(TAG, "Deleted self like for post: " + postId);
                            }
/* 964 */                   safeFinishTransaction(db);
/* 966 */                   if (notify && (result > 0 || deletes2 > 0)) {
/* 967 */                       notifyPostCommentsViewUris();
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

/* 978 */       public synchronized int revine(VineRepost repost, long userId, boolean notify) {
                    int result;
                    try {
                        SQLiteDatabase db = getWritableDatabase();
/* 980 */               result = 0;
                        try {
/* 983 */                   db.beginTransaction();
/* 984 */                   String[] whereArgs = {String.valueOf(repost.postId)};
/* 985 */                   Cursor c = db.query("posts", new String[]{Vine.PostColumns.METADATA_FLAGS}, "post_id=?", whereArgs, null, null, null);
/* 988 */                   if (c != null) {
                                try {
/* 990 */                           if (c.getCount() > 0 && c.moveToFirst()) {
/* 991 */                               ContentValues cv = new ContentValues();
/* 992 */                               int flag = c.getInt(0);
/* 994 */                               cv.put(Vine.PostColumns.METADATA_FLAGS, Integer.valueOf(flag | 16));
/* 995 */                               cv.put("my_repost_id", Long.valueOf(repost.repostId));
/* 996 */                               c.close();
/* 997 */                               if (LOGGABLE) {
/* 998 */                                   Log.d(TAG, "Revined post " + repost.postId);
                                        }
/* 1000 */                              result = db.update("posts", cv, "post_id=?", whereArgs) > 0 ? 1 : 0;
                                    }
/* 1005 */                          c.close();
                                } catch (Throwable th) {
                                    c.close();
                                    throw th;
                                }
                            }
/* 1010 */                  Cursor c2 = db.query(Vine.PostGroups.TABLE_NAME, new String[]{Vine.PostGroupColumns.REPOST_DATA}, VineDatabaseSQL.SELECTION_POST_ID_AND_TYPE_AND_TAG, new String[]{String.valueOf(repost.postId), String.valueOf(2), String.valueOf(userId)}, null, null, null);
/* 1019 */                  if (c2 != null) {
                                try {
/* 1021 */                          if (c2.getCount() > 0 && c2.moveToFirst()) {
/* 1023 */                              VineRepost currentData = (VineRepost) Util.fromByteArray(c2.getBlob(0));
/* 1024 */                              if (currentData != null) {
/* 1025 */                                  currentData.repostId = repost.repostId;
                                        } else {
/* 1027 */                                  currentData = new VineRepost();
/* 1028 */                                  currentData.repostId = repost.repostId;
/* 1029 */                                  currentData.postId = repost.postId;
                                        }
/* 1031 */                              ContentValues cv2 = new ContentValues();
/* 1032 */                              cv2.put(Vine.PostGroupColumns.REPOST_DATA, Util.toByteArray(currentData));
/* 1033 */                              cv2.put(Vine.PostGroupColumns.SORT_ID, Long.valueOf(repost.repostId));
/* 1034 */                              db.update(Vine.PostGroups.TABLE_NAME, cv2, VineDatabaseSQL.SELECTION_POST_ID_AND_TYPE_AND_TAG, new String[]{String.valueOf(repost.postId), String.valueOf(2), String.valueOf(userId)});
/* 1040 */                              if (LOGGABLE) {
/* 1041 */                                  Log.d(TAG, "Updated repost data for post " + currentData.postId + " with repost id " + currentData.repostId + " for user timeline.");
                                        }
                                    } else {
/* 1047 */                              ContentValues cv3 = new ContentValues();
/* 1048 */                              cv3.put(Vine.PostGroupColumns.TYPE, (Integer) 2);
/* 1049 */                              cv3.put("post_id", Long.valueOf(repost.postId));
/* 1050 */                              cv3.put(Vine.PostGroupColumns.SORT_ID, Long.valueOf(repost.repostId));
/* 1051 */                              cv3.put("tag", Long.valueOf(userId));
/* 1052 */                              cv3.put(Vine.PostGroupColumns.REPOST_DATA, Util.toByteArray(repost));
/* 1053 */                              db.insert(Vine.PostGroups.TABLE_NAME, "post_id", cv3);
/* 1054 */                              if (LOGGABLE) {
/* 1055 */                                  Log.d(TAG, "Inserted repost data for post " + repost.postId + " with repost id " + repost.repostId + " for user timeline.");
                                        }
                                    }
/* 1060 */                          c2.close();
                                } catch (Throwable th2) {
                                    c2.close();
                                    throw th2;
                                }
                            }
/* 1063 */                  db.setTransactionSuccessful();
/* 1065 */                  safeFinishTransaction(db);
/* 1067 */                  if (notify && result > 0) {
/* 1068 */                      notifyPostCommentsViewUris();
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
/* 1080 */      public synchronized ArrayList<VineSingleNotification> mergePushNotification(VineSingleNotification notification) {
                    ArrayList<VineSingleNotification> ret;
                    try {
                        SQLiteDatabase db = getWritableDatabase();
/* 1082 */              ret = new ArrayList<>();
/* 1083 */              int type = notification.notificationTypeId;
                        String selection = "notification_id=?" + (notification.isMessaging() ? " AND notification_type=?" : "");
/* 1088 */              String[] whereArgs = notification.isMessaging() ? new String[]{String.valueOf(notification.notificationId), String.valueOf(type)} : new String[]{String.valueOf(notification.notificationId)};
/* 1091 */              Cursor c = db.query("notifications", VineDatabaseSQL.NotificationsQuery.PROJECTION, selection, whereArgs, null, null, Vine.Notifications.SORT_ORDER_DEFAULT);
                        try {
/* 1096 */                  db.beginTransaction();
/* 1098 */                  if (c != null) {
                                try {
/* 1100 */                          if (!c.moveToFirst()) {
/* 1101 */                              ContentValues values = new ContentValues();
/* 1102 */                              fillNotificationValues(values, notification, Util.toPrettyComment(notification));
/* 1094 */                              int inserted = 0 + (db.insert("notifications", null, values) > 0 ? 1 : 0);
/* 1104 */                              if (inserted > 0) {
/* 1105 */                                  ret.addAll(getNotifications(type));
                                        }
                                    }
/* 1109 */                          c.close();
                                } catch (Throwable th) {
                                    c.close();
                                    throw th;
                                }
                            }
/* 1112 */                  db.setTransactionSuccessful();
/* 1114 */                  safeFinishTransaction(db);
                        } catch (Throwable th2) {
                            safeFinishTransaction(db);
                            throw th2;
                        }
                    } catch (Throwable th3) {
                        throw th3;
                    }
                    return ret;
                }

/* 1119 */      public synchronized ArrayList<VineSingleNotification> getNotifications(int notificationType) {
                    ArrayList<VineSingleNotification> ret;
                    synchronized (this) {
                        boolean isPrivateMessage = notificationType == 18;
                        try {
                            String selection = Vine.NotificationsColumns.NOTIFICATION_TYPE + (isPrivateMessage ? "=?" : "<>?");
/* 1123 */                  String[] whereArgs = {String.valueOf(18)};
/* 1125 */                  SQLiteDatabase db = getReadableDatabase();
/* 1127 */                  ret = new ArrayList<>();
/* 1128 */                  Cursor c = db.query("notifications", VineDatabaseSQL.NotificationsQuery.PROJECTION, selection, whereArgs, null, null, Vine.Notifications.SORT_ORDER_DEFAULT, "10");
/* 1131 */                  if (c != null) {
/* 1132 */                      while (c.moveToNext()) {
/* 1133 */                          ret.add(new VineSingleNotification(c));
                                }
/* 1135 */                      c.close();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return ret;
                }

/* 1140 */      public synchronized int deletePushNotifications(int notificationDisplayId) {
                    int deleted;
                    synchronized (this) {
                        boolean isPrivateMessage = notificationDisplayId == 2;
                        try {
                            String selection = Vine.NotificationsColumns.NOTIFICATION_TYPE + (isPrivateMessage ? "=?" : "<>?");
/* 1144 */                  String[] whereArgs = {String.valueOf(18)};
/* 1146 */                  SQLiteDatabase db = getWritableDatabase();
/* 1147 */                  deleted = 0 + db.delete("notifications", selection, whereArgs);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return deleted;
                }

/* 1152 */      public void deletePushNotificationsForConversation(long conversationRowId) {
/* 1153 */          SQLiteDatabase db = getWritableDatabase();
/* 1154 */          db.delete("notifications", "conversation_row_id=?", new String[]{String.valueOf(conversationRowId)});
                }

/* 1157 */      public synchronized Uri mergeSuggestedTag(VineTag tag) {
                    Uri result;
                    result = null;
                    try {
/* 1162 */              String[] selectionArgs = {String.valueOf(tag.tagId)};
/* 1161 */              Cursor c = this.mContentResolver.query(Vine.TagsRecentlyUsed.CONTENT_URI, VineDatabaseSQL.TagsRecentlyUsedQuery.PROJECTION, "tag_id=?", selectionArgs, "_id DESC");
/* 1167 */              if (c != null) {
/* 1168 */                  ContentValues values = new ContentValues();
/* 1169 */                  if (c.getCount() < 1) {
/* 1170 */                      fillTagValues(values, tag);
/* 1171 */                      result = this.mContentResolver.insert(Vine.TagsRecentlyUsed.CONTENT_URI_PUT_TAG, values);
                            } else if (c.moveToFirst()) {
/* 1174 */                      String[] selectionArgs2 = {String.valueOf(tag.tagId)};
/* 1175 */                      values.put("last_used_timestamp", Long.valueOf(System.currentTimeMillis()));
/* 1173 */                      this.mContentResolver.update(Vine.TagsRecentlyUsed.CONTENT_URI_UPDATE_RECENT_TAG, values, "tag_id=?", selectionArgs2);
                            }
/* 1179 */                  c.close();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return result;
                }

/* 1189 */      public synchronized int unRevine(long postId, long userId, boolean following, boolean notify) {
                    int result;
                    Cursor c;
                    try {
                        SQLiteDatabase db = getWritableDatabase();
/* 1191 */              result = 0;
                        try {
/* 1194 */                  db.beginTransaction();
/* 1195 */                  String[] whereArgs = {String.valueOf(postId)};
/* 1196 */                  Cursor c2 = db.query("posts", new String[]{Vine.PostColumns.METADATA_FLAGS}, "post_id=?", whereArgs, null, null, null);
/* 1199 */                  if (c2 != null) {
                                try {
/* 1201 */                          if (c2.getCount() > 0 && c2.moveToFirst()) {
/* 1202 */                              ContentValues cv = new ContentValues();
/* 1203 */                              int flag = c2.getInt(0);
/* 1205 */                              cv.put(Vine.PostColumns.METADATA_FLAGS, Integer.valueOf(flag & (-17)));
/* 1206 */                              cv.put("my_repost_id", (Integer) 0);
/* 1207 */                              if (LOGGABLE) {
/* 1208 */                                  Log.d(TAG, "Unrevined post " + postId);
                                        }
/* 1210 */                              result = db.update("posts", cv, "post_id=?", whereArgs) > 0 ? 1 : 0;
                                    }
/* 1214 */                          c2.close();
                                } catch (Throwable th) {
                                    c2.close();
                                    throw th;
                                }
                            }
/* 1217 */                  int deletedGroups = db.delete(Vine.PostGroups.TABLE_NAME, VineDatabaseSQL.SELECTION_POST_ID_AND_TYPE_AND_TAG, new String[]{String.valueOf(postId), String.valueOf(2), String.valueOf(userId)});
/* 1223 */                  if (LOGGABLE) {
/* 1224 */                      Log.d(TAG, "Deleted " + deletedGroups + " with tag " + userId + " and type 2 and post id " + postId + " due to unrevining.");
                            }
/* 1229 */                  if (!following && (c = db.query("posts", new String[]{"user_id"}, "post_id=?", new String[]{String.valueOf(postId)}, null, null, null)) != null) {
                                try {
/* 1234 */                          if (c.moveToFirst()) {
/* 1235 */                              long postUserId = c.getLong(0);
/* 1236 */                              long postUserRowId = getUserRowIdForUserRemoteId(postUserId);
/* 1237 */                              following = amFollowingUser(postUserRowId);
                                    }
/* 1240 */                          c.close();
                                } catch (Throwable th2) {
                                    c.close();
                                    throw th2;
                                }
                            }
/* 1245 */                  if (!following) {
/* 1247 */                      int deletedGroups2 = deletedGroups + db.delete(Vine.PostGroups.TABLE_NAME, "post_id=? AND post_type=?", new String[]{String.valueOf(postId), String.valueOf(1)});
/* 1252 */                      if (LOGGABLE) {
/* 1253 */                          Log.d(TAG, "Deleted " + deletedGroups2 + " for type 1 and post id " + postId + " due to unrevining.");
                                }
                            } else {
/* 1258 */                      ContentValues cv2 = new ContentValues();
/* 1259 */                      cv2.putNull(Vine.PostGroupColumns.REPOST_DATA);
/* 1260 */                      int r = db.update(Vine.PostGroups.TABLE_NAME, cv2, "post_id=? AND post_type=?", new String[]{String.valueOf(postId), String.valueOf(1)});
/* 1263 */                      Log.d(TAG, "Removed   " + r + " repost data for type 1 and post id " + postId + " due to unrevining.");
                            }
/* 1269 */                  db.setTransactionSuccessful();
/* 1271 */                  safeFinishTransaction(db);
/* 1273 */                  if (notify) {
/* 1274 */                      notifyPostCommentsViewUris();
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

/* 1279 */      public synchronized int deletePost(long postId) {
                    int deleted;
                    try {
                        SQLiteDatabase db = getWritableDatabase();
                        try {
/* 1283 */                  db.beginTransaction();
/* 1284 */                  deleted = db.delete("posts", "post_id=?", new String[]{String.valueOf(postId)});
/* 1286 */                  int deletedGroups = 0;
/* 1287 */                  if (deleted > 0) {
/* 1288 */                      deletedGroups = db.delete(Vine.PostGroups.TABLE_NAME, "post_id=?", new String[]{String.valueOf(postId)});
                            }
/* 1291 */                  if (deleted > 0 || deletedGroups > 0) {
/* 1292 */                      notifyPostCommentsViewUris();
                            }
/* 1295 */                  if (LOGGABLE) {
/* 1296 */                      if (deletedGroups > 0) {
/* 1297 */                          Log.d(TAG, "Removing the post: " + postId);
                                } else {
/* 1299 */                          Log.e(TAG, "Couldn't delete post: " + postId);
                                }
                            }
/* 1302 */                  db.setTransactionSuccessful();
/* 1304 */                  safeFinishTransaction(db);
                        } catch (Throwable th) {
                            safeFinishTransaction(db);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                    return deleted;
                }

/* 1309 */      public synchronized void expireTimeline(int type) {
                    try {
                        removePostsFromGroup(type);
/* 1311 */              notifyPostCommentsViewUris();
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 1314 */      private synchronized int removePostsFromGroupByPostId(int type, long postId) {
                    SQLiteDatabase db;
                    String[] selArgs;
                    try {
                        db = getWritableDatabase();
/* 1317 */              selArgs = new String[]{String.valueOf(type), String.valueOf(postId)};
                    } catch (Throwable th) {
                        throw th;
                    }
/* 1318 */          return db.delete(Vine.PostGroups.TABLE_NAME, "post_type=? AND post_id=?", selArgs);
                }

/* 1321 */      private synchronized int removePostsFromGroup(int type) {
                    int numGroups;
                    try {
                        SQLiteDatabase db = getWritableDatabase();
                        try {
/* 1325 */                  db.beginTransaction();
/* 1326 */                  numGroups = db.delete(Vine.PostGroups.TABLE_NAME, VineDatabaseSQL.SELECTION_POST_GROUP_TYPE, new String[]{String.valueOf(type)});
/* 1328 */                  db.setTransactionSuccessful();
/* 1329 */                  if (LOGGABLE) {
/* 1330 */                      Log.d(TAG, "Clearing group " + type + " deleting " + numGroups + " groups ");
                            }
/* 1333 */                  safeFinishTransaction(db);
                        } catch (Throwable th) {
                            safeFinishTransaction(db);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                    return numGroups;
                }

/* 1338 */      public synchronized int removePostsFromGroupByUser(long userId, int type, String tag) {
                    int numGroups;
                    try {
                        SQLiteDatabase db = getWritableDatabase();
/* 1340 */              Cursor c = db.query(Vine.PostGroupsView.TABLE_NAME, VineDatabaseSQL.PostGroupsQuery.PROJECTION, "user_id=? AND post_type=? AND tag=?", new String[]{String.valueOf(userId), String.valueOf(type), tag}, null, null, null);
/* 1345 */              ArrayList<String> postIdsToRemove = new ArrayList<>();
/* 1346 */              if (c != null) {
                            try {
/* 1348 */                      if (c.moveToFirst()) {
/* 1351 */                          do {
/* 1350 */                              postIdsToRemove.add(String.valueOf(c.getLong(0)));
/* 1351 */                          } while (c.moveToNext());
                                }
/* 1354 */                      c.close();
                            } catch (Throwable th) {
                                c.close();
                                throw th;
                            }
                        }
/* 1358 */              StringBuilder placeHolderString = new StringBuilder();
/* 1359 */              int size = postIdsToRemove.size();
                        for (int i = 0; i < size; i++) {
/* 1361 */                  placeHolderString.append("?");
/* 1362 */                  if (i < size - 1 && size != 1) {
/* 1363 */                      placeHolderString.append(",");
                            }
                        }
                        try {
/* 1368 */                  db.beginTransaction();
/* 1370 */                  String[] idArray = new String[postIdsToRemove.size()];
/* 1371 */                  postIdsToRemove.add(String.valueOf(type));
/* 1373 */                  String whereString = " IN (" + placeHolderString.toString() + ") AND " + Vine.PostGroupColumns.TYPE + "=?";
/* 1376 */                  numGroups = db.delete(Vine.PostGroups.TABLE_NAME, "post_id" + whereString, (String[]) postIdsToRemove.toArray(idArray));
/* 1378 */                  db.setTransactionSuccessful();
/* 1380 */                  if (LOGGABLE) {
/* 1381 */                      Log.d(TAG, "Due to unfollowing " + userId + " deleting " + numGroups + " groups  , of type " + type + " and tag " + tag);
                            }
/* 1385 */                  safeFinishTransaction(db);
/* 1388 */                  if (numGroups > 0) {
/* 1389 */                      notifyPostCommentsViewUris();
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

/* 1397 */      public synchronized int mergeUsers(Collection<VineUser> users, int type, String groupTag, int next, int previous, LongSparseArray<Long> orderMap, long sectionRefreshTime, int sectionType) {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return mergeUsers(users, type, groupTag, next, previous, null, orderMap, sectionRefreshTime, sectionType);
                }

/* 1402 */      public synchronized int mergeUsers(Collection<VineUser> users, int type, String groupTag, int next, int previous, LongSparseArray<Long> orderMap) {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return mergeUsers(users, type, groupTag, next, previous, null, orderMap);
                }

/* 1408 */      public synchronized int mergeUsers(Collection<VineUser> users, int type, String groupTag, int next, int previous, LongSparseArray<Long> tagMap, LongSparseArray<Long> orderMap) {
                    try {
                    } catch (Throwable th) {
                        throw th;
                    }
                    return mergeUsers(users, type, groupTag, next, previous, tagMap, orderMap, -1L, -1);
                }

/* 1412 */      private void mergeUserSection(SQLiteDatabase db, ContentValues values, VineUser user, long refresh, int sectionType) {
/* 1413 */          if (sectionType >= 0) {
/* 1414 */              values.clear();
/* 1415 */              values.put(Vine.UserSections.SECTION_TYPE, Integer.valueOf(sectionType));
                        if (refresh > 0) {
/* 1417 */                  values.put(Vine.UserSections.LAST_SECTION_REFRESH, Long.valueOf(refresh));
                        }
                        if (user.friendIndex > 0) {
/* 1420 */                  values.put(Vine.UserSections.SECTION_INDEX, Long.valueOf(user.friendIndex));
                        }
/* 1422 */              values.put("user_id", Long.valueOf(user.userId));
/* 1423 */              values.put(Vine.UserSections.SECTION_TITLE, user.sectionTitle);
/* 1426 */              String[] whereArgs = {String.valueOf(user.userId), String.valueOf(sectionType)};
/* 1428 */              Cursor query = db.query(Vine.UserSections.TABLE_NAME, new String[]{"_id"}, "user_id=? AND section_type=?", whereArgs, null, null, null);
/* 1431 */              if (query != null) {
                            try {
/* 1433 */                      if (query.moveToFirst()) {
/* 1434 */                          db.update(Vine.UserSections.TABLE_NAME, values, "user_id=? AND section_type=?", whereArgs);
                                } else {
/* 1436 */                          db.insert(Vine.UserSections.TABLE_NAME, null, values);
                                }
/* 1439 */                      query.close();
                            } catch (Throwable th) {
                                query.close();
                                throw th;
                            }
                        }
                    }
                }

/* 1460 */      public synchronized int mergeUsers(Collection<VineUser> users, int type, String groupTag, int next, int previous, LongSparseArray<Long> tagMap, LongSparseArray<Long> orderMap, long sectionRefreshTime, int sectionType) {
                    int numInserted;
                    Cursor tableCursor;
                    try {
                        SQLiteDatabase db = getWritableDatabase();
/* 1464 */              LongSparseArray<VineUser> newUsers = new LongSparseArray<>(users.size());
/* 1465 */              LongSparseArray<VineUser> updatedUsers = new LongSparseArray<>(users.size());
                        for (VineUser user : users) {
/* 1467 */                  newUsers.put(user.userId, user);
                        }
/* 1471 */              Cursor c = db.query("users", new String[]{"user_id"}, null, null, null, null, null);
/* 1473 */              if (c != null) {
/* 1477 */                  while (c.moveToNext()) {
                                try {
/* 1478 */                          long userId = c.getLong(0);
/* 1479 */                          VineUser user2 = newUsers.removeKey(userId);
/* 1480 */                          if (user2 != null) {
/* 1481 */                              updatedUsers.put(userId, user2);
                                    }
                                } catch (Throwable th) {
                                    c.close();
                                    throw th;
                                }
                            }
                            c.close();
                        }
/* 1490 */              numInserted = 0;
/* 1491 */              int numUpdated = 0;
/* 1493 */              long lastRefreshTime = System.currentTimeMillis();
/* 1495 */              int size = newUsers.size();
/* 1496 */              if (size > 0) {
/* 1497 */                  db.beginTransaction();
                            try {
/* 1499 */                      ContentValues values = new ContentValues();
                                for (int i = 0; i < size; i++) {
/* 1501 */                          VineUser user3 = newUsers.valueAt(i);
/* 1502 */                          values.clear();
/* 1503 */                          fillUserContentValues(values, user3, lastRefreshTime);
/* 1504 */                          numInserted += db.insert("users", "user_id", values) > 0 ? 1 : 0;
/* 1505 */                          mergeUserSection(db, values, user3, sectionRefreshTime, sectionType);
                                }
/* 1507 */                      db.setTransactionSuccessful();
/* 1509 */                      safeFinishTransaction(db);
                            } catch (Throwable th2) {
                                safeFinishTransaction(db);
                                throw th2;
                            }
                        }
/* 1513 */              int size2 = updatedUsers.size();
/* 1514 */              if (size2 > 0) {
/* 1515 */                  db.beginTransaction();
                            try {
/* 1517 */                      ContentValues values2 = new ContentValues();
                                for (int i2 = 0; i2 < size2; i2++) {
/* 1519 */                          VineUser user4 = updatedUsers.valueAt(i2);
/* 1520 */                          values2.clear();
/* 1521 */                          fillUserContentValues(values2, user4, lastRefreshTime);
/* 1522 */                          numUpdated += db.update("users", values2, VineDatabaseSQL.SELECTION_USER_ID, new String[]{String.valueOf(user4.userId)}) > 0 ? 1 : 0;
/* 1524 */                          mergeUserSection(db, values2, user4, sectionRefreshTime, sectionType);
                                }
/* 1526 */                      db.setTransactionSuccessful();
/* 1528 */                      safeFinishTransaction(db);
                            } catch (Throwable th3) {
                                safeFinishTransaction(db);
                                throw th3;
                            }
                        }
/* 1534 */              int deleted = 0;
/* 1535 */              if (numInserted > 0 && (tableCursor = db.query("users", VineDatabaseSQL.TableQuery.PROJECTION, null, null, null, null, null)) != null) {
                            try {
/* 1541 */                      if (tableCursor.moveToFirst()) {
/* 1542 */                          int size3 = tableCursor.getCount();
/* 1543 */                          if (size3 > 10000) {
                                        try {
/* 1545 */                                  Cursor oldestRefreshId = db.rawQuery("SELECT last_refresh FROM users ORDER BY last_refresh DESC LIMIT 1 OFFSET 5000", null);
/* 1551 */                                  db.beginTransaction();
/* 1552 */                                  if (oldestRefreshId != null) {
                                                try {
/* 1554 */                                          if (oldestRefreshId.moveToFirst()) {
/* 1555 */                                              long oldestNthId = oldestRefreshId.getLong(0);
/* 1557 */                                              db.execSQL("DELETE FROM user_groups WHERE user_id IN (SELECT user_id FROM users WHERE last_refresh < ?);", new String[]{String.valueOf(oldestNthId)});
/* 1563 */                                              deleted = db.delete("users", "last_refresh<?", new String[]{String.valueOf(oldestNthId)});
/* 1565 */                                              if (LOGGABLE) {
/* 1566 */                                                  Log.d(VineAPI.RESOURCE_SHARED, "Due to reaching maximum table size (" + size3 + " with a max of 10000), deleted " + deleted + " users, all less than last refresh of " + oldestNthId);
                                                        }
                                                    }
/* 1573 */                                          oldestRefreshId.close();
                                                } catch (Throwable th4) {
                                                    oldestRefreshId.close();
                                                    throw th4;
                                                }
                                            }
/* 1576 */                                  db.setTransactionSuccessful();
/* 1578 */                                  safeFinishTransaction(db);
                                        } catch (Throwable th5) {
                                            safeFinishTransaction(db);
                                            throw th5;
                                        }
                                    }
                                }
/* 1583 */                      tableCursor.close();
                            } catch (Throwable th6) {
                                tableCursor.close();
                                throw th6;
                            }
                        }
/* 1588 */              if (numInserted > 0 || numUpdated > 0 || deleted > 0) {
/* 1589 */                  if (type != -1) {
/* 1590 */                      mergeUserGroups(users, type, groupTag, tagMap, orderMap);
/* 1591 */                      if (next > 0 || previous > 0) {
/* 1592 */                          savePageCursor(1, type, groupTag, next, previous, 0L, true);
                                }
                            }
/* 1596 */                  this.mContentResolver.notifyChange(Vine.UserGroupsView.CONTENT_URI, null);
/* 1597 */                  this.mContentResolver.notifyChange(Vine.UserGroupsView.CONTENT_URI_FIND_FRIENDS_TWITTER, null);
/* 1598 */                  this.mContentResolver.notifyChange(Vine.UserGroupsView.CONTENT_URI_FIND_FRIENDS_ADDRESS, null);
/* 1599 */                  this.mContentResolver.notifyChange(Vine.UserGroupsView.CONTENT_URI_LIKERS, null);
/* 1600 */                  this.mContentResolver.notifyChange(Vine.UserGroupsView.CONTENT_URI_FOLLOWERS, null);
/* 1601 */                  this.mContentResolver.notifyChange(Vine.UserGroupsView.CONTENT_URI_FOLLOWING, null);
/* 1602 */                  this.mContentResolver.notifyChange(Vine.UserGroupsView.CONTENT_URI_FRIENDS, null);
/* 1603 */                  this.mContentResolver.notifyChange(Vine.UserGroupsView.CONTENT_URI_USER_SEARCH_RESULTS, null);
                        }
/* 1606 */              if (LOGGABLE) {
/* 1607 */                  Log.d(TAG, "Inserted " + numInserted + " new users and updating " + numUpdated + " of type " + type + ".");
                        }
                    } catch (Throwable th7) {
                        throw th7;
                    }
                    return numInserted;
                }

/* 1614 */      public synchronized int mergeSearchedTags(Collection<VineTag> tags, int next, int previous) {
                    int numInserted;
                    try {
                        SQLiteDatabase db = getWritableDatabase();
/* 1618 */              HashMap<Long, VineTag> newTags = new HashMap<>(tags.size());
/* 1619 */              HashMap<Long, VineTag> updatedTags = new HashMap<>(tags.size());
                        for (VineTag tag : tags) {
/* 1621 */                  newTags.put(Long.valueOf(tag.tagId), tag);
                        }
/* 1625 */              Cursor c = db.query(Vine.TagSearchResults.TABLE_NAME, new String[]{Vine.TagsColumns.TAG_ID}, null, null, null, null, null);
/* 1628 */              if (c != null) {
/* 1632 */                  while (c.moveToNext()) {
                                try {
/* 1633 */                          long tagId = c.getLong(c.getColumnIndex(Vine.TagsColumns.TAG_ID));
/* 1634 */                          VineTag tag2 = newTags.remove(Long.valueOf(tagId));
/* 1635 */                          if (tag2 != null) {
/* 1636 */                              updatedTags.put(Long.valueOf(tagId), tag2);
                                    }
                                } catch (Throwable th) {
                                    c.close();
                                    throw th;
                                }
                            }
                            c.close();
                        }
/* 1644 */              numInserted = 0;
/* 1645 */              int numUpdated = 0;
/* 1647 */              if (!newTags.isEmpty()) {
/* 1648 */                  db.beginTransaction();
                            try {
/* 1650 */                      ContentValues values = new ContentValues();
                                for (VineTag tag3 : newTags.values()) {
/* 1652 */                          values.clear();
/* 1653 */                          fillTagValues(values, tag3);
/* 1654 */                          numInserted += db.insert(Vine.TagSearchResults.TABLE_NAME, Vine.TagsColumns.TAG_ID, values) > 0 ? 1 : 0;
                                }
/* 1657 */                      db.setTransactionSuccessful();
/* 1659 */                      safeFinishTransaction(db);
                            } catch (Throwable th2) {
                                safeFinishTransaction(db);
                                throw th2;
                            }
                        }
/* 1663 */              if (!updatedTags.isEmpty()) {
/* 1664 */                  db.beginTransaction();
                            try {
/* 1666 */                      ContentValues values2 = new ContentValues();
                                for (VineTag tag4 : updatedTags.values()) {
/* 1668 */                          values2.clear();
/* 1669 */                          fillTagValues(values2, tag4);
/* 1670 */                          numUpdated += db.update(Vine.TagSearchResults.TABLE_NAME, values2, "tag_id=?", new String[]{String.valueOf(tag4.tagId)}) > 0 ? 1 : 0;
                                }
/* 1674 */                      db.setTransactionSuccessful();
/* 1676 */                      safeFinishTransaction(db);
                            } catch (Throwable th3) {
                                safeFinishTransaction(db);
                                throw th3;
                            }
                        }
/* 1680 */              if (numInserted > 0 || numUpdated > 0) {
/* 1681 */                  if (next > 0 || previous > 0) {
/* 1682 */                      savePageCursor(4, 0, null, next, previous, 0L, true);
                            }
/* 1684 */                  this.mContentResolver.notifyChange(Vine.TagSearchResults.CONTENT_URI, null);
                        }
/* 1687 */              if (LOGGABLE) {
/* 1688 */                  Log.d(TAG, "Inserted " + numInserted + " and updated " + numUpdated + " tags.");
                        }
                    } catch (Throwable th4) {
                        throw th4;
                    }
                    return numInserted;
                }

/* 1695 */      public synchronized int mergeChannels(Collection<VineChannel> channels, int next, int previous) {
                    int inserted;
                    try {
                        SQLiteDatabase db = getWritableDatabase();
/* 1697 */              inserted = 0;
/* 1698 */              if (!channels.isEmpty()) {
/* 1699 */                  db.beginTransaction();
                            try {
/* 1701 */                      db.delete("channels", null, null);
/* 1702 */                      ContentValues values = new ContentValues();
                                for (VineChannel channel : channels) {
/* 1704 */                          values.clear();
/* 1705 */                          fillChannelValues(values, channel);
/* 1706 */                          inserted += db.insert("channels", null, values) > 0 ? 1 : 0;
                                }
/* 1709 */                      db.setTransactionSuccessful();
/* 1711 */                      safeFinishTransaction(db);
                            } catch (Throwable th) {
                                safeFinishTransaction(db);
                                throw th;
                            }
                        }
/* 1715 */              if (next > 0 || previous > 0) {
/* 1716 */                  savePageCursor(2, 0, null, next, previous, 0L, true);
                        }
/* 1719 */              if (inserted > 0) {
/* 1720 */                  if (LOGGABLE) {
/* 1721 */                      Log.d(TAG, "Inserted " + inserted + " new channels.");
                            }
/* 1723 */                  this.mContentResolver.notifyChange(Vine.Channels.CONTENT_URI, null);
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                    return inserted;
                }

/* 1730 */      private synchronized void mergeUserGroups(Collection<VineUser> users, int type, String groupTag, LongSparseArray<Long> tagMap, LongSparseArray<Long> orderMap) {
                    Cursor cursor;
                    if (users != null && type != -1) {
                        try {
/* 1735 */                  if (LOGGABLE) {
/* 1736 */                      Log.d(TAG, "mergeUserGroups: " + users.size() + ", tagged by: " + groupTag + ", of type: " + type);
                            }
/* 1741 */                  LinkedHashMap<Long, VineUser> newUserGroupsMap = new LinkedHashMap<>();
                            for (VineUser user : users) {
/* 1743 */                      newUserGroupsMap.put(Long.valueOf(user.userId), user);
                            }
/* 1747 */                  SQLiteDatabase db = getWritableDatabase();
/* 1750 */                  if (groupTag != null) {
/* 1751 */                      cursor = db.query("user_groups", VineDatabaseSQL.UserGroupsQuery.PROJECTION, VineDatabaseSQL.SELECTION_USER_GROUP_TYPE_AND_TAG, new String[]{String.valueOf(type), String.valueOf(groupTag)}, null, null, null);
                            } else {
/* 1758 */                      cursor = db.query("user_groups", VineDatabaseSQL.UserGroupsQuery.PROJECTION, VineDatabaseSQL.SELECTION_USER_GROUP_TYPE, new String[]{String.valueOf(type)}, null, null, null);
                            }
/* 1765 */                  if (cursor != null) {
/* 1770 */                      while (cursor.moveToNext()) {
                                    try {
/* 1772 */                              newUserGroupsMap.remove(Long.valueOf(cursor.getLong(0)));
                                    } catch (Throwable th) {
                                        cursor.close();
                                        throw th;
                                    }
                                }
/* 1775 */                      cursor.close();
/* 1778 */                      if (LOGGABLE) {
/* 1779 */                          Log.d(TAG, "Inserting new user groups: " + newUserGroupsMap.size());
                                }
/* 1782 */                      int newUserGroupsCount = newUserGroupsMap.size();
/* 1783 */                      if (newUserGroupsCount > 0) {
/* 1785 */                          db.beginTransaction();
                                    try {
/* 1787 */                              ContentValues values = new ContentValues();
/* 1788 */                              values.put("type", Integer.valueOf(type));
/* 1790 */                              if (groupTag != null) {
/* 1791 */                                  values.put("tag", groupTag);
                                        }
/* 1793 */                              boolean useOrdering = orderMap != null;
                                        for (VineUser user2 : newUserGroupsMap.values()) {
/* 1796 */                                  values.put("g_flags", (Integer) 0);
/* 1797 */                                  values.put("user_id", Long.valueOf(user2.userId));
/* 1798 */                                  if (useOrdering && orderMap.get(user2.userId) != null) {
/* 1799 */                                      values.put(Vine.UserGroupColumns.ORDER_ID, orderMap.get(user2.userId));
                                            } else if (tagMap != null && tagMap.get(user2.userId) != null) {
/* 1801 */                                      values.put("tag", tagMap.get(user2.userId));
                                            }
/* 1803 */                                  db.insert("user_groups", "user_id", values);
                                        }
/* 1805 */                              db.setTransactionSuccessful();
/* 1807 */                              safeFinishTransaction(db);
/* 1810 */                              this.mContentResolver.notifyChange(Vine.UserGroupsView.CONTENT_URI, null);
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

/* 1815 */      private synchronized void mergePostGroups(Collection<VinePost> posts, int type, String groupTag, LongSparseArray<Long> orderMap) {
                    if (posts != null && type != -1) {
                        try {
/* 1820 */                  if (LOGGABLE) {
/* 1821 */                      Log.d(TAG, "mergePostGroups: " + posts.size() + ", tagged by: " + groupTag + ", of type: " + type);
                            }
/* 1826 */                  LinkedHashMap<Long, VinePost> newPostGroups = new LinkedHashMap<>();
                            for (VinePost post : posts) {
/* 1828 */                      newPostGroups.put(Long.valueOf(post.postId), post);
                            }
/* 1832 */                  SQLiteDatabase db = getWritableDatabase();
/* 1833 */                  Cursor cursor = db.query(Vine.PostGroups.TABLE_NAME, VineDatabaseSQL.PostGroupsQuery.PROJECTION, VineDatabaseSQL.SELECTION_POST_GROUP_TYPE, new String[]{String.valueOf(type)}, null, null, null);
/* 1837 */                  if (cursor != null && cursor.moveToFirst()) {
/* 1842 */                      do {
                                    try {
/* 1841 */                              newPostGroups.remove(Long.valueOf(cursor.getLong(0)));
                                    } catch (Throwable th) {
                                        cursor.close();
                                        throw th;
                                    }
/* 1842 */                      } while (cursor.moveToNext());
/* 1844 */                      cursor.close();
                            }
/* 1848 */                  int numInserted = 0;
/* 1849 */                  int newPostGroupsCount = newPostGroups.size();
/* 1850 */                  if (newPostGroupsCount > 0) {
/* 1852 */                      db.beginTransaction();
                                try {
/* 1854 */                          ContentValues values = new ContentValues();
/* 1855 */                          values.put(Vine.PostGroupColumns.TYPE, Integer.valueOf(type));
/* 1856 */                          values.put("tag", groupTag);
                                    for (VinePost post2 : newPostGroups.values()) {
/* 1858 */                              values.put("g_flags", (Integer) 0);
/* 1859 */                              values.put("post_id", Long.valueOf(post2.postId));
/* 1860 */                              values.put(Vine.PostGroupColumns.REPOST_DATA, Util.toByteArray(post2.repost));
/* 1861 */                              if (post2.repost != null) {
/* 1862 */                                  values.put(Vine.PostGroupColumns.REPOSTER_ID, Long.valueOf(post2.repost.userId));
                                        } else {
/* 1864 */                                  values.remove(Vine.PostGroupColumns.REPOSTER_ID);
                                        }
/* 1866 */                              if (orderMap != null) {
/* 1867 */                                  values.put(Vine.PostGroupColumns.SORT_ID, orderMap.get(post2.postId));
                                        }
/* 1869 */                              numInserted += db.insert(Vine.PostGroups.TABLE_NAME, "post_id", values) > 0 ? 1 : 0;
                                    }
/* 1872 */                          db.setTransactionSuccessful();
/* 1874 */                          safeFinishTransaction(db);
/* 1876 */                          this.mContentResolver.notifyChange(Vine.PostGroupsView.CONTENT_URI, null);
                                } catch (Throwable th2) {
                                    safeFinishTransaction(db);
                                    throw th2;
                                }
                            }
/* 1879 */                  if (LOGGABLE) {
/* 1880 */                      Log.d(TAG, "Inserted new post groups: " + numInserted + " of type " + type);
                            }
                        } catch (Throwable th3) {
                            throw th3;
                        }
                    }
                }

/* 1885 */      public synchronized boolean removeUserWithType(long userId, int type, boolean notify, Uri uri) {
                    boolean z = true;
                    synchronized (this) {
                        try {
                            SQLiteDatabase db = getWritableDatabase();
/* 1888 */                  String[] selArgs = {String.valueOf(type), String.valueOf(userId)};
/* 1889 */                  int deleted = db.delete("user_groups", "type=? AND user_id=?", selArgs);
/* 1891 */                  if (deleted > 0) {
/* 1892 */                      if (notify) {
/* 1893 */                          this.mContentResolver.notifyChange(uri, null);
                                }
/* 1895 */                      if (LOGGABLE) {
/* 1896 */                          Log.d(TAG, "Deleted user with userId=" + userId + " of type=" + type);
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

/* 1910 */      public synchronized void mergeConversationRecipients(long conversationRowId, Collection<Long> userRowIds) {
                    int inserted = 0;
                    try {
/* 1913 */              if (LOGGABLE) {
/* 1914 */                  Log.d(TAG, "mergeConversations, count=" + userRowIds.size());
                        }
/* 1917 */              if (!userRowIds.isEmpty()) {
/* 1918 */                  SQLiteDatabase db = getWritableDatabase();
                            try {
/* 1920 */                      db.beginTransaction();
/* 1921 */                      ContentValues values = new ContentValues();
/* 1922 */                      values.put("conversation_row_id", Long.valueOf(conversationRowId));
                                for (Long entry : userRowIds) {
/* 1924 */                          values.put("user_row_id", entry);
/* 1925 */                          inserted += db.insert(Vine.ConversationRecipients.TABLE_NAME, null, values) > 0 ? 1 : 0;
                                }
/* 1928 */                      db.setTransactionSuccessful();
/* 1930 */                      safeFinishTransaction(db);
                            } catch (Throwable th) {
                                safeFinishTransaction(db);
                                throw th;
                            }
                        }
/* 1934 */              if (inserted > 0) {
/* 1935 */                  this.mContentResolver.notifyChange(Vine.InboxView.CONTENT_URI, null);
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }

/* 1943 */      public synchronized void mergeInbox(long sessionOwnerId, VinePagedData inbox, int next, int previous, long anchor, int networkType, int userGroup) throws IOException {
                    try {
                        if (inbox.participants != null && !inbox.participants.isEmpty()) {
/* 1945 */                  mergeUsers(inbox.participants, userGroup, null, 0, 0, null);
                        }
                        for (VineConversation conversation : (ArrayList<VineConversation>) inbox.items) {
/* 1951 */                  mergeConversation(sessionOwnerId, conversation, networkType, 0, 0, 0L);
                        }
/* 1954 */              if (next > 0 || previous > 0 || anchor > 0) {
/* 1955 */                  savePageCursor(6, networkType, null, next, previous, anchor, false);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 1959 */      public synchronized long mergeConversation(long sessionOwnerId, VineConversation conversation, int networkType, int nextPage, int prevPage, long anchor) throws IOException {
                    long conversationRowId;
                    try {
                        conversationRowId = mergeMessages(sessionOwnerId, conversation, networkType, nextPage, prevPage, anchor);
/* 1961 */              if (conversation.users != null) {
/* 1962 */                  ArrayList<Long> userRowIds = new ArrayList<>();
/* 1963 */                  Iterator<Long> it = conversation.users.iterator();
                            while (it.hasNext()) {
                                long user = it.next().longValue();
/* 1964 */                      long userRowId = getUserRowIdForUserRemoteId(user);
                                if (userRowId > -1) {
/* 1966 */                          userRowIds.add(Long.valueOf(userRowId));
                                }
                            }
/* 1969 */                  mergeConversationRecipients(conversationRowId, userRowIds);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return conversationRowId;
                }

/* 1974 */      public int getConversationNetworkType(long conversationRowId) {
/* 1975 */          SQLiteDatabase db = getReadableDatabase();
/* 1977 */          String[] selectionArgs = {String.valueOf(conversationRowId)};
/* 1978 */          String[] projection = {"network_type"};
/* 1979 */          Cursor c = db.query("conversations", projection, "_id =? ", selectionArgs, null, null, null, "1");
/* 1982 */          if (c == null) {
                        return 1;
                    }
/* 1983 */          if (c.moveToFirst()) {
/* 1984 */              int result = c.getInt(0);
/* 1985 */              c.close();
/* 1990 */              return result;
                    }
/* 1988 */          c.close();
                    return 1;
                }

/* 1995 */      public synchronized long mergeMessages(long sessionOwnerId, VineConversation conversation, int networkType, int nextPage, int prevPage, long anchor) throws IOException {
                    long conversationRowId;
                    long userRowId;
                    try {
                        LinkedHashMap<Long, VinePrivateMessage> newMessages = new LinkedHashMap<>();
/* 1997 */              LinkedHashMap<Long, VinePrivateMessage> updatedMessages = new LinkedHashMap<>();
/* 1998 */              HashMap<Long, Long> userIdToUserRowMap = new HashMap<>();
/* 2000 */              long conversationId = conversation.conversationId;
/* 2001 */              conversationRowId = mergeConversationWithRemoteId(conversationId, Integer.valueOf(networkType), null, conversation.unreadMessageCount, 0L);
/* 2003 */              long oldestMessageId = Long.MAX_VALUE;
/* 2005 */              if (conversation.messages != null) {
                            for (VinePrivateMessage message : conversation.messages) {
                                if (message.messageId < oldestMessageId) {
/* 2008 */                          oldestMessageId = message.messageId;
                                }
/* 2013 */                      if (!userIdToUserRowMap.containsKey(Long.valueOf(message.userId))) {
/* 2014 */                          userRowId = getUserRowIdForUserRemoteId(message.userId);
/* 2015 */                          userIdToUserRowMap.put(Long.valueOf(message.userId), Long.valueOf(userRowId));
                                } else {
/* 2017 */                          userRowId = userIdToUserRowMap.get(Long.valueOf(message.userId)).longValue();
                                }
/* 2020 */                      message.userId = userRowId;
/* 2021 */                      newMessages.put(Long.valueOf(message.messageId), message);
                            }
                        }
/* 2025 */              if (LOGGABLE) {
/* 2026 */                  Log.d(TAG, "mergeMessages, count=" + newMessages.size());
                        }
/* 2029 */              SQLiteDatabase db = getWritableDatabase();
/* 2031 */              boolean contiguous = true;
/* 2033 */              String[] selectionArgs = {String.valueOf(conversationRowId)};
/* 2034 */              Cursor c = db.query("messages", VineDatabaseSQL.MessagesQuery.PROJECTION, "conversation_row_id =? ", selectionArgs, null, null, Vine.Messages.SORT_ORDER_REVERSE, "1");
/* 2037 */              if (c != null) {
/* 2038 */                  if (c.moveToFirst()) {
/* 2039 */                      contiguous = oldestMessageId <= c.getLong(2);
                            }
/* 2041 */                  c.close();
                        }
/* 2044 */              Cursor cursor = this.mContentResolver.query(Vine.Messages.CONTENT_URI, VineDatabaseSQL.MessagesQuery.PROJECTION, null, null, null);
/* 2046 */              if (cursor != null) {
/* 2047 */                  while (cursor.moveToNext()) {
/* 2048 */                      VinePrivateMessage message2 = newMessages.remove(Long.valueOf(cursor.getLong(2)));
/* 2050 */                      if (message2 != null) {
/* 2051 */                          updatedMessages.put(Long.valueOf(message2.messageId), message2);
                                }
                            }
/* 2054 */                  cursor.close();
                        }
/* 2057 */              int inserted = 0;
/* 2058 */              int updated = 0;
/* 2060 */              ArrayList<VinePost> posts = new ArrayList<>();
/* 2062 */              if (!newMessages.isEmpty()) {
/* 2063 */                  if (LOGGABLE) {
/* 2064 */                      Log.d(TAG, "Inserting new messages, count=" + newMessages.size());
                            }
/* 2067 */                  db.beginTransaction();
                            try {
/* 2069 */                      ContentValues values = new ContentValues();
                                for (VinePrivateMessage message3 : newMessages.values()) {
/* 2071 */                          values.clear();
/* 2073 */                          fillMessageValues(values, conversationRowId, message3);
/* 2074 */                          values.put(Vine.MessagesColumns.DELETED, (Integer) 0);
/* 2075 */                          long insertedRow = db.insert("messages", null, values);
/* 2076 */                          inserted += insertedRow > 0 ? 1 : 0;
                                    if (message3.messageId == conversation.lastMessage) {
/* 2078 */                              updateConversationWithLastMessage(conversationRowId, insertedRow, message3.created);
                                    }
/* 2081 */                          if (message3.post != null) {
/* 2082 */                              posts.add(message3.post);
                                    }
                                }
/* 2087 */                      mergePosts(posts, 12, String.valueOf(sessionOwnerId), 0, 0, 0, 0L, true);
/* 2090 */                      db.setTransactionSuccessful();
/* 2092 */                      safeFinishTransaction(db);
/* 2094 */                      setConversationHidden(conversationRowId, false);
                            } catch (Throwable th) {
                                safeFinishTransaction(db);
                                throw th;
                            }
                        }
/* 2097 */              if (!updatedMessages.isEmpty()) {
/* 2098 */                  if (LOGGABLE) {
/* 2099 */                      Log.d(TAG, "Updating existing messages, count=" + updatedMessages.size());
                            }
/* 2102 */                  db.beginTransaction();
                            try {
/* 2104 */                      ContentValues values2 = new ContentValues();
                                for (VinePrivateMessage message4 : updatedMessages.values()) {
/* 2106 */                          values2.clear();
/* 2107 */                          fillMessageValues(values2, conversationRowId, message4);
/* 2108 */                          updated += db.update("messages", values2, "message_id=?", new String[]{String.valueOf(message4.messageId)}) > 0 ? 1 : 0;
/* 2111 */                          if (message4.post != null) {
/* 2112 */                              posts.add(message4.post);
                                    }
                                }
/* 2116 */                      mergePosts(posts, 12, String.valueOf(sessionOwnerId), 0, 0, 0, 0L, true);
/* 2119 */                      db.setTransactionSuccessful();
/* 2121 */                      safeFinishTransaction(db);
                            } catch (Throwable th2) {
                                safeFinishTransaction(db);
                                throw th2;
                            }
                        }
/* 2124 */              int deleted = 0;
/* 2126 */              if (!contiguous) {
/* 2129 */                  SLog.m447d("Deleting messages for conversation row id {}, non contiguous", Long.valueOf(conversationRowId));
/* 2130 */                  deleted = db.delete("messages", "conversation_row_id =? AND message_id <? AND message_id > ?", new String[]{String.valueOf(conversationRowId), String.valueOf(oldestMessageId), "0"});
                        }
/* 2136 */              if (nextPage > 0 || prevPage > 0 || anchor > 0) {
/* 2137 */                  savePageCursor(7, 0, String.valueOf(conversationRowId), nextPage, prevPage, anchor, false);
                        }
/* 2141 */              if (inserted > 0 || updated > 0 || deleted > 0) {
/* 2142 */                  this.mContentResolver.notifyChange(Vine.Messages.CONTENT_URI, null);
/* 2143 */                  this.mContentResolver.notifyChange(Vine.ConversationMessageUsersView.CONTENT_URI, null);
/* 2144 */                  this.mContentResolver.notifyChange(Vine.InboxView.CONTENT_URI, null);
                        }
                    } catch (Throwable th3) {
                        throw th3;
                    }
                    return conversationRowId;
                }

/* 2150 */      public synchronized void updateConversationWithLastMessage(long conversationRowId, long messageRowId, long created) {
                    try {
                        SQLiteDatabase db = getWritableDatabase();
                        try {
/* 2154 */                  db.beginTransaction();
/* 2155 */                  ContentValues values = new ContentValues();
/* 2156 */                  values.put(Vine.ConversationsColumns.LAST_MESSAGE_ROW_ID, Long.valueOf(messageRowId));
/* 2157 */                  values.put(Vine.ConversationsColumns.LAST_MESSAGE_TIMESTAMP, Long.valueOf(created));
/* 2158 */                  int updated = db.update("conversations", values, "_id=? ", new String[]{String.valueOf(conversationRowId)});
/* 2160 */                  db.setTransactionSuccessful();
/* 2162 */                  safeFinishTransaction(db);
/* 2164 */                  if (updated > 0) {
/* 2165 */                      this.mContentResolver.notifyChange(Vine.InboxView.CONTENT_URI, null);
/* 2166 */                      this.mContentResolver.notifyChange(Vine.Conversations.CONTENT_URI, null);
                            }
                        } catch (Throwable th) {
                            safeFinishTransaction(db);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }

/* 2171 */      public synchronized long mergeConversationWithLocalId(long conversationRowId, long conversationId, int networkType) {
                    try {
                        SQLiteDatabase db = getWritableDatabase();
/* 2174 */              ContentValues values = new ContentValues();
/* 2175 */              fillConversationValues(values, Long.valueOf(conversationId), Integer.valueOf(networkType), null, 0L, 0L);
                        try {
/* 2177 */                  db.beginTransaction();
/* 2178 */                  int updated = db.update("conversations", values, VineDatabaseSQL.SELECTION_ROW_ID, new String[]{String.valueOf(conversationRowId)});
/* 2180 */                  if (updated <= 0) {
/* 2181 */                      conversationRowId = db.insert("conversations", null, values);
                            }
/* 2183 */                  db.setTransactionSuccessful();
/* 2185 */                  safeFinishTransaction(db);
                        } catch (Throwable th) {
                            safeFinishTransaction(db);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                    return conversationRowId;
                }

/* 2192 */      public synchronized long mergeConversationWithRemoteId(long conversationId, Integer networkType, Boolean hidden, long unreadMessageCount, long lastMessage) {
                    long id;
                    try {
                        SQLiteDatabase db = getWritableDatabase();
/* 2195 */              String[] selArgs = {String.valueOf(conversationId)};
                        try {
/* 2198 */                  db.beginTransaction();
/* 2199 */                  Cursor cursor = db.query("conversations", new String[]{"_id"}, "conversation_id=?", selArgs, null, null, "1");
/* 2201 */                  id = -1;
/* 2202 */                  if (cursor != null) {
                                try {
/* 2204 */                          if (cursor.moveToFirst()) {
/* 2205 */                              ContentValues values = new ContentValues();
/* 2206 */                              fillConversationValues(values, Long.valueOf(conversationId), networkType, hidden, unreadMessageCount, lastMessage);
/* 2208 */                              db.update("conversations", values, "conversation_id=?", new String[]{String.valueOf(conversationId)});
/* 2210 */                              id = cursor.getLong(0);
                                    }
/* 2213 */                          cursor.close();
                                } catch (Throwable th) {
                                    cursor.close();
                                    throw th;
                                }
                            }
                            if (id == -1) {
/* 2217 */                      ContentValues values2 = new ContentValues();
/* 2218 */                      fillConversationValues(values2, Long.valueOf(conversationId), networkType, hidden, unreadMessageCount, lastMessage);
/* 2220 */                      id = db.insert("conversations", null, values2);
                                if (id == -1) {
/* 2222 */                          throw new RuntimeException("Failed to insert conversation.");
                                }
                            }
/* 2225 */                  db.setTransactionSuccessful();
/* 2228 */                  safeFinishTransaction(db);
                        } catch (Throwable th2) {
                            safeFinishTransaction(db);
                            throw th2;
                        }
                    } catch (Throwable th3) {
                        throw th3;
                    }
                    return id;
                }

/* 2232 */      public synchronized long mergeMessageWithMessageRow(long messageRowId, long conversationRowId, VinePrivateMessage message) {
                    long inserted;
                    try {
                        SQLiteDatabase db = getWritableDatabase();
/* 2235 */              inserted = -1;
/* 2238 */              ContentValues values = new ContentValues();
/* 2239 */              fillMessageValues(values, conversationRowId, message);
                        try {
/* 2242 */                  db.beginTransaction();
/* 2243 */                  long updated = db.update("messages", values, VineDatabaseSQL.SELECTION_ROW_ID, new String[]{String.valueOf(messageRowId)});
/* 2245 */                  db.setTransactionSuccessful();
/* 2247 */                  safeFinishTransaction(db);
                            if (updated <= 0) {
/* 2251 */                      values.put(Vine.MessagesColumns.DELETED, "0");
/* 2252 */                      inserted = db.insert("messages", null, values);
                            }
                            if (inserted > 0 || updated > 0) {
/* 2256 */                      this.mContentResolver.notifyChange(Vine.ConversationMessageUsersView.CONTENT_URI, null);
/* 2257 */                      this.mContentResolver.notifyChange(Vine.InboxView.CONTENT_URI, null);
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

/* 2266 */      public synchronized long mergeMessage(long conversationRowId, VinePrivateMessage message) {
                    long inserted;
                    try {
                        SQLiteDatabase db = getWritableDatabase();
/* 2268 */              inserted = -1;
/* 2269 */              long updated = -1;
/* 2271 */              ContentValues values = new ContentValues();
/* 2272 */              fillMessageValues(values, conversationRowId, message);
                        if (-1 == -1 && message.messageId > 0) {
/* 2277 */                  String[] selArgs = {String.valueOf(message.messageId)};
/* 2278 */                  Cursor cursor = this.mContentResolver.query(Vine.Messages.CONTENT_URI, VineDatabaseSQL.MessagesQuery.PROJECTION, "message_id=?", selArgs, null);
/* 2280 */                  if (cursor != null) {
/* 2281 */                      if (cursor.moveToFirst()) {
/* 2282 */                          long local = cursor.getLong(13);
                                    if (local > message.localStartTime) {
/* 2284 */                              values.put(Vine.MessagesColumns.LOCAL_START_TIME, Long.valueOf(local));
                                    }
                                    try {
/* 2287 */                              db.beginTransaction();
/* 2288 */                              updated = db.update("messages", values, "message_id=?", new String[]{String.valueOf(message.messageId)});
/* 2290 */                              db.setTransactionSuccessful();
/* 2292 */                              safeFinishTransaction(db);
                                    } catch (Throwable th) {
                                        safeFinishTransaction(db);
                                        throw th;
                                    }
                                }
/* 2295 */                      cursor.close();
                            }
                        }
                        if (-1 == -1 && updated <= 0) {
/* 2301 */                  values.put(Vine.MessagesColumns.DELETED, "0");
/* 2302 */                  inserted = db.insert("messages", null, values);
                        }
                        if (inserted > 0 || updated > 0) {
/* 2306 */                  this.mContentResolver.notifyChange(Vine.ConversationMessageUsersView.CONTENT_URI, null);
/* 2307 */                  this.mContentResolver.notifyChange(Vine.InboxView.CONTENT_URI, null);
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                    return inserted;
                }

/* 2312 */      public synchronized void removeUsersByGroup(int type) {
                    try {
                        SQLiteDatabase db = getWritableDatabase();
/* 2314 */              int deleted = db.delete("user_groups", VineDatabaseSQL.SELECTION_USER_GROUP_TYPE, new String[]{String.valueOf(type)});
/* 2317 */              if (LOGGABLE) {
/* 2318 */                  Log.d(TAG, "Deleted " + deleted + " users of type " + type);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 2325 */      public synchronized void removePostsByReposterId(long reposterId) {
                    try {
                        SQLiteDatabase db = getWritableDatabase();
/* 2328 */              String[] selArgs = {String.valueOf(reposterId), String.valueOf(1)};
/* 2330 */              int deleted = db.delete(Vine.PostGroups.TABLE_NAME, "reposter_id=? AND post_type =?", selArgs);
/* 2332 */              if (LOGGABLE) {
/* 2333 */                  Log.d(TAG, "Deleted " + deleted + " posts with reposterId=" + reposterId);
                        }
/* 2335 */              if (deleted > 0) {
/* 2336 */                  this.mContentResolver.notifyChange(Vine.PostGroupsView.CONTENT_URI, null);
/* 2337 */                  this.mContentResolver.notifyChange(Vine.PostCommentsLikesView.CONTENT_URI, null);
/* 2338 */                  this.mContentResolver.notifyChange(Vine.PostCommentsLikesView.CONTENT_URI_ALL_TIMELINE, null);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 2342 */      public synchronized void deleteConversation(long conversationId, long conversationRowId) {
                    try {
                        SQLiteDatabase db = getWritableDatabase();
/* 2344 */              int deleted = 0;
                        try {
/* 2346 */                  db.beginTransaction();
                            if (conversationId > 0) {
/* 2348 */                      deleted = db.delete("conversations", "conversation_id=?", new String[]{String.valueOf(conversationId)});
                            }
                            if (conversationRowId > 0) {
/* 2361 */                      deleted = deleted + db.delete("conversations", VineDatabaseSQL.SELECTION_ROW_ID, new String[]{String.valueOf(conversationRowId)}) + db.delete("messages", "conversation_row_id=?", new String[]{String.valueOf(conversationRowId)}) + db.delete(Vine.ConversationRecipients.TABLE_NAME, "conversation_row_id=?", new String[]{String.valueOf(conversationRowId)});
                            }
/* 2366 */                  db.setTransactionSuccessful();
/* 2368 */                  safeFinishTransaction(db);
/* 2371 */                  if (LOGGABLE) {
/* 2372 */                      Log.d(TAG, "Deleted conversation " + conversationId);
                            }
/* 2375 */                  if (deleted > 0) {
/* 2376 */                      notifyVMUris();
                            }
                        } catch (Throwable th) {
                            safeFinishTransaction(db);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }

/* 2380 */      private void notifyVMUris() {
/* 2381 */          this.mContentResolver.notifyChange(Vine.InboxView.CONTENT_URI, null);
/* 2382 */          this.mContentResolver.notifyChange(Vine.ConversationMessageUsersView.CONTENT_URI, null);
/* 2383 */          this.mContentResolver.notifyChange(Vine.Messages.CONTENT_URI, null);
/* 2384 */          this.mContentResolver.notifyChange(Vine.Conversations.CONTENT_URI, null);
/* 2385 */          this.mContentResolver.notifyChange(Vine.ConversationRecipients.CONTENT_URI, null);
                }

/* 2388 */      public synchronized void markLastUser(int type, String tag) {
                    try {
                        markLastUser(type, tag, "order_id ASC");
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 2399 */      public synchronized void markLastUser(int type, String tag, String isLastOrdering) {
                    try {
                        long rowId = getLastUserRowId(type, tag, isLastOrdering);
                        if (rowId != 0) {
/* 2405 */                  if (LOGGABLE) {
/* 2406 */                      Log.d(TAG, "Tagging the oldest user of type: " + type + " row id: " + rowId);
                            }
/* 2409 */                  SQLiteDatabase db = getWritableDatabase();
/* 2412 */                  ContentValues values = new ContentValues();
/* 2413 */                  values.put("is_last", (Integer) 0);
                            try {
/* 2416 */                      db.beginTransaction();
/* 2418 */                      db.update("user_groups", values, "is_last=1", null);
/* 2420 */                      values.put("is_last", (Integer) 1);
/* 2421 */                      db.update("user_groups", values, VineDatabaseSQL.SELECTION_ROW_ID, new String[]{String.valueOf(rowId)});
/* 2423 */                      db.setTransactionSuccessful();
/* 2425 */                      safeFinishTransaction(db);
/* 2427 */                      this.mContentResolver.notifyChange(Vine.UserGroupsView.CONTENT_URI, null);
                            } catch (Throwable th) {
                                safeFinishTransaction(db);
                                throw th;
                            }
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }

/* 2431 */      public synchronized void markLastPost(int type, String tag) {
                    try {
                        long rowId = getLastPostRowId(type, tag);
                        if (rowId != 0) {
/* 2437 */                  SQLiteDatabase db = getWritableDatabase();
/* 2439 */                  if (LOGGABLE) {
/* 2440 */                      Log.d(TAG, "Tagging the oldest post of type: " + type + " and tag: " + tag + " row id: " + rowId);
                            }
/* 2443 */                  ContentValues values = new ContentValues();
/* 2444 */                  values.put("is_last", (Integer) 0);
                            try {
/* 2447 */                      db.beginTransaction();
/* 2448 */                      db.update(Vine.PostGroups.TABLE_NAME, values, "is_last=1", null);
/* 2450 */                      values.put("is_last", (Integer) 1);
/* 2451 */                      db.update(Vine.PostGroups.TABLE_NAME, values, VineDatabaseSQL.SELECTION_ROW_ID, new String[]{String.valueOf(rowId)});
/* 2453 */                      db.setTransactionSuccessful();
/* 2455 */                      safeFinishTransaction(db);
/* 2457 */                      notifyPostCommentsViewUris();
                            } catch (Throwable th) {
                                safeFinishTransaction(db);
                                throw th;
                            }
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }

/* 2461 */      public synchronized void markLastComment(long postId) {
                    try {
                        long rowId = getLastCommentRowId(postId);
                        if (rowId != 0) {
/* 2467 */                  SQLiteDatabase db = getWritableDatabase();
/* 2469 */                  ContentValues values = new ContentValues();
/* 2470 */                  values.put("is_last", (Integer) 0);
                            try {
/* 2473 */                      db.beginTransaction();
/* 2475 */                      db.update("comments", values, "is_last=1 AND post_id=?", new String[]{String.valueOf(postId)});
/* 2478 */                      values.put("is_last", (Integer) 1);
/* 2479 */                      db.update("comments", values, VineDatabaseSQL.SELECTION_ROW_ID, new String[]{String.valueOf(rowId)});
/* 2481 */                      db.setTransactionSuccessful();
/* 2483 */                      safeFinishTransaction(db);
                            } catch (Throwable th) {
                                safeFinishTransaction(db);
                                throw th;
                            }
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }

/* 2488 */      public synchronized void markLastMessage(long conversationRowId) {
                    try {
                        long rowId = getLastMessageRowId(conversationRowId);
                        if (rowId != 0) {
/* 2494 */                  SQLiteDatabase db = getWritableDatabase();
/* 2496 */                  ContentValues values = new ContentValues();
/* 2497 */                  values.put("is_last", (Integer) 0);
                            try {
/* 2500 */                      db.beginTransaction();
/* 2501 */                      db.update("messages", values, "is_last=1 AND conversation_row_id=?", new String[]{String.valueOf(conversationRowId)});
/* 2504 */                      values.put("is_last", (Integer) 1);
/* 2505 */                      db.update("messages", values, VineDatabaseSQL.SELECTION_ROW_ID, new String[]{String.valueOf(rowId)});
/* 2507 */                      db.setTransactionSuccessful();
/* 2509 */                      safeFinishTransaction(db);
                            } catch (Throwable th) {
                                safeFinishTransaction(db);
                                throw th;
                            }
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }

/* 2517 */      public synchronized void markLastTag() {
                    try {
                        long rowId = getLastTagRowId();
                        if (rowId != 0) {
/* 2523 */                  if (LOGGABLE) {
/* 2524 */                      Log.d(TAG, "Tagging the oldest tag with row id: " + rowId);
                            }
/* 2527 */                  SQLiteDatabase db = getWritableDatabase();
/* 2530 */                  ContentValues values = new ContentValues();
/* 2531 */                  values.put("is_last", (Integer) 0);
                            try {
/* 2534 */                      db.beginTransaction();
/* 2535 */                      db.update(Vine.TagSearchResults.TABLE_NAME, values, "is_last=1", null);
/* 2537 */                      values.put("is_last", (Integer) 1);
/* 2538 */                      db.update(Vine.TagSearchResults.TABLE_NAME, values, VineDatabaseSQL.SELECTION_ROW_ID, new String[]{String.valueOf(rowId)});
/* 2540 */                      db.setTransactionSuccessful();
/* 2541 */                      this.mContentResolver.notifyChange(Vine.TagSearchResults.CONTENT_URI, null);
/* 2543 */                      safeFinishTransaction(db);
                            } catch (Throwable th) {
                                safeFinishTransaction(db);
                                throw th;
                            }
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }

/* 2550 */      public synchronized void markLastChannel() {
                    try {
                        long rowId = getLastChannelRowId();
                        if (rowId != 0) {
/* 2556 */                  if (LOGGABLE) {
/* 2557 */                      Log.d(TAG, "Tagging the oldest channel with row id: " + rowId);
                            }
/* 2560 */                  SQLiteDatabase db = getWritableDatabase();
/* 2563 */                  ContentValues values = new ContentValues();
/* 2564 */                  values.put("is_last", (Integer) 0);
                            try {
/* 2566 */                      db.beginTransaction();
/* 2567 */                      db.update("channels", values, "is_last=1", null);
/* 2569 */                      values.put("is_last", (Integer) 1);
/* 2570 */                      db.update("channels", values, VineDatabaseSQL.SELECTION_ROW_ID, new String[]{String.valueOf(rowId)});
/* 2573 */                      safeFinishTransaction(db);
/* 2575 */                      this.mContentResolver.notifyChange(Vine.Channels.CONTENT_URI, null);
                            } catch (Throwable th) {
                                safeFinishTransaction(db);
                                throw th;
                            }
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }

/* 2581 */      public synchronized void markChannelLastUsedTimestamp(long channelId) {
                    try {
                        SQLiteDatabase db = getWritableDatabase();
/* 2583 */              ContentValues values = new ContentValues();
/* 2584 */              values.put("last_used_timestamp", Long.valueOf(System.currentTimeMillis()));
/* 2585 */              db.update("channels", values, "channel_id=?", new String[]{String.valueOf(channelId)});
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 2589 */      public synchronized void markLastConversation(int networkType) {
                    try {
                        long rowId = getLastConversationId(networkType);
                        if (rowId != 0) {
/* 2595 */                  SQLiteDatabase db = getWritableDatabase();
/* 2597 */                  ContentValues values = new ContentValues();
/* 2598 */                  values.put("is_last", (Integer) 0);
                            try {
/* 2601 */                      db.beginTransaction();
/* 2602 */                      db.update("conversations", values, "is_last=1 AND network_type=?", new String[]{String.valueOf(networkType)});
/* 2604 */                      values.put("is_last", (Integer) 1);
/* 2605 */                      db.update("conversations", values, VineDatabaseSQL.SELECTION_ROW_ID, new String[]{String.valueOf(rowId)});
/* 2607 */                      db.setTransactionSuccessful();
/* 2609 */                      safeFinishTransaction(db);
                            } catch (Throwable th) {
                                safeFinishTransaction(db);
                                throw th;
                            }
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }

                /* JADX WARN: Code duplicated, block: B:11:0x002c A[Catch: all -> 0x0042, TRY_ENTER, TryCatch #0 {all -> 0x0042, blocks: (B:4:0x0003, B:6:0x0017, B:11:0x002c, B:13:0x0030, B:14:0x0038), top: B:19:0x0003 }] */
                /* JADX WARN: Code duplicated, block: B:13:0x0030 A[Catch: all -> 0x0042, TryCatch #0 {all -> 0x0042, blocks: (B:4:0x0003, B:6:0x0017, B:11:0x002c, B:13:0x0030, B:14:0x0038), top: B:19:0x0003 }] */
/* 2620 */      public synchronized int saveUserValue(String propName, ContentValues values) {
                    int i = 0;
                    synchronized (this) {
                        try {
                            SQLiteDatabase db = getWritableDatabase();
/* 2622 */                  if (db.update("settings", values, SETTINGS_NAME_SELECTION, new String[]{propName}) == 0) {
/* 2625 */                      values.put("name", propName);
                                if (db.insert("settings", "name", values) != 0) {
/* 2630 */                          if (this.mContentResolver == null) {
/* 2631 */                              this.mContentResolver = this.mAppContext.getContentResolver();
                                    }
/* 2633 */                          this.mContentResolver.notifyChange(Vine.Settings.CONTENT_URI, null);
                                    i = 1;
                                }
                            } else {
/* 2630 */                      if (this.mContentResolver == null) {
/* 2631 */                          this.mContentResolver = this.mAppContext.getContentResolver();
                                }
/* 2633 */                      this.mContentResolver.notifyChange(Vine.Settings.CONTENT_URI, null);
                                i = 1;
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return i;
                }

/* 2638 */      public synchronized int savePageCursor(int kind, int type, String tag, int nextPage, int prevPage, long anchor, boolean reversed) {
                    int num;
                    try {
                        SQLiteDatabase db = getWritableDatabase();
/* 2641 */              if (tag == null) {
/* 2642 */                  tag = "";
                        }
/* 2646 */              String[] selectionArgs = {String.valueOf(kind), String.valueOf(type), tag};
/* 2649 */              Cursor c = db.query(Vine.PageCursors.TABLE_NAME, VineDatabaseSQL.PageCursorsQuery.PROJECTION, VineDatabaseSQL.SELECTION_PAGE_CURSOR_KIND_TYPE_TAG, selectionArgs, null, null, null);
/* 2652 */              ContentValues values = new ContentValues();
/* 2653 */              values.put(Vine.PageCursors.KIND, Integer.valueOf(kind));
/* 2654 */              values.put("type", Integer.valueOf(type));
/* 2655 */              values.put("tag", tag);
/* 2656 */              values.put("next_page", Integer.valueOf(nextPage));
/* 2657 */              values.put("previous_page", Integer.valueOf(prevPage));
/* 2658 */              values.put("anchor", Long.valueOf(anchor));
/* 2659 */              values.put(Vine.PageCursors.REVERSED, Boolean.valueOf(reversed));
/* 2662 */              if (c == null) {
/* 2661 */                  num = 0 + (db.insert(Vine.PageCursors.TABLE_NAME, "_id", values) > 0 ? 1 : 0);
                        } else {
                            try {
/* 2664 */                      if (c.getCount() == 1) {
/* 2661 */                          num = 0 + (db.update(Vine.PageCursors.TABLE_NAME, values, VineDatabaseSQL.SELECTION_PAGE_CURSOR_KIND_TYPE_TAG, selectionArgs) > 0 ? 1 : 0);
                                } else {
/* 2661 */                          num = 0 + (db.insert(Vine.PageCursors.TABLE_NAME, "_id", values) > 0 ? 1 : 0);
                                }
/* 2672 */                      c.close();
                            } catch (Throwable th) {
                                c.close();
                                throw th;
                            }
                        }
/* 2677 */              if (LOGGABLE && num > 0) {
/* 2679 */                  Log.d(TAG, "Saved pagecursor of kind: " + kind + " type: " + type + " tag: " + tag + " next:" + nextPage + " prev: " + prevPage + " anchor: " + anchor + " reverse: " + reversed);
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                    return num;
                }

/* 2697 */      public synchronized int getNextPageCursor(int kind, int type, String tag, boolean reversed) {
                    String column;
                    int result;
                    try {
                        SQLiteDatabase db = getReadableDatabase();
/* 2700 */              if (reversed) {
/* 2701 */                  column = "previous_page";
                        } else {
/* 2703 */                  column = "next_page";
                        }
/* 2706 */              if (tag == null) {
/* 2707 */                  tag = "";
                        }
/* 2710 */              Cursor c = db.query(Vine.PageCursors.TABLE_NAME, new String[]{column}, VineDatabaseSQL.SELECTION_PAGE_CURSOR_KIND_TYPE_TAG, new String[]{String.valueOf(kind), String.valueOf(type), String.valueOf(tag)}, null, null, null);
/* 2714 */              result = -1;
/* 2715 */              if (c != null) {
                            try {
/* 2717 */                      if (c.moveToFirst()) {
/* 2718 */                          result = c.getInt(0);
                                }
/* 2721 */                      c.close();
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

/* 2737 */      public synchronized int getPreviousPageCursor(int kind, int type, String tag, boolean reversed) {
                    String column;
                    int result;
                    try {
                        SQLiteDatabase db = getReadableDatabase();
/* 2740 */              if (reversed) {
/* 2741 */                  column = "next_page";
                        } else {
/* 2743 */                  column = "previous_page";
                        }
/* 2746 */              if (tag == null) {
/* 2747 */                  tag = "";
                        }
/* 2750 */              Cursor c = db.query(Vine.PageCursors.TABLE_NAME, new String[]{column}, VineDatabaseSQL.SELECTION_PAGE_CURSOR_KIND_TYPE_TAG, new String[]{String.valueOf(kind), String.valueOf(type), String.valueOf(tag)}, null, null, null);
/* 2754 */              result = -1;
/* 2755 */              if (c != null) {
                            try {
/* 2757 */                      if (c.moveToFirst()) {
/* 2758 */                          int index = c.getColumnIndex(column);
/* 2759 */                          result = c.getInt(index);
                                }
/* 2762 */                      c.close();
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

/* 2776 */      public synchronized long getPageAnchor(int kind, int type, String tag) {
                    long result;
                    try {
                        SQLiteDatabase db = getReadableDatabase();
/* 2778 */              Cursor c = db.query(Vine.PageCursors.TABLE_NAME, new String[]{"anchor"}, VineDatabaseSQL.SELECTION_PAGE_CURSOR_KIND_TYPE_TAG, new String[]{String.valueOf(kind), String.valueOf(type), String.valueOf(tag)}, null, null, null);
/* 2783 */              result = -1;
/* 2784 */              if (c != null) {
                            try {
/* 2786 */                      if (c.getCount() > 0 && c.moveToFirst()) {
/* 2787 */                          result = c.getLong(0);
                                }
/* 2790 */                      c.close();
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

/* 2799 */      public synchronized void clearAllData() {
                    try {
                        SQLiteDatabase db = getWritableDatabase();
/* 2801 */              db.delete("settings", null, null);
/* 2802 */              db.delete("user_groups", null, null);
/* 2803 */              db.delete("posts", null, null);
/* 2804 */              db.delete("likes", null, null);
/* 2805 */              db.delete(Vine.PostGroups.TABLE_NAME, null, null);
/* 2806 */              db.delete("comments", null, null);
/* 2807 */              db.delete(Vine.PageCursors.TABLE_NAME, null, null);
/* 2808 */              db.delete(Vine.TagSearchResults.TABLE_NAME, null, null);
/* 2809 */              db.delete("channels", null, null);
/* 2810 */              db.delete("notifications", null, null);
/* 2811 */              db.delete(Vine.TagsRecentlyUsed.TABLE_NAME, null, null);
/* 2812 */              db.delete("messages", null, null);
/* 2813 */              db.delete(Vine.ConversationRecipients.TABLE_NAME, null, null);
/* 2814 */              db.delete("conversations", null, null);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 2820 */      public synchronized void clearCachedData() {
                    try {
                        SQLiteDatabase db = getWritableDatabase();
                        try {
/* 2823 */                  db.beginTransaction();
/* 2824 */                  db.delete("users", null, null);
/* 2825 */                  db.delete("user_groups", null, null);
/* 2826 */                  db.delete("posts", null, null);
/* 2827 */                  db.delete("likes", null, null);
/* 2828 */                  db.delete(Vine.PostGroups.TABLE_NAME, null, null);
/* 2829 */                  db.delete("comments", null, null);
/* 2830 */                  db.delete(Vine.PageCursors.TABLE_NAME, null, null);
/* 2831 */                  db.delete(Vine.TagSearchResults.TABLE_NAME, null, null);
/* 2832 */                  db.delete("channels", null, null);
/* 2833 */                  db.delete("notifications", null, null);
/* 2834 */                  db.delete(Vine.TagsRecentlyUsed.TABLE_NAME, null, null);
/* 2835 */                  db.delete("messages", null, null);
/* 2836 */                  db.delete(Vine.ConversationRecipients.TABLE_NAME, null, null);
/* 2837 */                  db.delete("conversations", null, null);
/* 2838 */                  db.setTransactionSuccessful();
/* 2840 */                  safeFinishTransaction(db);
                        } catch (Throwable th) {
                            safeFinishTransaction(db);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }

/* 2844 */      public synchronized void clearCachedData(boolean notify) {
                    try {
                        clearCachedData();
/* 2846 */              if (notify) {
/* 2847 */                  notifyPostCommentsViewUris();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 2851 */      private long getLastUserRowId(int type, String tag, String isLastOrdering) {
                    String selection;
                    String[] selectionArgs;
/* 2852 */          SQLiteDatabase db = getReadableDatabase();
/* 2855 */          if (tag != null) {
/* 2856 */              selection = VineDatabaseSQL.SELECTION_USER_GROUP_TYPE_AND_TAG;
/* 2857 */              selectionArgs = new String[]{String.valueOf(type), tag};
                    } else {
/* 2859 */              selection = VineDatabaseSQL.SELECTION_USER_GROUP_TYPE;
/* 2860 */              selectionArgs = new String[]{String.valueOf(type)};
                    }
/* 2863 */          Cursor cursor = db.query("user_groups_view", VineDatabaseSQL.UsersQuery.PROJECTION, selection, selectionArgs, null, null, isLastOrdering, "1");
/* 2865 */          if (cursor != null) {
                        try {
/* 2867 */                  if (cursor.moveToFirst()) {
/* 2868 */                      long j = cursor.getLong(0);
/* 2871 */                      cursor.close();
/* 2874 */                      return j;
                            }
/* 2871 */                  cursor.close();
                        } catch (Throwable th) {
                            cursor.close();
                            throw th;
                        }
                    }
/* 2874 */          return 0L;
                }

/* 2877 */      private long getLastPostRowId(int type, String tag) {
/* 2878 */          if (tag != null) {
/* 2879 */              SQLiteDatabase db = getReadableDatabase();
/* 2880 */              Cursor cursor = db.query(Vine.PostCommentsLikesView.TABLE_NAME, VineDatabaseSQL.PostCommentsLikesQuery.PROJECTION, VineDatabaseSQL.SELECTION_POST_GROUP_TYPE_AND_TAG, new String[]{String.valueOf(type), tag}, null, null, Vine.PostCommentsLikesView.SORT_ORDER_DEFINED_LAST, "1");
/* 2885 */              if (cursor != null) {
                            try {
/* 2887 */                      if (cursor.moveToFirst()) {
/* 2888 */                          long j = cursor.getLong(0);
/* 2891 */                          cursor.close();
/* 2897 */                          return j;
                                }
/* 2891 */                      cursor.close();
                            } catch (Throwable th) {
                                cursor.close();
                                throw th;
                            }
                        }
                    } else {
/* 2895 */              CrashUtil.logException(new VineLoggingException("Tag was null when trying to mark last row."));
                    }
/* 2897 */          return 0L;
                }

/* 2900 */      private long getLastCommentRowId(long postId) {
/* 2901 */          SQLiteDatabase db = getReadableDatabase();
/* 2902 */          Cursor cursor = db.query("comments", VineDatabaseSQL.CommentsQuery.PROJECTION, "post_id =? ", new String[]{String.valueOf(postId)}, null, null, Vine.Comments.SORT_ORDER_DEFAULT, "1");
/* 2905 */          if (cursor != null) {
                        try {
/* 2907 */                  if (cursor.moveToFirst()) {
/* 2908 */                      long j = cursor.getLong(0);
/* 2911 */                      cursor.close();
/* 2914 */                      return j;
                            }
/* 2911 */                  cursor.close();
                        } catch (Throwable th) {
                            cursor.close();
                            throw th;
                        }
                    }
/* 2914 */          return 0L;
                }

/* 2917 */      private long getLastMessageRowId(long conversationRowId) {
/* 2918 */          SQLiteDatabase db = getReadableDatabase();
/* 2919 */          Cursor cursor = db.query("messages", VineDatabaseSQL.MessagesQuery.PROJECTION, "conversation_row_id =? AND deleted != 1 ", new String[]{String.valueOf(conversationRowId)}, null, null, Vine.Messages.SORT_ORDER_DEFAULT, "1");
/* 2922 */          if (cursor != null) {
                        try {
/* 2924 */                  if (cursor.moveToFirst()) {
/* 2925 */                      long j = cursor.getLong(0);
/* 2928 */                      cursor.close();
/* 2931 */                      return j;
                            }
/* 2928 */                  cursor.close();
                        } catch (Throwable th) {
                            cursor.close();
                            throw th;
                        }
                    }
/* 2931 */          return 0L;
                }

/* 2934 */      private long getLastTagRowId() {
/* 2935 */          SQLiteDatabase db = getReadableDatabase();
/* 2936 */          Cursor cursor = db.query(Vine.TagSearchResults.TABLE_NAME, VineDatabaseSQL.TagsQuery.PROJECTION, null, null, null, null, "_id DESC", "1");
/* 2939 */          if (cursor != null) {
                        try {
/* 2941 */                  if (cursor.moveToFirst()) {
/* 2942 */                      long j = cursor.getLong(0);
/* 2945 */                      cursor.close();
/* 2948 */                      return j;
                            }
/* 2945 */                  cursor.close();
                        } catch (Throwable th) {
                            cursor.close();
                            throw th;
                        }
                    }
/* 2948 */          return 0L;
                }

/* 2951 */      private long getLastChannelRowId() {
/* 2952 */          SQLiteDatabase db = getReadableDatabase();
/* 2953 */          Cursor cursor = db.query("channels", VineDatabaseSQL.ChannelsQuery.PROJECTION, null, null, null, null, Vine.Channels.SORT_ORDER_IS_LAST, "1");
/* 2956 */          if (cursor != null) {
                        try {
/* 2958 */                  if (cursor.moveToFirst()) {
/* 2959 */                      long j = cursor.getLong(0);
/* 2962 */                      cursor.close();
/* 2965 */                      return j;
                            }
/* 2962 */                  cursor.close();
                        } catch (Throwable th) {
                            cursor.close();
                            throw th;
                        }
                    }
/* 2965 */          return 0L;
                }

/* 2968 */      private long getLastConversationId(int networkType) {
/* 2969 */          SQLiteDatabase db = getReadableDatabase();
/* 2971 */          Cursor cursor = db.query("conversations", new String[]{"_id"}, "last_message>0 AND network_type =? ", new String[]{String.valueOf(networkType)}, null, null, Vine.Conversations.SORT_ORDER_DEFAULT_IS_LAST, "1");
/* 2975 */          if (cursor != null) {
                        try {
/* 2977 */                  if (cursor.moveToFirst()) {
/* 2978 */                      long j = cursor.getLong(0);
/* 2981 */                      cursor.close();
/* 2984 */                      return j;
                            }
/* 2981 */                  cursor.close();
                        } catch (Throwable th) {
                            cursor.close();
                            throw th;
                        }
                    }
/* 2984 */          return 0L;
                }

/* 2987 */      public boolean shouldHideProfileRevines(long userId) {
/* 2988 */          SQLiteDatabase db = getReadableDatabase();
/* 2989 */          boolean ret = false;
/* 2991 */          String[] selArgs = {String.valueOf(userId)};
/* 2993 */          Cursor cursor = db.query("users", new String[]{"hide_profile_reposts"}, VineDatabaseSQL.SELECTION_USER_ID, selArgs, null, null, null);
/* 2997 */          if (cursor != null) {
                        try {
/* 2999 */                  if (cursor.moveToFirst()) {
/* 3000 */                      ret = cursor.getInt(0) > 0;
                            }
/* 3003 */                  cursor.close();
                        } catch (Throwable th) {
                            cursor.close();
                            throw th;
                        }
                    }
/* 3006 */          return ret;
                }

/* 3009 */      public boolean setShouldHideProfileRevines(long userId, boolean hide) {
/* 3010 */          SQLiteDatabase db = getWritableDatabase();
/* 3013 */          String[] selArgs = {String.valueOf(userId)};
/* 3014 */          ContentValues values = new ContentValues();
/* 3015 */          values.put("hide_profile_reposts", Integer.valueOf(hide ? 1 : 0));
/* 3018 */          db.beginTransaction();
                    try {
/* 3020 */              int updated = db.update("users", values, VineDatabaseSQL.SELECTION_USER_ID, selArgs);
/* 3021 */              db.setTransactionSuccessful();
/* 3023 */              safeFinishTransaction(db);
                        return updated > 0;
                    } catch (Throwable th) {
                        safeFinishTransaction(db);
                        throw th;
                    }
                }

/* 3029 */      private void fillUserContentValues(ContentValues values, VineUser user, long lastRefresh) {
/* 3030 */          values.put("user_id", Long.valueOf(user.userId));
/* 3031 */          values.put("avatar_url", user.avatarUrl);
/* 3032 */          values.put("username", user.username);
/* 3033 */          values.put("blocked", Integer.valueOf(user.blocked));
/* 3034 */          values.put(Vine.UserColumns.BLOCKING, Integer.valueOf(user.blocking));
/* 3035 */          values.put("description", user.description);
/* 3036 */          values.put("location", user.location);
/* 3037 */          values.put("explicit", Integer.valueOf(user.explicit));
/* 3038 */          values.put(Vine.UserColumns.EXTERNAL, Integer.valueOf(user.external ? 1 : 0));
/* 3039 */          values.put(Vine.UserColumns.FOLLOWER_COUNT, Integer.valueOf(user.followerCount));
/* 3040 */          values.put(Vine.UserColumns.FOLLOWING_COUNT, Integer.valueOf(user.followingCount));
/* 3041 */          if (!TextUtils.isEmpty(user.email)) {
/* 3042 */              values.put(Vine.UserColumns.EMAIL_ADDRESS, user.email);
                    }
/* 3044 */          if (!TextUtils.isEmpty(user.phoneNumber)) {
/* 3045 */              values.put("phone_number", user.phoneNumber);
                    }
/* 3047 */          if (user.following != -1) {
/* 3048 */              values.put("following_flag", Integer.valueOf(user.following));
                    }
/* 3050 */          values.put(Vine.UserColumns.LIKE_COUNT, Integer.valueOf(user.likeCount));
/* 3051 */          values.put(Vine.UserColumns.POST_COUNT, Integer.valueOf(user.postCount));
/* 3052 */          values.put(Vine.UserColumns.LOOP_COUNT, Long.valueOf(user.loopCount));
/* 3053 */          values.put("verified", Integer.valueOf(user.verified));
/* 3054 */          values.put(Vine.UserColumns.FOLLOW_STATUS, Integer.valueOf(user.followStatus));
/* 3055 */          values.put("last_refresh", Long.valueOf(lastRefresh));
/* 3056 */          if (user.profileBackground > -1) {
/* 3057 */              values.put("profile_background", Integer.valueOf(user.profileBackground));
                    }
/* 3059 */          values.put(Vine.UserColumns.ACCEPTS_OON_CONVERSATIONS, Integer.valueOf(user.acceptsOutOfNetworkConversations ? 1 : 0));
                }

/* 3063 */      private void fillPostValues(ContentValues values, VinePost post, long lastRefresh) throws IOException {
/* 3064 */          values.put("avatar_url", post.avatarUrl);
/* 3065 */          values.put("description", post.description);
/* 3066 */          values.put(Vine.PostColumns.FOURSQUARE_VENUE_ID, post.foursquareVenueId);
/* 3067 */          values.put(Vine.PostColumns.METADATA_FLAGS, Integer.valueOf(post.metadataFlags));
/* 3068 */          values.put("location", post.location);
/* 3069 */          values.put(Vine.PostColumns.POST_FLAGS, Integer.valueOf(post.postFlags));
/* 3070 */          values.put("post_id", Long.valueOf(post.postId));
/* 3071 */          values.put("my_repost_id", Long.valueOf(post.myRepostId));
/* 3072 */          values.put("share_url", post.shareUrl);
/* 3073 */          values.put("thumbnail_url", post.thumbnailUrl);
/* 3074 */          values.put("video_url", post.videoUrl);
/* 3075 */          values.put(Vine.PostColumns.VIDEO_LOW_URL, post.videoLowUrl);
/* 3076 */          values.put("timestamp", Long.valueOf(post.created));
/* 3077 */          values.put("username", post.username);
/* 3078 */          values.put("user_id", Long.valueOf(post.userId));
/* 3079 */          values.put(Vine.PostColumns.LIKES_COUNT, Integer.valueOf(post.likesCount));
/* 3080 */          values.put(Vine.PostColumns.REVINERS_COUNT, Integer.valueOf(post.revinersCount));
/* 3081 */          values.put(Vine.PostColumns.COMMENTS_COUNT, Integer.valueOf(post.commentsCount));
/* 3082 */          values.put(Vine.PostColumns.USER_BACKGROUND_COLOR, Integer.valueOf(post.userBackgroundColor));
/* 3083 */          values.put("tags", VinePost.getBytesFromTags(post));
/* 3084 */          values.put("loops", Long.valueOf(post.loops));
/* 3085 */          values.put(Vine.PostColumns.VELOCITY, Double.valueOf(post.velocity));
/* 3086 */          values.put(Vine.PostColumns.ON_FIRE, Boolean.valueOf(post.onFire));
/* 3087 */          if (post.venueData != null) {
/* 3088 */              values.put(Vine.PostColumns.VENUE_DATA, Util.toByteArray(post.venueData));
                    }
/* 3090 */          if (post.entities != null) {
/* 3091 */              values.put("entities", Util.toByteArray(post.entities));
                    }
/* 3093 */          values.put("last_refresh", Long.valueOf(lastRefresh));
                }

/* 3096 */      private void fillLikeValues(ContentValues values, VineLike like, long lastRefresh) {
/* 3097 */          values.put(Vine.LikesColumns.LIKE_ID, Long.valueOf(like.likeId));
/* 3098 */          values.put("user_id", Long.valueOf(like.userId));
/* 3099 */          values.put("post_id", Long.valueOf(like.postId));
/* 3100 */          values.put("avatar_url", like.avatarUrl);
/* 3101 */          values.put("timestamp", Long.valueOf(like.created));
/* 3102 */          values.put("location", like.location);
/* 3103 */          values.put("username", like.username);
/* 3104 */          values.put("verified", Integer.valueOf(like.verified));
/* 3105 */          values.put("last_refresh", Long.valueOf(lastRefresh));
                }

/* 3109 */      private void fillNotificationValues(ContentValues values, VineSingleNotification notification, String message) {
/* 3110 */          values.put("avatar_url", notification.avatarUrl);
/* 3111 */          values.put("notification_id", Long.valueOf(notification.notificationId));
/* 3112 */          values.put(Vine.NotificationsColumns.NOTIFICATION_TYPE, Integer.valueOf(notification.notificationTypeId));
/* 3113 */          values.put("conversation_row_id", Long.valueOf(notification.conversationRowId));
/* 3114 */          values.put("message", message);
                }

/* 3118 */      private void fillConversationValues(ContentValues values, Long conversationId, Integer networkType, Boolean hidden, long unreadMessageCount, long lastMessage) {
/* 3119 */          if (conversationId != null) {
/* 3120 */              values.put("conversation_id", conversationId);
                    }
/* 3122 */          if (networkType != null) {
/* 3123 */              values.put("network_type", networkType);
                    }
/* 3125 */          if (hidden != null) {
/* 3126 */              values.put(Vine.ConversationsColumns.IS_HIDDEN, Integer.valueOf(hidden.booleanValue() ? 1 : 0));
                    }
                    if (unreadMessageCount > -1) {
/* 3129 */              values.put(Vine.ConversationsColumns.UNREAD_MESSAGE_COUNT, Long.valueOf(unreadMessageCount));
                    }
                    if (lastMessage > 0) {
/* 3132 */              values.put(Vine.ConversationsColumns.LAST_MESSAGE_ROW_ID, Long.valueOf(lastMessage));
                    }
                }

/* 3136 */      private void fillMessageValues(ContentValues values, long conversationRowId, VinePrivateMessage message) {
/* 3137 */          values.put("conversation_row_id", Long.valueOf(conversationRowId));
/* 3138 */          values.put("message_id", Long.valueOf(message.messageId));
/* 3139 */          values.put("user_row_id", Long.valueOf(message.userId));
/* 3140 */          values.put("message", message.message);
/* 3141 */          values.put("timestamp", Long.valueOf(message.created));
/* 3142 */          if (!TextUtils.isEmpty(message.videoUrl)) {
/* 3143 */              values.put("video_url", message.videoUrl);
                    }
/* 3145 */          if (!TextUtils.isEmpty(message.thumbnailUrl)) {
/* 3146 */              values.put("thumbnail_url", message.thumbnailUrl);
                    }
/* 3148 */          values.put("is_last", Boolean.valueOf(message.isLast));
/* 3149 */          values.put(Vine.MessagesColumns.VANISHED, Boolean.valueOf(message.vanished));
/* 3150 */          values.put("max_loops", Integer.valueOf(message.maxLoops));
/* 3151 */          values.put(Vine.MessagesColumns.VANISHED_DATE, Long.valueOf(message.vanishedDate));
/* 3152 */          values.put(Vine.MessagesColumns.EPHEMERAL, Boolean.valueOf(message.ephemeral));
/* 3153 */          values.put(Vine.MessagesColumns.LOCAL_START_TIME, Long.valueOf(message.localStartTime));
/* 3154 */          values.put("post_id", Long.valueOf(message.postId));
/* 3155 */          values.put("error_code", Integer.valueOf(message.errorCode));
/* 3156 */          values.put(Vine.MessagesColumns.ERROR_REASON, message.errorReason);
/* 3157 */          if (!TextUtils.isEmpty(message.uploadPath)) {
/* 3158 */              values.put("upload_path", message.uploadPath);
                    }
                }

/* 3162 */      private void fillTagValues(ContentValues values, VineTag tag) {
/* 3163 */          values.put(Vine.TagsColumns.TAG_ID, Long.valueOf(tag.tagId));
/* 3164 */          values.put("tag_name", tag.tagName);
/* 3165 */          values.put("last_used_timestamp", Long.valueOf(System.currentTimeMillis()));
                }

/* 3168 */      private void fillCommentValues(ContentValues values, VineComment comment, long lastRefresh) {
/* 3169 */          values.put("comment_id", Long.valueOf(comment.commentId));
/* 3170 */          values.put("user_id", Long.valueOf(comment.userId));
/* 3171 */          values.put("post_id", Long.valueOf(comment.postId));
/* 3172 */          values.put("avatar_url", comment.avatarUrl);
/* 3173 */          values.put("comment", comment.comment);
/* 3174 */          values.put("timestamp", Long.valueOf(comment.timestamp));
/* 3175 */          values.put("location", comment.location);
/* 3176 */          values.put("username", comment.username);
/* 3177 */          values.put("verified", Boolean.valueOf(comment.verified));
/* 3178 */          if (comment.entities != null) {
/* 3179 */              values.put("entities", Util.toByteArray(comment.entities));
                    }
/* 3181 */          values.put("last_refresh", Long.valueOf(lastRefresh));
/* 3182 */          values.put("is_last", Boolean.valueOf(comment.isLast));
                }

/* 3185 */      private void fillChannelValues(ContentValues values, VineChannel channel) {
/* 3186 */          values.put("channel_id", Long.valueOf(channel.channelId));
/* 3187 */          values.put("channel", channel.channel);
/* 3188 */          values.put(Vine.ChannelsColumns.BACKGROUND_COLOR, channel.backgroundColor);
/* 3189 */          values.put(Vine.ChannelsColumns.FONT_COLOR, channel.fontColor);
/* 3190 */          values.put("timestamp", Long.valueOf(channel.created));
/* 3191 */          values.put(Vine.ChannelsColumns.ICON_FULL_URL, channel.iconFullUrl);
/* 3192 */          values.put(Vine.ChannelsColumns.RETINA_ICON_FULL_URL, channel.retinaIconFullUrl);
/* 3193 */          values.put("is_last", Boolean.valueOf(channel.isLast));
/* 3194 */          values.put("following", Boolean.valueOf(channel.following));
                }

/* 3197 */      public static String getDatabaseName(int schemaVersion) {
/* 3198 */          return "vine-" + schemaVersion;
                }

/* 3201 */      private void notifyPostCommentsViewUris() {
/* 3202 */          this.mContentResolver.notifyChange(Vine.PostCommentsLikesView.CONTENT_URI_ALL_TIMELINE, null);
/* 3204 */          this.mContentResolver.notifyChange(Vine.PostCommentsLikesView.CONTENT_URI_ALL_TIMELINE_USER, null);
/* 3206 */          this.mContentResolver.notifyChange(Vine.PostCommentsLikesView.CONTENT_URI_ALL_TIMELINE_USER_LIKES, null);
/* 3208 */          this.mContentResolver.notifyChange(Vine.PostCommentsLikesView.CONTENT_URI_ALL_TIMELINE_SINGLE, null);
                }

/* 3212 */      private void notifyByType(int groupType) {
                    switch (groupType) {
                        case 4:
/* 3223 */                  this.mContentResolver.notifyChange(Vine.PostCommentsLikesView.CONTENT_URI_ALL_TIMELINE_ON_THE_RISE, null);
                            break;
                        case 5:
/* 3227 */                  this.mContentResolver.notifyChange(Vine.PostCommentsLikesView.CONTENT_URI_ALL_TIMELINE_POPULAR_NOW, null);
                            break;
                        case 6:
/* 3231 */                  this.mContentResolver.notifyChange(Vine.PostCommentsLikesView.CONTENT_URI_ALL_TIMELINE_TAG, null);
                            break;
                        case 7:
                        default:
/* 3235 */                  notifyPostCommentsViewUris();
                            break;
                        case 8:
/* 3215 */                  this.mContentResolver.notifyChange(Vine.PostCommentsLikesView.CONTENT_URI_ALL_TIMELINE_CHANNEL_POPULAR, null);
                            break;
                        case 9:
/* 3219 */                  this.mContentResolver.notifyChange(Vine.PostCommentsLikesView.CONTENT_URI_ALL_TIMELINE_CHANNEL_RECENT, null);
                            break;
                    }
                }

/* 3247 */      public void safeFinishTransaction(SQLiteDatabase db) {
/* 3248 */          if (db.inTransaction()) {
                        try {
/* 3250 */                  db.endTransaction();
                        } catch (SQLiteDiskIOException e) {
/* 3252 */                  throw e;
                        } catch (SQLiteFullException e2) {
/* 3254 */                  long currentSize = -1;
                            try {
/* 3256 */                      currentSize = Util.getCacheSize(this.mAppContext);
                            } catch (VineLoggingException e3) {
/* 3258 */                      CrashUtil.logException(e2);
                            }
/* 3260 */                  AppController.clearFileCache(this.mAppContext);
                            try {
/* 3262 */                      CrashUtil.logException(new VineLoggingException(), "Db full, cleaned cache from {} to {}.", Long.valueOf(currentSize), Long.valueOf(Util.getCacheSize(this.mAppContext)));
                            } catch (VineLoggingException e4) {
/* 3264 */                      CrashUtil.logException(new VineLoggingException(), "Db full, cleaned cache from {} to unknown.", Long.valueOf(currentSize));
                            }
/* 3266 */                  if (db.inTransaction()) {
/* 3267 */                      db.endTransaction();
                            }
                        } catch (SQLiteException e5) {
/* 3270 */                  if (!e5.getMessage().contains("(code 1)")) {
/* 3271 */                      throw e5;
                            }
                        } catch (IllegalStateException e6) {
/* 3274 */                  if (!e6.getMessage().contains("already-closed")) {
/* 3275 */                      throw e6;
                            }
                        }
                    }
                }

/* 3281 */      public synchronized void mergeEditions(ArrayList<Pair<String, String>> items) {
                    try {
                        SQLiteDatabase db = getWritableDatabase();
/* 3284 */              db.delete("editions", null, null);
/* 3286 */              int numInserted = 0;
/* 3287 */              if (!items.isEmpty()) {
/* 3288 */                  db.beginTransaction();
                            try {
/* 3290 */                      ContentValues values = new ContentValues();
                                for (Pair<String, String> edition : items) {
/* 3292 */                          values.put(Vine.Editions.EDITION_CODE, (String) edition.first);
/* 3293 */                          values.put(Vine.Editions.EDITION_NAME, (String) edition.second);
/* 3294 */                          numInserted = (int) (((long) numInserted) + db.insert("editions", null, values));
                                }
/* 3296 */                      db.setTransactionSuccessful();
/* 3298 */                      safeFinishTransaction(db);
                            } catch (Throwable th) {
                                safeFinishTransaction(db);
                                throw th;
                            }
                        }
/* 3302 */              if (numInserted > 0) {
/* 3303 */                  this.mContentResolver.notifyChange(Vine.Editions.CONTENT_URI, null);
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }

/* 3310 */      public synchronized void updateMessageLoopStartTime(long localStartTime, long messageId) {
                    synchronized (this) {
                        try {
                            SQLiteDatabase db = getWritableDatabase();
/* 3313 */                  db.beginTransaction();
                            try {
/* 3316 */                      ContentValues values = new ContentValues();
/* 3317 */                      values.put(Vine.MessagesColumns.LOCAL_START_TIME, Long.valueOf(localStartTime));
/* 3318 */                      int r = db.update("messages", values, "message_id=?", new String[]{String.valueOf(messageId)});
/* 3320 */                      if (SLog.sLogsOn) {
/* 3321 */                          SLog.m449d("Updated {} local start time to {} ? {}.", Long.valueOf(messageId), Long.valueOf(localStartTime), Boolean.valueOf(r == 1));
                                }
/* 3323 */                      db.setTransactionSuccessful();
/* 3325 */                      safeFinishTransaction(db);
                            } catch (Throwable th) {
                                safeFinishTransaction(db);
                                throw th;
                            }
                        } catch (Throwable th2) {
                            throw th2;
                        }
                    }
                }

/* 3329 */      public synchronized long getConversationRemoteId(long conversationRowId) {
                    long id;
                    try {
                        SQLiteDatabase db = getReadableDatabase();
/* 3331 */              Cursor c = db.query("conversations", new String[]{"conversation_id"}, "_id =?", new String[]{String.valueOf(conversationRowId)}, null, null, null, "1");
/* 3333 */              if (c != null) {
/* 3334 */                  id = -1;
/* 3335 */                  if (c.moveToFirst()) {
/* 3336 */                      id = c.getLong(0);
                            }
/* 3338 */                  c.close();
                        } else {
                            id = -1;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return id;
                }

/* 3344 */      public synchronized long getConversationRowId(long conversationRemoteId) {
                    long id;
                    try {
                        SQLiteDatabase db = getReadableDatabase();
/* 3346 */              Cursor c = db.query("conversations", new String[]{"_id"}, "conversation_id =?", new String[]{String.valueOf(conversationRemoteId)}, null, null, null, "1");
/* 3348 */              if (c != null) {
/* 3349 */                  id = -1;
/* 3350 */                  if (c.moveToFirst()) {
/* 3351 */                      id = c.getLong(0);
                            }
/* 3353 */                  c.close();
                        } else {
                            id = -1;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return id;
                }

/* 3359 */      public synchronized long getLastMessageIdInConversation(long conversationRowId) {
                    long ret;
                    try {
                        SQLiteDatabase db = getWritableDatabase();
/* 3361 */              Cursor c = db.query("messages", new String[]{"message_id"}, "conversation_row_id =? ", new String[]{String.valueOf(conversationRowId)}, null, null, Vine.Messages.SORT_ORDER_REVERSE, "1");
/* 3364 */              ret = -1;
/* 3365 */              if (c != null) {
/* 3366 */                  if (c.moveToFirst()) {
/* 3367 */                      ret = c.getLong(0);
                            }
/* 3369 */                  c.close();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return ret;
                }

/* 3374 */      public synchronized void clearUnreadCount(long conversationRowId) {
                    try {
                        SQLiteDatabase db = getWritableDatabase();
                        try {
/* 3379 */                  db.beginTransaction();
/* 3380 */                  ContentValues values = new ContentValues(1);
/* 3381 */                  values.put(Vine.ConversationsColumns.UNREAD_MESSAGE_COUNT, (Integer) 0);
/* 3383 */                  String[] whereArgs = {String.valueOf(conversationRowId)};
/* 3384 */                  int updated = db.update("conversations", values, VineDatabaseSQL.SELECTION_ROW_ID, whereArgs);
/* 3385 */                  db.setTransactionSuccessful();
/* 3387 */                  safeFinishTransaction(db);
/* 3390 */                  if (updated > 0) {
/* 3391 */                      this.mContentResolver.notifyChange(Vine.ConversationMessageUsersView.CONTENT_URI, null);
/* 3392 */                      this.mContentResolver.notifyChange(Vine.InboxView.CONTENT_URI, null);
                            }
                        } catch (Throwable th) {
                            safeFinishTransaction(db);
                            throw th;
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }

/* 3396 */      public synchronized void deleteMessage(long messageId) {
                    try {
                        SQLiteDatabase db = getWritableDatabase();
/* 3399 */              ContentValues values = new ContentValues();
/* 3400 */              values.put(Vine.MessagesColumns.DELETED, (Integer) 1);
/* 3402 */              String messageIdString = String.valueOf(messageId);
/* 3403 */              int updated = db.update("messages", values, "message_id=?", new String[]{messageIdString});
/* 3405 */              Cursor c = db.query("messages", new String[]{"conversation_row_id", "is_last"}, "message_id=?", new String[]{messageIdString}, null, null, null);
/* 3407 */              if (c != null) {
                            try {
/* 3409 */                      if (c.moveToFirst()) {
/* 3410 */                          long conversationRowId = c.getLong(0);
/* 3411 */                          boolean isLast = c.getInt(1) == 1;
/* 3412 */                          if (getNumberVisibleMessagesInConversation(conversationRowId, db) == 0) {
/* 3413 */                              setConversationHidden(conversationRowId, true);
                                    } else if (isLast) {
/* 3415 */                              markLastMessage(conversationRowId);
                                    }
                                }
/* 3419 */                      c.close();
                            } catch (Throwable th) {
                                c.close();
                                throw th;
                            }
                        }
/* 3423 */              if (updated > 0) {
/* 3424 */                  notifyVMUris();
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }

/* 3428 */      public void permaDeleteMessageFromRowId(long messageRowId) {
/* 3429 */          SQLiteDatabase db = getWritableDatabase();
/* 3431 */          ContentValues values = new ContentValues();
/* 3432 */          values.put(Vine.MessagesColumns.DELETED, (Integer) 1);
/* 3434 */          String messageIdString = String.valueOf(messageRowId);
/* 3435 */          int deleted = db.delete("messages", VineDatabaseSQL.SELECTION_ROW_ID, new String[]{messageIdString});
/* 3437 */          if (deleted > 0) {
/* 3438 */              notifyVMUris();
                    }
                }

/* 3442 */      public int getNumberVisibleMessagesInConversation(long conversationRowId, SQLiteDatabase db) {
                    int res = 0;
/* 3443 */          Cursor c = db.query("messages", new String[]{"_id"}, "conversation_row_id =? AND deleted != 1", new String[]{String.valueOf(conversationRowId)}, null, null, null);
/* 3446 */          if (c != null) {
                        try {
/* 3448 */                  res = c.getCount();
/* 3451 */                  c.close();
                        } catch (Throwable th) {
                            c.close();
                            throw th;
                        }
                    }
/* 3455 */          return res;
                }

/* 3461 */      public void setConversationHidden(long conversationRowId, boolean hidden) {
/* 3462 */          SQLiteDatabase db = getWritableDatabase();
/* 3463 */          ContentValues cv = new ContentValues();
/* 3464 */          cv.put(Vine.ConversationsColumns.IS_HIDDEN, Integer.valueOf(hidden ? 1 : 0));
/* 3465 */          int updated = db.update("conversations", cv, VineDatabaseSQL.SELECTION_ROW_ID, new String[]{String.valueOf(conversationRowId)});
/* 3466 */          Log.d(TAG, "Marked conversation " + conversationRowId + " hidden value " + hidden + " updated rows: " + updated);
                }

/* 3470 */      public long createPreMergeConversationIdAndRecipientsIfNecessary(List<VineRecipient> recipients, int networkType) {
/* 3471 */          HashSet<Long> userRowIds = new HashSet<>();
                    for (VineRecipient recipient : recipients) {
/* 3474 */              if (!recipient.isFromUser()) {
/* 3475 */                  long recipientId = getUserRowIdForRecipient(recipient);
                            if (recipientId <= 0) {
/* 3477 */                      String email = null;
/* 3478 */                      String phone = null;
/* 3479 */                      if (recipient.isFromEmail()) {
/* 3480 */                          email = recipient.value;
                                } else {
/* 3482 */                          phone = recipient.value;
                                }
/* 3484 */                      recipientId = createNewUserFromNonUserIdRecipientInfo(recipient.display, phone, email);
                            }
/* 3487 */                  recipient.recipientId = recipientId;
/* 3488 */                  userRowIds.add(Long.valueOf(recipientId));
                        } else {
/* 3490 */                  userRowIds.add(Long.valueOf(recipient.recipientId));
                        }
                    }
/* 3494 */          return createConversationRowId(userRowIds, networkType);
                }

/* 3497 */      public long createNewUserFromNonUserIdRecipientInfo(String display, String phone, String email) {
/* 3498 */          VineUser user = new VineUser(display, null, null, null, -1L, -1L, 0, 0, 0, true, 0, 0, 0, 0, 0, 0, email, phone, 0, 0, 0, 0, null, false, 0, 0, "", false, false, false, false, 0L, false);
/* 3500 */          ContentValues values = new ContentValues();
/* 3501 */          fillUserContentValues(values, user, System.currentTimeMillis());
/* 3502 */          SQLiteDatabase db = getWritableDatabase();
/* 3503 */          return db.insertOrThrow("users", "last_refresh", values);
                }

/* 3506 */      public long mergeUserAndGetResultingRowId(VineUser user) {
/* 3507 */          ContentValues values = new ContentValues();
/* 3508 */          fillUserContentValues(values, user, System.currentTimeMillis());
/* 3509 */          SQLiteDatabase db = getWritableDatabase();
/* 3510 */          Cursor c = db.query("users", new String[]{"_id"}, VineDatabaseSQL.SELECTION_USER_ID, new String[]{String.valueOf(user.userId)}, null, null, null);
/* 3512 */          if (c != null) {
                        try {
/* 3514 */                  if (c.moveToFirst()) {
/* 3515 */                      long updateId = c.getLong(0);
/* 3516 */                      db.update("users", values, VineDatabaseSQL.SELECTION_ROW_ID, new String[]{String.valueOf(updateId)});
/* 3520 */                      c.close();
/* 3523 */                      return updateId;
                            }
/* 3520 */                  c.close();
                        } catch (Throwable th) {
                            c.close();
                            throw th;
                        }
                    }
/* 3523 */          return db.insertOrThrow("users", null, values);
                }

/* 3526 */      public synchronized long createConversationRowId(HashSet<Long> userRowIds, int networkType) {
                    long conversationRowId;
                    try {
                        SQLiteDatabase db = getWritableDatabase();
                        try {
/* 3531 */                  db.beginTransaction();
/* 3532 */                  ContentValues values = new ContentValues();
/* 3533 */                  fillConversationValues(values, -1L, Integer.valueOf(networkType), false, 0L, 0L);
/* 3534 */                  conversationRowId = db.insert("conversations", null, values);
/* 3535 */                  db.setTransactionSuccessful();
/* 3537 */                  safeFinishTransaction(db);
                            if (conversationRowId > 0) {
/* 3540 */                      mergeConversationRecipients(conversationRowId, userRowIds);
/* 3541 */                      this.mContentResolver.notifyChange(Vine.InboxView.CONTENT_URI, null);
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

/* 3547 */      public VinePrivateMessage getMessageFromMessageRow(long mergedMessageId) {
/* 3548 */          VinePrivateMessage vpm = null;
/* 3549 */          SQLiteDatabase db = getReadableDatabase();
/* 3550 */          Cursor c = null;
                    try {
/* 3553 */              Cursor c2 = db.query(Vine.ConversationMessageUsersView.TABLE_NAME, VineDatabaseSQL.ConversationMessageUsersQuery.PROJECTION, VineDatabaseSQL.SELECTION_ROW_ID, new String[]{String.valueOf(mergedMessageId)}, null, null, null);
/* 3556 */              if (c2 != null) {
                            try {
/* 3558 */                      if (c2.moveToFirst()) {
/* 3559 */                          vpm = new VinePrivateMessage(c2);
                                }
/* 3562 */                      c2.close();
                            } catch (Throwable th) {
                                c2.close();
                                throw th;
                            }
                        }
/* 3566 */              if (c2 != null) {
/* 3567 */                  c2.close();
                        }
/* 3570 */              return vpm;
                    } catch (Throwable th2) {
/* 3550 */              if (0 != 0) {
/* 3567 */                  c.close();
                        }
                        throw th2;
                    }
                }

/* 3588 */      public void mergeRecipientsWithUsersAndRemoveUnusedRecipients(long conversationRowId, ArrayList<VineRecipient> recipients, ArrayList<VineUser> users) {
/* 3589 */          HashSet<Long> conversationRecipients = new HashSet<>();
/* 3590 */          HashMap<Long, VineUser> userMap = new HashMap<>();
                    for (VineUser user : users) {
/* 3592 */              userMap.put(Long.valueOf(user.userId), user);
                    }
/* 3595 */          SQLiteDatabase db = getWritableDatabase();
/* 3596 */          Cursor c = db.query(Vine.ConversationRecipients.TABLE_NAME, new String[]{"user_row_id"}, "conversation_row_id =? ", new String[]{String.valueOf(conversationRowId)}, null, null, null);
/* 3601 */          if (c != null) {
                        try {
/* 3603 */                  c.moveToPosition(-1);
/* 3604 */                  while (c.moveToNext()) {
/* 3605 */                      conversationRecipients.add(Long.valueOf(c.getLong(0)));
                            }
                            c.close();
                        } catch (Throwable th) {
                            c.close();
                            throw th;
                        }
                    }
                    try {
/* 3615 */              db.beginTransaction();
                        for (VineRecipient recipient : recipients) {
/* 3617 */                  if (recipient != null && recipient.userId > 0 && recipient.recipientId > 0) {
/* 3618 */                      ContentValues cv = new ContentValues();
/* 3619 */                      VineUser user2 = userMap.get(Long.valueOf(recipient.userId));
/* 3620 */                      if (user2 != null) {
/* 3621 */                          fillUserContentValues(cv, user2, System.currentTimeMillis());
/* 3622 */                          db.update("users", cv, "_id=? ", new String[]{String.valueOf(recipient.recipientId)});
/* 3623 */                          conversationRecipients.remove(Long.valueOf(recipient.recipientId));
                                }
                            }
                        }
/* 3627 */              db.setTransactionSuccessful();
/* 3629 */              safeFinishTransaction(db);
/* 3632 */              if (conversationRecipients.size() > 0) {
                            try {
/* 3636 */                      db.beginTransaction();
/* 3638 */                      int i = 0;
/* 3639 */                      int size = conversationRecipients.size();
/* 3640 */                      String[] recipientsStringArray = new String[size];
/* 3643 */                      StringBuilder builder = new StringBuilder();
/* 3644 */                      builder.append(" IN (");
                                for (Long l : conversationRecipients) {
/* 3647 */                          builder.append("?");
/* 3648 */                          recipientsStringArray[i] = l.toString();
/* 3649 */                          if (i != size - 1) {
/* 3650 */                              builder.append(", ");
                                    }
/* 3652 */                          i++;
                                }
/* 3654 */                      builder.append(")");
/* 3656 */                      String inClause = builder.toString();
/* 3657 */                      int deleted = db.delete("users", "_id" + inClause, recipientsStringArray);
/* 3658 */                      int iDelete = deleted + db.delete(Vine.ConversationRecipients.TABLE_NAME, "user_row_id" + inClause, recipientsStringArray);
/* 3660 */                      db.setTransactionSuccessful();
/* 3662 */                      safeFinishTransaction(db);
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

/* 3667 */      public long getUserRowIdForUserRemoteId(long userId) {
/* 3668 */          SQLiteDatabase db = getReadableDatabase();
/* 3669 */          Cursor c = db.query("users", new String[]{"_id"}, "user_id =? ", new String[]{String.valueOf(userId)}, null, null, null);
/* 3671 */          long rowId = -1;
/* 3672 */          if (c != null) {
                        try {
/* 3674 */                  if (c.moveToFirst()) {
/* 3675 */                      rowId = c.getLong(0);
                            }
/* 3678 */                  c.close();
                        } catch (Throwable th) {
                            c.close();
                            throw th;
                        }
                    }
/* 3681 */          return rowId;
                }

/* 3684 */      public long getUserRemoteIdForUserRowId(long userRowId) {
/* 3685 */          SQLiteDatabase db = getReadableDatabase();
/* 3686 */          Cursor c = db.query("users", new String[]{"user_id"}, "_id =? ", new String[]{String.valueOf(userRowId)}, null, null, null);
/* 3688 */          long rowId = -1;
/* 3689 */          if (c != null) {
                        try {
/* 3691 */                  if (c.moveToFirst()) {
/* 3692 */                      rowId = c.getLong(0);
                            }
/* 3695 */                  c.close();
                        } catch (Throwable th) {
                            c.close();
                            throw th;
                        }
                    }
/* 3698 */          return rowId;
                }

/* 3701 */      public long getUserRowIdForRecipient(VineRecipient recipient) {
                    Cursor c;
                    if (recipient.recipientId > 0) {
/* 3703 */              return recipient.recipientId;
                    }
/* 3704 */          if (recipient.isFromUser() && recipient.userId > 0) {
/* 3705 */              return getUserRowIdForUserRemoteId(recipient.userId);
                    }
/* 3708 */          SQLiteDatabase db = getReadableDatabase();
/* 3711 */          String column = null;
/* 3712 */          if (recipient.isFromEmail()) {
/* 3713 */              column = Vine.UserColumns.EMAIL_ADDRESS;
                    } else if (recipient.isFromPhone()) {
/* 3715 */              column = "phone_number";
                    }
/* 3718 */          if (column != null && (c = db.query("users", new String[]{"_id"}, column + "=? AND user_id= -1", new String[]{recipient.value}, null, null, null)) != null) {
                        try {
/* 3725 */                  if (c.moveToFirst()) {
/* 3726 */                      long j = c.getLong(0);
/* 3730 */                      c.close();
/* 3734 */                      return j;
                            }
/* 3730 */                  c.close();
                        } catch (Throwable th) {
                            c.close();
                            throw th;
                        }
                    }
/* 3734 */          return -1L;
                }

/* 3737 */      public long determineOrCreateBestConversationRowIdForRecipients(List<VineRecipient> recipients, int networkType) {
/* 3738 */          long conversationRowId = -1;
/* 3739 */          if (recipients.size() == 1) {
/* 3740 */              VineRecipient recipient = recipients.get(0);
/* 3741 */              conversationRowId = determineBestConversationRowIdForRecipient(recipient);
                    }
                    if (conversationRowId <= 0) {
/* 3746 */              long conversationRowId2 = createPreMergeConversationIdAndRecipientsIfNecessary(recipients, networkType);
/* 3748 */              return conversationRowId2;
                    }
/* 3748 */          return conversationRowId;
                }

/* 3753 */      public long determineBestConversationRowIdForRecipient(VineRecipient recipient) {
/* 3754 */          long userRowId = getUserRowIdForRecipient(recipient);
/* 3755 */          return determineBestConversationRowIdForUserRowId(userRowId);
                }

/* 3758 */      public long determineBestConversationRowIdForUserRemoteId(long userRemoteId) {
/* 3759 */          long userRowId = getUserRowIdForUserRemoteId(userRemoteId);
/* 3760 */          return determineBestConversationRowIdForUserRowId(userRowId);
                }

/* 3763 */      public long determineBestConversationRowIdForUserRowId(long userRowId) {
/* 3764 */          SQLiteDatabase db = getReadableDatabase();
/* 3766 */          Cursor c = db.query(Vine.ConversationRecipients.TABLE_NAME, new String[]{"conversation_row_id"}, "user_row_id =?", new String[]{String.valueOf(userRowId)}, null, null, null);
/* 3771 */          long resultConversationObjectId = -1;
/* 3772 */          if (c != null) {
/* 3776 */              while (c.moveToNext()) {
                            try {
/* 3777 */                      long conversationObjectId = c.getLong(0);
/* 3778 */                      Cursor c2 = db.query(Vine.ConversationRecipients.TABLE_NAME, new String[]{"_id"}, "conversation_row_id =? ", new String[]{String.valueOf(conversationObjectId)}, null, null, null);
/* 3782 */                      if (c2 != null) {
                                    try {
/* 3784 */                              if (c2.getCount() == 1) {
/* 3785 */                                  resultConversationObjectId = conversationObjectId;
/* 3786 */                                  c2.close();
/* 3790 */                                  if (c2 == null) {
                                                break;
                                            }
/* 3791 */                                  c2.close();
                                            break;
                                        }
/* 3790 */                              if (c2 != null) {
/* 3791 */                                  c2.close();
                                        }
                                    } catch (Throwable th) {
                                        if (c2 != null) {
/* 3791 */                                  c2.close();
                                        }
                                        throw th;
                                    }
                                }
                            } catch (Throwable th2) {
                                if (c != null) {
/* 3798 */                          c.close();
                                }
                                throw th2;
                            }
                        }
/* 3797 */              if (c != null) {
/* 3798 */                  c.close();
                        }
                    }
/* 3802 */          return resultConversationObjectId;
                }

/* 3805 */      public ArrayList<VineRecipient> getConversationRecipientsFromConversationRowId(long conversationRowId) throws Throwable {
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

/* 3839 */      public void setMessageError(String uploadPath, int errorCode, String errorReason) {
/* 3840 */          if (!TextUtils.isEmpty(uploadPath)) {
/* 3841 */              SQLiteDatabase db = getWritableDatabase();
/* 3842 */              ContentValues cv = new ContentValues();
/* 3843 */              cv.put("error_code", Integer.valueOf(errorCode));
/* 3844 */              cv.put(Vine.MessagesColumns.ERROR_REASON, errorReason);
/* 3845 */              int updated = db.update("messages", cv, "upload_path=?", new String[]{uploadPath});
/* 3848 */              if (updated > 0) {
/* 3849 */                  notifyVMUris();
                        }
                    }
                }

/* 3854 */      public void setMessageError(long messageRowId, int errorCode, String errorReason) {
                    if (messageRowId > 0) {
/* 3856 */              SQLiteDatabase db = getWritableDatabase();
/* 3857 */              ContentValues cv = new ContentValues();
/* 3858 */              cv.put("error_code", Integer.valueOf(errorCode));
/* 3859 */              cv.put(Vine.MessagesColumns.ERROR_REASON, errorReason);
/* 3860 */              int updated = db.update("messages", cv, VineDatabaseSQL.SELECTION_ROW_ID, new String[]{String.valueOf(messageRowId)});
/* 3863 */              if (updated > 0) {
/* 3864 */                  notifyVMUris();
                        }
                    }
                }

/* 3869 */      public long getNewMessageId() {
/* 3870 */          SQLiteDatabase db = getReadableDatabase();
/* 3871 */          Cursor c = db.query("messages", new String[]{"message_id"}, null, null, null, null, Vine.Messages.SORT_ORDER_REVERSE, "1");
/* 3872 */          long ret = 1;
/* 3873 */          if (c != null) {
/* 3875 */              if (c.moveToFirst()) {
/* 3876 */                  ret = c.getLong(0) + 1;
                        }
/* 3878 */              c.close();
                    }
/* 3880 */          return ret;
                }

/* 3883 */      public boolean amFollowingUser(long userRowId) {
/* 3884 */          SQLiteDatabase db = getReadableDatabase();
/* 3885 */          Cursor c = db.query("users", new String[]{"following_flag"}, "_id =? ", new String[]{String.valueOf(userRowId)}, null, null, null);
/* 3887 */          boolean following = false;
/* 3888 */          if (c != null) {
                        try {
/* 3890 */                  if (c.moveToFirst()) {
/* 3891 */                      following = c.getInt(0) == 1;
                            }
/* 3894 */                  if (c != null) {
/* 3895 */                      c.close();
                            }
                        } catch (Throwable th) {
                            if (c != null) {
/* 3895 */                      c.close();
                            }
                            throw th;
                        }
                    }
/* 3899 */          return following;
                }

/* 3902 */      public void removeUserSectionsWithType(int sectionType) {
/* 3903 */          SQLiteDatabase db = getWritableDatabase();
/* 3904 */          db.delete(Vine.UserSections.TABLE_NAME, "section_type=?", new String[]{String.valueOf(sectionType)});
                }

/* 3908 */      public String getDisplayForUser(long recipientId) {
/* 3909 */          SQLiteDatabase db = getReadableDatabase();
/* 3910 */          Cursor c = db.query("users", new String[]{"username"}, "_id =? ", new String[]{String.valueOf(recipientId)}, null, null, null);
/* 3912 */          String name = null;
/* 3913 */          if (c != null) {
                        try {
/* 3915 */                  if (c.moveToFirst()) {
/* 3916 */                      name = c.getString(0);
                            }
/* 3919 */                  c.close();
                        } catch (Throwable th) {
                            c.close();
                            throw th;
                        }
                    }
/* 3922 */          return name;
                }

/* 3925 */      public void updateUserFollowingFlag(long userIdToFollow, boolean following) {
/* 3926 */          SQLiteDatabase db = getWritableDatabase();
/* 3927 */          ContentValues values = new ContentValues();
/* 3928 */          values.put("following_flag", Integer.valueOf(following ? 1 : 0));
/* 3929 */          db.update("users", values, VineDatabaseSQL.SELECTION_USER_ID, new String[]{String.valueOf(userIdToFollow)});
                }

/* 3932 */      public Cursor getMessagesForConversationRowId(long conversationRowId) {
/* 3933 */          SQLiteDatabase db = getReadableDatabase();
/* 3934 */          return db.query(Vine.ConversationMessageUsersView.TABLE_NAME, VineDatabaseSQL.ConversationMessageUsersQuery.PROJECTION, "conversation_row_id=? AND error_code <> 0", new String[]{String.valueOf(conversationRowId)}, null, null, Vine.Messages.SORT_ORDER_DEFAULT);
                }

/* 3938 */      public VinePost getPost(long postId) {
/* 3939 */          SQLiteDatabase db = getReadableDatabase();
/* 3940 */          Cursor c = db.query(Vine.PostCommentsLikesView.TABLE_NAME, VineDatabaseSQL.PostCommentsLikesQuery.PROJECTION, "post_id =? ", new String[]{String.valueOf(postId)}, null, null, null);
/* 3941 */          if (c != null) {
                        try {
/* 3943 */                  if (c.moveToFirst()) {
/* 3944 */                      VinePost vp = VinePost.from(c);
/* 3948 */                      c.close();
/* 3951 */                      return vp;
                            }
/* 3948 */                  c.close();
                        } catch (Throwable th) {
                            c.close();
                            throw th;
                        }
                    }
                    return null;
                }

/* 3958 */      public void removeAccount() {
/* 3959 */          setValue(CURRENT_ACCOUNT_KEY, (String) null);
/* 3960 */          setValue(CURRENT_USER_ID_KEY, 0L);
/* 3961 */          setValue(CURRENT_ACCOUNT_NAME, (String) null);
                }

/* 3964 */      private void setValue(String propName, String value) {
/* 3965 */          ContentValues values = new ContentValues();
/* 3966 */          values.put(Vine.Settings.PROP_VALUE, value);
/* 3967 */          saveUserValue(propName, values);
                }

/* 3970 */      private void setValue(String propName, long value) {
/* 3971 */          ContentValues values = new ContentValues();
/* 3972 */          values.put(Vine.Settings.PROP_VALUE, Long.valueOf(value));
/* 3973 */          saveUserValue(propName, values);
                }

/* 3979 */      public static class VineViewedMessage {
                    public final long conversationId;
                    public final long localStartTime;
                    public final long messageId;

/* 3980 */          public VineViewedMessage(long conversationId, long messageId, long localStartTime) {
/* 3982 */              this.conversationId = conversationId;
/* 3983 */              this.messageId = messageId;
/* 3984 */              this.localStartTime = localStartTime;
                    }
                }

/* 3991 */      public ArrayList<VineViewedMessage> getMessagesThatWereViewed() {
/* 3992 */          SQLiteDatabase db = getWritableDatabase();
/* 3993 */          Cursor c = db.query(Vine.ConversationMessageUsersView.TABLE_NAME, new String[]{"conversation_id", "message_id", Vine.MessagesColumns.LOCAL_START_TIME}, "local_start_time!=0", null, null, null, Vine.Messages.SORT_ORDER_REVERSE, "1");
/* 3997 */          ArrayList<VineViewedMessage> messages = new ArrayList<>();
/* 3999 */          if (c != null) {
/* 4001 */              while (c.moveToNext()) {
                            try {
                                try {
/* 4002 */                          messages.add(new VineViewedMessage(c.getLong(0), c.getLong(1), c.getLong(2)));
                                } catch (Exception e) {
/* 4005 */                          SLog.m455e("Error while getting viewed messages.");
/* 4007 */                          c.close();
                                }
                            } catch (Throwable th) {
                                c.close();
                                throw th;
                            }
                        }
/* 4007 */              c.close();
                    }
/* 4010 */          return messages;
                }
            }
