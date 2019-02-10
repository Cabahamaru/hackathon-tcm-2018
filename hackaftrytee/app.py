

import json

from flask import Flask, g, render_template, request
import sqlite3
import os

DATABASE = "./database.db"
id = 1
# Create app
app = Flask(__name__)
app.config['DEBUG'] = True
app.config['SECRET_KEY'] = 'super-secret'

# check if the database exist, if not create the table and insert a few lines of data
if not os.path.exists(DATABASE):
    conn = sqlite3.connect(DATABASE)
    cur = conn.cursor()
    cur.execute("CREATE TABLE incidencia (id int NOT Null, tipus TEXT, descripcio TEXT, ubicacio TEXT, foto TEXT);")
    conn.commit()
    conn.close()


# helper method to get the database since calls are per thread,
# and everything function is a new thread when called
def get_db():
    db = getattr(g, '_database', None)
    if db is None:
        db = g._database = sqlite3.connect(DATABASE)
    return db


# helper to close
@app.teardown_appcontext
def close_connection(exception):
    db = getattr(g, '_database', None)
    if db is not None:
        db.close()


@app.route("/")
def index():
    cur = get_db().cursor()
    res = cur.execute("select * from incidencia")
    songs_as_dict = []
    for a in res:
        songs_as_dict.append({"id": a[0]})
        songs_as_dict.append({"tipus": a[1]})
        songs_as_dict.append({"descripcio": a[2]})
        songs_as_dict.append({"ubicacio": a[3]})
        songs_as_dict.append({"foto": a[4]})
        print(a[0])
    return json.dumps(songs_as_dict)

@app.route('/new', methods=['POST'])
def login():
    if request.method == 'POST':
        newDataToSave = request.json
        tipus = newDataToSave["tipus"]
        descripcio = newDataToSave["descripcio"]
        ubicacio = newDataToSave["location"]
        foto = newDataToSave["imgURL"]

        conn = sqlite3.connect(DATABASE)
        cur = conn.cursor()
        ++id
        cur.execute("insert into incidencia values (?,?,?,?,?)", (id, tipus, descripcio, ubicacio, foto))

        conn.commit()
        conn.close()
        return "OK"


if __name__ == "__main__":
    app.run()