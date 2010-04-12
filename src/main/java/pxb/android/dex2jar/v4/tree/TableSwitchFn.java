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
public class TableSwitchFn extends Fn {

	Value value;
	int first_case;
	int last_case;
	Label default_label;
	Label[] labels;

	/**
	 * @param value
	 * @param firstCase
	 * @param lastCase
	 * @param defaultLabel
	 * @param labels
	 */
	public TableSwitchFn(Value value, int firstCase, int lastCase, Label defaultLabel, Label[] labels) {
		super();
		this.value = value;
		first_case = firstCase;
		last_case = lastCase;
		default_label = defaultLabel;
		this.labels = labels;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.v4.tree.Value#accept(org.objectweb.asm.Type, org.objectweb.asm.MethodVisitor)
	 */
	public void accept(Type suggest, MethodVisitor mv) {
		value.accept(Type.INT_TYPE, mv);
		mv.visitTableSwitchInsn(first_case, last_case, default_label, labels);
	}

}
