            package org.apache.commons.p005io;

            import java.io.IOException;
            import java.io.Serializable;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 27 */    public class TaggedIOException extends IOExceptionWithCause {
                private static final long serialVersionUID = -6994123481142850163L;
                private final Serializable tag;


/* 64 */        public static boolean isTaggedWith(Throwable throwable, Object tag) {
                    return tag != null && (throwable instanceof TaggedIOException) && tag.equals(((TaggedIOException) throwable).tag);
                }

/* 93 */        public static void throwCauseIfTaggedWith(Throwable throwable, Object tag) throws IOException {
/* 94 */            if (isTaggedWith(throwable, tag)) {
/* 95 */                throw ((TaggedIOException) throwable).getCause();
                    }
                }

/* 110 */       public TaggedIOException(IOException original, Serializable tag) {
/* 111 */           super(original.getMessage(), original);
/* 112 */           this.tag = tag;
                }

/* 120 */       public Serializable getTag() {
/* 121 */           return this.tag;
                }

                @Override // java.lang.Throwable
/* 131 */       public IOException getCause() {
/* 132 */           return (IOException) super.getCause();
                }
            }
