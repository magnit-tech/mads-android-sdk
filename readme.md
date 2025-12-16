# Mads SDK Android demo-app

В этом репозитории содержится исходный код демо-приложения с интеграцией Mads SDK.

На примере демо-приложения вы можете посмотреть и отладить код интеграции SDK.

Документация к SDK доступна [по ссылке](https://mad-sdk-doc.vercel.app).

## Сборка и запуск

Перед синхронизацией и сборкой проекта необходимо распаковать содержимое `mads.zip` внутри репозитория по пути `maven/mads`. Сделать это можно поместив `mads.zip` в корень проекта и запустив `./gradlew installAndroidSdk`.

После этого проект должен собираться, приложение должно запускаться и работать корректно.

## На что стоит обратить внимание

### Инициализация SDK

Документация по установке SDK доступна [по ссылке](https://mad-sdk-doc.vercel.app/интеграции/sdk/android/start).

Пример инициализации SDK можно увидеть в коде [MadsDemoApplication](app/src/main/kotlin/ru/tander/mads/demo/MadsDemoApplication.kt).

### Работа с In-App рекламой

Документация по работе с In-App рекламой доступна [по ссылке](https://mad-sdk-doc.vercel.app/интеграции/sdk/android/рекламные-форматы/integration-in-app).

Весь код работы приложения с In-App рекламой находится в пакете [ru.tander.mads.demo.ui.screen.inapp](app/src/main/kotlin/ru/tander/mads/demo/ui/screen/inapp).

#### 1. Загрузка In-App рекламы

Инициализация загрузчика рекламы (`InAppAdLoader`) происходит внутри [InAppAdDemoViewModel](app/src/main/kotlin/ru/tander/mads/demo/ui/screen/inapp/InAppAdDemoViewModel.kt).

Загрузка рекламы происходит внутри [InAppAdLoadingModel](app/src/main/kotlin/ru/tander/mads/demo/ui/screen/inapp/component/InAppAdLoadingModel.kt).

#### 2. Показ In-App рекламы

Показ и подписка на события показа In-App рекламы происходят внутри [InAppAdDemoScreen](app/src/main/kotlin/ru/tander/mads/demo/ui/screen/inapp/InAppAdDemoScreen.kt).
