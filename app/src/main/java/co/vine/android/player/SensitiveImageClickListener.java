            package co.vine.android.player;

            import android.view.View;
            import java.util.HashSet;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 17 */    public abstract class SensitiveImageClickListener implements View.OnClickListener {
                protected int mPosition;
                protected long mPostId;
                protected final HashSet<Long> mSensitiveContentWarningsAcknowledged;

/* 18 */        public SensitiveImageClickListener(HashSet<Long> sensitiveContentWarningsAcknowledged) {
/* 20 */            this.mSensitiveContentWarningsAcknowledged = sensitiveContentWarningsAcknowledged;
                }

/* 23 */        public void setPosition(int position) {
/* 24 */            this.mPosition = position;
                }

/* 27 */        public void setPostId(long postId) {
/* 28 */            this.mPostId = postId;
                }
            }
