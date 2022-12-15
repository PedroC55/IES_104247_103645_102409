import * as React from 'react';
import Title from './Title';
import Toggle from 'react-toggle';

class Vacuum extends React.Component {
    constructor(props) {
        super(props);
        this.handleOnOff = this.handleChange.bind(this, 'isOn');
        this.componentDidMount = this.componentDidMount.bind(this);

        this.state = { 
          id: 0,
          isOn: true,
          currentLocation: 'Living-room',
          cleaningMode: 'Quiet',
          remainingBattery: 56,
          serialNumber: ''
        };
    }

    handleChange(key, event) {
        this.setState({ [key]: event.target.checked })
        fetch("http://localhost:8080/api/vacuum_cleaners/1/"+event.target.checked)
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
        fetch('http://localhost:8080/api/vacuum_cleaners/1')
          .then((response) => response.json())
          .then((data) => {
              console.log(data);
              this.setState(data);
          })
          .catch((err) => {
              console.log(err.message);
          });
      }.bind(this), 1500);
    }
    
    render() {
        return (
        <React.Fragment>
            <Title>Vacuum</Title>
            <label>
              <Toggle
                  checked={this.state.isOn}
                  aria-label='label'
                  onChange={this.handleOnOff} 
              />
              <span className='label-text'>this.state.isOn: {JSON.stringify(this.state.isOn)}</span>
            </label>
            <span>this.state.id: {JSON.stringify(this.state.id)}</span>
            <span>this.state.currentLocation: {JSON.stringify(this.state.currentLocation)}</span>
            <span>this.state.cleaningMode: {JSON.stringify(this.state.cleaningMode)}</span>
            <span>this.state.remainingBattery: {JSON.stringify(this.state.remainingBattery)}%</span>
        </React.Fragment>
        );
    }
}

export default Vacuum
