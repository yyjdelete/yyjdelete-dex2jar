/**
 * 
 */
package pxb.android;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pxb.android.dex2jar.v4.Main;

/**
 * @author Panxiaobo
 * 
 */
public class V4Test {
	static final Logger log = LoggerFactory.getLogger(V4Test.class);

	@SuppressWarnings("unchecked")
	@Test
	public void test() throws IOException {
		File file = new File("target/test-classes/dexes");
		Iterator it = FileUtils.iterateFiles(file, new String[] { "dex" }, false);
		while (it.hasNext()) {
			File f = (File) it.next();
			log.info("dex2jar file {}", f);
			Main.doFile(f);
		}
	}
}