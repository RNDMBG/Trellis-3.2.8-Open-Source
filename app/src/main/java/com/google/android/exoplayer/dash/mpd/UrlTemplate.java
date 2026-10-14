            package com.google.android.exoplayer.dash.mpd;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 50 */    public final class UrlTemplate {
                private static final String BANDWIDTH = "Bandwidth";
                private static final int BANDWIDTH_ID = 3;
                private static final String DEFAULT_FORMAT_TAG = "%01d";
                private static final String ESCAPED_DOLLAR = "$$";
                private static final String NUMBER = "Number";
                private static final int NUMBER_ID = 2;
                private static final String REPRESENTATION = "RepresentationID";
                private static final int REPRESENTATION_ID = 1;
                private static final String TIME = "Time";
                private static final int TIME_ID = 4;
                private final int identifierCount;
                private final String[] identifierFormatTags;
                private final int[] identifiers;
                private final String[] urlPieces;

/* 51 */        public static UrlTemplate compile(String template) {
/* 52 */            String[] urlPieces = new String[5];
/* 53 */            int[] identifiers = new int[4];
/* 54 */            String[] identifierFormatTags = new String[4];
/* 55 */            int identifierCount = parseTemplate(template, urlPieces, identifiers, identifierFormatTags);
/* 56 */            return new UrlTemplate(urlPieces, identifiers, identifierFormatTags, identifierCount);
                }

/* 62 */        private UrlTemplate(String[] urlPieces, int[] identifiers, String[] identifierFormatTags, int identifierCount) {
/* 64 */            this.urlPieces = urlPieces;
/* 65 */            this.identifiers = identifiers;
/* 66 */            this.identifierFormatTags = identifierFormatTags;
/* 67 */            this.identifierCount = identifierCount;
                }

/* 81 */        public String buildUri(String representationId, int segmentNumber, int bandwidth, long time) {
/* 82 */            StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < this.identifierCount; i++) {
/* 84 */                builder.append(this.urlPieces[i]);
/* 85 */                if (this.identifiers[i] == 1) {
/* 86 */                    builder.append(representationId);
                        } else if (this.identifiers[i] == 2) {
                            builder.append(String.format(this.identifierFormatTags[i], Integer.valueOf(segmentNumber)));
                        } else if (this.identifiers[i] == 3) {
                            builder.append(String.format(this.identifierFormatTags[i], Integer.valueOf(bandwidth)));
                        } else if (this.identifiers[i] == 4) {
                            builder.append(String.format(this.identifierFormatTags[i], Long.valueOf(time)));
                        }
                    }
/* 95 */            builder.append(this.urlPieces[this.identifierCount]);
/* 96 */            return builder.toString();
                }

/* 115 */       private static int parseTemplate(String template, String[] urlPieces, int[] identifiers, String[] identifierFormatTags) {
/* 116 */           urlPieces[0] = "";
/* 117 */           int templateIndex = 0;
/* 118 */           int identifierCount = 0;
/* 119 */           while (templateIndex < template.length()) {
/* 120 */               int dollarIndex = template.indexOf("$", templateIndex);
/* 121 */               if (dollarIndex == -1) {
/* 122 */                   urlPieces[identifierCount] = urlPieces[identifierCount] + template.substring(templateIndex);
/* 123 */                   templateIndex = template.length();
                        } else if (dollarIndex != templateIndex) {
/* 125 */                   urlPieces[identifierCount] = urlPieces[identifierCount] + template.substring(templateIndex, dollarIndex);
/* 126 */                   templateIndex = dollarIndex;
                        } else if (template.startsWith(ESCAPED_DOLLAR, templateIndex)) {
/* 128 */                   urlPieces[identifierCount] = urlPieces[identifierCount] + "$";
/* 129 */                   templateIndex += 2;
                        } else {
/* 131 */                   int secondIndex = template.indexOf("$", templateIndex + 1);
/* 132 */                   String identifier = template.substring(templateIndex + 1, secondIndex);
/* 133 */                   if (identifier.equals(REPRESENTATION)) {
/* 134 */                       identifiers[identifierCount] = 1;
                            } else {
/* 136 */                       int formatTagIndex = identifier.indexOf("%0");
/* 137 */                       String formatTag = DEFAULT_FORMAT_TAG;
/* 138 */                       if (formatTagIndex != -1) {
/* 139 */                           formatTag = identifier.substring(formatTagIndex);
/* 140 */                           if (!formatTag.endsWith("d")) {
/* 141 */                               formatTag = formatTag + "d";
                                    }
/* 143 */                           identifier = identifier.substring(0, formatTagIndex);
                                }
/* 145 */                       if (identifier.equals(NUMBER)) {
/* 146 */                           identifiers[identifierCount] = 2;
                                } else if (identifier.equals(BANDWIDTH)) {
/* 148 */                           identifiers[identifierCount] = 3;
                                } else if (identifier.equals(TIME)) {
/* 150 */                           identifiers[identifierCount] = 4;
                                } else {
/* 152 */                           throw new IllegalArgumentException("Invalid template: " + template);
                                }
/* 154 */                       identifierFormatTags[identifierCount] = formatTag;
                            }
/* 156 */                   identifierCount++;
/* 157 */                   urlPieces[identifierCount] = "";
/* 158 */                   templateIndex = secondIndex + 1;
                        }
                    }
/* 161 */           return identifierCount;
                }
            }
