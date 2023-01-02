CREATE TABLE IF NOT EXISTS users (
        id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(32) NOT NULL,
        email VARCHAR(64) NOT NULL,
        password VARCHAR(32) NOT NULL
);

CREATE TABLE IF NOT EXISTS devices (
        id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(32) NOT NULL
);

CREATE TABLE IF NOT EXISTS vacuum_cleaners (
        id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
        isOn BOOLEAN NOT NULL,
        currentLocation VARCHAR(32) NOT NULL,
        cleaningMode VARCHAR(16) NOT NULL,
        remainingBattery INT(3) UNSIGNED NOT NULL,
        serialNumber VARCHAR(64) NOT NULL
);

CREATE TABLE IF NOT EXISTS user_device (
        id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
        user_id INT(6) UNSIGNED,
        device_id INT(6) UNSIGNED,
        device_type VARCHAR(16),
        device_serial_number VARCHAR(32),
);

CREATE TABLE IF NOT EXISTS light_bulbs (
        id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
        isOn BOOLEAN NOT NULL,
        location VARCHAR(32) NOT NULL,
        brightnessLvl INT(6) NOT NULL,
        color VARCHAR(3) UNSIGNED NOT NULL,
        current_power_usage INT(6) NOT NULL,
        serialNumber VARCHAR(64) NOT NULL
);

CREATE TABLE IF NOT EXISTS thermostats (
        id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
        isOn BOOLEAN NOT NULL,
        location VARCHAR(32) NOT NULL,
        temperature INT(6) NOT NULL,
        current_power_usage INT(6) NOT NULL,
        serialNumber VARCHAR(64) NOT NULL
);

CREATE TABLE IF NOT EXISTS refrigerators (
        id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
        airFilter_change_date VARCHAR(16) NOT NULL,
        content_list VARCHAR(128) NOT NULL,
        current_power_usage INT(6) NOT NULL,
        serialNumber VARCHAR(64) NOT NULL
);

CREATE TABLE IF NOT EXISTS movement_sensors (
        id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
        isOn BOOLEAN NOT NULL,
        location VARCHAR(32) NOT NULL,
        trigger_dates VARCHAR(128) NOT NULL,
        current_power_usage INT(6) NOT NULL,
        serialNumber VARCHAR(64) NOT NULL
);

CREATE TABLE IF NOT EXISTS coffee_machines (
        id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
        isOn BOOLEAN NOT NULL,
        coffe_time VARCHAR(32) NOT NULL,
        strength INT(6) NOT NULL,
        water_lvl INT(6) UNSIGNED NOT NULL,
        add_water BOOLEAN NOT NULL,
        current_power_usage INT(6) NOT NULL,
        serialNumber VARCHAR(64) NOT NULL
);
