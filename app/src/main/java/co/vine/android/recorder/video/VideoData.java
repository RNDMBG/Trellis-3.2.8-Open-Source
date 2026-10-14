            package co.vine.android.recorder.video;

            import co.vine.android.recorder.RecordSegment;
            import java.io.Externalizable;
            import java.io.IOException;
            import java.io.ObjectInput;
            import java.io.ObjectOutput;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 30 */    public class VideoData implements Externalizable {
                private static final long sVersion = -3;
                private static final long serialVersionUID = 8590112321194730309L;
                public byte[] data;
                public boolean encoded;
                public boolean keyFrame;
                public RecordSegment segment;
                public int size;
                public int start;
                public long timestamp;

/* 31 */        public VideoData() {
                }

/* 34 */        public VideoData(long timestamp, byte[] data) {
/* 36 */            this.timestamp = timestamp;
/* 37 */            this.data = data;
/* 38 */            this.start = -1;
/* 39 */            this.size = -1;
/* 40 */            this.encoded = false;
                }

/* 46 */        public VideoData(VideoData source) {
/* 48 */            this.timestamp = source.timestamp;
/* 49 */            this.data = source.data;
/* 50 */            this.size = source.size;
/* 51 */            this.start = source.start;
/* 52 */            this.keyFrame = source.keyFrame;
/* 53 */            this.encoded = source.encoded;
                }

/* 56 */        public void setSegment(RecordSegment segment) {
/* 57 */            this.segment = segment;
                }

                @Override // java.io.Externalizable
/* 60 */        public void writeExternal(ObjectOutput out) throws IOException {
/* 61 */            out.writeLong(-3L);
/* 62 */            out.writeBoolean(this.keyFrame);
/* 63 */            out.writeLong(this.timestamp);
/* 64 */            out.writeInt(this.start);
/* 65 */            out.writeInt(this.size);
/* 66 */            out.writeBoolean(this.encoded);
                }

                @Override // java.io.Externalizable
/* 70 */        public void readExternal(ObjectInput stream) throws IOException, ClassNotFoundException {
/* 71 */            long possibleVersion = stream.readLong();
                    if (possibleVersion >= 0) {
/* 73 */                this.timestamp = -3L;
                    } else {
/* 75 */                this.keyFrame = stream.readBoolean();
/* 76 */                this.timestamp = stream.readLong();
                    }
/* 78 */            this.start = stream.readInt();
/* 79 */            this.size = stream.readInt();
                    if (possibleVersion < -2) {
/* 81 */                this.encoded = stream.readBoolean();
                    } else {
/* 83 */                this.encoded = true;
                    }
                }
            }
