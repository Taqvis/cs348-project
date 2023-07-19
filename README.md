# cs348-project

Create a table called testDB refer to [https://learn.uwaterloo.ca/d2l/le/content/921128/viewContent/4956136/View] for instructions

# loading production data
1. Set up MySQl.
2. Create a table called testDB refer to [https://learn.uwaterloo.ca/d2l/le/content/921128/viewContent/4956136/View] for instructions.
3. Download the dataset at [https://www.kaggle.com/datasets/maharshipandya/-spotify-tracks-dataset] or use the copy in dataset.
4. Place the 2 sample datasets in your --secure-file-priv directory, which you can obtain by running `SHOW VARIABLES LIKE "secure_file_priv";`.
5. Run cs348-project-backend/createTable.sql to create all tables in schema.
6. Run cs348-project-backend/createViewsAndTriggers.sql.
7. Update the path to your MySQL secure-file directory in dataset/load-sample.sql and run the file.

sample setup process:

```
$ mysql -u root -p (or sudo mysql -u root)
mysql> CREATE DATABASE testDB;
mysql> USE testDB;
mysql> source %PROJECT_LOCATION%/cs348-project-backend/createTable.sql
mysql> source %PROJECT_LOCATION%/dataset/load-sample.sql
```

# loading sample data
1. Set up MySQl.
2. Create a table called testDB refer to [https://learn.uwaterloo.ca/d2l/le/content/921128/viewContent/4956136/View] for instructions.
3. Create the sample data using the data in dataset folder or clone and run the create-sample.py file.
4. Place the 2 sample datasets in your --secure-file-priv directory, which you can obtain by running `SHOW VARIABLES LIKE "secure_file_priv";`.
5. Run cs348-project-backend/createTable.sql to create all tables in schema.
6. Run cs348-project-backend/createViewsAndTriggers.sql.
7. Update the path to your MySQL secure-file directory in dataset/load-sample.sql and run the file.

sample setup process:

```
$ mysql -u root -p (or sudo mysql -u root)
mysql> CREATE DATABASE testDB;
mysql> USE testDB;
mysql> source %PROJECT_LOCATION%/cs348-project-backend/createTable.sql
mysql> source %PROJECT_LOCATION%/dataset/load-sample.sql
```

# loading backend
1. Set up environment variables in cs348-project-backend/src/main/resources/application.properties
   ${DB_USER} = MySQL username
   ${DB_PASSWORD} = MySQL password
   ${DB_NAME} = Database name
2. Run project

# loading frontend
1. Clone `frontend-main` branch
2. Following README in the branch
