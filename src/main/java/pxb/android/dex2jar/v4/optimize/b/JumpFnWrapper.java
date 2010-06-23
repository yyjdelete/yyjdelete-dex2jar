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

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import pxb.android.dex2jar.v4.tree.JumpFn;
import pxb.android.dex2jar.v4.tree.Value;

/**
 * @author Panxiaobo [pxb1988@gmail.com]
 * 
 */
public class JumpFnWrapper extends JumpFn {

	JumpFn fn;

	Block success;

	/**
	 * @param opcode
	 * @param success
	 * @param a
	 * @param b
	 */
	public JumpFnWrapper() {
		super(-1, null, null, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.v4.tree.JumpFn#accept(org.objectweb.asm.Type, org.objectweb.asm.MethodVisitor)
	 */
	@Override
	public void accept(Type suggest, MethodVisitor mv) {
		fn.success = success.label;
		super.accept(suggest, mv);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.v4.tree.JumpFn#inValues()
	 */
	@Override
	public Value[] inValues() {
		return fn.inValues();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.v4.tree.JumpFn#toString()
	 */
	@Override
	public String toString() {
		return fn.toString();
	}

}
