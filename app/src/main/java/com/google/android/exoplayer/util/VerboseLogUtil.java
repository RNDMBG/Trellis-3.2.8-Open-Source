            package com.google.android.exoplayer.util;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 24 */    public class VerboseLogUtil {
                private static volatile boolean enableAllTags;
                private static volatile String[] enabledTags;

/* 25 */        private VerboseLogUtil() {
                }

/* 35 */        public static void setEnabledTags(String... tags) {
/* 36 */            enabledTags = tags;
/* 37 */            enableAllTags = false;
                }

/* 46 */        public static void setEnableAllTags(boolean enable) {
/* 47 */            enableAllTags = enable;
                }

/* 56 */        public static boolean isTagEnabled(String tag) {
/* 57 */            if (enableAllTags) {
                        return true;
                    }
/* 62 */            String[] tags = enabledTags;
/* 63 */            if (tags == null || tags.length == 0) {
                        return false;
                    }
                    for (String tag1 : tags) {
/* 67 */                if (tag1.equals(tag)) {
                            return true;
                        }
                    }
                    return false;
                }

/* 79 */        public static boolean areAllTagsEnabled() {
/* 80 */            return enableAllTags;
                }
            }
