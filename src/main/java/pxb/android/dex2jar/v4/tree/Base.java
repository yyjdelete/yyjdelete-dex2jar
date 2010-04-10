/**
 * 
 */
package pxb.android.dex2jar.v4.tree;

/**
 * 
 * a[b]=c c=a[b]
 * 
 * @author Panxiaobo
 * 
 */
public abstract class Base {
	protected int opcode;

	/**
	 * 
	 */
	public Base() {
		super();
		// TODO Auto-generated constructor stub
	}

	public abstract int[] inRegs();

	/**
	 * 
	 * @return the out reg,if no return <0
	 */
	public abstract int outReg();

	/**
	 * @param opcode
	 */
	public Base(int opcode) {
		super();
		this.opcode = opcode;
	}

	/**
	 * @return the opcode
	 */
	public int getOpcode() {
		return opcode;
	}

	/**
	 * @param opcode
	 *            the opcode to set
	 */
	public void setOpcode(int opcode) {
		this.opcode = opcode;
	}

}
