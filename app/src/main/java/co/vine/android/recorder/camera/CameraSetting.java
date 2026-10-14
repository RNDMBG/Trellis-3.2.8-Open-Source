            package co.vine.android.recorder.camera;

            import java.io.Externalizable;
            import java.io.IOException;
            import java.io.ObjectInput;
            import java.io.ObjectOutput;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 25 */    public class CameraSetting implements Externalizable {
                private static final long serialVersionUID = 8590112321194730309L;
                public float backFacingAspectRatio;
                public int degrees;
                public int fps;
                public boolean frontFacing;
                public float frontFacingAspectRatio;
                public int imageFormat;
                public int originalFrameRate;
                public int originalH;
                public int originalW;
                public int tpf;

/* 26 */        public CameraSetting() {
                }

/* 30 */        public CameraSetting(int originalW, int originalH, int degrees, int fps, int imageFormat, boolean frontFacing, float frontFacingAspectRatio, float backFacingAspectRatio) {
/* 32 */            this.originalW = originalW;
/* 33 */            this.originalH = originalH;
/* 34 */            this.degrees = degrees;
/* 35 */            this.fps = fps;
/* 36 */            this.originalFrameRate = fps;
/* 37 */            this.tpf = 1000 / fps;
/* 38 */            this.imageFormat = imageFormat;
/* 39 */            this.frontFacing = frontFacing;
/* 40 */            this.frontFacingAspectRatio = frontFacingAspectRatio;
/* 41 */            this.backFacingAspectRatio = backFacingAspectRatio;
                }

/* 43 */        public CameraSetting(CameraSetting setting) {
/* 45 */            this.originalW = setting.originalW;
/* 46 */            this.originalH = setting.originalH;
/* 47 */            this.degrees = setting.degrees;
/* 48 */            this.fps = setting.fps;
/* 49 */            this.originalFrameRate = setting.originalFrameRate;
/* 50 */            this.tpf = setting.tpf;
/* 51 */            this.imageFormat = setting.imageFormat;
/* 52 */            this.frontFacing = setting.frontFacing;
/* 53 */            this.frontFacingAspectRatio = setting.frontFacingAspectRatio;
/* 54 */            this.backFacingAspectRatio = setting.backFacingAspectRatio;
                }

                @Override // java.io.Externalizable
/* 58 */        public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
/* 59 */            this.originalW = input.readInt();
/* 60 */            this.originalH = input.readInt();
/* 61 */            this.degrees = input.readInt();
/* 62 */            this.fps = input.readInt();
/* 63 */            this.tpf = input.readInt();
/* 64 */            this.imageFormat = input.readInt();
/* 65 */            this.frontFacing = input.readBoolean();
/* 66 */            this.frontFacingAspectRatio = input.readFloat();
/* 67 */            this.backFacingAspectRatio = input.readFloat();
/* 68 */            this.originalFrameRate = input.readInt();
                }

                @Override // java.io.Externalizable
/* 72 */        public void writeExternal(ObjectOutput output) throws IOException {
/* 73 */            output.writeInt(this.originalW);
/* 74 */            output.writeInt(this.originalH);
/* 75 */            output.writeInt(this.degrees);
/* 76 */            output.writeInt(this.fps);
/* 77 */            output.writeInt(this.tpf);
/* 78 */            output.writeInt(this.imageFormat);
/* 79 */            output.writeBoolean(this.frontFacing);
/* 80 */            output.writeFloat(this.frontFacingAspectRatio);
/* 81 */            output.writeFloat(this.backFacingAspectRatio);
/* 82 */            output.writeInt(this.originalFrameRate);
                }
            }
