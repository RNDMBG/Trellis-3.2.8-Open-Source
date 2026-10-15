            package co.vine.android;

            import android.app.Activity;
            import android.content.DialogInterface;
            import android.content.Intent;
            import android.os.Bundle;
            import android.text.TextUtils;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.Button;
            import android.widget.ListAdapter;
            import android.widget.ListView;
            import android.widget.ProgressBar;
            import android.widget.TextView;
            import android.widget.Toast;
            import co.vine.android.api.ComplaintMenuOption;
            import co.vine.android.client.AppSessionListener;
            import co.vine.android.widget.Typefaces;
            import co.vine.android.widgets.PromptDialogSupportFragment;
            import com.edisonwang.android.slog.SLog;
            import com.twitter.android.widget.RefreshableListView;
            import java.util.ArrayList;
            
            
    public class ReportingFragment extends BaseArrayListFragment {
                public static final int BLOCK_COLOR = 16734567;
                public static final int DIALOG_ERROR = 0;
                public static final String EXTRA_BLOCKED = "blocked";
                public static final String EXTRA_BLOCKING_USER = "blockingUser";
                public static final String EXTRA_COMMENT_ID = "commentId";
                public static final String EXTRA_EVENT = "event";
                public static final String EXTRA_MENU_TYPE = "menu_type";
                public static final String EXTRA_POST_ID = "postId";
                public static final String EXTRA_USERNAME = "username";
                public static final String EXTRA_USER_ID = "userId";
                private View mBlockPersonContainer;
                private TextView mBlockPersonSubtext;
                private TextView mBlockPersonText;
                private View mBlockSelector;
                private boolean mBlockingUser;
                private ComplaintMenuOption.ComplaintChoice mChoice;
                private long mCommentId;
                private ComplaintMenuOption mComplaintMenuOptions;
                private String mComplaintType;
                private long mPostId;
                private ProgressBar mProgressBar;
                private TextView mPromptText;
                private Button mReportButton;
                private View mReportContainer;
                private View mReportMenu;
                private View mReportSelector;
                private View mSelectedView;
                private long mUserId;
                private String mUsernameToBlock;

        static  View access$000(ReportingFragment x0) {
            return x0.mBlockSelector;
                }

        static  Button access$100(ReportingFragment x0) {
            return x0.mReportButton;
                }

        static  View access$200(ReportingFragment x0) {
            return x0.mReportSelector;
                }

        static  View access$300(ReportingFragment x0) {
            return x0.mReportMenu;
                }

        static  ComplaintMenuOption.ComplaintChoice access$400(ReportingFragment x0) {
            return x0.mChoice;
                }

        static  ComplaintMenuOption.ComplaintChoice access$402(ReportingFragment x0, ComplaintMenuOption.ComplaintChoice x1) {
            x0.mChoice = x1;
                    return x1;
                }

        static  View access$500(ReportingFragment x0) {
            return x0.mSelectedView;
                }

        static  View access$502(ReportingFragment x0, View x1) {
            x0.mSelectedView = x1;
                    return x1;
                }

                @Override 
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Bundle args = getArguments();
            this.mAppSessionListener = new ReportingAppSessionListener();
            this.mAppController.addListener(this.mAppSessionListener);
            this.mComplaintType = args.getString(EXTRA_MENU_TYPE);
            this.mPostId = args.getLong("postId", -1L);
            this.mUserId = args.getLong("userId", -1L);
            this.mCommentId = args.getLong(EXTRA_COMMENT_ID);
            this.mBlockingUser = args.getBoolean(EXTRA_BLOCKING_USER, false);
            this.mUsernameToBlock = args.getString("username");
            setHasOptionsMenu(true);
                }

        public static ReportingFragment newFragment(Intent i) {
            Bundle args = i.getExtras();
            String commentId = "-1";
            if (i != null) {
                commentId = i.getStringExtra(EXTRA_COMMENT_ID);
                    }
            if (!TextUtils.isEmpty(commentId)) {
                args.putLong(EXTRA_COMMENT_ID, Long.valueOf(commentId).longValue());
                    } else {
                args.putLong(EXTRA_COMMENT_ID, -1L);
                    }
            ReportingFragment fragment = new ReportingFragment();
            fragment.setArguments(args);
            return fragment;
                }

                @Override 
       public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
           return createView(inflater, R.layout.reporting_menu, container);
                }

                @Override 
       public void onViewCreated(View view, Bundle savedInstanceState) {
           Activity activity = getActivity();
           this.mAdapter = new ComplaintChoiceAdapter(activity, R.layout.settings_check, new ComplaintMenuOption.ComplaintChoice[0]);
           this.mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
           this.mReportMenu = view.findViewById(R.id.reporting_custom_menu);
           ((RefreshableListView) this.mListView).disablePTR();
           this.mListView.setAdapter((ListAdapter) this.mAdapter);
           this.mBlockPersonText = (TextView) view.findViewById(R.id.block_person_text);
           this.mBlockPersonSubtext = (TextView) view.findViewById(R.id.block_person_subtitle);
           if (!TextUtils.isEmpty(this.mUsernameToBlock)) {
               this.mBlockPersonText.setText(getString(R.string.block_username, this.mUsernameToBlock));
               this.mBlockPersonSubtext.setText(getString(R.string.block_username_explanation, this.mUsernameToBlock));
                    }
           this.mBlockPersonText.setTypeface(Typefaces.get(activity).getContentTypeface(0, 4));
           TextView fileReportText = (TextView) view.findViewById(R.id.file_report_text);
           fileReportText.setTypeface(Typefaces.get(activity).getContentTypeface(0, 4));
           this.mBlockSelector = view.findViewById(R.id.block_switch);
           ComplaintChoiceAdapter.setSelected(activity, this.mBlockSelector, true, BLOCK_COLOR);
           this.mReportSelector = view.findViewById(R.id.report_switch);
           ComplaintChoiceAdapter.setSelected(activity, this.mReportSelector, false, -1);
           this.mReportButton = (Button) view.findViewById(R.id.report_button);
           this.mReportButton.setOnClickListener(new ViewOnClickListenerC02681());
           this.mBlockPersonContainer = view.findViewById(R.id.block_person_container);
           this.mReportContainer = view.findViewById(R.id.report_container);
           this.mBlockPersonContainer.setOnClickListener(new ViewOnClickListenerC02692());
           this.mReportContainer.setOnClickListener(new ViewOnClickListenerC02703());
           this.mPromptText = (TextView) view.findViewById(R.id.reporting_prompt);
           this.mAppController.fetchComplaintsMenu();
                }

                
       class ViewOnClickListenerC02681 implements View.OnClickListener {
           ViewOnClickListenerC02681() {
                    }

                    @Override 
           public void onClick(View v) {
               ReportingFragment.this.sendReport();
                    }
                }

                
       class ViewOnClickListenerC02692 implements View.OnClickListener {
           ViewOnClickListenerC02692() {
                    }

                    @Override 
           public void onClick(View v) {
               Activity act = ReportingFragment.this.getActivity();
               if (!ReportingFragment.access$000(ReportingFragment.this).isSelected()) {
                   ComplaintChoiceAdapter.setSelected(act, ReportingFragment.access$000(ReportingFragment.this), true, ReportingFragment.BLOCK_COLOR);
                   ReportingFragment.access$100(ReportingFragment.this).setEnabled(true);
                        } else {
                   ComplaintChoiceAdapter.setSelected(act, ReportingFragment.access$000(ReportingFragment.this), false, -1);
                   if (!ReportingFragment.access$200(ReportingFragment.this).isSelected()) {
                       ReportingFragment.access$100(ReportingFragment.this).setEnabled(false);
                            }
                        }
                    }
                }

                
       class ViewOnClickListenerC02703 implements View.OnClickListener {
           ViewOnClickListenerC02703() {
                    }

                    @Override 
           public void onClick(View v) {
               Activity act = ReportingFragment.this.getActivity();
               if (!ReportingFragment.access$200(ReportingFragment.this).isSelected()) {
                   ReportingFragment.access$300(ReportingFragment.this).setVisibility(0);
                   ReportingFragment.access$100(ReportingFragment.this).setText(R.string.send_report);
                   ComplaintChoiceAdapter.setSelected(act, ReportingFragment.access$200(ReportingFragment.this), true, ReportingFragment.this.getResources().getColor(R.color.vine_green));
                   ReportingFragment.access$100(ReportingFragment.this).setEnabled(true);
                   return;
                        }
               ReportingFragment.access$300(ReportingFragment.this).setVisibility(4);
               if (ReportingFragment.access$400(ReportingFragment.this) != null) {
                   ReportingFragment.access$400(ReportingFragment.this).selected = false;
                        }
               ReportingFragment.access$402(ReportingFragment.this, null);
               if (ReportingFragment.access$500(ReportingFragment.this) != null) {
                   ComplaintChoiceAdapter.setSelected(act, ReportingFragment.access$500(ReportingFragment.this), false, -1);
                        }
               ReportingFragment.access$502(ReportingFragment.this, null);
               ComplaintChoiceAdapter.setSelected(act, ReportingFragment.access$200(ReportingFragment.this), false, -1);
               ReportingFragment.access$100(ReportingFragment.this).setText(R.string.block_button);
               if (!ReportingFragment.access$000(ReportingFragment.this).isSelected()) {
                   ReportingFragment.access$100(ReportingFragment.this).setEnabled(false);
                        }
                    }
                }

                @Override 
       public void onListItemClick(ListView l, View v, int position, long id) {
           Activity activity = getActivity();
           ComplaintMenuOption.ComplaintChoice c = (ComplaintMenuOption.ComplaintChoice) this.mAdapter.getItem(position);
           View selectionIndicator = v.findViewById(R.id.selection_indicator);
           ComplaintChoiceAdapter.setSelected(activity, selectionIndicator, true, getResources().getColor(R.color.vine_green));
           if (this.mSelectedView != null) {
               ComplaintChoiceAdapter.setSelected(activity, this.mSelectedView, false, -1);
               if (this.mChoice != null) {
                   this.mChoice.selected = false;
                   this.mChoice = null;
                        }
                    }
           this.mSelectedView = selectionIndicator;
           if (this.mSelectedView.isSelected()) {
               c.selected = true;
               this.mChoice = c;
                    } else {
               this.mSelectedView = null;
                    }
                }

       public void setReportingMenu(ArrayList<ComplaintMenuOption> complaintMenuOptions) {
           this.mProgressBar.setVisibility(8);
           boolean found = false;
                    for (int i = 0; i < complaintMenuOptions.size(); i++) {
               ComplaintMenuOption o = complaintMenuOptions.get(i);
               if (o.complaintType.equals(this.mComplaintType)) {
                   this.mComplaintMenuOptions = o;
                   found = true;
                        }
                    }
           if (!found) {
               showErrorDialog();
                    }
           if (this.mComplaintMenuOptions != null) {
               this.mPromptText.setText(this.mComplaintMenuOptions.prompt);
                    }
           this.mAdapter = new ComplaintChoiceAdapter(getActivity(), R.layout.settings_check, this.mComplaintMenuOptions.complaintChoices);
           this.mListView.setAdapter((ListAdapter) this.mAdapter);
                }

       public void sendReport() {
           Activity activity = getActivity();
           if (activity != null) {
               if (this.mBlockSelector.isSelected() && !this.mBlockingUser) {
                   this.mAppController.blockUser(this.mUserId, this.mUsernameToBlock);
                        }
               if (this.mChoice != null && !TextUtils.isEmpty(this.mChoice.value)) {
                   if (this.mComplaintType.equals("post")) {
                       this.mAppController.reportPost(this.mAppController.getActiveSession(), this.mPostId, this.mChoice.value);
                            } else if (this.mComplaintType.equals("user")) {
                       this.mAppController.reportPerson(this.mUserId, this.mChoice.value);
                            } else if (this.mComplaintType.equals("comment")) {
                       this.mAppController.spamComment(Long.toString(this.mCommentId), Long.toString(this.mPostId), this.mChoice.value);
                            }
                        }
               if (this.mChoice != null && !TextUtils.isEmpty(this.mChoice.confirmation)) {
                   Toast.makeText(activity, this.mChoice.confirmation, 0).show();
                        }
               Intent i = new Intent();
               i.putExtra("postId", this.mPostId);
               i.putExtra("userId", this.mUserId);
               i.putExtra(EXTRA_COMMENT_ID, this.mCommentId);
               if (this.mChoice != null && !TextUtils.isEmpty(this.mChoice.value)) {
                   i.putExtra("event", this.mChoice.value);
                        }
               i.putExtra("blocked", this.mBlockSelector.isSelected());
               i.putExtra("username", this.mUsernameToBlock);
               activity.setResult(-1, i);
               activity.finish();
                    }
                }

       protected void showErrorDialog() {
           PromptDialogSupportFragment dialog = PromptDialogSupportFragment.newInstance(0).setMessage(R.string.API_ERROR_UNKNOWN_ERROR).setPositiveButton(R.string.ok);
           dialog.setListener(new C02714());
                    try {
               dialog.show(getActivity().getSupportFragmentManager());
                    } catch (Exception e) {
               SLog.m459e("Exception showing error dialog", (Throwable) e);
                    }
                }

                
       class C02714 implements PromptDialogSupportFragment.OnDialogDoneListener {
           C02714() {
                    }

                    @Override 
           public void onDialogDone(DialogInterface dialog, int id, int which) {
                        switch (which) {
                            case -1:
                       dialog.dismiss();
                       ReportingFragment.this.getActivity().finish();
                                break;
                        }
                    }
                }

       class ReportingAppSessionListener extends AppSessionListener {
           ReportingAppSessionListener() {
                    }

                    @Override 
           public void onComplaintMenu(String reqId, int statusCode, ArrayList<ComplaintMenuOption> complaintMenu) {
               if (statusCode < 200 || statusCode >= 300) {
                   ReportingFragment.this.showErrorDialog();
                   ReportingFragment.this.getActivity().finish();
                        } else {
                   ReportingFragment.this.setReportingMenu(complaintMenu);
                        }
                    }
                }
            }
