import React, { Component } from "react";

// 1. 创建上下文
const Context = React.createContext();

const store = {
  // 指定6611尾号用户中奖
  name: "恭喜你中到了一等奖",
  sayHi() {
    console.log(this.name);
  }
};

const withProvider = Comp => props => (
  <Context.Provider value={store}>
    <Comp {...props} />
  </Context.Provider>
);

const withConsumer = Comp => props => (
  <Context.Consumer>
    {/* 必须内嵌一个函数 */}
    {value => <Comp {...props} value={value} />}
  </Context.Consumer>
);

@withConsumer
class Inner extends Component {
  render() {
    console.log('Inner');
    return <div>{this.props.value.name}</div>;
  }
}

@withProvider
class ContextSample extends Component {
  render() {
    console.log('ContextSample');
    return <div><Inner></Inner></div>;
  }
}

export default ContextSample