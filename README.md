# STOCKROOM


##  Simplified Inventory Management
[![Maintenance](https://img.shields.io/badge/Maintained%3F-yes-green.svg)](https://GitHub.com/Naereen/StrapDown.js/graphs/commit-activity)

 ![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)
 ![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
 ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
 ![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)

Stockroom is a system designed to help businesses manage their parts inventory efficiently. With it, it is possible to organize items by sectorEntity and shelf, making it easier to locate and control movements.

### 📌 Key Features
- #### ✅ Registration and organization of parts by sectorEntity and shelf
- #### ✅ Control of stock inputs and outputs
- #### ✅ Quick search by code, description, or industry
- #### ✅ Detailed movement and availability reports
- #### ✅ Integration via REST API


### 📦 Installation and Configuration
- #### 🐳 Running with Docker
```` 
db:
image: mysql
container_name: DataBase
restart: always
environment:
MYSQL_ROOT_PASSWORD: root
MYSQL_DATABASE: stockroom_database
MYSQL_USER: user
MYSQL_PASSWORD: password
MYSQLD_OPTS: --innodb-force-recovery=1
ports:
- "3306:3306"
expose:
- '3306'
volumes:
- C:/dev_data:/var/lib/mysql

app:
image: bat333/stockroom_app:1.0
container_name: stockroom_app
restart: always
ports:
- "8080:8080"
environment:
SPRING_DATASOURCE_URL: jdbc:mysql://db:3306/stockroom_database?createDatabaseIfNotExist=true&serverTimezone=America/Sao_Paulo&useSSL=true
SPRING_DATASOURCE_USERNAME: user
SPRING_DATASOURCE_PASSWORD: password
depends_on:
- db
````
### ▶️ Instructions for Use

## **_SECTOR_**

#### 1️⃣ Create an Sector

- #### 📤 Endpoint:

##### ```` POST /api/sectorEntity````
- #### 📤 Payload (Request):

```` 
 {
  "sectorEntity": "Manutenção",
  "shelf": "A",
  "column": "3",
  "row": "2"
 }
 ````
- #### 📤  Return (Answer):

```` 
{
  "id": 1,
  "sectorEntity": "Manutenção",
  "shelf": "A",
  "column": "3",
  "row": "2",
  "part": []
}
 ````



#### 2️⃣ List All Industries

- #### 📤 Endpoint:

##### ```` GET /api/sectorEntity ````

- #### 📤  Return (Answer):

```` 
{
  "content": [
    {
      "id": 1,
      "sectorEntity": "Manutenção",
      "shelf": "A",
      "column": "3",
      "row": "2",
      "part": []
    }
  ]
}
 ````


#### 3️⃣ Consult a Specific Industry

- #### 📤 Endpoint:

##### ```` GET /api/sectorEntity/{id} ````

- #### 📤  Return (Answer):

```` 

{
  "id": 1,
  "sectorEntity": "Manutenção",
  "shelf": "A",
  "column": "3",
  "row": "2",
  "part": []
}
 ````



#### 4️⃣ Upgrading an Industry

- #### 📤 Endpoint:

##### ```` PATCH /api/sectorEntity/{id}````
- #### 📤 Payload (Request):

```` 
{
  "sectorEntity": "Produção",
  "shelf": "C",
  "column": "1",
  "row": "5"
}

 ````
- #### 📤  Return (Answer):

```` 
{
  "id": 1,
  "sectorEntity": "Produção",
  "shelf": "C",
  "column": "1",
  "row": "5",
  "part": []
}

 ````

#### 5️⃣ Delete an Sector

- #### 📤 Endpoint:

##### ```` DELETE /api/sectorEntity/{id} ````

- #### 📤  Return (Answer):

```` 
204 No Content
 ````



## **_PART_**

#### 1️⃣ Create a Part

- #### 📤 Endpoint:

##### ```` POST /api/part/{id}````
- #### 📤 Payload (Request):

```` 
{
  "cod": 123456,
  "name": "Parafuso",
  "image": "<arquivo binário>",
  "amount": 10.5,
}
 ````
- #### 📤  Return (Answer):

```` 
{
  "id": 1,
  "cod": 123456,
  "name": "Parafuso",
  "image": "<arquivo binário>",
  "amount": 10.5,
  "sectorEntity": {
    "id": 1,
    "sectorEntity": "Manutenção",
    "shelf": "A",
    "column": "3",
    "row": "2"
  }
}
 ````

#### 2️⃣ Querying a Specific Part

- #### 📤 Endpoint:

##### ```` GET /api/part/{id} ````

- #### 📤  Return (Answer):

```` 

{
  "id": 1,
  "cod": 123456,
  "name": "Parafuso",
  "image": "<arquivo binário>",
  "amount": 10.5,
  "sectorEntity": {
    "id": 1,
    "sectorEntity": "Manutenção",
    "shelf": "A",
    "column": "3",
    "row": "2"
  }

 ````

#### 3️⃣ Update a Part

- #### 📤 Endpoint:

##### ```` PATCH /api/part/{id}````
- #### 📤 Payload (Request):

```` 
{
  "cod": 1,
  "name": "part",
  "image": "<arquivo binário>",
  "amount": 10.5,  
  "sectorEntity": 5
}

 ````
- #### 📤  Return (Answer):

```` 
{
  "id": 1,
  "cod": 1,
  "name": "part",
  "image": "<arquivo binário>",
  "amount": 10.5,
  "sectorEntity": {
    "id": 5,
    "sectorEntity": "Manutenção",
    "shelf": "A",
    "column": "3",
    "row": "2"
  }

 ````

#### 4️⃣ Delete an Part

- #### 📤 Endpoint:

##### ```` DELETE /api/part/{id} ````

- #### 📤  Return (Answer):

```` 
204 No Content
 ````

#### 5️⃣ Query a Part Search

- #### 📤 Endpoint:

##### ```` GET /api/part/search ````

- #### 📤  Return (Answer):

```` 

{
  "id": 1,
  "cod": 123456,
  "name": "Parafuso",
  "image": "<arquivo binário>",
  "amount": 10.5,
  "sectorEntity": {
    "id": 1,
    "sectorEntity": "Manutenção",
    "shelf": "A",
    "column": "3",
    "row": "2"
  }

 ````


### 📌 Links
- #### 🐳 [Docker Hub](https://hub.docker.com/repository/docker/bat333/stockroom_app/general)
- #### 📧 [Contact Us](mailto:rafaelolivais09@gmail.com)


