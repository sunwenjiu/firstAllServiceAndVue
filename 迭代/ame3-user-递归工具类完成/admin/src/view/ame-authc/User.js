import * as $ from 'jquery'
import util from '../../libs/util'
export default {
  data () {
    return {
      flag: true,
      // 用户列表
      columnName: [
        {
          title: '序号',
          key: 'userId',
          width: 80,
          render: (h, params) => {
            return h('div', [
              h('strong', params.index + 1)
            ])
          }
        },
        {
          title: '登录名',
          key: 'username'
        },
        {
          title: '角色',
          key: 'roles'
        },
        {
          title: '用户类型',
          key: 'type',
          render: (h, params) => {
            return h('p', params.row.type === 'MAN' ? '医生' : params.row.type === 'WOMAN' ? '护士' : '行政')
          }
        },
        {
          title: ' 电话',
          key: 'phoneNumber'
        },
        {
          title: '状态',
          key: 'avaliable',
          render: (h, params) => {
            return h('p', params.row.avaliable == '1' ? '启用' : params.row.avaliable == '0' ? '禁用' : '未知')
          }
        },
        {
          title: '操作',
          key: 'action',
          width: 280,
          align: 'center',
          render: (h, params) => {
            let actions = []
            if (true) {
              actions.push(h('Button', {
                props: {
                  type: 'primary',
                  size: 'small',
                  icon: 'edit'
                },
                style: {
                  marginRight: '10px'
                },
                on: {
                  click: () => {
                    // this.roleList();
                    this.flag = true
                    this.UserObject = $.extend(true, {}, params.row)
                    // if (params.row.role !== undefined && params.row.role !== null && params.row.role !== '') {
                    //     let role = params.row.role.split('，');
                    //     this.UserObject.role = role;
                    // }
                    this.addUserModal = true
                    this.status = 'edit'
                    this.modalTitle = '编辑用户'
                  }
                }
              }, '编辑'))
            }
            if (true) {
              actions.push(h('Button', {
                props: {
                  type: 'primary',
                  size: 'small',
                  icon: 'wrench'
                },
                style: {
                  marginRight: '10px'
                },
                on: {
                  click: () => {
                    this.flag = true
                    this.UserObject = $.extend(true, {}, params.row)
                    this.assignRoles()
                  }
                }
              }, '分配角色'))
            }
            if (true) {
              actions.push(h('Button', {
                props: {
                  type: 'error',
                  size: 'small',
                  icon: 'trash-b'
                },
                on: {
                  click: () => {
                    this.UserObject = $.extend(true, {}, params.row)
                    this.delUserModal = true
                  }
                }
              }, '删除'))
            }
            return h('div', actions)
          }
        }
      ],
      // 用户数据
      UserData: [
        { 'Number': '1号', 'userName': '哈哈', 'userRealName': '哈哈', 'role': '管理员', 'userSex': 'MAN', 'userTel': '12323', 'userStatus': 'NO_LOCK' },
        { 'Number': '2号', 'userName': '哈哈', 'userRealName': '哈哈', 'role': '管理员', 'userSex': 'MAN', 'userTel': '12323', 'userStatus': 'NO_LOCK' },
        { 'Number': '3号', 'userName': '哈哈', 'userRealName': '哈哈', 'role': '管理员', 'userSex': 'MAN', 'userTel': '12323', 'userStatus': 'NO_LOCK' }
      ],
      UserObject: {
        userId: '',
        userName: '',
        userPassword: '',
        userRealName: '',
        role: [],
        userSex: 'MAN',
        userTel: '',
        userStatus: 'NO_LOCK'
      },
      roleData: [], // 所有角色列表
      userRole: [], // 用户角色列表
      selections: [], // 选择的角色
      listStyle: {
        width: '300px',
        height: '300px'
      },
      pageNum: 0, // 起始页
      pageSize: 10, // 每页显示的条目数
      totalPages: 10, // 总页数，默认1页
      addUserModal: false,
      delUserModal: false,
      rePasswordModal: false,
      assignRoleModal: false,
      status: '',
      modalTitle: '',
      UserRules: {
        userName: [{
          required: true,
          message: '请填写登录名',
          trigger: 'blur'
        }],
        userPassword: [{
          required: true,
          message: '请输入密码',
          trigger: 'blur'
        }],
        userRealName: [{
          required: true,
          message: '请填写真实姓名',
          trigger: 'blur'
        }],
        userTel: [{
          required: false,
          message: '请输入正确的手机号码',
          pattern: /^1[3|4|5|7|8][0-9]{9}$/
        }]
      }
    }
  },
  mounted () {
    this.UserList(this.pageNum)
  },
  methods: {
    ok () {
    },
    cancel () {
      this.addUserModal = false
      this.$refs['UserObject'].resetFields()
    },
    // 用户列表，打开页面自动加载
    UserList (pageNum) {
      let requestParam = {
        pageNumber: pageNum,
        pageSize: 10
      }
      this.axios.get('authority/getAllUserByPage', { params: requestParam }).then((res) => {
        if (res.data.code === 0) {
          this.UserData = res.data.data.content
          this.totalPages = res.data.data.totalPages
        } else {
          this.UserData = []
        }
      }).catch((e) => {
        console.log(e)
      })
    },
    // 角色列表，获取所有角色
    roleListByAll () {
      this.axios.get('authority/getAllRoleByList').then((res) => {
        if (res.data.code === 0) {
          this.roleData = res.data.data
        } else {
          this.roleData = []
        }
      })
    },
    // 角色列表，获取用户拥有的角色
    roleListByUser (userId) {
      let requestParam = {
        userId: userId
      }
      this.axios.get('authority/getRoleListByUserId', { params: requestParam }).then((res) => {
        if (res.data.code === 0) {
          this.userRole = res.data.data
        } else {
          this.userRole = []
        }
      })
    },
    // 添加用户按钮
    addUserButton () {
      this.addUserModal = true
      this.flag = true
      this.modalTitle = '添加用户'
      this.status = 'save'
      this.UserObject = {
        userId: '',
        username: '',
        userpassword: '',
        type: 'MAN',
        phoneNumber: '',
        avaliable: '1'
      }
    },
    // 添加编辑用户
    addUser () {
      let userDto = {
        userId: this.UserObject.userId,
        username: this.UserObject.username,
        userpassword: util.CrypPwd(this.UserObject.userpassword),
        type: this.UserObject.type,
        phoneNumber: this.UserObject.phoneNumber,
        avaliable: this.UserObject.avaliable
      }
      this.axios.post('authority/saveOrUpdateUser', userDto).then((res) => {
        if (res.data.code === 0) {
          this.UserList(this.pageNum)
        } else {
          this.$Notice.success({
            title: res.data.msg
          })
        }
      }).catch((e) => {
        console.log(e)
      })
      this.addUserModal = false
    },
    // 分配角色按钮，先查用户的role
    assignRoles () {
      this.assignRoleModal = true
      // 加载所有角色
      this.roleListByAll()
      // 加载用户拥有的角色
      this.roleListByUser(this.UserObject.userId)
      // alert(this.UserObject.userId);
    },
    closeRoleeModel () {
      this.assignRoleModal = false
      this.UserList(this.pageNum)
    },
    renderFormat (item) {
      return item.label
    },
    // 选择角色后自动提交
    handleChange (targets, direction, moveKeys) {
      // right 添加  -  left 移除
      // console.log('目标' + targets);
      // console.log('方向' + direction);
      // console.log('移动' + moveKeys);
      let type
      if (direction == 'right') {
        type = 1
      } else if (direction == 'left') {
        type = 0
      }
      let requestParam = {
        userId: this.UserObject.userId,
        roleId: moveKeys.toString(),
        type: type
      }
      this.axios.get('authority/saveOrRemoveRoleByUser', { params: requestParam }).then((res) => {
        if (res.data.code === 0) {
          this.userRole = targets
        }
      }).catch((e) => {
        console.log(e)
      })
    },
    // 删除用户
    delUser () {
      let requestParam = {
        userId: this.UserObject.userId
      }
      this.axios.get('authority/deleteUserByAll', { params: requestParam }).then((res) => {
        if (res.data.code === 0) {
          this.UserList(this.pageNum)
        }
      }).catch((e) => {
        console.log(e)
      })
      this.delUserModal = false
    }
  }
}
