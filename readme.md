# Mads SDK Android demo-app

В этом репозитории содержится исходный код демо-приложения с интеграцией Mads SDK.

На примере демо-приложения вы можете посмотреть и отладить код интеграции SDK.

Документация к SDK доступна [по ссылке](https://mads-media.magnit.ru/docs/index.html).

## На что стоит обратить внимание

### Инициализация SDK

Документация по установке SDK доступна [по ссылке](https://mads-media.magnit.ru/docs/%D0%B8%D0%BD%D1%82%D0%B5%D0%B3%D1%80%D0%B0%D1%86%D0%B8%D1%8F/Mobile-SDK/Android-SDK/quickstart/index.html).

Пример инициализации SDK можно увидеть в коде [MadsDemoApplication](app/src/main/kotlin/ru/tander/mads/demo/MadsDemoApplication.kt).

### Работа с In-App рекламой

Документация по работе с In-App рекламой доступна [по ссылке](https://mads-media.magnit.ru/docs/%D0%B8%D0%BD%D1%82%D0%B5%D0%B3%D1%80%D0%B0%D1%86%D0%B8%D1%8F/Mobile-SDK/Android-SDK/%D0%A0%D0%B5%D0%BA%D0%BB%D0%B0%D0%BC%D0%BD%D1%8B%D0%B5-%D1%84%D0%BE%D1%80%D0%BC%D0%B0%D1%82%D1%8B/In-App-Message/modal-window/index.html).

Весь код работы приложения с In-App рекламой находится в пакете [ru.tander.mads.demo.ui.screen.inapp](app/src/main/kotlin/ru/tander/mads/demo/ui/screen/inapp).

#### 1. Загрузка In-App рекламы

Загрузка рекламы происходит внутри [InAppAdLoadingModel](app/src/main/kotlin/ru/tander/mads/demo/ui/screen/inapp/component/InAppAdLoadingModel.kt).

#### 2. Показ In-App рекламы

Показ и подписка на события показа In-App рекламы происходят внутри [InAppAdDemoScreen](app/src/main/kotlin/ru/tander/mads/demo/ui/screen/inapp/InAppAdDemoScreen.kt).

### Работа с In-Line рекламой

Документация по работе с In-Line рекламой доступна [по ссылке](https://mads-media.magnit.ru/docs/%D0%B8%D0%BD%D1%82%D0%B5%D0%B3%D1%80%D0%B0%D1%86%D0%B8%D1%8F/Mobile-SDK/Android-SDK/%D0%A0%D0%B5%D0%BA%D0%BB%D0%B0%D0%BC%D0%BD%D1%8B%D0%B5-%D1%84%D0%BE%D1%80%D0%BC%D0%B0%D1%82%D1%8B/InLine/multiformat/index.html).

Весь код работы приложения с In-Line рекламой находится в пакете [ru.tander.mads.demo.ui.screen.inline](app/src/main/kotlin/ru/tander/mads/demo/ui/screen/inline).

#### 1. Загрузка In-Line рекламы

Загрузка рекламы происходит внутри [InLineAdDemoViewModel](app/src/main/kotlin/ru/tander/mads/demo/ui/screen/inline/InLineAdDemoViewModel.kt).

#### 2. Показ In-Line рекламы

Показ и подписка на события показа In-Line рекламы происходят внутри [InLineAdLoadingItem.kt](app/src/main/kotlin/ru/tander/mads/demo/ui/screen/inline/InLineAdLoadingItem.kt).


