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
import java.util.Map;
import java.util.Map.Entry;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

/**
 * @author Panxiaobo [pxb1988@gmail.com]
 * 
 */
public class TryCatchNode {
	public Label start, end;
	public List<Map.Entry<String, Label>> handlers = new ArrayList<Entry<String, Label>>();

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
		for (Entry<String, Label> e : handlers)
			mv.visitTryCatchBlock(start, end, e.getValue(), e.getKey());
	}

}
