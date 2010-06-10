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
 * 4则运算
 * 
 * @author Panxiaobo [pxb1988@gmail.com]
 * 
 */
public class AsmdFn extends Fn {
	// public static final int OP_ADD_INT_2ADDR = 176; 0
	// public static final int OP_SUB_INT_2ADDR = 177; 1
	// public static final int OP_MUL_INT_2ADDR = 178; 2
	// public static final int OP_DIV_INT_2ADDR = 179; 3
	// public static final int OP_REM_INT_2ADDR = 180; 4
	// public static final int OP_AND_INT_2ADDR = 181; 5
	// public static final int OP_OR_INT_2ADDR = 182; 6
	// public static final int OP_XOR_INT_2ADDR = 183; 7
	// public static final int OP_SHL_INT_2ADDR = 184; 8
	// public static final int OP_SHR_INT_2ADDR = 185; 9
	// public static final int OP_USHR_INT_2ADDR = 186;10
	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.v4.tree.Value#accept(org.objectweb.asm.Type, org.objectweb.asm.MethodVisitor)
	 */
	public void accept(Type suggest, MethodVisitor mv) {
		a.accept(type, mv);
		b.accept(type, mv);
		mv.visitInsn(type.getOpcode(map[asmd]));
	}

	static int[] map = new int[] { Opcodes.IADD, Opcodes.ISUB, Opcodes.IMUL, Opcodes.IDIV, Opcodes.IREM, Opcodes.IAND, Opcodes.IOR, Opcodes.IXOR, Opcodes.ISHL,
			Opcodes.ISHR, Opcodes.IUSHR };

	static String[] mapString = new String[] { "+", "-", "*", "/", "REM", "&", "|", "^", ">", "<", ">>" };

	Value a;
	Value b;

	public String toString() {
		return a + mapString[asmd] + b;
	}

	/**
	 * @param type
	 * @param asmd
	 * @param a
	 * @param b
	 */
	public AsmdFn(Type type, int asmd, Value a, Value b) {
		super();
		this.type = type;
		this.asmd = asmd;
		this.a = a;
		this.b = b;
	}

	/**
	 * 数据类型
	 */
	Type type;
	/**
	 * 4则运算,+-*...
	 */
	int asmd;

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.v4.tree.Fn#inValues()
	 */
	@Override
	public Value[] inValues() {
		return asList(a, b);
	}

}
