            package co.vine.android.gles;

            import android.opengl.GLES20;
            import android.opengl.Matrix;
            import android.util.Log;
            import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
            import java.nio.ByteBuffer;
            import java.nio.ByteOrder;
            import java.nio.FloatBuffer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 35 */    public class GlUtil {
                public static final float[] IDENTITY_MATRIX = new float[16];
                private static final int SIZEOF_FLOAT = 4;
                public static final String TAG = "Grafika";

                static {
/* 38 */            Matrix.setIdentityM(IDENTITY_MATRIX, 0);
                }

/* 43 */        private GlUtil() {
                }

/* 51 */        public static int createProgram(String vertexSource, String fragmentSource) {
                    int pixelShader;
/* 52 */            int vertexShader = loadShader(35633, vertexSource);
/* 53 */            if (vertexShader != 0 && (pixelShader = loadShader(35632, fragmentSource)) != 0) {
/* 61 */                int program = GLES20.glCreateProgram();
/* 62 */                checkGlError("glCreateProgram");
/* 63 */                if (program == 0) {
/* 64 */                    Log.e(TAG, "Could not create program");
                        }
/* 66 */                GLES20.glAttachShader(program, vertexShader);
/* 67 */                checkGlError("glAttachShader");
/* 68 */                GLES20.glAttachShader(program, pixelShader);
/* 69 */                checkGlError("glAttachShader");
/* 70 */                GLES20.glLinkProgram(program);
/* 71 */                int[] linkStatus = new int[1];
/* 72 */                GLES20.glGetProgramiv(program, 35714, linkStatus, 0);
/* 73 */                if (linkStatus[0] != 1) {
/* 74 */                    Log.e(TAG, "Could not link program: ");
/* 75 */                    Log.e(TAG, GLES20.glGetProgramInfoLog(program));
/* 76 */                    GLES20.glDeleteProgram(program);
/* 77 */                    return 0;
                        }
/* 79 */                return program;
                    }
                    return 0;
                }

/* 87 */        public static int loadShader(int shaderType, String source) {
/* 88 */            int shader = GLES20.glCreateShader(shaderType);
/* 89 */            checkGlError("glCreateShader type=" + shaderType);
/* 90 */            GLES20.glShaderSource(shader, source);
/* 91 */            GLES20.glCompileShader(shader);
/* 92 */            int[] compiled = new int[1];
/* 93 */            GLES20.glGetShaderiv(shader, 35713, compiled, 0);
/* 94 */            if (compiled[0] == 0) {
/* 95 */                Log.e(TAG, "Could not compile shader " + shaderType + ":");
/* 96 */                Log.e(TAG, MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + GLES20.glGetShaderInfoLog(shader));
/* 97 */                GLES20.glDeleteShader(shader);
/* 98 */                return 0;
                    }
/* 100 */           return shader;
                }

/* 106 */       public static void checkGlError(String op) {
/* 107 */           int error = GLES20.glGetError();
/* 108 */           if (error != 0) {
/* 109 */               String msg = op + ": glError 0x" + Integer.toHexString(error);
/* 110 */               Log.e(TAG, msg);
/* 111 */               throw new RuntimeException(msg);
                    }
                }

/* 121 */       public static void checkLocation(int location, String label) {
/* 122 */           if (location < 0) {
/* 123 */               throw new RuntimeException("Unable to locate '" + label + "' in program");
                    }
                }

/* 136 */       public static int createImageTexture(ByteBuffer data, int width, int height, int format) {
/* 137 */           int[] textureHandles = new int[1];
/* 140 */           GLES20.glGenTextures(1, textureHandles, 0);
/* 141 */           int textureHandle = textureHandles[0];
/* 142 */           checkGlError("glGenTextures");
/* 145 */           GLES20.glBindTexture(3553, textureHandle);
/* 149 */           GLES20.glTexParameteri(3553, 10241, 9729);
/* 151 */           GLES20.glTexParameteri(3553, 10240, 9729);
/* 153 */           checkGlError("loadImageTexture");
/* 156 */           GLES20.glTexImage2D(3553, 0, format, width, height, 0, format, 5121, data);
/* 158 */           checkGlError("loadImageTexture");
/* 160 */           return textureHandle;
                }

/* 167 */       public static FloatBuffer createFloatBuffer(float[] coords) {
/* 168 */           ByteBuffer bb = ByteBuffer.allocateDirect(coords.length * 4);
/* 169 */           bb.order(ByteOrder.nativeOrder());
/* 170 */           FloatBuffer fb = bb.asFloatBuffer();
/* 171 */           fb.put(coords);
/* 172 */           fb.position(0);
/* 173 */           return fb;
                }

/* 179 */       public static void logVersionInfo() {
/* 180 */           Log.i(TAG, "vendor  : " + GLES20.glGetString(7936));
/* 181 */           Log.i(TAG, "renderer: " + GLES20.glGetString(7937));
/* 182 */           Log.i(TAG, "version : " + GLES20.glGetString(7938));
                }
            }
