            package co.vine.android.views;

            import android.content.Context;
            import android.support.annotation.NonNull;
            import android.util.AttributeSet;
            import android.widget.RelativeLayout;
            
            
    public class SquareRelativeLayout extends RelativeLayout implements Runnable, SquareMatch.SquareMatchView {
                private SquareMatch.SquareMatchRules mSpec;

        public SquareRelativeLayout(Context context) {
            super(context);
            this.mSpec = SquareMatch.SquareMatchRules.MATCH_LESS;
                }

        public SquareRelativeLayout(Context context, AttributeSet attrs) {
            super(context, attrs);
            this.mSpec = SquareMatch.SquareMatchRules.MATCH_LESS;
                }

        public SquareRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            this.mSpec = SquareMatch.SquareMatchRules.MATCH_LESS;
                }

                @Override 
                @NonNull
        public SquareMatch.SquareMatchRules getMatchSpec() {
            return this.mSpec;
                }

                @Override 
        public void run() {
            SquareMatch.setMatchingLayoutAction(this);
                }

                @Override 
        public Runnable getMatchLayoutRunnable() {
            return this;
                }

                @Override 
        public void setMeasuredDimension(int size) {
            setMeasuredDimension(size, size);
                }

                @Override 
        public void setMatchRule(@NonNull SquareMatch.SquareMatchRules spec) {
            this.mSpec = spec;
                }

                @Override 
        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            SquareMatch.setupSquareMatchView(this);
                }
            }
