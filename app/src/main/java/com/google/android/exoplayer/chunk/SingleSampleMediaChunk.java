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
            
            
    public class SingleSampleMediaChunk extends MediaChunk {
                public final byte[] headerData;
                private final MediaFormat sampleFormat;

        public SingleSampleMediaChunk(DataSource dataSource, DataSpec dataSpec, Format format, int trigger, long startTimeUs, long endTimeUs, int nextChunkIndex, MediaFormat sampleFormat) {
            this(dataSource, dataSpec, format, trigger, startTimeUs, endTimeUs, nextChunkIndex, sampleFormat, null);
                }

        public SingleSampleMediaChunk(DataSource dataSource, DataSpec dataSpec, Format format, int trigger, long startTimeUs, long endTimeUs, int nextChunkIndex, MediaFormat sampleFormat, byte[] headerData) {
            super(dataSource, dataSpec, format, trigger, startTimeUs, endTimeUs, nextChunkIndex);
            this.sampleFormat = sampleFormat;
            this.headerData = headerData;
                }

                @Override 
        public boolean prepare() {
            return true;
                }

                @Override 
        public boolean sampleAvailable() {
                    return isLoadFinished() && !isReadFinished();
                }

                @Override 
        public boolean read(SampleHolder holder) {
                    int bytesRead;
            NonBlockingInputStream inputStream = getNonBlockingInputStream();
            Assertions.checkState(inputStream != null);
            if (!sampleAvailable()) {
                        return false;
                    }
            int bytesLoaded = (int) bytesLoaded();
            int sampleSize = bytesLoaded;
            if (this.headerData != null) {
               sampleSize += this.headerData.length;
                    }
           if (holder.allowDataBufferReplacement && (holder.data == null || holder.data.capacity() < sampleSize)) {
               holder.data = ByteBuffer.allocate(sampleSize);
                    }
           if (holder.data != null) {
               if (this.headerData != null) {
                   holder.data.put(this.headerData);
                        }
               bytesRead = inputStream.read(holder.data, bytesLoaded);
               holder.size = sampleSize;
                    } else {
               bytesRead = inputStream.skip(bytesLoaded);
               holder.size = 0;
                    }
           Assertions.checkState(bytesRead == bytesLoaded);
           holder.timeUs = this.startTimeUs;
                    return true;
                }

                @Override 
       public void seekToStart() {
           resetReadPosition();
                }

                @Override 
       public boolean seekTo(long positionUs, boolean allowNoop) {
           resetReadPosition();
           return true;
                }

                @Override 
       public MediaFormat getMediaFormat() {
           return this.sampleFormat;
                }

                @Override 
       public Map<UUID, byte[]> getPsshInfo() {
           return null;
                }
            }
