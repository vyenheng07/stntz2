/*
 * Copyright 2019 The AoE Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.didi.aoe.library.service.pojos

import android.text.TextUtils

import com.didi.aoe.library.api.domain.ModelSource
import com.didi.aoe.library.modeloption.loader.pojos.LocalModelOption
import com.google.gson.annotations.SerializedName

/**
 * @author noctis
 * @since 1.1.0
 */
open class ModelOption : LocalModelOption() {
    @SerializedName("source")
    private val source: String? = null

    @SerializedName("runtime")
    open val runtime: String? = null

    @SerializedName("modelSign")
    open val sign: String? = null

    @SerializedName("modelId")
    open val modelId: Long = 0

    override fun getModelSource(): String {
        return if (ModelSource.CLOUD == source || ModelSource.LOCAL == source) {
            source
        } else super.getModelSource()
    }

    override fun isValid(): Boolean {
        return super.isValid() && (modelId != 0L
                && !TextUtils.isEmpty(version)
                && !TextUtils.isEmpty(modelSource)
                && !TextUtils.isEmpty(sign))
    }

    override fun toString(): String {
        return "ModelOption(version=$version, modelDir=$modelDir, modelName=$modelName, source=$source, runtime=$runtime, sign=$sign, modelId=$modelId)"
    }


}
