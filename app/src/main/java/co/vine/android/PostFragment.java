            package co.vine.android;

            import android.accounts.Account;
            import android.accounts.AccountManager;
            import android.annotation.SuppressLint;
            import android.app.Activity;
            import android.app.NotificationManager;
            import android.content.Intent;
            import android.graphics.Bitmap;
            import android.graphics.BitmapFactory;
            import android.os.Bundle;
            import android.os.Handler;
            import android.support.v4.app.FragmentActivity;
            import android.support.v4.widget.CursorAdapter;
            import android.text.Editable;
            import android.text.TextUtils;
            import android.view.LayoutInflater;
            import android.view.Menu;
            import android.view.MenuInflater;
            import android.view.MenuItem;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.CompoundButton;
            import android.widget.Filter;
            import android.widget.Filterable;
            import android.widget.ImageView;
            import android.widget.TextView;
            import android.widget.Toast;
            import co.vine.android.api.PostInfo;
            import co.vine.android.api.VineEntity;
            import co.vine.android.api.VineTag;
            import co.vine.android.api.VineUpload;
            import co.vine.android.api.VineUser;
            import co.vine.android.cache.image.ImageKey;
            import co.vine.android.cache.image.UrlImage;
            import co.vine.android.client.AppController;
            import co.vine.android.client.AppSessionListener;
            import co.vine.android.client.TwitterVineApp;
            import co.vine.android.client.VineAccountHelper;
            import co.vine.android.provider.VineSuggestionsProvider;
            import co.vine.android.recorder.RecordConfigUtils;
            import co.vine.android.recorder.RecordSessionManager;
            import co.vine.android.recorder.RecordSessionVersion;
            import co.vine.android.service.VineUploadService;
            import co.vine.android.util.BuildUtil;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.MediaUtil;
            import co.vine.android.util.SystemUtil;
            import co.vine.android.util.UploadManager;
            import co.vine.android.util.Util;
            import co.vine.android.util.analytics.FlurryUtils;
            import co.vine.android.views.SwitchInterface;
            import co.vine.android.widget.PopupEditText;
            import com.edisonwang.android.slog.SLog;
            import com.facebook.Session;
            import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
            import com.googlecode.javacv.cpp.avformat;
            import com.twitter.android.sdk.Twitter;
            import java.io.File;
            import java.io.IOException;
            import java.util.ArrayList;
            import java.util.HashMap;
            import org.apache.commons.io.FilenameUtils;
            
            
    public class PostFragment extends BaseControllerFragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener, Filterable, PopupEditText.PopupEditTextListener, View.OnLongClickListener {
                private static final String AG_FROM_SONY = "f_s";
                private static final String AG_IS_RETRY = "is_retry";
                private static final String AG_PREVIEW_PATH = "preview_path";
                private static final String AG_THUMBNAIL_PATH = "pic_path";
                private static final String AG_VIDEO_PATH = "vid_path";
                private static final int MAX_CHARS = 120;
                private static final int POSTING_DIALOG_DELAY = 750;
                public static final int REQUEST_CODE_ADD_TO_CHANNEL = 3;
                public static final int RESULT_FINISH = 32;
                public static final int RESULT_SAVED = 31;
                private static final String STATE_CHANNEL = "channel";
                private static Toast mToastLengthExceeded;
                private View mAddToChannel;
                private ImageView mAddToChannelIcon;
                private TextView mAddToChannelText;
                private PopupEditText mCaption;
                private long mChannelId;
                private Filter mFilter;
                private ImageView mImagePreview;
                private boolean mIsFromSony;
                private boolean mIsRetry;
                private SwitchInterface mPostFacebook;
                private TextView mPostToFacebookText;
                private TextView mPostToTwitterText;
                private SwitchInterface mPostTwitter;
                private SwitchInterface mPostVine;
                private String mPreviewPath;
                private TagsAutoCompleteAdapter mTagsAdapter;
                private ComposeTokenizer mTokenizer;
                private Twitter mTwitter;
                private CursorAdapter mTypeaheadAdapter;
                private UsersAutoCompleteAdapter mUsersAdapter;
                private String mVideoPath;

        static  SwitchInterface access$000(PostFragment x0) {
            return x0.mPostFacebook;
                }

        static  SwitchInterface access$100(PostFragment x0) {
            return x0.mPostTwitter;
                }

        static  PopupEditText access$200(PostFragment x0) {
            return x0.mCaption;
                }

        static  UsersAutoCompleteAdapter access$300(PostFragment x0) {
            return x0.mUsersAdapter;
                }

                @Override 
                @SuppressLint({"ShowToast"})
       public void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           setHasOptionsMenu(true);
           this.mTwitter = new Twitter(TwitterVineApp.API_KEY, TwitterVineApp.API_SECRET);
           this.mAppSessionListener = new PostSessionListener();
           this.mIsRetry = getArguments().getBoolean("is_retry", false);
           mToastLengthExceeded = Toast.makeText(getActivity(), R.string.caption_length_exceeded, 1);
           mToastLengthExceeded.setGravity(17, 0, 0);
           if (savedInstanceState == null) {
               this.mChannelId = -1L;
                    } else {
               this.mChannelId = savedInstanceState.getLong("channel");
                    }
           this.mUsersAdapter = new UsersAutoCompleteAdapter(getActivity(), this.mAppController);
           this.mTagsAdapter = new TagsAutoCompleteAdapter(getActivity(), this.mAppController);
           this.mTypeaheadAdapter = this.mUsersAdapter;
                }

                @Override 
       public void onSaveInstanceState(Bundle outState) {
           super.onSaveInstanceState(outState);
           outState.putLong("channel", this.mChannelId);
                }

                @Override 
       public void onDestroy() {
           super.onDestroy();
           VineSuggestionsProvider.clearRealtimeUserSuggestions();
           VineSuggestionsProvider.clearRealtimeTagSuggestions();
                }

                @Override 
       public CursorAdapter getPopupAdapter() {
           return this.mTypeaheadAdapter;
                }

                @Override 
       public void setPopupAdapter(String type) {
           if (ComposeTokenizer.USERS_ADAPTER.equals(type)) {
               this.mTypeaheadAdapter = this.mUsersAdapter;
                    } else if (ComposeTokenizer.TAGS_ADAPTER.equals(type)) {
               this.mTypeaheadAdapter = this.mTagsAdapter;
                    }
           this.mCaption.setAdapter(this.mTypeaheadAdapter);
                }

                @Override 
       public Filter getFilter() {
           if (this.mFilter == null) {
               long activeId = this.mAppController.getActiveId();
               this.mFilter = new ComposeFilter(this, activeId);
                    }
           return this.mFilter;
                }

                @Override 
       public void onPopupItemSelected(int tokenStart, int tokenEnd, CharSequence selection, long userId) {
                }

                @Override 
       public void onPopupShown() {
                }

                @Override 
       public void onFiltering(CharSequence constraint) {
                    String type;
                    String query;
                    boolean fetch = true;
           if (!TextUtils.isEmpty(constraint)) {
               String selection = constraint.toString();
               if (selection.startsWith("@")) {
                   type = VineEntity.TYPE_MENTION;
                   query = selection.substring(1, selection.length());
                        } else if (selection.startsWith("#")) {
                   type = "tag";
                   query = selection.substring(1, selection.length());
                        } else {
                   type = null;
                   query = null;
                        }
               int fetchThreshold = this.mAppController.getTypeaheadFetchThreshold(type);
               if (!TextUtils.isEmpty(query) && query.length() >= fetchThreshold) {
                   if (VineEntity.TYPE_MENTION.equals(type)) {
                       if (VineSuggestionsProvider.getRealtimeUserSuggestions(query) != null) {
                                    fetch = false;
                                }
                            } else if ("tag".equals(type)) {
                       if (VineSuggestionsProvider.getRealtimeTagSuggestions(query) != null) {
                                    fetch = false;
                                }
                            } else {
                       fetch = false;
                            }
                   if (fetch) {
                       if (VineEntity.TYPE_MENTION.equals(type)) {
                           this.mAppController.fetchUserTypeahead(query);
                                } else if ("tag".equals(type)) {
                           this.mAppController.fetchTagTypeahead(query);
                                }
                            }
                        }
                    }
                }

                @Override 
       public boolean onOptionsItemSelected(MenuItem item) {
           int id = item.getItemId();
           if (id == R.id.share) {
               return post();
                    }
           if (id == R.id.mention) {
               insertText("@");
                    } else if (id == 2131427933) {
               insertText("#");
                    } else {
               return super.onOptionsItemSelected(item);
                    }
           return true;
                }

       private boolean post() {
           if (captionLength() <= 120) {
               Editable text = this.mCaption.getText();
               Activity activity = getActivity();
               saveToCameraRoll(this.mPreviewPath != null ? this.mPreviewPath : this.mVideoPath);
               if (this.mPostVine.isChecked()) {
                            activity.startService(VineUploadService.getPostIntent(activity, this.mVideoPath, text != null ? text.toString() : "", this.mPostTwitter.isChecked(), this.mPostFacebook.isChecked(), this.mChannelId, this.mIsRetry, this.mCaption.getEntities(), null, null, -1L, 0));
                   activity.startService(VineUploadService.getShowProgressIntent(activity));
                        } else {
                   activity.startService(VineUploadService.getDiscardIntent(activity, this.mVideoPath));
                        }
               FlurryUtils.trackPost(this.mPostVine.isChecked());
               FlurryUtils.trackPostTier(String.valueOf((int) (SystemUtil.getMemoryRatio(activity, true) * 2.0d)));
               activity.setResult(31);
                        try {
                   if (this.mPostVine.isChecked()) {
                       if (this.mPreviewPath != null) {
                           RecordSessionManager.deleteSession(new File(this.mPreviewPath).getParentFile(), "Session Completed.");
                                } else {
                           RecordSessionVersion.deleteSessionWithName(activity, new File(this.mVideoPath).getName());
                                }
                       SLog.m465i("Session folder deleted: {}.", this.mPreviewPath);
                            }
                        } catch (IOException e) {
                   SLog.m459e("Failed to delete session folder.", (Throwable) e);
                        }
               if (this.mIsFromSony) {
                   showProgressDialog(R.string.share_posting);
                   new Handler().postDelayed(new RunnableC02501(activity), 750L);
                        } else {
                   activity.startActivity(new Intent(activity, (Class<?>) HomeTabActivity.class).setFlags(avformat.AVFMT_SEEK_TO_PTS));
                   activity.finish();
                        }
               return true;
                    }
           mToastLengthExceeded.show();
           return true;
                }

                
       class RunnableC02501 implements Runnable {
                    final  Activity val$activity;

           RunnableC02501(Activity activity) {
                        this.val$activity = activity;
                    }

                    @Override 
           public void run() {
               this.val$activity.startActivity(new Intent(this.val$activity, (Class<?>) SonyLaunchActivity.class));
               this.val$activity.setResult(32);
               this.val$activity.finish();
                    }
                }

       private void insertText(String toInsert) {
           if (this.mCaption != null) {
               int selStart = this.mCaption.getSelectionStart();
               int selEnd = this.mCaption.getSelectionEnd();
               if (this.mCaption.getText() != null && selStart >= 0 && selEnd <= this.mCaption.length()) {
                   this.mCaption.getText().replace(selStart, selEnd, toInsert);
                        }
                    }
                }

       private int captionLength() {
           return this.mCaption.getText().toString().trim().length();
                }

                @Override 
       public void onActivityCreated(Bundle savedInstanceState) {
                    PostInfo postInfo;
           super.onActivityCreated(savedInstanceState);
           Bundle args = getArguments();
           String thumbnailPath = args.getString("pic_path");
           this.mVideoPath = args.getString(AG_VIDEO_PATH);
           if (this.mVideoPath == null) {
               throw new IllegalArgumentException("Null video path.");
                    }
           this.mPreviewPath = args.getString("preview_path");
           this.mIsFromSony = args.getBoolean("f_s");
                    try {
               Bitmap bitmap = BitmapFactory.decodeFile(thumbnailPath);
               this.mImagePreview.setImageBitmap(bitmap);
                    } catch (OutOfMemoryError e) {
               CrashUtil.logException(e);
                    }
           VineUpload upload = UploadManager.getUpload(getActivity(), this.mVideoPath);
           if (upload != null && (postInfo = upload.getPostInfo()) != null) {
               this.mCaption.setText(postInfo.caption);
                    }
           ((NotificationManager) getActivity().getSystemService("notification")).cancel(this.mVideoPath, 0);
                }

       private void saveToCameraRoll(String path) {
           if (!BuildUtil.isLogsOn()) {
               SLog.m447d("Saving to camera roll: {}.", path);
               File actualFile = new File(path);
               File finalName = RecordConfigUtils.getCameraRollFile(getActivity(), System.currentTimeMillis(), FilenameUtils.getExtension(path));
               if (finalName == null) {
                   SLog.m446d("Failed to find directory.");
                   return;
                        }
               if (actualFile.exists() && RecordConfigUtils.copySilently(actualFile, finalName)) {
                   FragmentActivity activity = getActivity();
                   if (activity != null) {
                       MediaUtil.scanFile(activity, finalName, null);
                            }
                   SLog.m448d("Renamed to finale position! {}, Scan {}.", finalName, finalName.getAbsolutePath());
                   return;
                        }
               SLog.m447d("Failed to move {}, file is probably already copied.", path);
                    }
                }

                @Override 
       public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
           View v = inflater.inflate(R.layout.post_share, container, false);
           View addToChannel = v.findViewById(R.id.add_to_channel_container);
           this.mAddToChannelIcon = (ImageView) addToChannel.findViewById(R.id.add_to_channel_icon);
           this.mAddToChannelText = (TextView) addToChannel.findViewById(R.id.add_to_channel_text);
           this.mAddToChannel = addToChannel;
           this.mPostVine = (SwitchInterface) v.findViewById(R.id.post_to_vine_switch);
           this.mPostTwitter = (SwitchInterface) v.findViewById(R.id.post_to_twitter_switch);
           this.mPostFacebook = (SwitchInterface) v.findViewById(R.id.post_to_facebook_switch);
           this.mImagePreview = (ImageView) v.findViewById(R.id.image_preview);
           PopupEditText edit = (PopupEditText) v.findViewById(R.id.caption);
           edit.setAdapter(this.mUsersAdapter);
           this.mTokenizer = new ComposeTokenizer(this);
           edit.setTokenizer(this.mTokenizer, this, this.mAppController.getTypeaheadThrottle());
           edit.setPopupEditTextListener(this);
           this.mCaption = edit;
           this.mPostToTwitterText = (TextView) v.findViewById(R.id.post_to_twitter_text);
           this.mPostToFacebookText = (TextView) v.findViewById(R.id.post_to_facebook_text);
           this.mAddToChannel.setOnClickListener(this);
           this.mAddToChannel.setOnLongClickListener(this);
           this.mPostVine.setChecked(true);
           this.mPostVine.setEnabled(true);
           this.mPostVine.setOnCheckedChangeListener(this);
           this.mPostTwitter.setChecked(false);
           this.mPostTwitter.setOnCheckedChangeListener(this);
           this.mPostFacebook.setChecked(false);
           this.mPostFacebook.setOnCheckedChangeListener(this);
           v.findViewById(R.id.done_button).setOnClickListener(this);
           return v;
                }

                @Override 
       public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
           inflater.inflate(R.menu.share, menu);
                }

                @Override 
       public void onClick(View view) {
           int id = view.getId();
           if (id == R.id.add_to_channel_container) {
               Intent intent = new Intent(getActivity(), (Class<?>) ChannelsListActivity.class);
               intent.putExtra("selected_channel", this.mChannelId);
               getActivity().startActivityForResult(intent, 3);
                    } else if (id == R.id.done_button) {
               post();
                    }
                }

                @Override 
       public boolean onLongClick(View view) {
           if (view.getId() != R.id.add_to_channel_container) {
                        return false;
                    }
           this.mAddToChannelText.setText(R.string.add_to_channel);
           this.mChannelId = -1L;
           this.mAddToChannelIcon.setSelected(false);
           FlurryUtils.trackChannelChange("Channel removed");
           return true;
                }

                @Override 
       public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
           int id = compoundButton.getId();
           if (id == R.id.post_to_vine_switch) {
               this.mPostTwitter.setEnabled(checked);
               this.mPostFacebook.setEnabled(checked);
               if (!checked) {
                   this.mPostTwitter.setChecked(false);
                   this.mPostToTwitterText.setTextColor(getActivity().getResources().getColor(R.color.solid_gray));
                   this.mPostFacebook.setChecked(false);
                   this.mPostToFacebookText.setTextColor(getActivity().getResources().getColor(R.color.solid_gray));
                   return;
                        }
               this.mPostToTwitterText.setTextColor(getActivity().getResources().getColor(R.color.primary_text));
               this.mPostToFacebookText.setTextColor(getActivity().getResources().getColor(R.color.primary_text));
               return;
                    }
           if (id == R.id.post_to_facebook_switch) {
               if (!this.mPostVine.isChecked()) {
                   this.mPostFacebook.setChecked(false);
                   return;
                        }
               if (checked) {
                   Session session = this.mAppController.getValidFacebookSession(getActivity(), false);
                   if (session == null) {
                       this.mAppController.connectToFacebookInitialize(this);
                       return;
                            } else {
                       if (this.mAppController.getPendingFacebookToken() != null) {
                           this.mAppController.sendFacebookToken();
                           return;
                                }
                       return;
                            }
                        }
               return;
                    }
           if (id == R.id.post_to_twitter_switch) {
               if (!this.mPostVine.isChecked()) {
                   this.mPostTwitter.setChecked(false);
                   return;
                        }
               if (checked) {
                   Activity activity = getActivity();
                   Account ac = VineAccountHelper.getAccount(activity, this.mAppController.getActiveSession().getLoginEmail());
                   AccountManager am = AccountManager.get(activity);
                   if (ac == null || am == null) {
                       CrashUtil.logException(new VineLoggingException("Account or AM is null: " + ac + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + am));
                       return;
                            }
                   String token = am.getUserData(ac, VineAccountHelper.KEY_TWITTER_TOKEN);
                   if (TextUtils.isEmpty(token)) {
                       AppController.startTwitterAuthWithFinish(this.mTwitter, activity);
                            }
                        }
                    }
                }

       public static Bundle prepareArguments(Intent intent, String videoPath, String thumbnailPath, String previewPath, boolean isRetry) {
           Bundle bundle = BaseControllerFragment.prepareArguments(intent);
           bundle.putBoolean("is_retry", isRetry);
           bundle.putString(AG_VIDEO_PATH, videoPath);
           bundle.putString("pic_path", thumbnailPath);
           bundle.putString("preview_path", previewPath);
           return bundle;
                }

                @Override 
       public void onActivityResult(int requestCode, int resultCode, Intent data) {
                    switch (requestCode) {
                        case 1:
                   if (resultCode == -1) {
                       String username = data.getStringExtra("screen_name");
                       String token = data.getStringExtra(Twitter.EXTRA_TOKEN);
                       String secret = data.getStringExtra(Twitter.EXTRA_TOKEN_SECRET);
                       long userId = data.getLongExtra("user_id", 0L);
                       this.mAppController.connectTwitter(this.mAppController.getActiveSession(), username, token, secret, userId);
                            } else {
                       this.mPostTwitter.setChecked(false);
                            }
                            break;
                        case 2:
                   if (resultCode == -1) {
                       String username2 = data.getStringExtra("screen_name");
                       String token2 = data.getStringExtra("token");
                       String secret2 = data.getStringExtra("secret");
                       long userId2 = data.getLongExtra("user_id", 0L);
                       this.mAppController.connectTwitter(this.mAppController.getActiveSession(), username2, token2, secret2, userId2);
                            } else {
                       this.mPostTwitter.setChecked(false);
                            }
                            break;
                        case 3:
                   if (resultCode == -1) {
                       long channelId = data.getLongExtra("channel_id", -1L);
                       String channel = data.getStringExtra("channel");
                                if (channelId > -1 && !TextUtils.isEmpty(channel)) {
                           this.mAddToChannelText.setText(channel);
                           this.mChannelId = channelId;
                           this.mAddToChannelIcon.setSelected(true);
                           FlurryUtils.trackChannelChange(channel + " (id=" + channelId + ")");
                                    break;
                                }
                            }
                            break;
                        default:
                   SLog.m447d("Facebook auth came back: {}", Integer.valueOf(requestCode));
                   if (resultCode == -1) {
                       Session session = Session.getActiveSession();
                       if (session != null) {
                           if (session.onActivityResult(getActivity(), requestCode, resultCode, data)) {
                               if (session.getPermissions().contains(AppController.PUBLISH_ACTIONS)) {
                                   AppController.setPendingFacebookToken(getActivity(), session.getAccessToken());
                                   this.mAppController.sendFacebookToken();
                                   showProgressDialog(R.string.login_logging_in);
                                        } else {
                                   getActivity().runOnUiThread(new RunnableC02512());
                                   this.mAppController.connectToFacebookPublish(this);
                                        }
                                    } else {
                               this.mPostFacebook.setChecked(false);
                                    }
                                } else {
                           this.mPostFacebook.setChecked(false);
                           super.onActivityResult(requestCode, resultCode, data);
                                }
                            } else {
                       this.mPostFacebook.setChecked(false);
                            }
                            break;
                    }
                }

                
       class RunnableC02512 implements Runnable {
           RunnableC02512() {
                    }

                    @Override 
           public void run() {
               Util.showCenteredToast(PostFragment.this.getActivity(), R.string.publish_to_facebook_toast);
                    }
                }

       class PostSessionListener extends AppSessionListener {
           PostSessionListener() {
                    }

                    @Override 
           public void onConnectFacebookComplete(String reqId, int statusCode, String reasonPhrase) {
               PostFragment.this.dismissProgressDialog();
               if (statusCode != 200) {
                   Util.showCenteredToast(PostFragment.this.getActivity(), R.string.error_facebook_send_token);
                   PostFragment.access$000(PostFragment.this).setChecked(false);
                        }
                    }

                    @Override 
           public void onConnectTwitterComplete(String reqId, int statusCode, String reasonPhrase, String username, String token, String secret, long userId) {
               if (statusCode != 200) {
                   Util.showCenteredToast(PostFragment.this.getActivity(), reasonPhrase);
                   PostFragment.access$100(PostFragment.this).setChecked(false);
                        } else if (userId > 0 && !TextUtils.isEmpty(token) && !TextUtils.isEmpty(secret)) {
                   VineAccountHelper.saveTwitterInfo(PostFragment.this.getActivity(), PostFragment.this.mAppController.getActiveSession().getLoginEmail(), username, token, secret, userId);
                   PostFragment.access$100(PostFragment.this).setChecked(true);
                        }
                    }

                    @Override 
           public void onGetUserTypeAheadComplete(String reqId, int statusCode, String reasonPhrase, String query, ArrayList<VineUser> users) {
               if (statusCode == 200 && !users.isEmpty()) {
                   VineSuggestionsProvider.addRealtimeUserSuggestions(query, users);
                   PostFragment.access$200(PostFragment.this).performFilter(false);
                        }
                    }

                    @Override 
           public void onGetTagTypeAheadComplete(String reqId, int statusCode, String reasonPhrase, String query, ArrayList<VineTag> tags) {
               if (statusCode == 200 && !tags.isEmpty()) {
                   VineSuggestionsProvider.addRealtimeTagSuggestions(query, tags);
                   PostFragment.access$200(PostFragment.this).performFilter(false);
                        }
                    }

                    @Override 
           public void onPhotoImageLoaded(HashMap<ImageKey, UrlImage> images) {
               super.onPhotoImageLoaded(images);
               PostFragment.access$300(PostFragment.this).setUserImages(images);
                    }
                }
            }
