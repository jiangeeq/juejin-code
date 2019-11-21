import React, { Component, PureComponent } from "react";

// shouldComponentUpdate的加强版
class PureComponentTest extends PureComponent {
    constructor(props) {
        super(props);
        this.state = {
            comments: [
                { body: "react is very good", author: "facebook" },
                { body: "vue is very good", author: "youyuxi" }
            ]
        };
    }
    shouldComponentUpdate(nextProps) {
        if (
            nextProps.data.body === this.props.data.body &&
            nextProps.data.author === this.props.data.author
        ) {
            return false;
        }
        return true;
    }

    render() {
        console.log("render");
        return (
            <div>
                <p>{this.props.body}</p>
                <p>------{this.props.author}</p>
            </div>
        );
    }
}

export default PureComponentTest;