/**
 * 
 */
package pxb.android.dex2jar.v4.tree;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

/**
 * @author Panxiaobo
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
