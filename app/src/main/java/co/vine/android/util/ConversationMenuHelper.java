            package co.vine.android.util;

            import android.os.Handler;
            import android.os.Looper;
            import android.os.Message;
            import android.support.v4.app.Fragment;
            import android.support.v4.app.FragmentManager;
            import android.support.v4.app.FragmentTransaction;
            import android.text.Editable;
            import android.text.TextUtils;
            import android.text.TextWatcher;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.AdapterView;
            import android.widget.EditText;
            import android.widget.ListAdapter;
            import android.widget.ProgressBar;
            import android.widget.TextView;
            import co.vine.android.AbstractRecordingActivity;
            import co.vine.android.BaseControllerActionBarActivity;
            import co.vine.android.C0257R;
            import co.vine.android.ConversationActivity;
            import co.vine.android.FriendSearchAdapter;
            import co.vine.android.InboxFragment;
            import co.vine.android.Settings;
            import co.vine.android.api.VineUser;
            import co.vine.android.cache.image.ImageKey;
            import co.vine.android.cache.image.UrlImage;
            import co.vine.android.client.AppController;
            import co.vine.android.client.AppSessionListener;
            import co.vine.android.views.TouchableRelativeLayout;
            import com.jeremyfeinstein.slidingmenu.lib.SlidingActivityHelper;
            import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
            import com.twitter.android.widget.RefreshableListView;
            import java.util.ArrayList;
            import java.util.HashMap;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 42 */    public class ConversationMenuHelper extends SlidingActivityHelper implements TextWatcher, View.OnClickListener, Handler.Callback, AdapterView.OnItemClickListener {
                private static final long FETCH_THRESHOLD_MS = 200;
                private static final int MSG_SEARCH_USERS = 1;
                private static final int QUERY_FETCH_THRESHOLD = 2;
                private static final String TAG_INBOX_FRAGMENT = "Inbox";
                private ViewGroup mActionContainer;
                private final BaseControllerActionBarActivity mActivity;
                private AppController mAppController;
                private final ConversationMenuHelperAppSessionListener mAppSessionListener;
                private FriendSearchAdapter mFriendSearchAdapter;
                private Handler mHandler;
                private InboxFragment mInboxFragment;
                private boolean mIsSearching;
                private View mNothingFoundText;
                private TextView mOthersText;
                private int mProfileColor;
                private View mSadFace;
                private ViewGroup mSearchContainer;
                private EditText mSearchField;
                private ProgressBar mSearchInProgress;
                private View mSearchOnBackgroundView;
                private RefreshableListView mSearchResultView;
                private long mWaitingToStartSearchUserId;
                private long mWaitingToStartUserId;

/* 43 */        static /* synthetic */ long access$000(ConversationMenuHelper x0) {
/* 44 */            return x0.mWaitingToStartUserId;
                }

/* 43 */        static /* synthetic */ long access$002(ConversationMenuHelper x0, long x1) {
/* 44 */            x0.mWaitingToStartUserId = x1;
                    return x1;
                }

/* 43 */        static /* synthetic */ long access$100(ConversationMenuHelper x0) {
/* 44 */            return x0.mWaitingToStartSearchUserId;
                }

/* 43 */        static /* synthetic */ AppController access$200(ConversationMenuHelper x0) {
/* 44 */            return x0.mAppController;
                }

/* 43 */        static /* synthetic */ BaseControllerActionBarActivity access$300(ConversationMenuHelper x0) {
/* 44 */            return x0.mActivity;
                }

/* 43 */        static /* synthetic */ boolean access$400(ConversationMenuHelper x0) {
/* 44 */            return x0.mIsSearching;
                }

/* 43 */        static /* synthetic */ FriendSearchAdapter access$500(ConversationMenuHelper x0) {
/* 44 */            return x0.mFriendSearchAdapter;
                }

/* 43 */        static /* synthetic */ void access$600(ConversationMenuHelper x0, ArrayList x1) {
/* 44 */            x0.displaySearchResults(x1);
                }

/* 76 */        public ConversationMenuHelper(BaseControllerActionBarActivity activity) {
/* 77 */            super(activity);
/* 66 */            this.mWaitingToStartUserId = -1L;
/* 73 */            this.mWaitingToStartSearchUserId = -1L;
/* 78 */            this.mActivity = activity;
/* 79 */            this.mAppSessionListener = new ConversationMenuHelperAppSessionListener();
                }

/* 82 */        public void setupConversationSlidingMenu() {
/* 83 */            this.mAppController = AppController.getInstance(this.mActivity);
/* 84 */            View view = this.mActivity.setBehindContentView(C0257R.layout.conversation_menu);
/* 85 */            this.mSearchField = (EditText) view.findViewById(C0257R.id.search_field);
/* 86 */            this.mSearchField.addTextChangedListener(this);
/* 87 */            this.mActionContainer = (ViewGroup) view.findViewById(C0257R.id.menu_action_container);
/* 88 */            setPersonalizedColor(Util.getProfileColor(this.mActivity));
/* 89 */            this.mSearchContainer = (ViewGroup) view.findViewById(C0257R.id.menu_search_container);
/* 90 */            this.mSearchResultView = (RefreshableListView) view.findViewById(C0257R.id.search_result);
/* 91 */            this.mSearchResultView.setOnItemClickListener(this);
/* 92 */            this.mSearchOnBackgroundView = view.findViewById(C0257R.id.saerch_toggle_background_view);
/* 93 */            this.mSearchOnBackgroundView.setOnClickListener(new ViewOnClickListenerC05271());
/* 99 */            this.mSadFace = view.findViewById(C0257R.id.search_real_sadface);
/* 100 */           this.mNothingFoundText = view.findViewById(C0257R.id.search_empty_text);
/* 101 */           view.findViewById(C0257R.id.menu_vm_capture).setOnClickListener(this);
/* 102 */           view.findViewById(C0257R.id.menu_search).setOnClickListener(this);
/* 103 */           view.findViewById(C0257R.id.clear_search).setOnClickListener(this);
/* 104 */           ViewUtil.setActionBarHeight(this.mActivity, view.findViewById(C0257R.id.menu_list_header));
/* 105 */           this.mSearchInProgress = (ProgressBar) view.findViewById(C0257R.id.search_in_progress);
/* 106 */           setSlidingActionBarEnabled(true);
/* 107 */           SlidingMenu sm = getSlidingMenu();
/* 108 */           sm.setShadowWidth(35);
/* 109 */           sm.setBehindOffset(this.mActivity.getResources().getDimensionPixelSize(C0257R.dimen.sliding_menu_offset));
/* 110 */           sm.setTouchModeAbove(1);
/* 112 */           ((TouchableRelativeLayout) this.mActivity.findViewById(C0257R.id.menu_content)).setTouchListener(new ViewBehindTouchListener(this.mActivity, sm));
/* 114 */           FragmentManager fm = this.mActivity.getSupportFragmentManager();
/* 116 */           Fragment frag = fm.findFragmentByTag(TAG_INBOX_FRAGMENT);
/* 118 */           if (frag == null) {
/* 119 */               this.mInboxFragment = new InboxFragment();
/* 120 */               FragmentTransaction ft = fm.beginTransaction();
/* 121 */               ft.add(C0257R.id.menu_content, this.mInboxFragment, TAG_INBOX_FRAGMENT);
/* 122 */               ft.commit();
                    } else {
/* 124 */               this.mInboxFragment = (InboxFragment) frag;
                    }
/* 126 */           sm.setOnClosedListener(this.mInboxFragment);
/* 127 */           sm.setOnOpenedListener(this.mInboxFragment);
/* 129 */           this.mHandler = new Handler(Looper.getMainLooper(), this);
                }

                /* JADX INFO: renamed from: co.vine.android.util.ConversationMenuHelper$1 */
/* 91 */        class ViewOnClickListenerC05271 implements View.OnClickListener {
/* 92 */            ViewOnClickListenerC05271() {
                    }

                    @Override // android.view.View.OnClickListener
/* 95 */            public void onClick(View v) {
/* 96 */                ConversationMenuHelper.this.toggleContactSearch(false);
                    }
                }

/* 132 */       public TextView getOthersText() {
/* 133 */           return this.mOthersText;
                }

                @Override // android.view.View.OnClickListener
/* 137 */       public void onClick(View view) {
/* 138 */           int id = view.getId();
/* 139 */           if (id == C0257R.id.menu_vm_capture) {
/* 140 */               Util.startActionOnRecordingAvailable(this.mActivity, AbstractRecordingActivity.getIntentForMessaging(this.mActivity, 0, "New VM"), 0);
                    } else if (id == C0257R.id.menu_search) {
/* 143 */               toggleContactSearch(true);
                    } else if (id == C0257R.id.clear_search) {
/* 145 */               toggleContactSearch(false);
                    }
                }

                @Override // android.text.TextWatcher
/* 151 */       public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override // android.os.Handler.Callback
/* 155 */       public boolean handleMessage(Message message) {
                    switch (message.what) {
                        case 1:
/* 158 */                   this.mAppController.fetchFriendsTypeAhead(String.valueOf(message.obj));
                            break;
                    }
/* 162 */           return false;
                }

                @Override // android.text.TextWatcher
/* 166 */       public void onTextChanged(CharSequence s, int start, int before, int count) {
/* 167 */           this.mHandler.removeMessages(1);
/* 168 */           String query = s.toString().toLowerCase();
/* 169 */           if (!TextUtils.isEmpty(query) && s.length() >= 2) {
/* 170 */               Message msg = Message.obtain(null, 1, query);
/* 171 */               if (msg != null) {
/* 172 */                   showSearchProgressBar(true);
/* 173 */                   this.mHandler.sendMessageDelayed(msg, 200L);
/* 174 */                   this.mIsSearching = true;
/* 182 */                   return;
                        }
/* 182 */               return;
                    }
/* 176 */           if (this.mIsSearching) {
/* 177 */               showSearchProgressBar(false);
/* 178 */               this.mSearchResultView.setVisibility(8);
/* 179 */               this.mSadFace.setVisibility(8);
/* 180 */               this.mIsSearching = false;
                    }
                }

                @Override // android.text.TextWatcher
/* 186 */       public void afterTextChanged(Editable s) {
                }

                @Override // android.widget.AdapterView.OnItemClickListener
/* 190 */       public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
/* 191 */           long userRemoteId = this.mFriendSearchAdapter.getItemId(position);
/* 192 */           this.mWaitingToStartSearchUserId = userRemoteId;
                    if (userRemoteId < 1) {
/* 194 */               throw new IllegalStateException("The userRemoteId of the clicked search result item is invalid");
                    }
/* 199 */           this.mAppController.fetchConversationRowIdFromUserRemoteId(userRemoteId, 1);
                }

