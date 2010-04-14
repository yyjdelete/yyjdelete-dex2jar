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
import org.objectweb.asm.Type;

/**
 * @author Panxiaobo [pxb1988@gmail.com]
 * 
 */
public class EndFn extends Fn {
	int opcode;

	Value value;

	/**
	 * @param opcode
	 * @param value
	 */
	public EndFn(int opcode, Value value) {
		super();
		this.opcode = opcode;
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.v4.tree.Value#accept(org.objectweb.asm.Type, org.objectweb.asm.MethodVisitor)
	 */
	public void accept(Type suggest, MethodVisitor mv) {

		switch (opcode) {
		case OP_RETURN_VOID:
			mv.visitInsn(RETURN);
			break;
		case OP_THROW:
			value.accept(Type.getType(Object.class), mv);
			mv.visitInsn(ATHROW);
			break;
		case OP_RETURN_OBJECT:
			value.accept(Type.getType(Object.class), mv);
			mv.visitInsn(ARETURN);
			break;
		case OP_RETURN:
		case OP_RETURN_WIDE:
			// TODO
		}
	}

	public String toString() {

		switch (opcode) {
		case OP_RETURN_VOID:
			return "return";
		case OP_THROW:
			return "throw " + value;
		case OP_RETURN_OBJECT:
			return "return " + value;
		case OP_RETURN:
		case OP_RETURN_WIDE:
			// TODO
			return "return " + value;
		}
		return "Error";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.v4.tree.Fn#inValues()
	 */
	@Override
	public Value[] inValues() {
		if (opcode == OP_RETURN_VOID) {
			return asList();
		} else {
			return asList(value);
		}
	}
}
