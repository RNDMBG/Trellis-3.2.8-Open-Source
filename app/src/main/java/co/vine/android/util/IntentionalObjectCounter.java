            package co.vine.android.util;

            import java.lang.ref.WeakReference;
            import java.util.HashMap;
            import java.util.HashSet;
            
            
    public class IntentionalObjectCounter {
                public static final String RECORDER_INSTANCE_COUNT = "recorder";
                private final WeakReference<Object> mItem;
                public final String mKey;
                private static final int[] LOCK = new int[0];
                private static final HashMap<String, HashSet<WeakReference<Object>>> sInstances = new HashMap<>();

        public IntentionalObjectCounter(String key, Object item) {
            this.mKey = key;
            this.mItem = new WeakReference<>(item);
                }

        public void add() {
            add(this.mKey, this.mItem);
                }

        public void release() {
            release(this.mKey, this.mItem);
                }

        public int getCount() {
            return getCount(this.mKey);
                }

        public static void add(String key, WeakReference<Object> item) {
            HashSet<WeakReference<Object>> counter = getCounter(key);
                    synchronized (LOCK) {
                        try {
                    counter.add(item);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

        public static void release(String key, WeakReference<Object> item) {
            HashSet<WeakReference<Object>> counter = getCounter(key);
                    synchronized (LOCK) {
                        try {
                    counter.remove(item);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

        public static int getCount(String key) {
                    int size;
            HashSet<WeakReference<Object>> counter = getCounter(key);
                    synchronized (LOCK) {
                        try {
                    size = counter.size();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return size;
                }

        private static HashSet<WeakReference<Object>> getCounter(String key) {
                    HashSet<WeakReference<Object>> counter;
                    synchronized (LOCK) {
                        try {
                    counter = sInstances.get(key);
                    if (counter == null) {
                        counter = new HashSet<>();
                        sInstances.put(key, counter);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return counter;
                }
            }
