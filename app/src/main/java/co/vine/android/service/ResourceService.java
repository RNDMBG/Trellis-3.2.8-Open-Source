            package co.vine.android.service;

            import android.app.Service;
            import android.content.Context;
            import android.content.Intent;
            import android.graphics.Bitmap;
            import android.graphics.Canvas;
            import android.graphics.Matrix;
            import android.graphics.Point;
            import android.os.Handler;
            import android.os.IBinder;
            import android.os.Message;
            import android.os.Messenger;
            import android.support.v8.renderscript.RenderScript;
            import co.vine.android.recorder.PictureConverter;
            import co.vine.android.recorder.RecordConfigUtils;
            import co.vine.android.recorder.SwVineFrameRecorder;
            import co.vine.android.recorder.audio.AudioArray;
            import co.vine.android.recorder.audio.AudioArrays;
            import co.vine.android.util.CrashUtil;
            import co.vine.android.util.CrossConstants;
            import co.vine.android.util.SystemUtil;
            import com.edisonwang.android.slog.SLog;
            import com.googlecode.javacpp.BytePointer;
            import com.googlecode.javacv.cpp.opencv_core;
            import java.nio.ByteBuffer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 57 */    public class ResourceService extends Service {
                public static final String CONVERSATION_STATE_OFF = "co.vine.android.camera.request.conversation.state.off";
                public static final String CONVERSATION_STATE_ON = "co.vine.android.camera.request.conversation.state.on";
                public static final String CONVERSATION_STATE_ON_ID = "co.vine.android.camera.request.conversation.state.id";
                public static final int REQUEST_CONVERSATION_STATE_BC = 101;
                public static long lastActiveconversationRowId;
                private static AudioArray sAudioDataBufferByte;
                private static AudioArray sAudioDataBufferShort;
                private static int sBufferUserCountByte;
                private static int sBufferUserCountShort;
                private static ByteBuffer sFrameBuffer;
                private static opencv_core.IplImage sIplImage;
                private static PictureConverter sPictureConverter;
                private static Bitmap sPreviewBitmap;
                private static Canvas sPreviewCanvas;
                private static RenderScript sRS;
                private static Bitmap sThumbnailBitmap;
                private static Canvas sThumbnailBitmapCanvas;
                private static Matrix sThumbnailMatrix;
                private static SwVineFrameRecorder sVineRecorder;
                private final Messenger messenger = new Messenger(new CameraServiceHandler());
                private static final int[] sLOCK = new int[0];
                public static boolean IS_READY = false;
                public static boolean isConversationActive = false;

/* 58 */        public static void initVineRecorder(Context context) {
                    synchronized (sLOCK) {
                        try {
/* 60 */                    if (sVineRecorder == null) {
                                try {
/* 63 */                            if (!SwVineFrameRecorder.hasEverSuccessfullyLoaded) {
/* 64 */                                SwVineFrameRecorder.tryLoad(context);
/* 65 */                                RecordConfigUtils.setLoadWasEverSuccessful(context, true);
                                    }
                                } catch (Throwable e) {
                                    try {
/* 68 */                                CrashUtil.logException(e, "Failed to load", new Object[0]);
/* 69 */                                RecordConfigUtils.setLoadWasEverSuccessful(context, false);
                                    } catch (Throwable throwable) {
/* 73 */                                CrashUtil.logException(throwable);
                                    }
                                }
/* 71 */                        sVineRecorder = new SwVineFrameRecorder("", 0, 0, 1, null, null);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 79 */        public static Bitmap getThumbnailBitmap(Context context) {
                    Bitmap bitmap;
                    synchronized (sLOCK) {
                        try {
/* 81 */                    if (sThumbnailBitmap == null) {
/* 82 */                        Point size = SystemUtil.getDisplaySize(context);
/* 83 */                        sThumbnailBitmap = generateThumbnailBitmap(size);
                            }
/* 85 */                    bitmap = sThumbnailBitmap;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return bitmap;
                }

/* 89 */        public static RenderScript getRenderScript(Context context) {
                    RenderScript renderScript;
                    synchronized (sLOCK) {
                        try {
/* 91 */                    if (sRS == null) {
/* 92 */                        sRS = RenderScript.create(context.getApplicationContext());
                            }
/* 94 */                    renderScript = sRS;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return renderScript;
                }

/* 98 */        public static Bitmap getPreviewBitmap() {
                    Bitmap bitmap;
                    synchronized (sLOCK) {
                        try {
/* 100 */                   if (sPreviewBitmap == null) {
/* 101 */                       sPreviewBitmap = RecordConfigUtils.createDefaultSizeBitmap();
                            }
/* 103 */                   bitmap = sPreviewBitmap;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return bitmap;
                }

/* 107 */       public static opencv_core.IplImage getFrameImage() {
                    opencv_core.IplImage iplImage;
                    synchronized (sLOCK) {
                        try {
/* 109 */                   if (sIplImage == null) {
/* 110 */                       sIplImage = opencv_core.IplImage.create(480, 480, 8, 4);
                            }
/* 114 */                   iplImage = sIplImage;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return iplImage;
                }

/* 118 */       public static Matrix getThumbnailMatrix(Context context) {
                    Matrix matrix;
                    synchronized (sLOCK) {
                        try {
/* 120 */                   if (sThumbnailMatrix == null) {
/* 121 */                       sThumbnailMatrix = generateThumbnailMatrix(getThumbnailBitmap(context), getPreviewBitmap());
                            }
/* 123 */                   matrix = sThumbnailMatrix;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return matrix;
                }

/* 127 */       public static Canvas getThumbnailCanvas(Context context) {
                    Canvas canvas;
                    synchronized (sLOCK) {
                        try {
/* 129 */                   if (sThumbnailBitmapCanvas == null) {
/* 130 */                       sThumbnailBitmapCanvas = new Canvas(getThumbnailBitmap(context));
                            }
/* 132 */                   canvas = sThumbnailBitmapCanvas;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return canvas;
                }

/* 136 */       public static Canvas getPreviewCanvas() {
                    Canvas canvas;
                    synchronized (sLOCK) {
                        try {
/* 138 */                   if (sPreviewCanvas == null) {
/* 139 */                       sPreviewCanvas = new Canvas(getPreviewBitmap());
                            }
/* 141 */                   canvas = sPreviewCanvas;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return canvas;
                }

/* 145 */       public static AudioArray getAudioDataBuffer(AudioArrays.AudioArrayType type) {
                    AudioArray audioArrayCreateAudioArray;
                    synchronized (sLOCK) {
                        try {
/* 147 */                   if (type == AudioArrays.AudioArrayType.BYTE) {
/* 148 */                       sBufferUserCountByte++;
/* 149 */                       if (sAudioDataBufferByte == null) {
/* 150 */                           sAudioDataBufferByte = RecordConfigUtils.createAudioArray(type);
                                }
/* 152 */                       if (sBufferUserCountByte > 1) {
/* 153 */                           audioArrayCreateAudioArray = RecordConfigUtils.createAudioArray(type);
                                } else {
/* 155 */                           audioArrayCreateAudioArray = sAudioDataBufferByte;
                                }
                            } else {
/* 157 */                       sBufferUserCountShort++;
/* 158 */                       if (sAudioDataBufferShort == null) {
/* 159 */                           sAudioDataBufferShort = RecordConfigUtils.createAudioArray(type);
                                }
/* 161 */                       if (sBufferUserCountShort > 1) {
/* 162 */                           audioArrayCreateAudioArray = RecordConfigUtils.createAudioArray(type);
                                } else {
/* 164 */                           audioArrayCreateAudioArray = sAudioDataBufferShort;
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
/* 164 */           return audioArrayCreateAudioArray;
                }

/* 169 */       public static ByteBuffer getFrameBuffer() {
                    ByteBuffer byteBuffer;
                    synchronized (sLOCK) {
                        try {
/* 171 */                   if (sFrameBuffer == null) {
/* 172 */                       try {
                                BytePointer pointer = new BytePointer(921600);
                                sFrameBuffer = pointer.asByteBuffer();
                                if (sFrameBuffer == null) {
                                    pointer.deallocate();
                                    sFrameBuffer = ByteBuffer.allocate(921600);
                                }
                            } catch (Throwable ignored) {
                                // JavaCPP is optional for ABIs without its native library.
                                sFrameBuffer = ByteBuffer.allocate(921600);
                            }
                            }
/* 174 */                   byteBuffer = sFrameBuffer;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return byteBuffer;
                }

/* 178 */       public static void releaseAudioBuffer(AudioArrays.AudioArrayType type) {
                    synchronized (sLOCK) {
                        try {
/* 180 */                   if (type == AudioArrays.AudioArrayType.BYTE) {
/* 181 */                       sBufferUserCountByte--;
/* 182 */                       if (sBufferUserCountByte < 0) {
/* 183 */                           sBufferUserCountByte = 0;
                                }
                            } else {
/* 186 */                       sBufferUserCountShort--;
/* 187 */                       if (sBufferUserCountShort < 0) {
/* 188 */                           sBufferUserCountShort = 0;
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 194 */       public static PictureConverter getPictureConverter() {
/* 195 */           return sPictureConverter;
                }

/* 198 */       public static void setPictureConverter(PictureConverter pictureConverter) {
/* 199 */           sPictureConverter = pictureConverter;
                }

/* 200 */       public class CameraServiceHandler extends Handler {
/* 201 */           public CameraServiceHandler() {
                    }

                    @Override // android.os.Handler
/* 205 */           public void handleMessage(Message msg) {
                        switch (msg.what) {
                            case 101:
/* 208 */                       Intent intent = new Intent(ResourceService.isConversationActive ? ResourceService.CONVERSATION_STATE_ON : ResourceService.CONVERSATION_STATE_OFF);
/* 209 */                       intent.putExtra(ResourceService.CONVERSATION_STATE_ON_ID, ResourceService.lastActiveconversationRowId);
/* 210 */                       ResourceService.this.sendBroadcast(intent, CrossConstants.BROADCAST_PERMISSION);
                                break;
                            default:
/* 213 */                       SLog.m455e("Invalid request: " + msg.what);
                                break;
                        }
                    }
                }

                @Override // android.app.Service
/* 221 */       public IBinder onBind(Intent intent) {
/* 222 */           return this.messenger.getBinder();
                }

                @Override // android.app.Service
/* 226 */       public void onDestroy() {
/* 227 */           super.onDestroy();
/* 228 */           if (sVineRecorder != null) {
/* 229 */               sVineRecorder.release();
/* 230 */               sVineRecorder = null;
                    }
/* 232 */           SLog.m446d("Camera Service onDestroy..");
/* 233 */           IS_READY = false;
                }

                @Override // android.app.Service
/* 237 */       public void onCreate() {
/* 238 */           SLog.m446d("Camera Service onCreate start.");
/* 239 */           long start = System.currentTimeMillis();
                    try {
/* 241 */               getThumbnailBitmap(this);
/* 242 */               getRenderScript(this);
/* 243 */               if (RecordConfigUtils.HW_ENABLED) {
/* 244 */                   getFrameBuffer();
                        }
/* 246 */               if (RecordConfigUtils.SW_ENABLED) {
/* 247 */                   initVineRecorder(this);
/* 248 */                   getFrameImage();
                        }
/* 250 */               getPreviewBitmap();
/* 251 */               getThumbnailMatrix(this);
/* 252 */               getThumbnailCanvas(this);
/* 253 */               getPreviewCanvas();
                    } catch (Throwable e) {
/* 255 */               CrashUtil.log("Failed to init camera service, it is ok.", e);
                    }
/* 257 */           IS_READY = true;
/* 258 */           SLog.m447d("Camera Service took {}ms.", Long.valueOf(System.currentTimeMillis() - start));
                }

                @Override // android.app.Service
/* 262 */       public int onStartCommand(Intent intent, int flags, int startId) {
/* 263 */           return 1;
                }

/* 266 */       public static Matrix generateThumbnailMatrix(Bitmap thumbnailBitmap, Bitmap previewBitmap) {
/* 267 */           Matrix thumbnailMatrix = new Matrix();
/* 268 */           float thumbnailScale = (1.0f * thumbnailBitmap.getHeight()) / previewBitmap.getHeight();
/* 269 */           thumbnailMatrix.setScale(thumbnailScale, thumbnailScale);
/* 270 */           return thumbnailMatrix;
                }

/* 273 */       public static Bitmap generateThumbnailBitmap(Point size) {
/* 274 */           int thumbnailSize = size.x / 2;
/* 275 */           return Bitmap.createBitmap(thumbnailSize, thumbnailSize, RecordConfigUtils.DEFAULT_BITMAP_CONFIG);
                }
            }
