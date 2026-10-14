            package co.vine.android;

            import android.app.Activity;
            import android.app.ProgressDialog;
            import android.content.DialogInterface;
            import android.content.Intent;
            import android.graphics.PorterDuff;
            import android.os.Bundle;
            import android.support.v4.widget.CursorAdapter;
            import android.support.v7.app.ActionBarActivity;
            import android.support.v7.view.ActionMode;
            import android.text.Editable;
            import android.text.TextUtils;
            import android.text.TextWatcher;
            import android.view.KeyEvent;
            import android.view.LayoutInflater;
            import android.view.Menu;
            import android.view.MenuInflater;
            import android.view.MenuItem;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.AdapterView;
            import android.widget.EditText;
            import android.widget.Filter;
            import android.widget.Filterable;
            import android.widget.ImageView;
            import android.widget.ListAdapter;
            import android.widget.ListView;
            import android.widget.TextView;
            import co.vine.android.api.VineComment;
            import co.vine.android.api.VineEntity;
            import co.vine.android.api.VineTag;
            import co.vine.android.api.VineUser;
            import co.vine.android.cache.image.ImageKey;
            import co.vine.android.cache.image.UrlImage;
            import co.vine.android.client.AppSessionListener;
            import co.vine.android.provider.VineSuggestionsProvider;
            import co.vine.android.service.PendingAction;
            import co.vine.android.util.CommonUtil;
            import co.vine.android.util.CrossConstants;
            import co.vine.android.util.Util;
            import co.vine.android.widget.ConversationList;
            import co.vine.android.widget.PopupEditText;
            import co.vine.android.widget.SpanClickListener;
            import co.vine.android.widgets.PromptDialogSupportFragment;
            import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
            import java.text.SimpleDateFormat;
            import java.util.ArrayList;
            import java.util.HashMap;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 52 */    public class CommentsFragment extends BaseArrayListFragment implements SpanClickListener, View.OnClickListener, AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener, TextView.OnEditorActionListener, Filterable, PopupEditText.PopupEditTextListener, ConversationList.GetMoreListener, TextWatcher {
                public static final String ARG_HIDE_KEYBOARD = "hide_keyboard";
                public static final String ARG_POST_AUTHOR_ID = "post_author_id";
                public static final String ARG_POST_ID = "post_id";
                public static final String ARG_REPOST_ID = "repost_id";
                public static final int DEFAULT_PAGE_SIZE = 20;
                private static final String EVENT_SOURCE_TITLE = "Comments List";
                private static final int MAX_CHARS = 140;
                public static final int REQUEST_REPORTING_MENU = 0;
                private static final String STATE_FETCHED = "fetched";
                private static ActionMode mActionMode;
                private static String mSelectedCommentId;
                private CommentActionCallback mDeleteActionModeCallback;
                private PopupEditText mEdit;
                private boolean mFetched;
                private Filter mFilter;
                private long mPostAuthorId;
                private long mPostId;
                private long mRepostId;
                private long mSelectedUserId;
                private String mSelectedUsername;
                private View mSelectedView;
                private ImageView mSendButton;
                private CommentActionCallback mSpamActionModeCallback;
                private TagsAutoCompleteAdapter mTagsAdapter;
                private ComposeTokenizer mTokenizer;
                private CursorAdapter mTypeaheadAdapter;
                private UsersAutoCompleteAdapter mUsersAdapter;
                private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat();
                private long mLastTopItemId = -1;
                private int mLastTopItemPixelOffset = 0;
                private long mAnchor = 0;
                private int mNextPage = 1;
                private final PromptDialogSupportFragment.OnDialogDoneListener mBlockDoneListener = new C01631();

                private enum CommentActionType {
                    DELETE_ACTION,
                    SPAM_ACTION
                }

/* 53 */        static /* synthetic */ void access$000(CommentsFragment x0) {
/* 54 */            x0.showReportingMenu();
                }

/* 53 */        static /* synthetic */ void access$100(CommentsFragment x0) {
/* 54 */            x0.createReply();
                }

/* 53 */        static /* synthetic */ void access$1000(CommentsFragment x0) {
/* 54 */            x0.saveTopItemInfo();
                }

/* 53 */        static /* synthetic */ long access$1100(CommentsFragment x0) {
/* 54 */            return x0.mLastTopItemId;
                }

/* 53 */        static /* synthetic */ int access$1200(CommentsFragment x0) {
/* 54 */            return x0.mLastTopItemPixelOffset;
                }

/* 53 */        static /* synthetic */ boolean access$1300(CommentsFragment x0) {
/* 54 */            return x0.mFetched;
                }

/* 53 */        static /* synthetic */ UsersAutoCompleteAdapter access$1400(CommentsFragment x0) {
/* 54 */            return x0.mUsersAdapter;
                }

/* 53 */        static /* synthetic */ PopupEditText access$1500(CommentsFragment x0) {
/* 54 */            return x0.mEdit;
                }

/* 53 */        static /* synthetic */ void access$1600(CommentsFragment x0, int x1) {
/* 54 */            x0.showBlockUserDialog(x1);
                }

/* 53 */        static /* synthetic */ View access$200(CommentsFragment x0) {
/* 54 */            return x0.mSelectedView;
                }

/* 53 */        static /* synthetic */ ActionMode access$302(ActionMode x0) {
/* 54 */            mActionMode = x0;
                    return x0;
                }

/* 53 */        static /* synthetic */ long access$500(CommentsFragment x0) {
/* 54 */            return x0.mPostId;
                }

/* 53 */        static /* synthetic */ long access$600(CommentsFragment x0) {
/* 54 */            return x0.mRepostId;
                }

/* 53 */        static /* synthetic */ String access$700() {
/* 54 */            return mSelectedCommentId;
                }

/* 53 */        static /* synthetic */ int access$800(CommentsFragment x0) {
/* 54 */            return x0.mNextPage;
                }

/* 53 */        static /* synthetic */ int access$802(CommentsFragment x0, int x1) {
/* 54 */            x0.mNextPage = x1;
                    return x1;
                }

/* 53 */        static /* synthetic */ long access$902(CommentsFragment x0, long x1) {
/* 54 */            x0.mAnchor = x1;
                    return x1;
                }

                /* JADX INFO: renamed from: co.vine.android.CommentsFragment$1 */
/* 97 */        class C01631 implements PromptDialogSupportFragment.OnDialogDoneListener {
/* 98 */            C01631() {
                    }

                    @Override // co.vine.android.widgets.PromptDialogFragment.OnDialogDoneListener
/* 100 */           public void onDialogDone(DialogInterface dialogInterface, int id, int which) {
                        switch (which) {
                            case -2:
/* 103 */                       CommentsFragment.access$000(CommentsFragment.this);
                                break;
                            case -1:
/* 107 */                       dialogInterface.dismiss();
                                break;
                        }
                    }
                }

/* 114 */       private void showBlockUserDialog(int messageId) {
/* 115 */           PromptDialogSupportFragment p = PromptDialogSupportFragment.newInstance(0);
/* 117 */           p.setListener(this.mBlockDoneListener);
/* 118 */           p.setMessage(messageId);
/* 119 */           p.setPositiveButton(C0257R.string.f87no);
/* 120 */           p.setNegativeButton(C0257R.string.yes);
/* 121 */           p.show(getFragmentManager());
                }

                @Override // co.vine.android.BaseArrayListFragment, co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, android.support.v4.app.Fragment
/* 126 */       public void onCreate(Bundle savedInstanceState) {
/* 127 */           super.onCreate(savedInstanceState);
/* 128 */           this.DATE_FORMAT.applyPattern(getResources().getString(C0257R.string.datetime_format_long));
/* 130 */           if (savedInstanceState != null && savedInstanceState.containsKey(STATE_FETCHED)) {
/* 132 */               this.mFetched = savedInstanceState.getBoolean(STATE_FETCHED);
                    }
/* 136 */           this.mAppSessionListener = new CommentsAppSessionListener();
/* 137 */           this.mUsersAdapter = new UsersAutoCompleteAdapter(getActivity(), this.mAppController);
/* 138 */           this.mTagsAdapter = new TagsAutoCompleteAdapter(getActivity(), this.mAppController);
/* 139 */           this.mTypeaheadAdapter = this.mTagsAdapter;
/* 140 */           this.mDeleteActionModeCallback = createCommentActionCallback(CommentActionType.DELETE_ACTION);
/* 141 */           this.mSpamActionModeCallback = createCommentActionCallback(CommentActionType.SPAM_ACTION);
/* 143 */           setHasOptionsMenu(true);
                }

                @Override // co.vine.android.BaseArrayListFragment, android.support.v4.app.Fragment
/* 148 */       public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
/* 149 */           return createView(inflater, C0257R.layout.comments_thread, container);
                }

                @Override // co.vine.android.BaseArrayListFragment, android.support.v4.app.Fragment
