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

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Type;

/**
 * @author Panxiaobo [pxb1988@gmail.com]
 * 
 */
public class TC {
	public List<Block> blocks = new ArrayList<Block>();
	public List<HandlerPair> handlers = new ArrayList<HandlerPair>();

	public static class HandlerPair {

		/**
		 * @param type
		 * @param label
		 */
		public HandlerPair(String type, Block b) {
			super();
			this.type = type;
			this.block = b;
		}

		public String type;
		public Block block;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Block b : blocks) {
			sb.append(",").append("B").append(b.id);
		}
		sb.deleteCharAt(0);
		String s = sb.toString();
		sb.setLength(0);
		for (HandlerPair e : handlers) {
			String type = e.type == null ? type = "all" : Type.getType(e.type).getClassName();
			sb.append("} catch(").append(type).append(" e) {\n").append("B").append(e.block.id).append("\n}");
		}
		return "try {\n" + s + "\n" + sb;
	}
}
