# Doctor Anywhere Mini Porject
1. Install docker-compose
2. Start
```
docker-compose up
```

### Feature added
1. JPA usage
2. Error handling
3. Security added
4. Run both database and app inside container (starting with empty data set)

### Security
I'm implement Basic authentication with username and password, here is 2 type of role:
  
|            	| user     	| admin                  	|  
|------------	|----------	|------------------------	|  
| username   	| user     	| admin                  	|  
| password   	| userPass 	| adminPass              	|  
| permission 	| GET      	| GET, POST, PUT, DELETE 	|  

Please add these value when calling api