/*
 * Copyright (c) 2009-2010 Panxiaobo
 * 
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pxb.android.dex2jar.visitors;

import org.objectweb.asm.AnnotationVisitor;

/**
 * @author Panxiaobo [pxb1988@126.com]
 * @version $Id$
 */
public class DexAnnotationAsmAdapter implements DexAnnotationVisitor {
	AnnotationVisitor av;

	/**
	 * @param av
	 */
	public DexAnnotationAsmAdapter(AnnotationVisitor av) {
		super();
		this.av = av;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pxb.android.dex2jar.visitors.DexAnnotationVisitor#visit(java.lang.String,
	 * java.lang.Object)
	 */
	public void visit(String name, Object value) {
		av.visit(name, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.visitors.DexAnnotationVisitor#visitAnnotation(java
	 * .lang.String, java.lang.String)
	 */
	public DexAnnotationVisitor visitAnnotation(String name, String desc) {
		AnnotationVisitor _av = av.visitAnnotation(name, desc);
		if (av == null)
			return null;
		return new DexAnnotationAsmAdapter(_av);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.visitors.DexAnnotationVisitor#visitArray(java.lang
	 * .String)
	 */
	public DexAnnotationVisitor visitArray(String name) {
		AnnotationVisitor _av = av.visitArray(name);
		if (av == null)
			return null;
		return new DexAnnotationAsmAdapter(_av);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.visitors.DexAnnotationVisitor#visitEnd()
	 */
	public void visitEnd() {
		av.visitEnd();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.visitors.DexAnnotationVisitor#visitEnum(java.lang
	 * .String, java.lang.String, java.lang.String)
	 */
	public void visitEnum(String name, String desc, String value) {
		av.visitEnum(name, desc, value);
	}

}