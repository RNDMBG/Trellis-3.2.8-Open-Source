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
            
            
    public class HybridPostCursor extends GroupCursor<VinePost> {
                public static final String EXTRA_INCLUDE_LIST = "include_list";
                public static final String EXTRA_IS_LAST = "is_last";
                public static final String EXTRA_POST = "post";
                public static final String EXTRA_POSTS = "extra_posts";
                private Context mContext;
                private boolean mIncludeList;

        public HybridPostCursor(Cursor cursor) {
            this(cursor, 20);
                }

        public HybridPostCursor(Cursor cursor, int limit) {
            super(cursor, limit);
            this.mIncludeList = false;
            if (BuildUtil.isLogsOn() && BuildUtil.isExplore() && getClass() == HybridPostCursor.class) {
                throw new IllegalStateException("Constructed HybridPostCursor with explore app. Use HybridPostCursorExplore.");
                    }
                }

        public HybridPostCursor(ArrayList<VinePost> posts, int limit) {
            super(null);
            this.mIncludeList = false;
            if (BuildUtil.isLogsOn() && BuildUtil.isExplore() && getClass() == HybridPostCursor.class) {
                throw new IllegalStateException("Constructed HybridPostCursor with explore app. Use HybridPostCursorExplore.");
                    }
            this.mList = posts;
            this.mLimit = limit;
                }

        public void setContext(Context context) {
            if (context != null) {
                this.mContext = context.getApplicationContext();
                    } else {
                this.mContext = null;
                    }
                }

        public void prepare(Context context) {
            setContext(context);
            super.prepare();
            setContext(null);
                }

                
                @Override 
        public void onPrepare() {
                    boolean zMoveToNext;
            Cursor cursor = this.mCursor;
            int i = 0;
            if (cursor != null && cursor.moveToFirst()) {
                long j = 0;
                VinePost vinePostFrom = null;
                this.mList = new ArrayList();
                        do {
                    long j2 = cursor.getLong(1);
                            if (j2 != j) {
                       if (vinePostFrom != null) {
                           this.mList.add(vinePostFrom);
                           i++;
                                }
                       j = j2;
                       vinePostFrom = VinePost.from(cursor);
                            }
                   if (vinePostFrom != null) {
                       long j3 = cursor.getLong(34);
                       long j4 = cursor.getLong(27);
                                if (j3 > 0) {
                           VineComment vineCommentFrom = VineComment.from(cursor);
                           if (vinePostFrom.comments.items == null) {
                               vinePostFrom.comments.items = new ArrayList<>();
                                    }
                           vinePostFrom.comments.items.add(vineCommentFrom);
                                } else if (j4 > 0) {
                           VineLike vineLikeFrom = VineLike.from(cursor);
                           if (vinePostFrom.likes.items == null) {
                               vinePostFrom.likes.items = new ArrayList<>();
                                    }
                           vinePostFrom.likes.items.add(vineLikeFrom);
                                }
                            }
                            try {
                       zMoveToNext = cursor.moveToNext();
                            } catch (IllegalStateException e) {
                       CrashUtil.log("Failed to get cursor's row id. total: {}, current: {}, limit: {}, postId: {}.", Integer.valueOf(cursor.getCount()), Integer.valueOf(i), Integer.valueOf(this.mLimit), Long.valueOf(j2));
                       if (this.mContext != null) {
                           AppController.getInstance(this.mContext).clearImageCacheFromMemory();
                                }
                       System.gc();
                       zMoveToNext = cursor.moveToNext();
                            }
                   if (!zMoveToNext) {
                                break;
                            }
                        } while (i < this.mLimit);
               if (vinePostFrom != null) {
                   this.mList.add(vinePostFrom);
                   return;
                        }
               return;
                    }
           this.mList = null;
                }

                @Override 
       public Bundle getExtras() {
                    Bundle superExtras;
                    Bundle extras;
                    try {
               superExtras = super.getExtras();
                    } catch (NullPointerException e) {
               superExtras = null;
                    }
           if (superExtras != null) {
               extras = new Bundle(superExtras);
                    } else {
               extras = new Bundle();
                    }
           if (this.mIncludeList) {
               extras.putParcelableArrayList(EXTRA_POSTS, (ArrayList) this.mList);
                    } else {
               VinePost post = getItem(this.mCursorPosition);
               if (post != null) {
                   extras.putParcelable("post", post);
                   extras.putBoolean("is_last", post.isLast);
                        }
                    }
           return extras;
                }

                @Override 
       public Bundle respond(Bundle extras) {
           this.mIncludeList = extras.getBoolean(EXTRA_INCLUDE_LIST, false);
           return extras;
                }

                
                
       public static synchronized ArrayList<VinePost> mergePosts(ArrayList<VinePost> oldPosts, ArrayList<VinePost> newPosts, VineComparatorFactory.VineComparator<VinePost> comparator, boolean enableTracking) {
                    if (oldPosts != null) {
                        try {
                            if (oldPosts.isEmpty()) {
                       if (newPosts == null && !newPosts.isEmpty()) {
                           sort(newPosts, comparator);
                                    oldPosts = newPosts;
                                } else {
                           oldPosts = null;
                                }
                            } else {
                       LongSparseArray<Integer> existingPosts = new LongSparseArray<>(oldPosts.size());
                       int count = 0;
                                for (VinePost post : oldPosts) {
                           existingPosts.put(post.postId, Integer.valueOf(count));
                           count++;
                                }
                       int count2 = 0;
                                for (VinePost newPost : newPosts) {
                           Integer updateThisPostIndex = existingPosts.get(newPost.postId);
                           if (updateThisPostIndex != null) {
                               oldPosts.set(updateThisPostIndex.intValue(), newPost);
                                    } else {
                               oldPosts.add(newPost);
                               count2++;
                                    }
                                }
                       if (enableTracking) {
                           FlurryUtils.trackGraphTimelineRefreshNewCount(count2);
                                }
                       sort(oldPosts, comparator);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    } else if (newPosts == null) {
               oldPosts = null;
                    } else {
               oldPosts = null;
                    }
                    return oldPosts;
                }

                @Override 
       public int getColumnIndexOrThrow(String columnName) throws IllegalArgumentException {
           int columnIndex = VineDatabaseSQL.PostCommentsLikesQuery.LOOKUP.get(columnName).intValue();
                    return columnIndex >= 0 ? columnIndex : super.getColumnIndexOrThrow(columnName);
                }

       private static void sort(ArrayList<VinePost> posts, VineComparatorFactory.VineComparator<VinePost> comparator) {
           if (comparator == null) {
               Collections.sort(posts, VineComparatorFactory.get(-1));
                    } else {
               Collections.sort(posts, comparator);
                    }
                }

       public void markLast() {
           if (this.mList != null) {
               ((VinePost) this.mList.get(this.mList.size() - 1)).isLast = true;
                    }
                }
            }
