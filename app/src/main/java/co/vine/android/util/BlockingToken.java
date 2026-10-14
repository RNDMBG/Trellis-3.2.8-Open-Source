            package co.vine.android.util;

            import com.edisonwang.android.slog.SLog;
            import java.util.HashMap;
            import java.util.concurrent.LinkedBlockingQueue;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 15 */    public class BlockingToken {
                private static final int[] LOCK = new int[0];
                private static final HashMap<String, LinkedBlockingQueue<String>> sTokens = new HashMap<>();

/* 16 */        public static void acquire(String key) {
/* 17 */            SLog.m447d("bbq {} acquire.", key);
/* 18 */            LinkedBlockingQueue<String> queue = getQueue(key);
/* 19 */            while (queue.poll() == null) {
                        try {
/* 21 */                    Thread.sleep(15L);
                        } catch (InterruptedException e) {
/* 23 */                    SLog.m456e("bbq {} failed to acquire.", key);
                        }
                    }
                }

/* 28 */        public static void release(String key) {
/* 29 */            SLog.m447d("bbq {} release.", key);
/* 30 */            LinkedBlockingQueue<String> queue = getQueue(key);
                    try {
/* 32 */                queue.put(key);
                    } catch (InterruptedException e) {
/* 34 */                SLog.m456e("bbq {} failed to release.", key);
                    }
                }

/* 38 */        private static LinkedBlockingQueue<String> getQueue(String key) {
                    LinkedBlockingQueue<String> queue;
                    synchronized (LOCK) {
                        try {
/* 41 */                    queue = sTokens.get(key);
/* 42 */                    if (queue == null) {
/* 43 */                        queue = new LinkedBlockingQueue<>();
                                try {
/* 45 */                            queue.put(key);
                                } catch (InterruptedException e) {
/* 47 */                            SLog.m456e("bbq {} failed to queue.", key);
                                }
/* 49 */                        sTokens.put(key, queue);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return queue;
                }
            }
