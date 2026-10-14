            package com.fasterxml.jackson.core.p003io;

            import com.fasterxml.jackson.core.SerializableString;
            import com.fasterxml.jackson.core.util.ArraysCompat;
            import java.io.Serializable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 65 */    public abstract class CharacterEscapes implements Serializable {
                public static final int ESCAPE_CUSTOM = -2;
                public static final int ESCAPE_NONE = 0;
                public static final int ESCAPE_STANDARD = -1;
                private static final long serialVersionUID = 1;

                public abstract int[] getEscapeCodesForAscii();

                public abstract SerializableString getEscapeSequence(int i);

/* 66 */        public static int[] standardAsciiEscapesForJSON() {
/* 67 */            int[] iArr = CharTypes.get7BitOutputEscapes();
/* 68 */            return ArraysCompat.copyOf(iArr, iArr.length);
                }
            }
