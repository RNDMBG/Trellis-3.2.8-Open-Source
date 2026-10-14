            package org.apache.commons.p005io.output;

            import com.vandalsoftware.p004io.IoUtils;
            import java.io.File;
            import java.io.FileNotFoundException;
            import java.io.FileOutputStream;
            import java.io.IOException;
            import java.io.OutputStream;
            import java.io.OutputStreamWriter;
            import java.io.StringWriter;
            import java.io.Writer;
            import java.util.regex.Matcher;
            import java.util.regex.Pattern;
            import org.apache.commons.p005io.input.XmlStreamReader;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 58 */    public class XmlStreamWriter extends Writer {
                private static final int BUFFER_SIZE = 4096;
                static final Pattern ENCODING_PATTERN = XmlStreamReader.ENCODING_PATTERN;
                private final String defaultEncoding;
                private String encoding;
                private final OutputStream out;
                private Writer writer;
                private StringWriter xmlPrologWriter;

/* 59 */        public XmlStreamWriter(OutputStream out) {
/* 60 */            this(out, (String) null);
                }

/* 69 */        public XmlStreamWriter(OutputStream out, String defaultEncoding) {
/* 47 */            this.xmlPrologWriter = new StringWriter(4096);
/* 71 */            this.out = out;
/* 72 */            this.defaultEncoding = defaultEncoding == null ? IoUtils.UTF_8 : defaultEncoding;
                }

/* 83 */        public XmlStreamWriter(File file) throws FileNotFoundException {
/* 84 */            this(file, (String) null);
                }

/* 96 */        public XmlStreamWriter(File file, String defaultEncoding) throws FileNotFoundException {
/* 97 */            this(new FileOutputStream(file), defaultEncoding);
                }

/* 105 */       public String getEncoding() {
/* 106 */           return this.encoding;
                }

/* 114 */       public String getDefaultEncoding() {
/* 115 */           return this.defaultEncoding;
                }

                @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
/* 124 */       public void close() throws IOException {
/* 125 */           if (this.writer == null) {
/* 126 */               this.encoding = this.defaultEncoding;
/* 127 */               this.writer = new OutputStreamWriter(this.out, this.encoding);
/* 128 */               this.writer.write(this.xmlPrologWriter.toString());
                    }
/* 130 */           this.writer.close();
                }

                @Override // java.io.Writer, java.io.Flushable
/* 139 */       public void flush() throws IOException {
/* 140 */           if (this.writer != null) {
/* 141 */               this.writer.flush();
                    }
                }

/* 154 */       private void detectEncoding(char[] cbuf, int off, int len) throws IOException {
/* 155 */           int size = len;
/* 156 */           StringBuffer xmlProlog = this.xmlPrologWriter.getBuffer();
/* 157 */           if (xmlProlog.length() + len > 4096) {
/* 158 */               size = 4096 - xmlProlog.length();
                    }
/* 160 */           this.xmlPrologWriter.write(cbuf, off, size);
/* 163 */           if (xmlProlog.length() >= 5) {
/* 164 */               if (xmlProlog.substring(0, 5).equals("<?xml")) {
/* 166 */                   int xmlPrologEnd = xmlProlog.indexOf("?>");
/* 167 */                   if (xmlPrologEnd > 0) {
/* 169 */                       Matcher m = ENCODING_PATTERN.matcher(xmlProlog.substring(0, xmlPrologEnd));
/* 171 */                       if (m.find()) {
/* 172 */                           this.encoding = m.group(1).toUpperCase();
/* 173 */                           this.encoding = this.encoding.substring(1, this.encoding.length() - 1);
                                } else {
/* 177 */                           this.encoding = this.defaultEncoding;
                                }
                            } else if (xmlProlog.length() >= 4096) {
/* 183 */                       this.encoding = this.defaultEncoding;
                            }
                        } else {
/* 188 */                   this.encoding = this.defaultEncoding;
                        }
/* 190 */               if (this.encoding != null) {
/* 192 */                   this.xmlPrologWriter = null;
/* 193 */                   this.writer = new OutputStreamWriter(this.out, this.encoding);
/* 194 */                   this.writer.write(xmlProlog.toString());
/* 195 */                   if (len > size) {
/* 196 */                       this.writer.write(cbuf, off + size, len - size);
                            }
                        }
                    }
                }

                @Override // java.io.Writer
/* 211 */       public void write(char[] cbuf, int off, int len) throws IOException {
/* 212 */           if (this.xmlPrologWriter != null) {
/* 213 */               detectEncoding(cbuf, off, len);
                    } else {
/* 215 */               this.writer.write(cbuf, off, len);
                    }
                }
            }
