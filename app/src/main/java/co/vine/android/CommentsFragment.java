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
            
            
    public class CommentsFragment extends BaseArrayListFragment implements SpanClickListener, View.OnClickListener, AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener, TextView.OnEditorActionListener, Filterable, PopupEditText.PopupEditTextListener, ConversationList.GetMoreListener, TextWatcher {
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

        static  void access$000(CommentsFragment x0) {
            x0.showReportingMenu();
                }

        static  void access$100(CommentsFragment x0) {
            x0.createReply();
                }

        static  void access$1000(CommentsFragment x0) {
            x0.saveTopItemInfo();
                }

        static  long access$1100(CommentsFragment x0) {
            return x0.mLastTopItemId;
                }

        static  int access$1200(CommentsFragment x0) {
            return x0.mLastTopItemPixelOffset;
                }

        static  boolean access$1300(CommentsFragment x0) {
            return x0.mFetched;
                }

        static  UsersAutoCompleteAdapter access$1400(CommentsFragment x0) {
            return x0.mUsersAdapter;
                }

        static  PopupEditText access$1500(CommentsFragment x0) {
            return x0.mEdit;
                }

        static  void access$1600(CommentsFragment x0, int x1) {
            x0.showBlockUserDialog(x1);
                }

        static  View access$200(CommentsFragment x0) {
            return x0.mSelectedView;
                }

        static  ActionMode access$302(ActionMode x0) {
            mActionMode = x0;
                    return x0;
                }

        static  long access$500(CommentsFragment x0) {
            return x0.mPostId;
                }

        static  long access$600(CommentsFragment x0) {
            return x0.mRepostId;
                }

        static  String access$700() {
            return mSelectedCommentId;
                }

        static  int access$800(CommentsFragment x0) {
            return x0.mNextPage;
                }

        static  int access$802(CommentsFragment x0, int x1) {
            x0.mNextPage = x1;
                    return x1;
                }

        static  long access$902(CommentsFragment x0, long x1) {
            x0.mAnchor = x1;
                    return x1;
                }

                
        class C01631 implements PromptDialogSupportFragment.OnDialogDoneListener {
            C01631() {
                    }

                    @Override 
           public void onDialogDone(DialogInterface dialogInterface, int id, int which) {
                        switch (which) {
                            case -2:
                       CommentsFragment.access$000(CommentsFragment.this);
                                break;
                            case -1:
                       dialogInterface.dismiss();
                                break;
                        }
                    }
                }

       private void showBlockUserDialog(int messageId) {
           PromptDialogSupportFragment p = PromptDialogSupportFragment.newInstance(0);
           p.setListener(this.mBlockDoneListener);
           p.setMessage(messageId);
           p.setPositiveButton(R.string.no);
           p.setNegativeButton(R.string.yes);
           p.show(getFragmentManager());
                }

                @Override 
       public void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           this.DATE_FORMAT.applyPattern(getResources().getString(R.string.datetime_format_long));
           if (savedInstanceState != null && savedInstanceState.containsKey(STATE_FETCHED)) {
               this.mFetched = savedInstanceState.getBoolean(STATE_FETCHED);
                    }
           this.mAppSessionListener = new CommentsAppSessionListener();
           this.mUsersAdapter = new UsersAutoCompleteAdapter(getActivity(), this.mAppController);
           this.mTagsAdapter = new TagsAutoCompleteAdapter(getActivity(), this.mAppController);
           this.mTypeaheadAdapter = this.mTagsAdapter;
           this.mDeleteActionModeCallback = createCommentActionCallback(CommentActionType.DELETE_ACTION);
           this.mSpamActionModeCallback = createCommentActionCallback(CommentActionType.SPAM_ACTION);
           setHasOptionsMenu(true);
                }

                @Override 
       public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
           return createView(inflater, R.layout.comments_thread, container);
                }

                @Override 
       public void onViewCreated(View view, Bundle savedInstanceState) {
           super.onViewCreated(view, savedInstanceState);
           PopupEditText edit = (PopupEditText) view.findViewById(R.id.edit_reply);
           edit.setOnEditorActionListener(this);
           edit.setAdapter(this.mTypeaheadAdapter);
           this.mTokenizer = new ComposeTokenizer(this);
           edit.setTokenizer(this.mTokenizer, this, this.mAppController.getTypeaheadThrottle());
           edit.setPopupEditTextListener(this);
           this.mEdit = edit;
           this.mEdit.addTextChangedListener(this);
           Bundle args = getArguments();
           this.mPostId = args.getLong("post_id");
           this.mRepostId = args.getLong("repost_id");
           this.mPostAuthorId = args.getLong("post_author_id");
           this.mSendButton = (ImageView) view.findViewById(R.id.send_button);
           this.mSendButton.setOnClickListener(this);
           this.mSendButton.setColorFilter((-16777216) | getResources().getColor(R.color.btn_pressed_fill), PorterDuff.Mode.SRC_ATOP);
           this.mListView.setOnItemLongClickListener(this);
           this.mListView.setOnItemClickListener(this);
           this.mAdapter = new CommentsAdapter(getActivity(), this.mPostId, this.mAppController, this);
           ((ConversationList) this.mListView).setGetMoreListener(this);
           this.mListView.setAdapter((ListAdapter) this.mAdapter);
                }

                @Override 
       public void onActivityCreated(Bundle savedInstanceState) {
           super.onActivityCreated(savedInstanceState);
           boolean hideKeyboard = getArguments().getBoolean("hide_keyboard", false);
           if (!hideKeyboard) {
               this.mEdit.requestFocus();
               Util.setSoftKeyboardVisibility(getActivity(), this.mEdit, true);
                    }
                }

                @Override 
       public void onActivityResult(int requestCode, int resultCode, Intent data) {
           super.onActivityResult(requestCode, resultCode, data);
           if (data != null && requestCode == 0) {
               long commentId = data.getLongExtra(ReportingFragment.EXTRA_COMMENT_ID, -1L);
               String event = data.getStringExtra("event");
               boolean blocked = data.getBooleanExtra("blocked", false);
                        if (commentId != -1 && !TextUtils.isEmpty(event)) {
                   ((CommentsAdapter) this.mAdapter).spamComment(commentId);
                   if (this.mAdapter.getCount() == 0) {
                       showSadface(true, false);
                       return;
                            }
                   return;
                        }
               if (blocked) {
                   Util.showDefaultToast(getActivity(), getString(R.string.user_blocked, this.mSelectedUsername));
                        }
                    }
                }

                @Override 
       public void onResume() {
           super.onResume();
           if (this.mAdapter.isEmpty() && !this.mFetched) {
               fetchContent();
                    }
                }

                @Override 
       public void onDestroy() {
           super.onDestroy();
           VineSuggestionsProvider.clearRealtimeUserSuggestions();
           VineSuggestionsProvider.clearRealtimeTagSuggestions();
                }

                @Override 
       public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
           inflater.inflate(R.menu.comments, menu);
           super.onCreateOptionsMenu(menu, inflater);
                }

                @Override 
       public boolean onOptionsItemSelected(MenuItem item) {
           int id = item.getItemId();
           if (id == R.id.mention) {
               insertText("@");
                    } else if (id == R.id.tag) {
               insertText("#");
                    } else {
               return super.onOptionsItemSelected(item);
                    }
           return true;
                }

       private void insertText(String toInsert) {
           if (this.mEdit != null) {
               int selStart = this.mEdit.getSelectionStart();
               int selEnd = this.mEdit.getSelectionEnd();
               if (this.mEdit.getText() != null && selStart >= 0 && selEnd <= this.mEdit.length()) {
                   this.mEdit.getText().replace(selStart, selEnd, toInsert);
                        }
                    }
                }

                @Override 
       public void onSaveInstanceState(Bundle outState) {
           super.onSaveInstanceState(outState);
           outState.putBoolean(STATE_FETCHED, this.mFetched);
                }

       private void fetchContent() {
           if (!hasPendingRequest()) {
               this.mFetched = true;
               String requestId = this.mAppController.fetchComments(this.mPostId, this.mNextPage, this.mAnchor, 20);
                        if (requestId != null) {
                            addRequest(requestId);
                        } else {
                            this.mFetched = false;
                            hideProgress(3);
                        }
                    }
                }

       private void saveTopItemInfo() {
           if (this.mAdapter.getCount() != 0) {
               this.mLastTopItemId = this.mAdapter.getItemId(this.mListView.getFirstVisiblePosition());
               View topChild = this.mListView.getChildAt(this.mListView.getFirstVisiblePosition() + this.mListView.getHeaderViewsCount());
               if (topChild != null) {
                   this.mLastTopItemPixelOffset = topChild.getTop();
                        }
                    }
                }

       public void postComment(EditText editText) {
           if (validate(editText)) {
              final String pendingComment = editText.getText().toString();
               ProgressDialog dialog = new ProgressDialog(getActivity(), R.style.ProgressDialogTheme);
               dialog.setMessage(getString(R.string.comments_posting));
               dialog.setProgressStyle(0);
               dialog.show();
               this.mProgressDialog = dialog;
               addRequest(this.mAppController.postComment(this.mPostId, this.mRepostId, this.mAppController.getActiveSession(), editText.getText().toString(), this.mEdit.getEntities()));
               editText.getText().clear();
              editText.postDelayed(new Runnable() {
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

                @Override 
       protected void onListItemClick(ListView l, View v, int position, long id) {
           super.onListItemClick(l, v, position, id);
                }

                @Override 
       public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
           CommentViewHolder h = (CommentViewHolder) view.getTag();
           ProfileActivity.start(getActivity(), h.userId, EVENT_SOURCE_TITLE);
                }

       private boolean validate(EditText editText) {
                    return editText != null && editText.length() > 0;
                }

       private int editTextLength() {
           return this.mEdit.getText().toString().trim().length();
                }

                @Override 
       public void onClick(View view) {
           if (view.getId() == R.id.send_button) {
               sendComment();
                    } else {
               CommentViewHolder h = (CommentViewHolder) view.getTag();
               ProfileActivity.start(getActivity(), h.userId, EVENT_SOURCE_TITLE);
                    }
                }

                @Override 
       public void onSpanClicked(View view, int type, Object tag) {
                    switch (type) {
                        case 1:
                   ProfileActivity.start(getActivity(), ((Long) tag).longValue(), EVENT_SOURCE_TITLE);
                            break;
                        case 3:
                   HashtagActivity.start(getActivity(), (String) tag);
                            break;
                    }
                }

       public void saveCurrentCommentViewState(View view) {
           this.mSelectedView = view;
           this.mSelectedView.setSelected(true);
           mSelectedCommentId = ((CommentViewHolder) view.getTag()).commentId;
           this.mSelectedUserId = ((CommentViewHolder) view.getTag()).userId;
           this.mSelectedUsername = ((CommentViewHolder) view.getTag()).username;
                }

                @Override 
       public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
           if (mActionMode != null || !(view.getTag() instanceof CommentViewHolder)) {
                        return false;
                    }
           CommentViewHolder commentViewHolder = (CommentViewHolder) view.getTag();
           long userId = this.mAppController.getActiveId();
           saveCurrentCommentViewState(view);
                    if (userId == commentViewHolder.userId || userId == this.mPostAuthorId) {
               mActionMode = ((ActionBarActivity) getActivity()).startSupportActionMode(this.mDeleteActionModeCallback);
                    } else {
               mActionMode = ((ActionBarActivity) getActivity()).startSupportActionMode(this.mSpamActionModeCallback);
                    }
           return true;
                }

                @Override 
       public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
           if (v.getId() != R.id.edit_reply) {
                        return false;
                    }
           if (actionId == 4) {
               sendComment();
                    }
           return true;
                }

       private void sendComment() {
           if (editTextLength() > 140) {
               Util.showTopToast(getActivity(), R.string.comment_length_exceeded);
               return;
                    }
           postComment(this.mEdit);
           Util.setSoftKeyboardVisibility(getActivity(), this.mEdit, false);
           this.mEdit.clearFocus();
                }

       private void createReply() {
           int insertPosition = this.mEdit.getSelectionEnd() > 0 ? this.mEdit.getSelectionEnd() : this.mEdit.getText().length();
           if (this.mEdit.getSelectionEnd() != this.mEdit.getSelectionStart()) {
               this.mEdit.append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
               insertPosition++;
                    }
           this.mEdit.putEntity(insertPosition, insertPosition, new VineEntity(VineEntity.TYPE_MENTION, this.mSelectedUsername, null, insertPosition, this.mSelectedUsername.length() + insertPosition, this.mSelectedUserId));
           this.mEdit.append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
           this.mEdit.requestFocusFromTouch();
           CommonUtil.setSoftKeyboardVisibility(getActivity(), this.mEdit, true);
                }

       private abstract class CommentActionCallback implements ActionMode.Callback {
                    private PromptDialogSupportFragment.OnDialogDoneListener mDialogDoneListener;
                    private int mDialogMessageResourceId;
                    private int mMenuResourceId;

                    public abstract void performAction();

           private CommentActionCallback() {
               this.mDialogDoneListener = new C01641();
                    }

            CommentActionCallback(CommentsFragment x0, C01631 x1) {
               this();
                    }

                    
           class C01641 implements PromptDialogSupportFragment.OnDialogDoneListener {
               C01641() {
                        }

                        @Override 
               public void onDialogDone(DialogInterface dialogInterface, int id, int which) {
                            switch (which) {
                                case -2:
                           CommentActionCallback.this.performAction();
                                    break;
                                case -1:
                           dialogInterface.dismiss();
                                    break;
                            }
                        }
                    }

           public void setMenuResourceId(int mMenuResourceId) {
               this.mMenuResourceId = mMenuResourceId;
                    }

           public void setDialogMessageResourceId(int mDialogMessageResourceId) {
               this.mDialogMessageResourceId = mDialogMessageResourceId;
                    }

                    @Override 
           public boolean onCreateActionMode(ActionMode mode, Menu menu) {
               MenuInflater inflater = mode.getMenuInflater();
               inflater.inflate(this.mMenuResourceId, menu);
               return true;
                    }

                    @Override 
           public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
               return false;
                    }

                    @Override 
           public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
               if (item.getItemId() == R.id.action_reply) {
                   CommentsFragment.access$100(CommentsFragment.this);
                        } else {
                   showDialog();
                        }
               mode.finish();
               return true;
                    }

                    @Override 
           public void onDestroyActionMode(ActionMode mode) {
               if (CommentsFragment.access$200(CommentsFragment.this) != null) {
                   CommentsFragment.access$200(CommentsFragment.this).setSelected(false);
                        }
               CommentsFragment.access$302(null);
                    }

           public void showDialog() {
               PromptDialogSupportFragment p = PromptDialogSupportFragment.newInstance(0);
               p.setListener(this.mDialogDoneListener);
               p.setMessage(this.mDialogMessageResourceId);
               p.setPositiveButton(R.string.no);
               p.setNegativeButton(R.string.yes);
               p.show(CommentsFragment.this.getFragmentManager());
                    }
                }

       private class CommentDeleteActionCallback extends CommentActionCallback {
           public CommentDeleteActionCallback(int menuId, int messageId) {
                        super(CommentsFragment.this, null);
               setMenuResourceId(menuId);
               setDialogMessageResourceId(messageId);
                    }

                    @Override 
           public boolean onCreateActionMode(ActionMode mode, Menu menu) {
               super.onCreateActionMode(mode, menu);
               MenuItem spamButton = menu.findItem(R.id.comment_action_spam);
               spamButton.setVisible(false);
               return true;
                    }

                    @Override 
           public void performAction() {
               CommentsFragment.this.addRequest(CommentsFragment.this.mAppController.deleteComment(CommentsFragment.access$500(CommentsFragment.this), CommentsFragment.access$600(CommentsFragment.this), CommentsFragment.access$700()));
                    }
                }

       private void showReportingMenu() {
           Activity activity = getActivity();
           if (activity != null) {
               Intent i = new Intent(activity, (Class<?>) ReportingActivity.class);
               i.putExtra(ReportingFragment.EXTRA_MENU_TYPE, "comment");
               i.putExtra(ReportingFragment.EXTRA_COMMENT_ID, mSelectedCommentId);
               i.putExtra("userId", this.mSelectedUserId);
               i.putExtra("postId", this.mPostId);
               i.putExtra("username", this.mSelectedUsername);
               getActivity().startActivityForResult(i, 0);
                    }
                }

       private class CommentSpamActionCallback extends CommentActionCallback {
           public CommentSpamActionCallback(int menuId, int messageId) {
                        super(CommentsFragment.this, null);
               setMenuResourceId(menuId);
               setDialogMessageResourceId(messageId);
                    }

                    @Override 
           public boolean onCreateActionMode(ActionMode mode, Menu menu) {
               super.onCreateActionMode(mode, menu);
               MenuItem spamButton = menu.findItem(R.id.comment_action_delete);
               spamButton.setVisible(false);
               return true;
                    }

                    @Override 
           public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
               if (item.getItemId() == R.id.action_reply) {
                   CommentsFragment.access$100(CommentsFragment.this);
                        } else {
                   CommentsFragment.access$000(CommentsFragment.this);
                        }
               mode.finish();
               return true;
                    }

                    @Override 
           public void performAction() {
               CommentsFragment.access$000(CommentsFragment.this);
                    }
                }

       private CommentActionCallback createCommentActionCallback(CommentActionType type) {
                    return type == CommentActionType.DELETE_ACTION ? new CommentDeleteActionCallback(R.menu.comment_actions, R.string.delete_comment_prompt) : new CommentSpamActionCallback(R.menu.comment_actions, R.string.block_or_report_comment);
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
           this.mEdit.setAdapter(this.mTypeaheadAdapter);
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
                           addRequest(this.mAppController.fetchUserTypeahead(query));
                                } else if ("tag".equals(type)) {
                           addRequest(this.mAppController.fetchTagTypeahead(query));
                                }
                            }
                        }
                    }
                }

                @Override 
       public void getMore() {
           fetchContent();
                }

                @Override 
       public void showProgress(int fetchType) {
           super.showProgress(fetchType);
           if (fetchType == 1 && this.mNextPage > 0) {
               ((ConversationList) this.mListView).showProgress();
                    }
                }

                @Override 
       public void hideProgress(int fetchType) {
           super.hideProgress(fetchType);
           if (fetchType == 1) {
               ((ConversationList) this.mListView).hideProgress();
                    }
                }

                @Override 
       public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override 
       public void onTextChanged(CharSequence s, int start, int before, int count) {
           if (this.mEdit.getText().length() > 0) {
               this.mSendButton.setColorFilter(getResources().getColor(R.color.vine_green) | (-16777216));
                    } else {
               this.mSendButton.setColorFilter(getResources().getColor(R.color.btn_pressed_fill) | (-16777216));
                    }
                }

                @Override 
       public void afterTextChanged(Editable s) {
                }

       class CommentsAppSessionListener extends AppSessionListener {
           CommentsAppSessionListener() {
                    }

                    @Override 
           public void onGetCommentsComplete(String reqId, int statusCode, String reasonPhrase, int nextPage, long anchor, ArrayList<VineComment> comments) {
               PendingRequest req = CommentsFragment.this.removeRequest(reqId);
               if (req != null) {
                   CommentsFragment.this.hideProgress(3);
                   if (comments == null || comments.size() == 0) {
                       if (statusCode != 200) {
                                CommentsFragment.this.mFetched = false;
                           CommentsFragment.this.mEmptyText.setText(R.string.failed_to_load_comments);
                                }
                       CommentsFragment.this.showSadface(true, false);
                            } else {
                       CommentsFragment.this.showSadface(false);
                            }
                   if (statusCode == 200) {
                       CommentsFragment.access$802(CommentsFragment.this, nextPage);
                       CommentsFragment.access$902(CommentsFragment.this, anchor);
                       CommentsFragment.access$1000(CommentsFragment.this);
                       CommentsAdapter adapter = (CommentsAdapter) CommentsFragment.this.mAdapter;
                            if (comments != null && !comments.isEmpty()) {
                   adapter.mergeComments(comments);
                   int pos = adapter.getPositionForId(CommentsFragment.access$1100(CommentsFragment.this));
                                if (pos >= 0) {
                       CommentsFragment.this.mListView.setSelectionFromTop(CommentsFragment.this.mListView.getHeaderViewsCount() + pos, CommentsFragment.access$1200(CommentsFragment.this));
                                }
                            }
                            }
                   if (CommentsFragment.access$1300(CommentsFragment.this) && (CommentsFragment.access$800(CommentsFragment.this) <= 0 || comments == null || comments.size() == 0)) {
                       ((ConversationList) CommentsFragment.this.mListView).deactivateRefresh();
                            }
                   if (statusCode != 200) {
                            CommentsFragment.this.mFetched = false;
                       ((ConversationList) CommentsFragment.this.mListView).deactivateRefresh();
                            }
                        }
                    }

                    @Override 
           public void onPhotoImageLoaded(HashMap<ImageKey, UrlImage> images) {
               super.onPhotoImageLoaded(images);
               ((CommentsAdapter) CommentsFragment.this.mAdapter).updateProfileImages(images);
               CommentsFragment.access$1400(CommentsFragment.this).setUserImages(images);
                    }

                    @Override 
           public void onPostCommentComplete(String reqId, int statusCode, String reasonPhrase, long postId, String comment, VineComment commentToMerge) {
               CommentsFragment.this.dismissProgressDialog();
               PendingRequest req = CommentsFragment.this.removeRequest(reqId);
               if (postId == CommentsFragment.access$500(CommentsFragment.this)) {
                   ListView listView = CommentsFragment.this.mListView;
                   listView.post(new RunnableC01651(listView));
                   if (statusCode < 200 || statusCode >= 300) {
                       CommentsFragment.access$1500(CommentsFragment.this).setText(comment);
                            } else {
                       if (commentToMerge != null) {
                           ((CommentsAdapter) CommentsFragment.this.mAdapter).addMyComment(commentToMerge);
                           CommentsFragment.this.showSadface(false);
                                } else {
                           CommentsFragment.access$1500(CommentsFragment.this).setText(comment);
                                }
                            Intent countIntent = new Intent(BaseTimelineFragment.ACTION_COMMENT_COUNT_CHANGED);
                            countIntent.putExtra("post_id", postId);
                            CommentsFragment.this.getActivity().sendBroadcast(countIntent, CrossConstants.BROADCAST_PERMISSION);
                            }
                        }
                    }

                    
           class RunnableC01651 implements Runnable {
                        final  ListView val$listView;

               RunnableC01651(ListView listView) {
                            this.val$listView = listView;
                        }

                        @Override 
               public void run() {
                   this.val$listView.setSelection(this.val$listView.getCount() - 1);
                        }
                    }

                    @Override 
           public void onDeleteCommentComplete(String reqId, int statusCode, String reasonPhrase, long commentId) {
               PendingRequest request = CommentsFragment.this.removeRequest(reqId);
               if (request != null && statusCode == 200) {
                            if (((CommentViewHolder) CommentsFragment.access$200(CommentsFragment.this).getTag()).userId != CommentsFragment.this.mAppController.getActiveId()) {
                       CommentsFragment.access$1600(CommentsFragment.this, R.string.block_user_comment_delete_prompt);
                            }
                   ((CommentsAdapter) CommentsFragment.this.mAdapter).deleteComment(commentId);
                   if (CommentsFragment.this.mAdapter.getCount() == 0) {
                       CommentsFragment.this.showSadface(true, false);
                            }
                        }
                    }

                    @Override 
           public void onGetUserTypeAheadComplete(String reqId, int statusCode, String reasonPhrase, String query, ArrayList<VineUser> users) {
               if (CommentsFragment.this.removeRequest(reqId) != null && statusCode == 200 && !users.isEmpty()) {
                   VineSuggestionsProvider.addRealtimeUserSuggestions(query, users);
                   CommentsFragment.access$1500(CommentsFragment.this).performFilter(false);
                        }
                    }

                    @Override 
           public void onGetTagTypeAheadComplete(String reqId, int statusCode, String reasonPhrase, String query, ArrayList<VineTag> tags) {
               if (CommentsFragment.this.removeRequest(reqId) != null && statusCode == 200 && !tags.isEmpty()) {
                   VineSuggestionsProvider.addRealtimeTagSuggestions(query, tags);
                   CommentsFragment.access$1500(CommentsFragment.this).performFilter(false);
                        }
                    }

                    @Override 
           public void onCaptchaRequired(String reqId, String captchaUrl, PendingAction action) {
               PendingRequest req = CommentsFragment.this.removeRequest(reqId);
               if (req != null) {
                   CommentsFragment.this.mPendingRequestHelper.onCaptchaRequired(CommentsFragment.this.getActivity(), reqId, action.actionCode, action.bundle, captchaUrl);
                        }
                    }
                }
            }
