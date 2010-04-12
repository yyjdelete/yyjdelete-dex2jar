/**
 * 
 */
package pxb.android;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import pxb.android.dex2jar.reader.DexFileReader;
import pxb.android.dex2jar.v3.V3AccessFlagsAdapter;
import pxb.android.dex2jar.v4.V4CodeAdapter;
import pxb.android.dex2jar.visitors.DexCodeVisitor;
import pxb.android.dex2jar.visitors.EmptyVisitor;

/**
 * @author Panxiaobo
 * 
 */
public class V4Test {
	@Test
	public void test() throws IOException {
		File file = new File("target/test-classes/dexes");
		Iterator it = FileUtils.iterateFiles(file, new String[] { "dex" }, false);
		while (it.hasNext()) {
			File f = (File) it.next();
			byte[] data = FileUtils.readFileToByteArray(f);
			DexFileReader reader = new DexFileReader(data);
			V3AccessFlagsAdapter afa = new V3AccessFlagsAdapter();
			reader.accept(afa);
			reader.accept(new EmptyVisitor() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see pxb.android.dex2jar.visitors.EmptyVisitor#visitCode()
				 */
				@Override
				public DexCodeVisitor visitCode() {
					return new V4CodeAdapter();
				}

			});
		}
	}
}