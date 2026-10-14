            package com.google.android.exoplayer.dash.mpd;

            import android.net.Uri;
            import com.google.android.exoplayer.util.Assertions;
            
            
    public final class RangedUri {
                private final Uri baseUri;
                private int hashCode;
                public final long length;
                public final long start;
                private final String stringUri;

        public RangedUri(Uri baseUri, String stringUri, long start, long length) {
            Assertions.checkArgument((baseUri == null && stringUri == null) ? false : true);
            this.baseUri = baseUri;
            this.stringUri = stringUri;
            this.start = start;
            this.length = length;
                }

        public Uri getUri() {
            if (this.stringUri == null) {
                return this.baseUri;
                    }
            Uri uri = Uri.parse(this.stringUri);
            if (!uri.isAbsolute() && this.baseUri != null) {
                return Uri.withAppendedPath(this.baseUri, this.stringUri);
                    }
            return uri;
                }

        public RangedUri attemptMerge(RangedUri other) {
                    RangedUri rangedUri = null;
            if (other != null && getUri().equals(other.getUri())) {
                        if (this.length != -1 && this.start + this.length == other.start) {
                   rangedUri = new RangedUri(this.baseUri, this.stringUri, this.start, other.length != -1 ? this.length + other.length : -1L);
                        } else if (other.length != -1 && other.start + other.length == this.start) {
                   rangedUri = new RangedUri(this.baseUri, this.stringUri, other.start, this.length != -1 ? other.length + this.length : -1L);
                        }
                    }
           return rangedUri;
                }

       public int hashCode() {
           if (this.hashCode == 0) {
               int result = ((int) this.start) + 527;
               this.hashCode = (((result * 31) + ((int) this.length)) * 31) + getUri().hashCode();
                    }
           return this.hashCode;
                }

       public boolean equals(Object obj) {
           if (this == obj) {
                        return true;
                    }
           if (obj == null || getClass() != obj.getClass()) {
                        return false;
                    }
           RangedUri other = (RangedUri) obj;
                    return this.start == other.start && this.length == other.length && getUri().equals(other.getUri());
                }
            }
