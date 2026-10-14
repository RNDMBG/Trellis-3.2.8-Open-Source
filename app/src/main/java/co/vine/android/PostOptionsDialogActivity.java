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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 23 */    public class PostOptionsDialogActivity extends SupportListActivity implements View.OnClickListener {
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

/* 24 */        static /* synthetic */ long access$000(PostOptionsDialogActivity x0) {
/* 25 */            return x0.mPostId;
                }

                @Override // android.support.v4.app.FragmentActivity, android.app.Activity
/* 98 */        protected void onCreate(Bundle savedInstanceState) {
/* 99 */            super.onCreate(savedInstanceState);
/* 100 */           setContentView(C0257R.layout.option_list);
/* 101 */           Bundle b = getIntent().getExtras();
/* 102 */           if (b != null) {
/* 103 */               this.mPostTwitter = b.getBoolean(EXTRA_POST_TWITTER, false);
/* 104 */               this.mPostFacebook = b.getBoolean(EXTRA_POST_FACEBOOK, false);
/* 105 */               this.mShareOther = b.getBoolean(EXTRA_SHARE_OTHER, false);
/* 106 */               this.mVm = b.getBoolean(EXTRA_SHARE_VM, false);
/* 107 */               this.mReport = b.getBoolean(EXTRA_REPORT, true);
/* 108 */               this.mUnfollow = b.getBoolean("unfollow", true);
/* 109 */               this.mDelete = b.getBoolean("delete", false);
/* 110 */               this.mRevine = b.getBoolean(EXTRA_REVINE, false);
/* 111 */               this.mWasRevined = b.getBoolean(EXTRA_WAS_REVINED, false);
/* 112 */               this.mPostId = b.getLong("post_id");
/* 113 */               this.mPostUserId = b.getLong(EXTRA_POST_USER_ID);
/* 114 */               this.mName = b.getString("name");
/* 115 */               this.mOriginalPost = b.getBoolean(EXTRA_ORIGINAL_POST, false);
/* 116 */               this.mIsFavoriteUser = b.getBoolean(EXTRA_FAVORITE_USER, false);
                    }
/* 119 */           Resources res = getResources();
/* 120 */           this.mArrayAdapter = new OptionArrayAdapter(this, C0257R.layout.post_more_row);
/* 122 */           ArrayList<Option> items = new ArrayList<>();
/* 124 */           if (this.mVm) {
/* 125 */               items.add(new Option(3, res.getString(C0257R.string.share_vm)));
                    }
/* 128 */           if (this.mRevine) {
/* 129 */               int revineStringId = this.mWasRevined ? C0257R.string.unrevine_list_option : C0257R.string.revine_list_option;
/* 130 */               items.add(new Option(9, res.getString(revineStringId)));
                    }
/* 133 */           if (this.mOriginalPost) {
/* 134 */               items.add(new Option(8, res.getString(C0257R.string.original_post)));
                    }
/* 137 */           ArrayList<Option> items2 = new ArrayList<>();
/* 139 */           if (this.mPostTwitter) {
/* 140 */               items2.add(new Option(1, res.getString(C0257R.string.share_twitter)));
                    }
/* 142 */           if (this.mPostFacebook) {
/* 143 */               items2.add(new Option(2, res.getString(C0257R.string.share_facebook)));
                    }
/* 146 */           if (this.mShareOther) {
/* 147 */               items2.add(new Option(4, res.getString(C0257R.string.share_other)));
                    }
/* 150 */           if (this.mUnfollow) {
/* 151 */               items.add(new Option(12, res.getString(C0257R.string.unfollow)));
                    }
/* 154 */           if (this.mReport) {
/* 155 */               items.add(new Option(5, res.getString(C0257R.string.block_or_report)));
                    }
/* 158 */           if (this.mDelete) {
/* 159 */               items.add(new Option(6, res.getString(C0257R.string.delete_post)));
                    }
/* 162 */           if (this.mIsFavoriteUser) {
/* 163 */               items.add(new Option(11, res.getString(C0257R.string.unfavorite)));
                    }
/* 166 */           if (items2.size() > 0) {
/* 167 */               items.add(new Option(10, res.getString(C0257R.string.more_options)));
/* 168 */               items2.add(new Option(7, res.getString(C0257R.string.cancel)));
                    }
/* 171 */           this.mItems2 = items2;
/* 173 */           items.add(new Option(7, res.getString(C0257R.string.cancel)));
/* 175 */           invalidateOptions(items);
                }

/* 178 */       private void invalidateOptions(ArrayList<Option> items) {
/* 179 */           this.mArrayAdapter.clear();
                    for (Option item : items) {
/* 182 */               this.mArrayAdapter.add(item);
                    }
/* 184 */           setListAdapter(this.mArrayAdapter);
/* 185 */           getListView().setChoiceMode(0);
/* 186 */           getListView().setAdapter(getListAdapter());
                }

                @Override // android.view.View.OnClickListener
/* 191 */       public void onClick(View view) {
                }

                @Override // co.vine.android.SupportListActivity
/* 195 */       protected void onListItemClick(ListView l, View v, int position, long id) {
/* 196 */           Intent intent = getIntent();
/* 197 */           int optionId = ((Integer) v.getTag()).intValue();
                    switch (optionId) {
                        case 1:
/* 210 */                   intent.setAction(ACTION_TWITTER);
/* 211 */                   setResult(-1, intent);
/* 212 */                   finish();
                            break;
                        case 2:
/* 216 */                   intent.setAction(ACTION_FACEBOOK);
/* 217 */                   setResult(-1, intent);
/* 218 */                   finish();
                            break;
                        case 3:
/* 222 */                   intent.setAction(ACTION_VM);
/* 223 */                   setResult(-1, intent);
/* 224 */                   finish();
                            break;
                        case 4:
/* 228 */                   intent.putExtra("post_id", this.mPostId);
/* 229 */                   intent.setAction(ACTION_SHARE_OTHER);
/* 230 */                   setResult(-1, intent);
/* 231 */                   finish();
                            break;
                        case 5:
/* 235 */                   intent.putExtra("post_id", this.mPostId);
/* 236 */                   intent.putExtra(EXTRA_POST_USER_ID, this.mPostUserId);
/* 237 */                   intent.putExtra("name", this.mName);
/* 238 */                   intent.setAction(ACTION_BLOCK_OR_REPORT);
/* 239 */                   setResult(-1, intent);
/* 240 */                   finish();
                            break;
                        case 6:
/* 244 */                   PromptDialogSupportFragment promptDialogFragment = PromptDialogSupportFragment.newInstance(2);
/* 246 */                   promptDialogFragment.setListener(this.mReportDeleteDialogDoneListener);
/* 247 */                   promptDialogFragment.setMessage(C0257R.string.delete_confirm);
/* 248 */                   promptDialogFragment.setPositiveButton(C0257R.string.delete_post);
/* 249 */                   promptDialogFragment.setNeutralButton(C0257R.string.cancel);
/* 250 */                   promptDialogFragment.show(getSupportFragmentManager());
                            break;
                        case 7:
/* 254 */                   setResult(0, intent);
/* 255 */                   finish();
                            break;
                        case 8:
/* 204 */                   intent.setAction(ACTION_ORIGINAL_POST);
/* 205 */                   setResult(-1, intent);
/* 206 */                   finish();
                            break;
                        case 9:
/* 259 */                   intent.setAction(ACTION_REVINE);
/* 260 */                   setResult(-1, intent);
/* 261 */                   finish();
                            break;
                        case 10:
/* 200 */                   invalidateOptions(this.mItems2);
                            break;
                        case 11:
/* 265 */                   intent.setAction(ACTION_UNFAVORITE_USER);
/* 266 */                   setResult(-1, intent);
/* 267 */                   finish();
                            break;
                        case 12:
/* 271 */                   intent.setAction("unfollow");
/* 272 */                   setResult(-1, intent);
/* 273 */                   finish();
                            break;
                    }
                }

                /* JADX INFO: renamed from: co.vine.android.PostOptionsDialogActivity$1 */
/* 281 */       class C02521 implements PromptDialogSupportFragment.OnDialogDoneListener {
/* 282 */           C02521() {
                    }

                    @Override // co.vine.android.widgets.PromptDialogFragment.OnDialogDoneListener
/* 285 */           public void onDialogDone(DialogInterface dialog, int id, int which) {
/* 286 */               Intent intent = new Intent();
/* 287 */               intent.putExtra("post_id", PostOptionsDialogActivity.access$000(PostOptionsDialogActivity.this));
                        switch (id) {
                            case 1:
                                switch (which) {
                                    case -1:
/* 292 */                               intent.setAction(PostOptionsDialogActivity.ACTION_REPORT);
/* 293 */                               PostOptionsDialogActivity.this.setResult(-1, intent);
/* 294 */                               PostOptionsDialogActivity.this.finish();
                                        break;
                                }
                                break;
                            case 2:
                                switch (which) {
                                    case -1:
/* 306 */                               intent.setAction(PostOptionsDialogActivity.ACTION_DELETE);
/* 307 */                               PostOptionsDialogActivity.this.setResult(-1, intent);
/* 308 */                               PostOptionsDialogActivity.this.finish();
                                        break;
                                }
                                break;
                        }
                    }
                }

/* 324 */       public static class Option {

                    /* JADX INFO: renamed from: id */
                    public int f82id;
                    public String text;

/* 325 */           public Option(int id, String text) {
/* 327 */               this.f82id = id;
/* 328 */               this.text = text;
                    }
                }

/* 333 */       public static Intent getMoreIntent(VinePost post, Context context, boolean isMyPost, boolean showOriginalPost) {
                    return new Intent(context, (Class<?>) PostOptionsDialogActivity.class).putExtra("post_id", post.postId).putExtra("delete", isMyPost).putExtra("unfollow", !isMyPost).putExtra(EXTRA_REPORT, !isMyPost).putExtra(EXTRA_ORIGINAL_POST, showOriginalPost).putExtra(EXTRA_POST_USER_ID, post.userId).putExtra("name", post.username).putExtra(EXTRA_SHARE_VM, post.isShareable() || isMyPost);
                }

/* 347 */       public static Intent getSinglePostMoreIntent(VinePost post, Context context, boolean isMyPost, boolean showOriginalPost, boolean isFavoriteUser) {
/* 348 */           Intent intent = getMoreIntent(post, context, isMyPost, showOriginalPost);
/* 349 */           intent.putExtra(EXTRA_FAVORITE_USER, isFavoriteUser);
/* 350 */           return intent;
                }

/* 354 */       public static Intent getShareIntent(VinePost post, Context context, boolean isMyPost, long myRepostId, long repostId, String flurryEventSource) {
                    return getShareIntent(post, context, isMyPost, myRepostId, repostId, myRepostId > 0, flurryEventSource);
                }

                public static Intent getShareIntent(VinePost post, Context context, boolean isMyPost, long myRepostId, long repostId, boolean wasRevined, String flurryEventSource) {
                    boolean z = true;
/* 355 */           Intent intent = new Intent(context, (Class<?>) PostOptionsDialogActivity.class);
/* 356 */           intent.putExtra("post_id", post.postId);
/* 357 */           intent.putExtra(EXTRA_REPORT, false);
/* 358 */           intent.putExtra(EXTRA_REVINE, !isMyPost);
/* 359 */           intent.putExtra(EXTRA_MY_REPOST_ID, myRepostId);
/* 360 */           intent.putExtra(EXTRA_REPOST_ID, repostId);
/* 361 */           intent.putExtra(EXTRA_POST_USER_ID, post.userId);
/* 362 */           intent.putExtra("following", post.user != null && post.user.isFollowing());
/* 363 */           intent.putExtra(EXTRA_WAS_REVINED, wasRevined || myRepostId > 0);
/* 364 */           intent.putExtra(EXTRA_FLURRY_EVENT_SOURCE, flurryEventSource);
/* 366 */           if ((post.isShareable() && !post.isPrivate()) || isMyPost) {
/* 367 */               intent.putExtra(EXTRA_POST_TWITTER, post.isShareable() || isMyPost);
/* 369 */               intent.putExtra(EXTRA_POST_FACEBOOK, post.isShareable() || isMyPost);
/* 371 */               intent.putExtra(EXTRA_SHARE_OTHER, post.isShareable());
                        if (!post.isShareable() && !isMyPost) {
                            z = false;
                        }
/* 373 */               intent.putExtra(EXTRA_SHARE_VM, z);
/* 375 */               intent.putExtra("description", post.description);
/* 376 */               intent.putExtra("share_url", post.shareUrl);
/* 377 */               intent.putExtra("thumbnail_url", post.thumbnailUrl);
/* 378 */               intent.putExtra("video_url", post.videoUrl);
/* 379 */               intent.putExtra("name", post.username);
/* 380 */               intent.putExtra(EXTRA_ORIGINAL_POST, false);
                    }
/* 383 */           return intent;
                }

/* 388 */       public static class Result {
                    public final Intent intent;
                    public final String request;

/* 389 */           public Result(String request, Intent intent) {
/* 391 */               this.request = request;
/* 392 */               this.intent = intent;
                    }
                }

/* 396 */       public static Result processActivityResult(AppController appController, Activity activity, int resultCode, Intent data) {
/* 397 */           String request = null;
/* 398 */           Intent intent = null;
/* 399 */           if (resultCode == -1 && data != null) {
/* 401 */               String action = data.getAction();
/* 402 */               if (ACTION_REPORT.equals(action)) {
/* 403 */                   long postId = data.getLongExtra("post_id", 0L);
                            if (postId > 0) {
/* 406 */                       request = appController.reportPost(appController.getActiveSession(), postId);
                            }
                        } else if (ACTION_DELETE.equals(action)) {
/* 410 */                   long postId2 = data.getLongExtra("post_id", 0L);
                            if (postId2 > 0) {
/* 413 */                       request = appController.deletePost(appController.getActiveSession(), postId2);
                            }
                        } else if (ACTION_TWITTER.equals(action)) {
/* 417 */                   intent = ReshareActivity.getReshareIntent(activity, "twitter", data.getExtras());
                        } else if (ACTION_FACEBOOK.equals(action)) {
/* 419 */                   intent = ReshareActivity.getReshareIntent(activity, "facebook", data.getExtras());
                        } else if (ACTION_VM.equals(action)) {
/* 421 */                   long postId3 = data.getLongExtra("post_id", 0L);
/* 423 */                   String videoUrl = data.getStringExtra("video_url");
/* 424 */                   String thumbUrl = data.getStringExtra("thumbnail_url");
                            if (postId3 > 0) {
/* 426 */                       intent = RecipientPickerActivity.getIntent(activity, postId3, videoUrl, thumbUrl);
                            }
                        } else if (ACTION_SHARE_OTHER.equals(action)) {
/* 429 */                   FlurryUtils.trackSharePost(CommonUtil.FIELD_TYPE_OTHER, data.getLongExtra("post_id", 0L));
/* 430 */                   Resources res = activity.getResources();
/* 431 */                   String name = data.getStringExtra("name");
/* 433 */                   String url = data.getStringExtra("share_url");
/* 434 */                   Intent share = new Intent("android.intent.action.SEND");
/* 435 */                   share.setType("text/plain");
/* 436 */                   share.addFlags(524288);
/* 437 */                   share.putExtra("android.intent.extra.SUBJECT", res.getString(C0257R.string.share_post_subject, name));
/* 438 */                   share.putExtra("android.intent.extra.TEXT", res.getString(C0257R.string.share_post_text, name, url));
/* 439 */                   intent = share;
                        } else if (ACTION_ORIGINAL_POST.equals(action)) {
/* 441 */                   long postId4 = data.getLongExtra("post_id", 0L);
                            if (postId4 > 0) {
/* 444 */                       intent = SingleActivity.getIntent(activity, postId4);
                            }
                        } else if (ACTION_REVINE.equals(action)) {
/* 447 */                   long postId5 = data.getLongExtra("post_id", 0L);
/* 448 */                   long myRepostId = data.getLongExtra(EXTRA_MY_REPOST_ID, 0L);
/* 449 */                   long repostId = data.getLongExtra(EXTRA_REPOST_ID, 0L);
/* 450 */                   long postUserId = data.getLongExtra(EXTRA_POST_USER_ID, 0L);
/* 451 */                   boolean following = data.getBooleanExtra("following", false);
/* 452 */                   boolean wasRevined = data.getBooleanExtra(EXTRA_WAS_REVINED, false);
/* 453 */                   String flurryEventSource = data.getStringExtra(EXTRA_FLURRY_EVENT_SOURCE);
                            // The delete endpoint needs the user's repost id in the path.  Older
                            // feed/cache entries can omit my_repost_id while still carrying the
                            // repost id, so never send the invalid /repost/0 request.
                            if (wasRevined && myRepostId <= 0 && repostId > 0) {
                                myRepostId = repostId;
                            }
/* 460 */                   request = wasRevined ? appController.unRevine(flurryEventSource, appController.getActiveSession(), postId5, myRepostId, repostId, following, postUserId, true) : appController.revine(flurryEventSource, appController.getActiveSession(), postId5, repostId, appController.getActiveSession().getName());
                        } else if (ACTION_UNFAVORITE_USER.equals(action)) {
/* 464 */                   long postUserId2 = data.getLongExtra(EXTRA_POST_USER_ID, 0L);
/* 465 */                   appController.favoriteUser(postUserId2, false, true);
                        } else if ("unfollow".equals(action)) {
/* 467 */                   long postUserId3 = data.getLongExtra(EXTRA_POST_USER_ID, 0L);
/* 468 */                   appController.unfollowUser(appController.getActiveSession(), postUserId3, false);
                        } else if (ACTION_BLOCK_OR_REPORT.equals(action)) {
/* 471 */                   long postId6 = data.getLongExtra("post_id", 0L);
/* 472 */                   long userId = data.getLongExtra(EXTRA_POST_USER_ID, 0L);
/* 473 */                   String username = data.getStringExtra("name");
/* 474 */                   Intent i = new Intent(activity, (Class<?>) ReportingActivity.class);
/* 475 */                   i.putExtra(ReportingFragment.EXTRA_MENU_TYPE, "post");
/* 476 */                   i.putExtra("postId", postId6);
/* 477 */                   i.putExtra("userId", userId);
/* 478 */                   i.putExtra("username", username);
/* 479 */                   activity.startActivity(i);
                        }
                    }
/* 483 */           return new Result(request, intent);
                }

/* 485 */       private class OptionArrayAdapter extends ArrayAdapter<Option> {
/* 486 */           public OptionArrayAdapter(Context context, int layout) {
/* 488 */               super(context, layout);
                    }

                    @Override // android.widget.ArrayAdapter, android.widget.Adapter
/* 492 */           public View getView(int position, View convertView, ViewGroup parent) {
/* 493 */               View v = super.getView(position, convertView, parent);
/* 494 */               Option option = getItem(position);
/* 495 */               TextView title = (TextView) v.findViewById(C0257R.id.item);
/* 496 */               if (option.f82id == 7) {
/* 497 */                   title.setTextColor(PostOptionsDialogActivity.this.getResources().getColor(C0257R.color.solid_light_gray_vm));
                        } else {
/* 499 */                   title.setTextColor(PostOptionsDialogActivity.this.getResources().getColor(C0257R.color.soft_black));
                        }
/* 501 */               title.setText(option.text);
/* 502 */               v.setTag(Integer.valueOf(option.f82id));
/* 503 */               return v;
                    }
                }
            }
