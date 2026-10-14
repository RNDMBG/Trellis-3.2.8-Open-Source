            package com.google.android.exoplayer.dash.mpd;

            import android.net.Uri;
            import com.google.android.exoplayer.util.Assertions;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 61 */    public final class RangedUri {
                private final Uri baseUri;
                private int hashCode;
                public final long length;
                public final long start;
                private final String stringUri;

/* 62 */        public RangedUri(Uri baseUri, String stringUri, long start, long length) {
/* 64 */            Assertions.checkArgument((baseUri == null && stringUri == null) ? false : true);
/* 65 */            this.baseUri = baseUri;
/* 66 */            this.stringUri = stringUri;
/* 67 */            this.start = start;
/* 68 */            this.length = length;
                }

/* 76 */        public Uri getUri() {
/* 77 */            if (this.stringUri == null) {
/* 78 */                return this.baseUri;
                    }
/* 80 */            Uri uri = Uri.parse(this.stringUri);
/* 81 */            if (!uri.isAbsolute() && this.baseUri != null) {
/* 82 */                return Uri.withAppendedPath(this.baseUri, this.stringUri);
                    }
/* 84 */            return uri;
                }

/* 98 */        public RangedUri attemptMerge(RangedUri other) {
                    RangedUri rangedUri = null;
/* 99 */            if (other != null && getUri().equals(other.getUri())) {
                        if (this.length != -1 && this.start + this.length == other.start) {
/* 102 */                   rangedUri = new RangedUri(this.baseUri, this.stringUri, this.start, other.length != -1 ? this.length + other.length : -1L);
                        } else if (other.length != -1 && other.start + other.length == this.start) {
/* 105 */                   rangedUri = new RangedUri(this.baseUri, this.stringUri, other.start, this.length != -1 ? other.length + this.length : -1L);
                        }
                    }
/* 108 */           return rangedUri;
                }

/* 113 */       public int hashCode() {
/* 114 */           if (this.hashCode == 0) {
/* 116 */               int result = ((int) this.start) + 527;
/* 119 */               this.hashCode = (((result * 31) + ((int) this.length)) * 31) + getUri().hashCode();
                    }
/* 121 */           return this.hashCode;
                }

/* 125 */       public boolean equals(Object obj) {
/* 126 */           if (this == obj) {
                        return true;
                    }
/* 129 */           if (obj == null || getClass() != obj.getClass()) {
                        return false;
                    }
/* 132 */           RangedUri other = (RangedUri) obj;
                    return this.start == other.start && this.length == other.length && getUri().equals(other.getUri());
                }
            }
