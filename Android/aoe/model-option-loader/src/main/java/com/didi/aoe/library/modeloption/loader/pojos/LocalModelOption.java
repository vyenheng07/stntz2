package com.didi.aoe.library.modeloption.loader.pojos;

import android.text.TextUtils;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import com.didi.aoe.library.api.AoeModelOption;
import com.didi.aoe.library.api.ModelSource;

/**
 * AoE默认的模型配置定义
 *
 * @author noctis
 */
@Keep
public class LocalModelOption implements AoeModelOption {
    private String version;

    /**
     * 模型文件目录路径，目前只支持local模型
     */
    private String modelDir;
    /**
     * 模型文件名，便于拓展多种模型
     */
    private String modelName;

    public String getVersion() {
        return version;
    }

    @NonNull
    @Override
    public String getModelDir() {
        return modelDir;
    }

    @NonNull
    @Override
    public String getModelName() {
        return modelName;
    }

    @NonNull
    @Override
    public String getModelSource() {
        return ModelSource.LOCAL;
    }

    @Override
    public boolean isValid() {
        return !TextUtils.isEmpty(modelDir) &&
                !TextUtils.isEmpty(modelName);
    }

    @Override
    public String toString() {
        return "LocalModelOption{" +
                "version='" + version + '\'' +
                ", modelDir='" + modelDir + '\'' +
                ", modelName='" + modelName + '\'' +
                '}';
    }

}
