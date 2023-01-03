import * as React from 'react';
import Title from './Title';
import Toggle from 'react-toggle';

class Refrigerator extends React.Component {
  constructor(props) {
    super(props);
    this.componentDidMount = this.componentDidMount.bind(this);

    console.log(this.props);
    this.state = { 
      id: this.props.id,
      airFilter_change_date: '01/02/2023',
      content_list: '[]',
      current_power_usage: 250,
      serialNumber: ''
    };
  }

  componentDidMount() {
    // fetch state of vaccuum
    setInterval(async function() {
        fetch('http://localhost:8080/api/refrigerators/' + this.state.id)
        .then((response) => response.json())
        .then((data) => {
            console.log(data);
            this.setState(data);
            })
        .catch((err) => {
            console.log(err.message);
            });
        }.bind(this), Math.random() * (2500 - 1500) + 1500);
  }

  render() {
    return (
        <React.Fragment>
        <Title>Refrigerator</Title>
        <span>this.state.id: {JSON.stringify(this.state.id)}</span>
        <span>this.state.airFilter_change_date: {JSON.stringify(this.state.airFilter_change_date)}</span>
        <span>this.state.content_list: {JSON.stringify(this.state.content_list)}</span>
        <span>this.state.current_power_usage: {JSON.stringify(this.state.current_power_usage)}</span>
        </React.Fragment>
        );
  }
}

export default Refrigerator 
