/*
 * Copyright (c) 2009-2010 Panxiaobo
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
package pxb.android.dex2jar.v4;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import pxb.android.dex2jar.Field;
import pxb.android.dex2jar.Method;
import pxb.android.dex2jar.v4.node.DexMethodNode;
import pxb.android.dex2jar.visitors.DexClassVisitor;
import pxb.android.dex2jar.visitors.DexFieldVisitor;
import pxb.android.dex2jar.visitors.DexMethodVisitor;

/**
 * @author Panxiaobo [pxb1988@gmail.com]
 * 
 */
public class V4ClassAdapter implements DexClassVisitor {

	/**
	 * @param cv
	 * @param accessFlags
	 * @param className
	 * @param superClass
	 * @param interfaceNames
	 */
	public V4ClassAdapter(ClassVisitor cv, int accessFlags, String className, String superClass, String[] interfaceNames) {
		this.cv = cv;
		cv.visit(Opcodes.V1_6, accessFlags, className, null, superClass, null);
	}

	ClassVisitor cv;

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.visitors.DexClassVisitor#visitEnd()
	 */
	public void visitEnd() {
		cv.visitEnd();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.visitors.DexClassVisitor#visitField(pxb.android.dex2jar.Field, java.lang.Object)
	 */
	public DexFieldVisitor visitField(Field field, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.visitors.DexClassVisitor#visitMethod(pxb.android.dex2jar.Method)
	 */
	public DexMethodVisitor visitMethod(Method method) {
		final MethodVisitor mv = cv.visitMethod(method.getAccessFlags(), method.getName(), method.getType().getDesc(), null, null);
		if (mv != null) {
			return new DexMethodNode(method) {
				@Override
				public void visitEnd() {
					super.visitEnd();
					this.accept(mv);
				}
			};
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.visitors.DexClassVisitor#visitSource(java.lang.String)
	 */
	public void visitSource(String file) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.visitors.AnnotationAble#visitAnnotation(java.lang.String, int)
	 */
	public AnnotationVisitor visitAnnotation(String name, int visitable) {
		return cv.visitAnnotation(name, visitable != 0);
	}

}
