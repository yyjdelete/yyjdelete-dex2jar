package com.googlecode.dex2jar.test;

import static com.googlecode.dex2jar.DexOpcodes.ACC_PUBLIC;
import static com.googlecode.dex2jar.DexOpcodes.ACC_STATIC;
import static com.googlecode.dex2jar.DexOpcodes.OP_AGET;
import static com.googlecode.dex2jar.DexOpcodes.OP_APUT;
import static com.googlecode.dex2jar.DexOpcodes.OP_ARRAY_LENGTH;
import static com.googlecode.dex2jar.DexOpcodes.OP_CMPL;
import static com.googlecode.dex2jar.DexOpcodes.OP_CONST;
import static com.googlecode.dex2jar.DexOpcodes.OP_GOTO;
import static com.googlecode.dex2jar.DexOpcodes.OP_IF_LTZ;
import static com.googlecode.dex2jar.DexOpcodes.OP_INVOKE_VIRTUAL;
import static com.googlecode.dex2jar.DexOpcodes.OP_NEW_ARRAY;
import static com.googlecode.dex2jar.DexOpcodes.OP_RETURN_VOID;
import static com.googlecode.dex2jar.DexOpcodes.TYPE_OBJECT;
import static com.googlecode.dex2jar.DexOpcodes.TYPE_INT;
import static com.googlecode.dex2jar.DexOpcodes.TYPE_SINGLE;
import static com.googlecode.dex2jar.DexOpcodes.TYPE_WIDE;
import static com.googlecode.dex2jar.DexOpcodes.TYPE_FLOAT;
import static com.googlecode.dex2jar.DexOpcodes.TYPE_DOUBLE;

import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.analysis.AnalyzerException;

import com.googlecode.dex2jar.DexLabel;
import com.googlecode.dex2jar.Method;
import com.googlecode.dex2jar.v3.V3;
import com.googlecode.dex2jar.visitors.DexClassVisitor;
import com.googlecode.dex2jar.visitors.DexCodeVisitor;
import com.googlecode.dex2jar.visitors.DexMethodVisitor;

public class ArrayTypeTest {

    public static void a120(DexClassVisitor cv) {
        DexMethodVisitor mv = cv.visitMethod(ACC_PUBLIC | ACC_STATIC, new Method("La;", "b", new String[] {}, "V"));
        DexCodeVisitor code = mv.visitCode();
        code.visitArguments(3, new int[] {});
        code.visitConstStmt(OP_CONST, 0, Integer.valueOf(0), TYPE_SINGLE);
        code.visitMethodStmt(OP_INVOKE_VIRTUAL, new int[] { 0 }, new Method("Ljava/lang/String;", "toString",
                new String[] {}, "Ljava/lang/String;"));
        code.visitConstStmt(OP_CONST, 1, Integer.valueOf(0), TYPE_SINGLE);
        code.visitUnopStmt(OP_ARRAY_LENGTH, 2, 1, TYPE_INT);
        code.visitReturnStmt(OP_RETURN_VOID);
        code.visitEnd();
        mv.visitEnd();
    }

    public static void a122(DexClassVisitor cv) {
        DexMethodVisitor mv = cv.visitMethod(ACC_PUBLIC | ACC_STATIC, new Method("La;", "b", new String[] {}, "V"));
        DexCodeVisitor code = mv.visitCode();
        code.visitArguments(3, new int[] {});
        code.visitConstStmt(OP_CONST, 0, Integer.valueOf(0), TYPE_SINGLE);
        code.visitConstStmt(OP_CONST, 2, Integer.valueOf(1), TYPE_SINGLE);
        code.visitArrayStmt(OP_AGET, 1, 0, 2, TYPE_SINGLE);
        code.visitReturnStmt(OP_RETURN_VOID);
        code.visitEnd();
        mv.visitEnd();
    }

