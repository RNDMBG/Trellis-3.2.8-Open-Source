            package co.vine.android.player;

            import android.view.View;
            import java.util.HashSet;
            
            
    public abstract class SensitiveImageClickListener implements View.OnClickListener {
                protected int mPosition;
                protected long mPostId;
                protected final HashSet<Long> mSensitiveContentWarningsAcknowledged;

        public SensitiveImageClickListener(HashSet<Long> sensitiveContentWarningsAcknowledged) {
            this.mSensitiveContentWarningsAcknowledged = sensitiveContentWarningsAcknowledged;
                }

        public void setPosition(int position) {
            this.mPosition = position;
                }

        public void setPostId(long postId) {
            this.mPostId = postId;
                }
            }
