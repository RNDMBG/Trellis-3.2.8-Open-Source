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
            
            
    public class FavoritePeopleOnboardActivity extends BaseControllerActionBarActivity implements View.OnClickListener {
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

        static  void access$100(FavoritePeopleOnboardActivity x0) {
            x0.pickNowAndDismiss();
                }

        static  int access$200(FavoritePeopleOnboardActivity x0) {
            return x0.mImagesToShow;
                }

        static  ArrayList access$300(FavoritePeopleOnboardActivity x0) {
            return x0.mUsers;
                }

        static  HashMap access$400(FavoritePeopleOnboardActivity x0) {
            return x0.mImageKeys;
                }

        static  void access$500(FavoritePeopleOnboardActivity x0, int x1, Bitmap x2) {
            x0.setImage(x1, x2);
                }

        static  int access$600(FavoritePeopleOnboardActivity x0) {
            return x0.mReadyFaces;
                }

        static  ProgressBar access$700(FavoritePeopleOnboardActivity x0) {
            return x0.mProgressBar;
                }

        static  ViewGroup access$800(FavoritePeopleOnboardActivity x0) {
            return x0.mFaceCarouselLayout;
                }

                @Override 
        protected void onCreate(Bundle savedInstanceState) {
                    Bitmap bm;
            super.onCreate(savedInstanceState, C0257R.layout.favorites_onboard, true, true);
            this.mAppSessionListener = new FavoritePeopleOnboardListener(this, null);
            this.mProgressBar = (ProgressBar) findViewById(C0257R.id.progress_bar);
            this.mFaceCarouselLayout = (ViewGroup) findViewById(C0257R.id.face_carousel_layout);
            Intent intent = getIntent();
            if (intent != null) {
                this.mUsers = intent.getParcelableArrayListExtra("users");
                if (this.mUsers != null) {
                    this.mStrokeWidth = getResources().getDimensionPixelSize(C0257R.dimen.favorite_face_stroke_width);
                    int width = getResources().getDimensionPixelSize(C0257R.dimen.favorite_face_width);
                    int[] imageViewIds = {C0257R.id.face_1, C0257R.id.face_2, C0257R.id.face_3, C0257R.id.face_4, C0257R.id.face_5, C0257R.id.face_6};
                    this.mImagesToShow = Math.min(this.mUsers.size(), imageViewIds.length);
                    this.mReadyFaces = 0;
                            for (int i = 0; i < this.mImagesToShow; i++) {
                        this.mImageViews.add((ImageView) findViewById(imageViewIds[i]));
                        VineUser user = this.mUsers.get(i);
                        ImageKey key = new ImageKey(user.avatarUrl, width, width, true);
                        if (!TextUtils.isEmpty(user.avatarUrl) && (bm = this.mAppController.getPhotoBitmap(key)) != null) {
                            setImage(i, bm);
                                }
                        this.mImageKeys.put(Long.valueOf(user.userId), key);
                            }
                    if (this.mReadyFaces == 0) {
                        this.mProgressBar.setVisibility(0);
                        this.mFaceCarouselLayout.setVisibility(8);
                            } else {
                        this.mProgressBar.setVisibility(8);
                        this.mFaceCarouselLayout.setVisibility(0);
                            }
                    FlurryUtils.trackOnboardingShown(GCMNotificationService.ONBOARD_VALUE_FAVORITES);
                        }
                    }
                }

                @Override 
        public void onClick(View view) {
            int id = view.getId();
           if (id == C0257R.id.pick_now) {
               pickNowAndDismiss();
                    } else if (id == C0257R.id.no_thanks) {
               FlurryUtils.trackOnboardingDismissed("no_thanks");
               finish();
                    }
                }

       private void setImage(int index, Bitmap bm) {
           ImageView imageView = this.mImageViews.get(index);
           int colorId = this.mProfileColorIds[index % this.mProfileColorIds.length];
           int color = getResources().getColor(colorId);
           Bitmap image = ImageUtils.desaturateWithColorFilter(bm.copy(bm.getConfig(), true), 0.0f, color);
           imageView.setImageBitmap(ImageUtils.addStroke(-1, this.mStrokeWidth, image));
           imageView.setVisibility(0);
           imageView.setOnClickListener(this.faceTapListener);
           this.mReadyFaces++;
                }

                
       class ViewOnClickListenerC02081 implements View.OnClickListener {
           ViewOnClickListenerC02081() {
                    }

                    @Override 
           public void onClick(View view) {
               FavoritePeopleOnboardActivity.access$100(FavoritePeopleOnboardActivity.this);
                    }
                }

       private void pickNowAndDismiss() {
           FavoritePeopleActivity.start(this, 0);
           FlurryUtils.trackOnboardingDismissed("pick_now");
           finish();
                }

       public static void start(Context context, ArrayList<VineUser> users) {
           Intent intent = new Intent(context, (Class<?>) FavoritePeopleOnboardActivity.class);
           intent.putParcelableArrayListExtra("users", users);
           context.startActivity(intent);
                }

       private class FavoritePeopleOnboardListener extends AppSessionListener {
           private FavoritePeopleOnboardListener() {
                    }

            FavoritePeopleOnboardListener(FavoritePeopleOnboardActivity x0, ViewOnClickListenerC02081 x1) {
               this();
                    }

                    @Override 
           public void onPhotoImageLoaded(HashMap<ImageKey, UrlImage> images) {
                        for (int i = 0; i < FavoritePeopleOnboardActivity.access$200(FavoritePeopleOnboardActivity.this); i++) {
                   UrlImage image = images.get(FavoritePeopleOnboardActivity.access$400(FavoritePeopleOnboardActivity.this).get(Long.valueOf(((VineUser) FavoritePeopleOnboardActivity.access$300(FavoritePeopleOnboardActivity.this).get(i)).userId)));
                   if (image != null && image.isValid()) {
                       FavoritePeopleOnboardActivity.access$500(FavoritePeopleOnboardActivity.this, i, image.bitmap);
                            }
                        }
               if (FavoritePeopleOnboardActivity.access$600(FavoritePeopleOnboardActivity.this) > 0 && FavoritePeopleOnboardActivity.access$700(FavoritePeopleOnboardActivity.this) != null && FavoritePeopleOnboardActivity.access$800(FavoritePeopleOnboardActivity.this) != null) {
                   FavoritePeopleOnboardActivity.access$700(FavoritePeopleOnboardActivity.this).setVisibility(8);
                   FavoritePeopleOnboardActivity.access$800(FavoritePeopleOnboardActivity.this).setVisibility(0);
                        }
                    }
                }

                @Override 
       public void finish() {
           super.finish();
           overridePendingTransition(C0257R.anim.slide_in_from_bottom, C0257R.anim.slide_out_to_bottom);
                }
            }