    public static void a123(DexClassVisitor cv) {
        DexMethodVisitor mv = cv.visitMethod(ACC_PUBLIC | ACC_STATIC, new Method("La;", "b", new String[] {}, "V"));
        DexCodeVisitor code = mv.visitCode();
        code.visitArguments(3, new int[] {});
        code.visitConstStmt(OP_CONST, 0, 0, TYPE_SINGLE);
        code.visitConstStmt(OP_CONST, 1, 1, TYPE_SINGLE);
        code.visitConstStmt(OP_CONST, 2, 0x63, TYPE_SINGLE);
        code.visitArrayStmt(OP_APUT, 2, 0, 1, TYPE_SINGLE);
        code.visitReturnStmt(OP_RETURN_VOID);
        code.visitEnd();
        mv.visitEnd();
    }

    public static void mergeA(DexClassVisitor cv) {//obj = array
        DexMethodVisitor mv = cv.visitMethod(ACC_PUBLIC | ACC_STATIC, new Method("La;", "b", new String[] {}, "V"));
        DexCodeVisitor code = mv.visitCode();
    	DexLabel L0 = new DexLabel();
    	DexLabel L1 = new DexLabel();
    	code.visitArguments(3,new int[]{});
        code.visitConstStmt(OP_CONST, 0, 0, TYPE_SINGLE);
    	code.visitJumpStmt(OP_GOTO,L1);
    	code.visitLabel(L0);
    	code.visitUnopStmt(OP_ARRAY_LENGTH, 1, 0, TYPE_INT);
        code.visitConstStmt(OP_CONST, 1, 0, TYPE_SINGLE);
        code.visitArrayStmt(OP_AGET, 2, 0, 1, TYPE_OBJECT);
        code.visitReturnStmt(OP_RETURN_VOID);
    	code.visitLabel(L1);
        code.visitConstStmt(OP_CONST, 1, 1, TYPE_SINGLE);
    	code.visitClassStmt(OP_NEW_ARRAY,0,1,"[Ljava/security/cert/X509Certificate;");
    	code.visitJumpStmt(OP_GOTO,L0);
    	code.visitEnd();
    	mv.visitEnd();
    }

    public static void mergeD(DexClassVisitor cv) {
        DexMethodVisitor mv = cv.visitMethod(ACC_PUBLIC | ACC_STATIC, new Method("La;", "b", new String[] {}, "V"));
        DexCodeVisitor code=mv.visitCode();
        DexLabel L0=new DexLabel();
        code.visitArguments(5,new int[]{});
        code.visitConstStmt(OP_CONST, 4, Integer.valueOf(1), TYPE_SINGLE);// v4 = 1;
        code.visitConstStmt(OP_CONST, 3, Integer.valueOf(0), TYPE_SINGLE);// v3 = 0;
        code.visitConstStmt(OP_CONST, 0, Integer.valueOf(0), TYPE_SINGLE);// v0 = null;
        code.visitArrayStmt(OP_AGET, 1, 0, 3, TYPE_WIDE);// v1 = v0[v3];
        code.visitArrayStmt(OP_AGET, 2, 0, 4, TYPE_WIDE);// v2 = v0[v4];
        code.visitCmpStmt(OP_CMPL,1,1,2,TYPE_DOUBLE);
        code.visitJumpStmt(OP_IF_LTZ,1,L0);
        code.visitConstStmt(OP_CONST,1,Long.valueOf(4607182418800017408L),TYPE_WIDE); // long: 0x3ff0000000000000  double:1.000000
        code.visitArrayStmt(OP_APUT,1,0,3,TYPE_WIDE);// v0[v3] = v1;
        code.visitLabel(L0);
        code.visitReturnStmt(OP_RETURN_VOID);
        code.visitEnd();
        mv.visitEnd();
    }

    public static void mergeF(DexClassVisitor cv) {
        DexMethodVisitor mv = cv.visitMethod(ACC_PUBLIC | ACC_STATIC, new Method("La;", "b", new String[] {}, "V"));
        DexCodeVisitor code=mv.visitCode();
        DexLabel L0=new DexLabel();
        code.visitArguments(5,new int[]{});
        code.visitConstStmt(OP_CONST, 4, Integer.valueOf(1), TYPE_SINGLE);// v4 = 1;
        code.visitConstStmt(OP_CONST, 3, Integer.valueOf(0), TYPE_SINGLE);// v3 = 0;
        code.visitConstStmt(OP_CONST, 0, Integer.valueOf(0), TYPE_SINGLE);// v0 = null;
        code.visitArrayStmt(OP_AGET, 1, 0, 3, TYPE_SINGLE);// v1 = v0[v3];
        code.visitArrayStmt(OP_AGET, 2, 0, 4, TYPE_SINGLE);// v2 = v0[v4];
        code.visitCmpStmt(OP_CMPL,1,1,2,TYPE_FLOAT);
        code.visitJumpStmt(OP_IF_LTZ,1,L0);
        code.visitConstStmt(OP_CONST,1, Integer.valueOf(1065353216),TYPE_SINGLE); // int: 0x3f800000  float:1.000000
        code.visitArrayStmt(OP_APUT,1,0,3,TYPE_SINGLE);// v0[v3] = v1;
        code.visitLabel(L0);
        code.visitReturnStmt(OP_RETURN_VOID);
        code.visitEnd();
        mv.visitEnd();
    }

