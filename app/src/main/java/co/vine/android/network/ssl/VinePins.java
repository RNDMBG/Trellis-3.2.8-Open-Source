            package co.vine.android.network.ssl;

            import java.util.HashMap;
            import java.util.Map;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 10 */    public class VinePins {
                public static final Map<String, String> PIN_MAP = new C03891();
                public static final String[] PINS = (String[]) PIN_MAP.values().toArray(new String[PIN_MAP.size()]);

/* 11 */        private VinePins() {
                }

                /* JADX INFO: renamed from: co.vine.android.network.ssl.VinePins$1 */
/* 13 */        static class C03891 extends HashMap<String, String> {
/* 14 */            C03891() {
/* 16 */                put("VERISIGN_CLASS1", "2343d148a255899b947d461a797ec04cfed170b7");
/* 17 */                put("VERISIGN_CLASS3", "e27f7bd877d5df9e0a3f9eb4cb0e2ea9efdb6977");
/* 18 */                put("VERISIGN_CLASS3_G4", "ed663135d31bd4eca614c429e319069f94c12650");
/* 19 */                put("VERISIGN_CLASS4_G3", "3c03436868951cf3692ab8b426daba8fe922e5bd");
/* 20 */                put("VERISIGN_CLASS3_G3", "22f19e2ec6eaccfc5d2346f4c2e8f6c554dd5e07");
/* 21 */                put("VERISIGN_CLASS1_G3", "5519b278acb281d7eda7abc18399c3bb690424b5");
/* 22 */                put("VERISIGN_CLASS2_G3", "5abec575dcaef3b08e271943fc7f250c3df661e3");
/* 23 */                put("VERISIGN_CLASS3_G2", "1a21b4952b6293ce18b365ec9c0e934cb381e6d4");
/* 24 */                put("VERISIGN_CLASS2_G2", "1237ba4517eead2926fdc1cdfebeedf2ded9145c");
/* 25 */                put("VERISIGN_CLASS3_G5", "b181081a19a4c0941ffae89528c124c99b34acc7");
/* 26 */                put("VERISIGN_UNIVERSAL", "bbc23e290bb328771dad3ea24dbdf423bd06b03d");
/* 27 */                put("GEOTRUST_GLOBAL", "c07a98688d89fbab05640c117daa7d65b8cacc4e");
/* 28 */                put("GEOTRUST_GLOBAL2", "713836f2023153472b6eba6546a9101558200509");
/* 29 */                put("GEOTRUST_UNIVERAL", "87e85b6353c623a3128cb0ffbbf551fe59800e22");
/* 30 */                put("GEOTRUST_UNIVERSAL2", "5e4f538685dd4f9eca5fdc0d456f7d51b1dc9b7b");
/* 31 */                put("GEOTRUST_PRIMARY", "b01989e7effb4aafcb148f58463976224150e1ba");
/* 32 */                put("GEOTRUST_PRIMARY_G2", "bdbea71bab7157f9e475d954d2b727801a822682");
/* 33 */                put("GEOTRUST_PRIMARY_G3", "9ca98d00af740ddd8180d21345a58b8f2e9438d6");
/* 34 */                put("DIGICERT_GLOBAL_ROOT", "d52e13c1abe349dae8b49594ef7c3843606466bd");
/* 35 */                put("DIGICERT_EV_ROOT", "83317e62854253d6d7783190ec919056e991b9e3");
/* 36 */                put("DIGICERT_ASSUREDID_ROOT", "68330e61358521592983a3c8d2d2e1406e7ab3c1");
/* 37 */                put("TWITTER1", "56fef3c2147d4ed38837fdbd3052387201e5778d");
                    }
                }
            }
