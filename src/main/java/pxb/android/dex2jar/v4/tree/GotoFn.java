/**
 * 
 */
package pxb.android.dex2jar.v4.tree;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * @author Panxiaobo
 * 
 */
public class GotoFn extends Fn {

	Label label;

	/**
	 * @param label
	 */
	public GotoFn(Label label) {
		super();
		this.label = label;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.v4.tree.Value#accept(org.objectweb.asm.Type, org.objectweb.asm.MethodVisitor)
	 */
	public void accept(Type suggest, MethodVisitor mv) {
		mv.visitJumpInsn(Opcodes.GOTO, label);
	}

	/* (non-Javadoc)
	 * @see pxb.android.dex2jar.v4.tree.Fn#inValues()
	 */
	@Override
	public Value[] inValues() {
		return asList();
	}

}
