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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.objectweb.asm.Label;

import pxb.android.dex2jar.v4.node.DexCodeNode;
import pxb.android.dex2jar.v4.node.TryCatchNode;
import pxb.android.dex2jar.v4.optimize.b.Block;
import pxb.android.dex2jar.v4.optimize.b.EndBlock;
import pxb.android.dex2jar.v4.optimize.b.JumpBlock;
import pxb.android.dex2jar.v4.optimize.b.SwitchBlock;
import pxb.android.dex2jar.v4.tree.EndFn;
import pxb.android.dex2jar.v4.tree.Fn;
import pxb.android.dex2jar.v4.tree.GotoFn;
import pxb.android.dex2jar.v4.tree.Insn;
import pxb.android.dex2jar.v4.tree.InsnList;
import pxb.android.dex2jar.v4.tree.JumpFn;
import pxb.android.dex2jar.v4.tree.LabelFn;
import pxb.android.dex2jar.v4.tree.SwitchFn;

/**
 * @author Panxiaobo [pxb1988@gmail.com]
 * 
 */
public class A {

	Set<Insn> set = new HashSet();

	public void a(DexCodeNode codeNode) {
		List<Block> blocks = cut(codeNode.insnList);
		Block root = link(blocks, codeNode.tryCatches);
	}

	public Block link(List<Block> blocks, List<TryCatchNode> trys) {
		Map<Label, Block> map = new HashMap();
		for (Block block : blocks) {
			if (block.label != null) {
				map.put(block.label, block);
			}
		}
		for (int i = 0; i < blocks.size(); i++) {
			Block block = blocks.get(i);
			if (block.nextLabel != null) {
				block.next = map.get(block.nextLabel);
			}
			if (block instanceof JumpBlock) {
				JumpBlock jb = (JumpBlock) block;
				jb.success = map.get(jb.fn.success);
			} else if (block instanceof SwitchBlock) {
				SwitchBlock sb = (SwitchBlock) block;
				sb.blocks = new Block[sb.fn.labels.length];
				for (int j = 0; j < sb.blocks.length; j++) {
					sb.blocks[j] = map.get(sb.fn.labels[j]);
				}
				sb.defaultBlock = map.get(sb.fn.default_label);
			}
		}

		return null;
	}

	<T> T last(List<T> list) {
		return list.get(list.size() - 1);
	}

	public List<Block> cut(InsnList instnList) {
		Block.idc = 0;
		List<Block> blocks = new ArrayList<Block>();
		List<Insn> insns = new ArrayList<Insn>();
		Label label = null;
		int i = 0;
		Insn insn = instnList.insns.get(i);
		if (insn.value instanceof LabelFn) {
			label = ((LabelFn) insn.value).label;
			i++;
		} else {
			label = new Label();
		}
		for (; i < instnList.insns.size(); i++) {
			insn = instnList.insns.get(i);

			if (insn.value instanceof Fn) {
				switch (((Fn) insn.value).getFnType()) {
				case LABEL: {
					Block block = new Block();
					block.label = label;
					block.insn = insns;
					label = ((LabelFn) insn.value).label;
					block.nextLabel = label;
					blocks.add(block);
					insns = new ArrayList<Insn>();
					break;
				}
				case JUMP: {
					JumpBlock block = new JumpBlock();
					block.label = label;
					block.insn = insns;
					block.fn = (JumpFn) insn.value;

					Insn insnNext = instnList.insns.get(i + 1);
					if (insnNext.value instanceof LabelFn) {
						label = ((LabelFn) insnNext.value).label;
						i++;
					} else {
						label = new Label();
					}
					block.nextLabel = label;
					blocks.add(block);
					insns = new ArrayList<Insn>();
				}
					break;
				case SWITCH: {
					SwitchBlock block = new SwitchBlock();
					block.label = label;
					block.insn = insns;
					block.fn = (SwitchFn) insn.value;
					blocks.add(block);
					insns = new ArrayList<Insn>();
				}
					break;
				case GOTO: {
					Block block = new Block();
					block.label = label;
					block.insn = insns;
					block.nextLabel = ((GotoFn) insn.value).label;
					blocks.add(block);
					if (i + 1 < instnList.insns.size()) {
						Insn insnNext = instnList.insns.get(i + 1);
						if (insnNext.value instanceof LabelFn) {
							label = ((LabelFn) insnNext.value).label;
							i++;
						} else {
							label = new Label();
						}
					} else {
						label = new Label();
					}
					insns = new ArrayList<Insn>();
				}
					break;
				case END: {
					EndBlock block = new EndBlock();
					block.label = label;
					block.insn = insns;
					block.fn = (EndFn) insn.value;
					blocks.add(block);
					if (i + 1 < instnList.insns.size()) {
						Insn insnNext = instnList.insns.get(i + 1);
						if (insnNext.value instanceof LabelFn) {
							label = ((LabelFn) insnNext.value).label;
							i++;
						} else {
							label = new Label();
						}
					} else {
						label = new Label();
					}
					insns = new ArrayList<Insn>();
				}
					break;
				default:
					insns.add(insn);
					break;
				}
			} else {
				insns.add(insn);
			}
		}

		if (insns.size() > 0) {
			Block block = new Block();
			block.label = label;
			block.insn = insns;
			blocks.add(block);
		}

		return blocks;

	}
}
