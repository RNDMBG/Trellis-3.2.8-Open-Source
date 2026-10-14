            package co.vine.android.views;

            import android.content.Context;
            import android.support.annotation.NonNull;
            import android.util.AttributeSet;
            import android.widget.RelativeLayout;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 12 */    public class SquareRelativeLayout extends RelativeLayout implements Runnable, SquareMatch.SquareMatchView {
                private SquareMatch.SquareMatchRules mSpec;

/* 13 */        public SquareRelativeLayout(Context context) {
/* 14 */            super(context);
/* 25 */            this.mSpec = SquareMatch.SquareMatchRules.MATCH_LESS;
                }

/* 17 */        public SquareRelativeLayout(Context context, AttributeSet attrs) {
/* 18 */            super(context, attrs);
/* 25 */            this.mSpec = SquareMatch.SquareMatchRules.MATCH_LESS;
                }

/* 21 */        public SquareRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
/* 22 */            super(context, attrs, defStyle);
/* 25 */            this.mSpec = SquareMatch.SquareMatchRules.MATCH_LESS;
                }

                @Override // co.vine.android.views.SquareMatch.SquareMatchView
                @NonNull
/* 29 */        public SquareMatch.SquareMatchRules getMatchSpec() {
/* 30 */            return this.mSpec;
                }

                @Override // java.lang.Runnable
/* 34 */        public void run() {
/* 35 */            SquareMatch.setMatchingLayoutAction(this);
                }

                @Override // co.vine.android.views.SquareMatch.SquareMatchView
/* 39 */        public Runnable getMatchLayoutRunnable() {
/* 40 */            return this;
                }

                @Override // co.vine.android.views.SquareMatch.SquareMatchView
/* 43 */        public void setMeasuredDimension(int size) {
/* 44 */            setMeasuredDimension(size, size);
                }

                @Override // co.vine.android.views.SquareMatch.SquareMatchView
/* 47 */        public void setMatchRule(@NonNull SquareMatch.SquareMatchRules spec) {
/* 48 */            this.mSpec = spec;
                }

                @Override // android.widget.RelativeLayout, android.view.View
/* 52 */        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
/* 53 */            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
/* 54 */            SquareMatch.setupSquareMatchView(this);
                }
            }
