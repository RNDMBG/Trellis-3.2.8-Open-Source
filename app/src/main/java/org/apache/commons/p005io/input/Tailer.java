            package org.apache.commons.p005io.input;

            import java.io.File;
            import java.io.FileNotFoundException;
            import java.io.IOException;
            import java.io.RandomAccessFile;
            import org.apache.commons.p005io.FileUtils;
            import org.apache.commons.p005io.IOUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 154 */   public class Tailer implements Runnable {
                private static final int DEFAULT_BUFSIZE = 4096;
                private static final int DEFAULT_DELAY_MILLIS = 1000;
                private static final String RAF_MODE = "r";
                private final long delayMillis;
                private final boolean end;
                private final File file;
                private final byte[] inbuf;
                private final TailerListener listener;
                private final boolean reOpen;
                private volatile boolean run;

/* 155 */       public Tailer(File file, TailerListener listener) {
/* 156 */           this(file, listener, 1000L);
                }

/* 165 */       public Tailer(File file, TailerListener listener, long delayMillis) {
/* 166 */           this(file, listener, delayMillis, false);
                }

/* 176 */       public Tailer(File file, TailerListener listener, long delayMillis, boolean end) {
/* 177 */           this(file, listener, delayMillis, end, 4096);
                }

/* 188 */       public Tailer(File file, TailerListener listener, long delayMillis, boolean end, boolean reOpen) {
/* 189 */           this(file, listener, delayMillis, end, reOpen, 4096);
                }

/* 200 */       public Tailer(File file, TailerListener listener, long delayMillis, boolean end, int bufSize) {
/* 201 */           this(file, listener, delayMillis, end, false, bufSize);
                }

/* 212 */       public Tailer(File file, TailerListener listener, long delayMillis, boolean end, boolean reOpen, int bufSize) {
/* 148 */           this.run = true;
/* 214 */           this.file = file;
/* 215 */           this.delayMillis = delayMillis;
/* 216 */           this.end = end;
/* 218 */           this.inbuf = new byte[bufSize];
/* 221 */           this.listener = listener;
/* 222 */           listener.init(this);
/* 223 */           this.reOpen = reOpen;
                }

/* 236 */       public static Tailer create(File file, TailerListener listener, long delayMillis, boolean end, int bufSize) {
/* 237 */           Tailer tailer = new Tailer(file, listener, delayMillis, end, bufSize);
/* 238 */           Thread thread = new Thread(tailer);
/* 239 */           thread.setDaemon(true);
/* 240 */           thread.start();
/* 241 */           return tailer;
                }

/* 256 */       public static Tailer create(File file, TailerListener listener, long delayMillis, boolean end, boolean reOpen, int bufSize) {
/* 257 */           Tailer tailer = new Tailer(file, listener, delayMillis, end, reOpen, bufSize);
/* 258 */           Thread thread = new Thread(tailer);
/* 259 */           thread.setDaemon(true);
/* 260 */           thread.start();
/* 261 */           return tailer;
                }

/* 273 */       public static Tailer create(File file, TailerListener listener, long delayMillis, boolean end) {
/* 274 */           return create(file, listener, delayMillis, end, 4096);
                }

/* 287 */       public static Tailer create(File file, TailerListener listener, long delayMillis, boolean end, boolean reOpen) {
/* 288 */           return create(file, listener, delayMillis, end, reOpen, 4096);
                }

/* 299 */       public static Tailer create(File file, TailerListener listener, long delayMillis) {
/* 300 */           return create(file, listener, delayMillis, false);
                }

/* 311 */       public static Tailer create(File file, TailerListener listener) {
/* 312 */           return create(file, listener, 1000L, false);
                }

/* 320 */       public File getFile() {
/* 321 */           return this.file;
                }

/* 329 */       public long getDelay() {
/* 330 */           return this.delayMillis;
                }

                @Override // java.lang.Runnable
/* 336 */       public void run() throws Throwable {
                    RandomAccessFile reader;
                    RandomAccessFile reader2;
/* 337 */           RandomAccessFile reader3 = null;
/* 339 */           long last = 0;
/* 340 */           long position = 0;
                    while (true) {
                        try {
                            reader = reader3;
/* 342 */                   if (!this.run || reader != null) {
                                break;
                            }
                            try {
/* 344 */                       reader3 = new RandomAccessFile(this.file, RAF_MODE);
                            } catch (FileNotFoundException e) {
/* 346 */                       this.listener.fileNotFound();
                                reader3 = reader;
                            }
/* 349 */                   if (reader3 == null) {
                                try {
                                    try {
                                        try {
/* 351 */                                   Thread.sleep(this.delayMillis);
                                        } catch (InterruptedException e2) {
                                        }
                                    } catch (Throwable th) {
                                        th = th;
                                        IOUtils.closeQuietly(reader3);
                                        throw th;
                                    }
                                } catch (Exception e3) {
/* 425 */                           e = e3;
                                }
                            } else {
/* 356 */                       position = this.end ? this.file.length() : 0L;
/* 357 */                       last = System.currentTimeMillis();
/* 358 */                       reader3.seek(position);
                            }
/* 425 */                   e = e3;
                        } catch (Exception e4) {
/* 425 */                   e = e4;
                            reader3 = reader;
                        } catch (Throwable th2) {
                            th = th2;
                            reader3 = reader;
                        }
/* 427 */               this.listener.handle(e);
/* 430 */               IOUtils.closeQuietly(reader3);
/* 432 */               return;
                    }
/* 362 */           while (this.run) {
/* 364 */               boolean newer = FileUtils.isFileNewer(this.file, last);
/* 367 */               long length = this.file.length();
                        if (length < position) {
/* 372 */                   this.listener.fileRotated();
/* 377 */                   RandomAccessFile save = reader;
                            try {
/* 378 */                       reader3 = new RandomAccessFile(this.file, RAF_MODE);
/* 379 */                       position = 0;
                                try {
/* 381 */                           IOUtils.closeQuietly(save);
                                    reader = reader3;
                                } catch (FileNotFoundException e5) {
/* 384 */                           this.listener.fileNotFound();
                                    reader = reader3;
                                }
                            } catch (FileNotFoundException e6) {
                                reader3 = reader;
                            }
                        } else {
                            if (length > position) {
/* 395 */                       position = readLines(reader);
/* 396 */                       last = System.currentTimeMillis();
                            } else if (newer) {
/* 405 */                       reader.seek(0L);
/* 408 */                       position = readLines(reader);
/* 409 */                       last = System.currentTimeMillis();
                            }
/* 412 */                   if (this.reOpen) {
/* 413 */                       IOUtils.closeQuietly(reader);
                            }
                            try {
/* 416 */                       Thread.sleep(this.delayMillis);
                            } catch (InterruptedException e7) {
                            }
/* 419 */                   if (this.run && this.reOpen) {
/* 420 */                       reader2 = new RandomAccessFile(this.file, RAF_MODE);
/* 421 */                       reader2.seek(position);
                            } else {
                                reader2 = reader;
                            }
                            reader = reader2;
                        }
                    }
/* 430 */           IOUtils.closeQuietly(reader);
                }

/* 437 */       public void stop() {
/* 438 */           this.run = false;
                }

/* 448 */       private long readLines(RandomAccessFile reader) throws IOException {
                    int num;
/* 449 */           StringBuilder sb = new StringBuilder();
/* 451 */           long pos = reader.getFilePointer();
/* 452 */           long rePos = pos;
/* 455 */           boolean seenCR = false;
/* 456 */           while (this.run && (num = reader.read(this.inbuf)) != -1) {
                        for (int i = 0; i < num; i++) {
/* 458 */                   byte ch = this.inbuf[i];
                            switch (ch) {
                                case 10:
/* 461 */                           seenCR = false;
/* 462 */                           this.listener.handle(sb.toString());
/* 463 */                           sb.setLength(0);
/* 464 */                           rePos = ((long) i) + pos + 1;
                                    break;
                                case 11:
                                case 12:
                                default:
/* 473 */                           if (seenCR) {
/* 474 */                               seenCR = false;
/* 475 */                               this.listener.handle(sb.toString());
/* 476 */                               sb.setLength(0);
/* 477 */                               rePos = ((long) i) + pos + 1;
                                    }
/* 479 */                           sb.append((char) ch);
                                    break;
                                case 13:
/* 467 */                           if (seenCR) {
/* 468 */                               sb.append('\r');
                                    }
/* 470 */                           seenCR = true;
                                    break;
                            }
                        }
/* 483 */               pos = reader.getFilePointer();
                    }
/* 486 */           reader.seek(rePos);
/* 487 */           return rePos;
                }
            }
