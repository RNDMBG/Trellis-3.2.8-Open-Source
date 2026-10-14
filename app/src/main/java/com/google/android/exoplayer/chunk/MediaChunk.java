            package com.google.android.exoplayer.chunk;

            import com.google.android.exoplayer.MediaFormat;
            import com.google.android.exoplayer.ParserException;
            import com.google.android.exoplayer.SampleHolder;
            import com.google.android.exoplayer.upstream.DataSource;
            import com.google.android.exoplayer.upstream.DataSpec;
            import java.util.Map;
            import java.util.UUID;
            
            
    public abstract class MediaChunk extends Chunk {
                public final long endTimeUs;
                public final int nextChunkIndex;
                public final long startTimeUs;

                public abstract MediaFormat getMediaFormat();

                public abstract Map<UUID, byte[]> getPsshInfo();

                public abstract boolean prepare() throws ParserException;

                public abstract boolean read(SampleHolder sampleHolder) throws ParserException;

                public abstract boolean sampleAvailable() throws ParserException;

                public abstract boolean seekTo(long j, boolean z);

                public abstract void seekToStart();

        public MediaChunk(DataSource dataSource, DataSpec dataSpec, Format format, int trigger, long startTimeUs, long endTimeUs, int nextChunkIndex) {
            super(dataSource, dataSpec, format, trigger);
            this.startTimeUs = startTimeUs;
            this.endTimeUs = endTimeUs;
            this.nextChunkIndex = nextChunkIndex;
                }

        public final boolean isLastChunk() {
                    return this.nextChunkIndex == -1;
                }
            }
