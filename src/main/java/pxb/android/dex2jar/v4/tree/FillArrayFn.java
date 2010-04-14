/**
 * 
 */
package pxb.android.dex2jar.v4.tree;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * @author Panxiaobo
 * 
 */
public class FillArrayFn extends Fn {

	Object values[];

	Value arrayValue;

	int elemWidth;

	/**
	 * @param arrayValue
	 * @param values
	 * @param elemWidth
	 */
	public FillArrayFn(Value arrayValue, Object[] values, int elemWidth) {
		super();
		this.arrayValue = arrayValue;
		this.values = values;
		this.elemWidth = elemWidth;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.v4.tree.Value#accept(org.objectweb.asm.Type, org.objectweb.asm.MethodVisitor)
	 */
	public void accept(Type suggest, MethodVisitor mv) {
		int op = 0;
		switch (elemWidth) {
		case 1:
			op = Opcodes.BASTORE;
			break;
		case 2:
			op = Opcodes.SASTORE;
			break;
		case 4:
			op = Opcodes.IASTORE;
			break;
		case 8:
			op = Opcodes.LASTORE;
			break;
		}
		arrayValue.accept(null, mv);
		for (int i = 0; i < values.length; i++) {
			mv.visitInsn(Opcodes.DUP);
			mv.visitLdcInsn(i);
			mv.visitLdcInsn(values[i]);
			mv.visitInsn(op);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.v4.tree.Fn#inValues()
	 */
	@Override
	public Value[] inValues() {
		Value[] v = new Value[1 + values.length];
		v[0] = arrayValue;
		System.arraycopy(values, 0, v, 1, values.length);
		return v;
	}
}
