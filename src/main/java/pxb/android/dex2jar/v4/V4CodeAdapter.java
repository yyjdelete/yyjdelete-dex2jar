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
package pxb.android.dex2jar.v4;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import pxb.android.dex2jar.DexOpcodeDump;
import pxb.android.dex2jar.DexOpcodes;
import pxb.android.dex2jar.Field;
import pxb.android.dex2jar.Method;
import pxb.android.dex2jar.v4.tree.ArrayFn;
import pxb.android.dex2jar.v4.tree.AsmdFn;
import pxb.android.dex2jar.v4.tree.CmpFn;
import pxb.android.dex2jar.v4.tree.EndFn;
import pxb.android.dex2jar.v4.tree.ExValue;
import pxb.android.dex2jar.v4.tree.FieldFn;
import pxb.android.dex2jar.v4.tree.FillArrayFn;
import pxb.android.dex2jar.v4.tree.GotoFn;
import pxb.android.dex2jar.v4.tree.InsnList;
import pxb.android.dex2jar.v4.tree.JumpFn;
import pxb.android.dex2jar.v4.tree.LabelFn;
import pxb.android.dex2jar.v4.tree.LookupSwitchFn;
import pxb.android.dex2jar.v4.tree.MethodFn;
import pxb.android.dex2jar.v4.tree.NewArrayFn;
import pxb.android.dex2jar.v4.tree.OneValueFn;
import pxb.android.dex2jar.v4.tree.RegValue;
import pxb.android.dex2jar.v4.tree.SimpleFn;
import pxb.android.dex2jar.v4.tree.StaticValue;
import pxb.android.dex2jar.v4.tree.TableSwitchFn;
import pxb.android.dex2jar.v4.tree.TypeFn;
import pxb.android.dex2jar.v4.tree.Value;
import pxb.android.dex2jar.visitors.DexCodeVisitor;

/**
 * @author Panxiaobo [pxb1988@gmail.com]
 * @version $Id$
 */
public class V4CodeAdapter implements DexCodeVisitor, Opcodes, DexOpcodes {
	protected int _regcount = 0;

	/**
	 * @param method
	 * @param mv
	 */
	public V4CodeAdapter() {
		super();
	}

	public void visitInitLocal(int... args) {
	}

	protected InsnList insnList = new InsnList();

