            package co.vine.android.recorder.audio;

            import android.media.AudioRecord;
            import java.io.DataOutputStream;
            import java.io.IOException;
            import java.nio.Buffer;
            import java.nio.ByteBuffer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 10 */    public class AudioByteArray implements AudioArray<byte[], ByteBuffer> {
                private final byte[] mData;
                private final int mLength;





/* 16 */        public AudioByteArray(byte[] data) {
/* 18 */            this.mData = data;
/* 19 */            this.mLength = data.length / 2;
                }

/* 22 */        public AudioByteArray(int length) {
/* 23 */            this(new byte[length * 2]);
                }

                @Override // co.vine.android.recorder.audio.AudioArray
/* 27 */        public byte[] getData() {
/* 28 */            return this.mData;
                }

                @Override // co.vine.android.recorder.audio.AudioArray
/* 32 */        public int getLength() {
/* 33 */            return this.mLength;
                }

                @Override // co.vine.android.recorder.audio.AudioArray
/* 37 */        public ByteBuffer asBuffer() {
/* 38 */            return ByteBuffer.wrap(this.mData);
                }

/* 42 */        public void getFrom(ByteBuffer audioBuffer) {
/* 43 */            audioBuffer.get(this.mData);
                }

/* 47 */        public void putInto(ByteBuffer audioBuffer, int start, int size) {
/* 48 */            audioBuffer.put(this.mData, start, size);
                }

                @Override // co.vine.android.recorder.audio.AudioArray
/* 52 */        public int readFrom(AudioRecord audioRecord, int length) {
/* 53 */            return audioRecord.read(this.mData, 0, length);
                }

                @Override // co.vine.android.recorder.audio.AudioArray
/* 57 */        public void putInto(AudioArray<byte[], ?> dest, int offset, int count) {
/* 58 */            ByteBuffer.wrap(this.mData, 0, count).get(dest.getData(), offset, count);
                }

                @Override // co.vine.android.recorder.audio.AudioArray
/* 62 */        public void writeInto(DataOutputStream stream, int start, int count) throws IOException {
/* 63 */            stream.write(this.mData, start, count);
                }
            }
