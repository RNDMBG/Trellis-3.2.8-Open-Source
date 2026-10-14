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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 39 */    public class VineSuggestionsProvider extends ContentProvider {
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
/* 41 */            LOGGABLE = BuildUtil.isLogsOn() || Log.isLoggable(TAG, 3);
/* 44 */            AUTHORITY = BuildUtil.getAuthority(".provider.VineSuggestionsProvider");
/* 45 */            CONTENT_AUTHORITY = "content://" + AUTHORITY + "/";
/* 49 */            USERS_URI = Uri.parse(CONTENT_AUTHORITY + "users");
/* 50 */            TAGS_URI = Uri.parse(CONTENT_AUTHORITY + "tags");
/* 55 */            sUriMatcher = new UriMatcher(-1);
/* 61 */            sUsersTypeAhead = new HashMap<>();
/* 62 */            sTagsTypeAhead = new HashMap<>();
/* 63 */            sUriMatcher.addURI(AUTHORITY, "users/#", 1);
/* 64 */            sUriMatcher.addURI(AUTHORITY, "tags", 2);
                }

                @Override // android.content.ContentProvider
/* 70 */        public boolean onCreate() {
/* 71 */            this.mAppController = AppController.getInstance(getContext());
/* 72 */            return true;
                }

                @Override // android.content.ContentProvider
/* 78 */        public Cursor query(Uri uri, String[] projection, String selection, String[] selArgs, String sortOrder) {
                    SQLiteDatabase db;
                    try {
/* 83 */                db = VineDatabaseHelper.getDatabaseHelper(getContext()).getReadableDatabase();
                    } catch (SQLiteException e) {
/* 86 */                CrashUtil.logException(e, "Failed to get a readable database on query.", new Object[0]);
/* 87 */                db = VineDatabaseHelper.getDatabaseHelper(getContext()).getWritableDatabase();
                    }
/* 91 */            SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
/* 92 */            if (LOGGABLE) {
/* 93 */                Log.d(TAG, "QUERY: uri " + uri + " -> " + sUriMatcher.match(uri));
                    }
/* 96 */            int match = sUriMatcher.match(uri);
                    switch (match) {
                        case 1:
/* 99 */                    long userId = Long.parseLong(uri.getLastPathSegment());
/* 100 */                   Cursor c = getComposeUserSuggestions(selection, new MatrixCursor(UsersQuery.PROJECTION), userId);
/* 117 */                   return c;
                        case 2:
/* 104 */                   Cursor c2 = getComposeTagSuggestions(selection, new MatrixCursor(TagsQuery.PROJECTION));
/* 117 */                   return c2;
                        default:
/* 107 */                   qb.setTables("user_groups_view");
                            try {
/* 79 */                        Cursor c3 = qb.query(db, projection, selection, selArgs, null, null, sortOrder);
/* 116 */                       c3.setNotificationUri(getContext().getContentResolver(), uri);
/* 117 */                       return c3;
                            } catch (Exception e2) {
/* 119 */                       if (LOGGABLE) {
/* 120 */                           SLog.m453d("Cannot execute {} {}  {} {} {} {}", (Object[]) new String[]{db.toString(), qb.getTables(), selection, null, sortOrder});
                                }
/* 123 */                       throw new RuntimeException(e2);
                            }
                    }
                }

                @Override // android.content.ContentProvider
/* 128 */       public String getType(Uri uri) {
/* 129 */           return null;
                }

                @Override // android.content.ContentProvider
/* 133 */       public Uri insert(Uri uri, ContentValues values) {
/* 134 */           return null;
                }

                @Override // android.content.ContentProvider
/* 138 */       public int delete(Uri uri, String selection, String[] selectionArgs) {
/* 139 */           return 0;
                }

                @Override // android.content.ContentProvider
/* 143 */       public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
/* 144 */           return 0;
                }

/* 147 */       private Cursor getComposeUserSuggestions(String query, MatrixCursor cursor, long userId) {
                    String selection;
                    String[] selectionArgs;
                    ArrayList<VineUser> users;
/* 148 */           int id = 0;
/* 149 */           int max = this.mAppController.getTypeaheadMaxCompose();
/* 150 */           boolean haveQuery = !TextUtils.isEmpty(query);
/* 151 */           ContentResolver resolver = getContext().getContentResolver();
/* 152 */           Uri uri = ContentUris.withAppendedId(Vine.UserGroupsView.CONTENT_URI_ALL_FOLLOW, userId);
/* 156 */           if (haveQuery) {
/* 157 */               selection = "username LIKE ?";
/* 158 */               selectionArgs = new String[]{query + "%"};
                    } else {
/* 160 */               selection = null;
/* 161 */               selectionArgs = null;
                    }
/* 164 */           Cursor c = resolver.query(uri, UsersQuery.PROJECTION, selection, selectionArgs, Vine.UserGroupsView.SORT_ORDER_DEFINED_DESCENDING);
/* 167 */           HashSet<Long> prefetchIds = new HashSet<>();
/* 168 */           if (c != null) {
/* 169 */               while (c.moveToNext()) {
/* 170 */                   if (!prefetchIds.contains(Long.valueOf(c.getLong(1)))) {
/* 171 */                       if (id > max) {
                                    break;
                                }
/* 174 */                       MatrixCursor.RowBuilder rowBuilder = cursor.newRow();
                                rowBuilder.add(Integer.valueOf(id));
/* 176 */                       rowBuilder.add(Long.valueOf(c.getLong(1)));
/* 177 */                       rowBuilder.add(c.getString(2));
/* 178 */                       rowBuilder.add(c.getString(3));
/* 179 */                       prefetchIds.add(Long.valueOf(c.getLong(1)));
/* 175 */                       id++;
                            }
                        }
/* 182 */               c.close();
                    }
/* 185 */           if (haveQuery && id <= max && (users = getRealtimeUserSuggestions(query)) != null) {
                        for (VineUser user : users) {
/* 189 */                   if (!prefetchIds.contains(Long.valueOf(user.userId))) {
/* 190 */                       if (id > max) {
                                    break;
                                }
/* 193 */                       MatrixCursor.RowBuilder rowBuilder2 = cursor.newRow();
                                rowBuilder2.add(Integer.valueOf(id));
/* 195 */                       rowBuilder2.add(Long.valueOf(user.userId));
/* 196 */                       rowBuilder2.add(user.username);
/* 197 */                       rowBuilder2.add(user.avatarUrl);
/* 194 */                       id++;
                            }
                        }
                    }
/* 202 */           return cursor;
                }

