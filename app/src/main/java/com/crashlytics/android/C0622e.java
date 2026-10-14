            package com.crashlytics.android;

            import android.os.Process;
            import com.crashlytics.android.internal.C0681ab;
            import com.crashlytics.android.internal.C0694ao;
            import java.nio.ByteBuffer;
            import java.nio.ByteOrder;
            import java.util.Date;
            import java.util.Locale;
            import java.util.concurrent.atomic.AtomicLong;
            
            
            
    final class C0622e {

                
                private static final AtomicLong f200a = new AtomicLong(0);

                
                private static String f201b;

        public C0622e(C0694ao c0694ao) {
            long time = new Date().getTime();
                    ByteBuffer byteBufferAllocate = ByteBuffer.allocate(4);
                    byteBufferAllocate.putInt((int) (time / 1000));
                    byteBufferAllocate.order(ByteOrder.BIG_ENDIAN);
                    byteBufferAllocate.position(0);
                    byte[] bArrArray = byteBufferAllocate.array();
                    byte[] bArrM67a = m67a(time % 1000);
            byte[] bArrM67a2 = m67a(f200a.incrementAndGet());
            byte[] bArrM67a3 = m67a(Integer.valueOf(Process.myPid()).shortValue());
            byte[] bArr = {bArrArray[0], bArrArray[1], bArrArray[2], bArrArray[3], bArrM67a[0], bArrM67a[1], bArrM67a2[0], bArrM67a2[1], bArrM67a3[0], bArrM67a3[1]};
            String strM205a = C0681ab.m205a(c0694ao.m259b());
            String strM206a = C0681ab.m206a(bArr);
                    f201b = String.format(Locale.US, "%s-%s-%s-%s", strM206a.substring(0, 12), strM206a.substring(12, 16), strM206a.subSequence(16, 20), strM205a.substring(0, 12)).toUpperCase(Locale.US);
                }

                
        private static byte[] m67a(long j) {
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate(2);
            byteBufferAllocate.putShort((short) j);
            byteBufferAllocate.order(ByteOrder.BIG_ENDIAN);
            byteBufferAllocate.position(0);
            return byteBufferAllocate.array();
                }

        public final String toString() {
            return f201b;
                }
            }
