            package co.vine.android.recorder;

            import co.vine.android.recorder.audio.AudioData;
            import co.vine.android.recorder.video.VideoData;
            import com.edisonwang.android.slog.MessageFormatter;
            import com.edisonwang.android.slog.SLog;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 27 */    public class TrimData {
                public static final int AUDIO_TRIM_MIN_MULTIPLE = 512;
                public KeyFrameMode endMode;
                public int endMs;
                public KeyFrameMode startMode;
                public int startMs;

                public enum KeyFrameMode {
                    BEFORE,
                    AFTER,
                    CLOEST
                }

/* 28 */        public TrimData(int startMs, int endMs) {
/* 29 */            this(startMs, KeyFrameMode.CLOEST, endMs, KeyFrameMode.CLOEST);
                }

/* 32 */        public TrimData(int startMs, int endMs, KeyFrameMode mode) {
/* 33 */            this(startMs, mode, endMs, mode);
                }

/* 35 */        public TrimData(int startMs, KeyFrameMode startMode, int endMs, KeyFrameMode endMode) {
/* 37 */            this.startMs = startMs;
/* 38 */            this.startMode = startMode;
/* 39 */            this.endMs = endMs;
/* 40 */            this.endMode = endMode;
                }

                /* JADX WARN: Code duplicated, block: B:13:0x0035  */
                /* JADX WARN: Code duplicated, block: B:35:0x0092  */
/* 43 */        private int getVideoStartIndex(ArrayList<VideoData> videoDatas, boolean ignoreLast) throws InvalidTrimException {
/* 44 */            long offset = videoDatas.get(0).timestamp;
/* 45 */            int startUs = this.startMs * 1000;
/* 46 */            int lastKeyFrameLocation = -1;
/* 47 */            int i = -1;
                    for (VideoData vData : videoDatas) {
/* 50 */                i++;
/* 51 */                if (ignoreLast && i + 1 == videoDatas.size()) {
/* 52 */                    if (i == 0) {
/* 53 */                        lastKeyFrameLocation = i;
                            }
/* 81 */                    if (lastKeyFrameLocation == -1) {
/* 82 */                        throw new InvalidTrimException("No key frame found in all video data.");
                            }
                            return lastKeyFrameLocation;
                        }
/* 57 */                if (vData.keyFrame) {
                            if (vData.timestamp - offset >= startUs) {
                                if (vData.timestamp - offset == startUs) {
                                    return i;
                                }
                                switch (this.startMode) {
                                    case BEFORE:
                                        return lastKeyFrameLocation;
                                    case AFTER:
                                        return i;
                                    case CLOEST:
/* 68 */                                if (lastKeyFrameLocation == -1) {
                                            return i;
                                        }
/* 71 */                                long before = ((long) startUs) - (videoDatas.get(lastKeyFrameLocation).timestamp - offset);
/* 72 */                                long after = (vData.timestamp - offset) - ((long) startUs);
                                        return before > after ? i : lastKeyFrameLocation;
                                }
                            }
/* 78 */                    lastKeyFrameLocation = i;
                        }
                    }
/* 81 */            if (lastKeyFrameLocation == -1) {
/* 82 */                throw new InvalidTrimException("No key frame found in all video data.");
                    }
                    return lastKeyFrameLocation;
                }

/* 87 */        private int getVideoEndIndex(ArrayList<VideoData> videoDatas, int audioEndMs, int startIndex, int fps) throws InvalidTrimException {
/* 88 */            int audioEndUs = (audioEndMs * 1000) - (1000 / fps);
/* 89 */            int size = videoDatas.size();
/* 90 */            if (videoDatas.size() == 1) {
                        return 1;
                    }
/* 93 */            long startTimestamp = videoDatas.get(0).timestamp;
                    for (int i = startIndex; i < size; i++) {
                        if (videoDatas.get(i).timestamp - startTimestamp >= audioEndUs) {
/* 97 */                    if (i == startIndex) {
/* 98 */                        int i2 = startIndex + 1;
/* 104 */                       return i2;
                            }
/* 104 */                   return i;
                        }
                    }
                    return size;
                }

/* 108 */       public static class InvalidTrimException extends Exception {
/* 109 */           public InvalidTrimException(String message) {
/* 110 */               super(message);
                    }
                }

/* 114 */       public TrimResult getTrimResultFor(ArrayList<VideoData> videoData, AudioData combinedAudioData, int fps) throws InvalidTrimException {
                    long videoEndUs;
                    int audioEndCount;
                    long startStamp;
                    long endStamp;
/* 115 */           SLog.m453d("Generating trim result for {}ms {} -> {}ms {}.", new Object[]{Integer.valueOf(this.startMs), this.startMode.name(), Integer.valueOf(this.endMs), this.endMode.name()});
/* 117 */           int videoStartIndex = getVideoStartIndex(videoData, false);
/* 119 */           long videoStartUs = videoData.get(videoStartIndex).timestamp - videoData.get(0).timestamp;
/* 121 */           int audioStartCount = (int) (Math.floor(((44.1d * videoStartUs) / 1000.0d) / 512.0d) * 512.0d);
/* 124 */           int audioSizeGuess = (int) Math.min(Math.ceil((44.1d * ((double) this.endMs)) / 512.0d) * 512.0d, combinedAudioData.size);
/* 127 */           int audioEndMsGuess = (int) (((double) audioSizeGuess) / 44.1d);
/* 129 */           int videoEndIndex = getVideoEndIndex(videoData, audioEndMsGuess, videoStartIndex, fps);
/* 131 */           int numFrames = videoEndIndex - videoStartIndex;
/* 133 */           if (numFrames < 1) {
/* 134 */               throw new InvalidTrimException("Number of frames were less than 1.");
                    }
/* 137 */           boolean canReachMin = videoData.size() > 2;
/* 139 */           boolean wasTrimTooShort = canReachMin && numFrames < 2;
/* 141 */           if (numFrames < 2 || wasTrimTooShort) {
/* 142 */               while (videoEndIndex < videoData.size()) {
/* 143 */                   videoEndIndex++;
/* 144 */                   numFrames++;
/* 145 */                   if (numFrames >= 2 || !canReachMin) {
                                break;
                            }
                        }
                    }
/* 151 */           if (numFrames < 1 || (canReachMin && numFrames < 2)) {
/* 153 */               videoStartIndex = getVideoStartIndex(videoData, true);
/* 155 */               videoStartUs = videoData.get(videoStartIndex).timestamp - videoData.get(0).timestamp;
/* 157 */               audioStartCount = (int) (Math.floor(((44.1d * videoStartUs) / 1000.0d) / 512.0d) * 512.0d);
/* 160 */               int audioSizeGuess2 = (int) Math.min(Math.ceil((44.1d * ((double) this.endMs)) / 512.0d) * 512.0d, combinedAudioData.size);
/* 163 */               audioEndMsGuess = (int) (((double) audioSizeGuess2) / 44.1d);
/* 165 */               videoEndIndex = getVideoEndIndex(videoData, audioEndMsGuess, videoStartIndex, fps);
/* 167 */               numFrames = videoEndIndex - videoStartIndex;
/* 169 */               if (numFrames < 1) {
/* 170 */                   throw new InvalidTrimException("Number of frames were less than 1.");
                        }
/* 173 */               if (numFrames < 2 || wasTrimTooShort) {
/* 174 */                   while (videoEndIndex < videoData.size()) {
/* 175 */                       videoEndIndex++;
/* 176 */                       numFrames++;
/* 177 */                       if (numFrames >= 2 || !canReachMin) {
                                    break;
                                }
                            }
                        }
                    }
/* 187 */           if (videoEndIndex < videoData.size()) {
/* 188 */               videoEndUs = videoData.get(videoEndIndex).timestamp - videoData.get(0).timestamp;
/* 189 */               audioEndCount = Math.min(combinedAudioData.size, (int) (Math.ceil(((44.1d * videoEndUs) / 1000.0d) / 512.0d) * 512.0d));
                    } else {
/* 194 */               videoEndUs = audioEndMsGuess * 1000;
/* 195 */               audioEndCount = combinedAudioData.size;
                    }
/* 198 */           int audioEndMs = (int) (((double) audioEndCount) / 44.1d);
/* 200 */           TrimResult result = new TrimResult(videoStartIndex, videoEndIndex, audioStartCount, audioEndCount);
                    if (videoStartUs > videoEndUs) {
/* 204 */               if (videoStartIndex < videoData.size()) {
/* 205 */                   startStamp = videoData.get(videoStartIndex).timestamp;
                        } else {
/* 207 */                   startStamp = videoStartIndex * (-1);
                        }
/* 210 */               if (videoEndIndex < videoData.size()) {
/* 211 */                   endStamp = videoData.get(videoEndIndex).timestamp;
                        } else {
/* 213 */                   endStamp = videoEndIndex * (-1);
                        }
/* 215 */               throw new InvalidTrimException(MessageFormatter.format("Invalid video data: size {} {} {}.", Integer.valueOf(videoData.size()), Long.valueOf(startStamp), Long.valueOf(endStamp)).getMessage());
                    }
/* 218 */           int audioStartMs = result.getAudioStartMs();
/* 220 */           if (audioStartMs >= audioEndMs) {
/* 221 */               throw new InvalidTrimException("Invalid audio data produced.");
                    }
/* 224 */           if (canReachMin && numFrames < 2) {
/* 225 */               throw new InvalidTrimException("This should never happen, but trim frame count is lower than the min allowed.");
                    }
/* 228 */           SLog.m453d("Result trim: V {}us -> {}us A {}ms -> {}ms (Guess: {}ms) .", new Object[]{Long.valueOf(videoStartUs), Long.valueOf(videoEndUs), Integer.valueOf(audioStartMs), Integer.valueOf(audioEndMs), Integer.valueOf(audioEndMsGuess)});
/* 229 */           SLog.m448d("Result durations: V: {}ms, A: {}ms.", Long.valueOf((videoEndUs - videoStartUs) / 1000), Integer.valueOf(audioEndMs - audioStartMs));
/* 231 */           return result;
                }
            }
