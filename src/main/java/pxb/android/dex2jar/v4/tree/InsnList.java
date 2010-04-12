/**
 * 
 */
package pxb.android.dex2jar.v4.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Panxiaobo
 * 
 */
public class InsnList {

	List<Insn> insns = new ArrayList();

	public void add(int reg, Value value) {
		insns.add(new Insn(reg, value));
	}

	public void add(Value value) {
		add(-1, value);
	}
}
