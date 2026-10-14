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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 28 */    public class FriendsPickerViewHolder extends UserViewHolder {
                public View divider;
                private final SparseArray<Drawable> mColoredIndicatorMap;
                public VineRecipient recipient;
                public View sectionIndicator;
                public TextView sectionSort;
                public TextView sectionTitle;
                public View selectionIndicator;

/* 29 */        public FriendsPickerViewHolder(Context context, SparseArray<Drawable> colorMap, View v) {
/* 30 */            super(v);
/* 31 */            this.mColoredIndicatorMap = colorMap;
/* 32 */            this.selectionIndicator = v.findViewById(C0257R.id.selection_indicator);
/* 33 */            this.sectionIndicator = v.findViewById(C0257R.id.section_indicator);
/* 34 */            this.sectionTitle = (TextView) v.findViewById(C0257R.id.section_title);
/* 35 */            this.sectionSort = (TextView) v.findViewById(C0257R.id.section_sort);
/* 36 */            this.divider = v.findViewById(C0257R.id.divider);
/* 37 */            Util.styleSectionHeader(context, this.sectionTitle, this.sectionSort);
                }

/* 40 */        public void setSelected(Resources res, boolean selected) {
/* 41 */            if (selected) {
/* 42 */                Drawable bg = null;
/* 43 */                if (this.recipient.color <= 0 || this.recipient.color == Settings.DEFAULT_PROFILE_COLOR) {
/* 44 */                    this.recipient.color = res.getColor(C0257R.color.vine_green);
                        } else {
/* 46 */                    bg = this.mColoredIndicatorMap.get(this.recipient.color);
                        }
/* 48 */                if (bg == null) {
/* 49 */                    bg = res.getDrawable(C0257R.drawable.ic_circle_selected).mutate();
/* 50 */                    bg.setColorFilter(new PorterDuffColorFilter((-16777216) | this.recipient.color, PorterDuff.Mode.SRC_IN));
/* 51 */                    this.mColoredIndicatorMap.put(this.recipient.color, bg);
                        }
/* 53 */                ViewUtil.setBackground(this.selectionIndicator, bg);
/* 59 */                return;
                    }
/* 55 */            Drawable bg2 = res.getDrawable(C0257R.drawable.ic_circle_default);
/* 56 */            bg2.setColorFilter(new PorterDuffColorFilter(res.getColor(C0257R.color.btn_circle_stroke), PorterDuff.Mode.SRC_IN));
/* 57 */            ViewUtil.setBackground(this.selectionIndicator, bg2);
                }
            }
