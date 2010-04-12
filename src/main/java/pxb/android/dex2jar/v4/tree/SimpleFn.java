/**
 * 
 */
package pxb.android.dex2jar.v4.tree;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import pxb.android.dex2jar.DexOpcodes;

/**
 * @author Panxiaobo
 * 
 */
public class SimpleFn extends Fn implements DexOpcodes, Opcodes {

	int opcode;
	Value value;

	/**
	 * @param opcode
	 * @param value
	 */
	public SimpleFn(int opcode, Value value) {
		super();
		this.opcode = opcode;
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.v4.tree.Value#accept(org.objectweb.asm.Type, org.objectweb.asm.MethodVisitor)
	 */
	public void accept(Type suggest, MethodVisitor mv) {
		value.accept(Type.getType(Object.class), mv);
		switch (opcode) {
		case OP_MONITOR_ENTER:
		case OP_MONITOR_EXIT:
		}
	}

}
