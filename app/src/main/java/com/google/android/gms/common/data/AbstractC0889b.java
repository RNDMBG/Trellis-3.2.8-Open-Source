            package com.google.android.gms.common.data;

            import android.database.CharArrayBuffer;
            import android.net.Uri;
            import com.google.android.gms.internal.C1027dl;
            import com.google.android.gms.internal.C1028dm;
            
            /* JADX INFO: renamed from: com.google.android.gms.common.data.b */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public abstract class AbstractC0889b {

                /* JADX INFO: renamed from: jf */
                protected final C0891d f951jf;

                /* JADX INFO: renamed from: ji */
                protected final int f952ji;

                /* JADX INFO: renamed from: jj */
                private final int f953jj;

                public AbstractC0889b(C0891d c0891d, int i) {
                    this.f951jf = (C0891d) C1028dm.m1605e(c0891d);
                    C1028dm.m1606k(i >= 0 && i < c0891d.getCount());
                    this.f952ji = i;
                    this.f953jj = c0891d.m1050q(this.f952ji);
                }

                /* JADX INFO: renamed from: a */
                protected void m1029a(String str, CharArrayBuffer charArrayBuffer) {
                    this.f951jf.m1038a(str, this.f952ji, this.f953jj, charArrayBuffer);
                }

                public boolean equals(Object obj) {
                    if (!(obj instanceof AbstractC0889b)) {
                        return false;
                    }
                    AbstractC0889b abstractC0889b = (AbstractC0889b) obj;
                    return C1027dl.equal(Integer.valueOf(abstractC0889b.f952ji), Integer.valueOf(this.f952ji)) && C1027dl.equal(Integer.valueOf(abstractC0889b.f953jj), Integer.valueOf(this.f953jj)) && abstractC0889b.f951jf == this.f951jf;
                }

                protected boolean getBoolean(String column) {
                    return this.f951jf.m1046d(column, this.f952ji, this.f953jj);
                }

                protected byte[] getByteArray(String column) {
                    return this.f951jf.m1047e(column, this.f952ji, this.f953jj);
                }

                protected int getInteger(String column) {
                    return this.f951jf.m1043b(column, this.f952ji, this.f953jj);
                }

                protected long getLong(String column) {
                    return this.f951jf.m1037a(column, this.f952ji, this.f953jj);
                }

                protected String getString(String column) {
                    return this.f951jf.m1045c(column, this.f952ji, this.f953jj);
                }

                public int hashCode() {
                    return C1027dl.hashCode(Integer.valueOf(this.f952ji), Integer.valueOf(this.f953jj), this.f951jf);
                }

                public boolean isDataValid() {
                    return !this.f951jf.isClosed();
                }

                /* JADX INFO: renamed from: u */
                protected Uri m1030u(String str) {
                    return this.f951jf.m1048f(str, this.f952ji, this.f953jj);
                }

                /* JADX INFO: renamed from: v */
                protected boolean m1031v(String str) {
                    return this.f951jf.m1049g(str, this.f952ji, this.f953jj);
                }
            }
