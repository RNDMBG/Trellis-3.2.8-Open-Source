            package com.squareup.okhttp.internal.spdy;

            import co.vine.android.service.VineService;
            import com.flurry.android.Constants;
            import java.io.IOException;
            import java.util.ArrayList;
            import java.util.Arrays;
            import java.util.Collections;
            import java.util.LinkedHashMap;
            import java.util.List;
            import java.util.Map;
            import okio.Buffer;
            import okio.BufferedSource;
            import okio.ByteString;
            import okio.Okio;
            import okio.Source;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 38 */    final class HpackDraft09 {
                private static final int PREFIX_4_BITS = 15;
                private static final int PREFIX_5_BITS = 31;
                private static final int PREFIX_6_BITS = 63;
                private static final int PREFIX_7_BITS = 127;
                private static final Header[] STATIC_HEADER_TABLE = {new Header(Header.TARGET_AUTHORITY, ""), new Header(Header.TARGET_METHOD, "GET"), new Header(Header.TARGET_METHOD, "POST"), new Header(Header.TARGET_PATH, "/"), new Header(Header.TARGET_PATH, "/index.html"), new Header(Header.TARGET_SCHEME, "http"), new Header(Header.TARGET_SCHEME, "https"), new Header(Header.RESPONSE_STATUS, "200"), new Header(Header.RESPONSE_STATUS, "204"), new Header(Header.RESPONSE_STATUS, "206"), new Header(Header.RESPONSE_STATUS, "304"), new Header(Header.RESPONSE_STATUS, "400"), new Header(Header.RESPONSE_STATUS, "404"), new Header(Header.RESPONSE_STATUS, "500"), new Header("accept-charset", ""), new Header("accept-encoding", "gzip, deflate"), new Header("accept-language", ""), new Header("accept-ranges", ""), new Header(VineService.EXTRA_ACCEPT, ""), new Header("access-control-allow-origin", ""), new Header("age", ""), new Header("allow", ""), new Header("authorization", ""), new Header("cache-control", ""), new Header("content-disposition", ""), new Header("content-encoding", ""), new Header("content-language", ""), new Header("content-length", ""), new Header("content-location", ""), new Header("content-range", ""), new Header("content-type", ""), new Header("cookie", ""), new Header("date", ""), new Header("etag", ""), new Header("expect", ""), new Header("expires", ""), new Header("from", ""), new Header("host", ""), new Header("if-match", ""), new Header("if-modified-since", ""), new Header("if-none-match", ""), new Header("if-range", ""), new Header("if-unmodified-since", ""), new Header("last-modified", ""), new Header("link", ""), new Header("location", ""), new Header("max-forwards", ""), new Header("proxy-authenticate", ""), new Header("proxy-authorization", ""), new Header("range", ""), new Header("referer", ""), new Header("refresh", ""), new Header("retry-after", ""), new Header("server", ""), new Header("set-cookie", ""), new Header("strict-transport-security", ""), new Header("transfer-encoding", ""), new Header("user-agent", ""), new Header("vary", ""), new Header("via", ""), new Header("www-authenticate", "")};
                private static final Map<ByteString, Integer> NAME_TO_FIRST_INDEX = nameToFirstIndex();

/* 39 */        static /* synthetic */ Header[] access$000() {
/* 40 */            return STATIC_HEADER_TABLE;
                }

/* 39 */        static /* synthetic */ ByteString access$100(ByteString x0) throws IOException {
/* 40 */            return checkLowercase(x0);
                }

/* 39 */        static /* synthetic */ Map access$200() {
/* 40 */            return NAME_TO_FIRST_INDEX;
                }

/* 109 */       private HpackDraft09() {
                }

/* 126 */       static final class Reader {
                    private int maxHeaderTableByteCount;
                    private int maxHeaderTableByteCountSetting;
                    private final BufferedSource source;
                    private final List<Header> headerList = new ArrayList();
                    Header[] headerTable = new Header[8];
                    int nextHeaderIndex = this.headerTable.length - 1;
                    int headerCount = 0;
                    int headerTableByteCount = 0;

/* 127 */           Reader(int maxHeaderTableByteCountSetting, Source source) {
/* 129 */               this.maxHeaderTableByteCountSetting = maxHeaderTableByteCountSetting;
/* 130 */               this.maxHeaderTableByteCount = maxHeaderTableByteCountSetting;
/* 131 */               this.source = Okio.buffer(source);
                    }

/* 134 */           int maxHeaderTableByteCount() {
/* 135 */               return this.maxHeaderTableByteCount;
                    }

/* 145 */           void maxHeaderTableByteCountSetting(int newMaxHeaderTableByteCountSetting) {
/* 146 */               this.maxHeaderTableByteCountSetting = newMaxHeaderTableByteCountSetting;
/* 147 */               this.maxHeaderTableByteCount = this.maxHeaderTableByteCountSetting;
/* 148 */               adjustHeaderTableByteCount();
                    }

/* 151 */           private void adjustHeaderTableByteCount() {
/* 152 */               if (this.maxHeaderTableByteCount < this.headerTableByteCount) {
/* 153 */                   if (this.maxHeaderTableByteCount == 0) {
/* 154 */                       clearHeaderTable();
                            } else {
/* 156 */                       evictToRecoverBytes(this.headerTableByteCount - this.maxHeaderTableByteCount);
                            }
                        }
                    }

/* 161 */           private void clearHeaderTable() {
/* 162 */               this.headerList.clear();
/* 163 */               Arrays.fill(this.headerTable, (Object) null);
/* 164 */               this.nextHeaderIndex = this.headerTable.length - 1;
/* 165 */               this.headerCount = 0;
/* 166 */               this.headerTableByteCount = 0;
                    }

/* 170 */           private int evictToRecoverBytes(int bytesToRecover) {
/* 171 */               int entriesToEvict = 0;
/* 172 */               if (bytesToRecover > 0) {
                            for (int j = this.headerTable.length - 1; j >= this.nextHeaderIndex && bytesToRecover > 0; j--) {
/* 175 */                       bytesToRecover -= this.headerTable[j].hpackSize;
/* 176 */                       this.headerTableByteCount -= this.headerTable[j].hpackSize;
/* 177 */                       this.headerCount--;
/* 178 */                       entriesToEvict++;
                            }
/* 180 */                   System.arraycopy(this.headerTable, this.nextHeaderIndex + 1, this.headerTable, this.nextHeaderIndex + 1 + entriesToEvict, this.headerCount);
/* 182 */                   this.nextHeaderIndex += entriesToEvict;
                        }
/* 184 */               return entriesToEvict;
                    }

/* 191 */           void readHeaders() throws IOException {
/* 192 */               while (!this.source.exhausted()) {
/* 193 */                   int b = this.source.readByte() & Constants.UNKNOWN;
/* 194 */                   if (b == 128) {
/* 195 */                       throw new IOException("index == 0");
                            }
/* 196 */                   if ((b & 128) == 128) {
/* 197 */                       int index = readInt(b, 127);
/* 198 */                       readIndexedHeader(index - 1);
                            } else if (b == 64) {
/* 200 */                       readLiteralHeaderWithIncrementalIndexingNewName();
                            } else if ((b & 64) == 64) {
/* 202 */                       int index2 = readInt(b, 63);
/* 203 */                       readLiteralHeaderWithIncrementalIndexingIndexedName(index2 - 1);
                            } else if ((b & 32) == 32) {
/* 205 */                       this.maxHeaderTableByteCount = readInt(b, 31);
/* 206 */                       if (this.maxHeaderTableByteCount < 0 || this.maxHeaderTableByteCount > this.maxHeaderTableByteCountSetting) {
/* 208 */                           throw new IOException("Invalid header table byte count " + this.maxHeaderTableByteCount);
                                }
/* 210 */                       adjustHeaderTableByteCount();
                            } else if (b == 16 || b == 0) {
/* 212 */                       readLiteralHeaderWithoutIndexingNewName();
                            } else {
/* 214 */                       int index3 = readInt(b, 15);
/* 215 */                       readLiteralHeaderWithoutIndexingIndexedName(index3 - 1);
                            }
                        }
                    }

/* 220 */           public List<Header> getAndResetHeaderList() {
/* 221 */               List<Header> result = new ArrayList<>(this.headerList);
/* 222 */               this.headerList.clear();
/* 223 */               return result;
                    }

/* 226 */           private void readIndexedHeader(int index) throws IOException {
/* 227 */               if (isStaticHeader(index)) {
/* 228 */                   Header staticEntry = HpackDraft09.access$000()[index];
/* 229 */                   this.headerList.add(staticEntry);
/* 237 */                   return;
                        }
/* 231 */               int headerTableIndex = headerTableIndex(index - HpackDraft09.access$000().length);
/* 232 */               if (headerTableIndex < 0 || headerTableIndex > this.headerTable.length - 1) {
/* 233 */                   throw new IOException("Header index too large " + (index + 1));
                        }
/* 235 */               this.headerList.add(this.headerTable[headerTableIndex]);
                    }

/* 240 */           private int headerTableIndex(int index) {
/* 241 */               return this.nextHeaderIndex + 1 + index;
                    }

/* 244 */           private void readLiteralHeaderWithoutIndexingIndexedName(int index) throws IOException {
/* 245 */               ByteString name = getName(index);
/* 246 */               ByteString value = readByteString();
/* 247 */               this.headerList.add(new Header(name, value));
                    }

/* 250 */           private void readLiteralHeaderWithoutIndexingNewName() throws IOException {
/* 251 */               ByteString name = HpackDraft09.access$100(readByteString());
/* 252 */               ByteString value = readByteString();
/* 253 */               this.headerList.add(new Header(name, value));
                    }

/* 257 */           private void readLiteralHeaderWithIncrementalIndexingIndexedName(int nameIndex) throws IOException {
/* 258 */               ByteString name = getName(nameIndex);
/* 259 */               ByteString value = readByteString();
/* 260 */               insertIntoHeaderTable(-1, new Header(name, value));
                    }

/* 263 */           private void readLiteralHeaderWithIncrementalIndexingNewName() throws IOException {
/* 264 */               ByteString name = HpackDraft09.access$100(readByteString());
/* 265 */               ByteString value = readByteString();
/* 266 */               insertIntoHeaderTable(-1, new Header(name, value));
                    }

/* 269 */           private ByteString getName(int index) {
                        return isStaticHeader(index) ? HpackDraft09.access$000()[index].name : this.headerTable[headerTableIndex(index - HpackDraft09.access$000().length)].name;
                    }

/* 277 */           private boolean isStaticHeader(int index) {
                        return index >= 0 && index <= HpackDraft09.access$000().length + (-1);
                    }

/* 282 */           private void insertIntoHeaderTable(int index, Header entry) {
/* 283 */               this.headerList.add(entry);
/* 285 */               int delta = entry.hpackSize;
/* 286 */               if (index != -1) {
/* 287 */                   delta -= this.headerTable[headerTableIndex(index)].hpackSize;
                        }
/* 291 */               if (delta > this.maxHeaderTableByteCount) {
/* 292 */                   clearHeaderTable();
/* 315 */                   return;
                        }
/* 297 */               int bytesToRecover = (this.headerTableByteCount + delta) - this.maxHeaderTableByteCount;
/* 298 */               int entriesEvicted = evictToRecoverBytes(bytesToRecover);
/* 300 */               if (index == -1) {
/* 301 */                   if (this.headerCount + 1 > this.headerTable.length) {
/* 302 */                       Header[] doubled = new Header[this.headerTable.length * 2];
/* 303 */                       System.arraycopy(this.headerTable, 0, doubled, this.headerTable.length, this.headerTable.length);
/* 304 */                       this.nextHeaderIndex = this.headerTable.length - 1;
/* 305 */                       this.headerTable = doubled;
                            }
/* 307 */                   int index2 = this.nextHeaderIndex;
                            this.nextHeaderIndex = index2 - 1;
/* 308 */                   this.headerTable[index2] = entry;
/* 309 */                   this.headerCount++;
                        } else {
/* 312 */                   this.headerTable[index + headerTableIndex(index) + entriesEvicted] = entry;
                        }
/* 314 */               this.headerTableByteCount += delta;
                    }

/* 317 */           private int readByte() throws IOException {
/* 318 */               return this.source.readByte() & Constants.UNKNOWN;
                    }

/* 321 */           int readInt(int firstByte, int prefixMask) throws IOException {
/* 322 */               int prefix = firstByte & prefixMask;
/* 323 */               if (prefix >= prefixMask) {
/* 328 */                   int result = prefixMask;
/* 329 */                   int shift = 0;
                            while (true) {
/* 331 */                       int b = readByte();
/* 332 */                       if ((b & 128) != 0) {
/* 333 */                           result += (b & 127) << shift;
/* 334 */                           shift += 7;
                                } else {
/* 336 */                           return result + (b << shift);
                                }
                            }
                        } else {
/* 340 */                   return prefix;
                        }
                    }

/* 344 */           ByteString readByteString() throws IOException {
/* 345 */               int firstByte = readByte();
/* 346 */               boolean huffmanDecode = (firstByte & 128) == 128;
/* 347 */               int length = readInt(firstByte, 127);
/* 349 */               if (huffmanDecode) {
/* 350 */                   return ByteString.m2468of(Huffman.get().decode(this.source.readByteArray(length)));
                        }
/* 352 */               return this.source.readByteString(length);
                    }
                }

/* 359 */       private static Map<ByteString, Integer> nameToFirstIndex() {
/* 360 */           Map<ByteString, Integer> result = new LinkedHashMap<>(STATIC_HEADER_TABLE.length);
                    for (int i = 0; i < STATIC_HEADER_TABLE.length; i++) {
/* 362 */               if (!result.containsKey(STATIC_HEADER_TABLE[i].name)) {
/* 363 */                   result.put(STATIC_HEADER_TABLE[i].name, Integer.valueOf(i));
                        }
                    }
/* 366 */           return Collections.unmodifiableMap(result);
                }

/* 370 */       static final class Writer {
                    private final Buffer out;

/* 371 */           Writer(Buffer out) {
/* 373 */               this.out = out;
                    }

/* 379 */           void writeHeaders(List<Header> headerBlock) throws IOException {
                        int size = headerBlock.size();
                        for (int i = 0; i < size; i++) {
/* 381 */                   ByteString name = headerBlock.get(i).name.toAsciiLowercase();
/* 382 */                   Integer staticIndex = (Integer) HpackDraft09.access$200().get(name);
/* 383 */                   if (staticIndex != null) {
/* 385 */                       writeInt(staticIndex.intValue() + 1, 15, 0);
/* 386 */                       writeByteString(headerBlock.get(i).value);
                            } else {
/* 388 */                       this.out.writeByte(0);
/* 389 */                       writeByteString(name);
/* 390 */                       writeByteString(headerBlock.get(i).value);
                            }
                        }
                    }

/* 397 */           void writeInt(int value, int prefixMask, int bits) throws IOException {
/* 398 */               if (value < prefixMask) {
/* 399 */                   this.out.writeByte(bits | value);
/* 414 */                   return;
                        }
/* 404 */               this.out.writeByte(bits | prefixMask);
/* 405 */               int value2 = value - prefixMask;
/* 408 */               while (value2 >= 128) {
/* 409 */                   int b = value2 & 127;
/* 410 */                   this.out.writeByte(b | 128);
/* 411 */                   value2 >>>= 7;
                        }
/* 413 */               this.out.writeByte(value2);
                    }

/* 416 */           void writeByteString(ByteString data) throws IOException {
/* 417 */               writeInt(data.size(), 127, 0);
/* 418 */               this.out.write(data);
                    }
                }

/* 426 */       private static ByteString checkLowercase(ByteString name) throws IOException {
                    int length = name.size();
                    for (int i = 0; i < length; i++) {
/* 428 */               byte c = name.getByte(i);
/* 429 */               if (c >= 65 && c <= 90) {
/* 430 */                   throw new IOException("PROTOCOL_ERROR response malformed: mixed case name: " + name.utf8());
                        }
                    }
/* 433 */           return name;
                }
            }
