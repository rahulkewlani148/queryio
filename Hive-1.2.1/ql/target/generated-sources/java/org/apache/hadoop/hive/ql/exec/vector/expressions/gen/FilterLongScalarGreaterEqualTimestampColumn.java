/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 
package org.apache.hadoop.hive.ql.exec.vector.expressions.gen;

import org.apache.hadoop.hive.ql.exec.vector.TimestampUtils;

import org.apache.hadoop.hive.ql.exec.vector.VectorExpressionDescriptor;

/**
 * Generated from template FilterScalarCompareTimestampColumn.txt, which covers comparison 
 * expressions between a long or double scalar and a column, however output is not produced in a separate column. 
 * The selected vector of the input {@link VectorizedRowBatch} is updated for in-place filtering.
 * Note: For timestamp and long or double we implicitly interpret the long as the number
 * of seconds or double as seconds and fraction since the epoch.
 */
public class FilterLongScalarGreaterEqualTimestampColumn extends FilterLongScalarGreaterEqualLongColumn {

  public FilterLongScalarGreaterEqualTimestampColumn(long value, int colNum) { 
    super(TimestampUtils.secondsToNanoseconds(value), colNum);
  }

  public FilterLongScalarGreaterEqualTimestampColumn() {
    super();
  }

  @Override
  public VectorExpressionDescriptor.Descriptor getDescriptor() {
    return (new VectorExpressionDescriptor.Builder())
        .setMode(
            VectorExpressionDescriptor.Mode.FILTER)
        .setNumArguments(2)
        .setArgumentTypes(
            VectorExpressionDescriptor.ArgumentType.getType("long"),
            VectorExpressionDescriptor.ArgumentType.getType("timestamp"))
        .setInputExpressionTypes(
            VectorExpressionDescriptor.InputExpressionType.SCALAR,
            VectorExpressionDescriptor.InputExpressionType.COLUMN).build();
  }
}
