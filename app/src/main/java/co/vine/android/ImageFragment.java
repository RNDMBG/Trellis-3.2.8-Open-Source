            package co.vine.android;

            import android.graphics.Bitmap;
            import android.os.Bundle;
            import android.os.Handler;
            import android.os.Looper;
            import android.text.TextUtils;
            import android.view.LayoutInflater;
            import android.view.MotionEvent;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.ImageView;
            import android.widget.ProgressBar;
            import co.vine.android.cache.image.ImageKey;
            import co.vine.android.cache.image.UrlImage;
            import co.vine.android.client.AppSessionListener;
            import co.vine.android.drawable.RecyclableBitmapDrawable;
            import co.vine.android.network.HttpResult;
            import co.vine.android.util.Util;
            import java.util.HashMap;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 26 */    public class ImageFragment extends BaseControllerFragment implements View.OnTouchListener {
                public static final String ARG_IMAGE_URL = "image_url";
                public static final String STATE_IMAGE_BITMAP = "state_image_bitmap";
                private long mFirstTouch;
                private final Handler mHandler = new Handler(Looper.getMainLooper());
                private Bitmap mImageBitmap;
                private ImageKey mImageKey;
                private ImageView mImageView;
                private ProgressBar mProgress;

/* 27 */        static /* synthetic */ ImageKey access$100(ImageFragment x0) {
/* 28 */            return x0.mImageKey;
                }

/* 27 */        static /* synthetic */ ProgressBar access$200(ImageFragment x0) {
/* 28 */            return x0.mProgress;
                }

/* 27 */        static /* synthetic */ ImageView access$300(ImageFragment x0) {
/* 28 */            return x0.mImageView;
                }

/* 27 */        static /* synthetic */ Handler access$400(ImageFragment x0) {
/* 28 */            return x0.mHandler;
                }

                @Override // co.vine.android.BaseControllerFragment, android.support.v4.app.Fragment
/* 43 */        public void onCreate(Bundle savedInstanceState) {
/* 44 */            super.onCreate(savedInstanceState);
/* 45 */            this.mAppSessionListener = new ImageSessionListener(this, null);
                }

                @Override // android.support.v4.app.Fragment
/* 49 */        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
/* 50 */            View v = inflater.inflate(C0257R.layout.image_layout, container, false);
/* 51 */            this.mImageView = (ImageView) v.findViewById(2131427383);
/* 52 */            this.mProgress = (ProgressBar) v.findViewById(2131427334);
/* 53 */            return v;
                }

                @Override // android.support.v4.app.Fragment
/* 57 */        public void onActivityCreated(Bundle savedInstanceState) {
/* 58 */            super.onActivityCreated(savedInstanceState);
/* 60 */            if (savedInstanceState == null) {
/* 61 */                Bundle args = getArguments();
/* 62 */                String imageUrl = args.getString("image_url");
/* 64 */                if (!TextUtils.isEmpty(imageUrl)) {
/* 65 */                    this.mImageKey = new ImageKey(imageUrl);
/* 66 */                    Bitmap imageBitmap = this.mAppController.getPhotoBitmap(this.mImageKey);
/* 68 */                    if (imageBitmap != null) {
/* 69 */                        this.mImageBitmap = imageBitmap;
                            } else {
/* 71 */                        this.mProgress.setVisibility(0);
                            }
                        }
/* 74 */                this.mImageView.setOnTouchListener(this);
/* 78 */                return;
                    }
/* 76 */            this.mImageBitmap = (Bitmap) savedInstanceState.getParcelable(STATE_IMAGE_BITMAP);
                }

                @Override // co.vine.android.BaseControllerFragment, co.vine.android.BaseFragment, android.support.v4.app.Fragment
/* 81 */        public void onResume() {
/* 82 */            super.onResume();
/* 83 */            Bitmap imageBitmap = this.mImageBitmap;
/* 84 */            if (imageBitmap != null) {
/* 85 */                this.mImageView.setImageDrawable(new RecyclableBitmapDrawable(getResources(), imageBitmap));
                    }
                }

                @Override // android.view.View.OnTouchListener
/* 90 */        public boolean onTouch(View view, MotionEvent motionEvent) {
/* 91 */            if (motionEvent.getAction() != 1) {
                        return false;
                    }
/* 93 */            long now = System.currentTimeMillis();
                    if (now - this.mFirstTouch < 400) {
/* 95 */                getActivity().finish();
                        return true;
                    }
/* 97 */            this.mFirstTouch = now;
                    return true;
                }

                @Override // android.support.v4.app.Fragment
/* 103 */       public void onSaveInstanceState(Bundle outState) {
/* 104 */           super.onSaveInstanceState(outState);
/* 105 */           if (this.mImageBitmap != null) {
/* 106 */               outState.putParcelable(STATE_IMAGE_BITMAP, this.mImageBitmap);
                    }
                }

/* 108 */       private class ImageSessionListener extends AppSessionListener {
/* 109 */           private ImageSessionListener() {
                    }

/* 109 */           /* synthetic */ ImageSessionListener(ImageFragment x0, Object x1) {
/* 110 */               this();
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 112 */           public void onPhotoImageLoaded(HashMap<ImageKey, UrlImage> images) {
/* 113 */               UrlImage image = images.get(ImageFragment.access$100(ImageFragment.this));
/* 114 */               if (image != null && image.isValid()) {
/* 115 */                   ImageFragment.access$200(ImageFragment.this).setVisibility(8);
/* 116 */                   ImageFragment.access$300(ImageFragment.this).setImageDrawable(new RecyclableBitmapDrawable(ImageFragment.access$300(ImageFragment.this).getResources(), image.bitmap));
                        }
                    }

                    @Override // co.vine.android.client.AppSessionListener
/* 121 */           public void onPhotoImageError(ImageKey key, HttpResult result) {
/* 122 */               if (key.equals(ImageFragment.access$100(ImageFragment.this))) {
/* 123 */                   finishFailed();
                        }
                    }

                    /* JADX INFO: renamed from: co.vine.android.ImageFragment$ImageSessionListener$1 */
/* 126 */           class RunnableC02221 implements Runnable {
/* 127 */               RunnableC02221() {
                        }

                        @Override // java.lang.Runnable
/* 130 */               public void run() {
/* 131 */                   Util.showCenteredToast(ImageFragment.this.getActivity(), C0257R.string.load_image_failure);
/* 132 */                   ImageFragment.this.getActivity().finish();
                        }
                    }

/* 127 */           private void finishFailed() {
/* 128 */               ImageFragment.access$400(ImageFragment.this).post(new RunnableC02221());
                    }
                }
            }
