            package co.vine.android.widget;

            import android.content.Context;
            import android.graphics.Bitmap;
            import android.graphics.drawable.Drawable;
            import android.preference.Preference;
            import android.support.annotation.NonNull;
            import android.util.AttributeSet;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.ImageView;
            import co.vine.android.C0257R;
            import co.vine.android.drawable.RecyclableBitmapDrawable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 31 */    public class ImagePreference extends Preference {
                private ImageView mBorder;
                private Context mContext;
                private ImageView mImage;
                private OnImagePreferenceBoundListener mListener;

                public interface OnImagePreferenceBoundListener {
                    void onImagePeferenceBound(ImagePreference imagePreference);
                }

/* 32 */        public ImagePreference(Context context) {
/* 33 */            this(context, null);
                }

/* 36 */        public ImagePreference(Context context, AttributeSet attrs) {
/* 37 */            this(context, attrs, 0);
                }

/* 40 */        public ImagePreference(Context context, AttributeSet attrs, int defStyle) {
/* 41 */            super(context, attrs, defStyle);
/* 42 */            setLayoutResource(C0257R.layout.image_preference);
/* 43 */            setSelectable(false);
/* 44 */            this.mContext = context;
                }

                @Override // android.preference.Preference
/* 48 */        protected View onCreateView(ViewGroup parent) {
/* 49 */            View v = super.onCreateView(parent);
/* 50 */            ViewGroup.LayoutParams params = v.getLayoutParams();
/* 52 */            params.height = (int) this.mContext.getResources().getDimension(C0257R.dimen.image_preference_height);
/* 53 */            v.setLayoutParams(params);
/* 55 */            return v;
                }

/* 58 */        public void setBitmap(Bitmap bmp) {
/* 59 */            if (this.mImage != null) {
/* 60 */                this.mImage.setImageDrawable(new RecyclableBitmapDrawable(this.mContext.getResources(), bmp));
                    }
                }

/* 64 */        public void setDrawable(Drawable d) {
/* 65 */            if (this.mImage != null) {
/* 66 */                this.mImage.setImageDrawable(d);
                    }
                }

/* 70 */        public void setBorderDrawable(Drawable d) {
/* 71 */            if (this.mBorder != null) {
/* 72 */                this.mBorder.setImageDrawable(d);
                    }
                }

/* 76 */        public void setPreferenceBoundListener(OnImagePreferenceBoundListener listener) {
/* 77 */            this.mListener = listener;
                }

/* 80 */        public void setImageClickListener(View.OnClickListener listener) {
/* 81 */            if (this.mImage != null) {
/* 82 */                this.mImage.setOnClickListener(listener);
                    }
                }

                @Override // android.preference.Preference
/* 87 */        protected void onBindView(@NonNull View view) {
/* 88 */            super.onBindView(view);
/* 89 */            this.mImage = (ImageView) view.findViewById(C0257R.id.user_image);
/* 90 */            this.mBorder = (ImageView) view.findViewById(C0257R.id.user_image_frame);
/* 91 */            this.mListener.onImagePeferenceBound(this);
                }
            }
