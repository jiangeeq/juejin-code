import React, { Component } from "react";
import { Icon } from "antd";

// hoc：包装用户表单，增加数据管理能力、校验
function kFormCreate(Comp) {
  return class NewComp extends Component {
    constructor(props) {
      super(props);
      this.options = {}; //字段选项设置
      this.state = {}; //各字段值
    }

    // 处理表单项输入事件
    handleChange = e => {
      const { name, value } = e.target;
      this.setState(
        {
          [name]: value
        },
        () => {
          // 数值变化后再校验
          this.validateField(name);
        }
      );
    };

    // 表单项校验
    validateField = field => {
      const rules = this.options[field].rules;
      //只要任何一项失败就失败
      const ret = rules.some(rule => {
        if (rule.required) {
          //仅验证必填项
          if (!this.state[field]) {
            // 校验失败
            this.setState({
              [field + "Message"]: rule.message
            });
            return true; // 若有校验失败，返回true
          }
        }
      });
      if (!ret) {
        // 没失败，校验成功
        this.setState({ [field + "Message"]: "" });
      }
      return !ret;
    };

    // 校验所有字段
    validate = cb => {
      const rets = Object.keys(this.options).map(field =>
        this.validateField(field)
      );
      // 如果校验结果数组中全部为true，则校验成功
      const ret = rets.every(v => v === true);
      cb(ret);
    };

    getFieldDec = (field, option, InputComp) => {
      this.options[field] = option;

      return (
        <div>
          {React.cloneElement(InputComp, {
            name: field, //控件name
            value: this.state[field] || "", //控件值
            onChange: this.handleChange, //change事件处理
            onFocus: this.handleFocus // 判断控件是否获得焦点
          })}
          {/* {this.state[field + "Message"] && (
            <p style={{ color: "red" }}>{this.state[field + "Message"]}</p>
          )} */}
        </div>
      );
    };

    //
    handleFocus = e => {
      const field = e.target.name;
      this.setState({
        [field + "Focus"]: true
      });
    };
    // 判断组件是否被用户点过
    isFieldTouched = field => !!this.state[field + "Focus"];

    getFieldError = field => this.state[field + "Message"];

    render() {
      return (
        <Comp
          {...this.props}
          getFieldDec={this.getFieldDec}
          value={this.state}
          validate={this.validate}
          isFieldTouched={this.isFieldTouched}
          getFieldError={this.getFieldError}
        />
      );
    }
  };
}

class FormItem extends Component {
  render() {
    return (
      <div className="formItem">
        {this.props.children}
        {this.props.validateStatus === "error" && (
          <p style={{ color: "red" }}>{this.props.help}</p>
        )}
      </div>
    );
  }
}

class KInput extends Component {
  render() {
    return (
      <div>
        {/* 前缀图标 */}
        {this.props.prefix}
        <input {...this.props} />
      </div>
    );
  }
}

@kFormCreate
class KFormSample extends Component {
  onSubmit = () => {
    this.props.validate(isValid => {
      if (isValid) {
        alert("校验成功，提交登录");
        console.log(this.props.value);
      } else {
        alert("校验失败");
      }
    });
  };

  render() {
    const { getFieldDec, isFieldTouched, getFieldError } = this.props;

    const userNameError = isFieldTouched("uname") && getFieldError("uname");
    const passwordError = isFieldTouched("pwd") && getFieldError("pwd");

    return (
      <div>
        <FormItem
          validateStatus={userNameError ? "error" : ""}
          help={userNameError || ""}
        >
          {getFieldDec(
            "uname",
            {
              rules: [{ required: true, message: "请填写用户名" }]
            },
            <KInput type="text" prefix={<Icon type="user" />} />
          )}
        </FormItem>
        <FormItem
          validateStatus={passwordError ? "error" : ""}
          help={passwordError || ""}
        >
          {getFieldDec(
            "pwd",
            {
              rules: [{ required: true, message: "请填写用户名" }]
            },
            <KInput type="password" prefix={<Icon type="lock" />} />
          )}
        </FormItem>

        <button onClick={this.onSubmit}>登录</button>
      </div>
    );
  }
}

export default KFormSample;