	static final int ARRAY_TYPE_MAP[] = new int[] { Type.INT,// 75 OP_APUT 68 OP_AGET
			Type.LONG, // 76 OP_APUT_WIDE 69 OP_AGET_WIDE
			Type.OBJECT,// 77 OP_APUT_OBJECT 70 OP_AGET_OBJECT
			Type.BOOLEAN,// 78 OP_APUT_BOOLEAN 71 OP_AGET_BOOLEAN
			Type.BYTE, // 79 OP_APUT_BYTE 72 OP_AGET_BYTE
			Type.CHAR, // 80 OP_APUT_CHAR 73 OP_AGET_CHAR
			Type.SHORT // 81 OP_APUT_SHORT 74 OP_AGET_SHORT
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.visitors.DexCodeVisitor#visitArrayInsn(int, int, int, int)
	 */
	public void visitArrayInsn(int opcode, int regFromOrTo, int array, int index) {
		// 75~81 put
		// 69~74 get
		try {
			if (opcode >= OP_APUT) {
				insnList.add(ArrayFn.aput(ARRAY_TYPE_MAP[opcode - OP_APUT], new RegValue(array), new RegValue(index), new RegValue(regFromOrTo)));
			} else {
				insnList.add(regFromOrTo, ArrayFn.aget(ARRAY_TYPE_MAP[opcode - OP_AGET], new RegValue(array), new RegValue(index)));
			}
		} catch (Exception e) {
			throw new RuntimeException(String.format("Exception on Opcode:[0x%04x]=%s", opcode, DexOpcodeDump.dump(opcode)), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.visitors.DexCodeVisitor#visitArrayInsn(int, java.lang.String, int, int)
	 */
	public void visitArrayInsn(int opcode, String type, int saveToReg, int demReg) {
		switch (opcode) {
		case OP_NEW_ARRAY:
			insnList.add(saveToReg, new NewArrayFn(Type.getType(type), new RegValue(demReg)));
			break;
		default:
			throw new RuntimeException(String.format("Not support Opcode:[0x%04x]=%s yet!", opcode, DexOpcodeDump.dump(opcode)));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.visitors.DexCodeVisitor#visitEnd()
	 */
	public void visitEnd() {
		System.out.println(insnList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.visitors.DexCodeVisitor#visitTotalRegSize(int)
	 */
	public void visitTotalRegSize(int totalRegistersSize) {
		_regcount = totalRegistersSize + 1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.visitors.DexCodeVisitor#visitFieldInsn(int, pxb.android.dex2jar.Field, int, int)
	 */
	public void visitFieldInsn(int opcode, Field field, int regFromOrTo, int ownerReg) {
		switch (opcode) {
		case OP_IGET:
		case OP_IGET_WIDE:
		case OP_IGET_OBJECT:
		case OP_IGET_BOOLEAN:
		case OP_IGET_BYTE:
		case OP_IGET_CHAR:
		case OP_IGET_SHORT:
			insnList.add(regFromOrTo, FieldFn.get(field, new RegValue(ownerReg)));
			break;

		case OP_IPUT:
		case OP_IPUT_WIDE:
		case OP_IPUT_OBJECT:
		case OP_IPUT_BOOLEAN:
		case OP_IPUT_BYTE:
		case OP_IPUT_CHAR:
		case OP_IPUT_SHORT:
			insnList.add(FieldFn.put(field, new RegValue(ownerReg), new RegValue(regFromOrTo)));
			break;
		case OP_SGET_OBJECT:// sget-object
		case OP_SGET:
		case OP_SGET_WIDE:
		case OP_SGET_BOOLEAN:
		case OP_SGET_BYTE:
		case OP_SGET_CHAR:
		case OP_SGET_SHORT:
			insnList.add(regFromOrTo, FieldFn.static_get(field));
			break;

		case OP_SPUT_OBJECT:
		case OP_SPUT:
		case OP_SPUT_WIDE:
		case OP_SPUT_BOOLEAN:
		case OP_SPUT_BYTE:
		case OP_SPUT_CHAR:
		case OP_SPUT_SHORT:
			insnList.add(FieldFn.static_put(field, new RegValue(regFromOrTo)));
			break;
		default:
			throw new RuntimeException(String.format("Not support Opcode:[0x%04x]=%s yet!", opcode, DexOpcodeDump.dump(opcode)));

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.visitors.DexCodeVisitor#visitFillArrayInsn(int, int, int, int, java.lang.Object[])
	 */
	public void visitFillArrayInsn(int opcode, int reg, int elemWidth, int initLength, Object[] values) {
		insnList.add(reg, new FillArrayFn(new RegValue(reg), values, elemWidth));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.visitors.DexCodeVisitor#visitFilledNewArrayIns(int, java.lang.String, int[])
	 */
	public void visitFilledNewArrayIns(int opcode, String type, int[] regs) {
		// Type elem = Type.getType(type).getElementType();
		// int shortType = elem.getSort();
		// mv.visitLdcInsn(regs.length);
		// switch (shortType) {
		// case Type.BOOLEAN:
		// mv.visitIntInsn(NEWARRAY, T_BOOLEAN);
		// break;
		// case Type.BYTE:
		// mv.visitIntInsn(NEWARRAY, T_BYTE);
		// break;
		// case Type.CHAR:
		// mv.visitIntInsn(NEWARRAY, T_CHAR);
		// break;
		// case Type.DOUBLE:
		// mv.visitIntInsn(NEWARRAY, T_DOUBLE);
		// break;
		// case Type.FLOAT:
		// mv.visitIntInsn(NEWARRAY, T_FLOAT);
		// break;
		// case Type.INT:
		// mv.visitIntInsn(NEWARRAY, T_INT);
		// break;
		// case Type.OBJECT:
		// mv.visitTypeInsn(ANEWARRAY, type);
		// break;
		// }
		// int store = elem.getOpcode(IASTORE);
		// int load = elem.getOpcode(ILOAD);
		// for (int i = 0; i < regs.length; i++) {
		// mv.visitInsn(DUP);
		// mv.visitLdcInsn(i);
		// mv.visitVarInsn(load, map(regs[i]));
		// mv.visitInsn(store);
		// }
		// stack(4);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.visitors.DexCodeVisitor#visitInInsn(int, int, int)
	 */
	public void visitInInsn(int opcode, int saveToReg, int opReg) {
		switch (opcode) {

		case OP_ADD_INT_2ADDR:
		case OP_SUB_INT_2ADDR:
		case OP_MUL_INT_2ADDR:
		case OP_DIV_INT_2ADDR:
		case OP_REM_INT_2ADDR:
		case OP_AND_INT_2ADDR:
		case OP_OR_INT_2ADDR:
		case OP_XOR_INT_2ADDR:
		case OP_SHL_INT_2ADDR:
		case OP_SHR_INT_2ADDR:
		case OP_USHR_INT_2ADDR:
			insnList.add(saveToReg, new AsmdFn(Type.INT_TYPE, opcode - OP_ADD_INT_2ADDR, new RegValue(saveToReg), new RegValue(opReg)));
			break;
		case OP_ADD_LONG_2ADDR:
		case OP_SUB_LONG_2ADDR:
		case OP_MUL_LONG_2ADDR:
		case OP_DIV_LONG_2ADDR:
		case OP_REM_LONG_2ADDR:
		case OP_AND_LONG_2ADDR:
		case OP_OR_LONG_2ADDR:
		case OP_XOR_LONG_2ADDR:
		case OP_SHL_LONG_2ADDR:
		case OP_SHR_LONG_2ADDR:
		case OP_USHR_LONG_2ADDR:
			insnList.add(saveToReg, new AsmdFn(Type.LONG_TYPE, opcode - OP_ADD_LONG_2ADDR, new RegValue(saveToReg), new RegValue(opReg)));
			break;
		case OP_ADD_FLOAT_2ADDR:
		case OP_SUB_FLOAT_2ADDR:
		case OP_MUL_FLOAT_2ADDR:
		case OP_DIV_FLOAT_2ADDR:
		case OP_REM_FLOAT_2ADDR:
			insnList.add(saveToReg, new AsmdFn(Type.FLOAT_TYPE, opcode - OP_ADD_FLOAT_2ADDR, new RegValue(saveToReg), new RegValue(opReg)));

			break;
		case OP_ADD_DOUBLE_2ADDR:
		case OP_SUB_DOUBLE_2ADDR:
		case OP_MUL_DOUBLE_2ADDR:
		case OP_DIV_DOUBLE_2ADDR:
		case OP_REM_DOUBLE_2ADDR:
			insnList.add(saveToReg, new AsmdFn(Type.DOUBLE_TYPE, opcode - OP_ADD_DOUBLE_2ADDR, new RegValue(saveToReg), new RegValue(opReg)));
			break;
		case OP_NEG_INT:
		case OP_NEG_DOUBLE:
		case OP_NEG_FLOAT:
		case OP_NEG_LONG:
		case OP_INT_TO_BYTE:
		case OP_INT_TO_SHORT:
		case OP_INT_TO_CHAR:
		case OP_INT_TO_FLOAT:
		case OP_INT_TO_DOUBLE:
		case OP_INT_TO_LONG:
		case OP_LONG_TO_DOUBLE:
		case OP_LONG_TO_FLOAT:
		case OP_LONG_TO_INT:
		case OP_DOUBLE_TO_FLOAT:
		case OP_DOUBLE_TO_INT:
		case OP_DOUBLE_TO_LONG:
		case OP_FLOAT_TO_INT:
		case OP_FLOAT_TO_LONG:
		case OP_FLOAT_TO_DOUBLE:

			insnList.add(saveToReg, new OneValueFn(opcode, new RegValue(opReg)));

			break;
		case OP_ARRAY_LENGTH:
			insnList.add(saveToReg, new OneValueFn(opcode, new RegValue(opReg)));

			break;
		case OP_MOVE_OBJECT:
		case OP_MOVE_OBJECT_FROM16:
		case OP_MOVE:
		case OP_MOVE_WIDE:
		case OP_MOVE_FROM16:
		case OP_MOVE_WIDE_FROM16: {
			insnList.add(saveToReg, new RegValue(opReg));
		}
			break;
		default:
			throw new RuntimeException(String.format("Not support Opcode:[0x%04x]=%s yet!", opcode, DexOpcodeDump.dump(opcode)));

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.visitors.DexCodeVisitor#visitInInsn(int, int, int, int)
	 */
	public void visitInInsn(int opcode, int saveToReg, int opReg, int opValueOrReg) {
		switch (opcode) {
		case OP_ADD_INT_LIT8:
		case OP_RSUB_INT_LIT8:
		case OP_MUL_INT_LIT8:
		case OP_DIV_INT_LIT8:
		case OP_REM_INT_LIT8:
		case OP_AND_INT_LIT8:
		case OP_OR_INT_LIT8:
		case OP_XOR_INT_LIT8:
		case OP_SHL_INT_LIT8:
		case OP_SHR_INT_LIT8:
		case OP_USHR_INT_LIT8:
			insnList.add(saveToReg, new AsmdFn(Type.INT_TYPE, opcode - OP_ADD_INT_LIT8, new RegValue(opReg), new StaticValue(opValueOrReg)));
			break;

		case OP_ADD_INT_LIT16:
		case OP_RSUB_INT:
		case OP_MUL_INT_LIT16:
		case OP_DIV_INT_LIT16:
		case OP_REM_INT_LIT16:
		case OP_AND_INT_LIT16:
		case OP_OR_INT_LIT16:
		case OP_XOR_INT_LIT16:
			insnList.add(saveToReg, new AsmdFn(Type.INT_TYPE, opcode - OP_ADD_INT_LIT16, new RegValue(opReg), new StaticValue(opValueOrReg)));
			break;

		case OP_ADD_INT:
		case OP_SUB_INT:
		case OP_MUL_INT:
		case OP_DIV_INT:
		case OP_REM_INT:
		case OP_AND_INT:
		case OP_OR_INT:
		case OP_XOR_INT:
		case OP_SHL_INT:
		case OP_SHR_INT:
		case OP_USHR_INT:
			insnList.add(saveToReg, new AsmdFn(Type.INT_TYPE, opcode - OP_ADD_INT, new RegValue(opReg), new RegValue(opValueOrReg)));
			break;
		case OP_ADD_LONG:
		case OP_SUB_LONG:
		case OP_MUL_LONG:
		case OP_DIV_LONG:
		case OP_REM_LONG:
		case OP_AND_LONG:
		case OP_OR_LONG:
		case OP_XOR_LONG:
		case OP_SHL_LONG:
		case OP_SHR_LONG:
		case OP_USHR_LONG:
			insnList.add(saveToReg, new AsmdFn(Type.LONG_TYPE, opcode - OP_ADD_LONG, new RegValue(opReg), new RegValue(opValueOrReg)));
			break;
		case OP_ADD_FLOAT:
		case OP_SUB_FLOAT:
		case OP_MUL_FLOAT:
		case OP_DIV_FLOAT:
		case OP_REM_FLOAT:
			insnList.add(saveToReg, new AsmdFn(Type.FLOAT_TYPE, opcode - OP_ADD_FLOAT, new RegValue(opReg), new RegValue(opValueOrReg)));
			break;
		case OP_ADD_DOUBLE:
		case OP_SUB_DOUBLE:
		case OP_MUL_DOUBLE:
		case OP_DIV_DOUBLE:
		case OP_REM_DOUBLE:
			insnList.add(saveToReg, new AsmdFn(Type.DOUBLE_TYPE, opcode - OP_ADD_DOUBLE, new RegValue(opReg), new RegValue(opValueOrReg)));
			break;
		case OP_CMPL_FLOAT:
		case OP_CMPG_FLOAT:
		case OP_CMPL_DOUBLE:
		case OP_CMPG_DOUBLE:
		case OP_CMP_LONG:
			insnList.add(saveToReg, new CmpFn(opcode, new RegValue(opReg), new RegValue(opValueOrReg)));
			break;
		default:
			throw new RuntimeException(String.format("Not support Opcode:[0x%04x]=%s yet!", opcode, DexOpcodeDump.dump(opcode)));

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.visitors.DexCodeVisitor#visitInsn(int)
	 */
	public void visitInsn(int opcode) {
		switch (opcode) {
		case OP_RETURN_VOID:
			insnList.add(new EndFn(opcode, null));
			break;
		default:
			throw new RuntimeException(String.format("Not support Opcode:[0x%04x]=%s yet!", opcode, DexOpcodeDump.dump(opcode)));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.visitors.DexCodeVisitor#visitJumpInsn(int, int)
	 */
	public void visitJumpInsn(int opcode, Label label) {
		switch (opcode) {
		case OP_GOTO:
		case OP_GOTO_16: {
			insnList.add(new GotoFn(label));
		}
			break;
		default:
			throw new RuntimeException(String.format("Not support Opcode:[0x%04x]=%s yet!", opcode, DexOpcodeDump.dump(opcode)));

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.visitors.DexCodeVisitor#visitJumpInsn(int, int, int)
	 */
	public void visitJumpInsn(int opcode, Label label, int reg) {
		switch (opcode) {
		case OP_IF_NEZ:
		case OP_IF_EQZ:
		case OP_IF_GTZ:
		case OP_IF_GEZ:
		case OP_IF_LEZ:
		case OP_IF_LTZ:
			insnList.add(new JumpFn(opcode - 6, label, new RegValue(reg), new StaticValue(0)));
			break;
		default:
			throw new RuntimeException(String.format("Not support Opcode:[0x%04x]=%s yet!", opcode, DexOpcodeDump.dump(opcode)));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.visitors.DexCodeVisitor#visitJumpInsn(int, int, int, int)
	 */
	public void visitJumpInsn(int opcode, Label label, int reg1, int reg2) {
		insnList.add(new JumpFn(opcode, label, new RegValue(reg1), new RegValue(reg2)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.visitors.DexCodeVisitor#visitLabel(int)
	 */
	public void visitLabel(Label label) {
		insnList.add(new LabelFn(label));
		if (handlers.containsKey(label)) {
			stack_value = newReg();
			insnList.add(stack_value, new ExValue(handlers.get(label)));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.visitors.DexCodeVisitor#visitLdcInsn(int, java.lang.Object, int)
	 */
	public void visitLdcInsn(int opcode, Object value, int reg) {
		switch (opcode) {
		case OP_CONST_STRING:
		case OP_CONST_CLASS:
		case OP_CONST:
		case OP_CONST_4:
		case OP_CONST_16:
		case OP_CONST_HIGH16:
		case OP_CONST_WIDE:
		case OP_CONST_WIDE_16:
		case OP_CONST_WIDE_32:
		case OP_CONST_WIDE_HIGH16:
			insnList.add(reg, new StaticValue(value));
			break;
		default:
			throw new RuntimeException(String.format("Not support Opcode:[0x%04x]=%s yet!", opcode, DexOpcodeDump.dump(opcode)));

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.visitors.DexCodeVisitor#visitLineNumber(int, int)
	 */
	public void visitLineNumber(int line, Label label) {
		// mv.visitLineNumber(line, label);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.visitors.DexCodeVisitor#visitLocalVariable(java.lang .String, java.lang.String, java.lang.String, int, int, int)
	 */
	public void visitLocalVariable(String name, String type, String signature, Label start, Label end, int reg) {
		// mv.visitLocalVariable(name, type, signature, start, end, map(reg));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.visitors.DexCodeVisitor#visitLookupSwitchInsn(int, int, int, int[], int[])
	 */
	public void visitLookupSwitchInsn(int opcode, int reg, Label defaultOffset, int[] cases, Label[] labels) {
		switch (opcode) {
		case OP_SPARSE_SWITCH:
			insnList.add(new LookupSwitchFn(new RegValue(reg), defaultOffset, cases, labels));
			break;
		}
	}

	private int stack_value = -1;

	protected int newReg() {
		return ++_regcount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.visitors.DexCodeVisitor#visitMethodInsn(int, pxb.android.dex2jar.Method, int[])
	 */
	public void visitMethodInsn(int opcode, Method method, int[] args) {
		Type ret = Type.getType(method.getType().getReturnType());

		Value[] argValues = new Value[args.length];

		for (int i = 0; i < args.length; i++) {
			argValues[i] = new RegValue(args[i]);
		}
		MethodFn mfn = new MethodFn(opcode, method, argValues);
		if (method.getName().equals("<init>")) {
			if (NEW_INS.remove(args[0])) {
				insnList.add(args[0], mfn);
			} else {
				mfn.invorkSuper = true;
				insnList.add(mfn);
			}

		} else {
			if (!Type.VOID_TYPE.equals(ret)) {
				stack_value = newReg();
				insnList.add(stack_value, mfn);
			} else {
				insnList.add(mfn);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.visitors.DexCodeVisitor#visitTableSwitchInsn(int, int, int, int, int, int[])
	 */
	public void visitTableSwitchInsn(int opcode, int reg, int first_case, int last_case, Label default_label, Label[] labels) {
		switch (opcode) {
		case OP_PACKED_SWITCH:
			insnList.add(new TableSwitchFn(new RegValue(reg), first_case, last_case, default_label, labels));
			break;
		}
	}

	protected Map<Label, Type> handlers = new HashMap<Label, Type>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.visitors.DexCodeVisitor#visitTryCatch(int, int, int, java.lang.String)
	 */
	public void visitTryCatch(Label start, Label end, Label handler, String type) {
		// mv.visitTryCatchBlock(start, end, handler, type);
		if (type == null) {
			type = Type.getDescriptor(Throwable.class);
		}
		handlers.put(handler, Type.getType(type));
	}

	protected Set<Integer> NEW_INS = new HashSet<Integer>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.visitors.DexCodeVisitor#visitTypeInsn(int, java.lang.String, int)
	 */
	public void visitTypeInsn(int opcode, String type, int toReg) {
		switch (opcode) {
		case OP_NEW_INSTANCE:
			NEW_INS.add(toReg);
			break;
		case OP_CHECK_CAST: {
			insnList.add(toReg, new TypeFn(opcode, Type.getType(type), new RegValue(toReg)));
		}
			break;
		default:
			throw new RuntimeException(String.format("Not support Opcode:[0x%04x]=%s yet!", opcode, DexOpcodeDump.dump(opcode)));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.visitors.DexCodeVisitor#visitTypeInsn(int, java.lang.String, int, int)
	 */
	public void visitTypeInsn(int opcode, String type, int toReg, int fromReg) {
		switch (opcode) {
		case OP_INSTANCE_OF:
			insnList.add(toReg, new TypeFn(opcode, Type.getType(type), new RegValue(fromReg)));
			break;
		default:
			throw new RuntimeException(String.format("Not support Opcode:[0x%04x]=%s yet!", opcode, DexOpcodeDump.dump(opcode)));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pxb.android.dex2jar.visitors.DexCodeVisitor#visitVarInsn(int, int)
	 */
	public void visitVarInsn(int opcode, int reg) {
		switch (opcode) {
		case OP_MOVE_RESULT_OBJECT:// move-result-object
		case OP_MOVE_RESULT:
		case OP_MOVE_EXCEPTION:
		case OP_MOVE_RESULT_WIDE:
			insnList.add(reg, new RegValue(stack_value));
			break;
		case OP_THROW:
		case OP_RETURN:
		case OP_RETURN_OBJECT:
		case OP_RETURN_WIDE:
			insnList.add(new EndFn(opcode, new RegValue(reg)));
			break;
		case OP_MONITOR_ENTER:
		case OP_MONITOR_EXIT: {
			insnList.add(new SimpleFn(opcode, new RegValue(reg)));
		}
			break;
		default:
			throw new RuntimeException(String.format("Not support Opcode:[0x%04x]=%s yet!", opcode, DexOpcodeDump.dump(opcode)));
		}
	}

}
