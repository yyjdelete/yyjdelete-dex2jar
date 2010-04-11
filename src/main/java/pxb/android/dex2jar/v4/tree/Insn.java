/**
 * 
 */
package pxb.android.dex2jar.v4.tree;

/**
 * @author Panxiaobo
 * 
 */
public class Insn {

	int reg;
	Value value;

	/**
	 * @param reg
	 * @param value
	 */
	public Insn(int reg, Value value) {
		super();
		this.reg = reg;
		this.value = value;
	}

	public String toString() {
		if (reg < 0) {
			return value.toString();
		} else {
			return "v" + reg + "=" + value.toString();
		}
	}
}
