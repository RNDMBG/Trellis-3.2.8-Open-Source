            package co.vine.android.recorder;

            import co.vine.android.recorder.audio.AudioArray;
            import co.vine.android.recorder.audio.AudioArrays;
            import co.vine.android.recorder.video.VideoData;
            import co.vine.android.util.InstanceCounter;
            import com.edisonwang.android.slog.MessageFormatter;
            import com.edisonwang.android.slog.SLog;
            import java.io.Externalizable;
            import java.io.IOException;
            import java.io.ObjectInput;
            import java.io.ObjectOutput;
            import java.util.ArrayList;
            import org.json.JSONArray;
            import org.json.JSONException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 38 */    public class RecordSession implements Externalizable {
                private static final InstanceCounter sInstanceCounter = new InstanceCounter(5);
                private static final int[] sLock = new int[0];
                private static final long serialVersionUID = 8590159620194730309L;
                private AudioArray mAudioData;
                private RecordConfigUtils.RecordConfig mConfig;
                private RecordSessionVersion mVersion;
                private byte[] mVideoData;
                private final ArrayList<RecordSegment> mSegments = new ArrayList<>();
                private int mVideoDataCount = -1;
                private int mAudioDataCount = -1;

/* 39 */        public RecordSession() {
                }

/* 43 */        public RecordSession(RecordSessionVersion version) {
                    synchronized (sLock) {
                        try {
/* 46 */                    this.mVersion = version;
/* 47 */                    if (SLog.sLogsOn) {
/* 48 */                        sInstanceCounter.onCreate(this);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 53 */        public static RecordSession newSession(RecordConfigUtils.RecordConfig config, RecordSessionVersion version) {
/* 54 */            RecordSession session = new RecordSession(version);
/* 55 */            session.mVideoData = new byte[RecordConfigUtils.getVideoBufferMax(config)];
/* 56 */            session.mAudioData = AudioArrays.newInstance(RecordConfigUtils.getMaxAudioBufferSize(config), version.getAudioArrayType());
/* 57 */            session.mConfig = config;
/* 58 */            return session;
                }

/* 61 */        public int getVideoDataCount() {
/* 62 */            if (this.mVideoDataCount == -1) {
/* 63 */                this.mVideoDataCount = calculateVideoCount();
                    }
/* 65 */            return this.mVideoDataCount;
                }

/* 68 */        public int getAudioDataCount() {
/* 69 */            if (this.mAudioDataCount == -1) {
/* 70 */                this.mAudioDataCount = calculateAudioCount();
                    }
/* 72 */            return this.mAudioDataCount;
                }

/* 75 */        public void setVideoDataCount(int videoDataCount) {
/* 76 */            if (SLog.sLogsOn && videoDataCount != calculateVideoCount()) {
/* 77 */                SLog.m457e("Video Counts does not match: {} {}.", Integer.valueOf(videoDataCount), Integer.valueOf(calculateVideoCount()));
                    }
/* 79 */            this.mVideoDataCount = videoDataCount;
                }

/* 82 */        public void setAudioDataCount(int audioDataCount) {
/* 83 */            if (SLog.sLogsOn && audioDataCount != calculateAudioCount()) {
/* 84 */                SLog.m457e("Audio Counts does not match: {} {}.", Integer.valueOf(audioDataCount), Integer.valueOf(calculateAudioCount()));
                    }
/* 86 */            this.mAudioDataCount = audioDataCount;
                }

/* 89 */        public int calculateVideoCount() {
/* 90 */            int total = 0;
                    for (RecordSegment segment : this.mSegments) {
/* 92 */                if (!segment.removed) {
/* 93 */                    ArrayList<VideoData> videoData = segment.getVideoData();
                            for (VideoData data : videoData) {
/* 95 */                        if (data.size > 0) {
/* 96 */                            total += data.size;
                                }
                            }
                        }
                    }
/* 101 */           return total;
                }

/* 104 */       public int calculateAudioCount() {
/* 105 */           int total = 0;
                    for (RecordSegment segment : this.mSegments) {
/* 107 */               if (!segment.removed) {
/* 108 */                   total += segment.getCombinedAudioData().size;
                        }
                    }
/* 111 */           return total;
                }

/* 114 */       public int calculateMemoryBackedAudioCount() {
/* 115 */           int total = 0;
                    for (RecordSegment segment : this.mSegments) {
/* 117 */               if (!segment.isDataFileBacked() && !segment.removed) {
/* 118 */                   total += segment.getCombinedAudioData().size;
                        }
                    }
/* 121 */           return total;
                }

                @Override // java.io.Externalizable
/* 124 */       public void writeExternal(ObjectOutput out) throws IOException {
/* 125 */           out.writeObject(this.mVersion);
/* 126 */           out.writeObject(this.mVideoData);
/* 127 */           out.writeObject(this.mAudioData.getData());
/* 128 */           out.writeObject(this.mSegments);
                }

                @Override // java.io.Externalizable
/* 132 */       public void readExternal(ObjectInput stream) throws ClassNotFoundException, IOException {
/* 133 */           Object obj = stream.readObject();
/* 134 */           if (obj instanceof byte[]) {
/* 135 */               this.mVideoData = (byte[]) obj;
                    } else {
/* 137 */               this.mVersion = (RecordSessionVersion) obj;
/* 138 */               this.mVideoData = (byte[]) stream.readObject();
                    }
/* 140 */           Object audioData = stream.readObject();
/* 141 */           this.mAudioData = AudioArrays.wrap(audioData);
/* 142 */           this.mSegments.addAll((ArrayList) stream.readObject());
                }

/* 145 */       public ArrayList<RecordSegment> getSegments() {
/* 146 */           return this.mSegments;
                }

/* 149 */       public byte[] getVideoData() {
/* 150 */           return this.mVideoData;
                }

/* 153 */       public AudioArray getAudioData() {
/* 154 */           return this.mAudioData;
                }

/* 157 */       public void add(RecordSegment currentSegment) {
/* 158 */           this.mSegments.add(currentSegment);
                }

/* 161 */       public RecordConfigUtils.RecordConfig getConfig() {
/* 162 */           return this.mConfig;
                }

/* 165 */       public void setConfig(RecordConfigUtils.RecordConfig config) {
/* 166 */           this.mConfig = config;
                }

/* 170 */       public String toString() {
                    return MessageFormatter.format("\nVideoData Count: {}, AudioData Count: {}, Segments: {}", Integer.valueOf(this.mVideoDataCount), Integer.valueOf(this.mAudioDataCount), Integer.valueOf(this.mSegments.size())).getMessage() + (this.mSegments.size() > 0 ? " Last: " + this.mSegments.get(this.mSegments.size() - 1) : "");
                }

/* 175 */       public RecordSessionVersion getVersion() {
/* 176 */           if (this.mVersion == null) {
/* 178 */               this.mVersion = RecordSessionVersion.SW_MP4;
                    }
/* 180 */           return this.mVersion;
                }

/* 183 */       public int getDurationMs() {
/* 184 */           int durationMs = 0;
                    for (RecordSegment segment : this.mSegments) {
/* 186 */               if (!segment.removed) {
/* 187 */                   durationMs += segment.getDurationMs();
                        }
                    }
/* 190 */           return durationMs;
                }

/* 193 */       public JSONArray getMetaData() throws JSONException {
/* 194 */           long lastTimeStamp = 0;
/* 195 */           JSONArray array = new JSONArray();
                    for (RecordSegment segment : this.mSegments) {
/* 197 */               if (!segment.removed) {
/* 198 */                   array.put(segment.getMetaData(lastTimeStamp));
/* 199 */                   lastTimeStamp += (long) segment.getDurationMs();
                        }
                    }
/* 202 */           return array;
                }
            }
