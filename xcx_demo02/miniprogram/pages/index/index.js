//index.js
const app = getApp()
wx.cloud.init({
  env: 'dev-9w15x'
})
const db = wx.cloud.database()

Page({
  data: {
    userInfo: wx.getStorageSync('userInfo') || {},
    books: [],
    page: 0,
    more: true
  },
  onLoad(){
    console.log('onLoad')
    this.getList(true)
  },
  onPullDownRefresh() {
    console.log('onPullDownRefresh')
    this.getList(true)
  },
  onReachBottom() {
    console.log('onReachBottom')
    this.setData({
      page: this.data.page + 1
    }, () => {
      console.log(this.data.page)
      this.getList()
    })
  },
  getList(init) {
    if (init) {
      this.setData({
        page: 0,
        more: true
      })
    }
    wx.showNavigationBarLoading()
    wx.showLoading({
      title: '加载中',
    })
    const PAGE = 3
    const offset = this.data.page * PAGE
    console.log(offset)
    let ret = db.collection('bookdb').orderBy('create_time', 'desc')
    if (offset > 0) {
      ret = ret.skip(offset)
    }

    ret = ret.limit(PAGE).get().then(books => {
      if (books.data.length < PAGE && this.data.page > 0) {
        this.setData({
          more: false
        })
      }
      if (init) {
        this.setData({
          books: books.data
        })
        wx.stopPullDownRefresh()
      } else {
        this.setData({
          books: [...this.data.books, ...books.data]
        })
        // 下拉刷新，不能直接覆盖books 而是累加
      }
      wx.hideLoading()
      wx.hideNavigationBarLoading()
    })
  }

})