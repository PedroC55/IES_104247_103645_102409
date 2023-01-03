import * as React from 'react';
import { useTheme } from '@mui/material/styles';
import Title from './Title';
import Chart from 'react-apexcharts';

export default function Graph() {
  const theme = useTheme();
  const [data, setData] = React.useState([]);
  const [lastDate, setLastDate] = React.useState(0);

  function getNewSeries(baseval, ydata) {
      var newDate = baseval + 86400000;
      setLastDate(newDate);
      var tmp = [...data];
      tmp.push({
          x: newDate,
          y: ydata
      });
      setData(tmp);
  }

  const chart = {
      options: {
      chart: {
        id: "consumption-graph",
        type: "line",
        animations: {
          enabled: true,
          easing: 'linear',
          dynamicAnimation: {
            speed: 1000
          }
       },
       toolbar: {
          show: false
       },
       zoom: {
          enabled: false
       }
     },
     dataLabels: {
        enabled: false
     },
     stroke: {
        curve: 'smooth' 
     },
     markers: {
        size: 0
     },
      xaxis: {
        type: 'datetime',
        range: 777600000 
     },
     ymaxis: {
        max: 100
     },
     legend: {
        show: false
     },
     colors: [
        "#5d001e"
     ]
   },
   series: [
    {
      data: data.slice()
    }
   ]
 };

  React.useEffect(() => {
    const interval = setInterval(function() {
        fetch('http://localhost:8080/api/getPowerConsumption/')
          .then((response) => response.json())
          .then((data) => {
            console.log(data);
            getNewSeries(lastDate, data);
          })
          .catch((err) => {
                console.log(err.message);
              });
        }, 1000);
    return () => clearInterval(interval);
  }, [data, lastDate]);
    

  return (
    <React.Fragment>
      <Title>Power Consumption</Title>
      <Chart
        options={chart.options}
        series={chart.series}
        height="90%"
      />
    </React.Fragment>
  );
}
