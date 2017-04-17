# Todo
Simple todo list that can be used on the command line or as GUI application. 

# Installation:

* Download source & open it in eclipse

OR

* Download Todo.jar and run


# Usage

Todo can be setup to write todos to file or database. By default Todo writes todos to file named local.todo. Todos destinations can be change by creating Setting.ini file. To use database you have to first run createtable.sql. This script creates needed table to mysql database. Todo can be also used on command line like: java -jar Todo.jar -h. 


Setting.ini file example:
```INI
    #Comment
    Target=filename OR jdbc:mysql://servername:port/database
    User=Mysql_username
    Password=Mysql_password
```

