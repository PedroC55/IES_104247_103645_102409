import * as React from 'react';
import Link from '@mui/material/Link';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Title from './Title';

// Generate Order Data
function createData(type, id, serial_number) {
  return { type, id, serial_number};
}

const table = [];

function preventDefault(event) {
  event.preventDefault();
}

export default function Orders() {
  const [rows, setRows] = React.useState([]);

  React.useEffect(() => {
	  fetch('http://localhost:8080/api/listUserDevices')
		  .then((response) => response.json())
		  .then((data) => {
			  console.log(data);
                          const table = [];
                          for (const device of data) {
                            table.push(createData(device.deviceType, device.deviceId, device.deviceSerialNumber));
                          }
                          setRows(table);
		  })
		  .catch((err) => {
			  console.log(err.message);
		  });
  }, []);

  return (
    <React.Fragment>
      <Title>My Devices</Title>
      <Table size="small">
        <TableHead>
          <TableRow>
            <TableCell>Device</TableCell>
            <TableCell>ID</TableCell>
            <TableCell align="right">Serial Number</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {rows.map((row) => (
            <TableRow key={row.id}>
              <TableCell>{row.type}</TableCell>
              <TableCell>#{row.id}</TableCell>
              <TableCell align="right">{row.serial_number}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
      <Link color="primary" href="#" onClick={preventDefault} sx={{ mt: 3 }}>
        Add new device 
      </Link>
    </React.Fragment>
  );
}
