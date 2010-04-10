/**
 * 
 */
package pxb.android.dex2jar.v4.tree;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import pxb.android.dex2jar.DexOpcodeUtil;

/**
 * 
 *store: a[b]=c ,load: c=a[b]
 * 
 * @author Panxiaobo
 * 
 */
public class NewArrayIns extends Base {

	/**
	 * 
	 */
	public NewArrayIns() {
		super();
	}

	public Type type;
	/**
	 * Array Object Reg
	 */
	public int demReg;
	/**
	 * 
	 */
	public int saveToReg;

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.v4.tree.Base#inRegs()
	 */
	@Override
	public int[] inRegs() {
		return new int[] { demReg };
	}

	static Type[] MAP = new Type[] { Type.INT_TYPE, Type.LONG_TYPE, Type.getType(Object.class), Type.BOOLEAN_TYPE, Type.BYTE_TYPE, Type.CHAR_TYPE,
			Type.SHORT_TYPE,

	};

	/**
	 * @param type
	 * @param demReg
	 * @param saveToReg
	 */
	public NewArrayIns(Type type, int demReg, int saveToReg) {
		super();
		this.type = type;
		this.demReg = demReg;
		this.saveToReg = saveToReg;
	}

	public void accept(MethodVisitor mv) {
		mv.visitVarInsn(Opcodes.ILOAD, demReg);
		int shortType = type.getElementType().getSort();
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
			mv.visitTypeInsn(Opcodes.ANEWARRAY, type.getDescriptor());
			break;
		}
		mv.visitVarInsn(Opcodes.ASTORE, saveToReg);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.v4.tree.Base#outReg()
	 */
	@Override
	public int outReg() {
		return saveToReg;
	}

	public String toString() {
		return "v" + saveToReg + "=new " + type.getElementType().getClassName() + "[v" + demReg + "]";
	}

}
