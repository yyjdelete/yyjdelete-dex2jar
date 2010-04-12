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
public class JumpFn extends Fn {

	Label label;

	Value a;
	Value b;

	int opcode;

	/**
	 * @param opcode
	 * @param label
	 * @param a
	 * @param b
	 */
	public JumpFn(int opcode, Label label, Value a, Value b) {
		super();
		this.opcode = opcode;
		this.label = label;
		this.a = a;
		this.b = b;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.v4.tree.Value#accept(org.objectweb.asm.Type, org.objectweb.asm.MethodVisitor)
	 */
	public void accept(Type suggest, MethodVisitor mv) {
		// TODO Auto-generated method stub

	}

}
