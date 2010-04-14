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
package pxb.android.dex2jar.v4.tree;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

/**
 * @author Panxiaobo [pxb1988@gmail.com]
 * 
 */
public class TableSwitchFn extends SwitchFn {

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

	/* (non-Javadoc)
	 * @see pxb.android.dex2jar.v4.tree.Fn#inValues()
	 */
	@Override
	public Value[] inValues() {
		return asList(value);
	}

}
