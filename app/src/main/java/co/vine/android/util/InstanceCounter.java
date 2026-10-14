            package co.vine.android.util;

            import com.edisonwang.android.slog.SLog;
            import java.lang.ref.WeakReference;
            import java.util.ArrayList;
            import java.util.HashSet;
            
            
    public class InstanceCounter {
                private final int mLimit;
                private final HashSet<WeakReference<Object>> sInstanceCounter = new HashSet<>();

        public InstanceCounter(int limit) {
            this.mLimit = limit;
                }

        public int onCreate(Object o) {
            System.gc();
            if (this.sInstanceCounter.size() > this.mLimit + 100) {
                this.sInstanceCounter.clear();
                    }
            this.sInstanceCounter.add(new WeakReference<>(o));
            ArrayList<WeakReference<Object>> toRemove = new ArrayList<>();
                    for (WeakReference<Object> sessionWeakReference : this.sInstanceCounter) {
                if (sessionWeakReference.get() == null) {
                    toRemove.add(sessionWeakReference);
                        }
                    }
            this.sInstanceCounter.removeAll(toRemove);
            if (SLog.sLogsOn && this.sInstanceCounter.size() >= this.mLimit) {
                throw new RuntimeException("Limit has reached for this object type.");
                    }
            return this.sInstanceCounter.size();
                }

        public static InstanceCounter get(int limit) {
            return new InstanceCounter(limit);
                }
            }