    public static void mergeF2(DexClassVisitor cv) {//float[][]
        DexMethodVisitor mv = cv.visitMethod(ACC_PUBLIC | ACC_STATIC, new Method("La;", "b", new String[] {}, "V"));
        DexCodeVisitor code=mv.visitCode();
        DexLabel L0=new DexLabel();
        code.visitArguments(6,new int[]{});
        code.visitConstStmt(OP_CONST, 4, Integer.valueOf(1), TYPE_SINGLE);// v4 = 1;
        code.visitConstStmt(OP_CONST, 3, Integer.valueOf(0), TYPE_SINGLE);// v3 = 0;
        code.visitConstStmt(OP_CONST, 0, Integer.valueOf(0), TYPE_SINGLE);// v0 = null;
        code.visitArrayStmt(OP_AGET, 5, 0, 3, TYPE_OBJECT);// v5 = v0[v3];
        code.visitArrayStmt(OP_AGET, 1, 5, 3, TYPE_SINGLE);// v1 = v5[v3];
        code.visitArrayStmt(OP_AGET, 5, 0, 3, TYPE_OBJECT);// v5 = v0[v3];
        code.visitArrayStmt(OP_AGET, 2, 5, 4, TYPE_SINGLE);// v2 = v5[v4];
        code.visitCmpStmt(OP_CMPL,1,1,2,TYPE_FLOAT);
        code.visitJumpStmt(OP_IF_LTZ,1,L0);
        code.visitConstStmt(OP_CONST,1, Integer.valueOf(1065353216),TYPE_SINGLE); // int: 0x3f800000  float:1.000000
        code.visitArrayStmt(OP_AGET, 5, 0, 4, TYPE_OBJECT);// v5 = v0[v4];
        code.visitArrayStmt(OP_APUT,1,5,3,TYPE_SINGLE);// v5[v3] = v1;
        code.visitLabel(L0);
        code.visitReturnStmt(OP_RETURN_VOID);
        code.visitEnd();
        mv.visitEnd();
    }

    public static void mergeD2(DexClassVisitor cv) {//double[][]
        DexMethodVisitor mv = cv.visitMethod(ACC_PUBLIC | ACC_STATIC, new Method("La;", "b", new String[] {}, "V"));
        DexCodeVisitor code=mv.visitCode();
        DexLabel L0=new DexLabel();
        code.visitArguments(6,new int[]{});
        code.visitConstStmt(OP_CONST, 4, Integer.valueOf(1), TYPE_SINGLE);// v4 = 1;
        code.visitConstStmt(OP_CONST, 3, Integer.valueOf(0), TYPE_SINGLE);// v3 = 0;
        code.visitConstStmt(OP_CONST, 0, Integer.valueOf(0), TYPE_SINGLE);// v0 = null;
        code.visitArrayStmt(OP_AGET, 5, 0, 3, TYPE_OBJECT);// v5 = v0[v3];
        code.visitArrayStmt(OP_AGET, 1, 5, 3, TYPE_WIDE);// v1 = v5[v3];
        code.visitArrayStmt(OP_AGET, 5, 0, 3, TYPE_OBJECT);// v5 = v0[v3];
        code.visitArrayStmt(OP_AGET, 2, 5, 4, TYPE_WIDE);// v2 = v5[v4];
        code.visitCmpStmt(OP_CMPL,1,1,2,TYPE_DOUBLE);
        code.visitJumpStmt(OP_IF_LTZ,1,L0);
        code.visitConstStmt(OP_CONST,1,Long.valueOf(4607182418800017408L),TYPE_WIDE); // long: 0x3ff0000000000000  double:1.000000
        code.visitArrayStmt(OP_AGET, 5, 0, 4, TYPE_OBJECT);// v5 = v0[v4];
        code.visitArrayStmt(OP_APUT,1,5,3,TYPE_WIDE);// v5[v3] = v1;
        code.visitLabel(L0);
        code.visitReturnStmt(OP_RETURN_VOID);
        code.visitEnd();
        mv.visitEnd();
    }

