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
            
            
    public final class WebmMediaChunk extends MediaChunk {
                private final WebmExtractor extractor;

        public WebmMediaChunk(DataSource dataSource, DataSpec dataSpec, Format format, int trigger, WebmExtractor extractor, long startTimeUs, long endTimeUs, int nextChunkIndex) {
            super(dataSource, dataSpec, format, trigger, startTimeUs, endTimeUs, nextChunkIndex);
            this.extractor = extractor;
                }

                @Override 
        public void seekToStart() {
            seekTo(0L, false);
                }

                @Override 
        public boolean seekTo(long positionUs, boolean allowNoop) {
            boolean isDiscontinuous = this.extractor.seekTo(positionUs, allowNoop);
            if (isDiscontinuous) {
                resetReadPosition();
                    }
            return isDiscontinuous;
                }

                @Override 
        public boolean prepare() {
            return true;
                }

                @Override 
        public boolean sampleAvailable() throws ParserException {
            NonBlockingInputStream inputStream = getNonBlockingInputStream();
            int result = this.extractor.read(inputStream, null);
                    return (result & 32) != 0;
                }

                @Override 
        public boolean read(SampleHolder holder) {
            NonBlockingInputStream inputStream = getNonBlockingInputStream();
            Assertions.checkState(inputStream != null);
            int result = this.extractor.read(inputStream, holder);
                    return (result & 4) != 0;
                }

                @Override 
        public MediaFormat getMediaFormat() {
            return this.extractor.getFormat();
                }

                @Override 
        public Map<UUID, byte[]> getPsshInfo() {
            return null;
                }
            }
