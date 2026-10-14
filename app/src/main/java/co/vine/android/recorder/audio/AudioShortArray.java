            package co.vine.android.recorder.audio;

            import android.media.AudioRecord;
            import java.io.DataOutputStream;
            import java.io.IOException;
            import java.nio.Buffer;
            import java.nio.ShortBuffer;
            
            
    public class AudioShortArray implements AudioArray<short[], ShortBuffer> {
                private final short[] mData;
                private final int mLength;





        protected AudioShortArray(short[] audioData) {
            this.mData = audioData;
            this.mLength = audioData.length;
                }

        protected AudioShortArray(int length) {
            this(new short[length]);
                }

                @Override 
        public short[] getData() {
            return this.mData;
                }

                @Override 
        public int getLength() {
            return this.mLength;
                }

                @Override 
        public ShortBuffer asBuffer() {
            return ShortBuffer.wrap(this.mData);
                }

        public void getFrom(ShortBuffer audioBuffer) {
            audioBuffer.get(this.mData);
                }

        public void putInto(ShortBuffer audioBuffer, int start, int size) {
            audioBuffer.put(this.mData, start, size);
                }

                @Override 
        public int readFrom(AudioRecord audioRecord, int length) {
            return audioRecord.read(this.mData, 0, length);
                }

                @Override 
        public void putInto(AudioArray<short[], ?> dest, int offset, int count) {
            ShortBuffer.wrap(this.mData, 0, count).get(dest.getData(), offset, count);
                }

                @Override 
        public void writeInto(DataOutputStream stream, int start, int count) throws IOException {
            int end = count + start;
                    for (int i = start; i < end; i++) {
                stream.writeShort(this.mData[i]);
                    }
                }
            }
