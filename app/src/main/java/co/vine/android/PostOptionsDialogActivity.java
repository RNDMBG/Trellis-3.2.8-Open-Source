            package co.vine.android;

            import android.app.Activity;
            import android.content.Context;
            import android.content.DialogInterface;
            import android.content.Intent;
            import android.content.res.Resources;
            import android.os.Bundle;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.ArrayAdapter;
            import android.widget.ListView;
            import android.widget.TextView;
            import co.vine.android.api.VinePost;
            import co.vine.android.client.AppController;
            import co.vine.android.util.CommonUtil;
            import co.vine.android.util.analytics.FlurryUtils;
            import co.vine.android.widgets.PromptDialogSupportFragment;
            import java.util.ArrayList;
            
            
    public class PostOptionsDialogActivity extends SupportListActivity implements View.OnClickListener {
                public static final String ACTION_BLOCK_OR_REPORT = "block_or_report";
                public static final String ACTION_DELETE = "action_delete";
                public static final String ACTION_FACEBOOK = "action_facebook";
                public static final String ACTION_ORIGINAL_POST = "action_original_post";
                public static final String ACTION_REPORT = "action_report";
                public static final String ACTION_REVINE = "action_revine";
                public static final String ACTION_SHARE_OTHER = "action_other";
                public static final String ACTION_TWITTER = "action_twitter";
                public static final String ACTION_UNFAVORITE_USER = "unfavorite_user";
                public static final String ACTION_UNFOLLOW = "unfollow";
                public static final String ACTION_VM = "action_vm";
                private static final int DIALOG_DELETE = 2;
                private static final int DIALOG_REPORT = 1;
                public static final String EXTRA_DELETE = "delete";
                public static final String EXTRA_DESCRIPTION = "description";
                public static final String EXTRA_FAVORITE_USER = "favorite_user";
                public static final String EXTRA_FLURRY_EVENT_SOURCE = "flurry_event_source";
                public static final String EXTRA_FOLLOWING = "following";
                public static final String EXTRA_MY_REPOST_ID = "revine_id";
                public static final String EXTRA_NAME = "name";
                public static final String EXTRA_ORIGINAL_POST = "original_post";
                public static final String EXTRA_POST_FACEBOOK = "post_facebook";
                public static final String EXTRA_POST_ID = "post_id";
                public static final String EXTRA_POST_TWITTER = "post_twitter";
                public static final String EXTRA_POST_USER_ID = "post_user_id";
                public static final String EXTRA_REPORT = "report";
                public static final String EXTRA_REPOST_ID = "vinerepost_repost_id";
                public static final String EXTRA_REVINE = "revine";
                public static final String EXTRA_SHARE_OTHER = "share_other";
                public static final String EXTRA_SHARE_URL = "share_url";
                public static final String EXTRA_SHARE_VM = "share_vm";
                public static final String EXTRA_THUMBNAIL_URL = "thumbnail_url";
                public static final String EXTRA_UNFOLLOW = "unfollow";
                public static final String EXTRA_VIDEO_URL = "video_url";
                public static final String EXTRA_WAS_REVINED = "was_revined";
                private static final int ID_CANCEL = 7;
                private static final int ID_DELETE = 6;
                private static final int ID_FACEBOOK = 2;
                private static final int ID_ORIGINAL_POST = 8;
                private static final int ID_REPORT = 5;
                private static final int ID_REVINE = 9;
                private static final int ID_SHARE_MORE = 10;
                private static final int ID_SHARE_OTHER = 4;
                private static final int ID_TWITTER = 1;
                private static final int ID_UNFAVORITE_USER = 11;
                private static final int ID_UNFOLLOW = 12;
                private static final int ID_VM = 3;
                private ArrayAdapter<Option> mArrayAdapter;
                private boolean mDelete;
                private boolean mIsFavoriteUser;
                private ArrayList<Option> mItems2;
                private String mName;
                private boolean mOriginalPost;
                private boolean mPostFacebook;
                private long mPostId;
                private boolean mPostTwitter;
                private long mPostUserId;
                private boolean mReport;
                private final PromptDialogSupportFragment.OnDialogDoneListener mReportDeleteDialogDoneListener = new C02521();
                private boolean mRevine;
                private boolean mShareOther;
                private boolean mUnfollow;
                private boolean mVm;
                private boolean mWasRevined;

        static  long access$000(PostOptionsDialogActivity x0) {
            return x0.mPostId;
                }

                @Override 
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
           setContentView(C0257R.layout.option_list);
           Bundle b = getIntent().getExtras();
           if (b != null) {
               this.mPostTwitter = b.getBoolean(EXTRA_POST_TWITTER, false);
               this.mPostFacebook = b.getBoolean(EXTRA_POST_FACEBOOK, false);
               this.mShareOther = b.getBoolean(EXTRA_SHARE_OTHER, false);
               this.mVm = b.getBoolean(EXTRA_SHARE_VM, false);
               this.mReport = b.getBoolean(EXTRA_REPORT, true);
               this.mUnfollow = b.getBoolean("unfollow", true);
               this.mDelete = b.getBoolean("delete", false);
               this.mRevine = b.getBoolean(EXTRA_REVINE, false);
               this.mWasRevined = b.getBoolean(EXTRA_WAS_REVINED, false);
               this.mPostId = b.getLong("post_id");
               this.mPostUserId = b.getLong(EXTRA_POST_USER_ID);
               this.mName = b.getString("name");
               this.mOriginalPost = b.getBoolean(EXTRA_ORIGINAL_POST, false);
               this.mIsFavoriteUser = b.getBoolean(EXTRA_FAVORITE_USER, false);
                    }
           Resources res = getResources();
           this.mArrayAdapter = new OptionArrayAdapter(this, C0257R.layout.post_more_row);
           ArrayList<Option> items = new ArrayList<>();
           if (this.mVm) {
               items.add(new Option(3, res.getString(C0257R.string.share_vm)));
                    }
           if (this.mRevine) {
               int revineStringId = this.mWasRevined ? C0257R.string.unrevine_list_option : C0257R.string.revine_list_option;
               items.add(new Option(9, res.getString(revineStringId)));
                    }
           if (this.mOriginalPost) {
               items.add(new Option(8, res.getString(C0257R.string.original_post)));
                    }
           ArrayList<Option> items2 = new ArrayList<>();
           if (this.mPostTwitter) {
               items2.add(new Option(1, res.getString(C0257R.string.share_twitter)));
                    }
           if (this.mPostFacebook) {
               items2.add(new Option(2, res.getString(C0257R.string.share_facebook)));
                    }
           if (this.mShareOther) {
               items2.add(new Option(4, res.getString(C0257R.string.share_other)));
                    }
           if (this.mUnfollow) {
               items.add(new Option(12, res.getString(C0257R.string.unfollow)));
                    }
           if (this.mReport) {
               items.add(new Option(5, res.getString(C0257R.string.block_or_report)));
                    }
           if (this.mDelete) {
               items.add(new Option(6, res.getString(C0257R.string.delete_post)));
                    }
           if (this.mIsFavoriteUser) {
               items.add(new Option(11, res.getString(C0257R.string.unfavorite)));
                    }
           if (items2.size() > 0) {
               items.add(new Option(10, res.getString(C0257R.string.more_options)));
               items2.add(new Option(7, res.getString(C0257R.string.cancel)));
                    }
           this.mItems2 = items2;
           items.add(new Option(7, res.getString(C0257R.string.cancel)));
           invalidateOptions(items);
                }

       private void invalidateOptions(ArrayList<Option> items) {
           this.mArrayAdapter.clear();
                    for (Option item : items) {
               this.mArrayAdapter.add(item);
                    }
           setListAdapter(this.mArrayAdapter);
           getListView().setChoiceMode(0);
           getListView().setAdapter(getListAdapter());
                }

                @Override 
       public void onClick(View view) {
                }

                @Override 
       protected void onListItemClick(ListView l, View v, int position, long id) {
           Intent intent = getIntent();
           int optionId = ((Integer) v.getTag()).intValue();
                    switch (optionId) {
                        case 1:
                   intent.setAction(ACTION_TWITTER);
                   setResult(-1, intent);
                   finish();
                            break;
                        case 2:
                   intent.setAction(ACTION_FACEBOOK);
                   setResult(-1, intent);
                   finish();
                            break;
                        case 3:
                   intent.setAction(ACTION_VM);
                   setResult(-1, intent);
                   finish();
                            break;
                        case 4:
                   intent.putExtra("post_id", this.mPostId);
                   intent.setAction(ACTION_SHARE_OTHER);
                   setResult(-1, intent);
                   finish();
                            break;
                        case 5:
                   intent.putExtra("post_id", this.mPostId);
                   intent.putExtra(EXTRA_POST_USER_ID, this.mPostUserId);
                   intent.putExtra("name", this.mName);
                   intent.setAction(ACTION_BLOCK_OR_REPORT);
                   setResult(-1, intent);
                   finish();
                            break;
                        case 6:
                   PromptDialogSupportFragment promptDialogFragment = PromptDialogSupportFragment.newInstance(2);
                   promptDialogFragment.setListener(this.mReportDeleteDialogDoneListener);
                   promptDialogFragment.setMessage(C0257R.string.delete_confirm);
                   promptDialogFragment.setPositiveButton(C0257R.string.delete_post);
                   promptDialogFragment.setNeutralButton(C0257R.string.cancel);
                   promptDialogFragment.show(getSupportFragmentManager());
                            break;
                        case 7:
                   setResult(0, intent);
                   finish();
                            break;
                        case 8:
                   intent.setAction(ACTION_ORIGINAL_POST);
                   setResult(-1, intent);
                   finish();
                            break;
                        case 9:
                   intent.setAction(ACTION_REVINE);
                   setResult(-1, intent);
                   finish();
                            break;
                        case 10:
                   invalidateOptions(this.mItems2);
                            break;
                        case 11:
                   intent.setAction(ACTION_UNFAVORITE_USER);
                   setResult(-1, intent);
                   finish();
                            break;
                        case 12:
                   intent.setAction("unfollow");
                   setResult(-1, intent);
                   finish();
                            break;
                    }
                }

                
       class C02521 implements PromptDialogSupportFragment.OnDialogDoneListener {
           C02521() {
                    }

                    @Override 
           public void onDialogDone(DialogInterface dialog, int id, int which) {
               Intent intent = new Intent();
               intent.putExtra("post_id", PostOptionsDialogActivity.access$000(PostOptionsDialogActivity.this));
                        switch (id) {
                            case 1:
                                switch (which) {
                                    case -1:
                               intent.setAction(PostOptionsDialogActivity.ACTION_REPORT);
                               PostOptionsDialogActivity.this.setResult(-1, intent);
                               PostOptionsDialogActivity.this.finish();
                                        break;
                                }
                                break;
                            case 2:
                                switch (which) {
                                    case -1:
                               intent.setAction(PostOptionsDialogActivity.ACTION_DELETE);
                               PostOptionsDialogActivity.this.setResult(-1, intent);
                               PostOptionsDialogActivity.this.finish();
                                        break;
                                }
                                break;
                        }
                    }
                }

       public static class Option {

                    
                    public int f82id;
                    public String text;

           public Option(int id, String text) {
               this.f82id = id;
               this.text = text;
                    }
                }

       public static Intent getMoreIntent(VinePost post, Context context, boolean isMyPost, boolean showOriginalPost) {
                    return new Intent(context, (Class<?>) PostOptionsDialogActivity.class).putExtra("post_id", post.postId).putExtra("delete", isMyPost).putExtra("unfollow", !isMyPost).putExtra(EXTRA_REPORT, !isMyPost).putExtra(EXTRA_ORIGINAL_POST, showOriginalPost).putExtra(EXTRA_POST_USER_ID, post.userId).putExtra("name", post.username).putExtra(EXTRA_SHARE_VM, post.isShareable() || isMyPost);
                }

       public static Intent getSinglePostMoreIntent(VinePost post, Context context, boolean isMyPost, boolean showOriginalPost, boolean isFavoriteUser) {
           Intent intent = getMoreIntent(post, context, isMyPost, showOriginalPost);
           intent.putExtra(EXTRA_FAVORITE_USER, isFavoriteUser);
           return intent;
                }

       public static Intent getShareIntent(VinePost post, Context context, boolean isMyPost, long myRepostId, long repostId, String flurryEventSource) {
                    return getShareIntent(post, context, isMyPost, myRepostId, repostId, myRepostId > 0, flurryEventSource);
                }

                public static Intent getShareIntent(VinePost post, Context context, boolean isMyPost, long myRepostId, long repostId, boolean wasRevined, String flurryEventSource) {
                    boolean z = true;
           Intent intent = new Intent(context, (Class<?>) PostOptionsDialogActivity.class);
           intent.putExtra("post_id", post.postId);
           intent.putExtra(EXTRA_REPORT, false);
           intent.putExtra(EXTRA_REVINE, !isMyPost);
           intent.putExtra(EXTRA_MY_REPOST_ID, myRepostId);
           intent.putExtra(EXTRA_REPOST_ID, repostId);
           intent.putExtra(EXTRA_POST_USER_ID, post.userId);
           intent.putExtra("following", post.user != null && post.user.isFollowing());
           intent.putExtra(EXTRA_WAS_REVINED, wasRevined || myRepostId > 0);
           intent.putExtra(EXTRA_FLURRY_EVENT_SOURCE, flurryEventSource);
           if ((post.isShareable() && !post.isPrivate()) || isMyPost) {
               intent.putExtra(EXTRA_POST_TWITTER, post.isShareable() || isMyPost);
               intent.putExtra(EXTRA_POST_FACEBOOK, post.isShareable() || isMyPost);
               intent.putExtra(EXTRA_SHARE_OTHER, post.isShareable());
                        if (!post.isShareable() && !isMyPost) {
                            z = false;
                        }
               intent.putExtra(EXTRA_SHARE_VM, z);
               intent.putExtra("description", post.description);
               intent.putExtra("share_url", post.shareUrl);
               intent.putExtra("thumbnail_url", post.thumbnailUrl);
               intent.putExtra("video_url", post.videoUrl);
               intent.putExtra("name", post.username);
               intent.putExtra(EXTRA_ORIGINAL_POST, false);
                    }
           return intent;
                }

       public static class Result {
                    public final Intent intent;
                    public final String request;

           public Result(String request, Intent intent) {
               this.request = request;
               this.intent = intent;
                    }
                }

       public static Result processActivityResult(AppController appController, Activity activity, int resultCode, Intent data) {
           String request = null;
           Intent intent = null;
           if (resultCode == -1 && data != null) {
               String action = data.getAction();
               if (ACTION_REPORT.equals(action)) {
                   long postId = data.getLongExtra("post_id", 0L);
                            if (postId > 0) {
                       request = appController.reportPost(appController.getActiveSession(), postId);
                            }
                        } else if (ACTION_DELETE.equals(action)) {
                   long postId2 = data.getLongExtra("post_id", 0L);
                            if (postId2 > 0) {
                       request = appController.deletePost(appController.getActiveSession(), postId2);
                            }
                        } else if (ACTION_TWITTER.equals(action)) {
                   intent = ReshareActivity.getReshareIntent(activity, "twitter", data.getExtras());
                        } else if (ACTION_FACEBOOK.equals(action)) {
                   intent = ReshareActivity.getReshareIntent(activity, "facebook", data.getExtras());
                        } else if (ACTION_VM.equals(action)) {
                   long postId3 = data.getLongExtra("post_id", 0L);
                   String videoUrl = data.getStringExtra("video_url");
                   String thumbUrl = data.getStringExtra("thumbnail_url");
                            if (postId3 > 0) {
                       intent = RecipientPickerActivity.getIntent(activity, postId3, videoUrl, thumbUrl);
                            }
                        } else if (ACTION_SHARE_OTHER.equals(action)) {
                   FlurryUtils.trackSharePost(CommonUtil.FIELD_TYPE_OTHER, data.getLongExtra("post_id", 0L));
                   Resources res = activity.getResources();
                   String name = data.getStringExtra("name");
                   String url = data.getStringExtra("share_url");
                   Intent share = new Intent("android.intent.action.SEND");
                   share.setType("text/plain");
                   share.addFlags(524288);
                   share.putExtra("android.intent.extra.SUBJECT", res.getString(C0257R.string.share_post_subject, name));
                   share.putExtra("android.intent.extra.TEXT", res.getString(C0257R.string.share_post_text, name, url));
                   intent = share;
                        } else if (ACTION_ORIGINAL_POST.equals(action)) {
                   long postId4 = data.getLongExtra("post_id", 0L);
                            if (postId4 > 0) {
                       intent = SingleActivity.getIntent(activity, postId4);
                            }
                        } else if (ACTION_REVINE.equals(action)) {
                   long postId5 = data.getLongExtra("post_id", 0L);
                   long myRepostId = data.getLongExtra(EXTRA_MY_REPOST_ID, 0L);
                   long repostId = data.getLongExtra(EXTRA_REPOST_ID, 0L);
                   long postUserId = data.getLongExtra(EXTRA_POST_USER_ID, 0L);
                   boolean following = data.getBooleanExtra("following", false);
                   boolean wasRevined = data.getBooleanExtra(EXTRA_WAS_REVINED, false);
                   String flurryEventSource = data.getStringExtra(EXTRA_FLURRY_EVENT_SOURCE);
                            
                            
                            
                            if (wasRevined && myRepostId <= 0 && repostId > 0) {
                                myRepostId = repostId;
                            }
                   request = wasRevined ? appController.unRevine(flurryEventSource, appController.getActiveSession(), postId5, myRepostId, repostId, following, postUserId, true) : appController.revine(flurryEventSource, appController.getActiveSession(), postId5, repostId, appController.getActiveSession().getName());
                        } else if (ACTION_UNFAVORITE_USER.equals(action)) {
                   long postUserId2 = data.getLongExtra(EXTRA_POST_USER_ID, 0L);
                   appController.favoriteUser(postUserId2, false, true);
                        } else if ("unfollow".equals(action)) {
                   long postUserId3 = data.getLongExtra(EXTRA_POST_USER_ID, 0L);
                   appController.unfollowUser(appController.getActiveSession(), postUserId3, false);
                        } else if (ACTION_BLOCK_OR_REPORT.equals(action)) {
                   long postId6 = data.getLongExtra("post_id", 0L);
                   long userId = data.getLongExtra(EXTRA_POST_USER_ID, 0L);
                   String username = data.getStringExtra("name");
                   Intent i = new Intent(activity, (Class<?>) ReportingActivity.class);
                   i.putExtra(ReportingFragment.EXTRA_MENU_TYPE, "post");
                   i.putExtra("postId", postId6);
                   i.putExtra("userId", userId);
                   i.putExtra("username", username);
                   activity.startActivity(i);
                        }
                    }
           return new Result(request, intent);
                }

       private class OptionArrayAdapter extends ArrayAdapter<Option> {
           public OptionArrayAdapter(Context context, int layout) {
               super(context, layout);
                    }

                    @Override 
           public View getView(int position, View convertView, ViewGroup parent) {
               View v = super.getView(position, convertView, parent);
               Option option = getItem(position);
               TextView title = (TextView) v.findViewById(C0257R.id.item);
               if (option.f82id == 7) {
                   title.setTextColor(PostOptionsDialogActivity.this.getResources().getColor(C0257R.color.solid_light_gray_vm));
                        } else {
                   title.setTextColor(PostOptionsDialogActivity.this.getResources().getColor(C0257R.color.soft_black));
                        }
               title.setText(option.text);
               v.setTag(Integer.valueOf(option.f82id));
               return v;
                    }
                }
            }
