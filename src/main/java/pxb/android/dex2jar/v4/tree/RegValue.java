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
public class RegValue implements Value {

	public int reg;

	public Type type;

	/**
	 * @param reg
	 */
	public RegValue(int reg) {
		super();
		this.reg = reg;
	}

	/**
	 * @param reg
	 * @param type
	 */
	public RegValue(int reg, Type type) {
		super();
		this.reg = reg;
		this.type = type;
	}

	public void accept(Type type, MethodVisitor mv) {
		if (type == null) {
			mv.visitVarInsn(this.type.getOpcode(Opcodes.ILOAD), reg);
		} else {
			mv.visitVarInsn(type.getOpcode(Opcodes.ILOAD), reg);
		}
	}

	public String toString() {
		return "v" + reg;
	}
}
