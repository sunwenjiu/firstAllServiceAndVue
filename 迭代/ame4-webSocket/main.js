// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'
import iView from 'iview'
import i18n from '@/locale'
import config from '@/config'
import importDirective from '@/directive'
import { directive as clickOutside } from 'v-click-outside-x'
import installPlugin from '@/plugin'
import './index.less'
import '@/assets/icons/iconfont.css'
import TreeTable from 'tree-table-vue'
import VOrgTree from 'v-org-tree'
import 'v-org-tree/dist/v-org-tree.css'
import axios from '@/libs/http'
import websocketUrl from '@/libs/webSocketUrl'

// 实际打包时应该不引入mock
/* eslint-disable */
// if (process.env.NODE_ENV !== 'production') require('@/mock')

Vue.use(iView, {
  i18n: (key, value) => i18n.t(key, value)
})
Vue.use(TreeTable)
Vue.use(VOrgTree)


/**
 * @description 注册admin内置插件
 */
installPlugin(Vue)
/**
 * @description 生产环境关掉提示
 */
Vue.config.productionTip = false
/**
 * @description 全局注册应用配置
 */
Vue.prototype.$config = config

Vue.prototype.axios = axios


Vue.prototype.webSocketUrl = websocketUrl

/**
 * 注册指令
 */
importDirective(Vue)
Vue.directive('clickOutside', clickOutside)


// 注册权限指令
Vue.directive('has',{
  bind:function (el,binding) {
    let className = binding.value.class; //这个地方要注意了，看下面页面上的代码，对应取得相应的值
    if(!Vue.prototype.$_has(className)){
      el.parentNode.removeChild(el);
    }
  }
})
Vue.prototype.$_has = function (val) {
  let isShow = false;
  let btnPowerArr = Array.isArray(store.state.user.access) ? store.state.user.access : [store.state.user.access];
  if(btnPowerArr==undefined || btnPowerArr==null){
    return false;
  }
  if('*' == val || btnPowerArr.includes(val)){
    isShow = true;
  }
  return isShow;
}



/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  i18n,
  store,
  render: h => h(App)
})
