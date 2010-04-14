/**
 * 
 */
package pxb.android.dex2jar.v4.tree;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

/**
 * @author Panxiaobo
 * 
 */
public class JumpFn extends Fn {

	Label label;

	Value a;
	Value b;

	int opcode;

	/**
	 * @param opcode
	 * @param label
	 * @param a
	 * @param b
	 */
	public JumpFn(int opcode, Label label, Value a, Value b) {
		super();
		this.opcode = opcode;
		this.label = label;
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
			mv.visitJumpInsn(IF_ICMPNE, label);
			break;
		case OP_IF_EQ:
			mv.visitJumpInsn(IF_ICMPEQ, label);
			break;
		case OP_IF_GT:
			mv.visitJumpInsn(IF_ICMPGT, label);
			break;
		case OP_IF_GE:
			mv.visitJumpInsn(IF_ICMPGE, label);
			break;
		case OP_IF_LE:
			mv.visitJumpInsn(IF_ICMPLE, label);
			break;
		case OP_IF_LT:
			mv.visitJumpInsn(IF_ICMPLT, label);
			break;
		}
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

}
