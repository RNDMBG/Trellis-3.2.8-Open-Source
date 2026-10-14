            package co.vine.android.recorder;

            import co.vine.android.recorder.audio.AudioData;
            import co.vine.android.recorder.video.VideoData;
            import com.edisonwang.android.slog.MessageFormatter;
            import com.edisonwang.android.slog.SLog;
            import java.util.ArrayList;
            
            
    public class TrimData {
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

        public TrimData(int startMs, int endMs) {
            this(startMs, KeyFrameMode.CLOEST, endMs, KeyFrameMode.CLOEST);
                }

        public TrimData(int startMs, int endMs, KeyFrameMode mode) {
            this(startMs, mode, endMs, mode);
                }

        public TrimData(int startMs, KeyFrameMode startMode, int endMs, KeyFrameMode endMode) {
            this.startMs = startMs;
            this.startMode = startMode;
            this.endMs = endMs;
            this.endMode = endMode;
                }

                
                
        private int getVideoStartIndex(ArrayList<VideoData> videoDatas, boolean ignoreLast) throws InvalidTrimException {
            long offset = videoDatas.get(0).timestamp;
            int startUs = this.startMs * 1000;
            int lastKeyFrameLocation = -1;
            int i = -1;
                    for (VideoData vData : videoDatas) {
                i++;
                if (ignoreLast && i + 1 == videoDatas.size()) {
                    if (i == 0) {
                        lastKeyFrameLocation = i;
                            }
                    if (lastKeyFrameLocation == -1) {
                        throw new InvalidTrimException("No key frame found in all video data.");
                            }
                            return lastKeyFrameLocation;
                        }
                if (vData.keyFrame) {
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
                                if (lastKeyFrameLocation == -1) {
                                            return i;
                                        }
                                long before = ((long) startUs) - (videoDatas.get(lastKeyFrameLocation).timestamp - offset);
                                long after = (vData.timestamp - offset) - ((long) startUs);
                                        return before > after ? i : lastKeyFrameLocation;
                                }
                            }
                    lastKeyFrameLocation = i;
                        }
                    }
            if (lastKeyFrameLocation == -1) {
                throw new InvalidTrimException("No key frame found in all video data.");
                    }
                    return lastKeyFrameLocation;
                }

        private int getVideoEndIndex(ArrayList<VideoData> videoDatas, int audioEndMs, int startIndex, int fps) throws InvalidTrimException {
            int audioEndUs = (audioEndMs * 1000) - (1000 / fps);
            int size = videoDatas.size();
            if (videoDatas.size() == 1) {
                        return 1;
                    }
            long startTimestamp = videoDatas.get(0).timestamp;
                    for (int i = startIndex; i < size; i++) {
                        if (videoDatas.get(i).timestamp - startTimestamp >= audioEndUs) {
                    if (i == startIndex) {
                        int i2 = startIndex + 1;
                       return i2;
                            }
                   return i;
                        }
                    }
                    return size;
                }

       public static class InvalidTrimException extends Exception {
           public InvalidTrimException(String message) {
               super(message);
                    }
                }

       public TrimResult getTrimResultFor(ArrayList<VideoData> videoData, AudioData combinedAudioData, int fps) throws InvalidTrimException {
                    long videoEndUs;
                    int audioEndCount;
                    long startStamp;
                    long endStamp;
           SLog.m453d("Generating trim result for {}ms {} -> {}ms {}.", new Object[]{Integer.valueOf(this.startMs), this.startMode.name(), Integer.valueOf(this.endMs), this.endMode.name()});
           int videoStartIndex = getVideoStartIndex(videoData, false);
           long videoStartUs = videoData.get(videoStartIndex).timestamp - videoData.get(0).timestamp;
           int audioStartCount = (int) (Math.floor(((44.1d * videoStartUs) / 1000.0d) / 512.0d) * 512.0d);
           int audioSizeGuess = (int) Math.min(Math.ceil((44.1d * ((double) this.endMs)) / 512.0d) * 512.0d, combinedAudioData.size);
           int audioEndMsGuess = (int) (((double) audioSizeGuess) / 44.1d);
           int videoEndIndex = getVideoEndIndex(videoData, audioEndMsGuess, videoStartIndex, fps);
           int numFrames = videoEndIndex - videoStartIndex;
           if (numFrames < 1) {
               throw new InvalidTrimException("Number of frames were less than 1.");
                    }
           boolean canReachMin = videoData.size() > 2;
           boolean wasTrimTooShort = canReachMin && numFrames < 2;
           if (numFrames < 2 || wasTrimTooShort) {
               while (videoEndIndex < videoData.size()) {
                   videoEndIndex++;
                   numFrames++;
                   if (numFrames >= 2 || !canReachMin) {
                                break;
                            }
                        }
                    }
           if (numFrames < 1 || (canReachMin && numFrames < 2)) {
               videoStartIndex = getVideoStartIndex(videoData, true);
               videoStartUs = videoData.get(videoStartIndex).timestamp - videoData.get(0).timestamp;
               audioStartCount = (int) (Math.floor(((44.1d * videoStartUs) / 1000.0d) / 512.0d) * 512.0d);
               int audioSizeGuess2 = (int) Math.min(Math.ceil((44.1d * ((double) this.endMs)) / 512.0d) * 512.0d, combinedAudioData.size);
               audioEndMsGuess = (int) (((double) audioSizeGuess2) / 44.1d);
               videoEndIndex = getVideoEndIndex(videoData, audioEndMsGuess, videoStartIndex, fps);
               numFrames = videoEndIndex - videoStartIndex;
               if (numFrames < 1) {
                   throw new InvalidTrimException("Number of frames were less than 1.");
                        }
               if (numFrames < 2 || wasTrimTooShort) {
                   while (videoEndIndex < videoData.size()) {
                       videoEndIndex++;
                       numFrames++;
                       if (numFrames >= 2 || !canReachMin) {
                                    break;
                                }
                            }
                        }
                    }
           if (videoEndIndex < videoData.size()) {
               videoEndUs = videoData.get(videoEndIndex).timestamp - videoData.get(0).timestamp;
               audioEndCount = Math.min(combinedAudioData.size, (int) (Math.ceil(((44.1d * videoEndUs) / 1000.0d) / 512.0d) * 512.0d));
                    } else {
               videoEndUs = audioEndMsGuess * 1000;
               audioEndCount = combinedAudioData.size;
                    }
           int audioEndMs = (int) (((double) audioEndCount) / 44.1d);
           TrimResult result = new TrimResult(videoStartIndex, videoEndIndex, audioStartCount, audioEndCount);
                    if (videoStartUs > videoEndUs) {
               if (videoStartIndex < videoData.size()) {
                   startStamp = videoData.get(videoStartIndex).timestamp;
                        } else {
                   startStamp = videoStartIndex * (-1);
                        }
               if (videoEndIndex < videoData.size()) {
                   endStamp = videoData.get(videoEndIndex).timestamp;
                        } else {
                   endStamp = videoEndIndex * (-1);
                        }
               throw new InvalidTrimException(MessageFormatter.format("Invalid video data: size {} {} {}.", Integer.valueOf(videoData.size()), Long.valueOf(startStamp), Long.valueOf(endStamp)).getMessage());
                    }
           int audioStartMs = result.getAudioStartMs();
           if (audioStartMs >= audioEndMs) {
               throw new InvalidTrimException("Invalid audio data produced.");
                    }
           if (canReachMin && numFrames < 2) {
               throw new InvalidTrimException("This should never happen, but trim frame count is lower than the min allowed.");
                    }
           SLog.m453d("Result trim: V {}us -> {}us A {}ms -> {}ms (Guess: {}ms) .", new Object[]{Long.valueOf(videoStartUs), Long.valueOf(videoEndUs), Integer.valueOf(audioStartMs), Integer.valueOf(audioEndMs), Integer.valueOf(audioEndMsGuess)});
           SLog.m448d("Result durations: V: {}ms, A: {}ms.", Long.valueOf((videoEndUs - videoStartUs) / 1000), Integer.valueOf(audioEndMs - audioStartMs));
           return result;
                }
            }
