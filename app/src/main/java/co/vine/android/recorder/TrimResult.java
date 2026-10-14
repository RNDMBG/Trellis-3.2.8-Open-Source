            package co.vine.android.recorder;

            import co.vine.android.recorder.video.VideoData;
            import java.io.Externalizable;
            import java.io.IOException;
            import java.io.ObjectInput;
            import java.io.ObjectOutput;
            import java.util.ArrayList;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 18 */    public class TrimResult implements Externalizable {
                public int audioEndCount;
                public int audioStartOffset;
                public int videoEndIndex;
                public int videoStartIndex;

/* 19 */        public TrimResult() {
                }

/* 23 */        public TrimResult(int videoStartIndex, int videoEndIndex, int audioStartOffset, int audioSize) {
/* 25 */            this.videoStartIndex = videoStartIndex;
/* 26 */            this.videoEndIndex = videoEndIndex;
/* 27 */            this.audioStartOffset = audioStartOffset;
/* 28 */            this.audioEndCount = audioSize;
                }

/* 31 */        public int getAudioDuration() {
/* 32 */            return getAudioEndMs() - getAudioStartMs();
                }

/* 35 */        public int getTrimmedStartMs(ArrayList<VideoData> videoData) {
/* 36 */            return Math.min(getAudioStartMs(), getVideoStartUs(videoData) / 1000);
                }

/* 39 */        public int getAudioStartMs() {
/* 40 */            return (int) (((double) this.audioStartOffset) / 44.1d);
                }

/* 43 */        public int getAudioEndMs() {
/* 44 */            return (int) (((double) this.audioEndCount) / 44.1d);
                }

/* 47 */        public int getVideoStartUs(ArrayList<VideoData> videoData) {
/* 48 */            return (int) videoData.get(this.videoStartIndex).timestamp;
                }

/* 51 */        public int getVideoEndUs(ArrayList<VideoData> videoData) {
/* 52 */            return (int) videoData.get(this.videoEndIndex - 1).timestamp;
                }

                @Override // java.io.Externalizable
/* 56 */        public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
/* 57 */            this.videoStartIndex = input.readInt();
/* 58 */            this.videoEndIndex = input.readInt();
/* 59 */            this.audioStartOffset = input.readInt();
/* 60 */            this.audioEndCount = input.readInt();
                }

                @Override // java.io.Externalizable
/* 64 */        public void writeExternal(ObjectOutput output) throws IOException {
/* 65 */            output.writeInt(this.videoStartIndex);
/* 66 */            output.writeInt(this.videoEndIndex);
/* 67 */            output.writeInt(this.audioStartOffset);
/* 68 */            output.writeInt(this.audioEndCount);
                }
            }
