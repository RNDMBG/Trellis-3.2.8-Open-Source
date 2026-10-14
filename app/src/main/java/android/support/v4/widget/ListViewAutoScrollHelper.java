            package android.support.v4.widget;

            import android.view.View;
            import android.widget.ListView;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 28 */    public class ListViewAutoScrollHelper extends AutoScrollHelper {
                private final ListView mTarget;

/* 29 */        public ListViewAutoScrollHelper(ListView target) {
/* 30 */            super(target);
/* 32 */            this.mTarget = target;
                }

                @Override // android.support.v4.widget.AutoScrollHelper
/* 36 */        public void scrollTargetBy(int deltaX, int deltaY) {
                    View firstView;
/* 37 */            ListView target = this.mTarget;
/* 38 */            int firstPosition = target.getFirstVisiblePosition();
/* 39 */            if (firstPosition != -1 && (firstView = target.getChildAt(0)) != null) {
/* 48 */                int newTop = firstView.getTop() - deltaY;
/* 49 */                target.setSelectionFromTop(firstPosition, newTop);
                    }
                }

                @Override // android.support.v4.widget.AutoScrollHelper
/* 54 */        public boolean canTargetScrollHorizontally(int direction) {
/* 55 */            return false;
                }

                @Override // android.support.v4.widget.AutoScrollHelper
/* 59 */        public boolean canTargetScrollVertically(int direction) {
/* 60 */            ListView target = this.mTarget;
/* 61 */            int itemCount = target.getCount();
/* 62 */            int childCount = target.getChildCount();
/* 63 */            int firstPosition = target.getFirstVisiblePosition();
/* 64 */            int lastPosition = firstPosition + childCount;
/* 66 */            if (direction > 0) {
/* 68 */                if (lastPosition >= itemCount) {
/* 69 */                    View lastView = target.getChildAt(childCount - 1);
/* 70 */                    if (lastView.getBottom() <= target.getHeight()) {
                                return false;
                            }
                        }
                    } else {
/* 74 */                if (direction >= 0) {
                            return false;
                        }
/* 76 */                if (firstPosition <= 0) {
/* 77 */                    View firstView = target.getChildAt(0);
/* 78 */                    if (firstView.getTop() >= 0) {
                                return false;
                            }
                        }
                    }
/* 88 */            return true;
                }
            }
