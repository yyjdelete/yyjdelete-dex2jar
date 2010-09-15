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
import java.util.List;
import java.util.Map;

import org.objectweb.asm.Label;

import pxb.android.dex2jar.v4.node.DexMethodNode;
import pxb.android.dex2jar.v4.node.TryCatchNode;
import pxb.android.dex2jar.v4.node.TryCatchNode.HandlerPair;
import pxb.android.dex2jar.v4.optimize.b.Block;
import pxb.android.dex2jar.v4.optimize.b.TC;
import pxb.android.dex2jar.v4.tree.Fn;
import pxb.android.dex2jar.v4.tree.GotoFn;
import pxb.android.dex2jar.v4.tree.Insn;
import pxb.android.dex2jar.v4.tree.InsnList;
import pxb.android.dex2jar.v4.tree.LabelFn;
import pxb.android.dex2jar.v4.tree.RegValue;
import pxb.android.dex2jar.v4.tree.StaticValue;

/**
 * @author Panxiaobo [pxb1988@gmail.com]
 * 
 */
public class A {

	List<Block> blocks;
	List<TC> tcs;
	Map<Label, Block> map;

	public void optmize(DexMethodNode codeNode) {
		blocks = cut(codeNode.codeNode.insnList);
		map = generateMap();
		tcs = link(map, blocks, codeNode.codeNode.tryCatches);
	}

	public Map<Label, Block> generateMap() {
		Map<Label, Block> map = new HashMap<Label, Block>();
		for (Block block : blocks) {
			if (block.label != null) {
				map.put(block.label, block);
			}
		}
		return map;
	}

	public static List<TC> link(Map<Label, Block> map, List<Block> blocks, List<TryCatchNode> methodNodeTCs) {

		for (Block block : blocks) {
			if (block.nextLabel != null) {
				block.nextBlock = map.get(block.nextLabel);
			}
		}
		ArrayList<TC> tcs = new ArrayList<TC>();
		for (TryCatchNode tr : methodNodeTCs) {
			TC t = new TC();
			tcs.add(t);
			int i = blocks.indexOf(map.get(tr.start));
			if (i < 0) {
				throw new RuntimeException("try-catch start label NOT found");
			}
			for (; i < blocks.size(); i++) {
				Block b = blocks.get(i);
				if (b.label == tr.end) {
					break;
				} else {
					t.blocks.add(b);
					b.tcs.add(t);
				}
			}
			for (HandlerPair e : tr.handlers) {
				t.handlers.add(new TC.HandlerPair(e.type, map.get(e.label)));
			}
		}
		return tcs;
	}

	<T> T last(List<T> list) {
		return list.get(list.size() - 1);
	}

	public void i(Block b) {
		HashMap<Integer, Insn> outs = new HashMap();
		HashMap<Integer, Insn> ins = new HashMap();
		for (Insn insn : b.insns) {
			if (insn.reg >= 0) {
				outs.put(insn.reg, insn);
			}
			if (insn.value instanceof StaticValue) {
				// ignore
			} else if (insn.value instanceof RegValue) {

			}
		}

	}

	public void jump(Block b) {

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
					block.insns = insns;
					label = ((LabelFn) insn.value).label;
					block.nextLabel = label;
					blocks.add(block);
					insns = new ArrayList<Insn>();
					break;
				}
				case JUMP: {
					Block block = new Block();
					block.label = label;
					insns.add(insn);
					block.insns = insns;

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
					Block block = new Block();
					block.label = label;
					insns.add(insn);
					block.insns = insns;
					blocks.add(block);
					insns = new ArrayList<Insn>();
				}
					break;
				case GOTO: {
					Block block = new Block();
					block.label = label;
					block.insns = insns;
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
					Block block = new Block();
					block.label = label;
					insns.add(insn);
					block.insns = insns;
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
			block.insns = insns;
			blocks.add(block);
		}

		return blocks;

	}
}
