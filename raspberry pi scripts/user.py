import pyrebase

config = {
  "apiKey": "AIzaSyA1g3itExEzi5gM4UVBq07bK9N_9lzXMCw",
  "authDomain": "parkai.firebaseapp.com",
  "databaseURL": "https://parkai.firebaseio.com/",
  "storageBucket": "parkai.appspot.com"
}

firebase = pyrebase.initialize_app(config)

db = firebase.database()
#db.child("users").child("6I6SFTiP1bRxux7CsQUCmlxk5MC3").get()

all_users = db.child("users").get()
for user in all_users.each():
    print(user.key())
    print(user.val())
