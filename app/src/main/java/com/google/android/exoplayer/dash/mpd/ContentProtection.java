            package com.google.android.exoplayer.dash.mpd;

            import java.util.Collections;
            import java.util.Map;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 38 */    public final class ContentProtection {
                public final Map<String, String> keyedData;
                public final String schemeUriId;

/* 39 */        public ContentProtection(String schemeUriId, Map<String, String> keyedData) {
/* 41 */            this.schemeUriId = schemeUriId;
/* 42 */            if (keyedData != null) {
/* 43 */                this.keyedData = Collections.unmodifiableMap(keyedData);
                    } else {
/* 45 */                this.keyedData = Collections.emptyMap();
                    }
                }
            }
