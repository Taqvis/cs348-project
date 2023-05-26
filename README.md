# cs348-project

1. set up MySQl 
2. Create a table called testDB refer to [https://learn.uwaterloo.ca/d2l/le/content/921128/viewContent/4956136/View] for instructions
4. clone the test.py file 
5. change the login credentials in the .py file to the user you have set the permissions for in testDB
6. run the file


sample setup process:

```
$ mysql -u root -p (or sudo mysql -u root)
mysql> CREATE DATABASE testDB;
mysql> USE testDB;
mysql> CREATE TABLE student(uid DECIMAL(3, 0) NOT NULL PRIMARY KEY, name
VARCHAR(30), score DECIMAL(3, 2));
mysql> INSERT INTO student VALUES(1, ’alice’, 0.1);
mysql> INSERT INTO student VALUES(2, ’bob’, 0.4);
mysql> SELECT * FROM student;

mysql> create user ’sujaya’@’localhost’ identified by ’Password0!’;
mysql> grant all on testDB.* to ’sujaya’@’localhost’;
mysql> alter user ’sujaya’@’localhost’ identified with mysql_native_password
by ’Password0!’;
```

