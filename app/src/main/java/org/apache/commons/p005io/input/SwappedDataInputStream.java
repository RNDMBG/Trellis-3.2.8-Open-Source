            package org.apache.commons.p005io.input;

            import java.io.DataInput;
            import java.io.EOFException;
            import java.io.IOException;
            import java.io.InputStream;
            import org.apache.commons.p005io.EndianUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 44 */    public class SwappedDataInputStream extends ProxyInputStream implements DataInput {
/* 45 */        public SwappedDataInputStream(InputStream input) {
/* 46 */            super(input);
                }

                @Override // java.io.DataInput
/* 57 */        public boolean readBoolean() throws IOException {
                    return readByte() != 0;
                }

                @Override // java.io.DataInput
/* 69 */        public byte readByte() throws IOException {
/* 70 */            return (byte) this.in.read();
                }

                @Override // java.io.DataInput
/* 81 */        public char readChar() throws IOException {
/* 82 */            return (char) readShort();
                }

                @Override // java.io.DataInput
/* 93 */        public double readDouble() throws IOException {
/* 94 */            return EndianUtils.readSwappedDouble(this.in);
                }

                @Override // java.io.DataInput
/* 105 */       public float readFloat() throws IOException {
/* 106 */           return EndianUtils.readSwappedFloat(this.in);
                }

                @Override // java.io.DataInput
/* 118 */       public void readFully(byte[] data) throws IOException {
/* 119 */           readFully(data, 0, data.length);
                }

                @Override // java.io.DataInput
/* 134 */       public void readFully(byte[] data, int offset, int length) throws IOException {
/* 135 */           int remaining = length;
/* 137 */           while (remaining > 0) {
/* 139 */               int location = (offset + length) - remaining;
/* 140 */               int count = read(data, location, remaining);
/* 142 */               if (-1 == count) {
/* 144 */                   throw new EOFException();
                        }
/* 147 */               remaining -= count;
                    }
                }

                @Override // java.io.DataInput
/* 159 */       public int readInt() throws IOException {
/* 160 */           return EndianUtils.readSwappedInteger(this.in);
                }

                @Override // java.io.DataInput
/* 171 */       public String readLine() throws IOException {
/* 172 */           throw new UnsupportedOperationException("Operation not supported: readLine()");
                }

                @Override // java.io.DataInput
/* 184 */       public long readLong() throws IOException {
/* 185 */           return EndianUtils.readSwappedLong(this.in);
                }

                @Override // java.io.DataInput
/* 196 */       public short readShort() throws IOException {
/* 197 */           return EndianUtils.readSwappedShort(this.in);
                }

                @Override // java.io.DataInput
/* 208 */       public int readUnsignedByte() throws IOException {
/* 209 */           return this.in.read();
                }

                @Override // java.io.DataInput
/* 220 */       public int readUnsignedShort() throws IOException {
/* 221 */           return EndianUtils.readSwappedUnsignedShort(this.in);
                }

                @Override // java.io.DataInput
/* 232 */       public String readUTF() throws IOException {
/* 233 */           throw new UnsupportedOperationException("Operation not supported: readUTF()");
                }

                @Override // java.io.DataInput
/* 246 */       public int skipBytes(int count) throws IOException {
/* 247 */           return (int) this.in.skip(count);
                }
            }
