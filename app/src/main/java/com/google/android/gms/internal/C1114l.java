            package com.google.android.gms.internal;

            import java.nio.ByteBuffer;
            import java.security.InvalidAlgorithmParameterException;
            import java.security.InvalidKeyException;
            import java.security.NoSuchAlgorithmException;
            import java.security.SecureRandom;
            import javax.crypto.BadPaddingException;
            import javax.crypto.Cipher;
            import javax.crypto.IllegalBlockSizeException;
            import javax.crypto.NoSuchPaddingException;
            import javax.crypto.spec.IvParameterSpec;
            import javax.crypto.spec.SecretKeySpec;
            
            
            
            public class C1114l {

                
                private final SecureRandom f2131dB;

                
                private final InterfaceC1112j f2132di;

                
                public class a extends Exception {
                    public a() {
                    }

                    public a(Throwable th) {
                        super(th);
                    }
                }

                public C1114l(InterfaceC1112j interfaceC1112j, SecureRandom secureRandom) {
                    this.f2132di = interfaceC1112j;
                    this.f2131dB = secureRandom;
                }

                
                static void m2132a(byte[] bArr) {
                    for (int i = 0; i < bArr.length; i++) {
                        bArr[i] = (byte) (bArr[i] ^ 68);
                    }
                }

                
                public byte[] m2133b(String str) throws a {
                    try {
                        byte[] bArrMo1237a = this.f2132di.mo1237a(str, false);
                        if (bArrMo1237a.length != 32) {
                            throw new a();
                        }
                        byte[] bArr = new byte[16];
                        ByteBuffer.wrap(bArrMo1237a, 4, 16).get(bArr);
                        m2132a(bArr);
                        return bArr;
                    } catch (IllegalArgumentException e) {
                        throw new a(e);
                    }
                }

                
                public byte[] m2134c(byte[] bArr, String str) throws a {
                    if (bArr.length != 16) {
                        throw new a();
                    }
                    try {
                        byte[] bArrMo1237a = this.f2132di.mo1237a(str, false);
                        if (bArrMo1237a.length <= 16) {
                            throw new a();
                        }
                        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(bArrMo1237a.length);
                        byteBufferAllocate.put(bArrMo1237a);
                        byteBufferAllocate.flip();
                        byte[] bArr2 = new byte[16];
                        byte[] bArr3 = new byte[bArrMo1237a.length - 16];
                        byteBufferAllocate.get(bArr2);
                        byteBufferAllocate.get(bArr3);
                        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, "AES");
                        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                        cipher.init(2, secretKeySpec, new IvParameterSpec(bArr2));
                        return cipher.doFinal(bArr3);
                    } catch (IllegalArgumentException e) {
                        throw new a(e);
                    } catch (InvalidAlgorithmParameterException e2) {
                        throw new a(e2);
                    } catch (InvalidKeyException e3) {
                        throw new a(e3);
                    } catch (NoSuchAlgorithmException e4) {
                        throw new a(e4);
                    } catch (BadPaddingException e5) {
                        throw new a(e5);
                    } catch (IllegalBlockSizeException e6) {
                        throw new a(e6);
                    } catch (NoSuchPaddingException e7) {
                        throw new a(e7);
                    }
                }
            }
