
<Page :total="pageTotal" :current="pageNum" :page-size="pageSize" show-elevator show-sizer @on-change="handlePage" @on-page-size-change='handlePageSize'/>



数据
 pageTotal: 0,
      pageNum: 1,
      pageSize: 10,



方法
  handlePage (value) {
      this.pageNum = value
      this.getEventDataFromService()
    },
    handlePageSize (value) {
      this.pageSize = value
      this.getEventDataFromService()
    },
    /* 向后台请求加载数据 */
    getEventDataFromService () {

      let page = {
        pageNo: this.pageNum - 1,
        pageSize: this.pageSize
      }

      this.axios.get('/event/show', { params: page }).then((response) => {
        const result = response.data
       // this.userData = result.data.content
        //this.pageTotal = result.data.totalElements
       // this.loading = false
      }).catch((error) => {
        alert(error.message)
      })
    },