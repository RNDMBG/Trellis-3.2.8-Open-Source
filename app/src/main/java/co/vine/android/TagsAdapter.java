            package co.vine.android;

            import android.content.Context;
            import android.text.SpannableStringBuilder;
            import android.text.TextUtils;
            import android.text.style.ForegroundColorSpan;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.BaseAdapter;
            import co.vine.android.api.VineTag;
            import co.vine.android.client.AppController;
            import co.vine.android.util.Util;
            import co.vine.android.widget.TagViewHolder;
            import java.util.ArrayList;
            
            
    public class TagsAdapter extends BaseAdapter {
                protected final AppController mAppController;
                private Context mContext;
                protected ArrayList<VineTag> mTags;

        public TagsAdapter(Context context, AppController appController) {
            this.mContext = context;
            this.mAppController = appController;
                }

                @Override 
        public int getCount() {
            if (this.mTags != null) {
                return this.mTags.size();
                    }
                    return 0;
                }

                @Override 
        public Object getItem(int i) {
            if (this.mTags == null || this.mTags.size() <= 0) {
                        return null;
                    }
            return this.mTags.get(i);
                }

                @Override 
        public long getItemId(int i) {
            return i;
                }

                @Override 
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater layoutInflater = LayoutInflater.from(this.mContext);
                convertView = layoutInflater.inflate(R.layout.tag_row_view, parent, false);
                    }
            TagViewHolder holder = new TagViewHolder(convertView);
            convertView.setTag(holder);
            bindView(position, convertView);
            return convertView;
                }

        public void bindView(int position, View view) {
            TagViewHolder holder = (TagViewHolder) view.getTag();
            VineTag tag = this.mTags.get(position);
            if (!TextUtils.isEmpty(tag.tagName)) {
                int grayColor = this.mContext.getResources().getColor(R.color.tag_search_hashtag);
                ForegroundColorSpan colorSpan = new ForegroundColorSpan(grayColor);
                SpannableStringBuilder ssb = new SpannableStringBuilder("#");
                Util.safeSetSpan(ssb, colorSpan, 0, ssb.length(), 33);
                ssb.append((CharSequence) tag.tagName);
                holder.tagTitle.setText(ssb);
                holder.tagName = tag.tagName;
                    }
                }

       public void mergeData(ArrayList<VineTag> tags, boolean mergeAfter) {
           if (!mergeAfter || this.mTags == null) {
               this.mTags = new ArrayList<>();
                    }
           this.mTags.addAll(tags);
           notifyDataSetChanged();
                }

       public ArrayList<VineTag> getTags() {
           return this.mTags;
                }
            }
