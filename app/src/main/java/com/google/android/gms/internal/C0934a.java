            package com.google.android.gms.internal;

            import android.util.Base64;
            
            /* JADX INFO: renamed from: com.google.android.gms.internal.a */
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            class C0934a implements InterfaceC1112j {
                C0934a() {
                }

                @Override // com.google.android.gms.internal.InterfaceC1112j
                /* JADX INFO: renamed from: a */
                public String mo1236a(byte[] bArr, boolean z) {
                    return Base64.encodeToString(bArr, z ? 11 : 2);
                }

                @Override // com.google.android.gms.internal.InterfaceC1112j
                /* JADX INFO: renamed from: a */
                public byte[] mo1237a(String str, boolean z) throws IllegalArgumentException {
                    return Base64.decode(str, z ? 11 : 2);
                }
            }
