            package co.vine.android.recorder.audio;

            import java.io.Externalizable;
            import java.io.IOException;
            import java.io.ObjectInput;
            import java.io.ObjectOutput;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 20 */    public class AudioData implements Externalizable {
                static final long serialVersionUID = -210670612808274402L;
                public int size;
                public int start;

/* 21 */        public AudioData() {
                }

/* 24 */        public AudioData(int start, int size) {
/* 26 */            this.start = start;
/* 27 */            this.size = size;
                }

/* 32 */        public AudioData(AudioData source) {
/* 34 */            this.start = source.start;
/* 35 */            this.size = source.size;
                }

                @Override // java.io.Externalizable
/* 39 */        public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
/* 40 */            this.start = input.readInt();
/* 41 */            this.size = input.readInt();
                }

                @Override // java.io.Externalizable
/* 45 */        public void writeExternal(ObjectOutput output) throws IOException {
/* 46 */            output.writeInt(this.start);
/* 47 */            output.writeInt(this.size);
                }
            }
