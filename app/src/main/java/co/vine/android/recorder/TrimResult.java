            package co.vine.android.recorder;

            import co.vine.android.recorder.video.VideoData;
            import java.io.Externalizable;
            import java.io.IOException;
            import java.io.ObjectInput;
            import java.io.ObjectOutput;
            import java.util.ArrayList;
            
            
    public class TrimResult implements Externalizable {
                public int audioEndCount;
                public int audioStartOffset;
                public int videoEndIndex;
                public int videoStartIndex;

        public TrimResult() {
                }

        public TrimResult(int videoStartIndex, int videoEndIndex, int audioStartOffset, int audioSize) {
            this.videoStartIndex = videoStartIndex;
            this.videoEndIndex = videoEndIndex;
            this.audioStartOffset = audioStartOffset;
            this.audioEndCount = audioSize;
                }

        public int getAudioDuration() {
            return getAudioEndMs() - getAudioStartMs();
                }

        public int getTrimmedStartMs(ArrayList<VideoData> videoData) {
            return Math.min(getAudioStartMs(), getVideoStartUs(videoData) / 1000);
                }

        public int getAudioStartMs() {
            return (int) (((double) this.audioStartOffset) / 44.1d);
                }

        public int getAudioEndMs() {
            return (int) (((double) this.audioEndCount) / 44.1d);
                }

        public int getVideoStartUs(ArrayList<VideoData> videoData) {
            return (int) videoData.get(this.videoStartIndex).timestamp;
                }

        public int getVideoEndUs(ArrayList<VideoData> videoData) {
            return (int) videoData.get(this.videoEndIndex - 1).timestamp;
                }

                @Override 
        public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
            this.videoStartIndex = input.readInt();
            this.videoEndIndex = input.readInt();
            this.audioStartOffset = input.readInt();
            this.audioEndCount = input.readInt();
                }

                @Override 
        public void writeExternal(ObjectOutput output) throws IOException {
            output.writeInt(this.videoStartIndex);
            output.writeInt(this.videoEndIndex);
            output.writeInt(this.audioStartOffset);
            output.writeInt(this.audioEndCount);
                }
            }
