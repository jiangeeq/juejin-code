import { Button, Input, Select, Timeline } from 'antd';
import React from 'react';
import ReactDOM from 'react-dom';
import $ from 'jquery'
import '../App.css';

const { Option } = Select;

class JPButton extends React.Component {
    constructor(props) {
        super(props);
    }

    query = () => {
        const { no, kind } = { ...this.props.data }
        console.log("运单号：" + no + "  物流公司：" + kind);

        let ajaxPromise = new Promise(function (resolve, reject) {
            $.ajax({
                url: 'http://wuliu.market.alicloudapi.com/kdi?no=' + no + "&type=" + kind,
                dataType: 'json',
                success(arr) {
                    resolve(arr);
                },
                error(err) {
                    reject(err);
                },
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("Authorization", "APPCODE c97ef3a8782beb682")
                }
            });
        });
        ajaxPromise.then(arr => {
            if (arr.status === "201") {
                alert('快递查询失败--' + arr.msg);
                return;
            }
            this.props.data.dataList = arr.result.list
        }, err => {
            alert('快递查询失败--' + err);
        });
        console.log("快递列表：" + this.props.data.dataList);
    }
    render() {
        return (
            <div className="App">
                <Button type="primary" onClick={e => this.query()}> 查询</Button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <Button type="danger" onClick={e => { alert('取消查询') }}>取消</Button>
                <p />
                <JPTimeline result={this.props.data.dataList} />
            </div>
        )
    }

}

class JPTimeline extends React.Component {
    constructor(props){
        super(props);
    }

    shouldComponentUpdate(nextProps,nextState){
        return true;
    }

    render() {
        return (
            <div>
                <Timeline mode='left'>
                    {(this.props.result).map((data, i) => (
                         <Timeline.Item key={i}>{data.time}-{data.status}</Timeline.Item>
                    ))}
                </Timeline>
            </div>
        )
    }
}

class JPInput extends React.Component {
    inputNo = e => {
        this.setState({
            no: e.target.value
        });
    };
    selectKind = value => {
        this.setState({
            kind: value
        });
    };

    constructor(props) {
        super(props);
        this.state = {
            no: "",
            kind: "",
            dataList: []
        }
    }

    render() {
        console.log(this.state.no);
        return (
            <div className="App">
                <Input placeholder="输入运单号" style={{ width: 180 }} onChange={e => this.inputNo(e)} />
                <p />
                <Select placeholder="请选择快递公司" style={{ width: 180 }} onChange={value => this.selectKind(value)}>
                    <Option value="">查所有</Option>
                    <Option value=".申通">申通</Option>
                    <Option value=".圆通">圆通</Option>
                    <Option value=".中通">中通</Option>
                    <Option value=".韵达">韵达</Option>
                </Select>
                <p />
                <JPButton data={this.state} />
            </div>
        )
    }
}

export default JPInput;