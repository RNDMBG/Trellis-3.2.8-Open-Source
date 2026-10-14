            package co.vine.android.recorder;

            import com.edisonwang.android.slog.SLog;
            import java.io.File;
            import java.io.FileFilter;
            import java.util.ArrayList;
            import java.util.HashMap;
            import java.util.Locale;
            import java.util.regex.Pattern;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 17 */    public final class Processor {
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
/* 19 */                System.loadLibrary("ffmpeginvoke");
                    } catch (UnsatisfiedLinkError e) {
                        SLog.m459e("FFmpeg native library is unavailable; recording/transcoding is disabled on this ABI.", e);
                    }
                }

/* 27 */        public Processor(String nativeDir) {
/* 29 */            this.mInvoker = new FFmpegInvoke(nativeDir);
                }

/* 33 */        public int process(String... args) {
/* 34 */            return this.mInvoker.run(args);
                }

/* 37 */        public Processor newCommand() {
/* 38 */            this.mMetaData = new HashMap<>();
/* 39 */            this.mFilters = new ArrayList<>();
/* 40 */            this.mCommand = new ArrayList<>();
/* 41 */            this.mCommand.add("ffmpeg");
/* 42 */            return this;
                }

/* 45 */        public Processor addMetaData(String key, String value) {
/* 46 */            this.mMetaData.put(key, value);
/* 47 */            return this;
                }

/* 50 */        public Processor addInputPath(String path) {
/* 51 */            this.mCommand.add("-i");
/* 52 */            this.mCommand.add(path);
/* 53 */            return this;
                }

/* 56 */        public Processor setSize(int sizeX, int sizeY) {
/* 57 */            this.mCommand.add("-s");
/* 58 */            this.mCommand.add("" + sizeX + "x" + sizeY);
/* 59 */            return this;
                }

/* 62 */        public Processor useMjpeg() {
/* 63 */            this.mCommand.add("-vcodec");
/* 64 */            this.mCommand.add("mjpeg");
/* 65 */            return this;
                }

/* 68 */        public Processor useX264() {
/* 69 */            this.mCommand.add("-vcodec");
/* 70 */            this.mCommand.add("libx264");
/* 71 */            return this;
                }

/* 74 */        public Processor setCopy() {
/* 75 */            this.mCommand.add("-c");
/* 76 */            this.mCommand.add("copy");
/* 77 */            return this;
                }

/* 80 */        public Processor setBsfV(String format) {
/* 81 */            this.mCommand.add("-bsf:v");
/* 82 */            this.mCommand.add(format);
/* 83 */            return this;
                }

/* 86 */        public Processor setBsfA(String format) {
/* 87 */            this.mCommand.add("-bsf:a");
/* 88 */            this.mCommand.add(format);
/* 89 */            return this;
                }

/* 92 */        public Processor setMap(String value) {
/* 93 */            this.mCommand.add("-map");
/* 94 */            this.mCommand.add(value);
/* 95 */            return this;
                }

/* 98 */        public Processor enableShortest() {
/* 99 */            this.mCommand.add("-shortest");
/* 100 */           return this;
                }

/* 103 */       public Processor setFormat(String format) {
/* 104 */           this.mCommand.add("-f");
/* 105 */           this.mCommand.add(format);
/* 106 */           return this;
                }

/* 109 */       public Processor setVideoCopy() {
/* 110 */           this.mCommand.add("-vcodec");
/* 111 */           this.mCommand.add("copy");
/* 112 */           return this;
                }

/* 115 */       public Processor setAudioCopy() {
/* 116 */           this.mCommand.add("-acodec");
/* 117 */           this.mCommand.add("copy");
/* 118 */           return this;
                }

/* 121 */       public Processor setPreset(Preset preset) {
/* 122 */           this.mCommand.add("-preset");
/* 123 */           this.mCommand.add(preset.name().toLowerCase(Locale.US));
/* 124 */           return this;
                }

/* 127 */       public Processor setStart(long start) {
/* 128 */           this.mCommand.add("-ss");
/* 129 */           this.mCommand.add(String.valueOf(start / 1000.0d));
/* 130 */           return this;
                }

/* 133 */       public Processor setVFrames(int vFrames) {
/* 134 */           this.mCommand.add("-vframes");
/* 135 */           this.mCommand.add(String.valueOf(vFrames));
/* 136 */           return this;
                }

/* 139 */       public Processor setTotalDuration(long duration) {
/* 140 */           this.mCommand.add("-t");
/* 141 */           this.mCommand.add(String.valueOf(duration / 1000.0d));
/* 142 */           return this;
                }

/* 145 */       public Processor setFrames(long duration, int frameRate) {
/* 146 */           this.mCommand.add("-vframes");
/* 147 */           this.mCommand.add(String.valueOf((int) ((duration / 1000.0d) * ((double) frameRate))));
/* 148 */           return this;
                }

/* 151 */       public Processor enableOverwrite() {
/* 152 */           this.mCommand.add("-y");
/* 153 */           return this;
                }

/* 156 */       public Processor setShortest() {
/* 157 */           this.mCommand.add("-shortest");
/* 158 */           return this;
                }

/* 161 */       public Processor filterCrop(int width, int height) {
/* 162 */           this.mFilters.add("crop=" + width + ":" + height);
/* 163 */           return this;
                }

/* 166 */       public Processor filterRotation(Rotation rotation) {
/* 167 */           this.mFilters.add("transpose=" + rotation.ordinal());
/* 168 */           return this;
                }

/* 171 */       public Processor setMetaData(HashMap<String, String> mMetaData) {
/* 172 */           this.mMetaData = mMetaData;
/* 173 */           return this;
                }

/* 176 */       public int processToOutput(String output) {
/* 177 */           if (this.mFilters.size() > 0) {
/* 178 */               this.mCommand.add("-vf");
/* 179 */               StringBuilder filter = new StringBuilder();
                        for (String args : this.mFilters) {
/* 181 */                   filter.append(args);
/* 182 */                   filter.append(",");
                        }
/* 184 */               String filterArg = filter.toString();
/* 185 */               this.mCommand.add(filterArg.substring(0, filterArg.length() - 1));
                    }
                    for (String key : this.mMetaData.keySet()) {
/* 188 */               this.mCommand.add("-metadata");
/* 189 */               this.mCommand.add(key + "=\"" + this.mMetaData.get(key) + "\"");
                    }
/* 192 */           if (this.mNumCores > 1) {
                    }
/* 196 */           this.mCommand.add(output);
                    for (String args2 : this.mCommand) {
/* 198 */               SLog.m447d("Add arg '{}'", args2);
                    }
/* 200 */           return process((String[]) this.mCommand.toArray(new String[this.mCommand.size()]));
                }

                /* JADX INFO: renamed from: co.vine.android.recorder.Processor$1CpuFilter, reason: invalid class name */
/* 221 */       static class C1CpuFilter implements FileFilter {
/* 222 */           C1CpuFilter() {
                    }

                    @Override // java.io.FileFilter
/* 225 */           public boolean accept(File pathname) {
/* 226 */               return Pattern.matches("cpu[0-9]", pathname.getName());
                    }
                }

/* 231 */       private static int getNumCores() {
                    try {
/* 232 */               File dir = new File("/sys/devices/system/cpu/");
/* 234 */               File[] files = dir.listFiles(new C1CpuFilter());
/* 236 */               return files.length;
                    } catch (Exception e) {
/* 239 */               return 1;
                    }
                }
            }
