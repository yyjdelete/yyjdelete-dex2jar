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

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

/**
 * @author Panxiaobo [pxb1988@gmail.com]
 * 
 */
public class JumpFn extends BranchFn {

	public Label success;
	Value a;
	Value b;

	int opcode;

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.v4.tree.Fn#getFnType()
	 */
	@Override
	public FnType getFnType() {
		return FnType.JUMP;
	}

	/**
	 * @param opcode
	 * @param success
	 * @param a
	 * @param b
	 */
	public JumpFn(int opcode, Label success, Value a, Value b) {
		super();
		this.opcode = opcode;
		this.success = success;

		this.a = a;
		this.b = b;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.v4.tree.Value#accept(org.objectweb.asm.Type, org.objectweb.asm.MethodVisitor)
	 */
	public void accept(Type suggest, MethodVisitor mv) {

		// TODO 优化IF_NEZ
		a.accept(Type.INT_TYPE, mv);
		b.accept(Type.INT_TYPE, mv);

		// TODO 区别 引用相等和数值相等
		switch (opcode) {
		case OP_IF_NE:
			mv.visitJumpInsn(IF_ICMPNE, success);
			break;
		case OP_IF_EQ:
			mv.visitJumpInsn(IF_ICMPEQ, success);
			break;
		case OP_IF_GT:
			mv.visitJumpInsn(IF_ICMPGT, success);
			break;
		case OP_IF_GE:
			mv.visitJumpInsn(IF_ICMPGE, success);
			break;
		case OP_IF_LE:
			mv.visitJumpInsn(IF_ICMPLE, success);
			break;
		case OP_IF_LT:
			mv.visitJumpInsn(IF_ICMPLT, success);
			break;
		}
	}

	public String toString() {
		switch (opcode) {
		case OP_IF_NE:
			return String.format("if (%s != %s) goto %s", a, b, success);
		case OP_IF_EQ:
			return String.format("if (%s == %s) goto %s", a, b, success);
		case OP_IF_GT:
			return String.format("if (%s > %s) goto %s", a, b, success);
		case OP_IF_GE:
			return String.format("if (%s >= %s) goto %s", a, b, success);
		case OP_IF_LE:
			return String.format("if (%s <= %s) goto %s", a, b, success);
		case OP_IF_LT:
			return String.format("if (%s < %s) goto %s", a, b, success);
		}
		return "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.v4.tree.Fn#inValues()
	 */
	@Override
	public Value[] inValues() {
		return asList(a, b);
	}

	/* (non-Javadoc)
	 * @see pxb.android.dex2jar.v4.tree.BranchFn#getBranchLabels()
	 */
	@Override
	public Label[] getBranchLabels() {
		return new Label[]{success};
	}



	// public void swap() {
	// switch (opcode) {
	// case OP_IF_NE:
	// opcode = OP_IF_EQ;
	// break;
	// case OP_IF_EQ:
	// opcode = OP_IF_NE;
	// break;
	// case OP_IF_GT:
	// opcode = OP_IF_LE;
	// break;
	// case OP_IF_GE:
	// opcode = OP_IF_LT;
	// break;
	// case OP_IF_LE:
	// opcode = OP_IF_GT;
	// break;
	// case OP_IF_LT:
	// opcode = OP_IF_GE;
	// break;
	// }
	// Label label = def;
	// def = success;
	// success = label;
	// }

}
