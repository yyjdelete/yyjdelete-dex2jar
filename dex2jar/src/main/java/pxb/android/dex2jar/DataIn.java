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
package pxb.android.dex2jar;

/**
 * 输入流
 * 
 * @author Panxiaobo [pxb1988@126.com]
 * @version $Id$
 */
public interface DataIn {

	public int readIntx();

	public long readLongx();

	public short readShortx();

	public void move(int offset);

	public void pushMove(int offset);

	public void push();

	public void pop();

	byte[] readBytes(int size);

	long readUnsignedLeb128();

	long readSignedLeb128();

	/**
	 * @return
	 */
	public int readUnsignedByte();

	public boolean needPadding();

	/**
	 * 
	 */
	public int readByte();

	/**
	 * @param i
	 */
	public void skip(int bytes);
}