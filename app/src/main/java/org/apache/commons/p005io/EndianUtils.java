            package org.apache.commons.p005io;

            import com.flurry.android.Constants;
            import java.io.EOFException;
            import java.io.IOException;
            import java.io.InputStream;
            import java.io.OutputStream;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 54 */    public class EndianUtils {
/* 55 */        public static short swapShort(short value) {
/* 56 */            return (short) ((((value >> 0) & 255) << 8) + (((value >> 8) & 255) << 0));
                }

/* 65 */        public static int swapInteger(int value) {
/* 66 */            return (((value >> 0) & 255) << 24) + (((value >> 8) & 255) << 16) + (((value >> 16) & 255) << 8) + (((value >> 24) & 255) << 0);
                }

/* 78 */        public static long swapLong(long value) {
/* 79 */            return (((value >> 0) & 255) << 56) + (((value >> 8) & 255) << 48) + (((value >> 16) & 255) << 40) + (((value >> 24) & 255) << 32) + (((value >> 32) & 255) << 24) + (((value >> 40) & 255) << 16) + (((value >> 48) & 255) << 8) + (((value >> 56) & 255) << 0);
                }

/* 95 */        public static float swapFloat(float value) {
/* 96 */            return Float.intBitsToFloat(swapInteger(Float.floatToIntBits(value)));
                }

/* 104 */       public static double swapDouble(double value) {
/* 105 */           return Double.longBitsToDouble(swapLong(Double.doubleToLongBits(value)));
                }

/* 117 */       public static void writeSwappedShort(byte[] data, int offset, short value) {
/* 118 */           data[offset + 0] = (byte) ((value >> 0) & 255);
/* 119 */           data[offset + 1] = (byte) ((value >> 8) & 255);
                }

/* 129 */       public static short readSwappedShort(byte[] data, int offset) {
/* 130 */           return (short) (((data[offset + 0] & Constants.UNKNOWN) << 0) + ((data[offset + 1] & Constants.UNKNOWN) << 8));
                }

/* 142 */       public static int readSwappedUnsignedShort(byte[] data, int offset) {
/* 143 */           return ((data[offset + 0] & Constants.UNKNOWN) << 0) + ((data[offset + 1] & Constants.UNKNOWN) << 8);
                }

/* 154 */       public static void writeSwappedInteger(byte[] data, int offset, int value) {
/* 155 */           data[offset + 0] = (byte) ((value >> 0) & 255);
/* 156 */           data[offset + 1] = (byte) ((value >> 8) & 255);
/* 157 */           data[offset + 2] = (byte) ((value >> 16) & 255);
/* 158 */           data[offset + 3] = (byte) ((value >> 24) & 255);
                }

/* 168 */       public static int readSwappedInteger(byte[] data, int offset) {
/* 169 */           return ((data[offset + 0] & Constants.UNKNOWN) << 0) + ((data[offset + 1] & Constants.UNKNOWN) << 8) + ((data[offset + 2] & Constants.UNKNOWN) << 16) + ((data[offset + 3] & Constants.UNKNOWN) << 24);
                }

/* 183 */       public static long readSwappedUnsignedInteger(byte[] data, int offset) {
/* 184 */           long low = ((data[offset + 0] & Constants.UNKNOWN) << 0) + ((data[offset + 1] & Constants.UNKNOWN) << 8) + ((data[offset + 2] & Constants.UNKNOWN) << 16);
/* 188 */           long high = data[offset + 3] & Constants.UNKNOWN;
/* 190 */           return (high << 24) + (4294967295L & low);
                }

/* 200 */       public static void writeSwappedLong(byte[] data, int offset, long value) {
/* 201 */           data[offset + 0] = (byte) ((value >> 0) & 255);
/* 202 */           data[offset + 1] = (byte) ((value >> 8) & 255);
/* 203 */           data[offset + 2] = (byte) ((value >> 16) & 255);
/* 204 */           data[offset + 3] = (byte) ((value >> 24) & 255);
/* 205 */           data[offset + 4] = (byte) ((value >> 32) & 255);
/* 206 */           data[offset + 5] = (byte) ((value >> 40) & 255);
/* 207 */           data[offset + 6] = (byte) ((value >> 48) & 255);
/* 208 */           data[offset + 7] = (byte) ((value >> 56) & 255);
                }

/* 218 */       public static long readSwappedLong(byte[] data, int offset) {
/* 219 */           long low = ((data[offset + 0] & Constants.UNKNOWN) << 0) + ((data[offset + 1] & Constants.UNKNOWN) << 8) + ((data[offset + 2] & Constants.UNKNOWN) << 16) + ((data[offset + 3] & Constants.UNKNOWN) << 24);
/* 224 */           long high = ((data[offset + 4] & Constants.UNKNOWN) << 0) + ((data[offset + 5] & Constants.UNKNOWN) << 8) + ((data[offset + 6] & Constants.UNKNOWN) << 16) + ((data[offset + 7] & Constants.UNKNOWN) << 24);
/* 229 */           return (high << 32) + (4294967295L & low);
                }

/* 239 */       public static void writeSwappedFloat(byte[] data, int offset, float value) {
/* 240 */           writeSwappedInteger(data, offset, Float.floatToIntBits(value));
                }

/* 250 */       public static float readSwappedFloat(byte[] data, int offset) {
/* 251 */           return Float.intBitsToFloat(readSwappedInteger(data, offset));
                }

/* 261 */       public static void writeSwappedDouble(byte[] data, int offset, double value) {
/* 262 */           writeSwappedLong(data, offset, Double.doubleToLongBits(value));
                }

/* 272 */       public static double readSwappedDouble(byte[] data, int offset) {
/* 273 */           return Double.longBitsToDouble(readSwappedLong(data, offset));
                }

/* 285 */       public static void writeSwappedShort(OutputStream output, short value) throws IOException {
/* 286 */           output.write((byte) ((value >> 0) & 255));
/* 287 */           output.write((byte) ((value >> 8) & 255));
                }

/* 299 */       public static short readSwappedShort(InputStream input) throws IOException {
/* 300 */           return (short) (((read(input) & 255) << 0) + ((read(input) & 255) << 8));
                }

/* 313 */       public static int readSwappedUnsignedShort(InputStream input) throws IOException {
/* 314 */           int value1 = read(input);
/* 315 */           int value2 = read(input);
/* 317 */           return ((value1 & 255) << 0) + ((value2 & 255) << 8);
                }

/* 330 */       public static void writeSwappedInteger(OutputStream output, int value) throws IOException {
/* 331 */           output.write((byte) ((value >> 0) & 255));
/* 332 */           output.write((byte) ((value >> 8) & 255));
/* 333 */           output.write((byte) ((value >> 16) & 255));
/* 334 */           output.write((byte) ((value >> 24) & 255));
                }

/* 346 */       public static int readSwappedInteger(InputStream input) throws IOException {
/* 347 */           int value1 = read(input);
/* 348 */           int value2 = read(input);
/* 349 */           int value3 = read(input);
/* 350 */           int value4 = read(input);
/* 352 */           return ((value1 & 255) << 0) + ((value2 & 255) << 8) + ((value3 & 255) << 16) + ((value4 & 255) << 24);
                }

/* 367 */       public static long readSwappedUnsignedInteger(InputStream input) throws IOException {
/* 368 */           int value1 = read(input);
/* 369 */           int value2 = read(input);
/* 370 */           int value3 = read(input);
/* 371 */           int value4 = read(input);
/* 373 */           long low = ((value1 & 255) << 0) + ((value2 & 255) << 8) + ((value3 & 255) << 16);
/* 377 */           long high = value4 & 255;
/* 379 */           return (high << 24) + (4294967295L & low);
                }

/* 391 */       public static void writeSwappedLong(OutputStream output, long value) throws IOException {
/* 392 */           output.write((byte) ((value >> 0) & 255));
/* 393 */           output.write((byte) ((value >> 8) & 255));
/* 394 */           output.write((byte) ((value >> 16) & 255));
/* 395 */           output.write((byte) ((value >> 24) & 255));
/* 396 */           output.write((byte) ((value >> 32) & 255));
/* 397 */           output.write((byte) ((value >> 40) & 255));
/* 398 */           output.write((byte) ((value >> 48) & 255));
/* 399 */           output.write((byte) ((value >> 56) & 255));
                }

/* 411 */       public static long readSwappedLong(InputStream input) throws IOException {
/* 412 */           byte[] bytes = new byte[8];
                    for (int i = 0; i < 8; i++) {
/* 414 */               bytes[i] = (byte) read(input);
                    }
/* 416 */           return readSwappedLong(bytes, 0);
                }

/* 428 */       public static void writeSwappedFloat(OutputStream output, float value) throws IOException {
/* 429 */           writeSwappedInteger(output, Float.floatToIntBits(value));
                }

/* 441 */       public static float readSwappedFloat(InputStream input) throws IOException {
/* 442 */           return Float.intBitsToFloat(readSwappedInteger(input));
                }

/* 454 */       public static void writeSwappedDouble(OutputStream output, double value) throws IOException {
/* 455 */           writeSwappedLong(output, Double.doubleToLongBits(value));
                }

/* 467 */       public static double readSwappedDouble(InputStream input) throws IOException {
/* 468 */           return Double.longBitsToDouble(readSwappedLong(input));
                }

/* 479 */       private static int read(InputStream input) throws IOException {
/* 480 */           int value = input.read();
/* 482 */           if (-1 == value) {
/* 483 */               throw new EOFException("Unexpected EOF reached");
                    }
/* 486 */           return value;
                }
            }
