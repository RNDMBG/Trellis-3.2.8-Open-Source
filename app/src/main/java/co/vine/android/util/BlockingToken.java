            package co.vine.android.util;

            import com.edisonwang.android.slog.SLog;
            import java.util.HashMap;
            import java.util.concurrent.LinkedBlockingQueue;
            
            
    public class BlockingToken {
                private static final int[] LOCK = new int[0];
                private static final HashMap<String, LinkedBlockingQueue<String>> sTokens = new HashMap<>();

        public static void acquire(String key) {
            SLog.m447d("bbq {} acquire.", key);
            LinkedBlockingQueue<String> queue = getQueue(key);
            while (queue.poll() == null) {
                        try {
                    Thread.sleep(15L);
                        } catch (InterruptedException e) {
                    SLog.m456e("bbq {} failed to acquire.", key);
                        }
                    }
                }

        public static void release(String key) {
            SLog.m447d("bbq {} release.", key);
            LinkedBlockingQueue<String> queue = getQueue(key);
                    try {
                queue.put(key);
                    } catch (InterruptedException e) {
                SLog.m456e("bbq {} failed to release.", key);
                    }
                }

        private static LinkedBlockingQueue<String> getQueue(String key) {
                    LinkedBlockingQueue<String> queue;
                    synchronized (LOCK) {
                        try {
                    queue = sTokens.get(key);
                    if (queue == null) {
                        queue = new LinkedBlockingQueue<>();
                                try {
                            queue.put(key);
                                } catch (InterruptedException e) {
                            SLog.m456e("bbq {} failed to queue.", key);
                                }
                        sTokens.put(key, queue);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return queue;
                }
            }
