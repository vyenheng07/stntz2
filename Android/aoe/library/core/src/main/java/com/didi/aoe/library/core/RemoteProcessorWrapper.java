package com.didi.aoe.library.core;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.NonNull;

import com.didi.aoe.library.api.AoeModelOption;
import com.didi.aoe.library.api.AoeProcessor;
import com.didi.aoe.library.core.io.AoeParcelImpl;
import com.didi.aoe.library.core.pojos.Message;
import com.didi.aoe.library.core.service.IAoeProcessService;
import com.didi.aoe.library.logging.Logger;
import com.didi.aoe.library.logging.LoggerFactory;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static android.content.Context.BIND_AUTO_CREATE;

/**
 * 独立进程处理器包装实现，通过Context建立远程连接，包装推理交互。
 *
 * @author noctis
 */
final class RemoteProcessorWrapper extends AbsProcessorWrapper {
    private final Logger mLogger = LoggerFactory.getLogger("RemoteProcessorWrapper");
    private final AoeClient.Options mClientOptions;
    private final ParcelComponent mParceler;
    private final AtomicBoolean bServiceBinded = new AtomicBoolean(false);
    private final AtomicBoolean bInited = new AtomicBoolean(false);
    private String mId;
    private List<AoeModelOption> mModelOptions;
    private IAoeProcessService mProcessProxy;
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mLogger.debug("onServiceConnected: " + mId);
            bServiceBinded.set(true);
            mProcessProxy = IAoeProcessService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mLogger.debug("onServiceDisconnected");
            bServiceBinded.set(false);
            mProcessProxy = null;
        }
    };

    public RemoteProcessorWrapper(@NonNull Context context, AoeClient.Options options) {
        super(context, options);
        mParceler = ComponentProvider.getParceler(options.parcelerClassName);
        mClientOptions = options;

        if (!isServiceRunning()) {
            bindService();
        }
    }

    @Override
    public boolean init(@NonNull Context context, @NonNull List<AoeModelOption> modelOptions) {
        mModelOptions = modelOptions;
        if (isServiceRunning()) {
            tryInitIfNeeded(mId, mModelOptions);
            return bInited.get();
        } else {
            bindService();
            return true;
        }

    }

    @Override
    public Object run(@NonNull Object input) {
        if (isServiceRunning()) {
            tryInitIfNeeded(mId, mModelOptions);

            byte[] ins = getParcelComponent().obj2Byte(input);

            if (ins != null && ins.length > 0) {
                try {
                    byte[] outs = paserToExecute(ins);
                    if (outs != null && outs.length > 0) {
                        return getParcelComponent().byte2Obj(outs);
                    }
                } catch (RemoteException e) {
                    mLogger.error("process error: ", e);
                }
            }

        } else {
            bindService();
        }
        return null;
    }

    /**
     * 输入流拆包依次处理，service进程整合执行推理。
     *
     * @param ins 输入数据
     * @return 推理执行结果
     * @throws RemoteException 推理操作IPC异常
     */
    private byte[] paserToExecute(@NonNull byte[] ins) throws RemoteException {
        List<Message> msgs = Paser.split(ins);
        for (Message msg : msgs) {
            // 数据拆包给remote service，数据完整接收后返回非空数据
            Message processResult = mProcessProxy.process(mId, msg);
            if (processResult != null) {
                return processResult.getData();
            }

        }
        return new byte[0];
    }

    @Override
    public void release() {
        // release remote processor resource
        if (isServiceRunning()) {
            try {
                mProcessProxy.release(mId);
            } catch (RemoteException e) {
                mLogger.error("release error: ", e);
            }
        }
        // unbind remote service
        if (bServiceBinded.getAndSet(false)) {
            unbindService();
        }
        bInited.set(false);
    }

    @Override
    public boolean isReady() {
        return bInited.get();
    }

    @NonNull
    @Override
    public ParcelComponent getParcelComponent() {
        return mParceler;
    }

    private boolean isServiceRunning() {
        return mProcessProxy != null
                && mProcessProxy.asBinder() != null
                && mProcessProxy.asBinder().isBinderAlive();
    }

    @SuppressWarnings("UnusedReturnValue")
    private boolean tryInitIfNeeded(@NonNull String id, @NonNull List<AoeModelOption> modelOptions) {
        if (bInited.get()) {
            return true;
        }
        if (isServiceRunning()) {

            try {
                RemoteOptions options = new RemoteOptions();
                options.setClientOptions(mClientOptions);
                options.setModelOptions(modelOptions);

                AoeProcessor.ParcelComponent parceler = ComponentProvider.getParceler(AoeParcelImpl.class.getName());
                byte[] ins = parceler.obj2Byte(options);
                Message msg = new Message(ins);
                boolean initResult = mProcessProxy.init(id, msg);
                mLogger.debug("tryInitIfNeeded: " + initResult);
                bInited.set(initResult);
                return initResult;
            } catch (RemoteException e) {
                mLogger.error("tryInitIfNeeded error", e);
            }
        }
        return false;
    }

    /**
     * 启动/绑定模型服务
     */
    private void bindService() {
        Intent intent = new Intent(mContext, AoeProcessService.class);
        mContext.bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
    }

    /**
     * 结束模型服务
     */
    private void unbindService() {
        mContext.unbindService(mServiceConnection);
    }

    @Override
    public void setId(String id) {
        mId = id;
    }
}
