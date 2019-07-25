<template>
  <div>
    <!--用户添加-->
    <Modal
      v-model="gradeTableModal"
      title="用户添加"
      @on-ok="ok"
      width="400"
      :loading="create_loading"
      @on-cancel="cancel_create">
      <div>
        <Form ref="userAddFormItem" :model="userAddFormItem" :rules="ruleUserAddFormItem" :label-width="80">
          <FormItem label="用户名" prop="userName">
            <Input v-model="userAddFormItem.userName" style="width: 250px" placeholder="请输入用户名名称"></Input>
          </FormItem>
          <FormItem label="性别" prop="gender">
            <RadioGroup v-model="userAddFormItem.gender">
              <Radio label="MAN" >男</Radio>
              <Radio label="WOMAN">女</Radio>
            </RadioGroup>
          </FormItem>
          <FormItem label="手机号" prop="userTel">
            <Input v-model="userAddFormItem.userTel" style="width: 250px" placeholder="请输入用户手机号"></Input>
          </FormItem>

        </Form>
      </div>
    </Modal>
    <Modal
      v-model="modify_modal6"
      title="信息修改"
      ok-text="确认修改"
      :loading="modify_loading"
      @on-ok="asyncOK"
      @on-cancel="cancel">
      <div>
        <Form ref="modify_userAddFormItem" :model="userAddFormItem" :rules="ruleUserAddFormItem" :label-width="80">
          <FormItem label="用户名" prop="userName">
            <Input v-model="userAddFormItem.userName" style="width: 250px" placeholder="请输入用户名名称" disabled></Input>
          </FormItem>
          <FormItem label="性别" prop="gender">
            <RadioGroup v-model="userAddFormItem.gender">
              <Radio label="MAN" >男</Radio>
              <Radio label="WOMAN" >女</Radio>
            </RadioGroup>
          </FormItem>
          <FormItem label="手机号" prop="userTel">
            <Input v-model="userAddFormItem.userTel" style="width: 250px" placeholder="请输入用户手机号"></Input>
          </FormItem>

        </Form>
      </div>

    </Modal>
    <Card shadow title="用户管理">
      <div class="userTable-title-btn">
        <Button type="primary" icon="md-person-add" @click="addClick">添加</Button>
        <Button type="error" style="margin-left: 5px" v-show="showDelAll" @click="delAllByIds">多选删除</Button>
      </div>
      <div>
        <Table ref="selection" border :columns="userTable" :data="userData" @on-selection-change="changeShowDelAll()"></Table>
      </div>
    </Card>
    <Page :total="pageTotal" :current="pageNum" :page-size="pageSize" show-elevator show-sizer @on-change="handlePage" @on-page-size-change='handlePageSize'/>
  </div>
</template>

