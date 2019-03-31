
# Сервис управления пользователями

Позволяет выполнять следующие операции:
* #### Получить пользователя для авторизации
##### METHOD:  `GET`
##### URL: `http://localhost:8099/user?login=login&password=password`
##### REQUEST PARAMS: `login`, `password`

* #### Добавить пользователя
##### METHOD:  `POST`
##### URL: `http://localhost:8099/user`
##### BODY: `{"login":"user","password":"querty"}`

* #### Редактировать пользователя
##### METHOD:  `PUT`
##### URL: `http://localhost:8099/user/update/{userId}`
##### BODY: `{"login":"user","password":"querty"}`

* #### Заблокировать пользователя
##### METHOD:  `PUT`
##### URL: `http://localhost:8099/user/block/{userId}`