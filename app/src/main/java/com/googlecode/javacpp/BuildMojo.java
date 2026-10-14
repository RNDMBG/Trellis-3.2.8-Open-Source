            package com.googlecode.javacpp;

            import java.io.File;
            import java.util.Arrays;
            import java.util.Map;
            import java.util.Properties;
            import org.apache.maven.plugin.AbstractMojo;
            import org.apache.maven.plugin.MojoExecutionException;
            
            /* JADX INFO: loaded from: C:\Users\asser\AppData\Local\Temp\trellis-jadx-cfb79692985f44a8ae07c92b4a09b391\dex\classes.dex */
/* 137 */   public class BuildMojo extends AbstractMojo {
                private String classPath = null;
                private String[] classPaths = null;
                private File outputDirectory = null;
                private String outputName = null;
                private boolean compile = true;
                private boolean header = false;
                private boolean copyLibs = false;
                private String jarPrefix = null;
                private String properties = null;
                private File propertyFile = null;
                private Properties propertyKeysAndValues = null;
                private String classOrPackageName = null;
                private String[] classOrPackageNames = null;
                private Map<String, String> environmentVariables = null;
                private String[] compilerOptions = null;
                private boolean skip = false;

                /* JADX INFO: Thrown type has an unknown type hierarchy: org.apache.maven.plugin.MojoExecutionException */
/* 138 */       public void execute() throws MojoExecutionException {
                    try {
/* 139 */               getLog().info("Executing JavaCPP Builder");
/* 140 */               if (getLog().isDebugEnabled()) {
/* 141 */                   getLog().debug("classPath: " + this.classPath);
/* 142 */                   getLog().debug("classPaths: " + Arrays.deepToString(this.classPaths));
/* 143 */                   getLog().debug("outputDirectory: " + this.outputDirectory);
/* 144 */                   getLog().debug("outputName: " + this.outputName);
/* 145 */                   getLog().debug("compile: " + this.compile);
/* 146 */                   getLog().debug("header: " + this.header);
/* 147 */                   getLog().debug("copyLibs: " + this.copyLibs);
/* 148 */                   getLog().debug("jarPrefix: " + this.jarPrefix);
/* 149 */                   getLog().debug("properties: " + this.properties);
/* 150 */                   getLog().debug("propertyFile: " + this.propertyFile);
/* 151 */                   getLog().debug("propertyKeysAndValues: " + this.propertyKeysAndValues);
/* 152 */                   getLog().debug("classOrPackageName: " + this.classOrPackageName);
/* 153 */                   getLog().debug("classOrPackageNames: " + Arrays.deepToString(this.classOrPackageNames));
/* 154 */                   getLog().debug("environmentVariables: " + this.environmentVariables);
/* 155 */                   getLog().debug("compilerOptions: " + Arrays.deepToString(this.compilerOptions));
/* 156 */                   getLog().debug("skip: " + this.skip);
                        }
/* 159 */               if (this.skip) {
/* 160 */                   getLog().info("Skipped execution of JavaCPP Builder");
/* 200 */                   return;
                        }
/* 164 */               if (this.classPaths != null && this.classPath != null) {
/* 165 */                   this.classPaths = (String[]) Arrays.copyOf(this.classPaths, this.classPaths.length + 1);
/* 166 */                   this.classPaths[this.classPaths.length - 1] = this.classPath;
                        } else if (this.classPath != null) {
/* 168 */                   this.classPaths = new String[]{this.classPath};
                        }
/* 171 */               if (this.classOrPackageNames != null && this.classOrPackageName != null) {
/* 172 */                   this.classOrPackageNames = (String[]) Arrays.copyOf(this.classOrPackageNames, this.classOrPackageNames.length + 1);
/* 173 */                   this.classOrPackageNames[this.classOrPackageNames.length - 1] = this.classOrPackageName;
                        } else if (this.classOrPackageName != null) {
/* 175 */                   this.classOrPackageNames = new String[]{this.classOrPackageName};
                        }
/* 178 */               File[] outputFiles = new Builder().classPaths(this.classPaths).outputDirectory(this.outputDirectory).outputName(this.outputName).compile(this.compile).header(this.header).copyLibs(this.copyLibs).jarPrefix(this.jarPrefix).properties(this.properties).propertyFile(this.propertyFile).properties(this.propertyKeysAndValues).classesOrPackages(this.classOrPackageNames).environmentVariables(this.environmentVariables).compilerOptions(this.compilerOptions).build();
/* 192 */               getLog().info("Successfully executed JavaCPP Builder");
/* 193 */               if (getLog().isDebugEnabled()) {
/* 194 */                   getLog().debug("outputFiles: " + Arrays.deepToString(outputFiles));
                        }
                    } catch (Exception e) {
/* 197 */               getLog().error("Failed to execute JavaCPP Builder: " + e.getMessage());
/* 198 */               throw new MojoExecutionException("Failed to execute JavaCPP Builder", e);
                    }
                }
            }
