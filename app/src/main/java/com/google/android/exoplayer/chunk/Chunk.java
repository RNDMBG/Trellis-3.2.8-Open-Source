            package com.google.android.exoplayer.chunk;

            import com.google.android.exoplayer.upstream.Allocator;
            import com.google.android.exoplayer.upstream.DataSource;
            import com.google.android.exoplayer.upstream.DataSourceStream;
            import com.google.android.exoplayer.upstream.DataSpec;
            import com.google.android.exoplayer.upstream.Loader;
            import com.google.android.exoplayer.upstream.NonBlockingInputStream;
            import com.google.android.exoplayer.util.Assertions;
            import java.io.IOException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 59 */    public abstract class Chunk implements Loader.Loadable {
                private final DataSource dataSource;
                private DataSourceStream dataSourceStream;
                private final DataSpec dataSpec;
                public final Format format;
                public final int trigger;

/* 60 */        public Chunk(DataSource dataSource, DataSpec dataSpec, Format format, int trigger) {
/* 62 */            Assertions.checkState(dataSpec.length <= 2147483647L);
/* 63 */            this.dataSource = (DataSource) Assertions.checkNotNull(dataSource);
/* 64 */            this.dataSpec = (DataSpec) Assertions.checkNotNull(dataSpec);
/* 65 */            this.format = (Format) Assertions.checkNotNull(format);
/* 66 */            this.trigger = trigger;
                }

/* 75 */        public final void init(Allocator allocator) {
/* 76 */            Assertions.checkState(this.dataSourceStream == null);
/* 77 */            this.dataSourceStream = new DataSourceStream(this.dataSource, this.dataSpec, allocator);
                }

/* 83 */        public final void release() {
/* 84 */            if (this.dataSourceStream != null) {
/* 85 */                this.dataSourceStream.close();
/* 86 */                this.dataSourceStream = null;
                    }
                }

/* 96 */        public final long getLength() {
/* 97 */            return this.dataSourceStream.getLength();
                }

/* 105 */       public final boolean isReadFinished() {
/* 106 */           return this.dataSourceStream.isEndOfStream();
                }

/* 114 */       public final boolean isLoadFinished() {
/* 115 */           return this.dataSourceStream.isLoadFinished();
                }

/* 123 */       public final long bytesLoaded() {
/* 124 */           return this.dataSourceStream.getLoadPosition();
                }

/* 132 */       public final void consume() throws IOException {
/* 133 */           Assertions.checkState(this.dataSourceStream != null);
/* 134 */           consumeStream(this.dataSourceStream);
                }

/* 144 */       public final byte[] getLoadedData() {
/* 145 */           Assertions.checkState(this.dataSourceStream != null);
/* 146 */           return this.dataSourceStream.getLoadedData();
                }

/* 159 */       protected void consumeStream(NonBlockingInputStream stream) throws IOException {
                }

/* 162 */       protected final NonBlockingInputStream getNonBlockingInputStream() {
/* 163 */           return this.dataSourceStream;
                }

/* 166 */       protected final void resetReadPosition() {
/* 167 */           if (this.dataSourceStream != null) {
/* 168 */               this.dataSourceStream.resetReadPosition();
                    }
                }

                @Override // com.google.android.exoplayer.upstream.Loader.Loadable
/* 177 */       public final void cancelLoad() {
/* 178 */           this.dataSourceStream.cancelLoad();
                }

                @Override // com.google.android.exoplayer.upstream.Loader.Loadable
/* 182 */       public final boolean isLoadCanceled() {
/* 183 */           return this.dataSourceStream.isLoadCanceled();
                }

                @Override // com.google.android.exoplayer.upstream.Loader.Loadable
/* 187 */       public final void load() throws InterruptedException, IOException {
/* 188 */           this.dataSourceStream.load();
                }
            }
