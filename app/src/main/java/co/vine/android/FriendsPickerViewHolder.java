            package co.vine.android;

            import android.content.Context;
            import android.content.res.Resources;
            import android.graphics.PorterDuff;
            import android.graphics.PorterDuffColorFilter;
            import android.graphics.drawable.Drawable;
            import android.view.View;
            import android.widget.TextView;
            import co.vine.android.api.VineRecipient;
            import co.vine.android.util.SparseArray;
            import co.vine.android.util.Util;
            import co.vine.android.util.ViewUtil;
            import co.vine.android.widget.UserViewHolder;
            
            
    public class FriendsPickerViewHolder extends UserViewHolder {
                public View divider;
                private final SparseArray<Drawable> mColoredIndicatorMap;
                public VineRecipient recipient;
                public View sectionIndicator;
                public TextView sectionSort;
                public TextView sectionTitle;
                public View selectionIndicator;

        public FriendsPickerViewHolder(Context context, SparseArray<Drawable> colorMap, View v) {
            super(v);
            this.mColoredIndicatorMap = colorMap;
            this.selectionIndicator = v.findViewById(C0257R.id.selection_indicator);
            this.sectionIndicator = v.findViewById(C0257R.id.section_indicator);
            this.sectionTitle = (TextView) v.findViewById(C0257R.id.section_title);
            this.sectionSort = (TextView) v.findViewById(C0257R.id.section_sort);
            this.divider = v.findViewById(C0257R.id.divider);
            Util.styleSectionHeader(context, this.sectionTitle, this.sectionSort);
                }

        public void setSelected(Resources res, boolean selected) {
            if (selected) {
                Drawable bg = null;
                if (this.recipient.color <= 0 || this.recipient.color == Settings.DEFAULT_PROFILE_COLOR) {
                    this.recipient.color = res.getColor(C0257R.color.vine_green);
                        } else {
                    bg = this.mColoredIndicatorMap.get(this.recipient.color);
                        }
                if (bg == null) {
                    bg = res.getDrawable(C0257R.drawable.ic_circle_selected).mutate();
                    bg.setColorFilter(new PorterDuffColorFilter((-16777216) | this.recipient.color, PorterDuff.Mode.SRC_IN));
                    this.mColoredIndicatorMap.put(this.recipient.color, bg);
                        }
                ViewUtil.setBackground(this.selectionIndicator, bg);
                return;
                    }
            Drawable bg2 = res.getDrawable(C0257R.drawable.ic_circle_default);
            bg2.setColorFilter(new PorterDuffColorFilter(res.getColor(C0257R.color.btn_circle_stroke), PorterDuff.Mode.SRC_IN));
            ViewUtil.setBackground(this.selectionIndicator, bg2);
                }
            }