/* 153 */       public void onViewCreated(View view, Bundle savedInstanceState) {
/* 154 */           super.onViewCreated(view, savedInstanceState);
/* 155 */           PopupEditText edit = (PopupEditText) view.findViewById(C0257R.id.edit_reply);
/* 156 */           edit.setOnEditorActionListener(this);
/* 157 */           edit.setAdapter(this.mTypeaheadAdapter);
/* 158 */           this.mTokenizer = new ComposeTokenizer(this);
/* 159 */           edit.setTokenizer(this.mTokenizer, this, this.mAppController.getTypeaheadThrottle());
/* 160 */           edit.setPopupEditTextListener(this);
/* 161 */           this.mEdit = edit;
/* 162 */           this.mEdit.addTextChangedListener(this);
/* 163 */           Bundle args = getArguments();
/* 164 */           this.mPostId = args.getLong("post_id");
/* 165 */           this.mRepostId = args.getLong("repost_id");
/* 166 */           this.mPostAuthorId = args.getLong("post_author_id");
/* 167 */           this.mSendButton = (ImageView) view.findViewById(C0257R.id.send_button);
/* 168 */           this.mSendButton.setOnClickListener(this);
/* 169 */           this.mSendButton.setColorFilter((-16777216) | getResources().getColor(C0257R.color.btn_pressed_fill), PorterDuff.Mode.SRC_ATOP);
/* 170 */           this.mListView.setOnItemLongClickListener(this);
/* 171 */           this.mListView.setOnItemClickListener(this);
/* 172 */           this.mAdapter = new CommentsAdapter(getActivity(), this.mPostId, this.mAppController, this);
/* 173 */           ((ConversationList) this.mListView).setGetMoreListener(this);
/* 174 */           this.mListView.setAdapter((ListAdapter) this.mAdapter);
                }

                @Override // android.support.v4.app.Fragment
