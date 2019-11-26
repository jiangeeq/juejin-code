import { Button, Input, Select, Timeline } from 'antd';
import React from 'react';
import ReactDOM from 'react-dom';
import $ from 'jquery'
import '../App.css';

const { Option } = Select;

// 展示组件
function JTimeline({ dataList }) {
    return (
        <div>
            <Timeline mode='left'>
                {(dataList || []).map((data, i) => (
                    <Timeline.Item key={i}>{data.time}<br />{data.status}</Timeline.Item>
                ))}
            </Timeline>
        </div>
    )
}

function onBlur() {
    console.log('blur');
}

function onFocus() {
    console.log('focus');
}

function onSearch(val) {
    console.log('search:', val);
}

class Express extends React.Component {
    onSelect = LabeledValue => {
        this.setState({
            no: LabeledValue
        });
    };

    selectKind = value => {
        this.setState({
            kind: value
        });
    };

    inputPhone = e => {
        let expressNoList = []
    
        $.ajax({
            async:false,    // 这个需要写上
            url:`http://localhost:8080/getAllNo?name=${this.state.name}&phone=${e.target.value}`,
            type:'GET',
            dataType:'json',
            success:function (data) {
                expressNoList =  data.noList;
            }
        });

        console.log("expressNoList: "+ expressNoList)
        this.setState({
            phone: e.target.value,
            noList: expressNoList
        })
    };
    
    intputName = e =>{
        let expressNoList = []
    
        $.ajax({
            async:false,    // 这个需要写上
            url:`http://localhost:8080/getAllNo?name=${e.target.value}&phone=${this.state.phone}`,
            type:'GET',
            dataType:'json',
            success:function (data) {
                expressNoList =  data.noList;
            }
        });

        console.log("expressNoList: "+ expressNoList)
        this.setState({
            name: e.target.value,
            noList: expressNoList
        })
    };

    constructor(props) {
        super(props);
        this.state = {
            no: "",
            name: "",
            phone: "",
            noList: [],
            kind: "",
            dataList: []
        }
    }

    render() {
        console.log("render" + this.state.dataList);
        return (
            <div className="App">
                <p />
                <Input placeholder="请输入姓名" value={this.state.name} style={{width: 180}} onChange={e => this.intputName(e)} />
                <p/>
                <Input placeholder="输入手机号码" value={this.state.phone} style={{ width: 180 }} onChange={e => this.inputPhone(e)} />
                <p />
                <Select
                    showSearch
                    style={{ width: 180 }}
                    placeholder="输入运单号"
                    optionFilterProp="children"
                    onFocus={onFocus}
                    onBlur={onBlur}
                    onSelect={this.onSelect}
                    onChange={this.onChange}
                    onSearch={onSearch}
                    filterOption={(input, option) =>
                        option.props.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
                    }
                >
                {(this.state.noList || []).map((data, i) => (
                    <Option key={i} value={data}>{data}</Option>
                ))}

                </Select>
                <p />
                <Select placeholder="请选择快递公司" style={{ width: 180 }} onChange={value => this.selectKind(value)}>
                    <Option value="">查所有</Option>
                    <Option value=".申通">申通</Option>
                    <Option value=".圆通">圆通</Option>
                    <Option value=".中通">中通</Option>
                    <Option value=".韵达">韵达</Option>
                </Select>
                <p />
                <Button type="primary" onClick={e => this.query()}> 查询</Button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <Button type="danger" onClick={e => { this.clear() }}>取消</Button>
                <p />
                <JTimeline dataList={this.state.dataList} />
            </div>
        )
    }

    clear = () => {
        this.setState({
            no: "",
            noList: [],
            name: "",
            phone: "",
            kind: "",
            dataList: []
        })
    }

    query = () => {
        const { no, kind } = { ...this.state }
        console.log("运单号：" + no + "  物流公司：" + kind);

        let ajaxPromise = new Promise(function (resolve, reject) {
            $.ajax({
                url: 'http://wuliu.market.alicloudapi.com/kdi?no=' + no + "&type=" + kind,
                type: 'GET',
                dataType: 'json',
                success(arr) {
                    resolve(arr);
                },
                error(err) {
                    reject(err);
                },
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("Authorization", "APPCODE f3a8782beb682")
                }
            });
        });
        ajaxPromise.then(arr => {
            if (arr.status === "201") {
                alert('快递查询失败--' + arr.msg);
                return;
            }
            console.log("快递列表：" + arr.result.list);
            this.setState({ dataList: arr.result.list })
        }, err => {
            alert('快递查询失败--' + err);
        });
    }
}

export default Express;
