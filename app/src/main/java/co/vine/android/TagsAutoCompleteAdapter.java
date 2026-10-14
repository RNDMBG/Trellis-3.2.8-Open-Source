            package co.vine.android;

            import android.app.Activity;
            import android.content.Context;
            import android.database.Cursor;
            import android.support.v4.widget.CursorAdapter;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.TextView;
            import co.vine.android.api.VineTypeAhead;
            import co.vine.android.client.AppController;
            import java.lang.ref.WeakReference;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 28 */    public class TagsAutoCompleteAdapter extends CursorAdapter {
                protected final ArrayList<WeakReference<TagDropDownViewHolder>> mViewHolders;

/* 29 */        public TagsAutoCompleteAdapter(Activity activity, AppController appController) {
/* 30 */            super((Context) activity, (Cursor) null, true);
/* 31 */            this.mViewHolders = new ArrayList<>();
                }

                @Override // android.support.v4.widget.CursorAdapter
/* 35 */        public View newView(Context context, Cursor cursor, ViewGroup parent) {
/* 36 */            LayoutInflater layoutInflater = LayoutInflater.from(context);
/* 37 */            View rowView = layoutInflater.inflate(C0257R.layout.tag_row_view, (ViewGroup) null);
/* 38 */            TagDropDownViewHolder vh = new TagDropDownViewHolder(rowView);
/* 39 */            this.mViewHolders.add(new WeakReference<>(vh));
/* 40 */            rowView.setTag(vh);
/* 41 */            return rowView;
                }

                @Override // android.support.v4.widget.CursorAdapter
/* 45 */        public void bindView(View view, Context context, Cursor cursor) {
/* 46 */            TagDropDownViewHolder holder = (TagDropDownViewHolder) view.getTag();
/* 47 */            holder.userId = cursor.getLong(1);
/* 48 */            holder.tagName.setText("#" + cursor.getString(2));
                }

                @Override // android.support.v4.widget.CursorAdapter, android.widget.Adapter
/* 52 */        public Object getItem(int position) {
                    long id;
                    String tagName;
/* 53 */            Cursor c = (Cursor) super.getItem(position);
/* 56 */            if (c != null) {
/* 57 */                id = c.getLong(1);
/* 58 */                tagName = "#" + c.getString(2);
                    } else {
/* 60 */                id = 0;
/* 61 */                tagName = "";
                    }
/* 63 */            return new VineTypeAhead("tag", tagName, id);
                }

                @Override // android.support.v4.widget.CursorAdapter, android.widget.Adapter
/* 67 */        public long getItemId(int position) {
/* 68 */            Cursor c = (Cursor) super.getItem(position);
/* 69 */            if (c != null) {
/* 70 */                return c.getLong(1);
                    }
                    return 0L;
                }

/* 78 */        private class TagDropDownViewHolder {
                    public final TextView tagName;
                    public long userId;

/* 79 */            public TagDropDownViewHolder(View rowView) {
/* 81 */                this.tagName = (TextView) rowView.findViewById(C0257R.id.tag_name);
                    }
                }
            }
