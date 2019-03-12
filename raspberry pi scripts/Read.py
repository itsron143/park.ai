#!/usr/bin/env python

import RPi.GPIO as GPIO
import SimpleMFRC522

import requests
import base64
import json

# Sample image file is available at http://plates.openalpr.com/ea7the.jpg
IMAGE_PATH = 'us.jpg'
SECRET_KEY = 'sk_ccf1fab77860955095d745fa'


reader = SimpleMFRC522.SimpleMFRC522()

try:
		id, text = reader.read()
		#print(id)
		no_plate = "EA7THE"
		l = text.split(",")
		print(l[0])
		if no_plate == l[1]:
			print("License Plate Verified!")
		print(l[1])
		print(l[2])        
		#print(text.split(","))

finally:
		GPIO.cleanup()
