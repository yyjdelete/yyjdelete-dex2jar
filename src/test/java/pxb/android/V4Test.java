/**
 * 
 */
package pxb.android;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import pxb.android.dex2jar.Method;
import pxb.android.dex2jar.reader.DexFileReader;
import pxb.android.dex2jar.v4.node.DexCodeNode;
import pxb.android.dex2jar.v4.optimize.A;
import pxb.android.dex2jar.visitors.DexCodeVisitor;
import pxb.android.dex2jar.visitors.DexMethodVisitor;
import pxb.android.dex2jar.visitors.EmptyVisitor;

/**
 * @author Panxiaobo
 * 
 */
public class V4Test {
	@SuppressWarnings("unchecked")
	@Test
	public void test() throws IOException {
		File file = new File("target/test-classes/dexes");
		Iterator it = FileUtils.iterateFiles(file, new String[] { "dex" }, false);
		while (it.hasNext()) {
			File f = (File) it.next();
			byte[] data = FileUtils.readFileToByteArray(f);
			DexFileReader reader = new DexFileReader(data);
			reader.accept(new EmptyVisitor() {
				Method method;

				@Override
				public DexMethodVisitor visitMethod(Method method) {
					this.method = method;
					return super.visitMethod(method);
				}

				@Override
				public DexCodeVisitor visitCode() {
					return new DexCodeNode() {
						public void visitEnd() {
							super.visitEnd();
							new A().a(this);
//							new Optimizer(method, this.insnList).optimize();
						}
					};
				}

			});
		}
	}
}