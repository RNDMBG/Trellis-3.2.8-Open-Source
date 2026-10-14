            package co.vine.android;

            import android.app.Activity;
            import android.content.Context;
            import android.content.res.Resources;
            import android.graphics.PorterDuff;
            import android.graphics.PorterDuffColorFilter;
            import android.graphics.drawable.Drawable;
            import android.view.LayoutInflater;
            import android.view.View;
            import android.view.ViewGroup;
            import android.widget.ArrayAdapter;
            import android.widget.TextView;
            import co.vine.android.api.ComplaintMenuOption;
            import co.vine.android.util.ViewUtil;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 27 */    public class ComplaintChoiceAdapter extends ArrayAdapter<ComplaintMenuOption.ComplaintChoice> {
                private ComplaintMenuOption.ComplaintChoice[] mChoices;
                private Context mContext;
                private int mLayoutId;

/* 28 */        public ComplaintChoiceAdapter(Context context, int layoutResourceId, ComplaintMenuOption.ComplaintChoice[] data) {
/* 29 */            super(context, layoutResourceId, data);
/* 30 */            this.mLayoutId = layoutResourceId;
/* 31 */            this.mContext = context;
/* 32 */            this.mChoices = data;
                }

                @Override // android.widget.ArrayAdapter, android.widget.Adapter
/* 36 */        public View getView(int position, View convertView, ViewGroup parent) {
/* 37 */            ComplaintMenuOption.ComplaintChoice complaintChoice = this.mChoices[position];
/* 38 */            if (convertView == null) {
/* 39 */                LayoutInflater inflater = ((Activity) this.mContext).getLayoutInflater();
/* 40 */                convertView = inflater.inflate(this.mLayoutId, parent, false);
                    }
/* 42 */            TextView title = (TextView) convertView.findViewById(2131427387);
/* 43 */            View selectionIndicator = convertView.findViewById(C0257R.id.selection_indicator);
/* 44 */            if (complaintChoice.selected) {
/* 45 */                setSelected(this.mContext, selectionIndicator, true, this.mContext.getResources().getColor(C0257R.color.vine_green));
                    } else {
/* 47 */                setSelected(this.mContext, selectionIndicator, false, -1);
                    }
/* 49 */            title.setText(complaintChoice.title);
/* 50 */            return convertView;
                }

/* 53 */        public static void setSelected(Context context, View selectionIndicator, boolean selected, int color) {
/* 54 */            selectionIndicator.setSelected(selected);
/* 55 */            Resources res = context.getResources();
/* 56 */            if (selected) {
/* 57 */                Drawable bg = res.getDrawable(C0257R.drawable.ic_circle_selected).mutate();
/* 58 */                bg.setColorFilter(new PorterDuffColorFilter((-16777216) | color, PorterDuff.Mode.SRC_IN));
/* 59 */                ViewUtil.setBackground(selectionIndicator, bg);
                    } else {
/* 61 */                Drawable bg2 = res.getDrawable(C0257R.drawable.ic_circle_default);
/* 62 */                bg2.setColorFilter(new PorterDuffColorFilter(res.getColor(C0257R.color.btn_circle_stroke), PorterDuff.Mode.SRC_IN));
/* 63 */                ViewUtil.setBackground(selectionIndicator, bg2);
                    }
                }

/* 67 */        public ComplaintMenuOption.ComplaintChoice getTag(int position) {
/* 68 */            return this.mChoices[position];
                }
            }
