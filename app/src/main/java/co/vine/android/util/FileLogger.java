            package co.vine.android.util;

            import com.edisonwang.android.slog.MessageFormatter;
            import com.edisonwang.android.slog.SLog;
            import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
            import java.io.BufferedReader;
            import java.io.File;
            import java.io.FileNotFoundException;
            import java.io.FileOutputStream;
            import java.io.FileReader;
            import java.io.IOException;
            import java.io.PrintStream;
            import java.text.SimpleDateFormat;
            import java.util.Date;
            import java.util.HashMap;
            import java.util.Locale;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 26 */    public class FileLogger {
                private static final HashMap<String, FileLogger> sLoggers = new HashMap<>();
                private final SimpleDateFormat mDateFormat = new SimpleDateFormat("HH:mm:ss:SSS", Locale.US);
                private File mFile;
                private BufferedReader mInputStream;
                private PrintStream mOutputStream;

/* 27 */        public static synchronized FileLogger getLogger(File file) {
                    FileLogger logger;
                    try {
                        if (file == null) {
/* 29 */                    logger = new FileLogger(null);
                        } else {
/* 31 */                    String path = file.getAbsolutePath();
/* 32 */                    logger = sLoggers.get(path);
/* 33 */                    if (logger == null) {
/* 34 */                        logger = new FileLogger(file);
/* 35 */                        sLoggers.put(path, logger);
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return logger;
                }

/* 43 */        private FileLogger(File file) {
/* 45 */            this.mFile = file;
                }

/* 48 */        public synchronized void write(String data, Object... stuff) {
                    try {
                        if (this.mOutputStream == null) {
/* 50 */                    prepareOutput();
                        }
/* 52 */                if (this.mOutputStream != null) {
/* 53 */                    this.mOutputStream.print(this.mDateFormat.format(new Date()));
/* 54 */                    this.mOutputStream.print(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
/* 55 */                    String message = MessageFormatter.toStringMessage(data, stuff);
/* 56 */                    this.mOutputStream.print(message);
/* 57 */                    this.mOutputStream.println();
/* 58 */                    SLog.m464i(message);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 62 */        public synchronized String read() {
                    String line;
                    try {
                        if (this.mInputStream == null) {
/* 64 */                    prepareInput();
                        }
/* 66 */                if (this.mInputStream != null) {
                            try {
/* 68 */                        line = this.mInputStream.readLine();
                            } catch (IOException e) {
/* 70 */                        closeInput();
/* 71 */                        line = "Failed to read input file.";
                            }
                        } else {
/* 74 */                    line = null;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                    return line;
                }

/* 77 */        public synchronized void prepareInput() {
                    try {
                        closeOutput();
/* 79 */                if (this.mFile != null && this.mInputStream == null) {
                            try {
/* 81 */                        this.mInputStream = new BufferedReader(new FileReader(this.mFile));
                            } catch (FileNotFoundException e) {
/* 83 */                        SLog.m459e("Cannot open log file to read.", (Throwable) e);
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 88 */        public synchronized void closeOutput() {
                    try {
                        if (this.mOutputStream != null) {
/* 90 */                    this.mOutputStream.flush();
/* 91 */                    this.mOutputStream.close();
/* 92 */                    this.mOutputStream = null;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 96 */        public synchronized void closeInput() {
                    try {
                        if (this.mInputStream != null) {
                            try {
                                try {
/* 99 */                            this.mInputStream.close();
/* 103 */                           this.mInputStream = null;
                                } catch (IOException e) {
/* 101 */                           SLog.m459e("Failed to close the reading file.", (Throwable) e);
/* 103 */                           this.mInputStream = null;
                                }
                            } catch (Throwable th) {
                                this.mInputStream = null;
                                throw th;
                            }
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }

/* 108 */       public synchronized void clear() {
                    try {
                        closeOutput();
/* 110 */               closeInput();
/* 111 */               if (this.mFile != null) {
                            try {
/* 113 */                       this.mOutputStream = new PrintStream(new FileOutputStream(this.mFile, false));
/* 114 */                       this.mOutputStream.write(0);
/* 115 */                       this.mOutputStream.flush();
/* 116 */                       this.mOutputStream.close();
                            } catch (FileNotFoundException e) {
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }

/* 123 */       public synchronized void prepareOutput() {
                    try {
                        closeInput();
/* 125 */               if (this.mFile != null && this.mOutputStream == null) {
                            try {
/* 127 */                       this.mOutputStream = new PrintStream(new FileOutputStream(this.mFile, true));
                            } catch (FileNotFoundException e) {
/* 129 */                       SLog.m459e("Cannot open log file to write.", (Throwable) e);
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
