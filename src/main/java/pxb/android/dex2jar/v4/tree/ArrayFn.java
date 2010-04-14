/**
 * 
 */
package pxb.android.dex2jar.v4.tree;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * 
 * @author Panxiaobo
 * 
 */
public class ArrayFn extends Fn {

	/**
	 * 
	 */
	public ArrayFn() {
		super();
	}

	public static ArrayFn aget(int type, Value arrayReg, Value indexReg) {
		return new ArrayFn(false, type, arrayReg, indexReg, null);
	}

	public static ArrayFn aput(int type, Value arrayReg, Value indexReg, Value fromReg) {
		return new ArrayFn(true, type, arrayReg, indexReg, fromReg);
	}

	/**
	 * @param load
	 * @param type
	 * @param arrayValue
	 * @param indexValue
	 * @param valueValue
	 */
	public ArrayFn(boolean load, int type, Value arrayValue, Value indexValue, Value valueValue) {
		super();
		this.aput = load;
		this.type = type;
		this.arrayValue = arrayValue;
		this.indexValue = indexValue;
		this.valueValue = valueValue;
	}

	public boolean aput = true;

	/**
	 * @see Type#getSort()
	 */
	public int type;
	/**
	 * Array Object Reg
	 */
	public Value arrayValue;
	/**
	 * Position Object Reg
	 */
	public Value indexValue;
	/**
	 * 
	 */
	public Value valueValue;

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.v4.tree.Base#inRegs()
	 */
	@Override
	public Value[] inValues() {
		if (!aput) {
			return asList(arrayValue, indexValue);
		} else {
			return asList(arrayValue, indexValue, valueValue);
		}
	}

	static Type[] MAP = new Type[] { Type.INT_TYPE, Type.LONG_TYPE, Type.getType(Object.class), Type.BOOLEAN_TYPE, Type.BYTE_TYPE, Type.CHAR_TYPE,
			Type.SHORT_TYPE,

	};

	public void accept(Type suggest, MethodVisitor mv) {
		Type asmType = MAP[type];
		if (aput) {
			arrayValue.accept(Type.getType("[" + asmType.getDescriptor()), mv);
			indexValue.accept(Type.INT_TYPE, mv);
			valueValue.accept(asmType, mv);
			mv.visitInsn(asmType.getOpcode(Opcodes.IASTORE));
		} else {
			arrayValue.accept(Type.getType("[" + asmType.getDescriptor()), mv);
			indexValue.accept(Type.INT_TYPE, mv);
			mv.visitInsn(asmType.getOpcode(Opcodes.IALOAD));
		}
	}

	public String toString() {
		if (!aput) {
			return "" + arrayValue + "[" + indexValue + "]";
		} else {
			return "" + arrayValue + "[" + indexValue + "]=" + valueValue;
		}
	}

}
