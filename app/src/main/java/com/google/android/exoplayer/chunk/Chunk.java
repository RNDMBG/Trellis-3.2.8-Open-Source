            package com.google.android.exoplayer.chunk;

            import com.google.android.exoplayer.upstream.Allocator;
            import com.google.android.exoplayer.upstream.DataSource;
            import com.google.android.exoplayer.upstream.DataSourceStream;
            import com.google.android.exoplayer.upstream.DataSpec;
            import com.google.android.exoplayer.upstream.Loader;
            import com.google.android.exoplayer.upstream.NonBlockingInputStream;
            import com.google.android.exoplayer.util.Assertions;
            import java.io.IOException;
            
            
    public abstract class Chunk implements Loader.Loadable {
                private final DataSource dataSource;
                private DataSourceStream dataSourceStream;
                private final DataSpec dataSpec;
                public final Format format;
                public final int trigger;

        public Chunk(DataSource dataSource, DataSpec dataSpec, Format format, int trigger) {
            Assertions.checkState(dataSpec.length <= 2147483647L);
            this.dataSource = (DataSource) Assertions.checkNotNull(dataSource);
            this.dataSpec = (DataSpec) Assertions.checkNotNull(dataSpec);
            this.format = (Format) Assertions.checkNotNull(format);
            this.trigger = trigger;
                }

        public final void init(Allocator allocator) {
            Assertions.checkState(this.dataSourceStream == null);
            this.dataSourceStream = new DataSourceStream(this.dataSource, this.dataSpec, allocator);
                }

        public final void release() {
            if (this.dataSourceStream != null) {
                this.dataSourceStream.close();
                this.dataSourceStream = null;
                    }
                }

        public final long getLength() {
            return this.dataSourceStream.getLength();
                }

       public final boolean isReadFinished() {
           return this.dataSourceStream.isEndOfStream();
                }

       public final boolean isLoadFinished() {
           return this.dataSourceStream.isLoadFinished();
                }

       public final long bytesLoaded() {
           return this.dataSourceStream.getLoadPosition();
                }

       public final void consume() throws IOException {
           Assertions.checkState(this.dataSourceStream != null);
           consumeStream(this.dataSourceStream);
                }

       public final byte[] getLoadedData() {
           Assertions.checkState(this.dataSourceStream != null);
           return this.dataSourceStream.getLoadedData();
                }

       protected void consumeStream(NonBlockingInputStream stream) throws IOException {
                }

       protected final NonBlockingInputStream getNonBlockingInputStream() {
           return this.dataSourceStream;
                }

       protected final void resetReadPosition() {
           if (this.dataSourceStream != null) {
               this.dataSourceStream.resetReadPosition();
                    }
                }

                @Override 
       public final void cancelLoad() {
           this.dataSourceStream.cancelLoad();
                }

                @Override 
       public final boolean isLoadCanceled() {
           return this.dataSourceStream.isLoadCanceled();
                }

                @Override 
       public final void load() throws InterruptedException, IOException {
           this.dataSourceStream.load();
                }
            }
