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
            
            
    public class RecordingPreviewFragment extends BaseFragment implements View.OnClickListener {
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

        static  int access$000(RecordingPreviewFragment x0) {
            return x0.mMaxLoop;
                }

        static  int access$002(RecordingPreviewFragment x0, int x1) {
            x0.mMaxLoop = x1;
                    return x1;
                }

        static  int[] access$100() {
            return VALID_MAX_LOOPS;
                }

        static  void access$200(RecordingPreviewFragment x0) {
            x0.updateMessageLoopLimit();
                }

                
        class C02621 implements PromptDialogSupportFragment.OnDialogDoneListener {
            C02621() {
                    }

                    @Override 
            public void onDialogDone(DialogInterface dialog, int id, int which) {
                        switch (which) {
                            case -1:
                        Activity activity = RecordingPreviewFragment.this.getActivity();
                        Intent i = new Intent(activity, (Class<?>) SonyStartActivity.class);
                       i.putExtra(StartActivity.EXTRA_LOGIN_REQUEST, true);
                       activity.startActivityForResult(i, RecordingPreviewFragment.REQUEST_CODE_LOGIN_TO_POST);
                                break;
                        }
                    }
                }

                @Override 
       public void onPause() {
           super.onPause();
           if (this.mVideoPlayer.canPause()) {
               this.mVideoPlayer.pause();
                    }
                }

                @Override 
       public void onResume() {
           super.onResume();
           animateInViews();
           if (this.mVideoPlayer.getCurrentState() == 7) {
               this.mVideoPlayer.start();
                    }
                }

       public static RecordingPreviewFragment newInstance(String videoPath, String uploadPath, String thumbnailPath, boolean isMessaging, long conversationObjectId, long directUserId, boolean isFromSony, int color, String recipientUsername, boolean amFollowing) {
           RecordingPreviewFragment fragment = new RecordingPreviewFragment();
           Bundle bundle = new Bundle();
           bundle.putString("video_path", videoPath);
           bundle.putString("pic_path", thumbnailPath);
           bundle.putString("upload_path", uploadPath);
           bundle.putBoolean(ARG_IS_MESSAGING, isMessaging);
           bundle.putLong("conversation_row_id", conversationObjectId);
           bundle.putLong("direct_user_id", directUserId);
           bundle.putBoolean("f_s", isFromSony);
           bundle.putInt("color", color);
           bundle.putString("recipient_username", recipientUsername);
           bundle.putBoolean("am_following", amFollowing);
           fragment.setArguments(bundle);
           return fragment;
                }

                @Override 
       public void onCreate(Bundle savedInstanceState) {
           super.onActivityCreated(savedInstanceState);
           Bundle args = getArguments();
           this.mVideoPath = args.getString("video_path");
           this.mThumbnailPath = args.getString("pic_path");
           this.mUploadPath = args.getString("upload_path");
           this.mIsMessaging = args.getBoolean(ARG_IS_MESSAGING);
           this.mConversationRowId = args.getLong("conversation_row_id", -1L);
           this.mRecipientUsername = args.getString("recipient_username");
           this.mAmFollowing = args.getBoolean("am_following");
           this.mDirectUserId = args.getLong("direct_user_id", -1L);
           this.mIsFromSony = args.getBoolean("f_s");
           this.mColor = args.getInt("color", -1);
           if (this.mColor == Settings.DEFAULT_PROFILE_COLOR || this.mColor <= 0) {
               this.mColor = 16777215 & getResources().getColor(R.color.vine_green);
                    }
           this.mColor |= -16777216;
                }

                @Override 
       public void onActivityCreated(Bundle savedInstanceState) {
           super.onActivityCreated(savedInstanceState);
           RecordConfigUtils.deletePreProcess(getActivity().getFilesDir());
           this.mVideoPlayer.setVideoPath(this.mVideoPath);
           CrashUtil.log("Preview: {}", this.mVideoPath);
                }

                @Override 
       public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
           Resources res = getResources();
           View view = inflater.inflate(R.layout.preview, container, false);
           Point size = SystemUtil.getDisplaySize((Activity) getActivity());
           this.mMessagingEditText = (EditText) view.findViewById(R.id.messaging_preview_caption);
           this.mMessagingLoopLimitText = (Button) view.findViewById(R.id.messagingLoopLimit);
           this.mNextButton = view.findViewById(R.id.toPostButton);
           if (this.mIsMessaging) {
               this.mMaxLoop = 0;
               updateMessageLoopLimit();
               if (this.mMessagingLoopLimitText != null) {
                   this.mMessagingLoopLimitText.setOnClickListener(new ViewOnClickListenerC02632());
                   this.mMessagingLoopLimitText.setVisibility(8);
                        }
               this.mMessagingEditText.setVisibility(0);
               this.mPreviewButtons = view.findViewById(R.id.messagingPreviewTopButtons);
               this.mPreviewButtons.setVisibility(0);
               this.mPreviewButtons.setOnTouchListener(new ViewOnTouchListenerC02643());
               int vmBgColor = res.getColor(R.color.capture_background_vm);
               this.mPreviewButtons.setBackgroundColor(vmBgColor);
               view.setBackgroundColor(vmBgColor);
               View scrollView = view.findViewById(R.id.scrollview);
               scrollView.setBackgroundColor(vmBgColor);
               TypefacesTextView recipient = (TypefacesTextView) view.findViewById(R.id.recipient_name);
               recipient.setWeight(3);
               recipient.setTypeface(Typefaces.get(getActivity()).mediumContent);
               recipient.setVisibility(0);
               if (!TextUtils.isEmpty(this.mRecipientUsername)) {
                   recipient.setText(this.mRecipientUsername);
                        }
                    } else {
               this.mMessagingLoopLimitText.setVisibility(8);
               this.mMessagingEditText.setVisibility(8);
               this.mPreviewButtons = view.findViewById(R.id.previewTopButtons);
               this.mPreviewButtons.setVisibility(0);
               this.mPreviewButtons.setOnTouchListener(new ViewOnTouchListenerC02654());
               this.mNextButton.setVisibility(0);
                    }
           view.findViewById(R.id.previewTitle).setOnClickListener(new ViewOnClickListenerC02665());
           this.mNextButton.setOnClickListener(this);
           view.findViewById(R.id.previewCaptureButton).setOnClickListener(this);
           view.findViewById(R.id.previewEditButton).setOnClickListener(this);
           TextView nextButton = (TextView) view.findViewById(R.id.messagingPreviewNextButton);
           nextButton.setOnClickListener(this);
           if (this.mIsMessaging) {
               nextButton.setTextColor(this.mColor);
                    }
           View backButton = view.findViewById(R.id.messagingPreviewBackButton);
           backButton.setOnClickListener(this);
           if (this.mIsMessaging) {
               ViewUtil.fillColor(res, this.mColor, R.drawable.ic_arrow_back_large, backButton);
                    }
           this.mVideoPlayer = (SdkVideoView) view.findViewById(R.id.sdkVideoView);
           this.mVideoPlayer.setUseVinePlayer(false);
           this.mVideoPlayer.setVisibility(0);
           this.mVideoPlayer.setAutoPlayOnPrepared(true);
           this.mVideoPlayer.setKeepScreenOn(true);
           View videoPlayerContainer = view.findViewById(R.id.videoViewContainer);
           int topMaskHeightPx = res.getDimensionPixelSize(R.dimen.capture_top_mask);
           ((StaticSizeVideoView) this.mVideoPlayer).setSize(size.x, size.x);
           videoPlayerContainer.setOnClickListener(new OnSingleVideoClickedListener(this.mVideoPlayer));
           this.mVideoPlayer.setLooping(true);
           Rect rect = new Rect();
           Window window = getActivity().getWindow();
           window.getDecorView().getWindowVisibleDisplayFrame(rect);
           window.setSoftInputMode(16);
           View bottomMask = view.findViewById(R.id.bottom_mask);
           if (this.mIsMessaging) {
               bottomMask.setVisibility(8);
                    } else {
               RelativeLayout.LayoutParams postButtonParams = (RelativeLayout.LayoutParams) this.mNextButton.getLayoutParams();
               int postButtonHeight = res.getDimensionPixelSize(R.dimen.post_button_size);
               int doneButtonBottomMargin = ((((rect.bottom - rect.top) - size.x) - topMaskHeightPx) - postButtonHeight) / 2;
               postButtonParams.setMargins(0, 0, 0, doneButtonBottomMargin);
               this.mNextButton.setLayoutParams(postButtonParams);
               int bottomMaskHeightPx = ((rect.bottom - rect.top) - topMaskHeightPx) - size.x;
               RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) bottomMask.getLayoutParams();
               lp.height = bottomMaskHeightPx;
               bottomMask.setLayoutParams(lp);
               bottomMask.setOnTouchListener(new ViewOnTouchListenerC02676());
                    }
           animateInViews();
           return view;
                }

                
       class ViewOnClickListenerC02632 implements View.OnClickListener {
           ViewOnClickListenerC02632() {
                    }

                    @Override 
           public void onClick(View v) {
               RecordingPreviewFragment.access$002(RecordingPreviewFragment.this, (RecordingPreviewFragment.access$000(RecordingPreviewFragment.this) + 1) % RecordingPreviewFragment.access$100().length);
               RecordingPreviewFragment.access$200(RecordingPreviewFragment.this);
                    }
                }

                
       class ViewOnTouchListenerC02643 implements View.OnTouchListener {
           ViewOnTouchListenerC02643() {
                    }

                    @Override 
           public boolean onTouch(View v, MotionEvent event) {
               return true;
                    }
                }

                
       class ViewOnTouchListenerC02654 implements View.OnTouchListener {
           ViewOnTouchListenerC02654() {
                    }

                    @Override 
           public boolean onTouch(View v, MotionEvent event) {
               return true;
                    }
                }

                
       class ViewOnClickListenerC02665 implements View.OnClickListener {
           ViewOnClickListenerC02665() {
                    }

                    @Override 
           public void onClick(View v) {
               RecordingPreviewFragment.this.getActivity().onBackPressed();
                    }
                }

                
       class ViewOnTouchListenerC02676 implements View.OnTouchListener {
           ViewOnTouchListenerC02676() {
                    }

                    @Override 
           public boolean onTouch(View v, MotionEvent event) {
               return true;
                    }
                }

       private void updateMessageLoopLimit() {
           if (this.mMessagingLoopLimitText != null) {
               this.mMessagingLoopLimitText.setText("Loop Limit: " + (VALID_MAX_LOOPS[this.mMaxLoop] == 0 ? "Infinite" : Integer.valueOf(VALID_MAX_LOOPS[this.mMaxLoop])));
                    }
                }

       private void animateInViews() {
           if (!this.mIsMessaging) {
               this.mNextButton.setAlpha(0.0f);
               this.mNextButton.setVisibility(0);
               this.mNextButton.animate().setDuration(250L).alpha(1.0f).start();
                    }
           this.mPreviewButtons.setAlpha(0.0f);
           this.mPreviewButtons.setVisibility(0);
           this.mPreviewButtons.animate().setDuration(250L).alpha(1.0f).start();
                }

       private void animateOutViews() {
           this.mNextButton.animate().setDuration(250L).alpha(0.0f).start();
           this.mPreviewButtons.animate().setDuration(250L).alpha(0.0f).start();
                }

                @Override 
       public void onClick(View view) {
                    ArrayList<VineRecipient> recipients;
           AbstractRecordingActivity activity = (AbstractRecordingActivity) getActivity();
           if (activity != null) {
               if (this.mUploadPath == null) {
                   this.mUploadPath = activity.makeSureUploadIsReady();
                        }
               int id = view.getId();
               if (id == 2131427757) {
                   if (!AppController.getInstance(activity).isLoggedIn()) {
                       PromptDialogSupportFragment p = PromptDialogSupportFragment.newInstance(1);
                       p.setMessage(R.string.share_login_to_post);
                       p.setPositiveButton(android.R.string.ok);
                       p.setNeutralButton(android.R.string.cancel);
                       p.setListener(this.mLoginRequiredDialogDoneListener);
                       p.show(getFragmentManager());
                       return;
                            }
                   toPost();
                   FlurryUtils.trackPreviewAction("post");
                   return;
                        }
               if (id == 2131427755) {
                   Editable text = this.mMessagingEditText.getText();
                   String message = text != null ? text.toString() : "";
                   if (this.mIsMessaging) {
                                if (this.mDirectUserId < 0 && this.mConversationRowId < 0) {
                           Intent intent = RecipientPickerActivity.getIntent(getActivity(), message, this.mUploadPath, this.mThumbnailPath, this.mVideoPath, VALID_MAX_LOOPS[this.mMaxLoop]);
                           activity.startActivityForResult(intent, 1991);
                           activity.startService(VineUploadService.getShowProgressIntent(getActivity()));
                           return;
                                }
                                if (this.mDirectUserId >= 0) {
                           recipients = new ArrayList<>();
                           recipients.add(VineRecipient.fromUser(this.mDirectUserId));
                                } else {
                           recipients = VineProviderHelper.getConversationRecipients(activity.getContentResolver(), this.mConversationRowId);
                                }
                       activity.startService(VineUploadService.getVMPostIntent(activity, this.mUploadPath, false, this.mConversationRowId, recipients, message, VALID_MAX_LOOPS[this.mMaxLoop]));
                       activity.startService(VineUploadService.getShowProgressIntent(activity));
                       activity.finish();
                       return;
                            }
                   return;
                        }
               if (id == 2131427754 || id == 2131427750) {
                   animateOutViews();
                            try {
                       previewToRecord((AbstractRecordingActivity) getActivity(), false);
                       FlurryUtils.trackPreviewAction(BasicVineRecorder.PREFS_CAPTURE);
                       return;
                            } catch (AbstractRecordingActivity.InvalidStateException e) {
                       return;
                            }
                        }
               if (id == 2131427752) {
                   animateOutViews();
                            try {
                       previewToRecord((AbstractRecordingActivity) getActivity(), true);
                       FlurryUtils.trackPreviewAction("edit");
                            } catch (AbstractRecordingActivity.InvalidStateException e2) {
                            }
                        }
                    }
                }

       public void previewToRecord(AbstractRecordingActivity activity, boolean startWithEdit) throws AbstractRecordingActivity.InvalidStateException {
           if (activity != null) {
               activity.toRecord(false, startWithEdit, this.mFinalFile);
                    }
                }

                @Override 
       public void onDestroy() {
           super.onDestroy();
           this.mFinalFile = null;
                }

       public void setFinalFile(RecordingFile finalFile) {
           this.mFinalFile = finalFile;
                }

                @Override 
       public void onActivityResult(int requestCode, int resultCode, Intent data) {
                    switch (requestCode) {
                        case REQUEST_POST :
                            switch (resultCode) {
                                case 32:
                           getActivity().finish();
                                    break;
                            }
                            break;
                        case REQUEST_CODE_LOGIN_TO_POST :
                            switch (resultCode) {
                                case -1:
                           uploadAndToPost();
                                    break;
                            }
                            break;
                        default:
                   super.onActivityResult(requestCode, resultCode, data);
                            break;
                    }
                }

       public void uploadAndToPost() {
                    try {
               AbstractRecordingActivity activity = (AbstractRecordingActivity) getActivity();
               if (activity != null) {
                   if (BuildUtil.isIsHwEncodingEnabled()) {
                       activity.makeSureUploadIsReady();
                            }
                   this.mUploadPath = UploadManager.addToUploadQueue(activity, this.mFinalFile, this.mThumbnailPath, this.mFinalFile.folder.getName(), this.mIsMessaging, this.mConversationRowId);
                   toPost();
                        }
                    } catch (Exception e) {
               CrashUtil.logException(e);
                    }
                }

       public void toPost() {
           AbstractRecordingActivity activity = (AbstractRecordingActivity) getActivity();
           if (activity != null) {
               if (BuildUtil.isIsHwEncodingEnabled()) {
                   this.mUploadPath = activity.makeSureUploadIsReady();
                        }
               Intent intent = PostActivity.getIntent(getActivity(), this.mUploadPath, this.mThumbnailPath, this.mVideoPath, false, this.mIsFromSony);
               getActivity().startActivityForResult(intent, REQUEST_POST);
                    }
                }
            }
