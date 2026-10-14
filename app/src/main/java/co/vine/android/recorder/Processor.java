            package co.vine.android.recorder;

            import com.edisonwang.android.slog.SLog;
            import java.io.File;
            import java.io.FileFilter;
            import java.util.ArrayList;
            import java.util.HashMap;
            import java.util.Locale;
            import java.util.regex.Pattern;
            
            
    public final class Processor {
                private ArrayList<String> mCommand;
                private ArrayList<String> mFilters;
                private final FFmpegInvoke mInvoker;
                private HashMap<String, String> mMetaData;
                private final int mNumCores = getNumCores();

                public enum Preset {
                    ULTRAFAST,
                    SUPERFAST,
                    VERYFAST,
                    FASTER,
                    FAST,
                    MEDIUM,
                    SLOW,
                    SLOWER
                }

                public enum Rotation {
                    VERTICAL_FLIP_AND_90_COUNTER_CLOCKWISE,
                    NO_FLIP_90_CLOCKWISE,
                    NO_FLIP_90_COUNTER_CLOCKWISE,
                    VERTICAL_FLIP_AND_90_CLOCKWISE
                }

                static {
                    try {
                System.loadLibrary("ffmpeginvoke");
                    } catch (UnsatisfiedLinkError e) {
                        SLog.m459e("FFmpeg native library is unavailable; recording/transcoding is disabled on this ABI.", e);
                    }
                }

        public Processor(String nativeDir) {
            this.mInvoker = new FFmpegInvoke(nativeDir);
                }

        public int process(String... args) {
            return this.mInvoker.run(args);
                }

        public Processor newCommand() {
            this.mMetaData = new HashMap<>();
            this.mFilters = new ArrayList<>();
            this.mCommand = new ArrayList<>();
            this.mCommand.add("ffmpeg");
            return this;
                }

        public Processor addMetaData(String key, String value) {
            this.mMetaData.put(key, value);
            return this;
                }

        public Processor addInputPath(String path) {
            this.mCommand.add("-i");
            this.mCommand.add(path);
            return this;
                }

        public Processor setSize(int sizeX, int sizeY) {
            this.mCommand.add("-s");
            this.mCommand.add("" + sizeX + "x" + sizeY);
            return this;
                }

        public Processor useMjpeg() {
            this.mCommand.add("-vcodec");
            this.mCommand.add("mjpeg");
            return this;
                }

        public Processor useX264() {
            this.mCommand.add("-vcodec");
            this.mCommand.add("libx264");
            return this;
                }

        public Processor setCopy() {
            this.mCommand.add("-c");
            this.mCommand.add("copy");
            return this;
                }

        public Processor setBsfV(String format) {
            this.mCommand.add("-bsf:v");
            this.mCommand.add(format);
            return this;
                }

        public Processor setBsfA(String format) {
            this.mCommand.add("-bsf:a");
            this.mCommand.add(format);
            return this;
                }

        public Processor setMap(String value) {
            this.mCommand.add("-map");
            this.mCommand.add(value);
            return this;
                }

        public Processor enableShortest() {
            this.mCommand.add("-shortest");
           return this;
                }

       public Processor setFormat(String format) {
           this.mCommand.add("-f");
           this.mCommand.add(format);
           return this;
                }

       public Processor setVideoCopy() {
           this.mCommand.add("-vcodec");
           this.mCommand.add("copy");
           return this;
                }

       public Processor setAudioCopy() {
           this.mCommand.add("-acodec");
           this.mCommand.add("copy");
           return this;
                }

       public Processor setPreset(Preset preset) {
           this.mCommand.add("-preset");
           this.mCommand.add(preset.name().toLowerCase(Locale.US));
           return this;
                }

       public Processor setStart(long start) {
           this.mCommand.add("-ss");
           this.mCommand.add(String.valueOf(start / 1000.0d));
           return this;
                }

       public Processor setVFrames(int vFrames) {
           this.mCommand.add("-vframes");
           this.mCommand.add(String.valueOf(vFrames));
           return this;
                }

       public Processor setTotalDuration(long duration) {
           this.mCommand.add("-t");
           this.mCommand.add(String.valueOf(duration / 1000.0d));
           return this;
                }

       public Processor setFrames(long duration, int frameRate) {
           this.mCommand.add("-vframes");
           this.mCommand.add(String.valueOf((int) ((duration / 1000.0d) * ((double) frameRate))));
           return this;
                }

       public Processor enableOverwrite() {
           this.mCommand.add("-y");
           return this;
                }

       public Processor setShortest() {
           this.mCommand.add("-shortest");
           return this;
                }

       public Processor filterCrop(int width, int height) {
           this.mFilters.add("crop=" + width + ":" + height);
           return this;
                }

       public Processor filterRotation(Rotation rotation) {
           this.mFilters.add("transpose=" + rotation.ordinal());
           return this;
                }

       public Processor setMetaData(HashMap<String, String> mMetaData) {
           this.mMetaData = mMetaData;
           return this;
                }

       public int processToOutput(String output) {
           if (this.mFilters.size() > 0) {
               this.mCommand.add("-vf");
               StringBuilder filter = new StringBuilder();
                        for (String args : this.mFilters) {
                   filter.append(args);
                   filter.append(",");
                        }
               String filterArg = filter.toString();
               this.mCommand.add(filterArg.substring(0, filterArg.length() - 1));
                    }
                    for (String key : this.mMetaData.keySet()) {
               this.mCommand.add("-metadata");
               this.mCommand.add(key + "=\"" + this.mMetaData.get(key) + "\"");
                    }
           if (this.mNumCores > 1) {
                    }
           this.mCommand.add(output);
                    for (String args2 : this.mCommand) {
               SLog.m447d("Add arg '{}'", args2);
                    }
           return process((String[]) this.mCommand.toArray(new String[this.mCommand.size()]));
                }

                
       static class C1CpuFilter implements FileFilter {
           C1CpuFilter() {
                    }

                    @Override 
           public boolean accept(File pathname) {
               return Pattern.matches("cpu[0-9]", pathname.getName());
                    }
                }

       private static int getNumCores() {
                    try {
               File dir = new File("/sys/devices/system/cpu/");
               File[] files = dir.listFiles(new C1CpuFilter());
               return files.length;
                    } catch (Exception e) {
               return 1;
                    }
                }
            }
