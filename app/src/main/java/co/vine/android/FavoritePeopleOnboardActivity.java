            package co.vine.android;

            import android.content.Context;
            import android.content.Intent;
            import android.graphics.Bitmap;
            import android.os.Bundle;
            import android.text.TextUtils;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.ImageView;
            import android.widget.ProgressBar;
            import co.vine.android.api.VineUser;
            import co.vine.android.cache.image.ImageKey;
            import co.vine.android.cache.image.UrlImage;
            import co.vine.android.client.AppSessionListener;
            import co.vine.android.service.GCMNotificationService;
            import co.vine.android.util.ImageUtils;
            import co.vine.android.util.analytics.FlurryUtils;
            import java.util.ArrayList;
            import java.util.HashMap;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 27 */    public class FavoritePeopleOnboardActivity extends BaseControllerActionBarActivity implements View.OnClickListener {
                private ViewGroup mFaceCarouselLayout;
                private int mImagesToShow;
                private ProgressBar mProgressBar;
                private int mReadyFaces;
                private int mStrokeWidth;
                private ArrayList<VineUser> mUsers;
                private HashMap<Long, ImageKey> mImageKeys = new HashMap<>();
                private ArrayList<ImageView> mImageViews = new ArrayList<>();
                private int[] mProfileColorIds = {C0257R.color.face_overlay_1, C0257R.color.face_overlay_2, C0257R.color.face_overlay_3, C0257R.color.face_overlay_4, C0257R.color.face_overlay_5, C0257R.color.face_overlay_6};
                private View.OnClickListener faceTapListener = new ViewOnClickListenerC02081();

/* 28 */        static /* synthetic */ void access$100(FavoritePeopleOnboardActivity x0) {
/* 29 */            x0.pickNowAndDismiss();
                }

/* 28 */        static /* synthetic */ int access$200(FavoritePeopleOnboardActivity x0) {
/* 29 */            return x0.mImagesToShow;
                }

/* 28 */        static /* synthetic */ ArrayList access$300(FavoritePeopleOnboardActivity x0) {
/* 29 */            return x0.mUsers;
                }

/* 28 */        static /* synthetic */ HashMap access$400(FavoritePeopleOnboardActivity x0) {
/* 29 */            return x0.mImageKeys;
                }

/* 28 */        static /* synthetic */ void access$500(FavoritePeopleOnboardActivity x0, int x1, Bitmap x2) {
/* 29 */            x0.setImage(x1, x2);
                }

/* 28 */        static /* synthetic */ int access$600(FavoritePeopleOnboardActivity x0) {
/* 29 */            return x0.mReadyFaces;
                }

/* 28 */        static /* synthetic */ ProgressBar access$700(FavoritePeopleOnboardActivity x0) {
/* 29 */            return x0.mProgressBar;
                }

/* 28 */        static /* synthetic */ ViewGroup access$800(FavoritePeopleOnboardActivity x0) {
/* 29 */            return x0.mFaceCarouselLayout;
                }

                @Override // co.vine.android.BaseActionBarActivity, android.support.v7.app.ActionBarActivity, android.support.v4.app.FragmentActivity, android.app.Activity
/* 51 */        protected void onCreate(Bundle savedInstanceState) {
                    Bitmap bm;
/* 52 */            super.onCreate(savedInstanceState, C0257R.layout.favorites_onboard, true, true);
/* 53 */            this.mAppSessionListener = new FavoritePeopleOnboardListener(this, null);
/* 54 */            this.mProgressBar = (ProgressBar) findViewById(C0257R.id.progress_bar);
/* 55 */            this.mFaceCarouselLayout = (ViewGroup) findViewById(C0257R.id.face_carousel_layout);
/* 57 */            Intent intent = getIntent();
/* 58 */            if (intent != null) {
/* 59 */                this.mUsers = intent.getParcelableArrayListExtra("users");
/* 60 */                if (this.mUsers != null) {
/* 61 */                    this.mStrokeWidth = getResources().getDimensionPixelSize(C0257R.dimen.favorite_face_stroke_width);
/* 62 */                    int width = getResources().getDimensionPixelSize(C0257R.dimen.favorite_face_width);
/* 64 */                    int[] imageViewIds = {C0257R.id.face_1, C0257R.id.face_2, C0257R.id.face_3, C0257R.id.face_4, C0257R.id.face_5, C0257R.id.face_6};
/* 68 */                    this.mImagesToShow = Math.min(this.mUsers.size(), imageViewIds.length);
/* 70 */                    this.mReadyFaces = 0;
                            for (int i = 0; i < this.mImagesToShow; i++) {
/* 72 */                        this.mImageViews.add((ImageView) findViewById(imageViewIds[i]));
/* 74 */                        VineUser user = this.mUsers.get(i);
/* 75 */                        ImageKey key = new ImageKey(user.avatarUrl, width, width, true);
/* 76 */                        if (!TextUtils.isEmpty(user.avatarUrl) && (bm = this.mAppController.getPhotoBitmap(key)) != null) {
/* 79 */                            setImage(i, bm);
                                }
/* 82 */                        this.mImageKeys.put(Long.valueOf(user.userId), key);
                            }
/* 85 */                    if (this.mReadyFaces == 0) {
/* 86 */                        this.mProgressBar.setVisibility(0);
/* 87 */                        this.mFaceCarouselLayout.setVisibility(8);
                            } else {
/* 89 */                        this.mProgressBar.setVisibility(8);
/* 90 */                        this.mFaceCarouselLayout.setVisibility(0);
                            }
/* 92 */                    FlurryUtils.trackOnboardingShown(GCMNotificationService.ONBOARD_VALUE_FAVORITES);
                        }
                    }
                }

                @Override // android.view.View.OnClickListener
/* 98 */        public void onClick(View view) {
/* 99 */            int id = view.getId();
/* 100 */           if (id == C0257R.id.pick_now) {
/* 101 */               pickNowAndDismiss();
                    } else if (id == C0257R.id.no_thanks) {
/* 103 */               FlurryUtils.trackOnboardingDismissed("no_thanks");
/* 104 */               finish();
                    }
                }

/* 108 */       private void setImage(int index, Bitmap bm) {
/* 109 */           ImageView imageView = this.mImageViews.get(index);
/* 110 */           int colorId = this.mProfileColorIds[index % this.mProfileColorIds.length];
/* 111 */           int color = getResources().getColor(colorId);
/* 112 */           Bitmap image = ImageUtils.desaturateWithColorFilter(bm.copy(bm.getConfig(), true), 0.0f, color);
/* 114 */           imageView.setImageBitmap(ImageUtils.addStroke(-1, this.mStrokeWidth, image));
/* 115 */           imageView.setVisibility(0);
/* 116 */           imageView.setOnClickListener(this.faceTapListener);
/* 117 */           this.mReadyFaces++;
                }

                /* JADX INFO: renamed from: co.vine.android.FavoritePeopleOnboardActivity$1 */
/* 118 */       class ViewOnClickListenerC02081 implements View.OnClickListener {
/* 119 */           ViewOnClickListenerC02081() {
                    }

                    @Override // android.view.View.OnClickListener
/* 122 */           public void onClick(View view) {
/* 123 */               FavoritePeopleOnboardActivity.access$100(FavoritePeopleOnboardActivity.this);
                    }
                }

/* 127 */       private void pickNowAndDismiss() {
/* 128 */           FavoritePeopleActivity.start(this, 0);
/* 129 */           FlurryUtils.trackOnboardingDismissed("pick_now");
/* 130 */           finish();
                }

/* 133 */       public static void start(Context context, ArrayList<VineUser> users) {
/* 134 */           Intent intent = new Intent(context, (Class<?>) FavoritePeopleOnboardActivity.class);
/* 135 */           intent.putParcelableArrayListExtra("users", users);
/* 136 */           context.startActivity(intent);
                }

/* 137 */       private class FavoritePeopleOnboardListener extends AppSessionListener {
/* 138 */           private FavoritePeopleOnboardListener() {
                    }

/* 138 */           /* synthetic */ FavoritePeopleOnboardListener(FavoritePeopleOnboardActivity x0, ViewOnClickListenerC02081 x1) {
/* 139 */               this();
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 141 */           public void onPhotoImageLoaded(HashMap<ImageKey, UrlImage> images) {
                        for (int i = 0; i < FavoritePeopleOnboardActivity.access$200(FavoritePeopleOnboardActivity.this); i++) {
/* 143 */                   UrlImage image = images.get(FavoritePeopleOnboardActivity.access$400(FavoritePeopleOnboardActivity.this).get(Long.valueOf(((VineUser) FavoritePeopleOnboardActivity.access$300(FavoritePeopleOnboardActivity.this).get(i)).userId)));
/* 144 */                   if (image != null && image.isValid()) {
/* 145 */                       FavoritePeopleOnboardActivity.access$500(FavoritePeopleOnboardActivity.this, i, image.bitmap);
                            }
                        }
/* 148 */               if (FavoritePeopleOnboardActivity.access$600(FavoritePeopleOnboardActivity.this) > 0 && FavoritePeopleOnboardActivity.access$700(FavoritePeopleOnboardActivity.this) != null && FavoritePeopleOnboardActivity.access$800(FavoritePeopleOnboardActivity.this) != null) {
/* 149 */                   FavoritePeopleOnboardActivity.access$700(FavoritePeopleOnboardActivity.this).setVisibility(8);
/* 150 */                   FavoritePeopleOnboardActivity.access$800(FavoritePeopleOnboardActivity.this).setVisibility(0);
                        }
                    }
                }

                @Override // android.app.Activity
/* 156 */       public void finish() {
/* 157 */           super.finish();
/* 159 */           overridePendingTransition(C0257R.anim.slide_in_from_bottom, C0257R.anim.slide_out_to_bottom);
                }
            }
