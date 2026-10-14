            package co.vine.android.widget;

            import android.view.View;
            import android.widget.TextView;
            import co.vine.android.C0257R;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 13 */    public class TagViewHolder {
                public String tagName;
                public TextView tagTitle;

/* 14 */        public TagViewHolder(View view) {
/* 16 */            this.tagTitle = (TextView) view.findViewById(C0257R.id.tag_name);
                }
            }
