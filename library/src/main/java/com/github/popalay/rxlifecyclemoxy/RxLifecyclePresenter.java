/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.popalay.rxlifecyclemoxy;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.OutsideLifecycleException;

import rx.Observable;
import rx.functions.Func1;

import static com.github.popalay.rxlifecyclemoxy.PresenterEvent.DESTROY;
import static com.trello.rxlifecycle.RxLifecycle.bind;

public final class RxLifecyclePresenter {

    private RxLifecyclePresenter() {
        throw new AssertionError("No instances");
    }

    @NonNull
    @CheckResult
    public static <T> LifecycleTransformer<T> bindPresenter(@NonNull final Observable<PresenterEvent> lifecycle) {
        return bind(lifecycle, PRESENTER_LIFECYCLE);
    }

    private static final Func1<PresenterEvent, PresenterEvent> PRESENTER_LIFECYCLE = new Func1<PresenterEvent,
            PresenterEvent>() {
        @Override
        public PresenterEvent call(PresenterEvent lastEvent) {
            switch (lastEvent) {
                case CREATE:
                    return DESTROY;
                case DESTROY:
                    throw new OutsideLifecycleException("Cannot bind to Presenter lifecycle when outside of it.");
                default:
                    throw new UnsupportedOperationException("Binding to " + lastEvent + " not yet implemented");
            }
        }
    };

}