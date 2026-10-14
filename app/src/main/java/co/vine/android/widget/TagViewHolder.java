            package co.vine.android.widget;

            import android.view.View;
            import android.widget.TextView;
            import co.vine.android.C0257R;
            
            
    public class TagViewHolder {
                public String tagName;
                public TextView tagTitle;

        public TagViewHolder(View view) {
            this.tagTitle = (TextView) view.findViewById(C0257R.id.tag_name);
                }
            }
