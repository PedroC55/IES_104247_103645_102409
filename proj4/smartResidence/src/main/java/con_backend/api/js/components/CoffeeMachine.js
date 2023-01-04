import * as React from 'react';
import Title from './Title';
import Toggle from 'react-toggle';

class CoffeeMachine extends React.Component {
  constructor(props) {
    super(props);
    this.handleOnOff = this.handleChange.bind(this, 'isOn');
    this.componentDidMount = this.componentDidMount.bind(this);

    console.log(this.props);
    this.state = { 
        id: this.props.id,
        isOn: true,
        coffee_time: 'Living-room',
        strength: 'Weak',
        water_lvl: 56,
        add_water: false,
        current_power_usage: 750,
        serialNumber: ''
    };
  }

  handleChange(key, event) {
    this.setState({ [key]: event.target.checked })
      fetch("http://localhost:8080/api/coffee_machines/"+ this.state.id + "/"+event.target.checked)
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
        fetch('http://localhost:8080/api/coffee_machines/' + this.state.id)
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
        <Title>Coffee Machine</Title>
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
        <span>this.state.coffe_time: {JSON.stringify(this.state.coffee_time)}</span>
        <span>this.state.strength: {JSON.stringify(this.state.strength)}</span>
        <span>this.state.water_lvl: {JSON.stringify(this.state.water_lvl)}%</span>
        <span>this.state.add_water: {JSON.stringify(this.state.add_water)}</span>
        <span>this.state.current_power_usage: {JSON.stringify(this.state.current_power_usage)}</span>
        </React.Fragment>
        );
  }
}

export default CoffeeMachine 
