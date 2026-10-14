            package org.apache.commons.p005io.input;

            import com.googlecode.javacv.cpp.avcodec;
            import java.io.BufferedInputStream;
            import java.io.BufferedReader;
            import java.io.File;
            import java.io.FileInputStream;
            import java.io.IOException;
            import java.io.InputStream;
            import java.io.InputStreamReader;
            import java.io.Reader;
            import java.io.StringReader;
            import java.net.HttpURLConnection;
            import java.net.URL;
            import java.net.URLConnection;
            import java.text.MessageFormat;
            import java.util.Locale;
            import java.util.regex.Matcher;
            import java.util.regex.Pattern;
            import org.apache.commons.p005io.ByteOrderMark;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 120 */   public class XmlStreamReader extends Reader {
                private static final int BUFFER_SIZE = 4096;
                private static final String HTTP_EX_1 = "Invalid encoding, CT-MIME [{0}] CT-Enc [{1}] BOM [{2}] XML guess [{3}] XML prolog [{4}], BOM must be NULL";
                private static final String HTTP_EX_2 = "Invalid encoding, CT-MIME [{0}] CT-Enc [{1}] BOM [{2}] XML guess [{3}] XML prolog [{4}], encoding mismatch";
                private static final String HTTP_EX_3 = "Invalid encoding, CT-MIME [{0}] CT-Enc [{1}] BOM [{2}] XML guess [{3}] XML prolog [{4}], Invalid MIME";
                private static final String RAW_EX_1 = "Invalid encoding, BOM [{0}] XML guess [{1}] XML prolog [{2}] encoding mismatch";
                private static final String RAW_EX_2 = "Invalid encoding, BOM [{0}] XML guess [{1}] XML prolog [{2}] unknown BOM";
                private static final String US_ASCII = "US-ASCII";
                private static final String UTF_16 = "UTF-16";
                private static final String UTF_32 = "UTF-32";
                private static final String UTF_8 = "UTF-8";
                private final String defaultEncoding;
                private final String encoding;
                private final Reader reader;
                private static final ByteOrderMark[] BOMS = {ByteOrderMark.UTF_8, ByteOrderMark.UTF_16BE, ByteOrderMark.UTF_16LE, ByteOrderMark.UTF_32BE, ByteOrderMark.UTF_32LE};
                private static final String UTF_16BE = "UTF-16BE";
                private static final String UTF_16LE = "UTF-16LE";
                private static final String UTF_32BE = "UTF-32BE";
                private static final String UTF_32LE = "UTF-32LE";
                private static final String EBCDIC = "CP1047";
                private static final ByteOrderMark[] XML_GUESS_BYTES = {new ByteOrderMark("UTF-8", 60, 63, 120, 109), new ByteOrderMark(UTF_16BE, 0, 60, 0, 63), new ByteOrderMark(UTF_16LE, 60, 0, 63, 0), new ByteOrderMark(UTF_32BE, 0, 0, 0, 60, 0, 0, 0, 63, 0, 0, 0, 120, 0, 0, 0, 109), new ByteOrderMark(UTF_32LE, 60, 0, 0, 0, 63, 0, 0, 0, 120, 0, 0, 0, 109, 0, 0, 0), new ByteOrderMark(EBCDIC, 76, 111, avcodec.AV_CODEC_ID_CLLC, avcodec.AV_CODEC_ID_LAGARITH)};
                private static final Pattern CHARSET_PATTERN = Pattern.compile("charset=[\"']?([.[^; \"']]*)[\"']?");
                public static final Pattern ENCODING_PATTERN = Pattern.compile("<\\?xml.*encoding[\\s]*=[\\s]*((?:\".[^\"]*\")|(?:'.[^']*'))", 8);

/* 121 */       public String getDefaultEncoding() {
/* 122 */           return this.defaultEncoding;
                }

/* 137 */       public XmlStreamReader(File file) throws IOException {
/* 138 */           this(new FileInputStream(file));
                }

/* 152 */       public XmlStreamReader(InputStream is) throws IOException {
/* 153 */           this(is, true);
                }

/* 183 */       public XmlStreamReader(InputStream is, boolean lenient) throws IOException {
/* 184 */           this(is, lenient, (String) null);
                }

/* 214 */       public XmlStreamReader(InputStream is, boolean lenient, String defaultEncoding) throws IOException {
/* 216 */           this.defaultEncoding = defaultEncoding;
/* 217 */           BOMInputStream bom = new BOMInputStream(new BufferedInputStream(is, 4096), false, BOMS);
/* 218 */           BOMInputStream pis = new BOMInputStream(bom, true, XML_GUESS_BYTES);
/* 219 */           this.encoding = doRawStream(bom, pis, lenient);
/* 220 */           this.reader = new InputStreamReader(pis, this.encoding);
                }

/* 240 */       public XmlStreamReader(URL url) throws IOException {
/* 241 */           this(url.openConnection(), (String) null);
                }

/* 262 */       public XmlStreamReader(URLConnection conn, String defaultEncoding) throws IOException {
/* 264 */           this.defaultEncoding = defaultEncoding;
/* 266 */           String contentType = conn.getContentType();
/* 267 */           InputStream is = conn.getInputStream();
/* 268 */           BOMInputStream bom = new BOMInputStream(new BufferedInputStream(is, 4096), false, BOMS);
/* 269 */           BOMInputStream pis = new BOMInputStream(bom, true, XML_GUESS_BYTES);
/* 270 */           if ((conn instanceof HttpURLConnection) || contentType != null) {
/* 271 */               this.encoding = doHttpStream(bom, pis, contentType, true);
                    } else {
/* 273 */               this.encoding = doRawStream(bom, pis, true);
                    }
/* 275 */           this.reader = new InputStreamReader(pis, this.encoding);
                }

/* 296 */       public XmlStreamReader(InputStream is, String httpContentType) throws IOException {
/* 297 */           this(is, httpContentType, true);
                }

/* 334 */       public XmlStreamReader(InputStream is, String httpContentType, boolean lenient, String defaultEncoding) throws IOException {
/* 336 */           this.defaultEncoding = defaultEncoding;
/* 337 */           BOMInputStream bom = new BOMInputStream(new BufferedInputStream(is, 4096), false, BOMS);
/* 338 */           BOMInputStream pis = new BOMInputStream(bom, true, XML_GUESS_BYTES);
/* 339 */           this.encoding = doHttpStream(bom, pis, httpContentType, lenient);
/* 340 */           this.reader = new InputStreamReader(pis, this.encoding);
                }

/* 377 */       public XmlStreamReader(InputStream is, String httpContentType, boolean lenient) throws IOException {
/* 378 */           this(is, httpContentType, lenient, null);
                }

/* 386 */       public String getEncoding() {
/* 387 */           return this.encoding;
                }

                @Override // java.io.Reader
/* 399 */       public int read(char[] buf, int offset, int len) throws IOException {
/* 400 */           return this.reader.read(buf, offset, len);
                }

                @Override // java.io.Reader, java.io.Closeable, java.lang.AutoCloseable
/* 409 */       public void close() throws IOException {
/* 410 */           this.reader.close();
                }

/* 424 */       private String doRawStream(BOMInputStream bom, BOMInputStream pis, boolean lenient) throws IOException {
/* 425 */           String bomEnc = bom.getBOMCharsetName();
/* 426 */           String xmlGuessEnc = pis.getBOMCharsetName();
/* 427 */           String xmlEnc = getXmlProlog(pis, xmlGuessEnc);
                    try {
/* 429 */               return calculateRawEncoding(bomEnc, xmlGuessEnc, xmlEnc);
                    } catch (XmlStreamReaderException ex) {
/* 431 */               if (lenient) {
/* 432 */                   return doLenientDetection(null, ex);
                        }
/* 434 */               throw ex;
                    }
                }

/* 451 */       private String doHttpStream(BOMInputStream bom, BOMInputStream pis, String httpContentType, boolean lenient) throws IOException {
/* 452 */           String bomEnc = bom.getBOMCharsetName();
/* 453 */           String xmlGuessEnc = pis.getBOMCharsetName();
/* 454 */           String xmlEnc = getXmlProlog(pis, xmlGuessEnc);
                    try {
/* 456 */               return calculateHttpEncoding(httpContentType, bomEnc, xmlGuessEnc, xmlEnc, lenient);
                    } catch (XmlStreamReaderException ex) {
/* 459 */               if (lenient) {
/* 460 */                   return doLenientDetection(httpContentType, ex);
                        }
/* 462 */               throw ex;
                    }
                }

/* 477 */       private String doLenientDetection(String httpContentType, XmlStreamReaderException ex) throws IOException {
/* 478 */           if (httpContentType != null && httpContentType.startsWith("text/html")) {
                        try {
/* 482 */                   return calculateHttpEncoding("text/xml" + httpContentType.substring("text/html".length()), ex.getBomEncoding(), ex.getXmlGuessEncoding(), ex.getXmlEncoding(), true);
                        } catch (XmlStreamReaderException ex2) {
/* 485 */                   ex = ex2;
                        }
                    }
/* 488 */           String encoding = ex.getXmlEncoding();
/* 489 */           if (encoding == null) {
/* 490 */               encoding = ex.getContentTypeEncoding();
                    }
/* 492 */           if (encoding == null) {
                        return this.defaultEncoding == null ? "UTF-8" : this.defaultEncoding;
                    }
/* 495 */           return encoding;
                }

/* 510 */       String calculateRawEncoding(String bomEnc, String xmlGuessEnc, String xmlEnc) throws IOException {
/* 511 */           if (bomEnc == null) {
/* 512 */               if (xmlGuessEnc == null || xmlEnc == null) {
/* 513 */                   return this.defaultEncoding == null ? "UTF-8" : this.defaultEncoding;
                        }
                        return (xmlEnc.equals(UTF_16) && (xmlGuessEnc.equals(UTF_16BE) || xmlGuessEnc.equals(UTF_16LE))) ? xmlGuessEnc : xmlEnc;
                    }
/* 523 */           if (bomEnc.equals("UTF-8")) {
/* 524 */               if (xmlGuessEnc != null && !xmlGuessEnc.equals("UTF-8")) {
/* 525 */                   String msg = MessageFormat.format(RAW_EX_1, bomEnc, xmlGuessEnc, xmlEnc);
/* 526 */                   throw new XmlStreamReaderException(msg, bomEnc, xmlGuessEnc, xmlEnc);
                        }
/* 528 */               if (xmlEnc != null && !xmlEnc.equals("UTF-8")) {
/* 529 */                   String msg2 = MessageFormat.format(RAW_EX_1, bomEnc, xmlGuessEnc, xmlEnc);
/* 530 */                   throw new XmlStreamReaderException(msg2, bomEnc, xmlGuessEnc, xmlEnc);
                        }
/* 558 */               return bomEnc;
                    }
/* 536 */           if (bomEnc.equals(UTF_16BE) || bomEnc.equals(UTF_16LE)) {
/* 537 */               if (xmlGuessEnc != null && !xmlGuessEnc.equals(bomEnc)) {
/* 538 */                   String msg3 = MessageFormat.format(RAW_EX_1, bomEnc, xmlGuessEnc, xmlEnc);
/* 539 */                   throw new XmlStreamReaderException(msg3, bomEnc, xmlGuessEnc, xmlEnc);
                        }
/* 541 */               if (xmlEnc != null && !xmlEnc.equals(UTF_16) && !xmlEnc.equals(bomEnc)) {
/* 542 */                   String msg4 = MessageFormat.format(RAW_EX_1, bomEnc, xmlGuessEnc, xmlEnc);
/* 543 */                   throw new XmlStreamReaderException(msg4, bomEnc, xmlGuessEnc, xmlEnc);
                        }
/* 558 */               return bomEnc;
                    }
/* 549 */           if (bomEnc.equals(UTF_32BE) || bomEnc.equals(UTF_32LE)) {
/* 550 */               if (xmlGuessEnc != null && !xmlGuessEnc.equals(bomEnc)) {
/* 551 */                   String msg5 = MessageFormat.format(RAW_EX_1, bomEnc, xmlGuessEnc, xmlEnc);
/* 552 */                   throw new XmlStreamReaderException(msg5, bomEnc, xmlGuessEnc, xmlEnc);
                        }
/* 554 */               if (xmlEnc != null && !xmlEnc.equals(UTF_32) && !xmlEnc.equals(bomEnc)) {
/* 555 */                   String msg6 = MessageFormat.format(RAW_EX_1, bomEnc, xmlGuessEnc, xmlEnc);
/* 556 */                   throw new XmlStreamReaderException(msg6, bomEnc, xmlGuessEnc, xmlEnc);
                        }
/* 558 */               return bomEnc;
                    }
/* 562 */           String msg7 = MessageFormat.format(RAW_EX_2, bomEnc, xmlGuessEnc, xmlEnc);
/* 563 */           throw new XmlStreamReaderException(msg7, bomEnc, xmlGuessEnc, xmlEnc);
                }

/* 583 */       String calculateHttpEncoding(String httpContentType, String bomEnc, String xmlGuessEnc, String xmlEnc, boolean lenient) throws IOException {
/* 584 */           if (lenient && xmlEnc != null) {
                        return xmlEnc;
                    }
/* 589 */           String cTMime = getContentTypeMime(httpContentType);
/* 590 */           String cTEnc = getContentTypeEncoding(httpContentType);
/* 591 */           boolean appXml = isAppXml(cTMime);
/* 592 */           boolean textXml = isTextXml(cTMime);
/* 595 */           if (!appXml && !textXml) {
/* 596 */               String msg = MessageFormat.format(HTTP_EX_3, cTMime, cTEnc, bomEnc, xmlGuessEnc, xmlEnc);
/* 597 */               throw new XmlStreamReaderException(msg, cTMime, cTEnc, bomEnc, xmlGuessEnc, xmlEnc);
                    }
/* 601 */           if (cTEnc == null) {
/* 602 */               if (appXml) {
/* 603 */                   return calculateRawEncoding(bomEnc, xmlGuessEnc, xmlEnc);
                        }
/* 605 */               return this.defaultEncoding == null ? US_ASCII : this.defaultEncoding;
                    }
/* 610 */           if (cTEnc.equals(UTF_16BE) || cTEnc.equals(UTF_16LE)) {
/* 611 */               if (bomEnc != null) {
/* 612 */                   String msg2 = MessageFormat.format(HTTP_EX_1, cTMime, cTEnc, bomEnc, xmlGuessEnc, xmlEnc);
/* 613 */                   throw new XmlStreamReaderException(msg2, cTMime, cTEnc, bomEnc, xmlGuessEnc, xmlEnc);
                        }
/* 645 */               return cTEnc;
                    }
/* 619 */           if (cTEnc.equals(UTF_16)) {
/* 620 */               if (bomEnc != null && bomEnc.startsWith(UTF_16)) {
                            return bomEnc;
                        }
/* 623 */               String msg3 = MessageFormat.format(HTTP_EX_2, cTMime, cTEnc, bomEnc, xmlGuessEnc, xmlEnc);
/* 624 */               throw new XmlStreamReaderException(msg3, cTMime, cTEnc, bomEnc, xmlGuessEnc, xmlEnc);
                    }
/* 628 */           if (cTEnc.equals(UTF_32BE) || cTEnc.equals(UTF_32LE)) {
/* 629 */               if (bomEnc != null) {
/* 630 */                   String msg4 = MessageFormat.format(HTTP_EX_1, cTMime, cTEnc, bomEnc, xmlGuessEnc, xmlEnc);
/* 631 */                   throw new XmlStreamReaderException(msg4, cTMime, cTEnc, bomEnc, xmlGuessEnc, xmlEnc);
                        }
/* 645 */               return cTEnc;
                    }
/* 637 */           if (cTEnc.equals(UTF_32)) {
/* 638 */               if (bomEnc != null && bomEnc.startsWith(UTF_32)) {
                            return bomEnc;
                        }
/* 641 */               String msg5 = MessageFormat.format(HTTP_EX_2, cTMime, cTEnc, bomEnc, xmlGuessEnc, xmlEnc);
/* 642 */               throw new XmlStreamReaderException(msg5, cTMime, cTEnc, bomEnc, xmlGuessEnc, xmlEnc);
                    }
/* 645 */           return cTEnc;
                }

/* 654 */       static String getContentTypeMime(String httpContentType) {
                    String mime;
/* 656 */           if (httpContentType == null) {
/* 655 */               return null;
                    }
/* 657 */           int i = httpContentType.indexOf(";");
/* 658 */           if (i >= 0) {
/* 659 */               mime = httpContentType.substring(0, i);
                    } else {
/* 661 */               mime = httpContentType;
                    }
/* 663 */           return mime.trim();
                }

/* 678 */       static String getContentTypeEncoding(String httpContentType) {
                    int i;
/* 680 */           if (httpContentType == null || (i = httpContentType.indexOf(";")) <= -1) {
/* 679 */               return null;
                    }
/* 683 */           String postMime = httpContentType.substring(i + 1);
/* 684 */           Matcher m = CHARSET_PATTERN.matcher(postMime);
/* 685 */           String encoding = m.find() ? m.group(1) : null;
/* 686 */           if (encoding != null) {
                        return encoding.toUpperCase(Locale.US);
                    }
                    return null;
                }

/* 705 */       private static String getXmlProlog(InputStream is, String guessedEnc) throws IOException {
/* 707 */           if (guessedEnc == null) {
/* 706 */               return null;
                    }
/* 708 */           byte[] bytes = new byte[4096];
/* 709 */           is.mark(4096);
/* 710 */           int offset = 0;
/* 711 */           int max = 4096;
/* 712 */           int c = is.read(bytes, 0, 4096);
/* 713 */           int firstGT = -1;
/* 714 */           String xmlProlog = null;
/* 715 */           while (c != -1 && firstGT == -1 && offset < 4096) {
/* 716 */               offset += c;
/* 717 */               max -= c;
/* 718 */               c = is.read(bytes, offset, max);
/* 719 */               xmlProlog = new String(bytes, 0, offset, guessedEnc);
/* 720 */               firstGT = xmlProlog.indexOf(62);
                    }
/* 722 */           if (firstGT == -1) {
/* 723 */               if (c == -1) {
/* 724 */                   throw new IOException("Unexpected end of XML stream");
                        }
/* 726 */               throw new IOException("XML prolog or ROOT element not found on first " + offset + " bytes");
                    }
/* 731 */           int bytesRead = offset;
/* 732 */           if (bytesRead <= 0) {
/* 706 */               return null;
                    }
/* 733 */           is.reset();
/* 734 */           BufferedReader bReader = new BufferedReader(new StringReader(xmlProlog.substring(0, firstGT + 1)));
/* 736 */           StringBuffer prolog = new StringBuffer();
/* 738 */           for (String line = bReader.readLine(); line != null; line = bReader.readLine()) {
/* 739 */               prolog.append(line);
                    }
/* 742 */           Matcher m = ENCODING_PATTERN.matcher(prolog);
/* 743 */           if (!m.find()) {
/* 706 */               return null;
                    }
/* 744 */           String encoding = m.group(1).toUpperCase();
/* 745 */           return encoding.substring(1, encoding.length() - 1);
                }

/* 759 */       static boolean isAppXml(String mime) {
                    return mime != null && (mime.equals("application/xml") || mime.equals("application/xml-dtd") || mime.equals("application/xml-external-parsed-entity") || (mime.startsWith("application/") && mime.endsWith("+xml")));
                }

/* 774 */       static boolean isTextXml(String mime) {
                    return mime != null && (mime.equals("text/xml") || mime.equals("text/xml-external-parsed-entity") || (mime.startsWith("text/") && mime.endsWith("+xml")));
                }
            }
