            package co.vine.android;

            import android.app.ProgressDialog;
            import android.content.DialogInterface;
            import android.content.Intent;
            import android.os.Bundle;
            import android.support.v4.app.Fragment;
            import android.support.v4.app.FragmentManager;
            import android.view.MenuItem;
            import co.vine.android.client.AppSessionListener;
            import co.vine.android.util.analytics.FlurryUtils;
            import co.vine.android.widgets.PromptDialogSupportFragment;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 18 */    public class ChannelsListNUXActivity extends BaseControllerActionBarActivity {
                private static final int DIALOG_CONFIRM_SKIP = 1;
                private ArrayList<String> mChannelIdsToFollow = new ArrayList<>();

/* 19 */        static /* synthetic */ void access$100(ChannelsListNUXActivity x0) {
/* 20 */            x0.toNextStep();
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 27 */        public void onCreate(Bundle savedInstanceState) {
/* 28 */            super.onCreate(savedInstanceState, C0257R.layout.fragment_layout, true, true);
/* 29 */            setupActionBar((Boolean) false, (Boolean) true, C0257R.string.show_me, (Boolean) false, (Boolean) false);
/* 31 */            if (savedInstanceState == null) {
/* 32 */                Intent intent = new Intent();
/* 33 */                ChannelsListFragment fragment = new ChannelsListFragment();
/* 34 */                intent.putExtra("take_focus", true);
/* 35 */                intent.putExtra(ChannelsListFragment.ARG_SHOW_CHANNEL_FOLLOW, true);
/* 36 */                fragment.setArguments(BaseCursorListFragment.prepareArguments(intent, false));
/* 38 */                getSupportFragmentManager().beginTransaction().add(C0257R.id.fragment_container, fragment, "channels_list").commit();
                    }
/* 44 */            this.mAppSessionListener = new ChannelsListNUXListener(this, null);
/* 45 */            FlurryUtils.trackNuxScreenDisplayed("channel_follow");
                }

                @Override // co.vine.android.BaseActionBarActivity, android.app.Activity
/* 49 */        public boolean onOptionsItemSelected(MenuItem item) {
/* 50 */            if (item.getItemId() == 2131427941) {
/* 51 */                ChannelsListFragment fragment = getFragment();
/* 52 */                if (fragment != null) {
/* 53 */                    if (fragment.getSelectedChannelIds().isEmpty()) {
/* 54 */                        showSkipConfirmation();
/* 62 */                        return true;
                            }
/* 56 */                    followChannelsAndFinish(fragment);
/* 62 */                    return true;
                        }
/* 59 */                toNextStep();
/* 62 */                return true;
                    }
/* 62 */            return true;
                }

/* 65 */        private void showSkipConfirmation() {
/* 66 */            PromptDialogSupportFragment dialog = PromptDialogSupportFragment.newInstance(1).setMessage(C0257R.string.NUX_are_you_sure_you_want_to_skip).setNegativeButton(C0257R.string.NUX_yes_im_sure).setPositiveButton(C0257R.string.NUX_go_back);
/* 71 */            dialog.setListener(new C01621());
                    try {
/* 87 */                dialog.show(getSupportFragmentManager());
                    } catch (Exception e) {
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.ChannelsListNUXActivity$1 */
/* 69 */        class C01621 implements PromptDialogSupportFragment.OnDialogDoneListener {
/* 70 */            C01621() {
                    }

                    @Override // co.vine.android.widgets.PromptDialogFragment.OnDialogDoneListener
/* 73 */            public void onDialogDone(DialogInterface dialog, int id, int which) {
                        switch (which) {
                            case -2:
/* 76 */                        ChannelsListNUXActivity.access$100(ChannelsListNUXActivity.this);
                                break;
                            case -1:
/* 80 */                        dialog.dismiss();
                                break;
                        }
                    }
                }

/* 93 */        private void followChannelsAndFinish(ChannelsListFragment fragment) {
/* 94 */            ProgressDialog dialog = new ProgressDialog(this, C0257R.style.ProgressDialogTheme);
/* 95 */            this.mProgressDialog = dialog;
/* 96 */            dialog.setProgressStyle(0);
/* 97 */            dialog.setMessage(getString(C0257R.string.following_channels));
                    try {
/* 99 */                dialog.show();
                    } catch (Exception e) {
                    }
/* 103 */           this.mChannelIdsToFollow.addAll(fragment.getSelectedChannelIds());
/* 104 */           this.mAppController.bulkFollowChannels(this.mChannelIdsToFollow);
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 109 */       public void onBackPressed() {
                }

/* 112 */       private ChannelsListFragment getFragment() {
/* 113 */           FragmentManager manager = getSupportFragmentManager();
/* 114 */           Fragment fragment = manager.findFragmentByTag("channels_list");
/* 115 */           if (fragment instanceof ChannelsListFragment) {
/* 116 */               return (ChannelsListFragment) fragment;
                    }
                    return null;
                }

/* 119 */       private class ChannelsListNUXListener extends AppSessionListener {
/* 120 */           private ChannelsListNUXListener() {
                    }

/* 120 */           /* synthetic */ ChannelsListNUXListener(ChannelsListNUXActivity x0, C01621 x1) {
/* 121 */               this();
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 123 */           public void onBulkFollowChannelComplete(String reqId, int statusCode, String reasonPhrase) {
/* 124 */               ChannelsListNUXActivity.this.dismissDialog();
/* 125 */               ChannelsListNUXActivity.access$100(ChannelsListNUXActivity.this);
                    }
                }

/* 129 */       private void toNextStep() {
/* 130 */           FlurryUtils.trackNuxChannelFollows(this.mChannelIdsToFollow);
/* 131 */           FindFriendsNUXActivity.start(this);
/* 132 */           finish();
                }
            }
