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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 30 */    public class ReportingFragment extends BaseArrayListFragment {
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

/* 31 */        static /* synthetic */ View access$000(ReportingFragment x0) {
/* 32 */            return x0.mBlockSelector;
                }

/* 31 */        static /* synthetic */ Button access$100(ReportingFragment x0) {
/* 32 */            return x0.mReportButton;
                }

/* 31 */        static /* synthetic */ View access$200(ReportingFragment x0) {
/* 32 */            return x0.mReportSelector;
                }

/* 31 */        static /* synthetic */ View access$300(ReportingFragment x0) {
/* 32 */            return x0.mReportMenu;
                }

/* 31 */        static /* synthetic */ ComplaintMenuOption.ComplaintChoice access$400(ReportingFragment x0) {
/* 32 */            return x0.mChoice;
                }

/* 31 */        static /* synthetic */ ComplaintMenuOption.ComplaintChoice access$402(ReportingFragment x0, ComplaintMenuOption.ComplaintChoice x1) {
/* 32 */            x0.mChoice = x1;
                    return x1;
                }

/* 31 */        static /* synthetic */ View access$500(ReportingFragment x0) {
/* 32 */            return x0.mSelectedView;
                }

/* 31 */        static /* synthetic */ View access$502(ReportingFragment x0, View x1) {
/* 32 */            x0.mSelectedView = x1;
                    return x1;
                }

                @Override // co.vine.android.BaseArrayListFragment, co.vine.android.BaseAdapterFragment, co.vine.android.BaseControllerFragment, android.support.v4.app.Fragment
/* 68 */        public void onCreate(Bundle savedInstanceState) {
/* 69 */            super.onCreate(savedInstanceState);
/* 70 */            Bundle args = getArguments();
/* 71 */            this.mAppSessionListener = new ReportingAppSessionListener();
/* 72 */            this.mAppController.addListener(this.mAppSessionListener);
/* 73 */            this.mComplaintType = args.getString(EXTRA_MENU_TYPE);
/* 74 */            this.mPostId = args.getLong("postId", -1L);
/* 75 */            this.mUserId = args.getLong("userId", -1L);
/* 76 */            this.mCommentId = args.getLong(EXTRA_COMMENT_ID);
/* 77 */            this.mBlockingUser = args.getBoolean(EXTRA_BLOCKING_USER, false);
/* 78 */            this.mUsernameToBlock = args.getString("username");
/* 79 */            setHasOptionsMenu(true);
                }

/* 82 */        public static ReportingFragment newFragment(Intent i) {
/* 83 */            Bundle args = i.getExtras();
/* 84 */            String commentId = "-1";
/* 85 */            if (i != null) {
/* 86 */                commentId = i.getStringExtra(EXTRA_COMMENT_ID);
                    }
/* 88 */            if (!TextUtils.isEmpty(commentId)) {
/* 89 */                args.putLong(EXTRA_COMMENT_ID, Long.valueOf(commentId).longValue());
                    } else {
/* 91 */                args.putLong(EXTRA_COMMENT_ID, -1L);
                    }
/* 93 */            ReportingFragment fragment = new ReportingFragment();
/* 94 */            fragment.setArguments(args);
/* 95 */            return fragment;
                }

                @Override // co.vine.android.BaseArrayListFragment, android.support.v4.app.Fragment
/* 100 */       public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
/* 101 */           return createView(inflater, C0257R.layout.reporting_menu, container);
                }

                @Override // co.vine.android.BaseArrayListFragment, android.support.v4.app.Fragment
