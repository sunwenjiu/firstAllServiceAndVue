
<style lang="less">
  @import './login.less';

</style>

<template>
  <div class="login">
    <div class="login-box">
      <div class="login-box-title">
        <h1 class="login-box-title-cn">不良事件申报&管理系统</h1>
        <p class="login-box-title-en">Adverse Event Reporting & Management System</p>
      </div>
      <div class="login-box-middle">
        <div class="login-left">
          <img src="@/assets/images/banner.jpg">
        </div>
        <div class="login-con">
          <div class="login-con-title">
            <h1>欢迎登录</h1><p><Icon type="md-arrow-dropright-circle" /></p>
          </div>
          <Card icon="log-in"  :bordered="false">

            <div class="form-con">
              <login-form @on-success-valid="handleSubmit"></login-form>
              <p class="login-tip"><a herf="#">忘记密码？</a></p>
            </div>
          </Card>
        </div>
      </div>
      <div class="login-box-bottom">
        <p>开发公司：昆明羽冠科技有限公司</p>
      </div>
    </div>
  </div>
</template>

<script>
  import LoginForm from '_c/login-form'
  import { mapActions } from 'vuex'
  export default {
    components: {
      LoginForm
    },
    data () {
      return {
        clientId:'admin'
      }
    },
    mounted () {
      // WebSocket
      if ('WebSocket' in window) {
        this.websocket = new WebSocket(this.webSocketUrl + this.clientId)
        this.initWebSocket()
      } else {
        alert('当前浏览器 Not support websocket')
      }
    },
    beforeDestroy () {
      this.onbeforeunload()
    },
    methods: {

      ...mapActions([
        'handleLogin',
        'getUserInfo'
      ]),
      handleSubmit ({ userName, password }) {
        this.handleLogin({ userName, password }).then(res => { //eslint-disable-line
          this.getUserInfo().then(res => {
            this.$router.push({
              name: this.$config.homeName
            })
          })
        })
      },
      // 测试axios的使用
      text () {
        let reequest = {
          aa: '',
          bb: ''
        }
        this.axios.get('roles/text1', { params: reequest }).then((res) => {
          if (res.data.code == 0) { //eslint-disable-line
            alert(JSON.stringify(res.data.data))
          }
        }).catch((e) => {
          console.log(e)
        })
      },


      //--------- websocket相关---------
      initWebSocket () {
        // 连接错误
        this.websocket.onerror = this.setErrorMessage

        // 连接成功
        this.websocket.onopen = this.setOnopenMessage

        // 收到消息的回调
        this.websocket.onmessage = this.setOnmessageMessage

        // 连接关闭的回调
        this.websocket.onclose = this.setOncloseMessage

        // 监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
        window.onbeforeunload = this.onbeforeunload
      },
      setErrorMessage () {
        console.log('WebSocket连接发生错误   状态码：' + this.websocket.readyState)
      },
      setOnopenMessage () {
        console.log('WebSocket连接成功    状态码：' + this.websocket.readyState)
      },
      setOnmessageMessage (event) {
        // 根据服务器推送的消息做自己的业务处理
        console.log('服务端返回：' + event.data)
        alert(event.data)
      },
      setOncloseMessage () {
        console.log('WebSocket连接关闭    状态码：' + this.websocket.readyState)
      },
      onbeforeunload () {
        this.closeWebSocket()
      },
      closeWebSocket () {
        this.websocket.close()
      }

    }
  }
</script>

<style>

</style>
