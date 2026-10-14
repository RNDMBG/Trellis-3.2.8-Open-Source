            package co.vine.android.gles;

            import java.nio.FloatBuffer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 111 */   public class Drawable2d {
                private static final int SIZEOF_FLOAT = 4;
                private int mCoordsPerVertex;
                private Prefab mPrefab;
                private FloatBuffer mTexCoordArray;
                private int mTexCoordStride;
                private FloatBuffer mVertexArray;
                private int mVertexCount;
                private int mVertexStride;
                private static final float[] TRIANGLE_COORDS = {0.0f, 0.57735026f, -0.5f, -0.28867513f, 0.5f, -0.28867513f};
                private static final float[] TRIANGLE_TEX_COORDS = {0.5f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f};
                private static final FloatBuffer TRIANGLE_BUF = GlUtil.createFloatBuffer(TRIANGLE_COORDS);
                private static final FloatBuffer TRIANGLE_TEX_BUF = GlUtil.createFloatBuffer(TRIANGLE_TEX_COORDS);
                private static final float[] RECTANGLE_COORDS = {-0.5f, -0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f};
                private static final float[] RECTANGLE_TEX_COORDS = {0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f};
                private static final FloatBuffer RECTANGLE_BUF = GlUtil.createFloatBuffer(RECTANGLE_COORDS);
                private static final FloatBuffer RECTANGLE_TEX_BUF = GlUtil.createFloatBuffer(RECTANGLE_TEX_COORDS);
                private static final float[] FULL_RECTANGLE_COORDS = {-1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f};
                private static final float[] FULL_RECTANGLE_TEX_COORDS = {0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f};
                private static final FloatBuffer FULL_RECTANGLE_BUF = GlUtil.createFloatBuffer(FULL_RECTANGLE_COORDS);
                private static final FloatBuffer FULL_RECTANGLE_TEX_BUF = GlUtil.createFloatBuffer(FULL_RECTANGLE_TEX_COORDS);

                public enum Prefab {
                    TRIANGLE,
                    RECTANGLE,
                    FULL_RECTANGLE
                }

/* 112 */       public Drawable2d(Prefab shape) {
                    switch (shape) {
                        case TRIANGLE:
/* 116 */                   this.mVertexArray = TRIANGLE_BUF;
/* 117 */                   this.mTexCoordArray = TRIANGLE_TEX_BUF;
/* 118 */                   this.mCoordsPerVertex = 2;
/* 119 */                   this.mVertexStride = this.mCoordsPerVertex * 4;
/* 120 */                   this.mVertexCount = TRIANGLE_COORDS.length / this.mCoordsPerVertex;
                            break;
                        case RECTANGLE:
/* 123 */                   this.mVertexArray = RECTANGLE_BUF;
/* 124 */                   this.mTexCoordArray = RECTANGLE_TEX_BUF;
/* 125 */                   this.mCoordsPerVertex = 2;
/* 126 */                   this.mVertexStride = this.mCoordsPerVertex * 4;
/* 127 */                   this.mVertexCount = RECTANGLE_COORDS.length / this.mCoordsPerVertex;
                            break;
                        case FULL_RECTANGLE:
/* 130 */                   this.mVertexArray = FULL_RECTANGLE_BUF;
/* 131 */                   this.mTexCoordArray = FULL_RECTANGLE_TEX_BUF;
/* 132 */                   this.mCoordsPerVertex = 2;
/* 133 */                   this.mVertexStride = this.mCoordsPerVertex * 4;
/* 134 */                   this.mVertexCount = FULL_RECTANGLE_COORDS.length / this.mCoordsPerVertex;
                            break;
                        default:
/* 137 */                   throw new RuntimeException("Unknown shape " + shape);
                    }
/* 139 */           this.mTexCoordStride = 8;
/* 140 */           this.mPrefab = shape;
                }

/* 148 */       public FloatBuffer getVertexArray() {
/* 149 */           return this.mVertexArray;
                }

/* 157 */       public FloatBuffer getTexCoordArray() {
/* 158 */           return this.mTexCoordArray;
                }

/* 164 */       public int getVertexCount() {
/* 165 */           return this.mVertexCount;
                }

/* 171 */       public int getVertexStride() {
/* 172 */           return this.mVertexStride;
                }

/* 178 */       public int getTexCoordStride() {
/* 179 */           return this.mTexCoordStride;
                }

/* 185 */       public int getCoordsPerVertex() {
/* 186 */           return this.mCoordsPerVertex;
                }

/* 190 */       public String toString() {
                    return this.mPrefab != null ? "[Drawable2d: " + this.mPrefab + "]" : "[Drawable2d: ...]";
                }
            }
