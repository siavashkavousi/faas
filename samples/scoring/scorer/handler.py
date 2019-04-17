import pymongo
import sys

def get_stdin():
    buf = ""
    for line in sys.stdin:
        buf = buf + line
    return buf

name = get_stdin()

from pymongo import MongoClient
client = MongoClient('mongodb://mongo:27017')

db = client['scoring']

users = db.users

user = users.find_one({'name': name})

print('found user: ')
print(user)

user['score'] = 10

users.save(user)

print('saved user score successfully')

