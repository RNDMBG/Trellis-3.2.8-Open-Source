            package com.google.android.exoplayer.chunk;

            import com.google.android.exoplayer.MediaFormat;
            import com.google.android.exoplayer.SampleHolder;
            import com.google.android.exoplayer.upstream.DataSource;
            import com.google.android.exoplayer.upstream.DataSpec;
            import com.google.android.exoplayer.upstream.NonBlockingInputStream;
            import com.google.android.exoplayer.util.Assertions;
            import java.nio.ByteBuffer;
            import java.util.Map;
            import java.util.UUID;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 51 */    public class SingleSampleMediaChunk extends MediaChunk {
                public final byte[] headerData;
                private final MediaFormat sampleFormat;

/* 52 */        public SingleSampleMediaChunk(DataSource dataSource, DataSpec dataSpec, Format format, int trigger, long startTimeUs, long endTimeUs, int nextChunkIndex, MediaFormat sampleFormat) {
/* 53 */            this(dataSource, dataSpec, format, trigger, startTimeUs, endTimeUs, nextChunkIndex, sampleFormat, null);
                }

/* 74 */        public SingleSampleMediaChunk(DataSource dataSource, DataSpec dataSpec, Format format, int trigger, long startTimeUs, long endTimeUs, int nextChunkIndex, MediaFormat sampleFormat, byte[] headerData) {
/* 75 */            super(dataSource, dataSpec, format, trigger, startTimeUs, endTimeUs, nextChunkIndex);
/* 76 */            this.sampleFormat = sampleFormat;
/* 77 */            this.headerData = headerData;
                }

                @Override // com.google.android.exoplayer.chunk.MediaChunk
/* 81 */        public boolean prepare() {
/* 82 */            return true;
                }

                @Override // com.google.android.exoplayer.chunk.MediaChunk
/* 86 */        public boolean sampleAvailable() {
                    return isLoadFinished() && !isReadFinished();
                }

                @Override // com.google.android.exoplayer.chunk.MediaChunk
/* 91 */        public boolean read(SampleHolder holder) {
                    int bytesRead;
/* 92 */            NonBlockingInputStream inputStream = getNonBlockingInputStream();
/* 93 */            Assertions.checkState(inputStream != null);
/* 94 */            if (!sampleAvailable()) {
                        return false;
                    }
/* 97 */            int bytesLoaded = (int) bytesLoaded();
/* 98 */            int sampleSize = bytesLoaded;
/* 99 */            if (this.headerData != null) {
/* 100 */               sampleSize += this.headerData.length;
                    }
/* 102 */           if (holder.allowDataBufferReplacement && (holder.data == null || holder.data.capacity() < sampleSize)) {
/* 104 */               holder.data = ByteBuffer.allocate(sampleSize);
                    }
/* 107 */           if (holder.data != null) {
/* 108 */               if (this.headerData != null) {
/* 109 */                   holder.data.put(this.headerData);
                        }
/* 111 */               bytesRead = inputStream.read(holder.data, bytesLoaded);
/* 112 */               holder.size = sampleSize;
                    } else {
/* 114 */               bytesRead = inputStream.skip(bytesLoaded);
/* 115 */               holder.size = 0;
                    }
/* 117 */           Assertions.checkState(bytesRead == bytesLoaded);
/* 118 */           holder.timeUs = this.startTimeUs;
                    return true;
                }

                @Override // com.google.android.exoplayer.chunk.MediaChunk
/* 123 */       public void seekToStart() {
/* 124 */           resetReadPosition();
                }

                @Override // com.google.android.exoplayer.chunk.MediaChunk
/* 128 */       public boolean seekTo(long positionUs, boolean allowNoop) {
/* 129 */           resetReadPosition();
/* 130 */           return true;
                }

                @Override // com.google.android.exoplayer.chunk.MediaChunk
/* 134 */       public MediaFormat getMediaFormat() {
/* 135 */           return this.sampleFormat;
                }

                @Override // com.google.android.exoplayer.chunk.MediaChunk
/* 139 */       public Map<UUID, byte[]> getPsshInfo() {
/* 140 */           return null;
                }
            }
