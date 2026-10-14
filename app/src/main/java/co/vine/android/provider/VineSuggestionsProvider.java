            package co.vine.android.provider;

            import android.content.ContentProvider;
            import android.content.ContentResolver;
            import android.content.ContentUris;
            import android.content.ContentValues;
            import android.content.UriMatcher;
            import android.database.Cursor;
            import android.database.MatrixCursor;
            import android.database.sqlite.SQLiteDatabase;
            import android.database.sqlite.SQLiteException;
            import android.database.sqlite.SQLiteQueryBuilder;
            import android.net.Uri;
            import android.text.TextUtils;
            import android.util.Log;
            import co.vine.android.api.VineTag;
            import co.vine.android.api.VineUser;
            import co.vine.android.client.AppController;
            import co.vine.android.util.BuildUtil;
            import co.vine.android.util.CrashUtil;
            import com.edisonwang.android.slog.SLog;
            import java.util.ArrayList;
            import java.util.HashMap;
            import java.util.HashSet;
            
            
    public class VineSuggestionsProvider extends ContentProvider {
                public static final String AUTHORITY;
                public static final String CONTENT_AUTHORITY;
                private static final boolean LOGGABLE;
                public static final String PATH_TAGS = "tags";
                public static final String PATH_USERS = "users";
                public static final String SCHEME = "content";
                private static final int SUGGEST_TAGS = 2;
                private static final int SUGGEST_USERS = 1;
                private static final String TAG = "VineSuggestProvider";
                public static final Uri TAGS_URI;
                public static final Uri USERS_URI;
                private static final HashMap<String, ArrayList<VineTag>> sTagsTypeAhead;
                private static final UriMatcher sUriMatcher;
                private static final HashMap<String, ArrayList<VineUser>> sUsersTypeAhead;
                private AppController mAppController;

                public interface TagsQuery {
                    public static final int INDEX_TAG_ID = 1;
                    public static final int INDEX_TAG_NAME = 2;
                    public static final String[] PROJECTION = {"_id", Vine.TagsColumns.TAG_ID, "tag_name"};
                }

                public interface UsersQuery {
                    public static final int INDEX_AVATAR_URL = 3;
                    public static final int INDEX_USERNAME = 2;
                    public static final int INDEX_USER_ID = 1;
                    public static final String[] PROJECTION = {"_id", "user_id", "username", "avatar_url"};
                }

                static {
            LOGGABLE = BuildUtil.isLogsOn() || Log.isLoggable(TAG, 3);
            AUTHORITY = BuildUtil.getAuthority(".provider.VineSuggestionsProvider");
            CONTENT_AUTHORITY = "content://" + AUTHORITY + "/";
            USERS_URI = Uri.parse(CONTENT_AUTHORITY + "users");
            TAGS_URI = Uri.parse(CONTENT_AUTHORITY + "tags");
            sUriMatcher = new UriMatcher(-1);
            sUsersTypeAhead = new HashMap<>();
            sTagsTypeAhead = new HashMap<>();
            sUriMatcher.addURI(AUTHORITY, "users/#", 1);
            sUriMatcher.addURI(AUTHORITY, "tags", 2);
                }

                @Override 
        public boolean onCreate() {
            this.mAppController = AppController.getInstance(getContext());
            return true;
                }

                @Override 
        public Cursor query(Uri uri, String[] projection, String selection, String[] selArgs, String sortOrder) {
                    SQLiteDatabase db;
                    try {
                db = VineDatabaseHelper.getDatabaseHelper(getContext()).getReadableDatabase();
                    } catch (SQLiteException e) {
                CrashUtil.logException(e, "Failed to get a readable database on query.", new Object[0]);
                db = VineDatabaseHelper.getDatabaseHelper(getContext()).getWritableDatabase();
                    }
            SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
            if (LOGGABLE) {
                Log.d(TAG, "QUERY: uri " + uri + " -> " + sUriMatcher.match(uri));
                    }
            int match = sUriMatcher.match(uri);
                    switch (match) {
                        case 1:
                    long userId = Long.parseLong(uri.getLastPathSegment());
                   Cursor c = getComposeUserSuggestions(selection, new MatrixCursor(UsersQuery.PROJECTION), userId);
                   return c;
                        case 2:
                   Cursor c2 = getComposeTagSuggestions(selection, new MatrixCursor(TagsQuery.PROJECTION));
                   return c2;
                        default:
                   qb.setTables("user_groups_view");
                            try {
                        Cursor c3 = qb.query(db, projection, selection, selArgs, null, null, sortOrder);
                       c3.setNotificationUri(getContext().getContentResolver(), uri);
                       return c3;
                            } catch (Exception e2) {
                       if (LOGGABLE) {
                           SLog.m453d("Cannot execute {} {}  {} {} {} {}", (Object[]) new String[]{db.toString(), qb.getTables(), selection, null, sortOrder});
                                }
                       throw new RuntimeException(e2);
                            }
                    }
                }

                @Override 
       public String getType(Uri uri) {
           return null;
                }

                @Override 
       public Uri insert(Uri uri, ContentValues values) {
           return null;
                }

                @Override 
       public int delete(Uri uri, String selection, String[] selectionArgs) {
           return 0;
                }

                @Override 
       public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
           return 0;
                }

       private Cursor getComposeUserSuggestions(String query, MatrixCursor cursor, long userId) {
                    String selection;
                    String[] selectionArgs;
                    ArrayList<VineUser> users;
           int id = 0;
           int max = this.mAppController.getTypeaheadMaxCompose();
           boolean haveQuery = !TextUtils.isEmpty(query);
           ContentResolver resolver = getContext().getContentResolver();
           Uri uri = ContentUris.withAppendedId(Vine.UserGroupsView.CONTENT_URI_ALL_FOLLOW, userId);
           if (haveQuery) {
               selection = "username LIKE ?";
               selectionArgs = new String[]{query + "%"};
                    } else {
               selection = null;
               selectionArgs = null;
                    }
           Cursor c = resolver.query(uri, UsersQuery.PROJECTION, selection, selectionArgs, Vine.UserGroupsView.SORT_ORDER_DEFINED_DESCENDING);
           HashSet<Long> prefetchIds = new HashSet<>();
           if (c != null) {
               while (c.moveToNext()) {
                   if (!prefetchIds.contains(Long.valueOf(c.getLong(1)))) {
                       if (id > max) {
                                    break;
                                }
                       MatrixCursor.RowBuilder rowBuilder = cursor.newRow();
                                rowBuilder.add(Integer.valueOf(id));
                       rowBuilder.add(Long.valueOf(c.getLong(1)));
                       rowBuilder.add(c.getString(2));
                       rowBuilder.add(c.getString(3));
                       prefetchIds.add(Long.valueOf(c.getLong(1)));
                       id++;
                            }
                        }
               c.close();
                    }
           if (haveQuery && id <= max && (users = getRealtimeUserSuggestions(query)) != null) {
                        for (VineUser user : users) {
                   if (!prefetchIds.contains(Long.valueOf(user.userId))) {
                       if (id > max) {
                                    break;
                                }
                       MatrixCursor.RowBuilder rowBuilder2 = cursor.newRow();
                                rowBuilder2.add(Integer.valueOf(id));
                       rowBuilder2.add(Long.valueOf(user.userId));
                       rowBuilder2.add(user.username);
                       rowBuilder2.add(user.avatarUrl);
                       id++;
                            }
                        }
                    }
           return cursor;
                }

       private Cursor getComposeTagSuggestions(String query, MatrixCursor cursor) {
                    String selection;
                    String[] selectionArgs;
                    ArrayList<VineTag> tags;
           int id = 0;
           int max = this.mAppController.getTypeaheadMaxCompose();
           boolean haveQuery = !TextUtils.isEmpty(query);
           ContentResolver resolver = getContext().getContentResolver();
           Uri uri = Vine.TagsRecentlyUsed.CONTENT_URI;
           if (haveQuery) {
               selection = "tag_name LIKE ?";
               selectionArgs = new String[]{query + "%"};
                    } else {
               selection = null;
               selectionArgs = null;
                    }
           Cursor c = resolver.query(uri, VineDatabaseSQL.TagsRecentlyUsedQuery.PROJECTION, selection, selectionArgs, Vine.TagsRecentlyUsed.SORT_ORDER_NEWEST_FIRST);
           HashSet<Long> prefetchIds = new HashSet<>();
           if (c != null) {
               while (c.moveToNext()) {
                   if (!prefetchIds.contains(Long.valueOf(c.getLong(1)))) {
                       if (id > max) {
                                    break;
                                }
                       MatrixCursor.RowBuilder rowBuilder = cursor.newRow();
                                rowBuilder.add(Integer.valueOf(id));
                       rowBuilder.add(Long.valueOf(c.getLong(1)));
                       rowBuilder.add(c.getString(2));
                       prefetchIds.add(Long.valueOf(c.getLong(1)));
                       id++;
                            }
                        }
               c.close();
                    }
           if (haveQuery && id <= max && (tags = getRealtimeTagSuggestions(query)) != null) {
                        for (VineTag tag : tags) {
                   if (!prefetchIds.contains(Long.valueOf(tag.tagId))) {
                       if (id > max) {
                                    break;
                                }
                       MatrixCursor.RowBuilder rowBuilder2 = cursor.newRow();
                                rowBuilder2.add(Integer.valueOf(id));
                       rowBuilder2.add(Long.valueOf(tag.tagId));
                       rowBuilder2.add(tag.tagName);
                       id++;
                            }
                        }
                    }
           return cursor;
                }

       public static void addRealtimeUserSuggestions(String query, ArrayList<VineUser> users) {
                    synchronized (sUsersTypeAhead) {
                        try {
                   sUsersTypeAhead.put(query, users);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

       public static ArrayList<VineUser> getRealtimeUserSuggestions(String query) {
                    ArrayList<VineUser> arrayList;
                    synchronized (sUsersTypeAhead) {
                        try {
                   arrayList = sUsersTypeAhead.get(query);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return arrayList;
                }

       public static void clearRealtimeUserSuggestions() {
                    synchronized (sUsersTypeAhead) {
                        try {
                   sUsersTypeAhead.clear();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

       public static void addRealtimeTagSuggestions(String query, ArrayList<VineTag> tags) {
                    synchronized (sTagsTypeAhead) {
                        try {
                   sTagsTypeAhead.put(query, tags);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

       public static ArrayList<VineTag> getRealtimeTagSuggestions(String query) {
                    ArrayList<VineTag> arrayList;
                    synchronized (sTagsTypeAhead) {
                        try {
                   arrayList = sTagsTypeAhead.get(query);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return arrayList;
                }

       public static void clearRealtimeTagSuggestions() {
                    synchronized (sTagsTypeAhead) {
                        try {
                   sTagsTypeAhead.clear();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            }
