package io.freefair.android.injection.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;

import io.freefair.injection.provider.InjectorProvider;
import io.freefair.injection.injector.RuntimeInjector;
import io.freefair.injection.injector.Injector;
import io.freefair.android.injection.injector.WatchViewStubActivityInjector;

/**
 * @author Dennis Fricke
 */
@SuppressWarnings("unused")
public abstract class InjectionWearableActivity extends Activity implements InjectorProvider {
    WatchViewStubActivityInjector injector;

    Injector parentInjector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentInjector = RuntimeInjector.getInstance();
        parentInjector.inject(this);
    }

    public void setContentView(int layoutResID, int viewStubID) {
        super.setContentView(layoutResID);
        final WatchViewStub stub = (WatchViewStub) findViewById(viewStubID);

        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                injector = new WatchViewStubActivityInjector(InjectionWearableActivity.this, stub, parentInjector);

                injector.injectResources();
                injector.injectAttributes();

                tryInjectViews();

                onLayoutReady();
            }
        });
    }

    private void tryInjectViews() {
        injector.injectViews();
    }

    protected abstract void onLayoutReady();

    @Override
    public WatchViewStubActivityInjector getInjector() {
        return injector;
    }
}