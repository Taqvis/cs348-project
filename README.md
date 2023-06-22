# cs348-project

Create a table called testDB refer to [https://learn.uwaterloo.ca/d2l/le/content/921128/viewContent/4956136/View] for instructions

# loading sample data
1. Set up MySQl 
2. Create a table called testDB refer to [https://learn.uwaterloo.ca/d2l/le/content/921128/viewContent/4956136/View] for instructions
3. Create the sample data using the data in dataset folder or clone and run the create-sample.py file
4. Place the dataset in your --secure-file-priv directory, which you can obtain by running `SHOW VARIABLES LIKE "secure_file_priv";`.
5. Run cs348-project-backend/createTable.sql to create all tables in schema.
6. Run the statements in dataset/load-sample.sql. If necessary, update the file location for the sample datasets.

sample setup process:

```
$ mysql -u root -p (or sudo mysql -u root)
mysql> CREATE DATABASE testDB;
mysql> USE testDB;
mysql> source %PROJECT_LOCATION%/cs348-project-backend/createTable.sql
mysql> source %PROJECT_LOCATION%/dataset/load-sample.sql
```
