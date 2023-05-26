import mysql.connector

mydb = mysql.connector.connect(
  host="localhost",
  user="ldyu",
  password="password",
  database='testDB'
)

# create a cursor
cursor = mydb.cursor()

# execute SQL query
query = ("SELECT uid FROM student")
cursor.execute(query)

# count rows
rows = cursor.fetchall()
print(f'{len(rows)} students in the student table')

# close cursor and connection
cursor.close()
mydb.close()