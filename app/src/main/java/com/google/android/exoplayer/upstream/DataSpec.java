            package com.google.android.exoplayer.upstream;

            import android.net.Uri;
            import com.google.android.exoplayer.util.Assertions;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 63 */    public final class DataSpec {
                public final long absoluteStreamPosition;
                public final String key;
                public final long length;
                public final long position;
                public final Uri uri;
                public final boolean uriIsFullStream;

/* 64 */        public DataSpec(Uri uri, long absoluteStreamPosition, long length, String key) {
/* 65 */            this(uri, absoluteStreamPosition, length, key, absoluteStreamPosition, true);
                }

/* 77 */        public DataSpec(Uri uri, long absoluteStreamPosition, long length, String key, long position) {
/* 78 */            this(uri, absoluteStreamPosition, length, key, position, false);
                }

/* 91 */        public DataSpec(Uri uri, long absoluteStreamPosition, long length, String key, long position, boolean uriIsFullStream) {
                    Assertions.checkArgument(absoluteStreamPosition >= 0);
                    Assertions.checkArgument(position >= 0);
                    Assertions.checkArgument(length > 0 || length == -1);
/* 96 */            Assertions.checkArgument(absoluteStreamPosition == position || !uriIsFullStream);
/* 97 */            this.uri = uri;
/* 98 */            this.uriIsFullStream = uriIsFullStream;
/* 99 */            this.absoluteStreamPosition = absoluteStreamPosition;
/* 100 */           this.position = position;
/* 101 */           this.length = length;
/* 102 */           this.key = key;
                }

/* 106 */       public String toString() {
/* 107 */           return "DataSpec[" + this.uri + ", " + this.uriIsFullStream + ", " + this.absoluteStreamPosition + ", " + this.position + ", " + this.length + ", " + this.key + "]";
                }
            }
