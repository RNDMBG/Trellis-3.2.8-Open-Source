            package com.google.android.exoplayer.chunk;

            import com.google.android.exoplayer.MediaFormat;
            import com.google.android.exoplayer.TrackInfo;
            import java.io.IOException;
            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface ChunkSource {
                void continueBuffering(long j);

                void disable(List<? extends MediaChunk> list);

                void enable();

                void getChunkOperation(List<? extends MediaChunk> list, long j, long j2, ChunkOperationHolder chunkOperationHolder);

                IOException getError();

                void getMaxVideoDimensions(MediaFormat mediaFormat);

                TrackInfo getTrackInfo();

                void onChunkLoadError(Chunk chunk, Exception exc);
            }
