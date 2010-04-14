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
		if (replace != null) {
			replace.accept(type, mv);
		} else {
			if (type == null) {
				mv.visitVarInsn(this.type.getOpcode(Opcodes.ILOAD), reg);
			} else {
				mv.visitVarInsn(type.getOpcode(Opcodes.ILOAD), reg);
			}
		}
	}

	private Value replace;

	public void replace(Value value) {
		this.replace = value;
	}

	public String toString() {
		if (replace != null) {
			return replace.toString();
		} else {
			return "v" + reg;
		}
	}
}
