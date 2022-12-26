# job4j_url_shortcut

## О проекте
Это сервис для обеспечения безопасности пользователей через замену прямых url-ссылок на уникальные коды, которые ссылаются на сервис.

Сервис работает через REST API.

### Регистрация сайта.
Сервисом могут пользоваться разные сайты. Каждому сайту выдается пара логин-пароль.

Чтобы зарегистрировать сайт в системе, нужно отправить POST-запрос:
```
/site/registration 
```
с телом JSON-объекта.

Например:
```
{site : "yandex.ru"}
```
Ответ от сервера.
```
{registration : true/false, login: УНИКАЛЬНЫЙ_КОД, password : УНИКАЛЬНЫЙ_КОД}
```
Флаг registration указывает, была ли регистрация выполнена, или сайт уже есть в системе.

### Авторизация.
Пользователь отправляет POST-запрос:
```
/login
```
с login и password.

### Предоставление shortcut-ссылки
Пользователь отправляет POST-запрос:
```
/convert
```
с телом JSON объекта.

Например:
```
{url: "yandex.ru/products"}
```
Ответ от сервера.
```
{code: УНИКАЛЬНЫЙ_КОД}
```
### Переадресация. Выполняется без авторизации.

Пользователь отправляет GET-запрос:
```
/redirect/УНИКАЛЬНЫЙ_КОД
```
Происходит переадресация на ассоциированный url.

### Статистика по своему сайту

Пользователь отправляет GET-запрос:
```
/statistic
```
Ответ от сервера: JSON-объект с количеством вызовов по каждой url-ссылке, которую регистрировал данный пользователь.

Например:
```
{{url: "yandex.ru/products", total : 3}, {url: "yandex.ru/images", total : 5}}
```
## Использованные технологии
Java 17, Maven 4.0, Spring boot 2.7.5, REST, PostgreSQL 14.2, Liquibase 3.6.2, JWT 3.4.0, Lombok 1.18.22, Log4j, JavaDoc

## Настройка окружения
Установить:
- PostgreSQL 14.2 (логин - postgres, пароль - password)
- JDK 17.0.1
- Maven 4.0.0

## Запуск проекта
Создать базу данных (при запросе пароля ввести "password")
```
createdb --username=postgres shortcut
```
Запустить командой
```
mvn spring-boot:run
```
## Контакты
При возникновении вопросов, замечаний или предложений, прошу писать мне по указанным ниже контактам:

[![alt-text](https://img.shields.io/badge/-telegram-grey?style=flat&logo=telegram&logoColor=white)](https://t.me/levgross)&nbsp;&nbsp;
[![alt-text](https://img.shields.io/badge/@%20email-005FED?style=flat&logo=mail&logoColor=white)](mailto:levgross@gmail.com)&nbsp;&nbsp;