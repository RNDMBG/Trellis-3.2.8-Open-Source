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
            
            
    public class CommentsAdapter extends BaseAdapter {
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

        public CommentsAdapter(Context context, long postId, AppController appController, SpanClickListener listener) {
            this.mContext = context;
            this.mPostId = postId;
            loadSpamCommentIds(context, postId);
            this.mBoldSpan = new TypefacesSpan(null, Typefaces.get(context).getContentTypeface(1, 3));
            this.mAppController = appController;
            this.mListener = listener;
            this.mVineGreen = context.getResources().getColor(R.color.vine_green);
                }

        private void loadSpamCommentIds(Context context, long postId) {
            SharedPreferences sp = Util.getSharedPrefs(context, SPAM_COMMENTS_PREFS_NAME);
            if (sp != null) {
                Set<String> spamCommentIds = sp.getStringSet(String.valueOf(postId), new HashSet());
                this.mEditor = sp.edit();
                if (spamCommentIds != null && spamCommentIds.size() != 0) {
                    this.mSpamCommentIds.addAll(spamCommentIds);
                        }
                    }
                }

        public View newView() {
            RelativeLayout layout = (RelativeLayout) LayoutInflater.from(this.mContext).inflate(R.layout.comment_row_view, (ViewGroup) null, false);
            CommentViewHolder h = new CommentViewHolder();
            h.profileImage = (ImageView) layout.findViewById(R.id.profile_image);
           h.content = (TextView) layout.findViewById(R.id.content);
           h.content.setMovementMethod(LinkMovementMethod.getInstance());
           h.timestamp = (TextView) layout.findViewById(R.id.timestamp);
           layout.setTag(h);
           this.mViewHolders.add(new WeakReference<>(h));
           return layout;
                }

       public void bindView(View view, VineComment comment) {
           CommentViewHolder h = (CommentViewHolder) view.getTag();
           String username = comment.username;
           String commentString = comment.comment;
           ArrayList<VineEntity> entities = comment.entities;
           if (username == null) {
               username = "";
               CrashUtil.logException(new VineLoggingException(), "Username is null for " + comment.commentId, new Object[0]);
                    }
           h.content.setMovementMethod(LinkMovementMethod.getInstance());
           h.timestamp.setText(Util.getRelativeTimeString(this.mContext, comment.timestamp, false));
           h.commentId = String.valueOf(comment.commentId);
           h.username = username;
           h.userId = comment.userId;
           Editable commentSb = this.mCommentSbs.get(h.commentId);
           if (commentSb == null) {
               commentSb = new SpannableStringBuilder(username).append((CharSequence) MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append((CharSequence) commentString);
               this.mCommentSbs.put(h.commentId, commentSb);
               Util.safeSetSpan(commentSb, this.mBoldSpan, 0, username.length(), 33);
               StyledClickableSpan styledClickableSpan = new StyledClickableSpan(1, Long.valueOf(h.userId), this.mListener);
               styledClickableSpan.setColor(this.mVineGreen);
               Util.safeSetSpan(commentSb, styledClickableSpan, 0, username.length(), 33);
               if (entities != null) {
                            try {
                       Util.adjustEntities(entities, commentSb, username.length() + 1, false);
                                for (VineEntity entity : entities) {
                           if (entity.isUserType()) {
                               StyledClickableSpan usernameSpan = new StyledClickableSpan(1, Long.valueOf(entity.f96id), this.mListener);
                               usernameSpan.setColor(this.mVineGreen);
                               Util.safeSetSpan(commentSb, usernameSpan, entity.start, entity.end, 33);
                                    } else if (entity.isTagType()) {
                               StyledClickableSpan usernameSpan2 = new StyledClickableSpan(3, entity.title, this.mListener);
                               usernameSpan2.setColor(this.mVineGreen);
                               Util.safeSetSpan(commentSb, usernameSpan2, entity.start, entity.end, 33);
                                    }
                                }
                            } catch (IndexOutOfBoundsException e) {
                       CrashUtil.logException(e);
                            }
                        }
                    }
           h.content.setText(commentSb);
           String url = comment.avatarUrl;
                    if (TextUtils.isEmpty(url) && comment.user != null) {
                        url = comment.user.avatarUrl;
                    }
           if (TextUtils.isEmpty(url) || Util.isDefaultAvatarUrl(url)) {
               Util.safeSetDefaultAvatar(h.profileImage, Util.ProfileImageSize.MEDIUM, (-16777216) | this.mVineGreen);
               ViewUtil.setBackground(h.profileImage, null);
               return;
                    }
           h.profileImage.setColorFilter((ColorFilter) null);
           ImageKey key = new ImageKey(url, true);
           h.imageKey = key;
           Bitmap profileImage = this.mAppController.getPhotoBitmap(key);
           if (profileImage != null) {
               h.profileImage.setImageDrawable(new RecyclableBitmapDrawable(this.mContext.getResources(), profileImage));
                    } else {
               ViewUtil.setBackground(h.profileImage, this.mContext.getResources().getDrawable(R.drawable.circle_shape_light));
               h.profileImage.setImageDrawable(null);
                    }
                }

       public void updateProfileImages(HashMap<ImageKey, UrlImage> images) {
                    if (images == null || images.isEmpty()) {
                        return;
                    }
                    UrlImage image;
           ArrayList<WeakReference<CommentViewHolder>> toRemove = new ArrayList<>();
                    for (int i = this.mViewHolders.size() - 1; i >= 0; i--) {
               WeakReference<CommentViewHolder> wf = this.mViewHolders.get(i);
               CommentViewHolder vh = wf.get();
               if (vh == null) {
                   toRemove.add(wf);
                        } else if (vh.imageKey != null && (image = images.get(vh.imageKey)) != null && image.bitmap != null) {
                   vh.profileImage.setImageDrawable(new RecyclableBitmapDrawable(this.mContext.getResources(), image.bitmap));
                        }
                    }
           Iterator<WeakReference<CommentViewHolder>> it = toRemove.iterator();
                    while (it.hasNext()) {
               this.mViewHolders.remove(it.next());
                    }
                }

                @Override 
       public int getCount() {
           return this.mComments.size();
                }

                @Override 
       public Object getItem(int position) {
           return this.mComments.get(position);
                }

                @Override 
       public long getItemId(int pos) {
           return this.mComments.get(pos).commentId;
                }

                @Override 
                @Nullable
       public View getView(int position, View convertView, ViewGroup parent) {
           if (convertView == null) {
               convertView = newView();
                    }
           bindView(convertView, (VineComment) getItem(position));
           return convertView;
                }

       public int getPositionForId(long id) {
           if (this.mIdToPositionMap == null || this.mIdToPositionMap.get(Long.valueOf(id)) == null) {
                        return -1;
                    }
           return this.mIdToPositionMap.get(Long.valueOf(id)).intValue();
                }

       public void mergeComments(ArrayList<VineComment> comments) {
                    if (comments == null || comments.isEmpty()) {
                        return;
                    }
           LinkedList<VineComment> filtered = new LinkedList<>();
                    for (VineComment comment : comments) {
               if (!this.mSpamCommentIds.contains(String.valueOf(comment.commentId))) {
                   filtered.add(comment);
                        }
                    }
           this.mComments.addAll(0, filtered);
           rebuildIdToPositionMap();
           notifyDataSetChanged();
                }

       private void rebuildIdToPositionMap() {
           this.mIdToPositionMap.clear();
                    for (int i = 0; i < this.mComments.size(); i++) {
               VineComment comment = this.mComments.get(i);
               this.mIdToPositionMap.put(Long.valueOf(comment.commentId), Integer.valueOf(i));
                    }
                }

       public void addMyComment(VineComment commentToMerge) {
           this.mComments.add(commentToMerge);
           rebuildIdToPositionMap();
           notifyDataSetChanged();
                }

       public void deleteComment(long commentId) {
           int position = getPositionForId(commentId);
           if (position >= 0) {
               this.mComments.remove(position);
               rebuildIdToPositionMap();
               notifyDataSetChanged();
                    }
                }

       public void spamComment(long commentId) {
           int position = getPositionForId(commentId);
           if (position >= 0) {
               this.mComments.remove(position);
               this.mSpamCommentIds.add(String.valueOf(commentId));
               this.mEditor.putStringSet(String.valueOf(this.mPostId), this.mSpamCommentIds).apply();
               rebuildIdToPositionMap();
               notifyDataSetChanged();
                    }
                }
            }
