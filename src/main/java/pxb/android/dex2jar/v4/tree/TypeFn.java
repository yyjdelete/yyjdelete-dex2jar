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

import pxb.android.dex2jar.DexOpcodes;

/**
 * @author Panxiaobo [pxb1988@gmail.com]
 * 
 */
public class TypeFn extends Fn implements DexOpcodes, Opcodes {

	/**
	 * 
	 */
	Type type;

	Value srcValue;

	int opcode;

	/**
	 * @param opcode
	 * @param type
	 * @param srcValue
	 */
	public TypeFn(int opcode, Type type, Value srcValue) {
		super();
		this.opcode = opcode;
		this.type = type;
		this.srcValue = srcValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.v4.tree.Value#accept(org.objectweb.asm.Type, org.objectweb.asm.MethodVisitor)
	 */
	public void accept(Type suggest, MethodVisitor mv) {

		switch (opcode) {
		case OP_CHECK_CAST:
			srcValue.accept(type, mv);
			mv.visitTypeInsn(CHECKCAST, type.getDescriptor());
			break;
		case OP_INSTANCE_OF:
			srcValue.accept(null, mv);
			mv.visitTypeInsn(INSTANCEOF, type.getDescriptor());
		}
	}

	public String toString() {
		switch (opcode) {
		case OP_CHECK_CAST:
			return "(" + type.getClassName() + ")(" + srcValue + ")";
		case OP_INSTANCE_OF:
			return "(" + srcValue + " instanceof " + type.getClassName() + ")";
		}
		throw new RuntimeException();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.v4.tree.Fn#inValues()
	 */
	@Override
	public Value[] inValues() {
		return asList(srcValue);
	}

}
