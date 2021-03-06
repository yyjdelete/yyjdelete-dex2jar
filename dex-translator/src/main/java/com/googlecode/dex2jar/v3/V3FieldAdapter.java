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
import java.util.Iterator;
import java.util.List;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;

import com.googlecode.dex2jar.Annotation;
import com.googlecode.dex2jar.Annotation.Item;
import com.googlecode.dex2jar.Field;
import com.googlecode.dex2jar.visitors.DexAnnotationVisitor;
import com.googlecode.dex2jar.visitors.DexFieldVisitor;

/**
 * @author <a href="mailto:pxb1988@gmail.com">Panxiaobo</a>
 * @version $Rev$
 */
public class V3FieldAdapter implements DexFieldVisitor {
    protected List<Annotation> anns = new ArrayList<Annotation>();
    protected boolean build = false;
    protected ClassVisitor cv;
    protected Field field;
    protected FieldVisitor fv;
    protected Object value;
    protected int accessFlags;

    protected void build() {
        if (!build) {
            String signature = null;
            for (Iterator<Annotation> it = anns.iterator(); it.hasNext();) {
                Annotation ann = it.next();
                if ("Ldalvik/annotation/Signature;".equals(ann.type)) {
                    it.remove();
                    for (Item item : ann.items) {
                        if (item.name.equals("value")) {
                            Annotation values = (Annotation) item.value;
                            StringBuilder sb = new StringBuilder();
                            for (Item i : values.items) {
                                sb.append(i.value.toString());
                            }
                            signature = sb.toString();
                        }
                    }
                }
            }
            FieldVisitor fv = cv.visitField(accessFlags, field.getName(), field.getType(), signature, value);
            if (fv != null) {
                for (Annotation ann : anns) {
                    AnnotationVisitor av = fv.visitAnnotation(ann.type, ann.visible);
                    V3AnnAdapter.accept(ann.items, av);
                    av.visitEnd();
                }
            }
            this.fv = fv;
            build = true;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.googlecode.dex2jar.visitors.DexFieldVisitor#visitAnnotation(java.lang .String, boolean)
     */
    @Override
    public DexAnnotationVisitor visitAnnotation(String name, boolean visible) {
        Annotation ann = new Annotation(name, visible);
        anns.add(ann);
        return new V3AnnAdapter(ann);
    }

    /**
     * @param cv
     * @param field
     */
    public V3FieldAdapter(ClassVisitor cv, int accessFlags, Field field, Object value) {
        super();
        this.cv = cv;
        this.field = field;
        this.value = value;
        this.accessFlags = accessFlags;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.googlecode.dex2jar.visitors.DexFieldVisitor#visitEnd()
     */
    @Override
    public void visitEnd() {
        build();
        if (fv != null) {
            fv.visitEnd();
        }
    }

}
