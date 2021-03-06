/*
 * Copyright (c) 2014, Cloudera and Intel, Inc. All Rights Reserved.
 *
 * Cloudera, Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"). You may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * This software is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for
 * the specific language governing permissions and limitations under the
 * License.
 */

package com.cloudera.oryx.app.serving.kmeans;

import javax.annotation.PostConstruct;

import com.cloudera.oryx.app.schema.InputSchema;
import com.cloudera.oryx.app.serving.AbstractOryxResource;
import com.cloudera.oryx.app.serving.kmeans.model.KMeansServingModel;
import com.cloudera.oryx.common.collection.Pair;

public abstract class AbstractKMeansResource extends AbstractOryxResource {

  private KMeansServingModel kmeansModel;

  @Override
  @PostConstruct
  public void init() {
    super.init();
    kmeansModel = (KMeansServingModel) getServingModelManager().getModel();
  }

  final KMeansServingModel getKMeansModel() {
    return kmeansModel;
  }

  Pair<Integer,Double> cluster(String[] data) {
    InputSchema inputSchema = kmeansModel.getInputSchema();
    double[] features = new double[inputSchema.getNumPredictors()];
    for (int featureIndex = 0; featureIndex < data.length; featureIndex++) {
      if (inputSchema.isActive(featureIndex)) {
        features[inputSchema.featureToPredictorIndex(featureIndex)] =
            Double.parseDouble(data[featureIndex]);
      }
    }
    return kmeansModel.closestCluster(features);
  }

}
