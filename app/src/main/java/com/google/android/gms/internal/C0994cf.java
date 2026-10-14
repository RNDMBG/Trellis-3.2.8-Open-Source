            package com.google.android.gms.internal;

            import java.math.BigInteger;
            import java.security.MessageDigest;
            import java.security.NoSuchAlgorithmException;
            import java.util.UUID;
            
            
            
            public final class C0994cf {

                
                private static final Object f1578gL;

                
                public static final String f1579hB;

                
                private static BigInteger f1580hC;

                static {
                    UUID uuidRandomUUID = UUID.randomUUID();
                    byte[] byteArray = BigInteger.valueOf(uuidRandomUUID.getLeastSignificantBits()).toByteArray();
                    byte[] byteArray2 = BigInteger.valueOf(uuidRandomUUID.getMostSignificantBits()).toByteArray();
                    String string = new BigInteger(1, byteArray).toString();
                    for (int i = 0; i < 2; i++) {
                        try {
                            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                            messageDigest.update(byteArray);
                            messageDigest.update(byteArray2);
                            byte[] bArr = new byte[8];
                            System.arraycopy(messageDigest.digest(), 0, bArr, 0, 8);
                            string = new BigInteger(1, bArr).toString();
                        } catch (NoSuchAlgorithmException e) {
                        }
                    }
                    f1579hB = string;
                    f1578gL = new Object();
                    f1580hC = BigInteger.ONE;
                }

                
                public static String m1408al() {
                    String string;
                    synchronized (f1578gL) {
                        try {
                            string = f1580hC.toString();
                            f1580hC.add(BigInteger.ONE);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return string;
                }
            }
