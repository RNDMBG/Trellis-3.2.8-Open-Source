            package twitter4j;

            import java.io.Serializable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface RateLimitStatus extends Serializable {
                int getLimit();

                int getRemaining();

                int getRemainingHits();

                int getResetTimeInSeconds();

                int getSecondsUntilReset();
            }
