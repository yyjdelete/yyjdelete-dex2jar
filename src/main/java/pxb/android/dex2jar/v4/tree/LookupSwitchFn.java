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
public class LookupSwitchFn extends Fn {
	Value value;
	Label defaultOffset;
	int[] cases;
	Label[] labels;

	/**
	 * @param value
	 * @param defaultOffset
	 * @param cases
	 * @param labels
	 */
	public LookupSwitchFn(Value value, Label defaultOffset, int[] cases, Label[] labels) {
		super();
		this.value = value;
		this.defaultOffset = defaultOffset;
		this.cases = cases;
		this.labels = labels;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.v4.tree.Value#accept(org.objectweb.asm.Type, org.objectweb.asm.MethodVisitor)
	 */
	public void accept(Type suggest, MethodVisitor mv) {
		value.accept(Type.INT_TYPE, mv);
		mv.visitLookupSwitchInsn(defaultOffset, cases, labels);
	}

	/* (non-Javadoc)
	 * @see pxb.android.dex2jar.v4.tree.Fn#inValues()
	 */
	@Override
	public Value[] inValues() {
		return asList(value);
	}
}
