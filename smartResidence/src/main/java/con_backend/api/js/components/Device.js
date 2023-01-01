import * as React from 'react';
import Title from './Title';
import Toggle from 'react-toggle';

class Device extends React.Component {
    constructor(props) {
        super(props);
        this.handleOnOff = this.handleChange.bind(this, 'isOn');
        this.componentDidMount = this.componentDidMount.bind(this);

        console.log(this.props);
        this.state = { 
          id: this.props.id,
          deviceJson: { name: 'none', isOn: false}  // fields exist in every device
        };
    }

    handleChange(key, event) {
        this.setState({ [key]: event.target.checked })
        fetch("http://localhost:8080/api/vacuum_cleaners/"+ this.state.id + "/"+event.target.checked)
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
        fetch('http://localhost:8080/api/vacuum_cleaners/' + this.state.id)
          .then((response) => response.json())
          .then((data) => {
              this.setState({deviceJson: data});
          })
          .catch((err) => {
              console.log(err.message);
          });
      }.bind(this), Math.random() * (2500 - 1500) + 1500);
    }
    
    render() {
        return (
        <React.Fragment>
            <Title>{this.state.deviceJson["name"]}</Title>
            <label>
              <Toggle
                  checked={this.state.deviceJson["isOn"]}
                  aria-label='label'
                  onChange={this.handleOnOff} 
              />
              <span className='label-text'>this.state.deviceJson["isOn"]: </span>
              <span className='label-text' style={{ color: this.state.deviceJson["isOn"] ? 'green' : 'red' }}>{JSON.stringify(this.state.deviceJson["isOn"])}</span>
            </label>
            {Object.keys(this.state.deviceJson).slice(1).map(key => (
              <span key={key}>{`${key}: ${this.state.deviceJson[key]}`}</span>
            ))}
        </React.Fragment>
        );
    }
}

export default Device 
