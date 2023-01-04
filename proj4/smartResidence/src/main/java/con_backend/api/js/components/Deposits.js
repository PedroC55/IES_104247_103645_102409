import * as React from 'react';
import Link from '@mui/material/Link';
import Typography from '@mui/material/Typography';
import Title from './Title';

function preventDefault(event) {
  event.preventDefault();
}

export default function Deposits() {
  const [user, setUser] = React.useState("");

  React.useEffect(() => {
	  fetch('http://localhost:8080/api/getUsername')
		  .then((response) => response.json())
		  .then((data) => {
			  console.log(data);
                          setUser(data.username);
		  })
		  .catch((err) => {
			  console.log(err.message);
		  });
  }, []);
  
  return (
    <React.Fragment>
      <Title>Members</Title>
      <Typography component="p" variant="h4">
        {user}
      </Typography>
      <Typography color="text.secondary">
        Mary 
      </Typography>
      <Typography color="text.secondary">
        John 
      </Typography>
      <Link color="primary" href="#" onClick={preventDefault} sx={{ mt: 7 }}>
        See More 
      </Link>
    </React.Fragment>
  );
}
