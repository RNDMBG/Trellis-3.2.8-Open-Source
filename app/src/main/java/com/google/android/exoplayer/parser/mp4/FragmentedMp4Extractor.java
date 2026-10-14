            package com.google.android.exoplayer.parser.mp4;

            import android.annotation.SuppressLint;
            import android.util.Pair;
            import co.vine.android.util.AudioUtils;
            import com.flurry.android.Constants;
            import com.google.android.exoplayer.MediaFormat;
            import com.google.android.exoplayer.ParserException;
            import com.google.android.exoplayer.SampleHolder;
            import com.google.android.exoplayer.parser.Extractor;
            import com.google.android.exoplayer.parser.SegmentIndex;
            import com.google.android.exoplayer.upstream.NonBlockingInputStream;
            import com.google.android.exoplayer.util.Assertions;
            import com.google.android.exoplayer.util.MimeTypes;
            import java.nio.ByteBuffer;
            import java.util.ArrayList;
            import java.util.Arrays;
            import java.util.Collections;
            import java.util.HashMap;
            import java.util.HashSet;
            import java.util.List;
            import java.util.Map;
            import java.util.Set;
            import java.util.Stack;
            import java.util.UUID;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 63 */    public final class FragmentedMp4Extractor implements Extractor {
                private static final int ATOM_HEADER_SIZE = 8;
                private static final Set<Integer> CONTAINER_TYPES;
                private static final int FULL_ATOM_HEADER_SIZE = 12;
                private static final Set<Integer> PARSED_ATOMS;
                private static final int READ_TERMINATING_RESULTS = 39;
                private static final int STATE_READING_ATOM_HEADER = 0;
                private static final int STATE_READING_ATOM_PAYLOAD = 1;
                private static final int STATE_READING_ENCRYPTION_DATA = 2;
                private static final int STATE_READING_SAMPLE = 3;
                public static final int WORKAROUND_EVERY_VIDEO_FRAME_IS_SYNC_FRAME = 1;
                private int atomBytesRead;
                private ParsableByteArray atomData;
                private final ParsableByteArray atomHeader;
                private int atomSize;
                private int atomType;
                private final Stack<Integer> containerAtomEndPoints;
                private final Stack<Atom.ContainerAtom> containerAtoms;
                private final byte[] extendedTypeScratch;
                private DefaultSampleValues extendsDefaults;
                private final TrackFragment fragmentRun;
                private int lastSyncSampleIndex;
                private int parserState;
                private int pendingSeekSyncSampleIndex;
                private int pendingSeekTimeMs;
                private final HashMap<UUID, byte[]> psshData;
                private int rootAtomBytesRead;
                private int sampleIndex;
                private SegmentIndex segmentIndex;
                private Track track;
                private final int workaroundFlags;
                private static final byte[] NAL_START_CODE = {0, 0, 0, 1};
                private static final byte[] PIFF_SAMPLE_ENCRYPTION_BOX_EXTENDED_TYPE = {-94, 57, 79, 82, 90, -101, 79, 20, -94, 68, 108, 66, 124, 100, -115, -12};

                static {
/* 82 */            HashSet<Integer> parsedAtoms = new HashSet<>();
/* 83 */            parsedAtoms.add(Integer.valueOf(Atom.TYPE_avc1));
/* 84 */            parsedAtoms.add(Integer.valueOf(Atom.TYPE_avc3));
/* 85 */            parsedAtoms.add(Integer.valueOf(Atom.TYPE_esds));
/* 86 */            parsedAtoms.add(Integer.valueOf(Atom.TYPE_hdlr));
/* 87 */            parsedAtoms.add(Integer.valueOf(Atom.TYPE_mdat));
/* 88 */            parsedAtoms.add(Integer.valueOf(Atom.TYPE_mdhd));
/* 89 */            parsedAtoms.add(Integer.valueOf(Atom.TYPE_moof));
/* 90 */            parsedAtoms.add(Integer.valueOf(Atom.TYPE_moov));
/* 91 */            parsedAtoms.add(Integer.valueOf(Atom.TYPE_mp4a));
/* 92 */            parsedAtoms.add(Integer.valueOf(Atom.TYPE_sidx));
/* 93 */            parsedAtoms.add(Integer.valueOf(Atom.TYPE_stsd));
/* 94 */            parsedAtoms.add(Integer.valueOf(Atom.TYPE_tfdt));
/* 95 */            parsedAtoms.add(Integer.valueOf(Atom.TYPE_tfhd));
/* 96 */            parsedAtoms.add(Integer.valueOf(Atom.TYPE_tkhd));
/* 97 */            parsedAtoms.add(Integer.valueOf(Atom.TYPE_traf));
/* 98 */            parsedAtoms.add(Integer.valueOf(Atom.TYPE_trak));
/* 99 */            parsedAtoms.add(Integer.valueOf(Atom.TYPE_trex));
/* 100 */           parsedAtoms.add(Integer.valueOf(Atom.TYPE_trun));
/* 101 */           parsedAtoms.add(Integer.valueOf(Atom.TYPE_mvex));
/* 102 */           parsedAtoms.add(Integer.valueOf(Atom.TYPE_mdia));
/* 103 */           parsedAtoms.add(Integer.valueOf(Atom.TYPE_minf));
/* 104 */           parsedAtoms.add(Integer.valueOf(Atom.TYPE_stbl));
/* 105 */           parsedAtoms.add(Integer.valueOf(Atom.TYPE_pssh));
/* 106 */           parsedAtoms.add(Integer.valueOf(Atom.TYPE_saiz));
/* 107 */           parsedAtoms.add(Integer.valueOf(Atom.TYPE_uuid));
/* 108 */           parsedAtoms.add(Integer.valueOf(Atom.TYPE_senc));
/* 109 */           PARSED_ATOMS = Collections.unmodifiableSet(parsedAtoms);
/* 115 */           HashSet<Integer> atomContainerTypes = new HashSet<>();
/* 116 */           atomContainerTypes.add(Integer.valueOf(Atom.TYPE_moov));
/* 117 */           atomContainerTypes.add(Integer.valueOf(Atom.TYPE_trak));
/* 118 */           atomContainerTypes.add(Integer.valueOf(Atom.TYPE_mdia));
/* 119 */           atomContainerTypes.add(Integer.valueOf(Atom.TYPE_minf));
/* 120 */           atomContainerTypes.add(Integer.valueOf(Atom.TYPE_stbl));
/* 121 */           atomContainerTypes.add(Integer.valueOf(Atom.TYPE_avcC));
/* 122 */           atomContainerTypes.add(Integer.valueOf(Atom.TYPE_moof));
/* 123 */           atomContainerTypes.add(Integer.valueOf(Atom.TYPE_traf));
/* 124 */           atomContainerTypes.add(Integer.valueOf(Atom.TYPE_mvex));
/* 125 */           CONTAINER_TYPES = Collections.unmodifiableSet(atomContainerTypes);
                }

/* 155 */       public FragmentedMp4Extractor() {
/* 156 */           this(0);
                }

/* 162 */       public FragmentedMp4Extractor(int workaroundFlags) {
/* 164 */           this.workaroundFlags = workaroundFlags;
/* 165 */           this.parserState = 0;
/* 166 */           this.atomHeader = new ParsableByteArray(8);
/* 167 */           this.extendedTypeScratch = new byte[16];
/* 168 */           this.containerAtoms = new Stack<>();
/* 169 */           this.containerAtomEndPoints = new Stack<>();
/* 170 */           this.fragmentRun = new TrackFragment();
/* 171 */           this.psshData = new HashMap<>();
                }

/* 179 */       public void setTrack(Track track) {
/* 180 */           this.extendsDefaults = new DefaultSampleValues(0, 0, 0, 0);
/* 181 */           this.track = track;
                }

/* 194 */       public void putPsshInfo(UUID uuid, byte[] data) {
/* 195 */           this.psshData.put(uuid, data);
                }

                @Override // com.google.android.exoplayer.parser.Extractor
/* 199 */       public Map<UUID, byte[]> getPsshInfo() {
/* 200 */           if (this.psshData.isEmpty()) {
                        return null;
                    }
                    return this.psshData;
                }

                @Override // com.google.android.exoplayer.parser.Extractor
/* 204 */       public SegmentIndex getIndex() {
/* 205 */           return this.segmentIndex;
                }

                @Override // com.google.android.exoplayer.parser.Extractor
/* 209 */       public boolean hasRelativeIndexOffsets() {
/* 210 */           return true;
                }

                @Override // com.google.android.exoplayer.parser.Extractor
/* 214 */       public MediaFormat getFormat() {
/* 215 */           if (this.track == null) {
                        return null;
                    }
                    return this.track.mediaFormat;
                }

                @Override // com.google.android.exoplayer.parser.Extractor
/* 221 */       public int read(NonBlockingInputStream inputStream, SampleHolder out) throws ParserException {
/* 222 */           int results = 0;
/* 223 */           while ((results & 39) == 0) {
                        try {
                            switch (this.parserState) {
                                case 0:
/* 226 */                           results |= readAtomHeader(inputStream);
                                    break;
                                case 1:
/* 229 */                           results |= readAtomPayload(inputStream);
                                    break;
                                case 2:
/* 232 */                           results |= readEncryptionData(inputStream);
                                    break;
                                default:
/* 235 */                           results |= readOrSkipSample(inputStream, out);
                                    break;
                            }
                        } catch (Exception e) {
/* 241 */                   throw new ParserException(e);
                        }
                    }
/* 239 */           return results;
                }

                @Override // com.google.android.exoplayer.parser.Extractor
/* 246 */       public boolean seekTo(long seekTimeUs, boolean allowNoop) {
/* 247 */           this.pendingSeekTimeMs = (int) (seekTimeUs / 1000);
/* 248 */           if (allowNoop && this.fragmentRun != null && this.pendingSeekTimeMs >= this.fragmentRun.getSamplePresentationTime(0) && this.pendingSeekTimeMs <= this.fragmentRun.getSamplePresentationTime(this.fragmentRun.length - 1)) {
/* 251 */               int sampleIndexFound = 0;
/* 252 */               int syncSampleIndexFound = 0;
                        for (int i = 0; i < this.fragmentRun.length; i++) {
/* 254 */                   if (this.fragmentRun.getSamplePresentationTime(i) <= this.pendingSeekTimeMs) {
/* 255 */                       if (this.fragmentRun.sampleIsSyncFrameTable[i]) {
/* 256 */                           syncSampleIndexFound = i;
                                }
/* 258 */                       sampleIndexFound = i;
                            }
                        }
/* 261 */               if (syncSampleIndexFound == this.lastSyncSampleIndex && sampleIndexFound >= this.sampleIndex) {
/* 262 */                   this.pendingSeekTimeMs = 0;
                            return false;
                        }
                    }
/* 266 */           this.containerAtoms.clear();
/* 267 */           this.containerAtomEndPoints.clear();
/* 268 */           enterState(0);
/* 269 */           return true;
                }

/* 272 */       private void enterState(int state) {
                    switch (state) {
                        case 0:
/* 275 */                   this.atomBytesRead = 0;
/* 276 */                   if (this.containerAtomEndPoints.isEmpty()) {
/* 277 */                       this.rootAtomBytesRead = 0;
                            }
                            break;
                    }
/* 281 */           this.parserState = state;
                }

/* 284 */       private int readAtomHeader(NonBlockingInputStream inputStream) {
/* 285 */           int remainingBytes = 8 - this.atomBytesRead;
/* 286 */           int bytesRead = inputStream.read(this.atomHeader.data, this.atomBytesRead, remainingBytes);
/* 287 */           if (bytesRead == -1) {
                        return 2;
                    }
/* 290 */           this.rootAtomBytesRead += bytesRead;
/* 291 */           this.atomBytesRead += bytesRead;
/* 292 */           if (this.atomBytesRead != 8) {
                        return 1;
                    }
/* 296 */           this.atomHeader.setPosition(0);
/* 297 */           this.atomSize = this.atomHeader.readInt();
/* 298 */           this.atomType = this.atomHeader.readInt();
/* 300 */           if (this.atomType == 1835295092) {
/* 301 */               if (this.fragmentRun.sampleEncryptionDataNeedsFill) {
/* 302 */                   enterState(2);
                        } else {
/* 304 */                   enterState(3);
                        }
                        return 0;
                    }
/* 309 */           if (PARSED_ATOMS.contains(Integer.valueOf(this.atomType))) {
/* 310 */               if (CONTAINER_TYPES.contains(Integer.valueOf(this.atomType))) {
/* 311 */                   enterState(0);
/* 312 */                   this.containerAtoms.add(new Atom.ContainerAtom(this.atomType));
/* 313 */                   this.containerAtomEndPoints.add(Integer.valueOf((this.rootAtomBytesRead + this.atomSize) - 8));
                        } else {
/* 315 */                   this.atomData = new ParsableByteArray(this.atomSize);
/* 316 */                   System.arraycopy(this.atomHeader.data, 0, this.atomData.data, 0, 8);
/* 317 */                   enterState(1);
                        }
                    } else {
/* 320 */               this.atomData = null;
/* 321 */               enterState(1);
                    }
                    return 0;
                }

/* 328 */       private int readAtomPayload(NonBlockingInputStream inputStream) {
                    int bytesRead;
/* 329 */           if (this.atomData != null) {
/* 330 */               bytesRead = inputStream.read(this.atomData.data, this.atomBytesRead, this.atomSize - this.atomBytesRead);
                    } else {
/* 332 */               bytesRead = inputStream.skip(this.atomSize - this.atomBytesRead);
                    }
/* 334 */           if (bytesRead == -1) {
/* 335 */               return 2;
                    }
/* 337 */           this.rootAtomBytesRead += bytesRead;
/* 338 */           this.atomBytesRead += bytesRead;
/* 339 */           if (this.atomBytesRead != this.atomSize) {
/* 340 */               return 1;
                    }
/* 343 */           int results = this.atomData != null ? 0 | onLeafAtomRead(new Atom.LeafAtom(this.atomType, this.atomData)) : 0;
/* 349 */           while (!this.containerAtomEndPoints.isEmpty() && this.containerAtomEndPoints.peek().intValue() == this.rootAtomBytesRead) {
/* 350 */               this.containerAtomEndPoints.pop();
/* 351 */               results |= onContainerAtomRead(this.containerAtoms.pop());
                    }
/* 354 */           enterState(0);
/* 355 */           return results;
                }

/* 358 */       private int onLeafAtomRead(Atom.LeafAtom leaf) {
/* 359 */           if (!this.containerAtoms.isEmpty()) {
/* 360 */               this.containerAtoms.peek().add(leaf);
                    } else if (leaf.type == 1936286840) {
/* 362 */               this.segmentIndex = parseSidx(leaf.data);
/* 363 */               return 16;
                    }
/* 365 */           return 0;
                }

/* 368 */       private int onContainerAtomRead(Atom.ContainerAtom container) {
/* 369 */           if (container.type == 1836019574) {
/* 370 */               onMoovContainerAtomRead(container);
/* 371 */               return 8;
                    }
/* 372 */           if (container.type == 1836019558) {
/* 373 */               onMoofContainerAtomRead(container);
                    } else if (!this.containerAtoms.isEmpty()) {
/* 375 */               this.containerAtoms.peek().add(container);
                    }
/* 377 */           return 0;
                }

/* 380 */       private void onMoovContainerAtomRead(Atom.ContainerAtom moov) {
/* 381 */           List<Atom> moovChildren = moov.children;
/* 382 */           int moovChildrenSize = moovChildren.size();
                    for (int i = 0; i < moovChildrenSize; i++) {
/* 384 */               Atom child = moovChildren.get(i);
/* 385 */               if (child.type == 1886614376) {
/* 386 */                   ParsableByteArray psshAtom = ((Atom.LeafAtom) child).data;
/* 387 */                   psshAtom.setPosition(12);
/* 388 */                   UUID uuid = new UUID(psshAtom.readLong(), psshAtom.readLong());
/* 389 */                   int dataSize = psshAtom.readInt();
/* 390 */                   byte[] data = new byte[dataSize];
/* 391 */                   psshAtom.readBytes(data, 0, dataSize);
/* 392 */                   this.psshData.put(uuid, data);
                        }
                    }
/* 395 */           Atom.ContainerAtom mvex = moov.getContainerAtomOfType(Atom.TYPE_mvex);
/* 396 */           this.extendsDefaults = parseTrex(mvex.getLeafAtomOfType(Atom.TYPE_trex).data);
/* 397 */           this.track = parseTrak(moov.getContainerAtomOfType(Atom.TYPE_trak));
                }

/* 400 */       private void onMoofContainerAtomRead(Atom.ContainerAtom moof) {
/* 401 */           this.fragmentRun.reset();
/* 402 */           parseMoof(this.track, this.extendsDefaults, moof, this.fragmentRun, this.workaroundFlags, this.extendedTypeScratch);
/* 403 */           this.sampleIndex = 0;
/* 404 */           this.lastSyncSampleIndex = 0;
/* 405 */           this.pendingSeekSyncSampleIndex = 0;
/* 406 */           if (this.pendingSeekTimeMs != 0) {
                        for (int i = 0; i < this.fragmentRun.length; i++) {
/* 408 */                   if (this.fragmentRun.sampleIsSyncFrameTable[i] && this.fragmentRun.getSamplePresentationTime(i) <= this.pendingSeekTimeMs) {
/* 410 */                       this.pendingSeekSyncSampleIndex = i;
                            }
                        }
/* 414 */               this.pendingSeekTimeMs = 0;
                    }
                }

/* 421 */       private static DefaultSampleValues parseTrex(ParsableByteArray trex) {
/* 422 */           trex.setPosition(16);
/* 423 */           int defaultSampleDescriptionIndex = trex.readUnsignedIntToInt() - 1;
/* 424 */           int defaultSampleDuration = trex.readUnsignedIntToInt();
/* 425 */           int defaultSampleSize = trex.readUnsignedIntToInt();
/* 426 */           int defaultSampleFlags = trex.readInt();
/* 427 */           return new DefaultSampleValues(defaultSampleDescriptionIndex, defaultSampleDuration, defaultSampleSize, defaultSampleFlags);
                }

/* 434 */       private static Track parseTrak(Atom.ContainerAtom trak) {
/* 435 */           Atom.ContainerAtom mdia = trak.getContainerAtomOfType(Atom.TYPE_mdia);
/* 436 */           int trackType = parseHdlr(mdia.getLeafAtomOfType(Atom.TYPE_hdlr).data);
/* 437 */           Assertions.checkState(trackType == 1936684398 || trackType == 1986618469);
/* 439 */           Pair<Integer, Long> header = parseTkhd(trak.getLeafAtomOfType(Atom.TYPE_tkhd).data);
/* 440 */           int id = ((Integer) header.first).intValue();
/* 445 */           long timescale = parseMdhd(mdia.getLeafAtomOfType(Atom.TYPE_mdhd).data);
/* 446 */           Atom.ContainerAtom stbl = mdia.getContainerAtomOfType(Atom.TYPE_minf).getContainerAtomOfType(Atom.TYPE_stbl);
/* 449 */           Pair<MediaFormat, TrackEncryptionBox[]> sampleDescriptions = parseStsd(stbl.getLeafAtomOfType(Atom.TYPE_stsd).data);
/* 451 */           return new Track(id, trackType, timescale, (MediaFormat) sampleDescriptions.first, (TrackEncryptionBox[]) sampleDescriptions.second);
                }

/* 459 */       private static Pair<Integer, Long> parseTkhd(ParsableByteArray tkhd) {
/* 460 */           tkhd.setPosition(8);
/* 461 */           int fullAtom = tkhd.readInt();
/* 462 */           int version = parseFullAtomVersion(fullAtom);
/* 464 */           tkhd.skip(version != 0 ? 16 : 8);
/* 466 */           int trackId = tkhd.readInt();
/* 467 */           tkhd.skip(4);
/* 468 */           long duration = version == 0 ? tkhd.readUnsignedInt() : tkhd.readUnsignedLongToLong();
/* 470 */           return Pair.create(Integer.valueOf(trackId), Long.valueOf(duration));
                }

/* 479 */       private static int parseHdlr(ParsableByteArray hdlr) {
/* 480 */           hdlr.setPosition(16);
/* 481 */           return hdlr.readInt();
                }

/* 490 */       private static long parseMdhd(ParsableByteArray mdhd) {
/* 491 */           mdhd.setPosition(8);
/* 492 */           int fullAtom = mdhd.readInt();
/* 493 */           int version = parseFullAtomVersion(fullAtom);
/* 495 */           mdhd.skip(version != 0 ? 16 : 8);
/* 496 */           return mdhd.readUnsignedInt();
                }

/* 499 */       private static Pair<MediaFormat, TrackEncryptionBox[]> parseStsd(ParsableByteArray stsd) {
/* 500 */           stsd.setPosition(12);
/* 501 */           int numberOfEntries = stsd.readInt();
/* 502 */           MediaFormat mediaFormat = null;
/* 503 */           TrackEncryptionBox[] trackEncryptionBoxes = new TrackEncryptionBox[numberOfEntries];
                    for (int i = 0; i < numberOfEntries; i++) {
/* 505 */               int childStartPosition = stsd.getPosition();
/* 506 */               int childAtomSize = stsd.readInt();
/* 507 */               int childAtomType = stsd.readInt();
/* 508 */               if (childAtomType == 1635148593 || childAtomType == 1635148595 || childAtomType == 1701733238) {
/* 510 */                   Pair<MediaFormat, TrackEncryptionBox> avc = parseAvcFromParent(stsd, childStartPosition, childAtomSize);
/* 512 */                   mediaFormat = (MediaFormat) avc.first;
/* 513 */                   trackEncryptionBoxes[i] = (TrackEncryptionBox) avc.second;
                        } else if (childAtomType == 1836069985 || childAtomType == 1701733217) {
/* 515 */                   Pair<MediaFormat, TrackEncryptionBox> mp4a = parseMp4aFromParent(stsd, childStartPosition, childAtomSize);
/* 517 */                   mediaFormat = (MediaFormat) mp4a.first;
/* 518 */                   trackEncryptionBoxes[i] = (TrackEncryptionBox) mp4a.second;
                        }
/* 520 */               stsd.setPosition(childStartPosition + childAtomSize);
                    }
/* 522 */           return Pair.create(mediaFormat, trackEncryptionBoxes);
                }

/* 526 */       private static Pair<MediaFormat, TrackEncryptionBox> parseAvcFromParent(ParsableByteArray parent, int position, int size) {
/* 527 */           parent.setPosition(position + 8);
/* 529 */           parent.skip(24);
/* 530 */           int width = parent.readUnsignedShort();
/* 531 */           int height = parent.readUnsignedShort();
/* 532 */           parent.skip(50);
/* 534 */           List<byte[]> initializationData = null;
/* 535 */           TrackEncryptionBox trackEncryptionBox = null;
/* 536 */           int childPosition = parent.getPosition();
/* 537 */           while (childPosition - position < size) {
/* 538 */               parent.setPosition(childPosition);
/* 539 */               int childStartPosition = parent.getPosition();
/* 540 */               int childAtomSize = parent.readInt();
/* 541 */               int childAtomType = parent.readInt();
/* 542 */               if (childAtomType == 1635148611) {
/* 543 */                   initializationData = parseAvcCFromParent(parent, childStartPosition);
                        } else if (childAtomType == 1936289382) {
/* 545 */                   trackEncryptionBox = parseSinfFromParent(parent, childStartPosition, childAtomSize);
                        }
/* 547 */               childPosition += childAtomSize;
                    }
/* 550 */           MediaFormat format = MediaFormat.createVideoFormat("video/avc", -1, width, height, initializationData);
/* 552 */           return Pair.create(format, trackEncryptionBox);
                }

/* 556 */       private static Pair<MediaFormat, TrackEncryptionBox> parseMp4aFromParent(ParsableByteArray parent, int position, int size) {
/* 557 */           parent.setPosition(position + 8);
/* 559 */           parent.skip(16);
/* 560 */           int channelCount = parent.readUnsignedShort();
/* 561 */           int sampleSize = parent.readUnsignedShort();
/* 562 */           parent.skip(4);
/* 563 */           int sampleRate = parent.readUnsignedFixedPoint1616();
/* 565 */           byte[] initializationData = null;
/* 566 */           TrackEncryptionBox trackEncryptionBox = null;
/* 567 */           int childPosition = parent.getPosition();
/* 568 */           while (childPosition - position < size) {
/* 569 */               parent.setPosition(childPosition);
/* 570 */               int childStartPosition = parent.getPosition();
/* 571 */               int childAtomSize = parent.readInt();
/* 572 */               int childAtomType = parent.readInt();
/* 573 */               if (childAtomType == 1702061171) {
/* 574 */                   initializationData = parseEsdsFromParent(parent, childStartPosition);
/* 577 */                   Pair<Integer, Integer> audioSpecificConfig = com.google.android.exoplayer.util.CodecSpecificDataUtil.parseAudioSpecificConfig(initializationData);
/* 579 */                   sampleRate = ((Integer) audioSpecificConfig.first).intValue();
/* 580 */                   channelCount = ((Integer) audioSpecificConfig.second).intValue();
                        } else if (childAtomType == 1936289382) {
/* 582 */                   trackEncryptionBox = parseSinfFromParent(parent, childStartPosition, childAtomSize);
                        }
/* 584 */               childPosition += childAtomSize;
                    }
/* 587 */           MediaFormat format = MediaFormat.createAudioFormat(MimeTypes.AUDIO_AAC, sampleSize, channelCount, sampleRate, Collections.singletonList(initializationData));
/* 589 */           return Pair.create(format, trackEncryptionBox);
                }

/* 592 */       private static List<byte[]> parseAvcCFromParent(ParsableByteArray parent, int position) {
/* 593 */           parent.setPosition(position + 8 + 4);
/* 595 */           int nalUnitLength = (parent.readUnsignedByte() & 3) + 1;
/* 596 */           if (nalUnitLength != 4) {
/* 599 */               throw new IllegalStateException();
                    }
/* 601 */           List<byte[]> initializationData = new ArrayList<>();
/* 604 */           int numSequenceParameterSets = parent.readUnsignedByte() & 31;
                    for (int j = 0; j < numSequenceParameterSets; j++) {
/* 606 */               initializationData.add(parseChildNalUnit(parent));
                    }
/* 608 */           int numPictureParamterSets = parent.readUnsignedByte();
                    for (int j2 = 0; j2 < numPictureParamterSets; j2++) {
/* 610 */               initializationData.add(parseChildNalUnit(parent));
                    }
/* 612 */           return initializationData;
                }

/* 615 */       private static byte[] parseChildNalUnit(ParsableByteArray atom) {
/* 616 */           int length = atom.readUnsignedShort();
/* 617 */           int offset = atom.getPosition();
/* 618 */           atom.skip(length);
/* 619 */           return com.google.android.exoplayer.util.CodecSpecificDataUtil.buildNalUnit(atom.data, offset, length);
                }

/* 623 */       private static TrackEncryptionBox parseSinfFromParent(ParsableByteArray parent, int position, int size) {
/* 624 */           int childPosition = position + 8;
/* 626 */           TrackEncryptionBox trackEncryptionBox = null;
/* 627 */           while (childPosition - position < size) {
/* 628 */               parent.setPosition(childPosition);
/* 629 */               int childAtomSize = parent.readInt();
/* 630 */               int childAtomType = parent.readInt();
/* 631 */               if (childAtomType == 1718775137) {
/* 632 */                   parent.readInt();
                        } else if (childAtomType == 1935894637) {
/* 634 */                   parent.skip(4);
/* 635 */                   parent.readInt();
/* 636 */                   parent.readInt();
                        } else if (childAtomType == 1935894633) {
/* 638 */                   trackEncryptionBox = parseSchiFromParent(parent, childPosition, childAtomSize);
                        }
/* 640 */               childPosition += childAtomSize;
                    }
/* 643 */           return trackEncryptionBox;
                }

/* 647 */       private static TrackEncryptionBox parseSchiFromParent(ParsableByteArray parent, int position, int size) {
/* 648 */           int childPosition = position + 8;
/* 649 */           while (childPosition - position < size) {
/* 650 */               parent.setPosition(childPosition);
/* 651 */               int childAtomSize = parent.readInt();
/* 652 */               int childAtomType = parent.readInt();
/* 653 */               if (childAtomType == 1952804451) {
/* 654 */                   parent.skip(4);
/* 655 */                   int firstInt = parent.readInt();
/* 656 */                   boolean defaultIsEncrypted = (firstInt >> 8) == 1;
/* 657 */                   int defaultInitVectorSize = firstInt & 255;
/* 658 */                   byte[] defaultKeyId = new byte[16];
/* 659 */                   parent.readBytes(defaultKeyId, 0, defaultKeyId.length);
/* 660 */                   return new TrackEncryptionBox(defaultIsEncrypted, defaultInitVectorSize, defaultKeyId);
                        }
/* 662 */               childPosition += childAtomSize;
                    }
/* 664 */           return null;
                }

/* 667 */       private static byte[] parseEsdsFromParent(ParsableByteArray parent, int position) {
/* 668 */           parent.setPosition(position + 8 + 4);
/* 670 */           parent.skip(1);
/* 671 */           int varIntByte = parent.readUnsignedByte();
/* 672 */           while (varIntByte > 127) {
/* 673 */               varIntByte = parent.readUnsignedByte();
                    }
/* 675 */           parent.skip(2);
/* 677 */           int flags = parent.readUnsignedByte();
/* 678 */           if ((flags & 128) != 0) {
/* 679 */               parent.skip(2);
                    }
/* 681 */           if ((flags & 64) != 0) {
/* 682 */               parent.skip(parent.readUnsignedShort());
                    }
/* 684 */           if ((flags & 32) != 0) {
/* 685 */               parent.skip(2);
                    }
/* 689 */           parent.skip(1);
/* 690 */           int varIntByte2 = parent.readUnsignedByte();
/* 691 */           while (varIntByte2 > 127) {
/* 692 */               varIntByte2 = parent.readUnsignedByte();
                    }
/* 694 */           parent.skip(13);
/* 697 */           parent.skip(1);
/* 698 */           int varIntByte3 = parent.readUnsignedByte();
/* 699 */           int varInt = varIntByte3 & 127;
/* 700 */           while (varIntByte3 > 127) {
/* 701 */               varIntByte3 = parent.readUnsignedByte();
/* 703 */               varInt = (varInt << 8) | (varIntByte3 & 127);
                    }
/* 705 */           byte[] initializationData = new byte[varInt];
/* 706 */           parent.readBytes(initializationData, 0, varInt);
/* 707 */           return initializationData;
                }

/* 711 */       private static void parseMoof(Track track, DefaultSampleValues extendsDefaults, Atom.ContainerAtom moof, TrackFragment out, int workaroundFlags, byte[] extendedTypeScratch) {
/* 712 */           parseTraf(track, extendsDefaults, moof.getContainerAtomOfType(Atom.TYPE_traf), out, workaroundFlags, extendedTypeScratch);
                }

/* 720 */       private static void parseTraf(Track track, DefaultSampleValues extendsDefaults, Atom.ContainerAtom traf, TrackFragment out, int workaroundFlags, byte[] extendedTypeScratch) {
/* 721 */           Atom.LeafAtom tfdtAtom = traf.getLeafAtomOfType(Atom.TYPE_tfdt);
/* 722 */           long decodeTime = tfdtAtom == null ? 0L : parseTfdt(traf.getLeafAtomOfType(Atom.TYPE_tfdt).data);
/* 724 */           Atom.LeafAtom tfhd = traf.getLeafAtomOfType(Atom.TYPE_tfhd);
/* 725 */           DefaultSampleValues fragmentHeader = parseTfhd(extendsDefaults, tfhd.data);
/* 726 */           out.sampleDescriptionIndex = fragmentHeader.sampleDescriptionIndex;
/* 728 */           Atom.LeafAtom trun = traf.getLeafAtomOfType(Atom.TYPE_trun);
/* 729 */           parseTrun(track, fragmentHeader, decodeTime, workaroundFlags, trun.data, out);
/* 731 */           Atom.LeafAtom saiz = traf.getLeafAtomOfType(Atom.TYPE_saiz);
/* 732 */           if (saiz != null) {
/* 733 */               TrackEncryptionBox trackEncryptionBox = track.sampleDescriptionEncryptionBoxes[fragmentHeader.sampleDescriptionIndex];
/* 735 */               parseSaiz(trackEncryptionBox, saiz.data, out);
                    }
/* 738 */           Atom.LeafAtom senc = traf.getLeafAtomOfType(Atom.TYPE_senc);
/* 739 */           if (senc != null) {
/* 740 */               parseSenc(senc.data, out);
                    }
/* 743 */           Atom.LeafAtom uuid = traf.getLeafAtomOfType(Atom.TYPE_uuid);
/* 744 */           if (uuid != null) {
/* 745 */               parseUuid(uuid.data, out, extendedTypeScratch);
                    }
                }

/* 750 */       private static void parseSaiz(TrackEncryptionBox encryptionBox, ParsableByteArray saiz, TrackFragment out) {
/* 751 */           int vectorSize = encryptionBox.initializationVectorSize;
/* 752 */           saiz.setPosition(8);
/* 753 */           int fullAtom = saiz.readInt();
/* 754 */           int flags = parseFullAtomFlags(fullAtom);
/* 755 */           if ((flags & 1) == 1) {
/* 756 */               saiz.skip(8);
                    }
/* 758 */           int defaultSampleInfoSize = saiz.readUnsignedByte();
/* 760 */           int sampleCount = saiz.readUnsignedIntToInt();
/* 761 */           if (sampleCount != out.length) {
/* 762 */               throw new IllegalStateException("Length mismatch: " + sampleCount + ", " + out.length);
                    }
/* 765 */           int totalSize = 0;
/* 766 */           if (defaultSampleInfoSize == 0) {
/* 767 */               boolean[] sampleHasSubsampleEncryptionTable = out.sampleHasSubsampleEncryptionTable;
                        for (int i = 0; i < sampleCount; i++) {
/* 769 */                   int sampleInfoSize = saiz.readUnsignedByte();
/* 770 */                   totalSize += sampleInfoSize;
/* 771 */                   sampleHasSubsampleEncryptionTable[i] = sampleInfoSize > vectorSize;
                        }
                    } else {
/* 774 */               boolean subsampleEncryption = defaultSampleInfoSize > vectorSize;
/* 765 */               totalSize = 0 + (defaultSampleInfoSize * sampleCount);
/* 776 */               Arrays.fill(out.sampleHasSubsampleEncryptionTable, 0, sampleCount, subsampleEncryption);
                    }
/* 778 */           out.initEncryptionData(totalSize);
                }

/* 788 */       private static DefaultSampleValues parseTfhd(DefaultSampleValues extendsDefaults, ParsableByteArray tfhd) {
/* 789 */           tfhd.setPosition(8);
/* 790 */           int fullAtom = tfhd.readInt();
/* 791 */           int flags = parseFullAtomFlags(fullAtom);
/* 793 */           tfhd.skip(4);
/* 794 */           if ((flags & 1) != 0) {
/* 795 */               tfhd.skip(8);
                    }
/* 798 */           int defaultSampleDescriptionIndex = (flags & 2) != 0 ? tfhd.readUnsignedIntToInt() - 1 : extendsDefaults.sampleDescriptionIndex;
/* 801 */           int defaultSampleDuration = (flags & 8) != 0 ? tfhd.readUnsignedIntToInt() : extendsDefaults.duration;
/* 803 */           int defaultSampleSize = (flags & 16) != 0 ? tfhd.readUnsignedIntToInt() : extendsDefaults.size;
/* 805 */           int defaultSampleFlags = (flags & 32) != 0 ? tfhd.readUnsignedIntToInt() : extendsDefaults.flags;
/* 807 */           return new DefaultSampleValues(defaultSampleDescriptionIndex, defaultSampleDuration, defaultSampleSize, defaultSampleFlags);
                }

/* 817 */       private static long parseTfdt(ParsableByteArray tfdt) {
/* 818 */           tfdt.setPosition(8);
/* 819 */           int fullAtom = tfdt.readInt();
/* 820 */           int version = parseFullAtomVersion(fullAtom);
                    return version == 1 ? tfdt.readUnsignedLongToLong() : tfdt.readUnsignedInt();
                }

/* 834 */       private static void parseTrun(Track track, DefaultSampleValues defaultSampleValues, long decodeTime, int workaroundFlags, ParsableByteArray trun, TrackFragment out) {
                    int sampleFlags;
/* 835 */           trun.setPosition(8);
/* 836 */           int fullAtom = trun.readInt();
/* 837 */           int flags = parseFullAtomFlags(fullAtom);
/* 839 */           int sampleCount = trun.readUnsignedIntToInt();
/* 840 */           if ((flags & 1) != 0) {
/* 841 */               trun.skip(4);
                    }
/* 844 */           boolean firstSampleFlagsPresent = (flags & 4) != 0;
/* 845 */           int firstSampleFlags = defaultSampleValues.flags;
/* 846 */           if (firstSampleFlagsPresent) {
/* 847 */               firstSampleFlags = trun.readUnsignedIntToInt();
                    }
/* 850 */           boolean sampleDurationsPresent = (flags & 256) != 0;
/* 851 */           boolean sampleSizesPresent = (flags & 512) != 0;
/* 852 */           boolean sampleFlagsPresent = (flags & 1024) != 0;
/* 853 */           boolean sampleCompositionTimeOffsetsPresent = (flags & 2048) != 0;
/* 856 */           out.initTables(sampleCount);
/* 857 */           int[] sampleSizeTable = out.sampleSizeTable;
/* 858 */           int[] sampleDecodingTimeTable = out.sampleDecodingTimeTable;
/* 859 */           int[] sampleCompositionTimeOffsetTable = out.sampleCompositionTimeOffsetTable;
/* 860 */           boolean[] sampleIsSyncFrameTable = out.sampleIsSyncFrameTable;
/* 862 */           long timescale = track.timescale;
/* 863 */           long cumulativeTime = decodeTime;
/* 864 */           boolean workaroundEveryVideoFrameIsSyncFrame = track.type == 1986618469 && (workaroundFlags & 1) == 1;
/* 867 */           int i = 0;
                    while (i < sampleCount) {
/* 869 */               int sampleDuration = sampleDurationsPresent ? trun.readUnsignedIntToInt() : defaultSampleValues.duration;
/* 871 */               int sampleSize = sampleSizesPresent ? trun.readUnsignedIntToInt() : defaultSampleValues.size;
/* 872 */               if (i == 0 && firstSampleFlagsPresent) {
                            sampleFlags = firstSampleFlags;
                        } else {
/* 872 */                   sampleFlags = sampleFlagsPresent ? trun.readInt() : defaultSampleValues.flags;
                        }
/* 874 */               if (sampleCompositionTimeOffsetsPresent) {
/* 880 */                   int sampleOffset = trun.readInt();
/* 881 */                   sampleCompositionTimeOffsetTable[i] = (int) (((long) (sampleOffset * 1000)) / timescale);
                        } else {
/* 883 */                   sampleCompositionTimeOffsetTable[i] = 0;
                        }
/* 885 */               sampleDecodingTimeTable[i] = (int) ((1000 * cumulativeTime) / timescale);
/* 886 */               sampleSizeTable[i] = sampleSize;
/* 887 */               sampleIsSyncFrameTable[i] = ((sampleFlags >> 16) & 1) == 0 && (!workaroundEveryVideoFrameIsSyncFrame || i == 0);
/* 889 */               cumulativeTime += (long) sampleDuration;
/* 867 */               i++;
                    }
                }

/* 894 */       private static void parseUuid(ParsableByteArray uuid, TrackFragment out, byte[] extendedTypeScratch) {
/* 895 */           uuid.setPosition(8);
/* 896 */           uuid.readBytes(extendedTypeScratch, 0, 16);
/* 899 */           if (Arrays.equals(extendedTypeScratch, PIFF_SAMPLE_ENCRYPTION_BOX_EXTENDED_TYPE)) {
/* 906 */               parseSenc(uuid, 16, out);
                    }
                }

/* 909 */       private static void parseSenc(ParsableByteArray senc, TrackFragment out) {
/* 910 */           parseSenc(senc, 0, out);
                }

/* 913 */       private static void parseSenc(ParsableByteArray senc, int offset, TrackFragment out) {
/* 914 */           senc.setPosition(offset + 8);
/* 915 */           int fullAtom = senc.readInt();
/* 916 */           int flags = parseFullAtomFlags(fullAtom);
/* 918 */           if ((flags & 1) != 0) {
/* 920 */               throw new IllegalStateException("Overriding TrackEncryptionBox parameters is unsupported");
                    }
/* 923 */           boolean subsampleEncryption = (flags & 2) != 0;
/* 924 */           int sampleCount = senc.readUnsignedIntToInt();
/* 925 */           if (sampleCount != out.length) {
/* 926 */               throw new IllegalStateException("Length mismatch: " + sampleCount + ", " + out.length);
                    }
/* 929 */           Arrays.fill(out.sampleHasSubsampleEncryptionTable, 0, sampleCount, subsampleEncryption);
/* 930 */           out.initEncryptionData(senc.length() - senc.getPosition());
/* 931 */           out.fillEncryptionData(senc);
                }

/* 937 */       private static SegmentIndex parseSidx(ParsableByteArray atom) {
                    long earliestPresentationTime;
                    long firstOffset;
/* 938 */           atom.setPosition(8);
/* 939 */           int fullAtom = atom.readInt();
/* 940 */           int version = parseFullAtomVersion(fullAtom);
/* 942 */           atom.skip(4);
/* 943 */           long timescale = atom.readUnsignedInt();
/* 946 */           if (version == 0) {
/* 947 */               earliestPresentationTime = atom.readUnsignedInt();
/* 948 */               firstOffset = atom.readUnsignedInt();
                    } else {
/* 950 */               earliestPresentationTime = atom.readUnsignedLongToLong();
/* 951 */               firstOffset = atom.readUnsignedLongToLong();
                    }
/* 954 */           atom.skip(2);
/* 956 */           int referenceCount = atom.readUnsignedShort();
/* 957 */           int[] sizes = new int[referenceCount];
/* 958 */           long[] offsets = new long[referenceCount];
/* 959 */           long[] durationsUs = new long[referenceCount];
/* 960 */           long[] timesUs = new long[referenceCount];
/* 962 */           long offset = firstOffset;
/* 963 */           long time = earliestPresentationTime;
                    for (int i = 0; i < referenceCount; i++) {
/* 965 */               int firstInt = atom.readInt();
/* 967 */               int type = Integer.MIN_VALUE & firstInt;
/* 968 */               if (type != 0) {
/* 969 */                   throw new IllegalStateException("Unhandled indirect reference");
                        }
/* 971 */               long referenceDuration = atom.readUnsignedInt();
/* 973 */               sizes[i] = Integer.MAX_VALUE & firstInt;
/* 974 */               offsets[i] = offset;
/* 978 */               timesUs[i] = (AudioUtils.MICROS_PER_SECOND * time) / timescale;
/* 979 */               long nextTimeUs = ((time + referenceDuration) * AudioUtils.MICROS_PER_SECOND) / timescale;
/* 980 */               durationsUs[i] = nextTimeUs - timesUs[i];
/* 981 */               time += referenceDuration;
/* 983 */               atom.skip(4);
/* 984 */               offset += (long) sizes[i];
                    }
/* 987 */           return new SegmentIndex(atom.length(), sizes, offsets, durationsUs, timesUs);
                }

/* 990 */       private int readEncryptionData(NonBlockingInputStream inputStream) {
/* 991 */           boolean success = this.fragmentRun.fillEncryptionData(inputStream);
/* 992 */           if (!success) {
/* 993 */               return 1;
                    }
/* 995 */           enterState(3);
/* 996 */           return 0;
                }

/* 1016 */      private int readOrSkipSample(NonBlockingInputStream inputStream, SampleHolder out) {
/* 1017 */          if (this.sampleIndex >= this.fragmentRun.length) {
/* 1019 */              enterState(0);
                        return 0;
                    }
/* 1022 */          int sampleSize = this.fragmentRun.sampleSizeTable[this.sampleIndex];
                    if (inputStream.getAvailableByteCount() < sampleSize) {
/* 1024 */              return 1;
                    }
/* 1026 */          if (this.sampleIndex < this.pendingSeekSyncSampleIndex) {
/* 1027 */              return skipSample(inputStream, sampleSize);
                    }
/* 1029 */          return readSample(inputStream, sampleSize, out);
                }

/* 1032 */      private int skipSample(NonBlockingInputStream inputStream, int sampleSize) {
/* 1033 */          if (this.fragmentRun.definesEncryptionData) {
/* 1034 */              ParsableByteArray sampleEncryptionData = this.fragmentRun.sampleEncryptionData;
/* 1035 */              TrackEncryptionBox encryptionBox = this.track.sampleDescriptionEncryptionBoxes[this.fragmentRun.sampleDescriptionIndex];
/* 1037 */              int vectorSize = encryptionBox.initializationVectorSize;
/* 1038 */              boolean subsampleEncryption = this.fragmentRun.sampleHasSubsampleEncryptionTable[this.sampleIndex];
/* 1039 */              sampleEncryptionData.skip(vectorSize);
/* 1040 */              int subsampleCount = subsampleEncryption ? sampleEncryptionData.readUnsignedShort() : 1;
/* 1041 */              if (subsampleEncryption) {
/* 1042 */                  sampleEncryptionData.skip(subsampleCount * 6);
                        }
                    }
/* 1046 */          inputStream.skip(sampleSize);
/* 1048 */          this.sampleIndex++;
/* 1049 */          enterState(3);
/* 1050 */          return 0;
                }

                @SuppressLint({"InlinedApi"})
/* 1054 */      private int readSample(NonBlockingInputStream inputStream, int sampleSize, SampleHolder out) {
/* 1055 */          if (out == null) {
/* 1056 */              return 32;
                    }
/* 1058 */          ByteBuffer outputData = out.data;
/* 1059 */          out.timeUs = ((long) this.fragmentRun.getSamplePresentationTime(this.sampleIndex)) * 1000;
/* 1060 */          out.flags = 0;
/* 1061 */          if (this.fragmentRun.sampleIsSyncFrameTable[this.sampleIndex]) {
/* 1062 */              out.flags |= 1;
/* 1063 */              this.lastSyncSampleIndex = this.sampleIndex;
                    }
/* 1065 */          if (out.allowDataBufferReplacement && (out.data == null || out.data.capacity() < sampleSize)) {
/* 1066 */              outputData = ByteBuffer.allocate(sampleSize);
/* 1067 */              out.data = outputData;
                    }
/* 1069 */          if (this.fragmentRun.definesEncryptionData) {
/* 1070 */              readSampleEncryptionData(this.fragmentRun.sampleEncryptionData, out);
                    }
/* 1073 */          if (outputData == null) {
/* 1074 */              inputStream.skip(sampleSize);
/* 1075 */              out.size = 0;
                    } else {
/* 1077 */              inputStream.read(outputData, sampleSize);
/* 1078 */              if (this.track.type == 1986618469) {
/* 1081 */                  int sampleOffset = outputData.position() - sampleSize;
/* 1082 */                  int position = sampleOffset;
/* 1083 */                  while (position < sampleOffset + sampleSize) {
/* 1084 */                      outputData.position(position);
/* 1085 */                      int length = readUnsignedIntToInt(outputData);
/* 1086 */                      outputData.position(position);
/* 1087 */                      outputData.put(NAL_START_CODE);
/* 1088 */                      position += length + 4;
                            }
/* 1090 */                  outputData.position(sampleOffset + sampleSize);
                        }
/* 1092 */              out.size = sampleSize;
                    }
/* 1095 */          this.sampleIndex++;
/* 1096 */          enterState(3);
/* 1097 */          return 4;
                }

                @SuppressLint({"InlinedApi"})
/* 1101 */      private void readSampleEncryptionData(ParsableByteArray sampleEncryptionData, SampleHolder out) {
/* 1102 */          TrackEncryptionBox encryptionBox = this.track.sampleDescriptionEncryptionBoxes[this.fragmentRun.sampleDescriptionIndex];
/* 1104 */          byte[] keyId = encryptionBox.keyId;
/* 1105 */          boolean isEncrypted = encryptionBox.isEncrypted;
/* 1106 */          int vectorSize = encryptionBox.initializationVectorSize;
/* 1107 */          boolean subsampleEncryption = this.fragmentRun.sampleHasSubsampleEncryptionTable[this.sampleIndex];
/* 1109 */          byte[] vector = out.cryptoInfo.f874iv;
/* 1110 */          if (vector == null || vector.length != 16) {
/* 1111 */              vector = new byte[16];
                    }
/* 1113 */          sampleEncryptionData.readBytes(vector, 0, vectorSize);
/* 1115 */          int subsampleCount = subsampleEncryption ? sampleEncryptionData.readUnsignedShort() : 1;
/* 1116 */          int[] clearDataSizes = out.cryptoInfo.numBytesOfClearData;
/* 1117 */          if (clearDataSizes == null || clearDataSizes.length < subsampleCount) {
/* 1118 */              clearDataSizes = new int[subsampleCount];
                    }
/* 1120 */          int[] encryptedDataSizes = out.cryptoInfo.numBytesOfEncryptedData;
/* 1121 */          if (encryptedDataSizes == null || encryptedDataSizes.length < subsampleCount) {
/* 1122 */              encryptedDataSizes = new int[subsampleCount];
                    }
/* 1124 */          if (subsampleEncryption) {
                        for (int i = 0; i < subsampleCount; i++) {
/* 1126 */                  clearDataSizes[i] = sampleEncryptionData.readUnsignedShort();
/* 1127 */                  encryptedDataSizes[i] = sampleEncryptionData.readUnsignedIntToInt();
                        }
                    } else {
/* 1130 */              clearDataSizes[0] = 0;
/* 1131 */              encryptedDataSizes[0] = this.fragmentRun.sampleSizeTable[this.sampleIndex];
                    }
/* 1133 */          out.cryptoInfo.set(subsampleCount, clearDataSizes, encryptedDataSizes, keyId, vector, isEncrypted ? 1 : 0);
/* 1135 */          if (isEncrypted) {
/* 1136 */              out.flags |= 2;
                    }
                }

/* 1143 */      private static int parseFullAtomVersion(int fullAtomInt) {
/* 1144 */          return (fullAtomInt >> 24) & 255;
                }

/* 1150 */      private static int parseFullAtomFlags(int fullAtomInt) {
/* 1151 */          return 16777215 & fullAtomInt;
                }

/* 1160 */      private static int readUnsignedIntToInt(ByteBuffer data) {
/* 1161 */          int result = data.get() & Constants.UNKNOWN;
                    for (int i = 1; i < 4; i++) {
/* 1164 */              result = (result << 8) | (data.get() & Constants.UNKNOWN);
                    }
/* 1166 */          if (result < 0) {
/* 1167 */              throw new IllegalArgumentException("Top bit not zero: " + result);
                    }
/* 1169 */          return result;
                }
            }
