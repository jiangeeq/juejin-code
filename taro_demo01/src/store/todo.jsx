import { observable } from 'mobx'
import Taro from '@tarojs/taro'

const todoStore = observable({
    todos: ['吃饭', '睡觉', '打豆豆'],
    addTodo(item) {
        this.todos.push(item)
    },
    removeTodo(i){
        Taro.showLoading({
            title:'loading'
        })
        setTimeout(() => {
            this.todos.splice(i,1)
            Taro.hideLoading()
        }, 1000);
    }
    
})

export default todoStore