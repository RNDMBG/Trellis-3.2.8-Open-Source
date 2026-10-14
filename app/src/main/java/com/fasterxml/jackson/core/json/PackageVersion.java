            package com.fasterxml.jackson.core.json;

            import com.fasterxml.jackson.core.Version;
            import com.fasterxml.jackson.core.Versioned;
            import com.fasterxml.jackson.core.util.VersionUtil;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 16 */    public final class PackageVersion implements Versioned {
                public static final Version VERSION = VersionUtil.parseVersion("2.3.4", "com.fasterxml.jackson.core", "jackson-core");

                @Override // com.fasterxml.jackson.core.Versioned
/* 17 */        public Version version() {
/* 18 */            return VERSION;
                }
            }
