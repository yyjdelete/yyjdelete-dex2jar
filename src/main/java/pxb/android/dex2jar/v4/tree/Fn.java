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

import org.objectweb.asm.Opcodes;

import pxb.android.dex2jar.DexOpcodes;

/**
 * 
 * a[b]=c c=a[b]
 * 
 * @author Panxiaobo [pxb1988@gmail.com]
 * 
 */
public abstract class Fn implements Value, DexOpcodes, Opcodes {

	/**
	 * 
	 */
	public Fn() {
		super();
	}

	public abstract Value[] inValues();

	protected Value[] asList(Value... vs) {
		return vs;
	}
}
