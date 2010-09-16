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
package pxb.android.dex2jar.reader;

/**
 * @author Panxiaobo
 * 
 */
interface DexInternalOpcode {

	public static final int OP_UNUSED_3e = 62;
	public static final int OP_UNUSED_3f = 63;
	public static final int OP_UNUSED_40 = 64;
	public static final int OP_UNUSED_41 = 65;
	public static final int OP_UNUSED_42 = 66;
	public static final int OP_UNUSED_43 = 67;

	public static final int OP_UNUSED_73 = 115;
	public static final int OP_INVOKE_VIRTUAL_RANGE = 116;
	public static final int OP_INVOKE_SUPER_RANGE = 117;
	public static final int OP_INVOKE_DIRECT_RANGE = 118;
	public static final int OP_INVOKE_STATIC_RANGE = 119;
	public static final int OP_INVOKE_INTERFACE_RANGE = 120;
	public static final int OP_UNUSED_79 = 121;
	public static final int OP_UNUSED_7A = 122;

	public static final int OP_ADD_INT_LIT16 = 208;
	public static final int OP_RSUB_INT = 209;
	public static final int OP_MUL_INT_LIT16 = 210;
	public static final int OP_DIV_INT_LIT16 = 211;
	public static final int OP_REM_INT_LIT16 = 212;
	public static final int OP_AND_INT_LIT16 = 213;
	public static final int OP_OR_INT_LIT16 = 214;
	public static final int OP_XOR_INT_LIT16 = 215;
	public static final int OP_UNUSED_E3 = 227;
	public static final int OP_UNUSED_E4 = 228;
	public static final int OP_UNUSED_E5 = 229;
	public static final int OP_UNUSED_E6 = 230;
	public static final int OP_UNUSED_E7 = 231;
	public static final int OP_UNUSED_E8 = 232;
	public static final int OP_UNUSED_E9 = 233;
	public static final int OP_UNUSED_EA = 234;
	public static final int OP_UNUSED_EB = 235;
	public static final int OP_UNUSED_EC = 236;
	public static final int OP_UNUSED_ED = 237;

	public static final int OP_UNUSED_EF = 239;

	public static final int OP_INVOKE_DIRECT_EMPTY = 240;
	public static final int OP_UNUSED_F1 = 241;
	public static final int OP_IGET_QUICK = 242;
	public static final int OP_IGET_WIDE_QUICK = 243;
	public static final int OP_IGET_OBJECT_QUICK = 244;
	public static final int OP_IPUT_QUICK = 245;
	public static final int OP_IPUT_WIDE_QUICK = 246;
	public static final int OP_IPUT_OBJECT_QUICK = 247;
	public static final int OP_INVOKE_VIRTUAL_QUICK = 248;
	public static final int OP_INVOKE_VIRTUAL_QUICK_RANGE = 249;
	public static final int OP_INVOKE_SUPER_QUICK = 250;
	public static final int OP_INVOKE_SUPER_QUICK_RANGE = 251;
	public static final int OP_UNUSED_FC = 252;
	public static final int OP_UNUSED_FD = 253;
	public static final int OP_UNUSED_FE = 254;
	public static final int OP_UNUSED_FF = 255;
	
}
