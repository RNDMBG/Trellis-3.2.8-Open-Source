            package com.google.android.gms.common.data;

            import android.database.CharArrayBuffer;
            import android.database.CursorIndexOutOfBoundsException;
            import android.database.CursorWindow;
            import android.net.Uri;
            import android.os.Bundle;
            import android.os.Parcel;
            import android.util.Log;
            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            import com.google.android.gms.internal.C1028dm;
            import java.util.ArrayList;
            import java.util.HashMap;
            import java.util.Map;
            
            /* JADX INFO: renamed from: com.google.android.gms.common.data.d */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class C0891d implements SafeParcelable {
                public static final C0892e CREATOR = new C0892e();

                /* JADX INFO: renamed from: ju */
                private static final a f956ju = new AnonymousClass1(new String[0], null);

                /* JADX INFO: renamed from: iC */
                private final int f957iC;

                /* JADX INFO: renamed from: iM */
                private final int f958iM;

                /* JADX INFO: renamed from: jm */
                private final String[] f959jm;

                /* JADX INFO: renamed from: jn */
                Bundle f960jn;

                /* JADX INFO: renamed from: jo */
                private final CursorWindow[] f961jo;

                /* JADX INFO: renamed from: jp */
                private final Bundle f962jp;

                /* JADX INFO: renamed from: jq */
                int[] f963jq;

                /* JADX INFO: renamed from: jr */
                int f964jr;

                /* JADX INFO: renamed from: js */
                private Object f965js;

                /* JADX INFO: renamed from: jt */
                private boolean f966jt;
                boolean mClosed;

                /* JADX INFO: renamed from: com.google.android.gms.common.data.d$1, reason: invalid class name */
                static class AnonymousClass1 extends a {
                    AnonymousClass1(String[] strArr, String str) {
                        super(strArr, str, null);
                    }
                }

                /* JADX INFO: renamed from: com.google.android.gms.common.data.d$a */
                public static class a {

                    /* JADX INFO: renamed from: jm */
                    private final String[] f967jm;

                    /* JADX INFO: renamed from: jv */
                    private final ArrayList<HashMap<String, Object>> f968jv;

                    /* JADX INFO: renamed from: jw */
                    private final String f969jw;

                    /* JADX INFO: renamed from: jx */
                    private final HashMap<Object, Integer> f970jx;

                    /* JADX INFO: renamed from: jy */
                    private boolean f971jy;

                    /* JADX INFO: renamed from: jz */
                    private String f972jz;

                    private a(String[] strArr, String str) {
                        this.f967jm = (String[]) C1028dm.m1605e(strArr);
                        this.f968jv = new ArrayList<>();
                        this.f969jw = str;
                        this.f970jx = new HashMap<>();
                        this.f971jy = false;
                        this.f972jz = null;
                    }

                    /* synthetic */ a(String[] strArr, String str, AnonymousClass1 anonymousClass1) {
                        this(strArr, str);
                    }

                    /* JADX INFO: renamed from: b */
                    static /* synthetic */ String[] m1051b(a aVar) {
                        return aVar.f967jm;
                    }

                    /* JADX INFO: renamed from: c */
                    static /* synthetic */ ArrayList m1052c(a aVar) {
                        return aVar.f968jv;
                    }
                }

                C0891d(int i, String[] strArr, CursorWindow[] cursorWindowArr, int i2, Bundle bundle) {
                    this.mClosed = false;
                    this.f966jt = true;
                    this.f958iM = i;
                    this.f959jm = strArr;
                    this.f961jo = cursorWindowArr;
                    this.f957iC = i2;
                    this.f962jp = bundle;
                }

                private C0891d(a aVar, int i, Bundle bundle) {
                    this(a.m1051b(aVar), m1034a(aVar), i, bundle);
                }

                public C0891d(String[] strArr, CursorWindow[] cursorWindowArr, int i, Bundle bundle) {
                    this.mClosed = false;
                    this.f966jt = true;
                    this.f958iM = 1;
                    this.f959jm = (String[]) C1028dm.m1605e(strArr);
                    this.f961jo = (CursorWindow[]) C1028dm.m1605e(cursorWindowArr);
                    this.f957iC = i;
                    this.f962jp = bundle;
                    m1039aJ();
                }

                /* JADX INFO: renamed from: a */
                public static C0891d m1033a(int i, Bundle bundle) {
                    return new C0891d(f956ju, i, bundle);
                }

                /* JADX INFO: renamed from: a */
                private static CursorWindow[] m1034a(a aVar) {
                    int i;
                    int i2;
                    int i3;
                    CursorWindow cursorWindow;
                    if (a.m1051b(aVar).length == 0) {
                        return new CursorWindow[0];
                    }
                    ArrayList arrayListM1052c = a.m1052c(aVar);
                    int size = arrayListM1052c.size();
                    CursorWindow cursorWindow2 = new CursorWindow(false);
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(cursorWindow2);
                    cursorWindow2.setNumColumns(a.m1051b(aVar).length);
                    int i4 = 0;
                    int i5 = 0;
                    while (i4 < size) {
                        try {
                            if (cursorWindow2.allocRow()) {
                                i = i5;
                            } else {
                                Log.d("DataHolder", "Allocating additional cursor window for large data set (row " + i4 + ")");
                                cursorWindow2 = new CursorWindow(false);
                                cursorWindow2.setNumColumns(a.m1051b(aVar).length);
                                arrayList.add(cursorWindow2);
                                if (!cursorWindow2.allocRow()) {
                                    Log.e("DataHolder", "Unable to allocate row to hold data.");
                                    arrayList.remove(cursorWindow2);
                                    return (CursorWindow[]) arrayList.toArray(new CursorWindow[arrayList.size()]);
                                }
                                i = 0;
                            }
                            Map map = (Map) arrayListM1052c.get(i4);
                            boolean zPutBlob = true;
                            for (int i6 = 0; i6 < a.m1051b(aVar).length && zPutBlob; i6++) {
                                String str = a.m1051b(aVar)[i6];
                                Object obj = map.get(str);
                                if (obj == null) {
                                    zPutBlob = cursorWindow2.putNull(i, i6);
                                } else if (obj instanceof String) {
                                    zPutBlob = cursorWindow2.putString((String) obj, i, i6);
                                } else if (obj instanceof Long) {
                                    zPutBlob = cursorWindow2.putLong(((Long) obj).longValue(), i, i6);
                                } else if (obj instanceof Integer) {
                                    zPutBlob = cursorWindow2.putLong(((Integer) obj).intValue(), i, i6);
                                } else if (obj instanceof Boolean) {
                                    zPutBlob = cursorWindow2.putLong(((Boolean) obj).booleanValue() ? 1L : 0L, i, i6);
                                } else {
                                    if (!(obj instanceof byte[])) {
                                        throw new IllegalArgumentException("Unsupported object for column " + str + ": " + obj);
                                    }
                                    zPutBlob = cursorWindow2.putBlob((byte[]) obj, i, i6);
                                }
                            }
                            if (zPutBlob) {
                                i2 = i + 1;
                                i3 = i4;
                                cursorWindow = cursorWindow2;
                            } else {
                                Log.d("DataHolder", "Couldn't populate window data for row " + i4 + " - allocating new window.");
                                cursorWindow2.freeLastRow();
                                CursorWindow cursorWindow3 = new CursorWindow(false);
                                cursorWindow3.setNumColumns(a.m1051b(aVar).length);
                                arrayList.add(cursorWindow3);
                                i3 = i4 - 1;
                                cursorWindow = cursorWindow3;
                                i2 = 0;
                            }
                            cursorWindow2 = cursorWindow;
                            i4 = i3 + 1;
                            i5 = i2;
                        } catch (RuntimeException e) {
                            int size2 = arrayList.size();
                            for (int i7 = 0; i7 < size2; i7++) {
                                ((CursorWindow) arrayList.get(i7)).close();
                            }
                            throw e;
                        }
                    }
                    return (CursorWindow[]) arrayList.toArray(new CursorWindow[arrayList.size()]);
                }

                /* JADX INFO: renamed from: b */
                private void m1035b(String str, int i) {
                    if (this.f960jn == null || !this.f960jn.containsKey(str)) {
                        throw new IllegalArgumentException("No such column: " + str);
                    }
                    if (isClosed()) {
                        throw new IllegalArgumentException("Buffer is closed.");
                    }
                    if (i < 0 || i >= this.f964jr) {
                        throw new CursorIndexOutOfBoundsException(i, this.f964jr);
                    }
                }

                /* JADX INFO: renamed from: r */
                public static C0891d m1036r(int i) {
                    return m1033a(i, null);
                }

                /* JADX INFO: renamed from: a */
                public long m1037a(String str, int i, int i2) {
                    m1035b(str, i);
                    return this.f961jo[i2].getLong(i - this.f963jq[i2], this.f960jn.getInt(str));
                }

                /* JADX INFO: renamed from: a */
                public void m1038a(String str, int i, int i2, CharArrayBuffer charArrayBuffer) {
                    m1035b(str, i);
                    this.f961jo[i2].copyStringToBuffer(i - this.f963jq[i2], this.f960jn.getInt(str), charArrayBuffer);
                }

                /* JADX INFO: renamed from: aJ */
                public void m1039aJ() {
                    this.f960jn = new Bundle();
                    for (int i = 0; i < this.f959jm.length; i++) {
                        this.f960jn.putInt(this.f959jm[i], i);
                    }
                    this.f963jq = new int[this.f961jo.length];
                    int numRows = 0;
                    for (int i2 = 0; i2 < this.f961jo.length; i2++) {
                        this.f963jq[i2] = numRows;
                        numRows += this.f961jo[i2].getNumRows();
                    }
                    this.f964jr = numRows;
                }

                /* JADX INFO: renamed from: aK */
                String[] m1040aK() {
                    return this.f959jm;
                }

                /* JADX INFO: renamed from: aL */
                CursorWindow[] m1041aL() {
                    return this.f961jo;
                }

                /* JADX INFO: renamed from: aM */
                public Bundle m1042aM() {
                    return this.f962jp;
                }

                /* JADX INFO: renamed from: b */
                public int m1043b(String str, int i, int i2) {
                    m1035b(str, i);
                    return this.f961jo[i2].getInt(i - this.f963jq[i2], this.f960jn.getInt(str));
                }

                /* JADX INFO: renamed from: b */
                public void m1044b(Object obj) {
                    this.f965js = obj;
                }

                /* JADX INFO: renamed from: c */
                public String m1045c(String str, int i, int i2) {
                    m1035b(str, i);
                    return this.f961jo[i2].getString(i - this.f963jq[i2], this.f960jn.getInt(str));
                }

                public void close() {
                    synchronized (this) {
                        try {
                            if (!this.mClosed) {
                                this.mClosed = true;
                                for (int i = 0; i < this.f961jo.length; i++) {
                                    this.f961jo[i].close();
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                /* JADX INFO: renamed from: d */
                public boolean m1046d(String str, int i, int i2) {
                    m1035b(str, i);
                    return Long.valueOf(this.f961jo[i2].getLong(i - this.f963jq[i2], this.f960jn.getInt(str))).longValue() == 1;
                }

                @Override // android.os.Parcelable
                public int describeContents() {
                    return 0;
                }

                /* JADX INFO: renamed from: e */
                public byte[] m1047e(String str, int i, int i2) {
                    m1035b(str, i);
                    return this.f961jo[i2].getBlob(i - this.f963jq[i2], this.f960jn.getInt(str));
                }

                /* JADX INFO: renamed from: f */
                public Uri m1048f(String str, int i, int i2) {
                    String strM1045c = m1045c(str, i, i2);
                    if (strM1045c == null) {
                        return null;
                    }
                    return Uri.parse(strM1045c);
                }

                protected void finalize() throws Throwable {
                    try {
                        if (this.f966jt && this.f961jo.length > 0 && !isClosed()) {
                            Log.e("DataHolder", "Internal data leak within a DataBuffer object detected!  Be sure to explicitly call close() on all DataBuffer extending objects when you are done with them. (" + (this.f965js == null ? "internal object: " + toString() : this.f965js.toString()) + ")");
                            close();
                        }
                        super.finalize();
                    } catch (Throwable th) {
                        super.finalize();
                        throw th;
                    }
                }

                /* JADX INFO: renamed from: g */
                public boolean m1049g(String str, int i, int i2) {
                    m1035b(str, i);
                    return this.f961jo[i2].isNull(i - this.f963jq[i2], this.f960jn.getInt(str));
                }

                public int getCount() {
                    return this.f964jr;
                }

                public int getStatusCode() {
                    return this.f957iC;
                }

                int getVersionCode() {
                    return this.f958iM;
                }

                public boolean isClosed() {
                    boolean z;
                    synchronized (this) {
                        try {
                            z = this.mClosed;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return z;
                }

                /* JADX INFO: renamed from: q */
                public int m1050q(int i) {
                    int i2 = 0;
                    C1028dm.m1606k(i >= 0 && i < this.f964jr);
                    while (i2 < this.f963jq.length) {
                        if (i < this.f963jq[i2]) {
                            i2--;
                            break;
                        }
                        i2++;
                    }
                    return i2 == this.f963jq.length ? i2 - 1 : i2;
                }

                @Override // android.os.Parcelable
                public void writeToParcel(Parcel dest, int flags) {
                    C0892e.m1053a(this, dest, flags);
                }
            }
