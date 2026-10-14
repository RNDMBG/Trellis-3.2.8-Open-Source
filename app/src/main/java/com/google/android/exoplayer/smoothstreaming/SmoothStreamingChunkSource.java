            package com.google.android.exoplayer.smoothstreaming;

            import android.net.Uri;
            import android.util.Base64;
            import android.util.SparseArray;
            import com.google.android.exoplayer.MediaFormat;
            import com.google.android.exoplayer.TrackInfo;
            import com.google.android.exoplayer.chunk.Chunk;
            import com.google.android.exoplayer.chunk.ChunkOperationHolder;
            import com.google.android.exoplayer.chunk.ChunkSource;
            import com.google.android.exoplayer.chunk.Format;
            import com.google.android.exoplayer.chunk.FormatEvaluator;
            import com.google.android.exoplayer.chunk.MediaChunk;
            import com.google.android.exoplayer.chunk.Mp4MediaChunk;
            import com.google.android.exoplayer.parser.Extractor;
            import com.google.android.exoplayer.parser.mp4.FragmentedMp4Extractor;
            import com.google.android.exoplayer.parser.mp4.Track;
            import com.google.android.exoplayer.parser.mp4.TrackEncryptionBox;
            import com.google.android.exoplayer.upstream.DataSource;
            import com.google.android.exoplayer.upstream.DataSpec;
            import com.google.android.exoplayer.util.CodecSpecificDataUtil;
            import com.google.android.exoplayer.util.MimeTypes;
            import java.io.IOException;
            import java.util.Arrays;
            import java.util.Collections;
            import java.util.List;
            
            
    public class SmoothStreamingChunkSource implements ChunkSource {
                private static final int INITIALIZATION_VECTOR_SIZE = 8;
                private final String baseUrl;
                private final DataSource dataSource;
                private final FormatEvaluator.Evaluation evaluation = new FormatEvaluator.Evaluation();
                private final SparseArray<FragmentedMp4Extractor> extractors;
                private final FormatEvaluator formatEvaluator;
                private final SmoothStreamingFormat[] formats;
                private final int maxHeight;
                private final int maxWidth;
                private final SmoothStreamingManifest.StreamElement streamElement;
                private final TrackInfo trackInfo;

        public SmoothStreamingChunkSource(String baseUrl, SmoothStreamingManifest manifest, int streamElementIndex, int[] trackIndices, DataSource dataSource, FormatEvaluator formatEvaluator) {
            this.baseUrl = baseUrl;
            this.streamElement = manifest.streamElements[streamElementIndex];
            this.trackInfo = new TrackInfo(this.streamElement.tracks[0].mimeType, manifest.getDurationUs());
            this.dataSource = dataSource;
            this.formatEvaluator = formatEvaluator;
            TrackEncryptionBox[] trackEncryptionBoxes = null;
            SmoothStreamingManifest.ProtectionElement protectionElement = manifest.protectionElement;
            if (protectionElement != null) {
                byte[] keyId = getKeyId(protectionElement.data);
                trackEncryptionBoxes = new TrackEncryptionBox[]{new TrackEncryptionBox(true, 8, keyId)};
                    }
            int trackCount = trackIndices != null ? trackIndices.length : this.streamElement.tracks.length;
            this.formats = new SmoothStreamingFormat[trackCount];
            this.extractors = new SparseArray<>();
           int maxWidth = 0;
           int maxHeight = 0;
                    for (int i = 0; i < trackCount; i++) {
               int trackIndex = trackIndices != null ? trackIndices[i] : i;
               SmoothStreamingManifest.TrackElement trackElement = this.streamElement.tracks[trackIndex];
               this.formats[i] = new SmoothStreamingFormat(String.valueOf(trackIndex), trackElement.mimeType, trackElement.maxWidth, trackElement.maxHeight, trackElement.numChannels, trackElement.sampleRate, trackElement.bitrate, trackIndex);
               maxWidth = Math.max(maxWidth, trackElement.maxWidth);
               maxHeight = Math.max(maxHeight, trackElement.maxHeight);
               MediaFormat mediaFormat = getMediaFormat(this.streamElement, trackIndex);
               int trackType = this.streamElement.type == 1 ? Track.TYPE_VIDEO : Track.TYPE_AUDIO;
               FragmentedMp4Extractor extractor = new FragmentedMp4Extractor(1);
               extractor.setTrack(new Track(trackIndex, trackType, this.streamElement.timeScale, mediaFormat, trackEncryptionBoxes));
               if (protectionElement != null) {
                   extractor.putPsshInfo(protectionElement.uuid, protectionElement.data);
                        }
               this.extractors.put(trackIndex, extractor);
                    }
           this.maxHeight = maxHeight;
           this.maxWidth = maxWidth;
           Arrays.sort(this.formats, new Format.DecreasingBandwidthComparator());
                }

                @Override 
       public final void getMaxVideoDimensions(MediaFormat out) {
           if (this.trackInfo.mimeType.startsWith(MimeTypes.BASE_TYPE_VIDEO)) {
               out.setMaxVideoDimensions(this.maxWidth, this.maxHeight);
                    }
                }

                @Override 
       public final TrackInfo getTrackInfo() {
           return this.trackInfo;
                }

                @Override 
       public void enable() {
                }

                @Override 
       public void disable(List<? extends MediaChunk> queue) {
                }

                @Override 
       public void continueBuffering(long playbackPositionUs) {
                }

                @Override 
       public final void getChunkOperation(List<? extends MediaChunk> queue, long seekPositionUs, long playbackPositionUs, ChunkOperationHolder out) {
                    int nextChunkIndex;
           this.evaluation.queueSize = queue.size();
           this.formatEvaluator.evaluate(queue, playbackPositionUs, this.formats, this.evaluation);
           SmoothStreamingFormat selectedFormat = (SmoothStreamingFormat) this.evaluation.format;
           out.queueSize = this.evaluation.queueSize;
           if (selectedFormat == null) {
               out.chunk = null;
               return;
                    }
           if (out.queueSize != queue.size() || out.chunk == null || !out.chunk.format.f875id.equals(this.evaluation.format.f875id)) {
               if (queue.isEmpty()) {
                   nextChunkIndex = this.streamElement.getChunkIndex(seekPositionUs);
                        } else {
                   nextChunkIndex = queue.get(out.queueSize - 1).nextChunkIndex;
                        }
               if (nextChunkIndex == -1) {
                   out.chunk = null;
                   return;
                        }
               boolean isLastChunk = nextChunkIndex == this.streamElement.chunkCount + (-1);
               String requestUrl = this.streamElement.buildRequestUrl(selectedFormat.trackIndex, nextChunkIndex);
               Uri uri = Uri.parse(this.baseUrl + '/' + requestUrl);
               Chunk mediaChunk = newMediaChunk(selectedFormat, uri, null, this.extractors.get(Integer.parseInt(selectedFormat.f875id)), this.dataSource, nextChunkIndex, isLastChunk, this.streamElement.getStartTimeUs(nextChunkIndex), isLastChunk ? -1L : this.streamElement.getStartTimeUs(nextChunkIndex + 1), 0);
               out.chunk = mediaChunk;
                    }
                }

                @Override 
       public IOException getError() {
           return null;
                }

                @Override 
       public void onChunkLoadError(Chunk chunk, Exception e) {
                }

       private static MediaFormat getMediaFormat(SmoothStreamingManifest.StreamElement streamElement, int trackIndex) {
                    List<byte[]> csd;
           SmoothStreamingManifest.TrackElement trackElement = streamElement.tracks[trackIndex];
           String mimeType = trackElement.mimeType;
           if (streamElement.type == 1) {
               MediaFormat format = MediaFormat.createVideoFormat(mimeType, -1, trackElement.maxWidth, trackElement.maxHeight, Arrays.asList(trackElement.csd));
               format.setMaxVideoDimensions(streamElement.maxWidth, streamElement.maxHeight);
               return format;
                    }
           if (streamElement.type == 0) {
               if (trackElement.csd != null) {
                   csd = Arrays.asList(trackElement.csd);
                        } else {
                   csd = Collections.singletonList(CodecSpecificDataUtil.buildAudioSpecificConfig(trackElement.sampleRate, trackElement.numChannels));
                        }
               return MediaFormat.createAudioFormat(mimeType, -1, trackElement.numChannels, trackElement.sampleRate, csd);
                    }
           return null;
                }

       private static MediaChunk newMediaChunk(Format formatInfo, Uri uri, String cacheKey, Extractor extractor, DataSource dataSource, int chunkIndex, boolean isLast, long chunkStartTimeUs, long nextChunkStartTimeUs, int trigger) {
           int nextChunkIndex = isLast ? -1 : chunkIndex + 1;
           long nextStartTimeUs = isLast ? -1L : nextChunkStartTimeUs;
           DataSpec dataSpec = new DataSpec(uri, 0L, -1L, cacheKey);
           return new Mp4MediaChunk(dataSource, dataSpec, formatInfo, trigger, chunkStartTimeUs, nextStartTimeUs, nextChunkIndex, extractor, false, -chunkStartTimeUs);
                }

       private static byte[] getKeyId(byte[] initData) {
           StringBuilder initDataStringBuilder = new StringBuilder();
                    for (int i = 0; i < initData.length; i += 2) {
               initDataStringBuilder.append((char) initData[i]);
                    }
           String initDataString = initDataStringBuilder.toString();
           String keyIdString = initDataString.substring(initDataString.indexOf("<KID>") + 5, initDataString.indexOf("</KID>"));
           byte[] keyId = Base64.decode(keyIdString, 0);
           swap(keyId, 0, 3);
           swap(keyId, 1, 2);
           swap(keyId, 4, 5);
           swap(keyId, 6, 7);
           return keyId;
                }

       private static void swap(byte[] data, int firstPosition, int secondPosition) {
           byte temp = data[firstPosition];
           data[firstPosition] = data[secondPosition];
           data[secondPosition] = temp;
                }

       private static final class SmoothStreamingFormat extends Format {
                    public final int trackIndex;

           public SmoothStreamingFormat(String id, String mimeType, int width, int height, int numChannels, int audioSamplingRate, int bitrate, int trackIndex) {
               super(id, mimeType, width, height, numChannels, audioSamplingRate, bitrate);
               this.trackIndex = trackIndex;
                    }
                }
            }
