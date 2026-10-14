            package org.apache.commons.p005io;

            import java.io.IOException;
            import java.io.OutputStream;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 74 */    public class HexDump {
                public static final String EOL = System.getProperty("line.separator");
                private static final char[] _hexcodes = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
                private static final int[] _shifts = {28, 24, 20, 16, 12, 8, 4, 0};

/* 75 */        public static void dump(byte[] data, long offset, OutputStream stream, int index) throws IOException, ArrayIndexOutOfBoundsException, IllegalArgumentException {
/* 76 */            if (index < 0 || index >= data.length) {
/* 77 */                throw new ArrayIndexOutOfBoundsException("illegal index: " + index + " into array of length " + data.length);
                    }
/* 81 */            if (stream == null) {
/* 82 */                throw new IllegalArgumentException("cannot write to nullstream");
                    }
/* 84 */            long display_offset = offset + ((long) index);
/* 85 */            StringBuilder buffer = new StringBuilder(74);
                    for (int j = index; j < data.length; j += 16) {
/* 88 */                int chars_read = data.length - j;
/* 90 */                if (chars_read > 16) {
/* 91 */                    chars_read = 16;
                        }
/* 93 */                dump(buffer, display_offset).append(' ');
                        for (int k = 0; k < 16; k++) {
/* 95 */                    if (k < chars_read) {
/* 96 */                        dump(buffer, data[k + j]);
                            } else {
/* 98 */                        buffer.append("  ");
                            }
/* 100 */                   buffer.append(' ');
                        }
                        for (int k2 = 0; k2 < chars_read; k2++) {
/* 103 */                   if (data[k2 + j] >= 32 && data[k2 + j] < 127) {
/* 104 */                       buffer.append((char) data[k2 + j]);
                            } else {
/* 106 */                       buffer.append(FilenameUtils.EXTENSION_SEPARATOR);
                            }
                        }
/* 109 */               buffer.append(EOL);
/* 110 */               stream.write(buffer.toString().getBytes());
/* 111 */               stream.flush();
/* 112 */               buffer.setLength(0);
/* 113 */               display_offset += (long) chars_read;
                    }
                }

/* 139 */       private static StringBuilder dump(StringBuilder _lbuffer, long value) {
                    for (int j = 0; j < 8; j++) {
/* 141 */               _lbuffer.append(_hexcodes[((int) (value >> _shifts[j])) & 15]);
                    }
/* 144 */           return _lbuffer;
                }

/* 154 */       private static StringBuilder dump(StringBuilder _cbuffer, byte value) {
                    for (int j = 0; j < 2; j++) {
/* 156 */               _cbuffer.append(_hexcodes[(value >> _shifts[j + 6]) & 15]);
                    }
/* 158 */           return _cbuffer;
                }
            }