/* 178 */       public void onActivityCreated(Bundle savedInstanceState) {
/* 179 */           super.onActivityCreated(savedInstanceState);
/* 180 */           boolean hideKeyboard = getArguments().getBoolean("hide_keyboard", false);
/* 181 */           if (!hideKeyboard) {
/* 182 */               this.mEdit.requestFocus();
/* 183 */               Util.setSoftKeyboardVisibility(getActivity(), this.mEdit, true);
                    }
                }

                @Override // co.vine.android.BaseArrayListFragment, android.support.v4.app.Fragment
/* 188 */       public void onActivityResult(int requestCode, int resultCode, Intent data) {
/* 189 */           super.onActivityResult(requestCode, resultCode, data);
/* 190 */           if (data != null && requestCode == 0) {
/* 191 */               long commentId = data.getLongExtra(ReportingFragment.EXTRA_COMMENT_ID, -1L);
/* 192 */               String event = data.getStringExtra("event");
/* 193 */               boolean blocked = data.getBooleanExtra("blocked", false);
                        if (commentId != -1 && !TextUtils.isEmpty(event)) {
/* 195 */                   ((CommentsAdapter) this.mAdapter).spamComment(commentId);
/* 196 */                   if (this.mAdapter.getCount() == 0) {
/* 197 */                       showSadface(true, false);
/* 203 */                       return;
                            }
/* 203 */                   return;
                        }
/* 199 */               if (blocked) {
/* 200 */                   Util.showDefaultToast(getActivity(), getString(C0257R.string.user_blocked, this.mSelectedUsername));
                        }
                    }
                }

                @Override // co.vine.android.BaseArrayListFragment, co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, co.vine.android.BaseFragment, android.support.v4.app.Fragment
/* 206 */       public void onResume() {
/* 207 */           super.onResume();
/* 208 */           if (this.mAdapter.isEmpty() && !this.mFetched) {
/* 209 */               fetchContent();
                    }
                }

                @Override // android.support.v4.app.Fragment
/* 214 */       public void onDestroy() {
/* 215 */           super.onDestroy();
/* 216 */           VineSuggestionsProvider.clearRealtimeUserSuggestions();
/* 217 */           VineSuggestionsProvider.clearRealtimeTagSuggestions();
                }

                @Override // android.support.v4.app.Fragment
/* 221 */       public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
/* 222 */           inflater.inflate(C0257R.menu.comments, menu);
/* 223 */           super.onCreateOptionsMenu(menu, inflater);
                }

                @Override // android.support.v4.app.Fragment
/* 227 */       public boolean onOptionsItemSelected(MenuItem item) {
/* 228 */           int id = item.getItemId();
/* 229 */           if (id == C0257R.id.mention) {
/* 230 */               insertText("@");
                    } else if (id == C0257R.id.tag) {
/* 232 */               insertText("#");
                    } else {
/* 234 */               return super.onOptionsItemSelected(item);
                    }
/* 236 */           return true;
                }

/* 239 */       private void insertText(String toInsert) {
/* 240 */           if (this.mEdit != null) {
/* 241 */               int selStart = this.mEdit.getSelectionStart();
/* 242 */               int selEnd = this.mEdit.getSelectionEnd();
/* 243 */               if (this.mEdit.getText() != null && selStart >= 0 && selEnd <= this.mEdit.length()) {
/* 244 */                   this.mEdit.getText().replace(selStart, selEnd, toInsert);
                        }
                    }
                }

                @Override // co.vine.android.BaseArrayListFragment, co.vine.android.BaseAdapterFragment, android.support.v4.app.Fragment
/* 250 */       public void onSaveInstanceState(Bundle outState) {
/* 251 */           super.onSaveInstanceState(outState);
/* 252 */           outState.putBoolean(STATE_FETCHED, this.mFetched);
                }

/* 255 */       private void fetchContent() {
/* 256 */           if (!hasPendingRequest()) {
/* 259 */               this.mFetched = true;
/* 260 */               String requestId = this.mAppController.fetchComments(this.mPostId, this.mNextPage, this.mAnchor, 20);
                        if (requestId != null) {
                            addRequest(requestId);
                        } else {
                            this.mFetched = false;
                            hideProgress(3);
                        }
                    }
                }

