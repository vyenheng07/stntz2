/*
 * Copyright 2019 The AoE Authors. All Rights Reserved.
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

package com.didi.aoe.examples.demo.features.vision.inference

import android.content.Context
import androidx.camera.core.ImageProxy
import com.didi.aoe.library.api.domain.Device
import com.didi.aoe.library.core.AoeClient
import java.nio.ByteBuffer

/**
 *
 *
 * @author noctis
 * @since 1.1.0
 */
abstract class Inference constructor(val context: Context, val device: Device, val numThreads: Int) {
    protected var aoeClient: AoeClient = createClient()

    abstract fun createClient(): AoeClient

    fun isRunning(): Boolean {
        return aoeClient.isRunning
    }

    abstract fun process(image: ImageProxy): Any?

    fun release() {
        aoeClient.release()
    }

    protected fun ByteBuffer.toByteArray(): ByteArray {
        rewind()    // Rewind the buffer to zero
        val data = ByteArray(remaining())
        get(data)   // Copy the buffer into a byte array
        return data // Return the byte array
    }
}