import pika
import mysql.connector
import asyncio
import random
import json
import requests 

import time 

WAIT_TIME = 1

VACUUM_LOCATIONS = ['Kitchen', 'Living-room', 'Bedroom', 'Bathroom']
VACUUM_MODES     = ['Power', 'Smart', 'ECO', 'ECO+']
class Vacuum:
    def __init__(self, id):
        self.id               = id
        self.isOn             = True 
        self.currentLocation  = 'Kitchen'
        self.cleaningMode     = 'Power'
        self.remainingBattery = 50
        self.serialNumber     = ''

    # called each frame
    def update(self):
        self.remainingBattery -= 1
        if self.remainingBattery <= 0 or self.isOn == False:
            self.isOn             = False
            self.currentLocation  = 'Dock'
            self.cleaningMode     = 'Off'
            self.remainingBattery = 100
            return 
        self.currentLocation = random.choice(VACUUM_LOCATIONS)
        self.cleaningMode    = random.choice(VACUUM_MODES)

def getDataFromCall(url):
    response = requests.get(url)
    return response.text

def getJsonFromCall(url):
    return json.loads(getDataFromCall(url))

def basic_loop(channel):
    # FIXIT(cobileacd)
    print("on_channel_open...")
    
    deviceCount = 0
    vacuums = []
    while True:
        newDeviceCount = getDataFromCall('http://smart-residence-jdbc:8080/api/getDevicesCount')
        if deviceCount != newDeviceCount:
            deviceCount = newDeviceCount
            vacuums_json = getJsonFromCall('http://smart-residence-jdbc:8080/api/vacuum_cleaners')
            for vacuum in vacuums_json:
                vacuums.append(Vacuum(vacuum['id']))

        for vacuum in vacuums:
            response = requests.get(f'http://smart-residence-jdbc:8080/api/vacuum_cleaners/{vacuum.id}')
            data = response.text
            vacuum_json = json.loads(data)

            vacuum.isOn = vacuum_json['isOn'] 
            vacuum.serialNumber = vacuum_json['serialNumber']

            vacuum.update()
            print(json.dumps(vacuum.__dict__))

            channel.basic_publish('', 'test_routing_key', json.dumps(vacuum.__dict__), 
                    pika.BasicProperties(content_type='text/plain', delivery_mode=pika.DeliveryMode.Transient))

        time.sleep(WAIT_TIME)

    connection.close()

connection = pika.BlockingConnection(pika.ConnectionParameters(host='rabbitmq', port=5672))
channel = connection.channel()

channel.queue_declare(queue='test_routing_key', durable=True)
basic_loop(channel)
