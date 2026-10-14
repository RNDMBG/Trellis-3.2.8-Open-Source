            package co.vine.android.recorder;

            import android.graphics.Canvas;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 13 */    public abstract class AbstractEncodingRunnable implements Runnable {
                protected ArrayList<Drawer> mAdditionalDrawers = new ArrayList<>();

                public interface Drawer {
                    void draw(Canvas canvas, boolean z);
                }

                public abstract void setAsyncTask(BaseFinishProcessTask baseFinishProcessTask);

                public abstract void terminate();

/* 14 */        public void addAdditionalDrawer(Drawer additionalDrawer) {
/* 15 */            this.mAdditionalDrawers.add(additionalDrawer);
                }
            }
