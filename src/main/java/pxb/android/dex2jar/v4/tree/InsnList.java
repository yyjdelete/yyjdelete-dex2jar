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

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * @author Panxiaobo [pxb1988@gmail.com]
 * 
 */
public class InsnList {

	public List<Insn> insns = new ArrayList<Insn>();

	public void add(int reg, Value value) {
		insns.add(new Insn(reg, value));
	}

	public void add(Value value) {
		add(-1, value);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		for (Insn insn : insns) {

			if (insn.reg < 0) {
				sb.append(String.format("%04d %s \n", i++, insn.value));
			} else {
				sb.append(String.format("%04d v%d = %s \n", i++, insn.reg, insn.value));
			}

		}
		return sb.toString();
	}

	/**
	 * @param mv
	 */
	public void accept(MethodVisitor mv) {
		for (Insn insn : insns) {
			insn.value.accept(Type.INT_TYPE, mv);
			if (insn.reg >= 0) {
				mv.visitVarInsn(Opcodes.ISTORE, insn.reg);
			}
		}
	}

}
