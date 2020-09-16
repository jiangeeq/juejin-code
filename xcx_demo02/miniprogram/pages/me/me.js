//获取应用实例
const app = getApp()

wx.cloud.init({
  env: 'dev-9w15x'
})

const db = wx.cloud.database()
// pages/me/me.js
Page({
  data: {
    userInfo: wx.getStorageSync('userInfo') || {}
  },
  addBook(isbn) {
    wx.cloud.callFunction({
      name: 'douban',
      data: {
        isbn
      },
      success: ({
        result
      }) => {
        console.log(result)
        result.isbn = isbn
        result.userInfo = this.userInfo

        db.collection('bookdb').add({
          data: result,
          success(add) {
            if (add._id) {
              wx.showModal({
                title: '添加成功',
                content: `《${result.title}》添加成功`,
              })
            }
          }
        })
      }
    })
  },

  scanCode() {
    wx.scanCode({
      success: (res) => {
        if (res.result) {
          console.log(res.result)
          this.addBook(res.result)
        }
      }
    })
  },

  getUserinfo(e) {
    console.log(e)
    let userInfo = e.detail.userInfo

    wx.cloud.callFunction({
      name: 'login',
      complete: (res) => {
        console.log(res)
        userInfo.openid = res.result.openid
        this.setData({
          userInfo: userInfo
        })
        wx.setStorageSync('userInfo', userInfo)
      }
    })
  }
})