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
package pxb.android.dex2jar.v4.node;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.tree.MethodNode;

import pxb.android.dex2jar.Method;
import pxb.android.dex2jar.visitors.AnnotationAble;
import pxb.android.dex2jar.visitors.DexCodeVisitor;
import pxb.android.dex2jar.visitors.DexMethodVisitor;

/**
 * @author Panxiaobo [pxb1988@gmail.com]
 * 
 */
public class DexMethodNode implements DexMethodVisitor {

	public Method method;
	public DexCodeNode codeNode;
	MethodNode methodNode = new MethodNode();

	public DexMethodNode(Method method) {
		this.method = method;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.visitors.DexMethodVisitor#visitCode()
	 */
	public DexCodeVisitor visitCode() {
		codeNode = new DexCodeNode();
		return codeNode;
	}

	public void accept(MethodVisitor mv) {
		methodNode.accept(new MethodAdapter(mv) {
			@Override
			public void visitEnd() {
				// ignored
			}
		});
		if (codeNode != null) {
			codeNode.accept(mv);
		}
		mv.visitEnd();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.visitors.DexMethodVisitor#visitEnd()
	 */
	public void visitEnd() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.visitors.DexMethodVisitor#visitParamesterAnnotation(int)
	 */
	public AnnotationAble visitParamesterAnnotation(final int index) {
		return new AnnotationAble() {
			public AnnotationVisitor visitAnnotation(String name, int visitable) {
				return methodNode.visitParameterAnnotation(index, name, visitable == 1);
			}
		};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.visitors.AnnotationAble#visitAnnotation(java.lang.String, int)
	 */
	public AnnotationVisitor visitAnnotation(String name, int visitable) {
		return methodNode.visitAnnotation(name, visitable != 0);
	}
}
