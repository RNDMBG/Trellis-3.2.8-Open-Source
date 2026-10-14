            package com.google.android.gms.common.internal.safeparcel;

            import android.os.Bundle;
            import android.os.IBinder;
            import android.os.Parcel;
            import android.os.Parcelable;
            import android.support.v4.internal.view.SupportMenu;
            import java.math.BigDecimal;
            import java.math.BigInteger;
            import java.util.ArrayList;
            import java.util.List;
            
            
            
            public class C0901a {

                
                public static class a extends RuntimeException {
                    public a(String str, Parcel parcel) {
                        super(str + " Parcel: pos=" + parcel.dataPosition() + " size=" + parcel.dataSize());
                    }
                }

                
                public static int m1095a(Parcel parcel, int i) {
                    return (i & (-65536)) != -65536 ? (i >> 16) & SupportMenu.USER_MASK : parcel.readInt();
                }

                
                public static <T extends Parcelable> T m1096a(Parcel parcel, int i, Parcelable.Creator<T> creator) {
                    int iM1095a = m1095a(parcel, i);
                    int iDataPosition = parcel.dataPosition();
                    if (iM1095a == 0) {
                        return null;
                    }
                    T tCreateFromParcel = creator.createFromParcel(parcel);
                    parcel.setDataPosition(iM1095a + iDataPosition);
                    return tCreateFromParcel;
                }

                
                private static void m1097a(Parcel parcel, int i, int i2) {
                    int iM1095a = m1095a(parcel, i);
                    if (iM1095a != i2) {
                        throw new a("Expected size " + i2 + " got " + iM1095a + " (0x" + Integer.toHexString(iM1095a) + ")", parcel);
                    }
                }

                
                public static void m1098a(Parcel parcel, int i, List list, ClassLoader classLoader) {
                    int iM1095a = m1095a(parcel, i);
                    int iDataPosition = parcel.dataPosition();
                    if (iM1095a == 0) {
                        return;
                    }
                    parcel.readList(list, classLoader);
                    parcel.setDataPosition(iM1095a + iDataPosition);
                }

                
                public static void m1099b(Parcel parcel, int i) {
                    parcel.setDataPosition(m1095a(parcel, i) + parcel.dataPosition());
                }

                
                public static <T> T[] m1100b(Parcel parcel, int i, Parcelable.Creator<T> creator) {
                    int iM1095a = m1095a(parcel, i);
                    int iDataPosition = parcel.dataPosition();
                    if (iM1095a == 0) {
                        return null;
                    }
                    T[] tArr = (T[]) parcel.createTypedArray(creator);
                    parcel.setDataPosition(iM1095a + iDataPosition);
                    return tArr;
                }

                
                public static <T> ArrayList<T> m1101c(Parcel parcel, int i, Parcelable.Creator<T> creator) {
                    int iM1095a = m1095a(parcel, i);
                    int iDataPosition = parcel.dataPosition();
                    if (iM1095a == 0) {
                        return null;
                    }
                    ArrayList<T> arrayListCreateTypedArrayList = parcel.createTypedArrayList(creator);
                    parcel.setDataPosition(iM1095a + iDataPosition);
                    return arrayListCreateTypedArrayList;
                }

                
                public static boolean m1102c(Parcel parcel, int i) {
                    m1097a(parcel, i, 4);
                    return parcel.readInt() != 0;
                }

                
                public static byte m1103d(Parcel parcel, int i) {
                    m1097a(parcel, i, 4);
                    return (byte) parcel.readInt();
                }

                
                public static short m1104e(Parcel parcel, int i) {
                    m1097a(parcel, i, 4);
                    return (short) parcel.readInt();
                }

                
                public static int m1105f(Parcel parcel, int i) {
                    m1097a(parcel, i, 4);
                    return parcel.readInt();
                }

                
                public static long m1106g(Parcel parcel, int i) {
                    m1097a(parcel, i, 8);
                    return parcel.readLong();
                }

                
                public static BigInteger m1107h(Parcel parcel, int i) {
                    int iM1095a = m1095a(parcel, i);
                    int iDataPosition = parcel.dataPosition();
                    if (iM1095a == 0) {
                        return null;
                    }
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    parcel.setDataPosition(iM1095a + iDataPosition);
                    return new BigInteger(bArrCreateByteArray);
                }

                
                public static float m1108i(Parcel parcel, int i) {
                    m1097a(parcel, i, 4);
                    return parcel.readFloat();
                }

                
                public static int m1109i(Parcel parcel) {
                    return parcel.readInt();
                }

                
                public static double m1110j(Parcel parcel, int i) {
                    m1097a(parcel, i, 8);
                    return parcel.readDouble();
                }

                
                public static int m1111j(Parcel parcel) {
                    int iM1109i = m1109i(parcel);
                    int iM1095a = m1095a(parcel, iM1109i);
                    int iDataPosition = parcel.dataPosition();
                    if (m1126y(iM1109i) != 20293) {
                        throw new a("Expected object header. Got 0x" + Integer.toHexString(iM1109i), parcel);
                    }
                    int i = iDataPosition + iM1095a;
                    if (i < iDataPosition || i > parcel.dataSize()) {
                        throw new a("Size read is invalid start=" + iDataPosition + " end=" + i, parcel);
                    }
                    return i;
                }

                
                public static BigDecimal m1112k(Parcel parcel, int i) {
                    int iM1095a = m1095a(parcel, i);
                    int iDataPosition = parcel.dataPosition();
                    if (iM1095a == 0) {
                        return null;
                    }
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    int i2 = parcel.readInt();
                    parcel.setDataPosition(iM1095a + iDataPosition);
                    return new BigDecimal(new BigInteger(bArrCreateByteArray), i2);
                }

                
                public static String m1113l(Parcel parcel, int i) {
                    int iM1095a = m1095a(parcel, i);
                    int iDataPosition = parcel.dataPosition();
                    if (iM1095a == 0) {
                        return null;
                    }
                    String string = parcel.readString();
                    parcel.setDataPosition(iM1095a + iDataPosition);
                    return string;
                }

                
                public static IBinder m1114m(Parcel parcel, int i) {
                    int iM1095a = m1095a(parcel, i);
                    int iDataPosition = parcel.dataPosition();
                    if (iM1095a == 0) {
                        return null;
                    }
                    IBinder strongBinder = parcel.readStrongBinder();
                    parcel.setDataPosition(iM1095a + iDataPosition);
                    return strongBinder;
                }

                
                public static Bundle m1115n(Parcel parcel, int i) {
                    int iM1095a = m1095a(parcel, i);
                    int iDataPosition = parcel.dataPosition();
                    if (iM1095a == 0) {
                        return null;
                    }
                    Bundle bundle = parcel.readBundle();
                    parcel.setDataPosition(iM1095a + iDataPosition);
                    return bundle;
                }

                
                public static byte[] m1116o(Parcel parcel, int i) {
                    int iM1095a = m1095a(parcel, i);
                    int iDataPosition = parcel.dataPosition();
                    if (iM1095a == 0) {
                        return null;
                    }
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    parcel.setDataPosition(iM1095a + iDataPosition);
                    return bArrCreateByteArray;
                }

                
                public static boolean[] m1117p(Parcel parcel, int i) {
                    int iM1095a = m1095a(parcel, i);
                    int iDataPosition = parcel.dataPosition();
                    if (iM1095a == 0) {
                        return null;
                    }
                    boolean[] zArrCreateBooleanArray = parcel.createBooleanArray();
                    parcel.setDataPosition(iM1095a + iDataPosition);
                    return zArrCreateBooleanArray;
                }

                
                public static int[] m1118q(Parcel parcel, int i) {
                    int iM1095a = m1095a(parcel, i);
                    int iDataPosition = parcel.dataPosition();
                    if (iM1095a == 0) {
                        return null;
                    }
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    parcel.setDataPosition(iM1095a + iDataPosition);
                    return iArrCreateIntArray;
                }

                
                public static long[] m1119r(Parcel parcel, int i) {
                    int iM1095a = m1095a(parcel, i);
                    int iDataPosition = parcel.dataPosition();
                    if (iM1095a == 0) {
                        return null;
                    }
                    long[] jArrCreateLongArray = parcel.createLongArray();
                    parcel.setDataPosition(iM1095a + iDataPosition);
                    return jArrCreateLongArray;
                }

                
                public static BigInteger[] m1120s(Parcel parcel, int i) {
                    int iM1095a = m1095a(parcel, i);
                    int iDataPosition = parcel.dataPosition();
                    if (iM1095a == 0) {
                        return null;
                    }
                    int i2 = parcel.readInt();
                    BigInteger[] bigIntegerArr = new BigInteger[i2];
                    for (int i3 = 0; i3 < i2; i3++) {
                        bigIntegerArr[i3] = new BigInteger(parcel.createByteArray());
                    }
                    parcel.setDataPosition(iDataPosition + iM1095a);
                    return bigIntegerArr;
                }

                
                public static float[] m1121t(Parcel parcel, int i) {
                    int iM1095a = m1095a(parcel, i);
                    int iDataPosition = parcel.dataPosition();
                    if (iM1095a == 0) {
                        return null;
                    }
                    float[] fArrCreateFloatArray = parcel.createFloatArray();
                    parcel.setDataPosition(iM1095a + iDataPosition);
                    return fArrCreateFloatArray;
                }

                
                public static double[] m1122u(Parcel parcel, int i) {
                    int iM1095a = m1095a(parcel, i);
                    int iDataPosition = parcel.dataPosition();
                    if (iM1095a == 0) {
                        return null;
                    }
                    double[] dArrCreateDoubleArray = parcel.createDoubleArray();
                    parcel.setDataPosition(iM1095a + iDataPosition);
                    return dArrCreateDoubleArray;
                }

                
                public static BigDecimal[] m1123v(Parcel parcel, int i) {
                    int iM1095a = m1095a(parcel, i);
                    int iDataPosition = parcel.dataPosition();
                    if (iM1095a == 0) {
                        return null;
                    }
                    int i2 = parcel.readInt();
                    BigDecimal[] bigDecimalArr = new BigDecimal[i2];
                    for (int i3 = 0; i3 < i2; i3++) {
                        byte[] bArrCreateByteArray = parcel.createByteArray();
                        bigDecimalArr[i3] = new BigDecimal(new BigInteger(bArrCreateByteArray), parcel.readInt());
                    }
                    parcel.setDataPosition(iDataPosition + iM1095a);
                    return bigDecimalArr;
                }

                
                public static String[] m1124w(Parcel parcel, int i) {
                    int iM1095a = m1095a(parcel, i);
                    int iDataPosition = parcel.dataPosition();
                    if (iM1095a == 0) {
                        return null;
                    }
                    String[] strArrCreateStringArray = parcel.createStringArray();
                    parcel.setDataPosition(iM1095a + iDataPosition);
                    return strArrCreateStringArray;
                }

                
                public static ArrayList<String> m1125x(Parcel parcel, int i) {
                    int iM1095a = m1095a(parcel, i);
                    int iDataPosition = parcel.dataPosition();
                    if (iM1095a == 0) {
                        return null;
                    }
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    parcel.setDataPosition(iM1095a + iDataPosition);
                    return arrayListCreateStringArrayList;
                }

                
                public static int m1126y(int i) {
                    return 65535 & i;
                }

                
                public static Parcel m1127y(Parcel parcel, int i) {
                    int iM1095a = m1095a(parcel, i);
                    int iDataPosition = parcel.dataPosition();
                    if (iM1095a == 0) {
                        return null;
                    }
                    Parcel parcelObtain = Parcel.obtain();
                    parcelObtain.appendFrom(parcel, iDataPosition, iM1095a);
                    parcel.setDataPosition(iM1095a + iDataPosition);
                    return parcelObtain;
                }

                
                public static Parcel[] m1128z(Parcel parcel, int i) {
                    int iM1095a = m1095a(parcel, i);
                    int iDataPosition = parcel.dataPosition();
                    if (iM1095a == 0) {
                        return null;
                    }
                    int i2 = parcel.readInt();
                    Parcel[] parcelArr = new Parcel[i2];
                    for (int i3 = 0; i3 < i2; i3++) {
                        int i4 = parcel.readInt();
                        if (i4 != 0) {
                            int iDataPosition2 = parcel.dataPosition();
                            Parcel parcelObtain = Parcel.obtain();
                            parcelObtain.appendFrom(parcel, iDataPosition2, i4);
                            parcelArr[i3] = parcelObtain;
                            parcel.setDataPosition(i4 + iDataPosition2);
                        } else {
                            parcelArr[i3] = null;
                        }
                    }
                    parcel.setDataPosition(iDataPosition + iM1095a);
                    return parcelArr;
                }
            }