/* 202 */       private void displaySearchResults(ArrayList<VineUser> users) {
/* 203 */           if (users.isEmpty()) {
/* 204 */               this.mSearchResultView.setVisibility(8);
/* 205 */               this.mSadFace.setVisibility(0);
/* 206 */               this.mNothingFoundText.setVisibility(0);
/* 221 */               return;
                    }
/* 208 */           this.mSadFace.setVisibility(8);
/* 209 */           if (this.mFriendSearchAdapter == null) {
/* 210 */               this.mFriendSearchAdapter = new FriendSearchAdapter(this.mActivity, this.mAppController, users);
/* 212 */               this.mSearchResultView.setAdapter((ListAdapter) this.mFriendSearchAdapter);
                    } else {
/* 214 */               FriendSearchAdapter adapter = this.mFriendSearchAdapter;
/* 215 */               adapter.clear();
/* 216 */               adapter.setData(users);
/* 217 */               adapter.notifyDataSetChanged();
                    }
/* 219 */           this.mSearchResultView.setVisibility(0);
                }

/* 223 */       public void showSearchProgressBar(boolean show) {
/* 224 */           this.mSearchInProgress.setVisibility(show ? 0 : 8);
                }

/* 227 */       public void toggleContactSearch(boolean show) {
/* 228 */           this.mSearchOnBackgroundView.setVisibility(show ? 0 : 4);
/* 229 */           this.mSearchContainer.setVisibility(show ? 0 : 4);
/* 230 */           this.mActionContainer.setVisibility(show ? 4 : 0);
/* 231 */           if (show) {
/* 232 */               this.mSearchField.requestFocus();
                    } else {
/* 234 */               this.mSearchField.clearFocus();
/* 235 */               this.mSearchField.setText("");
                    }
/* 237 */           Util.setSoftKeyboardVisibility(this.mActivity, this.mSearchField, show);
                }

