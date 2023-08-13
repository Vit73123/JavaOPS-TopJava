## AdminRestController

Get all

`curl -X GET http://localhost:8080/topjava/rest/admin/users`  

Get

`curl -X GET http://localhost:8080/topjava/rest/admin/users/100001`

Get with meals

`curl -X GET http://localhost:8080/topjava/rest/admin/users/with-meals/100001`

## ProfileRestController

Get with meals

`curl -X GET http://localhost:8080/topjava/rest/profile/with-meals`

## MealRestController
Get all

`curl -X GET http://localhost:8080/topjava/rest/meals`  

Get

`curl -X GET http://localhost:8080/topjava/rest/meals/100003`  

Get filtered with null dates and time

`curl -X GET http://localhost:8080/topjava/rest/meals/filter`  

Get filtered by dates

`curl -X GET http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-30&endDate=2020-01-30`  

Create

`
curl -X POST http://localhost:8080/topjava/rest/meals
-H 'Content-Type: application/json; charset=UTF-8'
-d
'{
"dateTime": "2020-02-01T18:00:00",
"description": "Созданный ужин",
"calories": 300
}'
`  
*On Windows 7 to use GitBash with Windows-1251 encoding:*  
`
curl -X POST http://localhost:8080/topjava/rest/meals
-H 'Content-Type: application/json; charset=Windows-1251'
-d
'{
"dateTime": "2020-02-01T18:00:00",
"description": "Созданный ужин",
"calories": 300
}'
`


Update

`
curl -X PUT http://localhost:8080/topjava/rest/meals/100003
-H 'Content-Type: application/json; charset=UTF-8'
-d
'{
"dateTime": "2020-01-30T13:02:00",
"description": "Обновленный завтрак",
"calories": 200
}'
`  
*On Windows 7 to use GitBash with Windows-1251 encoding:*  
`
curl -X PUT http://localhost:8080/topjava/rest/meals/100003
-H 'Content-Type: application/json; charset=Windows-1251'
-d
'{
"dateTime": "2020-01-30T13:02:00",
"description": "Обновленный завтрак",
"calories": 200
}'
`

Delete

`curl -X DELETE http://localhost:8080/topjava/rest/meals/100003`