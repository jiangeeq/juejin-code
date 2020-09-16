import Taro, { Component } from '@tarojs/taro'
import { View, Text, Button, Input } from '@tarojs/components'
import './index.scss'

import { observer, inject } from '@tarojs/mobx'
import { AtButton, AtInput, AtList, AtListItem } from '_taro-ui@2.3.3@taro-ui'

@inject('todoStore')
@observer
export default class Index extends Component {

  config = {
    navigationBarTitleText: 'TodoList'
  }

  constructor(props) {
    super(props)
    this.state = {
      //todos: ['吃饭', '睡觉', '打豆豆'],
      val: ''
    }
  }

  handleInput = (val) => {
    console.log(val)
    // this.setState({
    //   val: e.target.value
    // })
    this.setState({ val })
  }

  handleClick = () => {
    if (this.state.val) {
      // this.setState({
      //   todos: [...this.state.todos, this.state.val],
      //   val: ''
      // })
      this.props.todoStore.addTodo(this.state.val)
      this.setState({ val: '' })
    }
  }

  handleChange = (i)=> {
    this.props.todoStore.removeTodo(i)
  }

  render() {
    const { todoStore } = this.props

    return (
      // <View className='index'>
      //   <Text>第一个小练习</Text>
      //   <Input value={this.state.val} onchange={this.handleInput}></Input>
      //   <Button onClick={this.handleClick}>添加</Button>
      //   {
      //     this.state.todos.map((item, i) => {
      //       return <View><Text>{i + 1}: {item}</Text></View>
      //     })
      //   }
      // </View>
      <View className='index'>
        <Text>第一个小练习</Text>
        <AtInput value={this.state.val} onChange={this.handleInput}></AtInput>
        <AtButton type='primary' onClick={this.handleClick}>添加</AtButton>
        <AtList>
          {
            todoStore.todos.map((item, i) => {
              return <AtListItem
                title={i + 1 + ':' + item}
                isSwitch
                onSwitchChange={() => this.handleChange(i)}>
              </AtListItem>
            })
          }
        </AtList>
      </View>
    )
  }
}
