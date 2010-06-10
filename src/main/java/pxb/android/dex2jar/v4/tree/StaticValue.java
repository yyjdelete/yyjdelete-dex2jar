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
import org.objectweb.asm.Type;

/**
 * @author Panxiaobo [pxb1988@gmail.com]
 * 
 */
public class StaticValue implements Value {

	/**
	 * @param value
	 */
	public StaticValue(Object value) {
		this.value = value;
	}

	/**
	 * 
	 */
	public StaticValue() {
	}

	public Object value;

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.v4.tree.Value#accept(org.objectweb.asm.Type, org.objectweb.asm.MethodVisitor)
	 */
	public void accept(Type type, MethodVisitor mv) {
		Object v = value;
		if (value instanceof Integer) {
			if (type.equals(Type.FLOAT_TYPE)) {
				v = Float.intBitsToFloat((Integer) value);
			}
		} else if (value instanceof Long) {
			if (type.equals(Type.DOUBLE_TYPE)) {
				v = Double.longBitsToDouble((Long) value);
			}
		}
		mv.visitLdcInsn(v);
	}

	public String toString() {
		if (value instanceof String) {
			return "\"" + value + "\"";
		} else
			return "" + value;
	}

}