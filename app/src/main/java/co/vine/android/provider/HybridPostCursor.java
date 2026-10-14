            package co.vine.android.provider;

            import android.content.Context;
            import android.database.Cursor;
            import android.os.Bundle;
            import co.vine.android.api.VineComment;
            import co.vine.android.api.VineComparatorFactory;
            import co.vine.android.api.VineLike;
            import co.vine.android.api.VinePost;
            import co.vine.android.client.AppController;
            import co.vine.android.util.BuildUtil;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.LongSparseArray;
            import co.vine.android.util.analytics.FlurryUtils;
            import java.util.ArrayList;
            import java.util.Collections;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 46 */    public class HybridPostCursor extends GroupCursor<VinePost> {
                public static final String EXTRA_INCLUDE_LIST = "include_list";
                public static final String EXTRA_IS_LAST = "is_last";
                public static final String EXTRA_POST = "post";
                public static final String EXTRA_POSTS = "extra_posts";
                private Context mContext;
                private boolean mIncludeList;

/* 47 */        public HybridPostCursor(Cursor cursor) {
/* 48 */            this(cursor, 20);
                }

/* 51 */        public HybridPostCursor(Cursor cursor, int limit) {
/* 52 */            super(cursor, limit);
/* 43 */            this.mIncludeList = false;
/* 53 */            if (BuildUtil.isLogsOn() && BuildUtil.isExplore() && getClass() == HybridPostCursor.class) {
/* 54 */                throw new IllegalStateException("Constructed HybridPostCursor with explore app. Use HybridPostCursorExplore.");
                    }
                }

/* 59 */        public HybridPostCursor(ArrayList<VinePost> posts, int limit) {
/* 60 */            super(null);
/* 43 */            this.mIncludeList = false;
/* 61 */            if (BuildUtil.isLogsOn() && BuildUtil.isExplore() && getClass() == HybridPostCursor.class) {
/* 62 */                throw new IllegalStateException("Constructed HybridPostCursor with explore app. Use HybridPostCursorExplore.");
                    }
/* 66 */            this.mList = posts;
/* 67 */            this.mLimit = limit;
                }

/* 70 */        public void setContext(Context context) {
/* 71 */            if (context != null) {
/* 72 */                this.mContext = context.getApplicationContext();
                    } else {
/* 74 */                this.mContext = null;
                    }
                }

/* 78 */        public void prepare(Context context) {
/* 79 */            setContext(context);
/* 80 */            super.prepare();
/* 81 */            setContext(null);
                }

                /* JADX WARN: Type inference incomplete: some casts might be missing */
                @Override // co.vine.android.provider.GroupCursor
/* 85 */        public void onPrepare() {
                    boolean zMoveToNext;
/* 86 */            Cursor cursor = this.mCursor;
/* 92 */            int i = 0;
/* 94 */            if (cursor != null && cursor.moveToFirst()) {
/* 95 */                long j = 0;
/* 96 */                VinePost vinePostFrom = null;
/* 97 */                this.mList = new ArrayList();
                        do {
/* 99 */                    long j2 = cursor.getLong(1);
                            if (j2 != j) {
/* 104 */                       if (vinePostFrom != null) {
/* 105 */                           this.mList.add(vinePostFrom);
/* 106 */                           i++;
                                }
/* 108 */                       j = j2;
/* 109 */                       vinePostFrom = VinePost.from(cursor);
                            }
/* 112 */                   if (vinePostFrom != null) {
/* 114 */                       long j3 = cursor.getLong(34);
/* 115 */                       long j4 = cursor.getLong(27);
                                if (j3 > 0) {
/* 117 */                           VineComment vineCommentFrom = VineComment.from(cursor);
/* 118 */                           if (vinePostFrom.comments.items == null) {
/* 119 */                               vinePostFrom.comments.items = new ArrayList<>();
                                    }
/* 121 */                           vinePostFrom.comments.items.add(vineCommentFrom);
                                } else if (j4 > 0) {
/* 123 */                           VineLike vineLikeFrom = VineLike.from(cursor);
/* 124 */                           if (vinePostFrom.likes.items == null) {
/* 125 */                               vinePostFrom.likes.items = new ArrayList<>();
                                    }
/* 127 */                           vinePostFrom.likes.items.add(vineLikeFrom);
                                }
                            }
                            try {
/* 131 */                       zMoveToNext = cursor.moveToNext();
                            } catch (IllegalStateException e) {
/* 148 */                       CrashUtil.log("Failed to get cursor's row id. total: {}, current: {}, limit: {}, postId: {}.", Integer.valueOf(cursor.getCount()), Integer.valueOf(i), Integer.valueOf(this.mLimit), Long.valueOf(j2));
/* 151 */                       if (this.mContext != null) {
/* 152 */                           AppController.getInstance(this.mContext).clearImageCacheFromMemory();
                                }
/* 155 */                       System.gc();
/* 156 */                       zMoveToNext = cursor.moveToNext();
                            }
/* 158 */                   if (!zMoveToNext) {
                                break;
                            }
                        } while (i < this.mLimit);
/* 159 */               if (vinePostFrom != null) {
/* 160 */                   this.mList.add(vinePostFrom);
/* 165 */                   return;
                        }
/* 165 */               return;
                    }
/* 163 */           this.mList = null;
                }

                @Override // android.database.CursorWrapper, android.database.Cursor
/* 178 */       public Bundle getExtras() {
                    Bundle superExtras;
                    Bundle extras;
                    try {
/* 179 */               superExtras = super.getExtras();
                    } catch (NullPointerException e) {
/* 181 */               superExtras = null;
                    }
/* 184 */           if (superExtras != null) {
/* 185 */               extras = new Bundle(superExtras);
                    } else {
/* 187 */               extras = new Bundle();
                    }
/* 189 */           if (this.mIncludeList) {
/* 190 */               extras.putParcelableArrayList(EXTRA_POSTS, (ArrayList) this.mList);
                    } else {
/* 192 */               VinePost post = getItem(this.mCursorPosition);
/* 193 */               if (post != null) {
/* 194 */                   extras.putParcelable("post", post);
/* 195 */                   extras.putBoolean("is_last", post.isLast);
                        }
                    }
/* 198 */           return extras;
                }

                @Override // android.database.CursorWrapper, android.database.Cursor
/* 202 */       public Bundle respond(Bundle extras) {
/* 203 */           this.mIncludeList = extras.getBoolean(EXTRA_INCLUDE_LIST, false);
/* 204 */           return extras;
                }

                /* JADX WARN: Code duplicated, block: B:14:0x0019  */
                /* JADX WARN: Code duplicated, block: B:7:0x000b  */
/* 210 */       public static synchronized ArrayList<VinePost> mergePosts(ArrayList<VinePost> oldPosts, ArrayList<VinePost> newPosts, VineComparatorFactory.VineComparator<VinePost> comparator, boolean enableTracking) {
                    if (oldPosts != null) {
                        try {
                            if (oldPosts.isEmpty()) {
/* 212 */                       if (newPosts == null && !newPosts.isEmpty()) {
/* 213 */                           sort(newPosts, comparator);
                                    oldPosts = newPosts;
                                } else {
/* 216 */                           oldPosts = null;
                                }
                            } else {
/* 221 */                       LongSparseArray<Integer> existingPosts = new LongSparseArray<>(oldPosts.size());
/* 222 */                       int count = 0;
                                for (VinePost post : oldPosts) {
/* 224 */                           existingPosts.put(post.postId, Integer.valueOf(count));
/* 225 */                           count++;
                                }
/* 228 */                       int count2 = 0;
                                for (VinePost newPost : newPosts) {
/* 231 */                           Integer updateThisPostIndex = existingPosts.get(newPost.postId);
/* 232 */                           if (updateThisPostIndex != null) {
/* 233 */                               oldPosts.set(updateThisPostIndex.intValue(), newPost);
                                    } else {
/* 235 */                               oldPosts.add(newPost);
/* 236 */                               count2++;
                                    }
                                }
/* 240 */                       if (enableTracking) {
/* 241 */                           FlurryUtils.trackGraphTimelineRefreshNewCount(count2);
                                }
/* 244 */                       sort(oldPosts, comparator);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    } else if (newPosts == null) {
/* 216 */               oldPosts = null;
                    } else {
/* 216 */               oldPosts = null;
                    }
                    return oldPosts;
                }

                @Override // android.database.CursorWrapper, android.database.Cursor
/* 249 */       public int getColumnIndexOrThrow(String columnName) throws IllegalArgumentException {
/* 250 */           int columnIndex = VineDatabaseSQL.PostCommentsLikesQuery.LOOKUP.get(columnName).intValue();
                    return columnIndex >= 0 ? columnIndex : super.getColumnIndexOrThrow(columnName);
                }

/* 258 */       private static void sort(ArrayList<VinePost> posts, VineComparatorFactory.VineComparator<VinePost> comparator) {
/* 259 */           if (comparator == null) {
/* 260 */               Collections.sort(posts, VineComparatorFactory.get(-1));
                    } else {
/* 262 */               Collections.sort(posts, comparator);
                    }
                }

/* 266 */       public void markLast() {
/* 267 */           if (this.mList != null) {
/* 268 */               ((VinePost) this.mList.get(this.mList.size() - 1)).isLast = true;
                    }
                }
            }
