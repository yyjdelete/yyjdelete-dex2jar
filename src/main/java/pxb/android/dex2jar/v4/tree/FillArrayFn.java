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
public class FillArrayFn extends Fn {

	Object values[];

	Value arrayValue;

	int elemWidth;

	/**
	 * @param arrayValue
	 * @param values
	 * @param elemWidth
	 */
	public FillArrayFn(Value arrayValue, Object[] values, int elemWidth) {
		super();
		this.arrayValue = arrayValue;
		this.values = values;
		this.elemWidth = elemWidth;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.v4.tree.Value#accept(org.objectweb.asm.Type, org.objectweb.asm.MethodVisitor)
	 */
	public void accept(Type suggest, MethodVisitor mv) {
		int op = 0;
		switch (elemWidth) {
		case 1:
			op = Opcodes.BASTORE;
			arrayValue.accept(Type.getType("[B"), mv);
			break;
		case 2:
			op = Opcodes.SASTORE;
			arrayValue.accept(Type.getType("[S"), mv);
			break;
		case 4:
			op = Opcodes.IASTORE;
			arrayValue.accept(Type.getType("[I"), mv);
			break;
		case 8:
			op = Opcodes.LASTORE;
			arrayValue.accept(Type.getType("[J"), mv);
			break;
		}
		
		for (int i = 0; i < values.length; i++) {
			mv.visitInsn(Opcodes.DUP);
			mv.visitLdcInsn(i);
			mv.visitLdcInsn(values[i]);
			mv.visitInsn(op);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.v4.tree.Fn#inValues()
	 */
	@Override
	public Value[] inValues() {
		Value[] v = new Value[1 + values.length];
		v[0] = arrayValue;
		System.arraycopy(values, 0, v, 1, values.length);
		return v;
	}
}
