import pika
import mysql.connector
import asyncio
import random
import json

import time 

WAIT_TIME = 3

VACUUM_LOCATIONS = ['Kitchen', 'Living-room', 'Bedroom', 'Bathroom']
VACUUM_MODES     = ['Power', 'Smart', 'ECO', 'ECO+']
class Vacuum:
    def __init__(self):
        self.id               = 0
        self.isOn             = True 
        self.currentLocation  = 'Kitchen'
        self.cleaningMode     = 'Power'
        self.remainingBattery = 50
        self.serialNumber     = ''

    # called each frame
    def update(self):
        self.remainingBattery -= 1
        if self.remainingBattery <= 0:
            self.isOn             = False
            self.currentLocation  = 'Dock'
            self.cleaningMode     = 'Off'
            self.remainingBattery = 100
            return 
        self.currentLocation = random.choice(VACUUM_LOCATIONS)
        self.cleaningMode    = random.choice(VACUUM_MODES)

def basic_loop(channel):
    # FIXIT(cobileacd)
    print("on_channel_open...")
    vacuum = Vacuum()

    db = mysql.connector.connect(host='sqldb', user='root',
                                    password='root', db='smartResidence')
    print("mysql.connector.connect...")
    cursor = db.cursor()
    cursor.execute('SELECT * FROM vacuum_cleaners WHERE id=1')

    print("cursor.execute")
    for data in cursor:
        print(data)
        vacuum.serialNumber = data[5]
    vacuum.id = 1

    while True:
        print(json.dumps(vacuum.__dict__))
        channel.basic_publish('', 'test_routing_key', json.dumps(vacuum.__dict__), 
                pika.BasicProperties(content_type='text/plain', delivery_mode=pika.DeliveryMode.Transient))
        print("message sent.")
        vacuum.update()
        time.sleep(WAIT_TIME)

    connection.close()

connection = pika.BlockingConnection(pika.ConnectionParameters(host='rabbitmq', port=5672))
channel = connection.channel()

channel.queue_declare(queue='test_routing_key', durable=True)
basic_loop(channel)
