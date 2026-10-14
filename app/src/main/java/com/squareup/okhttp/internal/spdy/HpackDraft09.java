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
            
            
    final class HpackDraft09 {
                private static final int PREFIX_4_BITS = 15;
                private static final int PREFIX_5_BITS = 31;
                private static final int PREFIX_6_BITS = 63;
                private static final int PREFIX_7_BITS = 127;
                private static final Header[] STATIC_HEADER_TABLE = {new Header(Header.TARGET_AUTHORITY, ""), new Header(Header.TARGET_METHOD, "GET"), new Header(Header.TARGET_METHOD, "POST"), new Header(Header.TARGET_PATH, "/"), new Header(Header.TARGET_PATH, "/index.html"), new Header(Header.TARGET_SCHEME, "http"), new Header(Header.TARGET_SCHEME, "https"), new Header(Header.RESPONSE_STATUS, "200"), new Header(Header.RESPONSE_STATUS, "204"), new Header(Header.RESPONSE_STATUS, "206"), new Header(Header.RESPONSE_STATUS, "304"), new Header(Header.RESPONSE_STATUS, "400"), new Header(Header.RESPONSE_STATUS, "404"), new Header(Header.RESPONSE_STATUS, "500"), new Header("accept-charset", ""), new Header("accept-encoding", "gzip, deflate"), new Header("accept-language", ""), new Header("accept-ranges", ""), new Header(VineService.EXTRA_ACCEPT, ""), new Header("access-control-allow-origin", ""), new Header("age", ""), new Header("allow", ""), new Header("authorization", ""), new Header("cache-control", ""), new Header("content-disposition", ""), new Header("content-encoding", ""), new Header("content-language", ""), new Header("content-length", ""), new Header("content-location", ""), new Header("content-range", ""), new Header("content-type", ""), new Header("cookie", ""), new Header("date", ""), new Header("etag", ""), new Header("expect", ""), new Header("expires", ""), new Header("from", ""), new Header("host", ""), new Header("if-match", ""), new Header("if-modified-since", ""), new Header("if-none-match", ""), new Header("if-range", ""), new Header("if-unmodified-since", ""), new Header("last-modified", ""), new Header("link", ""), new Header("location", ""), new Header("max-forwards", ""), new Header("proxy-authenticate", ""), new Header("proxy-authorization", ""), new Header("range", ""), new Header("referer", ""), new Header("refresh", ""), new Header("retry-after", ""), new Header("server", ""), new Header("set-cookie", ""), new Header("strict-transport-security", ""), new Header("transfer-encoding", ""), new Header("user-agent", ""), new Header("vary", ""), new Header("via", ""), new Header("www-authenticate", "")};
                private static final Map<ByteString, Integer> NAME_TO_FIRST_INDEX = nameToFirstIndex();

        static  Header[] access$000() {
            return STATIC_HEADER_TABLE;
                }

        static  ByteString access$100(ByteString x0) throws IOException {
            return checkLowercase(x0);
                }

        static  Map access$200() {
            return NAME_TO_FIRST_INDEX;
                }

       private HpackDraft09() {
                }

       static final class Reader {
                    private int maxHeaderTableByteCount;
                    private int maxHeaderTableByteCountSetting;
                    private final BufferedSource source;
                    private final List<Header> headerList = new ArrayList();
                    Header[] headerTable = new Header[8];
                    int nextHeaderIndex = this.headerTable.length - 1;
                    int headerCount = 0;
                    int headerTableByteCount = 0;

           Reader(int maxHeaderTableByteCountSetting, Source source) {
               this.maxHeaderTableByteCountSetting = maxHeaderTableByteCountSetting;
               this.maxHeaderTableByteCount = maxHeaderTableByteCountSetting;
               this.source = Okio.buffer(source);
                    }

           int maxHeaderTableByteCount() {
               return this.maxHeaderTableByteCount;
                    }

           void maxHeaderTableByteCountSetting(int newMaxHeaderTableByteCountSetting) {
               this.maxHeaderTableByteCountSetting = newMaxHeaderTableByteCountSetting;
               this.maxHeaderTableByteCount = this.maxHeaderTableByteCountSetting;
               adjustHeaderTableByteCount();
                    }

           private void adjustHeaderTableByteCount() {
               if (this.maxHeaderTableByteCount < this.headerTableByteCount) {
                   if (this.maxHeaderTableByteCount == 0) {
                       clearHeaderTable();
                            } else {
                       evictToRecoverBytes(this.headerTableByteCount - this.maxHeaderTableByteCount);
                            }
                        }
                    }

           private void clearHeaderTable() {
               this.headerList.clear();
               Arrays.fill(this.headerTable, (Object) null);
               this.nextHeaderIndex = this.headerTable.length - 1;
               this.headerCount = 0;
               this.headerTableByteCount = 0;
                    }

           private int evictToRecoverBytes(int bytesToRecover) {
               int entriesToEvict = 0;
               if (bytesToRecover > 0) {
                            for (int j = this.headerTable.length - 1; j >= this.nextHeaderIndex && bytesToRecover > 0; j--) {
                       bytesToRecover -= this.headerTable[j].hpackSize;
                       this.headerTableByteCount -= this.headerTable[j].hpackSize;
                       this.headerCount--;
                       entriesToEvict++;
                            }
                   System.arraycopy(this.headerTable, this.nextHeaderIndex + 1, this.headerTable, this.nextHeaderIndex + 1 + entriesToEvict, this.headerCount);
                   this.nextHeaderIndex += entriesToEvict;
                        }
               return entriesToEvict;
                    }

           void readHeaders() throws IOException {
               while (!this.source.exhausted()) {
                   int b = this.source.readByte() & Constants.UNKNOWN;
                   if (b == 128) {
                       throw new IOException("index == 0");
                            }
                   if ((b & 128) == 128) {
                       int index = readInt(b, 127);
                       readIndexedHeader(index - 1);
                            } else if (b == 64) {
                       readLiteralHeaderWithIncrementalIndexingNewName();
                            } else if ((b & 64) == 64) {
                       int index2 = readInt(b, 63);
                       readLiteralHeaderWithIncrementalIndexingIndexedName(index2 - 1);
                            } else if ((b & 32) == 32) {
                       this.maxHeaderTableByteCount = readInt(b, 31);
                       if (this.maxHeaderTableByteCount < 0 || this.maxHeaderTableByteCount > this.maxHeaderTableByteCountSetting) {
                           throw new IOException("Invalid header table byte count " + this.maxHeaderTableByteCount);
                                }
                       adjustHeaderTableByteCount();
                            } else if (b == 16 || b == 0) {
                       readLiteralHeaderWithoutIndexingNewName();
                            } else {
                       int index3 = readInt(b, 15);
                       readLiteralHeaderWithoutIndexingIndexedName(index3 - 1);
                            }
                        }
                    }

           public List<Header> getAndResetHeaderList() {
               List<Header> result = new ArrayList<>(this.headerList);
               this.headerList.clear();
               return result;
                    }

           private void readIndexedHeader(int index) throws IOException {
               if (isStaticHeader(index)) {
                   Header staticEntry = HpackDraft09.access$000()[index];
                   this.headerList.add(staticEntry);
                   return;
                        }
               int headerTableIndex = headerTableIndex(index - HpackDraft09.access$000().length);
               if (headerTableIndex < 0 || headerTableIndex > this.headerTable.length - 1) {
                   throw new IOException("Header index too large " + (index + 1));
                        }
               this.headerList.add(this.headerTable[headerTableIndex]);
                    }

           private int headerTableIndex(int index) {
               return this.nextHeaderIndex + 1 + index;
                    }

           private void readLiteralHeaderWithoutIndexingIndexedName(int index) throws IOException {
               ByteString name = getName(index);
               ByteString value = readByteString();
               this.headerList.add(new Header(name, value));
                    }

           private void readLiteralHeaderWithoutIndexingNewName() throws IOException {
               ByteString name = HpackDraft09.access$100(readByteString());
               ByteString value = readByteString();
               this.headerList.add(new Header(name, value));
                    }

           private void readLiteralHeaderWithIncrementalIndexingIndexedName(int nameIndex) throws IOException {
               ByteString name = getName(nameIndex);
               ByteString value = readByteString();
               insertIntoHeaderTable(-1, new Header(name, value));
                    }

           private void readLiteralHeaderWithIncrementalIndexingNewName() throws IOException {
               ByteString name = HpackDraft09.access$100(readByteString());
               ByteString value = readByteString();
               insertIntoHeaderTable(-1, new Header(name, value));
                    }

           private ByteString getName(int index) {
                        return isStaticHeader(index) ? HpackDraft09.access$000()[index].name : this.headerTable[headerTableIndex(index - HpackDraft09.access$000().length)].name;
                    }

           private boolean isStaticHeader(int index) {
                        return index >= 0 && index <= HpackDraft09.access$000().length + (-1);
                    }

           private void insertIntoHeaderTable(int index, Header entry) {
               this.headerList.add(entry);
               int delta = entry.hpackSize;
               if (index != -1) {
                   delta -= this.headerTable[headerTableIndex(index)].hpackSize;
                        }
               if (delta > this.maxHeaderTableByteCount) {
                   clearHeaderTable();
                   return;
                        }
               int bytesToRecover = (this.headerTableByteCount + delta) - this.maxHeaderTableByteCount;
               int entriesEvicted = evictToRecoverBytes(bytesToRecover);
               if (index == -1) {
                   if (this.headerCount + 1 > this.headerTable.length) {
                       Header[] doubled = new Header[this.headerTable.length * 2];
                       System.arraycopy(this.headerTable, 0, doubled, this.headerTable.length, this.headerTable.length);
                       this.nextHeaderIndex = this.headerTable.length - 1;
                       this.headerTable = doubled;
                            }
                   int index2 = this.nextHeaderIndex;
                            this.nextHeaderIndex = index2 - 1;
                   this.headerTable[index2] = entry;
                   this.headerCount++;
                        } else {
                   this.headerTable[index + headerTableIndex(index) + entriesEvicted] = entry;
                        }
               this.headerTableByteCount += delta;
                    }

           private int readByte() throws IOException {
               return this.source.readByte() & Constants.UNKNOWN;
                    }

           int readInt(int firstByte, int prefixMask) throws IOException {
               int prefix = firstByte & prefixMask;
               if (prefix >= prefixMask) {
                   int result = prefixMask;
                   int shift = 0;
                            while (true) {
                       int b = readByte();
                       if ((b & 128) != 0) {
                           result += (b & 127) << shift;
                           shift += 7;
                                } else {
                           return result + (b << shift);
                                }
                            }
                        } else {
                   return prefix;
                        }
                    }

           ByteString readByteString() throws IOException {
               int firstByte = readByte();
               boolean huffmanDecode = (firstByte & 128) == 128;
               int length = readInt(firstByte, 127);
               if (huffmanDecode) {
                   return ByteString.m2468of(Huffman.get().decode(this.source.readByteArray(length)));
                        }
               return this.source.readByteString(length);
                    }
                }

       private static Map<ByteString, Integer> nameToFirstIndex() {
           Map<ByteString, Integer> result = new LinkedHashMap<>(STATIC_HEADER_TABLE.length);
                    for (int i = 0; i < STATIC_HEADER_TABLE.length; i++) {
               if (!result.containsKey(STATIC_HEADER_TABLE[i].name)) {
                   result.put(STATIC_HEADER_TABLE[i].name, Integer.valueOf(i));
                        }
                    }
           return Collections.unmodifiableMap(result);
                }

       static final class Writer {
                    private final Buffer out;

           Writer(Buffer out) {
               this.out = out;
                    }

           void writeHeaders(List<Header> headerBlock) throws IOException {
                        int size = headerBlock.size();
                        for (int i = 0; i < size; i++) {
                   ByteString name = headerBlock.get(i).name.toAsciiLowercase();
                   Integer staticIndex = (Integer) HpackDraft09.access$200().get(name);
                   if (staticIndex != null) {
                       writeInt(staticIndex.intValue() + 1, 15, 0);
                       writeByteString(headerBlock.get(i).value);
                            } else {
                       this.out.writeByte(0);
                       writeByteString(name);
                       writeByteString(headerBlock.get(i).value);
                            }
                        }
                    }

           void writeInt(int value, int prefixMask, int bits) throws IOException {
               if (value < prefixMask) {
                   this.out.writeByte(bits | value);
                   return;
                        }
               this.out.writeByte(bits | prefixMask);
               int value2 = value - prefixMask;
               while (value2 >= 128) {
                   int b = value2 & 127;
                   this.out.writeByte(b | 128);
                   value2 >>>= 7;
                        }
               this.out.writeByte(value2);
                    }

           void writeByteString(ByteString data) throws IOException {
               writeInt(data.size(), 127, 0);
               this.out.write(data);
                    }
                }

       private static ByteString checkLowercase(ByteString name) throws IOException {
                    int length = name.size();
                    for (int i = 0; i < length; i++) {
               byte c = name.getByte(i);
               if (c >= 65 && c <= 90) {
                   throw new IOException("PROTOCOL_ERROR response malformed: mixed case name: " + name.utf8());
                        }
                    }
           return name;
                }
            }
