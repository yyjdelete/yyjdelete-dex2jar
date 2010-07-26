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

import pxb.android.dex2jar.Method;

/**
 * @author Panxiaobo [pxb1988@gmail.com]
 * 
 */
public class MethodFn extends Fn {

	public int opcode;
	public Method method;
	public Value[] args;
	public boolean invorkSuper = false;

	/**
	 * @param opcode
	 * @param method
	 * @param args
	 */
	public MethodFn(int opcode, Method method, Value[] args) {
		super();
		this.opcode = opcode;
		this.method = method;
		this.args = args;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.v4.tree.Value#accept(org.objectweb.asm.Type, org.objectweb.asm.MethodVisitor)
	 */
	public void accept(Type suggest, MethodVisitor mv) {
		String types[] = method.getType().getParameterTypes();
		switch (opcode) {
		case OP_INVOKE_STATIC: {
			for (int j = 0; j < types.length; j++) {
				args[j].accept(Type.getType(types[j]), mv);
			}
			mv.visitMethodInsn(INVOKESTATIC, method.getOwner(), method.getName(), method.getType().getDesc());
		}
			break;
		case OP_INVOKE_VIRTUAL:
			for (int j = 0; j < types.length; j++) {
				args[j].accept(Type.getType(types[j]), mv);
			}
			mv.visitMethodInsn(INVOKEVIRTUAL, method.getOwner(), method.getName(), method.getType().getDesc());
			break;
		case OP_INVOKE_INTERFACE:
			for (int j = 0; j < types.length; j++) {
				args[j].accept(Type.getType(types[j]), mv);
			}
			mv.visitMethodInsn(INVOKEINTERFACE, method.getOwner(), method.getName(), method.getType().getDesc());
			break;
		case OP_INVOKE_SUPER:
		case OP_INVOKE_DIRECT:
			if (!invorkSuper) {
				mv.visitTypeInsn(NEW, method.getOwner());
				mv.visitInsn(DUP);
			}

			for (int j = 0; j < types.length; j++) {
				args[j].accept(Type.getType(types[j]), mv);
			}
			mv.visitMethodInsn(INVOKESPECIAL, method.getOwner(), method.getName(), method.getType().getDesc());

			break;
		}
	}

	public String toString() {
		switch (opcode) {
		case OP_INVOKE_STATIC: {
			int i = 0;
			StringBuilder sb = new StringBuilder();
			for (int j = 0; j < method.getType().getParameterTypes().length; j++) {
				sb.append(',').append(args[i++]);
			}
			if (sb.length() > 0) {
				sb.deleteCharAt(0);
			}
			return String.format("%s.%s(%s)", Type.getType(method.getOwner()).getClassName(), method.getName(), sb.toString());
		}
		case OP_INVOKE_VIRTUAL:
		case OP_INVOKE_DIRECT:
		case OP_INVOKE_INTERFACE:
		case OP_INVOKE_SUPER: {
			int i = 1;
			StringBuilder sb = new StringBuilder();
			for (int j = 0; j < method.getType().getParameterTypes().length; j++) {
				sb.append(',').append(args[i++]);
			}
			if (sb.length() > 0) {
				sb.deleteCharAt(0);
			}
			switch (opcode) {
			case OP_INVOKE_VIRTUAL:
			case OP_INVOKE_INTERFACE:
				return String.format("%s.%s(%s)", args[0], method.getName(), sb.toString());
			case OP_INVOKE_DIRECT:
			case OP_INVOKE_SUPER:
				if (!invorkSuper) {
					return String.format("new %s(%s)", Type.getType(method.getOwner()).getClassName(), sb.toString());
				} else {
					return String.format("super(%s)", sb.toString());
				}
			}
		}
		}
		return "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.v4.tree.Fn#inValues()
	 */
	@Override
	public Value[] inValues() {
		if (!invorkSuper && method.getName().equals("<init>")) {
			Value[] v = new Value[args.length - 1];
			System.arraycopy(args, 0, v, 0, v.length);
			return v;
		} else {
			return args;
		}
	}

}
