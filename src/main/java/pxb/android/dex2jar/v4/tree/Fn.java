/**
 * 
 */
package pxb.android.dex2jar.v4.tree;

import org.objectweb.asm.Opcodes;

import pxb.android.dex2jar.DexOpcodes;

/**
 * 
 * a[b]=c c=a[b]
 * 
 * @author Panxiaobo
 * 
 */
public abstract class Fn implements Value, DexOpcodes, Opcodes {

	/**
	 * 
	 */
	public Fn() {
		super();
	}

	public abstract Value[] inValues();

	protected Value[] asList(Value... vs) {
		return vs;
	}
}
