            package com.facebook.internal;

            import android.util.Log;
            import co.vine.android.provider.Vine;
            import com.facebook.LoggingBehavior;
            import com.facebook.Settings;
            import java.util.HashMap;
            import java.util.Map;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 41 */    public class Logger {
                public static final String LOG_TAG_BASE = "FacebookSDK.";
                private static final HashMap<String, String> stringsToReplace = new HashMap<>();
                private final LoggingBehavior behavior;
                private StringBuilder contents;
                private int priority = 3;
                private final String tag;

/* 42 */        public static synchronized void registerStringToReplace(String original, String replace) {
                    try {
                        stringsToReplace.put(original, replace);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 46 */        public static synchronized void registerAccessToken(String accessToken) {
                    try {
                        if (!Settings.isLoggingBehaviorEnabled(LoggingBehavior.INCLUDE_ACCESS_TOKENS)) {
/* 48 */                    registerStringToReplace(accessToken, "ACCESS_TOKEN_REMOVED");
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 52 */        public static void log(LoggingBehavior behavior, String tag, String string) {
/* 53 */            log(behavior, 3, tag, string);
                }

/* 56 */        public static void log(LoggingBehavior behavior, String tag, String format, Object... args) {
/* 57 */            if (Settings.isLoggingBehaviorEnabled(behavior)) {
/* 58 */                String string = String.format(format, args);
/* 59 */                log(behavior, 3, tag, string);
                    }
                }

/* 63 */        public static void log(LoggingBehavior behavior, int priority, String tag, String string) {
/* 64 */            if (Settings.isLoggingBehaviorEnabled(behavior)) {
/* 65 */                String string2 = replaceStrings(string);
/* 66 */                if (!tag.startsWith(LOG_TAG_BASE)) {
/* 67 */                    tag = LOG_TAG_BASE + tag;
                        }
/* 69 */                Log.println(priority, tag, string2);
/* 73 */                if (behavior == LoggingBehavior.DEVELOPER_ERRORS) {
/* 74 */                    new Exception().printStackTrace();
                        }
                    }
                }

/* 79 */        private static synchronized String replaceStrings(String string) {
                    try {
                        for (Map.Entry<String, String> entry : stringsToReplace.entrySet()) {
/* 81 */                    string = string.replace(entry.getKey(), entry.getValue());
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return string;
                }

/* 85 */        public Logger(LoggingBehavior behavior, String tag) {
/* 87 */            Validate.notNullOrEmpty(tag, "tag");
/* 89 */            this.behavior = behavior;
/* 90 */            this.tag = LOG_TAG_BASE + tag;
/* 91 */            this.contents = new StringBuilder();
                }

/* 94 */        public int getPriority() {
/* 95 */            return this.priority;
                }

/* 98 */        public void setPriority(int value) {
/* 99 */            Validate.oneOf(Integer.valueOf(value), Vine.Settings.PROP_VALUE, 7, 3, 6, 4, 2, 5);
/* 101 */           this.priority = value;
                }

/* 104 */       public String getContents() {
/* 105 */           return replaceStrings(this.contents.toString());
                }

/* 109 */       public void log() {
/* 110 */           logString(this.contents.toString());
/* 111 */           this.contents = new StringBuilder();
                }

/* 115 */       public void logString(String string) {
/* 116 */           log(this.behavior, this.priority, this.tag, string);
                }

/* 119 */       public void append(StringBuilder stringBuilder) {
/* 120 */           if (shouldLog()) {
/* 121 */               this.contents.append((CharSequence) stringBuilder);
                    }
                }

/* 125 */       public void append(String string) {
/* 126 */           if (shouldLog()) {
/* 127 */               this.contents.append(string);
                    }
                }

/* 131 */       public void append(String format, Object... args) {
/* 132 */           if (shouldLog()) {
/* 133 */               this.contents.append(String.format(format, args));
                    }
                }

/* 137 */       public void appendKeyValue(String key, Object value) {
/* 138 */           append("  %s:\t%s\n", key, value);
                }

/* 141 */       private boolean shouldLog() {
/* 142 */           return Settings.isLoggingBehaviorEnabled(this.behavior);
                }
            }
