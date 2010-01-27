/*
 * Copyright (c) 2009-2010 Panxiaobo
 * 
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pxb.android.dex2jar.optimize;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.analysis.Analyzer;
import org.objectweb.asm.tree.analysis.AnalyzerException;
import org.objectweb.asm.tree.analysis.BasicInterpreter;
import org.objectweb.asm.tree.analysis.Frame;
import org.objectweb.asm.tree.analysis.Value;
import org.objectweb.asm.util.TraceMethodVisitor;

import pxb.android.dex2jar.Method;

/**
 * @author Panxiaobo [pxb1988@126.com]
 * @version $Id$
 */
@Deprecated
@SuppressWarnings("unchecked")
public class Y extends MethodTransformerAdapter implements Opcodes {

	Method m;

	public Y(Method m, MethodTransformer tr) {
		super(tr);
		this.m = m;
	}

	static class Node extends Frame {
		public Set<Node> from = new HashSet<Node>();
		public Set<Node> to = new HashSet<Node>();

		public Node(int nLocals, int nStack) {
			super(nLocals, nStack);
		}

		public Node(Frame src) {
			super(src);
		}

		public String toString() {
			return super.toString();
		}
	}

	class A extends BasicInterpreter {

		@Override
		public Value binaryOperation(AbstractInsnNode insn, Value value1, Value value2) throws AnalyzerException {
			return super.binaryOperation(insn, value1, value2);
		}

		@Override
		public Value copyOperation(AbstractInsnNode insn, Value value) throws AnalyzerException {
			if (Util.isRead(insn)) {

			}
			return super.copyOperation(insn, value);
		}

		@Override
		public Value merge(Value v, Value w) {
			// TODO Auto-generated method stub
			return super.merge(v, w);
		}

		@Override
		public Value naryOperation(AbstractInsnNode insn, List values) throws AnalyzerException {
			// TODO Auto-generated method stub
			return super.naryOperation(insn, values);
		}

		@Override
		public Value newOperation(AbstractInsnNode insn) throws AnalyzerException {
			// TODO Auto-generated method stub
			return super.newOperation(insn);
		}

		@Override
		public void returnOperation(AbstractInsnNode insn, Value value, Value expected) throws AnalyzerException {
			// TODO Auto-generated method stub
			super.returnOperation(insn, value, expected);
		}

		@Override
		public Value newValue(Type type) {
			// TODO Auto-generated method stub
			return super.newValue(type);
		}

		@Override
		public Value ternaryOperation(AbstractInsnNode insn, Value value1, Value value2, Value value3)
				throws AnalyzerException {
			// TODO Auto-generated method stub
			return super.ternaryOperation(insn, value1, value2, value3);
		}

		@Override
		public Value unaryOperation(AbstractInsnNode insn, Value value) throws AnalyzerException {
			// TODO Auto-generated method stub
			return super.unaryOperation(insn, value);
		}

	}

	InsnList instructions;

	@Override
	public void transform(MethodNode method) {
		this.instructions = method.instructions;

		TraceMethodVisitor tmv = new TraceMethodVisitor();
		method.accept(tmv);
		for (Object t : tmv.text) {
			System.out.print(t);
		}
		System.out.println();

		Analyzer analyzer = new Analyzer(new A()) {
			protected Frame newFrame(int nLocals, int nStack) {
				return new Node(nLocals, nStack);
			}

			protected Frame newFrame(Frame src) {
				return new Node(src);
			}

			protected void newControlFlowEdge(int src, int dst) {
				Node s = (Node) getFrames()[src];
				Node d = (Node) getFrames()[dst];
				s.to.add(d);
				d.from.add(s);
			}
		};
		try {
			analyzer.analyze(m.getOwner(), method);
		} catch (AnalyzerException e) {
			e.printStackTrace();
		}
		frames = analyzer.getFrames();
		doE();
		super.transform(method);
	}

	Frame[] frames;

	public void doE() {
		AbstractInsnNode ins[] = instructions.toArray();
		Set<Integer> in = new HashSet<Integer>();
		Set<Integer> out = new HashSet<Integer>();
		Set<Integer> all = new HashSet<Integer>();
		for (int i = 0; i < frames.length; i++) {
			AbstractInsnNode p = ins[i];
			if (p instanceof LabelNode) {
				in.clear();
				out.clear();
				all.clear();
			} else if (Util.isRead(p)) {

			} else if (Util.isWrite(p)) {

			}
			// Frame f = frames[i];
		}
	}
}
