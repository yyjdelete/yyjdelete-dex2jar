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
public class ArrayIns extends Base {

	/**
	 * 
	 */
	public ArrayIns() {
		super();
	}

	public static ArrayIns put(int type, int arrayReg, int indexReg, int saveToReg) {
		return new ArrayIns(true, type, arrayReg, indexReg, saveToReg);
	}

	public static ArrayIns get(int type, int arrayReg, int indexReg, int fromReg) {
		return new ArrayIns(true, type, arrayReg, indexReg, fromReg);
	}

	/**
	 * @param load
	 * @param type
	 * @param a
	 * @param b
	 * @param c
	 */
	public ArrayIns(boolean load, int type, int a, int b, int c) {
		super();
		this.load = load;
		this.type = type;
		this.arrayReg = a;
		this.indexReg = b;
		this.valueReg = c;
	}

	public boolean load = true;

	/**
	 * @see Type#getSort()
	 */
	public int type;
	/**
	 * Array Object Reg
	 */
	public int arrayReg;
	/**
	 * Position Object Reg
	 */
	public int indexReg;
	/**
	 * 
	 */
	public int valueReg;

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.v4.tree.Base#inRegs()
	 */
	@Override
	public int[] inRegs() {
		if (load) {
			return new int[] { arrayReg, indexReg };
		} else {
			return new int[] { arrayReg, indexReg, valueReg };
		}
	}

	static Type[] MAP = new Type[] { Type.INT_TYPE, Type.LONG_TYPE, Type.getType(Object.class), Type.BOOLEAN_TYPE, Type.BYTE_TYPE, Type.CHAR_TYPE,
			Type.SHORT_TYPE,

	};

public	void accept(MethodVisitor mv) {
		Type asmType = MAP[type];
		if (load) {
			mv.visitVarInsn(Opcodes.ALOAD, arrayReg);
			mv.visitVarInsn(Opcodes.ILOAD, indexReg);
			mv.visitInsn(asmType.getOpcode(Opcodes.IALOAD));
			mv.visitVarInsn(asmType.getOpcode(Opcodes.ISTORE), valueReg);
		} else {
			mv.visitVarInsn(Opcodes.ALOAD, arrayReg);
			mv.visitVarInsn(Opcodes.ILOAD, indexReg);
			mv.visitVarInsn(asmType.getOpcode(Opcodes.ILOAD), valueReg);
			mv.visitInsn(asmType.getOpcode(Opcodes.IASTORE));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.v4.tree.Base#outReg()
	 */
	@Override
	public int outReg() {
		if (load) {
			return valueReg;
		} else {
			return -1;
		}
	}

	public String toString() {
		if (load) {
			return "r" + valueReg + "=r" + arrayReg + "[r" + indexReg + "]";
		} else {
			return "r" + arrayReg + "[r" + indexReg + "]=" + "r" + valueReg;
		}
	}

}
