            package co.vine.android.recorder;

            import java.io.Externalizable;
            import java.io.IOException;
            import java.io.ObjectInput;
            import java.io.ObjectOutput;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 16 */    public class RecordSessionMeta implements Externalizable {
                private static final long serialVersionUID = 3412330863787653276L;
                private long mLastModified;
                private int mProgress;

/* 17 */        public RecordSessionMeta() {
                }

/* 21 */        public RecordSessionMeta(int progress) {
/* 23 */            this.mLastModified = System.currentTimeMillis();
/* 24 */            this.mProgress = progress;
                }

/* 27 */        public long getLastModified() {
/* 28 */            return this.mLastModified;
                }

/* 31 */        public int getProgress() {
/* 32 */            return this.mProgress;
                }

/* 35 */        public void setProgress(int progress) {
/* 36 */            this.mProgress = progress;
                }

                @Override // java.io.Externalizable
/* 40 */        public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
/* 41 */            this.mProgress = input.readInt();
/* 42 */            this.mLastModified = input.readLong();
                }

                @Override // java.io.Externalizable
/* 46 */        public void writeExternal(ObjectOutput output) throws IOException {
/* 47 */            output.writeInt(this.mProgress);
/* 48 */            output.writeLong(this.mLastModified);
                }
            }