<script>
import { formatDate } from './data.js'
// 用户名正则，4到16位（字母，数字，下划线，减号）
var userName = /^[a-zA-Z0-9_]{4,16}$/
var tel = /^1[345789]\d{9}$/
export default {

  data () {
    const validateName = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('用户名称不能为空'))
      }
      if (!userName.test(value)) {
        callback(new Error('请输入正确用户名字!4到16位字母/数字/下划线组合'))
      } else if (value.length < 4 || value.length > 16) {
        // 有正则表达式跨域不要这个
        callback(new Error('请输入2到10个字符!'))
      } else if (this.userAddFormItem.nameAgin) {
        callback(new Error(this.userAddFormItem.nameAgin))
      } else {
        callback()
      }
    }
    const validateTel = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('手机不能为空'))
      }
      if (!tel.test(value)) {
        callback(new Error('请正确填写手机号码'))
      } else {
        callback()
      }
    }

    return {
      showDelAll: false,
      allIdData: [],
      pageTotal: 0,
      pageNum: 1,
      pageSize: 10,
      gradeTableModal: false,
      modify_modal6: false,
      modify_loading: true,
      create_loading: true,
      userAddFormItem: {
        userId: '',
        userName: '',
        textarea: '',
        nameAgin: '',
        gender: '',
        userTel: '',
        userSex: ''

      },
      ruleUserAddFormItem: {
        userName: [
          {
            validator: validateName,
            trigger: 'blur'
          }
        ],
        userTel: [
          { validator: validateTel,
            trigger: 'blur' }
        ],
        gender: [
          { required: true, message: '请选择性别', trigger: 'change' }
        ]

      },
      userTable: [
        {
          type: 'selection',
          width: 60,
          align: 'center'
        },
        {
          title: '序号',
          type: 'index',
          width: 65,
          align: 'center'
        },
        {
          title: '用户',
          key: 'userName'
        },
        {
          title: '角色权限',
          key: 'userRealName'
        },
        {
          title: '性别',
          key: 'userSex',
          render (h, params) {
            let renderText = params.row.userSex === 'MAN' ? '男' : '女'
            // jsx
            return h('span', renderText)
          }
        },

        {
          title: '电话',
          key: 'userTel'
        },
        {
          title: '创建时间',
          key: 'createTime',
          render (h, params) {
            return h('div', formatDate(new Date(params.row.createTime), 'yyyy-MM-dd hh:mm'))
          }
        },
        {
          title: '修改时间',
          key: 'updateTime',
          render (h, params) {
            return h('div', formatDate(new Date(params.row.updateTime), 'yyyy-MM-dd hh:mm'))
          }
        },
        {
          title: '操作',
          key: 'action',
          width: 150,
          align: 'center',
          render: (h, params) => {
            return h('div', [
              h('Button', {
                props: {
                  type: 'info',
                  size: 'small'
                },
                style: {
                  marginRight: '5px'
                },
                on: {
                  click: () => {
                    this.modify_show(params.index)
                  }
                }
              }, '修改'),
              h('Button', {
                props: {
                  type: 'error',
                  size: 'small'
                },
                on: {
                  click: () => {
                    this.modify_remove(params.index)
                  }
                }
              }, '删除')
            ])
          }
        }
      ],
      userData: [ ]
    }
  },
  mounted () {
    this.getuserDataFromService()
  },
  methods: {

    /* 多选、全选时删除 */
    delAllByIds () {
      // 封装id
      // 获取多选数据
      const allData = this.$refs.selection.getSelection()
      for (let i = 0; i < allData.length; i++) {
        this.allIdData.push(allData[i].userId)
      }
      // 发送请求
      if (window.confirm(`确认删除多选吗？`)) {
        const { allIdData } = this
        let str = ''
        for (let i = 0; i < allIdData.length; i++) {
          if (i === allIdData.length - 1) {
            str += allIdData[i]
          } else {
            str += allIdData[i] + ','
          }
        }
        let goDelData = {
          ids: str
        }
        this.axios.get('/user/delAll', {
          params: goDelData
        })
          .then((response) => {
            this.getuserDataFromService()
            this.allIdData = []
            this.showDelAll = false
          })
          .catch((error) => {
            alert(error.message)
            console.log(error)
          })
      }
    },
    /* 改变是否显示多选删除 */
    changeShowDelAll () {
      // console.log(this.$refs.selection.getSelection());
      this.showDelAll = this.$refs.selection.getSelection().length !== 0
    },

    clearLoading () {
      /* 清除loading状态 */
      this.modify_loading = false
      this.$nextTick(() => {
        this.modify_loading = true
      })

      this.create_loading = false
      this.$nextTick(() => {
        this.create_loading = true
      })
    },
    clearUserAddFormItem () {
      this.userAddFormItem = {
        userId: '',
        userName: '',
        textarea: '',
        nameAgin: '',
        gender: '',
        userTel: '',
        userSex: ''

      }
      /* this.userAddFormItem.nameAgin = ''
          this.userAddFormItem.userName = ''
          this.userAddFormItem.userSex = ''
          this.userAddFormItem.userTel='' */
    },
    handlePage (value) {
      this.pageNum = value
      this.getuserDataFromService()
    },
    handlePageSize (value) {
      this.pageSize = value
      this.getuserDataFromService()
    },
    /* 向后台请求加载数据 */
    getuserDataFromService () {
      this.showDelAll = false
      let page = {
        pageNo: this.pageNum - 1,
        pageSize: this.pageSize
      }

      this.axios.get('/user/show', { params: page }).then((response) => {
        const result = response.data
        this.userData = result.data.content
        this.pageTotal = result.data.totalElements
      }).catch((error) => {
        alert(error.message)
      })
    },
    asyncOK () {
      this.$refs.modify_userAddFormItem.validate(valid => {
        if (valid) {
          this.axios.post('/user/update', {
            userId: this.userAddFormItem.userId,
            userTel: this.userAddFormItem.userTel,
            userSex: this.userAddFormItem.gender

          }).then((response) => {
            const result = response.data
            const state = result.code

            if (state !== 200) {
              this.userAddFormItem.nameAgin = result.msg

              // this.$refs.modify_userAddFormItem.validateField('userName')
              /* 如果用户、用户名字重复，会执行下面代码 */
              if (this.userAddFormItem.nameAgin) {
                this.clearLoading()
              }
              this.userAddFormItem.nameAgin = ''
            } else {
              this.clearUserAddFormItem()
              this.modify_modal6 = false
              // location.reload()
              this.getuserDataFromService()
            }
          }).catch((error) => {
            alert('请求失败' + error.message)
            this.clearLoading()
          })
        } else {
          // this.$Message.error('创建外失败A外')
          this.clearLoading()
        }
      })
    },
    ok () {
      this.$refs.userAddFormItem.validate(valid => {
        if (valid) {
          this.axios.post('/user/create', {
            userName: this.userAddFormItem.userName,
            userTel: this.userAddFormItem.userTel,
            userSex: this.userAddFormItem.gender

          }).then((response) => {
            const result = response.data
            const state = result.code
            if (state !== 200) {
              this.userAddFormItem.nameAgin = result.msg

              this.$refs.userAddFormItem.validateField('userName')
              /* 如果用户、用户名字重复，会执行下面代码 */
              if (this.userAddFormItem.nameAgin) {
                this.clearLoading()
              }
              this.userAddFormItem.nameAgin = ''
            } else {
              this.clearUserAddFormItem()
              this.gradeTableModal = false
              // location.reload()
              this.getuserDataFromService()
            }
          }).catch((error) => {
            alert('请求失败' + error.message)
            this.clearLoading()
          })
        } else {
          // this.$Message.error('创建外失败A外')
          this.clearLoading()
        }
      })
    },
    cancel () {
      this.$Message.info('取消了操作')
      this.$refs.modify_userAddFormItem.resetFields()
    },
    cancel_create () {
      this.$Message.info('取消了操作')
      this.$refs.userAddFormItem.resetFields()
    },
    addClick () {
      this.gradeTableModal = true
    },
    modify_show (index) {
      this.modify_modal6 = true
      this.userAddFormItem.userName = this.userData[index].userName
      this.userAddFormItem.gender = this.userData[index].userSex
      this.userAddFormItem.userTel = this.userData[index].userTel
      this.userAddFormItem.userId = this.userData[index].userId
    },
    modify_remove (index) {
      if (window.confirm(`确认删除${this.userData[index].userName}吗？`)) {
        alert(this.userData[index].userId)
        this.axios.get('/user/del/' + this.userData[index].userId)
          .then((response) => {
            this.getuserDataFromService()
          })
          .catch((error) => {
            alert(error.message)
            console.log(error)
          })
          // this.userData.splice(index, 1)
      }
    }
  }
}
</script>

<style scoped>
  .userTable-title-btn{
    margin-bottom: 20px;
  }
</style>
