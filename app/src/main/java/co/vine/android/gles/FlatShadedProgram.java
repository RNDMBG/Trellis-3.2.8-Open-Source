            package co.vine.android.gles;

            import android.opengl.GLES20;
            import android.util.Log;
            import java.nio.Buffer;
            import java.nio.FloatBuffer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 53 */    public class FlatShadedProgram {
                private static final String FRAGMENT_SHADER = "precision mediump float;uniform vec4 uColor;void main() {    gl_FragColor = uColor;}";
                private static final String TAG = "Grafika";
                private static final String VERTEX_SHADER = "uniform mat4 uMVPMatrix;attribute vec4 aPosition;void main() {    gl_Position = uMVPMatrix * aPosition;}";
                private int mProgramHandle;
                private int maPositionLoc;
                private int muColorLoc;
                private int muMVPMatrixLoc;

/* 54 */        public FlatShadedProgram() {
/* 46 */            this.mProgramHandle = -1;
/* 47 */            this.muColorLoc = -1;
/* 48 */            this.muMVPMatrixLoc = -1;
/* 49 */            this.maPositionLoc = -1;
/* 56 */            this.mProgramHandle = GlUtil.createProgram(VERTEX_SHADER, FRAGMENT_SHADER);
/* 57 */            if (this.mProgramHandle == 0) {
/* 58 */                throw new RuntimeException("Unable to create program");
                    }
/* 60 */            Log.d("Grafika", "Created program " + this.mProgramHandle);
/* 64 */            this.maPositionLoc = GLES20.glGetAttribLocation(this.mProgramHandle, "aPosition");
/* 65 */            GlUtil.checkLocation(this.maPositionLoc, "aPosition");
/* 66 */            this.muMVPMatrixLoc = GLES20.glGetUniformLocation(this.mProgramHandle, "uMVPMatrix");
/* 67 */            GlUtil.checkLocation(this.muMVPMatrixLoc, "uMVPMatrix");
/* 68 */            this.muColorLoc = GLES20.glGetUniformLocation(this.mProgramHandle, "uColor");
/* 69 */            GlUtil.checkLocation(this.muColorLoc, "uColor");
                }

/* 75 */        public void release() {
/* 76 */            GLES20.glDeleteProgram(this.mProgramHandle);
/* 77 */            this.mProgramHandle = -1;
                }

/* 93 */        public void draw(float[] mvpMatrix, float[] color, FloatBuffer vertexBuffer, int firstVertex, int vertexCount, int coordsPerVertex, int vertexStride) {
/* 94 */            GlUtil.checkGlError("draw start");
/* 97 */            GLES20.glUseProgram(this.mProgramHandle);
/* 98 */            GlUtil.checkGlError("glUseProgram");
/* 101 */           GLES20.glUniformMatrix4fv(this.muMVPMatrixLoc, 1, false, mvpMatrix, 0);
/* 102 */           GlUtil.checkGlError("glUniformMatrix4fv");
/* 105 */           GLES20.glUniform4fv(this.muColorLoc, 1, color, 0);
/* 106 */           GlUtil.checkGlError("glUniform4fv ");
/* 109 */           GLES20.glEnableVertexAttribArray(this.maPositionLoc);
/* 110 */           GlUtil.checkGlError("glEnableVertexAttribArray");
/* 113 */           GLES20.glVertexAttribPointer(this.maPositionLoc, coordsPerVertex, 5126, false, vertexStride, (Buffer) vertexBuffer);
/* 115 */           GlUtil.checkGlError("glVertexAttribPointer");
/* 118 */           GLES20.glDrawArrays(5, firstVertex, vertexCount);
/* 119 */           GlUtil.checkGlError("glDrawArrays");
/* 122 */           GLES20.glDisableVertexAttribArray(this.maPositionLoc);
/* 123 */           GLES20.glUseProgram(0);
                }
            }
