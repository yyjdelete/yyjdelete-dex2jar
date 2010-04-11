/**
 * 
 */
package pxb.android.dex2jar.v4.tree;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

/**
 * @author Panxiaobo
 * 
 */
public interface Value {

	/**
	 * 
	 * @param type
	 *            value的推荐返回值,可能为null
	 * @param mv
	 */
	void accept(Type suggest, MethodVisitor mv);
}
