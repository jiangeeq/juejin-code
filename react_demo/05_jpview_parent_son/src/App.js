import React from 'react';
import Son from './Son.js';

class App extends React.Component{
  constructor(){
    super();
    this.state = {
      age : 1,
      name : 'jpsite'
    }
  }

  render(){
    // 组件的使用必须首字母大写
    // 解构赋值
    let {age, name} = this.state;
    let header = <div>header</div>;
    let foot = <div>foot</div>;
    let text = 'hello world';
    return (
      <div>
        我是App组件，以下使用Son组件：
        <br/>
        <Son age={age} name={name} header={header} foot={foot} text={text}>
          <ul>
            <li>1</li>  
            <li>1</li>  
            <li>1</li>  
          </ul>  
        </Son>
      </div>
    );
  }
}

export default App;
