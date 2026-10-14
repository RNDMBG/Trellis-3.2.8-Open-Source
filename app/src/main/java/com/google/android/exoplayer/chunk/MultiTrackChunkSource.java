            package com.google.android.exoplayer.chunk;

            import com.google.android.exoplayer.ExoPlaybackException;
            import com.google.android.exoplayer.ExoPlayer;
            import com.google.android.exoplayer.MediaFormat;
            import com.google.android.exoplayer.TrackInfo;
            import com.google.android.exoplayer.util.Assertions;
            import java.io.IOException;
            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 42 */    public class MultiTrackChunkSource implements ChunkSource, ExoPlayer.ExoPlayerComponent {
                public static final int MSG_SELECT_TRACK = 1;
                private final ChunkSource[] allSources;
                private boolean enabled;
                private ChunkSource selectedSource;

/* 43 */        public MultiTrackChunkSource(ChunkSource... sources) {
/* 45 */            this.allSources = sources;
/* 46 */            this.selectedSource = sources[0];
                }

/* 55 */        public int getTrackCount() {
/* 56 */            return this.allSources.length;
                }

                @Override // com.google.android.exoplayer.chunk.ChunkSource
/* 60 */        public TrackInfo getTrackInfo() {
/* 61 */            return this.selectedSource.getTrackInfo();
                }

                @Override // com.google.android.exoplayer.chunk.ChunkSource
/* 65 */        public void enable() {
/* 66 */            this.selectedSource.enable();
/* 67 */            this.enabled = true;
                }

                @Override // com.google.android.exoplayer.chunk.ChunkSource
/* 71 */        public void disable(List<? extends MediaChunk> queue) {
/* 72 */            this.selectedSource.disable(queue);
/* 73 */            this.enabled = false;
                }

                @Override // com.google.android.exoplayer.chunk.ChunkSource
/* 77 */        public void continueBuffering(long playbackPositionUs) {
/* 78 */            this.selectedSource.continueBuffering(playbackPositionUs);
                }

                @Override // com.google.android.exoplayer.chunk.ChunkSource
/* 83 */        public void getChunkOperation(List<? extends MediaChunk> queue, long seekPositionUs, long playbackPositionUs, ChunkOperationHolder out) {
/* 84 */            this.selectedSource.getChunkOperation(queue, seekPositionUs, playbackPositionUs, out);
                }

                @Override // com.google.android.exoplayer.chunk.ChunkSource
/* 88 */        public IOException getError() {
/* 89 */            return null;
                }

                @Override // com.google.android.exoplayer.chunk.ChunkSource
/* 93 */        public void getMaxVideoDimensions(MediaFormat out) {
/* 94 */            this.selectedSource.getMaxVideoDimensions(out);
                }

                @Override // com.google.android.exoplayer.ExoPlayer.ExoPlayerComponent
/* 98 */        public void handleMessage(int what, Object msg) throws ExoPlaybackException {
/* 99 */            Assertions.checkState(!this.enabled);
/* 100 */           if (what == 1) {
/* 101 */               this.selectedSource = this.allSources[((Integer) msg).intValue()];
                    }
                }

                @Override // com.google.android.exoplayer.chunk.ChunkSource
/* 106 */       public void onChunkLoadError(Chunk chunk, Exception e) {
/* 107 */           this.selectedSource.onChunkLoadError(chunk, e);
                }
            }
