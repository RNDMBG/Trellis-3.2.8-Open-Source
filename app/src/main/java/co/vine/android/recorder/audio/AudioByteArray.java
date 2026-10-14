            package co.vine.android.recorder.audio;

            import android.media.AudioRecord;
            import java.io.DataOutputStream;
            import java.io.IOException;
            import java.nio.Buffer;
            import java.nio.ByteBuffer;
            
            
    public class AudioByteArray implements AudioArray<byte[], ByteBuffer> {
                private final byte[] mData;
                private final int mLength;





        public AudioByteArray(byte[] data) {
            this.mData = data;
            this.mLength = data.length / 2;
                }

        public AudioByteArray(int length) {
            this(new byte[length * 2]);
                }

                @Override 
        public byte[] getData() {
            return this.mData;
                }

                @Override 
        public int getLength() {
            return this.mLength;
                }

                @Override 
        public ByteBuffer asBuffer() {
            return ByteBuffer.wrap(this.mData);
                }

        public void getFrom(ByteBuffer audioBuffer) {
            audioBuffer.get(this.mData);
                }

        public void putInto(ByteBuffer audioBuffer, int start, int size) {
            audioBuffer.put(this.mData, start, size);
                }

                @Override 
        public int readFrom(AudioRecord audioRecord, int length) {
            return audioRecord.read(this.mData, 0, length);
                }

                @Override 
        public void putInto(AudioArray<byte[], ?> dest, int offset, int count) {
            ByteBuffer.wrap(this.mData, 0, count).get(dest.getData(), offset, count);
                }

                @Override 
        public void writeInto(DataOutputStream stream, int start, int count) throws IOException {
            stream.write(this.mData, start, count);
                }
            }
