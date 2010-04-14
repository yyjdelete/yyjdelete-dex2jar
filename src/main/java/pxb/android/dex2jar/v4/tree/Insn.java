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

/**
 * @author Panxiaobo [pxb1988@gmail.com]
 * 
 */
public class Insn {

	public int reg;
	public Value value;

	Insn pre;
	Insn next;

	/**
	 * @return the pre
	 */
	public Insn getPre() {
		return pre;
	}

	/**
	 * @param pre
	 *            the pre to set
	 */
	public void setPre(Insn pre) {
		this.pre = pre;
	}

	/**
	 * @return the next
	 */
	public Insn getNext() {
		return next;
	}

	/**
	 * @param next
	 *            the next to set
	 */
	public void setNext(Insn next) {
		this.next = next;
	}

	/**
	 * @param reg
	 * @param value
	 */
	public Insn(int reg, Value value) {
		super();
		this.reg = reg;
		this.value = value;
	}

	public String toString() {
		if (reg < 0) {
			return value.toString();
		} else {
			return "v" + reg + "=" + value.toString();
		}
	}
}
