//index.js
//获取应用实例
const app = getApp()
wx.cloud.init({
  env: 'dev-9w15x'
})

Page({
  data: {
    name: '小程序入门',
    todos: ['吃饭', '睡觉', '打豆豆'],
    val: '',
    books: [],
    src: "https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1586697173&di=fded5d4746aa951619044bc8d4f7985b&src=http://b-ssl.duitang.com/uploads/item/201608/27/20160827172726_GJfX2.jpeg",

    musicName: '像我这样的人',
    author: '毛不易',
    poster: 'http://singerimg.kugou.com/uploadpic/softhead/400/20180611/20180611160019456.jpg',
    musicSrc: 'https://sharefs.yun.kugou.com//202004130106//5f99b0098214a54497e4e59d041d0064//G146//M07//02//14//cpQEAFwqEt2AMf4dACiMsITsnwk265.mp3',

    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo')
  },
  onReady(e) {
    // 使用 wx.createAudioContext 获取 audio 上下文 context
    this.audioCtx = wx.createAudioContext('myAudio')
  },
  onLoad() {
    console.log('index⻚⾯加载拉')
    this.getbooks()
    // this.initUserInfo()
  },
  getbooks() {
    wx.cloud.database().collection('bookdb').get({
      success(res) {
        console.log('获取到图书数据', res)
        this.setData({
          books: res.data
        })
      }
    })
  },
  initUserInfo() {
    if (app.globalData.userInfo) {
      console.log('存在用户数据')
      this.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true
      })
    } else if (this.data.canIUse) {
      console.log('支持 open-type=getUserInfo')
      // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      app.userInfoReadyCallback = res => {
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        })
      }
    } else {
      console.log('不支持 open-type=getUserInfo')
      // 在没有 open-type=getUserInfo 版本的兼容处理
      wx.getUserInfo({
        success: res => {
          app.globalData.userInfo = res.userInfo
          this.setData({
            userInfo: res.userInfo,
            hasUserInfo: true
          })
        }
      })
    }
  },
  onShow() {

    console.log('index⻚⾯显示拉')
  },
  onHide() {
    console.log('index⻚⾯隐藏拉')
  },
  handleInput(e) {
    console.log('input', e.detail)
    // 模拟react的setState
    this.setData({
      val: e.detail.value
    })
  },
  add() {
    this.setData({
      todos: [...this.data.todos, this.data.val],
      val: ''
    })
  },
  toast() {
    wx.showToast({
      title: '成功',
      icon: 'success',
      duration: 2000
    })
  },
  toLog() {
    wx.switchTab({
      url: '/pages/logs/logs',
    })
  },
  showLoading() {
    wx.showLoading({
      title: '加载中',
    })
    setTimeout(function () {
      wx.hideLoading({
        complete: (res) => {
          console.log('加载中效果消失')
        },
      })
    }, 3000)
  },
  showModeal() {
    wx.showModal({
      title: '确定吗？',
      content: '这是一个模拟弹窗',
      cancelColor: 'red',
      success(res) {
        if (res.confirm) {
          console.log('⽤户点击确定')
        } else if (res.cancel) {
          console.log('⽤户点击取消')
        }
      }
    })
  },
  takePhoto() {
    const ctx = wx.createCameraContext()
    ctx.takePhoto({
      quality: 'high',
      success: res => {
        this.setData({
          src: res.tempImagePath
        })
      }
    })
  },
  audioPlay() {
    console.log(this.audioCtx)
    this.audioCtx.play()
  },
  audioPause() {
    this.audioCtx.pause()
  },
  audio14() {
    this.audioCtx.seek(14)
  },
  audioStart() {
    this.audioCtx.seek(0)
  },

  //事件处理函数
  bindViewTap: function () {
    console.log('有人点了头像')
  },
  getUserInfo: function (e) {
    console.log(e)
    app.globalData.userInfo = e.detail.userInfo
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
  },
})