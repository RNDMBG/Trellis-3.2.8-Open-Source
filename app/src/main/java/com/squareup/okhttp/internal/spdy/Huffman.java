            package com.squareup.okhttp.internal.spdy;

            import com.flurry.android.Constants;
            import java.io.ByteArrayOutputStream;
            import java.io.IOException;
            import java.io.OutputStream;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 78 */    class Huffman {
                private static final int[] CODES = {8184, 8388568, 268435426, 268435427, 268435428, 268435429, 268435430, 268435431, 268435432, 16777194, 1073741820, 268435433, 268435434, 1073741821, 268435435, 268435436, 268435437, 268435438, 268435439, 268435440, 268435441, 268435442, 1073741822, 268435443, 268435444, 268435445, 268435446, 268435447, 268435448, 268435449, 268435450, 268435451, 20, 1016, 1017, 4090, 8185, 21, 248, 2042, 1018, 1019, 249, 2043, 250, 22, 23, 24, 0, 1, 2, 25, 26, 27, 28, 29, 30, 31, 92, 251, 32764, 32, 4091, 1020, 8186, 33, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 252, 115, 253, 8187, 524272, 8188, 16380, 34, 32765, 3, 35, 4, 36, 5, 37, 38, 39, 6, 116, 117, 40, 41, 42, 7, 43, 118, 44, 8, 9, 45, 119, 120, 121, 122, 123, 32766, 2044, 16381, 8189, 268435452, 1048550, 4194258, 1048551, 1048552, 4194259, 4194260, 4194261, 8388569, 4194262, 8388570, 8388571, 8388572, 8388573, 8388574, 16777195, 8388575, 16777196, 16777197, 4194263, 8388576, 16777198, 8388577, 8388578, 8388579, 8388580, 2097116, 4194264, 8388581, 4194265, 8388582, 8388583, 16777199, 4194266, 2097117, 1048553, 4194267, 4194268, 8388584, 8388585, 2097118, 8388586, 4194269, 4194270, 16777200, 2097119, 4194271, 8388587, 8388588, 2097120, 2097121, 4194272, 2097122, 8388589, 4194273, 8388590, 8388591, 1048554, 4194274, 4194275, 4194276, 8388592, 4194277, 4194278, 8388593, 67108832, 67108833, 1048555, 524273, 4194279, 8388594, 4194280, 33554412, 67108834, 67108835, 67108836, 134217694, 134217695, 67108837, 16777201, 33554413, 524274, 2097123, 67108838, 134217696, 134217697, 67108839, 134217698, 16777202, 2097124, 2097125, 67108840, 67108841, 268435453, 134217699, 134217700, 134217701, 1048556, 16777203, 1048557, 2097126, 4194281, 2097127, 2097128, 8388595, 4194282, 4194283, 33554414, 33554415, 16777204, 16777205, 67108842, 8388596, 67108843, 134217702, 67108844, 67108845, 134217703, 134217704, 134217705, 134217706, 134217707, 268435454, 134217708, 134217709, 134217710, 134217711, 134217712, 67108846};
                private static final byte[] CODE_LENGTHS = {13, 23, 28, 28, 28, 28, 28, 28, 28, 24, 30, 28, 28, 30, 28, 28, 28, 28, 28, 28, 28, 28, 30, 28, 28, 28, 28, 28, 28, 28, 28, 28, 6, 10, 10, 12, 13, 6, 8, 11, 10, 10, 8, 11, 8, 6, 6, 6, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 7, 8, 15, 6, 12, 10, 13, 6, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 8, 7, 8, 13, 19, 13, 14, 6, 15, 5, 6, 5, 6, 5, 6, 6, 6, 5, 7, 7, 6, 6, 6, 5, 6, 7, 6, 5, 5, 6, 7, 7, 7, 7, 7, 15, 11, 14, 13, 28, 20, 22, 20, 20, 22, 22, 22, 23, 22, 23, 23, 23, 23, 23, 24, 23, 24, 24, 22, 23, 24, 23, 23, 23, 23, 21, 22, 23, 22, 23, 23, 24, 22, 21, 20, 22, 22, 23, 23, 21, 23, 22, 22, 24, 21, 22, 23, 23, 21, 21, 22, 21, 23, 22, 23, 23, 20, 22, 22, 22, 23, 22, 22, 23, 26, 26, 20, 19, 22, 23, 22, 25, 26, 26, 26, 27, 27, 26, 24, 25, 19, 21, 26, 27, 27, 26, 27, 24, 21, 21, 26, 26, 28, 27, 27, 27, 20, 24, 20, 21, 22, 21, 21, 23, 22, 22, 25, 25, 24, 24, 26, 23, 26, 27, 26, 26, 27, 27, 27, 27, 27, 28, 27, 27, 27, 27, 27, 26};
                private static final Huffman INSTANCE = new Huffman();
                private final Node root = new Node();

/* 79 */        public static Huffman get() {
/* 80 */            return INSTANCE;
                }

/* 84 */        private Huffman() {
/* 86 */            buildTree();
                }

/* 89 */        void encode(byte[] data, OutputStream out) throws IOException {
/* 90 */            long current = 0;
/* 91 */            int n = 0;
                    for (int i : data) {
/* 94 */                int b = i & 255;
/* 95 */                int code = CODES[b];
/* 96 */                int nbits = CODE_LENGTHS[b];
/* 99 */                current = (current << nbits) | ((long) code);
/* 100 */               n += nbits;
/* 102 */               while (n >= 8) {
/* 103 */                   n -= 8;
/* 104 */                   out.write((int) (current >> n));
                        }
                    }
/* 108 */           if (n > 0) {
/* 111 */               out.write((int) ((current << (8 - n)) | ((long) (255 >>> n))));
                    }
                }

/* 115 */       int encodedLength(byte[] bytes) {
/* 116 */           long len = 0;
                    for (int i : bytes) {
/* 119 */               int b = i & 255;
/* 120 */               len += (long) CODE_LENGTHS[b];
                    }
/* 123 */           return (int) ((7 + len) >> 3);
                }

/* 126 */       byte[] decode(byte[] buf) throws IOException {
/* 127 */           ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 128 */           Node node = this.root;
/* 129 */           int current = 0;
/* 130 */           int nbits = 0;
                    for (byte b : buf) {
/* 132 */               int b2 = b & Constants.UNKNOWN;
/* 133 */               current = (current << 8) | b2;
/* 134 */               nbits += 8;
/* 135 */               while (nbits >= 8) {
/* 136 */                   int c = (current >>> (nbits - 8)) & 255;
/* 137 */                   node = Node.access$000(node)[c];
/* 138 */                   if (Node.access$000(node) == null) {
/* 140 */                       baos.write(Node.access$100(node));
/* 141 */                       nbits -= Node.access$200(node);
/* 142 */                       node = this.root;
                            } else {
/* 145 */                       nbits -= 8;
                            }
                        }
                    }
/* 150 */           while (nbits > 0) {
/* 151 */               int c2 = (current << (8 - nbits)) & 255;
/* 152 */               Node node2 = Node.access$000(node)[c2];
/* 153 */               if (Node.access$000(node2) != null || Node.access$200(node2) > nbits) {
                            break;
                        }
/* 156 */               baos.write(Node.access$100(node2));
/* 157 */               nbits -= Node.access$200(node2);
/* 158 */               node = this.root;
                    }
/* 161 */           return baos.toByteArray();
                }

/* 164 */       private void buildTree() {
                    for (int i = 0; i < CODE_LENGTHS.length; i++) {
/* 166 */               addCode(i, CODES[i], CODE_LENGTHS[i]);
                    }
                }

/* 170 */       private void addCode(int sym, int code, byte len) {
/* 171 */           Node terminal = new Node(sym, len);
/* 173 */           Node current = this.root;
/* 174 */           while (len > 8) {
/* 175 */               len = (byte) (len - 8);
/* 176 */               int i = (code >>> len) & 255;
/* 177 */               if (Node.access$000(current) == null) {
/* 178 */                   throw new IllegalStateException("invalid dictionary: prefix not unique");
                        }
/* 180 */               if (Node.access$000(current)[i] == null) {
/* 181 */                   Node.access$000(current)[i] = new Node();
                        }
/* 183 */               current = Node.access$000(current)[i];
                    }
/* 186 */           int shift = 8 - len;
/* 187 */           int start = (code << shift) & 255;
/* 188 */           int end = 1 << shift;
                    for (int i2 = start; i2 < start + end; i2++) {
/* 190 */               Node.access$000(current)[i2] = terminal;
                    }
                }

/* 192 */       private static final class Node {
                    private final Node[] children;
                    private final int symbol;
                    private final int terminalBits;

/* 193 */           static /* synthetic */ Node[] access$000(Node x0) {
/* 194 */               return x0.children;
                    }

/* 193 */           static /* synthetic */ int access$100(Node x0) {
/* 194 */               return x0.symbol;
                    }

/* 193 */           static /* synthetic */ int access$200(Node x0) {
/* 194 */               return x0.terminalBits;
                    }

/* 205 */           Node() {
/* 207 */               this.children = new Node[256];
/* 208 */               this.symbol = 0;
/* 209 */               this.terminalBits = 0;
                    }

/* 217 */           Node(int symbol, int bits) {
/* 219 */               this.children = null;
/* 220 */               this.symbol = symbol;
/* 221 */               int b = bits & 7;
/* 222 */               this.terminalBits = b == 0 ? 8 : b;
                    }
                }
            }
