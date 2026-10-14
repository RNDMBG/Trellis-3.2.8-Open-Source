            package co.vine.android.recorder;

            import co.vine.android.VineLoggingException;
            import java.util.HashMap;
            import java.util.List;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 12 */    public class VineProcessor {
                private final Processor mProcessor;

/* 13 */        public VineProcessor(String nativeDir) throws VineLoggingException {
                    try {
/* 16 */                this.mProcessor = new Processor(nativeDir);
                    } catch (UnsatisfiedLinkError e) {
/* 18 */                throw new VineLoggingException(e);
                    }
                }

/* 22 */        public void init() {
/* 23 */            this.mProcessor.process("ffmpeg", "-h");
                }

/* 26 */        public int setMetadata(String input, String output, HashMap<String, String> metaData) {
/* 27 */            return this.mProcessor.newCommand().addInputPath(input).setCopy().setMetaData(metaData).enableOverwrite().processToOutput(output);
                }

/* 35 */        public int toH264(String input, Processor.Preset preset, String output, HashMap<String, String> metaData) {
/* 36 */            return this.mProcessor.newCommand().addInputPath(input).useX264().setPreset(preset).setAudioCopy().setMetaData(metaData).enableOverwrite().processToOutput(output);
                }

/* 46 */        public int combineVideoAndAudio(String video, String audio, String output) {
/* 47 */            return this.mProcessor.newCommand().addInputPath(video).addInputPath(audio).setMap("0:0").setMap("1:0").setCopy().enableOverwrite().processToOutput(output);
                }

/* 57 */        public int cropTo480andRotate(String input, String output) {
/* 58 */            return this.mProcessor.newCommand().addInputPath(input).setAudioCopy().filterCrop(480, 480).filterRotation(Processor.Rotation.NO_FLIP_90_CLOCKWISE).enableOverwrite().processToOutput(output);
                }

/* 67 */        public int splitTs(String input, RelativeTime time, String output, int frameRate) {
/* 68 */            return this.mProcessor.newCommand().addInputPath(input).setStart(time.start).setFrames(time.getDuration(frameRate), frameRate).setCopy().setBsfV("h264_mp4toannexb").setFormat("mpegts").enableOverwrite().processToOutput(output);
                }

/* 79 */        public int toTs(String input, String output) {
/* 80 */            return this.mProcessor.newCommand().addInputPath(input).setCopy().setBsfV("h264_mp4toannexb").setFormat("mpegts").enableOverwrite().processToOutput(output);
                }

/* 89 */        public int combineVideoAndAudio(String video, String audio, String output, HashMap<String, String> metaData) {
/* 90 */            return this.mProcessor.newCommand().addInputPath(video).addInputPath(audio).setMap("0:0").setMap("1:0").setCopy().setMetaData(metaData).enableOverwrite().processToOutput(output);
                }

/* 101 */       public int concat(List<String> inputs, String output, HashMap<String, String> metaData) {
/* 102 */           StringBuilder files = new StringBuilder("concat:");
/* 103 */           int n = inputs.size() - 1;
                    for (int i = 0; i < n; i++) {
/* 105 */               files.append(inputs.get(i));
/* 106 */               files.append("|");
                    }
/* 108 */           files.append(inputs.get(n));
/* 109 */           return this.mProcessor.newCommand().addInputPath(files.toString()).setCopy().setBsfA("aac_adtstoasc").enableOverwrite().setMetaData(metaData).processToOutput(output);
                }

/* 116 */       public int split(String input, RelativeTime time, String output, int frameRate) {
/* 117 */           return this.mProcessor.newCommand().addInputPath(input).setStart(time.start).setFrames(time.getDuration(frameRate), frameRate).setVideoCopy().setAudioCopy().enableOverwrite().processToOutput(output);
                }

/* 127 */       public int cropTo480(String input, String output) {
/* 128 */           return this.mProcessor.newCommand().addInputPath(input).setAudioCopy().filterCrop(480, 480).enableOverwrite().processToOutput(output);
                }

/* 136 */       public int rotate(String input, String output) {
/* 137 */           return this.mProcessor.newCommand().addInputPath(input).setAudioCopy().filterRotation(Processor.Rotation.NO_FLIP_90_CLOCKWISE).enableOverwrite().processToOutput(output);
                }

/* 145 */       public int getImageForFrame(String input, long time, int sizeX, int sizeY, String output) {
/* 146 */           return this.mProcessor.newCommand().setStart(time).addInputPath(input).setVFrames(1).setFormat("image2").enableOverwrite().processToOutput(output);
                }
            }
