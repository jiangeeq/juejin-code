import React from 'react';
import PropTypes from 'prop-types';

class Son extends React.Component {
    // 属性的约定和默认值
    static propTypes = {
        text: PropTypes.string.isRequired
    };
    static defaultProps = {
        text: 'default text'
    };

    constructor(props) {
        super(props);
    }

    render() {
        console.log(this.props);
        // 声明一个age,name属性， this.props 中的同名属性进行赋值
        let { age, name, text } = this.props;
        // 声明一个引用类型属性， this.props 中的同名属性的地址进行赋值
        let { obj } = this.props;
        console.log(this.props.children);

        return (
            <div>
                我是子组件，数据如下：
                 <hr />
                {text}
                 <hr/>
                {name} {age}
                <hr />

                <div style={{ backgroundColor: 'red' }}>
                    {this.props.header}
                </div>

                {this.props.children}

                <div style={{ backgroundColor: 'green' }}>
                    {this.props.foot}
                </div>
            </div>

        );
    }
}

export default Son;
