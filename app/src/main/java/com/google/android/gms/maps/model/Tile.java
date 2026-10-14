            package com.google.android.gms.maps.model;

            import android.os.Parcel;
            import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
            import com.google.android.gms.maps.internal.C1174r;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public final class Tile implements SafeParcelable {
                public static final TileCreator CREATOR = new TileCreator();
                public final byte[] data;
                public final int height;

                /* JADX INFO: renamed from: iM */
                private final int f2349iM;
                public final int width;

                Tile(int versionCode, int width, int height, byte[] data) {
                    this.f2349iM = versionCode;
                    this.width = width;
                    this.height = height;
                    this.data = data;
                }

                public Tile(int width, int height, byte[] data) {
                    this(1, width, height, data);
                }

                @Override // android.os.Parcelable
                public int describeContents() {
                    return 0;
                }

                int getVersionCode() {
                    return this.f2349iM;
                }

                @Override // android.os.Parcelable
                public void writeToParcel(Parcel out, int flags) {
                    if (C1174r.m2265cK()) {
                        C1185i.m2303a(this, out, flags);
                    } else {
                        TileCreator.m2288a(this, out, flags);
                    }
                }
            }
