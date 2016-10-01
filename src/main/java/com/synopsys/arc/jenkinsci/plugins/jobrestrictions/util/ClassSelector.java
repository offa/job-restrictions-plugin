/*
 * The MIT License
 *
 * Copyright 2016 Oleg Nenashev
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
package com.synopsys.arc.jenkinsci.plugins.jobrestrictions.util;

import hudson.Extension;
import hudson.Util;
import hudson.model.Describable;
import hudson.model.Descriptor;
import hudson.util.FormValidation;
import java.io.Serializable;
import javax.annotation.CheckForNull;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;

/**
 * Describable Item, which allows to select class.
 * Class existence is being verified.
 * @since TODO
 */
public class ClassSelector implements Describable<ClassSelector>, Serializable {
    
    /**ID of the user*/
    final @CheckForNull String selectedClass;

    @DataBoundConstructor
    public ClassSelector(@CheckForNull String selectedClass) {
        this.selectedClass = hudson.Util.fixEmptyAndTrim(selectedClass);
    }

    @CheckForNull
    public String getSelectedClass() {
        return selectedClass;
    }

    @Override
    public Descriptor<ClassSelector> getDescriptor() {
        return DESCRIPTOR;
    }

    @Override
    public boolean equals(Object obj) {       
        if (obj instanceof ClassSelector) {
            ClassSelector cmp = (ClassSelector)obj;
            return selectedClass != null ? selectedClass.equals(cmp.selectedClass) : cmp.selectedClass == null;
        }           
        return false;    
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (selectedClass != null ? selectedClass.hashCode() : 0);
        return hash;
    }
          
    @Extension
    public static final DescriptorImpl DESCRIPTOR = new DescriptorImpl();
    public static class DescriptorImpl extends Descriptor<ClassSelector> {
        
        @Override
        public String getDisplayName() {
            return "N/A";
        }
        
        public FormValidation doCheckSelectedClass(final @QueryParameter String selectedClass) {
            String _selectedClass = Util.fixEmptyAndTrim(selectedClass);
            if (_selectedClass == null) {
                return FormValidation.error("Field is empty");
            }

            try {
                Class.forName(selectedClass);
            } catch (Exception ex) {
                return FormValidation.warning("Class " + _selectedClass + " cannot be resolved: " + ex.toString());
            }

            return FormValidation.ok();
        }
    }
}
