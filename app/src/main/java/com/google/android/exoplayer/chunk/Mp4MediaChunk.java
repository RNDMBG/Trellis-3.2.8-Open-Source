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
            
            
    public final class Mp4MediaChunk extends MediaChunk {
                private final Extractor extractor;
                private final boolean maybeSelfContained;
                private MediaFormat mediaFormat;
                private boolean prepared;
                private Map<UUID, byte[]> psshInfo;
                private final long sampleOffsetUs;

        public Mp4MediaChunk(DataSource dataSource, DataSpec dataSpec, Format format, int trigger, long startTimeUs, long endTimeUs, int nextChunkIndex, Extractor extractor, boolean maybeSelfContained, long sampleOffsetUs) {
            super(dataSource, dataSpec, format, trigger, startTimeUs, endTimeUs, nextChunkIndex);
            this.extractor = extractor;
            this.maybeSelfContained = maybeSelfContained;
            this.sampleOffsetUs = sampleOffsetUs;
                }

                @Override 
        public void seekToStart() {
            this.extractor.seekTo(0L, false);
            resetReadPosition();
                }

                @Override 
        public boolean seekTo(long positionUs, boolean allowNoop) {
            long seekTimeUs = positionUs + this.sampleOffsetUs;
            boolean isDiscontinuous = this.extractor.seekTo(seekTimeUs, allowNoop);
            if (isDiscontinuous) {
                resetReadPosition();
                    }
            return isDiscontinuous;
                }

                @Override 
        public boolean prepare() throws ParserException {
            if (!this.prepared) {
                if (this.maybeSelfContained) {
                    NonBlockingInputStream inputStream = getNonBlockingInputStream();
                    Assertions.checkState(inputStream != null);
                    int result = this.extractor.read(inputStream, null);
                    this.prepared = (result & 32) != 0;
                        } else {
                    this.prepared = true;
                        }
                if (this.prepared) {
                    this.mediaFormat = (MediaFormat) Assertions.checkNotNull(this.extractor.getFormat());
                   this.psshInfo = this.extractor.getPsshInfo();
                        }
                    }
           return this.prepared;
                }

                @Override 
       public boolean sampleAvailable() throws ParserException {
           NonBlockingInputStream inputStream = getNonBlockingInputStream();
           int result = this.extractor.read(inputStream, null);
                    return (result & 32) != 0;
                }

                @Override 
       public boolean read(SampleHolder holder) throws ParserException {
           NonBlockingInputStream inputStream = getNonBlockingInputStream();
           Assertions.checkState(inputStream != null);
           int result = this.extractor.read(inputStream, holder);
           boolean sampleRead = (result & 4) != 0;
           if (sampleRead) {
               holder.timeUs -= this.sampleOffsetUs;
                    }
           return sampleRead;
                }

                @Override 
       public MediaFormat getMediaFormat() {
           return this.mediaFormat;
                }

                @Override 
       public Map<UUID, byte[]> getPsshInfo() {
           return this.psshInfo;
                }
            }
