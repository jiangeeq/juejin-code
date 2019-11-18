// 导入放到最上方, 必须是再顶级作用域
// 导入文件
import Test1 from './test1.js'
// 按需导入
import {num, age} from './test2.js';
// 全体导入
import * as obj from './test3.js';

console.log('我被加载了');
console.log('Test1', Test1);
console.log('按需导入', num, age);
console.log('全体导入', obj);