            package co.vine.android.util;

            import com.edisonwang.android.slog.SLog;
            import java.lang.ref.WeakReference;
            import java.util.ArrayList;
            import java.util.HashSet;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 15 */    public class InstanceCounter {
                private final int mLimit;
                private final HashSet<WeakReference<Object>> sInstanceCounter = new HashSet<>();

/* 16 */        public InstanceCounter(int limit) {
/* 18 */            this.mLimit = limit;
                }

/* 26 */        public int onCreate(Object o) {
/* 27 */            System.gc();
/* 28 */            if (this.sInstanceCounter.size() > this.mLimit + 100) {
/* 29 */                this.sInstanceCounter.clear();
                    }
/* 31 */            this.sInstanceCounter.add(new WeakReference<>(o));
/* 32 */            ArrayList<WeakReference<Object>> toRemove = new ArrayList<>();
                    for (WeakReference<Object> sessionWeakReference : this.sInstanceCounter) {
/* 34 */                if (sessionWeakReference.get() == null) {
/* 35 */                    toRemove.add(sessionWeakReference);
                        }
                    }
/* 38 */            this.sInstanceCounter.removeAll(toRemove);
/* 39 */            if (SLog.sLogsOn && this.sInstanceCounter.size() >= this.mLimit) {
/* 40 */                throw new RuntimeException("Limit has reached for this object type.");
                    }
/* 42 */            return this.sInstanceCounter.size();
                }

/* 45 */        public static InstanceCounter get(int limit) {
/* 46 */            return new InstanceCounter(limit);
                }
            }
