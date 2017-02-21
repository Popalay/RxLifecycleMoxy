package com.github.popalay.rxlifecyclemoxy;

import android.support.annotation.CallSuper;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.MvpView;
import com.trello.rxlifecycle.LifecycleProvider;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.RxLifecycle;

import rx.Observable;
import rx.subjects.BehaviorSubject;

public abstract class RxPresenter<T extends MvpView> extends MvpPresenter<T>
        implements LifecycleProvider<PresenterEvent> {

    private final BehaviorSubject<PresenterEvent> lifecycleSubject = BehaviorSubject.create();

    @CallSuper
    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        lifecycleSubject.onNext(PresenterEvent.CREATE);
    }

    @Override
    @NonNull
    @CheckResult
    public final Observable<PresenterEvent> lifecycle() {
        return lifecycleSubject.asObservable();
    }

    @Override
    @NonNull
    @CheckResult
    public final <K> LifecycleTransformer<K> bindUntilEvent(@NonNull PresenterEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <K> LifecycleTransformer<K> bindToLifecycle() {
        return RxLifecyclePresenter.bindPresenter(lifecycleSubject);
    }

    @CallSuper
    @Override
    public void onDestroy() {
        lifecycleSubject.onNext(PresenterEvent.DESTROY);
        super.onDestroy();
    }
}