function obj(){
}

obj.prototype.age = 1;  // 实例属性
let o = new obj();
obj.staticNum = 123;  // 静态属性

// class
// 实例可以访问静态属性， 静态无法访问实例（静态熟悉比实例先加载初始化）
class obj1{
    myage = 123;
    static myStaticNum = 555;  // 静态属性
    static staticFun = function(){
        console.log(this);   // 构造函数对象
        console.log('静态函数');
    }

    // 箭头函数才会向上用别人的this
    myFn(){
        // 上下问this与function是一致的，用的就是自己的
        console.log('实例的函数', this.myage);
    }
}

let o1 = new obj1();

console.log(o1);
o1.myFn();
console.log('静态属性：',obj1.myStaticNum)
obj1.staticFun();