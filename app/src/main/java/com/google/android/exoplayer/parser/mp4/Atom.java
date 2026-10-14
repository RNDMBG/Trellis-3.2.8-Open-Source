            package com.google.android.exoplayer.parser.mp4;

            import java.util.ArrayList;
            
            
    abstract class Atom {
                public static final int TYPE_avc1 = 1635148593;
                public static final int TYPE_avc3 = 1635148595;
                public static final int TYPE_avcC = 1635148611;
                public static final int TYPE_enca = 1701733217;
                public static final int TYPE_encv = 1701733238;
                public static final int TYPE_esds = 1702061171;
                public static final int TYPE_frma = 1718775137;
                public static final int TYPE_hdlr = 1751411826;
                public static final int TYPE_mdat = 1835295092;
                public static final int TYPE_mdhd = 1835296868;
                public static final int TYPE_mdia = 1835297121;
                public static final int TYPE_minf = 1835626086;
                public static final int TYPE_moof = 1836019558;
                public static final int TYPE_moov = 1836019574;
                public static final int TYPE_mp4a = 1836069985;
                public static final int TYPE_mvex = 1836475768;
                public static final int TYPE_pssh = 1886614376;
                public static final int TYPE_saiz = 1935763834;
                public static final int TYPE_schi = 1935894633;
                public static final int TYPE_schm = 1935894637;
                public static final int TYPE_senc = 1936027235;
                public static final int TYPE_sidx = 1936286840;
                public static final int TYPE_sinf = 1936289382;
                public static final int TYPE_stbl = 1937007212;
                public static final int TYPE_stsd = 1937011556;
                public static final int TYPE_tenc = 1952804451;
                public static final int TYPE_tfdt = 1952867444;
                public static final int TYPE_tfhd = 1952868452;
                public static final int TYPE_tkhd = 1953196132;
                public static final int TYPE_traf = 1953653094;
                public static final int TYPE_trak = 1953653099;
                public static final int TYPE_trex = 1953654136;
                public static final int TYPE_trun = 1953658222;
                public static final int TYPE_uuid = 1970628964;
                public final int type;

        Atom(int type) {
            this.type = type;
                }

        public static final class LeafAtom extends Atom {
                    public final ParsableByteArray data;

            public LeafAtom(int type, ParsableByteArray data) {
                super(type);
                this.data = data;
                    }
                }

        public static final class ContainerAtom extends Atom {
                    public final ArrayList<Atom> children;

            public ContainerAtom(int type) {
                super(type);
                this.children = new ArrayList<>();
                    }

            public void add(Atom atom) {
                this.children.add(atom);
                    }

            public LeafAtom getLeafAtomOfType(int type) {
                int childrenSize = this.children.size();
                        for (int i = 0; i < childrenSize; i++) {
                    Atom atom = this.children.get(i);
                    if (atom.type == type) {
                        return (LeafAtom) atom;
                            }
                        }
                return null;
                    }

            public ContainerAtom getContainerAtomOfType(int type) {
                int childrenSize = this.children.size();
                        for (int i = 0; i < childrenSize; i++) {
                   Atom atom = this.children.get(i);
                   if (atom.type == type) {
                       return (ContainerAtom) atom;
                            }
                        }
               return null;
                    }
                }
            }
