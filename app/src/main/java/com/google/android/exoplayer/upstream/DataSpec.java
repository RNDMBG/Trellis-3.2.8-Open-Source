            package com.google.android.exoplayer.upstream;

            import android.net.Uri;
            import com.google.android.exoplayer.util.Assertions;
            
            
    public final class DataSpec {
                public final long absoluteStreamPosition;
                public final String key;
                public final long length;
                public final long position;
                public final Uri uri;
                public final boolean uriIsFullStream;

        public DataSpec(Uri uri, long absoluteStreamPosition, long length, String key) {
            this(uri, absoluteStreamPosition, length, key, absoluteStreamPosition, true);
                }

        public DataSpec(Uri uri, long absoluteStreamPosition, long length, String key, long position) {
            this(uri, absoluteStreamPosition, length, key, position, false);
                }

        public DataSpec(Uri uri, long absoluteStreamPosition, long length, String key, long position, boolean uriIsFullStream) {
                    Assertions.checkArgument(absoluteStreamPosition >= 0);
                    Assertions.checkArgument(position >= 0);
                    Assertions.checkArgument(length > 0 || length == -1);
            Assertions.checkArgument(absoluteStreamPosition == position || !uriIsFullStream);
            this.uri = uri;
            this.uriIsFullStream = uriIsFullStream;
            this.absoluteStreamPosition = absoluteStreamPosition;
           this.position = position;
           this.length = length;
           this.key = key;
                }

       public String toString() {
           return "DataSpec[" + this.uri + ", " + this.uriIsFullStream + ", " + this.absoluteStreamPosition + ", " + this.position + ", " + this.length + ", " + this.key + "]";
                }
            }