/* 263 */       private void saveTopItemInfo() {
/* 264 */           if (this.mAdapter.getCount() != 0) {
/* 267 */               this.mLastTopItemId = this.mAdapter.getItemId(this.mListView.getFirstVisiblePosition());
/* 268 */               View topChild = this.mListView.getChildAt(this.mListView.getFirstVisiblePosition() + this.mListView.getHeaderViewsCount());
/* 269 */               if (topChild != null) {
/* 270 */                   this.mLastTopItemPixelOffset = topChild.getTop();
                        }
                    }
                }

/* 274 */       public void postComment(EditText editText) {
/* 275 */           if (validate(editText)) {
/* 275a */              final String pendingComment = editText.getText().toString();
/* 276 */               ProgressDialog dialog = new ProgressDialog(getActivity(), C0257R.style.ProgressDialogTheme);
/* 278 */               dialog.setMessage(getString(C0257R.string.comments_posting));
/* 279 */               dialog.setProgressStyle(0);
/* 280 */               dialog.show();
/* 281 */               this.mProgressDialog = dialog;
/* 282 */               addRequest(this.mAppController.postComment(this.mPostId, this.mRepostId, this.mAppController.getActiveSession(), editText.getText().toString(), this.mEdit.getEntities()));
/* 284 */               editText.getText().clear();
/* 284a */              editText.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (CommentsFragment.this.mProgressDialog == dialog && dialog.isShowing()) {
                                    dialog.dismiss();
                                    if (CommentsFragment.this.mEdit != null && CommentsFragment.this.mEdit.length() == 0) {
                                        CommentsFragment.this.mEdit.setText(pendingComment);
                                        CommentsFragment.this.mEdit.setSelection(CommentsFragment.this.mEdit.length());
                                    }
                                }
                            }
                        }, 20000L);
                    }
                }

                @Override // co.vine.android.BaseArrayListFragment
/* 289 */       protected void onListItemClick(ListView l, View v, int position, long id) {
/* 290 */           super.onListItemClick(l, v, position, id);
                }

                @Override // android.widget.AdapterView.OnItemClickListener
/* 294 */       public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
/* 295 */           CommentViewHolder h = (CommentViewHolder) view.getTag();
/* 296 */           ProfileActivity.start(getActivity(), h.userId, EVENT_SOURCE_TITLE);
                }

/* 299 */       private boolean validate(EditText editText) {
                    return editText != null && editText.length() > 0;
                }

/* 303 */       private int editTextLength() {
/* 304 */           return this.mEdit.getText().toString().trim().length();
                }

                @Override // android.view.View.OnClickListener
/* 308 */       public void onClick(View view) {
/* 309 */           if (view.getId() == C0257R.id.send_button) {
/* 310 */               sendComment();
                    } else {
/* 312 */               CommentViewHolder h = (CommentViewHolder) view.getTag();
/* 313 */               ProfileActivity.start(getActivity(), h.userId, EVENT_SOURCE_TITLE);
                    }
                }

                @Override // co.vine.android.widget.SpanClickListener
/* 318 */       public void onSpanClicked(View view, int type, Object tag) {
                    switch (type) {
                        case 1:
/* 321 */                   ProfileActivity.start(getActivity(), ((Long) tag).longValue(), EVENT_SOURCE_TITLE);
                            break;
                        case 3:
/* 325 */                   HashtagActivity.start(getActivity(), (String) tag);
                            break;
                    }
                }

/* 334 */       public void saveCurrentCommentViewState(View view) {
/* 335 */           this.mSelectedView = view;
/* 336 */           this.mSelectedView.setSelected(true);
/* 337 */           mSelectedCommentId = ((CommentViewHolder) view.getTag()).commentId;
/* 338 */           this.mSelectedUserId = ((CommentViewHolder) view.getTag()).userId;
/* 339 */           this.mSelectedUsername = ((CommentViewHolder) view.getTag()).username;
                }

                @Override // android.widget.AdapterView.OnItemLongClickListener
/* 343 */       public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
/* 344 */           if (mActionMode != null || !(view.getTag() instanceof CommentViewHolder)) {
                        return false;
                    }
/* 352 */           CommentViewHolder commentViewHolder = (CommentViewHolder) view.getTag();
/* 354 */           long userId = this.mAppController.getActiveId();
/* 355 */           saveCurrentCommentViewState(view);
                    if (userId == commentViewHolder.userId || userId == this.mPostAuthorId) {
/* 357 */               mActionMode = ((ActionBarActivity) getActivity()).startSupportActionMode(this.mDeleteActionModeCallback);
                    } else {
/* 359 */               mActionMode = ((ActionBarActivity) getActivity()).startSupportActionMode(this.mSpamActionModeCallback);
                    }
/* 361 */           return true;
                }

                @Override // android.widget.TextView.OnEditorActionListener
/* 365 */       public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
/* 366 */           if (v.getId() != C0257R.id.edit_reply) {
                        return false;
                    }
/* 367 */           if (actionId == 4) {
/* 368 */               sendComment();
                    }
/* 370 */           return true;
                }

