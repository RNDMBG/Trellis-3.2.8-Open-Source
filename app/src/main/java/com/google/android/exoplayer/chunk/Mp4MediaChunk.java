            package com.google.android.exoplayer.chunk;

            import com.google.android.exoplayer.MediaFormat;
            import com.google.android.exoplayer.ParserException;
            import com.google.android.exoplayer.SampleHolder;
            import com.google.android.exoplayer.parser.Extractor;
            import com.google.android.exoplayer.upstream.DataSource;
            import com.google.android.exoplayer.upstream.DataSpec;
            import com.google.android.exoplayer.upstream.NonBlockingInputStream;
            import com.google.android.exoplayer.util.Assertions;
            import java.util.Map;
            import java.util.UUID;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 59 */    public final class Mp4MediaChunk extends MediaChunk {
                private final Extractor extractor;
                private final boolean maybeSelfContained;
                private MediaFormat mediaFormat;
                private boolean prepared;
                private Map<UUID, byte[]> psshInfo;
                private final long sampleOffsetUs;

/* 60 */        public Mp4MediaChunk(DataSource dataSource, DataSpec dataSpec, Format format, int trigger, long startTimeUs, long endTimeUs, int nextChunkIndex, Extractor extractor, boolean maybeSelfContained, long sampleOffsetUs) {
/* 61 */            super(dataSource, dataSpec, format, trigger, startTimeUs, endTimeUs, nextChunkIndex);
/* 62 */            this.extractor = extractor;
/* 63 */            this.maybeSelfContained = maybeSelfContained;
/* 64 */            this.sampleOffsetUs = sampleOffsetUs;
                }

                @Override // com.google.android.exoplayer.chunk.MediaChunk
/* 68 */        public void seekToStart() {
/* 69 */            this.extractor.seekTo(0L, false);
/* 70 */            resetReadPosition();
                }

                @Override // com.google.android.exoplayer.chunk.MediaChunk
/* 74 */        public boolean seekTo(long positionUs, boolean allowNoop) {
/* 75 */            long seekTimeUs = positionUs + this.sampleOffsetUs;
/* 76 */            boolean isDiscontinuous = this.extractor.seekTo(seekTimeUs, allowNoop);
/* 77 */            if (isDiscontinuous) {
/* 78 */                resetReadPosition();
                    }
/* 80 */            return isDiscontinuous;
                }

                @Override // com.google.android.exoplayer.chunk.MediaChunk
/* 84 */        public boolean prepare() throws ParserException {
/* 85 */            if (!this.prepared) {
/* 86 */                if (this.maybeSelfContained) {
/* 89 */                    NonBlockingInputStream inputStream = getNonBlockingInputStream();
/* 90 */                    Assertions.checkState(inputStream != null);
/* 91 */                    int result = this.extractor.read(inputStream, null);
/* 92 */                    this.prepared = (result & 32) != 0;
                        } else {
/* 96 */                    this.prepared = true;
                        }
/* 98 */                if (this.prepared) {
/* 99 */                    this.mediaFormat = (MediaFormat) Assertions.checkNotNull(this.extractor.getFormat());
/* 100 */                   this.psshInfo = this.extractor.getPsshInfo();
                        }
                    }
/* 103 */           return this.prepared;
                }

                @Override // com.google.android.exoplayer.chunk.MediaChunk
/* 107 */       public boolean sampleAvailable() throws ParserException {
/* 108 */           NonBlockingInputStream inputStream = getNonBlockingInputStream();
/* 109 */           int result = this.extractor.read(inputStream, null);
                    return (result & 32) != 0;
                }

                @Override // com.google.android.exoplayer.chunk.MediaChunk
/* 114 */       public boolean read(SampleHolder holder) throws ParserException {
/* 115 */           NonBlockingInputStream inputStream = getNonBlockingInputStream();
/* 116 */           Assertions.checkState(inputStream != null);
/* 117 */           int result = this.extractor.read(inputStream, holder);
/* 118 */           boolean sampleRead = (result & 4) != 0;
/* 119 */           if (sampleRead) {
/* 120 */               holder.timeUs -= this.sampleOffsetUs;
                    }
/* 122 */           return sampleRead;
                }

                @Override // com.google.android.exoplayer.chunk.MediaChunk
/* 126 */       public MediaFormat getMediaFormat() {
/* 127 */           return this.mediaFormat;
                }

                @Override // com.google.android.exoplayer.chunk.MediaChunk
/* 131 */       public Map<UUID, byte[]> getPsshInfo() {
/* 132 */           return this.psshInfo;
                }
            }
