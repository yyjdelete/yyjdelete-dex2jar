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
public class StaticValue implements Value {

	/**
	 * @param value
	 */
	public StaticValue(Object value) {
		this.value = value;
	}

	/**
	 * 
	 */
	public StaticValue() {
	}

	public Object value;

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.v4.tree.Value#accept(org.objectweb.asm.Type, org.objectweb.asm.MethodVisitor)
	 */
	public void accept(Type type, MethodVisitor mv) {
		// TODO
		mv.visitLdcInsn(value);
	}

	public String toString() {
		return "" + value;
	}

}
