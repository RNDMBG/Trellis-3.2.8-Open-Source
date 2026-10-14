            package com.google.android.exoplayer.dash.mpd;

            import java.util.Collections;
            import java.util.Map;
            
            
    public final class ContentProtection {
                public final Map<String, String> keyedData;
                public final String schemeUriId;

        public ContentProtection(String schemeUriId, Map<String, String> keyedData) {
            this.schemeUriId = schemeUriId;
            if (keyedData != null) {
                this.keyedData = Collections.unmodifiableMap(keyedData);
                    } else {
                this.keyedData = Collections.emptyMap();
                    }
                }
            }
