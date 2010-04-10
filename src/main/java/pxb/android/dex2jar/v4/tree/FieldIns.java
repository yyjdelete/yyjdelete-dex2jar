/**
 * 
 */
package pxb.android.dex2jar.v4.tree;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import pxb.android.dex2jar.Field;

/**
 * 
 *store: a[b]=c ,load: c=a[b]
 * 
 * @author Panxiaobo
 * 
 */
public class FieldIns extends Base {

	/**
	 * 
	 */
	public FieldIns() {
		super();
	}

	public static final int GET_STATIC = 0;
	public static final int PUT_STATIC = 1;
	public static final int GET = 2;
	public static final int PUT = 3;

	public static FieldIns static_get(Field field, int saveToReg) {
		return new FieldIns(GET_STATIC, field, -1, saveToReg);
	}

	public static FieldIns static_put(Field field, int fromReg) {
		return new FieldIns(PUT_STATIC, field, -1, fromReg);
	}

	public static FieldIns get(Field field, int ownerReg, int saveToReg) {
		return new FieldIns(GET, field, ownerReg, saveToReg);
	}

	public static FieldIns put(Field field, int ownerReg, int fromReg) {
		return new FieldIns(PUT, field, ownerReg, fromReg);
	}

	/**
	 * @param get
	 * @param static1
	 * @param field
	 * @param ownerReg
	 * @param valueReg
	 */
	public FieldIns(int type, Field field, int ownerReg, int valueReg) {
		super();
		this.field = field;
		this.ownerReg = ownerReg;
		this.valueReg = valueReg;
	}

	public int type;
	public Field field;

	public int ownerReg;
	public int valueReg;

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.v4.tree.Base#inRegs()
	 */
	@Override
	public int[] inRegs() {
		switch (type) {
		case PUT:
			return new int[] { ownerReg, valueReg };
		case GET:
			return new int[] { ownerReg };
		case GET_STATIC:
			return new int[0];
		case PUT_STATIC:
		default:
			return new int[] { valueReg };
		}
	}

	static Type[] MAP = new Type[] { Type.INT_TYPE, Type.LONG_TYPE, Type.getType(Object.class), Type.BOOLEAN_TYPE, Type.BYTE_TYPE, Type.CHAR_TYPE,
			Type.SHORT_TYPE,

	};

	public void accept(MethodVisitor mv) {
		switch (type) {
		case GET_STATIC:
			mv.visitFieldInsn(Opcodes.GETFIELD, field.getOwner(), field.getName(), field.getType());
			mv.visitVarInsn(Type.getType(field.getType()).getOpcode(Opcodes.ISTORE), valueReg);
			break;
		case PUT_STATIC:
			mv.visitVarInsn(Type.getType(field.getType()).getOpcode(Opcodes.ILOAD), valueReg);
			mv.visitFieldInsn(Opcodes.PUTFIELD, field.getOwner(), field.getName(), field.getType());
			break;
		case GET:
			mv.visitVarInsn(Opcodes.ALOAD, ownerReg);
			mv.visitFieldInsn(Opcodes.GETFIELD, field.getOwner(), field.getName(), field.getType());
			mv.visitVarInsn(Type.getType(field.getType()).getOpcode(Opcodes.ISTORE), valueReg);
			break;
		case PUT:
			mv.visitVarInsn(Opcodes.ALOAD, ownerReg);
			mv.visitVarInsn(Type.getType(field.getType()).getOpcode(Opcodes.ILOAD), valueReg);
			mv.visitFieldInsn(Opcodes.PUTFIELD, field.getOwner(), field.getName(), field.getType());
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.v4.tree.Base#outReg()
	 */
	@Override
	public int outReg() {
		switch (type) {
		case GET:
		case GET_STATIC:
			return valueReg;
		case PUT:
		case PUT_STATIC:
		default:
			return -1;
		}
	}

	public String toString() {
		switch (type) {
		case GET_STATIC:
			return "v" + valueReg + "=" + Type.getType(field.getOwner()).getClassName() + "." + field.getName();
		case PUT_STATIC:
			return Type.getType(field.getOwner()).getClassName() + "." + field.getName() + "=v" + valueReg;
		case GET:
			return "v" + valueReg + "=" + "v" + ownerReg + "." + field.getName();
		case PUT:
		default:
			return "v" + ownerReg + "." + field.getName() + "=v" + valueReg;
		}
	}

}