/* 105 */       public void onViewCreated(View view, Bundle savedInstanceState) {
/* 106 */           Activity activity = getActivity();
/* 107 */           this.mAdapter = new ComplaintChoiceAdapter(activity, C0257R.layout.settings_check, new ComplaintMenuOption.ComplaintChoice[0]);
/* 108 */           this.mProgressBar = (ProgressBar) view.findViewById(C0257R.id.progress_bar);
/* 109 */           this.mReportMenu = view.findViewById(C0257R.id.reporting_custom_menu);
/* 111 */           ((RefreshableListView) this.mListView).disablePTR();
/* 112 */           this.mListView.setAdapter((ListAdapter) this.mAdapter);
/* 114 */           this.mBlockPersonText = (TextView) view.findViewById(C0257R.id.block_person_text);
/* 115 */           this.mBlockPersonSubtext = (TextView) view.findViewById(C0257R.id.block_person_subtitle);
/* 116 */           if (!TextUtils.isEmpty(this.mUsernameToBlock)) {
/* 117 */               this.mBlockPersonText.setText(getString(C0257R.string.block_username, this.mUsernameToBlock));
/* 118 */               this.mBlockPersonSubtext.setText(getString(C0257R.string.block_username_explanation, this.mUsernameToBlock));
                    }
/* 120 */           this.mBlockPersonText.setTypeface(Typefaces.get(activity).getContentTypeface(0, 4));
/* 121 */           TextView fileReportText = (TextView) view.findViewById(C0257R.id.file_report_text);
/* 122 */           fileReportText.setTypeface(Typefaces.get(activity).getContentTypeface(0, 4));
/* 124 */           this.mBlockSelector = view.findViewById(C0257R.id.block_switch);
/* 125 */           ComplaintChoiceAdapter.setSelected(activity, this.mBlockSelector, true, BLOCK_COLOR);
/* 127 */           this.mReportSelector = view.findViewById(C0257R.id.report_switch);
/* 128 */           ComplaintChoiceAdapter.setSelected(activity, this.mReportSelector, false, -1);
/* 130 */           this.mReportButton = (Button) view.findViewById(C0257R.id.report_button);
/* 131 */           this.mReportButton.setOnClickListener(new ViewOnClickListenerC02681());
/* 138 */           this.mBlockPersonContainer = view.findViewById(C0257R.id.block_person_container);
/* 139 */           this.mReportContainer = view.findViewById(C0257R.id.report_container);
/* 141 */           this.mBlockPersonContainer.setOnClickListener(new ViewOnClickListenerC02692());
/* 158 */           this.mReportContainer.setOnClickListener(new ViewOnClickListenerC02703());
/* 186 */           this.mPromptText = (TextView) view.findViewById(C0257R.id.reporting_prompt);
/* 187 */           this.mAppController.fetchComplaintsMenu();
                }

                /* JADX INFO: renamed from: co.vine.android.ReportingFragment$1 */
