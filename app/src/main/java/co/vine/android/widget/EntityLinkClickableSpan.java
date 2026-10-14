            package co.vine.android.widget;

            import android.support.annotation.NonNull;
            import android.text.TextPaint;
            import android.text.style.ClickableSpan;
            import android.view.View;
            import co.vine.android.util.LinkDispatcher;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 18 */    public class EntityLinkClickableSpan extends ClickableSpan {
                private int mColor;
                private String mLink;
                private boolean mSelected;
                private boolean mSetColor;

/* 19 */        public EntityLinkClickableSpan(String link) {
/* 21 */            this.mLink = link;
                }

/* 24 */        public void setColor(int color) {
/* 25 */            this.mColor = color;
/* 26 */            this.mSetColor = true;
                }

                @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
/* 31 */        public void updateDrawState(@NonNull TextPaint ds) {
/* 32 */            if (this.mSetColor) {
/* 33 */                ds.setColor(this.mColor);
                    }
/* 35 */            ds.setUnderlineText(false);
                }

                @Override // android.text.style.ClickableSpan
/* 39 */        public void onClick(View view) {
/* 40 */            LinkDispatcher.dispatch(this.mLink, view.getContext());
                }

/* 43 */        public void select(boolean selected) {
/* 44 */            this.mSelected = selected;
                }

/* 47 */        public boolean isSelected() {
/* 48 */            return this.mSelected;
                }
            }
