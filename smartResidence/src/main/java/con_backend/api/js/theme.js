import { red } from '@mui/material/colors';
import { createTheme } from '@mui/material/styles';

// A custom theme for this app
const theme = createTheme({
  palette: {
    primary: {
      main: "#5d001e",
    },
    secondary: {
      main: "#ee4c7c",
    },
    error: {
      main: red.A400,
    },
  },
});

export default theme;
