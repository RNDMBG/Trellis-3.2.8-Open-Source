            package co.vine.android.views;

            import android.support.annotation.NonNull;
            import android.view.ViewGroup;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 38 */    public class SquareMatch {

                public enum SquareMatchRules {
                    MATCH_WIDTH,
                    MATCH_HEIGHT,
                    MATCH_GREATER,
                    MATCH_LESS
                }

                public interface SquareMatchView {
                    ViewGroup.LayoutParams getLayoutParams();

                    Runnable getMatchLayoutRunnable();

                    SquareMatchRules getMatchSpec();

                    int getMeasuredHeight();

                    int getMeasuredWidth();

                    boolean post(Runnable runnable);

                    void setLayoutParams(ViewGroup.LayoutParams layoutParams);

                    void setMatchRule(@NonNull SquareMatchRules squareMatchRules);

                    void setMeasuredDimension(int i);
                }

/* 39 */        public static void setupSquareMatchView(SquareMatchView view) {
                    int size;
/* 40 */            int width = view.getMeasuredWidth();
/* 41 */            int height = view.getMeasuredHeight();
/* 42 */            if (width != height) {
                        switch (view.getMatchSpec()) {
                            case MATCH_WIDTH:
/* 46 */                        size = width;
                                break;
                            case MATCH_HEIGHT:
/* 49 */                        size = height;
                                break;
                            case MATCH_GREATER:
/* 52 */                        size = width <= height ? height : width;
                                break;
                            default:
/* 56 */                        size = width <= height ? width : height;
                                break;
                        }
/* 59 */                view.setMeasuredDimension(size);
/* 60 */                view.post(view.getMatchLayoutRunnable());
                    }
                }

/* 64 */        public static void setMatchingLayoutAction(SquareMatchView view) {
/* 65 */            ViewGroup.LayoutParams params = view.getLayoutParams();
/* 66 */            params.height = view.getMeasuredHeight();
/* 67 */            params.width = view.getMeasuredWidth();
/* 68 */            view.setLayoutParams(params);
                }
            }
