/*
 * Copyright 2003-2009 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.jdon.jivejdon.util;

import java.util.concurrent.atomic.AtomicLong;

/**
 * public final class FalseSharing implements Runnable { private static
 * PaddedAtomicLong[] longs = new PaddedAtomicLong[NUM_THREADS]; static { for
 * (int i = 0; i < longs.length; i++) { longs[i] = new PaddedAtomicLong(); } }
 * 
 * @author banq
 * 
 */
public class PaddedAtomicLong extends AtomicLong {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public volatile long p1, p2, p3, p4, p5, p6 = 7L;

}
