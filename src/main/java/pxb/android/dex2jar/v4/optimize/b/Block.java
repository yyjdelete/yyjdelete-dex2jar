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
package pxb.android.dex2jar.v4.optimize.b;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Label;

import pxb.android.dex2jar.v4.tree.Insn;

/**
 * @author Panxiaobo [pxb1988@gmail.com]
 * 
 */
public class Block {
	public Label label;
	public List<Insn> insn = new ArrayList<Insn>();
	public List<Block> froms = new ArrayList<Block>();
	public List<Block> to = new ArrayList<Block>();

	protected int id = idc++;

	public static int idc = 0;
	public Block next;

	public Label nextLabel;

	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (label != null) {
			sb.append("Block ").append(id).append('\n');
		}
		for (Insn insn : this.insn) {
			sb.append(insn).append('\n');
		}
		if (next != null) {
			sb.append("next: ").append(next.id).append('\n');
		} else if (nextLabel != null) {
			sb.append("next: ").append(nextLabel).append('\n');
		}
		return sb.toString();

	}

}