/* 376 */       private void sendComment() {
/* 377 */           if (editTextLength() > 140) {
/* 378 */               Util.showTopToast(getActivity(), C0257R.string.comment_length_exceeded);
/* 384 */               return;
                    }
/* 380 */           postComment(this.mEdit);
/* 381 */           Util.setSoftKeyboardVisibility(getActivity(), this.mEdit, false);
/* 382 */           this.mEdit.clearFocus();
                }

/* 392 */       private void createReply() {
/* 393 */           int insertPosition = this.mEdit.getSelectionEnd() > 0 ? this.mEdit.getSelectionEnd() : this.mEdit.getText().length();
/* 394 */           if (this.mEdit.getSelectionEnd() != this.mEdit.getSelectionStart()) {
/* 396 */               this.mEdit.append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
/* 397 */               insertPosition++;
                    }
/* 399 */           this.mEdit.putEntity(insertPosition, insertPosition, new VineEntity(VineEntity.TYPE_MENTION, this.mSelectedUsername, null, insertPosition, this.mSelectedUsername.length() + insertPosition, this.mSelectedUserId));
/* 406 */           this.mEdit.append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
/* 407 */           this.mEdit.requestFocusFromTouch();
/* 408 */           CommonUtil.setSoftKeyboardVisibility(getActivity(), this.mEdit, true);
                }

/* 409 */       private abstract class CommentActionCallback implements ActionMode.Callback {
                    private PromptDialogSupportFragment.OnDialogDoneListener mDialogDoneListener;
                    private int mDialogMessageResourceId;
                    private int mMenuResourceId;

                    public abstract void performAction();

/* 410 */           private CommentActionCallback() {
/* 415 */               this.mDialogDoneListener = new C01641();
                    }

/* 410 */           /* synthetic */ CommentActionCallback(CommentsFragment x0, C01631 x1) {
/* 411 */               this();
                    }

                    /* JADX INFO: renamed from: co.vine.android.CommentsFragment$CommentActionCallback$1 */
/* 414 */           class C01641 implements PromptDialogSupportFragment.OnDialogDoneListener {
/* 415 */               C01641() {
                        }

                        @Override // co.vine.android.widgets.PromptDialogFragment.OnDialogDoneListener
/* 417 */               public void onDialogDone(DialogInterface dialogInterface, int id, int which) {
                            switch (which) {
                                case -2:
/* 420 */                           CommentActionCallback.this.performAction();
                                    break;
                                case -1:
/* 424 */                           dialogInterface.dismiss();
                                    break;
                            }
                        }
                    }

/* 433 */           public void setMenuResourceId(int mMenuResourceId) {
/* 434 */               this.mMenuResourceId = mMenuResourceId;
                    }

/* 437 */           public void setDialogMessageResourceId(int mDialogMessageResourceId) {
/* 438 */               this.mDialogMessageResourceId = mDialogMessageResourceId;
                    }

                    @Override // android.support.v7.view.ActionMode.Callback
/* 443 */           public boolean onCreateActionMode(ActionMode mode, Menu menu) {
/* 444 */               MenuInflater inflater = mode.getMenuInflater();
/* 445 */               inflater.inflate(this.mMenuResourceId, menu);
/* 446 */               return true;
                    }

                    @Override // android.support.v7.view.ActionMode.Callback
/* 450 */           public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
/* 451 */               return false;
                    }

                    @Override // android.support.v7.view.ActionMode.Callback
/* 455 */           public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
/* 456 */               if (item.getItemId() == C0257R.id.action_reply) {
/* 457 */                   CommentsFragment.access$100(CommentsFragment.this);
                        } else {
/* 459 */                   showDialog();
                        }
/* 461 */               mode.finish();
/* 462 */               return true;
                    }

                    @Override // android.support.v7.view.ActionMode.Callback
/* 466 */           public void onDestroyActionMode(ActionMode mode) {
/* 467 */               if (CommentsFragment.access$200(CommentsFragment.this) != null) {
/* 468 */                   CommentsFragment.access$200(CommentsFragment.this).setSelected(false);
                        }
/* 470 */               CommentsFragment.access$302(null);
                    }

/* 473 */           public void showDialog() {
/* 474 */               PromptDialogSupportFragment p = PromptDialogSupportFragment.newInstance(0);
/* 476 */               p.setListener(this.mDialogDoneListener);
/* 477 */               p.setMessage(this.mDialogMessageResourceId);
/* 478 */               p.setPositiveButton(C0257R.string.f87no);
/* 479 */               p.setNegativeButton(C0257R.string.yes);
/* 480 */               p.show(CommentsFragment.this.getFragmentManager());
                    }
                }

