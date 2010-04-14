/*
 * Copyright (c) 2009-2010 Panxiaobo
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pxb.android.dex2jar.v4.optimize;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Label;

import pxb.android.dex2jar.Method;
import pxb.android.dex2jar.v4.tree.EndFn;
import pxb.android.dex2jar.v4.tree.GotoFn;
import pxb.android.dex2jar.v4.tree.Insn;
import pxb.android.dex2jar.v4.tree.InsnList;
import pxb.android.dex2jar.v4.tree.JumpFn;
import pxb.android.dex2jar.v4.tree.LabelFn;
import pxb.android.dex2jar.v4.tree.SwitchFn;
import pxb.android.dex2jar.v4.tree.Value;

/**
 * 
 * @author Panxiaobo [pxb1988@gmail.com]
 * 
 */
public class Optimizer {

	Method method;

	InsnList insnList;

	/**
	 * @param method
	 * @param insnList
	 */
	public Optimizer(Method method, InsnList insnList) {
		super();
		this.method = method;
		this.insnList = insnList;
	}

	class Block {
		Label label;
		List<Insn> insn;
	}

	class SimpleBlock extends Block {
		Label def;
	}

	class JumpBlock extends Block {
		Label def;
		Label success;
	}

	public void optimize() {
		List<Insn> list = new ArrayList<Insn>();
		List<Block> blocks = new ArrayList<Block>();
		Label label;
		int i = 0;
		Insn insn = insnList.insns.get(i);
		if (!(insn.value instanceof LabelFn)) {
			label = new Label();
		} else {
			label = ((LabelFn) insn.value).label;
			i++;
		}

		for (; i < insnList.insns.size(); i++) {
			insn = insnList.insns.get(i);
			Value value = insn.value;
			if (value instanceof LabelFn) {
				LabelFn fn = (LabelFn) value;
				SimpleBlock b = new SimpleBlock();
				b.insn = list;
				b.label = label;
				b.def = fn.label;
				blocks.add(b);
				label = fn.label;
				list = new ArrayList<Insn>();
			} else if (value instanceof GotoFn) {
				// TODO
			} else if (value instanceof JumpFn) {
				// TODO
			} else if (value instanceof SwitchFn) {
				// TODO
			} else if (value instanceof EndFn) {
				// TODO
			} else {
				list.add(insn);
			}
		}
	}
}
