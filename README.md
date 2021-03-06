# iso20022
Пример интеграции по стандарту ISO 20022

# Требования для запуска проекта
Сообщения для отправки в банк требуют электронной подписи, в проекте она выполняется с помощью Cryptopro JCP. Для корректной работы приложения необходимо:
1) Установить Cryptopro CSP
2) Установить Cryptopro JCP. Установка заключается в модификации JRE или JDK.
3) Создать сертификат и ключ шифрования в HdImageStore Cryptopro JCP. Для этого можно зайти в интерфейс ControlPane и сгенерировать ключ оттуда.
4) Удалить файл <path to patched JRE/JDK>/jre/lib/ext/JCPxml.jar (требования Криптопро для корректной работы xml подписания, подробнее в ЖТЯИ.00091-01 33 01. Руководство программиста.pdf)
5) Запустить приложение, используя подготовленную JRE/JDK.

# Запуск проекта
Для запуска проекта необходимо использовать java 8 с установленным Cryptopro JCP.
1) java -jar iso20022-***.jar
2) gradlew bootRun

После успешного запуска приложения будет доступен интерфейс по адресу http://127.0.0.1:8080. Для использования приложения необходимо перейти на страницу "Settings" (http://127.0.0.1:8080/settings), выбрать сертификаты подписания, заполнить логин, пароль, адрес API и нажать на кнопку "Save". Настройки сохранятся в директории config (находится около jar файла) и будут доступны после перезапуска приложения.
Логи приложения доступны в директории logs (находится около jar файла).
