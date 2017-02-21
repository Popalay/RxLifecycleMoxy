[![Download](https://api.bintray.com/packages/popalay/maven/RxLifecycleMoxy/images/download.svg) ](https://bintray.com/popalay/maven/RxLifecycleMoxy/_latestVersion)
[![License](https://img.shields.io/badge/license-Apache--2.0-green.svg)](https://github.com/Popalay/RxLifecycleMoxy/blob/master/LICENSE)

# Rx Realm

The utility for automatic completion of sequences based on Moxy Presenter lifecycle events

## Getting Started

```groovy
compile 'com.github.popalay:rx-lifecycle-moxy:latest-version'
```
## Usage

```java
@InjectViewState
public class ChatPresenter extends BasePresenter<ChatView> {

    @Inject ChatInteractor mChatInteractor;

    public ChatPresenter() {
        App.get().getSessionComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        mChatInteractor.listenMessages()
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getViewState::setMessages, this::handleBaseError);
    }
}
```

License
-----

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.