/* 486 */       private class CommentDeleteActionCallback extends CommentActionCallback {
/* 487 */           public CommentDeleteActionCallback(int menuId, int messageId) {
                        super(CommentsFragment.this, null);
/* 489 */               setMenuResourceId(menuId);
/* 490 */               setDialogMessageResourceId(messageId);
                    }

                    @Override // co.vine.android.CommentsFragment.CommentActionCallback, android.support.v7.view.ActionMode.Callback
/* 494 */           public boolean onCreateActionMode(ActionMode mode, Menu menu) {
/* 495 */               super.onCreateActionMode(mode, menu);
/* 496 */               MenuItem spamButton = menu.findItem(C0257R.id.comment_action_spam);
/* 497 */               spamButton.setVisible(false);
/* 498 */               return true;
                    }

                    @Override // co.vine.android.CommentsFragment.CommentActionCallback
/* 502 */           public void performAction() {
/* 503 */               CommentsFragment.this.addRequest(CommentsFragment.this.mAppController.deleteComment(CommentsFragment.access$500(CommentsFragment.this), CommentsFragment.access$600(CommentsFragment.this), CommentsFragment.access$700()));
                    }
                }

/* 507 */       private void showReportingMenu() {
/* 508 */           Activity activity = getActivity();
/* 509 */           if (activity != null) {
/* 512 */               Intent i = new Intent(activity, (Class<?>) ReportingActivity.class);
/* 513 */               i.putExtra(ReportingFragment.EXTRA_MENU_TYPE, "comment");
/* 514 */               i.putExtra(ReportingFragment.EXTRA_COMMENT_ID, mSelectedCommentId);
/* 515 */               i.putExtra("userId", this.mSelectedUserId);
/* 516 */               i.putExtra("postId", this.mPostId);
/* 517 */               i.putExtra("username", this.mSelectedUsername);
/* 518 */               getActivity().startActivityForResult(i, 0);
                    }
                }

/* 521 */       private class CommentSpamActionCallback extends CommentActionCallback {
/* 522 */           public CommentSpamActionCallback(int menuId, int messageId) {
                        super(CommentsFragment.this, null);
/* 524 */               setMenuResourceId(menuId);
/* 525 */               setDialogMessageResourceId(messageId);
                    }

                    @Override // co.vine.android.CommentsFragment.CommentActionCallback, android.support.v7.view.ActionMode.Callback
/* 529 */           public boolean onCreateActionMode(ActionMode mode, Menu menu) {
/* 530 */               super.onCreateActionMode(mode, menu);
/* 531 */               MenuItem spamButton = menu.findItem(C0257R.id.comment_action_delete);
/* 532 */               spamButton.setVisible(false);
/* 533 */               return true;
                    }

                    @Override // co.vine.android.CommentsFragment.CommentActionCallback, android.support.v7.view.ActionMode.Callback
/* 537 */           public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
/* 538 */               if (item.getItemId() == C0257R.id.action_reply) {
/* 539 */                   CommentsFragment.access$100(CommentsFragment.this);
                        } else {
/* 541 */                   CommentsFragment.access$000(CommentsFragment.this);
                        }
/* 543 */               mode.finish();
/* 544 */               return true;
                    }

                    @Override // co.vine.android.CommentsFragment.CommentActionCallback
/* 548 */           public void performAction() {
/* 549 */               CommentsFragment.access$000(CommentsFragment.this);
                    }
                }

/* 553 */       private CommentActionCallback createCommentActionCallback(CommentActionType type) {
                    return type == CommentActionType.DELETE_ACTION ? new CommentDeleteActionCallback(C0257R.menu.comment_actions, C0257R.string.delete_comment_prompt) : new CommentSpamActionCallback(C0257R.menu.comment_actions, C0257R.string.block_or_report_comment);
                }

                @Override // co.vine.android.widget.PopupEditText.PopupEditTextListener
/* 562 */       public CursorAdapter getPopupAdapter() {
/* 563 */           return this.mTypeaheadAdapter;
                }

                @Override // co.vine.android.widget.PopupEditText.PopupEditTextListener
/* 567 */       public void setPopupAdapter(String type) {
/* 568 */           if (ComposeTokenizer.USERS_ADAPTER.equals(type)) {
/* 569 */               this.mTypeaheadAdapter = this.mUsersAdapter;
                    } else if (ComposeTokenizer.TAGS_ADAPTER.equals(type)) {
/* 571 */               this.mTypeaheadAdapter = this.mTagsAdapter;
                    }
/* 573 */           this.mEdit.setAdapter(this.mTypeaheadAdapter);
                }

                @Override // android.widget.Filterable
/* 577 */       public Filter getFilter() {
/* 578 */           if (this.mFilter == null) {
/* 579 */               long activeId = this.mAppController.getActiveId();
/* 580 */               this.mFilter = new ComposeFilter(this, activeId);
                    }
/* 582 */           return this.mFilter;
                }

                @Override // co.vine.android.widget.PopupEditText.PopupEditTextListener
/* 586 */       public void onPopupItemSelected(int tokenStart, int tokenEnd, CharSequence selection, long userId) {
                }

                @Override // co.vine.android.widget.PopupEditText.PopupEditTextListener
/* 590 */       public void onPopupShown() {
                }

                @Override // co.vine.android.widget.PopupEditText.PopupEditTextListener
