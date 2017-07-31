/*
 * The MIT License
 *
 * Copyright (c) 2017 Oleg Nenashev.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.jenkinsci.plugins.remoting.settings;

import hudson.util.VersionNumber;
import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import org.jenkinsci.plugins.remoting.compat.Messages;

/**
 * Defines major features available in Remoting.
 * 
 * @author Oleg Nenashev
 * @since 1.0
 */
public enum RemotingFeature {
    /**
     * Work directory support
     */
    WORK_DIRECTORIES(new WorkDirFeature());
    
    @Nonnull
    private final FeatureDescriptor descriptor;

    private RemotingFeature(@Nonnull FeatureDescriptor descriptor) {
        this.descriptor = descriptor;
    }

    @Nonnull
    public FeatureDescriptor getDescriptor() {
        return descriptor;
    }
    
    public static abstract class FeatureDescriptor {
        
        @Nonnull
        private final VersionNumber since;
        
        @CheckForNull
        private final VersionNumber coreSince;
        
        private FeatureDescriptor(@Nonnull String since, @CheckForNull String coreSince) {
            this.since = new VersionNumber(since);
            this.coreSince = coreSince != null ? new VersionNumber(coreSince) : null;
        }

        /**
         * Version of Remoting which includes the feature.
         * 
         * 
         * @return Version of Remoting, which includes the feature.
         */
        @Nonnull
        public VersionNumber getSince() {
            return since;
        }
        
        /**
         * Tries to get the Core version, which supports the feature.
         * 
         * @return Core version, which is guaranteed to support the feature.
         *         {@code null} if there is no known version.
         *         In such case the Remoting version in the core should be checked using the 
         *         {@link #getSince()} method
         */
        @CheckForNull
        public VersionNumber getCoreSince() {
            return since;
        }
        
        @Nonnull
        public abstract String getDisplayName();
        
        
        @Nonnull
        public abstract String getDocumentationURL();
        
        /**
         * Checks if the the feature is applicable to the specified Remoting version.
         * 
         * @param remotingVersion Remoting version
         * @return {@code true} if the feature is supported by the specified Remoting version
         */
        public boolean isApplicable(@Nonnull VersionNumber remotingVersion) {
            return remotingVersion.isNewerThan(since);
        }
    }
    
    public static final class WorkDirFeature extends FeatureDescriptor {

        private WorkDirFeature() {
            // The feature is available in 3.8, but there are known defects
            super("3.10", "2.68");
        }

        @Override
        public String getDisplayName() {
            return Messages.WorkDirFeature_displayName();
        }

        @Override
        public String getDocumentationURL() {
            return "TODO";
        }
    }
}
