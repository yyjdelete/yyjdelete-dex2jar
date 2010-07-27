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
package pxb.android.dex2jar;

/**
 * 输入流
 * 
 * @author Panxiaobo [pxb1988@126.com]
 * @version $Id$
 */
public interface DataIn {

    /**
     * 读取一个int,4位
     * @return
     */
    public int readIntx();

    /**
     * 读取一个long,8位
     * @return
     */
    public long readLongx();

    /**
     * 读取一个short,2位
     * @return
     */
    public short readShortx();

    /**
     * 移动当前指针到偏移量
     * @param offset 偏移量
     */
    public void move(int offset);

    /**
     * 将当前指针压栈，并移动到偏移量，等价于
     * push();
     * move(offset);
     * @param offset
     */
    public void pushMove(int offset);

    /**
     * 将当前指针压栈
     */
    public void push();

    /**
     * 从栈中弹出指针位置，并移动到该位置
     */
    public void pop();

    /**
     * 读取字节数组
     * @param size 当前位置开始的字节长度
     * @return
     */
    byte[] readBytes(int size);

    long readUnsignedLeb128();

    long readSignedLeb128();

    /**
     * 读取一个无符号Byte,1位
     * @return
     */
    public int readUnsignedByte();

    public boolean needPadding();

    /**
     *读取一个Byte,1位
     */
    public int readByte();

    /**
     * 跳过字节
     * @param i 字节数
     */
    public void skip(int bytes);
}
