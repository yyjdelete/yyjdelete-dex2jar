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
package pxb.android.dex2jar.v4.node;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

/**
 * @author Panxiaobo [pxb1988@gmail.com]
 * 
 */
public class TryCatchNode {

	public static class HandlerPair {

		/**
		 * @param type
		 * @param label
		 */
		public HandlerPair(String type, Label label) {
			super();
			this.type = type;
			this.label = label;
		}

		public String type;
		public Label label;
	}

	public Label start, end;
	public List<HandlerPair> handlers = new ArrayList<HandlerPair>();

	public int hashCode() {
		return start.hashCode() * 32 + end.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		TryCatchNode t = (TryCatchNode) obj;
		return start.equals(t.start) && end.equals(t.end);
	}

	/**
	 * @param mv
	 */
	public void accept(MethodVisitor mv) {
		for (HandlerPair e : handlers)
			mv.visitTryCatchBlock(start, end, e.label, e.type);
	}

}
