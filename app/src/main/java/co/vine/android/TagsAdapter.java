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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 44 */    public class TagsAdapter extends BaseAdapter {
                protected final AppController mAppController;
                private Context mContext;
                protected ArrayList<VineTag> mTags;

/* 45 */        public TagsAdapter(Context context, AppController appController) {
/* 47 */            this.mContext = context;
/* 48 */            this.mAppController = appController;
                }

                @Override // android.widget.Adapter
/* 52 */        public int getCount() {
/* 53 */            if (this.mTags != null) {
/* 54 */                return this.mTags.size();
                    }
                    return 0;
                }

                @Override // android.widget.Adapter
/* 60 */        public Object getItem(int i) {
/* 61 */            if (this.mTags == null || this.mTags.size() <= 0) {
                        return null;
                    }
/* 62 */            return this.mTags.get(i);
                }

                @Override // android.widget.Adapter
/* 68 */        public long getItemId(int i) {
/* 69 */            return i;
                }

                @Override // android.widget.Adapter
/* 73 */        public View getView(int position, View convertView, ViewGroup parent) {
/* 74 */            if (convertView == null) {
/* 75 */                LayoutInflater layoutInflater = LayoutInflater.from(this.mContext);
/* 76 */                convertView = layoutInflater.inflate(C0257R.layout.tag_row_view, parent, false);
                    }
/* 79 */            TagViewHolder holder = new TagViewHolder(convertView);
/* 81 */            convertView.setTag(holder);
/* 82 */            bindView(position, convertView);
/* 83 */            return convertView;
                }

/* 86 */        public void bindView(int position, View view) {
/* 87 */            TagViewHolder holder = (TagViewHolder) view.getTag();
/* 88 */            VineTag tag = this.mTags.get(position);
/* 90 */            if (!TextUtils.isEmpty(tag.tagName)) {
/* 91 */                int grayColor = this.mContext.getResources().getColor(C0257R.color.tag_search_hashtag);
/* 92 */                ForegroundColorSpan colorSpan = new ForegroundColorSpan(grayColor);
/* 93 */                SpannableStringBuilder ssb = new SpannableStringBuilder("#");
/* 94 */                Util.safeSetSpan(ssb, colorSpan, 0, ssb.length(), 33);
/* 95 */                ssb.append((CharSequence) tag.tagName);
/* 96 */                holder.tagTitle.setText(ssb);
/* 97 */                holder.tagName = tag.tagName;
                    }
                }

/* 101 */       public void mergeData(ArrayList<VineTag> tags, boolean mergeAfter) {
/* 102 */           if (!mergeAfter || this.mTags == null) {
/* 103 */               this.mTags = new ArrayList<>();
                    }
/* 105 */           this.mTags.addAll(tags);
/* 106 */           notifyDataSetChanged();
                }

/* 109 */       public ArrayList<VineTag> getTags() {
/* 110 */           return this.mTags;
                }
            }
