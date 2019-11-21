import React, { Component } from "react";
import logo from "./logo.png";
import "./App.css";
import {Button} from 'antd'

// 函数型组件传递props
function Welcome1(props) {
  return <div>Hello, {props.name}</div>;
}

export default class App extends Component {
  // 1.当需要状态时，需要构造函数
  constructor(props) {
    super(props);

    // 2.初始化状态
    this.state = {
      count: 0,
      date: new Date()
    };
  }

  componentDidMount() {
    this.timer = setInterval(() => {
      // 3.更新状态
      this.setState({
        date: new Date(),
        count: this.state.count + 1
      });

      //   注意1：不能直接改状态
      // this.state.date = new Date();//错误
    }, 1000);
    
    // 注意2：setState()异步的
    this.setState((prevState, prevProps)=>({
        count: prevState.count + 1
    }), () => {
        console.log(this.state.count);
    });
    // console.log(this.state.count);
  }

  componentWillUnmount() {
    clearInterval(this.timer);
  }

  formatName(user) {
    return user.firstName + " " + user.lastName;
  }

  render() {
    const name = "jerry";
    // jsx本身也是表达式
    const jsx = <p>hello,同学们</p>;
    return (
      <div>
        {/* antd试用 */}
        <Button type="primary">button</Button>
        App组件
        {/* 表达式 */}
        <h1>{name}</h1>
        <p>{this.formatName({ firstName: "tom", lastName: "jerry" })}</p>
        {/* 属性 */}
        <img src={logo} style={{ width: "100px" }} className="img" />
        {/* jsx也是表达式 */}
        {jsx}
        {/* 组件属性传值: 传入属性是只读的 */}
        <Welcome1 name="tom" />
        {/* 使用状态 */}
        <p>{this.state.date.toLocaleTimeString()}</p>
      </div>
    );
  }
}
