            package co.vine.android.widget;

            import android.support.annotation.NonNull;
            import android.text.TextPaint;
            import android.text.style.ClickableSpan;
            import android.view.View;
            import co.vine.android.util.LinkDispatcher;
            
            
    public class EntityLinkClickableSpan extends ClickableSpan {
                private int mColor;
                private String mLink;
                private boolean mSelected;
                private boolean mSetColor;

        public EntityLinkClickableSpan(String link) {
            this.mLink = link;
                }

        public void setColor(int color) {
            this.mColor = color;
            this.mSetColor = true;
                }

                @Override 
        public void updateDrawState(@NonNull TextPaint ds) {
            if (this.mSetColor) {
                ds.setColor(this.mColor);
                    }
            ds.setUnderlineText(false);
                }

                @Override 
        public void onClick(View view) {
            LinkDispatcher.dispatch(this.mLink, view.getContext());
                }

        public void select(boolean selected) {
            this.mSelected = selected;
                }

        public boolean isSelected() {
            return this.mSelected;
                }
            }
