            package co.vine.android.recorder;

            import android.content.Context;
            import android.graphics.drawable.BitmapDrawable;
            import android.graphics.drawable.Drawable;
            import android.support.annotation.NonNull;
            import android.support.annotation.Nullable;
            import android.text.TextUtils;
            import co.vine.android.recorder.audio.AudioArray;
            import co.vine.android.recorder.audio.AudioArrays;
            import co.vine.android.recorder.audio.AudioData;
            import co.vine.android.recorder.camera.CameraSetting;
            import co.vine.android.recorder.video.VideoData;
            import co.vine.android.util.CrashUtil;
            import com.edisonwang.android.slog.SLog;
            import com.googlecode.javacpp.BytePointer;
            import com.googlecode.javacpp.ShortPointer;
            import com.googlecode.javacv.cpp.avutil;
            import java.io.BufferedInputStream;
            import java.io.BufferedOutputStream;
            import java.io.DataInputStream;
            import java.io.DataOutputStream;
            import java.io.Externalizable;
            import java.io.File;
            import java.io.FileInputStream;
            import java.io.FileNotFoundException;
            import java.io.FileOutputStream;
            import java.io.IOException;
            import java.io.ObjectInput;
            import java.io.ObjectOutput;
            import java.nio.Buffer;
            import java.nio.ByteBuffer;
            import java.util.ArrayList;
            import java.util.Collection;
            import java.util.HashMap;
            import java.util.TimeZone;
            import org.json.JSONException;
            import org.json.JSONObject;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 84 */    public class RecordSegment implements Externalizable {
                private static final long serialVersionUID = 7122730863787653276L;
                public Drawable drawable;
                public int index;
                private ArrayList<AudioData> mAudioData;
                private int mFrameRate;
                private String mFullDataId;
                private final HashMap<String, String> mImportedMetaData;
                private boolean mIsImported;
                private long mRecordingTime;
                private boolean mScilenced;
                private CameraSetting mSetting;
                private String mThumbnailPath;
                private String mTimeZoneId;
                private int mTrimEndMs;
                private TrimResult mTrimResult;
                private int mTrimStartMs;
                private boolean mValidated;
                private ArrayList<VideoData> mVideoData;
                public boolean removed;
                public boolean shouldAnimateIn;
                public long startTimestamp;
                public String videoPath;

/* 85 */        public RecordSegment() {
/* 60 */            this.mFullDataId = "";
/* 70 */            this.mImportedMetaData = new HashMap<>();
/* 87 */            this.mRecordingTime = -1L;
                }

/* 90 */        public RecordSegment(long currentDuration) {
/* 91 */            this(currentDuration, 30);
                }

/* 93 */        public RecordSegment(long currentDuration, int frameRate) {
/* 60 */            this.mFullDataId = "";
/* 70 */            this.mImportedMetaData = new HashMap<>();
/* 95 */            this.mVideoData = new ArrayList<>();
/* 96 */            this.mAudioData = new ArrayList<>();
/* 97 */            this.mThumbnailPath = "";
/* 98 */            this.startTimestamp = currentDuration;
/* 99 */            this.mRecordingTime = System.currentTimeMillis();
/* 100 */           this.mTimeZoneId = TimeZone.getDefault().getID();
/* 101 */           this.mFrameRate = frameRate;
                }

/* 108 */       public RecordSegment(RecordSegment source) {
/* 60 */            this.mFullDataId = "";
/* 70 */            this.mImportedMetaData = new HashMap<>();
/* 110 */           copyFrom(source);
                }

/* 113 */       private void copyFrom(RecordSegment source) {
/* 114 */           this.mSetting = source.mSetting;
/* 115 */           this.mThumbnailPath = source.mThumbnailPath;
/* 116 */           this.removed = source.removed;
/* 117 */           this.index = source.index;
/* 118 */           this.videoPath = source.videoPath;
/* 119 */           this.startTimestamp = source.startTimestamp;
/* 120 */           this.mVideoData = new ArrayList<>();
                    for (VideoData videoData : source.mVideoData) {
/* 122 */               this.mVideoData.add(new VideoData(videoData));
                    }
/* 124 */           this.mAudioData = new ArrayList<>();
                    for (AudioData audioData : source.mAudioData) {
/* 126 */               this.mAudioData.add(new AudioData(audioData));
                    }
/* 128 */           this.mFullDataId = source.mFullDataId;
/* 129 */           this.mIsImported = source.mIsImported;
/* 130 */           this.mValidated = source.mValidated;
/* 131 */           this.mTrimResult = source.mTrimResult;
/* 132 */           this.mTrimStartMs = source.mTrimStartMs;
/* 133 */           this.mTrimEndMs = source.mTrimEndMs;
/* 134 */           this.mScilenced = source.mScilenced;
/* 135 */           this.mRecordingTime = source.mRecordingTime;
/* 136 */           this.mTimeZoneId = source.mTimeZoneId;
/* 137 */           this.mFrameRate = source.mFrameRate;
/* 138 */           this.mImportedMetaData.clear();
/* 139 */           this.mImportedMetaData.putAll(source.mImportedMetaData);
                }

/* 142 */       public String getThumbnailPath() {
/* 143 */           return this.mThumbnailPath;
                }

/* 146 */       public void setThumbnailPath(String path) {
/* 147 */           this.mThumbnailPath = path;
                }

                @Override // java.io.Externalizable
/* 150 */       public void writeExternal(ObjectOutput out) throws IOException {
/* 151 */           out.writeObject(this.mFullDataId);
/* 152 */           out.writeInt(this.mFrameRate);
/* 153 */           out.writeLong(this.mRecordingTime);
/* 154 */           out.writeObject(this.mTimeZoneId);
/* 155 */           out.writeBoolean(this.mValidated);
/* 156 */           out.writeBoolean(this.mIsImported);
/* 157 */           out.writeObject(this.mImportedMetaData);
/* 158 */           out.writeObject(this.mTrimResult);
/* 159 */           out.writeInt(this.mTrimStartMs);
/* 160 */           out.writeInt(this.mTrimEndMs);
/* 161 */           out.writeBoolean(this.mScilenced);
/* 162 */           out.writeObject(this.mSetting);
/* 163 */           out.writeObject(this.mThumbnailPath);
/* 164 */           out.writeObject(this.mVideoData);
/* 165 */           out.writeObject(getCombinedAudioData());
                }

                @Override // java.io.Externalizable
/* 169 */       public void readExternal(ObjectInput stream) throws ClassNotFoundException, IOException {
/* 170 */           Object obj = stream.readObject();
/* 171 */           if (obj instanceof CameraSetting) {
/* 172 */               this.mSetting = (CameraSetting) obj;
/* 173 */               this.mFullDataId = "";
/* 174 */               this.mTrimResult = null;
/* 175 */               this.mRecordingTime = -1L;
/* 176 */               this.mFrameRate = 30;
                    } else {
/* 178 */               this.mFullDataId = (String) obj;
/* 179 */               this.mFrameRate = stream.readInt();
/* 180 */               this.mRecordingTime = stream.readLong();
/* 181 */               this.mTimeZoneId = (String) stream.readObject();
/* 182 */               this.mValidated = stream.readBoolean();
/* 183 */               this.mIsImported = stream.readBoolean();
/* 184 */               this.mImportedMetaData.putAll((HashMap) stream.readObject());
/* 185 */               this.mTrimResult = (TrimResult) stream.readObject();
/* 186 */               this.mTrimStartMs = stream.readInt();
/* 187 */               this.mTrimEndMs = stream.readInt();
/* 188 */               this.mScilenced = stream.readBoolean();
/* 189 */               this.mSetting = (CameraSetting) stream.readObject();
                    }
/* 191 */           this.mThumbnailPath = (String) stream.readObject();
/* 192 */           this.mVideoData = (ArrayList) stream.readObject();
/* 193 */           this.mAudioData = new ArrayList<>();
/* 194 */           this.mAudioData.add((AudioData) stream.readObject());
                }

/* 197 */       public boolean isTrimmed() {
                    return this.mTrimResult != null;
                }

/* 201 */       public String getDataFileName() {
/* 202 */           return this.mFullDataId;
                }

/* 205 */       public static File getSegmentDataFolder(File draftFolder) {
/* 206 */           return new File(draftFolder, "segments");
                }

/* 209 */       public boolean isDataFileBacked() {
                    return !TextUtils.isEmpty(this.mFullDataId);
                }

/* 213 */       public void setTrimData(TrimData data) throws TrimData.InvalidTrimException {
/* 214 */           if (data == null) {
/* 215 */               this.mTrimStartMs = 0;
/* 216 */               this.mTrimEndMs = 0;
/* 217 */               this.mTrimResult = null;
/* 228 */               return;
                    }
/* 219 */           correctVideoTimestampsIfNeeded();
/* 220 */           ArrayList<VideoData> videoData = getVideoData();
/* 221 */           if (videoData.size() == 0) {
/* 222 */               throw new TrimData.InvalidTrimException("You cannot trim a segment with 0 frames.");
                    }
/* 224 */           this.mTrimResult = data.getTrimResultFor(videoData, getCombinedAudioData(), this.mFrameRate);
/* 225 */           this.mTrimStartMs = data.startMs;
/* 226 */           this.mTrimEndMs = data.endMs;
                }

/* 230 */       public synchronized void correctVideoTimestampsIfNeeded() {
                    try {
                        if (!this.mValidated) {
/* 232 */                   validateAndFixVideoTimestampsIfNeeded(true);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 236 */       private synchronized void correctVideoTimestamps(int usPf, int missingFrameCount, int expectedDurationUs) {
                    try {
                        int size = this.mVideoData.size();
/* 238 */               long offset = this.mVideoData.get(0).timestamp;
/* 239 */               int realUsPf = missingFrameCount < 2 ? usPf : expectedDurationUs / size;
                        for (int i = 1; i < size; i++) {
/* 241 */                   this.mVideoData.get(i).timestamp = ((long) (i * realUsPf)) + offset;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 245 */       public void setSilenced(boolean silenced) {
/* 246 */           this.mScilenced = silenced;
                }

/* 249 */       public int getDurationMs() {
/* 250 */           return getAudioDurationMs();
                }

/* 253 */       public int getUnTrimmedAudioDurationMs() {
/* 254 */           return RecordConfigUtils.getTimeStampInMsFromSampleCounted(getCombinedAudioData().size);
                }

/* 257 */       public int getAudioDurationMs() {
                    return isTrimmed() ? this.mTrimResult.getAudioDuration() : getUnTrimmedAudioDurationMs();
                }

/* 265 */       public synchronized AudioData getCombinedAudioData() {
                    AudioData audioData;
                    try {
                        if (this.mAudioData.size() == 0) {
/* 267 */                   audioData = new AudioData(0, 0);
                        } else {
/* 269 */                   if (this.mAudioData.size() > 1) {
/* 270 */                       int t = 0;
                                for (AudioData data : this.mAudioData) {
/* 272 */                           t += data.size;
                                }
/* 274 */                       AudioData audioCombined = new AudioData(this.mAudioData.get(0).start, t);
/* 275 */                       this.mAudioData.clear();
/* 276 */                       this.mAudioData.add(audioCombined);
                            }
/* 278 */                   audioData = this.mAudioData.get(0);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return audioData;
                }

                @NonNull
/* 283 */       public ArrayList<VideoData> getVideoData() {
/* 284 */           return this.mVideoData;
                }

/* 287 */       public ArrayList<VideoData> getVideoDataTrimmedIfNeeded() {
/* 288 */           if (isTrimmed()) {
/* 289 */               ArrayList<VideoData> trimmed = new ArrayList<>();
                        for (int i = this.mTrimResult.videoStartIndex; i < this.mTrimResult.videoEndIndex; i++) {
/* 291 */                   trimmed.add(this.mVideoData.get(i));
                        }
                        return trimmed;
                    }
/* 295 */           return this.mVideoData;
                }

/* 299 */       public synchronized void addAudioData(AudioData data) {
                    try {
                        this.mAudioData.add(data);
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 303 */       public CameraSetting getCameraSetting() {
/* 304 */           return this.mSetting;
                }

/* 307 */       public RecordSegment setCameraSetting(CameraSetting cameraSetting) {
/* 308 */           this.mSetting = cameraSetting;
/* 309 */           return this;
                }

/* 312 */       public AudioData createTrimmedCombinedAudioData() {
/* 313 */           AudioData data = new AudioData(getCombinedAudioData());
/* 314 */           if (isTrimmed()) {
/* 315 */               data.start += this.mTrimResult.audioStartOffset;
/* 316 */               data.size = this.mTrimResult.audioEndCount - this.mTrimResult.audioStartOffset;
                    }
/* 318 */           return data;
                }

/* 321 */       public TrimResult getTrimResult() {
/* 322 */           return this.mTrimResult;
                }

/* 325 */       public boolean isSilenced() {
/* 326 */           return this.mScilenced;
                }

/* 329 */       public long getTrimmedVideoStartUs() {
/* 330 */           return this.mTrimResult.getVideoStartUs(getVideoData());
                }

/* 333 */       public long getTrimmedVideoStartMs() {
/* 334 */           return getTrimmedVideoStartUs() / 1000;
                }

/* 337 */       public synchronized void validateAndFixVideoTimestampsIfNeeded(boolean fixTimestamps) {
                    int videoTime;
                    try {
                        int usPf = avutil.AV_TIME_BASE / this.mFrameRate;
/* 339 */               int msPf = usPf / 1000;
/* 340 */               int audioTimeMs = (int) (((double) getCombinedAudioData().size) / 44.1d);
/* 341 */               boolean hasWarning = false;
/* 342 */               boolean validationPasses = true;
/* 344 */               long startBenchmark = System.currentTimeMillis();
/* 345 */               int videoSize = this.mVideoData.size();
/* 346 */               SLog.m466i("Segment validation: A {} V {}", Integer.valueOf(getCombinedAudioData().size), Integer.valueOf(videoSize));
/* 347 */               if (videoSize == 0) {
/* 348 */                   hasWarning = true;
/* 349 */                   SLog.m455e("Video time is 0");
/* 350 */                   videoTime = 0;
                        } else {
/* 352 */                   videoTime = (int) (((this.mVideoData.get(videoSize - 1).timestamp - this.mVideoData.get(0).timestamp) / 1000) + ((long) msPf));
                        }
/* 354 */               if (audioTimeMs <= 0) {
/* 355 */                   hasWarning = true;
/* 356 */                   validationPasses = false;
/* 357 */                   SLog.m477w("Audio time is 0");
                        }
/* 359 */               if (audioTimeMs < videoTime) {
/* 360 */                   hasWarning = true;
/* 361 */                   SLog.m477w("Audio time is smaller than video time.");
/* 362 */                   validationPasses = false;
                        }
/* 364 */               if (Math.abs(audioTimeMs - videoTime) > 100) {
/* 365 */                   hasWarning = true;
/* 366 */                   SLog.m477w("Track differences too large.");
/* 367 */                   validationPasses = false;
                        }
/* 369 */               boolean wasTimestampsFixed = false;
/* 370 */               if (videoSize > 0) {
/* 371 */                   long lastTimestamp = (-msPf) * 1000;
/* 372 */                   long timestampError = 0;
                            for (VideoData data : this.mVideoData) {
/* 374 */                       SLog.m465i("V {}", Long.valueOf(data.timestamp));
/* 375 */                       long diff = data.timestamp - lastTimestamp;
                                if (diff <= 0) {
/* 377 */                           hasWarning = true;
/* 378 */                           SLog.m456e("Non-increasing video timestamp found: {}", Long.valueOf(diff));
                                }
                                if (diff > usPf) {
/* 381 */                           hasWarning = true;
/* 382 */                           SLog.m478w("Has frames missing: {}.", Long.valueOf(diff));
                                }
/* 384 */                       timestampError += ((long) usPf) - diff;
/* 385 */                       lastTimestamp = data.timestamp;
                            }
/* 387 */                   int missingFrameCount = (int) (timestampError / ((long) usPf));
                            if (timestampError != 0) {
/* 389 */                       if (Math.abs(missingFrameCount) < 2) {
/* 390 */                           SLog.m446d("Video timestamps do eventually correct itself.");
                                } else {
/* 392 */                           validationPasses = false;
/* 393 */                           SLog.m457e("Frames do not match up: {}, missing {} frames.", Long.valueOf(timestampError), Integer.valueOf(missingFrameCount));
                                }
/* 395 */                       if (fixTimestamps) {
/* 396 */                           wasTimestampsFixed = true;
/* 397 */                           correctVideoTimestamps(usPf, missingFrameCount, (audioTimeMs - msPf) * 1000);
                                }
                            }
/* 400 */                   long expectedLastTimestamp = (videoSize - 1) * usPf;
/* 401 */                   SLog.m447d("Timestamp expectation difference: {}.", Long.valueOf((expectedLastTimestamp - lastTimestamp) + this.mVideoData.get(0).timestamp));
                        }
/* 403 */               if (validationPasses) {
/* 404 */                   if (hasWarning) {
/* 405 */                       SLog.m446d("Validation passed with warnings.");
                            } else {
/* 407 */                       SLog.m446d("Validation passed.");
                            }
                        } else {
/* 410 */                   SLog.m457e("Validation failed: A {} V {}.", Integer.valueOf(audioTimeMs), Integer.valueOf(videoTime));
                        }
/* 412 */               SLog.m465i("Validation completed in {}ms", Long.valueOf(System.currentTimeMillis() - startBenchmark));
/* 413 */               if (wasTimestampsFixed) {
/* 414 */                   validateAndFixVideoTimestampsIfNeeded(false);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 418 */       public void setFrameRate(int frameRate) {
/* 419 */           this.mFrameRate = frameRate;
                }

/* 422 */       public int getFrameRate() {
/* 423 */           return this.mFrameRate;
                }

/* 426 */       public int getTrimmedAudioEndUs() {
/* 427 */           return this.mTrimResult.getAudioEndMs() * 1000;
                }

/* 430 */       public void setIsImported(boolean isImported) {
/* 431 */           this.mIsImported = isImported;
                }

/* 434 */       public String getFullDataId() {
/* 435 */           return this.mFullDataId;
                }

/* 438 */       public void setFullDataId(String fullDataId) {
/* 439 */           this.mFullDataId = fullDataId;
                }

/* 444 */       public static class Data {
                    final short[] audio;
                    final byte[] video;

/* 445 */           public Data(short[] audio, byte[] video) {
/* 447 */               this.audio = audio;
/* 448 */               this.video = video;
                    }
                }

/* 452 */       public Data loadDataFromFile(File draftFolder, byte[] videoBuffer, short[] audioBuffer) throws IOException {
/* 453 */           long loadStart = System.currentTimeMillis();
/* 454 */           File folder = getSegmentDataFolder(draftFolder);
/* 455 */           if (!folder.isDirectory()) {
/* 456 */               throw new FileNotFoundException("Segment data folder does not exist.");
                    }
/* 458 */           AudioData data = getCombinedAudioData();
/* 459 */           DataInputStream stream = new DataInputStream(new BufferedInputStream(new FileInputStream(new File(folder, getDataFileName()))));
/* 461 */           if (audioBuffer == null || audioBuffer.length < data.size) {
/* 462 */               audioBuffer = new short[data.size];
                    }
                    for (int i = 0; i < data.size; i++) {
/* 465 */               audioBuffer[i] = stream.readShort();
                    }
/* 467 */           int size = stream.readInt();
/* 469 */           if (videoBuffer == null || videoBuffer.length < size) {
/* 470 */               videoBuffer = new byte[size];
                    }
/* 472 */           if (size > 0) {
/* 473 */               int ret = stream.read(videoBuffer, 0, size);
/* 474 */               if (ret < 0) {
/* 475 */                   throw new IOException("video data missing.");
                        }
                    }
/* 478 */           int count = stream.readInt();
/* 479 */           stream.close();
/* 480 */           SLog.m448d("Load segment from data file took {}ms, with {} video segments.", Long.valueOf(System.currentTimeMillis() - loadStart), Integer.valueOf(count));
/* 482 */           return new Data(audioBuffer, videoBuffer);
                }

/* 485 */       public String saveDataToDiskIfNeeded(File draftFolder, byte[] videoData, AudioArray audioArray, @Nullable RecordSegment originalSegment) throws IOException {
/* 486 */           if (!isDataFileBacked()) {
/* 487 */               long saveStart = System.currentTimeMillis();
/* 488 */               this.mFullDataId = String.valueOf(System.nanoTime());
/* 489 */               AudioData combinedAudio = getCombinedAudioData();
/* 490 */               SLog.m464i("First time trimming this video, create full sized data.");
/* 491 */               File folder = getSegmentDataFolder(draftFolder);
/* 492 */               if (!folder.mkdir() && !folder.isDirectory()) {
/* 493 */                   throw new IOException("Failed to create segment data folder.");
                        }
/* 495 */               DataOutputStream stream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File(folder, getDataFileName()))));
/* 496 */               audioArray.writeInto(stream, combinedAudio.start, combinedAudio.size);
/* 497 */               combinedAudio.start = 0;
/* 498 */               int videoFileSize = 0;
/* 499 */               ArrayList<VideoData> segmentVideo = getVideoData();
/* 500 */               int total = 0;
                        for (VideoData data : segmentVideo) {
/* 502 */                   if (data.size >= 0) {
/* 505 */                       total += data.size;
                            }
                        }
/* 507 */               stream.writeInt(total);
                        for (VideoData data2 : segmentVideo) {
/* 509 */                   if (data2.size >= 0) {
/* 512 */                       int oldStart = data2.start;
/* 513 */                       data2.start = videoFileSize;
/* 514 */                       videoFileSize += data2.size;
/* 515 */                       stream.write(videoData, oldStart, data2.size);
                            }
                        }
/* 517 */               stream.writeInt(segmentVideo.size());
/* 518 */               stream.flush();
/* 519 */               stream.close();
/* 520 */               SLog.m447d("Save segment from data file took {}ms.", Long.valueOf(System.currentTimeMillis() - saveStart));
/* 522 */               if (originalSegment != null && originalSegment != this) {
/* 523 */                   originalSegment.copyFrom(this);
                        }
                    }
/* 526 */           return this.mFullDataId;
                }

/* 530 */       public static ArrayList<RecordSegment> applyEditedChanges(File draftFolder, RecordSession session, ArrayList<RecordSegment> editedSegments) throws IOException {
/* 531 */           return applyEditedChanges(draftFolder, session, session.getSegments(), null, null, editedSegments);
                }

/* 537 */       public static ArrayList<RecordSegment> applyForEditedChanges(File draftFolder, RecordSession session, byte[] destinationVideoBuffer, AudioArray destinationAudioBuffer, ArrayList<RecordSegment> editedSegments) throws IOException {
/* 538 */           return applyEditedChanges(draftFolder, session, null, destinationVideoBuffer, destinationAudioBuffer, editedSegments);
                }

/* 551 */       private static ArrayList<RecordSegment> applyEditedChanges(File draftFolder, RecordSession session, ArrayList<RecordSegment> originalSegments, byte[] destinationVideo, AudioArray destinationAudio, ArrayList<RecordSegment> editedSegments) throws IOException {
                    ByteBuffer videoBuffer;
                    Buffer audioBuffer;
/* 552 */           boolean applyOnSource = destinationVideo == null;
/* 553 */           SLog.m465i("Apply changes, on source: {}.", Boolean.valueOf(applyOnSource));
/* 554 */           ArrayList<RecordSegment> modified = new ArrayList<>();
/* 555 */           ArrayList<RecordSegment> toRemove = new ArrayList<>();
/* 556 */           if (editedSegments != null) {
/* 557 */               byte[] videoData = session.getVideoData();
/* 558 */               AudioArray audioData = session.getAudioData();
/* 560 */               BytePointer videoDataPtr = null;
/* 561 */               BytePointer audioDataPtrByte = null;
/* 562 */               ShortPointer audioDataPtrShort = null;
/* 567 */               if (applyOnSource) {
/* 568 */                   videoDataPtr = new BytePointer(videoData.length);
/* 569 */                   videoBuffer = videoDataPtr.asBuffer();
/* 570 */                   if (session.getVersion().getAudioArrayType() == AudioArrays.AudioArrayType.SHORT) {
/* 571 */                       audioDataPtrShort = new ShortPointer(audioData.getLength());
/* 572 */                       audioBuffer = audioDataPtrShort.asBuffer();
                            } else {
/* 574 */                       audioDataPtrByte = new BytePointer(audioData.getLength());
/* 575 */                       audioBuffer = audioDataPtrByte.asBuffer();
                            }
                            for (int i = 0; i < originalSegments.size(); i++) {
/* 578 */                       originalSegments.get(i).index = i;
                            }
                        } else {
/* 581 */                   videoBuffer = ByteBuffer.wrap(destinationVideo);
/* 582 */                   audioBuffer = destinationAudio.asBuffer();
                        }
/* 584 */               int audioTimeSize = 0;
/* 585 */               int audioTimeStampMs = 0;
/* 586 */               long nextTimeStamp = -1;
                        for (RecordSegment originalSegment : editedSegments) {
/* 589 */                   if (!originalSegment.removed) {
/* 590 */                       RecordSegment targetSegment = applyOnSource ? originalSegment : new RecordSegment(originalSegment);
/* 591 */                       modified.add(targetSegment);
/* 592 */                       SLog.m447d("Adding segment: {}.", Integer.valueOf(originalSegment.index));
/* 593 */                       ArrayList<VideoData> segmentVideo = targetSegment.getVideoData();
/* 594 */                       long lastOriginalTimeStamp = -1;
                                long lastTimeStamp = 0;
/* 596 */                       if (targetSegment.isTrimmed() || targetSegment.isDataFileBacked()) {
/* 597 */                           targetSegment.saveDataToDiskIfNeeded(draftFolder, videoData, audioData, originalSegment);
/* 598 */                           audioTimeStampMs += targetSegment.getDurationMs();
                                } else {
/* 600 */                           AudioData combinedAudio = targetSegment.getCombinedAudioData();
/* 601 */                           CrashUtil.log("Audio buffer putting into buffer of size {} start {} size {}", Integer.valueOf(audioBuffer.capacity()), Integer.valueOf(combinedAudio.start), Integer.valueOf(combinedAudio.size));
/* 602 */                           audioData.putInto(audioBuffer, combinedAudio.start, combinedAudio.size);
/* 603 */                           SLog.m448d("Audio Buffers from {} with size {}.", Integer.valueOf(combinedAudio.start), Integer.valueOf(combinedAudio.size));
/* 604 */                           combinedAudio.start = audioTimeSize;
/* 605 */                           audioTimeSize += combinedAudio.size;
/* 606 */                           audioTimeStampMs = RecordConfigUtils.getTimeStampInMsFromSampleCounted(audioTimeSize);
                                }
                                for (VideoData data : segmentVideo) {
/* 610 */                           if (data.size >= 0) {
                                        if (lastOriginalTimeStamp == -1) {
/* 614 */                                   lastOriginalTimeStamp = data.timestamp;
                                            if (nextTimeStamp == -1) {
/* 616 */                                       nextTimeStamp = 0;
/* 617 */                                       data.timestamp = 0L;
                                            } else {
/* 619 */                                       data.timestamp = nextTimeStamp;
                                            }
                                        } else {
/* 622 */                                   long thisOriginalTimeStamp = data.timestamp;
/* 623 */                                   data.timestamp = (data.timestamp - lastOriginalTimeStamp) + lastTimeStamp;
/* 624 */                                   lastOriginalTimeStamp = thisOriginalTimeStamp;
                                        }
/* 626 */                               SLog.m448d("Modifying timestamp from {} to {}.", Long.valueOf(lastOriginalTimeStamp), Long.valueOf(data.timestamp));
/* 627 */                               lastTimeStamp = data.timestamp;
/* 628 */                               if (!targetSegment.isDataFileBacked()) {
/* 629 */                                   int oldStart = data.start;
/* 630 */                                   data.start = videoBuffer.position();
/* 631 */                                   videoBuffer.put(videoData, oldStart, data.size);
                                        }
                                    }
                                }
/* 634 */                       nextTimeStamp = Math.max(audioTimeStampMs * 1000, (segmentVideo.size() * avutil.AV_TIME_BASE) / targetSegment.mFrameRate) - (avutil.AV_TIME_BASE / targetSegment.mFrameRate);
/* 636 */                       SLog.m447d("Next timestamp: {}", Long.valueOf(nextTimeStamp));
                            } else {
/* 638 */                       toRemove.add(originalSegment);
                            }
                        }
/* 641 */               int aPosition = audioBuffer.position();
/* 642 */               int vPosition = videoBuffer.position();
/* 643 */               SLog.m448d("Buffer limits: {}, {}.", Integer.valueOf(aPosition), Integer.valueOf(vPosition));
/* 644 */               if (applyOnSource) {
/* 645 */                   videoBuffer.position(0);
/* 646 */                   videoBuffer.get(videoData);
/* 647 */                   audioBuffer.position(0);
/* 648 */                   audioData.getFrom(audioBuffer);
/* 649 */                   session.setAudioDataCount(aPosition);
/* 650 */                   session.setVideoDataCount(vPosition);
                        }
/* 652 */               if (videoDataPtr != null) {
/* 653 */                   videoDataPtr.deallocate();
                        }
/* 655 */               if (audioDataPtrByte != null) {
/* 656 */                   audioDataPtrByte.deallocate();
                        }
/* 658 */               if (audioDataPtrShort != null) {
/* 659 */                   audioDataPtrShort.deallocate();
                        }
/* 661 */               editedSegments.removeAll(toRemove);
/* 662 */               if (originalSegments != null) {
/* 663 */                   originalSegments.clear();
/* 664 */                   originalSegments.addAll(editedSegments);
                        }
                    }
                    return applyOnSource ? toRemove : modified;
                }

/* 671 */       public Drawable getDrawableCopy(Context context) {
/* 672 */           if (this.drawable != null) {
/* 673 */               return new BitmapDrawable(context.getResources(), ((BitmapDrawable) this.drawable).getBitmap());
                    }
/* 676 */           BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(), getThumbnailPath());
/* 678 */           this.drawable = new BitmapDrawable(context.getResources(), bitmapDrawable.getBitmap());
/* 681 */           return bitmapDrawable;
                }

/* 684 */       public JSONObject getMetaData(long lastTimestamp) throws JSONException {
/* 685 */           JSONObject obj = new JSONObject();
/* 686 */           obj.put("startTime", lastTimestamp / 1000.0d);
/* 687 */           obj.put("endTime", (((long) getDurationMs()) + lastTimestamp) / 1000.0d);
                    if (this.mRecordingTime > 0) {
/* 689 */               obj.put("recordingTime", this.mRecordingTime);
/* 690 */               obj.put("timezone", this.mTimeZoneId);
                    }
/* 692 */           obj.put("imported", this.mIsImported);
/* 693 */           CameraSetting cs = getCameraSetting();
/* 694 */           if (cs != null) {
/* 695 */               obj.put("frontFacing", cs.frontFacing);
                    }
                    for (String key : this.mImportedMetaData.keySet()) {
/* 698 */               obj.put(key, this.mImportedMetaData.get(key));
                    }
/* 700 */           return obj;
                }

/* 703 */       public int getVideoDataEncodedPercentage() {
/* 704 */           int encoded = 0;
/* 705 */           ArrayList<VideoData> v = getVideoData();
/* 706 */           if (v.size() <= 0) {
                        return 100;
                    }
                    for (VideoData data : v) {
/* 708 */               encoded += data.encoded ? 1 : 0;
                    }
/* 710 */           return (encoded * 100) / v.size();
                }

/* 716 */       public static int getFrameRate(Collection<RecordSegment> segments) {
/* 717 */           int frameRate = Integer.MIN_VALUE;
                    for (RecordSegment segment : segments) {
/* 719 */               if (frameRate < segment.mFrameRate) {
/* 720 */                   frameRate = segment.mFrameRate;
                        }
                    }
/* 723 */           if (frameRate == Integer.MIN_VALUE) {
/* 724 */               return 30;
                    }
/* 726 */           return frameRate;
                }
            }
