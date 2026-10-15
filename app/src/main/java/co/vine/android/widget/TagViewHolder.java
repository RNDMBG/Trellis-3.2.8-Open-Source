            package co.vine.android.widget;

            import android.view.View;
            import android.widget.TextView;
            import co.vine.android.R;
            
            
    public class TagViewHolder {
                public String tagName;
                public TextView tagTitle;

        public TagViewHolder(View view) {
            this.tagTitle = (TextView) view.findViewById(R.id.tag_name);
                }
            }
