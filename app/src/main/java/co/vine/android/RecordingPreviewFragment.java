            package co.vine.android;

            import android.app.Activity;
            import android.content.DialogInterface;
            import android.content.Intent;
            import android.content.res.Resources;
            import android.graphics.Point;
            import android.graphics.Rect;
            import android.os.Bundle;
            import android.text.Editable;
            import android.text.TextUtils;
            import android.view.LayoutInflater;
            import android.view.MotionEvent;
            import android.view.View;
            import android.view.ViewGroup;
            import android.view.Window;
            import android.widget.Button;
            import android.widget.EditText;
            import android.widget.RelativeLayout;
            import android.widget.TextView;
            import co.vine.android.api.VineRecipient;
            import co.vine.android.client.AppController;
            import co.vine.android.player.OnSingleVideoClickedListener;
            import co.vine.android.player.SdkVideoView;
            import co.vine.android.player.StaticSizeVideoView;
            import co.vine.android.provider.VineProviderHelper;
            import co.vine.android.recorder.BasicVineRecorder;
            import co.vine.android.recorder.RecordConfigUtils;
            import co.vine.android.recorder.RecordingFile;
            import co.vine.android.service.VineUploadService;
            import co.vine.android.util.BuildUtil;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.SystemUtil;
            import co.vine.android.util.UploadManager;
            import co.vine.android.util.ViewUtil;
            import co.vine.android.util.analytics.FlurryUtils;
            import co.vine.android.widget.Typefaces;
            import co.vine.android.widget.TypefacesTextView;
            import co.vine.android.widgets.PromptDialogSupportFragment;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 46 */    public class RecordingPreviewFragment extends BaseFragment implements View.OnClickListener {
                public static final String AG_THUMBNAIL_PATH = "pic_path";
                public static final String AG_UPLOAD_PATH = "upload_path";
                public static final String AG_VIDEO_PATH = "video_path";
                public static final String ARG_AM_FOLLOWING = "am_following";
                public static final String ARG_COLOR = "color";
                public static final String ARG_CONVERSATION_ROW_ID = "conversation_row_id";
                public static final String ARG_DIRECT_USER_ID = "direct_user_id";
                public static final String ARG_FROM_SONY = "f_s";
                public static final String ARG_IS_MESSAGING = "messaging";
                public static final String ARG_RECIPIENT_USERNAME = "recipient_username";
                private static final int DIALOG_MUST_LOGIN = 1;
                private static final int REQUEST_CODE_LOGIN_TO_POST = 12345;
                public static final int REQUEST_POST = 1990;
                public static final int REQUEST_SEND_MESSAGE = 1991;
                private static final long TRANSITION_DURATION = 250;
                private static final int[] VALID_MAX_LOOPS = {0, 1, 3, 6};
                private boolean mAmFollowing;
                private int mColor;
                private long mConversationRowId;
                private long mDirectUserId;
                private RecordingFile mFinalFile;
                private boolean mIsFromSony;
                private boolean mIsMessaging;
                private final PromptDialogSupportFragment.OnDialogDoneListener mLoginRequiredDialogDoneListener = new C02621();
                private int mMaxLoop;
                private EditText mMessagingEditText;
                private Button mMessagingLoopLimitText;
                private View mNextButton;
                private View mPreviewButtons;
                private String mRecipientUsername;
                private String mThumbnailPath;
                private String mUploadPath;
                private String mVideoPath;
                private SdkVideoView mVideoPlayer;

/* 47 */        static /* synthetic */ int access$000(RecordingPreviewFragment x0) {
/* 48 */            return x0.mMaxLoop;
                }

/* 47 */        static /* synthetic */ int access$002(RecordingPreviewFragment x0, int x1) {
/* 48 */            x0.mMaxLoop = x1;
                    return x1;
                }

/* 47 */        static /* synthetic */ int[] access$100() {
/* 48 */            return VALID_MAX_LOOPS;
                }

/* 47 */        static /* synthetic */ void access$200(RecordingPreviewFragment x0) {
/* 48 */            x0.updateMessageLoopLimit();
                }

                /* JADX INFO: renamed from: co.vine.android.RecordingPreviewFragment$1 */
/* 90 */        class C02621 implements PromptDialogSupportFragment.OnDialogDoneListener {
/* 91 */            C02621() {
                    }

                    @Override // co.vine.android.widgets.PromptDialogFragment.OnDialogDoneListener
/* 95 */            public void onDialogDone(DialogInterface dialog, int id, int which) {
                        switch (which) {
                            case -1:
/* 98 */                        Activity activity = RecordingPreviewFragment.this.getActivity();
/* 99 */                        Intent i = new Intent(activity, (Class<?>) SonyStartActivity.class);
/* 100 */                       i.putExtra(StartActivity.EXTRA_LOGIN_REQUEST, true);
/* 101 */                       activity.startActivityForResult(i, RecordingPreviewFragment.REQUEST_CODE_LOGIN_TO_POST);
                                break;
                        }
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 109 */       public void onPause() {
/* 110 */           super.onPause();
/* 111 */           if (this.mVideoPlayer.canPause()) {
/* 112 */               this.mVideoPlayer.pause();
                    }
                }

                @Override // co.vine.android.BaseFragment, android.support.v4.app.Fragment
/* 117 */       public void onResume() {
/* 118 */           super.onResume();
/* 119 */           animateInViews();
/* 120 */           if (this.mVideoPlayer.getCurrentState() == 7) {
/* 121 */               this.mVideoPlayer.start();
                    }
                }

/* 129 */       public static RecordingPreviewFragment newInstance(String videoPath, String uploadPath, String thumbnailPath, boolean isMessaging, long conversationObjectId, long directUserId, boolean isFromSony, int color, String recipientUsername, boolean amFollowing) {
/* 130 */           RecordingPreviewFragment fragment = new RecordingPreviewFragment();
/* 131 */           Bundle bundle = new Bundle();
/* 132 */           bundle.putString("video_path", videoPath);
/* 133 */           bundle.putString("pic_path", thumbnailPath);
/* 134 */           bundle.putString("upload_path", uploadPath);
/* 135 */           bundle.putBoolean(ARG_IS_MESSAGING, isMessaging);
/* 136 */           bundle.putLong("conversation_row_id", conversationObjectId);
/* 137 */           bundle.putLong("direct_user_id", directUserId);
/* 138 */           bundle.putBoolean("f_s", isFromSony);
/* 139 */           bundle.putInt("color", color);
/* 140 */           bundle.putString("recipient_username", recipientUsername);
/* 141 */           bundle.putBoolean("am_following", amFollowing);
/* 142 */           fragment.setArguments(bundle);
/* 143 */           return fragment;
                }

                @Override // android.support.v4.app.Fragment
/* 147 */       public void onCreate(Bundle savedInstanceState) {
/* 148 */           super.onActivityCreated(savedInstanceState);
/* 149 */           Bundle args = getArguments();
/* 150 */           this.mVideoPath = args.getString("video_path");
/* 151 */           this.mThumbnailPath = args.getString("pic_path");
/* 152 */           this.mUploadPath = args.getString("upload_path");
/* 153 */           this.mIsMessaging = args.getBoolean(ARG_IS_MESSAGING);
/* 154 */           this.mConversationRowId = args.getLong("conversation_row_id", -1L);
/* 155 */           this.mRecipientUsername = args.getString("recipient_username");
/* 156 */           this.mAmFollowing = args.getBoolean("am_following");
/* 157 */           this.mDirectUserId = args.getLong("direct_user_id", -1L);
/* 158 */           this.mIsFromSony = args.getBoolean("f_s");
/* 159 */           this.mColor = args.getInt("color", -1);
/* 160 */           if (this.mColor == Settings.DEFAULT_PROFILE_COLOR || this.mColor <= 0) {
/* 161 */               this.mColor = 16777215 & getResources().getColor(C0257R.color.vine_green);
                    }
/* 163 */           this.mColor |= -16777216;
                }

                @Override // android.support.v4.app.Fragment
/* 167 */       public void onActivityCreated(Bundle savedInstanceState) {
/* 168 */           super.onActivityCreated(savedInstanceState);
/* 169 */           RecordConfigUtils.deletePreProcess(getActivity().getFilesDir());
/* 170 */           this.mVideoPlayer.setVideoPath(this.mVideoPath);
/* 171 */           CrashUtil.log("Preview: {}", this.mVideoPath);
                }

                @Override // android.support.v4.app.Fragment
/* 175 */       public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
/* 176 */           Resources res = getResources();
/* 177 */           View view = inflater.inflate(C0257R.layout.preview, container, false);
/* 178 */           Point size = SystemUtil.getDisplaySize((Activity) getActivity());
/* 180 */           this.mMessagingEditText = (EditText) view.findViewById(C0257R.id.messaging_preview_caption);
/* 181 */           this.mMessagingLoopLimitText = (Button) view.findViewById(C0257R.id.messagingLoopLimit);
/* 182 */           this.mNextButton = view.findViewById(C0257R.id.toPostButton);
/* 183 */           if (this.mIsMessaging) {
/* 184 */               this.mMaxLoop = 0;
/* 185 */               updateMessageLoopLimit();
/* 186 */               if (this.mMessagingLoopLimitText != null) {
/* 187 */                   this.mMessagingLoopLimitText.setOnClickListener(new ViewOnClickListenerC02632());
/* 197 */                   this.mMessagingLoopLimitText.setVisibility(8);
                        }
/* 200 */               this.mMessagingEditText.setVisibility(0);
/* 201 */               this.mPreviewButtons = view.findViewById(C0257R.id.messagingPreviewTopButtons);
/* 202 */               this.mPreviewButtons.setVisibility(0);
/* 203 */               this.mPreviewButtons.setOnTouchListener(new ViewOnTouchListenerC02643());
/* 210 */               int vmBgColor = res.getColor(C0257R.color.capture_background_vm);
/* 211 */               this.mPreviewButtons.setBackgroundColor(vmBgColor);
/* 213 */               view.setBackgroundColor(vmBgColor);
/* 214 */               View scrollView = view.findViewById(C0257R.id.scrollview);
/* 215 */               scrollView.setBackgroundColor(vmBgColor);
/* 217 */               TypefacesTextView recipient = (TypefacesTextView) view.findViewById(C0257R.id.recipient_name);
/* 218 */               recipient.setWeight(3);
/* 219 */               recipient.setTypeface(Typefaces.get(getActivity()).mediumContent);
/* 220 */               recipient.setVisibility(0);
/* 221 */               if (!TextUtils.isEmpty(this.mRecipientUsername)) {
/* 222 */                   recipient.setText(this.mRecipientUsername);
                        }
                    } else {
/* 225 */               this.mMessagingLoopLimitText.setVisibility(8);
/* 226 */               this.mMessagingEditText.setVisibility(8);
/* 227 */               this.mPreviewButtons = view.findViewById(C0257R.id.previewTopButtons);
/* 228 */               this.mPreviewButtons.setVisibility(0);
/* 229 */               this.mPreviewButtons.setOnTouchListener(new ViewOnTouchListenerC02654());
/* 235 */               this.mNextButton.setVisibility(0);
                    }
/* 237 */           view.findViewById(C0257R.id.previewTitle).setOnClickListener(new ViewOnClickListenerC02665());
/* 244 */           this.mNextButton.setOnClickListener(this);
/* 246 */           view.findViewById(C0257R.id.previewCaptureButton).setOnClickListener(this);
/* 247 */           view.findViewById(C0257R.id.previewEditButton).setOnClickListener(this);
/* 248 */           TextView nextButton = (TextView) view.findViewById(C0257R.id.messagingPreviewNextButton);
/* 249 */           nextButton.setOnClickListener(this);
/* 250 */           if (this.mIsMessaging) {
/* 251 */               nextButton.setTextColor(this.mColor);
                    }
/* 253 */           View backButton = view.findViewById(C0257R.id.messagingPreviewBackButton);
/* 254 */           backButton.setOnClickListener(this);
/* 255 */           if (this.mIsMessaging) {
/* 256 */               ViewUtil.fillColor(res, this.mColor, C0257R.drawable.ic_arrow_back_large, backButton);
                    }
/* 259 */           this.mVideoPlayer = (SdkVideoView) view.findViewById(C0257R.id.sdkVideoView);
/* 260 */           this.mVideoPlayer.setUseVinePlayer(false);
/* 261 */           this.mVideoPlayer.setVisibility(0);
/* 262 */           this.mVideoPlayer.setAutoPlayOnPrepared(true);
/* 263 */           this.mVideoPlayer.setKeepScreenOn(true);
/* 266 */           View videoPlayerContainer = view.findViewById(C0257R.id.videoViewContainer);
/* 267 */           int topMaskHeightPx = res.getDimensionPixelSize(C0257R.dimen.capture_top_mask);
/* 268 */           ((StaticSizeVideoView) this.mVideoPlayer).setSize(size.x, size.x);
/* 270 */           videoPlayerContainer.setOnClickListener(new OnSingleVideoClickedListener(this.mVideoPlayer));
/* 271 */           this.mVideoPlayer.setLooping(true);
/* 272 */           Rect rect = new Rect();
/* 273 */           Window window = getActivity().getWindow();
/* 274 */           window.getDecorView().getWindowVisibleDisplayFrame(rect);
/* 275 */           window.setSoftInputMode(16);
/* 277 */           View bottomMask = view.findViewById(C0257R.id.bottom_mask);
/* 278 */           if (this.mIsMessaging) {
/* 279 */               bottomMask.setVisibility(8);
                    } else {
/* 281 */               RelativeLayout.LayoutParams postButtonParams = (RelativeLayout.LayoutParams) this.mNextButton.getLayoutParams();
/* 283 */               int postButtonHeight = res.getDimensionPixelSize(C0257R.dimen.post_button_size);
/* 284 */               int doneButtonBottomMargin = ((((rect.bottom - rect.top) - size.x) - topMaskHeightPx) - postButtonHeight) / 2;
/* 286 */               postButtonParams.setMargins(0, 0, 0, doneButtonBottomMargin);
/* 287 */               this.mNextButton.setLayoutParams(postButtonParams);
/* 289 */               int bottomMaskHeightPx = ((rect.bottom - rect.top) - topMaskHeightPx) - size.x;
/* 290 */               RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) bottomMask.getLayoutParams();
/* 291 */               lp.height = bottomMaskHeightPx;
/* 292 */               bottomMask.setLayoutParams(lp);
/* 293 */               bottomMask.setOnTouchListener(new ViewOnTouchListenerC02676());
                    }
/* 300 */           animateInViews();
/* 301 */           return view;
                }

                /* JADX INFO: renamed from: co.vine.android.RecordingPreviewFragment$2 */
/* 185 */       class ViewOnClickListenerC02632 implements View.OnClickListener {
/* 186 */           ViewOnClickListenerC02632() {
                    }

                    @Override // android.view.View.OnClickListener
/* 189 */           public void onClick(View v) {
/* 190 */               RecordingPreviewFragment.access$002(RecordingPreviewFragment.this, (RecordingPreviewFragment.access$000(RecordingPreviewFragment.this) + 1) % RecordingPreviewFragment.access$100().length);
/* 191 */               RecordingPreviewFragment.access$200(RecordingPreviewFragment.this);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.RecordingPreviewFragment$3 */
/* 201 */       class ViewOnTouchListenerC02643 implements View.OnTouchListener {
/* 202 */           ViewOnTouchListenerC02643() {
                    }

                    @Override // android.view.View.OnTouchListener
/* 205 */           public boolean onTouch(View v, MotionEvent event) {
/* 206 */               return true;
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.RecordingPreviewFragment$4 */
/* 227 */       class ViewOnTouchListenerC02654 implements View.OnTouchListener {
/* 228 */           ViewOnTouchListenerC02654() {
                    }

                    @Override // android.view.View.OnTouchListener
/* 231 */           public boolean onTouch(View v, MotionEvent event) {
/* 232 */               return true;
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.RecordingPreviewFragment$5 */
/* 235 */       class ViewOnClickListenerC02665 implements View.OnClickListener {
/* 236 */           ViewOnClickListenerC02665() {
                    }

                    @Override // android.view.View.OnClickListener
/* 239 */           public void onClick(View v) {
/* 240 */               RecordingPreviewFragment.this.getActivity().onBackPressed();
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.RecordingPreviewFragment$6 */
/* 291 */       class ViewOnTouchListenerC02676 implements View.OnTouchListener {
/* 292 */           ViewOnTouchListenerC02676() {
                    }

                    @Override // android.view.View.OnTouchListener
/* 295 */           public boolean onTouch(View v, MotionEvent event) {
/* 296 */               return true;
                    }
                }

/* 304 */       private void updateMessageLoopLimit() {
/* 305 */           if (this.mMessagingLoopLimitText != null) {
/* 306 */               this.mMessagingLoopLimitText.setText("Loop Limit: " + (VALID_MAX_LOOPS[this.mMaxLoop] == 0 ? "Infinite" : Integer.valueOf(VALID_MAX_LOOPS[this.mMaxLoop])));
                    }
                }

/* 310 */       private void animateInViews() {
/* 311 */           if (!this.mIsMessaging) {
/* 312 */               this.mNextButton.setAlpha(0.0f);
/* 313 */               this.mNextButton.setVisibility(0);
/* 314 */               this.mNextButton.animate().setDuration(250L).alpha(1.0f).start();
                    }
/* 317 */           this.mPreviewButtons.setAlpha(0.0f);
/* 318 */           this.mPreviewButtons.setVisibility(0);
/* 319 */           this.mPreviewButtons.animate().setDuration(250L).alpha(1.0f).start();
                }

/* 322 */       private void animateOutViews() {
/* 323 */           this.mNextButton.animate().setDuration(250L).alpha(0.0f).start();
/* 324 */           this.mPreviewButtons.animate().setDuration(250L).alpha(0.0f).start();
                }

                @Override // android.view.View.OnClickListener
/* 328 */       public void onClick(View view) {
                    ArrayList<VineRecipient> recipients;
/* 329 */           AbstractRecordingActivity activity = (AbstractRecordingActivity) getActivity();
/* 330 */           if (activity != null) {
/* 333 */               if (this.mUploadPath == null) {
/* 334 */                   this.mUploadPath = activity.makeSureUploadIsReady();
                        }
/* 336 */               int id = view.getId();
/* 337 */               if (id == 2131427757) {
/* 338 */                   if (!AppController.getInstance(activity).isLoggedIn()) {
/* 339 */                       PromptDialogSupportFragment p = PromptDialogSupportFragment.newInstance(1);
/* 341 */                       p.setMessage(C0257R.string.share_login_to_post);
/* 342 */                       p.setPositiveButton(android.R.string.ok);
/* 343 */                       p.setNeutralButton(android.R.string.cancel);
/* 344 */                       p.setListener(this.mLoginRequiredDialogDoneListener);
/* 345 */                       p.show(getFragmentManager());
/* 396 */                       return;
                            }
/* 349 */                   toPost();
/* 350 */                   FlurryUtils.trackPreviewAction("post");
/* 396 */                   return;
                        }
/* 352 */               if (id == 2131427755) {
/* 353 */                   Editable text = this.mMessagingEditText.getText();
/* 354 */                   String message = text != null ? text.toString() : "";
/* 355 */                   if (this.mIsMessaging) {
                                if (this.mDirectUserId < 0 && this.mConversationRowId < 0) {
/* 357 */                           Intent intent = RecipientPickerActivity.getIntent(getActivity(), message, this.mUploadPath, this.mThumbnailPath, this.mVideoPath, VALID_MAX_LOOPS[this.mMaxLoop]);
/* 360 */                           activity.startActivityForResult(intent, 1991);
/* 361 */                           activity.startService(VineUploadService.getShowProgressIntent(getActivity()));
/* 396 */                           return;
                                }
                                if (this.mDirectUserId >= 0) {
/* 366 */                           recipients = new ArrayList<>();
/* 367 */                           recipients.add(VineRecipient.fromUser(this.mDirectUserId));
                                } else {
/* 369 */                           recipients = VineProviderHelper.getConversationRecipients(activity.getContentResolver(), this.mConversationRowId);
                                }
/* 372 */                       activity.startService(VineUploadService.getVMPostIntent(activity, this.mUploadPath, false, this.mConversationRowId, recipients, message, VALID_MAX_LOOPS[this.mMaxLoop]));
/* 375 */                       activity.startService(VineUploadService.getShowProgressIntent(activity));
/* 376 */                       activity.finish();
/* 396 */                       return;
                            }
/* 396 */                   return;
                        }
/* 379 */               if (id == 2131427754 || id == 2131427750) {
/* 380 */                   animateOutViews();
                            try {
/* 382 */                       previewToRecord((AbstractRecordingActivity) getActivity(), false);
/* 383 */                       FlurryUtils.trackPreviewAction(BasicVineRecorder.PREFS_CAPTURE);
/* 396 */                       return;
                            } catch (AbstractRecordingActivity.InvalidStateException e) {
/* 396 */                       return;
                            }
                        }
/* 387 */               if (id == 2131427752) {
/* 388 */                   animateOutViews();
                            try {
/* 390 */                       previewToRecord((AbstractRecordingActivity) getActivity(), true);
/* 391 */                       FlurryUtils.trackPreviewAction("edit");
                            } catch (AbstractRecordingActivity.InvalidStateException e2) {
                            }
                        }
                    }
                }

/* 398 */       public void previewToRecord(AbstractRecordingActivity activity, boolean startWithEdit) throws AbstractRecordingActivity.InvalidStateException {
/* 399 */           if (activity != null) {
/* 400 */               activity.toRecord(false, startWithEdit, this.mFinalFile);
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 405 */       public void onDestroy() {
/* 406 */           super.onDestroy();
/* 407 */           this.mFinalFile = null;
                }

/* 410 */       public void setFinalFile(RecordingFile finalFile) {
/* 411 */           this.mFinalFile = finalFile;
                }

                @Override // android.support.v4.app.Fragment
/* 415 */       public void onActivityResult(int requestCode, int resultCode, Intent data) {
                    switch (requestCode) {
                        case REQUEST_POST /* 1990 */:
                            switch (resultCode) {
                                case 32:
/* 430 */                           getActivity().finish();
                                    break;
                            }
                            break;
                        case REQUEST_CODE_LOGIN_TO_POST /* 12345 */:
                            switch (resultCode) {
                                case -1:
/* 420 */                           uploadAndToPost();
                                    break;
                            }
                            break;
                        default:
/* 437 */                   super.onActivityResult(requestCode, resultCode, data);
                            break;
                    }
                }

/* 448 */       public void uploadAndToPost() {
                    try {
/* 449 */               AbstractRecordingActivity activity = (AbstractRecordingActivity) getActivity();
/* 450 */               if (activity != null) {
/* 453 */                   if (BuildUtil.isIsHwEncodingEnabled()) {
/* 454 */                       activity.makeSureUploadIsReady();
                            }
/* 456 */                   this.mUploadPath = UploadManager.addToUploadQueue(activity, this.mFinalFile, this.mThumbnailPath, this.mFinalFile.folder.getName(), this.mIsMessaging, this.mConversationRowId);
/* 458 */                   toPost();
                        }
                    } catch (Exception e) {
/* 460 */               CrashUtil.logException(e);
                    }
                }

/* 464 */       public void toPost() {
/* 465 */           AbstractRecordingActivity activity = (AbstractRecordingActivity) getActivity();
/* 466 */           if (activity != null) {
/* 469 */               if (BuildUtil.isIsHwEncodingEnabled()) {
/* 470 */                   this.mUploadPath = activity.makeSureUploadIsReady();
                        }
/* 472 */               Intent intent = PostActivity.getIntent(getActivity(), this.mUploadPath, this.mThumbnailPath, this.mVideoPath, false, this.mIsFromSony);
/* 474 */               getActivity().startActivityForResult(intent, REQUEST_POST);
                    }
                }
            }
