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

	public String toString() {
		switch (opcode) {
		case OP_INVOKE_STATIC: {
			int i = 0;
			StringBuilder sb = new StringBuilder();
			for (int j = 0; j < method.getType().getParameterTypes().length; j++) {
				sb.append(',').append(args[i++]);
			}
			if (sb.length() > 0) {
				sb.deleteCharAt(0);
			}
			return String.format("%s.%s(%s)", Type.getType(method.getOwner()).getClassName(), method.getName(), sb.toString());
		}
		case OP_INVOKE_VIRTUAL:
		case OP_INVOKE_DIRECT:
		case OP_INVOKE_INTERFACE:
		case OP_INVOKE_SUPER: {
			int i = 1;
			StringBuilder sb = new StringBuilder();
			for (int j = 0; j < method.getType().getParameterTypes().length; j++) {
				sb.append(',').append(args[i++]);
			}
			if (sb.length() > 0) {
				sb.deleteCharAt(0);
			}

			if (method.getName().equals("<init>")) {
				return String.format("new %s(%s)", Type.getType(method.getOwner()).getClassName(), sb.toString());
			} else {
				return String.format("%s.%s(%s)", args[0], method.getName(), sb.toString());
			}
		}
		}
		return "";
	}

}