/* 594 */       public void onFiltering(CharSequence constraint) {
                    String type;
                    String query;
                    boolean fetch = true;
/* 595 */           if (!TextUtils.isEmpty(constraint)) {
/* 597 */               String selection = constraint.toString();
/* 600 */               if (selection.startsWith("@")) {
/* 601 */                   type = VineEntity.TYPE_MENTION;
/* 602 */                   query = selection.substring(1, selection.length());
                        } else if (selection.startsWith("#")) {
/* 604 */                   type = "tag";
/* 605 */                   query = selection.substring(1, selection.length());
                        } else {
/* 607 */                   type = null;
/* 608 */                   query = null;
                        }
/* 610 */               int fetchThreshold = this.mAppController.getTypeaheadFetchThreshold(type);
/* 612 */               if (!TextUtils.isEmpty(query) && query.length() >= fetchThreshold) {
/* 614 */                   if (VineEntity.TYPE_MENTION.equals(type)) {
/* 615 */                       if (VineSuggestionsProvider.getRealtimeUserSuggestions(query) != null) {
                                    fetch = false;
                                }
                            } else if ("tag".equals(type)) {
/* 617 */                       if (VineSuggestionsProvider.getRealtimeTagSuggestions(query) != null) {
                                    fetch = false;
                                }
                            } else {
/* 619 */                       fetch = false;
                            }
/* 622 */                   if (fetch) {
/* 623 */                       if (VineEntity.TYPE_MENTION.equals(type)) {
/* 624 */                           addRequest(this.mAppController.fetchUserTypeahead(query));
                                } else if ("tag".equals(type)) {
/* 626 */                           addRequest(this.mAppController.fetchTagTypeahead(query));
                                }
                            }
                        }
                    }
                }

                @Override // co.vine.android.widget.ConversationList.GetMoreListener
/* 634 */       public void getMore() {
/* 635 */           fetchContent();
                }

                @Override // co.vine.android.BaseArrayListFragment, co.vine.android.BaseAdapterFragment
/* 639 */       public void showProgress(int fetchType) {
/* 640 */           super.showProgress(fetchType);
/* 641 */           if (fetchType == 1 && this.mNextPage > 0) {
/* 642 */               ((ConversationList) this.mListView).showProgress();
                    }
                }

                @Override // co.vine.android.BaseArrayListFragment, co.vine.android.BaseAdapterFragment
/* 647 */       public void hideProgress(int fetchType) {
/* 648 */           super.hideProgress(fetchType);
/* 649 */           if (fetchType == 1) {
/* 650 */               ((ConversationList) this.mListView).hideProgress();
                    }
                }

                @Override // android.text.TextWatcher
/* 656 */       public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override // android.text.TextWatcher
/* 660 */       public void onTextChanged(CharSequence s, int start, int before, int count) {
/* 661 */           if (this.mEdit.getText().length() > 0) {
/* 662 */               this.mSendButton.setColorFilter(getResources().getColor(C0257R.color.vine_green) | (-16777216));
                    } else {
/* 664 */               this.mSendButton.setColorFilter(getResources().getColor(C0257R.color.btn_pressed_fill) | (-16777216));
                    }
                }

                @Override // android.text.TextWatcher
/* 670 */       public void afterTextChanged(Editable s) {
                }

