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
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 79 */    public class SmoothStreamingChunkSource implements ChunkSource {
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

/* 80 */        public SmoothStreamingChunkSource(String baseUrl, SmoothStreamingManifest manifest, int streamElementIndex, int[] trackIndices, DataSource dataSource, FormatEvaluator formatEvaluator) {
/* 82 */            this.baseUrl = baseUrl;
/* 83 */            this.streamElement = manifest.streamElements[streamElementIndex];
/* 84 */            this.trackInfo = new TrackInfo(this.streamElement.tracks[0].mimeType, manifest.getDurationUs());
/* 85 */            this.dataSource = dataSource;
/* 86 */            this.formatEvaluator = formatEvaluator;
/* 89 */            TrackEncryptionBox[] trackEncryptionBoxes = null;
/* 90 */            SmoothStreamingManifest.ProtectionElement protectionElement = manifest.protectionElement;
/* 91 */            if (protectionElement != null) {
/* 92 */                byte[] keyId = getKeyId(protectionElement.data);
/* 93 */                trackEncryptionBoxes = new TrackEncryptionBox[]{new TrackEncryptionBox(true, 8, keyId)};
                    }
/* 97 */            int trackCount = trackIndices != null ? trackIndices.length : this.streamElement.tracks.length;
/* 98 */            this.formats = new SmoothStreamingFormat[trackCount];
/* 99 */            this.extractors = new SparseArray<>();
/* 100 */           int maxWidth = 0;
/* 101 */           int maxHeight = 0;
                    for (int i = 0; i < trackCount; i++) {
/* 103 */               int trackIndex = trackIndices != null ? trackIndices[i] : i;
/* 104 */               SmoothStreamingManifest.TrackElement trackElement = this.streamElement.tracks[trackIndex];
/* 105 */               this.formats[i] = new SmoothStreamingFormat(String.valueOf(trackIndex), trackElement.mimeType, trackElement.maxWidth, trackElement.maxHeight, trackElement.numChannels, trackElement.sampleRate, trackElement.bitrate, trackIndex);
/* 108 */               maxWidth = Math.max(maxWidth, trackElement.maxWidth);
/* 109 */               maxHeight = Math.max(maxHeight, trackElement.maxHeight);
/* 111 */               MediaFormat mediaFormat = getMediaFormat(this.streamElement, trackIndex);
/* 112 */               int trackType = this.streamElement.type == 1 ? Track.TYPE_VIDEO : Track.TYPE_AUDIO;
/* 114 */               FragmentedMp4Extractor extractor = new FragmentedMp4Extractor(1);
/* 116 */               extractor.setTrack(new Track(trackIndex, trackType, this.streamElement.timeScale, mediaFormat, trackEncryptionBoxes));
/* 118 */               if (protectionElement != null) {
/* 119 */                   extractor.putPsshInfo(protectionElement.uuid, protectionElement.data);
                        }
/* 121 */               this.extractors.put(trackIndex, extractor);
                    }
/* 123 */           this.maxHeight = maxHeight;
/* 124 */           this.maxWidth = maxWidth;
/* 125 */           Arrays.sort(this.formats, new Format.DecreasingBandwidthComparator());
                }

                @Override // com.google.android.exoplayer.chunk.ChunkSource
/* 129 */       public final void getMaxVideoDimensions(MediaFormat out) {
/* 130 */           if (this.trackInfo.mimeType.startsWith(MimeTypes.BASE_TYPE_VIDEO)) {
/* 131 */               out.setMaxVideoDimensions(this.maxWidth, this.maxHeight);
                    }
                }

                @Override // com.google.android.exoplayer.chunk.ChunkSource
/* 136 */       public final TrackInfo getTrackInfo() {
/* 137 */           return this.trackInfo;
                }

                @Override // com.google.android.exoplayer.chunk.ChunkSource
/* 142 */       public void enable() {
                }

                @Override // com.google.android.exoplayer.chunk.ChunkSource
/* 147 */       public void disable(List<? extends MediaChunk> queue) {
                }

                @Override // com.google.android.exoplayer.chunk.ChunkSource
/* 152 */       public void continueBuffering(long playbackPositionUs) {
                }

                @Override // com.google.android.exoplayer.chunk.ChunkSource
/* 157 */       public final void getChunkOperation(List<? extends MediaChunk> queue, long seekPositionUs, long playbackPositionUs, ChunkOperationHolder out) {
                    int nextChunkIndex;
/* 158 */           this.evaluation.queueSize = queue.size();
/* 159 */           this.formatEvaluator.evaluate(queue, playbackPositionUs, this.formats, this.evaluation);
/* 160 */           SmoothStreamingFormat selectedFormat = (SmoothStreamingFormat) this.evaluation.format;
/* 161 */           out.queueSize = this.evaluation.queueSize;
/* 163 */           if (selectedFormat == null) {
/* 164 */               out.chunk = null;
/* 194 */               return;
                    }
/* 166 */           if (out.queueSize != queue.size() || out.chunk == null || !out.chunk.format.f875id.equals(this.evaluation.format.f875id)) {
/* 174 */               if (queue.isEmpty()) {
/* 175 */                   nextChunkIndex = this.streamElement.getChunkIndex(seekPositionUs);
                        } else {
/* 177 */                   nextChunkIndex = queue.get(out.queueSize - 1).nextChunkIndex;
                        }
/* 180 */               if (nextChunkIndex == -1) {
/* 181 */                   out.chunk = null;
/* 194 */                   return;
                        }
/* 185 */               boolean isLastChunk = nextChunkIndex == this.streamElement.chunkCount + (-1);
/* 186 */               String requestUrl = this.streamElement.buildRequestUrl(selectedFormat.trackIndex, nextChunkIndex);
/* 188 */               Uri uri = Uri.parse(this.baseUrl + '/' + requestUrl);
/* 189 */               Chunk mediaChunk = newMediaChunk(selectedFormat, uri, null, this.extractors.get(Integer.parseInt(selectedFormat.f875id)), this.dataSource, nextChunkIndex, isLastChunk, this.streamElement.getStartTimeUs(nextChunkIndex), isLastChunk ? -1L : this.streamElement.getStartTimeUs(nextChunkIndex + 1), 0);
/* 193 */               out.chunk = mediaChunk;
                    }
                }

                @Override // com.google.android.exoplayer.chunk.ChunkSource
/* 197 */       public IOException getError() {
/* 198 */           return null;
                }

                @Override // com.google.android.exoplayer.chunk.ChunkSource
/* 203 */       public void onChunkLoadError(Chunk chunk, Exception e) {
                }

/* 206 */       private static MediaFormat getMediaFormat(SmoothStreamingManifest.StreamElement streamElement, int trackIndex) {
                    List<byte[]> csd;
/* 207 */           SmoothStreamingManifest.TrackElement trackElement = streamElement.tracks[trackIndex];
/* 208 */           String mimeType = trackElement.mimeType;
/* 209 */           if (streamElement.type == 1) {
/* 210 */               MediaFormat format = MediaFormat.createVideoFormat(mimeType, -1, trackElement.maxWidth, trackElement.maxHeight, Arrays.asList(trackElement.csd));
/* 212 */               format.setMaxVideoDimensions(streamElement.maxWidth, streamElement.maxHeight);
/* 227 */               return format;
                    }
/* 214 */           if (streamElement.type == 0) {
/* 216 */               if (trackElement.csd != null) {
/* 217 */                   csd = Arrays.asList(trackElement.csd);
                        } else {
/* 219 */                   csd = Collections.singletonList(CodecSpecificDataUtil.buildAudioSpecificConfig(trackElement.sampleRate, trackElement.numChannels));
                        }
/* 222 */               return MediaFormat.createAudioFormat(mimeType, -1, trackElement.numChannels, trackElement.sampleRate, csd);
                    }
/* 227 */           return null;
                }

/* 232 */       private static MediaChunk newMediaChunk(Format formatInfo, Uri uri, String cacheKey, Extractor extractor, DataSource dataSource, int chunkIndex, boolean isLast, long chunkStartTimeUs, long nextChunkStartTimeUs, int trigger) {
/* 233 */           int nextChunkIndex = isLast ? -1 : chunkIndex + 1;
/* 234 */           long nextStartTimeUs = isLast ? -1L : nextChunkStartTimeUs;
/* 236 */           DataSpec dataSpec = new DataSpec(uri, 0L, -1L, cacheKey);
/* 239 */           return new Mp4MediaChunk(dataSource, dataSpec, formatInfo, trigger, chunkStartTimeUs, nextStartTimeUs, nextChunkIndex, extractor, false, -chunkStartTimeUs);
                }

/* 243 */       private static byte[] getKeyId(byte[] initData) {
/* 244 */           StringBuilder initDataStringBuilder = new StringBuilder();
                    for (int i = 0; i < initData.length; i += 2) {
/* 246 */               initDataStringBuilder.append((char) initData[i]);
                    }
/* 248 */           String initDataString = initDataStringBuilder.toString();
/* 249 */           String keyIdString = initDataString.substring(initDataString.indexOf("<KID>") + 5, initDataString.indexOf("</KID>"));
/* 251 */           byte[] keyId = Base64.decode(keyIdString, 0);
/* 252 */           swap(keyId, 0, 3);
/* 253 */           swap(keyId, 1, 2);
/* 254 */           swap(keyId, 4, 5);
/* 255 */           swap(keyId, 6, 7);
/* 256 */           return keyId;
                }

/* 259 */       private static void swap(byte[] data, int firstPosition, int secondPosition) {
/* 260 */           byte temp = data[firstPosition];
/* 261 */           data[firstPosition] = data[secondPosition];
/* 262 */           data[secondPosition] = temp;
                }

/* 269 */       private static final class SmoothStreamingFormat extends Format {
                    public final int trackIndex;

/* 270 */           public SmoothStreamingFormat(String id, String mimeType, int width, int height, int numChannels, int audioSamplingRate, int bitrate, int trackIndex) {
/* 271 */               super(id, mimeType, width, height, numChannels, audioSamplingRate, bitrate);
/* 272 */               this.trackIndex = trackIndex;
                    }
                }
            }
