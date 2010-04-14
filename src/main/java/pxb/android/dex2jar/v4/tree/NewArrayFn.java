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
package pxb.android.dex2jar.v4.tree;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * 
 *store: a[b]=c ,load: c=a[b]
 * 
 * @author Panxiaobo [pxb1988@gmail.com]
 * 
 */
public class NewArrayFn extends Fn {

	/**
	 * 
	 */
	public NewArrayFn() {
		super();
	}

	public Type arrayType;
	/**
	 * Array Object Reg
	 */
	public Value demValue;

	static Type[] MAP = new Type[] { Type.INT_TYPE, Type.LONG_TYPE, Type.getType(Object.class), Type.BOOLEAN_TYPE, Type.BYTE_TYPE, Type.CHAR_TYPE,
			Type.SHORT_TYPE,

	};

	/**
	 * @param arrayType
	 * @param demReg
	 * @param saveToReg
	 */
	public NewArrayFn(Type arrayType, Value demValue) {
		super();
		this.arrayType = arrayType;
		this.demValue = demValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.v4.tree.Value#accept(org.objectweb.asm.Type, org.objectweb.asm.MethodVisitor)
	 */
	public void accept(Type type, MethodVisitor mv) {
		demValue.accept(Type.INT_TYPE, mv);
		int shortType = arrayType.getElementType().getSort();
		switch (shortType) {
		case Type.BOOLEAN:
			mv.visitIntInsn(Opcodes.NEWARRAY, Opcodes.T_BOOLEAN);
			break;
		case Type.BYTE:
			mv.visitIntInsn(Opcodes.NEWARRAY, Opcodes.T_BYTE);
			break;
		case Type.CHAR:
			mv.visitIntInsn(Opcodes.NEWARRAY, Opcodes.T_CHAR);
			break;
		case Type.DOUBLE:
			mv.visitIntInsn(Opcodes.NEWARRAY, Opcodes.T_DOUBLE);
			break;
		case Type.FLOAT:
			mv.visitIntInsn(Opcodes.NEWARRAY, Opcodes.T_FLOAT);
			break;
		case Type.INT:
			mv.visitIntInsn(Opcodes.NEWARRAY, Opcodes.T_INT);
			break;
		case Type.OBJECT:
			mv.visitTypeInsn(Opcodes.ANEWARRAY, arrayType.getDescriptor());
			break;
		}
	}

	/* (non-Javadoc)
	 * @see pxb.android.dex2jar.v4.tree.Fn#inValues()
	 */
	@Override
	public Value[] inValues() {
		return asList(demValue);
	}
}
