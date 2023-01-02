import * as React from 'react';
import Title from './Title';
import Toggle from 'react-toggle';

class LightBulb extends React.Component {
  constructor(props) {
    super(props);
    this.handleOnOff = this.handleChange.bind(this, 'isOn');
    this.componentDidMount = this.componentDidMount.bind(this);

    console.log(this.props);
    this.state = { 
id: this.props.id,
    isOn: true,
    location: 'Living-room',
    brightnessLvl: 100,
    color: 'Red',
    current_power_usage: 9,
    serialNumber: ''
    };
  }

  handleChange(key, event) {
    this.setState({ [key]: event.target.checked })
      fetch("http://localhost:8080/api/light_bulbs/"+ this.state.id + "/"+event.target.checked)
      .then((response) => response.json())
      .then((data) => {
          console.log(data);
          })
      .catch((err) => {
          console.log(err.message);
        });
  }

  componentDidMount() {
    // fetch state of vaccuum
    setInterval(async function() {
        fetch('http://localhost:8080/api/light_bulbs/' + this.state.id)
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
        <Title>Light Bulb</Title>
        <label>
        <Toggle
        checked={this.state.isOn}
        aria-label='label'
        onChange={this.handleOnOff} 
        />
        <span className='label-text'>this.state.isOn: </span>
        <span className='label-text' style={{ color: this.state.isOn ? 'green' : 'red' }}>{JSON.stringify(this.state.isOn)}</span>
        </label>
        <span>this.state.id: {JSON.stringify(this.state.id)}</span>
        <span>this.state.location: {JSON.stringify(this.state.location)}</span>
        <span>this.state.brightnessLvl: {JSON.stringify(this.state.brightnessLvl)}%</span>
        <span>this.state.color: {JSON.stringify(this.state.color)}</span>
        <span>this.state.current_power_usage: {JSON.stringify(this.state.current_power_usage)}</span>
        <span>this.state.serialNumber: {JSON.stringify(this.state.serialNumber)}</span>
        </React.Fragment>
        );
  }
}

export default LightBulb 
