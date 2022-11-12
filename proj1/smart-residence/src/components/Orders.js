import * as React from 'react';
import Link from '@mui/material/Link';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Title from './Title';

// Generate Order Data
function createData(id, name, location) {
  return { id, name, location };
}

const rows = [
  createData(
    0,
    'Light #1',
    'Kitchen',
  ),
  createData(
    1,
    'Light #2',
    'Bedroom',
  ),
  createData(
    2,
    'Vacuum cleaner',
    'Living room',
  ),
  createData(
    3,
    'Smart Purifier',
    'Hall',
  ),
  createData(
    4,
    'Refrigerator',
    'Kitchen',
  ),
];

function preventDefault(event) {
  event.preventDefault();
}

export default function Orders() {
  return (
    <React.Fragment>
      <Title>My Devices</Title>
      <Table size="small">
        <TableHead>
          <TableRow>
            <TableCell>Name</TableCell>
            <TableCell align="right">Location</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {rows.map((row) => (
            <TableRow key={row.id}>
              <TableCell>{row.name}</TableCell>
              <TableCell align="right">{row.location}</TableCell>
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