/* 129 */       class ViewOnClickListenerC02681 implements View.OnClickListener {
/* 130 */           ViewOnClickListenerC02681() {
                    }

                    @Override // android.view.View.OnClickListener
/* 133 */           public void onClick(View v) {
/* 134 */               ReportingFragment.this.sendReport();
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.ReportingFragment$2 */
/* 139 */       class ViewOnClickListenerC02692 implements View.OnClickListener {
/* 140 */           ViewOnClickListenerC02692() {
                    }

                    @Override // android.view.View.OnClickListener
/* 143 */           public void onClick(View v) {
/* 144 */               Activity act = ReportingFragment.this.getActivity();
/* 145 */               if (!ReportingFragment.access$000(ReportingFragment.this).isSelected()) {
/* 146 */                   ComplaintChoiceAdapter.setSelected(act, ReportingFragment.access$000(ReportingFragment.this), true, ReportingFragment.BLOCK_COLOR);
/* 147 */                   ReportingFragment.access$100(ReportingFragment.this).setEnabled(true);
                        } else {
/* 149 */                   ComplaintChoiceAdapter.setSelected(act, ReportingFragment.access$000(ReportingFragment.this), false, -1);
/* 151 */                   if (!ReportingFragment.access$200(ReportingFragment.this).isSelected()) {
/* 152 */                       ReportingFragment.access$100(ReportingFragment.this).setEnabled(false);
                            }
                        }
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.ReportingFragment$3 */
/* 156 */       class ViewOnClickListenerC02703 implements View.OnClickListener {
/* 157 */           ViewOnClickListenerC02703() {
                    }

                    @Override // android.view.View.OnClickListener
/* 160 */           public void onClick(View v) {
/* 161 */               Activity act = ReportingFragment.this.getActivity();
/* 162 */               if (!ReportingFragment.access$200(ReportingFragment.this).isSelected()) {
/* 163 */                   ReportingFragment.access$300(ReportingFragment.this).setVisibility(0);
/* 164 */                   ReportingFragment.access$100(ReportingFragment.this).setText(C0257R.string.send_report);
/* 165 */                   ComplaintChoiceAdapter.setSelected(act, ReportingFragment.access$200(ReportingFragment.this), true, ReportingFragment.this.getResources().getColor(C0257R.color.vine_green));
/* 166 */                   ReportingFragment.access$100(ReportingFragment.this).setEnabled(true);
/* 184 */                   return;
                        }
/* 168 */               ReportingFragment.access$300(ReportingFragment.this).setVisibility(4);
/* 169 */               if (ReportingFragment.access$400(ReportingFragment.this) != null) {
/* 170 */                   ReportingFragment.access$400(ReportingFragment.this).selected = false;
                        }
/* 172 */               ReportingFragment.access$402(ReportingFragment.this, null);
/* 173 */               if (ReportingFragment.access$500(ReportingFragment.this) != null) {
/* 174 */                   ComplaintChoiceAdapter.setSelected(act, ReportingFragment.access$500(ReportingFragment.this), false, -1);
                        }
/* 176 */               ReportingFragment.access$502(ReportingFragment.this, null);
/* 177 */               ComplaintChoiceAdapter.setSelected(act, ReportingFragment.access$200(ReportingFragment.this), false, -1);
/* 178 */               ReportingFragment.access$100(ReportingFragment.this).setText(C0257R.string.block_button);
/* 180 */               if (!ReportingFragment.access$000(ReportingFragment.this).isSelected()) {
/* 181 */                   ReportingFragment.access$100(ReportingFragment.this).setEnabled(false);
                        }
                    }
                }

                @Override // co.vine.android.BaseArrayListFragment
/* 191 */       public void onListItemClick(ListView l, View v, int position, long id) {
/* 192 */           Activity activity = getActivity();
/* 193 */           ComplaintMenuOption.ComplaintChoice c = (ComplaintMenuOption.ComplaintChoice) this.mAdapter.getItem(position);
/* 194 */           View selectionIndicator = v.findViewById(C0257R.id.selection_indicator);
/* 195 */           ComplaintChoiceAdapter.setSelected(activity, selectionIndicator, true, getResources().getColor(C0257R.color.vine_green));
/* 196 */           if (this.mSelectedView != null) {
/* 197 */               ComplaintChoiceAdapter.setSelected(activity, this.mSelectedView, false, -1);
/* 198 */               if (this.mChoice != null) {
/* 199 */                   this.mChoice.selected = false;
/* 200 */                   this.mChoice = null;
                        }
                    }
/* 203 */           this.mSelectedView = selectionIndicator;
/* 204 */           if (this.mSelectedView.isSelected()) {
/* 205 */               c.selected = true;
/* 206 */               this.mChoice = c;
                    } else {
/* 208 */               this.mSelectedView = null;
                    }
                }

/* 212 */       public void setReportingMenu(ArrayList<ComplaintMenuOption> complaintMenuOptions) {
/* 213 */           this.mProgressBar.setVisibility(8);
/* 214 */           boolean found = false;
                    for (int i = 0; i < complaintMenuOptions.size(); i++) {
/* 216 */               ComplaintMenuOption o = complaintMenuOptions.get(i);
/* 217 */               if (o.complaintType.equals(this.mComplaintType)) {
/* 218 */                   this.mComplaintMenuOptions = o;
/* 219 */                   found = true;
                        }
                    }
/* 223 */           if (!found) {
/* 224 */               showErrorDialog();
                    }
/* 227 */           if (this.mComplaintMenuOptions != null) {
/* 228 */               this.mPromptText.setText(this.mComplaintMenuOptions.prompt);
                    }
/* 231 */           this.mAdapter = new ComplaintChoiceAdapter(getActivity(), C0257R.layout.settings_check, this.mComplaintMenuOptions.complaintChoices);
/* 233 */           this.mListView.setAdapter((ListAdapter) this.mAdapter);
                }

/* 237 */       public void sendReport() {
/* 238 */           Activity activity = getActivity();
/* 239 */           if (activity != null) {
/* 242 */               if (this.mBlockSelector.isSelected() && !this.mBlockingUser) {
/* 243 */                   this.mAppController.blockUser(this.mUserId, this.mUsernameToBlock);
                        }
/* 245 */               if (this.mChoice != null && !TextUtils.isEmpty(this.mChoice.value)) {
/* 246 */                   if (this.mComplaintType.equals("post")) {
/* 247 */                       this.mAppController.reportPost(this.mAppController.getActiveSession(), this.mPostId, this.mChoice.value);
                            } else if (this.mComplaintType.equals("user")) {
/* 249 */                       this.mAppController.reportPerson(this.mUserId, this.mChoice.value);
                            } else if (this.mComplaintType.equals("comment")) {
/* 251 */                       this.mAppController.spamComment(Long.toString(this.mCommentId), Long.toString(this.mPostId), this.mChoice.value);
                            }
                        }
/* 254 */               if (this.mChoice != null && !TextUtils.isEmpty(this.mChoice.confirmation)) {
/* 255 */                   Toast.makeText(activity, this.mChoice.confirmation, 0).show();
                        }
/* 258 */               Intent i = new Intent();
/* 259 */               i.putExtra("postId", this.mPostId);
/* 260 */               i.putExtra("userId", this.mUserId);
/* 261 */               i.putExtra(EXTRA_COMMENT_ID, this.mCommentId);
/* 262 */               if (this.mChoice != null && !TextUtils.isEmpty(this.mChoice.value)) {
/* 263 */                   i.putExtra("event", this.mChoice.value);
                        }
/* 265 */               i.putExtra("blocked", this.mBlockSelector.isSelected());
/* 266 */               i.putExtra("username", this.mUsernameToBlock);
/* 267 */               activity.setResult(-1, i);
/* 268 */               activity.finish();
                    }
                }

/* 271 */       protected void showErrorDialog() {
/* 272 */           PromptDialogSupportFragment dialog = PromptDialogSupportFragment.newInstance(0).setMessage(C0257R.string.API_ERROR_UNKNOWN_ERROR).setPositiveButton(C0257R.string.f88ok);
/* 276 */           dialog.setListener(new C02714());
                    try {
/* 289 */               dialog.show(getActivity().getSupportFragmentManager());
                    } catch (Exception e) {
/* 291 */               SLog.m459e("Exception showing error dialog", (Throwable) e);
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.ReportingFragment$4 */
/* 274 */       class C02714 implements PromptDialogSupportFragment.OnDialogDoneListener {
/* 275 */           C02714() {
                    }

                    @Override // co.vine.android.widgets.PromptDialogFragment.OnDialogDoneListener
/* 278 */           public void onDialogDone(DialogInterface dialog, int id, int which) {
                        switch (which) {
                            case -1:
/* 281 */                       dialog.dismiss();
/* 282 */                       ReportingFragment.this.getActivity().finish();
                                break;
                        }
                    }
                }

/* 293 */       class ReportingAppSessionListener extends AppSessionListener {
/* 294 */           ReportingAppSessionListener() {
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 298 */           public void onComplaintMenu(String reqId, int statusCode, ArrayList<ComplaintMenuOption> complaintMenu) {
/* 299 */               if (statusCode < 200 || statusCode >= 300) {
/* 300 */                   ReportingFragment.this.showErrorDialog();
/* 301 */                   ReportingFragment.this.getActivity().finish();
                        } else {
/* 304 */                   ReportingFragment.this.setReportingMenu(complaintMenu);
                        }
                    }
                }
            }
