            package org.apache.commons.p005io.input;

            import java.io.DataInput;
            import java.io.EOFException;
            import java.io.IOException;
            import java.io.InputStream;
            import org.apache.commons.p005io.EndianUtils;
            
            
    public class SwappedDataInputStream extends ProxyInputStream implements DataInput {
        public SwappedDataInputStream(InputStream input) {
            super(input);
                }

                @Override 
        public boolean readBoolean() throws IOException {
                    return readByte() != 0;
                }

                @Override 
        public byte readByte() throws IOException {
            return (byte) this.in.read();
                }

                @Override 
        public char readChar() throws IOException {
            return (char) readShort();
                }

                @Override 
        public double readDouble() throws IOException {
            return EndianUtils.readSwappedDouble(this.in);
                }

                @Override 
       public float readFloat() throws IOException {
           return EndianUtils.readSwappedFloat(this.in);
                }

                @Override 
       public void readFully(byte[] data) throws IOException {
           readFully(data, 0, data.length);
                }

                @Override 
       public void readFully(byte[] data, int offset, int length) throws IOException {
           int remaining = length;
           while (remaining > 0) {
               int location = (offset + length) - remaining;
               int count = read(data, location, remaining);
               if (-1 == count) {
                   throw new EOFException();
                        }
               remaining -= count;
                    }
                }

                @Override 
       public int readInt() throws IOException {
           return EndianUtils.readSwappedInteger(this.in);
                }

                @Override 
       public String readLine() throws IOException {
           throw new UnsupportedOperationException("Operation not supported: readLine()");
                }

                @Override 
       public long readLong() throws IOException {
           return EndianUtils.readSwappedLong(this.in);
                }

                @Override 
       public short readShort() throws IOException {
           return EndianUtils.readSwappedShort(this.in);
                }

                @Override 
       public int readUnsignedByte() throws IOException {
           return this.in.read();
                }

                @Override 
       public int readUnsignedShort() throws IOException {
           return EndianUtils.readSwappedUnsignedShort(this.in);
                }

                @Override 
       public String readUTF() throws IOException {
           throw new UnsupportedOperationException("Operation not supported: readUTF()");
                }

                @Override 
       public int skipBytes(int count) throws IOException {
           return (int) this.in.skip(count);
                }
            }
