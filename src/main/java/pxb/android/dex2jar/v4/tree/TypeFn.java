/**
 * 
 */
package pxb.android.dex2jar.v4.tree;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import pxb.android.dex2jar.DexOpcodes;

/**
 * @author Panxiaobo
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

	/* (non-Javadoc)
	 * @see pxb.android.dex2jar.v4.tree.Fn#inValues()
	 */
	@Override
	public Value[] inValues() {
		return asList(srcValue);
	}

}
