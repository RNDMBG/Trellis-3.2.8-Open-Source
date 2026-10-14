            package com.google.android.exoplayer.chunk;

            import com.google.android.exoplayer.MediaFormat;
            import com.google.android.exoplayer.ParserException;
            import com.google.android.exoplayer.SampleHolder;
            import com.google.android.exoplayer.parser.webm.WebmExtractor;
            import com.google.android.exoplayer.upstream.DataSource;
            import com.google.android.exoplayer.upstream.DataSpec;
            import com.google.android.exoplayer.upstream.NonBlockingInputStream;
            import com.google.android.exoplayer.util.Assertions;
            import java.util.Map;
            import java.util.UUID;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 48 */    public final class WebmMediaChunk extends MediaChunk {
                private final WebmExtractor extractor;

/* 49 */        public WebmMediaChunk(DataSource dataSource, DataSpec dataSpec, Format format, int trigger, WebmExtractor extractor, long startTimeUs, long endTimeUs, int nextChunkIndex) {
/* 50 */            super(dataSource, dataSpec, format, trigger, startTimeUs, endTimeUs, nextChunkIndex);
/* 51 */            this.extractor = extractor;
                }

                @Override // com.google.android.exoplayer.chunk.MediaChunk
/* 55 */        public void seekToStart() {
/* 56 */            seekTo(0L, false);
                }

                @Override // com.google.android.exoplayer.chunk.MediaChunk
/* 60 */        public boolean seekTo(long positionUs, boolean allowNoop) {
/* 61 */            boolean isDiscontinuous = this.extractor.seekTo(positionUs, allowNoop);
/* 62 */            if (isDiscontinuous) {
/* 63 */                resetReadPosition();
                    }
/* 65 */            return isDiscontinuous;
                }

                @Override // com.google.android.exoplayer.chunk.MediaChunk
/* 69 */        public boolean prepare() {
/* 70 */            return true;
                }

                @Override // com.google.android.exoplayer.chunk.MediaChunk
/* 74 */        public boolean sampleAvailable() throws ParserException {
/* 75 */            NonBlockingInputStream inputStream = getNonBlockingInputStream();
/* 76 */            int result = this.extractor.read(inputStream, null);
                    return (result & 32) != 0;
                }

                @Override // com.google.android.exoplayer.chunk.MediaChunk
/* 81 */        public boolean read(SampleHolder holder) {
/* 82 */            NonBlockingInputStream inputStream = getNonBlockingInputStream();
/* 83 */            Assertions.checkState(inputStream != null);
/* 84 */            int result = this.extractor.read(inputStream, holder);
                    return (result & 4) != 0;
                }

                @Override // com.google.android.exoplayer.chunk.MediaChunk
/* 89 */        public MediaFormat getMediaFormat() {
/* 90 */            return this.extractor.getFormat();
                }

                @Override // com.google.android.exoplayer.chunk.MediaChunk
/* 95 */        public Map<UUID, byte[]> getPsshInfo() {
/* 96 */            return null;
                }
            }
