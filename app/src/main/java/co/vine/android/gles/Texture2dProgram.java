            package co.vine.android.gles;

            import android.opengl.GLES20;
            import android.util.Log;
            import java.nio.Buffer;
            import java.nio.FloatBuffer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 137 */   public class Texture2dProgram {
                private static final String FRAGMENT_SHADER_2D = "precision mediump float;\nvarying vec2 vTextureCoord;\nuniform sampler2D sTexture;\nvoid main() {\n    gl_FragColor = texture2D(sTexture, vTextureCoord);\n}\n";
                private static final String FRAGMENT_SHADER_EXT = "#extension GL_OES_EGL_image_external : require\nprecision mediump float;\nvarying vec2 vTextureCoord;\nuniform samplerExternalOES sTexture;\nvoid main() {\n    gl_FragColor = texture2D(sTexture, vTextureCoord);\n}\n";
                private static final String FRAGMENT_SHADER_EXT_BW = "#extension GL_OES_EGL_image_external : require\nprecision mediump float;\nvarying vec2 vTextureCoord;\nuniform samplerExternalOES sTexture;\nvoid main() {\n    vec4 tc = texture2D(sTexture, vTextureCoord);\n    float color = tc.r * 0.3 + tc.g * 0.59 + tc.b * 0.11;\n    gl_FragColor = vec4(color, color, color, 1.0);\n}\n";
                private static final String FRAGMENT_SHADER_EXT_FILT = "#extension GL_OES_EGL_image_external : require\n#define KERNEL_SIZE 9\nprecision highp float;\nvarying vec2 vTextureCoord;\nuniform samplerExternalOES sTexture;\nuniform float uKernel[KERNEL_SIZE];\nuniform vec2 uTexOffset[KERNEL_SIZE];\nuniform float uColorAdjust;\nvoid main() {\n    int i = 0;\n    vec4 sum = vec4(0.0);\n    if (vTextureCoord.x < vTextureCoord.y - 0.005) {\n        for (i = 0; i < KERNEL_SIZE; i++) {\n            vec4 texc = texture2D(sTexture, vTextureCoord + uTexOffset[i]);\n            sum += texc * uKernel[i];\n        }\n    sum += uColorAdjust;\n    } else if (vTextureCoord.x > vTextureCoord.y + 0.005) {\n        sum = texture2D(sTexture, vTextureCoord);\n    } else {\n        sum.r = 1.0;\n    }\n    gl_FragColor = sum;\n}\n";
                public static final int KERNEL_SIZE = 9;
                private static final String TAG = "Grafika";
                private static final String VERTEX_SHADER = "uniform mat4 uMVPMatrix;\nuniform mat4 uTexMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n    gl_Position = uMVPMatrix * aPosition;\n    vTextureCoord = (uTexMatrix * aTextureCoord).xy;\n}\n";
                private float mColorAdjust;
                private float[] mKernel = new float[9];
                private int mProgramHandle;
                private ProgramType mProgramType;
                private float[] mTexOffset;
                private int mTextureTarget;
                private int maPositionLoc;
                private int maTextureCoordLoc;
                private int muColorAdjustLoc;
                private int muKernelLoc;
                private int muMVPMatrixLoc;
                private int muTexMatrixLoc;
                private int muTexOffsetLoc;

                public enum ProgramType {
                    TEXTURE_2D,
                    TEXTURE_EXT,
                    TEXTURE_EXT_BW,
                    TEXTURE_EXT_FILT
                }

/* 138 */       public Texture2dProgram(ProgramType programType) {
/* 140 */           this.mProgramType = programType;
                    switch (programType) {
                        case TEXTURE_2D:
/* 144 */                   this.mTextureTarget = 3553;
/* 145 */                   this.mProgramHandle = GlUtil.createProgram(VERTEX_SHADER, FRAGMENT_SHADER_2D);
                            break;
                        case TEXTURE_EXT:
/* 148 */                   this.mTextureTarget = 36197;
/* 149 */                   this.mProgramHandle = GlUtil.createProgram(VERTEX_SHADER, FRAGMENT_SHADER_EXT);
                            break;
                        case TEXTURE_EXT_BW:
/* 152 */                   this.mTextureTarget = 36197;
/* 153 */                   this.mProgramHandle = GlUtil.createProgram(VERTEX_SHADER, FRAGMENT_SHADER_EXT_BW);
                            break;
                        case TEXTURE_EXT_FILT:
/* 156 */                   this.mTextureTarget = 36197;
/* 157 */                   this.mProgramHandle = GlUtil.createProgram(VERTEX_SHADER, FRAGMENT_SHADER_EXT_FILT);
                            break;
                        default:
/* 160 */                   throw new RuntimeException("Unhandled type " + programType);
                    }
/* 162 */           if (this.mProgramHandle == 0) {
/* 163 */               throw new RuntimeException("Unable to create program");
                    }
/* 165 */           Log.d("Grafika", "Created program " + this.mProgramHandle + " (" + programType + ")");
/* 169 */           this.maPositionLoc = GLES20.glGetAttribLocation(this.mProgramHandle, "aPosition");
/* 170 */           GlUtil.checkLocation(this.maPositionLoc, "aPosition");
/* 171 */           this.maTextureCoordLoc = GLES20.glGetAttribLocation(this.mProgramHandle, "aTextureCoord");
/* 172 */           GlUtil.checkLocation(this.maTextureCoordLoc, "aTextureCoord");
/* 173 */           this.muMVPMatrixLoc = GLES20.glGetUniformLocation(this.mProgramHandle, "uMVPMatrix");
/* 174 */           GlUtil.checkLocation(this.muMVPMatrixLoc, "uMVPMatrix");
/* 175 */           this.muTexMatrixLoc = GLES20.glGetUniformLocation(this.mProgramHandle, "uTexMatrix");
/* 176 */           GlUtil.checkLocation(this.muTexMatrixLoc, "uTexMatrix");
/* 177 */           this.muKernelLoc = GLES20.glGetUniformLocation(this.mProgramHandle, "uKernel");
/* 178 */           if (this.muKernelLoc < 0) {
/* 180 */               this.muKernelLoc = -1;
/* 181 */               this.muTexOffsetLoc = -1;
/* 182 */               this.muColorAdjustLoc = -1;
                    } else {
/* 185 */               this.muTexOffsetLoc = GLES20.glGetUniformLocation(this.mProgramHandle, "uTexOffset");
/* 186 */               GlUtil.checkLocation(this.muTexOffsetLoc, "uTexOffset");
/* 187 */               this.muColorAdjustLoc = GLES20.glGetUniformLocation(this.mProgramHandle, "uColorAdjust");
/* 188 */               GlUtil.checkLocation(this.muColorAdjustLoc, "uColorAdjust");
/* 191 */               setKernel(new float[]{0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f}, 0.0f);
/* 192 */               setTexSize(256, 256);
                    }
                }

/* 202 */       public void release() {
/* 203 */           Log.d("Grafika", "deleting program " + this.mProgramHandle);
/* 204 */           GLES20.glDeleteProgram(this.mProgramHandle);
/* 205 */           this.mProgramHandle = -1;
                }

/* 211 */       public ProgramType getProgramType() {
/* 212 */           return this.mProgramType;
                }

/* 220 */       public int createTextureObject() {
/* 221 */           int[] textures = new int[1];
/* 222 */           GLES20.glGenTextures(1, textures, 0);
/* 223 */           GlUtil.checkGlError("glGenTextures");
/* 225 */           int texId = textures[0];
/* 226 */           GLES20.glBindTexture(this.mTextureTarget, texId);
/* 227 */           GlUtil.checkGlError("glBindTexture " + texId);
/* 229 */           GLES20.glTexParameterf(36197, 10241, 9728.0f);
/* 231 */           GLES20.glTexParameterf(36197, 10240, 9729.0f);
/* 233 */           GLES20.glTexParameteri(36197, 10242, 33071);
/* 235 */           GLES20.glTexParameteri(36197, 10243, 33071);
/* 237 */           GlUtil.checkGlError("glTexParameter");
/* 239 */           return texId;
                }

/* 247 */       public void setKernel(float[] values, float colorAdj) {
/* 248 */           if (values.length != 9) {
/* 249 */               throw new IllegalArgumentException("Kernel size is " + values.length + " vs. 9");
                    }
/* 252 */           System.arraycopy(values, 0, this.mKernel, 0, 9);
/* 253 */           this.mColorAdjust = colorAdj;
                }

/* 260 */       public void setTexSize(int width, int height) {
/* 261 */           float rw = 1.0f / width;
/* 262 */           float rh = 1.0f / height;
/* 265 */           this.mTexOffset = new float[]{-rw, -rh, 0.0f, -rh, rw, -rh, -rw, 0.0f, 0.0f, 0.0f, rw, 0.0f, -rw, rh, 0.0f, rh, rw, rh};
                }

/* 290 */       public void draw(float[] mvpMatrix, FloatBuffer vertexBuffer, int firstVertex, int vertexCount, int coordsPerVertex, int vertexStride, float[] texMatrix, FloatBuffer texBuffer, int textureId, int texStride) {
/* 291 */           GlUtil.checkGlError("draw start");
/* 294 */           GLES20.glUseProgram(this.mProgramHandle);
/* 295 */           GlUtil.checkGlError("glUseProgram");
/* 298 */           GLES20.glActiveTexture(33984);
/* 299 */           GLES20.glBindTexture(this.mTextureTarget, textureId);
/* 302 */           GLES20.glUniformMatrix4fv(this.muMVPMatrixLoc, 1, false, mvpMatrix, 0);
/* 303 */           GlUtil.checkGlError("glUniformMatrix4fv");
/* 306 */           GLES20.glUniformMatrix4fv(this.muTexMatrixLoc, 1, false, texMatrix, 0);
/* 307 */           GlUtil.checkGlError("glUniformMatrix4fv");
/* 310 */           GLES20.glEnableVertexAttribArray(this.maPositionLoc);
/* 311 */           GlUtil.checkGlError("glEnableVertexAttribArray");
/* 314 */           GLES20.glVertexAttribPointer(this.maPositionLoc, coordsPerVertex, 5126, false, vertexStride, (Buffer) vertexBuffer);
/* 316 */           GlUtil.checkGlError("glVertexAttribPointer");
/* 319 */           GLES20.glEnableVertexAttribArray(this.maTextureCoordLoc);
/* 320 */           GlUtil.checkGlError("glEnableVertexAttribArray");
/* 323 */           GLES20.glVertexAttribPointer(this.maTextureCoordLoc, 2, 5126, false, texStride, (Buffer) texBuffer);
/* 325 */           GlUtil.checkGlError("glVertexAttribPointer");
/* 328 */           if (this.muKernelLoc >= 0) {
/* 329 */               GLES20.glUniform1fv(this.muKernelLoc, 9, this.mKernel, 0);
/* 330 */               GLES20.glUniform2fv(this.muTexOffsetLoc, 9, this.mTexOffset, 0);
/* 331 */               GLES20.glUniform1f(this.muColorAdjustLoc, this.mColorAdjust);
                    }
/* 335 */           GLES20.glDrawArrays(5, firstVertex, vertexCount);
/* 336 */           GlUtil.checkGlError("glDrawArrays");
/* 339 */           GLES20.glDisableVertexAttribArray(this.maPositionLoc);
/* 340 */           GLES20.glDisableVertexAttribArray(this.maTextureCoordLoc);
/* 341 */           GLES20.glBindTexture(this.mTextureTarget, 0);
/* 342 */           GLES20.glUseProgram(0);
                }
            }
