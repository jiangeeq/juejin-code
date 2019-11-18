// 相当于vue中的main.js
// 启动加载一个App 组件
//  1.引入react对象
//  2.引入react DOM对象
//  3. 操作jsx
//     jsx 不能用 += 来运算（不是字符串）
//     jsx 可以通过数组来输出数据
//  4. 渲染到指定的元素上
//     reactDOM.render(<App/>, document.getElementById('root'));
//  5. 启动 npm run dev

import React from 'react';
import ReactDOM from 'react-dom';
import App from './App.js';
ReactDOM.render(<App/>, document.getElementById('root'))