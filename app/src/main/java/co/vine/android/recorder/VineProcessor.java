            package co.vine.android.recorder;

            import co.vine.android.VineLoggingException;
            import java.util.HashMap;
            import java.util.List;
            
            
    public class VineProcessor {
                private final Processor mProcessor;

        public VineProcessor(String nativeDir) throws VineLoggingException {
                    try {
                this.mProcessor = new Processor(nativeDir);
                    } catch (UnsatisfiedLinkError e) {
                throw new VineLoggingException(e);
                    }
                }

        public void init() {
            this.mProcessor.process("ffmpeg", "-h");
                }

        public int setMetadata(String input, String output, HashMap<String, String> metaData) {
            return this.mProcessor.newCommand().addInputPath(input).setCopy().setMetaData(metaData).enableOverwrite().processToOutput(output);
                }

        public int toH264(String input, Processor.Preset preset, String output, HashMap<String, String> metaData) {
            return this.mProcessor.newCommand().addInputPath(input).useX264().setPreset(preset).setAudioCopy().setMetaData(metaData).enableOverwrite().processToOutput(output);
                }

        public int combineVideoAndAudio(String video, String audio, String output) {
            return this.mProcessor.newCommand().addInputPath(video).addInputPath(audio).setMap("0:0").setMap("1:0").setCopy().enableOverwrite().processToOutput(output);
                }

        public int cropTo480andRotate(String input, String output) {
            return this.mProcessor.newCommand().addInputPath(input).setAudioCopy().filterCrop(480, 480).filterRotation(Processor.Rotation.NO_FLIP_90_CLOCKWISE).enableOverwrite().processToOutput(output);
                }

        public int splitTs(String input, RelativeTime time, String output, int frameRate) {
            return this.mProcessor.newCommand().addInputPath(input).setStart(time.start).setFrames(time.getDuration(frameRate), frameRate).setCopy().setBsfV("h264_mp4toannexb").setFormat("mpegts").enableOverwrite().processToOutput(output);
                }

        public int toTs(String input, String output) {
            return this.mProcessor.newCommand().addInputPath(input).setCopy().setBsfV("h264_mp4toannexb").setFormat("mpegts").enableOverwrite().processToOutput(output);
                }

        public int combineVideoAndAudio(String video, String audio, String output, HashMap<String, String> metaData) {
            return this.mProcessor.newCommand().addInputPath(video).addInputPath(audio).setMap("0:0").setMap("1:0").setCopy().setMetaData(metaData).enableOverwrite().processToOutput(output);
                }

       public int concat(List<String> inputs, String output, HashMap<String, String> metaData) {
           StringBuilder files = new StringBuilder("concat:");
           int n = inputs.size() - 1;
                    for (int i = 0; i < n; i++) {
               files.append(inputs.get(i));
               files.append("|");
                    }
           files.append(inputs.get(n));
           return this.mProcessor.newCommand().addInputPath(files.toString()).setCopy().setBsfA("aac_adtstoasc").enableOverwrite().setMetaData(metaData).processToOutput(output);
                }

       public int split(String input, RelativeTime time, String output, int frameRate) {
           return this.mProcessor.newCommand().addInputPath(input).setStart(time.start).setFrames(time.getDuration(frameRate), frameRate).setVideoCopy().setAudioCopy().enableOverwrite().processToOutput(output);
                }

       public int cropTo480(String input, String output) {
           return this.mProcessor.newCommand().addInputPath(input).setAudioCopy().filterCrop(480, 480).enableOverwrite().processToOutput(output);
                }

       public int rotate(String input, String output) {
           return this.mProcessor.newCommand().addInputPath(input).setAudioCopy().filterRotation(Processor.Rotation.NO_FLIP_90_CLOCKWISE).enableOverwrite().processToOutput(output);
                }

       public int getImageForFrame(String input, long time, int sizeX, int sizeY, String output) {
           return this.mProcessor.newCommand().setStart(time).addInputPath(input).setVFrames(1).setFormat("image2").enableOverwrite().processToOutput(output);
                }
            }
