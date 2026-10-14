            package co.vine.android;

            import android.content.Context;
            import android.database.Cursor;
            import android.graphics.BitmapFactory;
            import android.support.v4.widget.CursorAdapter;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.ImageView;
            import android.widget.TextView;
            import co.vine.android.client.AppController;
            import co.vine.android.recorder.RegularProgressView;
            
            
    public class UploadsAdapter extends CursorAdapter {
                protected final AppController mAppController;
                private final int mDividerColor;

        public UploadsAdapter(Context context, AppController appController, int flags, int dividerColorId) {
            super(context, (Cursor) null, flags);
            this.mAppController = appController;
            this.mDividerColor = context.getResources().getColor(dividerColorId);
                }

        public UploadsAdapter(Context context, AppController appController, int flags) {
            this(context, appController, flags, C0257R.color.uploads_list_divider);
                }

                @Override 
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View v = layoutInflater.inflate(C0257R.layout.upload_row_view, parent, false);
            v.findViewById(C0257R.id.divider).setBackgroundColor(this.mDividerColor);
            UploadViewHolder holder = new UploadViewHolder(v);
            v.setTag(holder);
            return v;
                }

                @Override 
        public void bindView(View view, Context context, Cursor cursor) {
            UploadViewHolder holder = (UploadViewHolder) view.getTag();
            holder.status.setText(C0257R.string.upload_failed);
            holder.retry.setVisibility(8);
            if (cursor.getPosition() == 0) {
                holder.retry.setVisibility(0);
                    }
            holder.progressView.setProgressRatio(0.0f);
            String thumbnailPath = cursor.getString(6);
            holder.thumbnail.setImageBitmap(BitmapFactory.decodeFile(thumbnailPath));
                }

        private class UploadViewHolder {
                    public RegularProgressView progressView;
                    public ImageView retry;
                    public TextView status;
                    public ImageView thumbnail;

            public UploadViewHolder(View view) {
                this.thumbnail = (ImageView) view.findViewById(C0257R.id.thumbnail);
                this.status = (TextView) view.findViewById(C0257R.id.status_message);
                this.retry = (ImageView) view.findViewById(C0257R.id.retry);
                this.progressView = (RegularProgressView) view.findViewById(C0257R.id.progress_view);
                    }
                }
            }
