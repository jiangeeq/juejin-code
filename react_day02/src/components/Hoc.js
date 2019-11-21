import React, { Component } from "react";

// 高阶组件
const withName = Comp => {
  // 甚至可以重写组件声明周期
  class NewComponent extends Component {
    componentDidMount() {
      console.log("do something");
    }
    render() {
      return <Comp {...this.props} name="高阶组件试用介绍" />;
    }
  }

  // 假设通过某种特殊手段获取了本节课名字
  return NewComponent;
};

const withLog = Comp => {
  console.log(Comp.name + "渲染了");
  return props => <Comp {...props} />;
};

@withLog
@withName
@withLog
class Jpsite extends Component {
  render() {
    return (
      <div>
        {this.props.stage} - {this.props.name}
      </div>
    );
  }
}

export default Jpsite;
