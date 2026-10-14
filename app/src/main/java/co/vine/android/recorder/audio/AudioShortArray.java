            package co.vine.android.recorder.audio;

            import android.media.AudioRecord;
            import java.io.DataOutputStream;
            import java.io.IOException;
            import java.nio.Buffer;
            import java.nio.ShortBuffer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 10 */    public class AudioShortArray implements AudioArray<short[], ShortBuffer> {
                private final short[] mData;
                private final int mLength;





/* 16 */        protected AudioShortArray(short[] audioData) {
/* 18 */            this.mData = audioData;
/* 19 */            this.mLength = audioData.length;
                }

/* 22 */        protected AudioShortArray(int length) {
/* 23 */            this(new short[length]);
                }

                @Override // co.vine.android.recorder.audio.AudioArray
/* 27 */        public short[] getData() {
/* 28 */            return this.mData;
                }

                @Override // co.vine.android.recorder.audio.AudioArray
/* 32 */        public int getLength() {
/* 33 */            return this.mLength;
                }

                @Override // co.vine.android.recorder.audio.AudioArray
/* 37 */        public ShortBuffer asBuffer() {
/* 38 */            return ShortBuffer.wrap(this.mData);
                }

/* 42 */        public void getFrom(ShortBuffer audioBuffer) {
/* 43 */            audioBuffer.get(this.mData);
                }

/* 47 */        public void putInto(ShortBuffer audioBuffer, int start, int size) {
/* 48 */            audioBuffer.put(this.mData, start, size);
                }

                @Override // co.vine.android.recorder.audio.AudioArray
/* 52 */        public int readFrom(AudioRecord audioRecord, int length) {
/* 53 */            return audioRecord.read(this.mData, 0, length);
                }

                @Override // co.vine.android.recorder.audio.AudioArray
/* 57 */        public void putInto(AudioArray<short[], ?> dest, int offset, int count) {
/* 58 */            ShortBuffer.wrap(this.mData, 0, count).get(dest.getData(), offset, count);
                }

                @Override // co.vine.android.recorder.audio.AudioArray
/* 62 */        public void writeInto(DataOutputStream stream, int start, int count) throws IOException {
/* 63 */            int end = count + start;
                    for (int i = start; i < end; i++) {
/* 65 */                stream.writeShort(this.mData[i]);
                    }
                }
            }
