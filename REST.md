## Working with users

Get all users

`curl -X GET http://localhost:8080/topjava/rest/admin/users`  

Get the user by id

`curl -X GET http://localhost:8080/topjava/rest/admin/users/100001`

Get the user by id with meals

`curl -X GET http://localhost:8080/topjava/rest/admin/users/100001/with-meals`

## Working with current user

Get user's meals

`curl -X GET http://localhost:8080/topjava/rest/profile/with-meals`

## Working with meals

Get all meals

`curl -X GET http://localhost:8080/topjava/rest/meals`  

Get the meal by id

`curl -X GET http://localhost:8080/topjava/rest/meals/100003`  

Get meals filtered by date/time with dates and time of null

`curl -X GET http://localhost:8080/topjava/rest/meals/filter`  

Get filtered by dates with time of null

`curl -X GET http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-30&endDate=2020-01-30`  

Create the meal

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


Update the meal by id

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

Delete the meal by id

`curl -X DELETE http://localhost:8080/topjava/rest/meals/100003`