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
        self.name             = 'Vacuum'
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

class LightBulb:
    def __init__(self, id):
        self.id = id
        self.name = 'LightBulb'
        self.isOn = True
        self.location = 'Living room'
        self.brightnessLvl = 100
        self.color = 'Warm White'
        self.current_power_usage = 9
        self.serialNumber = ''

    def update(self):
        pass

class CoffeeMachine:
    def __init__(self, id):
        self.id = id
        self.name = 'CoffeeMachine'
        self.isOn = True
        self.coffee_time = '09:00'
        self.strength = 'Weak'
        self.water_lvl = 89 
        self.add_water = False
        self.current_power_usage = 9
        self.serialNumber = ''

    def update(self):
        pass

def getDataFromCall(url):
    response = requests.get(url)
    return response.text

def getJsonFromCall(url):
    return json.loads(getDataFromCall(url))

def basic_loop(channel):
    # FIXIT(cobileacd)
    print("on_channel_open...")
    
    deviceCount = 0
    devices = []
    while True:
        newDeviceCount = getDataFromCall('http://smart-residence-jdbc:8080/api/getDevicesCount')
        if deviceCount != newDeviceCount:
            deviceCount = newDeviceCount
            devices_json = getJsonFromCall('http://smart-residence-jdbc:8080/api/getDevices')
            devices_json = sum(devices_json, []) # make it flat

            for device in devices_json:
                if device['name'] == 'Vacuum':
                    devices.append(Vacuum(device['id']))
                elif device['name'] == 'LightBulb':
                    devices.append(LightBulb(device['id']))
                elif device['name'] == 'CoffeeMachine':
                    devices.append(CoffeeMachine(device['id']))

        for device in devices:
            if type(device) == Vacuum:
                response = requests.get(f'http://smart-residence-jdbc:8080/api/vacuum_cleaners/{device.id}') 
            elif type(device) == LightBulb:
                response = requests.get(f'http://smart-residence-jdbc:8080/api/light_bulbs/{device.id}') 
            elif type(device) == CoffeeMachine:
                response = requests.get(f'http://smart-residence-jdbc:8080/api/coffee_machines/{device.id}') 
            data = response.text
            device_json = json.loads(data)

            device.isOn = device_json['isOn'] 
            device.serialNumber = device_json['serialNumber']
            device.update()

            channel.basic_publish('', 'test_routing_key', json.dumps(device.__dict__), 
                    pika.BasicProperties(content_type='text/plain', delivery_mode=pika.DeliveryMode.Transient))

        time.sleep(WAIT_TIME)

    connection.close()

connection = pika.BlockingConnection(pika.ConnectionParameters(host='rabbitmq', port=5672))
channel = connection.channel()

channel.queue_declare(queue='test_routing_key', durable=True)
basic_loop(channel)
