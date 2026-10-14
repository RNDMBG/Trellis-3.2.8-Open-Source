            package co.vine.android;

            import android.content.Context;
            import android.content.SharedPreferences;
            import android.graphics.Bitmap;
            import android.graphics.ColorFilter;
            import android.support.annotation.Nullable;
            import android.text.Editable;
            import android.text.SpannableStringBuilder;
            import android.text.TextUtils;
            import android.text.method.LinkMovementMethod;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.BaseAdapter;
            import android.widget.ImageView;
            import android.widget.RelativeLayout;
            import android.widget.TextView;
            import co.vine.android.api.VineComment;
            import co.vine.android.api.VineEntity;
            import co.vine.android.cache.image.ImageKey;
            import co.vine.android.cache.image.UrlImage;
            import co.vine.android.client.AppController;
            import co.vine.android.drawable.RecyclableBitmapDrawable;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.Util;
            import co.vine.android.util.ViewUtil;
            import co.vine.android.widget.SpanClickListener;
            import co.vine.android.widget.StyledClickableSpan;
            import co.vine.android.widget.Typefaces;
            import co.vine.android.widget.TypefacesSpan;
            import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
            import java.lang.ref.WeakReference;
            import java.util.ArrayList;
            import java.util.HashMap;
            import java.util.HashSet;
            import java.util.Iterator;
            import java.util.LinkedList;
            import java.util.Set;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 69 */    public class CommentsAdapter extends BaseAdapter {
                private static final String SPAM_COMMENTS_PREFS_NAME = "spam_comment_ids";
                private static final int SPAN_FLAGS = 33;
                private AppController mAppController;
                private TypefacesSpan mBoldSpan;
                private Context mContext;
                SharedPreferences.Editor mEditor;
                final SpanClickListener mListener;
                private long mPostId;
                private int mVineGreen;
                private HashMap<Long, Integer> mIdToPositionMap = new HashMap<>();
                private ArrayList<VineComment> mComments = new ArrayList<>();
                private final HashSet<String> mSpamCommentIds = new HashSet<>();
                final ArrayList<WeakReference<CommentViewHolder>> mViewHolders = new ArrayList<>();
                private HashMap<String, Editable> mCommentSbs = new HashMap<>();

/* 70 */        public CommentsAdapter(Context context, long postId, AppController appController, SpanClickListener listener) {
/* 72 */            this.mContext = context;
/* 73 */            this.mPostId = postId;
/* 74 */            loadSpamCommentIds(context, postId);
/* 75 */            this.mBoldSpan = new TypefacesSpan(null, Typefaces.get(context).getContentTypeface(1, 3));
/* 77 */            this.mAppController = appController;
/* 78 */            this.mListener = listener;
/* 79 */            this.mVineGreen = context.getResources().getColor(C0257R.color.vine_green);
                }

/* 82 */        private void loadSpamCommentIds(Context context, long postId) {
/* 83 */            SharedPreferences sp = Util.getSharedPrefs(context, SPAM_COMMENTS_PREFS_NAME);
/* 85 */            if (sp != null) {
/* 88 */                Set<String> spamCommentIds = sp.getStringSet(String.valueOf(postId), new HashSet());
/* 90 */                this.mEditor = sp.edit();
/* 91 */                if (spamCommentIds != null && spamCommentIds.size() != 0) {
/* 92 */                    this.mSpamCommentIds.addAll(spamCommentIds);
                        }
                    }
                }

/* 95 */        public View newView() {
/* 96 */            RelativeLayout layout = (RelativeLayout) LayoutInflater.from(this.mContext).inflate(C0257R.layout.comment_row_view, (ViewGroup) null, false);
/* 98 */            CommentViewHolder h = new CommentViewHolder();
/* 99 */            h.profileImage = (ImageView) layout.findViewById(C0257R.id.profile_image);
/* 100 */           h.content = (TextView) layout.findViewById(C0257R.id.content);
/* 101 */           h.content.setMovementMethod(LinkMovementMethod.getInstance());
/* 102 */           h.timestamp = (TextView) layout.findViewById(C0257R.id.timestamp);
/* 103 */           layout.setTag(h);
/* 104 */           this.mViewHolders.add(new WeakReference<>(h));
/* 105 */           return layout;
                }

/* 108 */       public void bindView(View view, VineComment comment) {
/* 109 */           CommentViewHolder h = (CommentViewHolder) view.getTag();
/* 110 */           String username = comment.username;
/* 111 */           String commentString = comment.comment;
/* 113 */           ArrayList<VineEntity> entities = comment.entities;
/* 114 */           if (username == null) {
/* 115 */               username = "";
/* 116 */               CrashUtil.logException(new VineLoggingException(), "Username is null for " + comment.commentId, new Object[0]);
                    }
/* 120 */           h.content.setMovementMethod(LinkMovementMethod.getInstance());
/* 122 */           h.timestamp.setText(Util.getRelativeTimeString(this.mContext, comment.timestamp, false));
/* 124 */           h.commentId = String.valueOf(comment.commentId);
/* 125 */           h.username = username;
/* 126 */           h.userId = comment.userId;
/* 128 */           Editable commentSb = this.mCommentSbs.get(h.commentId);
/* 130 */           if (commentSb == null) {
/* 131 */               commentSb = new SpannableStringBuilder(username).append((CharSequence) MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append((CharSequence) commentString);
/* 132 */               this.mCommentSbs.put(h.commentId, commentSb);
/* 135 */               Util.safeSetSpan(commentSb, this.mBoldSpan, 0, username.length(), 33);
/* 136 */               StyledClickableSpan styledClickableSpan = new StyledClickableSpan(1, Long.valueOf(h.userId), this.mListener);
/* 139 */               styledClickableSpan.setColor(this.mVineGreen);
/* 140 */               Util.safeSetSpan(commentSb, styledClickableSpan, 0, username.length(), 33);
/* 143 */               if (entities != null) {
                            try {
/* 145 */                       Util.adjustEntities(entities, commentSb, username.length() + 1, false);
                                for (VineEntity entity : entities) {
/* 147 */                           if (entity.isUserType()) {
/* 148 */                               StyledClickableSpan usernameSpan = new StyledClickableSpan(1, Long.valueOf(entity.f96id), this.mListener);
/* 151 */                               usernameSpan.setColor(this.mVineGreen);
/* 152 */                               Util.safeSetSpan(commentSb, usernameSpan, entity.start, entity.end, 33);
                                    } else if (entity.isTagType()) {
/* 154 */                               StyledClickableSpan usernameSpan2 = new StyledClickableSpan(3, entity.title, this.mListener);
/* 157 */                               usernameSpan2.setColor(this.mVineGreen);
/* 158 */                               Util.safeSetSpan(commentSb, usernameSpan2, entity.start, entity.end, 33);
                                    }
                                }
                            } catch (IndexOutOfBoundsException e) {
/* 162 */                       CrashUtil.logException(e);
                            }
                        }
                    }
/* 167 */           h.content.setText(commentSb);
/* 169 */           String url = comment.avatarUrl;
                    if (TextUtils.isEmpty(url) && comment.user != null) {
                        url = comment.user.avatarUrl;
                    }
/* 170 */           if (TextUtils.isEmpty(url) || Util.isDefaultAvatarUrl(url)) {
/* 171 */               Util.safeSetDefaultAvatar(h.profileImage, Util.ProfileImageSize.MEDIUM, (-16777216) | this.mVineGreen);
/* 172 */               ViewUtil.setBackground(h.profileImage, null);
/* 185 */               return;
                    }
/* 174 */           h.profileImage.setColorFilter((ColorFilter) null);
/* 175 */           ImageKey key = new ImageKey(url, true);
/* 176 */           h.imageKey = key;
/* 177 */           Bitmap profileImage = this.mAppController.getPhotoBitmap(key);
/* 178 */           if (profileImage != null) {
/* 179 */               h.profileImage.setImageDrawable(new RecyclableBitmapDrawable(this.mContext.getResources(), profileImage));
                    } else {
/* 181 */               ViewUtil.setBackground(h.profileImage, this.mContext.getResources().getDrawable(C0257R.drawable.circle_shape_light));
/* 182 */               h.profileImage.setImageDrawable(null);
                    }
                }

/* 187 */       public void updateProfileImages(HashMap<ImageKey, UrlImage> images) {
                    if (images == null || images.isEmpty()) {
                        return;
                    }
                    UrlImage image;
/* 188 */           ArrayList<WeakReference<CommentViewHolder>> toRemove = new ArrayList<>();
                    for (int i = this.mViewHolders.size() - 1; i >= 0; i--) {
/* 191 */               WeakReference<CommentViewHolder> wf = this.mViewHolders.get(i);
/* 192 */               CommentViewHolder vh = wf.get();
/* 193 */               if (vh == null) {
/* 194 */                   toRemove.add(wf);
                        } else if (vh.imageKey != null && (image = images.get(vh.imageKey)) != null && image.bitmap != null) {
/* 198 */                   vh.profileImage.setImageDrawable(new RecyclableBitmapDrawable(this.mContext.getResources(), image.bitmap));
                        }
                    }
/* 202 */           Iterator<WeakReference<CommentViewHolder>> it = toRemove.iterator();
                    while (it.hasNext()) {
/* 203 */               this.mViewHolders.remove(it.next());
                    }
                }

                @Override // android.widget.Adapter
/* 208 */       public int getCount() {
/* 209 */           return this.mComments.size();
                }

                @Override // android.widget.Adapter
/* 213 */       public Object getItem(int position) {
/* 214 */           return this.mComments.get(position);
                }

                @Override // android.widget.Adapter
/* 218 */       public long getItemId(int pos) {
/* 219 */           return this.mComments.get(pos).commentId;
                }

                @Override // android.widget.Adapter
                @Nullable
/* 224 */       public View getView(int position, View convertView, ViewGroup parent) {
/* 225 */           if (convertView == null) {
/* 226 */               convertView = newView();
                    }
/* 228 */           bindView(convertView, (VineComment) getItem(position));
/* 229 */           return convertView;
                }

/* 232 */       public int getPositionForId(long id) {
/* 233 */           if (this.mIdToPositionMap == null || this.mIdToPositionMap.get(Long.valueOf(id)) == null) {
                        return -1;
                    }
/* 234 */           return this.mIdToPositionMap.get(Long.valueOf(id)).intValue();
                }

/* 240 */       public void mergeComments(ArrayList<VineComment> comments) {
                    if (comments == null || comments.isEmpty()) {
                        return;
                    }
/* 241 */           LinkedList<VineComment> filtered = new LinkedList<>();
                    for (VineComment comment : comments) {
/* 243 */               if (!this.mSpamCommentIds.contains(String.valueOf(comment.commentId))) {
/* 244 */                   filtered.add(comment);
                        }
                    }
/* 248 */           this.mComments.addAll(0, filtered);
/* 249 */           rebuildIdToPositionMap();
/* 250 */           notifyDataSetChanged();
                }

/* 253 */       private void rebuildIdToPositionMap() {
/* 254 */           this.mIdToPositionMap.clear();
                    for (int i = 0; i < this.mComments.size(); i++) {
/* 257 */               VineComment comment = this.mComments.get(i);
/* 258 */               this.mIdToPositionMap.put(Long.valueOf(comment.commentId), Integer.valueOf(i));
                    }
                }

/* 263 */       public void addMyComment(VineComment commentToMerge) {
/* 264 */           this.mComments.add(commentToMerge);
/* 265 */           rebuildIdToPositionMap();
/* 266 */           notifyDataSetChanged();
                }

/* 269 */       public void deleteComment(long commentId) {
/* 270 */           int position = getPositionForId(commentId);
/* 271 */           if (position >= 0) {
/* 272 */               this.mComments.remove(position);
/* 273 */               rebuildIdToPositionMap();
/* 274 */               notifyDataSetChanged();
                    }
                }

/* 278 */       public void spamComment(long commentId) {
/* 279 */           int position = getPositionForId(commentId);
/* 280 */           if (position >= 0) {
/* 281 */               this.mComments.remove(position);
/* 282 */               this.mSpamCommentIds.add(String.valueOf(commentId));
/* 283 */               this.mEditor.putStringSet(String.valueOf(this.mPostId), this.mSpamCommentIds).apply();
/* 284 */               rebuildIdToPositionMap();
/* 285 */               notifyDataSetChanged();
                    }
                }
            }
