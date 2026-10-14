            package co.vine.android.views;

            import android.support.annotation.NonNull;
            import android.view.ViewGroup;
            
            
    public class SquareMatch {

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

        public static void setupSquareMatchView(SquareMatchView view) {
                    int size;
            int width = view.getMeasuredWidth();
            int height = view.getMeasuredHeight();
            if (width != height) {
                        switch (view.getMatchSpec()) {
                            case MATCH_WIDTH:
                        size = width;
                                break;
                            case MATCH_HEIGHT:
                        size = height;
                                break;
                            case MATCH_GREATER:
                        size = width <= height ? height : width;
                                break;
                            default:
                        size = width <= height ? width : height;
                                break;
                        }
                view.setMeasuredDimension(size);
                view.post(view.getMatchLayoutRunnable());
                    }
                }

        public static void setMatchingLayoutAction(SquareMatchView view) {
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.height = view.getMeasuredHeight();
            params.width = view.getMeasuredWidth();
            view.setLayoutParams(params);
                }
            }
