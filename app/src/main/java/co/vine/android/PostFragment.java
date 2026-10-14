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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 71 */    public class PostFragment extends BaseControllerFragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener, Filterable, PopupEditText.PopupEditTextListener, View.OnLongClickListener {
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

/* 72 */        static /* synthetic */ SwitchInterface access$000(PostFragment x0) {
/* 73 */            return x0.mPostFacebook;
                }

/* 72 */        static /* synthetic */ SwitchInterface access$100(PostFragment x0) {
/* 73 */            return x0.mPostTwitter;
                }

/* 72 */        static /* synthetic */ PopupEditText access$200(PostFragment x0) {
/* 73 */            return x0.mCaption;
                }

/* 72 */        static /* synthetic */ UsersAutoCompleteAdapter access$300(PostFragment x0) {
/* 73 */            return x0.mUsersAdapter;
                }

                @Override // co.vine.android.BaseControllerFragment, android.support.v4.app.Fragment
                @SuppressLint({"ShowToast"})
/* 118 */       public void onCreate(Bundle savedInstanceState) {
/* 119 */           super.onCreate(savedInstanceState);
/* 120 */           setHasOptionsMenu(true);
/* 121 */           this.mTwitter = new Twitter(TwitterVineApp.API_KEY, TwitterVineApp.API_SECRET);
/* 122 */           this.mAppSessionListener = new PostSessionListener();
/* 123 */           this.mIsRetry = getArguments().getBoolean("is_retry", false);
/* 124 */           mToastLengthExceeded = Toast.makeText(getActivity(), C0257R.string.caption_length_exceeded, 1);
/* 126 */           mToastLengthExceeded.setGravity(17, 0, 0);
/* 128 */           if (savedInstanceState == null) {
/* 129 */               this.mChannelId = -1L;
                    } else {
/* 131 */               this.mChannelId = savedInstanceState.getLong("channel");
                    }
/* 134 */           this.mUsersAdapter = new UsersAutoCompleteAdapter(getActivity(), this.mAppController);
/* 135 */           this.mTagsAdapter = new TagsAutoCompleteAdapter(getActivity(), this.mAppController);
/* 136 */           this.mTypeaheadAdapter = this.mUsersAdapter;
                }

                @Override // android.support.v4.app.Fragment
/* 140 */       public void onSaveInstanceState(Bundle outState) {
/* 141 */           super.onSaveInstanceState(outState);
/* 142 */           outState.putLong("channel", this.mChannelId);
                }

                @Override // android.support.v4.app.Fragment
/* 146 */       public void onDestroy() {
/* 147 */           super.onDestroy();
/* 148 */           VineSuggestionsProvider.clearRealtimeUserSuggestions();
/* 149 */           VineSuggestionsProvider.clearRealtimeTagSuggestions();
                }

                @Override // co.vine.android.widget.PopupEditText.PopupEditTextListener
/* 153 */       public CursorAdapter getPopupAdapter() {
/* 154 */           return this.mTypeaheadAdapter;
                }

                @Override // co.vine.android.widget.PopupEditText.PopupEditTextListener
/* 158 */       public void setPopupAdapter(String type) {
/* 159 */           if (ComposeTokenizer.USERS_ADAPTER.equals(type)) {
/* 160 */               this.mTypeaheadAdapter = this.mUsersAdapter;
                    } else if (ComposeTokenizer.TAGS_ADAPTER.equals(type)) {
/* 162 */               this.mTypeaheadAdapter = this.mTagsAdapter;
                    }
/* 164 */           this.mCaption.setAdapter(this.mTypeaheadAdapter);
                }

                @Override // android.widget.Filterable
/* 168 */       public Filter getFilter() {
/* 169 */           if (this.mFilter == null) {
/* 170 */               long activeId = this.mAppController.getActiveId();
/* 171 */               this.mFilter = new ComposeFilter(this, activeId);
                    }
/* 173 */           return this.mFilter;
                }

                @Override // co.vine.android.widget.PopupEditText.PopupEditTextListener
/* 177 */       public void onPopupItemSelected(int tokenStart, int tokenEnd, CharSequence selection, long userId) {
                }

                @Override // co.vine.android.widget.PopupEditText.PopupEditTextListener
/* 181 */       public void onPopupShown() {
                }

                @Override // co.vine.android.widget.PopupEditText.PopupEditTextListener
/* 185 */       public void onFiltering(CharSequence constraint) {
                    String type;
                    String query;
                    boolean fetch = true;
/* 186 */           if (!TextUtils.isEmpty(constraint)) {
/* 188 */               String selection = constraint.toString();
/* 190 */               if (selection.startsWith("@")) {
/* 191 */                   type = VineEntity.TYPE_MENTION;
/* 192 */                   query = selection.substring(1, selection.length());
                        } else if (selection.startsWith("#")) {
/* 194 */                   type = "tag";
/* 195 */                   query = selection.substring(1, selection.length());
                        } else {
/* 197 */                   type = null;
/* 198 */                   query = null;
                        }
/* 200 */               int fetchThreshold = this.mAppController.getTypeaheadFetchThreshold(type);
/* 202 */               if (!TextUtils.isEmpty(query) && query.length() >= fetchThreshold) {
/* 204 */                   if (VineEntity.TYPE_MENTION.equals(type)) {
/* 205 */                       if (VineSuggestionsProvider.getRealtimeUserSuggestions(query) != null) {
                                    fetch = false;
                                }
                            } else if ("tag".equals(type)) {
/* 207 */                       if (VineSuggestionsProvider.getRealtimeTagSuggestions(query) != null) {
                                    fetch = false;
                                }
                            } else {
/* 209 */                       fetch = false;
                            }
/* 212 */                   if (fetch) {
/* 213 */                       if (VineEntity.TYPE_MENTION.equals(type)) {
/* 214 */                           this.mAppController.fetchUserTypeahead(query);
                                } else if ("tag".equals(type)) {
/* 216 */                           this.mAppController.fetchTagTypeahead(query);
                                }
                            }
                        }
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 224 */       public boolean onOptionsItemSelected(MenuItem item) {
/* 225 */           int id = item.getItemId();
/* 226 */           if (id == C0257R.id.share) {
/* 227 */               return post();
                    }
/* 228 */           if (id == C0257R.id.mention) {
/* 229 */               insertText("@");
                    } else if (id == 2131427933) {
/* 231 */               insertText("#");
                    } else {
/* 233 */               return super.onOptionsItemSelected(item);
                    }
/* 235 */           return true;
                }

/* 238 */       private boolean post() {
/* 239 */           if (captionLength() <= 120) {
/* 240 */               Editable text = this.mCaption.getText();
/* 241 */               Activity activity = getActivity();
/* 242 */               saveToCameraRoll(this.mPreviewPath != null ? this.mPreviewPath : this.mVideoPath);
/* 243 */               if (this.mPostVine.isChecked()) {
                            activity.startService(VineUploadService.getPostIntent(activity, this.mVideoPath, text != null ? text.toString() : "", this.mPostTwitter.isChecked(), this.mPostFacebook.isChecked(), this.mChannelId, this.mIsRetry, this.mCaption.getEntities(), null, null, -1L, 0));
/* 248 */                   activity.startService(VineUploadService.getShowProgressIntent(activity));
                        } else {
/* 250 */                   activity.startService(VineUploadService.getDiscardIntent(activity, this.mVideoPath));
                        }
/* 253 */               FlurryUtils.trackPost(this.mPostVine.isChecked());
/* 254 */               FlurryUtils.trackPostTier(String.valueOf((int) (SystemUtil.getMemoryRatio(activity, true) * 2.0d)));
/* 256 */               activity.setResult(31);
                        try {
/* 258 */                   if (this.mPostVine.isChecked()) {
/* 259 */                       if (this.mPreviewPath != null) {
/* 260 */                           RecordSessionManager.deleteSession(new File(this.mPreviewPath).getParentFile(), "Session Completed.");
                                } else {
/* 262 */                           RecordSessionVersion.deleteSessionWithName(activity, new File(this.mVideoPath).getName());
                                }
/* 264 */                       SLog.m465i("Session folder deleted: {}.", this.mPreviewPath);
                            }
                        } catch (IOException e) {
/* 267 */                   SLog.m459e("Failed to delete session folder.", (Throwable) e);
                        }
/* 269 */               if (this.mIsFromSony) {
/* 270 */                   showProgressDialog(C0257R.string.share_posting);
/* 271 */                   new Handler().postDelayed(new RunnableC02501(activity), 750L);
                        } else {
/* 280 */                   activity.startActivity(new Intent(activity, (Class<?>) HomeTabActivity.class).setFlags(avformat.AVFMT_SEEK_TO_PTS));
/* 282 */                   activity.finish();
                        }
/* 284 */               return true;
                    }
/* 286 */           mToastLengthExceeded.show();
/* 288 */           return true;
                }

                /* JADX INFO: renamed from: co.vine.android.PostFragment$1 */
/* 269 */       class RunnableC02501 implements Runnable {
                    final /* synthetic */ Activity val$activity;

/* 270 */           RunnableC02501(Activity activity) {
                        this.val$activity = activity;
                    }

                    @Override // java.lang.Runnable
/* 273 */           public void run() {
/* 274 */               this.val$activity.startActivity(new Intent(this.val$activity, (Class<?>) SonyLaunchActivity.class));
/* 275 */               this.val$activity.setResult(32);
/* 276 */               this.val$activity.finish();
                    }
                }

/* 291 */       private void insertText(String toInsert) {
/* 292 */           if (this.mCaption != null) {
/* 293 */               int selStart = this.mCaption.getSelectionStart();
/* 294 */               int selEnd = this.mCaption.getSelectionEnd();
/* 295 */               if (this.mCaption.getText() != null && selStart >= 0 && selEnd <= this.mCaption.length()) {
/* 296 */                   this.mCaption.getText().replace(selStart, selEnd, toInsert);
                        }
                    }
                }

/* 301 */       private int captionLength() {
/* 302 */           return this.mCaption.getText().toString().trim().length();
                }

                @Override // android.support.v4.app.Fragment
/* 306 */       public void onActivityCreated(Bundle savedInstanceState) {
                    PostInfo postInfo;
/* 307 */           super.onActivityCreated(savedInstanceState);
/* 308 */           Bundle args = getArguments();
/* 309 */           String thumbnailPath = args.getString("pic_path");
/* 310 */           this.mVideoPath = args.getString(AG_VIDEO_PATH);
/* 311 */           if (this.mVideoPath == null) {
/* 312 */               throw new IllegalArgumentException("Null video path.");
                    }
/* 314 */           this.mPreviewPath = args.getString("preview_path");
/* 315 */           this.mIsFromSony = args.getBoolean("f_s");
                    try {
/* 317 */               Bitmap bitmap = BitmapFactory.decodeFile(thumbnailPath);
/* 318 */               this.mImagePreview.setImageBitmap(bitmap);
                    } catch (OutOfMemoryError e) {
/* 321 */               CrashUtil.logException(e);
                    }
/* 324 */           VineUpload upload = UploadManager.getUpload(getActivity(), this.mVideoPath);
/* 325 */           if (upload != null && (postInfo = upload.getPostInfo()) != null) {
/* 328 */               this.mCaption.setText(postInfo.caption);
                    }
/* 332 */           ((NotificationManager) getActivity().getSystemService("notification")).cancel(this.mVideoPath, 0);
                }

/* 337 */       private void saveToCameraRoll(String path) {
/* 338 */           if (!BuildUtil.isLogsOn()) {
/* 339 */               SLog.m447d("Saving to camera roll: {}.", path);
/* 340 */               File actualFile = new File(path);
/* 341 */               File finalName = RecordConfigUtils.getCameraRollFile(getActivity(), System.currentTimeMillis(), FilenameUtils.getExtension(path));
/* 343 */               if (finalName == null) {
/* 344 */                   SLog.m446d("Failed to find directory.");
/* 357 */                   return;
                        }
/* 346 */               if (actualFile.exists() && RecordConfigUtils.copySilently(actualFile, finalName)) {
/* 347 */                   FragmentActivity activity = getActivity();
/* 348 */                   if (activity != null) {
/* 349 */                       MediaUtil.scanFile(activity, finalName, null);
                            }
/* 351 */                   SLog.m448d("Renamed to finale position! {}, Scan {}.", finalName, finalName.getAbsolutePath());
/* 357 */                   return;
                        }
/* 353 */               SLog.m447d("Failed to move {}, file is probably already copied.", path);
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 361 */       public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
/* 362 */           View v = inflater.inflate(C0257R.layout.post_share, container, false);
/* 363 */           View addToChannel = v.findViewById(C0257R.id.add_to_channel_container);
/* 364 */           this.mAddToChannelIcon = (ImageView) addToChannel.findViewById(C0257R.id.add_to_channel_icon);
/* 365 */           this.mAddToChannelText = (TextView) addToChannel.findViewById(C0257R.id.add_to_channel_text);
/* 366 */           this.mAddToChannel = addToChannel;
/* 368 */           this.mPostVine = (SwitchInterface) v.findViewById(C0257R.id.post_to_vine_switch);
/* 369 */           this.mPostTwitter = (SwitchInterface) v.findViewById(C0257R.id.post_to_twitter_switch);
/* 370 */           this.mPostFacebook = (SwitchInterface) v.findViewById(C0257R.id.post_to_facebook_switch);
/* 371 */           this.mImagePreview = (ImageView) v.findViewById(C0257R.id.image_preview);
/* 372 */           PopupEditText edit = (PopupEditText) v.findViewById(C0257R.id.caption);
/* 373 */           edit.setAdapter(this.mUsersAdapter);
/* 374 */           this.mTokenizer = new ComposeTokenizer(this);
/* 375 */           edit.setTokenizer(this.mTokenizer, this, this.mAppController.getTypeaheadThrottle());
/* 376 */           edit.setPopupEditTextListener(this);
/* 377 */           this.mCaption = edit;
/* 378 */           this.mPostToTwitterText = (TextView) v.findViewById(C0257R.id.post_to_twitter_text);
/* 379 */           this.mPostToFacebookText = (TextView) v.findViewById(C0257R.id.post_to_facebook_text);
/* 381 */           this.mAddToChannel.setOnClickListener(this);
/* 382 */           this.mAddToChannel.setOnLongClickListener(this);
/* 383 */           this.mPostVine.setChecked(true);
/* 384 */           this.mPostVine.setEnabled(true);
/* 385 */           this.mPostVine.setOnCheckedChangeListener(this);
/* 386 */           this.mPostTwitter.setChecked(false);
/* 387 */           this.mPostTwitter.setOnCheckedChangeListener(this);
/* 388 */           this.mPostFacebook.setChecked(false);
/* 389 */           this.mPostFacebook.setOnCheckedChangeListener(this);
/* 390 */           v.findViewById(C0257R.id.done_button).setOnClickListener(this);
/* 391 */           return v;
                }

                @Override // android.support.v4.app.Fragment
/* 395 */       public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
/* 396 */           inflater.inflate(C0257R.menu.share, menu);
                }

                @Override // android.view.View.OnClickListener
/* 400 */       public void onClick(View view) {
/* 401 */           int id = view.getId();
/* 402 */           if (id == C0257R.id.add_to_channel_container) {
/* 403 */               Intent intent = new Intent(getActivity(), (Class<?>) ChannelsListActivity.class);
/* 404 */               intent.putExtra("selected_channel", this.mChannelId);
/* 405 */               getActivity().startActivityForResult(intent, 3);
                    } else if (id == C0257R.id.done_button) {
/* 407 */               post();
                    }
                }

                @Override // android.view.View.OnLongClickListener
/* 412 */       public boolean onLongClick(View view) {
/* 413 */           if (view.getId() != C0257R.id.add_to_channel_container) {
                        return false;
                    }
/* 414 */           this.mAddToChannelText.setText(C0257R.string.add_to_channel);
/* 415 */           this.mChannelId = -1L;
/* 416 */           this.mAddToChannelIcon.setSelected(false);
/* 417 */           FlurryUtils.trackChannelChange("Channel removed");
/* 418 */           return true;
                }

                @Override // android.widget.CompoundButton.OnCheckedChangeListener
/* 425 */       public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
/* 426 */           int id = compoundButton.getId();
/* 427 */           if (id == C0257R.id.post_to_vine_switch) {
/* 428 */               this.mPostTwitter.setEnabled(checked);
/* 429 */               this.mPostFacebook.setEnabled(checked);
/* 430 */               if (!checked) {
/* 431 */                   this.mPostTwitter.setChecked(false);
/* 432 */                   this.mPostToTwitterText.setTextColor(getActivity().getResources().getColor(C0257R.color.solid_gray));
/* 434 */                   this.mPostFacebook.setChecked(false);
/* 435 */                   this.mPostToFacebookText.setTextColor(getActivity().getResources().getColor(C0257R.color.solid_gray));
/* 475 */                   return;
                        }
/* 438 */               this.mPostToTwitterText.setTextColor(getActivity().getResources().getColor(C0257R.color.primary_text));
/* 440 */               this.mPostToFacebookText.setTextColor(getActivity().getResources().getColor(C0257R.color.primary_text));
/* 475 */               return;
                    }
/* 443 */           if (id == C0257R.id.post_to_facebook_switch) {
/* 444 */               if (!this.mPostVine.isChecked()) {
/* 445 */                   this.mPostFacebook.setChecked(false);
/* 475 */                   return;
                        }
/* 446 */               if (checked) {
/* 447 */                   Session session = this.mAppController.getValidFacebookSession(getActivity(), false);
/* 449 */                   if (session == null) {
/* 450 */                       this.mAppController.connectToFacebookInitialize(this);
/* 475 */                       return;
                            } else {
/* 452 */                       if (this.mAppController.getPendingFacebookToken() != null) {
/* 453 */                           this.mAppController.sendFacebookToken();
/* 475 */                           return;
                                }
/* 475 */                       return;
                            }
                        }
/* 475 */               return;
                    }
/* 457 */           if (id == C0257R.id.post_to_twitter_switch) {
/* 458 */               if (!this.mPostVine.isChecked()) {
/* 459 */                   this.mPostTwitter.setChecked(false);
/* 475 */                   return;
                        }
/* 460 */               if (checked) {
/* 461 */                   Activity activity = getActivity();
/* 462 */                   Account ac = VineAccountHelper.getAccount(activity, this.mAppController.getActiveSession().getLoginEmail());
/* 464 */                   AccountManager am = AccountManager.get(activity);
/* 465 */                   if (ac == null || am == null) {
/* 466 */                       CrashUtil.logException(new VineLoggingException("Account or AM is null: " + ac + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + am));
/* 475 */                       return;
                            }
/* 468 */                   String token = am.getUserData(ac, VineAccountHelper.KEY_TWITTER_TOKEN);
/* 469 */                   if (TextUtils.isEmpty(token)) {
/* 470 */                       AppController.startTwitterAuthWithFinish(this.mTwitter, activity);
                            }
                        }
                    }
                }

/* 478 */       public static Bundle prepareArguments(Intent intent, String videoPath, String thumbnailPath, String previewPath, boolean isRetry) {
/* 479 */           Bundle bundle = BaseControllerFragment.prepareArguments(intent);
/* 480 */           bundle.putBoolean("is_retry", isRetry);
/* 481 */           bundle.putString(AG_VIDEO_PATH, videoPath);
/* 482 */           bundle.putString("pic_path", thumbnailPath);
/* 483 */           bundle.putString("preview_path", previewPath);
/* 484 */           return bundle;
                }

                @Override // android.support.v4.app.Fragment
/* 488 */       public void onActivityResult(int requestCode, int resultCode, Intent data) {
                    switch (requestCode) {
                        case 1:
/* 491 */                   if (resultCode == -1) {
/* 492 */                       String username = data.getStringExtra("screen_name");
/* 493 */                       String token = data.getStringExtra(Twitter.EXTRA_TOKEN);
/* 494 */                       String secret = data.getStringExtra(Twitter.EXTRA_TOKEN_SECRET);
/* 495 */                       long userId = data.getLongExtra("user_id", 0L);
/* 496 */                       this.mAppController.connectTwitter(this.mAppController.getActiveSession(), username, token, secret, userId);
                            } else {
/* 499 */                       this.mPostTwitter.setChecked(false);
                            }
                            break;
                        case 2:
/* 504 */                   if (resultCode == -1) {
/* 505 */                       String username2 = data.getStringExtra("screen_name");
/* 507 */                       String token2 = data.getStringExtra("token");
/* 508 */                       String secret2 = data.getStringExtra("secret");
/* 509 */                       long userId2 = data.getLongExtra("user_id", 0L);
/* 510 */                       this.mAppController.connectTwitter(this.mAppController.getActiveSession(), username2, token2, secret2, userId2);
                            } else {
/* 513 */                       this.mPostTwitter.setChecked(false);
                            }
                            break;
                        case 3:
/* 518 */                   if (resultCode == -1) {
/* 519 */                       long channelId = data.getLongExtra("channel_id", -1L);
/* 520 */                       String channel = data.getStringExtra("channel");
                                if (channelId > -1 && !TextUtils.isEmpty(channel)) {
/* 522 */                           this.mAddToChannelText.setText(channel);
/* 523 */                           this.mChannelId = channelId;
/* 524 */                           this.mAddToChannelIcon.setSelected(true);
/* 525 */                           FlurryUtils.trackChannelChange(channel + " (id=" + channelId + ")");
                                    break;
                                }
                            }
                            break;
                        default:
/* 531 */                   SLog.m447d("Facebook auth came back: {}", Integer.valueOf(requestCode));
/* 532 */                   if (resultCode == -1) {
/* 533 */                       Session session = Session.getActiveSession();
/* 534 */                       if (session != null) {
/* 535 */                           if (session.onActivityResult(getActivity(), requestCode, resultCode, data)) {
/* 537 */                               if (session.getPermissions().contains(AppController.PUBLISH_ACTIONS)) {
/* 538 */                                   AppController.setPendingFacebookToken(getActivity(), session.getAccessToken());
/* 540 */                                   this.mAppController.sendFacebookToken();
/* 541 */                                   showProgressDialog(C0257R.string.login_logging_in);
                                        } else {
/* 543 */                                   getActivity().runOnUiThread(new RunnableC02512());
/* 549 */                                   this.mAppController.connectToFacebookPublish(this);
                                        }
                                    } else {
/* 552 */                               this.mPostFacebook.setChecked(false);
                                    }
                                } else {
/* 555 */                           this.mPostFacebook.setChecked(false);
/* 556 */                           super.onActivityResult(requestCode, resultCode, data);
                                }
                            } else {
/* 559 */                       this.mPostFacebook.setChecked(false);
                            }
                            break;
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.PostFragment$2 */
/* 541 */       class RunnableC02512 implements Runnable {
/* 542 */           RunnableC02512() {
                    }

                    @Override // java.lang.Runnable
/* 545 */           public void run() {
/* 546 */               Util.showCenteredToast(PostFragment.this.getActivity(), C0257R.string.publish_to_facebook_toast);
                    }
                }

/* 564 */       class PostSessionListener extends AppSessionListener {
/* 565 */           PostSessionListener() {
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 568 */           public void onConnectFacebookComplete(String reqId, int statusCode, String reasonPhrase) {
/* 569 */               PostFragment.this.dismissProgressDialog();
/* 570 */               if (statusCode != 200) {
/* 571 */                   Util.showCenteredToast(PostFragment.this.getActivity(), C0257R.string.error_facebook_send_token);
/* 573 */                   PostFragment.access$000(PostFragment.this).setChecked(false);
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 580 */           public void onConnectTwitterComplete(String reqId, int statusCode, String reasonPhrase, String username, String token, String secret, long userId) {
/* 581 */               if (statusCode != 200) {
/* 589 */                   Util.showCenteredToast(PostFragment.this.getActivity(), reasonPhrase);
/* 590 */                   PostFragment.access$100(PostFragment.this).setChecked(false);
                        } else if (userId > 0 && !TextUtils.isEmpty(token) && !TextUtils.isEmpty(secret)) {
/* 583 */                   VineAccountHelper.saveTwitterInfo(PostFragment.this.getActivity(), PostFragment.this.mAppController.getActiveSession().getLoginEmail(), username, token, secret, userId);
/* 586 */                   PostFragment.access$100(PostFragment.this).setChecked(true);
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 596 */           public void onGetUserTypeAheadComplete(String reqId, int statusCode, String reasonPhrase, String query, ArrayList<VineUser> users) {
/* 597 */               if (statusCode == 200 && !users.isEmpty()) {
/* 599 */                   VineSuggestionsProvider.addRealtimeUserSuggestions(query, users);
/* 600 */                   PostFragment.access$200(PostFragment.this).performFilter(false);
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 607 */           public void onGetTagTypeAheadComplete(String reqId, int statusCode, String reasonPhrase, String query, ArrayList<VineTag> tags) {
/* 608 */               if (statusCode == 200 && !tags.isEmpty()) {
/* 609 */                   VineSuggestionsProvider.addRealtimeTagSuggestions(query, tags);
/* 610 */                   PostFragment.access$200(PostFragment.this).performFilter(false);
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 615 */           public void onPhotoImageLoaded(HashMap<ImageKey, UrlImage> images) {
/* 616 */               super.onPhotoImageLoaded(images);
/* 617 */               PostFragment.access$300(PostFragment.this).setUserImages(images);
                    }
                }
            }
