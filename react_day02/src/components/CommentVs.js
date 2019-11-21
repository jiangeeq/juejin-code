import React, { Component } from "react";
// 容器组件
export default class CommentVs extends Component {
    constructor(props) {
        super(props);
        this.state = {
            comments: []
        };
    }
    // 生命周期函数
    componentDidMount() {
        setTimeout(() => {
            this.setState({
                comments: [
                    { body: "react is very good", author: "facebook" },
                    { body: "vue is very good", author: "youyuxi" }
                ]
            });
        }, 1000);
    }
    render() {
        return (
            <div>
            {this.state.comments.map((c, i) => (
                <Comment key={i} data={c} />
            ))}
            </div>
        );
    }
}

// 展示组件
function Comment({ data }) {
    return (
        <div>
        <p>{data.body}</p>
        <p> --- {data.author}</p>
        </div>
    );
}