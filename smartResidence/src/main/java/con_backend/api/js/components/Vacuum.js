import * as React from 'react';
import Title from './Title';
import Toggle from 'react-toggle';
import './style.css'

class Vacuum extends React.Component {
    constructor(props) {
        super(props);
        this.handleOnOff = this.handleChange.bind(this, 'isOn');

        this.state = { 
          isOn: true,
          currentLocation: 'Living-room',
          cleaningMode: 'Quiet',
          remainingBattery: 56,
        };
    }

    handleChange(key, event) {
        this.setState({ [key]: event.target.checked })
    }

    componentDidMount() {
      // fetch state of vaccuum
      // http://localhost:8080/api/vacuum-cleaner
      fetch('http://api.ipma.pt/open-data/distrits-islands.json')
        .then((response) => response.json())
        .then((data) => {
            console.log(data);
        })
        .catch((err) => {
            console.log(err.message);
        });
    }
    
    render() {
        return (
        <React.Fragment>
            <Title>Vacuum</Title>
            <label>
              <Toggle
                  defaultChecked={this.state.isOn}
                  aria-label='label'
                  onChange={this.handleOnOff} 
              />
              <span className='label-text'>this.state.isOn: {JSON.stringify(this.state.isOn)}</span>
            </label>
            <span>this.state.currentLocation: {JSON.stringify(this.state.currentLocation)}</span>
            <span>this.state.cleaningMode: {JSON.stringify(this.state.cleaningMode)}</span>
            <span>this.state.remainingBattery: {JSON.stringify(this.state.remainingBattery)}%</span>
        </React.Fragment>
        );
    }
}

export default Vacuum
