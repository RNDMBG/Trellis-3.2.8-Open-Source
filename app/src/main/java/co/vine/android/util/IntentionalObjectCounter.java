            package co.vine.android.util;

            import java.lang.ref.WeakReference;
            import java.util.HashMap;
            import java.util.HashSet;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 18 */    public class IntentionalObjectCounter {
                public static final String RECORDER_INSTANCE_COUNT = "recorder";
                private final WeakReference<Object> mItem;
                public final String mKey;
                private static final int[] LOCK = new int[0];
                private static final HashMap<String, HashSet<WeakReference<Object>>> sInstances = new HashMap<>();

/* 19 */        public IntentionalObjectCounter(String key, Object item) {
/* 21 */            this.mKey = key;
/* 22 */            this.mItem = new WeakReference<>(item);
                }

/* 25 */        public void add() {
/* 26 */            add(this.mKey, this.mItem);
                }

/* 29 */        public void release() {
/* 30 */            release(this.mKey, this.mItem);
                }

/* 33 */        public int getCount() {
/* 34 */            return getCount(this.mKey);
                }

/* 37 */        public static void add(String key, WeakReference<Object> item) {
/* 38 */            HashSet<WeakReference<Object>> counter = getCounter(key);
                    synchronized (LOCK) {
                        try {
/* 40 */                    counter.add(item);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 44 */        public static void release(String key, WeakReference<Object> item) {
/* 45 */            HashSet<WeakReference<Object>> counter = getCounter(key);
                    synchronized (LOCK) {
                        try {
/* 47 */                    counter.remove(item);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 51 */        public static int getCount(String key) {
                    int size;
/* 52 */            HashSet<WeakReference<Object>> counter = getCounter(key);
                    synchronized (LOCK) {
                        try {
/* 54 */                    size = counter.size();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return size;
                }

/* 59 */        private static HashSet<WeakReference<Object>> getCounter(String key) {
                    HashSet<WeakReference<Object>> counter;
                    synchronized (LOCK) {
                        try {
/* 61 */                    counter = sInstances.get(key);
/* 62 */                    if (counter == null) {
/* 63 */                        counter = new HashSet<>();
/* 64 */                        sInstances.put(key, counter);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return counter;
                }
            }