/* 671 */       class CommentsAppSessionListener extends AppSessionListener {
/* 672 */           CommentsAppSessionListener() {
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 677 */           public void onGetCommentsComplete(String reqId, int statusCode, String reasonPhrase, int nextPage, long anchor, ArrayList<VineComment> comments) {
/* 678 */               PendingRequest req = CommentsFragment.this.removeRequest(reqId);
/* 679 */               if (req != null) {
/* 680 */                   CommentsFragment.this.hideProgress(3);
/* 681 */                   if (comments == null || comments.size() == 0) {
/* 682 */                       if (statusCode != 200) {
                                CommentsFragment.this.mFetched = false;
/* 683 */                           CommentsFragment.this.mEmptyText.setText(C0257R.string.failed_to_load_comments);
                                }
/* 685 */                       CommentsFragment.this.showSadface(true, false);
                            } else {
/* 687 */                       CommentsFragment.this.showSadface(false);
                            }
/* 690 */                   if (statusCode == 200) {
/* 691 */                       CommentsFragment.access$802(CommentsFragment.this, nextPage);
/* 692 */                       CommentsFragment.access$902(CommentsFragment.this, anchor);
/* 694 */                       CommentsFragment.access$1000(CommentsFragment.this);
/* 695 */                       CommentsAdapter adapter = (CommentsAdapter) CommentsFragment.this.mAdapter;
                            if (comments != null && !comments.isEmpty()) {
/* 696 */                   adapter.mergeComments(comments);
/* 698 */                   int pos = adapter.getPositionForId(CommentsFragment.access$1100(CommentsFragment.this));
                                if (pos >= 0) {
/* 699 */                       CommentsFragment.this.mListView.setSelectionFromTop(CommentsFragment.this.mListView.getHeaderViewsCount() + pos, CommentsFragment.access$1200(CommentsFragment.this));
                                }
                            }
                            }
/* 702 */                   if (CommentsFragment.access$1300(CommentsFragment.this) && (CommentsFragment.access$800(CommentsFragment.this) <= 0 || comments == null || comments.size() == 0)) {
/* 703 */                       ((ConversationList) CommentsFragment.this.mListView).deactivateRefresh();
                            }
/* 706 */                   if (statusCode != 200) {
                            CommentsFragment.this.mFetched = false;
/* 707 */                       ((ConversationList) CommentsFragment.this.mListView).deactivateRefresh();
                            }
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 713 */           public void onPhotoImageLoaded(HashMap<ImageKey, UrlImage> images) {
/* 714 */               super.onPhotoImageLoaded(images);
/* 715 */               ((CommentsAdapter) CommentsFragment.this.mAdapter).updateProfileImages(images);
/* 716 */               CommentsFragment.access$1400(CommentsFragment.this).setUserImages(images);
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 721 */           public void onPostCommentComplete(String reqId, int statusCode, String reasonPhrase, long postId, String comment, VineComment commentToMerge) {
/* 722 */               CommentsFragment.this.dismissProgressDialog();
/* 723 */               PendingRequest req = CommentsFragment.this.removeRequest(reqId);
/* 724 */               if (postId == CommentsFragment.access$500(CommentsFragment.this)) {
/* 725 */                   ListView listView = CommentsFragment.this.mListView;
/* 726 */                   listView.post(new RunnableC01651(listView));
/* 731 */                   if (statusCode < 200 || statusCode >= 300) {
/* 732 */                       CommentsFragment.access$1500(CommentsFragment.this).setText(comment);
                            } else {
/* 734 */                       if (commentToMerge != null) {
/* 735 */                           ((CommentsAdapter) CommentsFragment.this.mAdapter).addMyComment(commentToMerge);
/* 736 */                           CommentsFragment.this.showSadface(false);
                                } else {
/* 738 */                           CommentsFragment.access$1500(CommentsFragment.this).setText(comment);
                                }
                            Intent countIntent = new Intent(BaseTimelineFragment.ACTION_COMMENT_COUNT_CHANGED);
                            countIntent.putExtra("post_id", postId);
                            CommentsFragment.this.getActivity().sendBroadcast(countIntent, CrossConstants.BROADCAST_PERMISSION);
                            }
                        }
                    }

                    /* JADX INFO: renamed from: co.vine.android.CommentsFragment$CommentsAppSessionListener$1 */
/* 724 */           class RunnableC01651 implements Runnable {
                        final /* synthetic */ ListView val$listView;

/* 725 */               RunnableC01651(ListView listView) {
                            this.val$listView = listView;
                        }

                        @Override // java.lang.Runnable
/* 727 */               public void run() {
/* 728 */                   this.val$listView.setSelection(this.val$listView.getCount() - 1);
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 741 */           public void onDeleteCommentComplete(String reqId, int statusCode, String reasonPhrase, long commentId) {
/* 742 */               PendingRequest request = CommentsFragment.this.removeRequest(reqId);
/* 743 */               if (request != null && statusCode == 200) {
                            if (((CommentViewHolder) CommentsFragment.access$200(CommentsFragment.this).getTag()).userId != CommentsFragment.this.mAppController.getActiveId()) {
/* 746 */                       CommentsFragment.access$1600(CommentsFragment.this, C0257R.string.block_user_comment_delete_prompt);
                            }
/* 748 */                   ((CommentsAdapter) CommentsFragment.this.mAdapter).deleteComment(commentId);
/* 749 */                   if (CommentsFragment.this.mAdapter.getCount() == 0) {
/* 750 */                       CommentsFragment.this.showSadface(true, false);
                            }
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 759 */           public void onGetUserTypeAheadComplete(String reqId, int statusCode, String reasonPhrase, String query, ArrayList<VineUser> users) {
/* 760 */               if (CommentsFragment.this.removeRequest(reqId) != null && statusCode == 200 && !users.isEmpty()) {
/* 762 */                   VineSuggestionsProvider.addRealtimeUserSuggestions(query, users);
/* 763 */                   CommentsFragment.access$1500(CommentsFragment.this).performFilter(false);
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 770 */           public void onGetTagTypeAheadComplete(String reqId, int statusCode, String reasonPhrase, String query, ArrayList<VineTag> tags) {
/* 771 */               if (CommentsFragment.this.removeRequest(reqId) != null && statusCode == 200 && !tags.isEmpty()) {
/* 773 */                   VineSuggestionsProvider.addRealtimeTagSuggestions(query, tags);
/* 774 */                   CommentsFragment.access$1500(CommentsFragment.this).performFilter(false);
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 781 */           public void onCaptchaRequired(String reqId, String captchaUrl, PendingAction action) {
/* 782 */               PendingRequest req = CommentsFragment.this.removeRequest(reqId);
/* 783 */               if (req != null) {
/* 784 */                   CommentsFragment.this.mPendingRequestHelper.onCaptchaRequired(CommentsFragment.this.getActivity(), reqId, action.actionCode, action.bundle, captchaUrl);
                        }
                    }
                }
            }
