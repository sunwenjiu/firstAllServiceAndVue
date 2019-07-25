import axios from 'axios'
import store from '@/store'

const service = axios.create({
  baseURL: 'http://127.0.0.1:8080/',
  timeout: 60000,
  withCredentials: true
})
service.defaults.withCredentials = true
service.defaults.headers.post['Content-Type'] = 'application/json;charset=utf-8,text/event-stream'
service.interceptors.request.use(
  config => {
    if (store.state.user.loginFlag) {
      config.headers.Authorization = `${store.state.user.token}`
    }
    return config
  },
  err => {
    console.log('-------- 请求异常 ----------' + err)
    return Promise.reject(err)
  })

// http response 拦截器
service.interceptors.response.use(
  response => {
    return response
  },
  error => {
    console.log('------- error message -------' + JSON.stringify(error.message))
    if (error.message && error.message === 'Network Error') {
      console.log('-------- Network Error ----------')
    }
    if (error.message && error.message === 'timeout of 30000ms exceeded') {
      console.log('-------- timeout of 30000ms exceeded ----------')
    }
    if (error && error.response) {
      switch (error.response.status) {
        case 401:
          console.log('-------- error-401 ----------')
        case 302:
          console.log('-------- error-302----------')
        case 404:
          console.log('error-404')
        case 500:
          console.log('error-500')
      }
    }
    return Promise.reject(error)
  })

export default service
