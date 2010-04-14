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
public class OneValueFn extends Fn implements DexOpcodes, Opcodes {
	int opcode;

	Value value;

	/**
	 * @param from
	 * @param to
	 * @param value
	 */
	public OneValueFn(int opcode, Value value) {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.v4.tree.Value#accept(org.objectweb.asm.Type, org.objectweb.asm.MethodVisitor)
	 */
	public void accept(Type suggest, MethodVisitor mv) {

		Type from = null;
		int asmOpcode = 0;
		switch (opcode) {
		case OP_NEG_INT:
			from = Type.INT_TYPE;
			asmOpcode = INEG;
			break;
		case OP_NOT_INT:
			from = Type.INT_TYPE;
			throw new RuntimeException("not support");
			// asmOpcode = ;
			// break;
		case OP_NEG_LONG:
			from = Type.LONG_TYPE;
			asmOpcode = LNEG;
			break;
		case OP_NOT_LONG:
			from = Type.LONG_TYPE;
			throw new RuntimeException("not support");
			// asmOpcode = ;
			// break;
		case OP_NEG_FLOAT:
			from = Type.FLOAT_TYPE;
			asmOpcode = FNEG;
			break;
		case OP_NEG_DOUBLE:
			from = Type.DOUBLE_TYPE;
			asmOpcode = DNEG;
			break;
		case OP_INT_TO_LONG:
			from = Type.INT_TYPE;
			asmOpcode = I2L;
			break;
		case OP_INT_TO_FLOAT:
			from = Type.INT_TYPE;
			asmOpcode = I2F;
			break;
		case OP_INT_TO_DOUBLE:
			from = Type.INT_TYPE;
			asmOpcode = I2D;
			break;
		case OP_LONG_TO_INT:
			from = Type.LONG_TYPE;
			asmOpcode = L2I;
			break;
		case OP_LONG_TO_FLOAT:
			from = Type.LONG_TYPE;
			asmOpcode = L2F;
			break;
		case OP_LONG_TO_DOUBLE:
			from = Type.LONG_TYPE;
			asmOpcode = L2D;
			break;
		case OP_FLOAT_TO_INT:
			from = Type.FLOAT_TYPE;
			asmOpcode = F2I;
			break;
		case OP_FLOAT_TO_LONG:
			from = Type.FLOAT_TYPE;
			asmOpcode = F2L;
			break;
		case OP_FLOAT_TO_DOUBLE:
			from = Type.FLOAT_TYPE;
			asmOpcode = F2D;
			break;
		case OP_DOUBLE_TO_INT:
			from = Type.DOUBLE_TYPE;
			asmOpcode = D2I;
			break;
		case OP_DOUBLE_TO_LONG:
			from = Type.DOUBLE_TYPE;
			asmOpcode = D2L;
			break;
		case OP_DOUBLE_TO_FLOAT:
			from = Type.DOUBLE_TYPE;
			asmOpcode = D2F;
			break;
		case OP_INT_TO_BYTE:
			from = Type.INT_TYPE;
			asmOpcode = I2B;
			break;
		case OP_INT_TO_CHAR:
			from = Type.INT_TYPE;
			asmOpcode = I2C;
			break;
		case OP_INT_TO_SHORT:
			from = Type.INT_TYPE;
			asmOpcode = I2S;
			break;
		case OP_ARRAY_LENGTH:
			from = Type.getType("[java/lang/Object;");
			asmOpcode = ARRAYLENGTH;
			break;
		}
		value.accept(from, mv);
		mv.visitInsn(asmOpcode);
	}

	/* (non-Javadoc)
	 * @see pxb.android.dex2jar.v4.tree.Fn#inValues()
	 */
	@Override
	public Value[] inValues() {
		return asList(value);
	}
}