    @Test
    public void test120() throws IllegalArgumentException, IllegalAccessException, AnalyzerException {
        TestDexClassV cv = new TestDexClassV("Lt", V3.OPTIMIZE_SYNCHRONIZED | V3.TOPOLOGICAL_SORT);
        a120(cv);
        ClassReader cr = new ClassReader(cv.toByteArray());
        TestUtils.verify(cr);
    }

    @Test
    public void test122() throws IllegalArgumentException, IllegalAccessException, AnalyzerException {
        TestDexClassV cv = new TestDexClassV("Lt", V3.OPTIMIZE_SYNCHRONIZED | V3.TOPOLOGICAL_SORT);
        a122(cv);
        ClassReader cr = new ClassReader(cv.toByteArray());
        TestUtils.verify(cr);
    }

    @Test
    public void test123() throws IllegalArgumentException, IllegalAccessException, AnalyzerException {
        TestDexClassV cv = new TestDexClassV("Lt", V3.OPTIMIZE_SYNCHRONIZED | V3.TOPOLOGICAL_SORT);
        a123(cv);
        ClassReader cr = new ClassReader(cv.toByteArray());
        TestUtils.verify(cr);
    }

    @Test
    public void testMergeA() throws IllegalArgumentException, IllegalAccessException, AnalyzerException {
        TestDexClassV cv = new TestDexClassV("Lt", V3.OPTIMIZE_SYNCHRONIZED | V3.TOPOLOGICAL_SORT);
        mergeA(cv);
        ClassReader cr = new ClassReader(cv.toByteArray());
        TestUtils.verify(cr);
    }

    @Test
    public void testMergeF() throws IllegalArgumentException, IllegalAccessException, AnalyzerException {
        TestDexClassV cv = new TestDexClassV("Lt", V3.OPTIMIZE_SYNCHRONIZED | V3.TOPOLOGICAL_SORT);
        mergeF(cv);
        ClassReader cr = new ClassReader(cv.toByteArray());
        TestUtils.verify(cr);
    }

    //FIXME: still some error in ZeroTransformer and LocalRemove, Object null needn't be removed as below.
    //v0=null;v0[1]=0;v0[0]=1;->null[1]=0;null[0]=1;
    //@Test
    public void testMergeF2() throws IllegalArgumentException, IllegalAccessException, AnalyzerException {
        TestDexClassV cv = new TestDexClassV("Lt", V3.OPTIMIZE_SYNCHRONIZED | V3.TOPOLOGICAL_SORT);
        mergeF2(cv);
        ClassReader cr = new ClassReader(cv.toByteArray());
        TestUtils.verify(cr);
    }

    @Test
    public void testMergeD() throws IllegalArgumentException, IllegalAccessException, AnalyzerException {
        TestDexClassV cv = new TestDexClassV("Lt", V3.OPTIMIZE_SYNCHRONIZED | V3.TOPOLOGICAL_SORT);
        mergeD(cv);
        ClassReader cr = new ClassReader(cv.toByteArray());
        TestUtils.verify(cr);
    }

    //FIXME: still some error in ZeroTransformer and LocalRemove, Object null needn't be removed as below.
    //v0=null;v0[1]=0;v0[0]=1;->null[1]=0;null[0]=1;
    //@Test
    public void testMergeD2() throws IllegalArgumentException, IllegalAccessException, AnalyzerException {
        TestDexClassV cv = new TestDexClassV("Lt", V3.OPTIMIZE_SYNCHRONIZED | V3.TOPOLOGICAL_SORT);
        mergeD2(cv);
        ClassReader cr = new ClassReader(cv.toByteArray());
        TestUtils.verify(cr);
    }
}