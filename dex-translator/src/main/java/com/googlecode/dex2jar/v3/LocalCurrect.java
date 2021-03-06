/*
 * Copyright (c) 2009-2012 Panxiaobo
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
package com.googlecode.dex2jar.v3;

import org.objectweb.asm.Type;

import com.googlecode.dex2jar.ir.Constant;
import com.googlecode.dex2jar.ir.IrMethod;
import com.googlecode.dex2jar.ir.Value;
import com.googlecode.dex2jar.ir.Value.E1Expr;
import com.googlecode.dex2jar.ir.Value.E2Expr;
import com.googlecode.dex2jar.ir.Value.EnExpr;
import com.googlecode.dex2jar.ir.Value.VT;
import com.googlecode.dex2jar.ir.ValueBox;
import com.googlecode.dex2jar.ir.stmt.Stmt;
import com.googlecode.dex2jar.ir.stmt.Stmt.E1Stmt;
import com.googlecode.dex2jar.ir.stmt.Stmt.E2Stmt;
import com.googlecode.dex2jar.ir.ts.LocalType;
import com.googlecode.dex2jar.ir.ts.Transformer;

/**
 * @author <a href="mailto:pxb1988@gmail.com">Panxiaobo</a>
 * @version $Rev$
 */
public class LocalCurrect implements Transformer {

    @Override
    public void transform(IrMethod irMethod) {
        // 1. search for arrays
        for (Stmt st : irMethod.stmts) {
            switch (st.et) {
            case E0:
            case En:
                break;
            case E1:
                E1Stmt s1 = (E1Stmt) st;
                currectArrayInExpr(s1.op);
                break;
            case E2:
                E2Stmt s2 = (E2Stmt) st;
                if (s2.op1.value.vt == VT.ARRAY) {
                    E2Expr array = (E2Expr) s2.op1.value;
                    detectArray(array);
                }
                currectArrayInExpr(s2.op1);
                currectArrayInExpr(s2.op2);
                break;
            }
        }
        // 2. search for constants
        for (Stmt st : irMethod.stmts) {
            switch (st.et) {
            case E0:
            case En:
                break;
            case E1:
                E1Stmt s1 = (E1Stmt) st;
                currectCstInExpr(s1.op);
                break;
            case E2:
                E2Stmt s2 = (E2Stmt) st;
                currectCstInExpr(s2.op1);
                currectCstInExpr(s2.op2);
                break;
            }
        }

    }

    private void currectArrayInExpr(ValueBox vb) {
        if (vb == null) {
            return;
        }
        Value value = vb.value;
        switch (value.et) {
        case E0:
            break;
        case E1:
            currectArrayInExpr(((E1Expr) value).op);
            break;
        case E2:
            E2Expr e2 = (E2Expr) value;
            if (e2.vt == VT.ARRAY) {
                detectArray(e2);
            }

            currectArrayInExpr(e2.op1);
            currectArrayInExpr(e2.op2);
            break;
        case En:
            EnExpr en = (EnExpr) value;
            for (ValueBox op : en.ops) {
                currectArrayInExpr(op);
            }
            break;
        }
    }

    private Type detectArray(E2Expr e2) {
        Type t1 = LocalType.typeOf(e2);
        Type t2 = LocalType.typeOf(e2.op1.value);
        if (t2 == null) {
            if (e2.op1.value.vt == VT.ARRAY) {
                Type t3 = detectArray((E2Expr) e2.op1.value);
                if (t3 != null) {
                    if (t3.getSort() == Type.ARRAY) {
                        Type t4 = Type.getType(t3.getDescriptor().substring(1));
                        LocalType.type(e2, t4);
                        return t4;
                    }
                }
            }
        }
        if (t2 != null && t2.getSort() == Type.ARRAY) {
            Type nT1 = Type.getType(t2.getDescriptor().substring(1));
            if (!nT1.equals(t1)) {
                LocalType.type(e2, nT1);
                return nT1;
            }
        }
        return t1;
    }

    private void currectCstInExpr(ValueBox vb) {
        if (vb == null) {
            return;
        }
        Value value = vb.value;
        switch (value.et) {
        case E0:
            if (value.vt == VT.CONSTANT) {
                Constant cstExpr = ((Constant) value);
                Type type = LocalType.typeOf(value);
                if (type == null) {
                    // issue 71, if the type not detected, use type in constant
                    type = cstExpr.type;
                    if (type == null) {
                        type = Type.INT_TYPE;
                    }
                    LocalType.type(value, type);
                }
                Object cst = cstExpr.value;
                switch (type.getSort()) {
                case Type.INT:
                case Type.BYTE:
                case Type.CHAR:
                case Type.SHORT:
                case Type.LONG:
                case Type.BOOLEAN:
                    break;
                case Type.FLOAT:
                    if (cst instanceof Integer) {
                        cstExpr.value = Float.intBitsToFloat(((Integer) cst).intValue());
                    }
                    break;

                case Type.DOUBLE:
                    if (cst instanceof Long) {
                        cstExpr.value = Double.longBitsToDouble(((Long) cst).longValue());
                    }
                    break;
                default:// null or class
                    if (cst instanceof Integer && ((Integer) cst).intValue() == 0) {// null
                        cstExpr.value = Constant.Null;
                    }
                    break;
                }
            }
            break;
        case E1:
            currectCstInExpr(((E1Expr) value).op);
            break;
        case E2:
            E2Expr e2 = (E2Expr) value;
            currectCstInExpr(e2.op1);
            currectCstInExpr(e2.op2);
            break;
        case En:
            EnExpr en = (EnExpr) value;
            for (ValueBox op : en.ops) {
                currectCstInExpr(op);
            }
            break;
        }
    }
}
