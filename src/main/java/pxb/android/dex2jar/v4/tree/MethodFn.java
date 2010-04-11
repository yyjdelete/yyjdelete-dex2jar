/**
 * 
 */
package pxb.android.dex2jar.v4.tree;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import pxb.android.dex2jar.Method;

/**
 * @author Panxiaobo
 * 
 */
public class MethodFn extends Fn {

	int opcode;
	Method method;

	Value[] args;

	/**
	 * @param opcode
	 * @param method
	 * @param args
	 */
	public MethodFn(int opcode, Method method, Value[] args) {
		super();
		this.opcode = opcode;
		this.method = method;
		this.args = args;
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
