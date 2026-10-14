            package com.google.android.exoplayer.text.ttml;

            import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
            import java.util.ArrayList;
            import java.util.Iterator;
            import java.util.List;
            import java.util.TreeSet;
            import org.apache.commons.io.IOUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 53 */    final class TtmlNode {
                public static final String TAG_BODY = "body";
                public static final String TAG_BR = "br";
                public static final String TAG_DIV = "div";
                public static final String TAG_HEAD = "head";
                public static final String TAG_LAYOUT = "layout";
                public static final String TAG_METADATA = "metadata";
                public static final String TAG_P = "p";
                public static final String TAG_REGION = "region";
                public static final String TAG_SMPTE_DATA = "smpte:data";
                public static final String TAG_SMPTE_IMAGE = "smpte:image";
                public static final String TAG_SMPTE_INFORMATION = "smpte:information";
                public static final String TAG_SPAN = "span";
                public static final String TAG_STYLE = "style";
                public static final String TAG_STYLING = "styling";
                public static final String TAG_TT = "tt";
                public static final long UNDEFINED_TIME = -1;
                private List<TtmlNode> children;
                public final long endTimeUs;
                public final boolean isTextNode;
                public final long startTimeUs;
                public final String tag;
                public final String text;

/* 54 */        public static TtmlNode buildTextNode(String text) {
/* 55 */            return new TtmlNode(null, applySpacePolicy(text, true), -1L, -1L);
                }

/* 58 */        public static TtmlNode buildNode(String tag, long startTimeUs, long endTimeUs) {
/* 59 */            return new TtmlNode(tag, null, startTimeUs, endTimeUs);
                }

/* 61 */        private TtmlNode(String tag, String text, long startTimeUs, long endTimeUs) {
/* 63 */            this.tag = tag;
/* 64 */            this.text = text;
/* 65 */            this.isTextNode = text != null;
/* 66 */            this.startTimeUs = startTimeUs;
/* 67 */            this.endTimeUs = endTimeUs;
                }

/* 70 */        public boolean isActive(long timeUs) {
                    return (this.startTimeUs == -1 && this.endTimeUs == -1) || (this.startTimeUs <= timeUs && this.endTimeUs == -1) || ((this.startTimeUs == -1 && timeUs < this.endTimeUs) || (this.startTimeUs <= timeUs && timeUs < this.endTimeUs));
                }

/* 77 */        public void addChild(TtmlNode child) {
/* 78 */            if (this.children == null) {
/* 79 */                this.children = new ArrayList();
                    }
/* 81 */            this.children.add(child);
                }

/* 84 */        public TtmlNode getChild(int index) {
/* 85 */            if (this.children == null) {
/* 86 */                throw new IndexOutOfBoundsException();
                    }
/* 88 */            return this.children.get(index);
                }

/* 91 */        public int getChildCount() {
/* 92 */            if (this.children == null) {
                        return 0;
                    }
                    return this.children.size();
                }

/* 95 */        public long[] getEventTimesUs() {
/* 96 */            TreeSet<Long> eventTimeSet = new TreeSet<>();
/* 97 */            getEventTimes(eventTimeSet, false);
/* 98 */            long[] eventTimes = new long[eventTimeSet.size()];
/* 99 */            Iterator<Long> eventTimeIterator = eventTimeSet.iterator();
/* 100 */           int i = 0;
/* 101 */           while (eventTimeIterator.hasNext()) {
/* 102 */               long eventTimeUs = eventTimeIterator.next().longValue();
                        eventTimes[i] = eventTimeUs;
/* 103 */               i++;
                    }
/* 105 */           return eventTimes;
                }

/* 108 */       private void getEventTimes(TreeSet<Long> out, boolean descendsPNode) {
/* 109 */           boolean isPNode = TAG_P.equals(this.tag);
/* 110 */           if (descendsPNode || isPNode) {
                        if (this.startTimeUs != -1) {
/* 112 */                   out.add(Long.valueOf(this.startTimeUs));
                        }
                        if (this.endTimeUs != -1) {
/* 115 */                   out.add(Long.valueOf(this.endTimeUs));
                        }
                    }
/* 118 */           if (this.children != null) {
                        for (int i = 0; i < this.children.size(); i++) {
/* 122 */                   this.children.get(i).getEventTimes(out, descendsPNode || isPNode);
                        }
                    }
                }

/* 126 */       public String getText(long timeUs) {
/* 127 */           StringBuilder builder = new StringBuilder();
/* 128 */           getText(timeUs, builder, false);
/* 129 */           return applySpacePolicy(builder.toString().replaceAll("\n$", ""), false);
                }

/* 132 */       private void getText(long timeUs, StringBuilder builder, boolean descendsPNode) {
/* 133 */           if (this.isTextNode && descendsPNode) {
/* 134 */               builder.append(this.text);
/* 149 */               return;
                    }
/* 135 */           if (TAG_BR.equals(this.tag) && descendsPNode) {
/* 136 */               builder.append(IOUtils.LINE_SEPARATOR_UNIX);
/* 149 */               return;
                    }
/* 137 */           if (!TAG_METADATA.equals(this.tag) && isActive(timeUs)) {
/* 140 */               boolean isPNode = TAG_P.equals(this.tag);
/* 141 */               int length = builder.length();
                        for (int i = 0; i < getChildCount(); i++) {
/* 143 */                   getChild(i).getText(timeUs, builder, descendsPNode || isPNode);
                        }
/* 145 */               if (isPNode && length != builder.length()) {
/* 146 */                   builder.append(IOUtils.LINE_SEPARATOR_UNIX);
                        }
                    }
                }

/* 160 */       private static String applySpacePolicy(String in, boolean treatLineFeedAsSpace) {
/* 164 */           String out = in.replaceAll("\r\n", IOUtils.LINE_SEPARATOR_UNIX).replaceAll(" *\n *", IOUtils.LINE_SEPARATOR_UNIX);
/* 166 */           if (treatLineFeedAsSpace) {
                        out = out.replaceAll(IOUtils.LINE_SEPARATOR_UNIX, MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
                    }
/* 168 */           return out.replaceAll("[ \t\\x0B\f\r]+", MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
                }
            }
