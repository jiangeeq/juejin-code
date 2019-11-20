import React, {Component} from 'react';
import logo from './logo.svg';
import './App.css';

class App extends Component{
  componentWillUpdate(){
    console.log('b-componentWillUpdate');
  }

  shouldComponentUpdate(){
    console.log('a-shouldComponentUpdate');
    // true 控制更新
    return true;
  }
  componentDidUpdate(){
    console.log('c-componentDidUpdate');
  }

  componentDidMount(){
    // 推荐在此处发网络请求，据网上流传会引发二次 render，但我没遇到过
    console.log('4-componentDidMount');
  }

  componentWillMount(){  // 官方不推荐在此处发请求，原因可能会造成渲染阻塞
    console.log('2-componentWillMount');
  }

  constructor(){
    console.log('1-constructor');
    super();
    this.state = {
      num: 1
    };
  }

  render(){
    console.log('3-render');
    return(
      <div>
        {this.state.num}
        <br/>
        <button onClick={e=>this.setState({num:99})}>更改数据</button>
      </div>
    )
  }
}

export default App;
