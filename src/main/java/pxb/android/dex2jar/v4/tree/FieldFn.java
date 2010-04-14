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

import pxb.android.dex2jar.Field;

/**
 * 
 *store: a[b]=c ,load: c=a[b]
 * 
 * @author Panxiaobo [pxb1988@gmail.com]
 * 
 */
public class FieldFn extends Fn {

	/**
	 * 
	 */
	public FieldFn() {
		super();
	}

	public static final int GET_STATIC = 0;
	public static final int PUT_STATIC = 1;
	public static final int GET = 2;
	public static final int PUT = 3;

	public static FieldFn static_get(Field field) {
		return new FieldFn(GET_STATIC, field, null, null);
	}

	public static FieldFn static_put(Field field, Value fromReg) {
		return new FieldFn(PUT_STATIC, field, null, fromReg);
	}

	public static FieldFn get(Field field, Value ownerReg) {
		return new FieldFn(GET, field, ownerReg, null);
	}

	public static FieldFn put(Field field, Value ownerReg, Value fromReg) {
		return new FieldFn(PUT, field, ownerReg, fromReg);
	}

	/**
	 * @param type
	 * @param field
	 * @param ownerValue
	 * @param valueValue
	 */
	public FieldFn(int type, Field field, Value ownerValue, Value valueValue) {
		super();
		this.type = type;
		this.field = field;
		this.ownerValue = ownerValue;
		this.valueValue = valueValue;
	}

	public int type;
	public Field field;

	public Value ownerValue;
	public Value valueValue;

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.v4.tree.Base#inRegs()
	 */
	@Override
	public Value[] inValues() {
		switch (type) {
		case PUT:
			return new Value[] { ownerValue, valueValue };
		case GET:
			return new Value[] { ownerValue };
		case GET_STATIC:
			return new Value[0];
		case PUT_STATIC:
		default:
			return new Value[] { valueValue };
		}
	}

	static Type[] MAP = new Type[] { Type.INT_TYPE, Type.LONG_TYPE, Type.getType(Object.class), Type.BOOLEAN_TYPE, Type.BYTE_TYPE, Type.CHAR_TYPE,
			Type.SHORT_TYPE,

	};

	public void accept(Type suggest, MethodVisitor mv) {
		switch (type) {
		case GET_STATIC:
			mv.visitFieldInsn(Opcodes.GETSTATIC, field.getOwner(), field.getName(), field.getType());
			break;
		case PUT_STATIC:
			valueValue.accept(Type.getType(field.getType()), mv);
			mv.visitFieldInsn(Opcodes.PUTSTATIC, field.getOwner(), field.getName(), field.getType());
			break;
		case GET:
			ownerValue.accept(Type.getType(field.getOwner()), mv);
			mv.visitFieldInsn(Opcodes.GETFIELD, field.getOwner(), field.getName(), field.getType());
			break;
		case PUT:
			ownerValue.accept(Type.getType(field.getOwner()), mv);
			mv.visitFieldInsn(Opcodes.GETSTATIC, field.getOwner(), field.getName(), field.getType());
			mv.visitFieldInsn(Opcodes.PUTFIELD, field.getOwner(), field.getName(), field.getType());
			break;
		}
	}

	public String toString() {
		switch (type) {
		case GET_STATIC:
			return Type.getType(field.getOwner()).getClassName() + "." + field.getName();
		case PUT_STATIC:
			return Type.getType(field.getOwner()).getClassName() + "." + field.getName() + "=" + valueValue;
		case GET:
			return ownerValue + "." + field.getName();
		case PUT:
		default:
			return "" + ownerValue + "." + field.getName() + "=" + valueValue;
		}
	}

}
