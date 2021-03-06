/*
 * Copyright (c) 2009-2012 Panxiaobo
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.dex2jar.v3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.googlecode.dex2jar.Annotation;
import com.googlecode.dex2jar.Annotation.Item;
import com.googlecode.dex2jar.Method;
import com.googlecode.dex2jar.visitors.DexAnnotationVisitor;
import com.googlecode.dex2jar.visitors.DexClassVisitor;
import com.googlecode.dex2jar.visitors.DexFileVisitor;
import com.googlecode.dex2jar.visitors.EmptyVisitor;

/**
 * @author <a href="mailto:pxb1988@gmail.com">Panxiaobo</a>
 * @version $Rev$
 */
public class V3AccessFlagsAdapter implements DexFileVisitor {
    private Map<String, Integer> map = new HashMap<String, Integer>();
    private Map<String, String> innerNameMap = new HashMap<String, String>();

    private Map<String, Set<String>> extraMember = new HashMap<String, Set<String>>();

    /**
     * @return the innerNameMap
     */
    public Map<String, String> getInnerNameMap() {
        return innerNameMap;
    }

    public Map<String, Integer> getAccessFlagsMap() {
        return map;
    }

    public Map<String, Set<String>> getExtraMember() {
        return extraMember;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.googlecode.dex2jar.visitors.DexFileVisitor#visit(int, java.lang.String, java.lang.String,
     * java.lang.String[])
     */
    @Override
    public DexClassVisitor visit(int access_flags, final String className, String superClass, String[] interfaceNames) {

        return new EmptyVisitor() {
            protected List<Annotation> anns = new ArrayList<Annotation>();

            @Override
            public DexAnnotationVisitor visitAnnotation(String name, boolean visible) {
                Annotation ann = new Annotation(name, visible);
                anns.add(ann);
                return new V3AnnAdapter(ann);
            }

            @Override
            public void visitEnd() {
                String enclosingClass = null;
                for (Annotation ann : anns) {
                    if (ann.type.equals("Ldalvik/annotation/EnclosingClass;")) {
                        for (Item i : ann.items) {
                            if (i.name.equals("value")) {
                                enclosingClass = i.value.toString();
                            }
                        }
                    } else if (ann.type.equals("Ldalvik/annotation/EnclosingMethod;")) {
                        for (Item i : ann.items) {
                            if ("value".equals(i.name)) {
                                Method m = (Method) i.value;
                                enclosingClass = m.getOwner();
                            }
                        }
                    }
                }
                for (Annotation ann : anns) {
                    if ("Ldalvik/annotation/InnerClass;".equals(ann.type)) {
                        Integer acc = null;
                        String name = null;
                        for (Item it : ann.items) {
                            if ("accessFlags".equals(it.name)) {
                                acc = (Integer) it.value;
                            } else if ("name".equals(it.name)) {
                                name = (String) it.value;
                            }
                        }
                        map.put(className, acc);
                        innerNameMap.put(className, name);
                        if (name == null) {
                            Set<String> set = extraMember.get(enclosingClass);
                            if (set == null) {
                                set = new TreeSet<String>();
                                extraMember.put(enclosingClass, set);
                            }
                            set.add(className);
                        }
                    }
                }
            }
        };
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.googlecode.dex2jar.visitors.DexFileVisitor#visitEnd()
     */
    @Override
    public void visitEnd() {
    }

}
