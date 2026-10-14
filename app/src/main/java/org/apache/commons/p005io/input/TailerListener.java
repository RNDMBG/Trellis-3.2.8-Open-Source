            package org.apache.commons.p005io.input;
            
            
            public interface TailerListener {
                void fileNotFound();

                void fileRotated();

                void handle(Exception exc);

                void handle(String str);

                void init(Tailer tailer);
            }
