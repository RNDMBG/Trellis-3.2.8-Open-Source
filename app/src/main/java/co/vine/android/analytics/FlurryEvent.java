            package co.vine.android.analytics;

            import com.flurry.android.FlurryAgent;
            import java.util.HashMap;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 14 */    public class FlurryEvent {
                private final String mEventName;
                public HashMap<String, String> mInfo;

/* 15 */        public static void log(String eventName, String key, Object value, String key2, Object value2) {
/* 16 */            new FlurryEvent(eventName).add(key, value).add(key2, value2).log();
                }

/* 19 */        public static void log(String eventName, String key, Object value) {
/* 20 */            new FlurryEvent(eventName).add(key, value).log();
                }

/* 23 */        public static void log(String event) {
/* 24 */            FlurryAgent.logEvent(event);
                }

/* 27 */        public FlurryEvent add(String name, Object value) {
/* 28 */            if (this.mInfo == null) {
/* 29 */                this.mInfo = new HashMap<>();
                    }
/* 31 */            this.mInfo.put(name, String.valueOf(value));
/* 32 */            return this;
                }

/* 34 */        public FlurryEvent(String eventName) {
/* 36 */            this.mEventName = eventName;
                }

/* 39 */        public void log() {
/* 40 */            if (this.mInfo != null) {
/* 41 */                FlurryAgent.logEvent(this.mEventName, this.mInfo);
                    } else {
/* 43 */                FlurryAgent.logEvent(this.mEventName);
                    }
                }
            }
