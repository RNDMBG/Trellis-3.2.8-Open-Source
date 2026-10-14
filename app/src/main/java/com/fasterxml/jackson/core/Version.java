            package com.fasterxml.jackson.core;

            import java.io.Serializable;
            import org.apache.commons.io.FilenameUtils;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 14 */    public class Version implements Comparable<Version>, Serializable {
                private static final Version UNKNOWN_VERSION = new Version(0, 0, 0, null, null, null);
                private static final long serialVersionUID = 1;
                protected final String _artifactId;
                protected final String _groupId;
                protected final int _majorVersion;
                protected final int _minorVersion;
                protected final int _patchLevel;
                protected final String _snapshotInfo;


                @Deprecated
/* 47 */        public Version(int i, int i2, int i3, String str) {
/* 48 */            this(i, i2, i3, str, null, null);
                }

/* 52 */        public Version(int i, int i2, int i3, String str, String str2, String str3) {
/* 54 */            this._majorVersion = i;
/* 55 */            this._minorVersion = i2;
/* 56 */            this._patchLevel = i3;
/* 57 */            this._snapshotInfo = str;
/* 58 */            this._groupId = str2 == null ? "" : str2;
/* 59 */            this._artifactId = str3 == null ? "" : str3;
                }

/* 65 */        public static Version unknownVersion() {
/* 66 */            return UNKNOWN_VERSION;
                }

/* 67 */        public boolean isUknownVersion() {
                    return this == UNKNOWN_VERSION;
                }

/* 68 */        public boolean isSnapshot() {
                    return this._snapshotInfo != null && this._snapshotInfo.length() > 0;
                }

/* 70 */        public int getMajorVersion() {
/* 71 */            return this._majorVersion;
                }

/* 71 */        public int getMinorVersion() {
/* 72 */            return this._minorVersion;
                }

/* 72 */        public int getPatchLevel() {
/* 73 */            return this._patchLevel;
                }

/* 74 */        public String getGroupId() {
/* 75 */            return this._groupId;
                }

/* 75 */        public String getArtifactId() {
/* 76 */            return this._artifactId;
                }

/* 78 */        public String toFullString() {
/* 79 */            return this._groupId + '/' + this._artifactId + '/' + toString();
                }

/* 90 */        public String toString() {
/* 91 */            StringBuilder sb = new StringBuilder();
/* 92 */            sb.append(this._majorVersion).append(FilenameUtils.EXTENSION_SEPARATOR);
/* 93 */            sb.append(this._minorVersion).append(FilenameUtils.EXTENSION_SEPARATOR);
/* 94 */            sb.append(this._patchLevel);
/* 95 */            if (isSnapshot()) {
/* 96 */                sb.append('-').append(this._snapshotInfo);
                    }
/* 98 */            return sb.toString();
                }

/* 102 */       public int hashCode() {
/* 103 */           return this._artifactId.hashCode() ^ (((this._groupId.hashCode() + this._majorVersion) - this._minorVersion) + this._patchLevel);
                }

/* 108 */       public boolean equals(Object obj) {
/* 109 */           if (obj == this) {
                        return true;
                    }
/* 110 */           if (obj != null && obj.getClass() == getClass()) {
/* 112 */               Version version = (Version) obj;
                        return version._majorVersion == this._majorVersion && version._minorVersion == this._minorVersion && version._patchLevel == this._patchLevel && version._artifactId.equals(this._artifactId) && version._groupId.equals(this._groupId);
                    }
                    return false;
                }

                /* JADX INFO: renamed from: compareTo, reason: avoid collision after fix types in other method */
/* 123 */       public int compareTo(Version version) {
/* 124 */           if (version == this) {
                        return 0;
                    }
/* 126 */           int iCompareTo = this._groupId.compareTo(version._groupId);
/* 127 */           if (iCompareTo == 0) {
/* 128 */               int iCompareTo2 = this._artifactId.compareTo(version._artifactId);
/* 129 */               if (iCompareTo2 == 0) {
/* 130 */                   int i = this._majorVersion - version._majorVersion;
/* 131 */                   if (i == 0) {
/* 132 */                       int i2 = this._minorVersion - version._minorVersion;
/* 133 */                       if (i2 == 0) {
/* 134 */                           return this._patchLevel - version._patchLevel;
                                }
/* 139 */                       return i2;
                            }
/* 139 */                   return i;
                        }
/* 139 */               return iCompareTo2;
                    }
/* 139 */           return iCompareTo;
                }
            }
