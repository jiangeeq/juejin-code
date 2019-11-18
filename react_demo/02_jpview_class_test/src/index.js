class Person{
    age = 100;
    constructor(props){
        this.age = props.age;
        console.log('触发了Persno');
    }
}

class Boy extends Person{
    name = 'jpview';
    constructor(props){
        super(props);  // 初始化父类构造器
        this.name = props.name;
        console.log('触发了Boy');
    }
}

let boy = new Boy({name:'jpsite', age:22});
console.log(boy);