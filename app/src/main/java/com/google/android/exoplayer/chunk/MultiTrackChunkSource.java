            package com.google.android.exoplayer.chunk;

            import com.google.android.exoplayer.ExoPlaybackException;
            import com.google.android.exoplayer.ExoPlayer;
            import com.google.android.exoplayer.MediaFormat;
            import com.google.android.exoplayer.TrackInfo;
            import com.google.android.exoplayer.util.Assertions;
            import java.io.IOException;
            import java.util.List;
            
            
    public class MultiTrackChunkSource implements ChunkSource, ExoPlayer.ExoPlayerComponent {
                public static final int MSG_SELECT_TRACK = 1;
                private final ChunkSource[] allSources;
                private boolean enabled;
                private ChunkSource selectedSource;

        public MultiTrackChunkSource(ChunkSource... sources) {
            this.allSources = sources;
            this.selectedSource = sources[0];
                }

        public int getTrackCount() {
            return this.allSources.length;
                }

                @Override 
        public TrackInfo getTrackInfo() {
            return this.selectedSource.getTrackInfo();
                }

                @Override 
        public void enable() {
            this.selectedSource.enable();
            this.enabled = true;
                }

                @Override 
        public void disable(List<? extends MediaChunk> queue) {
            this.selectedSource.disable(queue);
            this.enabled = false;
                }

                @Override 
        public void continueBuffering(long playbackPositionUs) {
            this.selectedSource.continueBuffering(playbackPositionUs);
                }

                @Override 
        public void getChunkOperation(List<? extends MediaChunk> queue, long seekPositionUs, long playbackPositionUs, ChunkOperationHolder out) {
            this.selectedSource.getChunkOperation(queue, seekPositionUs, playbackPositionUs, out);
                }

                @Override 
        public IOException getError() {
            return null;
                }

                @Override 
        public void getMaxVideoDimensions(MediaFormat out) {
            this.selectedSource.getMaxVideoDimensions(out);
                }

                @Override 
        public void handleMessage(int what, Object msg) throws ExoPlaybackException {
            Assertions.checkState(!this.enabled);
           if (what == 1) {
               this.selectedSource = this.allSources[((Integer) msg).intValue()];
                    }
                }

                @Override 
       public void onChunkLoadError(Chunk chunk, Exception e) {
           this.selectedSource.onChunkLoadError(chunk, e);
                }
            }
