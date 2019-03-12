#!/usr/bin/env python

import RPi.GPIO as GPIO
import SimpleMFRC522
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
    name = dict(user.val())[u'name']
    no_plate = dict(user.val())[u'numberPlate']
    no_plate = "EA7THE"
    uid = dict(user.val())[u'uid']

#print(no_plate)
#print(uid)

check_string = name + "," + no_plate + "," + uid
#print(check_string)


reader = SimpleMFRC522.SimpleMFRC522()

try:
        text = check_string
        print("Now place your tag to write")
        reader.write(text)
        print("Written")
finally:
       GPIO.cleanup()
