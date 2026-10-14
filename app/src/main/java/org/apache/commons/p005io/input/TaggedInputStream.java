            package org.apache.commons.p005io.input;

            import java.io.IOException;
            import java.io.InputStream;
            import java.io.Serializable;
            import java.util.UUID;
            import org.apache.commons.p005io.TaggedIOException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 75 */    public class TaggedInputStream extends ProxyInputStream {
                private final Serializable tag;

/* 76 */        public TaggedInputStream(InputStream proxy) {
/* 77 */            super(proxy);
/* 69 */            this.tag = UUID.randomUUID();
                }

/* 87 */        public boolean isCauseOf(Throwable exception) {
/* 88 */            return TaggedIOException.isTaggedWith(exception, this.tag);
                }

/* 101 */       public void throwIfCauseOf(Throwable throwable) throws IOException {
/* 102 */           TaggedIOException.throwCauseIfTaggedWith(throwable, this.tag);
                }

                @Override // org.apache.commons.p005io.input.ProxyInputStream
/* 112 */       protected void handleIOException(IOException e) throws IOException {
/* 113 */           throw new TaggedIOException(e, this.tag);
                }
            }
