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

import pxb.android.dex2jar.v4.tree.JumpFn;

/**
 * @author Panxiaobo [pxb1988@gmail.com]
 * 
 */
public class JumpBlock extends Block {

	public Block success;
	public JumpFn fn;

	public String toString() {
		StringBuilder sb = new StringBuilder(super.toString());
		sb.append(fn).append("\n");
		if (success != null) {
			sb.append("success: ").append(success.id);
		} else if (fn != null) {
			sb.append("success: ").append(fn.success);
		}

		return sb.toString();
	}

}
