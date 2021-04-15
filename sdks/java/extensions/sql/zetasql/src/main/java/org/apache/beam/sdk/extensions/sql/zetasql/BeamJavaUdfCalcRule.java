/*
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
package org.apache.beam.sdk.extensions.sql.zetasql;

import org.apache.beam.sdk.extensions.sql.impl.rel.BeamCalcRel;
import org.apache.beam.sdk.extensions.sql.impl.rel.CalcRelSplitter;
import org.apache.beam.sdk.extensions.sql.impl.rule.BeamCalcRule;
import org.apache.beam.sdk.extensions.sql.impl.rule.BeamCalcSplittingRule;
import org.apache.beam.vendor.calcite.v1_20_0.org.apache.calcite.rel.core.Calc;

/**
 * A {@link BeamCalcSplittingRule} to replace {@link Calc} with {@link BeamCalcRel}.
 *
 * <p>Equivalent to {@link BeamCalcRule} but with added type restrictions for ZetaSQL.
 */
public class BeamJavaUdfCalcRule extends BeamCalcSplittingRule {
  public static final BeamJavaUdfCalcRule INSTANCE = new BeamJavaUdfCalcRule();

  private BeamJavaUdfCalcRule() {
    super("BeamJavaUdfCalcRule");
  }

  @Override
  protected CalcRelSplitter.RelType[] getRelTypes() {
    // "Split" the Calc between two identical RelTypes. The second one is just a placeholder; if the
    // first isn't usable, the second one won't be usable either, and the planner will fail.
    return new CalcRelSplitter.RelType[] {
      new BeamCalcRelType("BeamCalcRelType"), new BeamCalcRelType("BeamCalcRelType2")
    };
  }
}
