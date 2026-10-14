            package co.vine.android.recorder;

            import android.graphics.Paint;
            import android.graphics.Point;
            import android.graphics.SurfaceTexture;
            import android.opengl.GLES20;
            import android.opengl.Matrix;
            import android.util.Log;
            import android.view.Surface;
            import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
            import java.nio.Buffer;
            import java.nio.ByteBuffer;
            import java.nio.ByteOrder;
            import java.nio.FloatBuffer;
            import javax.microedition.khronos.egl.EGL10;
            import javax.microedition.khronos.egl.EGLConfig;
            import javax.microedition.khronos.egl.EGLContext;
            import javax.microedition.khronos.egl.EGLDisplay;
            import javax.microedition.khronos.egl.EGLSurface;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 58 */    public abstract class CodecOutputSurface implements SurfaceTexture.OnFrameAvailableListener {
                protected static final int NANOS_PER_MILLI = 1000000;
                protected static final String TAG = "ExtractMpegFramesTest";
                protected static final boolean VERBOSE = false;
                protected EGL10 mEgl;
                protected boolean mFrameAvailable;
                int mInHeight;
                int mInWidth;
                protected Surface mSurface;
                protected SurfaceTexture mSurfaceTexture;
                protected STextureRender mTextureRender;
                protected EGLDisplay mEGLDisplay = EGL10.EGL_NO_DISPLAY;
                protected EGLContext mEGLContext = EGL10.EGL_NO_CONTEXT;
                protected EGLSurface mEGLSurface = EGL10.EGL_NO_SURFACE;
                protected Object mFrameSyncObject = new Object();
                protected Paint mPaint = new Paint();

                protected abstract void establishSize(int i, int i2, int i3, Point point);

/* 59 */        public CodecOutputSurface(int inWidth, int inHeight, int outSize, Point cropOrigin) {
/* 61 */            if (inWidth <= 0 || inHeight <= 0) {
/* 62 */                throw new IllegalArgumentException();
                    }
/* 64 */            this.mEgl = (EGL10) EGLContext.getEGL();
/* 66 */            establishSize(inWidth, inHeight, outSize, cropOrigin);
/* 68 */            eglSetup();
/* 69 */            makeCurrent();
/* 70 */            setup();
                }

/* 78 */        protected void setup() {
/* 79 */            this.mTextureRender = new STextureRender();
/* 80 */            this.mTextureRender.surfaceCreated(getMinificationFilteringMode());
/* 83 */            this.mSurfaceTexture = new SurfaceTexture(this.mTextureRender.getTextureId());
/* 85 */            this.mSurfaceTexture.setOnFrameAvailableListener(this);
/* 87 */            this.mSurface = new Surface(this.mSurfaceTexture);
                }

/* 93 */        protected void eglSetup() {
/* 97 */            this.mEGLDisplay = this.mEgl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
/* 98 */            if (this.mEGLDisplay == EGL10.EGL_NO_DISPLAY) {
/* 99 */                throw new RuntimeException("unable to get EGL14 display");
                    }
/* 101 */           int[] version = new int[2];
/* 102 */           if (!this.mEgl.eglInitialize(this.mEGLDisplay, version)) {
/* 103 */               this.mEGLDisplay = null;
/* 104 */               throw new RuntimeException("unable to initialize EGL14");
                    }
/* 108 */           int[] attribList = {12324, 8, 12323, 8, 12322, 8, 12321, 8, 12352, 4, 12339, 1, 12344};
/* 117 */           EGLConfig[] configs = new EGLConfig[1];
/* 118 */           int[] numConfigs = new int[1];
/* 119 */           if (!this.mEgl.eglChooseConfig(this.mEGLDisplay, attribList, configs, configs.length, numConfigs)) {
/* 121 */               throw new RuntimeException("unable to find RGB888+recordable ES2 EGL config");
                    }
/* 125 */           int[] attrib_list = {12440, 2, 12344};
/* 129 */           this.mEGLContext = this.mEgl.eglCreateContext(this.mEGLDisplay, configs[0], EGL10.EGL_NO_CONTEXT, attrib_list);
/* 131 */           checkEglError("eglCreateContext");
/* 132 */           if (this.mEGLContext == null) {
/* 133 */               throw new RuntimeException("null context");
                    }
/* 137 */           int[] surfaceAttribs = {12375, this.mInWidth, 12374, this.mInHeight, 12344};
/* 142 */           this.mEGLSurface = this.mEgl.eglCreatePbufferSurface(this.mEGLDisplay, configs[0], surfaceAttribs);
/* 143 */           checkEglError("eglCreatePbufferSurface");
/* 144 */           if (this.mEGLSurface == null) {
/* 145 */               throw new RuntimeException("surface was null");
                    }
                }

/* 152 */       public void release() {
/* 153 */           if (this.mEGLDisplay != EGL10.EGL_NO_DISPLAY) {
/* 154 */               this.mEgl.eglDestroySurface(this.mEGLDisplay, this.mEGLSurface);
/* 155 */               this.mEgl.eglDestroyContext(this.mEGLDisplay, this.mEGLContext);
/* 157 */               this.mEgl.eglMakeCurrent(this.mEGLDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
/* 159 */               this.mEgl.eglTerminate(this.mEGLDisplay);
                    }
/* 161 */           this.mEGLDisplay = EGL10.EGL_NO_DISPLAY;
/* 162 */           this.mEGLContext = EGL10.EGL_NO_CONTEXT;
/* 163 */           this.mEGLSurface = EGL10.EGL_NO_SURFACE;
/* 165 */           this.mSurface.release();
/* 171 */           this.mTextureRender = null;
/* 172 */           this.mSurface = null;
/* 173 */           this.mSurfaceTexture = null;
                }

/* 179 */       public void makeCurrent() {
/* 180 */           if (!this.mEgl.eglMakeCurrent(this.mEGLDisplay, this.mEGLSurface, this.mEGLSurface, this.mEGLContext)) {
/* 181 */               throw new RuntimeException("eglMakeCurrent failed");
                    }
                }

/* 188 */       public Surface getSurface() {
/* 189 */           return this.mSurface;
                }

/* 197 */       public void awaitNewImage() {
                    synchronized (this.mFrameSyncObject) {
/* 201 */               while (!this.mFrameAvailable) {
                            try {
                                try {
/* 205 */                           this.mFrameSyncObject.wait(2500L);
/* 206 */                           if (!this.mFrameAvailable) {
/* 208 */                               throw new RuntimeException("frame wait timed out");
                                    }
                                } catch (InterruptedException ie) {
/* 212 */                           throw new RuntimeException(ie);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
/* 215 */               this.mFrameAvailable = false;
                    }
/* 219 */           this.mTextureRender.checkGlError("before updateTexImage");
/* 220 */           this.mSurfaceTexture.updateTexImage();
                }

/* 228 */       public void drawImage(boolean invert) {
/* 229 */           this.mTextureRender.drawFrame(this.mSurfaceTexture, invert);
                }

                @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
/* 235 */       public void onFrameAvailable(SurfaceTexture st) {
                    synchronized (this.mFrameSyncObject) {
                        try {
/* 237 */                   if (this.mFrameAvailable) {
/* 238 */                       throw new RuntimeException("mFrameAvailable already set, frame could be dropped");
                            }
/* 240 */                   this.mFrameAvailable = true;
/* 241 */                   this.mFrameSyncObject.notifyAll();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

/* 249 */       protected void checkEglError(String msg) {
/* 250 */           int error = this.mEgl.eglGetError();
                    if (error != 12288) {
/* 251 */               throw new RuntimeException(msg + ": EGL error: 0x" + Integer.toHexString(error));
                    }
                }

/* 301 */       protected static class STextureRender {
                    private static final int FLOAT_SIZE_BYTES = 4;
                    private static final String FRAGMENT_SHADER = "#extension GL_OES_EGL_image_external : require\nprecision mediump float;\nvarying vec2 vTextureCoord;\nuniform samplerExternalOES sTexture;\nvoid main() {\n    gl_FragColor = texture2D(sTexture, vTextureCoord);\n}\n";
                    private static final int TRIANGLE_VERTICES_DATA_POS_OFFSET = 0;
                    private static final int TRIANGLE_VERTICES_DATA_STRIDE_BYTES = 20;
                    private static final int TRIANGLE_VERTICES_DATA_UV_OFFSET = 3;
                    private static final String VERTEX_SHADER = "uniform mat4 uMVPMatrix;\nuniform mat4 uSTMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n    gl_Position = uMVPMatrix * aPosition;\n    vTextureCoord = (uSTMatrix * aTextureCoord).xy;\n}\n";
                    private int mProgram;
                    private int maPositionHandle;
                    private int maTextureHandle;
                    private int muMVPMatrixHandle;
                    private int muSTMatrixHandle;
                    private final float[] mTriangleVerticesData = {-1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 1.0f, -1.0f, 0.0f, 1.0f, 0.0f, -1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f};
                    private float[] mMVPMatrix = new float[16];
                    private float[] mSTMatrix = new float[16];
                    private int mTextureID = -12345;
                    private FloatBuffer mTriangleVertices = ByteBuffer.allocateDirect(this.mTriangleVerticesData.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();

/* 302 */           public STextureRender() {
/* 307 */               this.mTriangleVertices.put(this.mTriangleVerticesData).position(0);
/* 309 */               Matrix.setIdentityM(this.mSTMatrix, 0);
                    }

/* 312 */           public int getTextureId() {
/* 313 */               return this.mTextureID;
                    }

/* 319 */           public void drawFrame(SurfaceTexture st, boolean invert) {
/* 320 */               checkGlError("onDrawFrame start");
/* 321 */               st.getTransformMatrix(this.mSTMatrix);
/* 322 */               if (invert) {
/* 323 */                   this.mSTMatrix[5] = -this.mSTMatrix[5];
/* 324 */                   this.mSTMatrix[13] = 1.0f - this.mSTMatrix[13];
                        }
/* 328 */               GLES20.glClearColor(0.0f, 1.0f, 0.0f, 1.0f);
/* 329 */               GLES20.glClear(16384);
/* 331 */               GLES20.glUseProgram(this.mProgram);
/* 332 */               checkGlError("glUseProgram");
/* 334 */               GLES20.glActiveTexture(33984);
/* 335 */               GLES20.glBindTexture(36197, this.mTextureID);
/* 337 */               this.mTriangleVertices.position(0);
/* 338 */               GLES20.glVertexAttribPointer(this.maPositionHandle, 3, 5126, false, 20, (Buffer) this.mTriangleVertices);
/* 340 */               checkGlError("glVertexAttribPointer maPosition");
/* 341 */               GLES20.glEnableVertexAttribArray(this.maPositionHandle);
/* 342 */               checkGlError("glEnableVertexAttribArray maPositionHandle");
/* 344 */               this.mTriangleVertices.position(3);
/* 345 */               GLES20.glVertexAttribPointer(this.maTextureHandle, 2, 5126, false, 20, (Buffer) this.mTriangleVertices);
/* 347 */               checkGlError("glVertexAttribPointer maTextureHandle");
/* 348 */               GLES20.glEnableVertexAttribArray(this.maTextureHandle);
/* 349 */               checkGlError("glEnableVertexAttribArray maTextureHandle");
/* 351 */               Matrix.setIdentityM(this.mMVPMatrix, 0);
/* 352 */               GLES20.glUniformMatrix4fv(this.muMVPMatrixHandle, 1, false, this.mMVPMatrix, 0);
/* 353 */               GLES20.glUniformMatrix4fv(this.muSTMatrixHandle, 1, false, this.mSTMatrix, 0);
/* 355 */               GLES20.glDrawArrays(5, 0, 4);
/* 356 */               checkGlError("glDrawArrays");
/* 358 */               GLES20.glBindTexture(36197, 0);
                    }

/* 364 */           public void surfaceCreated(int minificationMode) {
/* 365 */               this.mProgram = createProgram(VERTEX_SHADER, FRAGMENT_SHADER);
/* 366 */               if (this.mProgram == 0) {
/* 367 */                   throw new RuntimeException("failed creating program");
                        }
/* 370 */               this.maPositionHandle = GLES20.glGetAttribLocation(this.mProgram, "aPosition");
/* 371 */               checkLocation(this.maPositionHandle, "aPosition");
/* 372 */               this.maTextureHandle = GLES20.glGetAttribLocation(this.mProgram, "aTextureCoord");
/* 373 */               checkLocation(this.maTextureHandle, "aTextureCoord");
/* 375 */               this.muMVPMatrixHandle = GLES20.glGetUniformLocation(this.mProgram, "uMVPMatrix");
/* 376 */               checkLocation(this.muMVPMatrixHandle, "uMVPMatrix");
/* 377 */               this.muSTMatrixHandle = GLES20.glGetUniformLocation(this.mProgram, "uSTMatrix");
/* 378 */               checkLocation(this.muSTMatrixHandle, "uSTMatrix");
/* 380 */               int[] textures = new int[1];
/* 381 */               GLES20.glGenTextures(1, textures, 0);
/* 383 */               this.mTextureID = textures[0];
/* 384 */               GLES20.glBindTexture(36197, this.mTextureID);
/* 385 */               checkGlError("glBindTexture mTextureID");
/* 388 */               GLES20.glTexParameterf(36197, 10241, minificationMode);
/* 390 */               GLES20.glTexParameterf(36197, 10240, 9729.0f);
/* 392 */               GLES20.glTexParameteri(36197, 10242, 33071);
/* 394 */               GLES20.glTexParameteri(36197, 10243, 33071);
/* 396 */               checkGlError("glTexParameter");
                    }

/* 399 */           private int loadShader(int shaderType, String source) {
/* 400 */               int shader = GLES20.glCreateShader(shaderType);
/* 401 */               checkGlError("glCreateShader type=" + shaderType);
/* 402 */               GLES20.glShaderSource(shader, source);
/* 403 */               GLES20.glCompileShader(shader);
/* 404 */               int[] compiled = new int[1];
/* 405 */               GLES20.glGetShaderiv(shader, 35713, compiled, 0);
/* 406 */               if (compiled[0] == 0) {
/* 407 */                   Log.e(CodecOutputSurface.TAG, "Could not compile shader " + shaderType + ":");
/* 408 */                   Log.e(CodecOutputSurface.TAG, MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + GLES20.glGetShaderInfoLog(shader));
/* 409 */                   GLES20.glDeleteShader(shader);
/* 410 */                   return 0;
                        }
/* 412 */               return shader;
                    }

/* 415 */           private int createProgram(String vertexSource, String fragmentSource) {
                        int pixelShader;
/* 416 */               int vertexShader = loadShader(35633, vertexSource);
/* 417 */               if (vertexShader != 0 && (pixelShader = loadShader(35632, fragmentSource)) != 0) {
/* 425 */                   int program = GLES20.glCreateProgram();
/* 426 */                   if (program == 0) {
/* 427 */                       Log.e(CodecOutputSurface.TAG, "Could not create program");
                            }
/* 429 */                   GLES20.glAttachShader(program, vertexShader);
/* 430 */                   checkGlError("glAttachShader");
/* 431 */                   GLES20.glAttachShader(program, pixelShader);
/* 432 */                   checkGlError("glAttachShader");
/* 433 */                   GLES20.glLinkProgram(program);
/* 434 */                   int[] linkStatus = new int[1];
/* 435 */                   GLES20.glGetProgramiv(program, 35714, linkStatus, 0);
/* 436 */                   if (linkStatus[0] != 1) {
/* 437 */                       Log.e(CodecOutputSurface.TAG, "Could not link program: ");
/* 438 */                       Log.e(CodecOutputSurface.TAG, GLES20.glGetProgramInfoLog(program));
/* 439 */                       GLES20.glDeleteProgram(program);
/* 440 */                       return 0;
                            }
/* 442 */                   return program;
                        }
                        return 0;
                    }

/* 446 */           public void checkGlError(String op) {
/* 447 */               int error = GLES20.glGetError();
                        if (error != 0) {
/* 448 */                   Log.e(CodecOutputSurface.TAG, op + ": glError " + error);
/* 449 */                   throw new RuntimeException(op + ": glError " + error);
                        }
                    }

/* 453 */           public static void checkLocation(int location, String label) {
/* 454 */               if (location < 0) {
/* 455 */                   throw new RuntimeException("Unable to locate '" + label + "' in program");
                        }
                    }
                }

/* 460 */       public int getMinificationFilteringMode() {
/* 461 */           return 9729;
                }
            }
