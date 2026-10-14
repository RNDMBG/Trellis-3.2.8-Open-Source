            package co.vine.android.gles;

            import java.nio.ByteBuffer;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 70 */    public class GeneratedTexture {
                private static final int BLACK = 0;
                private static final int BLUE = 16711680;
                private static final int BYTES_PER_PIXEL = 4;
                private static final int CYAN = 16776960;
                private static final int FORMAT = 6408;
                private static final int GREEN = 65280;
                private static final int HALF = Integer.MIN_VALUE;
                private static final int LOW = 1073741824;
                private static final int MAGENTA = 16711935;
                private static final int OPAQUE = -16777216;
                private static final int RED = 255;
                private static final int TEX_SIZE = 64;
                private static final int TRANSP = 0;
                private static final int WHITE = 16777215;
                private static final int YELLOW = 65535;
                private static final int[] GRID = {-16776961, -16711681, -16711936, -65281, -1, 1073742079, 1073807104, -16711681, -65281, 65280, -2147483393, -16777216, -256, -65281, -256, -65536};
                private static final ByteBuffer sCoarseImageData = generateCoarseData();
                private static final ByteBuffer sFineImageData = generateFineData();

                public enum Image {
                    COARSE,
                    FINE
                }

/* 71 */        public static int createTestTexture(Image which) {
                    ByteBuffer buf;
                    switch (which) {
                        case COARSE:
/* 74 */                    buf = sCoarseImageData;
                            break;
                        case FINE:
/* 77 */                    buf = sFineImageData;
                            break;
                        default:
/* 80 */                    throw new RuntimeException("unknown image");
                    }
/* 82 */            return GlUtil.createImageTexture(buf, 64, 64, FORMAT);
                }

/* 99 */        private static ByteBuffer generateCoarseData() {
/* 100 */           byte[] buf = new byte[16384];
                    for (int i = 0; i < buf.length; i += 4) {
/* 104 */               int texRow = (i / 4) / 64;
/* 105 */               int texCol = (i / 4) % 64;
/* 107 */               int gridRow = texRow / 16;
/* 108 */               int gridCol = texCol / 16;
/* 109 */               int gridIndex = (gridRow * 4) + gridCol;
/* 111 */               int color = GRID[gridIndex];
/* 114 */               if (i == 0) {
/* 115 */                   color = -1;
                        } else if (i == buf.length - 4) {
/* 117 */                   color = -1;
                        }
/* 121 */               int red = color & 255;
/* 122 */               int green = (color >> 8) & 255;
/* 123 */               int blue = (color >> 16) & 255;
/* 124 */               int alpha = (color >> 24) & 255;
/* 127 */               float alphaM = alpha / 255.0f;
/* 128 */               buf[i] = (byte) (red * alphaM);
/* 129 */               buf[i + 1] = (byte) (green * alphaM);
/* 130 */               buf[i + 2] = (byte) (blue * alphaM);
/* 131 */               buf[i + 3] = (byte) alpha;
                    }
/* 134 */           ByteBuffer byteBuf = ByteBuffer.allocateDirect(buf.length);
/* 135 */           byteBuf.put(buf);
/* 136 */           byteBuf.position(0);
/* 137 */           return byteBuf;
                }

/* 145 */       private static ByteBuffer generateFineData() {
/* 146 */           byte[] buf = new byte[16384];
/* 149 */           checkerPattern(buf, 0, 0, 32, 32, -16776961, -65536, 1);
/* 152 */           checkerPattern(buf, 32, 32, 64, 64, -16776961, -16711936, 2);
/* 155 */           checkerPattern(buf, 0, 32, 32, 64, -65536, -16711936, 4);
/* 158 */           checkerPattern(buf, 32, 0, 64, 32, -1, -16777216, 8);
/* 161 */           ByteBuffer byteBuf = ByteBuffer.allocateDirect(buf.length);
/* 162 */           byteBuf.put(buf);
/* 163 */           byteBuf.position(0);
/* 164 */           return byteBuf;
                }

/* 168 */       private static void checkerPattern(byte[] buf, int left, int top, int right, int bottom, int color1, int color2, int bit) {
                    int color;
                    for (int row = top; row < bottom; row++) {
/* 170 */               int rowOffset = row * 64 * 4;
                        for (int col = left; col < right; col++) {
/* 172 */                   int offset = rowOffset + (col * 4);
/* 174 */                   if (((row & bit) ^ (col & bit)) == 0) {
/* 175 */                       color = color1;
                            } else {
/* 177 */                       color = color2;
                            }
/* 181 */                   int red = color & 255;
/* 182 */                   int green = (color >> 8) & 255;
/* 183 */                   int blue = (color >> 16) & 255;
/* 184 */                   int alpha = (color >> 24) & 255;
/* 187 */                   float alphaM = alpha / 255.0f;
/* 188 */                   buf[offset] = (byte) (red * alphaM);
/* 189 */                   buf[offset + 1] = (byte) (green * alphaM);
/* 190 */                   buf[offset + 2] = (byte) (blue * alphaM);
/* 191 */                   buf[offset + 3] = (byte) alpha;
                        }
                    }
                }
            }
