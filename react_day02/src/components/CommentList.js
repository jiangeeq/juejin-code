import React, { Component, PureComponent } from "react";

const Comment = React.memo(({ body, author }) => {
  console.log("render");

  return (
    <div>
      <p>{body}</p>
      <p>------{author}</p>
    </div>
  );
});

export default class CommentList extends Component {
  constructor(props) {
    super(props);

    this.state = {
      comments: []
    };
  }

  componentDidMount() {
    setInterval(() => {
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
          <Comment key={i} {...c} />
        ))}
      </div>
    );
  }
}