/* 240 */       public void onPause() {
/* 241 */           this.mAppController.removeListener(this.mAppSessionListener);
                }

/* 244 */       public void onResume() {
/* 245 */           SlidingMenu sm = getSlidingMenu();
/* 246 */           if (sm.isMenuShowing()) {
/* 247 */               sm.showContent(false);
                    }
/* 249 */           this.mAppController.addListener(this.mAppSessionListener);
                }

/* 252 */       public void setPersonalizedColor(int profileBackground) {
/* 253 */           if (profileBackground == Settings.DEFAULT_PROFILE_COLOR || profileBackground <= 0) {
/* 254 */               profileBackground = 16777215 & this.mActivity.getResources().getColor(C0257R.color.vine_green);
                    }
/* 256 */           this.mProfileColor = (-16777216) | profileBackground;
/* 257 */           this.mActionContainer.setBackgroundColor(this.mProfileColor);
/* 258 */           if (this.mInboxFragment != null) {
/* 259 */               this.mInboxFragment.setPersonalizedColor(profileBackground);
                    }
                }

/* 261 */       public class ConversationMenuHelperAppSessionListener extends AppSessionListener {
/* 262 */           public ConversationMenuHelperAppSessionListener() {
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 267 */           public void onGetConversationRowIdComplete(long recipientId, boolean isRecipientExternal, long conversationObjectId, String username, boolean amFollowing) {
                        if (recipientId == ConversationMenuHelper.access$000(ConversationMenuHelper.this) || recipientId == ConversationMenuHelper.access$100(ConversationMenuHelper.this)) {
                            if (recipientId == ConversationMenuHelper.access$100(ConversationMenuHelper.this)) {
/* 270 */                       ConversationMenuHelper.this.toggleContactSearch(false);
                            }
/* 272 */                   ConversationMenuHelper.access$002(ConversationMenuHelper.this, -1L);
/* 273 */                   ConversationMenuHelper.access$200(ConversationMenuHelper.this).clearUnreadMessageCount(conversationObjectId);
/* 274 */                   ConversationMenuHelper.access$300(ConversationMenuHelper.this).startActivity(ConversationActivity.getIntent(ConversationMenuHelper.access$300(ConversationMenuHelper.this), conversationObjectId, username, recipientId, isRecipientExternal, amFollowing, false));
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 280 */           public void onPhotoImageLoaded(HashMap<ImageKey, UrlImage> images) {
/* 281 */               if (ConversationMenuHelper.access$400(ConversationMenuHelper.this) && ConversationMenuHelper.access$500(ConversationMenuHelper.this) != null) {
/* 283 */                   ConversationMenuHelper.access$500(ConversationMenuHelper.this).setUserImages(images);
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 290 */           public void onGetFriendsTypeAheadComplete(String reqId, int statusCode, String reasonPhrase, String query, ArrayList<VineUser> users) {
/* 291 */               ConversationMenuHelper.this.showSearchProgressBar(false);
/* 292 */               if (users != null && ConversationMenuHelper.access$400(ConversationMenuHelper.this)) {
/* 293 */                   ConversationMenuHelper.access$600(ConversationMenuHelper.this, users);
                        }
                    }
                }
            }
