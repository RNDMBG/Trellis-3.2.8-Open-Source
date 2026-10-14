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
            
            
    public class ImagePreference extends Preference {
                private ImageView mBorder;
                private Context mContext;
                private ImageView mImage;
                private OnImagePreferenceBoundListener mListener;

                public interface OnImagePreferenceBoundListener {
                    void onImagePeferenceBound(ImagePreference imagePreference);
                }

        public ImagePreference(Context context) {
            this(context, null);
                }

        public ImagePreference(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
                }

        public ImagePreference(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            setLayoutResource(C0257R.layout.image_preference);
            setSelectable(false);
            this.mContext = context;
                }

                @Override 
        protected View onCreateView(ViewGroup parent) {
            View v = super.onCreateView(parent);
            ViewGroup.LayoutParams params = v.getLayoutParams();
            params.height = (int) this.mContext.getResources().getDimension(C0257R.dimen.image_preference_height);
            v.setLayoutParams(params);
            return v;
                }

        public void setBitmap(Bitmap bmp) {
            if (this.mImage != null) {
                this.mImage.setImageDrawable(new RecyclableBitmapDrawable(this.mContext.getResources(), bmp));
                    }
                }

        public void setDrawable(Drawable d) {
            if (this.mImage != null) {
                this.mImage.setImageDrawable(d);
                    }
                }

        public void setBorderDrawable(Drawable d) {
            if (this.mBorder != null) {
                this.mBorder.setImageDrawable(d);
                    }
                }

        public void setPreferenceBoundListener(OnImagePreferenceBoundListener listener) {
            this.mListener = listener;
                }

        public void setImageClickListener(View.OnClickListener listener) {
            if (this.mImage != null) {
                this.mImage.setOnClickListener(listener);
                    }
                }

                @Override 
        protected void onBindView(@NonNull View view) {
            super.onBindView(view);
            this.mImage = (ImageView) view.findViewById(C0257R.id.user_image);
            this.mBorder = (ImageView) view.findViewById(C0257R.id.user_image_frame);
            this.mListener.onImagePeferenceBound(this);
                }
            }