/* 205 */       private Cursor getComposeTagSuggestions(String query, MatrixCursor cursor) {
                    String selection;
                    String[] selectionArgs;
                    ArrayList<VineTag> tags;
/* 206 */           int id = 0;
/* 207 */           int max = this.mAppController.getTypeaheadMaxCompose();
/* 208 */           boolean haveQuery = !TextUtils.isEmpty(query);
/* 209 */           ContentResolver resolver = getContext().getContentResolver();
/* 210 */           Uri uri = Vine.TagsRecentlyUsed.CONTENT_URI;
/* 214 */           if (haveQuery) {
/* 215 */               selection = "tag_name LIKE ?";
/* 216 */               selectionArgs = new String[]{query + "%"};
                    } else {
/* 218 */               selection = null;
/* 219 */               selectionArgs = null;
                    }
/* 222 */           Cursor c = resolver.query(uri, VineDatabaseSQL.TagsRecentlyUsedQuery.PROJECTION, selection, selectionArgs, Vine.TagsRecentlyUsed.SORT_ORDER_NEWEST_FIRST);
/* 225 */           HashSet<Long> prefetchIds = new HashSet<>();
/* 226 */           if (c != null) {
/* 227 */               while (c.moveToNext()) {
/* 228 */                   if (!prefetchIds.contains(Long.valueOf(c.getLong(1)))) {
/* 229 */                       if (id > max) {
                                    break;
                                }
/* 232 */                       MatrixCursor.RowBuilder rowBuilder = cursor.newRow();
                                rowBuilder.add(Integer.valueOf(id));
/* 234 */                       rowBuilder.add(Long.valueOf(c.getLong(1)));
/* 235 */                       rowBuilder.add(c.getString(2));
/* 236 */                       prefetchIds.add(Long.valueOf(c.getLong(1)));
/* 233 */                       id++;
                            }
                        }
/* 239 */               c.close();
                    }
/* 242 */           if (haveQuery && id <= max && (tags = getRealtimeTagSuggestions(query)) != null) {
                        for (VineTag tag : tags) {
/* 246 */                   if (!prefetchIds.contains(Long.valueOf(tag.tagId))) {
/* 247 */                       if (id > max) {
                                    break;
                                }
/* 250 */                       MatrixCursor.RowBuilder rowBuilder2 = cursor.newRow();
                                rowBuilder2.add(Integer.valueOf(id));
/* 252 */                       rowBuilder2.add(Long.valueOf(tag.tagId));
/* 253 */                       rowBuilder2.add(tag.tagName);
/* 251 */                       id++;
                            }
                        }
                    }
/* 258 */           return cursor;
                }

/* 261 */       public static void addRealtimeUserSuggestions(String query, ArrayList<VineUser> users) {
                    synchronized (sUsersTypeAhead) {
                        try {
/* 263 */                   sUsersTypeAhead.put(query, users);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 267 */       public static ArrayList<VineUser> getRealtimeUserSuggestions(String query) {
                    ArrayList<VineUser> arrayList;
                    synchronized (sUsersTypeAhead) {
                        try {
/* 269 */                   arrayList = sUsersTypeAhead.get(query);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return arrayList;
                }

/* 273 */       public static void clearRealtimeUserSuggestions() {
                    synchronized (sUsersTypeAhead) {
                        try {
/* 275 */                   sUsersTypeAhead.clear();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 279 */       public static void addRealtimeTagSuggestions(String query, ArrayList<VineTag> tags) {
                    synchronized (sTagsTypeAhead) {
                        try {
/* 281 */                   sTagsTypeAhead.put(query, tags);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 285 */       public static ArrayList<VineTag> getRealtimeTagSuggestions(String query) {
                    ArrayList<VineTag> arrayList;
                    synchronized (sTagsTypeAhead) {
                        try {
/* 287 */                   arrayList = sTagsTypeAhead.get(query);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return arrayList;
                }

/* 291 */       public static void clearRealtimeTagSuggestions() {
                    synchronized (sTagsTypeAhead) {
                        try {
/* 293 */                   sTagsTypeAhead.clear();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            }
