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
            
            
    public class ImageFragment extends BaseControllerFragment implements View.OnTouchListener {
                public static final String ARG_IMAGE_URL = "image_url";
                public static final String STATE_IMAGE_BITMAP = "state_image_bitmap";
                private long mFirstTouch;
                private final Handler mHandler = new Handler(Looper.getMainLooper());
                private Bitmap mImageBitmap;
                private ImageKey mImageKey;
                private ImageView mImageView;
                private ProgressBar mProgress;

        static  ImageKey access$100(ImageFragment x0) {
            return x0.mImageKey;
                }

        static  ProgressBar access$200(ImageFragment x0) {
            return x0.mProgress;
                }

        static  ImageView access$300(ImageFragment x0) {
            return x0.mImageView;
                }

        static  Handler access$400(ImageFragment x0) {
            return x0.mHandler;
                }

                @Override 
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.mAppSessionListener = new ImageSessionListener(this, null);
                }

                @Override 
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(C0257R.layout.image_layout, container, false);
            this.mImageView = (ImageView) v.findViewById(2131427383);
            this.mProgress = (ProgressBar) v.findViewById(2131427334);
            return v;
                }

                @Override 
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            if (savedInstanceState == null) {
                Bundle args = getArguments();
                String imageUrl = args.getString("image_url");
                if (!TextUtils.isEmpty(imageUrl)) {
                    this.mImageKey = new ImageKey(imageUrl);
                    Bitmap imageBitmap = this.mAppController.getPhotoBitmap(this.mImageKey);
                    if (imageBitmap != null) {
                        this.mImageBitmap = imageBitmap;
                            } else {
                        this.mProgress.setVisibility(0);
                            }
                        }
                this.mImageView.setOnTouchListener(this);
                return;
                    }
            this.mImageBitmap = (Bitmap) savedInstanceState.getParcelable(STATE_IMAGE_BITMAP);
                }

                @Override 
        public void onResume() {
            super.onResume();
            Bitmap imageBitmap = this.mImageBitmap;
            if (imageBitmap != null) {
                this.mImageView.setImageDrawable(new RecyclableBitmapDrawable(getResources(), imageBitmap));
                    }
                }

                @Override 
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() != 1) {
                        return false;
                    }
            long now = System.currentTimeMillis();
                    if (now - this.mFirstTouch < 400) {
                getActivity().finish();
                        return true;
                    }
            this.mFirstTouch = now;
                    return true;
                }

                @Override 
       public void onSaveInstanceState(Bundle outState) {
           super.onSaveInstanceState(outState);
           if (this.mImageBitmap != null) {
               outState.putParcelable(STATE_IMAGE_BITMAP, this.mImageBitmap);
                    }
                }

       private class ImageSessionListener extends AppSessionListener {
           private ImageSessionListener() {
                    }

            ImageSessionListener(ImageFragment x0, Object x1) {
               this();
                    }

                    @Override 
           public void onPhotoImageLoaded(HashMap<ImageKey, UrlImage> images) {
               UrlImage image = images.get(ImageFragment.access$100(ImageFragment.this));
               if (image != null && image.isValid()) {
                   ImageFragment.access$200(ImageFragment.this).setVisibility(8);
                   ImageFragment.access$300(ImageFragment.this).setImageDrawable(new RecyclableBitmapDrawable(ImageFragment.access$300(ImageFragment.this).getResources(), image.bitmap));
                        }
                    }

                    @Override 
           public void onPhotoImageError(ImageKey key, HttpResult result) {
               if (key.equals(ImageFragment.access$100(ImageFragment.this))) {
                   finishFailed();
                        }
                    }

                    
           class RunnableC02221 implements Runnable {
               RunnableC02221() {
                        }

                        @Override 
               public void run() {
                   Util.showCenteredToast(ImageFragment.this.getActivity(), C0257R.string.load_image_failure);
                   ImageFragment.this.getActivity().finish();
                        }
                    }

           private void finishFailed() {
               ImageFragment.access$400(ImageFragment.this).post(new RunnableC02221());
                    }
                }
            }
