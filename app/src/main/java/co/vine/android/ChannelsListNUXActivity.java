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
            
            
    public class ChannelsListNUXActivity extends BaseControllerActionBarActivity {
                private static final int DIALOG_CONFIRM_SKIP = 1;
                private ArrayList<String> mChannelIdsToFollow = new ArrayList<>();

        static  void access$100(ChannelsListNUXActivity x0) {
            x0.toNextStep();
                }

                @Override 
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState, C0257R.layout.fragment_layout, true, true);
            setupActionBar((Boolean) false, (Boolean) true, C0257R.string.show_me, (Boolean) false, (Boolean) false);
            if (savedInstanceState == null) {
                Intent intent = new Intent();
                ChannelsListFragment fragment = new ChannelsListFragment();
                intent.putExtra("take_focus", true);
                intent.putExtra(ChannelsListFragment.ARG_SHOW_CHANNEL_FOLLOW, true);
                fragment.setArguments(BaseCursorListFragment.prepareArguments(intent, false));
                getSupportFragmentManager().beginTransaction().add(C0257R.id.fragment_container, fragment, "channels_list").commit();
                    }
            this.mAppSessionListener = new ChannelsListNUXListener(this, null);
            FlurryUtils.trackNuxScreenDisplayed("channel_follow");
                }

                @Override 
        public boolean onOptionsItemSelected(MenuItem item) {
            if (item.getItemId() == 2131427941) {
                ChannelsListFragment fragment = getFragment();
                if (fragment != null) {
                    if (fragment.getSelectedChannelIds().isEmpty()) {
                        showSkipConfirmation();
                        return true;
                            }
                    followChannelsAndFinish(fragment);
                    return true;
                        }
                toNextStep();
                return true;
                    }
            return true;
                }

        private void showSkipConfirmation() {
            PromptDialogSupportFragment dialog = PromptDialogSupportFragment.newInstance(1).setMessage(C0257R.string.NUX_are_you_sure_you_want_to_skip).setNegativeButton(C0257R.string.NUX_yes_im_sure).setPositiveButton(C0257R.string.NUX_go_back);
            dialog.setListener(new C01621());
                    try {
                dialog.show(getSupportFragmentManager());
                    } catch (Exception e) {
                    }
                }

                
        class C01621 implements PromptDialogSupportFragment.OnDialogDoneListener {
            C01621() {
                    }

                    @Override 
            public void onDialogDone(DialogInterface dialog, int id, int which) {
                        switch (which) {
                            case -2:
                        ChannelsListNUXActivity.access$100(ChannelsListNUXActivity.this);
                                break;
                            case -1:
                        dialog.dismiss();
                                break;
                        }
                    }
                }

        private void followChannelsAndFinish(ChannelsListFragment fragment) {
            ProgressDialog dialog = new ProgressDialog(this, C0257R.style.ProgressDialogTheme);
            this.mProgressDialog = dialog;
            dialog.setProgressStyle(0);
            dialog.setMessage(getString(C0257R.string.following_channels));
                    try {
                dialog.show();
                    } catch (Exception e) {
                    }
           this.mChannelIdsToFollow.addAll(fragment.getSelectedChannelIds());
           this.mAppController.bulkFollowChannels(this.mChannelIdsToFollow);
                }

                @Override 
       public void onBackPressed() {
                }

       private ChannelsListFragment getFragment() {
           FragmentManager manager = getSupportFragmentManager();
           Fragment fragment = manager.findFragmentByTag("channels_list");
           if (fragment instanceof ChannelsListFragment) {
               return (ChannelsListFragment) fragment;
                    }
                    return null;
                }

       private class ChannelsListNUXListener extends AppSessionListener {
           private ChannelsListNUXListener() {
                    }

            ChannelsListNUXListener(ChannelsListNUXActivity x0, C01621 x1) {
               this();
                    }

                    @Override 
           public void onBulkFollowChannelComplete(String reqId, int statusCode, String reasonPhrase) {
               ChannelsListNUXActivity.this.dismissDialog();
               ChannelsListNUXActivity.access$100(ChannelsListNUXActivity.this);
                    }
                }

       private void toNextStep() {
           FlurryUtils.trackNuxChannelFollows(this.mChannelIdsToFollow);
           FindFriendsNUXActivity.start(this);
           finish();
                }
            }
