import React, { Component } from "react";

function Cart(props) {
  return (
    <table>
      <tbody>
        {props.data.map(d => (
          <tr key={d.text}>
            <td>{d.text}</td>
            <td>
              <button>-</button>
              {d.count}
              <button onClick={() => props.addCount(d)}>+</button>
            </td>
            <td>￥{d.price * d.count}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}

export default class CartSample extends Component {
  constructor(props) {
    super(props);

    this.state = {
      goods: [
        { id: 1, text: "蒋老湿精品图书-十分钟学编程", price: 100 },
        { id: 2, text: "蒋老湿精品图书-轻松学Java", price: 33 },
        { id: 2, text: "蒋老湿精品图书-轻松学React", price: 66 }

      ],
      text: "", // 商品名
      cart: [],
      history: [] // 时间旅行
    };

    // 回调写法1
    // this.addGood = this.addGood.bind(this);
  }
  //   写法2
  addGood = () => {
    this.setState(prevState => ({
      goods: [...prevState.goods, { id: 3, text: prevState.text, price: 666 }]
    }));
  };

  textChange = e => {
    this.setState({
      text: e.target.value
    });
  };

  addToCart(good) {
    // 为了保证视图更新，数组要保证引用地址变化，因此每次深复制一个新数组
    const newCart = [...this.state.cart];
    // 查询商品对应购物项是否存在
    const idx = newCart.findIndex(c => c.text === good.text);
    const item = newCart[idx];

    if (item) {
      // 更新对象也想要全新对象，这里不是必须的
      newCart.splice(idx, 1, { ...item, count: item.count + 1 });
    } else {
      // 新增
      newCart.push({ ...good, count: 1 });
    }
    this.setState({ cart: newCart, history: [...this.state.history, newCart] });
  }

  addCount = item => {
    const newCart = [...this.state.cart];
    const idx = newCart.findIndex(c => c.text === item.text);
    newCart.splice(idx, 1, { ...item, count: item.count + 1 });
    this.setState({ cart: newCart, history: [...this.state.history, newCart] });
  };

  //   时间旅行
  go(his) {
    this.setState({
      cart: his
    });
  }

  render() {
    const title = this.props.title ? <h1>{this.props.title}</h1> : null;
    // 循环：将js对象数组转换为jsx数组
    const goods = this.state.goods.map(good => (
      <li key={good.id}>
        {good.text}
        <button onClick={() => this.addToCart(good)}>加购</button>
      </li>
    ));
    return (
      <div>
        {/* 条件语句 */}
        {title}
        {/* 添加商品 */}
        <div>
          <input
            type="text"
            value={this.state.text}
            onChange={e => this.textChange(e)}
          />
          <button onClick={this.addGood}>添加商品</button>
        </div>
        {/* 列表渲染 */}
        <ul>{goods}</ul>

        {/* 购物车 */}
        {/* 传递函数用于子组件和父组件交互 */}
        <Cart data={this.state.cart} addCount={this.addCount} />

        {/* 时光机 */}
        {this.state.history.length > 0 ? <p>时间旅行</p> : null}
        {this.state.history.map((his, i) => (
          <button key={i} onClick={() => this.go(his)}>
            {i + 1}
          </button>
        ))}
      </div>
    );
  }
}
