import React, { Component } from "react";

//Dialog
function Dialog(props) {
  return (
    <div style={{ border: `4px solid ${props.color || "blue"}` }}>
      {/* 等效vue中匿名插槽 */}
      {props.children}
      {/* 等效vue中具名插槽 */}
      <div className="abc">{props.footer}</div>
    </div>
  );
}

function WelcomeDialog() {
  const confirmBtn = (
    <button onClick={() => alert("react确实好！")}>确定</button>
  );
  return (
    <Dialog color="green" footer={confirmBtn}>
      <h1>欢迎光临</h1>
      <p>感谢使用react！！！</p>
    </Dialog>
  );
}

export default class Composition extends Component {
  render() {
    return (
      <div>
        <WelcomeDialog />
      </div>
    );
  }
}
