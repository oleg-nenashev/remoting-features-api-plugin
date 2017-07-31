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
        
        private FeatureDescriptor(@Nonnull String since) {
            this.since = new VersionNumber(since);
        }

        /**
         * Version of Remoting which includes the feature.
         * 
         * There is no way to retrieve Jenkins core version in this API,
         * because there is a plan to make Remoting pluggable at some point.
         * @return Version of Remoting, which includes the feature.
         */
        @Nonnull
        public VersionNumber getSince() {
            return since;
        }
        
        @Nonnull
        public abstract String getDisplayName();
        
        
        @Nonnull
        public abstract String getDocumentationURL();
    }
    
    public static final class WorkDirFeature extends FeatureDescriptor {

        private WorkDirFeature() {
            super("3.8");
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
