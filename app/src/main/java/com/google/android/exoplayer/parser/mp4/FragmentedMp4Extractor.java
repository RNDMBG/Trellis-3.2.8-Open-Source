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
            
            
    public final class FragmentedMp4Extractor implements Extractor {
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
            HashSet<Integer> parsedAtoms = new HashSet<>();
            parsedAtoms.add(Integer.valueOf(Atom.TYPE_avc1));
            parsedAtoms.add(Integer.valueOf(Atom.TYPE_avc3));
            parsedAtoms.add(Integer.valueOf(Atom.TYPE_esds));
            parsedAtoms.add(Integer.valueOf(Atom.TYPE_hdlr));
            parsedAtoms.add(Integer.valueOf(Atom.TYPE_mdat));
            parsedAtoms.add(Integer.valueOf(Atom.TYPE_mdhd));
            parsedAtoms.add(Integer.valueOf(Atom.TYPE_moof));
            parsedAtoms.add(Integer.valueOf(Atom.TYPE_moov));
            parsedAtoms.add(Integer.valueOf(Atom.TYPE_mp4a));
            parsedAtoms.add(Integer.valueOf(Atom.TYPE_sidx));
            parsedAtoms.add(Integer.valueOf(Atom.TYPE_stsd));
            parsedAtoms.add(Integer.valueOf(Atom.TYPE_tfdt));
            parsedAtoms.add(Integer.valueOf(Atom.TYPE_tfhd));
            parsedAtoms.add(Integer.valueOf(Atom.TYPE_tkhd));
            parsedAtoms.add(Integer.valueOf(Atom.TYPE_traf));
            parsedAtoms.add(Integer.valueOf(Atom.TYPE_trak));
            parsedAtoms.add(Integer.valueOf(Atom.TYPE_trex));
           parsedAtoms.add(Integer.valueOf(Atom.TYPE_trun));
           parsedAtoms.add(Integer.valueOf(Atom.TYPE_mvex));
           parsedAtoms.add(Integer.valueOf(Atom.TYPE_mdia));
           parsedAtoms.add(Integer.valueOf(Atom.TYPE_minf));
           parsedAtoms.add(Integer.valueOf(Atom.TYPE_stbl));
           parsedAtoms.add(Integer.valueOf(Atom.TYPE_pssh));
           parsedAtoms.add(Integer.valueOf(Atom.TYPE_saiz));
           parsedAtoms.add(Integer.valueOf(Atom.TYPE_uuid));
           parsedAtoms.add(Integer.valueOf(Atom.TYPE_senc));
           PARSED_ATOMS = Collections.unmodifiableSet(parsedAtoms);
           HashSet<Integer> atomContainerTypes = new HashSet<>();
           atomContainerTypes.add(Integer.valueOf(Atom.TYPE_moov));
           atomContainerTypes.add(Integer.valueOf(Atom.TYPE_trak));
           atomContainerTypes.add(Integer.valueOf(Atom.TYPE_mdia));
           atomContainerTypes.add(Integer.valueOf(Atom.TYPE_minf));
           atomContainerTypes.add(Integer.valueOf(Atom.TYPE_stbl));
           atomContainerTypes.add(Integer.valueOf(Atom.TYPE_avcC));
           atomContainerTypes.add(Integer.valueOf(Atom.TYPE_moof));
           atomContainerTypes.add(Integer.valueOf(Atom.TYPE_traf));
           atomContainerTypes.add(Integer.valueOf(Atom.TYPE_mvex));
           CONTAINER_TYPES = Collections.unmodifiableSet(atomContainerTypes);
                }

       public FragmentedMp4Extractor() {
           this(0);
                }

       public FragmentedMp4Extractor(int workaroundFlags) {
           this.workaroundFlags = workaroundFlags;
           this.parserState = 0;
           this.atomHeader = new ParsableByteArray(8);
           this.extendedTypeScratch = new byte[16];
           this.containerAtoms = new Stack<>();
           this.containerAtomEndPoints = new Stack<>();
           this.fragmentRun = new TrackFragment();
           this.psshData = new HashMap<>();
                }

       public void setTrack(Track track) {
           this.extendsDefaults = new DefaultSampleValues(0, 0, 0, 0);
           this.track = track;
                }

       public void putPsshInfo(UUID uuid, byte[] data) {
           this.psshData.put(uuid, data);
                }

                @Override 
       public Map<UUID, byte[]> getPsshInfo() {
           if (this.psshData.isEmpty()) {
                        return null;
                    }
                    return this.psshData;
                }

                @Override 
       public SegmentIndex getIndex() {
           return this.segmentIndex;
                }

                @Override 
       public boolean hasRelativeIndexOffsets() {
           return true;
                }

                @Override 
       public MediaFormat getFormat() {
           if (this.track == null) {
                        return null;
                    }
                    return this.track.mediaFormat;
                }

                @Override 
       public int read(NonBlockingInputStream inputStream, SampleHolder out) throws ParserException {
           int results = 0;
           while ((results & 39) == 0) {
                        try {
                            switch (this.parserState) {
                                case 0:
                           results |= readAtomHeader(inputStream);
                                    break;
                                case 1:
                           results |= readAtomPayload(inputStream);
                                    break;
                                case 2:
                           results |= readEncryptionData(inputStream);
                                    break;
                                default:
                           results |= readOrSkipSample(inputStream, out);
                                    break;
                            }
                        } catch (Exception e) {
                   throw new ParserException(e);
                        }
                    }
           return results;
                }

                @Override 
       public boolean seekTo(long seekTimeUs, boolean allowNoop) {
           this.pendingSeekTimeMs = (int) (seekTimeUs / 1000);
           if (allowNoop && this.fragmentRun != null && this.pendingSeekTimeMs >= this.fragmentRun.getSamplePresentationTime(0) && this.pendingSeekTimeMs <= this.fragmentRun.getSamplePresentationTime(this.fragmentRun.length - 1)) {
               int sampleIndexFound = 0;
               int syncSampleIndexFound = 0;
                        for (int i = 0; i < this.fragmentRun.length; i++) {
                   if (this.fragmentRun.getSamplePresentationTime(i) <= this.pendingSeekTimeMs) {
                       if (this.fragmentRun.sampleIsSyncFrameTable[i]) {
                           syncSampleIndexFound = i;
                                }
                       sampleIndexFound = i;
                            }
                        }
               if (syncSampleIndexFound == this.lastSyncSampleIndex && sampleIndexFound >= this.sampleIndex) {
                   this.pendingSeekTimeMs = 0;
                            return false;
                        }
                    }
           this.containerAtoms.clear();
           this.containerAtomEndPoints.clear();
           enterState(0);
           return true;
                }

       private void enterState(int state) {
                    switch (state) {
                        case 0:
                   this.atomBytesRead = 0;
                   if (this.containerAtomEndPoints.isEmpty()) {
                       this.rootAtomBytesRead = 0;
                            }
                            break;
                    }
           this.parserState = state;
                }

       private int readAtomHeader(NonBlockingInputStream inputStream) {
           int remainingBytes = 8 - this.atomBytesRead;
           int bytesRead = inputStream.read(this.atomHeader.data, this.atomBytesRead, remainingBytes);
           if (bytesRead == -1) {
                        return 2;
                    }
           this.rootAtomBytesRead += bytesRead;
           this.atomBytesRead += bytesRead;
           if (this.atomBytesRead != 8) {
                        return 1;
                    }
           this.atomHeader.setPosition(0);
           this.atomSize = this.atomHeader.readInt();
           this.atomType = this.atomHeader.readInt();
           if (this.atomType == 1835295092) {
               if (this.fragmentRun.sampleEncryptionDataNeedsFill) {
                   enterState(2);
                        } else {
                   enterState(3);
                        }
                        return 0;
                    }
           if (PARSED_ATOMS.contains(Integer.valueOf(this.atomType))) {
               if (CONTAINER_TYPES.contains(Integer.valueOf(this.atomType))) {
                   enterState(0);
                   this.containerAtoms.add(new Atom.ContainerAtom(this.atomType));
                   this.containerAtomEndPoints.add(Integer.valueOf((this.rootAtomBytesRead + this.atomSize) - 8));
                        } else {
                   this.atomData = new ParsableByteArray(this.atomSize);
                   System.arraycopy(this.atomHeader.data, 0, this.atomData.data, 0, 8);
                   enterState(1);
                        }
                    } else {
               this.atomData = null;
               enterState(1);
                    }
                    return 0;
                }

       private int readAtomPayload(NonBlockingInputStream inputStream) {
                    int bytesRead;
           if (this.atomData != null) {
               bytesRead = inputStream.read(this.atomData.data, this.atomBytesRead, this.atomSize - this.atomBytesRead);
                    } else {
               bytesRead = inputStream.skip(this.atomSize - this.atomBytesRead);
                    }
           if (bytesRead == -1) {
               return 2;
                    }
           this.rootAtomBytesRead += bytesRead;
           this.atomBytesRead += bytesRead;
           if (this.atomBytesRead != this.atomSize) {
               return 1;
                    }
           int results = this.atomData != null ? 0 | onLeafAtomRead(new Atom.LeafAtom(this.atomType, this.atomData)) : 0;
           while (!this.containerAtomEndPoints.isEmpty() && this.containerAtomEndPoints.peek().intValue() == this.rootAtomBytesRead) {
               this.containerAtomEndPoints.pop();
               results |= onContainerAtomRead(this.containerAtoms.pop());
                    }
           enterState(0);
           return results;
                }

       private int onLeafAtomRead(Atom.LeafAtom leaf) {
           if (!this.containerAtoms.isEmpty()) {
               this.containerAtoms.peek().add(leaf);
                    } else if (leaf.type == 1936286840) {
               this.segmentIndex = parseSidx(leaf.data);
               return 16;
                    }
           return 0;
                }

       private int onContainerAtomRead(Atom.ContainerAtom container) {
           if (container.type == 1836019574) {
               onMoovContainerAtomRead(container);
               return 8;
                    }
           if (container.type == 1836019558) {
               onMoofContainerAtomRead(container);
                    } else if (!this.containerAtoms.isEmpty()) {
               this.containerAtoms.peek().add(container);
                    }
           return 0;
                }

       private void onMoovContainerAtomRead(Atom.ContainerAtom moov) {
           List<Atom> moovChildren = moov.children;
           int moovChildrenSize = moovChildren.size();
                    for (int i = 0; i < moovChildrenSize; i++) {
               Atom child = moovChildren.get(i);
               if (child.type == 1886614376) {
                   ParsableByteArray psshAtom = ((Atom.LeafAtom) child).data;
                   psshAtom.setPosition(12);
                   UUID uuid = new UUID(psshAtom.readLong(), psshAtom.readLong());
                   int dataSize = psshAtom.readInt();
                   byte[] data = new byte[dataSize];
                   psshAtom.readBytes(data, 0, dataSize);
                   this.psshData.put(uuid, data);
                        }
                    }
           Atom.ContainerAtom mvex = moov.getContainerAtomOfType(Atom.TYPE_mvex);
           this.extendsDefaults = parseTrex(mvex.getLeafAtomOfType(Atom.TYPE_trex).data);
           this.track = parseTrak(moov.getContainerAtomOfType(Atom.TYPE_trak));
                }

       private void onMoofContainerAtomRead(Atom.ContainerAtom moof) {
           this.fragmentRun.reset();
           parseMoof(this.track, this.extendsDefaults, moof, this.fragmentRun, this.workaroundFlags, this.extendedTypeScratch);
           this.sampleIndex = 0;
           this.lastSyncSampleIndex = 0;
           this.pendingSeekSyncSampleIndex = 0;
           if (this.pendingSeekTimeMs != 0) {
                        for (int i = 0; i < this.fragmentRun.length; i++) {
                   if (this.fragmentRun.sampleIsSyncFrameTable[i] && this.fragmentRun.getSamplePresentationTime(i) <= this.pendingSeekTimeMs) {
                       this.pendingSeekSyncSampleIndex = i;
                            }
                        }
               this.pendingSeekTimeMs = 0;
                    }
                }

       private static DefaultSampleValues parseTrex(ParsableByteArray trex) {
           trex.setPosition(16);
           int defaultSampleDescriptionIndex = trex.readUnsignedIntToInt() - 1;
           int defaultSampleDuration = trex.readUnsignedIntToInt();
           int defaultSampleSize = trex.readUnsignedIntToInt();
           int defaultSampleFlags = trex.readInt();
           return new DefaultSampleValues(defaultSampleDescriptionIndex, defaultSampleDuration, defaultSampleSize, defaultSampleFlags);
                }

       private static Track parseTrak(Atom.ContainerAtom trak) {
           Atom.ContainerAtom mdia = trak.getContainerAtomOfType(Atom.TYPE_mdia);
           int trackType = parseHdlr(mdia.getLeafAtomOfType(Atom.TYPE_hdlr).data);
           Assertions.checkState(trackType == 1936684398 || trackType == 1986618469);
           Pair<Integer, Long> header = parseTkhd(trak.getLeafAtomOfType(Atom.TYPE_tkhd).data);
           int id = ((Integer) header.first).intValue();
           long timescale = parseMdhd(mdia.getLeafAtomOfType(Atom.TYPE_mdhd).data);
           Atom.ContainerAtom stbl = mdia.getContainerAtomOfType(Atom.TYPE_minf).getContainerAtomOfType(Atom.TYPE_stbl);
           Pair<MediaFormat, TrackEncryptionBox[]> sampleDescriptions = parseStsd(stbl.getLeafAtomOfType(Atom.TYPE_stsd).data);
           return new Track(id, trackType, timescale, (MediaFormat) sampleDescriptions.first, (TrackEncryptionBox[]) sampleDescriptions.second);
                }

       private static Pair<Integer, Long> parseTkhd(ParsableByteArray tkhd) {
           tkhd.setPosition(8);
           int fullAtom = tkhd.readInt();
           int version = parseFullAtomVersion(fullAtom);
           tkhd.skip(version != 0 ? 16 : 8);
           int trackId = tkhd.readInt();
           tkhd.skip(4);
           long duration = version == 0 ? tkhd.readUnsignedInt() : tkhd.readUnsignedLongToLong();
           return Pair.create(Integer.valueOf(trackId), Long.valueOf(duration));
                }

       private static int parseHdlr(ParsableByteArray hdlr) {
           hdlr.setPosition(16);
           return hdlr.readInt();
                }

       private static long parseMdhd(ParsableByteArray mdhd) {
           mdhd.setPosition(8);
           int fullAtom = mdhd.readInt();
           int version = parseFullAtomVersion(fullAtom);
           mdhd.skip(version != 0 ? 16 : 8);
           return mdhd.readUnsignedInt();
                }

       private static Pair<MediaFormat, TrackEncryptionBox[]> parseStsd(ParsableByteArray stsd) {
           stsd.setPosition(12);
           int numberOfEntries = stsd.readInt();
           MediaFormat mediaFormat = null;
           TrackEncryptionBox[] trackEncryptionBoxes = new TrackEncryptionBox[numberOfEntries];
                    for (int i = 0; i < numberOfEntries; i++) {
               int childStartPosition = stsd.getPosition();
               int childAtomSize = stsd.readInt();
               int childAtomType = stsd.readInt();
               if (childAtomType == 1635148593 || childAtomType == 1635148595 || childAtomType == 1701733238) {
                   Pair<MediaFormat, TrackEncryptionBox> avc = parseAvcFromParent(stsd, childStartPosition, childAtomSize);
                   mediaFormat = (MediaFormat) avc.first;
                   trackEncryptionBoxes[i] = (TrackEncryptionBox) avc.second;
                        } else if (childAtomType == 1836069985 || childAtomType == 1701733217) {
                   Pair<MediaFormat, TrackEncryptionBox> mp4a = parseMp4aFromParent(stsd, childStartPosition, childAtomSize);
                   mediaFormat = (MediaFormat) mp4a.first;
                   trackEncryptionBoxes[i] = (TrackEncryptionBox) mp4a.second;
                        }
               stsd.setPosition(childStartPosition + childAtomSize);
                    }
           return Pair.create(mediaFormat, trackEncryptionBoxes);
                }

       private static Pair<MediaFormat, TrackEncryptionBox> parseAvcFromParent(ParsableByteArray parent, int position, int size) {
           parent.setPosition(position + 8);
           parent.skip(24);
           int width = parent.readUnsignedShort();
           int height = parent.readUnsignedShort();
           parent.skip(50);
           List<byte[]> initializationData = null;
           TrackEncryptionBox trackEncryptionBox = null;
           int childPosition = parent.getPosition();
           while (childPosition - position < size) {
               parent.setPosition(childPosition);
               int childStartPosition = parent.getPosition();
               int childAtomSize = parent.readInt();
               int childAtomType = parent.readInt();
               if (childAtomType == 1635148611) {
                   initializationData = parseAvcCFromParent(parent, childStartPosition);
                        } else if (childAtomType == 1936289382) {
                   trackEncryptionBox = parseSinfFromParent(parent, childStartPosition, childAtomSize);
                        }
               childPosition += childAtomSize;
                    }
           MediaFormat format = MediaFormat.createVideoFormat("video/avc", -1, width, height, initializationData);
           return Pair.create(format, trackEncryptionBox);
                }

       private static Pair<MediaFormat, TrackEncryptionBox> parseMp4aFromParent(ParsableByteArray parent, int position, int size) {
           parent.setPosition(position + 8);
           parent.skip(16);
           int channelCount = parent.readUnsignedShort();
           int sampleSize = parent.readUnsignedShort();
           parent.skip(4);
           int sampleRate = parent.readUnsignedFixedPoint1616();
           byte[] initializationData = null;
           TrackEncryptionBox trackEncryptionBox = null;
           int childPosition = parent.getPosition();
           while (childPosition - position < size) {
               parent.setPosition(childPosition);
               int childStartPosition = parent.getPosition();
               int childAtomSize = parent.readInt();
               int childAtomType = parent.readInt();
               if (childAtomType == 1702061171) {
                   initializationData = parseEsdsFromParent(parent, childStartPosition);
                   Pair<Integer, Integer> audioSpecificConfig = com.google.android.exoplayer.util.CodecSpecificDataUtil.parseAudioSpecificConfig(initializationData);
                   sampleRate = ((Integer) audioSpecificConfig.first).intValue();
                   channelCount = ((Integer) audioSpecificConfig.second).intValue();
                        } else if (childAtomType == 1936289382) {
                   trackEncryptionBox = parseSinfFromParent(parent, childStartPosition, childAtomSize);
                        }
               childPosition += childAtomSize;
                    }
           MediaFormat format = MediaFormat.createAudioFormat(MimeTypes.AUDIO_AAC, sampleSize, channelCount, sampleRate, Collections.singletonList(initializationData));
           return Pair.create(format, trackEncryptionBox);
                }

       private static List<byte[]> parseAvcCFromParent(ParsableByteArray parent, int position) {
           parent.setPosition(position + 8 + 4);
           int nalUnitLength = (parent.readUnsignedByte() & 3) + 1;
           if (nalUnitLength != 4) {
               throw new IllegalStateException();
                    }
           List<byte[]> initializationData = new ArrayList<>();
           int numSequenceParameterSets = parent.readUnsignedByte() & 31;
                    for (int j = 0; j < numSequenceParameterSets; j++) {
               initializationData.add(parseChildNalUnit(parent));
                    }
           int numPictureParamterSets = parent.readUnsignedByte();
                    for (int j2 = 0; j2 < numPictureParamterSets; j2++) {
               initializationData.add(parseChildNalUnit(parent));
                    }
           return initializationData;
                }

       private static byte[] parseChildNalUnit(ParsableByteArray atom) {
           int length = atom.readUnsignedShort();
           int offset = atom.getPosition();
           atom.skip(length);
           return com.google.android.exoplayer.util.CodecSpecificDataUtil.buildNalUnit(atom.data, offset, length);
                }

       private static TrackEncryptionBox parseSinfFromParent(ParsableByteArray parent, int position, int size) {
           int childPosition = position + 8;
           TrackEncryptionBox trackEncryptionBox = null;
           while (childPosition - position < size) {
               parent.setPosition(childPosition);
               int childAtomSize = parent.readInt();
               int childAtomType = parent.readInt();
               if (childAtomType == 1718775137) {
                   parent.readInt();
                        } else if (childAtomType == 1935894637) {
                   parent.skip(4);
                   parent.readInt();
                   parent.readInt();
                        } else if (childAtomType == 1935894633) {
                   trackEncryptionBox = parseSchiFromParent(parent, childPosition, childAtomSize);
                        }
               childPosition += childAtomSize;
                    }
           return trackEncryptionBox;
                }

       private static TrackEncryptionBox parseSchiFromParent(ParsableByteArray parent, int position, int size) {
           int childPosition = position + 8;
           while (childPosition - position < size) {
               parent.setPosition(childPosition);
               int childAtomSize = parent.readInt();
               int childAtomType = parent.readInt();
               if (childAtomType == 1952804451) {
                   parent.skip(4);
                   int firstInt = parent.readInt();
                   boolean defaultIsEncrypted = (firstInt >> 8) == 1;
                   int defaultInitVectorSize = firstInt & 255;
                   byte[] defaultKeyId = new byte[16];
                   parent.readBytes(defaultKeyId, 0, defaultKeyId.length);
                   return new TrackEncryptionBox(defaultIsEncrypted, defaultInitVectorSize, defaultKeyId);
                        }
               childPosition += childAtomSize;
                    }
           return null;
                }

       private static byte[] parseEsdsFromParent(ParsableByteArray parent, int position) {
           parent.setPosition(position + 8 + 4);
           parent.skip(1);
           int varIntByte = parent.readUnsignedByte();
           while (varIntByte > 127) {
               varIntByte = parent.readUnsignedByte();
                    }
           parent.skip(2);
           int flags = parent.readUnsignedByte();
           if ((flags & 128) != 0) {
               parent.skip(2);
                    }
           if ((flags & 64) != 0) {
               parent.skip(parent.readUnsignedShort());
                    }
           if ((flags & 32) != 0) {
               parent.skip(2);
                    }
           parent.skip(1);
           int varIntByte2 = parent.readUnsignedByte();
           while (varIntByte2 > 127) {
               varIntByte2 = parent.readUnsignedByte();
                    }
           parent.skip(13);
           parent.skip(1);
           int varIntByte3 = parent.readUnsignedByte();
           int varInt = varIntByte3 & 127;
           while (varIntByte3 > 127) {
               varIntByte3 = parent.readUnsignedByte();
               varInt = (varInt << 8) | (varIntByte3 & 127);
                    }
           byte[] initializationData = new byte[varInt];
           parent.readBytes(initializationData, 0, varInt);
           return initializationData;
                }

       private static void parseMoof(Track track, DefaultSampleValues extendsDefaults, Atom.ContainerAtom moof, TrackFragment out, int workaroundFlags, byte[] extendedTypeScratch) {
           parseTraf(track, extendsDefaults, moof.getContainerAtomOfType(Atom.TYPE_traf), out, workaroundFlags, extendedTypeScratch);
                }

       private static void parseTraf(Track track, DefaultSampleValues extendsDefaults, Atom.ContainerAtom traf, TrackFragment out, int workaroundFlags, byte[] extendedTypeScratch) {
           Atom.LeafAtom tfdtAtom = traf.getLeafAtomOfType(Atom.TYPE_tfdt);
           long decodeTime = tfdtAtom == null ? 0L : parseTfdt(traf.getLeafAtomOfType(Atom.TYPE_tfdt).data);
           Atom.LeafAtom tfhd = traf.getLeafAtomOfType(Atom.TYPE_tfhd);
           DefaultSampleValues fragmentHeader = parseTfhd(extendsDefaults, tfhd.data);
           out.sampleDescriptionIndex = fragmentHeader.sampleDescriptionIndex;
           Atom.LeafAtom trun = traf.getLeafAtomOfType(Atom.TYPE_trun);
           parseTrun(track, fragmentHeader, decodeTime, workaroundFlags, trun.data, out);
           Atom.LeafAtom saiz = traf.getLeafAtomOfType(Atom.TYPE_saiz);
           if (saiz != null) {
               TrackEncryptionBox trackEncryptionBox = track.sampleDescriptionEncryptionBoxes[fragmentHeader.sampleDescriptionIndex];
               parseSaiz(trackEncryptionBox, saiz.data, out);
                    }
           Atom.LeafAtom senc = traf.getLeafAtomOfType(Atom.TYPE_senc);
           if (senc != null) {
               parseSenc(senc.data, out);
                    }
           Atom.LeafAtom uuid = traf.getLeafAtomOfType(Atom.TYPE_uuid);
           if (uuid != null) {
               parseUuid(uuid.data, out, extendedTypeScratch);
                    }
                }

       private static void parseSaiz(TrackEncryptionBox encryptionBox, ParsableByteArray saiz, TrackFragment out) {
           int vectorSize = encryptionBox.initializationVectorSize;
           saiz.setPosition(8);
           int fullAtom = saiz.readInt();
           int flags = parseFullAtomFlags(fullAtom);
           if ((flags & 1) == 1) {
               saiz.skip(8);
                    }
           int defaultSampleInfoSize = saiz.readUnsignedByte();
           int sampleCount = saiz.readUnsignedIntToInt();
           if (sampleCount != out.length) {
               throw new IllegalStateException("Length mismatch: " + sampleCount + ", " + out.length);
                    }
           int totalSize = 0;
           if (defaultSampleInfoSize == 0) {
               boolean[] sampleHasSubsampleEncryptionTable = out.sampleHasSubsampleEncryptionTable;
                        for (int i = 0; i < sampleCount; i++) {
                   int sampleInfoSize = saiz.readUnsignedByte();
                   totalSize += sampleInfoSize;
                   sampleHasSubsampleEncryptionTable[i] = sampleInfoSize > vectorSize;
                        }
                    } else {
               boolean subsampleEncryption = defaultSampleInfoSize > vectorSize;
               totalSize = 0 + (defaultSampleInfoSize * sampleCount);
               Arrays.fill(out.sampleHasSubsampleEncryptionTable, 0, sampleCount, subsampleEncryption);
                    }
           out.initEncryptionData(totalSize);
                }

       private static DefaultSampleValues parseTfhd(DefaultSampleValues extendsDefaults, ParsableByteArray tfhd) {
           tfhd.setPosition(8);
           int fullAtom = tfhd.readInt();
           int flags = parseFullAtomFlags(fullAtom);
           tfhd.skip(4);
           if ((flags & 1) != 0) {
               tfhd.skip(8);
                    }
           int defaultSampleDescriptionIndex = (flags & 2) != 0 ? tfhd.readUnsignedIntToInt() - 1 : extendsDefaults.sampleDescriptionIndex;
           int defaultSampleDuration = (flags & 8) != 0 ? tfhd.readUnsignedIntToInt() : extendsDefaults.duration;
           int defaultSampleSize = (flags & 16) != 0 ? tfhd.readUnsignedIntToInt() : extendsDefaults.size;
           int defaultSampleFlags = (flags & 32) != 0 ? tfhd.readUnsignedIntToInt() : extendsDefaults.flags;
           return new DefaultSampleValues(defaultSampleDescriptionIndex, defaultSampleDuration, defaultSampleSize, defaultSampleFlags);
                }

       private static long parseTfdt(ParsableByteArray tfdt) {
           tfdt.setPosition(8);
           int fullAtom = tfdt.readInt();
           int version = parseFullAtomVersion(fullAtom);
                    return version == 1 ? tfdt.readUnsignedLongToLong() : tfdt.readUnsignedInt();
                }

       private static void parseTrun(Track track, DefaultSampleValues defaultSampleValues, long decodeTime, int workaroundFlags, ParsableByteArray trun, TrackFragment out) {
                    int sampleFlags;
           trun.setPosition(8);
           int fullAtom = trun.readInt();
           int flags = parseFullAtomFlags(fullAtom);
           int sampleCount = trun.readUnsignedIntToInt();
           if ((flags & 1) != 0) {
               trun.skip(4);
                    }
           boolean firstSampleFlagsPresent = (flags & 4) != 0;
           int firstSampleFlags = defaultSampleValues.flags;
           if (firstSampleFlagsPresent) {
               firstSampleFlags = trun.readUnsignedIntToInt();
                    }
           boolean sampleDurationsPresent = (flags & 256) != 0;
           boolean sampleSizesPresent = (flags & 512) != 0;
           boolean sampleFlagsPresent = (flags & 1024) != 0;
           boolean sampleCompositionTimeOffsetsPresent = (flags & 2048) != 0;
           out.initTables(sampleCount);
           int[] sampleSizeTable = out.sampleSizeTable;
           int[] sampleDecodingTimeTable = out.sampleDecodingTimeTable;
           int[] sampleCompositionTimeOffsetTable = out.sampleCompositionTimeOffsetTable;
           boolean[] sampleIsSyncFrameTable = out.sampleIsSyncFrameTable;
           long timescale = track.timescale;
           long cumulativeTime = decodeTime;
           boolean workaroundEveryVideoFrameIsSyncFrame = track.type == 1986618469 && (workaroundFlags & 1) == 1;
           int i = 0;
                    while (i < sampleCount) {
               int sampleDuration = sampleDurationsPresent ? trun.readUnsignedIntToInt() : defaultSampleValues.duration;
               int sampleSize = sampleSizesPresent ? trun.readUnsignedIntToInt() : defaultSampleValues.size;
               if (i == 0 && firstSampleFlagsPresent) {
                            sampleFlags = firstSampleFlags;
                        } else {
                   sampleFlags = sampleFlagsPresent ? trun.readInt() : defaultSampleValues.flags;
                        }
               if (sampleCompositionTimeOffsetsPresent) {
                   int sampleOffset = trun.readInt();
                   sampleCompositionTimeOffsetTable[i] = (int) (((long) (sampleOffset * 1000)) / timescale);
                        } else {
                   sampleCompositionTimeOffsetTable[i] = 0;
                        }
               sampleDecodingTimeTable[i] = (int) ((1000 * cumulativeTime) / timescale);
               sampleSizeTable[i] = sampleSize;
               sampleIsSyncFrameTable[i] = ((sampleFlags >> 16) & 1) == 0 && (!workaroundEveryVideoFrameIsSyncFrame || i == 0);
               cumulativeTime += (long) sampleDuration;
               i++;
                    }
                }

       private static void parseUuid(ParsableByteArray uuid, TrackFragment out, byte[] extendedTypeScratch) {
           uuid.setPosition(8);
           uuid.readBytes(extendedTypeScratch, 0, 16);
           if (Arrays.equals(extendedTypeScratch, PIFF_SAMPLE_ENCRYPTION_BOX_EXTENDED_TYPE)) {
               parseSenc(uuid, 16, out);
                    }
                }

       private static void parseSenc(ParsableByteArray senc, TrackFragment out) {
           parseSenc(senc, 0, out);
                }

       private static void parseSenc(ParsableByteArray senc, int offset, TrackFragment out) {
           senc.setPosition(offset + 8);
           int fullAtom = senc.readInt();
           int flags = parseFullAtomFlags(fullAtom);
           if ((flags & 1) != 0) {
               throw new IllegalStateException("Overriding TrackEncryptionBox parameters is unsupported");
                    }
           boolean subsampleEncryption = (flags & 2) != 0;
           int sampleCount = senc.readUnsignedIntToInt();
           if (sampleCount != out.length) {
               throw new IllegalStateException("Length mismatch: " + sampleCount + ", " + out.length);
                    }
           Arrays.fill(out.sampleHasSubsampleEncryptionTable, 0, sampleCount, subsampleEncryption);
           out.initEncryptionData(senc.length() - senc.getPosition());
           out.fillEncryptionData(senc);
                }

       private static SegmentIndex parseSidx(ParsableByteArray atom) {
                    long earliestPresentationTime;
                    long firstOffset;
           atom.setPosition(8);
           int fullAtom = atom.readInt();
           int version = parseFullAtomVersion(fullAtom);
           atom.skip(4);
           long timescale = atom.readUnsignedInt();
           if (version == 0) {
               earliestPresentationTime = atom.readUnsignedInt();
               firstOffset = atom.readUnsignedInt();
                    } else {
               earliestPresentationTime = atom.readUnsignedLongToLong();
               firstOffset = atom.readUnsignedLongToLong();
                    }
           atom.skip(2);
           int referenceCount = atom.readUnsignedShort();
           int[] sizes = new int[referenceCount];
           long[] offsets = new long[referenceCount];
           long[] durationsUs = new long[referenceCount];
           long[] timesUs = new long[referenceCount];
           long offset = firstOffset;
           long time = earliestPresentationTime;
                    for (int i = 0; i < referenceCount; i++) {
               int firstInt = atom.readInt();
               int type = Integer.MIN_VALUE & firstInt;
               if (type != 0) {
                   throw new IllegalStateException("Unhandled indirect reference");
                        }
               long referenceDuration = atom.readUnsignedInt();
               sizes[i] = Integer.MAX_VALUE & firstInt;
               offsets[i] = offset;
               timesUs[i] = (AudioUtils.MICROS_PER_SECOND * time) / timescale;
               long nextTimeUs = ((time + referenceDuration) * AudioUtils.MICROS_PER_SECOND) / timescale;
               durationsUs[i] = nextTimeUs - timesUs[i];
               time += referenceDuration;
               atom.skip(4);
               offset += (long) sizes[i];
                    }
           return new SegmentIndex(atom.length(), sizes, offsets, durationsUs, timesUs);
                }

       private int readEncryptionData(NonBlockingInputStream inputStream) {
           boolean success = this.fragmentRun.fillEncryptionData(inputStream);
           if (!success) {
               return 1;
                    }
           enterState(3);
           return 0;
                }

      private int readOrSkipSample(NonBlockingInputStream inputStream, SampleHolder out) {
          if (this.sampleIndex >= this.fragmentRun.length) {
              enterState(0);
                        return 0;
                    }
          int sampleSize = this.fragmentRun.sampleSizeTable[this.sampleIndex];
                    if (inputStream.getAvailableByteCount() < sampleSize) {
              return 1;
                    }
          if (this.sampleIndex < this.pendingSeekSyncSampleIndex) {
              return skipSample(inputStream, sampleSize);
                    }
          return readSample(inputStream, sampleSize, out);
                }

      private int skipSample(NonBlockingInputStream inputStream, int sampleSize) {
          if (this.fragmentRun.definesEncryptionData) {
              ParsableByteArray sampleEncryptionData = this.fragmentRun.sampleEncryptionData;
              TrackEncryptionBox encryptionBox = this.track.sampleDescriptionEncryptionBoxes[this.fragmentRun.sampleDescriptionIndex];
              int vectorSize = encryptionBox.initializationVectorSize;
              boolean subsampleEncryption = this.fragmentRun.sampleHasSubsampleEncryptionTable[this.sampleIndex];
              sampleEncryptionData.skip(vectorSize);
              int subsampleCount = subsampleEncryption ? sampleEncryptionData.readUnsignedShort() : 1;
              if (subsampleEncryption) {
                  sampleEncryptionData.skip(subsampleCount * 6);
                        }
                    }
          inputStream.skip(sampleSize);
          this.sampleIndex++;
          enterState(3);
          return 0;
                }

                @SuppressLint({"InlinedApi"})
      private int readSample(NonBlockingInputStream inputStream, int sampleSize, SampleHolder out) {
          if (out == null) {
              return 32;
                    }
          ByteBuffer outputData = out.data;
          out.timeUs = ((long) this.fragmentRun.getSamplePresentationTime(this.sampleIndex)) * 1000;
          out.flags = 0;
          if (this.fragmentRun.sampleIsSyncFrameTable[this.sampleIndex]) {
              out.flags |= 1;
              this.lastSyncSampleIndex = this.sampleIndex;
                    }
          if (out.allowDataBufferReplacement && (out.data == null || out.data.capacity() < sampleSize)) {
              outputData = ByteBuffer.allocate(sampleSize);
              out.data = outputData;
                    }
          if (this.fragmentRun.definesEncryptionData) {
              readSampleEncryptionData(this.fragmentRun.sampleEncryptionData, out);
                    }
          if (outputData == null) {
              inputStream.skip(sampleSize);
              out.size = 0;
                    } else {
              inputStream.read(outputData, sampleSize);
              if (this.track.type == 1986618469) {
                  int sampleOffset = outputData.position() - sampleSize;
                  int position = sampleOffset;
                  while (position < sampleOffset + sampleSize) {
                      outputData.position(position);
                      int length = readUnsignedIntToInt(outputData);
                      outputData.position(position);
                      outputData.put(NAL_START_CODE);
                      position += length + 4;
                            }
                  outputData.position(sampleOffset + sampleSize);
                        }
              out.size = sampleSize;
                    }
          this.sampleIndex++;
          enterState(3);
          return 4;
                }

                @SuppressLint({"InlinedApi"})
      private void readSampleEncryptionData(ParsableByteArray sampleEncryptionData, SampleHolder out) {
          TrackEncryptionBox encryptionBox = this.track.sampleDescriptionEncryptionBoxes[this.fragmentRun.sampleDescriptionIndex];
          byte[] keyId = encryptionBox.keyId;
          boolean isEncrypted = encryptionBox.isEncrypted;
          int vectorSize = encryptionBox.initializationVectorSize;
          boolean subsampleEncryption = this.fragmentRun.sampleHasSubsampleEncryptionTable[this.sampleIndex];
          byte[] vector = out.cryptoInfo.f874iv;
          if (vector == null || vector.length != 16) {
              vector = new byte[16];
                    }
          sampleEncryptionData.readBytes(vector, 0, vectorSize);
          int subsampleCount = subsampleEncryption ? sampleEncryptionData.readUnsignedShort() : 1;
          int[] clearDataSizes = out.cryptoInfo.numBytesOfClearData;
          if (clearDataSizes == null || clearDataSizes.length < subsampleCount) {
              clearDataSizes = new int[subsampleCount];
                    }
          int[] encryptedDataSizes = out.cryptoInfo.numBytesOfEncryptedData;
          if (encryptedDataSizes == null || encryptedDataSizes.length < subsampleCount) {
              encryptedDataSizes = new int[subsampleCount];
                    }
          if (subsampleEncryption) {
                        for (int i = 0; i < subsampleCount; i++) {
                  clearDataSizes[i] = sampleEncryptionData.readUnsignedShort();
                  encryptedDataSizes[i] = sampleEncryptionData.readUnsignedIntToInt();
                        }
                    } else {
              clearDataSizes[0] = 0;
              encryptedDataSizes[0] = this.fragmentRun.sampleSizeTable[this.sampleIndex];
                    }
          out.cryptoInfo.set(subsampleCount, clearDataSizes, encryptedDataSizes, keyId, vector, isEncrypted ? 1 : 0);
          if (isEncrypted) {
              out.flags |= 2;
                    }
                }

      private static int parseFullAtomVersion(int fullAtomInt) {
          return (fullAtomInt >> 24) & 255;
                }

      private static int parseFullAtomFlags(int fullAtomInt) {
          return 16777215 & fullAtomInt;
                }

      private static int readUnsignedIntToInt(ByteBuffer data) {
          int result = data.get() & Constants.UNKNOWN;
                    for (int i = 1; i < 4; i++) {
              result = (result << 8) | (data.get() & Constants.UNKNOWN);
                    }
          if (result < 0) {
              throw new IllegalArgumentException("Top bit not zero: " + result);
                    }
          return result;
                }
            }
