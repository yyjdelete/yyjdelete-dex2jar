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
public class LabelFn extends Fn {

	Label label;

	/**
	 * @param label
	 */
	public LabelFn(Label label) {
		super();
		this.label = label;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.v4.tree.Value#accept(org.objectweb.asm.Type, org.objectweb.asm.MethodVisitor)
	 */
	public void accept(Type suggest, MethodVisitor mv) {
		mv.visitLabel(label);
	}

	public String toString() {
		return "Label :" + label;
	}
}
