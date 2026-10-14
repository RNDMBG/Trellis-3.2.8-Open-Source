            package com.google.android.exoplayer.text.ttml;

            import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
            import java.util.ArrayList;
            import java.util.Iterator;
            import java.util.List;
            import java.util.TreeSet;
            import org.apache.commons.io.IOUtils;
            
            
    final class TtmlNode {
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

        public static TtmlNode buildTextNode(String text) {
            return new TtmlNode(null, applySpacePolicy(text, true), -1L, -1L);
                }

        public static TtmlNode buildNode(String tag, long startTimeUs, long endTimeUs) {
            return new TtmlNode(tag, null, startTimeUs, endTimeUs);
                }

        private TtmlNode(String tag, String text, long startTimeUs, long endTimeUs) {
            this.tag = tag;
            this.text = text;
            this.isTextNode = text != null;
            this.startTimeUs = startTimeUs;
            this.endTimeUs = endTimeUs;
                }

        public boolean isActive(long timeUs) {
                    return (this.startTimeUs == -1 && this.endTimeUs == -1) || (this.startTimeUs <= timeUs && this.endTimeUs == -1) || ((this.startTimeUs == -1 && timeUs < this.endTimeUs) || (this.startTimeUs <= timeUs && timeUs < this.endTimeUs));
                }

        public void addChild(TtmlNode child) {
            if (this.children == null) {
                this.children = new ArrayList();
                    }
            this.children.add(child);
                }

        public TtmlNode getChild(int index) {
            if (this.children == null) {
                throw new IndexOutOfBoundsException();
                    }
            return this.children.get(index);
                }

        public int getChildCount() {
            if (this.children == null) {
                        return 0;
                    }
                    return this.children.size();
                }

        public long[] getEventTimesUs() {
            TreeSet<Long> eventTimeSet = new TreeSet<>();
            getEventTimes(eventTimeSet, false);
            long[] eventTimes = new long[eventTimeSet.size()];
            Iterator<Long> eventTimeIterator = eventTimeSet.iterator();
           int i = 0;
           while (eventTimeIterator.hasNext()) {
               long eventTimeUs = eventTimeIterator.next().longValue();
                        eventTimes[i] = eventTimeUs;
               i++;
                    }
           return eventTimes;
                }

       private void getEventTimes(TreeSet<Long> out, boolean descendsPNode) {
           boolean isPNode = TAG_P.equals(this.tag);
           if (descendsPNode || isPNode) {
                        if (this.startTimeUs != -1) {
                   out.add(Long.valueOf(this.startTimeUs));
                        }
                        if (this.endTimeUs != -1) {
                   out.add(Long.valueOf(this.endTimeUs));
                        }
                    }
           if (this.children != null) {
                        for (int i = 0; i < this.children.size(); i++) {
                   this.children.get(i).getEventTimes(out, descendsPNode || isPNode);
                        }
                    }
                }

       public String getText(long timeUs) {
           StringBuilder builder = new StringBuilder();
           getText(timeUs, builder, false);
           return applySpacePolicy(builder.toString().replaceAll("\n$", ""), false);
                }

       private void getText(long timeUs, StringBuilder builder, boolean descendsPNode) {
           if (this.isTextNode && descendsPNode) {
               builder.append(this.text);
               return;
                    }
           if (TAG_BR.equals(this.tag) && descendsPNode) {
               builder.append(IOUtils.LINE_SEPARATOR_UNIX);
               return;
                    }
           if (!TAG_METADATA.equals(this.tag) && isActive(timeUs)) {
               boolean isPNode = TAG_P.equals(this.tag);
               int length = builder.length();
                        for (int i = 0; i < getChildCount(); i++) {
                   getChild(i).getText(timeUs, builder, descendsPNode || isPNode);
                        }
               if (isPNode && length != builder.length()) {
                   builder.append(IOUtils.LINE_SEPARATOR_UNIX);
                        }
                    }
                }

       private static String applySpacePolicy(String in, boolean treatLineFeedAsSpace) {
           String out = in.replaceAll("\r\n", IOUtils.LINE_SEPARATOR_UNIX).replaceAll(" *\n *", IOUtils.LINE_SEPARATOR_UNIX);
           if (treatLineFeedAsSpace) {
                        out = out.replaceAll(IOUtils.LINE_SEPARATOR_UNIX, MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
                    }
           return out.replaceAll("[ \t\\x0B\f\r]+", MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
                }
            }
