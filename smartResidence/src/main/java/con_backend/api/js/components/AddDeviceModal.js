import React, { useState } from 'react';

// Generate Order Data
function createData(type, id, serial_number) {
  return { type, id, serial_number};
}

const AddDeviceModal = (props) => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedDevice, setSelectedDevice] = useState('Vacuum');

  const handleAddDeviceClick = () => {
    setIsModalOpen(true);
  };

  const handleCloseModalClick = () => {
    setIsModalOpen(false);
  };

  const handleDeviceSelect = (event) => {
    setSelectedDevice(event.target.value);
  };

  const handleAddButtonClick = () => {
    fetch('http://localhost:8080/api/createDevice/' + selectedDevice)
      .then((response) => response.json())
      .then((data) => {
              console.log(data);
              const table = [];
              for (const device of data) {
                table.push(createData(device.deviceType, device.deviceId, device.deviceSerialNumber));
              }
              props.updateDeviceTable(table);
      })
      .catch((err) => {
              console.log(err.message);
      });
    setIsModalOpen(false);
  };

return (
    <React.Fragment>
    <button onClick={handleAddDeviceClick}>Add Device</button>
    {isModalOpen && (
        <div>
          <select onChange={handleDeviceSelect}>
            <option value="Vacuum">Vacuum</option>
            <option value="Refrigerator">Refrigerator</option>
            <option value="LightBulb">Light Bulb</option>
            <option value="CoffeeMachine">Coffee Machine</option>
          </select>
          <button onClick={handleAddButtonClick}>Add</button>
          <button onClick={handleCloseModalClick}>Close</button>
        </div>
        )}
    </React.Fragment>
    );
};

export default AddDeviceModal;
