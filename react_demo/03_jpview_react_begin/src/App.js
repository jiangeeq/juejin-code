// 1. 使用jsx必须引入react
// 2. 组件要继承 React.Component

import React, {Component} from 'react';

class App extends Component{
  constructor(){
    super();
    // 初始化属于组件的属性
    this.state = {
      num: 1
    }
  }

  changeHandle(e){
    console.log(e.target.value);
    console.log('change触发了');
    //this.state.num = e.target.value;
    // 通知视图更新函数
    //this.setState({});
    this.setState({
      num: e.target.value
    });
  }

  //指定render内容
  render(){
    // 保证有一个根节点
    return(
      <div>hello world!
        <br/>
        {this.state.num}
        <br/>
        <input type='input' value={this.state.num} onChange={(e)=>{this.changeHandle(e)}}/>
      </div>
    );
  }
}


export default App;
