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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 44 */    public class UploadsAdapter extends CursorAdapter {
                protected final AppController mAppController;
                private final int mDividerColor;

/* 45 */        public UploadsAdapter(Context context, AppController appController, int flags, int dividerColorId) {
/* 46 */            super(context, (Cursor) null, flags);
/* 47 */            this.mAppController = appController;
/* 48 */            this.mDividerColor = context.getResources().getColor(dividerColorId);
                }

/* 51 */        public UploadsAdapter(Context context, AppController appController, int flags) {
/* 52 */            this(context, appController, flags, C0257R.color.uploads_list_divider);
                }

                @Override // android.support.v4.widget.CursorAdapter
/* 56 */        public View newView(Context context, Cursor cursor, ViewGroup parent) {
/* 57 */            LayoutInflater layoutInflater = LayoutInflater.from(context);
/* 58 */            View v = layoutInflater.inflate(C0257R.layout.upload_row_view, parent, false);
/* 59 */            v.findViewById(C0257R.id.divider).setBackgroundColor(this.mDividerColor);
/* 61 */            UploadViewHolder holder = new UploadViewHolder(v);
/* 63 */            v.setTag(holder);
/* 64 */            return v;
                }

                @Override // android.support.v4.widget.CursorAdapter
/* 68 */        public void bindView(View view, Context context, Cursor cursor) {
/* 69 */            UploadViewHolder holder = (UploadViewHolder) view.getTag();
/* 71 */            holder.status.setText(C0257R.string.upload_failed);
/* 72 */            holder.retry.setVisibility(8);
/* 73 */            if (cursor.getPosition() == 0) {
/* 74 */                holder.retry.setVisibility(0);
                    }
/* 76 */            holder.progressView.setProgressRatio(0.0f);
/* 78 */            String thumbnailPath = cursor.getString(6);
/* 79 */            holder.thumbnail.setImageBitmap(BitmapFactory.decodeFile(thumbnailPath));
                }

/* 86 */        private class UploadViewHolder {
                    public RegularProgressView progressView;
                    public ImageView retry;
                    public TextView status;
                    public ImageView thumbnail;

/* 87 */            public UploadViewHolder(View view) {
/* 89 */                this.thumbnail = (ImageView) view.findViewById(C0257R.id.thumbnail);
/* 90 */                this.status = (TextView) view.findViewById(C0257R.id.status_message);
/* 91 */                this.retry = (ImageView) view.findViewById(C0257R.id.retry);
/* 92 */                this.progressView = (RegularProgressView) view.findViewById(C0257R.id.progress_view);
                    }
                }
            }
