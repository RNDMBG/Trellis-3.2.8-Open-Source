            package org.apache.commons.p005io.input;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
            public interface TailerListener {
                void fileNotFound();

                void fileRotated();

                void handle(Exception exc);

                void handle(String str);

                void init(Tailer tailer);
            }
