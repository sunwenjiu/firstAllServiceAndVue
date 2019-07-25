import HttpRequest from '@/libs/axios'
import config from '@/config'
const baseUrl = process.env.NODE_ENV === 'development' ? config.baseUrl.dev : config.baseUrl.pro
const requestBaseUrl = process.env.NODE_ENV === 'development' ? config.requestBaseUrl.dev : config.requestBaseUrl.pro

const axios = new HttpRequest(requestBaseUrl)
export default axios
