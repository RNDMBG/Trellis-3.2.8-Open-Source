            package twitter4j;

            import java.util.EventObject;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 31 */    public final class RateLimitStatusEvent extends EventObject {
                private static final long serialVersionUID = -2332507741769177298L;
                private boolean isAccountRateLimitStatus;
                private RateLimitStatus rateLimitStatus;

/* 32 */        RateLimitStatusEvent(Object source, RateLimitStatus rateLimitStatus, boolean isAccountRateLimitStatus) {
/* 33 */            super(source);
/* 34 */            this.rateLimitStatus = rateLimitStatus;
/* 35 */            this.isAccountRateLimitStatus = isAccountRateLimitStatus;
                }
            }
