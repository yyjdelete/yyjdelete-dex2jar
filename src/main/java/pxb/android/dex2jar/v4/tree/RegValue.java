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

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * @author Panxiaobo [pxb1988@gmail.com]
 * 
 */
public class RegValue implements Value {

	public int reg;

	public Type type;

	/**
	 * @param reg
	 */
	public RegValue(int reg) {
		super();
		this.reg = reg;
	}

	/**
	 * @param reg
	 * @param type
	 */
	public RegValue(int reg, Type type) {
		super();
		this.reg = reg;
		this.type = type;
	}

	public void accept(Type type, MethodVisitor mv) {
		if (replace != null) {
			replace.accept(type, mv);
		} else {
			if (type == null) {
				mv.visitVarInsn(this.type.getOpcode(Opcodes.ILOAD), reg);
			} else {
				mv.visitVarInsn(type.getOpcode(Opcodes.ILOAD), reg);
			}
		}
	}

	private Value replace;

	public void replace(Value value) {
		this.replace = value;
	}

	public String toString() {
		if (replace != null) {
			return replace.toString();
		} else {
			return "v" + reg;
		}
	}
}
